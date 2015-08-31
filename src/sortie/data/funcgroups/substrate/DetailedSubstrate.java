package sortie.data.funcgroups.substrate;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.funcgroups.output.DetailedOutput;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.DetailedGridSettings;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clDetailedSubstrate class.
 * @author lora
 */
public class DetailedSubstrate extends Behavior {

  /**Substrate - Proportion of fallen that uproot for each species.*/
  protected ModelVector mp_fProportionOfFallenThatUproot = new ModelVector(
      "Proportion of Fallen that Uproot", "su_propOfFallUproot", "su_pofuVal",
      0, ModelVector.FLOAT);

  /**Substrate - Proportion of snags that uproot for each species. LEM 04/22/05*/
  protected ModelVector mp_fProportionOfSnagsThatUproot = new ModelVector(
      "Proportion of Snags that Uproot", "su_propOfSnagsUproot", "su_posuVal",
      0, ModelVector.FLOAT);

  /** Detailed substrate - Boundary between two log diameter classes, in cm */
  protected ModelFloat m_fLogSizeClassBoundary = new ModelFloat(0, 
      "Boundary Between Log Diam Classes (cm)", "su_logSizeClassBoundary");

  /** Detailed substrate - species group ID for each species */
  protected ModelVector mp_iSpeciesGroup = new ModelVector(
      "Species Group", "su_logSpGroupAssignment", "su_lsgaVal", 0,
      ModelVector.MODEL_ENUM);

  /**Detailed substrate - proportion of live trees that enter decay class 1*/
  protected ModelFloat m_fPropFallenTreesDecayClass1 = new ModelFloat(0,
      "Prop. Live Trees Entering Decay Class 1 (0-1)", 
      "su_propFallenTreesLogDecayClass1");

  /**Detailed substrate - proportion of live trees that enter decay class 2*/
  protected ModelFloat m_fPropFallenTreesDecayClass2 = new ModelFloat(0,
      "Prop. Live Trees Entering Decay Class 2 (0-1)", 
      "su_propFallenTreesLogDecayClass2");

  /**Detailed substrate - proportion of live trees that enter decay class 3*/
  protected ModelFloat m_fPropFallenTreesDecayClass3 = new ModelFloat(0,
      "Prop. Live Trees Entering Decay Class 3 (0-1)", 
      "su_propFallenTreesLogDecayClass3");

  /**Detailed substrate - proportion of live trees that enter decay class 4*/
  protected ModelFloat m_fPropFallenTreesDecayClass4 = new ModelFloat(0,
      "Prop. Live Trees Entering Decay Class 4 (0-1)", 
      "su_propFallenTreesLogDecayClass4");

  /**Detailed substrate - proportion of live trees that enter decay class 5*/
  protected ModelFloat m_fPropFallenTreesDecayClass5 = new ModelFloat(0,
      "Prop. Live Trees Entering Decay Class 5 (0-1)", 
      "su_propFallenTreesLogDecayClass5");

  /**Detailed substrate - proportion of snags that enter decay class 1*/
  protected ModelFloat m_fPropFallenSnagsDecayClass1 = new ModelFloat(0,
      "Prop. Snags Entering Decay Class 1 (0-1)", 
      "su_propFallenSnagsLogDecayClass1");

  /**Detailed substrate - proportion of snags that enter decay class 2*/
  protected ModelFloat m_fPropFallenSnagsDecayClass2 = new ModelFloat(0,
      "Prop. Snags Entering Decay Class 2 (0-1)", 
      "su_propFallenSnagsLogDecayClass2");

  /**Detailed substrate - proportion of snags that enter decay class 3*/
  protected ModelFloat m_fPropFallenSnagsDecayClass3 = new ModelFloat(0,
      "Prop. Snags Entering Decay Class 3 (0-1)", 
      "su_propFallenSnagsLogDecayClass3");

  /**Detailed substrate - proportion of snags that enter decay class 4*/
  protected ModelFloat m_fPropFallenSnagsDecayClass4 = new ModelFloat(0,
      "Prop. Snags Entering Decay Class 4 (0-1)", 
      "su_propFallenSnagsLogDecayClass4");

  /**Detailed substrate - proportion of snags that enter decay class 5*/
  protected ModelFloat m_fPropFallenSnagsDecayClass5 = new ModelFloat(0,
      "Prop. Snags Entering Decay Class 5 (0-1)", 
      "su_propFallenSnagsLogDecayClass5");

  /**Detailed substrate - species group 1 small class 1 log decay alpha*/
  protected ModelFloat m_fSpGroup1SmallClass1Alpha = new ModelFloat(0,
      "Species Group 1 Small Class 1 Log Decay Alpha", 
      "su_logSpGroup1SmallDecayClass1DecayAlpha");

  /**Detailed substrate - species group 1 small class 2 log decay alpha*/
  protected ModelFloat m_fSpGroup1SmallClass2Alpha = new ModelFloat(0,
      "Species Group 1 Small Class 2 Log Decay Alpha", 
      "su_logSpGroup1SmallDecayClass2DecayAlpha");

  /**Detailed substrate - species group 1 small class 3 log decay alpha*/
  protected ModelFloat m_fSpGroup1SmallClass3Alpha = new ModelFloat(0,
      "Species Group 1 Small Class 3 Log Decay Alpha", 
      "su_logSpGroup1SmallDecayClass3DecayAlpha");

  /**Detailed substrate - species group 1 small class 4 log decay alpha*/
  protected ModelFloat m_fSpGroup1SmallClass4Alpha = new ModelFloat(0,
      "Species Group 1 Small Class 4 Log Decay Alpha", 
      "su_logSpGroup1SmallDecayClass4DecayAlpha");

  /**Detailed substrate - species group 1 small class 5 log decay alpha*/
  protected ModelFloat m_fSpGroup1SmallClass5Alpha = new ModelFloat(0,
      "Species Group 1 Small Class 5 Log Decay Alpha", 
      "su_logSpGroup1SmallDecayClass5DecayAlpha");

  /**Detailed substrate - species group 2 small class 1 log decay alpha*/
  protected ModelFloat m_fSpGroup2SmallClass1Alpha = new ModelFloat(0,
      "Species Group 2 Small Class 1 Log Decay Alpha", 
      "su_logSpGroup2SmallDecayClass1DecayAlpha");

  /**Detailed substrate - species group 2 small class 2 log decay alpha*/
  protected ModelFloat m_fSpGroup2SmallClass2Alpha = new ModelFloat(0,
      "Species Group 2 Small Class 2 Log Decay Alpha", 
      "su_logSpGroup2SmallDecayClass2DecayAlpha");

  /**Detailed substrate - species group 2 small class 3 log decay alpha*/
  protected ModelFloat m_fSpGroup2SmallClass3Alpha = new ModelFloat(0,
      "Species Group 2 Small Class 3 Log Decay Alpha", 
      "su_logSpGroup2SmallDecayClass3DecayAlpha");

  /**Detailed substrate - species group 2 small class 4 log decay alpha*/
  protected ModelFloat m_fSpGroup2SmallClass4Alpha = new ModelFloat(0,
      "Species Group 2 Small Class 4 Log Decay Alpha", 
      "su_logSpGroup2SmallDecayClass4DecayAlpha");

  /**Detailed substrate - species group 2 small class 5 log decay alpha*/
  protected ModelFloat m_fSpGroup2SmallClass5Alpha = new ModelFloat(0,
      "Species Group 2 Small Class 5 Log Decay Alpha", 
      "su_logSpGroup2SmallDecayClass5DecayAlpha");

  /**Detailed substrate - species group 3 small class 1 log decay alpha*/
  protected ModelFloat m_fSpGroup3SmallClass1Alpha = new ModelFloat(0,
      "Species Group 3 Small Class 1 Log Decay Alpha", 
      "su_logSpGroup3SmallDecayClass1DecayAlpha");

  /**Detailed substrate - species group 3 small class 2 log decay alpha*/
  protected ModelFloat m_fSpGroup3SmallClass2Alpha = new ModelFloat(0,
      "Species Group 3 Small Class 2 Log Decay Alpha", 
      "su_logSpGroup3SmallDecayClass2DecayAlpha");

  /**Detailed substrate - species group 3 small class 3 log decay alpha*/
  protected ModelFloat m_fSpGroup3SmallClass3Alpha = new ModelFloat(0,
      "Species Group 3 Small Class 3 Log Decay Alpha", 
      "su_logSpGroup3SmallDecayClass3DecayAlpha");

  /**Detailed substrate - species group 3 small class 4 log decay alpha*/
  protected ModelFloat m_fSpGroup3SmallClass4Alpha = new ModelFloat(0,
      "Species Group 3 Small Class 4 Log Decay Alpha", 
      "su_logSpGroup3SmallDecayClass4DecayAlpha");

  /**Detailed substrate - species group 3 small class 5 log decay alpha*/
  protected ModelFloat m_fSpGroup3SmallClass5Alpha = new ModelFloat(0,
      "Species Group 3 Small Class 5 Log Decay Alpha", 
      "su_logSpGroup3SmallDecayClass5DecayAlpha");

  /**Detailed substrate - species group 1 large class 1 log decay alpha*/
  protected ModelFloat m_fSpGroup1LargeClass1Alpha = new ModelFloat(0,
      "Species Group 1 Large Class 1 Log Decay Alpha", 
      "su_logSpGroup1LargeDecayClass1DecayAlpha");

  /**Detailed substrate - species group 1 large class 2 log decay alpha*/
  protected ModelFloat m_fSpGroup1LargeClass2Alpha = new ModelFloat(0,
      "Species Group 1 Large Class 2 Log Decay Alpha", 
      "su_logSpGroup1LargeDecayClass2DecayAlpha");

  /**Detailed substrate - species group 1 large class 3 log decay alpha*/
  protected ModelFloat m_fSpGroup1LargeClass3Alpha = new ModelFloat(0,
      "Species Group 1 Large Class 3 Log Decay Alpha", 
      "su_logSpGroup1LargeDecayClass3DecayAlpha");

  /**Detailed substrate - species group 1 large class 4 log decay alpha*/
  protected ModelFloat m_fSpGroup1LargeClass4Alpha = new ModelFloat(0,
      "Species Group 1 Large Class 4 Log Decay Alpha", 
      "su_logSpGroup1LargeDecayClass4DecayAlpha");

  /**Detailed substrate - species group 1 large class 5 log decay alpha*/
  protected ModelFloat m_fSpGroup1LargeClass5Alpha = new ModelFloat(0,
      "Species Group 1 Large Class 5 Log Decay Alpha", 
      "su_logSpGroup1LargeDecayClass5DecayAlpha");

  /**Detailed substrate - species group 2 large class 1 log decay alpha*/
  protected ModelFloat m_fSpGroup2LargeClass1Alpha = new ModelFloat(0,
      "Species Group 2 Large Class 1 Log Decay Alpha", 
      "su_logSpGroup2LargeDecayClass1DecayAlpha");

  /**Detailed substrate - species group 2 large class 2 log decay alpha*/
  protected ModelFloat m_fSpGroup2LargeClass2Alpha = new ModelFloat(0,
      "Species Group 2 Large Class 2 Log Decay Alpha", 
      "su_logSpGroup2LargeDecayClass2DecayAlpha");

  /**Detailed substrate - species group 2 large class 3 log decay alpha*/
  protected ModelFloat m_fSpGroup2LargeClass3Alpha = new ModelFloat(0,
      "Species Group 2 Large Class 3 Log Decay Alpha", 
      "su_logSpGroup2LargeDecayClass3DecayAlpha");

  /**Detailed substrate - species group 2 large class 4 log decay alpha*/
  protected ModelFloat m_fSpGroup2LargeClass4Alpha = new ModelFloat(0,
      "Species Group 2 Large Class 4 Log Decay Alpha", 
      "su_logSpGroup2LargeDecayClass4DecayAlpha");

  /**Detailed substrate - species group 2 large class 5 log decay alpha*/
  protected ModelFloat m_fSpGroup2LargeClass5Alpha = new ModelFloat(0,
      "Species Group 2 Large Class 5 Log Decay Alpha", 
      "su_logSpGroup2LargeDecayClass5DecayAlpha");

  /**Detailed substrate - species group 3 large class 1 log decay alpha*/
  protected ModelFloat m_fSpGroup3LargeClass1Alpha = new ModelFloat(0,
      "Species Group 3 Large Class 1 Log Decay Alpha",
      "su_logSpGroup3LargeDecayClass1DecayAlpha");

  /**Detailed substrate - species group 3 large class 2 log decay alpha*/
  protected ModelFloat m_fSpGroup3LargeClass2Alpha = new ModelFloat(0,
      "Species Group 3 Large Class 2 Log Decay Alpha", 
      "su_logSpGroup3LargeDecayClass2DecayAlpha");

  /**Detailed substrate - species group 3 large class 3 log decay alpha*/
  protected ModelFloat m_fSpGroup3LargeClass3Alpha = new ModelFloat(0,
      "Species Group 3 Large Class 3 Log Decay Alpha", 
      "su_logSpGroup3LargeDecayClass3DecayAlpha");

  /**Detailed substrate - species group 3 large class 4 log decay alpha*/
  protected ModelFloat m_fSpGroup3LargeClass4Alpha = new ModelFloat(0,
      "Species Group 3 Large Class 4 Log Decay Alpha", 
      "su_logSpGroup3LargeDecayClass4DecayAlpha");

  /**Detailed substrate - species group 3 large class 5 log decay alpha*/
  protected ModelFloat m_fSpGroup3LargeClass5Alpha = new ModelFloat(0,
      "Species Group 3 Large Class 5 Log Decay Alpha", 
      "su_logSpGroup3LargeDecayClass5DecayAlpha");

  /**Detailed substrate - species group 1 small class 1 log decay beta*/
  protected ModelFloat m_fSpGroup1SmallClass1Beta = new ModelFloat(0,
      "Species Group 1 Small Class 1 Log Decay Beta", 
      "su_logSpGroup1SmallDecayClass1DecayBeta");

  /**Detailed substrate - species group 1 small class 2 log decay beta*/
  protected ModelFloat m_fSpGroup1SmallClass2Beta = new ModelFloat(0,
      "Species Group 1 Small Class 2 Log Decay Beta", 
      "su_logSpGroup1SmallDecayClass2DecayBeta");

  /**Detailed substrate - species group 1 small class 3 log decay beta*/
  protected ModelFloat m_fSpGroup1SmallClass3Beta = new ModelFloat(0,
      "Species Group 1 Small Class 3 Log Decay Beta", 
      "su_logSpGroup1SmallDecayClass3DecayBeta");

  /**Detailed substrate - species group 1 small class 4 log decay beta*/
  protected ModelFloat m_fSpGroup1SmallClass4Beta = new ModelFloat(0,
      "Species Group 1 Small Class 4 Log Decay Beta", 
      "su_logSpGroup1SmallDecayClass4DecayBeta");

  /**Detailed substrate - species group 1 small class 5 log decay beta*/
  protected ModelFloat m_fSpGroup1SmallClass5Beta = new ModelFloat(0,
      "Species Group 1 Small Class 5 Log Decay Beta", 
      "su_logSpGroup1SmallDecayClass5DecayBeta");

  /**Detailed substrate - species group 2 small class 1 log decay beta*/
  protected ModelFloat m_fSpGroup2SmallClass1Beta = new ModelFloat(0,
      "Species Group 2 Small Class 1 Log Decay Beta", 
      "su_logSpGroup2SmallDecayClass1DecayBeta");

  /**Detailed substrate - species group 2 small class 2 log decay beta*/
  protected ModelFloat m_fSpGroup2SmallClass2Beta = new ModelFloat(0,
      "Species Group 2 Small Class 2 Log Decay Beta", 
      "su_logSpGroup2SmallDecayClass2DecayBeta");

  /**Detailed substrate - species group 2 small class 3 log decay beta*/
  protected ModelFloat m_fSpGroup2SmallClass3Beta = new ModelFloat(0,
      "Species Group 2 Small Class 3 Log Decay Beta", 
      "su_logSpGroup2SmallDecayClass3DecayBeta");

  /**Detailed substrate - species group 2 small class 4 log decay beta*/
  protected ModelFloat m_fSpGroup2SmallClass4Beta = new ModelFloat(0,
      "Species Group 2 Small Class 4 Log Decay Beta", 
      "su_logSpGroup2SmallDecayClass4DecayBeta");

  /**Detailed substrate - species group 2 small class 5 log decay beta*/
  protected ModelFloat m_fSpGroup2SmallClass5Beta = new ModelFloat(0,
      "Species Group 2 Small Class 5 Log Decay Beta", 
      "su_logSpGroup2SmallDecayClass5DecayBeta");

  /**Detailed substrate - species group 3 small class 1 log decay beta*/
  protected ModelFloat m_fSpGroup3SmallClass1Beta = new ModelFloat(0,
      "Species Group 3 Small Class 1 Log Decay Beta", 
      "su_logSpGroup3SmallDecayClass1DecayBeta");

  /**Detailed substrate - species group 3 small class 2 log decay beta*/
  protected ModelFloat m_fSpGroup3SmallClass2Beta = new ModelFloat(0,
      "Species Group 3 Small Class 2 Log Decay Beta", 
      "su_logSpGroup3SmallDecayClass2DecayBeta");

  /**Detailed substrate - species group 3 small class 3 log decay beta*/
  protected ModelFloat m_fSpGroup3SmallClass3Beta = new ModelFloat(0,
      "Species Group 3 Small Class 3 Log Decay Beta", 
      "su_logSpGroup3SmallDecayClass3DecayBeta");

  /**Detailed substrate - species group 3 small class 4 log decay beta*/
  protected ModelFloat m_fSpGroup3SmallClass4Beta = new ModelFloat(0,
      "Species Group 3 Small Class 4 Log Decay Beta", 
      "su_logSpGroup3SmallDecayClass4DecayBeta");

  /**Detailed substrate - species group 3 small class 5 log decay beta*/
  protected ModelFloat m_fSpGroup3SmallClass5Beta = new ModelFloat(0,
      "Species Group 3 Small Class 5 Log Decay Beta", 
      "su_logSpGroup3SmallDecayClass5DecayBeta");

  /**Detailed substrate - species group 1 large class 1 log decay beta*/
  protected ModelFloat m_fSpGroup1LargeClass1Beta = new ModelFloat(0,
      "Species Group 1 Large Class 1 Log Decay Beta", 
      "su_logSpGroup1LargeDecayClass1DecayBeta");

  /**Detailed substrate - species group 1 large class 2 log decay beta*/
  protected ModelFloat m_fSpGroup1LargeClass2Beta = new ModelFloat(0,
      "Species Group 1 Large Class 2 Log Decay Beta", 
      "su_logSpGroup1LargeDecayClass2DecayBeta");

  /**Detailed substrate - species group 1 large class 3 log decay beta*/
  protected ModelFloat m_fSpGroup1LargeClass3Beta = new ModelFloat(0,
      "Species Group 1 Large Class 3 Log Decay Beta", 
      "su_logSpGroup1LargeDecayClass3DecayBeta");

  /**Detailed substrate - species group 1 large class 4 log decay beta*/
  protected ModelFloat m_fSpGroup1LargeClass4Beta = new ModelFloat(0,
      "Species Group 1 Large Class 4 Log Decay Beta", 
      "su_logSpGroup1LargeDecayClass4DecayBeta");

  /**Detailed substrate - species group 1 large class 5 log decay beta*/
  protected ModelFloat m_fSpGroup1LargeClass5Beta = new ModelFloat(0,
      "Species Group 1 Large Class 5 Log Decay Beta", 
      "su_logSpGroup1LargeDecayClass5DecayBeta");

  /**Detailed substrate - species group 2 large class 1 log decay beta*/
  protected ModelFloat m_fSpGroup2LargeClass1Beta = new ModelFloat(0,
      "Species Group 2 Large Class 1 Log Decay Beta", 
      "su_logSpGroup2LargeDecayClass1DecayBeta");

  /**Detailed substrate - species group 2 large class 2 log decay beta*/
  protected ModelFloat m_fSpGroup2LargeClass2Beta = new ModelFloat(0,
      "Species Group 2 Large Class 2 Log Decay Beta", 
      "su_logSpGroup2LargeDecayClass2DecayBeta");

  /**Detailed substrate - species group 2 large class 3 log decay beta*/
  protected ModelFloat m_fSpGroup2LargeClass3Beta = new ModelFloat(0,
      "Species Group 2 Large Class 3 Log Decay Beta", 
      "su_logSpGroup2LargeDecayClass3DecayBeta");

  /**Detailed substrate - species group 2 large class 4 log decay beta*/
  protected ModelFloat m_fSpGroup2LargeClass4Beta = new ModelFloat(0,
      "Species Group 2 Large Class 4 Log Decay Beta", 
      "su_logSpGroup2LargeDecayClass4DecayBeta");

  /**Detailed substrate - species group 2 large class 5 log decay beta*/
  protected ModelFloat m_fSpGroup2LargeClass5Beta = new ModelFloat(0,
      "Species Group 2 Large Class 5 Log Decay Beta", 
      "su_logSpGroup2LargeDecayClass5DecayBeta");

  /**Detailed substrate - species group 3 large class 1 log decay beta*/
  protected ModelFloat m_fSpGroup3LargeClass1Beta = new ModelFloat(0,
      "Species Group 3 Large Class 1 Log Decay Beta", 
      "su_logSpGroup3LargeDecayClass1DecayBeta");

  /**Detailed substrate - species group 3 large class 2 log decay beta*/
  protected ModelFloat m_fSpGroup3LargeClass2Beta = new ModelFloat(0,
      "Species Group 3 Large Class 2 Log Decay Beta", 
      "su_logSpGroup3LargeDecayClass2DecayBeta");

  /**Detailed substrate - species group 3 large class 3 log decay beta*/
  protected ModelFloat m_fSpGroup3LargeClass3Beta = new ModelFloat(0,
      "Species Group 3 Large Class 3 Log Decay Beta", 
      "su_logSpGroup3LargeDecayClass3DecayBeta");

  /**Detailed substrate - species group 3 large class 4 log decay beta*/
  protected ModelFloat m_fSpGroup3LargeClass4Beta = new ModelFloat(0,
      "Species Group 3 Large Class 4 Log Decay Beta", 
      "su_logSpGroup3LargeDecayClass4DecayBeta");

  /**Detailed substrate - species group 3 large class 5 log decay beta*/
  protected ModelFloat m_fSpGroup3LargeClass5Beta = new ModelFloat(0,
      "Species Group 3 Large Class 5 Log Decay Beta", 
      "su_logSpGroup3LargeDecayClass5DecayBeta");

  /**Detailed substrate - species group 1 small class 1 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup1SmallClass1InitLog = new ModelFloat(0,
      "Species Group 1 Small Class 1 Initial Log Prop (0-1)",
      "su_initialLogSpGroup1SmallDecayClass1");

  /**Detailed substrate - species group 1 small class 2 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup1SmallClass2InitLog = new ModelFloat(0,
      "Species Group 1 Small Class 2 Initial Log Prop (0-1)",
      "su_initialLogSpGroup1SmallDecayClass2");

  /**Detailed substrate - species group 1 small class 3 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup1SmallClass3InitLog = new ModelFloat(0,
      "Species Group 1 Small Class 3 Initial Log Prop (0-1)",
      "su_initialLogSpGroup1SmallDecayClass3");

  /**Detailed substrate - species group 1 small class 4 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup1SmallClass4InitLog = new ModelFloat(0,
      "Species Group 1 Small Class 4 Initial Log Prop (0-1)",
      "su_initialLogSpGroup1SmallDecayClass4");

  /**Detailed substrate - species group 1 small class 5 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup1SmallClass5InitLog = new ModelFloat(0,
      "Species Group 1 Small Class 5 Initial Log Prop (0-1)",
      "su_initialLogSpGroup1SmallDecayClass5");

  /**Detailed substrate - species group 2 small class 1 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup2SmallClass1InitLog = new ModelFloat(0,
      "Species Group 2 Small Class 1 Initial Log Prop (0-1)",
      "su_initialLogSpGroup2SmallDecayClass1");

  /**Detailed substrate - species group 2 small class 2 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup2SmallClass2InitLog = new ModelFloat(0,
      "Species Group 2 Small Class 2 Initial Log Prop (0-1)",
      "su_initialLogSpGroup2SmallDecayClass2");

  /**Detailed substrate - species group 2 small class 3 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup2SmallClass3InitLog = new ModelFloat(0,
      "Species Group 2 Small Class 3 Initial Log Prop (0-1)",
      "su_initialLogSpGroup2SmallDecayClass3");

  /**Detailed substrate - species group 2 small class 4 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup2SmallClass4InitLog = new ModelFloat(0,
      "Species Group 2 Small Class 4 Initial Log Prop (0-1)",
      "su_initialLogSpGroup2SmallDecayClass4");

  /**Detailed substrate - species group 2 small class 5 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup2SmallClass5InitLog = new ModelFloat(0,
      "Species Group 2 Small Class 5 Initial Log Prop (0-1)",
      "su_initialLogSpGroup2SmallDecayClass5");

  /**Detailed substrate - species group 3 small class 1 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup3SmallClass1InitLog = new ModelFloat(0,
      "Species Group 3 Small Class 1 Initial Log Prop (0-1)",
      "su_initialLogSpGroup3SmallDecayClass1");

  /**Detailed substrate - species group 3 small class 2 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup3SmallClass2InitLog = new ModelFloat(0,
      "Species Group 3 Small Class 2 Initial Log Prop (0-1)",
      "su_initialLogSpGroup3SmallDecayClass2");

  /**Detailed substrate - species group 3 small class 3 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup3SmallClass3InitLog = new ModelFloat(0,
      "Species Group 3 Small Class 3 Initial Log Prop (0-1)",
      "su_initialLogSpGroup3SmallDecayClass3");

  /**Detailed substrate - species group 3 small class 4 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup3SmallClass4InitLog = new ModelFloat(0,
      "Species Group 3 Small Class 4 Initial Log Prop (0-1)",
      "su_initialLogSpGroup3SmallDecayClass4");

  /**Detailed substrate - species group 3 small class 5 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup3SmallClass5InitLog = new ModelFloat(0,
      "Species Group 3 Small Class 5 Initial Log Prop (0-1)",
      "su_initialLogSpGroup3SmallDecayClass5");

  /**Detailed substrate - species group 1 large class 1 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup1LargeClass1InitLog = new ModelFloat(0,
      "Species Group 1 Large Class 1 Initial Log Prop (0-1)",
      "su_initialLogSpGroup1LargeDecayClass1");

  /**Detailed substrate - species group 1 large class 2 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup1LargeClass2InitLog = new ModelFloat(0,
      "Species Group 1 Large Class 2 Initial Log Prop (0-1)",
      "su_initialLogSpGroup1LargeDecayClass2");

  /**Detailed substrate - species group 1 large class 3 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup1LargeClass3InitLog = new ModelFloat(0,
      "Species Group 1 Large Class 3 Initial Log Prop (0-1)",
      "su_initialLogSpGroup1LargeDecayClass3");

  /**Detailed substrate - species group 1 large class 4 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup1LargeClass4InitLog = new ModelFloat(0,
      "Species Group 1 Large Class 4 Initial Log Prop (0-1)",
      "su_initialLogSpGroup1LargeDecayClass4");

  /**Detailed substrate - species group 1 large class 5 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup1LargeClass5InitLog = new ModelFloat(0,
      "Species Group 1 Large Class 5 Initial Log Prop (0-1)",
      "su_initialLogSpGroup1LargeDecayClass5");

  /**Detailed substrate - species group 2 large class 1 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup2LargeClass1InitLog = new ModelFloat(0,
      "Species Group 2 Large Class 1 Initial Log Prop (0-1)",
      "su_initialLogSpGroup2LargeDecayClass1");

  /**Detailed substrate - species group 2 large class 2 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup2LargeClass2InitLog = new ModelFloat(0,
      "Species Group 2 Large Class 2 Initial Log Prop (0-1)",
      "su_initialLogSpGroup2LargeDecayClass2");

  /**Detailed substrate - species group 2 large class 3 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup2LargeClass3InitLog = new ModelFloat(0,
      "Species Group 2 Large Class 3 Initial Log Prop (0-1)",
      "su_initialLogSpGroup2LargeDecayClass3");

  /**Detailed substrate - species group 2 large class 4 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup2LargeClass4InitLog = new ModelFloat(0,
      "Species Group 2 Large Class 4 Initial Log Prop (0-1)",
      "su_initialLogSpGroup2LargeDecayClass4");

  /**Detailed substrate - species group 2 large class 5 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup2LargeClass5InitLog = new ModelFloat(0,
      "Species Group 2 Large Class 5 Initial Log Prop (0-1)",
      "su_initialLogSpGroup2LargeDecayClass5");

  /**Detailed substrate - species group 3 large class 1 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup3LargeClass1InitLog = new ModelFloat(0,
      "Species Group 3 Large Class 1 Initial Log Prop (0-1)",
      "su_initialLogSpGroup3LargeDecayClass1");

  /**Detailed substrate - species group 3 large class 2 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup3LargeClass2InitLog = new ModelFloat(0,
      "Species Group 3 Large Class 2 Initial Log Prop (0-1)",
      "su_initialLogSpGroup3LargeDecayClass2");

  /**Detailed substrate - species group 3 large class 3 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup3LargeClass3InitLog = new ModelFloat(0,
      "Species Group 3 Large Class 3 Initial Log Prop (0-1)",
      "su_initialLogSpGroup3LargeDecayClass3");

  /**Detailed substrate - species group 3 large class 4 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup3LargeClass4InitLog = new ModelFloat(0,
      "Species Group 3 Large Class 4 Initial Log Prop (0-1)",
      "su_initialLogSpGroup3LargeDecayClass4");

  /**Detailed substrate - species group 3 large class 5 log initial 
   * conditions proportion*/
  protected ModelFloat m_fSpGroup3LargeClass5InitLog = new ModelFloat(0,
      "Species Group 3 Large Class 5 Initial Log Prop (0-1)",
      "su_initialLogSpGroup3LargeDecayClass5");

  /**Detailed substrate - species group 1 small class 1 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1SmallClass1PartCutLog = new ModelFloat(0,
      "Species Group 1 Small Class 1 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup1SmallDecayClass1");

  /**Detailed substrate - species group 1 small class 2 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1SmallClass2PartCutLog = new ModelFloat(0,
      "Species Group 1 Small Class 2 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup1SmallDecayClass2");

  /**Detailed substrate - species group 1 small class 3 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1SmallClass3PartCutLog = new ModelFloat(0,
      "Species Group 1 Small Class 3 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup1SmallDecayClass3");

  /**Detailed substrate - species group 1 small class 4 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1SmallClass4PartCutLog = new ModelFloat(0,
      "Species Group 1 Small Class 4 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup1SmallDecayClass4");

  /**Detailed substrate - species group 1 small class 5 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1SmallClass5PartCutLog = new ModelFloat(0,
      "Species Group 1 Small Class 5 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup1SmallDecayClass5");

  /**Detailed substrate - species group 2 small class 1 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2SmallClass1PartCutLog = new ModelFloat(0,
      "Species Group 2 Small Class 1 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup2SmallDecayClass1");

  /**Detailed substrate - species group 2 small class 2 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2SmallClass2PartCutLog = new ModelFloat(0,
      "Species Group 2 Small Class 2 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup2SmallDecayClass2");

  /**Detailed substrate - species group 2 small class 3 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2SmallClass3PartCutLog = new ModelFloat(0,
      "Species Group 2 Small Class 3 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup2SmallDecayClass3");

  /**Detailed substrate - species group 2 small class 4 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2SmallClass4PartCutLog = new ModelFloat(0,
      "Species Group 2 Small Class 4 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup2SmallDecayClass4");

  /**Detailed substrate - species group 2 small class 5 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2SmallClass5PartCutLog = new ModelFloat(0,
      "Species Group 2 Small Class 5 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup2SmallDecayClass5");

  /**Detailed substrate - species group 3 small class 1 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3SmallClass1PartCutLog = new ModelFloat(0,
      "Species Group 3 Small Class 1 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup3SmallDecayClass1");

  /**Detailed substrate - species group 3 small class 2 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3SmallClass2PartCutLog = new ModelFloat(0,
      "Species Group 3 Small Class 2 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup3SmallDecayClass2");

  /**Detailed substrate - species group 3 small class 3 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3SmallClass3PartCutLog = new ModelFloat(0,
      "Species Group 3 Small Class 3 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup3SmallDecayClass3");

  /**Detailed substrate - species group 3 small class 4 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3SmallClass4PartCutLog = new ModelFloat(0,
      "Species Group 3 Small Class 4 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup3SmallDecayClass4");

  /**Detailed substrate - species group 3 small class 5 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3SmallClass5PartCutLog = new ModelFloat(0,
      "Species Group 3 Small Class 5 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup3SmallDecayClass5");

  /**Detailed substrate - species group 1 large class 1 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1LargeClass1PartCutLog = new ModelFloat(0,
      "Species Group 1 Large Class 1 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup1LargeDecayClass1");

  /**Detailed substrate - species group 1 large class 2 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1LargeClass2PartCutLog = new ModelFloat(0,
      "Species Group 1 Large Class 2 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup1LargeDecayClass2");

  /**Detailed substrate - species group 1 large class 3 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1LargeClass3PartCutLog = new ModelFloat(0,
      "Species Group 1 Large Class 3 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup1LargeDecayClass3");

  /**Detailed substrate - species group 1 large class 4 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1LargeClass4PartCutLog = new ModelFloat(0,
      "Species Group 1 Large Class 4 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup1LargeDecayClass4");

  /**Detailed substrate - species group 1 large class 5 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1LargeClass5PartCutLog = new ModelFloat(0,
      "Species Group 1 Large Class 5 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup1LargeDecayClass5");

  /**Detailed substrate - species group 2 large class 1 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2LargeClass1PartCutLog = new ModelFloat(0,
      "Species Group 2 Large Class 1 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup2LargeDecayClass1");

  /**Detailed substrate - species group 2 large class 2 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2LargeClass2PartCutLog = new ModelFloat(0,
      "Species Group 2 Large Class 2 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup2LargeDecayClass2");

  /**Detailed substrate - species group 2 large class 3 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2LargeClass3PartCutLog = new ModelFloat(0,
      "Species Group 2 Large Class 3 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup2LargeDecayClass3");

  /**Detailed substrate - species group 2 large class 4 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2LargeClass4PartCutLog = new ModelFloat(0,
      "Species Group 2 Large Class 4 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup2LargeDecayClass4");

  /**Detailed substrate - species group 2 large class 5 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2LargeClass5PartCutLog = new ModelFloat(0,
      "Species Group 2 Large Class 5 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup2LargeDecayClass5");

  /**Detailed substrate - species group 3 large class 1 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3LargeClass1PartCutLog = new ModelFloat(0,
      "Species Group 3 Large Class 1 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup3LargeDecayClass1");

  /**Detailed substrate - species group 3 large class 2 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3LargeClass2PartCutLog = new ModelFloat(0,
      "Species Group 3 Large Class 2 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup3LargeDecayClass2");

  /**Detailed substrate - species group 3 large class 3 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3LargeClass3PartCutLog = new ModelFloat(0,
      "Species Group 3 Large Class 3 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup3LargeDecayClass3");

  /**Detailed substrate - species group 3 large class 4 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3LargeClass4PartCutLog = new ModelFloat(0,
      "Species Group 3 Large Class 4 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup3LargeDecayClass4");

  /**Detailed substrate - species group 3 large class 5 partial cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3LargeClass5PartCutLog = new ModelFloat(0,
      "Species Group 3 Large Class 5 Partial Cut Log (0-1)",
      "su_partialCutLogSpGroup3LargeDecayClass5");

  /**Detailed substrate - species group 1 small class 1 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1SmallClass1GapCutLog = new ModelFloat(0,
      "Species Group 1 Small Class 1 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup1SmallDecayClass1");

  /**Detailed substrate - species group 1 small class 2 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1SmallClass2GapCutLog = new ModelFloat(0,
      "Species Group 1 Small Class 2 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup1SmallDecayClass2");

  /**Detailed substrate - species group 1 small class 3 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1SmallClass3GapCutLog = new ModelFloat(0,
      "Species Group 1 Small Class 3 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup1SmallDecayClass3");

  /**Detailed substrate - species group 1 small class 4 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1SmallClass4GapCutLog = new ModelFloat(0,
      "Species Group 1 Small Class 4 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup1SmallDecayClass4");

  /**Detailed substrate - species group 1 small class 5 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1SmallClass5GapCutLog = new ModelFloat(0,
      "Species Group 1 Small Class 5 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup1SmallDecayClass5");

  /**Detailed substrate - species group 2 small class 1 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2SmallClass1GapCutLog = new ModelFloat(0,
      "Species Group 2 Small Class 1 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup2SmallDecayClass1");

  /**Detailed substrate - species group 2 small class 2 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2SmallClass2GapCutLog = new ModelFloat(0,
      "Species Group 2 Small Class 2 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup2SmallDecayClass2");

  /**Detailed substrate - species group 2 small class 3 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2SmallClass3GapCutLog = new ModelFloat(0,
      "Species Group 2 Small Class 3 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup2SmallDecayClass3");

  /**Detailed substrate - species group 2 small class 4 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2SmallClass4GapCutLog = new ModelFloat(0,
      "Species Group 2 Small Class 4 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup2SmallDecayClass4");

  /**Detailed substrate - species group 2 small class 5 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2SmallClass5GapCutLog = new ModelFloat(0,
      "Species Group 2 Small Class 5 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup2SmallDecayClass5");

  /**Detailed substrate - species group 3 small class 1 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3SmallClass1GapCutLog = new ModelFloat(0,
      "Species Group 3 Small Class 1 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup3SmallDecayClass1");

  /**Detailed substrate - species group 3 small class 2 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3SmallClass2GapCutLog = new ModelFloat(0,
      "Species Group 3 Small Class 2 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup3SmallDecayClass2");

  /**Detailed substrate - species group 3 small class 3 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3SmallClass3GapCutLog = new ModelFloat(0,
      "Species Group 3 Small Class 3 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup3SmallDecayClass3");

  /**Detailed substrate - species group 3 small class 4 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3SmallClass4GapCutLog = new ModelFloat(0,
      "Species Group 3 Small Class 4 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup3SmallDecayClass4");

  /**Detailed substrate - species group 3 small class 5 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3SmallClass5GapCutLog = new ModelFloat(0,
      "Species Group 3 Small Class 5 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup3SmallDecayClass5");

  /**Detailed substrate - species group 1 large class 1 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1LargeClass1GapCutLog = new ModelFloat(0,
      "Species Group 1 Large Class 1 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup1LargeDecayClass1");

  /**Detailed substrate - species group 1 large class 2 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1LargeClass2GapCutLog = new ModelFloat(0,
      "Species Group 1 Large Class 2 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup1LargeDecayClass2");

  /**Detailed substrate - species group 1 large class 3 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1LargeClass3GapCutLog = new ModelFloat(0,
      "Species Group 1 Large Class 3 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup1LargeDecayClass3");

  /**Detailed substrate - species group 1 large class 4 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1LargeClass4GapCutLog = new ModelFloat(0,
      "Species Group 1 Large Class 4 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup1LargeDecayClass4");

  /**Detailed substrate - species group 1 large class 5 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1LargeClass5GapCutLog = new ModelFloat(0,
      "Species Group 1 Large Class 5 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup1LargeDecayClass5");

  /**Detailed substrate - species group 2 large class 1 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2LargeClass1GapCutLog = new ModelFloat(0,
      "Species Group 2 Large Class 1 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup2LargeDecayClass1");

  /**Detailed substrate - species group 2 large class 2 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2LargeClass2GapCutLog = new ModelFloat(0,
      "Species Group 2 Large Class 2 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup2LargeDecayClass2");

  /**Detailed substrate - species group 2 large class 3 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2LargeClass3GapCutLog = new ModelFloat(0,
      "Species Group 2 Large Class 3 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup2LargeDecayClass3");

  /**Detailed substrate - species group 2 large class 4 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2LargeClass4GapCutLog = new ModelFloat(0,
      "Species Group 2 Large Class 4 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup2LargeDecayClass4");

  /**Detailed substrate - species group 2 large class 5 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2LargeClass5GapCutLog = new ModelFloat(0,
      "Species Group 2 Large Class 5 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup2LargeDecayClass5");

  /**Detailed substrate - species group 3 large class 1 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3LargeClass1GapCutLog = new ModelFloat(0,
      "Species Group 3 Large Class 1 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup3LargeDecayClass1");

  /**Detailed substrate - species group 3 large class 2 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3LargeClass2GapCutLog = new ModelFloat(0,
      "Species Group 3 Large Class 2 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup3LargeDecayClass2");

  /**Detailed substrate - species group 3 large class 3 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3LargeClass3GapCutLog = new ModelFloat(0,
      "Species Group 3 Large Class 3 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup3LargeDecayClass3");

  /**Detailed substrate - species group 3 large class 4 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3LargeClass4GapCutLog = new ModelFloat(0,
      "Species Group 3 Large Class 4 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup3LargeDecayClass4");

  /**Detailed substrate - species group 3 large class 5 gap cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3LargeClass5GapCutLog = new ModelFloat(0,
      "Species Group 3 Large Class 5 Gap Cut Log (0-1)",
      "su_gapCutLogSpGroup3LargeDecayClass5");

  /**Detailed substrate - species group 1 small class 1 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1SmallClass1ClearCutLog = new ModelFloat(0,
      "Species Group 1 Small Class 1 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup1SmallDecayClass1");

  /**Detailed substrate - species group 1 small class 2 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1SmallClass2ClearCutLog = new ModelFloat(0,
      "Species Group 1 Small Class 2 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup1SmallDecayClass2");

  /**Detailed substrate - species group 1 small class 3 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1SmallClass3ClearCutLog = new ModelFloat(0,
      "Species Group 1 Small Class 3 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup1SmallDecayClass3");

  /**Detailed substrate - species group 1 small class 4 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1SmallClass4ClearCutLog = new ModelFloat(0,
      "Species Group 1 Small Class 4 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup1SmallDecayClass4");

  /**Detailed substrate - species group 1 small class 5 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1SmallClass5ClearCutLog = new ModelFloat(0,
      "Species Group 1 Small Class 5 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup1SmallDecayClass5");

  /**Detailed substrate - species group 2 small class 1 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2SmallClass1ClearCutLog = new ModelFloat(0,
      "Species Group 2 Small Class 1 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup2SmallDecayClass1");

  /**Detailed substrate - species group 2 small class 2 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2SmallClass2ClearCutLog = new ModelFloat(0,
      "Species Group 2 Small Class 2 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup2SmallDecayClass2");

  /**Detailed substrate - species group 2 small class 3 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2SmallClass3ClearCutLog = new ModelFloat(0,
      "Species Group 2 Small Class 3 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup2SmallDecayClass3");

  /**Detailed substrate - species group 2 small class 4 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2SmallClass4ClearCutLog = new ModelFloat(0,
      "Species Group 2 Small Class 4 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup2SmallDecayClass4");

  /**Detailed substrate - species group 2 small class 5 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2SmallClass5ClearCutLog = new ModelFloat(0,
      "Species Group 2 Small Class 5 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup2SmallDecayClass5");

  /**Detailed substrate - species group 3 small class 1 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3SmallClass1ClearCutLog = new ModelFloat(0,
      "Species Group 3 Small Class 1 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup3SmallDecayClass1");

  /**Detailed substrate - species group 3 small class 2 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3SmallClass2ClearCutLog = new ModelFloat(0,
      "Species Group 3 Small Class 2 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup3SmallDecayClass2");

  /**Detailed substrate - species group 3 small class 3 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3SmallClass3ClearCutLog = new ModelFloat(0,
      "Species Group 3 Small Class 3 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup3SmallDecayClass3");

  /**Detailed substrate - species group 3 small class 4 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3SmallClass4ClearCutLog = new ModelFloat(0,
      "Species Group 3 Small Class 4 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup3SmallDecayClass4");

  /**Detailed substrate - species group 3 small class 5 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3SmallClass5ClearCutLog = new ModelFloat(0,
      "Species Group 3 Small Class 5 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup3SmallDecayClass5");

  /**Detailed substrate - species group 1 large class 1 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1LargeClass1ClearCutLog = new ModelFloat(0,
      "Species Group 1 Large Class 1 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup1LargeDecayClass1");

  /**Detailed substrate - species group 1 large class 2 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1LargeClass2ClearCutLog = new ModelFloat(0,
      "Species Group 1 Large Class 2 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup1LargeDecayClass2");

  /**Detailed substrate - species group 1 large class 3 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1LargeClass3ClearCutLog = new ModelFloat(0,
      "Species Group 1 Large Class 3 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup1LargeDecayClass3");

  /**Detailed substrate - species group 1 large class 4 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1LargeClass4ClearCutLog = new ModelFloat(0,
      "Species Group 1 Large Class 4 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup1LargeDecayClass4");

  /**Detailed substrate - species group 1 large class 5 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup1LargeClass5ClearCutLog = new ModelFloat(0,
      "Species Group 1 Large Class 5 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup1LargeDecayClass5");

  /**Detailed substrate - species group 2 large class 1 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2LargeClass1ClearCutLog = new ModelFloat(0,
      "Species Group 2 Large Class 1 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup2LargeDecayClass1");

  /**Detailed substrate - species group 2 large class 2 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2LargeClass2ClearCutLog = new ModelFloat(0,
      "Species Group 2 Large Class 2 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup2LargeDecayClass2");

  /**Detailed substrate - species group 2 large class 3 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2LargeClass3ClearCutLog = new ModelFloat(0,
      "Species Group 2 Large Class 3 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup2LargeDecayClass3");

  /**Detailed substrate - species group 2 large class 4 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2LargeClass4ClearCutLog = new ModelFloat(0,
      "Species Group 2 Large Class 4 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup2LargeDecayClass4");

  /**Detailed substrate - species group 2 large class 5 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup2LargeClass5ClearCutLog = new ModelFloat(0,
      "Species Group 2 Large Class 5 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup2LargeDecayClass5");

  /**Detailed substrate - species group 3 large class 1 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3LargeClass1ClearCutLog = new ModelFloat(0,
      "Species Group 3 Large Class 1 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup3LargeDecayClass1");

  /**Detailed substrate - species group 3 large class 2 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3LargeClass2ClearCutLog = new ModelFloat(0,
      "Species Group 3 Large Class 2 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup3LargeDecayClass2");

  /**Detailed substrate - species group 3 large class 3 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3LargeClass3ClearCutLog = new ModelFloat(0,
      "Species Group 3 Large Class 3 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup3LargeDecayClass3");

  /**Detailed substrate - species group 3 large class 4 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3LargeClass4ClearCutLog = new ModelFloat(0,
      "Species Group 3 Large Class 4 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup3LargeDecayClass4");

  /**Detailed substrate - species group 3 large class 5 clear cut log
   * proportion*/
  protected ModelFloat m_fSpGroup3LargeClass5ClearCutLog = new ModelFloat(0,
      "Species Group 3 Large Class 5 Clear Cut Log (0-1)",
      "su_clearCutLogSpGroup3LargeDecayClass5");

  /**Detailed substrate - mean diameter of small logs at initial conditions*/
  protected ModelFloat m_fInitSmallLogMeanDiam = new ModelFloat(0,
      "Initial Small Logs Mean Diameter (cm)", "su_initialSmallLogMeanDiameter");

  /**Detailed substrate - mean diameter of large logs at initial conditions*/
  protected ModelFloat m_fInitLargeLogMeanDiam = new ModelFloat(0,
      "Initial Large Logs Mean Diameter (cm)", "su_initialLargeLogMeanDiameter");

  /**Detailed substrate - mean diameter of small logs for partial cuts*/
  protected ModelFloat m_fPartCutSmallLogMeanDiam = new ModelFloat(0,
      "Partial Cut Small Logs Mean Diameter (cm)", 
      "su_partialCutSmallLogMeanDiameter");

  /**Detailed substrate - mean diameter of large logs for partial cuts*/
  protected ModelFloat m_fPartCutLargeLogMeanDiam = new ModelFloat(0,
      "Partial Cut Large Logs Mean Diameter (cm)", 
      "su_partialCutLargeLogMeanDiameter");

  /**Detailed substrate - mean diameter of small logs for gap cuts*/
  protected ModelFloat m_fGapCutSmallLogMeanDiam = new ModelFloat(0,
      "Gap Cut Small Logs Mean Diameter (cm)", "su_gapCutSmallLogMeanDiameter");

  /**Detailed substrate - mean diameter of large logs for gap cuts*/
  protected ModelFloat m_fGapCutLargeLogMeanDiam = new ModelFloat(0,
      "Gap Cut Large Logs Mean Diameter (cm)", "su_gapCutLargeLogMeanDiameter");

  /**Detailed substrate - mean diameter of small logs for clear cuts*/
  protected ModelFloat m_fClearCutSmallLogMeanDiam = new ModelFloat(0,
      "Clear Cut Small Logs Mean Diameter (cm)", "su_clearCutSmallLogMeanDiameter");

  /**Detailed substrate - mean diameter of large logs for clear cuts*/
  protected ModelFloat m_fClearCutLargeLogMeanDiam = new ModelFloat(0,
      "Clear Cut Large Logs Mean Diameter (cm)", "su_clearCutLargeLogMeanDiameter");

  /**Substrate - Substrate - Scarified soil decay alpha*/
  protected ModelFloat m_fScarSoilA = new ModelFloat(0, 
      "Scarified Soil Annual Decay Alpha", "su_scarSoilDecayAlpha");

  /**Substrate - Scarified soil decay beta*/
  protected ModelFloat m_fScarSoilB = new ModelFloat(0,
      "Scarified Soil Annual Decay Beta",
      "su_scarSoilDecayBeta");

  /**Substrate - Tip-Up Mounds decay alpha*/
  protected ModelFloat m_fTipUpA = new ModelFloat(0,
      "Tip-Up Mounds Annual Decay Alpha",
      "su_tipupDecayAlpha");

  /**Substrate - Tip-Up Mounds decay beta*/
  protected ModelFloat m_fTipUpB = new ModelFloat(0,
      "Tip-Up Mounds Annual Decay Beta",
      "su_tipupDecayBeta");

  /**Substrate - Scarified soil initial condition proportion*/
  protected ModelFloat m_fInitCondScarSoil = new ModelFloat(0,
      "Initial Conditions Proportion of Scarified Soil", "su_initialScarSoil");

  /**Substrate - Scarified soil partial cut proportion*/
  protected ModelFloat m_fPartialCutScarSoil = new ModelFloat(0,
      "Partial Cut Proportion of Scarified Soil",
      "su_partialCutScarSoil");

  /**Substrate - Scarified soil gap cut proportion*/
  protected ModelFloat m_fGapCutScarSoil = new ModelFloat(0,
      "Gap Cut Proportion of Scarified Soil",
      "su_gapCutScarSoil");

  /**Substrate - Scarified soil clear cut proportion*/
  protected ModelFloat m_fClearCutScarSoil = new ModelFloat(0,
      "Clear Cut Proportion of Scarified Soil",
      "su_clearCutScarSoil");

  /**Substrate - Tip-Up Mounds initial condition proportion*/
  protected ModelFloat m_fInitCondTipup = new ModelFloat(0,
      "Initial Conditions Proportion of Tip-Up Mounds", "su_initialTipup");

  /**Substrate - Tip-Up Mounds partial cut proportion*/
  protected ModelFloat m_fPartialCutTipup = new ModelFloat(0,
      "Partial Cut Proportion of Tip-Up Mounds",
      "su_partialCutTipup");

  /**Substrate - Tip-Up Mounds gap cut proportion*/
  protected ModelFloat m_fGapCutTipup = new ModelFloat(0,
      "Gap Cut Proportion of Tip-Up Mounds",
      "su_gapCutTipup");

  /**Substrate - Tip-Up Mounds clear cut proportion*/
  protected ModelFloat m_fClearCutTipup = new ModelFloat(0,
      "Clear Cut Proportion of Tip-Up Mounds",
      "su_clearCutTipup");

  /**Substrate - Root soil disturbance factor*/
  protected ModelFloat m_fRootTipupFactor = new ModelFloat(0,
      "Uprooted Tree Radius Increase Factor for Root Rip-Out",
      "su_rootTipupFactor");

  /**Substrate - Proportion of litter/moss that is moss*/
  protected ModelFloat m_fMossProportion = new ModelFloat(0,
      "Proportion of Forest Floor Litter/Moss Pool that is Moss",
      "su_mossProportion");

  /**Substrate - Whether or not tree fall is directional - LEM 06/03/05*/
  protected ModelEnum m_iDirectionalTreeFall =
      new ModelEnum(new int[] {0, 1},
          new String[] {"false", "true"},
          "Use Directional Tree Fall",
          "su_directionalTreeFall");

  /**Substrate - Maximum number of years a substrate event hangs around*/
  protected ModelInt m_iMaxDecayTime = new ModelInt(10,
      "Maximum Number of Years that Decay Occurs",
      "su_maxNumberDecayYears");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public DetailedSubstrate(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "substrate_behaviors.detailed_substrate");

    //Substrate cannot apply to seedlings
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);

    addRequiredData(mp_iSpeciesGroup);
    addRequiredData(mp_fProportionOfFallenThatUproot);
    addRequiredData(mp_fProportionOfSnagsThatUproot);
    addRequiredData(m_fRootTipupFactor);
    addRequiredData(m_fScarSoilA);
    addRequiredData(m_fScarSoilB);
    addRequiredData(m_fTipUpA);
    addRequiredData(m_fTipUpB);
    addRequiredData(m_iMaxDecayTime);
    addRequiredData(m_fMossProportion);
    addRequiredData(m_iDirectionalTreeFall);
    addRequiredData(m_fInitCondScarSoil);
    addRequiredData(m_fInitCondTipup);
    addRequiredData(m_fLogSizeClassBoundary);
    addRequiredData(m_fPropFallenTreesDecayClass1);
    addRequiredData(m_fPropFallenTreesDecayClass2);
    addRequiredData(m_fPropFallenTreesDecayClass3);
    addRequiredData(m_fPropFallenTreesDecayClass4);
    addRequiredData(m_fPropFallenTreesDecayClass5);
    addRequiredData(m_fPropFallenSnagsDecayClass1);
    addRequiredData(m_fPropFallenSnagsDecayClass2);
    addRequiredData(m_fPropFallenSnagsDecayClass3);
    addRequiredData(m_fPropFallenSnagsDecayClass4);
    addRequiredData(m_fPropFallenSnagsDecayClass5);
    addRequiredData(m_fSpGroup1SmallClass1Alpha);
    addRequiredData(m_fSpGroup1SmallClass2Alpha);
    addRequiredData(m_fSpGroup1SmallClass3Alpha);
    addRequiredData(m_fSpGroup1SmallClass4Alpha);
    addRequiredData(m_fSpGroup1SmallClass5Alpha);
    addRequiredData(m_fSpGroup2SmallClass1Alpha);
    addRequiredData(m_fSpGroup2SmallClass2Alpha);
    addRequiredData(m_fSpGroup2SmallClass3Alpha);
    addRequiredData(m_fSpGroup2SmallClass4Alpha);
    addRequiredData(m_fSpGroup2SmallClass5Alpha);
    addRequiredData(m_fSpGroup3SmallClass1Alpha);
    addRequiredData(m_fSpGroup3SmallClass2Alpha);
    addRequiredData(m_fSpGroup3SmallClass3Alpha);
    addRequiredData(m_fSpGroup3SmallClass4Alpha);
    addRequiredData(m_fSpGroup3SmallClass5Alpha);
    addRequiredData(m_fSpGroup1LargeClass1Alpha);
    addRequiredData(m_fSpGroup1LargeClass2Alpha);
    addRequiredData(m_fSpGroup1LargeClass3Alpha);
    addRequiredData(m_fSpGroup1LargeClass4Alpha);
    addRequiredData(m_fSpGroup1LargeClass5Alpha);
    addRequiredData(m_fSpGroup2LargeClass1Alpha);
    addRequiredData(m_fSpGroup2LargeClass2Alpha);
    addRequiredData(m_fSpGroup2LargeClass3Alpha);
    addRequiredData(m_fSpGroup2LargeClass4Alpha);
    addRequiredData(m_fSpGroup2LargeClass5Alpha);
    addRequiredData(m_fSpGroup3LargeClass1Alpha);
    addRequiredData(m_fSpGroup3LargeClass2Alpha);
    addRequiredData(m_fSpGroup3LargeClass3Alpha);
    addRequiredData(m_fSpGroup3LargeClass4Alpha);
    addRequiredData(m_fSpGroup3LargeClass5Alpha);
    addRequiredData(m_fSpGroup1SmallClass1Beta);
    addRequiredData(m_fSpGroup1SmallClass2Beta);
    addRequiredData(m_fSpGroup1SmallClass3Beta);
    addRequiredData(m_fSpGroup1SmallClass4Beta);
    addRequiredData(m_fSpGroup1SmallClass5Beta);
    addRequiredData(m_fSpGroup2SmallClass1Beta);
    addRequiredData(m_fSpGroup2SmallClass2Beta);
    addRequiredData(m_fSpGroup2SmallClass3Beta);
    addRequiredData(m_fSpGroup2SmallClass4Beta);
    addRequiredData(m_fSpGroup2SmallClass5Beta);
    addRequiredData(m_fSpGroup3SmallClass1Beta);
    addRequiredData(m_fSpGroup3SmallClass2Beta);
    addRequiredData(m_fSpGroup3SmallClass3Beta);
    addRequiredData(m_fSpGroup3SmallClass4Beta);
    addRequiredData(m_fSpGroup3SmallClass5Beta);
    addRequiredData(m_fSpGroup1LargeClass1Beta);
    addRequiredData(m_fSpGroup1LargeClass2Beta);
    addRequiredData(m_fSpGroup1LargeClass3Beta);
    addRequiredData(m_fSpGroup1LargeClass4Beta);
    addRequiredData(m_fSpGroup1LargeClass5Beta);
    addRequiredData(m_fSpGroup2LargeClass1Beta);
    addRequiredData(m_fSpGroup2LargeClass2Beta);
    addRequiredData(m_fSpGroup2LargeClass3Beta);
    addRequiredData(m_fSpGroup2LargeClass4Beta);
    addRequiredData(m_fSpGroup2LargeClass5Beta);
    addRequiredData(m_fSpGroup3LargeClass1Beta);
    addRequiredData(m_fSpGroup3LargeClass2Beta);
    addRequiredData(m_fSpGroup3LargeClass3Beta);
    addRequiredData(m_fSpGroup3LargeClass4Beta);
    addRequiredData(m_fSpGroup3LargeClass5Beta);
    addRequiredData(m_fInitSmallLogMeanDiam);
    addRequiredData(m_fInitLargeLogMeanDiam);
    addRequiredData(m_fSpGroup1SmallClass1InitLog);
    addRequiredData(m_fSpGroup1SmallClass2InitLog);
    addRequiredData(m_fSpGroup1SmallClass3InitLog);
    addRequiredData(m_fSpGroup1SmallClass4InitLog);
    addRequiredData(m_fSpGroup1SmallClass5InitLog);
    addRequiredData(m_fSpGroup2SmallClass1InitLog);
    addRequiredData(m_fSpGroup2SmallClass2InitLog);
    addRequiredData(m_fSpGroup2SmallClass3InitLog);
    addRequiredData(m_fSpGroup2SmallClass4InitLog);
    addRequiredData(m_fSpGroup2SmallClass5InitLog);
    addRequiredData(m_fSpGroup3SmallClass1InitLog);
    addRequiredData(m_fSpGroup3SmallClass2InitLog);
    addRequiredData(m_fSpGroup3SmallClass3InitLog);
    addRequiredData(m_fSpGroup3SmallClass4InitLog);
    addRequiredData(m_fSpGroup3SmallClass5InitLog);
    addRequiredData(m_fSpGroup1LargeClass1InitLog);
    addRequiredData(m_fSpGroup1LargeClass2InitLog);
    addRequiredData(m_fSpGroup1LargeClass3InitLog);
    addRequiredData(m_fSpGroup1LargeClass4InitLog);
    addRequiredData(m_fSpGroup1LargeClass5InitLog);
    addRequiredData(m_fSpGroup2LargeClass1InitLog);
    addRequiredData(m_fSpGroup2LargeClass2InitLog);
    addRequiredData(m_fSpGroup2LargeClass3InitLog);
    addRequiredData(m_fSpGroup2LargeClass4InitLog);
    addRequiredData(m_fSpGroup2LargeClass5InitLog);
    addRequiredData(m_fSpGroup3LargeClass1InitLog);
    addRequiredData(m_fSpGroup3LargeClass2InitLog);
    addRequiredData(m_fSpGroup3LargeClass3InitLog);
    addRequiredData(m_fSpGroup3LargeClass4InitLog);
    addRequiredData(m_fSpGroup3LargeClass5InitLog);
    addRequiredData(m_fPartialCutScarSoil);
    addRequiredData(m_fPartialCutTipup);
    addRequiredData(m_fSpGroup1SmallClass1PartCutLog);
    addRequiredData(m_fSpGroup1SmallClass2PartCutLog);
    addRequiredData(m_fSpGroup1SmallClass3PartCutLog);
    addRequiredData(m_fSpGroup1SmallClass4PartCutLog);
    addRequiredData(m_fSpGroup1SmallClass5PartCutLog);
    addRequiredData(m_fSpGroup2SmallClass1PartCutLog);
    addRequiredData(m_fSpGroup2SmallClass2PartCutLog);
    addRequiredData(m_fSpGroup2SmallClass3PartCutLog);
    addRequiredData(m_fSpGroup2SmallClass4PartCutLog);
    addRequiredData(m_fSpGroup2SmallClass5PartCutLog);
    addRequiredData(m_fSpGroup3SmallClass1PartCutLog);
    addRequiredData(m_fSpGroup3SmallClass2PartCutLog);
    addRequiredData(m_fSpGroup3SmallClass3PartCutLog);
    addRequiredData(m_fSpGroup3SmallClass4PartCutLog);
    addRequiredData(m_fSpGroup3SmallClass5PartCutLog);
    addRequiredData(m_fSpGroup1LargeClass1PartCutLog);
    addRequiredData(m_fSpGroup1LargeClass2PartCutLog);
    addRequiredData(m_fSpGroup1LargeClass3PartCutLog);
    addRequiredData(m_fSpGroup1LargeClass4PartCutLog);
    addRequiredData(m_fSpGroup1LargeClass5PartCutLog);
    addRequiredData(m_fSpGroup2LargeClass1PartCutLog);
    addRequiredData(m_fSpGroup2LargeClass2PartCutLog);
    addRequiredData(m_fSpGroup2LargeClass3PartCutLog);
    addRequiredData(m_fSpGroup2LargeClass4PartCutLog);
    addRequiredData(m_fSpGroup2LargeClass5PartCutLog);
    addRequiredData(m_fSpGroup3LargeClass1PartCutLog);
    addRequiredData(m_fSpGroup3LargeClass2PartCutLog);
    addRequiredData(m_fSpGroup3LargeClass3PartCutLog);
    addRequiredData(m_fSpGroup3LargeClass4PartCutLog);
    addRequiredData(m_fSpGroup3LargeClass5PartCutLog);
    addRequiredData(m_fGapCutScarSoil);
    addRequiredData(m_fGapCutTipup);
    addRequiredData(m_fSpGroup1SmallClass1GapCutLog);
    addRequiredData(m_fSpGroup1SmallClass2GapCutLog);
    addRequiredData(m_fSpGroup1SmallClass3GapCutLog);
    addRequiredData(m_fSpGroup1SmallClass4GapCutLog);
    addRequiredData(m_fSpGroup1SmallClass5GapCutLog);
    addRequiredData(m_fSpGroup2SmallClass1GapCutLog);
    addRequiredData(m_fSpGroup2SmallClass2GapCutLog);
    addRequiredData(m_fSpGroup2SmallClass3GapCutLog);
    addRequiredData(m_fSpGroup2SmallClass4GapCutLog);
    addRequiredData(m_fSpGroup2SmallClass5GapCutLog);
    addRequiredData(m_fSpGroup3SmallClass1GapCutLog);
    addRequiredData(m_fSpGroup3SmallClass2GapCutLog);
    addRequiredData(m_fSpGroup3SmallClass3GapCutLog);
    addRequiredData(m_fSpGroup3SmallClass4GapCutLog);
    addRequiredData(m_fSpGroup3SmallClass5GapCutLog);
    addRequiredData(m_fSpGroup1LargeClass1GapCutLog);
    addRequiredData(m_fSpGroup1LargeClass2GapCutLog);
    addRequiredData(m_fSpGroup1LargeClass3GapCutLog);
    addRequiredData(m_fSpGroup1LargeClass4GapCutLog);
    addRequiredData(m_fSpGroup1LargeClass5GapCutLog);
    addRequiredData(m_fSpGroup2LargeClass1GapCutLog);
    addRequiredData(m_fSpGroup2LargeClass2GapCutLog);
    addRequiredData(m_fSpGroup2LargeClass3GapCutLog);
    addRequiredData(m_fSpGroup2LargeClass4GapCutLog);
    addRequiredData(m_fSpGroup2LargeClass5GapCutLog);
    addRequiredData(m_fSpGroup3LargeClass1GapCutLog);
    addRequiredData(m_fSpGroup3LargeClass2GapCutLog);
    addRequiredData(m_fSpGroup3LargeClass3GapCutLog);
    addRequiredData(m_fSpGroup3LargeClass4GapCutLog);
    addRequiredData(m_fSpGroup3LargeClass5GapCutLog);
    addRequiredData(m_fClearCutScarSoil);
    addRequiredData(m_fClearCutTipup);
    addRequiredData(m_fSpGroup1SmallClass1ClearCutLog);
    addRequiredData(m_fSpGroup1SmallClass2ClearCutLog);
    addRequiredData(m_fSpGroup1SmallClass3ClearCutLog);
    addRequiredData(m_fSpGroup1SmallClass4ClearCutLog);
    addRequiredData(m_fSpGroup1SmallClass5ClearCutLog);
    addRequiredData(m_fSpGroup2SmallClass1ClearCutLog);
    addRequiredData(m_fSpGroup2SmallClass2ClearCutLog);
    addRequiredData(m_fSpGroup2SmallClass3ClearCutLog);
    addRequiredData(m_fSpGroup2SmallClass4ClearCutLog);
    addRequiredData(m_fSpGroup2SmallClass5ClearCutLog);
    addRequiredData(m_fSpGroup3SmallClass1ClearCutLog);
    addRequiredData(m_fSpGroup3SmallClass2ClearCutLog);
    addRequiredData(m_fSpGroup3SmallClass3ClearCutLog);
    addRequiredData(m_fSpGroup3SmallClass4ClearCutLog);
    addRequiredData(m_fSpGroup3SmallClass5ClearCutLog);
    addRequiredData(m_fSpGroup1LargeClass1ClearCutLog);
    addRequiredData(m_fSpGroup1LargeClass2ClearCutLog);
    addRequiredData(m_fSpGroup1LargeClass3ClearCutLog);
    addRequiredData(m_fSpGroup1LargeClass4ClearCutLog);
    addRequiredData(m_fSpGroup1LargeClass5ClearCutLog);
    addRequiredData(m_fSpGroup2LargeClass1ClearCutLog);
    addRequiredData(m_fSpGroup2LargeClass2ClearCutLog);
    addRequiredData(m_fSpGroup2LargeClass3ClearCutLog);
    addRequiredData(m_fSpGroup2LargeClass4ClearCutLog);
    addRequiredData(m_fSpGroup2LargeClass5ClearCutLog);
    addRequiredData(m_fSpGroup3LargeClass1ClearCutLog);
    addRequiredData(m_fSpGroup3LargeClass2ClearCutLog);
    addRequiredData(m_fSpGroup3LargeClass3ClearCutLog);
    addRequiredData(m_fSpGroup3LargeClass4ClearCutLog);
    addRequiredData(m_fSpGroup3LargeClass5ClearCutLog);
    addRequiredData(m_fPartCutSmallLogMeanDiam);
    addRequiredData(m_fPartCutLargeLogMeanDiam);
    addRequiredData(m_fGapCutSmallLogMeanDiam);
    addRequiredData(m_fGapCutLargeLogMeanDiam);
    addRequiredData(m_fClearCutSmallLogMeanDiam);
    addRequiredData(m_fClearCutLargeLogMeanDiam);
    
    // Defaults
    //Set the proportion of snags that uproot to all 0s, in case snags aren't
    //being used
    TreePopulation oPop = m_oManager.getTreePopulation();
    int iNumSpecies = oPop.getNumberOfSpecies(), i;
    if (mp_fProportionOfSnagsThatUproot.getValue().size() == 0) {
      for (i = 0; i < iNumSpecies; i++) {
        mp_fProportionOfSnagsThatUproot.getValue().add(new Float(0));
      }
    }

    //Detailed substrate setup
    //Set up the species group vector - values display in reverse order
    ModelEnum oVal;
    for (i = 0; i < iNumSpecies; i++) {
      oVal = new ModelEnum(new int[] {3, 2, 1}, new String[] {"3", "2", "1"}, "", "");
      oVal.setValue(1);
      mp_iSpeciesGroup.getValue().add(oVal);
    }
    
    //This will get overwritten but will be here in case of grid maps
    gridSetup();
  }

  /**
   * Sets up the substrate grids.
   * @throws ModelException
   */
  private void gridSetup() throws ModelException {

    Grid oNewGrid;
    int i, j, k, iCount;
    int iDecayTimesteps = (int) java.lang.Math.ceil(  m_iMaxDecayTime.
        getValue() / m_oManager.getPlot().getNumberOfYearsPerTimestep());

    //************************
    //Detailed substrate grid
    //************************
    String sGridName = "DetailedSubstrate";

    //Create the six data members
    DataMember[] p_oDataMembers = new DataMember[66];

    p_oDataMembers[0] = new DataMember("Proportion of scarified soil",
        "scarsoil",
        DataMember.FLOAT);
    p_oDataMembers[1] = new DataMember("Proportion of tip-up mounds",
        "tipup", DataMember.FLOAT);
    p_oDataMembers[2] = new DataMember("Proportion of forest floor moss",
        "ffmoss",
        DataMember.FLOAT);
    p_oDataMembers[3] = new DataMember("Proportion of forest floor litter",
        "fflitter",
        DataMember.FLOAT);
    p_oDataMembers[4] = new DataMember("Total Log", "totallog", DataMember.FLOAT);
    p_oDataMembers[5] = new DataMember("Total Log Volume", "totallogvol", DataMember.FLOAT);
    iCount = 6;
    for (i = 1; i <= 5; i++) { //decay class
      for (j = 1; j <= 3; j++) { //species group
        p_oDataMembers[iCount] = new DataMember(
            "Prop Sp Group " + j + " Small Decay " + i, 
            "loggroup" + j + "smalldecay" + i, DataMember.FLOAT);         
        iCount++;
        p_oDataMembers[iCount] = new DataMember(
            "Prop Sp Group " + j + " Large Decay " + i, 
            "loggroup" + j + "largedecay" + i, DataMember.FLOAT);         
        iCount++;
        p_oDataMembers[iCount] = new DataMember(
            "Vol Sp Group " + j + " Small Decay " + i, 
            "vloggroup" + j + "smalldecay" + i, DataMember.FLOAT);        
        iCount++;
        p_oDataMembers[iCount] = new DataMember(
            "Vol Sp Group " + j + " Large Decay " + i, 
            "vloggroup" + j + "largedecay" + i, DataMember.FLOAT);        
        iCount++;
      }
    }

    DataMember[] p_oPackageDataMembers = new DataMember[3];
    p_oPackageDataMembers[0] = new DataMember("Substrate cohort age", "age",
        DataMember.INTEGER);
    p_oPackageDataMembers[1] = new DataMember(
        "Substrate cohort new scarified soil substrate", "scarsoil",
        DataMember.FLOAT);
    p_oPackageDataMembers[2] = new DataMember(
        "Substrate cohort new tip-up mounds substrate",
        "tipup", DataMember.FLOAT);

    oNewGrid = new Grid(sGridName, p_oDataMembers, p_oPackageDataMembers, 8, 8);

    //Add to the detailed substrate behavior
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid, false);

    //************************
    // Detailed substrate calculations grid
    //************************
    sGridName = "detailedsubstratecalcs";

    //Create the data members
    p_oDataMembers = new DataMember[31 + (30*iDecayTimesteps)];

    p_oDataMembers[0] = new DataMember("Amount of new tip-up mounds",
        "newtipup",
        DataMember.FLOAT);

    iCount = 1;
    for (i = 1; i <= 5; i++) { //decay class
      for (j = 1; j <= 3; j++) { //species group
        p_oDataMembers[iCount] = new DataMember(
            "New Log Area Sp Group " + j + " Small Decay " + i, 
            "newloggroup" + j + "smalldecay" + i, DataMember.FLOAT);        
        iCount++;
        p_oDataMembers[iCount] = new DataMember(
            "New Log Area Sp Group " + j + " Large Decay " + i, 
            "newloggroup" + j + "largedecay" + i, DataMember.FLOAT);        
        iCount++;
        for (k = 0; k < iDecayTimesteps; k++) {
          p_oDataMembers[iCount] = new DataMember(
              "Prop Sp Group " + j + " Small Decay " + i + " Timestep " + k, 
              "loggroup" + j + "smalldecay" + i + "_" + k, DataMember.FLOAT);         
          iCount++;
          p_oDataMembers[iCount] = new DataMember(
              "Prop Sp Group " + j + " Large Decay " + i + " Timestep " + k, 
              "loggroup" + j + "largedecay" + i + "_" + k, DataMember.FLOAT);         
          iCount++;
        }
      }
    }
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);
    //Add to the substrate behavior
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid, false);
  }

  /**
   * Overridden from WorkerBase.  This is on the lookout for changes to
   * m_iMaxDecayTime, so gridSetup() can be triggered and the grids recreated.
   * @param oDataMember Data member to set.
   * @param oData Data value to set into data member.
   * @throws ModelException if data is of the wrong type.
   */
  protected void setSingleValue(ModelData oDataMember, Object oData) throws
  ModelException {
    super.setSingleValue(oDataMember, oData);
    if (oDataMember.equals(m_iMaxDecayTime)) {
      gridSetup();
    }
  }

  /**
   * Validates the data in preparation for parameter file writing or some such.
   * @throws ModelException if:
   * <ul>
   * <li>m_fRootTipupFactor or m_iMaxDecayTime are not positive numbers</li>
   * <li>if the values in mp_fProportionOfDeadThatFall,
   * mp_fProportionOfFallenThatUproot, and mp_fProportionOfSnagsThatUproot are
   * not proportions</li>
   * <li>m_fMossProportion isn't a proportion</li>
   * <li>any initial or harvest conditions value isn't a proportion</li>
   * <li>the total of any set of initial or harvest conditions is greater than
   * 1</li>
   * <li>Detailed substrate is enabled and:
   * <li>Detailed substrate betas are too large</li>
   * <li>Detailed substrate alphas are positive</li>
   * <li>Initial conditions proportions aren't less than 1</li>
   * <li>Partial cut conditions proportions aren't less than 1</li>
   * <li>Gap cut conditions proportions aren't less than 1</li>
   * <li>Clear cut conditions proportions aren't less than 1</li>
   * <li>Live tree decay class proportions don't add up to 1</li>
   * <li>Snag decay class proportions don't add up to 1</li>
   * <li>Mean small and large log diameters don't straddle the log size class
   * boundary</li>
   * <li>Snag dynamics behavior is not enabled</li>
   * </li>
   * </ul>
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {

    boolean[] p_bApplies = getWhichSpeciesUsed(oPop);

    ValidationHelpers.makeSureAllAreProportions(mp_fProportionOfFallenThatUproot, p_bApplies);
    ValidationHelpers.makeSureAllAreProportions(mp_fProportionOfSnagsThatUproot, p_bApplies);
    ValidationHelpers.makeSureGreaterThan(m_fRootTipupFactor, 0);
    ValidationHelpers.makeSureGreaterThan(m_iMaxDecayTime, 0);
    ValidationHelpers.makeSureIsProportion(m_fMossProportion);
    ValidationHelpers.makeSureIsProportion(m_fInitCondScarSoil);
    ValidationHelpers.makeSureIsProportion(m_fInitCondTipup);
    ValidationHelpers.makeSureIsProportion(m_fPartialCutScarSoil);
    ValidationHelpers.makeSureIsProportion(m_fPartialCutTipup);
    ValidationHelpers.makeSureIsProportion(m_fGapCutScarSoil);
    ValidationHelpers.makeSureIsProportion(m_fGapCutTipup);
    ValidationHelpers.makeSureIsProportion(m_fClearCutScarSoil);
    ValidationHelpers.makeSureIsProportion(m_fClearCutTipup);

    //Check to see species groups
    ModelEnum oEnum;
    boolean bAnyGroup1 = false, bAnyGroup2 = false, bAnyGroup3 = false;
    for (int i = 0; i < mp_iSpeciesGroup.getValue().size(); i++) {
      oEnum = (ModelEnum) mp_iSpeciesGroup.getValue().get(i);
      if (oEnum.getValue() == 1) bAnyGroup1 = true;
      else if (oEnum.getValue() == 2) bAnyGroup2 = true;
      else if (oEnum.getValue() == 3) bAnyGroup3 = true;
    }

    //Check betas to make sure they are not too large and that the 
    //alpha-beta pair won't be too large to raise e to the power of it
    float fMaxBeta = (float)(30.0 / java.lang.Math.log(m_iMaxDecayTime.getValue()));
    ValidationHelpers.makeSureLessThan(m_fScarSoilB, fMaxBeta);
    ValidationHelpers.makeSureLessThan(m_fTipUpB, fMaxBeta);

    if (m_fScarSoilA.getValue() * 
        java.lang.Math.pow(m_iMaxDecayTime.getValue(),m_fScarSoilB.getValue()) < -30.0) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
              "following parameters: " + m_fScarSoilA.getDescriptor() + ", " + 
              m_fScarSoilB.getDescriptor());
    }

    if (m_fTipUpA.getValue() * 
        java.lang.Math.pow(m_iMaxDecayTime.getValue(),m_fTipUpB.getValue()) < -30.0) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
              "following parameters: " + m_fTipUpA.getDescriptor() + ", " + 
              m_fTipUpB.getDescriptor());
    }
    if (bAnyGroup1) {
      ValidationHelpers.makeSureLessThan(m_fSpGroup1SmallClass1Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1SmallClass2Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1SmallClass3Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1SmallClass4Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1SmallClass5Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1LargeClass1Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1LargeClass2Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1LargeClass3Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1LargeClass4Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1LargeClass5Beta, fMaxBeta);

      if (m_fSpGroup1SmallClass1Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup1SmallClass1Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup1SmallClass1Alpha.getDescriptor() + ", " + 
                m_fSpGroup1SmallClass1Beta.getDescriptor());
      }

      if (m_fSpGroup1SmallClass2Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup1SmallClass2Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup1SmallClass2Alpha.getDescriptor() + ", " + 
                m_fSpGroup1SmallClass2Beta.getDescriptor());
      }

      if (m_fSpGroup1SmallClass3Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup1SmallClass3Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup1SmallClass3Alpha.getDescriptor() + ", " + 
                m_fSpGroup1SmallClass3Beta.getDescriptor());
      }

      if (m_fSpGroup1SmallClass4Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup1SmallClass4Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup1SmallClass4Alpha.getDescriptor() + ", " + 
                m_fSpGroup1SmallClass4Beta.getDescriptor());
      }

      if (m_fSpGroup1SmallClass5Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup1SmallClass5Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup1SmallClass5Alpha.getDescriptor() + ", " + 
                m_fSpGroup1SmallClass5Beta.getDescriptor());
      }

      if (m_fSpGroup1LargeClass1Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup1LargeClass1Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup1LargeClass1Alpha.getDescriptor() + ", " + 
                m_fSpGroup1LargeClass1Beta.getDescriptor());
      }

      if (m_fSpGroup1LargeClass2Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup1LargeClass2Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup1LargeClass2Alpha.getDescriptor() + ", " + 
                m_fSpGroup1LargeClass2Beta.getDescriptor());
      }

      if (m_fSpGroup1LargeClass3Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup1LargeClass3Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup1LargeClass3Alpha.getDescriptor() + ", " + 
                m_fSpGroup1LargeClass3Beta.getDescriptor());
      }

      if (m_fSpGroup1LargeClass4Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup1LargeClass4Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup1LargeClass4Alpha.getDescriptor() + ", " + 
                m_fSpGroup1LargeClass4Beta.getDescriptor());
      }

      if (m_fSpGroup1LargeClass5Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup1LargeClass5Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup1LargeClass5Alpha.getDescriptor() + ", " + 
                m_fSpGroup1LargeClass5Beta.getDescriptor());
      }
    }
    if (bAnyGroup2) {
      ValidationHelpers.makeSureLessThan(m_fSpGroup2SmallClass1Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2SmallClass2Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2SmallClass3Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2SmallClass4Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2SmallClass5Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2LargeClass1Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2LargeClass2Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2LargeClass3Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2LargeClass4Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2LargeClass5Beta, fMaxBeta);

      if (m_fSpGroup2SmallClass1Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup2SmallClass1Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup2SmallClass1Alpha.getDescriptor() + ", " + 
                m_fSpGroup2SmallClass1Beta.getDescriptor());
      }

      if (m_fSpGroup2SmallClass2Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup2SmallClass2Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup2SmallClass2Alpha.getDescriptor() + ", " + 
                m_fSpGroup2SmallClass2Beta.getDescriptor());
      }

      if (m_fSpGroup2SmallClass3Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup2SmallClass3Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup2SmallClass3Alpha.getDescriptor() + ", " + 
                m_fSpGroup2SmallClass3Beta.getDescriptor());
      }

      if (m_fSpGroup2SmallClass4Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup2SmallClass4Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup2SmallClass4Alpha.getDescriptor() + ", " + 
                m_fSpGroup2SmallClass4Beta.getDescriptor());
      }

      if (m_fSpGroup2SmallClass5Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup2SmallClass5Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup2SmallClass5Alpha.getDescriptor() + ", " + 
                m_fSpGroup2SmallClass5Beta.getDescriptor());
      }

      if (m_fSpGroup2LargeClass1Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup2LargeClass1Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup2LargeClass1Alpha.getDescriptor() + ", " + 
                m_fSpGroup2LargeClass1Beta.getDescriptor());
      }

      if (m_fSpGroup2LargeClass2Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup2LargeClass2Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup2LargeClass2Alpha.getDescriptor() + ", " + 
                m_fSpGroup2LargeClass2Beta.getDescriptor());
      }

      if (m_fSpGroup2LargeClass3Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup2LargeClass3Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup2LargeClass3Alpha.getDescriptor() + ", " + 
                m_fSpGroup2LargeClass3Beta.getDescriptor());
      }

      if (m_fSpGroup2LargeClass4Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup2LargeClass4Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup2LargeClass4Alpha.getDescriptor() + ", " + 
                m_fSpGroup2LargeClass4Beta.getDescriptor());
      }

      if (m_fSpGroup2LargeClass5Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup2LargeClass5Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup2LargeClass5Alpha.getDescriptor() + ", " + 
                m_fSpGroup2LargeClass5Beta.getDescriptor());
      }
    }
    if (bAnyGroup3) {
      ValidationHelpers.makeSureLessThan(m_fSpGroup3SmallClass1Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3SmallClass2Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3SmallClass3Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3SmallClass4Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3SmallClass5Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3LargeClass1Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3LargeClass2Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3LargeClass3Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3LargeClass4Beta, fMaxBeta);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3LargeClass5Beta, fMaxBeta);

      if (m_fSpGroup3SmallClass1Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup3SmallClass1Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup3SmallClass1Alpha.getDescriptor() + ", " + 
                m_fSpGroup3SmallClass1Beta.getDescriptor());
      }

      if (m_fSpGroup3SmallClass2Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup3SmallClass2Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup3SmallClass2Alpha.getDescriptor() + ", " + 
                m_fSpGroup3SmallClass2Beta.getDescriptor());
      }

      if (m_fSpGroup3SmallClass3Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup3SmallClass3Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup3SmallClass3Alpha.getDescriptor() + ", " + 
                m_fSpGroup3SmallClass3Beta.getDescriptor());
      }

      if (m_fSpGroup3SmallClass4Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup3SmallClass4Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup3SmallClass4Alpha.getDescriptor() + ", " + 
                m_fSpGroup3SmallClass4Beta.getDescriptor());
      }

      if (m_fSpGroup3SmallClass5Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup3SmallClass5Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup3SmallClass5Alpha.getDescriptor() + ", " + 
                m_fSpGroup3SmallClass5Beta.getDescriptor());
      }

      if (m_fSpGroup3LargeClass1Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup3LargeClass1Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup3LargeClass1Alpha.getDescriptor() + ", " + 
                m_fSpGroup3LargeClass1Beta.getDescriptor());
      }

      if (m_fSpGroup3LargeClass2Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup3LargeClass2Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup3LargeClass2Alpha.getDescriptor() + ", " + 
                m_fSpGroup3LargeClass2Beta.getDescriptor());
      }

      if (m_fSpGroup3LargeClass3Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup3LargeClass3Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup3LargeClass3Alpha.getDescriptor() + ", " + 
                m_fSpGroup3LargeClass3Beta.getDescriptor());
      }

      if (m_fSpGroup3LargeClass4Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup3LargeClass4Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup3LargeClass4Alpha.getDescriptor() + ", " + 
                m_fSpGroup3LargeClass4Beta.getDescriptor());
      }

      if (m_fSpGroup3LargeClass5Alpha.getValue() * 
          java.lang.Math.pow(m_iMaxDecayTime.getValue(),
              m_fSpGroup3LargeClass5Beta.getValue()) < -30.0) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Alpha * (beta ^ max timesteps) < -30 must be true for the " +
                "following parameters: " + 
                m_fSpGroup3LargeClass5Alpha.getDescriptor() + ", " + 
                m_fSpGroup3LargeClass5Beta.getDescriptor());
      }
    }

    //Check alphas to make sure they are negative
    ValidationHelpers.makeSureLessThan(m_fScarSoilA, 0);
    ValidationHelpers.makeSureLessThan(m_fTipUpA, 0);
    if (bAnyGroup1) {
      ValidationHelpers.makeSureLessThan(m_fSpGroup1SmallClass1Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1SmallClass2Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1SmallClass3Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1SmallClass4Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1SmallClass5Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1LargeClass1Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1LargeClass2Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1LargeClass3Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1LargeClass4Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup1LargeClass5Alpha, 0);
    }
    if (bAnyGroup2) {
      ValidationHelpers.makeSureLessThan(m_fSpGroup2SmallClass1Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2SmallClass2Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2SmallClass3Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2SmallClass4Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2SmallClass5Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2LargeClass1Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2LargeClass2Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2LargeClass3Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2LargeClass4Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup2LargeClass5Alpha, 0);
    }
    if (bAnyGroup3) {
      ValidationHelpers.makeSureLessThan(m_fSpGroup3SmallClass1Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3SmallClass2Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3SmallClass3Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3SmallClass4Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3SmallClass5Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3LargeClass1Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3LargeClass2Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3LargeClass3Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3LargeClass4Alpha, 0);
      ValidationHelpers.makeSureLessThan(m_fSpGroup3LargeClass5Alpha, 0);
    }

    //Make sure initial conditions proportions add up to less than 1
    if (m_fSpGroup1SmallClass1InitLog.getValue() +
        m_fSpGroup1SmallClass2InitLog.getValue() +
        m_fSpGroup1SmallClass3InitLog.getValue() +
        m_fSpGroup1SmallClass4InitLog.getValue() +
        m_fSpGroup1SmallClass5InitLog.getValue() +
        m_fSpGroup2SmallClass1InitLog.getValue() +
        m_fSpGroup2SmallClass2InitLog.getValue() +
        m_fSpGroup2SmallClass3InitLog.getValue() +
        m_fSpGroup2SmallClass4InitLog.getValue() +
        m_fSpGroup2SmallClass5InitLog.getValue() +
        m_fSpGroup3SmallClass1InitLog.getValue() +
        m_fSpGroup3SmallClass2InitLog.getValue() +
        m_fSpGroup3SmallClass3InitLog.getValue() +
        m_fSpGroup3SmallClass4InitLog.getValue() +
        m_fSpGroup3SmallClass5InitLog.getValue() +
        m_fSpGroup1LargeClass1InitLog.getValue() +
        m_fSpGroup1LargeClass2InitLog.getValue() +
        m_fSpGroup1LargeClass3InitLog.getValue() +
        m_fSpGroup1LargeClass4InitLog.getValue() +
        m_fSpGroup1LargeClass5InitLog.getValue() +
        m_fSpGroup2LargeClass1InitLog.getValue() +
        m_fSpGroup2LargeClass2InitLog.getValue() +
        m_fSpGroup2LargeClass3InitLog.getValue() +
        m_fSpGroup2LargeClass4InitLog.getValue() +
        m_fSpGroup2LargeClass5InitLog.getValue() +
        m_fSpGroup3LargeClass1InitLog.getValue() +
        m_fSpGroup3LargeClass2InitLog.getValue() +
        m_fSpGroup3LargeClass3InitLog.getValue() +
        m_fSpGroup3LargeClass4InitLog.getValue() +
        m_fSpGroup3LargeClass5InitLog.getValue() +
        m_fInitCondScarSoil.getValue() +
        m_fInitCondTipup.getValue() > 1) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "The initial conditions values must not add up to be greater than 1.");
    }

    if (m_fSpGroup1SmallClass1PartCutLog.getValue() +
        m_fSpGroup1SmallClass2PartCutLog.getValue() +
        m_fSpGroup1SmallClass3PartCutLog.getValue() +
        m_fSpGroup1SmallClass4PartCutLog.getValue() +
        m_fSpGroup1SmallClass5PartCutLog.getValue() +
        m_fSpGroup2SmallClass1PartCutLog.getValue() +
        m_fSpGroup2SmallClass2PartCutLog.getValue() +
        m_fSpGroup2SmallClass3PartCutLog.getValue() +
        m_fSpGroup2SmallClass4PartCutLog.getValue() +
        m_fSpGroup2SmallClass5PartCutLog.getValue() +
        m_fSpGroup3SmallClass1PartCutLog.getValue() +
        m_fSpGroup3SmallClass2PartCutLog.getValue() +
        m_fSpGroup3SmallClass3PartCutLog.getValue() +
        m_fSpGroup3SmallClass4PartCutLog.getValue() +
        m_fSpGroup3SmallClass5PartCutLog.getValue() +
        m_fSpGroup1LargeClass1PartCutLog.getValue() +
        m_fSpGroup1LargeClass2PartCutLog.getValue() +
        m_fSpGroup1LargeClass3PartCutLog.getValue() +
        m_fSpGroup1LargeClass4PartCutLog.getValue() +
        m_fSpGroup1LargeClass5PartCutLog.getValue() +
        m_fSpGroup2LargeClass1PartCutLog.getValue() +
        m_fSpGroup2LargeClass2PartCutLog.getValue() +
        m_fSpGroup2LargeClass3PartCutLog.getValue() +
        m_fSpGroup2LargeClass4PartCutLog.getValue() +
        m_fSpGroup2LargeClass5PartCutLog.getValue() +
        m_fSpGroup3LargeClass1PartCutLog.getValue() +
        m_fSpGroup3LargeClass2PartCutLog.getValue() +
        m_fSpGroup3LargeClass3PartCutLog.getValue() +
        m_fSpGroup3LargeClass4PartCutLog.getValue() +
        m_fSpGroup3LargeClass5PartCutLog.getValue() +
        m_fPartialCutScarSoil.getValue() +
        m_fPartialCutTipup.getValue() > 1) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "The partial cut values must not add up to be greater than 1.");
    }

    if (m_fSpGroup1SmallClass1GapCutLog.getValue() +
        m_fSpGroup1SmallClass2GapCutLog.getValue() +
        m_fSpGroup1SmallClass3GapCutLog.getValue() +
        m_fSpGroup1SmallClass4GapCutLog.getValue() +
        m_fSpGroup1SmallClass5GapCutLog.getValue() +
        m_fSpGroup2SmallClass1GapCutLog.getValue() +
        m_fSpGroup2SmallClass2GapCutLog.getValue() +
        m_fSpGroup2SmallClass3GapCutLog.getValue() +
        m_fSpGroup2SmallClass4GapCutLog.getValue() +
        m_fSpGroup2SmallClass5GapCutLog.getValue() +
        m_fSpGroup3SmallClass1GapCutLog.getValue() +
        m_fSpGroup3SmallClass2GapCutLog.getValue() +
        m_fSpGroup3SmallClass3GapCutLog.getValue() +
        m_fSpGroup3SmallClass4GapCutLog.getValue() +
        m_fSpGroup3SmallClass5GapCutLog.getValue() +
        m_fSpGroup1LargeClass1GapCutLog.getValue() +
        m_fSpGroup1LargeClass2GapCutLog.getValue() +
        m_fSpGroup1LargeClass3GapCutLog.getValue() +
        m_fSpGroup1LargeClass4GapCutLog.getValue() +
        m_fSpGroup1LargeClass5GapCutLog.getValue() +
        m_fSpGroup2LargeClass1GapCutLog.getValue() +
        m_fSpGroup2LargeClass2GapCutLog.getValue() +
        m_fSpGroup2LargeClass3GapCutLog.getValue() +
        m_fSpGroup2LargeClass4GapCutLog.getValue() +
        m_fSpGroup2LargeClass5GapCutLog.getValue() +
        m_fSpGroup3LargeClass1GapCutLog.getValue() +
        m_fSpGroup3LargeClass2GapCutLog.getValue() +
        m_fSpGroup3LargeClass3GapCutLog.getValue() +
        m_fSpGroup3LargeClass4GapCutLog.getValue() +
        m_fSpGroup3LargeClass5GapCutLog.getValue() +
        m_fGapCutScarSoil.getValue() +
        m_fGapCutTipup.getValue() > 1) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "The gap cut values must not add up to be greater than 1.");
    }

    if (m_fSpGroup1SmallClass1ClearCutLog.getValue() +
        m_fSpGroup1SmallClass2ClearCutLog.getValue() +
        m_fSpGroup1SmallClass3ClearCutLog.getValue() +
        m_fSpGroup1SmallClass4ClearCutLog.getValue() +
        m_fSpGroup1SmallClass5ClearCutLog.getValue() +
        m_fSpGroup2SmallClass1ClearCutLog.getValue() +
        m_fSpGroup2SmallClass2ClearCutLog.getValue() +
        m_fSpGroup2SmallClass3ClearCutLog.getValue() +
        m_fSpGroup2SmallClass4ClearCutLog.getValue() +
        m_fSpGroup2SmallClass5ClearCutLog.getValue() +
        m_fSpGroup3SmallClass1ClearCutLog.getValue() +
        m_fSpGroup3SmallClass2ClearCutLog.getValue() +
        m_fSpGroup3SmallClass3ClearCutLog.getValue() +
        m_fSpGroup3SmallClass4ClearCutLog.getValue() +
        m_fSpGroup3SmallClass5ClearCutLog.getValue() +
        m_fSpGroup1LargeClass1ClearCutLog.getValue() +
        m_fSpGroup1LargeClass2ClearCutLog.getValue() +
        m_fSpGroup1LargeClass3ClearCutLog.getValue() +
        m_fSpGroup1LargeClass4ClearCutLog.getValue() +
        m_fSpGroup1LargeClass5ClearCutLog.getValue() +
        m_fSpGroup2LargeClass1ClearCutLog.getValue() +
        m_fSpGroup2LargeClass2ClearCutLog.getValue() +
        m_fSpGroup2LargeClass3ClearCutLog.getValue() +
        m_fSpGroup2LargeClass4ClearCutLog.getValue() +
        m_fSpGroup2LargeClass5ClearCutLog.getValue() +
        m_fSpGroup3LargeClass1ClearCutLog.getValue() +
        m_fSpGroup3LargeClass2ClearCutLog.getValue() +
        m_fSpGroup3LargeClass3ClearCutLog.getValue() +
        m_fSpGroup3LargeClass4ClearCutLog.getValue() +
        m_fSpGroup3LargeClass5ClearCutLog.getValue() +
        m_fClearCutScarSoil.getValue() +
        m_fClearCutTipup.getValue() > 1) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "The clear cut values must not add up to be greater than 1.");
    }

    //Make sure that the mean log diameters for small and large logs straddle
    //the size class boundary
    ValidationHelpers.makeSureIsBounded(m_fInitSmallLogMeanDiam, 0, m_fLogSizeClassBoundary.getValue());
    ValidationHelpers.makeSureGreaterThan(m_fInitLargeLogMeanDiam, m_fLogSizeClassBoundary.getValue());

    //Make sure the tree decay class values sum to 1
    if (java.lang.Math.abs(
        (m_fPropFallenTreesDecayClass1.getValue() +
            m_fPropFallenTreesDecayClass2.getValue() +
            m_fPropFallenTreesDecayClass3.getValue() +
            m_fPropFallenTreesDecayClass4.getValue() +
            m_fPropFallenTreesDecayClass5.getValue()) - 1) > 0.00001) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "The tree decay class values must add up to 1.");
    }

    //Make sure the snag decay class values sum to 1
    if (java.lang.Math.abs(
        (m_fPropFallenSnagsDecayClass1.getValue() +
            m_fPropFallenSnagsDecayClass2.getValue() +
            m_fPropFallenSnagsDecayClass3.getValue() +
            m_fPropFallenSnagsDecayClass4.getValue() +
            m_fPropFallenSnagsDecayClass5.getValue()) - 1) > 0.00001) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "The snag decay class values must add up to 1.");
    }

    //More to do if Harvest is enabled
    if (m_oManager.getDisturbanceBehaviors().getBehaviorByParameterFileTag("Harvest").size() > 0) {
      //Make sure that the mean log diameters for small and large logs 
      //straddle the size class boundary
      ValidationHelpers.makeSureIsBounded(m_fPartCutSmallLogMeanDiam, 0, m_fLogSizeClassBoundary.getValue());
      ValidationHelpers.makeSureIsBounded(m_fGapCutSmallLogMeanDiam, 0, m_fLogSizeClassBoundary.getValue());
      ValidationHelpers.makeSureIsBounded(m_fClearCutSmallLogMeanDiam, 0, m_fLogSizeClassBoundary.getValue());
      ValidationHelpers.makeSureGreaterThan(m_fPartCutLargeLogMeanDiam, m_fLogSizeClassBoundary.getValue());
      ValidationHelpers.makeSureGreaterThan(m_fGapCutLargeLogMeanDiam, m_fLogSizeClassBoundary.getValue());
      ValidationHelpers.makeSureGreaterThan(m_fClearCutLargeLogMeanDiam, m_fLogSizeClassBoundary.getValue());
    }

    //Make sure the snag dynamics behavior is enabled
    if (m_oManager.getSnagDynamicsBehaviors().getBehaviorByDisplayName("Snag Decay Class Dynamics").size() == 0) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "The behavior \"" + m_oManager.getSnagDynamicsBehaviors().
          getDescriptor("Snag Decay Class Dynamics") + 
          "\" must be used with the \"" + getDescriptor() + "\".");
    }


    //Check to see if the substrate calculations grids need updating - it
    //depends on what the user entered for max decay time
    Grid oGrid = m_oManager.getGridByName("detailedsubstratecalcs");
    DataMember[] p_oDataMembers = oGrid.getDataMembers();
    int iDecayTimesteps = ((int) java.lang.Math.ceil( (float) m_iMaxDecayTime.
        getValue() / m_oManager.getPlot().getNumberOfYearsPerTimestep())) + 1;
    if (p_oDataMembers.length != 31 + (30*iDecayTimesteps)) {
      int i, j, k, iCount;
      p_oDataMembers = new DataMember[31 + (30*iDecayTimesteps)];

      p_oDataMembers[0] = new DataMember("Amount of new tip-up mounds",
          "newtipup",
          DataMember.FLOAT);

      iCount = 1;
      for (i = 1; i <= 5; i++) { //decay class
        for (j = 1; j <= 3; j++) { //species group
          p_oDataMembers[iCount] = new DataMember(
              "New Log Area Sp Group " + j + " Small Decay " + i, 
              "newloggroup" + j + "smalldecay" + i, DataMember.FLOAT);        
          iCount++;
          p_oDataMembers[iCount] = new DataMember(
              "New Log Area Sp Group " + j + " Large Decay " + i, 
              "newloggroup" + j + "largedecay" + i, DataMember.FLOAT);        
          iCount++;
          for (k = 0; k < iDecayTimesteps; k++) {
            p_oDataMembers[iCount] = new DataMember(
                "Prop Sp Group " + j + " Small Decay " + i + " Timestep " + k, 
                "loggroup" + j + "smalldecay" + i + "_" + k, DataMember.FLOAT);         
            iCount++;
            p_oDataMembers[iCount] = new DataMember(
                "Prop Sp Group " + j + " Large Decay " + i + " Timestep " + k, 
                "loggroup" + j + "largedecay" + i + "_" + k, DataMember.FLOAT);         
            iCount++;
          }
        }
      }
      oGrid.setDataMembers(p_oDataMembers, oGrid.getPackageDataMembers());
      //Go to the output behavior - if detailedsubstratecalcs is a grid, erase 
      //any data members that may have been removed
      OutputBehaviors oOutputBeh = m_oManager.getOutputBehaviors();
      if (oOutputBeh.getBehaviorByParameterFileTag("Output").size() > 0) {
        DetailedOutput oOutput = (DetailedOutput) oOutputBeh.getBehaviorByParameterFileTag("Output").get(0);
        DetailedGridSettings oSettings;
        DataMember oMember;
        boolean bFound;
        for (i = 0; i < oOutput.getNumberOfDetailedGridSettings(); i++) {
          oSettings = (DetailedGridSettings) oOutput.getDetailedGridSetting(i);
          if (oSettings.getName().equals("detailedsubstratecalcs")) {
            for (j = 0; j < oSettings.getNumberOfFloats(); j++) {
              oMember = (DataMember) oSettings.getFloat(j);
              bFound = false;
              for (k = 0; k < p_oDataMembers.length; k++) {
                if (p_oDataMembers[k].equals(oMember)) {
                  bFound = true; break;
                }
              }
              if (bFound == false) {
                oSettings.removeFloat(j);
                j--;
              }
            }
          }
        }
      }
    } 
  }

  /**
   * Overridden to do enums.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) throws ModelException {
    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    
    ModelEnum oVal;
    int iNumSpecies = p_sNewSpecies.length, i;

    for (i = 0; i < iNumSpecies; i++) {
      if (null == mp_iSpeciesGroup.getValue().get(i)) {
        oVal = new ModelEnum(new int[] {3, 2, 1}, new String[] {"3", "2", "1"}, "", "");
        oVal.setValue(1);
        mp_iSpeciesGroup.getValue().remove(i);
        mp_iSpeciesGroup.getValue().add(i, oVal);
      }
    }
  }
}
