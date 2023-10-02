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
 * seasonal precipitation, or annual water deficit. The function is:
 *
 * Precipitation Effect = current + prev
 *
 * where both current and prev have the form:
 *
 * effect = a * b.lo ^((ppt-lts)^2) if ppt <  lts
 *        = a * b.hi ^((ppt-lts)^2) if ppt >= lts
 *
 * where:
 * <ul>
 * <li>ppt is the value for Precipitation, from the plot
 * object; current year for current, previous year for prev</li>
 * <li>b.lo, b.hi, and c are parameters</li>
 * </ul>
 *
 * lts = ltm + c
 * where ltm is the long-term mean of Precipitation and c is a parameter.
 *
 * a = a.a * a.b.lo ^((ltm-a.c)^2) if ltm <  a.c
 *   = a.a * a.b.hi ^((ltm-a.c)^2) if ltm >= a.c
 *
 * where a.a, a.b.lo, a.b.hi, and a.c are parameters.
 * 
 * @author LORA
 *
 */
public class PrecipitationEffectDoubleLocalDiff extends Behavior {

  /** Precipitation effect current a a */
  protected ModelVector mp_fCurrAA = new ModelVector(
      "Double Local Precip Effect \"curr.a.a\"",
      "nciDoubLocPrecipEffCurrAA", "ndlpecaaVal", 0, ModelVector.FLOAT);

  /** Precipitation effect current a b lo */
  protected ModelVector mp_fCurrABLo = new ModelVector(
      "Double Local Precip Effect \"curr.a.b.lo\"",
      "nciDoubLocPrecipEffCurrABLo", "ndlpecablVal", 0, ModelVector.FLOAT);

  /** Precipitation effect current a b hi */
  protected ModelVector mp_fCurrABHi = new ModelVector(
      "Double Local Precip Effect \"curr.a.b.hi\"",
      "nciDoubLocPrecipEffCurrABHi", "ndlpecabhVal", 0, ModelVector.FLOAT);
  
  /** Precipitation effect current a c */
  protected ModelVector mp_fCurrAC = new ModelVector(
      "Double Local Precip Effect \"curr.a.c\"",
      "nciDoubLocPrecipEffCurrAC", "ndlpecacVal", 0, ModelVector.FLOAT);
  
   
	/** Precipitation effect current b lo */
	protected ModelVector mp_fCurrBLo = new ModelVector(
			"Double Local Precip Effect \"curr.b.lo\"",
			"nciDoubLocPrecipEffCurrBLo", "ndlpecblVal", 0, ModelVector.FLOAT);

	/** Precipitation effect current b hi */
	protected ModelVector mp_fCurrBHi = new ModelVector(
			"Double Local Precip Effect \"curr.b.hi\"",
			"nciDoubLocPrecipEffCurrBHi", "ndlpecbhVal", 0, ModelVector.FLOAT);

	/** Precipitation effect current c */
	protected ModelVector mp_fCurrC = new ModelVector(
			"Double Local Precip Effect \"curr.c\"",
			"nciDoubLocPrecipEffCurrC", "ndlpeccVal", 0, ModelVector.FLOAT);
	
	
  /** Precipitation effect previous a a */
  protected ModelVector mp_fPrevAA = new ModelVector(
      "Double Local Precip Effect \"prev.a.a\"",
      "nciDoubLocPrecipEffPrevAA", "ndlpepaaVal", 0, ModelVector.FLOAT);

  /** Precipitation effect previous a b lo */
  protected ModelVector mp_fPrevABLo = new ModelVector(
      "Double Local Precip Effect \"prev.a.b.lo\"",
      "nciDoubLocPrecipEffPrevABLo", "ndlpepablVal", 0, ModelVector.FLOAT);

  /** Precipitation effect previous a b hi */
  protected ModelVector mp_fPrevABHi = new ModelVector(
      "Double Local Precip Effect \"prev.a.b.hi\"",
      "nciDoubLocPrecipEffPrevABHi", "ndlpepabhVal", 0, ModelVector.FLOAT);

  /** Precipitation effect previous a c */
  protected ModelVector mp_fPrevAC = new ModelVector(
      "Double Local Precip Effect \"prev.a.c\"",
      "nciDoubLocPrecipEffPrevAC", "ndlpepacVal", 0, ModelVector.FLOAT);

	/** Precipitation effect previous b lo */
	protected ModelVector mp_fPrevBLo = new ModelVector(
			"Double Local Precip Effect \"prev.b.lo\"",
			"nciDoubLocPrecipEffPrevBLo", "ndlpepblVal", 0, ModelVector.FLOAT);

	/** Precipitation effect previous b hi */
	protected ModelVector mp_fPrevBHi = new ModelVector(
			"Double Local Precip Effect \"prev.b.hi\"",
			"nciDoubLocPrecipEffPrevBHi", "ndlpepbhVal", 0, ModelVector.FLOAT);

	/** Precipitation effect previous c */
	protected ModelVector mp_fPrevC = new ModelVector(
			"Double Local Precip Effect \"prev.c\"",
			"nciDoubLocPrecipEffPrevC", "ndlpepcVal", 0, ModelVector.FLOAT);
	
	/** Desired precipitation variable */
  protected ModelEnum m_iPrecipType = new ModelEnum(
      new int[]{2, 1, 0}, 
      new String[]{"Water deficit", "Seasonal precipitation", "Annual precipitation"},
      "Precipitation Type to Use", "nciDoubLocPrecipType");

	/**
	 * Constructor.
	 */
	public PrecipitationEffectDoubleLocalDiff(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
		super(oManager, oParent, sDescriptor, "", "", "");

		addRequiredData(mp_fCurrAA);
		addRequiredData(mp_fCurrABLo);
		addRequiredData(mp_fCurrABHi);
		addRequiredData(mp_fCurrAC);
		addRequiredData(mp_fCurrBLo);
		addRequiredData(mp_fCurrBHi);
		addRequiredData(mp_fCurrC);
		addRequiredData(mp_fPrevAA);
		addRequiredData(mp_fPrevABLo);
		addRequiredData(mp_fPrevABHi);
		addRequiredData(mp_fPrevAC);
		addRequiredData(mp_fPrevBLo);
		addRequiredData(mp_fPrevBHi);
		addRequiredData(mp_fPrevC);
		addRequiredData(m_iPrecipType);
	}

	/**
	 * Validates the data. Does nothing.
	 * @param oPop TreePopulation object.
	 */
	public void validateData(TreePopulation oPop) throws ModelException {;}
}
