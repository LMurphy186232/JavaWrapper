package sortie.data.funcgroups.disperse;

import java.util.ArrayList;

import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSpatialDispersal class.
 * @author lora
 */
public abstract class SpatialDisperseBase extends DisperseBase {
  
  /**Weibull disperse function*/
  public final static int WEIBULL = 0;
  /**Lognormal disperse function*/
  public final static int LOGNORMAL = 1;
  /**Canopy forest cover status for cells*/
  public final static int CANOPY = 0;
  /**Gap forest cover status for cells*/
  public final static int GAP = 1;

  /**Total number of disperse functions*/
  public final static int NUMBER_OF_DISPERSE_FUNCTIONS = 2;
  /**Total number of forest cover statuses*/
  public final static int NUMBER_OF_FOREST_COVERS = 2;

  
  /** STR for disperse function. First index is cover -
   * canopy or gap. The second index is species.*/
  protected ModelVector[] mp_fSTR;
      
  /** Beta for disperse function. First index is cover -
   * canopy or gap. The second index is species.*/
  protected ModelVector[] mp_fBeta;
      
  /** Theta (if weibull) or Xb (if lognormal) for disperse function. Array is 
   * 3D - first index is which disperse function is used - weibull or lognormal.
   * The second index is cover - canopy or gap.  The third index is species.*/
  protected ModelVector[][] mp_fThetaOrXb;
  
  /** Dispersal (if weibull) or X0 (if lognormal) for disperse function. Array
   * is 3D - first index is which disperse function is used - weibull or 
   * lognormal. The second index is cover - canopy or gap. The third index is 
   * species.*/
  protected ModelVector[][] mp_fDispOrX0;
  
  /**Which disperse function to use under each forest cover - valid values
   * are WEIBULL and LOGNORMAL - this is a vector of ModelEnums*/
  protected ModelVector[] mp_iWhichFunctionUsed;  
  
  /**STR for stump dispersal for each species*/
  protected ModelVector mp_fStumpSTR = new ModelVector("STR for Stumps",
      "di_suckerSTR", "di_ssVal", 0, ModelVector.FLOAT);

  /**Beta for stump dispersal for each species*/
  protected ModelVector mp_fStumpBeta = new ModelVector("Beta for Stumps",
      "di_suckerBeta", "di_sbVal", 0, ModelVector.FLOAT);


  /**
   * Constructor.
   * @param oManager GUI manager.
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @param sHelpFileString String matching this behavior's topic in the help
   * @throws ModelException 
   */
  public SpatialDisperseBase(GUIManager oManager, 
                             BehaviorTypeBase oParent, 
                             String sDescriptor, 
                             String sParFileTag, 
                             String sXMLRootString,
                             String sHelpFileString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, sHelpFileString);
    int i, j;

    //Initialize all our arrays and prep our vectors
    mp_fSTR = new ModelVector[NUMBER_OF_FOREST_COVERS];
    mp_fBeta = new ModelVector[NUMBER_OF_FOREST_COVERS];

    for (j = 0; j < NUMBER_OF_FOREST_COVERS; j++) {
      if (GAP == j) {
        mp_fSTR[j] = new ModelVector("Gap Annual STR",
            "di_weibullGapSTR",
            "di_wgsVal", 0, ModelVector.FLOAT);
        mp_fBeta[j] = new ModelVector("Gap Beta",
            "di_weibullGapBeta", "di_wgbVal", 0,
            ModelVector.FLOAT);        
      }
      else if (CANOPY == j) {
        mp_fSTR[j] = new ModelVector("Annual STR",
            "di_weibullCanopySTR", "di_wcsVal", 0,
            ModelVector.FLOAT);
        mp_fBeta[j] = new ModelVector("Beta",
            "di_weibullCanopyBeta", "di_wcbVal",
            0, ModelVector.FLOAT);        
      }      
    }
  
    mp_fThetaOrXb = new ModelVector[NUMBER_OF_DISPERSE_FUNCTIONS][];
    mp_fDispOrX0 = new ModelVector[NUMBER_OF_DISPERSE_FUNCTIONS][];
    
    for (i = 0; i < NUMBER_OF_DISPERSE_FUNCTIONS; i++) {
      mp_fThetaOrXb[i] = new ModelVector[NUMBER_OF_FOREST_COVERS];
      mp_fDispOrX0[i] = new ModelVector[NUMBER_OF_FOREST_COVERS];

      for (j = 0; j < NUMBER_OF_FOREST_COVERS; j++) {
        if (WEIBULL == i && GAP == j) {
          mp_fThetaOrXb[i][j] = new ModelVector("Weibull Gap Theta",
                                                "di_weibullGapTheta",
                                                "di_wgtVal", 0,
                                                ModelVector.FLOAT);
          mp_fDispOrX0[i][j] = new ModelVector("Weibull Gap Dispersal",
                                               "di_weibullGapDispersal",
                                               "di_wgdVal", 0,
                                               ModelVector.FLOAT);
        }
        else if (WEIBULL == i && CANOPY == j) {
          mp_fThetaOrXb[i][j] = new ModelVector("Weibull Canopy Theta",
                                                "di_weibullCanopyTheta",
                                                "di_wctVal", 0,
                                                ModelVector.FLOAT);
          mp_fDispOrX0[i][j] = new ModelVector("Weibull Canopy Dispersal",
                                               "di_weibullCanopyDispersal",
                                               "di_wcdVal", 0,
                                               ModelVector.FLOAT);
        }
        else if (LOGNORMAL == i && GAP == j) {
          mp_fThetaOrXb[i][j] = new ModelVector("Lognormal Gap Xb",
                                                "di_lognormalGapXb",
                                                "di_lgxbVal", 0,
                                                ModelVector.FLOAT);
          mp_fDispOrX0[i][j] = new ModelVector("Lognormal Gap X0",
                                               "di_lognormalGapX0",
                                               "di_lgx0Val", 0,
                                               ModelVector.FLOAT);
        }
        else {
          mp_fThetaOrXb[i][j] = new ModelVector("Lognormal Canopy Xb",
                                                "di_lognormalCanopyXb",
                                                "di_lcxbVal", 0,
                                                ModelVector.FLOAT);
          mp_fDispOrX0[i][j] = new ModelVector("Lognormal Canopy X0",
                                               "di_lognormalCanopyX0",
                                               "di_lcx0Val", 0,
                                               ModelVector.FLOAT);
        }
      }
    }

    mp_iWhichFunctionUsed = new ModelVector[NUMBER_OF_FOREST_COVERS];

    for (i = 0; i < NUMBER_OF_FOREST_COVERS; i++) {
      if (CANOPY == i) {
        mp_iWhichFunctionUsed[i] = new ModelVector("Canopy Function Used",
            "di_canopyFunction", "di_cfVal", 0, ModelVector.MODEL_ENUM);
      }
      else {
        mp_iWhichFunctionUsed[i] = new ModelVector("Gap Function Used",
            "di_gapFunction", "di_gfVal", 0, ModelVector.MODEL_ENUM);
      }
    }
    doSetup(m_oManager.getTreePopulation());
  }
  
  

  /**
   * Overridden to redo the enums.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) throws ModelException {
    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    doSetup(m_oManager.getTreePopulation());
  }



  /**
   * Does setup of enum vectors. 
   * @param oPop TreePopulation object.
   * @throws ModelException if there's a problem setting behavior use data.
   */
  private void doSetup(TreePopulation oPop) {
    int iNumSpecies = oPop.getNumberOfSpecies(), i, j, k;
    
    //Set up the "which function" vector
    for (i = 0; i < NUMBER_OF_FOREST_COVERS; i++) {
      if (mp_iWhichFunctionUsed[i].getValue().size() == 0) {
        for (j = 0; j < iNumSpecies; j++) {
          mp_iWhichFunctionUsed[i].getValue().add(new ModelEnum(new int[] {
              WEIBULL, LOGNORMAL}, new String[] {"Weibull", "Lognormal"}, 
              "Function used", ""));
        }
      } else {
        //There were already species - verify that any nulls get their enum
        //added
        //Don't use iNumSpecies here - if this is changeOfSpecies, then
        //the tree pop has the wrong number of species still; the vector
        //has already been adjusted and is correct
        //for (j = 0; j < iNumSpecies; j++) {
        for (j = 0; j < mp_iWhichFunctionUsed[i].getValue().size(); j++) {
          if (null == mp_iWhichFunctionUsed[i].getValue().get(j)) {
            mp_iWhichFunctionUsed[i].getValue().remove(j);
            mp_iWhichFunctionUsed[i].getValue().add(j, new ModelEnum(new int[] {
                WEIBULL, LOGNORMAL}, new String[] {"Weibull", "Lognormal"},
                "Function used", ""));
          }
        }
      }
    }
    
    //Initialize our arrays with zeroes if they haven't been set
    for (i = 0; i < NUMBER_OF_DISPERSE_FUNCTIONS; i++) {
      for (j = 0; j < NUMBER_OF_FOREST_COVERS; j++) {
        if (mp_fThetaOrXb[i][j].getValue().size() == 0) {
          for (k = 0; k < iNumSpecies; k++) {
            mp_fThetaOrXb[i][j].getValue().add(Float.valueOf(0));
          }
        }
        if (mp_fDispOrX0[i][j].getValue().size() == 0) {
          for (k = 0; k < iNumSpecies; k++) {
            mp_fDispOrX0[i][j].getValue().add(Float.valueOf(0));
          }
        }
      }
    }

    for (j = 0; j < NUMBER_OF_FOREST_COVERS; j++) {
      if (mp_fSTR[j].getValue().size() == 0) {
        for (k = 0; k < iNumSpecies; k++) {
          mp_fSTR[j].getValue().add(Float.valueOf(0));
        }
      }
      if (mp_fBeta[j].getValue().size() == 0) {
        for (k = 0; k < iNumSpecies; k++) {
          mp_fBeta[j].getValue().add(Float.valueOf(0));
        }
      }      
    }

    if (mp_fStumpSTR.getValue().size() == 0) {
      for (k = 0; k < iNumSpecies; k++) {
        mp_fStumpSTR.getValue().add(Float.valueOf(0));
      }
    }
    if (mp_fStumpBeta.getValue().size() == 0) {
      for (k = 0; k < iNumSpecies; k++) {
        mp_fStumpBeta.getValue().add(Float.valueOf(0));
      }
    }
  }
  
  /**
   * Overrides for backwards compatibility of STR and beta tags, formerly 
   * (and pointlessly) separated by function.
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


    if (sXMLTag.equals("di_lognormalGapSTR")) {
      sXMLTag = "di_weibullGapSTR";
      for (int i = 0; i < p_sChildXMLTags.length; i++) {
        p_sChildXMLTags[i] = "di_wgsVal";
      }
    }
    if (sXMLTag.equals("di_lognormalGapBeta")) {
      sXMLTag = "di_weibullGapBeta";
      for (int i = 0; i < p_sChildXMLTags.length; i++) {
        p_sChildXMLTags[i] = "di_wgbVal";
      }
    }
    if (sXMLTag.equals("di_lognormalCanopySTR")) {
      sXMLTag = "di_weibullCanopySTR";
      for (int i = 0; i < p_sChildXMLTags.length; i++) {
        p_sChildXMLTags[i] = "di_wcsVal";
      }
    }
    if (sXMLTag.equals("di_lognormalCanopyBeta")) {
      sXMLTag = "di_weibullCanopyBeta";
      for (int i = 0; i < p_sChildXMLTags.length; i++) {
        p_sChildXMLTags[i] = "di_wcbVal";
      }
    }
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }
  
}
