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
  protected ModelVector mp_fCurrBLo = new ModelVector(
      "Double No Local Precip Effect \"curr b lo\"",
      "nciDoubNoLocPrecipEffCurrBLo", "ndnlpecblVal", 0, ModelVector.FLOAT);
  
  FillSpeciesSpecificValue( p_oElement, "nciDoubNoLocPrecipEffCurrC",
      "ndnlpeccVal", p_fTempValues, iNumBehaviorSpecies, p_oPop, true );
  //Transfer to the appropriate array buckets
  for ( i = 0; i < iNumBehaviorSpecies; i++ )
    mp_fCurrC[p_fTempValues[i].code] = p_fTempValues[i].val;


  //----- Precipitation effect previous a -----------------------------------//
  protected ModelVector mp_fCurrBLo = new ModelVector(
      "Double No Local Precip Effect \"curr b lo\"",
      "nciDoubNoLocPrecipEffCurrBLo", "ndnlpecblVal", 0, ModelVector.FLOAT);
  
  FillSpeciesSpecificValue( p_oElement, "nciDoubNoLocPrecipEffPrevA",
      "ndnlpepaVal", p_fTempValues, iNumBehaviorSpecies, p_oPop, true );
  //Transfer to the appropriate array buckets
  for ( i = 0; i < iNumBehaviorSpecies; i++ )
    mp_fPrevA[p_fTempValues[i].code] = p_fTempValues[i].val;


  //----- Precipitation effect previous b lo --------------------------------//
  protected ModelVector mp_fCurrBLo = new ModelVector(
      "Double No Local Precip Effect \"curr b lo\"",
      "nciDoubNoLocPrecipEffCurrBLo", "ndnlpecblVal", 0, ModelVector.FLOAT);
  
  FillSpeciesSpecificValue( p_oElement, "nciDoubNoLocPrecipEffPrevBLo",
      "ndnlpepblVal", p_fTempValues, iNumBehaviorSpecies, p_oPop, true );
  //Transfer to the appropriate array buckets
  for ( i = 0; i < iNumBehaviorSpecies; i++ )
    mp_fPrevBLo[p_fTempValues[i].code] = p_fTempValues[i].val;


  //----- Precipitation effect previous b hi --------------------------------//
  protected ModelVector mp_fCurrBLo = new ModelVector(
      "Double No Local Precip Effect \"curr b lo\"",
      "nciDoubNoLocPrecipEffCurrBLo", "ndnlpecblVal", 0, ModelVector.FLOAT);
  
  FillSpeciesSpecificValue( p_oElement, "nciDoubNoLocPrecipEffPrevBHi",
      "ndnlpepbhVal", p_fTempValues, iNumBehaviorSpecies, p_oPop, true );
  //Transfer to the appropriate array buckets
  for ( i = 0; i < iNumBehaviorSpecies; i++ )
    mp_fPrevBHi[p_fTempValues[i].code] = p_fTempValues[i].val;


  //----- Precipitation effect previous c -----------------------------------//
  protected ModelVector mp_fCurrBLo = new ModelVector(
      "Double No Local Precip Effect \"curr b lo\"",
      "nciDoubNoLocPrecipEffCurrBLo", "ndnlpecblVal", 0, ModelVector.FLOAT);
  
  FillSpeciesSpecificValue( p_oElement, "nciDoubNoLocPrecipEffPrevC",
      "ndnlpepcVal", p_fTempValues, iNumBehaviorSpecies, p_oPop, true );
  //Transfer to the appropriate array buckets
  for ( i = 0; i < iNumBehaviorSpecies; i++ )
    mp_fPrevC[p_fTempValues[i].code] = p_fTempValues[i].val;


  delete[] p_fTempValues;

  //Precip value desired
  iPrecipType = mean_precip;
  FillSingleValue(p_oElement, "nciDoubNoLocPrecipType", &iPrecipType, true);

  
  
  
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
  public PrecipitationEffectDoubleNoLocalDiff(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
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
