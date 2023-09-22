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
 * effect = a * b.lo ^((ppt-c)^2) if ppt < c
 *        = a * b.hi ^((ppt-c)^2) if ppt >= c
 *
 * where:
 * <ul>
 * <li>ppt is the value for temperature, from the plot
 * object; current year for current, previous year for prev</li>
 * <li>a, b.lo, b.hi, and c are parameters</li>
 * </ul>
 * 
 * @author LORA
 *
 */
public class TemperatureEffectDoubleNoLocalDiff extends Behavior {

	//----- Temperature effect current a --------------------------------------//
	protected ModelVector mp_fCurrA = new ModelVector(
			"Double No Local Temp Effect \"curr.a\"",
			"nciDoubNoLocTempEffCurrA", "ndnltecaVal", 0, ModelVector.FLOAT);

	//----- Temperature effect current b lo -----------------------------------//
	protected ModelVector mp_fCurrBLo = new ModelVector(
			"Double No Local Temp Effect \"curr.b.lo\"",
			"nciDoubNoLocTempEffCurrBLo", "ndnltecblVal", 0, ModelVector.FLOAT);

	//----- Temperature effect current b hi -----------------------------------//
	protected ModelVector mp_fCurrBHi = new ModelVector(
			"Double No Local Temp Effect \"curr.b.hi\"",
			"nciDoubNoLocTempEffCurrBHi", "ndnltecbhVal", 0, ModelVector.FLOAT);

	//----- Temperature effect current c --------------------------------------//
	protected ModelVector mp_fCurrC = new ModelVector(
			"Double No Local Temp Effect \"curr.c\"",
			"nciDoubNoLocTempEffCurrC", "ndnlteccVal", 0, ModelVector.FLOAT);

	//----- Temperature effect previous a -------------------------------------//
	protected ModelVector mp_fPrevA = new ModelVector(
			"Double No Local Temp Effect \"prev.a\"",
			"nciDoubNoLocTempEffPrevA", "ndnltepaVal", 0, ModelVector.FLOAT);

	//----- Temperature effect previous b lo ----------------------------------//
	protected ModelVector mp_fPrevBLo = new ModelVector(
			"Double No Local Temp Effect \"prev.b.lo\"",
			"nciDoubNoLocTempEffPrevBLo", "ndnltepblVal", 0, ModelVector.FLOAT);

	//----- Temperature effect previous b hi ----------------------------------//
	protected ModelVector mp_fPrevBHi = new ModelVector(
			"Double No Local Temp Effect \"prev.b.hi\"",
			"nciDoubNoLocTempEffPrevBHi", "ndnltepbhVal", 0, ModelVector.FLOAT);

	//----- Temperature effect previous c -------------------------------------//
	protected ModelVector mp_fPrevC = new ModelVector(
			"Double No Local Temp Effect \"prev.c\"",
			"nciDoubNoLocTempEffPrevC", "ndnltepcVal", 0, ModelVector.FLOAT);


	/**
	 * Constructor.
	 */
	public TemperatureEffectDoubleNoLocalDiff(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
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
