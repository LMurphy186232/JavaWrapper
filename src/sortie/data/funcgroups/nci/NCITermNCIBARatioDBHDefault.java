package sortie.data.funcgroups.nci;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * NCI<sub>i</sub> is calculated as follows (simplifying the notation):
 * @htmlonly
  <center><i>NCI<sub>i</sub> = &Sigma; &lambda;<sub>k</sub>((DBH<sub>k</sub>)<sup>&alpha;</sup>/distance<sup>&beta;</sup>)</i></center>

  where:
  <ul>
  <li>we're summing over k = 0 to N neighbors greater than the minimum
  neighbor DBH</li>
  <li><i>&alpha;</i> is the neighbor DBH effect parameter</li>
  <li><i>&beta;</i> is the neighbor distance effect parameter</li>
  <li><i>DBH</i> is of the target tree, in cm</li>
  <li><i>DBH<sub>k</sub></i> is the DBH of the kth neighbor, in meters</li>
  <li><i>&gamma;</i> is the size sensitivity to NCI parameter</li>
  <li><i>&lambda;<sub>k</sub></i> is the NCI lambda parameter for the species
  of the kth neighbor</li>
  <li><i>distance</i> is distance from target to neighbor, in meters</li>
  </ul>
  <br>
  @endhtmlonly
 * 
 * This is not meant to be part of the growth behaviors collection, but to be 
 * accessed by NCI.
 *
 * @author LORA
 *
 */
public class NCITermNCIBARatioDBHDefault extends Behavior {
  
  /**NCI maximum adult crowding distance, in m, for each species*/
  protected ModelFloat m_fMaxAdultRadius = new ModelFloat(
      "Maximum Adult Crowding Distance, in meters", "nciMaxAdultCrowdingRadius");
  
  /**NCI maximum sapling crowding distance, in m, for each species*/
  protected ModelFloat m_fMaxSaplingRadius = new ModelFloat(
      "Maximum Sapling Crowding Distance, in meters", "nciMaxSaplingCrowdingRadius");

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
  
  /**DBH units adjustor*/
  protected ModelFloat m_fDBHAdjustor = new ModelFloat(0,
      "DBH Adjustment Factor", "nciDbhAdjustor");
  
  /**Default DBH, in cm*/
  protected ModelFloat m_fDefaultDBH = new ModelFloat(0,
      "Default DBH, in cm", "nciBADefaultDBH");
  
  /**
   * Constructor.
   */
  public NCITermNCIBARatioDBHDefault(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
        
    addRequiredData(m_fMaxAdultRadius);
    addRequiredData(m_fMaxSaplingRadius);
    addRequiredData(mp_fNCIAlpha);
    addRequiredData(mp_fNCIBeta);
    addRequiredData(mp_fNCIMinNeighborDBH);
    addRequiredData(m_fDefaultDBH);
    addRequiredData(m_fDBHAdjustor);
    
    //Default units adjustor to 1
    m_fDBHAdjustor.setValue(1);
    
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
   * <li>NCI Max. radius of neighbor effects is <= 0</li>
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
    ValidationHelpers.makeSureGreaterThan(m_fMaxAdultRadius, 0);
    ValidationHelpers.makeSureGreaterThan(m_fMaxSaplingRadius, 0);
    ValidationHelpers.makeSureAllNonNegative(mp_fNCIMinNeighborDBH); //this is for all species
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fDefaultDBH, 0);
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
      ModelData oData = mp_oAllData.get(i);
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
}
