package sortie.data.funcgroups;

import java.io.BufferedWriter;

import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;


/**
 * Plot object.
 * Copyright: Copyright (c) Charles D. Canham 2003
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class PlotBehaviors extends BehaviorTypeBase {

  /**
   * Constructor.
   * @param oManager GUIManager object.
   */
  public PlotBehaviors(GUIManager oManager) {
    super(oManager, "Plot");
    
    String sXMLRootString, sParFileTag, sDescriptor;
    
    sXMLRootString = "plot";
    sParFileTag = "plot";
    sDescriptor = "Plot";
    
    mp_oInstantiatedBehaviors.add(new Plot(oManager, this, sDescriptor, sParFileTag, sXMLRootString));
  }  
  
  /**
   * Overridden to ignore.
   */
  public void removeBehavior(Behavior oBeh) {;}
  
  public void writeBehaviorNodes(BufferedWriter out, TreePopulation oPop) throws
  ModelException {;}


  /**
   * Overridden to ignore position.
   */
  public Behavior getBehaviorByXMLParametersParentTag(String sXMLTag, int iPos) {
    if (mp_oInstantiatedBehaviors != null) {
      if (sXMLTag.equals("timestepRundata")) 
        sXMLTag = mp_oInstantiatedBehaviors.get(0).getParameterFileBehaviorName();
      for (Behavior oBeh : mp_oInstantiatedBehaviors) {
        if (oBeh.getXMLParametersRoot().equals(sXMLTag)) {
          return oBeh;
        }
      }
    }
    return null;
  }

  /**
   * Overridden to not create, and to watch for rundata files.
   */
  public Behavior createBehaviorFromParameterFileTag(String sParameterFileTag)
      throws ModelException {
    if (sParameterFileTag.equals("timestepRundata")) 
      sParameterFileTag = mp_oInstantiatedBehaviors.get(0).getParameterFileBehaviorName();
    if (sParameterFileTag.equals(mp_oInstantiatedBehaviors.get(0).getParameterFileBehaviorName())) {
      return mp_oInstantiatedBehaviors.get(0);
    }
    return null;
  }
  
  
}
