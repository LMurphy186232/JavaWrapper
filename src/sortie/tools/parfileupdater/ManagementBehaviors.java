package sortie.tools.parfileupdater;


import java.util.ArrayList;

import sortie.data.simpletypes.ModelVector;

public class ManagementBehaviors extends GroupBase {

  /** Quality vigor classifier - beta0 for vigor transition. */
  protected ModelVector mp_fQualVigorBeta0Vig = new ModelVector(
      "Quality Vigor Classifier - Vigor Beta 0", "ma_classifierVigBeta0", 
      "ma_cvb0Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - beta1 for vigor transition, initial class 1. */
  protected ModelVector mp_fQualVigorBeta11Vig = new ModelVector(
      "Quality Vigor Classifier - Vigor Beta 1 Class 1", 
      "ma_classifierVigBeta11", "ma_cvb11Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - beta1 for vigor transition, initial class 2. */
  protected ModelVector mp_fQualVigorBeta12Vig = new ModelVector(
      "Quality Vigor Classifier - Vigor Beta 1 Class 2", 
      "ma_classifierVigBeta12", "ma_cvb12Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - beta1 for vigor transition, initial class 3. */
  protected ModelVector mp_fQualVigorBeta13Vig = new ModelVector(
      "Quality Vigor Classifier - Vigor Beta 1 Class 3", 
      "ma_classifierVigBeta13", "ma_cvb13Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - beta1 for vigor transition, initial class 4. */
  protected ModelVector mp_fQualVigorBeta14Vig = new ModelVector(
      "Quality Vigor Classifier - Vigor Beta 1 Class 4", 
      "ma_classifierVigBeta14", "ma_cvb14Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - beta1 for vigor transition, initial class 5. */
  protected ModelVector mp_fQualVigorBeta15Vig = new ModelVector(
      "Quality Vigor Classifier - Vigor Beta 1 Class 5", 
      "ma_classifierVigBeta15", "ma_cvb15Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - beta1 for vigor transition, initial class 6. */
  protected ModelVector mp_fQualVigorBeta16Vig = new ModelVector(
      "Quality Vigor Classifier - Vigor Beta 1 Class 6", 
      "ma_classifierVigBeta16", "ma_cvb16Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - beta2 for vigor transition. */
  protected ModelVector mp_fQualVigorBeta2Vig = new ModelVector(
      "Quality Vigor Classifier - Vigor Beta 2", "ma_classifierVigBeta2", 
      "ma_cvb2Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - beta3 for vigor transition. */
  protected ModelVector mp_fQualVigorBeta3Vig = new ModelVector(
      "Quality Vigor Classifier - Vigor Beta 3", "ma_classifierVigBeta3", 
      "ma_cvb3Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - beta0 for quality transition. */
  protected ModelVector mp_fQualVigorBeta0Qual = new ModelVector(
      "Quality Vigor Classifier - Quality Beta 0", "ma_classifierQualBeta0", 
      "ma_cqb0Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - beta1 for quality transition, initial 
   * class 1. */
  protected ModelVector mp_fQualVigorBeta11Qual = new ModelVector(
      "Quality Vigor Classifier - Quality Beta 1 Class 1", "ma_classifierQualBeta11", 
      "ma_cqb11Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - beta1 for quality transition, initial 
   * class 2. */
  protected ModelVector mp_fQualVigorBeta12Qual = new ModelVector(
      "Quality Vigor Classifier - Quality Beta 1 Class 2", "ma_classifierQualBeta12", 
      "ma_cqb12Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - beta1 for quality transition, initial 
   * class 3. */
  protected ModelVector mp_fQualVigorBeta13Qual = new ModelVector(
      "Quality Vigor Classifier - Quality Beta 1 Class 3", "ma_classifierQualBeta13", 
      "ma_cqb13Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - beta1 for quality transition, initial 
   * class 4. */
  protected ModelVector mp_fQualVigorBeta14Qual = new ModelVector(
      "Quality Vigor Classifier - Quality Beta 1 Class 4", "ma_classifierQualBeta14", 
      "ma_cqb14Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - beta2 for quality transition. */
  protected ModelVector mp_fQualVigorBeta2Qual = new ModelVector(
      "Quality Vigor Classifier - Quality Beta 2", "ma_classifierQualBeta2", 
      "ma_cqb2Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - beta3 for quality transition. */
  protected ModelVector mp_fQualVigorBeta3Qual = new ModelVector(
      "Quality Vigor Classifier - Quality Beta 3", "ma_classifierQualBeta3", 
      "ma_cqb3Val", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - probability of new adults being vigorous. */
  protected ModelVector mp_fQualVigorProbNewAdultsVig = new ModelVector(
      "Quality Vigor Classifier - Prob New Adults Vigorous", 
      "ma_classifierNewAdultProbVigorous", "ma_cnapvVal", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - probability of new adults being sawlog quality. */
  protected ModelVector mp_fQualVigorProbNewAdultsQual = new ModelVector(
      "Quality Vigor Classifier - Prob New Adults Sawlog", 
      "ma_classifierNewAdultProbSawlog", "ma_cnapsVal", 0, ModelVector.FLOAT);

  /** Quality vigor classifier - whether or not a species is deciduous. */
  protected ModelVector mp_iQualVigorDeciduous = new ModelVector(
      "Quality Vigor Classifier - Species Type", "ma_classifierDeciduous", "ma_cdVal", 
      0, ModelVector.MODEL_ENUM);
  
  protected ArrayList<QualityVigorSizeClass> mp_oQualityVigorSizeClasses = 
    new ArrayList<QualityVigorSizeClass>(0);
  
  public ManagementBehaviors() {
    super("");

    
  }

  /**
   * Holds data for a single size class for quality vigor initial conditions.
   * @author lora
   *
   */
  class QualityVigorSizeClass {
    /** Size class minimum DBH. */
    public float m_fMinDbh;
    
    /** Size class maximum DBH. */
    public float m_fMaxDbh;
    
    /** Probability that trees in the size class are vigorous. */
    public ModelVector mp_fProbVigorous = new ModelVector("", 
        "ma_classifierProbVigorous", "ma_cpvVal", 0, ModelVector.FLOAT);
    
    /** Probability that trees in the size class are sawlog quality. */
    public ModelVector mp_fProbSawlog = new ModelVector("", 
        "ma_classifierProbSawlog", "ma_cpsVal", 0, ModelVector.FLOAT);
    
    /**
     * Constructor.
     * @param fMinDbh Minimum DBH of size class.
     * @param fMaxDbh Maximum DBH of size class. 
     */
    public QualityVigorSizeClass(float fMinDbh, float fMaxDbh, int iNumSpecies) {
      m_fMinDbh = fMinDbh;
      m_fMaxDbh = fMaxDbh;
      
      mp_fProbVigorous.setDescriptor(
          "Quality Vigor - Prob Vigorous Init Conditions " + m_fMinDbh + " - "
          + m_fMaxDbh + " cm DBH");
      
      mp_fProbSawlog.setDescriptor(
          "Quality Vigor - Prob Sawlog Init Conditions " + m_fMinDbh + " - "
          + m_fMaxDbh + " cm DBH");
      
      for (int i = 0; i < iNumSpecies; i++) {
        mp_fProbVigorous.getValue().add(new Float(-1));
        mp_fProbSawlog.getValue().add(new Float(-1));
      }
    }
  }
}
