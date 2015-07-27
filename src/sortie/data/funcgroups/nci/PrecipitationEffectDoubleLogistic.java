package sortie.data.funcgroups.nci;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * This represents precipitation effect in NCI behaviors calculated with a 
 * double logistic function. This is not meant to be part of the behaviors 
 * collection, but to be accessed by NCI.
 *
 * Precipitation Effect is calculated as:
 * <center><i>Precipitation Effect = (al + ( (1-al)/(1+(bl/ppt)^cl))) * (ah + ( (1-ah)/(1+(ppt/bh)^ch)))</i></center>
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
public class PrecipitationEffectDoubleLogistic extends Behavior {
  
  /**Precipitation effect al.*/
  protected ModelVector mp_fAl = new ModelVector(
      "Double Logistic Precip Effect \"al\"",
      "nciDoubLogPrecipEffAl", "ndlpealVal", 0, ModelVector.FLOAT);
  
  /**Precipitation effect bl.*/
  protected ModelVector mp_fBl = new ModelVector(
      "Double Logistic Precip Effect \"bl\"",
      "nciDoubLogPrecipEffBl", "ndlpeblVal", 0, ModelVector.FLOAT);
  
  /**Precipitation effect cl.*/
  protected ModelVector mp_fCl = new ModelVector(
      "Double Logistic Precip Effect \"cl\"",
      "nciDoubLogPrecipEffCl", "ndlpeclVal", 0, ModelVector.FLOAT);
  
  /**Precipitation effect ah.*/
  protected ModelVector mp_fAh = new ModelVector(
      "Double Logistic Precip Effect \"ah\"",
      "nciDoubLogPrecipEffAh", "ndlpeahVal", 0, ModelVector.FLOAT);
  
  /**Precipitation effect bh.*/
  protected ModelVector mp_fBh = new ModelVector(
      "Double Logistic Precip Effect \"bh\"",
      "nciDoubLogPrecipEffBh", "ndlpebhVal", 0, ModelVector.FLOAT);
  
  /**Precipitation effect ch.*/
  protected ModelVector mp_fCh = new ModelVector(
      "Double Logistic Precip Effect \"ch\"",
      "nciDoubLogPrecipEffCh", "ndlpechVal", 0, ModelVector.FLOAT);
  
  /**Desired precipitation variable*/
  protected ModelEnum m_iPrecipType = new ModelEnum(
      new int[]{2, 1, 0}, 
      new String[]{"Water deficit", "Seasonal precipitation", "Annual precipitation"},
      "Precipitation Type to Use", "nciDoubleLogisticPrecipType");
  
  
  /**
   * Constructor.
   */
  public PrecipitationEffectDoubleLogistic(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fAl);
    addRequiredData(mp_fBl);
    addRequiredData(mp_fCl);
    addRequiredData(mp_fAh);
    addRequiredData(mp_fBh);
    addRequiredData(mp_fCh);
    
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
