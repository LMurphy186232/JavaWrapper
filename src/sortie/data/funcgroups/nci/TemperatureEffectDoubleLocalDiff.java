package sortie.data.funcgroups.nci;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * This returns the temperature effect using a double function.
 * is:
 *
 * Temperature Effect = current + prev
 *
 * where both current and prev have the form:
 *
 * effect = a * b.lo ^((ppt-lts)^2) if ppt <  lts
 *        = a * b.hi ^((ppt-lts)^2) if ppt >= lts
 *
 * where:
 * <ul>
 * <li>ppt is the value for temperature, from the plot
 * object; current year for current, previous year for prev</li>
 * <li>b.lo, b.hi, and c are parameters</li>
 * </ul>
 *
 * lts = ltm + c
 * where ltm is the long-term mean of temperature and c is a parameter.
 *
 * a = a.a * a.b.lo ^((ltm-a.c)^2) if ltm <  a.c
 *   = a.a * a.b.hi ^((ltm-a.c)^2) if ltm >= a.c
 *
 * where a.a, a.b.lo, a.b.hi, and a.c are parameters.
 * 
 * @author LORA
 *
 */
public class TemperatureEffectDoubleLocalDiff extends Behavior {

  //----- Temperature effect current a a ------------------------------------//
  protected ModelVector mp_fCurrAA = new ModelVector(
      "Double Local Temp Effect \"curr.a.a\"",
      "nciDoubLocTempEffCurrAA", "ndltecaaVal", 0, ModelVector.FLOAT);

  //----- Temperature effect current a b lo ---------------------------------//
  protected ModelVector mp_fCurrABLo = new ModelVector(
      "Double Local Temp Effect \"curr.a.b.lo\"",
      "nciDoubLocTempEffCurrABLo", "ndltecablVal", 0, ModelVector.FLOAT);

  //----- Temperature effect current a b hi ---------------------------------//
  protected ModelVector mp_fCurrABHi = new ModelVector(
      "Double Local Temp Effect \"curr.a.b.hi\"",
      "nciDoubLocTempEffCurrABHi", "ndltecabhVal", 0, ModelVector.FLOAT);
  
  //----- Temperature effect current a c ------------------------------------//
  protected ModelVector mp_fCurrAC = new ModelVector(
      "Double Local Temp Effect \"curr.a.c\"",
      "nciDoubLocTempEffCurrAC", "ndltecacVal", 0, ModelVector.FLOAT);
  
   
	//----- Temperature effect current b lo -----------------------------------//
	protected ModelVector mp_fCurrBLo = new ModelVector(
			"Double Local Temp Effect \"curr.b.lo\"",
			"nciDoubLocTempEffCurrBLo", "ndltecblVal", 0, ModelVector.FLOAT);

	//----- Temperature effect current b hi -----------------------------------//
	protected ModelVector mp_fCurrBHi = new ModelVector(
			"Double Local Temp Effect \"curr.b.hi\"",
			"nciDoubLocTempEffCurrBHi", "ndltecbhVal", 0, ModelVector.FLOAT);

	//----- Temperature effect current c --------------------------------------//
	protected ModelVector mp_fCurrC = new ModelVector(
			"Double Local Temp Effect \"curr.c\"",
			"nciDoubLocTempEffCurrC", "ndlteccVal", 0, ModelVector.FLOAT);
	
	
  //----- Temperature effect previous a a -----------------------------------//
  protected ModelVector mp_fPrevAA = new ModelVector(
      "Double Local Temp Effect \"prev.a.a\"",
      "nciDoubLocTempEffPrevAA", "ndltepaaVal", 0, ModelVector.FLOAT);

  //----- Temperature effect previous b lo ----------------------------------//
  protected ModelVector mp_fPrevBLo = new ModelVector(
      "Double Local Temp Effect \"prev.b.lo\"",
      "nciDoubLocTempEffPrevBLo", "ndltepblVal", 0, ModelVector.FLOAT);

  //----- Temperature effect previous b hi ----------------------------------//
  protected ModelVector mp_fPrevBHi = new ModelVector(
      "Double Local Temp Effect \"prev.b.hi\"",
      "nciDoubLocTempEffPrevBHi", "ndltepbhVal", 0, ModelVector.FLOAT);

  //----- Temperature effect previous c -------------------------------------//
  protected ModelVector mp_fPrevC = new ModelVector(
      "Double Local Temp Effect \"prev.c\"",
      "nciDoubLocTempEffPrevC", "ndltepcVal", 0, ModelVector.FLOAT);
	
	


	//----- Temperature effect previous b lo ----------------------------------//
	protected ModelVector mp_fPrevBLo = new ModelVector(
			"Double Local Temp Effect \"prev.b.lo\"",
			"nciDoubLocTempEffPrevBLo", "ndltepblVal", 0, ModelVector.FLOAT);

	//----- Temperature effect previous b hi ----------------------------------//
	protected ModelVector mp_fPrevBHi = new ModelVector(
			"Double Local Temp Effect \"prev.b.hi\"",
			"nciDoubLocTempEffPrevBHi", "ndltepbhVal", 0, ModelVector.FLOAT);

	//----- Temperature effect previous c -------------------------------------//
	protected ModelVector mp_fPrevC = new ModelVector(
			"Double Local Temp Effect \"prev.c\"",
			"nciDoubLocTempEffPrevC", "ndltepcVal", 0, ModelVector.FLOAT);
	
	 


 

  //----- Temperature effect previous a b lo --------------------------------//
  FillSpeciesSpecificValue( p_oElement, "nciDoubLocTempEffPrevABLo",
      "ndltepablVal", p_fTempValues, iNumBehaviorSpecies, p_oPop, true );
  //Transfer to the appropriate array buckets
  for ( i = 0; i < iNumBehaviorSpecies; i++ )
    mp_fPrevABLo[p_fTempValues[i].code] = p_fTempValues[i].val;


  //----- Temperature effect previous a b hi --------------------------------//
  FillSpeciesSpecificValue( p_oElement, "nciDoubLocTempEffPrevABHi",
      "ndltepabhVal", p_fTempValues, iNumBehaviorSpecies, p_oPop, true );
  //Transfer to the appropriate array buckets
  for ( i = 0; i < iNumBehaviorSpecies; i++ )
    mp_fPrevABHi[p_fTempValues[i].code] = p_fTempValues[i].val;


  //----- Temperature effect previous a c -----------------------------------//
  FillSpeciesSpecificValue( p_oElement, "nciDoubLocTempEffPrevAC",
      "ndltepacVal", p_fTempValues, iNumBehaviorSpecies, p_oPop, true );
  //Transfer to the appropriate array buckets
  for ( i = 0; i < iNumBehaviorSpecies; i++ )
    mp_fPrevAC[p_fTempValues[i].code] = p_fTempValues[i].val;


  //----- Temperature effect previous b lo ----------------------------------//
  FillSpeciesSpecificValue( p_oElement, "nciDoubLocTempEffPrevBLo",
      "ndltepblVal", p_fTempValues, iNumBehaviorSpecies, p_oPop, true );
  //Transfer to the appropriate array buckets
  for ( i = 0; i < iNumBehaviorSpecies; i++ )
    mp_fPrevBLo[p_fTempValues[i].code] = p_fTempValues[i].val;


  //----- Temperature effect previous b hi ----------------------------------//
  FillSpeciesSpecificValue( p_oElement, "nciDoubLocTempEffPrevBHi",
      "ndltepbhVal", p_fTempValues, iNumBehaviorSpecies, p_oPop, true );
  //Transfer to the appropriate array buckets
  for ( i = 0; i < iNumBehaviorSpecies; i++ )
    mp_fPrevBHi[p_fTempValues[i].code] = p_fTempValues[i].val;


  //----- Temperature effect previous c -------------------------------------//
  FillSpeciesSpecificValue( p_oElement, "nciDoubLocTempEffPrevC",
      "ndltepcVal", p_fTempValues, iNumBehaviorSpecies, p_oPop, true );
  //Transfer to the appropriate array buckets
  for ( i = 0; i < iNumBehaviorSpecies; i++ )
    mp_fPrevC[p_fTempValues[i].code] = p_fTempValues[i].val;



	/**
	 * Constructor.
	 */
	public TemperatureEffectDoubleLocalDiff(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
		super(oManager, oParent, sDescriptor, "", "", "");

		addRequiredData(mp_fCurrA);
    addRequiredData(mp_fCurrBLo);
    addRequiredData(mp_fCurrBHi);
    addRequiredData(mp_fCurrC);
    addRequiredData(mp_fPrevA);
    addRequiredData(mp_fPrevBLo);
    addRequiredData(mp_fPrevBHi);
    addRequiredData(mp_fPrevC);
	}

	/**
	 * Validates the data. Does nothing.
	 * @param oPop TreePopulation object.
	 */
	public void validateData(TreePopulation oPop) throws ModelException {;}
}
