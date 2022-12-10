package sortie.data.funcgroups.snagdynamics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JDialog;
import java.lang.Math;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;


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
   * This behavior's copy of size class limits for snags, to allow for update
   * checking.
   */
  protected ArrayList<Float> mp_fSnagSizeClasses = new ArrayList<Float>(0);
  
  /**
   * Initial density size class proportions
   * They will look like this:
   *                              Species 1 Species 2 Species 3
   * Size class 10 Decay class 1     0.1       0.3         0.5    
   * Size class 10 Decay class 2     0.2       0.4         0.1
   * Size class 10 Decay class 3     0.0       0.1         0.0
   * Size class 10 Decay class 4     0.7       0.0         0.2
   * Size class 10 Decay class 5     0.0       0.2         0.2
   * 
   * Size class 20 Decay class 1     0.1       0.3         0.5
   * ...
   */
  protected ArrayList<ModelVector> mp_fSnagInitProportions = new ArrayList<ModelVector>(0); 
  

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
    addGrid(oNewGrid, false);
    
    setUpInitialConditionsDecayClasses();
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
    
    //------------------------------------------------------------------------/
    // Make sure snag initial conditions add up to 1
    if (mp_fSnagInitProportions.size() > 0) {
      
      // First off make sure that the number is divisible by 5 so we know we
      // got decay classes for everything
      float div = mp_fSnagInitProportions.size() / 5;
      if (mp_fSnagInitProportions.size() > 0 &&
          Math.abs(div*5 - mp_fSnagInitProportions.size()) > 0.0001) {
        throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Snag initial conditions are broken."));
      }

      // Make sure every vector is the same length, and a proportion   
      int iVecSize = mp_fSnagInitProportions.get(0).getValue().size();
      int i = 0, sc, sp;
      
      for (i = 0; i < mp_fSnagInitProportions.size(); i++) {
        if (mp_fSnagInitProportions.get(0).getValue().size() != iVecSize) {
          throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "Snag initial conditions are broken."));
        }
        ValidationHelpers.makeSureAllAreProportions(mp_fSnagInitProportions.get(i));
      }
      
      
      // Now make sure all for each size class/decay class add up to 1
      float fTotal;
      
      // Cycle over an individual species - one element in the array
      for (sp=0; sp < iVecSize; sp++) {        
        i = 0;
        while (i < mp_fSnagInitProportions.size()) {
          fTotal = 0;
          for (sc = 0; sc < 5; sc++) {            
            fTotal += 
                ((Float)mp_fSnagInitProportions.get(i).getValue().get(sp)).floatValue();
            i++;
          }
          if (Math.abs(fTotal - 1) > 0.001) {            
            throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                "Proportions across decay classes must sum to 1 for all " +
                "species for each size class."));
          }
        }
      }
    }
  }
  
  /**
   * Function for setting up or reconciling snag decay class initial conditions
   * data. This can be called repeatedly as a check. If the snag size classes
   * are the same as the last time this was called, nothing happens. If they
   * are not the same, or this is an initial setup, all existing data will be
   * erased and places to put all the stuff will be added to the data list.
   */
  private void setUpInitialConditionsDecayClasses() throws ModelException {

    TreePopulation oPop = m_oManager.getTreePopulation();
    double fDiff;
    int i, j, sc, iNumSpecies = oPop.getNumberOfSpecies();
    boolean bTheSame = true;

    //------------------------------------------------------------------------/
    // Are the size classes the same as whatever has been already set up?
    if (oPop.getNumberOfSnagSizeClasses() != mp_fSnagSizeClasses.size()) {
      bTheSame = false;       
    } else {

      // Double check all the size classes
      for (i = 0; i < oPop.getNumberOfSizeClasses(); i++) {
        fDiff = Math.abs(oPop.getSnagSizeClass(i) - mp_fSnagSizeClasses.get(i));
        if (fDiff > 0.01) {
          bTheSame = false;
        }
      }
    }
    //------------------------------------------------------------------------/


    //----- If we found no differences, no changes are necessary -------------/
    if (bTheSame) return;

    

    //------------------------------------------------------------------------/
    // If we are still here - we need to set up everything. Start by erasing
    // all existing data
    mp_fSnagSizeClasses.clear();
    mp_fSnagInitProportions.clear();
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData =  mp_oAllData.get(i);
      if (oData.getDescriptor().indexOf("Prop Initial Snags") > -1) {
        mp_oAllData.remove(i);
        i--;
      }
    }
    //------------------------------------------------------------------------/



    //------------------------------------------------------------------------/
    // Add the new size class data - keep our own copy so we can see if it
    // changed
    for (i = 0; i < oPop.getNumberOfSnagSizeClasses(); i++) {
      mp_fSnagSizeClasses.add(oPop.getSnagSizeClass(i));
    }
    //------------------------------------------------------------------------/
    
    
    
    
    //------------------------------------------------------------------------/
    // Add objects for collecting proportion information 
    ModelVector oDensity;
    for (i = 0; i < mp_fSnagSizeClasses.size(); i++) {
      for (sc = 1; sc < 6; sc++) {
        if (i == 0) {              
          oDensity = new ModelVector("Prop Initial Snags 0-"
              + String.valueOf(mp_fSnagSizeClasses.get(i)) + " Decay class " + 
              sc, "", "", iNumSpecies, ModelVector.FLOAT, true);

        } else {
          oDensity = new ModelVector("Prop Initial Snags "
              + String.valueOf(mp_fSnagSizeClasses.get(i - 1)) + "-"
              + String.valueOf(mp_fSnagSizeClasses.get(i)) + " Decay class " + 
                  sc, "", "", iNumSpecies, ModelVector.FLOAT, true);

        }

        // Initialize to 1s for decay class 1
        if (sc == 1) {
          for (j = 0; j < iNumSpecies; j++) {
            oDensity.getValue().add(Float.valueOf(1));          
          }
        } else {
          for (j = 0; j < iNumSpecies; j++) {
            oDensity.getValue().add(Float.valueOf(0));          
          }
        }
        mp_fSnagInitProportions.add(oDensity);
        mp_oAllData.add(oDensity);
      }
    }
    //------------------------------------------------------------------------/

  }
  
  /**
   * Sets up initial conditions snag decay classes.
   */
  public void endOfParameterFileRead() {
    try {
    setUpInitialConditionsDecayClasses();
    } catch (ModelException e) {;}
  }
  
  /**
   * Override this to make sure initial conditions snag decay classes are 
   * set up. 
   */
  public void callSetupDialog(JDialog jParent, MainWindow oMain) {
    try {
      setUpInitialConditionsDecayClasses();
    } catch (ModelException e) {;}
    super.callSetupDialog(jParent, oMain);
  }
  
  /**
   * Writes the data to the parameter file.
   * 
   * @param jOut FileWriter File to write to.
   * @param oPop Tree population object.
   * @throws ModelException won't.
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop) throws
  ModelException {
    try {
      
      jOut.write("<" + m_sXMLRootString + m_iListPosition + ">");
      
      //----- Write the stuff that writes normally ---------------------------/
      Object oData;
      ModelData oDataPiece;
      ModelVector p_oDataVector;
      int i;
      for (i = 0; i < mp_oAllData.size(); i++) {
        oData = mp_oAllData.get(i);
                
        if (oData instanceof ModelVector) {          
          p_oDataVector = (ModelVector) oData;
          // Don't do the snag init conditions proportions
          if (p_oDataVector.getDescriptor().indexOf("Prop Initial Snags") == -1) {
            writeSpeciesSpecificValue(jOut, p_oDataVector, oPop);
          }
        }
        else if (oData instanceof ModelData) {
          oDataPiece = (ModelData) oData;
          writeDataToFile(jOut, oDataPiece);
        }
      }
      
      
      //----- Write snag init conditions proportions. Note that these will ---/ 
      //----- be rearranged from the display ---------------------------------/
      //<< "<sd_idVals whatSpecies=\"Species_2\" whatSizeClass=\"s15.0\">"
      //<< "<sd_id decayClass=\"1\">0.0</sd_id>"
      //<< "<sd_id decayClass=\"2\">0.0</sd_id>"
      //<< "<sd_id decayClass=\"3\">1.0</sd_id>"
      //<< "<sd_id decayClass=\"4\">0.0</sd_id>"
      //<< "<sd_id decayClass=\"5\">0.0</sd_id>"
      //<< "</sd_idVals>"     
      if (mp_fSnagSizeClasses.size() > 0) {
        jOut.write("<sd_snagDecompInitialClasses>");
                
        float[] fDat = new float[5];
        int sc, dc, sp;
        
        // Work with one size class at a time
        for (sc = 0; sc < mp_fSnagSizeClasses.size(); sc++) {
          
          // Each species in this size class
          for (sp = 0; sp < oPop.getNumberOfSpecies(); sp++) {
            
            // Clear out the array to hold new data
            for (dc = 0; dc < 5; dc++) {
              fDat[dc] = 0;

              // Collect this species's data
              p_oDataVector = mp_fSnagInitProportions.get((sc*5 + dc));
              if (p_oDataVector.getValue().get(sp) != null) {
                fDat[dc] = ((Float)p_oDataVector.getValue().get(sp)).floatValue(); 
              }              
            }
            
            // Only bother if it's not the default (all in size class 1)
            if (fDat[0] < 0.99) {
              jOut.write("<sd_idVals whatSpecies=\"" + 
                  oPop.getSpeciesNameFromCode(sp).replace(' ', '_') + 
                  "\" whatSizeClass=\"s" +
                  mp_fSnagSizeClasses.get(sc).toString() + "\">");
              for (dc = 0; dc < 5; dc++) {
                jOut.write("<sd_id decayClass=\"" + (dc+1) + "\">" +
                  fDat[dc] + "</sd_id>");
              }
              jOut.write("</sd_idVals>");
            }
          }
          
        }
        jOut.write("</sd_snagDecompInitialClasses>");
      }
      jOut.write("</" + m_sXMLRootString + m_iListPosition + ">");

    } catch (IOException oExp) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
          "There was a problem writing the parameter file."));
    }
  }
  
  /**
   * Reads snag initial conditions by decay class, if present.
   * 
   * @param sXMLTag Parent XML tag of data vector whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param p_oData Vector of data values appropriate to the data type
   * @param p_sChildXMLTags The XML tags of the child elements
   * @param p_bAppliesTo Array of booleans saying which of the vector values 
   * should be set. This is important in the case of species-specifics - the 
   * vector index is the species number but not all species are set.
   * @param oParentAttributes Attributes of parent tag. May be useful when 
   * overridding this for unusual tags.
   * @param p_oAttributes Attributes passed from parser. This may be needed when
   * overriding this function. Basic species-specific values are already handled
   * by this function.
   * @return true if the value was set successfully; false if the value could
   * not be found. (This would not be an error, because I need a way to cycle 
   * through the objects until one of the objects comes up with a match.) If a 
   * match to a data object is made via XML tag, but the found object is not a 
   * ModelVector, this returns false.
   * @throws ModelException if the value could not be assigned to the data 
   * object.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags,
      boolean[] p_bAppliesTo, org.xml.sax.Attributes oParentAttributes,
      org.xml.sax.Attributes[] p_oAttributes) throws ModelException {


    if (sXMLTag.equals("sd_idVals")) {
      
      // Make sure this has been set up
      if (mp_fSnagSizeClasses.size() == 0) {
        setUpInitialConditionsDecayClasses();
      }
      
      // If the size is still zero, error
      if (mp_fSnagSizeClasses.size() == 0 || 
          mp_fSnagInitProportions.size() == 0) {
        throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
            "Receiving initial conditions info by size class, but no " +
            "size classes are defined."));  
      }
      
      // Make sure we have the expected 5 decay classes of data
      if (p_oData.size() != 5) {
        throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
            "Initial conditions info by size class must have 5 decay " +
            "classes included."));
      }

      TreePopulation oPop = m_oManager.getTreePopulation();
      ModelVector p_oSnagInit;
      String sSp, sSizeClass = "";  
      Float fSizeClass;
      int iClass, dc, j, iSp;


      // What species?
      sSp = oParentAttributes.getValue("whatSpecies");
      iSp = oPop.getSpeciesCodeFromName(sSp);

      // What size class?
      try {
        sSizeClass = oParentAttributes.getValue("whatSizeClass");
        sSizeClass = sSizeClass.substring(1); // trim off inital letter
        fSizeClass = Float.valueOf(sSizeClass);
      } catch (NumberFormatException e) {
        //Whoops - wasn't formatted as a number
        throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
            "Couldn't read size class for snag initial condition. " +
                "Bad sizeclass value: " + sSizeClass));
      }

      // Find the index of the size class
      iClass = -1;
      for (j = 0; j < mp_fSnagSizeClasses.size(); j++) {
        if (fSizeClass.equals(mp_fSnagSizeClasses.get(j))) {
          iClass = j;
          break;
        }
      }
      if (iClass == -1) {
        throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Invalid size class in snag initial densities - " + sSizeClass));
      }

      // Go through each of the decay class values, and find the right
      // slot for it
      for (dc = 0; dc < p_oData.size(); dc++) {

        p_oSnagInit = (ModelVector) mp_fSnagInitProportions.get(iClass*5 + dc);
        try {
          p_oSnagInit.getValue().set(iSp, Float.valueOf(p_oData.get(dc)));
        } catch (NumberFormatException e) {
          //Whoops - wasn't formatted as a number
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
              "Couldn't read size class for snag initial condition for species"
              + sSp + " for decay class " + dc + ". Bad value: " +
              p_oData.get(dc)));
        }

      }
      return true;
    } 
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }
}

