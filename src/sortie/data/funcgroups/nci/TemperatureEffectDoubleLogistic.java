package sortie.data.funcgroups.nci;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * This represents temperature effect in NCI behaviors calculated with a 
 * double logistic function. This is not meant to be part of the behaviors 
 * collection, but to be accessed by NCI.
 *
 * Temperature Effect is calculated as:
 * <center><i>Temperature Effect = (al + ( (1-al)/(1+(bl/temp)^cl))) * (ah + ( (1-ah)/(1+(temp/bh)^ch)))</i></center>
 *
 * where:
 * <ul>
 * <li>temp is the mean annual temperature in C, from the plot object</li>
 * <li>al, bl, cl, ah, bh, and ch are parameters</li>
 * </ul>
 * 
 * @author LORA
 *
 */
public class TemperatureEffectDoubleLogistic extends Behavior {
  
  /**Temperature effect al.*/
  protected ModelVector mp_fAl = new ModelVector(
      "Double Logistic Temp Effect \"al\"",
      "nciDoubLogTempEffAl", "ndltealVal", 0, ModelVector.FLOAT);
  
  /**Temperature effect bl.*/
  protected ModelVector mp_fBl = new ModelVector(
      "Double Logistic Temp Effect \"bl\"",
      "nciDoubLogTempEffBl", "ndlteblVal", 0, ModelVector.FLOAT);
  
  /**Temperature effect cl.*/
  protected ModelVector mp_fCl = new ModelVector(
      "Double Logistic Temp Effect \"cl\"",
      "nciDoubLogTempEffCl", "ndlteclVal", 0, ModelVector.FLOAT);
  
  /**Temperature effect ah.*/
  protected ModelVector mp_fAh = new ModelVector(
      "Double Logistic Temp Effect \"ah\"",
      "nciDoubLogTempEffAh", "ndlteahVal", 0, ModelVector.FLOAT);
  
  /**Temperature effect bh.*/
  protected ModelVector mp_fBh = new ModelVector(
      "Double Logistic Temp Effect \"bh\"",
      "nciDoubLogTempEffBh", "ndltebhVal", 0, ModelVector.FLOAT);
  
  /**Temperature effect ch.*/
  protected ModelVector mp_fCh = new ModelVector(
      "Double Logistic Temp Effect \"ch\"",
      "nciDoubLogTempEffCh", "ndltechVal", 0, ModelVector.FLOAT);
  
  
  /**
   * Constructor.
   */
  public TemperatureEffectDoubleLogistic(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fAl);
    addRequiredData(mp_fBl);
    addRequiredData(mp_fCl);
    addRequiredData(mp_fAh);
    addRequiredData(mp_fBh);
    addRequiredData(mp_fCh);
  }

  /**
   * Validates the data. Does nothing.
   * @param oPop TreePopulation object.
   */
  public void validateData(TreePopulation oPop) throws ModelException {;}
}
