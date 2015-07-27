package sortie.data.funcgroups.analysis;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clBoleVolumeCalculator class.
 * @author lora
 */
public class BoleVolumeCalculator extends Behavior {
  
  /** Bole volume - b0 in the volume equation */
  protected ModelVector mp_fBoleVolumeB0 = new ModelVector(
      "Bole Volume Parameter (b0)", "an_boleB0", "an_bb0Val", 0,
      ModelVector.FLOAT);

  /** Bole volume - b1 in the volume equation */
  protected ModelVector mp_fBoleVolumeB1 = new ModelVector(
      "Bole Volume Parameter (b1)", "an_boleB1", "an_bb1Val", 0,
      ModelVector.FLOAT);

  /** Bole volume - b2 in the volume equation */
  protected ModelVector mp_fBoleVolumeB2 = new ModelVector(
      "Bole Volume Parameter (b2)", "an_boleB2", "an_bb2Val", 0,
      ModelVector.FLOAT);

  /** Bole volume - b3 in the volume equation */
  protected ModelVector mp_fBoleVolumeB3 = new ModelVector(
      "Bole Volume Parameter (b3)", "an_boleB3", "an_bb3Val", 0,
      ModelVector.FLOAT);

  /** Bole volume - b4 in the volume equation */
  protected ModelVector mp_fBoleVolumeB4 = new ModelVector(
      "Bole Volume Parameter (b4)", "an_boleB4", "an_bb4Val", 0,
      ModelVector.FLOAT);

  /** Bole volume - b5 in the volume equation */
  protected ModelVector mp_fBoleVolumeB5 = new ModelVector(
      "Bole Volume Parameter (b5)", "an_boleB5", "an_bb5Val", 0,
      ModelVector.FLOAT);

  /** Bole volume - form classes */
  protected ModelVector mp_fBoleVolumeFormClasses = new ModelVector(
      "Bole Volume Form Class, 60% - 100%", "an_boleFormClasses", "an_bfcVal",
      0, ModelVector.FLOAT);  
  
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
  public BoleVolumeCalculator(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {  
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "analysis_behaviors.tree_bole_volume_calculator");

    setCanApplyTo(TreePopulation.SEEDLING, false);
    // Data members
    mp_oNewTreeDataMembers.add(new DataMember(
        "Tree Bole Volume", "Bole Vol", DataMember.FLOAT));
    addRequiredData(mp_fBoleVolumeFormClasses);
    addRequiredData(mp_fBoleVolumeB0);
    addRequiredData(mp_fBoleVolumeB1);
    addRequiredData(mp_fBoleVolumeB2);
    addRequiredData(mp_fBoleVolumeB3);
    addRequiredData(mp_fBoleVolumeB4);
    addRequiredData(mp_fBoleVolumeB5);

  }

  /**
   * Validates the data before writing to a parameter file.
   * 
   * @throws ModelException if a form class is not between 60 and 100.
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    int iNumSpecies = oPop.getNumberOfSpecies();
    boolean[] p_bApplies = getWhichSpeciesUsed(oPop);
    
    ValidationHelpers.makeSureRightSize(mp_fBoleVolumeB0, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fBoleVolumeB1, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fBoleVolumeB2, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fBoleVolumeB3, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fBoleVolumeB4, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fBoleVolumeB5, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fBoleVolumeFormClasses, iNumSpecies);
    
    ValidationHelpers.makeSureAllAreBounded(mp_fBoleVolumeFormClasses, p_bApplies, 60, 100);
  }

}
