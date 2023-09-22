package sortie.data.funcgroups.nci;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * This returns the precipitation effect using a double function. Three
 * possible precipitation values can be used: mean annual precipitation,
 * seasonal precipitation, or annual water deficit. The function
 * is:
 *
 * Precipitation Effect = current + prev
 *
 * where both current and prev have the form:
 *
 * effect = a * b.lo ^((ppt-c)^2) if ppt < c
 *        = a * b.hi ^((ppt-c)^2) if ppt >= c
 *
 * where:
 * <ul>
 * <li>ppt is the value for the desired form of precipitation, from the plot
 * object; current year for current, previous year for prev; in meters</li>
 * <li>a, b.lo, b.hi, and c are parameters</li>
 * </ul>
 * 
 * @author LORA
 *
 */
public class PrecipitationEffectDoubleNoLocalDiff extends Behavior {
  
  //----- Precipitation effect current a ------------------------------------//
  protected ModelVector mp_fCurrA = new ModelVector(
      "Double No Local Precip Effect \"curr.a\"",
      "nciDoubNoLocPrecipEffCurrA", "ndnlpecaVal", 0, ModelVector.FLOAT);
  
  //----- Precipitation effect current b lo ---------------------------------//
  protected ModelVector mp_fCurrBLo = new ModelVector(
      "Double No Local Precip Effect \"curr.b.lo\"",
      "nciDoubNoLocPrecipEffCurrBLo", "ndnlpecblVal", 0, ModelVector.FLOAT);
  
  //----- Precipitation effect current b hi ---------------------------------//
  protected ModelVector mp_fCurrBHi = new ModelVector(
      "Double No Local Precip Effect \"curr.b.hi\"",
      "nciDoubNoLocPrecipEffCurrBHi", "ndnlpecbhVal", 0, ModelVector.FLOAT);
    
  //----- Precipitation effect current c ------------------------------------//
  protected ModelVector mp_fCurrC = new ModelVector(
      "Double No Local Precip Effect \"curr.c\"",
      "nciDoubNoLocPrecipEffCurrC", "ndnlpeccVal", 0, ModelVector.FLOAT);
  
  //----- Precipitation effect previous a -----------------------------------//
  protected ModelVector mp_fPrevA = new ModelVector(
      "Double No Local Precip Effect \"prev.a\"",
      "nciDoubNoLocPrecipEffPrevA", "ndnlpepaVal", 0, ModelVector.FLOAT);
  
  //----- Precipitation effect previous b lo --------------------------------//
  protected ModelVector mp_fPrevBLo = new ModelVector(
      "Double No Local Precip Effect \"prev.b.lo\"",
      "nciDoubNoLocPrecipEffPrevBLo", "ndnlpepblVal", 0, ModelVector.FLOAT);
  
  //----- Precipitation effect previous b hi --------------------------------//
  protected ModelVector mp_fPrevBHi = new ModelVector(
      "Double No Local Precip Effect \"prev.b.hi\"",
      "nciDoubNoLocPrecipEffPrevBHi", "ndnlpepbhVal", 0, ModelVector.FLOAT);

  //----- Precipitation effect previous c -----------------------------------//
  protected ModelVector mp_fPrevC = new ModelVector(
      "Double No Local Precip Effect \"prev.c\"",
      "nciDoubNoLocPrecipEffPrevC", "ndnlpepcVal", 0, ModelVector.FLOAT);
   
  /**Desired precipitation variable*/
  protected ModelEnum m_iPrecipType = new ModelEnum(
      new int[]{2, 1, 0}, 
      new String[]{"Water deficit", "Seasonal precipitation", "Annual precipitation"},
      "Precipitation Type to Use", "nciDoubNoLocPrecipType");
  
  
  /**
   * Constructor.
   */
  public PrecipitationEffectDoubleNoLocalDiff(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fCurrA);
    addRequiredData(mp_fCurrBLo);
    addRequiredData(mp_fCurrBHi);
    addRequiredData(mp_fCurrC);
    addRequiredData(mp_fPrevA);
    addRequiredData(mp_fPrevBLo);
    addRequiredData(mp_fPrevBHi);
    addRequiredData(mp_fPrevC);
    addRequiredData(m_iPrecipType);
    
    //Default precipitation type to annual
    m_iPrecipType.setValue(0);
  }

  /**
   * Validates the data. Does nothing.
   * @param oPop TreePopulation object.
   */
  public void validateData(TreePopulation oPop) throws ModelException {;}
}
