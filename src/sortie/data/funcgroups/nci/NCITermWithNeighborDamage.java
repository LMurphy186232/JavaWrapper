package sortie.data.funcgroups.nci;

import java.util.ArrayList;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * NCI<sub>i</sub> is calculated as follows (simplifying the notation):
 * @htmlonly
  <center><i>NCI<sub>i</sub> = &Sigma; &eta; &lambda;<sub>k</sub>((DBH<sub>k</sub>/q)<sup>&alpha;</sup>/distance<sup>&beta;</sup>)</i></center>

  where:
  <ul>
  <li>we're summing over k = 0 to N neighbors greater than the minimum
  neighbor DBH</li>
  <li><i>&eta;</i> is the storm damage parameter of the target,
  depending on the damage status.  It’s 1 if the neighbor is undamaged.</li>
  <li><i>&alpha;</i> is the neighbor DBH effect parameter</li>
  <li><i>&beta;</i> is the neighbor distance effect parameter</li>
  <li><i>DBH</i> is of the target tree, in cm</li>
  <li><i>q</i> is the DBH divisor parameter</li>
  <li><i>DBH<sub>k</sub></i> is the DBH of the kth neighbor, in meters</li>
  <li><i>&gamma;</i> is the size sensitivity to NCI parameter</li>
  <li><i>&lambda;<sub>k</sub></i> is the NCI lambda parameter for the species
  of the kth neighbor</li>
  <li><i>distance</i> is distance from target to neighbor, in meters</li>
  </ul>
  <br>
  @endhtmlonly
 * Note that eta is per target species, not per neighbor (like lambda).
 * 
 * This is not meant to be part of the growth behaviors collection, but to be 
 * accessed by NCI.
 *
 * @author LORA
 *
 */
public class NCITermWithNeighborDamage extends Behavior {
  
  /**NCI maximum crowding distance, in m, for each species*/
  protected ModelVector mp_fNCIMaxCrowdingRadius = new ModelVector(
      "NCI Maximum Crowding Distance, in meters", "nciMaxCrowdingRadius",
      "nmcrVal", 0, ModelVector.FLOAT);

  /**NCI minimum DBH for crowding neighbors, for each species; all species
   * required*/
  protected ModelVector mp_fNCIMinNeighborDBH = new ModelVector(
      "NCI Minimum Neighbor DBH, in cm", "nciMinNeighborDBH",
      "nmndVal", 0, ModelVector.FLOAT, true);

  /**NCI alpha for each species*/
  protected ModelVector mp_fNCIAlpha = new ModelVector("NCI Alpha",
      "nciAlpha", "naVal", 0,
      ModelVector.FLOAT);

  /**NCI beta for each species*/
  protected ModelVector mp_fNCIBeta = new ModelVector("NCI Beta", "nciBeta",
      "nbVal", 0,
      ModelVector.FLOAT);
  
  /**NCI growth - Neighbor storm effect - medium damage*/
  protected ModelVector mp_fNCINeighStormEffMed = new ModelVector(
      "NCI Neighbor Storm Damage (eta) - Medium (0-1)",
      "nciNeighStormEffMedDmg", "nnsemdVal", 0, ModelVector.FLOAT);

  /**NCI growth - Neighbor storm effect - full damage*/
  protected ModelVector mp_fNCINeighStormEffFull = new ModelVector(
      "NCI Neighbor Storm Damage (eta) - Complete (0-1)",
      "nciNeighStormEffFullDmg", "nnsefdVal", 0, ModelVector.FLOAT);
  
  /**NCI - NCI DBH divisor*/
  protected ModelFloat m_fNCIDbhDivisor = new ModelFloat(1,
      "NCI DBH Divisor (q)", "nciDbhDivisor");

  /**NCI - Whether or not to include snags in NCI calculations*/
  protected ModelEnum m_iIncludeSnagsInNCI =
      new ModelEnum(new int[] {0, 1},
          new String[] {"false", "true"},
          "Include Snags in NCI Calculations",
          "nciIncludeSnagsInNCI");
  
  /**
   * Constructor.
   */
  public NCITermWithNeighborDamage(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    //Default the snags in NCI flag to false
    m_iIncludeSnagsInNCI.setValue("false");
    
    addRequiredData(mp_fNCIMaxCrowdingRadius);
    addRequiredData(mp_fNCIAlpha);
    addRequiredData(mp_fNCIBeta);
    addRequiredData(m_fNCIDbhDivisor);
    addRequiredData(m_iIncludeSnagsInNCI);
    addRequiredData(mp_fNCIMinNeighborDBH);
    addRequiredData(mp_fNCINeighStormEffMed);
    addRequiredData(mp_fNCINeighStormEffFull);
    
    TreePopulation oPop = m_oManager.getTreePopulation();
    int iNumSpecies = oPop.getNumberOfSpecies(), i; 
    for (i = 0; i < iNumSpecies; i++) {
      String sSpName = oPop.getSpeciesNameFromCode(i);
      ModelVector oVector = new ModelVector(
          sSpName.replace('_', ' ') + " NCI Lambda Neighbors",
          "nci" + sSpName + "NeighborLambda", "nlVal",
          0, ModelVector.FLOAT);

      addRequiredData(oVector);        
    }
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if:
   * <ul>
   * <li>NCI Max. radius of neighbor effects is <= 0 for any species</li>
   * <li>NCI DBH divisor is <= 0</li>
   * <li>Minimum neighbor DBH value is < 0</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {

    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    int i;

    //Count the number of lambdas
    int iNumLambdas = 0;
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData = mp_oAllData.get(i);
      String sDescriptor = oData.getDescriptor().toLowerCase();
      if (sDescriptor.indexOf("lambda") > -1) {
        ValidationHelpers.makeSureAllAreProportions((ModelVector) oData, p_bAppliesTo);
        iNumLambdas++;
      }
    }

    if (oPop.getNumberOfSpecies() > iNumLambdas) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "There is the wrong number of NCI lambda neighbors."));
    }
    ValidationHelpers.makeSureAllNonNegative(mp_fNCIMaxCrowdingRadius, p_bAppliesTo);
    ValidationHelpers.makeSureGreaterThan(m_fNCIDbhDivisor, 0);
    ValidationHelpers.makeSureAllNonNegative(mp_fNCIMinNeighborDBH); //this is for all species
    ValidationHelpers.makeSureAllAreProportions(mp_fNCINeighStormEffFull, p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(mp_fNCINeighStormEffMed, p_bAppliesTo);
  }

  /**
   * Overridden to handle lambdas.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) throws ModelException {
    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    
    //What we want to do is preserve lambdas any for species
    //that didn't change, because they may have values.

    //Create an array of the lambda arrays, placed in the new species
    //order
    int iCode, i, j,
        iNumSpecies = p_sNewSpecies.length;
    ModelData[] p_oNCI = new ModelData[iNumSpecies];
    

    for (i = 0; i < iNumSpecies; i++) {
      p_oNCI[i] = null;
    }

    //Get the existing lambdas and put them where they go in our arrays -
    //any for species that were removed will be left behind
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData = mp_oAllData.get(i);
      String sKey = oData.getDescriptor().toLowerCase();
      if (sKey.indexOf("lambda neighbor") > -1) {
        //get the species
        String sSpecies;
        sKey = oData.getDescriptor();
        sSpecies = sKey.substring(0, sKey.indexOf(" NCI")).replace(' ', '_');
        iCode = -1;
        for (j = 0; j < p_sNewSpecies.length; j++) {
          if (sSpecies.equals(p_sNewSpecies[j])) {
            iCode = j;
            break;
          }
        }
        //iCode = oPop.getSpeciesCodeFromName(sSpecies);
        if (iCode > -1) {
          p_oNCI[iCode] = oData;
        }
      }
    }

    //Now remove all lambdas from the required data
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData = mp_oAllData.get(i);
      String sKey = oData.getDescriptor().toLowerCase();
      if (sKey.indexOf("lambda neighbor") > -1) {
        mp_oAllData.remove(i);
        i--;
      }
    }
    
    //Now add back the lambdas, creating new ones for any new species
    for (i = 0; i < p_oNCI.length; i++) {
      if (p_oNCI[i] == null) {
        p_oNCI[i] = new ModelVector(
            p_sNewSpecies[i].replace('_', ' ') + " NCI Lambda Neighbors",
            "nci" + p_sNewSpecies[i] + "NeighborLambda", "nlVal",
            0, ModelVector.FLOAT);
      }
      mp_oAllData.add(p_oNCI[i]);      
    }

  }

  /**
   * Updates the lambda neighbor names when a species name is changed.
   * @param sOldSpecies String Old name of the species
   * @param sNewSpecies String New name of the species
   */
  public void changeOfSpeciesName (String sOldSpecies, String sNewSpecies) {
    int i;

    sOldSpecies = sOldSpecies.replace('_', ' ');

    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData =  mp_oAllData.get(i);
      String sKey = oData.getDescriptor();
      if (sKey.indexOf(sOldSpecies) > -1) {
        ModelVector oVector = (ModelVector) oData;
        oVector.setDescriptor(sNewSpecies.replace('_', ' ') + 
            " NCI Lambda Neighbors");
        oVector.setXMLTag("nci" + sNewSpecies + "NeighborLambda");        
      }
    }
    super.changeOfSpeciesName(sOldSpecies, sNewSpecies);
  }

  /**
   * Updates the lambda when a species is copied. The lambdas for that neighbor
   * are made identical to those being copied as well as entries for species
   * within the lambda.
   * @param iSpeciesCopyFrom int Species to copy.
   * @param iSpeciesCopyTo int Species that is the copy.
   * @throws ModelException if there is a problem.
   */
  public void copySpecies(int iSpeciesCopyFrom, int iSpeciesCopyTo)
      throws ModelException {
    TreePopulation oPop = m_oManager.getTreePopulation();
    ModelVector oCopyFrom = null, oCopyTo = null;
    ModelData oData;
    Float f1, f2;
    String sCopyFrom = oPop.getSpeciesNameFromCode(iSpeciesCopyFrom).replace('_', ' '),
        sCopyTo = oPop.getSpeciesNameFromCode(iSpeciesCopyTo).replace('_', ' ');
    int i;
    //Names can be very short - like "A" - so make sure that they are 
    //surrounded by whitespace to ensure they were a whole word
    sCopyFrom = sCopyFrom.concat(" ");
    sCopyTo = sCopyTo.concat(" ");

    //Find the lambdas in our list
    for (i = 0; i < mp_oAllData.size(); i++) {
      oData =  mp_oAllData.get(i);
      String sKey = oData.getDescriptor();
      if (sKey.indexOf(sCopyFrom) > -1 &&
          sKey.indexOf("Lambda") > -1) {
        oCopyFrom = (ModelVector) oData;
      }
      if (sKey.indexOf(sCopyTo) > -1 &&
          sKey.indexOf("Lambda") > -1) {
        oCopyTo = (ModelVector) oData;
      }
    }

    if (null == oCopyFrom || null == oCopyTo) {
      throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
          "Growth could not find NCI lambdas for neighbor"
              + " species when copying species."));
    }

    //Do it differently depending on whether there are existing values or not
    //in the target array
    if (oCopyTo.getValue().size() > 0) {
      for (i = 0; i < oCopyFrom.getValue().size(); i++) {
        f1 = (Float)oCopyFrom.getValue().get(i);
        if (null == f1)
          f2 = null;
        else
          f2 = new Float(f1.floatValue());
        oCopyTo.getValue().remove(i);
        oCopyTo.getValue().add(i, f2);
      }
    } else {
      for (i = 0; i < oCopyFrom.getValue().size(); i++) {
        f1 = (Float)oCopyFrom.getValue().get(i);
        if (null == f1)
          f2 = null;
        else
          f2 = new Float(f1.floatValue());
        oCopyTo.getValue().add(i, f2);
      }
    }
    super.copySpecies(iSpeciesCopyFrom, iSpeciesCopyTo);
  }
  
  /**
   * Overridden to capture tag changes with the integration of the NCI
   * behaviors.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags,
      boolean[] p_bAppliesTo, Attributes oParentAttributes,
      Attributes[] p_oAttributes) throws ModelException {
    
    if (sXMLTag.equals("gr_nciMaxCrowdingRadius") || sXMLTag.equals("mo_nciMaxCrowdingRadius")) {
      sXMLTag = "nciMaxCrowdingRadius";
    } else if (sXMLTag.equals("gr_nciMinNeighborDBH") || sXMLTag.equals("mo_nciMinNeighborDBH")) {
      sXMLTag = "nciMinNeighborDBH";
    } else if (sXMLTag.equals("gr_nciAlpha") || sXMLTag.equals("mo_nciNeighDBHEff")) {
      sXMLTag = "nciAlpha";
    } else if (sXMLTag.equals("gr_nciBeta") || sXMLTag.equals("mo_nciNeighDistEff")) {
      sXMLTag = "nciBeta";
    } else if (sXMLTag.equals("gr_nciNeighStormEffMedDmg") || sXMLTag.equals("mo_nciNeighStormEffMedDmg")) {
      sXMLTag = "nciNeighStormEffMedDmg";
    } else if (sXMLTag.equals("gr_nciNeighStormEffFullDmg") || sXMLTag.equals("mo_nciNeighStormEffFullDmg")) {
      sXMLTag = "nciNeighStormEffFullDmg";
    } 
    
    //Lambda
    else if ((sXMLTag.startsWith("gr_nci") && p_sChildXMLTags[0].equals("gr_nlVal")) ||
             (sXMLTag.startsWith("mo_nci") && p_sChildXMLTags[0].equals("mo_nlVal"))) {
      sXMLTag = sXMLTag.substring(3);
    }
    
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }
  
  /**
   * Overridden to capture tag changes with the integration of the NCI
   * behaviors.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes, Object oData) throws ModelException {

    if (sXMLTag.equals("gr_nciDbhDivisor") || sXMLTag.equals("mo_nciDbhDivisor")) {
      sXMLTag = "nciDbhDivisor";
    } else if (sXMLTag.equals("gr_nciIncludeSnagsInNCI") || sXMLTag.equals("mo_nciIncludeSnagsInNCI")) {
      sXMLTag = "nciIncludeSnagsInNCI";
    }

    return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes, oData);
  }
}
