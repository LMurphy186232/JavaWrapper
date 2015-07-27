package sortie.data.funcgroups.mortality;

import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clCompetitionMort class.
 * @author lora
 */
public class CompetitionMortality extends Behavior {
  
  /**Competition mortality - Shape parameter (Z)*/
  protected ModelVector mp_fCompMortShape = new ModelVector(
      "Competition Mortality Shape Parameter (Z)",
      "mo_CompMort", "mo_cmVal", 0, ModelVector.FLOAT);

  /**Competition mortality - maximum parameter (max)*/
  protected ModelVector mp_fCompMortMax = new ModelVector(
      "Competition Mortality Maximum Parameter (max)",
      "mo_CompMortMax", "mo_cmmVal", 0, ModelVector.FLOAT);

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
  public CompetitionMortality(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.competition");

    addRequiredData(mp_fCompMortShape);
    addRequiredData(mp_fCompMortMax);
    //Can't apply to seedlings
    setCanApplyTo(TreePopulation.SEEDLING, false);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation
   * @throws ModelException if the timestep length is not one year, or NCI 
   * growth is not enabled.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
   
    if (m_oManager.getPlot().getNumberOfYearsPerTimestep() != 1) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", getDescriptor() + 
          " cannot be used unless the timestep length is one year."));
    }
    //Make sure an NCI growth behavior is enabled
    GrowthBehaviors oGrowth = m_oManager.getGrowthBehaviors();
    if (oGrowth.getBehaviorByParameterFileTag("NCIMasterGrowth").size() == 0 &&
        oGrowth.getBehaviorByParameterFileTag("NCIMasterGrowth diam only").size() == 0) {
      throw (new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
              getDescriptor() + " must be used with an NCI growth behavior."));
    }
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop),
              p_bNCI;
    ArrayList<Behavior> p_oNCI1 = oGrowth.getBehaviorByParameterFileTag("NCIMasterGrowth"),
             p_oNCI2 = oGrowth.getBehaviorByParameterFileTag("NCIMasterGrowth diam only");
    int i;
    
    p_bNCI = getWhichSpeciesUsed(oPop);
    for (i = 0; i < p_bNCI.length; i++) p_bNCI[i] = false;
    for (Behavior oNCI : p_oNCI1) 
      p_bNCI = BehaviorTypeBase.mergeBooleans(p_bNCI, oNCI.getWhichSpeciesUsed(oPop));
    for (Behavior oNCI : p_oNCI2)
      p_bNCI = BehaviorTypeBase.mergeBooleans(p_bNCI, oNCI.getWhichSpeciesUsed(oPop));
    
    for (i = 0; i < p_bAppliesTo.length; i++) 
      if (p_bAppliesTo[i] && !p_bNCI[i])
        throw (new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
            oPop.getSpeciesNameFromCode(i) +
            " must use an NCI growth behavior in order to use the " +
            getDescriptor() + " behavior."));
  }

}
