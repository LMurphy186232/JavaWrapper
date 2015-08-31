package sortie.data.funcgroups.light;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clBasalAreaLight class.
 * @author lora
 *
 */
public class BasalAreaLight extends Behavior {

  /**Basal area light - tree type: angiosperm (0) or conifer (1)*/
  protected ModelVector mp_iBasalAreaLightWhatType = new ModelVector(
      "Basal Area Light - Species Type", "li_baTreeType", "li_bttVal", 0, ModelVector.MODEL_ENUM, true);

  /**Basal area light "a" parameter for calculating mean GLI from basal area*/
  protected ModelFloat m_fBasalAreaLightA = new ModelFloat(0,
      "Basal Area Light - Mean GLI \"a\" Parameter", "li_baLightA");

  /**Basal area light conifer "b" parameter for calculating mean GLI from
   * basal area*/
  protected ModelFloat m_fBasalAreaLightConiferB = new ModelFloat(0,
      "Basal Area Light - Conifer \"b\" Parameter", "li_baConiferLightB");

  /**Basal area light conifer "c" parameter for calculating mean GLI from
   * basal area*/
  protected ModelFloat m_fBasalAreaLightConiferC = new ModelFloat(0,
      "Basal Area Light - Conifer \"c\" Parameter", "li_baConiferLightC");

  /**Basal area light angiosperm "b" parameter for calculating mean GLI from
   * basal area*/
  protected ModelFloat m_fBasalAreaLightAngiospermB = new ModelFloat(0,
      "Basal Area Light - Angiosperm \"b\" Parameter", "li_baAngiospermLightB");

  /**Basal area light angiosperm "c" parameter for calculating mean GLI from
   * basal area*/
  protected ModelFloat m_fBasalAreaLightAngiospermC = new ModelFloat(0,
      "Basal Area Light - Angiosperm \"c\" Parameter", "li_baAngiospermLightC");

  /**Basal area light sigma parameter for lognormal PDF*/
  protected ModelFloat m_fBasalAreaLightSigma = new ModelFloat(0,
      "Basal Area Light - Lognormal PDF Sigma", "li_baLightSigma");

  /**Basal area light minimum DBH (cm) for a tree counting towards the
   * basal area*/
  protected ModelFloat m_fBasalAreaLightMinDBH = new ModelFloat(0,
      "Basal Area Light - Minimum DBH for Trees", "li_baLightMinDBH");

  /**Basal area light basal threshold, in square meters, for new trees in grid
   * cell for recalculating GLI*/
  protected ModelFloat m_fBasalAreaLightChangeThreshold = new ModelFloat(0,
      "Basal Area Light - Minimum BA Change for New GLI (m2)",
  "li_baLightChangeThreshold");

  /**Basal area light search radius for neighbors*/
  protected ModelFloat m_fBasalAreaSearchRadius = new ModelFloat(0,
      "Basal Area Light - Search Radius for Neighbors (m)",
  "li_baLightSearchRadius");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public BasalAreaLight(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "light_behaviors.basal_area_light");

    m_fMinVersion = 1.0;
    m_fVersion = 1.0;
    mp_oNewTreeDataMembers.add(new DataMember("Light level", "Light", DataMember.FLOAT));

    addRequiredData(m_fBasalAreaLightA);
    addRequiredData(m_fBasalAreaLightAngiospermB);
    addRequiredData(m_fBasalAreaLightAngiospermC);
    addRequiredData(m_fBasalAreaLightConiferB);
    addRequiredData(m_fBasalAreaLightConiferC);
    addRequiredData(m_fBasalAreaLightChangeThreshold);
    addRequiredData(m_fBasalAreaLightMinDBH);
    addRequiredData(m_fBasalAreaLightSigma);
    addRequiredData(mp_iBasalAreaLightWhatType);
    addRequiredData(m_fBasalAreaSearchRadius);

    //Grids
    Grid oNewGrid;

    //Basal area light
    String sGridName = "Basal Area Light";

    //Data member
    DataMember[] p_oDataMembers = new DataMember[3];
    p_oDataMembers[0] = new DataMember("Light", "Light", DataMember.FLOAT);
    p_oDataMembers[1] = new DataMember("Conifer Basal Area", "Con BA", DataMember.FLOAT);
    p_oDataMembers[2] = new DataMember("Angiosperm Basal Area", "Ang BA", DataMember.FLOAT);
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);

    //Assign to Basal Area Light
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid, false);

    ModelEnum oEnum;
    TreePopulation oPop = m_oManager.getTreePopulation();
    int iNumSpecies = oPop.getNumberOfSpecies(), i;
    for (i = 0; i < iNumSpecies; i++) {
      oEnum = new ModelEnum(new int[] {1, 0},
          new String[] {"Conifer", "Angiosperm"},
          "Species type", "");
      oEnum.setValue("Conifer");
      mp_iBasalAreaLightWhatType.getValue().add(oEnum);
    }
  }
  
  /**
   * Overridden to handle enums.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) throws ModelException {
    
  super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    ModelEnum oEnum;
    int i;
    for (i = 0; i < p_sNewSpecies.length; i++) {
      if (null == mp_iBasalAreaLightWhatType.getValue().get(i)) {
        oEnum = new ModelEnum(new int[] {1, 0},
            new String[] {"Conifer", "Angiosperm"},
            "Species type", "");
        oEnum.setValue("Conifer");
        mp_iBasalAreaLightWhatType.getValue().remove(i);
        mp_iBasalAreaLightWhatType.getValue().add(i, oEnum);
      }
    }
  }


  /**
   * Validates the data.
   * @param oPop TreePopulation object
   * @throws ModelException in any of the following cases:
   * <ul>
   * <li>The value for either c cannot equal 0.</li>
   * <li>The basal area change threshold cannot be less than 0.</li>
   * <li>The minimum DBH cannot be less than 0.</li>
   * <li>Grid cell lengths of "Basal Area Light" must divide evenly by the plot
   * lengths.</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {

    ValidationHelpers.makeSureNotEqualTo(m_fBasalAreaLightAngiospermC, 0);
    ValidationHelpers.makeSureNotEqualTo(m_fBasalAreaLightConiferC, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fBasalAreaLightMinDBH, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fBasalAreaLightChangeThreshold, 0);
    //Make sure the grid cell lengths of the basal light grid divide
    //evenly into the plot lengths
    Grid oGrid = m_oManager.getGridByName("Basal Area Light");
    if (oGrid == null)
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "I couldn't find the \"Basal Area Light\" grid."));
    Plot oPlot = m_oManager.getPlot();
    if (oPlot.getPlotXLength() % oGrid.getXCellLength() > 0.001 ||
        oPlot.getPlotYLength() % oGrid.getYCellLength() > 0.001)
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The grid cell lengths for the " +
          oGrid.getName() + " grid must divide evenly into the plot lengths."));
  }
}
