package sortie.data.funcgroups;

import java.util.ArrayList;

import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelString;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.behaviorsetup.BehaviorParameterDisplay;

/**
 * Holds clPlot data.
 * @author LORA
 */
public class Plot extends Behavior {
  
  /** Length of cells, in meters. */
  public static final float CELL_LENGTH = (float)8.0;
  
  protected ModelString
  /**Plot title or comment*/
  m_sPlotTitle = new ModelString("", "Plot title", "plot_title");

/**Length of plot in X direction.  X is EW.*/
protected ModelFloat m_fPlotLenX = new ModelFloat(0,
  "Plot Length in the X (E-W) Direction, in meters", "plot_lenX");

/**Length of plot in Y direction.  Y is NS.*/
protected ModelFloat m_fPlotLenY = new ModelFloat(0,
  "Plot Length in the Y (N-S) Direction, in meters", "plot_lenY");

/**Latitude of plot in decimal degrees*/
protected ModelFloat m_fLatitude = new ModelFloat(0,
  "Plot Latitude, in decimal degrees", "plot_latitude");

/**Mean annual precipitation, mm*/
protected ModelFloat m_fMeanAnnualPrecipMm = new ModelFloat(0,
  "Annual Precipitation, mm", "plot_precip_mm_yr");

/**Mean long-term annual precipitation, mm*/
protected ModelFloat m_fLTMAnnualPrecipMm = new ModelFloat(0,
  "Long-term Mean Annual Precipitation, mm", "plot_ltm_precip");

/**Seasonal precipitation*/
protected ModelFloat m_fSeasonalPrecip = new ModelFloat(0,
    "Seasonal Precipitation", "plot_seasonal_precipitation");

/**Long-term Mean Seasonal precipitation*/
protected ModelFloat m_fLTMSeasonalPrecip = new ModelFloat(0,
    "Long-term Mean Seasonal Precipitation", "plot_ltm_seasonal_precipitation");

/**Water deficit*/
protected ModelFloat m_fWaterDeficit = new ModelFloat(0,
    "Water Deficit", "plot_water_deficit");

/**Long-term Mean Water deficit*/
protected ModelFloat m_fLTMWaterDeficit = new ModelFloat(0,
    "Long-term Mean Water Deficit", "plot_ltm_water_deficit");

/**Mean annual temperature, degrees Celsius*/
protected ModelFloat m_fMeanAnnualTempC = new ModelFloat(0,
  "Mean Annual Temperature, degrees C", "plot_temp_C");

/**Long-term Mean annual temperature, degrees Celsius*/
protected ModelFloat m_fLTMAnnualTempC = new ModelFloat(0,
  "Long-term Mean Annual Temperature", "plot_ltm_temp");

/**Annual nitrogen deposition */
protected ModelFloat m_fAnnualNDep = new ModelFloat(0,
    "Annual Nitrogen Deposition", "plot_n_dep");

/**Number of years per timestep*/
protected ModelFloat m_fNumberOfYearsPerTimestep = new ModelFloat(1,
  "Number of years per timestep", "yearsPerTimestep");

/**Number of timesteps for the run*/
protected ModelInt m_iNumTimesteps = new ModelInt(0, "Number of Timesteps",
  "timesteps");

/**Run's random seed*/
protected ModelInt m_iRandomSeed = new ModelInt(0, "Random Seed",
                                              "randomSeed");

/**Current timestep, if a detailed output timestep file has
been fed in.  Not required.*/
protected ModelInt m_iCurrentTimestep = new ModelInt(0, "Current Timestep",
  "rt_timestep");

  /**
   * Constructor
   *  @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public Plot(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "data.plot");
    
    m_bMustHaveTrees = false;

    mp_oAllData.add(m_iNumTimesteps);
    mp_oAllData.add(m_iCurrentTimestep);
    mp_oAllData.add(m_iRandomSeed);
    mp_oAllData.add(m_fNumberOfYearsPerTimestep);
    mp_oAllData.add(m_fPlotLenX);
    mp_oAllData.add(m_fPlotLenY);
    mp_oAllData.add(m_fLatitude);
    mp_oAllData.add(m_fMeanAnnualPrecipMm);
    mp_oAllData.add(m_fSeasonalPrecip);
    mp_oAllData.add(m_fWaterDeficit);
    mp_oAllData.add(m_fMeanAnnualTempC);
    mp_oAllData.add(m_fAnnualNDep);
    mp_oAllData.add(m_fLTMAnnualPrecipMm);
    mp_oAllData.add(m_fLTMSeasonalPrecip);
    mp_oAllData.add(m_fLTMWaterDeficit);
    mp_oAllData.add(m_fLTMAnnualTempC);
    mp_oAllData.add(m_sPlotTitle);
  }
  
  /**
   * Checks for changes to plot size, and tells the GUIManager if there are any.
   * @param p_oData Vector Vector of data
   * @param oPop TreePopulation Tree population object
   * @throws ModelException won't.
   */
  public void readDataFromDisplay(ArrayList<BehaviorParameterDisplay> p_oData, TreePopulation oPop) throws
  ModelException {
    //Capture the existing plot size
    float fX = getPlotXLength(), 
          fY = getPlotYLength();
    super.readDataFromDisplay(p_oData, oPop);
    if (Math.abs(fX - getPlotXLength()) > 0.001 ||
        Math.abs(fY - getPlotYLength()) > 0.001) {
      m_oManager.changeOfPlotResolution(fX, fY, getPlotXLength(), getPlotYLength());
    }
  }

  /**
   * Sets the number of years per timestep.
   * @param fYears Number of years per timestep.
   * @throws ModelException if the number of years per timestep is not greater
   * than 0.
   */
  public void setYearsPerTimestep(float fYears) throws ModelException {
    ModelFloat fTest = new ModelFloat(fYears,
                                      m_fNumberOfYearsPerTimestep.getDescriptor(),
                                      "");
    ValidationHelpers.makeSureGreaterThan(fTest, 0);
    m_fNumberOfYearsPerTimestep.setValue(fYears);
  }

  /**
   * Gets the number of years per timestep.
   * @return Number of years per timestep.
   */
  public float getNumberOfYearsPerTimestep() {
    return m_fNumberOfYearsPerTimestep.getValue();
  }

  /**
   * Sets the plot title.
   * @param sTitle Title of the plot.
   */
  public void setPlotTitle(String sTitle) {
    m_sPlotTitle.setValue(sTitle);
  }

  /**
   * Sets the number of timesteps for a run.
   * @param i The number of timesteps.
   * @throws ModelException when the number of timesteps is less than or equal
   * to zero.
   */
  public void setNumberOfTimesteps(int i) throws ModelException {
    ModelInt iTest = new ModelInt(i, m_iNumTimesteps.getDescriptor(), "");
    ValidationHelpers.makeSureGreaterThan(iTest, 0);
    m_iNumTimesteps.setValue(i);
  }

  /**
   * Gets the number of timesteps for a run.
   * @return The number of timesteps.
   */
  public int getNumberOfTimesteps() {
    return m_iNumTimesteps.getValue();
  }

  /**
   * Sets the random seed for the run.  A value of 0 means a new random seed each time a parameter file is run.
   * @param i The random seed value.
   */
  public void setRandomSeed(int i) {
    m_iRandomSeed.setValue(i);
  }

  /**
   * Sets the plot length in the X (East-West) direction.
   * @param f Length of the plot in the X direction.
   * @throws ModelException if the passed length is less than or equal to 0.
   */
  public void setPlotXLength(float f) throws ModelException {
    ModelFloat fTest = new ModelFloat(f, m_fPlotLenX.getDescriptor(), "");
    ValidationHelpers.makeSureGreaterThan(fTest, 0);
    m_fPlotLenX.setValue(f);
  }

  /**
   * Gets the current plot X (East-West) length.
   * @return Current plot X length.
   */
  public float getPlotXLength() {
    return m_fPlotLenX.getValue();
  }

  /**
   * Sets the plot length in the Y (North-South) direction.
   * @param f Length of the plot in the Y direction.
   * @throws ModelException if the passed length is less than or equal to 0.
   */
  public void setPlotYLength(float f) throws ModelException {
    ModelFloat fTest = new ModelFloat(f, m_fPlotLenY.getDescriptor(), "");
    ValidationHelpers.makeSureGreaterThan(fTest, 0);
    m_fPlotLenY.setValue(f);
  }

  /**
   * Gets the current plot Y (North-South) length.
   * @return Current plot Y length.
   */
  public float getPlotYLength() {
    return m_fPlotLenY.getValue();
  }

  /**
   * Sets the latitude of the plot.
   * @param f Latitude in decimal degrees.
   * @throws ModelException if latitude is not between -90 and 90.
   */
  public void setLatitude(float f) throws ModelException {
    if (f > 90 || f < -90) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                               m_fLatitude.getDescriptor() +
                               " must be between -90 and 90."));
    }
    m_fLatitude.setValue(f);
  }

  /**
   * Validates the data prior to running the model.
   * @throws ModelException if any of the following are not greater than zero:
   * <ul>
   * <li> m_fPlotLenX
   * <li> m_fPlotLenY
   * <li> m_iNumTimesteps
   * <li> m_fNumberOfYearsPerTimestep
   * </ul>
   * or if m_fLatitude is not between -90 and +90.
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureGreaterThan(m_fPlotLenX, 0);
    ValidationHelpers.makeSureGreaterThan(m_fPlotLenY, 0);
    ValidationHelpers.makeSureGreaterThan(m_iNumTimesteps, 0);
    ValidationHelpers.makeSureGreaterThan(m_fNumberOfYearsPerTimestep, 0);
    ValidationHelpers.makeSureIsBounded(m_fLatitude, -90, 90);
  }
}
