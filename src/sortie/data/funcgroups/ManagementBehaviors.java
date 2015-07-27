package sortie.data.funcgroups;

import sortie.data.funcgroups.management.QualityVigorClassifier;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

/**
 * Manages management behaviors and data.
 * 
 * Copyright: Copyright (c) Charles D. Canham 2012
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *  
 * <br>Edit history:
 * <br>------------------
 * <br>March 14, 2012: Created
 */
public class ManagementBehaviors extends BehaviorTypeBase {

  /**
   * Constructor.
   * 
   * @param oManager GUIManager object.
   * @throws ModelException passed through from called functions.
   */
  public ManagementBehaviors(GUIManager oManager) throws ModelException {
    super(oManager, "Management");
    
    String sXMLRootString, sParFileTag, sDescriptor;
    
    // Quality vigor classifier
    sXMLRootString = "QualityVigorClassifier";
    sParFileTag = "QualityVigorClassifier";
    sDescriptor = "Tree Quality Vigor Classifier";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(QualityVigorClassifier.class, sDescriptor, sParFileTag, sXMLRootString));
  }
}
