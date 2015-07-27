package sortie.data.funcgroups.nci;

import java.util.ArrayList;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.SpeciesSpecific;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;
import sortie.gui.behaviorsetup.BehaviorParameterDisplay;

/**
 * Corresponds to the clNCIMasterGrowth class. This is newly updated with the more
 * flexible NCI system allowing the user to choose any combination of 
 * multiplicative effects.
 * @author lora
 */
public class NCIMasterGrowth extends NCIMasterBase { 

  /**NCI max potential growth for each species*/
  protected ModelVector mp_fNCIMaxPotentialGrowth = new ModelVector(
      "NCI Maximum Potential Growth, cm/yr",
      "gr_nciMaxPotentialGrowth",
      "gr_nmpgVal", 0,
      ModelVector.FLOAT);
  
  /**Standard deviation if normal or lognormal distribution is desired. One for 
   * each species.*/
  protected ModelVector mp_fRandParameter = new ModelVector("Std Deviation for Normal or Lognormal Adjustment", 
      "gr_standardDeviation", "gr_sdVal", 0, ModelVector.FLOAT);
  
  /** Which growth adjustment */
  protected ModelEnum m_iStochasticGrowthMethod = new ModelEnum(new int[] { 0, 3, 2 },
      new String[] { "None", 
                     "Normal",
                     "Lognormal"},
       "Growth Increment Adjustment PDF", "gr_stochGrowthMethod");
  
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
  public NCIMasterGrowth(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.nci");

    m_fVersion = 3.0;
    
    addRequiredData(mp_fNCIMaxPotentialGrowth);
    addRequiredData(m_iStochasticGrowthMethod);
    addRequiredData(mp_fRandParameter);
    
    //Default PDF adjustment to deterministic (no adjustment)
    m_iStochasticGrowthMethod.setValue(0);
    
    mp_oNewTreeDataMembers.add(new DataMember("Diameter growth", "Growth", DataMember.FLOAT));
  }
      
  /**
   * Overridden to capture pre-integration tags.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags, boolean[] p_bAppliesTo,
      Attributes oParentAttributes, Attributes[] p_oAttributes)
      throws ModelException {
    
    if (sXMLTag.equals("gr_weibClimMaxGrowth") ||
        sXMLTag.equals("gr_juvNCIMaxPotentialGrowth")) {
      sXMLTag = "gr_nciMaxPotentialGrowth";
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
    ValidationHelpers.makeSureAllPositive(mp_fNCIMaxPotentialGrowth, p_bAppliesTo);
    
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
    
    addDataObjectToDisplayArrays(mp_fNCIMaxPotentialGrowth, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    addDataObjectToDisplayArrays(m_iStochasticGrowthMethod, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    addDataObjectToDisplayArrays(mp_fRandParameter, p_oSingles, p_oSpeciesSpecific, p_bAppliesTo);
    BehaviorParameterDisplay oDisp = formatTable(p_oSingles, p_oSpeciesSpecific, oPop);
    
    
    ArrayList<BehaviorParameterDisplay> jReturn = formatEffectsDataForDisplay(oPop);
    if (jReturn == null) jReturn = new ArrayList<BehaviorParameterDisplay>(1);
    jReturn.add(0, oDisp);
    
    return jReturn;
  }
}
