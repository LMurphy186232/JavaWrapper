package sortie.data.funcgroups.establishment;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clMicroEstablishment class.
 * @author lora
 */
public class MicroEstablishment extends Behavior {
  
  /**Mean mound height, in m*/
  protected ModelFloat m_fMeanMoundHeight = new ModelFloat(0,
      "Mean Height of Mounds, in m", "es_meanMoundHeight");

  /**Standard deviation of mound height*/
  protected ModelFloat m_fMoundStdDev = new ModelFloat(0,
      "Standard Deviation of Mound Height, in m", "es_moundStdDev");

  /**Mean log height, in m*/
  protected ModelFloat m_fMeanFreshLogHeight = new ModelFloat(0,
      "Mean Height of Fresh Log Substrate, in m", "es_meanFreshLogHeight");

  /**Standard deviation of fresh log height, in m*/
  protected ModelFloat m_fFreshLogStdDev = new ModelFloat(0,
      "Standard Deviation of Fresh Log Substrate Height, in m",
      "es_freshLogStdDev");
  
  /**Proportion of the plot which is mound*/
  protected ModelFloat m_fMoundProportion = new ModelFloat(0,
      "Proportion of Plot Area that is Mound", "es_moundProportion");
  
  /**Number of years of respite from fern shading*/
  protected ModelInt m_iMaxRespite = new ModelInt(0,
             "# Years Respite from Fern Shading for Seeds on Fresh Logs",
             "es_maxRespite");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public MicroEstablishment(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "establishment_behaviors.establishment_with_microtopography");
    
    addRequiredData(m_fMoundProportion);
    addRequiredData(m_fMeanMoundHeight);
    addRequiredData(m_fMoundStdDev);
    addRequiredData(m_fMeanFreshLogHeight);
    addRequiredData(m_fFreshLogStdDev);
    addRequiredData(m_iMaxRespite);
    //Disallow for all but seeds
    setCanApplyTo(TreePopulation.SEED, true);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, false);
  }

  /**
   * Validates data.
   * @param oPop TreePopulation object.
   * @throws ModelException if:
   * <ul>
   * <li>The proportion of mound value isn't a proportion</li>
   * <li>Proportion of mound value isn't a proportion</li>
   * <li>The Beer's law light filter isn't enabled</li>
   * <li>Substrate isn't enabled</li>
   * </ul>
   *
   * This will also make sure that establishment is enabled if any of the
   * others are.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureIsProportion(m_fMoundProportion);

    //Make sure that the light filter behavior is enabled
    if (m_oManager.getLightBehaviors().getBehaviorByParameterFileTag("LightFilter").
        size() == 0) {
      throw new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
          "You must use the \""+
          m_oManager.getLightBehaviors().getDescriptor("LightFilter")
          + "\" behavior with the \"" + getDescriptor() + 
          "\" behavior.");
    }

    //Make sure that the substrate behavior is enabled
    if (m_oManager.getSubstrateBehaviors().getBehaviorByParameterFileTag("Substrate").
        size() == 0) {
      throw new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
          "You must use the \""+
          m_oManager.getSubstrateBehaviors().getDescriptor("Substrate")
          + "\" behavior with the \"" + getDescriptor() + "\" behavior.");
    }
  }

}
