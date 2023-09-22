package sortie.data.funcgroups.nci;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JDialog;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.behaviorsetup.BehaviorParameterDisplay;
import sortie.gui.behaviorsetup.NCIParameterEdit;

/**
 * The base for behaviors using the flexible NCI system allowing the user to 
 * choose any combination of multiplicative effects.
 * @author lora
 */
public class NCIMasterBase extends Behavior {
  
  /** Set of possible shading effects */
  protected ArrayList<NCIEffect> mp_iShadingEffects = new ArrayList<NCIEffect>(0);
  
  /** Which shading effect */
  protected ModelInt m_iShadingEffect = new ModelInt("Shading Effect", "nciWhichShadingEffect");
  
  /** Set of possible crowding effects */
  protected ArrayList<NCIEffect> mp_iCrowdingEffects = new ArrayList<NCIEffect>(0);
  
  /** Which crowding effect */
  protected ModelInt m_iCrowdingEffect = new ModelInt("Crowding Effect", "nciWhichCrowdingEffect");
  
  /** Set of possible NCI terms */
  protected ArrayList<NCIEffect> mp_iNCITerms = new ArrayList<NCIEffect>(0);
  
  /** Which NCI term */
  protected ModelInt m_iNCITerm = new ModelInt("NCI Term", "nciWhichNCITerm");
  
  /** Set of possible size effects */
  protected ArrayList<NCIEffect> mp_iSizeEffects = new ArrayList<NCIEffect>(0);
  
  /** Which size effect */
  protected ModelInt m_iSizeEffect = new ModelInt("Size Effect", "nciWhichSizeEffect");
  
  /** Set of possible damage effects */
  protected ArrayList<NCIEffect> mp_iDamageEffects = new ArrayList<NCIEffect>(0);
  
  /** Which damage effect */
  protected ModelInt m_iDamageEffect = new ModelInt("Damage Effect", "nciWhichDamageEffect");
  
  /** Set of possible precipitation effects */
  protected ArrayList<NCIEffect> mp_iPrecipEffects = new ArrayList<NCIEffect>(0);
  
  /** Which precipitation effect */
  protected ModelInt m_iPrecipEffect = new ModelInt("Precipitation Effect", "nciWhichPrecipitationEffect");
  
  /** Set of possible temperature effects */
  protected ArrayList<NCIEffect> mp_iTempEffects = new ArrayList<NCIEffect>(0);
  
  /** Which temperature effect */
  protected ModelInt m_iTempEffect = new ModelInt("Temperature Effect", "nciWhichTemperatureEffect");
  
  /** Set of possible nitrogen effects */
  protected ArrayList<NCIEffect> mp_iNEffects = new ArrayList<NCIEffect>(0);
  
  /** Which nitrogen effect */
  protected ModelInt m_iNEffect = new ModelInt("Nitrogen Effect", "nciWhichNitrogenEffect");
  
  /** Set of possible infection effects */
  protected ArrayList<NCIEffect> mp_iInfectionEffects = new ArrayList<NCIEffect>(0);
  
  /** Which infection effect */
  protected ModelInt m_iInfectionEffect = new ModelInt("Infection Effect", "nciWhichInfectionEffect");
    
  /** The multiplicative effects assigned. */
  protected ArrayList<Behavior> mp_oEffects;
  
  /** Whether or not the NCI behavior uses individuals and therefore will
   * have target diameters available. */
  protected boolean m_bUsesIndividuals = true;  

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @param sHelpString Map value for the help topic for this behavior.
   * @throws ModelException 
   */
  public NCIMasterBase(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString, String sHelpString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, sHelpString);

    m_iBehaviorSetupType = setupType.custom_display;
    
    //Add shading effects
    mp_iShadingEffects.add(new NCIEffect(null, 
        "No shading",
        NCIEffect.shading_effect.no_shading.ordinal(),
        true));
    mp_iShadingEffects.add(new NCIEffect(ShadingDefault.class, 
        "Default shading", 
        NCIEffect.shading_effect.default_shading.ordinal(),
        true));
    mp_iShadingEffects.add(new NCIEffect(ShadingEffectPower.class, 
        "Power function of light", 
        NCIEffect.shading_effect.power_shading.ordinal(),
        true));
    
    
    // Add crowding effects
    mp_iCrowdingEffects.add(new NCIEffect(null,
        "No crowding",
        NCIEffect.crowding_effect.no_crowding_effect.ordinal()));

    mp_iCrowdingEffects.add(new NCIEffect(CrowdingEffectDefault.class,
        "Default crowding",
        NCIEffect.crowding_effect.default_crowding_effect.ordinal()));

    mp_iCrowdingEffects.add(new NCIEffect(CrowdingEffectDefault.class,
        "Crowding version 2",
        NCIEffect.crowding_effect.crowding_effect_two.ordinal(),
        true));

    mp_iCrowdingEffects.add(new NCIEffect(CrowdingEffectNoSize.class,
        "Crowding no size term",
        NCIEffect.crowding_effect.crowding_effect_no_size.ordinal()));
    
    mp_iCrowdingEffects.add(new NCIEffect(CrowdingEffectTempDep.class,
        "Temperature dependent crowding",
        NCIEffect.crowding_effect.crowding_effect_temp_dep.ordinal()));
   
    
    
    // Add NCI terms
    mp_iNCITerms.add(new NCIEffect(null, 
        "No NCI term", 
        NCIEffect.nci_term.no_nci_term.ordinal(), false));

    mp_iNCITerms.add(new NCIEffect(NCITermDefault.class, 
        "Default NCI term",
        NCIEffect.nci_term.default_nci_term.ordinal(), false));

    mp_iNCITerms.add(new NCIEffect(NCITermWithNeighborDamage.class, 
        "NCI with neighbor damage",
        NCIEffect.nci_term.nci_with_neighbor_damage.ordinal(), false));

    mp_iNCITerms.add(new NCIEffect(NCILargerNeighbors.class, 
        "Count of larger neighbors",
        NCIEffect.nci_term.larger_neighbors.ordinal(), false));

    mp_iNCITerms.add(new NCIEffect(NCINeighborBA.class, 
        "BA of neighbors",
        NCIEffect.nci_term.neighbor_ba.ordinal(), false));

    mp_iNCITerms.add(new NCIEffect(NCIWithSeedlings.class, 
        "NCI with seedlings",
        NCIEffect.nci_term.nci_with_seedlings.ordinal(), false));
    
    mp_iNCITerms.add(new NCIEffect(NCITermBARatio.class, 
        "BA ratio",
        NCIEffect.nci_term.nci_ba_ratio.ordinal(), true));
    
    mp_iNCITerms.add(new NCIEffect(NCITermBARatioDBHDefault.class, 
        "BA ratio with target default DBH",
        NCIEffect.nci_term.nci_ba_ratio_dbh_default.ordinal(), false));
    
    mp_iNCITerms.add(new NCIEffect(NCITermNCIBARatio.class, 
        "NCI + BA ratio",
        NCIEffect.nci_term.nci_nci_ba_ratio.ordinal(), true));
    
    mp_iNCITerms.add(new NCIEffect(NCITermNCIBARatioDBHDefault.class, 
        "NCI + BA ratio with target default DBH",
        NCIEffect.nci_term.nci_nci_ba_ratio_ba_default.ordinal(), false));
    
    mp_iNCITerms.add(new NCIEffect(NCITermNCITempDepBARatio.class, 
        "NCI with temp dependent lambda + BA ratio",
        NCIEffect.nci_term.nci_nci_temp_dep_ba_ratio.ordinal(), true));
    
    mp_iNCITerms.add(new NCIEffect(NCITermNCITempDepBARatioDBHDefault.class, 
        "NCI with temp dependent lambda + BA ratio, target default DBH",
        NCIEffect.nci_term.nci_nci_temp_dep_ba_ratio_ba_default.ordinal(), false));

    
    // Add size effects
    mp_iSizeEffects.add(new NCIEffect(null,
        "No size effect",
        NCIEffect.size_effect.no_size_effect.ordinal(),
        true));

    mp_iSizeEffects.add(new NCIEffect(SizeEffectDefault.class,
        "Default size effect",
        NCIEffect.size_effect.default_size_effect.ordinal(),
        true));

    mp_iSizeEffects.add(new NCIEffect(SizeEffectLowerBounded.class,
        "Size effect w/ lower bound",
        NCIEffect.size_effect.size_effect_bounded.ordinal(),
        true));

    mp_iSizeEffects.add(new NCIEffect(SizeEffectPowerFunction.class,
        "Size effect power function",
        NCIEffect.size_effect.size_effect_power_function.ordinal(),
        true));

    mp_iSizeEffects.add(new NCIEffect(SizeEffectShiftedLognormal.class,
        "Size effect shifted lognormal",
        NCIEffect.size_effect.size_effect_shifted_lognormal.ordinal(),
        true));
    
    mp_iSizeEffects.add(new NCIEffect(SizeEffectCompoundExponential.class,
        "Size effect compound exponential",
        NCIEffect.size_effect.size_effect_compound_exp.ordinal(),
        true));
    
    mp_iSizeEffects.add(new NCIEffect(SizeEffectShiftedLogInf.class,
        "Size effect shifted lognormal w/ infestation",
        NCIEffect.size_effect.size_effect_shifted_log_inf.ordinal(),
        true));
    
    mp_iSizeEffects.add(new NCIEffect(SizeEffectCompoundExpInf.class,
        "Size effect compound exponential w/ infestation",
        NCIEffect.size_effect.size_effect_compound_exp_inf.ordinal(),
        true));
    
    mp_iSizeEffects.add(new NCIEffect(SizeEffectExponentialHeight.class,
        "Size effect exponential of height",
        NCIEffect.size_effect.size_effect_exp_height.ordinal(),
        true));

    
    
    // Add damage effects
    mp_iDamageEffects.add(new NCIEffect(null,
        "No storm damage",
        NCIEffect.damage_effect.no_damage_effect.ordinal(),
        true));
    
    mp_iDamageEffects.add(new NCIEffect(DamageEffectDefault.class,
        "Default storm damage",
        NCIEffect.damage_effect.default_damage_effect.ordinal(), 
        true));
        
    
    
    // Add precipitation effects
    mp_iPrecipEffects.add(new NCIEffect(null,
        "No precipitation effect",
        NCIEffect.precipitation_effect.no_precip_effect.ordinal()));

    mp_iPrecipEffects.add(new NCIEffect(PrecipitationEffectWeibull.class,
        "Weibull precipitation effect",
        NCIEffect.precipitation_effect.weibull_precip_effect.ordinal()));

    mp_iPrecipEffects.add(new NCIEffect(PrecipitationEffectDoubleLogistic.class,
        "Double logistic precipitation effect",
        NCIEffect.precipitation_effect.double_logistic_precip_effect.ordinal()));
    
    mp_iPrecipEffects.add(new NCIEffect(PrecipitationEffectDoubleNoLocalDiff.class,
            "Double no local diff. precip effect",
            NCIEffect.precipitation_effect.double_no_local_diff_precip_effect.ordinal()));

    
    
    // Add temperature effects
    mp_iTempEffects.add(new NCIEffect(null,
        "No temperature effect",
        NCIEffect.temperature_effect.no_temp_effect.ordinal()));

    mp_iTempEffects.add(new NCIEffect(TemperatureEffectWeibull.class,
        "Weibull temperature effect",
        NCIEffect.temperature_effect.weibull_temp_effect.ordinal()));

    mp_iTempEffects.add(new NCIEffect(TemperatureEffectDoubleLogistic.class,
        "Double logistic temperature effect",
        NCIEffect.temperature_effect.double_logistic_temp_effect.ordinal()));
    
    mp_iTempEffects.add(new NCIEffect(TemperatureEffectDoubleNoLocalDiff.class,
            "Double no local diff. temp. effect",
            NCIEffect.temperature_effect.double_no_local_diff_temp_effect.ordinal()));

    
    
    // Add nitrogen effects
    mp_iNEffects.add(new NCIEffect(null,
        "No nitrogen effect", 
        NCIEffect.nitrogen_effect.no_nitrogen_effect.ordinal()));

    mp_iNEffects.add(new NCIEffect(NitrogenEffectGaussian.class,
        "Gaussian nitrogen effect",
        NCIEffect.nitrogen_effect.gauss_nitrogen_effect.ordinal()));

    
    
    // Add infection effects
    mp_iInfectionEffects.add(new NCIEffect(null,
        "No infection effect",
        NCIEffect.infection_effect.no_infection_effect.ordinal()));

    mp_iInfectionEffects.add(new NCIEffect(InfectionEffect.class,
        "Size independent infection effect", 
        NCIEffect.infection_effect.infection_effect.ordinal(),
        true));
    
    mp_iInfectionEffects.add(new NCIEffect(InfectionEffectSizeDependent.class,
        "Size dependent infection effect", 
        NCIEffect.infection_effect.infection_effect_size_dep.ordinal(),
        true));
        
    
    addRequiredData(m_iShadingEffect);
    addRequiredData(m_iCrowdingEffect);
    addRequiredData(m_iNCITerm);
    addRequiredData(m_iSizeEffect);
    addRequiredData(m_iDamageEffect);
    addRequiredData(m_iPrecipEffect);
    addRequiredData(m_iTempEffect);
    addRequiredData(m_iNEffect);
    addRequiredData(m_iInfectionEffect);
    
    //Default effects to none
    m_iShadingEffect.setValue(0);
    m_iCrowdingEffect.setValue(0);
    m_iNCITerm.setValue(0);
    m_iSizeEffect.setValue(0);
    m_iDamageEffect.setValue(0);
    m_iPrecipEffect.setValue(0);
    m_iTempEffect.setValue(0);
    m_iNEffect.setValue(0);
    m_iInfectionEffect.setValue(0);
        
    mp_oEffects = new ArrayList<Behavior>(0);
  }
  
  /**
   * @return the m_bUsesIndividuals
   */
  public boolean usesIndividuals() {
    return m_bUsesIndividuals;
  }
    
  /**
   * Overridden to capture effects choices. A choice for an effect will cause
   * the corresponding behavior object to be added to the effects list.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes, Object oData) throws ModelException {

    if (sXMLTag.equals("nciWhichShadingEffect")) {
      int iChoice = Integer.parseInt(oData.toString());
      setEffect(mp_iShadingEffects, m_iShadingEffect, iChoice);
      return true;
      
    } else if (sXMLTag.equals("nciWhichCrowdingEffect")) {
      int iChoice = Integer.parseInt(oData.toString());
      setEffect(mp_iCrowdingEffects, m_iCrowdingEffect, iChoice);
      return true;      
      
    } else if (sXMLTag.equals("nciWhichNCITerm")) {
      int iChoice = Integer.parseInt(oData.toString());
      setEffect(mp_iNCITerms, m_iNCITerm, iChoice);
      return true;            
      
    } else if (sXMLTag.equals("nciWhichSizeEffect")) {
      int iChoice = Integer.parseInt(oData.toString());
      setEffect(mp_iSizeEffects, m_iSizeEffect, iChoice);
      return true;            
      
    } else if (sXMLTag.equals("nciWhichDamageEffect")) {
      int iChoice = Integer.parseInt(oData.toString());
      setEffect(mp_iDamageEffects, m_iDamageEffect, iChoice);
      return true;    

    } else if (sXMLTag.equals("nciWhichPrecipitationEffect")) {
      int iChoice = Integer.parseInt(oData.toString());
      setEffect(mp_iPrecipEffects, m_iPrecipEffect, iChoice);
      return true;      

    } else if (sXMLTag.equals("nciWhichTemperatureEffect")) {
      int iChoice = Integer.parseInt(oData.toString());
      setEffect(mp_iTempEffects, m_iTempEffect, iChoice);
      return true;
    
    } else if (sXMLTag.equals("nciWhichNitrogenEffect")) {
      int iChoice = Integer.parseInt(oData.toString());
      setEffect(mp_iNEffects, m_iNEffect, iChoice);
      return true;
        
    } else if (sXMLTag.equals("nciWhichInfectionEffect")) {
      int iChoice = Integer.parseInt(oData.toString());
      setEffect(mp_iInfectionEffects, m_iInfectionEffect, iChoice);
      return true;
    }

    //Allow all the effects to have a crack at this value
    for (Behavior oBeh : mp_oEffects) {
      if (oBeh.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes, oData))
        return true;
    }
    return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes, oData);
  }
  
  /**
   * Overridden to allow effects to read parameters as well.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags, boolean[] p_bAppliesTo,
      Attributes oParentAttributes, Attributes[] p_oAttributes)
      throws ModelException {
        
    for (Behavior oBeh : mp_oEffects) {
      if (oBeh.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
          p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes))
        return true;
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
    for (Behavior oBehavior : mp_oEffects) oBehavior.validateData(oPop);
  }

  /**
   * Overridden to allow effects to write their parameters.
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop)
      throws ModelException {
    try {
      if (m_sXMLRootString.length() > 0) {
        if (m_iListPosition == -1)
          jOut.write("<" + m_sXMLRootString + ">");
        else
          jOut.write("<" + m_sXMLRootString + m_iListPosition + ">");
      }
      Object oData;
      ModelData oDataPiece;
      ModelVector p_oDataVector;
      int i;
      for (i = 0; i < mp_oAllData.size(); i++) {
        oData = mp_oAllData.get(i);
        if (oData instanceof ModelVector) {
          p_oDataVector = (ModelVector) oData;
          writeSpeciesSpecificValue(jOut, p_oDataVector, oPop);
        }
        else if (oData instanceof ModelData) {
          oDataPiece = (ModelData) oData;
          writeDataToFile(jOut, oDataPiece);
        }        
      }
      
      for (Behavior oBehavior : mp_oEffects) oBehavior.writeXML(jOut, oPop);
      
      if (m_sXMLRootString.length() > 0) {
        if (m_iListPosition == -1)
          jOut.write("</" + m_sXMLRootString + ">");
        else
          jOut.write("</" + m_sXMLRootString + m_iListPosition + ">");
      }
    }
    catch (IOException oExp) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
          "There was a problem writing the parameter file."));
    }
  }

  /**
   * Override to give effects behaviors the same instructions
   */
  public void deleteSpeciesTypeCombo(int iIndex) throws ModelException {
    //Do this in a safe way - since child behaviors get calls to species/type
    //combo changers like changeOfSpecies, they may already be updated
    SpeciesTypeCombo oCombo = mp_oTreesAppliesTo.get(iIndex);
    for (Behavior oBehavior : mp_oEffects) {
      oBehavior.deleteSpeciesTypeCombo(oCombo);
    }
    super.deleteSpeciesTypeCombo(iIndex);
  }

  /**
   * Override to give effects behaviors the same instructions
   */
  public void clearSpeciesTypeCombos() {
    for (Behavior oBehavior : mp_oEffects) oBehavior.clearSpeciesTypeCombos();
    super.clearSpeciesTypeCombos();
  }

  /**
   * Override to give effects behaviors the same instructions
   */
  public void addSpeciesTypeCombo(SpeciesTypeCombo oCombo)
      throws ModelException {
    for (Behavior oBehavior : mp_oEffects) oBehavior.addSpeciesTypeCombo(oCombo);
    super.addSpeciesTypeCombo(oCombo);
  }

  /**
   * Override to give effects behaviors the same instructions
   */
  public void copySpecies(int iSpeciesCopyFrom, int iSpeciesCopyTo)
      throws ModelException {
    for (Behavior oBehavior : mp_oEffects) oBehavior.copySpecies(iSpeciesCopyFrom, iSpeciesCopyTo);
    super.copySpecies(iSpeciesCopyFrom, iSpeciesCopyTo);
  }

  /**
   * Override to give effects behaviors the same instructions
   */
  public void changeOfSpeciesName(String sOldSpecies, String sNewSpecies) {
    for (Behavior oBehavior : mp_oEffects) oBehavior.changeOfSpeciesName(sOldSpecies, sNewSpecies);
    super.changeOfSpeciesName(sOldSpecies, sNewSpecies);
  }

  /**
   * Override to give effects behaviors the same instructions
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) throws ModelException {
    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    for (Behavior oBehavior : mp_oEffects) oBehavior.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);    
  }
  
  /**
   * For backwards compatibility with the old NCI behavior. Adds default
   * size effect, default crowding effect, default damage effect, and NCI
   * with neighbor damage.
   */
  public void setupLikeOldNCI() throws ModelException {
    setSingleValueByXMLTag("nciWhichShadingEffect", "", null, Integer.valueOf(1));
    setSingleValueByXMLTag("nciWhichCrowdingEffect", "", null, Integer.valueOf(1));
    setSingleValueByXMLTag("nciWhichNCITerm", "", null, Integer.valueOf(2));
    setSingleValueByXMLTag("nciWhichSizeEffect", "", null, Integer.valueOf(1));
    setSingleValueByXMLTag("nciWhichDamageEffect", "", null, Integer.valueOf(1));
    setSingleValueByXMLTag("nciWhichPrecipitationEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichTemperatureEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichNitrogenEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichInfectionEffect", "", null, Integer.valueOf(0));
  }
  
  /**
   * For backwards compatibility with the old weibull climate behavior. 
   */
  public void setupLikeOldWeibullClimate() throws ModelException {
    setSingleValueByXMLTag("nciWhichShadingEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichCrowdingEffect", "", null, Integer.valueOf(1));
    setSingleValueByXMLTag("nciWhichNCITerm", "", null, Integer.valueOf(3));
    setSingleValueByXMLTag("nciWhichSizeEffect", "", null, Integer.valueOf(2));
    setSingleValueByXMLTag("nciWhichDamageEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichPrecipitationEffect", "", null, Integer.valueOf(1));
    setSingleValueByXMLTag("nciWhichTemperatureEffect", "", null, Integer.valueOf(1));
    setSingleValueByXMLTag("nciWhichNitrogenEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichInfectionEffect", "", null, Integer.valueOf(0));
  }
  
  /**
   * For backwards compatibility with the old weibull climate quadrat behavior. 
   */
  public void setupLikeOldWeibullClimateQuadrat() throws ModelException {
    setSingleValueByXMLTag("nciWhichShadingEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichCrowdingEffect", "", null, Integer.valueOf(3));
    setSingleValueByXMLTag("nciWhichNCITerm", "", null, Integer.valueOf(3));
    setSingleValueByXMLTag("nciWhichSizeEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichDamageEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichPrecipitationEffect", "", null, Integer.valueOf(1));
    setSingleValueByXMLTag("nciWhichTemperatureEffect", "", null, Integer.valueOf(1));
    setSingleValueByXMLTag("nciWhichNitrogenEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichInfectionEffect", "", null, Integer.valueOf(0));
  }
  
  /**
   * For backwards compatibility with the old BA NCI behavior.
   */
  public void setupLikeOldBANCI() throws ModelException {
    setSingleValueByXMLTag("nciWhichShadingEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichCrowdingEffect", "", null, Integer.valueOf(2));
    setSingleValueByXMLTag("nciWhichNCITerm", "", null, Integer.valueOf(4));
    setSingleValueByXMLTag("nciWhichSizeEffect", "", null, Integer.valueOf(1));
    setSingleValueByXMLTag("nciWhichDamageEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichPrecipitationEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichTemperatureEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichNitrogenEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichInfectionEffect", "", null, Integer.valueOf(0));
  }
  
  /**
   * For backwards compatibility with the old NCI Juvenile growth behavior.
   */
  public void setupLikeOldNCIJuvenile() throws ModelException {
    setSingleValueByXMLTag("nciWhichShadingEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichCrowdingEffect", "", null, Integer.valueOf(3));
    setSingleValueByXMLTag("nciWhichNCITerm", "", null, Integer.valueOf(5));
    setSingleValueByXMLTag("nciWhichSizeEffect", "", null, Integer.valueOf(3));
    setSingleValueByXMLTag("nciWhichDamageEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichPrecipitationEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichTemperatureEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichNitrogenEffect", "", null, Integer.valueOf(0));
    setSingleValueByXMLTag("nciWhichInfectionEffect", "", null, Integer.valueOf(0));
  }

  
  /**
   * Override to call NCI's dialog 
   */
  public void callSetupDialog(JDialog jParent, MainWindow oMain) {
    NCIParameterEdit oWindow = new NCIParameterEdit(jParent, m_oManager, oMain, this);
    oWindow.pack();
    oWindow.setLocationRelativeTo(null);
    oWindow.setVisible(true);
  }
  
  
  /**
   * Formats effects data for display in a set of JTables. Only those values 
   * appropriate to the effects functions that have been chosen are used.
   * @param oPop TreePopulation object.
   * @return Vector of vectors suitable for creating a set of JTables, or null
   * if there is no data to display.
   */
  public ArrayList<BehaviorParameterDisplay> formatEffectsDataForDisplay(
      TreePopulation oPop) {
        
    if (mp_oEffects.size() == 0) return null;
    ArrayList<BehaviorParameterDisplay> jReturn = new ArrayList<BehaviorParameterDisplay>(0);
    
    //Add one for each effect
    ArrayList<BehaviorParameterDisplay> p_oBehReturn;
    for (Behavior oBeh : mp_oEffects) {
      p_oBehReturn = oBeh.formatDataForDisplay(oPop);
      for (BehaviorParameterDisplay oDisp2 : p_oBehReturn) jReturn.add(oDisp2);
    }
    return jReturn;
  }
  
  /**
   * Overriden to give effects their shot at the parameters as well.
   */
  public void readDataFromDisplay(ArrayList<BehaviorParameterDisplay> oData,
      TreePopulation oPop) throws ModelException {
    //We are expecting a BehaviorParameterDisplay for this plus each effect,
    //so split them out
    ArrayList<BehaviorParameterDisplay> p_oPasser = new ArrayList<BehaviorParameterDisplay>(0);
    p_oPasser.add(oData.get(0));    
    super.readDataFromDisplay(p_oPasser, oPop);
    
    if (oData.size() > 1) {
      String sEffectName;
      int i;
      for (i = 1; i < oData.size(); i++) {
        sEffectName = oData.get(i).m_sBehaviorName;
        p_oPasser = new ArrayList<BehaviorParameterDisplay>(0);
        p_oPasser.add(oData.get(i));
        for (Behavior oEff : mp_oEffects) if (oEff.getDescriptor().equals(sEffectName))
          oEff.readDataFromDisplay(p_oPasser, oPop);
      }
    }
  }
  
  /**
   * Generic function for extracting the choices for a particular effect. If
   * this does not use individuals, only those effects that do not require
   * target diameters will be listed.
   * @param p_effects Effects list from which to extract choices. 
   * @param all If true, all effects are added; if false, only those effects 
   * that do not require target diameters will be listed.
   * @return List of effects choices, or null if there are none.
   */
  private String[] getEffectChoices(ArrayList<NCIEffect> p_effects, boolean all) {
    ArrayList<String> p_sValues = new ArrayList<String>(0);
    if (all) {
      for (NCIEffect e : p_effects) p_sValues.add(e.getDescriptor());
    } else {
      if (m_bUsesIndividuals == false) {
        for (NCIEffect e : p_effects) {
          if ( !e.requiresDiameter())
            p_sValues.add(e.getDescriptor());
        }
      } else {
        for (NCIEffect e : p_effects) p_sValues.add(e.getDescriptor());
      }
    }
    if (p_sValues.size() == 0) return null;
    String[] p_sReturn = new String[p_sValues.size()];
    for (int i = 0; i < p_sReturn.length; i++) p_sReturn[i] = p_sValues.get(i);
    return p_sReturn;    
  }

  public String[] getSizeEffectChoices(boolean all) {
    return getEffectChoices(mp_iSizeEffects, all);
  }
  
  public String[] getShadingEffectChoices(boolean all) {
    return getEffectChoices(mp_iShadingEffects, all);
  }
  
  public String[] getCrowdingEffectChoices(boolean all) {
    return getEffectChoices(mp_iCrowdingEffects, all);
  }
  
  public String[] getNCITermChoices(boolean all) {
    return getEffectChoices(mp_iNCITerms, all);
  }
  
  public String[] getDamageEffectChoices(boolean all) {
    return getEffectChoices(mp_iDamageEffects, all);
  }
  
  public String[] getTemperatureEffectChoices(boolean all) {
    return getEffectChoices(mp_iTempEffects, all);
  }
  
  public String[] getPrecipitationEffectChoices(boolean all) {
    return getEffectChoices(mp_iPrecipEffects, all);
  }
  
  public String[] getNitrogenEffectChoices(boolean all) {
    return getEffectChoices(mp_iNEffects, all);
  }
  
  public String[] getInfectionEffectChoices(boolean all) {
    return getEffectChoices(mp_iInfectionEffects, all);
  }
  
  public int getSizeEffectChoice() {
    return m_iSizeEffect.getValue();
  }
  
  public int getShadingEffectChoice() {
    return m_iShadingEffect.getValue();
  }
  
  public int getCrowdingEffectChoice() {
    return m_iCrowdingEffect.getValue();
  }
  
  public int getNCITermChoice() {
    return m_iNCITerm.getValue();
  }
  
  public int getDamageEffectChoice() {
    return m_iDamageEffect.getValue();
  }
  
  public int getTemperatureEffectChoice() {
    return m_iTempEffect.getValue();
  }
  
  public int getPrecipitationEffectChoice() {
    return m_iPrecipEffect.getValue();
  }
  
  public int getNitrogenEffectChoice() {
    return m_iNEffect.getValue();
  }
  
  public int getInfectionEffectChoice() {
    return m_iInfectionEffect.getValue();
  }
  
  /**
   * Sets the choice of some generic effect option, and updates the effects list
   * accordingly.
   * @param p_choices Array of choices from which choice is being made. 
   * @param setChoice Number of choice.
   * @param sChoice Descriptor of choice.
   * @throws ModelException if the choice is not valid.
   */
  private void setEffect(ArrayList<NCIEffect> p_choices, ModelInt setChoice, String sChoice) throws ModelException {
    if (p_choices.get(setChoice.getValue()).getDescriptor().equals(sChoice)) return;
    
    int i;
 
    //Remove any existing values for size
    for (Behavior o : mp_oEffects) {
      for (i = 0; i < p_choices.size(); i++) {
        if (o.getDescriptor().equals(p_choices.get(i).getDescriptor())) {
          mp_oEffects.remove(o); 
          break;
        }
      }
    }
  
    for (i = 0; i < p_choices.size(); i++) {
      if (p_choices.get(i).getDescriptor().equals(sChoice)) {
        
        setChoice.setValue(i);
        Behavior oBeh = p_choices.get(i).createBehavior(m_oManager, null);

        if (oBeh != null) {
          //Give this effects behavior the same species/types combos as this
          for (SpeciesTypeCombo oCombo : mp_oTreesAppliesTo)
            oBeh.addSpeciesTypeCombo(oCombo);
          mp_oEffects.add(oBeh);
        }
        
        return;
      }
    }

    //If we're still here, we couldn't find the choice
    throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA", "Unrecognized NCI effect choice " + sChoice));    
  }
  
  /**
   * Sets the choice of some generic effect option, and updates the effects list
   * accordingly.
   * @param p_choices Array of choices from which choice is being made. 
   * @param setChoice Number of choice.
   * @param iChoice Number of choice, again?.
   * @throws ModelException if the choice is not valid.
   */
  private void setEffect(ArrayList<NCIEffect> p_choices, ModelInt setChoice, int iChoice) throws ModelException {
    int i;
    for (i = 0; i < p_choices.size(); i++) {
      if (p_choices.get(i).getEffectNum() == iChoice) {
        setEffect(p_choices, setChoice, p_choices.get(i).getDescriptor());
        return;
      }
    }
    //If we're still here the choice was not allowed
    throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA", 
        "Unrecognized effect value " + iChoice));    
  }
  
  /**
   * Sets the choice of size effect options, and updates the effects list
   * accordingly.
   * @param sChoice Descriptor of choice.
   * @throws ModelException if the choice is not valid.
   */
  public void setSizeEffect(String sChoice) throws ModelException {
    setEffect(mp_iSizeEffects, m_iSizeEffect, sChoice);    
  }    
  
  /**
   * Sets the choice of shading effect options, and updates the effects list
   * accordingly.
   * @param sChoice Descriptor of choice.
   * @throws ModelException if the choice is not valid.
   */
  public void setShadingEffect(String sChoice) throws ModelException {
    setEffect(mp_iShadingEffects, m_iShadingEffect, sChoice);    
  }
  
  /**
   * Sets the choice of crowding effect options, and updates the effects list
   * accordingly.
   * @param sChoice Descriptor of choice.
   * @throws ModelException if the choice is not valid.
   */
  public void setCrowdingEffect(String sChoice) throws ModelException {
    setEffect(mp_iCrowdingEffects, m_iCrowdingEffect, sChoice);    
  }
  
  /**
   * Sets the choice of NCI term options, and updates the effects list
   * accordingly.
   * @param sChoice Descriptor of choice.
   * @throws ModelException if the choice is not valid.
   */
  public void setNCITerm(String sChoice) throws ModelException {
    setEffect(mp_iNCITerms, m_iNCITerm, sChoice);
  }
  
  /**
   * Sets the choice of damage effect options, and updates the effects list
   * accordingly.
   * @param sChoice Descriptor of choice.
   * @throws ModelException if the choice is not valid.
   */
  public void setDamageEffect(String sChoice) throws ModelException {
    setEffect(mp_iDamageEffects, m_iDamageEffect, sChoice);
  }
  
  /**
   * Sets the choice of precipitation effect options, and updates the effects 
   * list accordingly.
   * @param sChoice Descriptor of choice.
   * @throws ModelException if the choice is not valid.
   */
  public void setPrecipitationEffect(String sChoice) throws ModelException {
    setEffect(mp_iPrecipEffects, m_iPrecipEffect, sChoice);
  }
  
  /**
   * Sets the choice of temperature effect options, and updates the effects 
   * list accordingly.
   * @param sChoice Descriptor of choice.
   * @throws ModelException if the choice is not valid.
   */
  public void setTemperatureEffect(String sChoice) throws ModelException {
    setEffect(mp_iTempEffects, m_iTempEffect, sChoice);
  }
  
  /**
   * Sets the choice of nitrogen effect options, and updates the effects 
   * list accordingly.
   * @param sChoice Descriptor of choice.
   * @throws ModelException if the choice is not valid.
   */
  public void setNitrogenEffect(String sChoice) throws ModelException {
    setEffect(mp_iNEffects, m_iNEffect, sChoice);
  }
  
  /**
   * Sets the choice of infection effect options, and updates the effects 
   * list accordingly.
   * @param sChoice Descriptor of choice.
   * @throws ModelException if the choice is not valid.
   */
  public void setInfectionEffect(String sChoice) throws ModelException {
    setEffect(mp_iInfectionEffects, m_iInfectionEffect, sChoice);
  }  
}
