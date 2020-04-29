package sortie.datavisualizer;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

import java.awt.event.ActionListener;
import java.io.IOException;
import org.xml.sax.helpers.XMLReaderFactory;

import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.simpletypes.*;
import sortie.gui.ErrorGUI;
import sortie.gui.MainWindowStateSetter;
import sortie.gui.components.SortieFont;
import sortie.gui.components.SortieMenuItem;
import sortie.tools.batchoutput.ChartInfo;
//import sortie.tools.batchoutput.ChartInfo;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import com.ice.tar.TarInputStream;
import com.ice.tar.TarEntry;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;

import java.util.ArrayList;


/**
 * Controls the data visualization for a single detailed output file.
 * Copyright: Copyright (c) Charles D. Canham 2012
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.3
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM) 
 */

public class DetailedOutputFileManager
    extends DataFileManager {
  
  /**The collection of all data requests currently open*/
  private ArrayList<DataRequest> mp_oDataRequests = new ArrayList<DataRequest>(0);

  /**Species names - the order determines the species codes*/
  private ArrayList<String> mp_sSpeciesNames = new ArrayList<String>(0);

  /**Collection of DetailedTreeSettings objects*/
  private ArrayList<DetailedTreeSettings> mp_oTreeSettings = new ArrayList<DetailedTreeSettings>(0);

  /**Collection of DetailedGridSettings objects*/
  private ArrayList<DetailedGridSettings> mp_oGridSettings = new ArrayList<DetailedGridSettings>(0);

  /**Whether each histogram option is float (true) or int (false)*/
  private boolean[] mp_bHistogramIsFloat;

  /**Histogram names - so we can figure out which value in mp_bHistogramIsFloat
   * value to use*/
  private String[] mp_sHistogramNames;
  
  /**All the map names, so we can quickly check if an option is valid*/
  private String[] mp_sMapNames;
  
  /**All the table names, so we can quickly check if an option is valid*/
  private String[] mp_sTableNames;
  
  /**All the line graph names, so we can quickly check if an option is valid*/
  private String[] mp_sLineGraphNames;
  
  /**Dead reason codes for each tree data member*/
  private int[] mp_iDeadCodes;

  /**Display string of filename - should have the last 40 chars.*/
  private String m_sShortFileDisplayName;
  /**Display string of filename - should have the last 90 chars.*/
  private String m_sLongFileDisplayName;
  private XMLReader m_oParser; /**<File parser*/
  private String m_sTempRoot, /**<Root directory to put temp files*/
      m_sTempDir, /**<Where we actually extract to*/
      m_sFileRoot; /**<Root file name of all detailed output files*/
  private float
      m_fPlotArea = 0, /**<Area of plot (or subplot) in hectares*/
      m_fXPlotLength = 0, /**<Plot X length*/
      m_fYPlotLength = 0, /**<Plot Y length*/
      /**X length of subplot cells, in m, if this is a subplot*/
      m_fSubplotXCellLength = 8,  
      /**Y length of subplot cells, in m, if this is a subplot*/
      m_fSubplotYCellLength = 8,
      m_fNumYearsPerTimestep; /**<Number of years per timestep*/
  private int m_iNumTimesteps, /**<Number of actual timesteps of data*/
      m_iNumParFileTimesteps, /**<Number of timestes from the par file*/
      m_iNumTypes = 7, /**<Number of tree types*/
      m_iLastTimestepRead; /**<When updating charts, this is the file parsed*/
  
  /**Whether not this is working in the context of batch output extraction*/
  private boolean m_bBatchMode = false;
  
  /**
   * Constructor.
   * @param oManager Data file manager.
   * @param sTarball  Detailed output file to manage.
   * @throws ModelException If there was a problem reading the file.
   */
  public DetailedOutputFileManager(DataVisualizerManager oManager, String sTarball) throws ModelException {
    super(oManager, sTarball);
    try {

      m_iLastTimestepRead = 0;

      //Put together the root temp directory - the application's directory
      //plus temp
      String sPath = System.getProperty("SORTIE_PATH");
      String sSeparator = System.getProperty("file.separator");
      if (sPath == null) {
        m_sTempRoot = sSeparator + "temp";
      }
      else {
        if (sPath.endsWith(sSeparator) == false) {
          sPath += sSeparator;
        }
        m_sTempRoot = sPath + "temp";
      }
      
      //Get the root filename
      m_sFileRoot = sTarball.substring(0, sTarball.indexOf(".gz.tar"));
      
      //Create a display name
      if (m_sFileRoot.length() > 40) {
        //Take the LAST 40, minus the extension
        String sText = m_sFileRoot.substring(m_sFileRoot.length() - 40);
        m_sShortFileDisplayName = "..." + sText;
      }
      else {
        m_sShortFileDisplayName = m_sFileRoot;
      }
      if (m_sFileRoot.length() > 90) {
        //Take the LAST 90, minus the extension
        String sText = m_sFileRoot.substring(m_sFileRoot.length() - 90);
        m_sLongFileDisplayName = "..." + sText;
      }
      else {
        m_sLongFileDisplayName = m_sFileRoot;
      }

      m_sTempDir = sortie.fileops.Tarball.extractTarball(m_sFilename, m_sTempRoot);

      //Create our parser
      //Create the parser this way - don't go through the SAXParserFactory!
      //Parsers created that way throw MalformedURLExceptions when asked to
      //read local files.
      m_oParser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");

      parseParamFile();
      countTimesteps();
      
      //Update area calculation
      if (m_fPlotArea < 0.0001) {
        m_fPlotArea = (m_fXPlotLength * m_fYPlotLength)/10000;
      } else {
        //This was a subplot and area is in square meters - transform to hectares
        m_fPlotArea /= 10000;
      }

      //Create the legend
      String[] sSpeciesNames = new String[mp_sSpeciesNames.size()];
      for (int i = 0; i < mp_sSpeciesNames.size(); i++) {
        sSpeciesNames[i] = mp_sSpeciesNames.get(i);
      }

      String sFileName = new java.io.File(m_sFilename).getName();
      m_oLegend = new DetailedOutputLegend(this, sFileName, sSpeciesNames,
                                           m_iNumTimesteps);

    }
    catch (SAXException e) {
      throw(new ModelException(ErrorGUI.UNKNOWN, "JAVA",
      "There were problems constructing the XML parser."));
    }
  }
  
  /**
   * Constructor, for testing.
   * @param sParFile Filename of a parameter file.
   * @throws ModelException If there was a problem reading the file.
   */
  public DetailedOutputFileManager(String sParFile) throws ModelException {
    super(null, sParFile);
    try {

      m_iLastTimestepRead = 0;

      //Put together the root temp directory - the application's directory
      //plus temp
      String sPath = System.getProperty("SORTIE_PATH");
      String sSeparator = System.getProperty("file.separator");
      if (sPath == null) {
        m_sTempRoot = sSeparator + "temp";
      }
      else {
        if (sPath.endsWith(sSeparator) == false) {
          sPath += sSeparator;
        }
        m_sTempRoot = sPath + "temp";
      }
      
      //Get the root filename
      m_sFileRoot = sParFile.substring(0, sParFile.indexOf(".xml"));
      
      //Create a display name
      if (m_sFileRoot.length() > 40) {
        //Take the LAST 40, minus the extension
        String sText = m_sFileRoot.substring(m_sFileRoot.length() - 40);
        m_sShortFileDisplayName = "..." + sText;
      }
      else {
        m_sShortFileDisplayName = m_sFileRoot;
      }
      if (m_sFileRoot.length() > 90) {
        //Take the LAST 90, minus the extension
        String sText = m_sFileRoot.substring(m_sFileRoot.length() - 90);
        m_sLongFileDisplayName = "..." + sText;
      }
      else {
        m_sLongFileDisplayName = m_sFileRoot;
      }

      m_sTempDir = sortie.fileops.Tarball.extractTarball(m_sFilename, m_sTempRoot);

      //Create our parser
      //Create the parser this way - don't go through the SAXParserFactory!
      //Parsers created that way throw MalformedURLExceptions when asked to
      //read local files.
      m_oParser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");

      parseParamFile();
      countTimesteps();
      
      //Update area calculation
      if (m_fPlotArea < 0.0001) {
        m_fPlotArea = (m_fXPlotLength * m_fYPlotLength)/10000;
      } else {
        //This was a subplot and area is in square meters - transform to hectares
        m_fPlotArea /= 10000;
      }

      //Create the legend
      String[] sSpeciesNames = new String[mp_sSpeciesNames.size()];
      for (int i = 0; i < mp_sSpeciesNames.size(); i++) {
        sSpeciesNames[i] = mp_sSpeciesNames.get(i);
      }

      String sFileName = new java.io.File(m_sFilename).getName();
      m_oLegend = new DetailedOutputLegend(this, sFileName, sSpeciesNames,
                                           m_iNumTimesteps);

    }
    catch (SAXException e) {
      throw(new ModelException(ErrorGUI.UNKNOWN, "JAVA",
      "There were problems constructing the XML parser."));
    }
  }

  /**
   * Sets the number of timesteps for the detailed output file parameter file.
   * @param i Number of timesteps.
   */
  public void setParFileTimesteps(int i) {
    m_iNumParFileTimesteps = i;
  }

  /**
   * Gets the number of actual data timesteps contained in this detailed output
   * file (as opposed to how many the par file says there are).
   * @return int Number of timesteps for this detailed output file.
   */
  public int getNumberOfActualTimesteps() {
    return m_iNumTimesteps;
  }
  
  /**
   * Sets the subplot X cell length.
   * @param f X cell length, in m.
   */
  public void setSubplotXCellLength(float f) {
  	m_fSubplotXCellLength = f;
  }
  
  /**
   * Sets the subplot Y cell length.
   * @param f Y cell length, in m.
   */
  public void setSubplotYCellLength(float f) {
  	m_fSubplotYCellLength = f;
  }
  
  /**
   * Checks to see if a subplot name indicates that the detailed output file 
   * being managed is in fact for that subplot. If it were, the subplot name
   * would be the last part of the tarball name preceded by an underscore.
   * @param sSubplotName Name of possible subplot for this file.
   * @return True if this is a subplot with that name, false otherwise.
   */
  public boolean isThisSubplot(String sSubplotName) {
  	if (m_sFileRoot.endsWith("_" + sSubplotName))
  		return true;
  	return false;
  }
  
  /**
   * Gets the area of this plot or subplot, in ha.
   * @return Plot area in ha.
   */
  public float getPlotArea() {
  	return m_fPlotArea;
  }
  
  public static JPanel getOptionsPanel(String sGraphName) throws ModelException {
    if (sGraphName.equals("BA Stand Table") ||
        sGraphName.equals("Density Stand Table")) {
      return StandTableDataRequest.makeSizeClassPanel();
    } else if (sGraphName.equals("Stock Table")) {
      return StockTableDataRequest.makeSizeClassPanel();
    } else if (sGraphName.endsWith("Histogram")) {
      if (sGraphName.startsWith("Grid")) {
        if (sGraphName.indexOf(" All ") > -1) {
          return GridAllSpeciesHistogramDataRequest.makeBatchControlsPanel();
        } else {
          return GridHistogramDataRequest.makeBatchControlsPanel();
        }
      } else {
        return HistogramDataRequest.makeBatchControlsPanel();
      }
    }
    return null;
  }
  
  /**
   * Adds a new item to the data request list. This is the same as drawChart 
   * but does not actually open or draw a chart. This will verify that the 
   * option is possible for this file before adding it. No file parsing is
   * triggered.
   * @param oInfo Info for chart to draw.
   * @throws ModelException If anything goes wrong.
   */
  public void addBatchData(ChartInfo oInfo) throws ModelException {
    //Check to see if this is a valid option
    boolean bOK = false;
    if (mp_sHistogramNames != null && mp_sHistogramNames.length > 0) {
      for (String sOption : mp_sHistogramNames) {
        if (oInfo.m_sGraphName.equals(sOption)) {
          bOK = true; break;
        }      
      }
    }
    if (!bOK && mp_sLineGraphNames != null && mp_sLineGraphNames.length > 0) {
      for (String sOption : mp_sLineGraphNames) {
        if (oInfo.m_sGraphName.equals(sOption)) {
          bOK = true; break;
        }      
      }
    }
    if (!bOK && mp_sMapNames != null && mp_sMapNames.length > 0) {
      for (String sOption : mp_sMapNames) {
        if (oInfo.m_sGraphName.equals(sOption)) {
          bOK = true; break;
        }      
      }
    }
    if (!bOK && mp_sTableNames != null && mp_sTableNames.length > 0) {
      for (String sOption : mp_sTableNames) {
        if (oInfo.m_sGraphName.equals(sOption)) {
          bOK = true; break;
        }      
      }
    }
    if (!bOK) return;
    DataRequest oRequest = interpretGraphName(oInfo.m_sGraphName);
    oRequest.extractBatchSetupInfo(oInfo);
    mp_oDataRequests.add(oRequest);
  }
  
  /**
   * Does batch data extraction of all data requests.
   */
  public void doBatchExtraction() throws ModelException {
    m_bBatchMode = true;
    
    ArrayList<FileWriter> p_jOut = new ArrayList<FileWriter>(mp_oDataRequests.size());
    int i, iTs;
    DataRequest oRequest;
        
    try {
      //Create each file and write the header
      for (i = 0; i < mp_oDataRequests.size(); i++) {
        oRequest = mp_oDataRequests.get(i);
        
        if (oRequest.isFileWritingExternallyManaged()) {

          //Get this output file's root name to add to the batch file name root                
          String sDetailedOutputRoot = getFileName();
          if (sDetailedOutputRoot.lastIndexOf("\\") > -1) {
            sDetailedOutputRoot = sDetailedOutputRoot.substring(sDetailedOutputRoot.lastIndexOf("\\") + 1);
          }
          if (sDetailedOutputRoot.lastIndexOf("/") > -1) {
            sDetailedOutputRoot = sDetailedOutputRoot.substring(sDetailedOutputRoot.lastIndexOf("/") + 1);
          }
          if (sDetailedOutputRoot.lastIndexOf(".gz.tar") > -1) {
            sDetailedOutputRoot = sDetailedOutputRoot.substring(0, sDetailedOutputRoot.lastIndexOf(".gz.tar"));          
          }

          //Get the batch filename ready - check for an extension to append
          String sFileName = oRequest.m_sBatchFilename;
          int iPos = sFileName.lastIndexOf(".");
          String sExt = "";
          if (iPos == sFileName.length() - 4 ||
              iPos == sFileName.length() - 5) {
            sExt = sFileName.substring(iPos);
            sFileName = oRequest.m_sBatchFilename.substring(0, iPos);
          }
          sFileName = sFileName + "_" + sDetailedOutputRoot + sExt;

          p_jOut.add(i, new FileWriter(sFileName));

          // Start with the file header - reconstitute the full file name
          String sTitle = oRequest.m_sChartName;
          sTitle = sTitle + " - " + getFileName();
          p_jOut.get(i).write(sTitle + "\n");
        } else {
          p_jOut.add(null);
        }
      }

      // Write the whole run to file
      for (iTs = 0; iTs <= m_iNumTimesteps; iTs++) {

        readFile(iTs);
        for (i = 0; i < mp_oDataRequests.size(); i++) {        
          oRequest = mp_oDataRequests.get(i);
          if (oRequest.m_bShowOneTimestep && 
              oRequest.isFileWritingExternallyManaged()) {
            // Write a line for the current timestep
            p_jOut.get(i).write("Timestep: \t" + String.valueOf(iTs) + "\n");

            // Have the chart write this timestep's data
            oRequest.writeChartDataToFile(p_jOut.get(i));
          }
        }
      }
      
      for (i = 0; i < mp_oDataRequests.size(); i++) {        
        oRequest = mp_oDataRequests.get(i);
        oRequest.outputFileParseFinished(m_bBatchMode);
        if (!oRequest.m_bShowOneTimestep && 
            oRequest.isFileWritingExternallyManaged()) {
          oRequest.writeChartDataToFile(p_jOut.get(i));
        }
      }
    }
    catch (IOException oErr) {
      throw new ModelException(ErrorGUI.BAD_FILE, "Java", oErr.getMessage());
    }
    finally {
      m_bBatchMode = false;
      try {
        for (FileWriter jOut : p_jOut)
        if (jOut != null) {
          jOut.close();
        }
      }
      catch (java.io.IOException oErr) {
        throw new ModelException(ErrorGUI.BAD_FILE, "Java", oErr.getMessage());
      }
    }  
  }

  /**
   * Handles the request to draw a chart for this file.  The request will be
   * passed on to an appropriate DataRequest object.  If the chart is already
   * open, the open chart will be returned.
   * @param sGraphName Name of chart to draw.
   * @return The drawn chart, or NULL if it could not be drawn.
   * @throws ModelException If anything goes wrong.
   */
  public JInternalFrame drawChart(String sGraphName) throws ModelException {

    DataRequest oRequest = null;
    boolean bNeedShortFileDisplayName = false;

    //See if the chart wanted is already open
    int i;
    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      if (sGraphName.equals(oRequest.getChartName())) {
        return oRequest.getChart();
      }
    }
    
    oRequest = interpretGraphName(sGraphName);
    if (null == oRequest) {
      throw (new ModelException(ErrorGUI.BAD_COMMAND, "JAVA", "The graph \"" + 
          sGraphName + "\" is not supported for the file " + m_sFilename));
    }
    
    if (oRequest instanceof TreeListWriter ||
        oRequest instanceof HarvestDataRequest ||
        oRequest instanceof WindstormDataRequest ||
        oRequest instanceof MerchValueDataRequest ||
        oRequest instanceof CarbonValueDataRequest ||
        oRequest instanceof PartitionedBiomassTableDataRequest ||
        oRequest instanceof FoliarChemistryTableDataRequest ||
        oRequest instanceof SeedTableDataRequest ||
        oRequest instanceof StormKilledPartitionedBiomassDataRequest ||
        oRequest instanceof StormDataRequest ||
        oRequest instanceof StateVariableTableDataRequest || 
        oRequest instanceof SeedTableDataRequest ||
        oRequest instanceof StormKilledPartitionedBiomassDataRequest ||
        oRequest instanceof StormDataRequest ||
        oRequest instanceof StateVariableTableDataRequest ||
        oRequest instanceof LineGraphDataRequest ||
        oRequest instanceof RipleysKDataRequest ||
        oRequest instanceof RelativeNeighborhoodDensityDataRequest ||
        oRequest instanceof FoliarChemistryLineGraphDataRequest ||
        oRequest instanceof PartitionedBiomassLineGraphDataRequest ||
        oRequest instanceof SubstrateLineGraphDataRequest) {
      bNeedShortFileDisplayName = true;
    }

    mp_oDataRequests.add(oRequest);

    //Parse our file
    readFile(m_iLastTimestepRead);
    if (bNeedShortFileDisplayName)
      return oRequest.drawChart(m_oLegend,
          sGraphName + " - " + m_sShortFileDisplayName);
    else 
      return oRequest.drawChart(m_oLegend,
          sGraphName + " - " + m_sLongFileDisplayName);
  }
  
  /**
   * Updates charts for a current run.  This re-inflates the tarball and gets
   * the last timestep file for parsing and processing.
   * @throws ModelException if there is a problem reading the file or
   * drawing the charts.
   */
  public void updateCurrentRunCharts() throws ModelException {
    if (mp_oCharts.size() == 0) {
      return;
    }

    //Get the new number of timesteps
    countTimesteps();

    //Extract the tarball again
    sortie.fileops.Tarball.extractTarball(m_sFilename, m_sTempRoot);

    //Advance through the legend
    DetailedOutputLegend oLegend = (DetailedOutputLegend) m_oLegend;
    oLegend.setNumberOfTimesteps(m_iNumTimesteps);

    ( (DetailedOutputLegend) m_oLegend).goToTimestep(m_iNumTimesteps);
  }

  /**
   * Gets the tables that can be drawn for this file.
   * @return String of tables, or NULL if no tables can be drawn.
   */
  public JPopupMenu getTableOptions(ActionListener sActionListener) {

    JPopupMenu jMenu = new JPopupMenu();
    DetailedGridSettings oSettings;
    int i, j;    
    
    if (mp_oTreeSettings.size() > 0)
      jMenu.add(new SortieMenuItem("Timestep Tree Writer",
        "Timestep Tree Writer", sActionListener));

    //Check our grids for specialized grid table options
    for (i = 0; i < mp_oGridSettings.size(); i++) {

      oSettings = mp_oGridSettings.get(i);
      if (oSettings.getName().equals("Harvest Results")) {
        jMenu.add(new SortieMenuItem("Harvest Results",
            "Harvest Results", sActionListener));
      }
      else if (oSettings.getName().equals("Mortality Episode Results")) {
        jMenu.add(new SortieMenuItem("Mortality Episode Results",
            "Mortality Episode Results", sActionListener));
      }
      else if (oSettings.getName().equals("Windstorm Results")) {
        jMenu.add(new SortieMenuItem("Windstorm Results",
            "Windstorm Results", sActionListener));
      }
      else if (oSettings.getName().equals("Merchantable Timber Value")) {
        jMenu.add(new SortieMenuItem("Merchantable Timber Value",
            "Merchantable Timber Value", sActionListener));
      }
      else if (oSettings.getName().equals("Carbon Value")) {
        jMenu.add(new SortieMenuItem("Carbon Value",
            "Carbon Value", sActionListener));
      }
      else if (oSettings.getName().equals("Foliar Chemistry")) {
        jMenu.add(new SortieMenuItem("Foliar Chemistry",
            "Foliar Chemistry", sActionListener));
      }
      else if (oSettings.getName().equals("Dispersed Seeds")) {
        jMenu.add(new SortieMenuItem("Dispersed Seeds",
            "Dispersed Seeds", sActionListener));
      }
      else if (oSettings.getName().equals("Partitioned Biomass")) {
        jMenu.add(new SortieMenuItem("Partitioned Biomass",
            "Partitioned Biomass", sActionListener));
      }
      else if (oSettings.getName().equals("Storm Killed Partitioned Biomass")) {
        jMenu.add(new SortieMenuItem("Storm Killed Partitioned Biomass",
            "Storm Killed Partitioned Biomass", sActionListener));
      }
      else if (oSettings.getName().equals("Storm Damage")) {
        for (j = 0; j < oSettings.getNumberOfPackageFloats(); j++) {
          if (oSettings.getPackageFloat(j).getCodeName().equals("1dmg_index")) {
            jMenu.add(new SortieMenuItem("Storm Summary",
                "Storm Summary", sActionListener));
            break;
          }
        }	
      }	
      else if (oSettings.getName().equals("State Variables")) {
        jMenu.add(new SortieMenuItem("State Variables",
            "State Variables", sActionListener));
      }
    }

    //See if there are tree DBH or diam10 datamembers
    DetailedTreeSettings oTreeSetting;

    //Can we do diameter (overview table)?
    boolean bHasDiam = false;
    for (i = 0; i < mp_oTreeSettings.size(); i++) {
      oTreeSetting = mp_oTreeSettings.get(i);
      for (j = 0; j < oTreeSetting.getNumberOfFloats(); j++) {
        String sLabel = oTreeSetting.getFloat(j).getCodeName();
        if (sLabel.equalsIgnoreCase("dbh")) {
          bHasDiam = true;
          break;
        }
        else if (sLabel.equalsIgnoreCase("diam10") &&
            oTreeSetting.getType() == sortie.data.funcgroups.TreePopulation.SEEDLING) {
          bHasDiam = true;
          break;
        }
      }
      if (bHasDiam) {
        jMenu.add(new SortieMenuItem("Whole plot: Table",
            "Whole plot: Table", sActionListener));
        break;
      }
    }

    //Can we do DBH (stand tables)?
    boolean bHasDBH = false;
    for (i = 0; i < mp_oTreeSettings.size(); i++) {
      oTreeSetting = mp_oTreeSettings.get(i);
      for (j = 0; j < oTreeSetting.getNumberOfFloats(); j++) {
        String sLabel = oTreeSetting.getFloat(j).getCodeName();
        if (sLabel.equalsIgnoreCase("dbh")) {
          bHasDBH = true;
          break;
        }
      }
      if (bHasDBH) {
        jMenu.add(new SortieMenuItem("BA Stand Table", "BA Stand Table", sActionListener));
        jMenu.add(new SortieMenuItem("Density Stand Table", "Density Stand Table",
            sActionListener));
        break;
      }
    }

    //If we have volume, we can do a stock table
    boolean bHasVolume = false;
    for (i = 0; i < mp_oTreeSettings.size(); i++) {
      oTreeSetting = mp_oTreeSettings.get(i);
      for (j = 0; j < oTreeSetting.getNumberOfFloats(); j++) {
        String sLabel = oTreeSetting.getFloat(j).getCodeName();
        if (sLabel.equalsIgnoreCase("volume")) {
          bHasVolume = true;
          break;
        }
      }
      if (bHasVolume) {
        jMenu.add(new SortieMenuItem("Stock Table", "Stock Table", sActionListener));
        break;
      }
    }
    
    if (jMenu.getSubElements().length > 0)
      mp_sTableNames = getMenuOptions(jMenu).toArray(new String[0]);

    if (jMenu.getSubElements().length > 0) return jMenu;
    return null;
  }

  /**
   * Gets the histograms that can be drawn for this file.
   * @return String of tables, or NULL if no histograms can be drawn.
   */
  public JPopupMenu getHistogramOptions(ActionListener sActionListener) {
    JPopupMenu jMenu = new JPopupMenu();
    ArrayList<String> oOptions = new ArrayList<String>(0);
    ArrayList<Boolean> oIsFloat = new ArrayList<Boolean>(0);
    ArrayList<Integer> oDeadCode = new ArrayList<Integer>(0);
    DetailedTreeSettings oSetting;
    DetailedGridSettings oGridSetting;
    String sLabel, sTestLabel;
    int i, j, k, iDeadCode;
    boolean bFound;    

    for (i = 0; i < mp_oTreeSettings.size(); i++) {

      oSetting = mp_oTreeSettings.get(i);

      //Go through the floats - we could histogram any of them
      for (j = 0; j < oSetting.getNumberOfFloats(); j++) {

        //But we'll skip X and Y
        sLabel = oSetting.getFloat(j).getCodeName();
        if (!sLabel.equalsIgnoreCase("x") && !sLabel.equalsIgnoreCase("y")) {
          iDeadCode = oSetting.getDeadCode();

          //Make sure we haven't already added this float
          bFound = false;
          for (k = 0; k < oOptions.size(); k++) {
            sTestLabel = oOptions.get(k);
            if (sLabel.equals(sTestLabel) && 
                oDeadCode.get(k) == iDeadCode) {
              bFound = true;
              break;
            }
          }
          if (!bFound) {
            oOptions.add(sLabel);
            oIsFloat.add(Boolean.valueOf(true));
            oDeadCode.add(Integer.valueOf(iDeadCode));            
          }
        }
      }
            
      
      //Go through the ints - we could histogram any of them
      for (j = 0; j < oSetting.getNumberOfInts(); j++) {
        sLabel = oSetting.getInt(j).getCodeName();
        iDeadCode = oSetting.getDeadCode();

        //Make sure we haven't already added this int
        bFound = false;
        for (k = 0; k < oOptions.size(); k++) {
          sTestLabel = oOptions.get(k);
          if (sLabel.equals(sTestLabel) && 
              oDeadCode.get(k) == iDeadCode) {
            bFound = true;
            break;
          }
        }
        if (!bFound) {
          oOptions.add(sLabel);
          oIsFloat.add(Boolean.valueOf(false));
          oDeadCode.add(Integer.valueOf(iDeadCode));          
        }
      }
    }      

    //At the top of the list, put all species histograms for the grids that 
    //support it 
    for (i = 0; i < mp_oGridSettings.size(); i++) {
      oGridSetting = mp_oGridSettings.get(i);
      String sName = oGridSetting.getName();
      if (sName.equals("Carbon Value") ||
          sName.equals("Competition Harvest Results") ||
          sName.equals("Dispersed Seeds") ||
          sName.equals("Foliar Chemistry") ||
          sName.equals("Merchantable Timber Value") ||
          sName.equals("Neighborhood Seed Predation") ||
          sName.equals("Partitioned Biomass") ||
          sName.equals("Storm Killed Partitioned Biomass") ||
          sName.equals("Substrate Favorability") ||
          sName.equals("Temperature Dependent Neighborhood Survival") ||
          sName.equals("Weibull Climate Quadrat Growth")) {
      
        //Go through the floats - look for species specific 
        for (j = 0; j < oGridSetting.getNumberOfFloats(); j++) {

          sLabel = oGridSetting.getFloat(j).getCodeName();
          if (sLabel.matches(".*_\\d.*")) {
            sLabel = sLabel.substring(0, sLabel.lastIndexOf("_"));
            sLabel = "Grid: " + oGridSetting.getName() + " - All " + sLabel;
            
            //Make sure we haven't already added this float
            bFound = false;
            for (k = 0; k < oOptions.size(); k++) {
              sTestLabel = oOptions.get(k);
              if (sLabel.equals(sTestLabel)) {
                bFound = true;
                break;
              }
            }
            if (!bFound) {
              oOptions.add(sLabel);
              oIsFloat.add(Boolean.valueOf(true));
              oDeadCode.add(OutputBehaviors.NOTDEAD);              
            }
          }
        }
        //Repeat with ints
        for (j = 0; j < oGridSetting.getNumberOfInts(); j++) {
          sLabel = oGridSetting.getInt(j).getCodeName();
          if (sLabel.matches(".*_\\d.*")) {
            sLabel = sLabel.substring(0, sLabel.lastIndexOf("_"));
            sLabel = "Grid: " + oGridSetting.getName() + " - All " + sLabel;
            bFound = false;
            for (k = 0; k < oOptions.size(); k++) {
              sTestLabel = oOptions.get(k);
              if (sLabel.equals(sTestLabel)) {
                bFound = true;
                break;
              }
            }
            if (!bFound) {
              oOptions.add(sLabel);
              oIsFloat.add(Boolean.valueOf(false));
              oDeadCode.add(OutputBehaviors.NOTDEAD);
            }
          }
        }
      }
    }
    
    //LEM 03-29-05
    for (i = 0; i < mp_oGridSettings.size(); i++) {

      oGridSetting = mp_oGridSettings.get(i);
      
      //Skip some grids, because they just swamp the options list without 
      //producing useful maps
      if (oGridSetting.getName().equals("Ripley's K") == false &&
          oGridSetting.getName().equals("Relative Neighborhood Density") == false &&
          oGridSetting.getName().equals("Harvest Results") == false &&
          oGridSetting.getName().equals("Partitioned Biomass") == false &&
          oGridSetting.getName().equals("Foliar Chemistry") == false &&
          oGridSetting.getName().equals("Storm Killed Partitioned Biomass") == false &&
          oGridSetting.getName().equals("Mortality Episode Results") == false) {

        //Go through the floats - we could histogram any of them
        for (j = 0; j < oGridSetting.getNumberOfFloats(); j++) {

          sLabel = oGridSetting.getFloat(j).getCodeName();

          //Make sure we haven't already added this float
          bFound = false;
          for (k = 0; k < oOptions.size(); k++) {
            sTestLabel = oOptions.get(k);
            if (sLabel.equals(sTestLabel)) {
              bFound = true;
              break;
            }
          }
          if (!bFound) {
            oOptions.add("Grid: " + oGridSetting.getName() + " - " + sLabel);
            oIsFloat.add(Boolean.valueOf(true));
            oDeadCode.add(OutputBehaviors.NOTDEAD);
          }
        }
        //Go through the ints - we could histogram any of them
        for (j = 0; j < oGridSetting.getNumberOfInts(); j++) {

          sLabel = oGridSetting.getInt(j).getCodeName();

          //Make sure we haven't already added this int
          bFound = false;
          for (k = 0; k < oOptions.size(); k++) {
            sTestLabel = oOptions.get(k);
            if (sLabel.equals(sTestLabel)) {
              bFound = true;
              break;
            }
          }
          if (!bFound) {
            oOptions.add("Grid: " + oGridSetting.getName() + " - " + sLabel);
            oIsFloat.add(Boolean.valueOf(false));
            oDeadCode.add(OutputBehaviors.NOTDEAD);
          }
        }
      }
    }

    //Transform our Vector into an array of strings
    if (oOptions.size() == 0) {
      return null;
    }
    mp_sHistogramNames = new String[oOptions.size()];
    mp_iDeadCodes = new int[oOptions.size()];
    mp_bHistogramIsFloat = new boolean[oOptions.size()];
    for (i = 0; i < mp_sHistogramNames.length; i++) {
      mp_sHistogramNames[i] = oOptions.get(i);
      mp_bHistogramIsFloat[i] = oIsFloat.get(i).booleanValue();
      mp_iDeadCodes[i] = oDeadCode.get(i).intValue();
    }
    
    //Add the name "Histogram" to each name
    for (i = 0; i < mp_sHistogramNames.length; i++) {
      if (OutputBehaviors.NOTDEAD == mp_iDeadCodes[i]) {
        mp_sHistogramNames[i] = mp_sHistogramNames[i] + " Histogram";  
      } else {
        mp_sHistogramNames[i] = mp_sHistogramNames[i] + " " + 
        mp_sDeadCodeNames[mp_iDeadCodes[i]] + " Dead Histogram";
      }      
    }
    
    JMenu jTrees = new JMenu("Trees");
    jTrees.setFont(new SortieFont());
    JMenu jLiveTrees = new JMenu("Live trees");
    jLiveTrees.setFont(new SortieFont());
    JMenu jDeadTrees = new JMenu("Dead trees");
    jDeadTrees.setFont(new SortieFont());
    JMenu jGrids = new JMenu("Grids");
    jGrids.setFont(new SortieFont());
    ArrayList<JMenu> p_jGridMenus = new ArrayList<JMenu>();
    JMenu[] p_jDeadMenus = new JMenu[mp_sDeadCodeNames.length];
    for (i = 0; i < p_jDeadMenus.length; i++) {
      p_jDeadMenus[i] = new JMenu(mp_sDeadCodeNames[i]);
      p_jDeadMenus[i].setFont(new SortieFont());
    }
    
    for (i = 0; i < mp_sHistogramNames.length; i++) {
      if (mp_sHistogramNames[i].startsWith("Grid")) {
        String sGridName = mp_sHistogramNames[i].substring(mp_sHistogramNames[i].
            indexOf(":") + 2, mp_sHistogramNames[i].indexOf(" - "));
        bFound = false;
        for (j = 0; j < p_jGridMenus.size(); j++) {
          if (sGridName.equals(p_jGridMenus.get(j).getText())) {
            bFound = true; break;
          }
        }
        if (!bFound) {
          JMenu jGridder = new JMenu(sGridName);
          jGridder.setFont(new SortieFont());
          p_jGridMenus.add(jGridder);
          j = p_jGridMenus.size() - 1;
        }
        p_jGridMenus.get(j).add(new SortieMenuItem(
            mp_sHistogramNames[i].substring(mp_sHistogramNames[i].indexOf(" - ") + 3), 
                                      mp_sHistogramNames[i], 
                                      sActionListener));
      } else {
        if (mp_iDeadCodes[i] == OutputBehaviors.NOTDEAD) {
          jLiveTrees.add(new SortieMenuItem(mp_sHistogramNames[i], 
                                            mp_sHistogramNames[i], 
                                            sActionListener));
        } else {
          p_jDeadMenus[mp_iDeadCodes[i]].add(new SortieMenuItem(mp_sHistogramNames[i], 
              mp_sHistogramNames[i], 
              sActionListener));
        }
      }
    }
    if (jLiveTrees.getSubElements().length > 0) jTrees.add(jLiveTrees);
    for (i = 0; i < p_jDeadMenus.length; i++) {
      if (p_jDeadMenus[i].getSubElements().length > 0)
        jDeadTrees.add(p_jDeadMenus[i]);
    }
    if (jDeadTrees.getSubElements().length > 0) jTrees.add(jDeadTrees);
    if (jTrees.getSubElements().length > 0) jMenu.add(jTrees);
    for (i = 0; i < p_jGridMenus.size(); i++) jGrids.add(p_jGridMenus.get(i));     
    if (jGrids.getSubElements().length > 0) jMenu.add(jGrids);

    return jMenu;
  }
  
  /**
   * Gets the maps that can be drawn for this file.
   * @return JPopupMenu of maps, or NULL if no maps can be drawn.
   */
  public JPopupMenu getMapOptions(ActionListener sActionListener) {
    
    //This will hold all our options as we collect them
    JPopupMenu jMenu = new JPopupMenu();
    JMenu jTrees = new JMenu("Trees"),
          jDeadTrees = new JMenu("Dead trees"),
          jGrids = new JMenu("Grids");
    ArrayList<JMenu> p_jGridMenus = new ArrayList<JMenu>();
    JMenu[] p_jTreeMenus = new JMenu[mp_sDeadCodeNames.length];
    DetailedTreeSettings oTreeSetting;
    DetailedGridSettings oGridSetting;
    boolean[] p_bCanDoXYMap = new boolean[mp_sDeadCodeNames.length],
              p_bCanDoCrownMap = new boolean[mp_sDeadCodeNames.length];
    int i, j, iGridIndex, iDeadCode;    
    boolean bFound, bHasX = false, bHasY = false, bHasDBH = false, 
    bHasCrownRad = false;
    
    jTrees.setFont(new SortieFont());
    jDeadTrees.setFont(new SortieFont());
    jGrids.setFont(new SortieFont());
    p_jTreeMenus[OutputBehaviors.NOTDEAD] = new JMenu("Live trees");
    p_jTreeMenus[OutputBehaviors.NOTDEAD].setFont(new SortieFont());
    for (i = (OutputBehaviors.NOTDEAD+1); i < p_jTreeMenus.length; i++) {
      p_jTreeMenus[i] = new JMenu(mp_sDeadCodeNames[i]);
      p_jTreeMenus[i].setFont(new SortieFont());
    }
    for (i = 0; i < p_bCanDoCrownMap.length; i++) {
      p_bCanDoCrownMap[i] = false;
      p_bCanDoXYMap[i] = false;
    }

    //Can we do XY and crown radius maps?     
    for (i = 0; i < mp_oTreeSettings.size(); i++) {
      oTreeSetting = mp_oTreeSettings.get(i);
      iDeadCode = oTreeSetting.getDeadCode();
      for (j = 0; j < oTreeSetting.getNumberOfFloats(); j++) {
        String sLabel = oTreeSetting.getFloat(j).getCodeName();
        if (sLabel.equalsIgnoreCase("x")) bHasX = true;
        else if (sLabel.equalsIgnoreCase("y")) bHasY = true;
        else if (sLabel.equalsIgnoreCase("dbh")) bHasDBH = true;
        else if (sLabel.equalsIgnoreCase("crown radius")) bHasCrownRad = true;
      }

      if (bHasX && bHasY && bHasDBH) {
        p_bCanDoXYMap[iDeadCode] = true;                
      } 
      if (bHasX && bHasY && bHasCrownRad) {
        p_bCanDoCrownMap[iDeadCode] = true;
      }
      bHasX = false;
      bHasY = false;
      bHasDBH = false;
      bHasCrownRad = false;      
    }
    for (i = 0; i < p_bCanDoCrownMap.length; i++) {
      if (p_bCanDoXYMap[i]) {
        p_jTreeMenus[i].add(new SortieMenuItem("XY DBH Tree Map",
            "XY DBH Tree Map: " + mp_sDeadCodeNames[i], sActionListener));                
      } 
      if (p_bCanDoCrownMap[i]) {
        p_jTreeMenus[i].add(new SortieMenuItem("Crown Radius Tree Map",
            "Crown Radius Tree Map: " + mp_sDeadCodeNames[i], sActionListener));         
      } 
    }

    //We can map any non-char, non-package grid data members
    for (i = 0; i < mp_oGridSettings.size(); i++) {
      oGridSetting = mp_oGridSettings.get(i);
      String sGridName = oGridSetting.getName();
      
      //Skip some grids, because they just swamp the options list without 
      //producing useful maps
      if (sGridName.equals("Ripley's K") == false &&
          oGridSetting.getName().equals("Relative Neighborhood Density") == false &&
          oGridSetting.getName().equals("Harvest Results") == false &&
          oGridSetting.getName().equals("Mortality Episode Results") == false &&
          oGridSetting.getName().equals("Partitioned Biomass") == false &&
          oGridSetting.getName().equals("Foliar Chemistry") == false &&
          oGridSetting.getName().equals("Storm Killed Partitioned Biomass") == false) {
        
        bFound = false;
        iGridIndex = 0;
        for (j = 0; j < p_jGridMenus.size(); j++) {
          if (sGridName.equals(p_jGridMenus.get(j).getText())) {
            bFound = true; iGridIndex = j; break;
          }
        }
        if (!bFound) {
          JMenu jGridder = new JMenu(sGridName);
          jGridder.setFont(new SortieFont());
          p_jGridMenus.add(jGridder);
          iGridIndex = p_jGridMenus.size() - 1;
        }        
        
        for (j = 0; j < oGridSetting.getNumberOfBools(); j++) {
          String sLabel = oGridSetting.getBool(j).getCodeName();
          p_jGridMenus.get(iGridIndex).add(new SortieMenuItem(
              sLabel, "Map - " + sGridName + " - " + sLabel, sActionListener));          
        }
        for (j = 0; j < oGridSetting.getNumberOfInts(); j++) {
          String sLabel = oGridSetting.getInt(j).getCodeName();
          p_jGridMenus.get(iGridIndex).add(new SortieMenuItem(
              sLabel, "Map - " + sGridName + " - " + sLabel, sActionListener));
        }
        for (j = 0; j < oGridSetting.getNumberOfFloats(); j++) {
          String sLabel = oGridSetting.getFloat(j).getCodeName();
          p_jGridMenus.get(iGridIndex).add(new SortieMenuItem(
              sLabel, "Map - " + sGridName + " - " + sLabel, sActionListener));
        }
      }
    }
    
    if (p_jTreeMenus[OutputBehaviors.NOTDEAD].getSubElements().length > 0) 
      jTrees.add(p_jTreeMenus[OutputBehaviors.NOTDEAD]);
    for (i = (OutputBehaviors.NOTDEAD+1); i < p_jTreeMenus.length; i++) {
      if (p_jTreeMenus[i].getSubElements().length > 0)
        jDeadTrees.add(p_jTreeMenus[i]);
    }
    if (jDeadTrees.getSubElements().length > 0) jTrees.add(jDeadTrees);
    if (jTrees.getSubElements().length > 0) jMenu.add(jTrees);
    for (i = 0; i < p_jGridMenus.size(); i++) jGrids.add(p_jGridMenus.get(i));     
    if (jGrids.getSubElements().length > 0) jMenu.add(jGrids);
    
    if (jMenu.getSubElements().length > 0) {
      mp_sMapNames = getMenuOptions(jMenu).toArray(new String[0]);
    }

    if (jMenu.getSubElements().length > 0) return jMenu;
    else return null;
  }

  /**
   * Gets the line graphs that can be drawn for this file.
   * @return JPopupMenu of tables, or NULL if no line graphs can be drawn.
   */
  public JPopupMenu getLineGraphOptions(ActionListener sActionListener) { 
    
    JPopupMenu jMenu = new JPopupMenu();
    JMenu jTrees = new JMenu("Trees"),
          jLiveTrees = new JMenu("Live trees"),
          jDeadTrees = new JMenu("Dead trees"),
          jGrids = new JMenu("Grids"),
          jSeedlings = new JMenu("Seedlings"),
          jSaplings = new JMenu("Saplings"),
          jAdults = new JMenu("Adults"),
          jSnags = new JMenu("Snags");
    DetailedTreeSettings oTreeSetting;
    DetailedGridSettings oGridSetting;
    int i, j;
    boolean[] p_bSeedling = new boolean[OutputBehaviors.NUMCODES],
              p_bSaplingDiam = new boolean[OutputBehaviors.NUMCODES], 
              p_bAdultDiam = new boolean[OutputBehaviors.NUMCODES],
              p_bSnagDiam = new boolean[OutputBehaviors.NUMCODES], 
              p_bSaplingVolume = new boolean[OutputBehaviors.NUMCODES], 
              p_bAdultVolume = new boolean[OutputBehaviors.NUMCODES],
              p_bSnagVolume = new boolean[OutputBehaviors.NUMCODES], 
              p_bSaplingBiomass = new boolean[OutputBehaviors.NUMCODES], 
              p_bAdultBiomass = new boolean[OutputBehaviors.NUMCODES],
              p_bSnagBiomass = new boolean[OutputBehaviors.NUMCODES];
    
    for (i = 0; i < OutputBehaviors.NUMCODES; i++) {
      p_bSeedling[i] = false;
      p_bSaplingDiam[i] = false; 
      p_bAdultDiam[i] = false;
      p_bSnagDiam[i] = false; 
      p_bSaplingVolume[i] = false; 
      p_bAdultVolume[i] = false;
      p_bSnagVolume[i] = false; 
      p_bSaplingBiomass[i] = false; 
      p_bAdultBiomass[i] = false;
      p_bSnagBiomass[i] = false;
    }
    
    jTrees.setFont(new SortieFont());
    jLiveTrees.setFont(new SortieFont());
    jDeadTrees.setFont(new SortieFont());
    jGrids.setFont(new SortieFont()); 
    jSeedlings.setFont(new SortieFont());
    jSaplings.setFont(new SortieFont());
    jAdults.setFont(new SortieFont());
    jSnags.setFont(new SortieFont());

    //Check for which tree types we have diameter information
    for (i = 0; i < mp_oTreeSettings.size(); i++) {
      oTreeSetting = mp_oTreeSettings.get(i);
      for (j = 0; j < oTreeSetting.getNumberOfFloats(); j++) {
        String sLabel = oTreeSetting.getFloat(j).getCodeName();
        if (sLabel.equalsIgnoreCase("dbh")) {
          if (oTreeSetting.getType() == sortie.data.funcgroups.TreePopulation.SAPLING) {
            p_bSaplingDiam[oTreeSetting.getDeadCode()] = true;
          }
          else if (oTreeSetting.getType() == sortie.data.funcgroups.TreePopulation.ADULT) {
            p_bAdultDiam[oTreeSetting.getDeadCode()] = true;
          }
          else if (oTreeSetting.getType() == sortie.data.funcgroups.TreePopulation.SNAG) {
            p_bSnagDiam[oTreeSetting.getDeadCode()] = true;
          }
        }
        else if (sLabel.equalsIgnoreCase("diam10") &&
                 oTreeSetting.getType() == sortie.data.funcgroups.TreePopulation.SEEDLING) {
          p_bSeedling[oTreeSetting.getDeadCode()] = true;
        }
        else if (sLabel.equalsIgnoreCase("volume")) {
          if (oTreeSetting.getType() == sortie.data.funcgroups.TreePopulation.SAPLING) {
            p_bSaplingVolume[oTreeSetting.getDeadCode()] = true;
          }
          else if (oTreeSetting.getType() == sortie.data.funcgroups.TreePopulation.ADULT) {
            p_bAdultVolume[oTreeSetting.getDeadCode()] = true;
          }
          else if (oTreeSetting.getType() == sortie.data.funcgroups.TreePopulation.SNAG) {
            p_bSnagVolume[oTreeSetting.getDeadCode()] = true;
          }
        }
        else if (sLabel.equalsIgnoreCase("biomass")) {
          if (oTreeSetting.getType() == sortie.data.funcgroups.TreePopulation.SAPLING) {
            p_bSaplingBiomass[oTreeSetting.getDeadCode()] = true;
          }
          else if (oTreeSetting.getType() == sortie.data.funcgroups.TreePopulation.ADULT) {
            p_bAdultBiomass[oTreeSetting.getDeadCode()] = true;
          }
          else if (oTreeSetting.getType() == sortie.data.funcgroups.TreePopulation.SNAG) {
            p_bSnagBiomass[oTreeSetting.getDeadCode()] = true;
          }
        }
      }
    }

    
    jSeedlings.setFont(new SortieFont());
    if (p_bSeedling[OutputBehaviors.NOTDEAD]) {
      jSeedlings.add(new SortieMenuItem(
          "Absolute Density", 
          "Seedling Abs Density", 
          sActionListener));
      jSeedlings.add(new SortieMenuItem(
          "Relative Density", 
          "Seedling Rel Density", 
          sActionListener));
    }
    if (p_bSaplingDiam[OutputBehaviors.NOTDEAD]) {
      jSaplings.add(new SortieMenuItem(
          "Absolute Basal Area",
          "Sapling Abs BA",
          sActionListener));
      jSaplings.add(new SortieMenuItem(
          "Relative Basal Area",
          "Sapling Rel BA",
          sActionListener));
      jSaplings.add(new SortieMenuItem(
          "Absolute Density",
          "Sapling Abs Density",
          sActionListener));
      jSaplings.add(new SortieMenuItem(
          "Relative Density",
          "Sapling Rel Density",
          sActionListener));
    }
    if (p_bAdultDiam[OutputBehaviors.NOTDEAD]) {
      jAdults.add(new SortieMenuItem(
          "Absolute Basal Area",
          "Adult Abs BA",
          sActionListener));
      jAdults.add(new SortieMenuItem(
          "Relative Basal Area",
          "Adult Rel BA",
          sActionListener));
      jAdults.add(new SortieMenuItem(
          "Absolute Density",
          "Adult Abs Density",
          sActionListener));
      jAdults.add(new SortieMenuItem(
          "Relative Density",
          "Adult Rel Density",
          sActionListener));
    }
    if (p_bSnagDiam[OutputBehaviors.NOTDEAD]) {
      jSnags.add(new SortieMenuItem(
          "Absolute Basal Area",
          "Snag Abs BA",
          sActionListener));
      jSnags.add(new SortieMenuItem(
          "Relative Basal Area",
          "Snag Rel BA",
          sActionListener));
      jSnags.add(new SortieMenuItem(
          "Absolute Density",
          "Snag Abs Density",
          sActionListener));
      jSnags.add(new SortieMenuItem(
          "Relative Density",
          "Snag Rel Density",
          sActionListener));
    }
    
    
    if (p_bSaplingVolume[OutputBehaviors.NOTDEAD]) {
      jSaplings.add(new SortieMenuItem(
          "Absolute Volume",
          "Sapling Abs Volume",
          sActionListener));
      jSaplings.add(new SortieMenuItem(
          "Relative Volume",
          "Sapling Rel Volume",
          sActionListener));
    }
    if (p_bAdultVolume[OutputBehaviors.NOTDEAD]) {
      jAdults.add(new SortieMenuItem(
          "Absolute Volume",
          "Adult Abs Volume",
          sActionListener));
      jAdults.add(new SortieMenuItem(
          "Relative Volume",
          "Adult Rel Volume",
          sActionListener));
    }
    if (p_bSnagVolume[OutputBehaviors.NOTDEAD]) {
      jSnags.add(new SortieMenuItem(
          "Absolute Volume",
          "Snag Abs Volume",
          sActionListener));
      jSnags.add(new SortieMenuItem(
          "Snag Relative Volume",
          "Snag Rel Volume",
          sActionListener));
    }
    if (p_bSaplingBiomass[OutputBehaviors.NOTDEAD]) {
      jSaplings.add(new SortieMenuItem(
          "Absolute Biomass",
          "Sapling Abs Biomass",
          sActionListener));
      jSaplings.add(new SortieMenuItem(
          "Relative Biomass",
          "Sapling Rel Biomass",
          sActionListener));
    }
    if (p_bAdultBiomass[OutputBehaviors.NOTDEAD]) {
      jAdults.add(new SortieMenuItem(
          "Absolute Biomass",
          "Adult Abs Biomass",
          sActionListener));
      jAdults.add(new SortieMenuItem(
          "Relative Biomass",
          "Adult Rel Biomass",
          sActionListener));
    }
    if (p_bSnagBiomass[OutputBehaviors.NOTDEAD]) {
      jSnags.add(new SortieMenuItem(
          "Absolute Biomass",
          "Snag Abs Biomass",
          sActionListener));
      jSnags.add(new SortieMenuItem(
          "Relative Biomass",
          "Snag Rel Biomass",
          sActionListener));
    }

    if (jSeedlings.getSubElements().length > 0) jLiveTrees.add(jSeedlings); 
    if (jSaplings.getSubElements().length > 0) jLiveTrees.add(jSaplings);
    if (jAdults.getSubElements().length > 0) jLiveTrees.add(jAdults);
    if (jSnags.getSubElements().length > 0) jLiveTrees.add(jSnags);
    
    for (i = 1; i < OutputBehaviors.NUMCODES; i++) {
      jSeedlings = new JMenu("Seedlings");
      jSaplings = new JMenu("Saplings");
      jAdults = new JMenu("Adults");
      jSnags = new JMenu("Snags");
      JMenu jDeadType = new JMenu(mp_sDeadCodeNames[i]);
      jDeadType.setFont(new SortieFont());
      jSeedlings.setFont(new SortieFont());
      jSaplings.setFont(new SortieFont());
      jAdults.setFont(new SortieFont());
      jSnags.setFont(new SortieFont());
      
      if (p_bSeedling[i]) {
        jSeedlings.add(new SortieMenuItem(
           "Absolute Density",
            mp_sDeadCodeNames[i] + " Dead Seedling Abs Den",
            sActionListener));
        jSeedlings.add(new SortieMenuItem(
            "Relative Density",
            mp_sDeadCodeNames[i] + " Dead Seedling Rel Den",
            sActionListener));
      }
      if (p_bSaplingDiam[i]) {
        jSaplings.add(new SortieMenuItem(
            "Absolute Basal Area",
            mp_sDeadCodeNames[i] + " Dead Sapling Abs BA",
            sActionListener));
        jSaplings.add(new SortieMenuItem(
            "Relative Basal Area",
            mp_sDeadCodeNames[i] + " Dead Sapling Rel BA",
            sActionListener));
        jSaplings.add(new SortieMenuItem(
            "Absolute Density",
            mp_sDeadCodeNames[i] + " Dead Sapling Abs Den",
            sActionListener));
        jSaplings.add(new SortieMenuItem(
            "Relative Density",
            mp_sDeadCodeNames[i] + " Dead Sapling Rel Den",
            sActionListener));
      }
      if (p_bAdultDiam[i]) {
        jAdults.add(new SortieMenuItem(
            "Absolute Basal Area",
            mp_sDeadCodeNames[i] + " Dead Adult Abs BA",
            sActionListener));
        jAdults.add(new SortieMenuItem(
            "Relative Basal Area",
            mp_sDeadCodeNames[i] + " Dead Adult Rel BA",
            sActionListener));
        jAdults.add(new SortieMenuItem(
            "Absolute Density",
            mp_sDeadCodeNames[i] + " Dead Adult Abs Den",
            sActionListener));
        jAdults.add(new SortieMenuItem(
            "Relative Density",
            mp_sDeadCodeNames[i] + " Dead Adult Rel Den",
            sActionListener));
      }
      if (p_bSnagDiam[i]) {
        jSnags.add(new SortieMenuItem(
            "Absolute Basal Area",
            mp_sDeadCodeNames[i] + " Dead Snag Abs BA",
            sActionListener));
        jSnags.add(new SortieMenuItem(
            "Relative Basal Area",
            mp_sDeadCodeNames[i] + " Dead Snag Rel BA",
            sActionListener));
        jSnags.add(new SortieMenuItem(
            "Absolute Density",
            mp_sDeadCodeNames[i] + " Dead Snag Abs Den",
            sActionListener));
        jSnags.add(new SortieMenuItem(
            "Relative Density",
            mp_sDeadCodeNames[i] + " Dead Snag Rel Den",
            sActionListener));
      }
      if (p_bSaplingVolume[i]) {
        jSaplings.add(new SortieMenuItem(
            "Absolute Volume",
            mp_sDeadCodeNames[i] + " Dead Sapling Abs Volume",
            sActionListener));
        jSaplings.add(new SortieMenuItem(
            "Relative Volume",
            mp_sDeadCodeNames[i] + " Dead Sapling Rel Volume",
            sActionListener));
      }
      if (p_bAdultVolume[i]) {
        jAdults.add(new SortieMenuItem(
            "Absolute Volume",
            mp_sDeadCodeNames[i] + " Dead Adult Abs Volume",
            sActionListener));
        jAdults.add(new SortieMenuItem(
            "Relative Volume",
            mp_sDeadCodeNames[i] + " Dead Adult Rel Volume",
            sActionListener));
      }
      if (p_bSnagVolume[i]) {
        jSnags.add(new SortieMenuItem(
            "Absolute Volume",
            mp_sDeadCodeNames[i] + " Dead Snag Abs Volume",
            sActionListener));
        jSnags.add(new SortieMenuItem(
            "Relative Volume",
            mp_sDeadCodeNames[i] + " Dead Snag Rel Volume",
            sActionListener));
      }
      if (p_bSaplingBiomass[i]) {
        jSaplings.add(new SortieMenuItem(
            "Absolute Biomass",
            mp_sDeadCodeNames[i] + " Dead Sapling Abs Biomass",
            sActionListener));
        jSaplings.add(new SortieMenuItem(
            "Relative Biomass",
            mp_sDeadCodeNames[i] + " Dead Sapling Rel Biomass",
            sActionListener));
      }
      if (p_bAdultBiomass[i]) {
        jAdults.add(new SortieMenuItem(
            "Absolute Biomass",
            mp_sDeadCodeNames[i] + " Dead Adult Abs Biomass",
            sActionListener));
        jAdults.add(new SortieMenuItem(
            "Relative Biomass",
            mp_sDeadCodeNames[i] + " Dead Adult Rel Biomass",
            sActionListener));
      }
      if (p_bSnagBiomass[i]) {
        jSnags.add(new SortieMenuItem(
            "Absolute Biomass",
            mp_sDeadCodeNames[i] + " Dead Snag Abs Biomass",
            sActionListener));
        jSnags.add(new SortieMenuItem(
            "Relative Biomass",
            mp_sDeadCodeNames[i] + " Dead Snag Rel Biomass",
            sActionListener));
      }
      
      if (jSeedlings.getSubElements().length > 0) jDeadType.add(jSeedlings); 
      if (jSaplings.getSubElements().length > 0) jDeadType.add(jSaplings);
      if (jAdults.getSubElements().length > 0) jDeadType.add(jAdults);
      if (jSnags.getSubElements().length > 0) jDeadType.add(jSnags);
      if (jDeadType.getSubElements().length > 0) jDeadTrees.add(jDeadType);
    }

    //Grid options
    for (i = 0; i < mp_oGridSettings.size(); i++) {
    	oGridSetting = mp_oGridSettings.get(i);
    	if (oGridSetting.getName().equals("Ripley's K")) {
    		jGrids.add(new SortieMenuItem("Ripley's K", "Ripley's K", sActionListener));
    	} else if (oGridSetting.getName().equals("Relative Neighborhood Density")) {
    		jGrids.add(new SortieMenuItem("Relative Neighborhood Density", "Relative Neighborhood Density", sActionListener));
    	}
    	else if (oGridSetting.getName().equals("Partitioned Biomass")) {
    		jGrids.add(new SortieMenuItem("Biomass - Leaf", "Biomass - Leaf", sActionListener));
    		jGrids.add(new SortieMenuItem("Biomass - Branch", "Biomass - Branch", sActionListener));
    		jGrids.add(new SortieMenuItem("Biomass - Bole", "Biomass - Bole", sActionListener));
    	}
    	else if (oGridSetting.getName().equals("Foliar Chemistry")) {
        jGrids.add(new SortieMenuItem("Foliar Chemistry - P", "Foliar Chemistry - P", sActionListener));
        jGrids.add(new SortieMenuItem("Foliar Chemistry - N", "Foliar Chemistry - N", sActionListener));
        jGrids.add(new SortieMenuItem("Foliar Chemistry - Lignin", "Foliar Chemistry - Lignin", sActionListener));
        jGrids.add(new SortieMenuItem("Foliar Chemistry - SLA", "Foliar Chemistry - SLA", sActionListener));
        jGrids.add(new SortieMenuItem("Foliar Chemistry - Fiber", "Foliar Chemistry - Fiber", sActionListener));
        jGrids.add(new SortieMenuItem("Foliar Chemistry - Cellulose", "Foliar Chemistry - Cellulose", sActionListener));
        jGrids.add(new SortieMenuItem("Foliar Chemistry - Tannins", "Foliar Chemistry - Tannins", sActionListener));
        jGrids.add(new SortieMenuItem("Foliar Chemistry - Phenolics", "Foliar Chemistry - Phenolics", sActionListener));
      }
    	else if (oGridSetting.getName().equals("Substrate")) {
    	  jGrids.add(new SortieMenuItem("Substrate", "Substrate", sActionListener));
    	}
    }

    if (jLiveTrees.getSubElements().length > 0) jTrees.add(jLiveTrees);
    if (jDeadTrees.getSubElements().length > 0) jTrees.add(jDeadTrees);
    if (jTrees.getSubElements().length > 0) jMenu.add(jTrees);
    if (jGrids.getSubElements().length > 0) jMenu.add(jGrids);
    
    if (jMenu.getSubElements().length > 0) {
      mp_sLineGraphNames = getMenuOptions(jMenu).toArray(new String[0]);
    }
    
    if (jMenu.getSubElements().length > 0) return jMenu; 
    else return null;
  }
  
  /**
   * Recursively add all the elements of a menu and its sub menus to an array 
   * of strings. 
   * @param jMenu Menu to analyze
   * @return Array of strings, one for each menu item (sub menu names are 
   * skipped)
   */
  private ArrayList<String> getMenuOptions(MenuElement jMenu) {
    ArrayList<String> jReturn = new ArrayList<String>(0);
    MenuElement[] jSubs = jMenu.getSubElements();
    if (jSubs == null || jSubs.length == 0) return null;
    for (MenuElement menuElement : jSubs) {
      if (menuElement instanceof SortieMenuItem) {
        jReturn.add(((SortieMenuItem)menuElement).getActionCommand());
      } else if (menuElement instanceof JPopupMenu ||
          menuElement instanceof JMenu) {
        ArrayList<String> jOpts = getMenuOptions(menuElement);
        if (jOpts != null) jReturn.addAll(jOpts);
      }
    }
    return jReturn;
  }

  /**
   * Sets the plot X length.
   * @param f Plot X length, in meters.
   */
  public void setXPlotLength(float f) {
    m_fXPlotLength = f;
  }

  /**
   * Sets the plot Y length.
   * @param f Plot Y length, in meters.
   */
  public void setYPlotLength(float f) {
    m_fYPlotLength = f;
  }

  /**
   * Sets the number of years per timestep.
   * @param f Number of years per timestep.
   */
  public void setNumberOfYearsPerTimestep(float f) {
    m_fNumYearsPerTimestep = f;
  }

  /**
   * Gets the number of years per timestep.
   * @return Number of years per timestep.
   */
  public float getNumberOfYearsPerTimestep() {
    return m_fNumYearsPerTimestep;
  }

  /**
   * Gets the plot X length.
   * @return Plot X length, in meters.
   */
  public float getXPlotLength() {
    return m_fXPlotLength;
  }

  /**
   * Gets the plot Y length.
   * @return Plot Y length, in meters.
   */
  public float getYPlotLength() {
    return m_fYPlotLength;
  }  

  /**
   * Redraws all the charts currently open for this detailed output file.  This
   * is accomplished by sending an UpdateChart() call to each data request
   * object.
   * @throws ModelException wrapping another exception.
   */
  public void updateCharts() throws ModelException {
    DataRequest oRequest;
    int i;
    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      oRequest.updateChart(m_oLegend);
    }
  }

  /**
   * Adds a species name to the end of the species name list.  The position
   * within the vector becomes that name's species index code.
   * @param sName The species name to add.
   */
  public void addSpeciesName(String sName) {
    mp_sSpeciesNames.add(sName);
  }

  /**
   * Creates a new DetailedTreeSettings object and adds it to the
   * tree settings vector.
   * @param sSpeciesName The species name string
   * @param iType The type number
   * @param iDeadCode Dead code for tree settings.
   * @return The new object created
   * @throws ModelException if either the type or the species name is invalid.
   */
  public DetailedTreeSettings createNewTreeSettings(String
      sSpeciesName, int iType, int iDeadCode) throws
      ModelException {
    String sName;
    int iSpecies = -1, i;

    //Validate the type
    if (iType < 0 || iType >= m_iNumTypes) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
      "Invalid type encountered while parsing detailed output file - " +
          String.valueOf(iType)));
    }

    //Try to find the species
    for (i = 0; i < mp_sSpeciesNames.size(); i++) {
      sName = mp_sSpeciesNames.get(i);
      if (sName.equals(sSpeciesName)) {
        iSpecies = i;
        break;
      }
    }

    //Throw an error if we didn't find the name
    if (iSpecies == -1) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
      "Invalid species name encountered while parsing detailed output file - " +
          sSpeciesName));
    }
    
    if (iDeadCode < OutputBehaviors.NOTDEAD || iDeadCode >= OutputBehaviors.NUMCODES) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Invalid dead reason code - " + String.valueOf(iDeadCode)));
    }

    DetailedTreeSettings oData = new DetailedTreeSettings(iType, iSpecies, iDeadCode);

    mp_oTreeSettings.add(oData);

    return oData;
  }

  /**
   * Creates a new DetailedGridSettings object and adds it to the
   * grid settings vector.
   * @param sGridName The grid name string
   * @return The new object created
   */
  public DetailedGridSettings createNewGridSettings(String
      sGridName) {

    DetailedGridSettings oData = new DetailedGridSettings(sGridName);
    mp_oGridSettings.add(oData);
    return oData;
  }

  /**
   * Adds the X cell length to grid settings.
   * @param sGridName The grid name for which to add the cell length.
   * @param fLength The new X cell length in m.
   */
  public void addGridXCellLength(String sGridName, float fLength) {

    //Go through our grids and find this one
    DetailedGridSettings oSettings;
    int i;
    for (i = 0; i < mp_oGridSettings.size(); i++) {
      oSettings = mp_oGridSettings.get(i);
      if (oSettings.getName().equals(sGridName)) {
        oSettings.setXCellLength(fLength);
      }
    }
  }

  /**
   * Adds the Y cell length to grid settings.
   * @param sGridName The grid name for which to add the cell length.
   * @param fLength The new Y cell length in m.
   */
  public void addGridYCellLength(String sGridName, float fLength) {

    //Go through our grids and find this one
    DetailedGridSettings oSettings;
    int i;
    for (i = 0; i < mp_oGridSettings.size(); i++) {
      oSettings = mp_oGridSettings.get(i);
      if (oSettings.getName().equals(sGridName)) {
        oSettings.setYCellLength(fLength);
      }
    }
  }

  /**
   * Counts the number of timesteps in the tarball and places it in
   * m_iNumTimesteps.  The value is found by counting the number of
   * files.  The number of timesteps is the number of files - 2 (for the
   * parameter file and initial conditions).  This is compared to the
   * number of timesteps found in the header file and the lesser one is chosen.
   * @throws ModelException - wrapping other exceptions
   */
  private void countTimesteps() throws ModelException {
    FileInputStream oFileStream = null;
    TarInputStream oTarballStream = null;
    try {
      TarEntry oTarEntry;

      int iCountedTimesteps;

      //Count the number of files in the tarball - it should be number of
      //timesteps plus 2 (for detailed output header and initial conditions)
      iCountedTimesteps = 0;

      oFileStream = new FileInputStream(m_sFilename);
      oTarballStream = new TarInputStream(oFileStream);
      oTarEntry = oTarballStream.getNextEntry();

      while (oTarEntry != null) {
        iCountedTimesteps++;
        oTarEntry = oTarballStream.getNextEntry();
      }

      if (iCountedTimesteps < m_iNumParFileTimesteps + 2) {
        m_iNumTimesteps = iCountedTimesteps - 2;
      }
      else {
        m_iNumTimesteps = m_iNumParFileTimesteps;
      }

    }
    catch (IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "DoDetailedOutputSetup",
                               m_sFilename));
    }
    finally {
      try {
        if (null != oTarballStream) {
          oTarballStream.close();
        }
        if (null != oFileStream) {
          oFileStream.close();
        }
      }
      catch (java.io.IOException e) {
        ; //do nothing
      }
    }
  }

  /**
   * Parses the parameter file portion of the tarball.  This function parses
   * the detailed output header file to find species information and tree setup
   * information.
   * @throws ModelException if the file cannot be parsed.
   */
  private void parseParamFile() throws ModelException {
    InputStream oInputStream = null;
    String sFileToRead = "";
    try {
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new DetailedOutputFileSetupParseHandler(this);
      m_oParser.setContentHandler(oHandler);

      //Create the string which has the filename of the detailed output file
      sFileToRead = m_sFileRoot + ".xml.gz";
      //Trim off path and add temp directory
      sFileToRead = sFileToRead.substring(sFileToRead.lastIndexOf("\\") + 1);
      sFileToRead = m_sTempDir + sFileToRead;

      //Parse the file
      oInputStream = sortie.fileops.Tarball.getUnzipFileStream(sFileToRead);
      InputSource oToParse = new InputSource(oInputStream);

      m_oParser.parse(oToParse);
    }
    catch (IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "DoDetailedOutputSetup",
                              sFileToRead));

    }
    catch (SAXException e) {
      throw(new ModelException(ErrorGUI.BAD_XML_FILE, "JAVA", e.getMessage()));
    }
    finally {
      if (oInputStream != null) {
        try {
          oInputStream.close();
        }
        catch (java.io.IOException e) {
          ; //do nothing
        }
      }
    }
  }

  /**
   * Gets the number of species that exist in the detailed output header file.
   * @return The number of species.
   */
  public int getNumberOfSpecies() {
    return mp_sSpeciesNames.size();
  }

  /**
   * Gets the number of tree types.
   * @return The number of types.
   */
  public int getNumberOfTypes() {
    return m_iNumTypes;
  }

  /**
   * When passed the name of a species, will give back the corresponding
   * species number code.
   * @param sName The name of the species.
   * @return The code.
   * @throws ModelException if the name is not recognized.
   */
  public int getSpeciesCodeFromName(String sName) throws ModelException {
    int i;

    for (i = 0; i < mp_sSpeciesNames.size(); i++) {
      if (sName.equalsIgnoreCase(mp_sSpeciesNames.get(i))) {
        return i;
      }
    }
    throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                             "Unrecognized species " + sName +
                             " passed to detailed output file manager for " +
                             m_sFilename));
  }

  /**
   * When passed the index of a species, will give back the corresponding
   * species name.
   * @param iIndex Species index.
   * @return Species name.
   * @throws ModelException if the index is unrecognized.
   */
  public String getSpeciesNameFromCode(int iIndex) throws ModelException {
    if (iIndex < 0 || iIndex >= mp_sSpeciesNames.size()) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                               "Unrecognized species index" +
                               String.valueOf(iIndex) +
                               " passed to detailed output file manager for " +
                               m_sFilename));
    }

    return mp_sSpeciesNames.get(iIndex);
  }

  /**
   * Read the detailed output file for a particular timestep.
   * @param iTimestep Timestep for which to read the file.
   * @throws ModelException If the file cannot be read.
   */
  public void readFile(int iTimestep) throws ModelException {
    InputStream oInputStream = null;
    InputSource oToParse = null;
    String sFileToRead = "";
    try {

      m_iLastTimestepRead = iTimestep;
      if (mp_oDataRequests.size() == 0) return;

      //Remove any requests that no longer have charts open if not in
      //batch mode      
  
      int i;
      if (m_bBatchMode == false) {
        DataRequest oDataRequest;
        for (i = 0; i < mp_oDataRequests.size(); i++) {
          oDataRequest = mp_oDataRequests.get(i);
          if (oDataRequest.m_oChartFrame != null &&
              oDataRequest.m_oChartFrame.isVisible() == false) {
            mp_oDataRequests.remove(i);
            i--;
          }
        }
      }
      if (mp_oDataRequests.size() == 0) return;
      
      //Tell the open data requests to clear all their old data and get 
      //ready for the new timestep
      for (DataRequest oDataRequest : mp_oDataRequests) {  
        oDataRequest.setCurrentTimestep(iTimestep);
        oDataRequest.getReadyForTimestepParse(iTimestep, m_bBatchMode);
      }
      
      //Create the string which has the filename of the detailed output file
      sFileToRead = m_sFileRoot + "_" + String.valueOf(iTimestep) + ".xml.gz";
      //Trim off path and add temp directory
      sFileToRead = sFileToRead.substring(sFileToRead.lastIndexOf("\\") + 1);
      sFileToRead = m_sTempDir + sFileToRead;

      //Now install the detailed output timestep handler
      DefaultHandler oHandler = new DetailedOutputTimestepParseHandler(this);
      m_oParser.setContentHandler(oHandler);

      //Parse the file
      try {
        oInputStream = sortie.fileops.Tarball.getUnzipFileStream(sFileToRead);
        oToParse = new InputSource(oInputStream);
      }  catch (Exception e) {
        if (e instanceof java.io.FileNotFoundException) {
          if (m_oManager.getModelState() == MainWindowStateSetter.PAUSED) {
            ; //fail silently to avoid interrupting the run
          } else {
            throw(e);
          }
        } else {
          throw(e);
        }
      }  

      m_oParser.parse(oToParse);

      //Tell the open data requests that we're done with the timestep
      for (DataRequest oDataRequest : mp_oDataRequests) {              
        oDataRequest.timestepParseFinished(m_bBatchMode);          
      }
    }
    catch (SAXException e) {
      throw(new ModelException(ErrorGUI.BAD_XML_FILE, "JAVA",
          "There were problems parsing " + sFileToRead));
    }
    catch (IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE,
          "DetailedOutputFileManager.ReadFile",
          sFileToRead));
    }
    catch (Exception e) {
      throw(new ModelException(ErrorGUI.UNKNOWN,
          "DetailedOutputFileManager.ReadFile",
          e.getMessage()));
    }
    finally {
      if (oInputStream != null) {
        try {
          oInputStream.close();
        }
        catch (java.io.IOException e) {
          ; //do nothing
        }
      }
    }
  }
  
  /**
   * Whether or not any open data requests want any tree float data members.
   * @return True if float data members are desired, false if not.
   */
  public boolean wantAnyTreeFloats() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyTreeFloats();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }
  
  /**
   * Whether or not any open data requests want any dead tree float data members.
   * @return True if float data members are desired, false if not.
   */
  public boolean wantAnyDeadTreeFloats() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyDeadTreeFloats();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }

  /**
   * Whether or not any open data requests want any grid float data members.
   * @return True if float data members are desired, false if not.
   */
  public boolean wantAnyGridFloats() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyGridFloats();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }

  /**
   * Whether or not any open data requests want any grid package float data
   * members.
   * @return True if float data members are desired, false if not.
   */
  public boolean wantAnyGridPackageFloats() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyGridPackageFloats();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }


  /**
   * Whether or not any open data requests want any tree int data members.
   * @return True if int data members are desired, false if not.
   */
  public boolean wantAnyTreeInts() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyTreeInts();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }
  
  /**
   * Whether or not any open data requests want any dead tree int data members.
   * @return True if int data members are desired, false if not.
   */
  public boolean wantAnyDeadTreeInts() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyDeadTreeInts();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }

  /**
   * Whether or not any open data requests want any grid int data members.
   * @return True if int data members are desired, false if not.
   */
  public boolean wantAnyGridInts() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyGridInts();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }

  /**
   * Whether or not any open data requests want any grid package int data
   * members.
   * @return True if int data members are desired, false if not.
   */
  public boolean wantAnyGridPackageInts() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyGridPackageInts();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }

  /**
   * Whether or not any open data requests want any tree char data members.
   * @return True if char data members are desired, false if not.
   */
  public boolean wantAnyTreeChars() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyTreeChars();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }
  
  /**
   * Whether or not any open data requests want any dead tree char data members.
   * @return True if char data members are desired, false if not.
   */
  public boolean wantAnyDeadTreeChars() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyDeadTreeChars();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }

  /**
   * Whether or not any open data requests want any grid char data members.
   * @return True if char data members are desired, false if not.
   */
  public boolean wantAnyGridChars() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyGridChars();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }

  /**
   * Whether or not any open data requests want any grid package char data
   * members.
   * @return True if char data members are desired, false if not.
   */
  public boolean wantAnyGridPackageChars() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyGridPackageChars();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }

  /**
   * Whether or not any open data requests want any tree bool data members.
   * @return True if bool data members are desired, false if not.
   */
  public boolean wantAnyTreeBools() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyTreeBools();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }
  
  /**
   * Whether or not any open data requests want any dead tree bool data members.
   * @return True if bool data members are desired, false if not.
   */
  public boolean wantAnyDeadTreeBools() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyDeadTreeBools();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }

  /**
   * Whether or not any open data requests want any grid bool data members.
   * @return True if bool data members are desired, false if not.
   */
  public boolean wantAnyGridBools() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyGridBools();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }

  /**
   * Whether or not any open data requests want any grid package bool data
   * members.
   * @return True if bool data members are desired, false if not.
   */
  public boolean wantAnyGridPackageBools() {
    DataRequest oRequest;
    int i;
    boolean bWants = false;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oRequest = mp_oDataRequests.get(i);
      bWants = oRequest.wantAnyGridPackageBools();
      if (bWants) {
        break;
      }
    }

    return bWants;
  }

  /**
   * Accepts a piece of tree float data from the parser and passes it on to
   * open data requests.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param fVal Value.
   */
  public void addTreeFloatData(int iSpecies, int iType, int iCode, float fVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyTreeFloats()) {
        oDataRequest.addTreeFloatData(iSpecies, iType, iCode, fVal, m_bBatchMode);
      }
    }
  }
  
  /**
   * Accepts a piece of dead tree float data from the parser and passes it on to
   * open data requests.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param iDeadCode Dead code for this tree.
   * @param fVal Value.
   */
  public void addDeadTreeFloatData(int iSpecies, int iType, int iCode, 
      int iDeadCode, float fVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyDeadTreeFloats()) {
        oDataRequest.addDeadTreeFloatData(iSpecies, iType, iCode, iDeadCode, fVal, m_bBatchMode);
      }
    }
  }

  /**
   * Accepts a piece of grid float data from the parser and passes it on to
   * open data requests.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param fVal Value.
   */
  public void addGridFloatData(String sGridName, int iX, int iY, int iCode,
                               float fVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyGridFloats()) {
        oDataRequest.addGridFloatData(sGridName, iX, iY, iCode, fVal, m_bBatchMode);
      }
    }
  }

  /**
   * Accepts a piece of grid package float data from the parser and passes it
   * on to open data requests.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param fVal Value.
   */
  public void addGridPackageFloatData(String sGridName, int iX, int iY, int iCode,
                               float fVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyGridPackageFloats()) {
        oDataRequest.addGridPackageFloatData(sGridName, iX, iY, iCode, fVal, m_bBatchMode);
      }
    }
  }

  /**
   * Accepts a piece of tree int data from the parser and passes it on to open
   * data requests.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param iVal Value.
   */
  public void addTreeIntData(int iSpecies, int iType, int iCode, int iVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyTreeInts()) {
        oDataRequest.addTreeIntData(iSpecies, iType, iCode, iVal, m_bBatchMode);
      }
    }
  }

  /**
   * Accepts a piece of dead tree int data from the parser and passes it on to 
   * open data requests.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param iDeadCode Dead code for this tree.
   * @param iVal Value.
   */
  public void addDeadTreeIntData(int iSpecies, int iType, int iCode, 
      int iDeadCode, int iVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyDeadTreeInts()) {
        oDataRequest.addDeadTreeIntData(iSpecies, iType, iCode, iDeadCode, iVal, m_bBatchMode);
      }
    }
  }

  /**
   * Accepts a piece of grid int data from the parser and passes it on to open
   * data requests.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param iVal Value.
   */
  public void addGridIntData(String sGridName, int iX, int iY, int iCode,
                             int iVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyGridInts()) {
        oDataRequest.addGridIntData(sGridName, iX, iY, iCode, iVal, m_bBatchMode);
      }
    }
  }

  /**
   * Accepts a piece of grid package int data from the parser and passes it on
   * to open data requests.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param iVal Value.
   */
  public void addGridPackageIntData(String sGridName, int iX, int iY, int iCode,
                             int iVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyGridPackageInts()) {
        oDataRequest.addGridPackageIntData(sGridName, iX, iY, iCode, iVal, m_bBatchMode);
      }
    }
  }

  /**
   * Accepts a piece of tree char data from the parser and passes it on to open
   * data requests.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param sVal Value.
   */
  public void addTreeCharData(int iSpecies, int iType, int iCode, String sVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyTreeChars()) {
        oDataRequest.addTreeCharData(iSpecies, iType, iCode, sVal, m_bBatchMode);
      }
    }
  }
  
  /**
   * Accepts a piece of dead tree char data from the parser and passes it on to 
   * open data requests.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param iDeadCode Dead code for this tree.
   * @param sVal Value.
   */
  public void addDeadTreeCharData(int iSpecies, int iType, int iCode, 
      int iDeadCode, String sVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyDeadTreeChars()) {
        oDataRequest.addDeadTreeCharData(iSpecies, iType, iCode, iDeadCode, sVal, m_bBatchMode);
      }
    }
  }

  /**
   * Accepts a piece of grid char data from the parser and passes it on to open
   * data requests.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param sVal Value.
   */
  public void addGridCharData(String sGridName, int iX, int iY, int iCode,
                              String sVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyGridChars()) {
        oDataRequest.addGridCharData(sGridName, iX, iY, iCode, sVal, m_bBatchMode);
      }
    }
  }

  /**
   * Accepts a piece of grid package char data from the parser and passes it on
   * to open data requests.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param sVal Value.
   */
  public void addGridPackageCharData(String sGridName, int iX, int iY, int iCode,
                              String sVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyGridPackageChars()) {
        oDataRequest.addGridPackageCharData(sGridName, iX, iY, iCode, sVal, m_bBatchMode);
      }
    }
  }

  /**
   * Accepts a piece of tree bool data from the parser to pass on to open data
   * requests.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value and passes it on to open
   * data requests.
   * @param bVal Value.
   */
  public void addTreeBoolData(int iSpecies, int iType, int iCode, boolean bVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyTreeBools()) {
        oDataRequest.addTreeBoolData(iSpecies, iType, iCode, bVal, m_bBatchMode);
      }
    }
  }
  
  /**
   * Accepts a piece of dead tree bool data from the parser to pass on to open 
   * data requests.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value and passes it on to open
   * data requests.
   * @param iDeadCode Dead code for this tree.
   * @param bVal Value.
   */
  public void addDeadTreeBoolData(int iSpecies, int iType, int iCode, 
      int iDeadCode, boolean bVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyDeadTreeBools()) {
        oDataRequest.addDeadTreeBoolData(iSpecies, iType, iCode, iDeadCode, bVal, m_bBatchMode);
      }
    }
  }

  /**
   * Accepts a piece of grid bool data from the parser to pass on to open data
   * requests.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value and passes it on to open
   * data requests.
   * @param bVal Value.
   */
  public void addGridBoolData(String sGridName, int iX, int iY, int iCode,
                              boolean bVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyGridBools()) {
        oDataRequest.addGridBoolData(sGridName, iX, iY, iCode, bVal, m_bBatchMode);
      }
    }
  }

  /**
   * Accepts a piece of grid package bool data from the parser to pass on to
   * open data requests.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value and passes it on to open
   * data requests.
   * @param bVal Value.
   */
  public void addGridPackageBoolData(String sGridName, int iX, int iY, int iCode,
                              boolean bVal) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      if (oDataRequest.wantAnyGridPackageBools()) {
        oDataRequest.addGridPackageBoolData(sGridName, iX, iY, iCode, bVal, m_bBatchMode);
      }
    }
  }

  /**
   * Accepts a tree float data member code from the parser to pass on to open
   * data requests.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addTreeFloatDataMemberCode(int iSpecies, int iType, String sLabel,
                                         int iCode) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      oDataRequest.addTreeFloatDataMemberCode(iSpecies, iType, sLabel, iCode);
    }
  }

  /**
   * Accepts a grid float data member code from the parser to pass on to open
   * data requests.
   * @param sGridName Name of the grid for this data
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridFloatDataMemberCode(String sGridName, String sLabel,
                                         int iCode) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      oDataRequest.addGridFloatDataMemberCode(sGridName, sLabel, iCode);
    }
  }

  /**
   * Accepts a grid package float data member code from the parser to pass on
   * to open data requests.
   * @param sGridName Name of the grid for this data
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridPackageFloatDataMemberCode(String sGridName, String sLabel,
                                         int iCode) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      oDataRequest.addGridPackageFloatDataMemberCode(sGridName, sLabel, iCode);
    }
  }

  /**
   * Accepts an int data member code from the parser to pass on to open data
   * requests.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addTreeIntDataMemberCode(int iSpecies, int iType, String sLabel,
                                       int iCode) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      oDataRequest.addTreeIntDataMemberCode(iSpecies, iType, sLabel, iCode);
    }
  }

  /**
   * Accepts an int data member code from the parser to pass on to open data
   * requests.
   * @param sGridName Name of the grid for this data
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridIntDataMemberCode(String sGridName, String sLabel,
                                       int iCode) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      oDataRequest.addGridIntDataMemberCode(sGridName, sLabel, iCode);
    }
  }

  /**
   * Accepts a grid package int data member code from the parser to pass on to
   * open data requests.
   * @param sGridName Name of the grid for this data
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridPackageIntDataMemberCode(String sGridName, String sLabel,
                                       int iCode) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      oDataRequest.addGridPackageIntDataMemberCode(sGridName, sLabel, iCode);
    }
  }

  /**
   * Accepts a tree char data member code from the parser to pass on to open
   * data requests.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addTreeCharDataMemberCode(int iSpecies, int iType, String sLabel,
                                        int iCode) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      oDataRequest.addTreeCharDataMemberCode(iSpecies, iType, sLabel, iCode);
    }
  }

  /**
   * Accepts a grid char data member code from the parser to pass on to open
   * data requests.
   * @param sGridName Name of the grid for this data
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridCharDataMemberCode(String sGridName, String sLabel,
                                        int iCode) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      oDataRequest.addGridCharDataMemberCode(sGridName, sLabel, iCode);
    }
  }

  /**
   * Accepts a grid package char data member code from the parser to pass on
   * to open data requests.
   * @param sGridName Name of the grid for this data
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridPackageCharDataMemberCode(String sGridName, String sLabel,
                                        int iCode) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      oDataRequest.addGridPackageCharDataMemberCode(sGridName, sLabel, iCode);
    }
  }


  /**
   * Accepts a tree bool data member code from the parser to pass on to open
   * data requests.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addTreeBoolDataMemberCode(int iSpecies, int iType, String sLabel,
                                        int iCode) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      oDataRequest.addTreeBoolDataMemberCode(iSpecies, iType, sLabel, iCode);
    }
  }

  /**
   * Accepts a grid bool data member code from the parser to pass on to open
   * data requests.
   * @param sGridName Name of the grid for this data
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridBoolDataMemberCode(String sGridName, String sLabel,
                                        int iCode) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      oDataRequest.addGridBoolDataMemberCode(sGridName, sLabel, iCode);
    }
  }

  /**
   * Accepts a grid package bool data member code from the parser to pass on
   * to open data requests.
   * @param sGridName Name of the grid for this data
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridPackageBoolDataMemberCode(String sGridName, String sLabel,
                                        int iCode) throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      oDataRequest.addGridPackageBoolDataMemberCode(sGridName, sLabel, iCode);
    }
  }

  /**
   * Notifies all open data requests that a new package has ended.
   */
  public void endPackage() throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      oDataRequest.endPackage();
    }
  }
  
  /**
   * Notifies all open data requests that a tree record has ended.
   */
  public void endTree() throws SAXException {
    DataRequest oDataRequest;
    int i;

    for (i = 0; i < mp_oDataRequests.size(); i++) {
      oDataRequest = mp_oDataRequests.get(i);
      oDataRequest.endTree(m_bBatchMode);
    }
  }
  
  /**
   * Get the total number of detailed tree settings.
   * @return the total number of detailed tree settings.
   */
  public int getNumberTreeSettings() {return mp_oTreeSettings.size();}
  
  /**
   * Access a detailed tree output settings record.
   * @param i Index of the desired record.
   * @return The record at position i.
   */
  public DetailedTreeSettings getTreeSetting(int i) {
    return mp_oTreeSettings.get(i);    
  }

  /**
   * Gets the length of X cells for a grid.
   * @param sGridName Grid for which to get the X cell length.
   * @return The X cell length, in m, or 0 if the grid is not recognized.
   */
  public float getGridXCellLength(String sGridName) {
    DetailedGridSettings oSettings;
    float fLength = 0;
    int i;

    for (i = 0; i < mp_oGridSettings.size(); i++) {
      oSettings = mp_oGridSettings.get(i);
      if (sGridName.equals(oSettings.getName())) {
        fLength = oSettings.getXCellLength();
      }
    }
    return fLength;
  }

  /**
   * Gets the length of Y cells for a grid.
   * @param sGridName Grid for which to get the Y cell length.
   * @return The Y cell length, in m, or 0 if the grid is not recognized.
   */
  public float getGridYCellLength(String sGridName) {
    DetailedGridSettings oSettings;
    float fLength = 0;
    int i;

    for (i = 0; i < mp_oGridSettings.size(); i++) {
      oSettings = mp_oGridSettings.get(i);
      if (sGridName.equals(oSettings.getName())) {
        fLength = oSettings.getYCellLength();
      }
    }
    return fLength;
  }

  /**
   * Deletes all files from the tarball in the temp folder
   */
  public void cleanUp() throws ModelException {
    sortie.fileops.Tarball.cleanUp(m_sFilename, m_sTempDir, m_sTempRoot);
  }
  
  /**
   * Adds one of the cells of this subplot. This adds the area of the cell to
   * the total, taking into account odd-sized end cells.
   * @param iX X cell
   * @param iY Y cell
   */
  public void addSubplotCell(int iX, int iY) {
  	float fXLength, fYLength;
  	int iNumXCells = (int)java.lang.Math.ceil(m_fXPlotLength / m_fSubplotXCellLength),
  	    iNumYCells = (int)java.lang.Math.ceil(m_fYPlotLength / m_fSubplotYCellLength); 
  	if (iX < iNumXCells - 1) {
      fXLength = m_fSubplotXCellLength; 
    } else {
      fXLength = m_fXPlotLength - (iX * m_fSubplotXCellLength); 
    }   
    if (iY < iNumYCells - 1) {
      fYLength = m_fSubplotYCellLength; 
    } else {
    	fYLength = m_fYPlotLength - (iY * m_fSubplotYCellLength); 
    }
    m_fPlotArea += (fXLength * fYLength);
  }
  
  /**
   * Matches a graph name to a data request class and returns an instance of 
   * the appropriate class.
   * @param sGraphName Graph name.
   * @return New instance of the data request class that corresponds to the 
   * graph name.
   * @throws ModelException Passed through from created objects. 
   */
  private DataRequest interpretGraphName(String sGraphName) throws ModelException {
        
    if (sGraphName.startsWith("XY DBH Tree Map")) {
      String sDead = sGraphName.substring((sGraphName.indexOf(":") + 2));
      for (int j = 0; j < mp_sDeadCodeNames.length; j++) {
        if (mp_sDeadCodeNames[j].equals(sDead)) {
          return(new TreeMapDataRequest(this, sGraphName, j));          
        }
      }
    }
    else if (sGraphName.equalsIgnoreCase("Timestep Tree Writer")) {
      return(new TreeListWriter(sGraphName, this));
    }
    else if (sGraphName.startsWith("Crown Radius Tree Map")) {
      String sDead = sGraphName.substring((sGraphName.indexOf(":") + 2));
      for (int j = 0; j < mp_sDeadCodeNames.length; j++) {
        if (mp_sDeadCodeNames[j].equals(sDead)) {
          return(new TreeMapCrownRadDataRequest(this, sGraphName, j));
        }
      }
    }
    else if (sGraphName.endsWith("Histogram")) {
      //Find the index of the histogram
      int iIndex = 0, i;
      for (i = 0; i < mp_sHistogramNames.length; i++) {
        if (mp_sHistogramNames[i].equals(sGraphName)) {
          iIndex = i;
          break;
        }
      }
      if (sGraphName.startsWith("Grid")) {
        if (sGraphName.indexOf(" All ") > -1) {
          String sGridName = mp_sHistogramNames[i].substring(mp_sHistogramNames[i].
              indexOf(":") + 2, mp_sHistogramNames[i].indexOf(" - "));
          String sMemberName = mp_sHistogramNames[i].substring(mp_sHistogramNames[
              i].indexOf(" All ") + 5, mp_sHistogramNames[i].indexOf("Histogram") - 1);
          return(new GridAllSpeciesHistogramDataRequest(sMemberName,
                                              mp_bHistogramIsFloat[iIndex], this,
                                              sGraphName, sGridName));
        } else {
          String sGridName = mp_sHistogramNames[i].substring(mp_sHistogramNames[i].
              indexOf(":") + 2, mp_sHistogramNames[i].indexOf(" - "));
          String sMemberName = mp_sHistogramNames[i].substring(mp_sHistogramNames[
              i].indexOf(" - ") + 3, mp_sHistogramNames[i].indexOf("Histogram") - 1);
          return(new GridHistogramDataRequest(sMemberName,
                                              mp_bHistogramIsFloat[iIndex], this,
                                              sGraphName, sGridName));
        }
      } else {
        if (sGraphName.indexOf("Dead") > -1) {
          String sDead = sGraphName.substring(0, sGraphName.indexOf(" Dead"));
          sGraphName = sDead.substring(0, sDead.lastIndexOf(" "));
          sDead = sDead.substring(sDead.lastIndexOf(" ")+1);
          for (int j = 0; j < mp_sDeadCodeNames.length; j++) {
            if (sDead.equals(mp_sDeadCodeNames[j])) {
              return(new HistogramDataRequest(sGraphName,
                  mp_bHistogramIsFloat[iIndex], this, sGraphName, j));
            }
          }       
        } else {
          return(new HistogramDataRequest(sGraphName.substring(0,
              sGraphName.indexOf(" Histogram")),
              mp_bHistogramIsFloat[iIndex], this, sGraphName, 
              OutputBehaviors.NOTDEAD));
        }
      }
    }

    //Grid map object
    else if (sGraphName.startsWith("Map")) {

      //Parse out the grid name and data member name
      String sGridName = sGraphName.substring(sGraphName.indexOf(" - ") + 3,
                                              sGraphName.lastIndexOf(" - "));
      String sDataMember = sGraphName.substring(sGraphName.lastIndexOf(" - ") +
                                                3);
      return(new GridDataRequest(sGraphName, sGridName, sDataMember, this));
    }

    //Tables
    else if (sGraphName.equals("Whole plot: Table")) {
      return(new OverviewTableDataRequest(this, sGraphName));
    }
    else if (sGraphName.equals("BA Stand Table")) {
      return(new StandTableDataRequest(this, sGraphName,
                                           StandTableDataRequest.BASAL_AREA));
    }
    else if (sGraphName.equals("Density Stand Table")) {
      return(new StandTableDataRequest(this, sGraphName,
                                           StandTableDataRequest.DENSITY));
    }
    else if (sGraphName.equals("Stock Table")) {
      return(new StockTableDataRequest(this, sGraphName));
    }
    else if (sGraphName.endsWith("Harvest Results") ||
             sGraphName.endsWith("Mortality Episode Results")) {
      return(new HarvestDataRequest(sGraphName, this));
    }

    else if (sGraphName.endsWith("Windstorm Results")) {
      return(new WindstormDataRequest(sGraphName, this));
    }

    else if (sGraphName.endsWith("Merchantable Timber Value")) {
      return(new MerchValueDataRequest(sGraphName, this));
    }

    else if (sGraphName.endsWith("Carbon Value")) {
      return(new CarbonValueDataRequest(sGraphName, this));
    }

    else if (sGraphName.endsWith("Partitioned Biomass") &&
             !sGraphName.endsWith("Storm Killed Partitioned Biomass")) {
      return(new PartitionedBiomassTableDataRequest(sGraphName, this));
    }
    
    else if (sGraphName.endsWith("Foliar Chemistry")) {
      return(new FoliarChemistryTableDataRequest(sGraphName, this));
    }
    
    else if (sGraphName.endsWith("Dispersed Seeds")) {
      return(new SeedTableDataRequest(sGraphName, this));
    }

    else if (sGraphName.endsWith("Storm Killed Partitioned Biomass")) {
      return(new StormKilledPartitionedBiomassDataRequest(sGraphName, this));
    }
    else if (sGraphName.endsWith("Storm Summary")) {
      return(new StormDataRequest(sGraphName, this));
    }
    else if (sGraphName.endsWith("State Variables")) {
      return(new StateVariableTableDataRequest(sGraphName, this)); 
    }


    //Line graphs
    else if (sGraphName.equals("Seedling Abs Density")) {
      return(new LineGraphDataRequest(this, sGraphName,
                                          LineGraphDataRequest.ABSOLUTE_DENSITY,
                                          sortie.data.funcgroups.TreePopulation.SEEDLING,
                                          OutputBehaviors.NOTDEAD));
    }
    else if (sGraphName.equals("Seedling Rel Density")) {
      return(new LineGraphDataRequest(this, sGraphName,
                                          LineGraphDataRequest.RELATIVE_DENSITY,
                                          sortie.data.funcgroups.TreePopulation.SEEDLING,
                                          OutputBehaviors.NOTDEAD));
    }
    else if (sGraphName.equals("Sapling Abs Density")) {
      return(new LineGraphDataRequest(this, sGraphName,
                                          LineGraphDataRequest.ABSOLUTE_DENSITY,
                                          sortie.data.funcgroups.TreePopulation.SAPLING,
                                          OutputBehaviors.NOTDEAD));
    }
    else if (sGraphName.equals("Sapling Rel Density")) {
      return(new LineGraphDataRequest(this, sGraphName,
                                          LineGraphDataRequest.RELATIVE_DENSITY,
                                          sortie.data.funcgroups.TreePopulation.SAPLING,
                                          OutputBehaviors.NOTDEAD));
    }
    else if (sGraphName.equals("Sapling Abs BA")) {
      return(new LineGraphDataRequest(this, sGraphName,
                                          LineGraphDataRequest.ABSOLUTE_BASAL_AREA,
                                          sortie.data.funcgroups.TreePopulation.SAPLING,
                                          OutputBehaviors.NOTDEAD));
    }
    else if (sGraphName.equals("Sapling Rel BA")) {
      return(new LineGraphDataRequest(this, sGraphName,
                                          LineGraphDataRequest.RELATIVE_BASAL_AREA,
                                          sortie.data.funcgroups.TreePopulation.SAPLING,
                                          OutputBehaviors.NOTDEAD));
    }
    else if (sGraphName.equals("Adult Abs Density")) {
      return(new LineGraphDataRequest(this, sGraphName,
                                          LineGraphDataRequest.ABSOLUTE_DENSITY,
                                          sortie.data.funcgroups.TreePopulation.ADULT,
                                          OutputBehaviors.NOTDEAD));
    }
    else if (sGraphName.equals("Adult Rel Density")) {
      return(new LineGraphDataRequest(this, sGraphName,
                                          LineGraphDataRequest.RELATIVE_DENSITY,
                                          sortie.data.funcgroups.TreePopulation.ADULT,
                                          OutputBehaviors.NOTDEAD));
    }
    else if (sGraphName.equals("Adult Abs BA")) {
      return(new LineGraphDataRequest(this, sGraphName,
                                          LineGraphDataRequest.ABSOLUTE_BASAL_AREA,
                                          sortie.data.funcgroups.TreePopulation.ADULT,
                                          OutputBehaviors.NOTDEAD));
    }
    else if (sGraphName.equals("Adult Rel BA")) {
      return(new LineGraphDataRequest(this, sGraphName,
                                          LineGraphDataRequest.RELATIVE_BASAL_AREA,
                                          sortie.data.funcgroups.TreePopulation.ADULT,
                                          OutputBehaviors.NOTDEAD));
    }
    else if (sGraphName.equals("Snag Abs Density")) {
      return(new LineGraphDataRequest(this, sGraphName,
                                          LineGraphDataRequest.ABSOLUTE_DENSITY,
                                          sortie.data.funcgroups.TreePopulation.SNAG,
                                          OutputBehaviors.NOTDEAD));
    }
    else if (sGraphName.equals("Snag Rel Density")) {
      return(new LineGraphDataRequest(this, sGraphName,
                                          LineGraphDataRequest.RELATIVE_DENSITY,
                                          sortie.data.funcgroups.TreePopulation.SNAG,
                                          OutputBehaviors.NOTDEAD));
    }
    else if (sGraphName.equals("Snag Abs BA")) {
      return(new LineGraphDataRequest(this, sGraphName,
                                          LineGraphDataRequest.ABSOLUTE_BASAL_AREA,
                                          sortie.data.funcgroups.TreePopulation.SNAG,
                                          OutputBehaviors.NOTDEAD));
    }
    else if (sGraphName.equals("Snag Rel BA")) {
      return(new LineGraphDataRequest(this, sGraphName,
                                          LineGraphDataRequest.RELATIVE_BASAL_AREA,
                                          sortie.data.funcgroups.TreePopulation.SNAG,
                                          OutputBehaviors.NOTDEAD));
    }
    else if (sGraphName.toLowerCase().indexOf("abs den") > -1) {
      //This might have come from the recent charts list, and be from short
      //output; see if it has the format "[plot]:"
      if (sGraphName.indexOf(":") > -1) {
        if (!sGraphName.substring(0, sGraphName.indexOf(":")).toLowerCase().equals("whole plot")) {
          return null;
        }
        sGraphName = sGraphName.substring(sGraphName.indexOf(":")+1).trim();
      }
      
      int iType = sortie.data.funcgroups.TreePopulation.SAPLING,
      iDeadCode = OutputBehaviors.NOTDEAD;

      if (sGraphName.toLowerCase().indexOf("dead") > -1) {
        String sDead = sGraphName.substring(0, sGraphName.indexOf(' '));
        int k;
        for (k = 0; k < mp_sDeadCodeNames.length; k++) 
          if (sDead.equalsIgnoreCase(mp_sDeadCodeNames[k]))
            iDeadCode = k;
      }

      if (sGraphName.toLowerCase().indexOf("adult") > -1) 
        iType = sortie.data.funcgroups.TreePopulation.ADULT;
      else if (sGraphName.toLowerCase().indexOf("seedling") > -1)
        iType = sortie.data.funcgroups.TreePopulation.SEEDLING;
      else if (sGraphName.toLowerCase().indexOf("snag") > -1)
        iType = sortie.data.funcgroups.TreePopulation.SNAG;

      return(new LineGraphDataRequest(this, sGraphName,
          LineGraphDataRequest.ABSOLUTE_DENSITY,
          iType, iDeadCode));
    }
    else if (sGraphName.toLowerCase().indexOf("rel den") > -1) {
      //This might have come from the recent charts list, and be from short
      //output; see if it has the format "[plot]:"
      if (sGraphName.indexOf(":") > -1) {
        if (!sGraphName.substring(0, sGraphName.indexOf(":")).toLowerCase().equals("whole plot")) {
          return null;
        }
        sGraphName = sGraphName.substring(sGraphName.indexOf(":")+1).trim();
      }
      
      int iType = sortie.data.funcgroups.TreePopulation.SAPLING,
      iDeadCode = OutputBehaviors.NOTDEAD;

      if (sGraphName.toLowerCase().indexOf("dead") > -1) {
        String sDead = sGraphName.substring(0, sGraphName.indexOf(' '));
        int k;
        for (k = 0; k < mp_sDeadCodeNames.length; k++) 
          if (sDead.equalsIgnoreCase(mp_sDeadCodeNames[k]))
            iDeadCode = k;
      }

      if (sGraphName.toLowerCase().indexOf("adult") > -1) 
        iType = sortie.data.funcgroups.TreePopulation.ADULT;
      else if (sGraphName.toLowerCase().indexOf("seedling") > -1)
        iType = sortie.data.funcgroups.TreePopulation.SEEDLING;
      else if (sGraphName.toLowerCase().indexOf("snag") > -1)
        iType = sortie.data.funcgroups.TreePopulation.SNAG;

      return(new LineGraphDataRequest(this, sGraphName,
          LineGraphDataRequest.RELATIVE_DENSITY,
          iType, iDeadCode));
    }
    else if (sGraphName.toLowerCase().indexOf("abs ba") > -1) {
      //This might have come from the recent charts list, and be from short
      //output; see if it has the format "[plot]:"
      if (sGraphName.indexOf(":") > -1) {
        if (!sGraphName.substring(0, sGraphName.indexOf(":")).toLowerCase().equals("whole plot")) {
          return null;
        }
        sGraphName = sGraphName.substring(sGraphName.indexOf(":")+1).trim();
      }
      
      int iType = sortie.data.funcgroups.TreePopulation.SAPLING,
      iDeadCode = OutputBehaviors.NOTDEAD;

      if (sGraphName.toLowerCase().indexOf("dead") > -1) {
        String sDead = sGraphName.substring(0, sGraphName.indexOf(' '));
        int k;
        for (k = 0; k < mp_sDeadCodeNames.length; k++) 
          if (sDead.equalsIgnoreCase(mp_sDeadCodeNames[k]))
            iDeadCode = k;
      }

      if (sGraphName.toLowerCase().indexOf("adult") > -1) 
        iType = sortie.data.funcgroups.TreePopulation.ADULT;
      else if (sGraphName.toLowerCase().indexOf("snag") > -1)
        iType = sortie.data.funcgroups.TreePopulation.SNAG;

      return(new LineGraphDataRequest(this, sGraphName,
          LineGraphDataRequest.ABSOLUTE_BASAL_AREA,
          iType, iDeadCode));
    }
    else if (sGraphName.toLowerCase().indexOf("rel ba") > -1) {
      //This might have come from the recent charts list, and be from short
      //output; see if it has the format "[plot]:"
      if (sGraphName.indexOf(":") > -1) {
        if (!sGraphName.substring(0, sGraphName.indexOf(":")).toLowerCase().equals("whole plot")) {
          return null;
        }
        sGraphName = sGraphName.substring(sGraphName.indexOf(":")+1).trim();
      }
      
      int iType = sortie.data.funcgroups.TreePopulation.SAPLING,
      iDeadCode = OutputBehaviors.NOTDEAD;

      if (sGraphName.toLowerCase().indexOf("dead") > -1) {
        String sDead = sGraphName.substring(0, sGraphName.indexOf(' '));
        int k;
        for (k = 0; k < mp_sDeadCodeNames.length; k++) 
          if (sDead.equalsIgnoreCase(mp_sDeadCodeNames[k]))
            iDeadCode = k;
      }

      if (sGraphName.toLowerCase().indexOf("adult") > -1) 
        iType = sortie.data.funcgroups.TreePopulation.ADULT;
      else if (sGraphName.toLowerCase().indexOf("snag") > -1)
        iType = sortie.data.funcgroups.TreePopulation.SNAG;

      return(new LineGraphDataRequest(this, sGraphName,
          LineGraphDataRequest.RELATIVE_BASAL_AREA,
          iType, iDeadCode));
    }
    else if (sGraphName.toLowerCase().indexOf("volume") > -1) {
      int iChartType = LineGraphDataRequest.ABSOLUTE_VOLUME,
          iType = sortie.data.funcgroups.TreePopulation.SAPLING,
          iDeadCode = OutputBehaviors.NOTDEAD;
      if (sGraphName.toLowerCase().indexOf("relative") > -1 ||
          sGraphName.toLowerCase().indexOf("rel") > -1)
        iChartType = LineGraphDataRequest.RELATIVE_VOLUME;
      
      if (sGraphName.toLowerCase().indexOf("dead") > -1) {
        String sDead = sGraphName.substring(0, sGraphName.indexOf(' '));
        int k;
        for (k = 0; k < mp_sDeadCodeNames.length; k++) 
          if (sDead.equalsIgnoreCase(mp_sDeadCodeNames[k]))
            iDeadCode = k;
      }
      
      if (sGraphName.toLowerCase().indexOf("adult") > -1) 
        iType = sortie.data.funcgroups.TreePopulation.ADULT;
      else if (sGraphName.toLowerCase().indexOf("snag") > -1)
        iType = sortie.data.funcgroups.TreePopulation.SNAG;
      
      return(new LineGraphDataRequest(this, sGraphName,
                                          iChartType, iType, iDeadCode));
    }
    else if (sGraphName.toLowerCase().indexOf("biomass") > -1 &&
             (sGraphName.toLowerCase().indexOf("rel") > -1 ||
              sGraphName.toLowerCase().indexOf("abs") > -1)) {
      int iChartType = LineGraphDataRequest.ABSOLUTE_BIOMASS,
          iType = sortie.data.funcgroups.TreePopulation.SAPLING,
          iDeadCode = OutputBehaviors.NOTDEAD;
      if (sGraphName.toLowerCase().indexOf("rel") > -1)
        iChartType = LineGraphDataRequest.RELATIVE_BIOMASS;
      
      if (sGraphName.toLowerCase().indexOf("dead") > -1) {
        String sDead = sGraphName.substring(0, sGraphName.indexOf(' '));
        int k;
        for (k = 0; k < mp_sDeadCodeNames.length; k++) 
          if (sDead.equalsIgnoreCase(mp_sDeadCodeNames[k]))
            iDeadCode = k;
      }
      
      if (sGraphName.toLowerCase().indexOf("adult") > -1) 
        iType = sortie.data.funcgroups.TreePopulation.ADULT;
      else if (sGraphName.toLowerCase().indexOf("snag") > -1)
        iType = sortie.data.funcgroups.TreePopulation.SNAG;
      
      return(new LineGraphDataRequest(this, sGraphName,
                                          iChartType, iType, iDeadCode));
    }
    else if (sGraphName.equals("Ripley's K")) {
      return(new RipleysKDataRequest(sGraphName, this));
    }
    else if (sGraphName.equals("Relative Neighborhood Density")) {
      return(new RelativeNeighborhoodDensityDataRequest(sGraphName, this));
    }
    else if (sGraphName.startsWith("Foliar Chemistry")) {
      return(new FoliarChemistryLineGraphDataRequest(sGraphName, this));
    }
    else if (sGraphName.startsWith("Biomass")) {
      return(new PartitionedBiomassLineGraphDataRequest(sGraphName, this));
    }
    else if (sGraphName.equals("Substrate")) {
      return(new SubstrateLineGraphDataRequest(sGraphName, this));
    }
    return null;    
  }
  
  public void addChartForBatchExtraction(String sGraphName) throws ModelException {
    
  }
}
