package sortie.data.funcgroups;

import java.io.BufferedWriter;
import java.util.ArrayList;

import sortie.data.funcgroups.output.DetailedOutput;
import sortie.data.funcgroups.output.ShortOutput;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.OutputSetup;

/**
 * Manages output behaviors and data. Output behaviors are always 
 * instantiated.
 * Copyright: Copyright (c) Charles D. Canham 2011
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class OutputBehaviors
    extends BehaviorTypeBase {
  
  /** Dead code for not dead - matches definition in C++ */
  public final static int NOTDEAD = 0;

  /** Dead code for harvest - matches definition in C++ */
  public final static int HARVEST = 1;

  /** Dead code for natural mortality - matches definition in C++ */
  public final static int NATURAL = 2;

  /** Dead code for disease - matches definition in C++ */
  public final static int DISEASE = 3; 

  /** Dead code for fire - matches definition in C++ */
  public final static int FIRE = 4;

  /** Dead code for insects - matches definition in C++ */
  public final static int INSECTS = 5;

  /** Dead code for storm - matches definition in C++ */
  public final static int STORM = 6;
  
  /** Total number of dead reason codes */
  public final static int NUMCODES = 7;  
  
          
  /**
   * Constructor. 
   * @param oManager GUIManager object.
   */
  public OutputBehaviors(GUIManager oManager) throws ModelException {
    super(oManager, "Output");
    
    String sXMLRootString, sParFileTag, sDescriptor;
    
    // Short output
    sXMLRootString = "ShortOutput";
    sParFileTag = "ShortOutput";
    sDescriptor = "Summary Output";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(ShortOutput.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Detailed output
    sXMLRootString = "Output";
    sParFileTag = "Output";
    sDescriptor = "Detailed Output";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(DetailedOutput.class, sDescriptor, sParFileTag, sXMLRootString));
    
    createBehaviorFromParameterFileTag("ShortOutput");
    createBehaviorFromParameterFileTag("Output");
  }
    
  /**
   * Overwritten to only write the nodes if they're enabled.
   */
  public void writeBehaviorNodes(BufferedWriter out, TreePopulation oPop)
      throws ModelException {
    if (getShortOutput().isActive()) {
      getShortOutput().writeBehaviorNode(out, oPop);
    }
    if (getDetailedOutput().isActive()) {
      getDetailedOutput().writeBehaviorNode(out, oPop);
    }
  }

  /**
   * Overridden to only return the activated output behaviors.
   */
  public ArrayList<Behavior> getAllInstantiatedBehaviors() {
    ArrayList<Behavior> oReturn = new ArrayList<Behavior>(0);
    if (getDetailedOutput().isActive())
      oReturn.add(getDetailedOutput());
    if (getShortOutput().isActive())
      oReturn.add(getShortOutput());
    
    return oReturn;
  }

  /**
   * Overridden from base to ignore this. The previously instantiated behaviors
   * are returned.
   */
  public Behavior createBehaviorFromParameterFileTag(String sParameterFileTag) throws ModelException {
    for (int i = 0; i < mp_oInstantiatedBehaviors.size(); i++) {
      if (mp_oInstantiatedBehaviors.get(i).getParameterFileBehaviorName().equals(sParameterFileTag)) 
        return mp_oInstantiatedBehaviors.get(i);
    }
    //Actually wasn't created
    return(super.createBehaviorFromParameterFileTag(sParameterFileTag));
  }

  /**
   * Overridden from base to inactivate.
   */
  public void removeBehavior(Behavior oBeh) {
    if (oBeh instanceof ShortOutput) getShortOutput().inactivate();
    if (oBeh instanceof DetailedOutput) getDetailedOutput().inactivate();
  }

  /**
   * Gets short output.
   * @return Short output.
   */
  public ShortOutput getShortOutput() {
    if (mp_oInstantiatedBehaviors.size() > 0)
      for (Behavior oBeh : mp_oInstantiatedBehaviors)
        if (oBeh instanceof ShortOutput)
          return (ShortOutput) oBeh;
    return null;
  }
  
  /**
   * Gets detailed output.
   * @return Detailed output.
   */
  public DetailedOutput getDetailedOutput() {
    if (mp_oInstantiatedBehaviors.size() > 0)
      for (Behavior oBeh : mp_oInstantiatedBehaviors)
        if (oBeh instanceof DetailedOutput)
          return (DetailedOutput) oBeh;
    return null;
  }
  
  /**
   * Gets whether or not short output is being saved.
   * @return Save status.
   */
  public boolean savingShortOutputData() {
    return getShortOutput().isActive();
  }
  
  /**
   * Displays the output setup options.
   * @param oWindow Main application window.
   */
  public void displayWindow(MainWindow oWindow) {
    OutputSetup oOutputFrame = new OutputSetup(this, oWindow);
    oOutputFrame.pack();
    oOutputFrame.setLocationRelativeTo(oWindow);
    oOutputFrame.setVisible(true);
  }
}
