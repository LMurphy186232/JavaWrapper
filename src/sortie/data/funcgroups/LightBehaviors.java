package sortie.data.funcgroups;

import sortie.data.funcgroups.light.AverageLight;
import sortie.data.funcgroups.light.BasalAreaLight;
import sortie.data.funcgroups.light.ConstantGLI;
import sortie.data.funcgroups.light.GLIBase;
import sortie.data.funcgroups.light.GLILight;
import sortie.data.funcgroups.light.GLIMap;
import sortie.data.funcgroups.light.GLIPoints;
import sortie.data.funcgroups.light.GapLight;
import sortie.data.funcgroups.light.LightFilter;
import sortie.data.funcgroups.light.QuadratGLILight;
import sortie.data.funcgroups.light.SailLight;
import sortie.data.funcgroups.light.StormLight;
import sortie.data.simpletypes.*;
import sortie.gui.GUIManager;

/**
* Manages data for the light behaviors.
* Copyright: Copyright (c) 2011 Charles D. Canham
* Company: Cary Institute of Ecosystem Studies
* @author Lora E. Murphy
* @version 1.0
*
* <br>Edit history:
* <br>------------------
* <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
*/

public class LightBehaviors
extends BehaviorTypeBase {
  
  /**
  * Constructor.
  * @param oManager GUIManager object.
  * @throws ModelException If something goes wrong in setup.
  */
  public LightBehaviors(GUIManager oManager) throws ModelException {
    super(oManager, "Light");
    
    String sXMLRootString, sParFileTag, sDescriptor;
    
    GLIBase.initialize();
            
    //Initialize list of light behaviors
    
    //Average light
    sXMLRootString = "AverageLight";
    sParFileTag = "AverageLight";
    sDescriptor = "Average Light";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(AverageLight.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Basal area light
    sXMLRootString = "BasalAreaLight";
    sParFileTag = "BasalAreaLight";
    sDescriptor = "Basal Area Light";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(BasalAreaLight.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Beer's law light filter
    sXMLRootString = "LightFilter";
    sParFileTag = "LightFilter";
    sDescriptor = "Beer's Law light filter";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(LightFilter.class, sDescriptor, sParFileTag, sXMLRootString));
    
    // Constant GLI
    sXMLRootString = "ConstantGLI";
    sParFileTag = "ConstantGLI";
    sDescriptor = "Constant GLI";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(ConstantGLI.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Gap light
    sXMLRootString = "GapLight";
    sParFileTag = "GapLight";
    sDescriptor = "Gap Light";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(GapLight.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //GLI light
    sXMLRootString = "GLILight";
    sParFileTag = "GLILight";
    sDescriptor = "GLI Light";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(GLILight.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //GLI map creator
    sXMLRootString = "GLIMapCreator";
    sParFileTag = "GLIMapCreator";
    sDescriptor = "GLI Map Creator";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(GLIMap.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //GLI points file creator
    sXMLRootString = "GLIPointCreator";
    sParFileTag = "GLIPointCreator";
    sDescriptor = "GLI Points File Creator";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(GLIPoints.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Quadrat light
    sXMLRootString = "QuadratLight";
    sParFileTag = "QuadratLight";
    sDescriptor = "Quadrat-based GLI light";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(QuadratGLILight.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Sail light
    sXMLRootString = "SailLight";
    sParFileTag = "SailLight";
    sDescriptor = "Sail Light";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(SailLight.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Storm light
    sXMLRootString = "StormLight";
    sParFileTag = "StormLight";
    sDescriptor = "Storm Light";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(StormLight.class, sDescriptor, sParFileTag, sXMLRootString));
                    
  }
  
  /**
   * Override this to trap for common GLI base objects. If the tag is 
   * "GeneralLight", this will find a GLIBase object and return it.
   */
    public Behavior getBehaviorByXMLParametersParentTag(String sXMLTag, int iPos) {
    if (sXMLTag.equals(GLIBase.sGeneralLightParTag)) {
      for (Behavior oBeh : mp_oInstantiatedBehaviors) {
        if (oBeh instanceof GLIBase) return oBeh;
      }
    }
    return super.getBehaviorByXMLParametersParentTag(sXMLTag, iPos);
  }

  /* Unhooks GLIBase behaviors, if necessary.
   */
  public void removeBehavior(Behavior oBeh) {
    if (oBeh instanceof GLIBase) {
      GLIBase oBase = (GLIBase) oBeh;
      oBase.unhook();
    }
    super.removeBehavior(oBeh);
  }

  /**
   * Overridden to add general light parameters if necessary.
   */
  /*public ArrayList<BehaviorParameterDisplay> formatDataForDisplay(
      TreePopulation oPop) {
    ArrayList<BehaviorParameterDisplay> oReturn = super.formatDataForDisplay(oPop);
    for (int i = 0; i < mp_oInstantiatedBehaviors.size(); i++) {
      if (mp_oInstantiatedBehaviors.get(i) instanceof GLIBase) {
        GLIBase oBase = (GLIBase) mp_oInstantiatedBehaviors.get(i);
        if (oBase.isHookedBehavior()) {
          //Add general light parameters at the beginning, so validation
          //will work properly when parameters are read back in
          oReturn.add(0, oBase.formatGeneralLightDataForDisplay(oPop));
        }
      }
    }
    return oReturn;
  }*/

  /**
   * Overridden to read general light parameters if necessary.
   */
 /* public boolean readDataFromDisplay(Vector<TableData> p_oData,
      TreePopulation oPop, String sObjectName, int iListPosition)
      throws ModelException {
    if (sObjectName.equals(GLIBase.sGeneralLightDescriptor)) {
      for (int i = 0; i < mp_oInstantiatedBehaviors.size(); i++) {
        if (mp_oInstantiatedBehaviors.get(i) instanceof GLIBase) {
          mp_oInstantiatedBehaviors.get(i).readDataFromDisplay(p_oData, oPop);
          return true;
        }
      }
    }
    return super.readDataFromDisplay(p_oData, oPop, sObjectName, iListPosition);
  }*/
    
  
}