package sortie.data.funcgroups.snagdynamics;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSnagDecomp class.
 * @author lora
 */
public class SnagDecayClassDynamics extends Behavior {

  /** Snag Decay Class Dynamics Tree Fall Beta Parameter, species-specific */
  protected ModelVector mp_fSnagDecompTreeFallBeta = new ModelVector(
      "Snag Decay Class Dynamics Tree Fall Beta",
      "sd_snagDecompTreefallBeta", "sd_sdtbVal", 0,
      ModelVector.FLOAT);

  /** Snag Decay Class Dynamics Snag Fall Beta Parameter, species-specific */
  protected ModelVector mp_fSnagDecompSnagFallBeta = new ModelVector(
      "Snag Decay Class Dynamics Snag Fall Beta",
      "sd_snagDecompSnagfallBeta", "sd_sdsbVal", 0, ModelVector.FLOAT);

  /** Snag Decay Class Dynamics Tree Fall Alpha Parameter */
  protected ModelFloat m_fSnagDecompTreeFallAlpha = new ModelFloat(0,
      "Snag Decay Class Dynamics Tree Fall Alpha",
  "sd_snagDecompTreefallAlpha");

  /** Snag Decay Class Dynamics Tree Fall Delta Parameter */
  protected ModelFloat m_fSnagDecompTreeFallDelta = new ModelFloat(0,
      "Snag Decay Class Dynamics Tree Fall Delta",
  "sd_snagDecompTreefallDelta");

  /** Snag Decay Class Dynamics Tree Fall Theta Parameter */
  protected ModelFloat m_fSnagDecompTreeFallTheta = new ModelFloat(0,
      "Snag Decay Class Dynamics Tree Fall Theta",
  "sd_snagDecompTreefallTheta");

  /** Snag Decay Class Dynamics Tree Fall Iota Parameter */
  protected ModelFloat m_fSnagDecompTreeFallIota = new ModelFloat(0,
      "Snag Decay Class Dynamics Tree Fall Iota",
  "sd_snagDecompTreefallIota");

  /** Snag Decay Class Dynamics Tree Fall Lambda Parameter */
  protected ModelFloat m_fSnagDecompTreeFallLambda = new ModelFloat(0,
      "Snag Decay Class Dynamics Tree Fall Lambda",
  "sd_snagDecompTreefallLambda");

  /** Snag Decay Class Dynamics Snag Fall Alpha Parameter */
  protected ModelFloat m_fSnagDecompSnagFallAlpha = new ModelFloat(0,
      "Snag Decay Class Dynamics Snag Fall Alpha",
  "sd_snagDecompSnagfallAlpha");

  /** Snag Decay Class Dynamics Snag Fall Gamma 2 Parameter */
  protected ModelFloat m_fSnagDecompSnagFallGamma2 = new ModelFloat(0,
      "Snag Decay Class Dynamics Snag Fall Gamma 2",
  "sd_snagDecompSnagfallGamma2");

  /** Snag Decay Class Dynamics Snag Fall Gamma 3 Parameter */
  protected ModelFloat m_fSnagDecompSnagFallGamma3 = new ModelFloat(0,
      "Snag Decay Class Dynamics Snag Fall Gamma 3",
  "sd_snagDecompSnagfallGamma3");

  /** Snag Decay Class Dynamics Snag Fall Gamma 4 Parameter */
  protected ModelFloat m_fSnagDecompSnagFallGamma4 = new ModelFloat(0,
      "Snag Decay Class Dynamics Snag Fall Gamma 4",
  "sd_snagDecompSnagfallGamma4");

  /** Snag Decay Class Dynamics Snag Fall Gamma 5 Parameter */
  protected ModelFloat m_fSnagDecompSnagFallGamma5 = new ModelFloat(0,
      "Snag Decay Class Dynamics Snag Fall Gamma 5",
  "sd_snagDecompSnagfallGamma5");

  /** Snag Decay Class Dynamics Snag Fall Zeta Parameter */
  protected ModelFloat m_fSnagDecompSnagFallZeta = new ModelFloat(0,
      "Snag Decay Class Dynamics Snag Fall Zeta",
  "sd_snagDecompSnagfallZeta");

  /** Snag Decay Class Dynamics Snag Fall Eta Parameter */
  protected ModelFloat m_fSnagDecompSnagFallEta = new ModelFloat(0,
      "Snag Decay Class Dynamics Snag Fall Eta",
  "sd_snagDecompSnagfallEta");

  /** Snag Decay Class Dynamics Snag Fall Kappa Parameter */
  protected ModelFloat m_fSnagDecompSnagFallKappa = new ModelFloat(0,
      "Snag Decay Class Dynamics Snag Fall Kappa",
  "sd_snagDecompSnagfallKappa");

  /** Snag Decay Class Dynamics Live To One Transition Probability */
  protected ModelFloat m_fSnagDecompLiveTo1Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Live To Class 1 Prob (0-1)",
  "sd_snagDecompLiveTo1Prob");

  /** Snag Decay Class Dynamics Live To Two Transition Probability */
  protected ModelFloat m_fSnagDecompLiveTo2Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Live To Class 2 Prob (0-1)",
  "sd_snagDecompLiveTo2Prob");

  /** Snag Decay Class Dynamics Live To Three Transition Probability */
  protected ModelFloat m_fSnagDecompLiveTo3Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Live To Class 3 Prob (0-1)",
  "sd_snagDecompLiveTo3Prob");

  /** Snag Decay Class Dynamics Live To Four Transition Probability */
  protected ModelFloat m_fSnagDecompLiveTo4Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Live To Class 4 Prob (0-1)",
  "sd_snagDecompLiveTo4Prob");

  /** Snag Decay Class Dynamics Live To Five Transition Probability */
  protected ModelFloat m_fSnagDecompLiveTo5Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Live To Class 5 Prob (0-1)",
  "sd_snagDecompLiveTo5Prob");

  /** Snag Decay Class Dynamics One To One Transition Probability */
  protected ModelFloat m_fSnagDecomp1To1Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Class 1 To Class 1 Prob (0-1)",
  "sd_snagDecomp1To1Prob");

  /** Snag Decay Class Dynamics One To Two Transition Probability */
  protected ModelFloat m_fSnagDecomp1To2Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Class 1 To Class 2 Prob (0-1)",
  "sd_snagDecomp1To2Prob");

  /** Snag Decay Class Dynamics One To Three Transition Probability */
  protected ModelFloat m_fSnagDecomp1To3Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Class 1 To Class 3 Prob (0-1)",
  "sd_snagDecomp1To3Prob");

  /** Snag Decay Class Dynamics One To Four Transition Probability */
  protected ModelFloat m_fSnagDecomp1To4Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Class 1 To Class 4 Prob (0-1)",
  "sd_snagDecomp1To4Prob");

  /** Snag Decay Class Dynamics One To Five Transition Probability */
  protected ModelFloat m_fSnagDecomp1To5Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Class 1 To Class 5 Prob (0-1)",
  "sd_snagDecomp1To5Prob");

  /** Snag Decay Class Dynamics Two To Two Transition Probability */
  protected ModelFloat m_fSnagDecomp2To2Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Class 2 To Class 2 Prob (0-1)",
  "sd_snagDecomp2To2Prob");

  /** Snag Decay Class Dynamics Two To Three Transition Probability */
  protected ModelFloat m_fSnagDecomp2To3Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Class 2 To Class 3 Prob (0-1)",
  "sd_snagDecomp2To3Prob");

  /** Snag Decay Class Dynamics Two To Four Transition Probability */
  protected ModelFloat m_fSnagDecomp2To4Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Class 2 To Class 4 Prob (0-1)",
  "sd_snagDecomp2To4Prob");

  /** Snag Decay Class Dynamics Two To Five Transition Probability */
  protected ModelFloat m_fSnagDecomp2To5Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Class 2 To Class 5 Prob (0-1)",
  "sd_snagDecomp2To5Prob");

  /** Snag Decay Class Dynamics Three To Three Transition Probability */
  protected ModelFloat m_fSnagDecomp3To3Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Class 3 To Class 3 Prob (0-1)",
  "sd_snagDecomp3To3Prob");

  /** Snag Decay Class Dynamics Three To Four Transition Probability */
  protected ModelFloat m_fSnagDecomp3To4Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Class 3 To Class 4 Prob (0-1)",
  "sd_snagDecomp3To4Prob");

  /** Snag Decay Class Dynamics Three To Five Transition Probability */
  protected ModelFloat m_fSnagDecomp3To5Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Class 3 To Class 5 Prob (0-1)",
  "sd_snagDecomp3To5Prob");

  /** Snag Decay Class Dynamics Four To Four Transition Probability */
  protected ModelFloat m_fSnagDecomp4To4Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Class 4 To Class 4 Prob (0-1)",
  "sd_snagDecomp4To4Prob");

  /** Snag Decay Class Dynamics Four To Five Transition Probability */
  protected ModelFloat m_fSnagDecomp4To5Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Class 4 To Class 5 Prob (0-1)",
  "sd_snagDecomp4To5Prob");

  /** Snag Decay Class Dynamics Five To Five Transition Probability */
  protected ModelFloat m_fSnagDecomp5To5Prob = new ModelFloat(0,
      "Snag Decay Class Dynamics Class 5 To Class 5 Prob (0-1)",
  "sd_snagDecomp5To5Prob");

  /** Snag Decay Class Dynamics minimum snag break height */
  protected ModelFloat m_fSnagDecompMinSnagBreakHeight = new ModelFloat(0,
      "Snag Decay Class Dynamics Minimum Snag Break Height",
  "sd_minSnagBreakHeight");

  /** Snag Decay Class Dynamics maximum snag break height */
  protected ModelFloat m_fSnagDecompMaxSnagBreakHeight = new ModelFloat(0,
      "Snag Decay Class Dynamics Maximum Snag Break Height",
  "sd_maxSnagBreakHeight");

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
  public SnagDecayClassDynamics(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "snag_dynamics_behaviors.snag_decay_class_dynamics");

    m_fVersion = 2;

    mp_oNewTreeDataMembers.add(new DataMember(
        "Snag Decay Class", "SnagDecayClass", DataMember.INTEGER));
    mp_oNewTreeDataMembers.add(new DataMember(
        "New Break Height", "NewBreakHeight", DataMember.FLOAT));
    mp_oNewTreeDataMembers.add(new DataMember(
        "Snag Old Break Height", "SnagOldBreakHeight", DataMember.FLOAT));
    mp_oNewTreeDataMembers.add(new DataMember(
        "Fall", "Fall", DataMember.BOOLEAN));
    //Substrate can only apply to snags and adults
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, true);
    setCanApplyTo(TreePopulation.SNAG, true);

    addRequiredData(m_fSnagDecompTreeFallAlpha);
    addRequiredData(mp_fSnagDecompTreeFallBeta);
    addRequiredData(m_fSnagDecompTreeFallDelta);
    addRequiredData(m_fSnagDecompTreeFallTheta);
    addRequiredData(m_fSnagDecompTreeFallIota);
    addRequiredData(m_fSnagDecompTreeFallLambda);
    addRequiredData(m_fSnagDecompSnagFallAlpha);
    addRequiredData(mp_fSnagDecompSnagFallBeta);
    addRequiredData(m_fSnagDecompSnagFallGamma2);
    addRequiredData(m_fSnagDecompSnagFallGamma3);
    addRequiredData(m_fSnagDecompSnagFallGamma4);
    addRequiredData(m_fSnagDecompSnagFallGamma5);
    addRequiredData(m_fSnagDecompSnagFallZeta);
    addRequiredData(m_fSnagDecompSnagFallEta);
    addRequiredData(m_fSnagDecompSnagFallKappa);
    addRequiredData(m_fSnagDecompLiveTo1Prob);
    addRequiredData(m_fSnagDecompLiveTo2Prob);
    addRequiredData(m_fSnagDecompLiveTo3Prob);
    addRequiredData(m_fSnagDecompLiveTo4Prob);
    addRequiredData(m_fSnagDecompLiveTo5Prob);
    addRequiredData(m_fSnagDecomp1To1Prob);
    addRequiredData(m_fSnagDecomp1To2Prob);
    addRequiredData(m_fSnagDecomp1To3Prob);
    addRequiredData(m_fSnagDecomp1To4Prob);
    addRequiredData(m_fSnagDecomp1To5Prob);
    addRequiredData(m_fSnagDecomp2To2Prob);
    addRequiredData(m_fSnagDecomp2To3Prob);
    addRequiredData(m_fSnagDecomp2To4Prob);
    addRequiredData(m_fSnagDecomp2To5Prob);
    addRequiredData(m_fSnagDecomp3To3Prob);
    addRequiredData(m_fSnagDecomp3To4Prob);
    addRequiredData(m_fSnagDecomp3To5Prob);
    addRequiredData(m_fSnagDecomp4To4Prob);
    addRequiredData(m_fSnagDecomp4To5Prob);
    addRequiredData(m_fSnagDecomp5To5Prob);
    addRequiredData(m_fSnagDecompMinSnagBreakHeight);
    addRequiredData(m_fSnagDecompMaxSnagBreakHeight);
    addRequiredData(m_fSnagDecomp5To5Prob);

    //Set up the grid
    DataMember[] p_oDataMembers = new DataMember[2];
    Grid oNewGrid;

    String sGridName = "Snag Decay Class Dynamics Basal Area";
    p_oDataMembers[0] = new DataMember("Live BA Per Ha",  "Live BA Per Ha",
        DataMember.FLOAT);
    p_oDataMembers[1] = new DataMember("Cut BA Per Ha",  "Cut BA Per Ha",
        DataMember.FLOAT);
    //Create our grid
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid);

  }

  /**
   * Validates the data in preparation for parameter file writing or some such.
   * @throws ModelException if any of the probabilities are not proportions,
   * or if the probabilities for a class don't add up to 1.
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {

    ValidationHelpers.makeSureIsProportion(m_fSnagDecompLiveTo1Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecompLiveTo2Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecompLiveTo3Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecompLiveTo4Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecompLiveTo5Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecomp1To1Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecomp1To2Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecomp1To3Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecomp1To4Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecomp1To5Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecomp2To2Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecomp2To3Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecomp2To4Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecomp2To5Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecomp3To3Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecomp3To4Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecomp3To5Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecomp4To4Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecomp4To5Prob);
    ValidationHelpers.makeSureIsProportion(m_fSnagDecomp5To5Prob);
    ValidationHelpers.makeSureGreaterThan(m_fSnagDecompMaxSnagBreakHeight, 0);
    ValidationHelpers.makeSureGreaterThan(m_fSnagDecompMinSnagBreakHeight, 0);

    if(java.lang.Math.abs(
        m_fSnagDecompLiveTo1Prob.getValue() +
        m_fSnagDecompLiveTo2Prob.getValue() +
        m_fSnagDecompLiveTo3Prob.getValue() +
        m_fSnagDecompLiveTo4Prob.getValue() +
        m_fSnagDecompLiveTo5Prob.getValue() - 1) > 0.001 ) {

      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Probabilities for transitioning from live must add up to 1."));
    }

    if(java.lang.Math.abs(
        m_fSnagDecomp1To1Prob.getValue() +
        m_fSnagDecomp1To2Prob.getValue() +
        m_fSnagDecomp1To3Prob.getValue() +
        m_fSnagDecomp1To4Prob.getValue() +
        m_fSnagDecomp1To5Prob.getValue() - 1) > 0.001 ) {

      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA",
      "Probabilities for transitioning from class 1 must add up to 1."));
    }

    if(java.lang.Math.abs(
        m_fSnagDecomp2To2Prob.getValue() +
        m_fSnagDecomp2To3Prob.getValue() +
        m_fSnagDecomp2To4Prob.getValue() +
        m_fSnagDecomp2To5Prob.getValue() - 1) > 0.001 ) {

      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA",
      "Probabilities for transitioning from class 2 must add up to 1."));
    }

    if(java.lang.Math.abs(
        m_fSnagDecomp3To3Prob.getValue() +
        m_fSnagDecomp3To4Prob.getValue() +
        m_fSnagDecomp3To5Prob.getValue() - 1) > 0.001 ) {

      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA",
      "Probabilities for transitioning from class 3 must add up to 1."));
    }

    if(java.lang.Math.abs(
        m_fSnagDecomp4To4Prob.getValue() +
        m_fSnagDecomp4To5Prob.getValue() - 1) > 0.001 ) {

      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA",
      "Probabilities for transitioning from class 4 must add up to 1."));
    }

    if(java.lang.Math.abs(m_fSnagDecomp5To5Prob.getValue() - 1) > 0.001 ) {

      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA",
      "Probabilities for transitioning from class 5 must equal 1."));
    }

    if (m_fSnagDecompMinSnagBreakHeight.getValue() >= 
      m_fSnagDecompMaxSnagBreakHeight.getValue()) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "The value of \"" + m_fSnagDecompMaxSnagBreakHeight.getDescriptor() +
          "\" must be greater than the value of \"" + 
          m_fSnagDecompMinSnagBreakHeight.getDescriptor() + "\"."));
    }
  }
}
