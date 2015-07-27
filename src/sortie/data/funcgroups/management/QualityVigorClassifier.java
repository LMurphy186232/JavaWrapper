package sortie.data.funcgroups.management;

import java.io.BufferedWriter;
import java.util.ArrayList;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.behaviorsetup.BehaviorParameterDisplay;

/**
 * Corresponds to the clQualityVigorClassifier class.
 * @author lora
 */
public class QualityVigorClassifier extends Behavior {

  /** Initial conditions size classes. */
  protected ArrayList<QualityVigorSizeClass> mp_oQualityVigorSizeClasses = 
      new ArrayList<QualityVigorSizeClass>(0);

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
  
  /** Placeholder for reading priorities from an XML file. This is for name. */
  protected String m_sPriorityName = "";

  /** Placeholder for reading priorities from an XML file. This is for min 
   * value. */
  protected float m_fPriorityMin = -1;

  /** Placeholder for reading priorities from an XML file. This is for max 
   * value. */
  protected float m_fPriorityMax = -1;

  /** Placeholder for reading priorities from an XML file. This is for data 
   * type. */
  protected int m_iPriorityType = -1;

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
  public QualityVigorClassifier(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "management_behaviors.quality_vigor_classifier");

    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.SNAG, false);
    addRequiredData(mp_iQualVigorDeciduous);
    addRequiredData(mp_fQualVigorBeta0Vig);
    addRequiredData(mp_fQualVigorBeta11Vig);
    addRequiredData(mp_fQualVigorBeta12Vig);
    addRequiredData(mp_fQualVigorBeta13Vig);
    addRequiredData(mp_fQualVigorBeta14Vig);
    addRequiredData(mp_fQualVigorBeta15Vig);
    addRequiredData(mp_fQualVigorBeta16Vig);
    addRequiredData(mp_fQualVigorBeta2Vig);
    addRequiredData(mp_fQualVigorBeta3Vig);
    addRequiredData(mp_fQualVigorBeta0Qual);
    addRequiredData(mp_fQualVigorBeta11Qual);
    addRequiredData(mp_fQualVigorBeta12Qual);
    addRequiredData(mp_fQualVigorBeta13Qual);
    addRequiredData(mp_fQualVigorBeta14Qual);
    addRequiredData(mp_fQualVigorBeta2Qual);
    addRequiredData(mp_fQualVigorBeta3Qual);
    addRequiredData(mp_fQualVigorProbNewAdultsVig);
    addRequiredData(mp_fQualVigorProbNewAdultsQual);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember(
        "Vigorous", "vigorous", DataMember.BOOLEAN));
    mp_oNewTreeDataMembers.add(new DataMember(
        "Sawlog", "sawlog", DataMember.BOOLEAN));
    mp_oNewTreeDataMembers.add(new DataMember(
        "Tree class", "treeclass", DataMember.INTEGER));
    
    // Set up the quality tree classifier deciduous enums vector - values 
    //display in reverse order
    TreePopulation oPop = m_oManager.getTreePopulation();
    ModelEnum oVal;
    int iNumSpecies = oPop.getNumberOfSpecies(), i;
    for (i = 0; i < iNumSpecies; i++) {
      oVal = new ModelEnum(new int[] { 1, 0 }, 
          new String[] { "Deciduous", "Coniferous" }, "", "");
      oVal.setValue(1);
      mp_iQualVigorDeciduous.getValue().add(oVal);
    }       
  } 

  /**
   * Overridden to update enums.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) throws ModelException {
    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    
    ModelEnum oVal;    
    int iNumSpecies = p_sNewSpecies.length, i;
    for (i = 0; i < iNumSpecies; i++) {
      if (null == mp_iQualVigorDeciduous.getValue().get(i)) {
        oVal = new ModelEnum(new int[] { 1, 0 }, 
            new String[] { "Deciduous", "Coniferous" }, "", "");
        oVal.setValue(1);
        mp_iQualVigorDeciduous.getValue().remove(i);
        mp_iQualVigorDeciduous.getValue().add(i, oVal);
      }
    }
  }

  /**
   * Writes the tree vigor quality classifier data to the parameter file.
   * Overwritten to handle initial conditions.
   * 
   * @param jOut FileWriter File to write to.
   * @param oPop Tree population object.
   * @throws ModelException passed through from called functions.
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop) throws
  ModelException {

    try {
      boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
      int i, j;
      jOut.write("<" + m_sXMLRootString + m_iListPosition + ">");

      writeSpeciesSpecificValue(jOut, mp_iQualVigorDeciduous, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta0Vig, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta11Vig, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta12Vig, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta13Vig, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta14Vig, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta15Vig, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta16Vig, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta2Vig, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta3Vig, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta0Qual, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta11Qual, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta12Qual, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta13Qual, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta14Qual, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta2Qual, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorBeta3Qual, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorProbNewAdultsVig, oPop, p_bAppliesTo);
      writeSpeciesSpecificValue(jOut, mp_fQualVigorProbNewAdultsQual, oPop, p_bAppliesTo);

      //Make sure there's something to write for initial conditions
      if (mp_oQualityVigorSizeClasses.size() > 0) {
        boolean bAny = false;
        for (i = 0; i < mp_oQualityVigorSizeClasses.size(); i++) {
          QualityVigorSizeClass oQual = mp_oQualityVigorSizeClasses.get(i); 
          if (oQual.mp_fProbVigorous.getValue().size() > 0 ||
              oQual.mp_fProbSawlog.getValue().size() > 0) {
            for (j = 0; j < p_bAppliesTo.length; j++) {
              if (p_bAppliesTo[j] && 
                  (oQual.mp_fProbVigorous.getValue().get(j) != null &&
                  ((Float)oQual.mp_fProbVigorous.getValue().get(j)).floatValue() > -1) ||
                  (oQual.mp_fProbSawlog.getValue().get(j) != null &&
                  ((Float)oQual.mp_fProbSawlog.getValue().get(j)).floatValue() > -1)) {
                bAny = true; break;
              }                  
            }
          }
        }
        if (bAny) {
          //There is something to write, but be careful not to write any
          //skipped categories
          jOut.write("<ma_classifierInitialConditions>");
          for (i = 0; i < mp_oQualityVigorSizeClasses.size(); i++) {
            QualityVigorSizeClass oQual = mp_oQualityVigorSizeClasses.get(i);
            bAny = false;
            if (oQual.mp_fProbVigorous.getValue().size() > 0 ||
                oQual.mp_fProbSawlog.getValue().size() > 0) {
              for (j = 0; j < p_bAppliesTo.length; j++) {
                if (p_bAppliesTo[j] && 
                    (oQual.mp_fProbVigorous.getValue().get(j) != null &&
                    ((Float)oQual.mp_fProbVigorous.getValue().get(j)).floatValue() > -1) ||
                    (oQual.mp_fProbSawlog.getValue().get(j) != null &&
                    ((Float)oQual.mp_fProbSawlog.getValue().get(j)).floatValue() > -1)) {
                  bAny = true; break;
                }                  
              }
            }
            if (bAny) {
              jOut.write("<ma_classifierSizeClass>");
              jOut.write("<ma_classifierBeginDBH>" + oQual.m_fMinDbh + "</ma_classifierBeginDBH>");
              jOut.write("<ma_classifierEndDBH>" + oQual.m_fMaxDbh + "</ma_classifierEndDBH>");
              writeSpeciesSpecificValue(jOut, oQual.mp_fProbVigorous, oPop, p_bAppliesTo);
              writeSpeciesSpecificValue(jOut, oQual.mp_fProbSawlog, oPop, p_bAppliesTo);
              jOut.write("</ma_classifierSizeClass>");
            }            
          } 
          jOut.write("</ma_classifierInitialConditions>");
        }
      }
      jOut.write("</" + m_sXMLRootString + m_iListPosition + ">");
    } catch (java.io.IOException oExp) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
          "There was a problem writing the parameter file."));
    }
  }

  /**
   * Accepts an XML parent tag (empty, no data) from the parser. This method
   * watches for the ma_classifierSizeClass tag.
   * 
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if there is a problem reading this data.
   */
  public void readXMLParentTag(String sXMLTag, Attributes oAttributes)
      throws ModelException {

    if (sXMLTag.equals("ma_classifierSizeClass")) {
      //Reset the placeholders - use the same place holders for priorities
      m_fPriorityMin = -1;
      m_fPriorityMax = -1;
    }
    super.readXMLParentTag(sXMLTag, oAttributes);
  }

  /**
   * This method looks for the following tags:
   * <ul>
   * <li>ma_classifierBeginDBH</li>
   * <li>ma_classifierEndDBH</li>
   * </ul>
   * 
   * @param sXMLTag XML tag of data object whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param oAttributes Attributes of the object. Ignored, but may be needed by 
   * overriding objects.
   * @param oData Data value appropriate to the data type
   * @return true if the value was set successfully; false if the value could
   * not be found. (This would not be an error, because I need a way to cycle 
   * through the objects until one of the objects comes up with a match.)
   * @throws ModelException if the value could not be assigned to the data 
   * object, or if the cut type or cut type amount values are unrecognized.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes, Object oData) throws ModelException {
    if (sXMLTag != null) {
      if (sXMLTag.equals("ma_classifierBeginDBH")) {
        // Extract the value
        float fVal = new Float(String.valueOf(oData)).floatValue();
        m_fPriorityMin = fVal;
        //Check to see if the end DBH has already been read
        if (m_fPriorityMax > -1) {
          //Create a new tree quality initial conditions object
          mp_oQualityVigorSizeClasses.add(
              new QualityVigorSizeClass(m_fPriorityMin, m_fPriorityMax,
                  m_oManager.getTreePopulation().getNumberOfSpecies()));
          m_fPriorityMin = -1;
          m_fPriorityMax = -1;
        }
        return true;
      } else if (sXMLTag.equals("ma_classifierEndDBH")) {
        // Extract the value
        float fVal = new Float(String.valueOf(oData)).floatValue();
        m_fPriorityMax = fVal;
        //Check to see if the end DBH has already been read
        if (m_fPriorityMin > -1) {
          //Create a new tree quality initial conditions object
          mp_oQualityVigorSizeClasses.add(
              new QualityVigorSizeClass(m_fPriorityMin, m_fPriorityMax,
                  m_oManager.getTreePopulation().getNumberOfSpecies()));
          m_fPriorityMin = -1;
          m_fPriorityMax = -1;
        }
        return true;
      }
    }
    return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes,
        oData);

  }

  /**
   * Reads initial conditions probabilities for tree classifier.
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


    if (sXMLTag.equals("ma_classifierProbVigorous")) {

      // Get the last initial conditions object
      QualityVigorSizeClass oQual =  
          mp_oQualityVigorSizeClasses.get(mp_oQualityVigorSizeClasses.size() - 1);
      for (int i = 0; i < p_oData.size(); i++) {
        if (p_bAppliesTo[i]) {
          String sData = p_oData.get(i);
          try {
            oQual.mp_fProbVigorous.getValue().set(i, new Float(sData));
          } catch (NumberFormatException e) {
            throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
                "Vigor probability unreadable."));
          }
        }
      }
      return true;
    } else if (sXMLTag.equals("ma_classifierProbSawlog")) {

      // Get the last initial conditions object
      QualityVigorSizeClass oQual =  
          mp_oQualityVigorSizeClasses.get(mp_oQualityVigorSizeClasses.size() - 1);
      for (int i = 0; i < p_oData.size(); i++) {
        if (p_bAppliesTo[i]) {
          String sData = p_oData.get(i);
          try {
            oQual.mp_fProbSawlog.getValue().set(i, new Float(sData));
          } catch (NumberFormatException e) {
            throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
                "Sawlog probability unreadable."));
          }
        }
      }
      return true;
    }    

    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }

  /**
   * Gets the tree population initial conditions size classes for use with the 
   * quality vigor initial conditions. If these have been added before, this 
   * will check to see if the size classes are the same and will keep any values 
   * for those size classes. 
   * @param oPop Tree population
   */
  protected void getQualityVigorInitialConditions(TreePopulation oPop) {
    int i, iNumSpecies = oPop.getNumberOfSpecies();
    boolean bSame;

    //If there are no size classes, return
    if (mp_oQualityVigorSizeClasses.size() == 0 && 
        oPop.getNumberOfSizeClasses() == 0) return;

    //If there is no change in size classes, do nothing
    if (mp_oQualityVigorSizeClasses.size() == oPop.getNumberOfSizeClasses()) {
      bSame = true;             
      for (i = 1; i < oPop.getNumberOfSizeClasses(); i++) {
        if (Math.abs(mp_oQualityVigorSizeClasses.get(i).m_fMinDbh - 
            oPop.getSizeClass(i - 1).floatValue()) > 0.0001 || 
            Math.abs(mp_oQualityVigorSizeClasses.get(i).m_fMaxDbh - 
                oPop.getSizeClass(i).floatValue()) > 0.0001) { 
          bSame = false; break;
        }
      }
      if (Math.abs(mp_oQualityVigorSizeClasses.get(0).m_fMaxDbh - 
          oPop.getSizeClass(0).floatValue()) > 0.0001) { 
        bSame = false;
      }
      if (bSame) return;
    }

    //If there were already size classes, delete them
    if (mp_oQualityVigorSizeClasses.size() > 0) {
      mp_oQualityVigorSizeClasses.clear();
      
      for (i = 0; i < mp_oAllData.size(); i++) {
        ModelData oData = mp_oAllData.get(i);
        String sKey = oData.getDescriptor();
        if (sKey.indexOf("Quality Vigor") > -1 && 
            sKey.indexOf("Init Conditions") > -1) {
          mp_oAllData.remove(i); i--;
        }
      }
    }

    //Create and add the appropriate classes
    if (oPop.getNumberOfSizeClasses() == 0) return;    
    if (oPop.getSizeClass(0).floatValue() > 0) {
      mp_oQualityVigorSizeClasses.add(new QualityVigorSizeClass(0, 
          oPop.getSizeClass(0).floatValue(), iNumSpecies));      
    }
    for (i = 1; i < oPop.getNumberOfSizeClasses(); i++) {
      mp_oQualityVigorSizeClasses.add(new QualityVigorSizeClass(
          oPop.getSizeClass(i - 1).floatValue(), 
          oPop.getSizeClass(i).floatValue(), iNumSpecies));
    }   
    for (QualityVigorSizeClass oQual : mp_oQualityVigorSizeClasses) {
      mp_oAllData.add(oQual.mp_fProbVigorous);      
    }
    for (QualityVigorSizeClass oQual : mp_oQualityVigorSizeClasses) {
      mp_oAllData.add(oQual.mp_fProbSawlog);
    }
  }

  
  /**
   * Adds tree vigor quality initial conditions if necessary.
   */
  public void endOfParameterFileRead() {
    if (mp_oQualityVigorSizeClasses.size() == 0) return;
  
    for (QualityVigorSizeClass oQual : mp_oQualityVigorSizeClasses) {
      mp_oAllData.add(oQual.mp_fProbVigorous);
    }
    for (QualityVigorSizeClass oQual : mp_oQualityVigorSizeClasses) {
      mp_oAllData.add(oQual.mp_fProbSawlog);
    }    
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}

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
  
  /**
   * Overridden to get the latest tree initial conditions size classes.
   * 
   * @param oPop TreePopulation object.
   * @return Vector from the base class.
   */
  public ArrayList<BehaviorParameterDisplay> formatDataForDisplay(TreePopulation oPop) {
    
    getQualityVigorInitialConditions(oPop);
    
    return super.formatDataForDisplay(oPop);
  }

}
