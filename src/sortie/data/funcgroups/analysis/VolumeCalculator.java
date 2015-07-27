package sortie.data.funcgroups.analysis;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clVolumeCalculator class.
 * @author lora
 */
public class VolumeCalculator extends Behavior {
  
  /** Volume calculator - a in the taper equation (also known as a0) */
  protected ModelVector mp_fTaperA = new ModelVector(
      "Taper Equation Initial Multiplier (a0)", "vo_taperA", "vo_taVal", 0,
      ModelVector.FLOAT);

  /** Volume calculator - b in the taper equation (also known as a1) */
  protected ModelVector mp_fTaperB = new ModelVector(
      "Taper Equation DBH Exponent (a1)", "vo_taperB", "vo_tbVal", 0,
      ModelVector.FLOAT);

  /** Volume calculator - c in the taper equation (also known as a2) */
  protected ModelVector mp_fTaperC = new ModelVector(
      "Taper Equation Height Exponent (a2)", "vo_taperC", "vo_tcVal", 0,
      ModelVector.FLOAT);

  /** Volume calculator - d in the taper equation (also known as b1) */
  protected ModelVector mp_fTaperD = new ModelVector(
      "Taper Equation X Exponent 1 (b1)", "vo_taperD", "vo_tdVal", 0,
      ModelVector.FLOAT);

  /** Volume calculator - f in the taper equation (also known as b2) */
  protected ModelVector mp_fTaperF = new ModelVector(
      "Taper Equation X Exponent 2 (b2)", "vo_taperF", "vo_tfVal", 0,
      ModelVector.FLOAT);

  /** Volume calculator - g in the taper equation (also known as b3) */
  protected ModelVector mp_fTaperG = new ModelVector(
      "Taper Equation X Exponent 3 (b3)", "vo_taperG", "vo_tgVal", 0,
      ModelVector.FLOAT);

  /** Volume calculator - i in the taper equation (also known as b4) */
  protected ModelVector mp_fTaperI = new ModelVector(
      "Taper Equation X Exponent 4 (b4)", "vo_taperI", "vo_tiVal", 0,
      ModelVector.FLOAT);

  /** Volume calculator - j in the taper equation (also known as b5) */
  protected ModelVector mp_fTaperJ = new ModelVector(
      "Taper Equation X Exponent 5 (b5)", "vo_taperJ", "vo_tjVal", 0,
      ModelVector.FLOAT);

  /** Volume calculator - k in the taper equation (also known as b6) */
  protected ModelVector mp_fTaperK = new ModelVector(
      "Taper Equation X Exponent 6 (b6)", "vo_taperK", "vo_tkVal", 0,
      ModelVector.FLOAT);

  /** Volume calculator - a for the diameter-outside-bark (also known as a1) */
  protected ModelVector mp_fBarkA = new ModelVector(
      "Diameter-Outside-Bark Constant (a1)", "vo_barkA", "vo_baVal", 0,
      ModelVector.FLOAT);

  /** Volume calculator - b for the diameter-outside-bark (also known as a2) */
  protected ModelVector mp_fBarkB = new ModelVector(
      "Diameter-Outside-Bark First Degree Parameter (a2)", "vo_barkB",
      "vo_bbVal", 0, ModelVector.FLOAT);

  /** Volume calculator - c for the diameter-outside-bark (also known as a3) */
  protected ModelVector mp_fBarkC = new ModelVector(
      "Diameter-Outside-Bark Second Degree Parameter (a3)", "vo_barkC",
      "vo_bcVal", 0, ModelVector.FLOAT);      
    
  /**
   * Volume calculator - stump height, in cm. Point at which to start summing
   * trunk volume
   */
  protected ModelFloat m_fStumpHeight = new ModelFloat(0,
      "Height to Begin Calculating Trunk Volume, in cm", "vo_stumpHeight");

  /**
   * Volume calculator - minimum usable diameter, in cm. Point at which to stop
   * summing trunk volume
   */
  protected ModelFloat m_fMinUsableDiam = new ModelFloat(0,
      "Minimum Trunk Diameter for Volume Calculations, in cm",
      "vo_minUsableDiam");

  /** Volume calculator - Length of tree trunk volume segments, in m */
  protected ModelFloat m_fSegmentLength = new ModelFloat(0,
      "Trunk Segment Length for Volume Calculations, in m", "vo_segmentLength");


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
  public VolumeCalculator(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "analysis_behaviors.tree_volume_calculator");
    
    setCanApplyTo(TreePopulation.SEEDLING, false);
    // Data members
    mp_oNewTreeDataMembers.add(new DataMember(
        "Tree Volume", "Volume", DataMember.FLOAT));
    addRequiredData(mp_fTaperA);
    addRequiredData(mp_fTaperB);
    addRequiredData(mp_fTaperC);
    addRequiredData(mp_fTaperD);
    addRequiredData(mp_fTaperF);
    addRequiredData(mp_fTaperG);
    addRequiredData(mp_fTaperI);
    addRequiredData(mp_fTaperJ);
    addRequiredData(mp_fTaperK);
    addRequiredData(mp_fBarkA);
    addRequiredData(mp_fBarkB);
    addRequiredData(mp_fBarkC);
    addRequiredData(m_fMinUsableDiam);
    addRequiredData(m_fSegmentLength);
    addRequiredData(m_fStumpHeight);
    
  }

  /**
   * Validates the data before writing to a parameter file.
   * 
   * @throws ModelException if either m_fStumpHeight or m_fMinUsableDiam is 
   * less than 0, or m_fSegmentLength is less than or equal to 0.
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    int iNumSpecies = oPop.getNumberOfSpecies();
    
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fMinUsableDiam, 0);
    ValidationHelpers.makeSureGreaterThan(m_fSegmentLength, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fStumpHeight, 0);
    ValidationHelpers.makeSureRightSize(mp_fTaperA, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fTaperB, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fTaperC, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fTaperD, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fTaperF, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fTaperG, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fTaperI, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fTaperJ, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fTaperK, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fBarkA, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fBarkB, iNumSpecies);
    ValidationHelpers.makeSureRightSize(mp_fBarkC, iNumSpecies);

  }
}
