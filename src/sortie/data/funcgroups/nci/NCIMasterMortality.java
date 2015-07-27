package sortie.data.funcgroups.nci;

import java.util.ArrayList;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.SpeciesSpecific;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;
import sortie.gui.behaviorsetup.BehaviorParameterDisplay;

/**
 * Corresponds to the clNCIGrowth class. This is newly updated with the more
 * flexible NCI system allowing the user to choose any combination of 
 * multiplicative effects.
 * @author lora
 */
public class NCIMasterMortality extends NCIMasterBase {

  /**NCI Mortality - Maximum probability of survival*/
  protected ModelVector mp_fNCIMaxProbSurvival = new ModelVector(
      "NCI Max Survival Probability (0-1)", "mo_nciMaxPotentialSurvival",
      "mo_nmpsVal", 0, ModelVector.FLOAT);
  
  /**Parameter period - default to 1*/
  protected ModelFloat m_fParamPeriod = new ModelFloat(1,
      "NCI Parameter Period (years)",
      "mo_nciMortSurvPeriod");
    
  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @throws ModelException 
   */
  public NCIMasterMortality(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.nci");

    m_fVersion = 3.0;
    
    addRequiredData(mp_fNCIMaxProbSurvival);
    addRequiredData(m_fParamPeriod);
    
    //Disallow for seedlings
    setCanApplyTo(TreePopulation.SEEDLING, false);
    
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));    
  }
    
    
  /**
   * Overridden to capture pre-integration tags.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags, boolean[] p_bAppliesTo,
      Attributes oParentAttributes, Attributes[] p_oAttributes)
      throws ModelException {
    
    //Capture old Weibull Climate survival tag
    if (sXMLTag.equals("mo_weibClimMaxSurv")) {
      sXMLTag = "mo_nciMaxPotentialSurvival";
    }
    
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if NCI Max growth is <= 0 for any species.
   */
  public void validateData(TreePopulation oPop) throws ModelException {

    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllAreProportions(mp_fNCIMaxProbSurvival, p_bAppliesTo);
    
    super.validateData(oPop);
  }

  
  /**
   * Formats data for display in a set of JTables. This is overridden so that
   * only those values appropriate to the effects functions that have been
   * chosen are used.
   * @param oPop TreePopulation object.
   * @return Vector of vectors suitable for creating a set of JTables, or null
   * if there is no data to display.
   */
  public ArrayList<BehaviorParameterDisplay> formatDataForDisplay(
      TreePopulation oPop) {
    ArrayList<ModelData> p_oSingles = new ArrayList<ModelData>(); 
    ArrayList<ArrayList<SpeciesSpecific>> p_oSpeciesSpecific = new ArrayList<ArrayList<SpeciesSpecific>>();
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    
    addDataObjectToDisplayArrays(mp_fNCIMaxProbSurvival, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    addDataObjectToDisplayArrays(m_fParamPeriod, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    BehaviorParameterDisplay oDisp = formatTable(p_oSingles, p_oSpeciesSpecific, oPop);
    
    
    ArrayList<BehaviorParameterDisplay> jReturn = formatEffectsDataForDisplay(oPop);
    if (jReturn == null) jReturn = new ArrayList<BehaviorParameterDisplay>(1);
    jReturn.add(0, oDisp);
    
    return jReturn;
  }
}
