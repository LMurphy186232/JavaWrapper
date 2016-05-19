package sortie.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.help.HelpBroker;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import sortie.data.funcgroups.AnalysisBehaviors;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.DisperseBehaviors;
import sortie.data.funcgroups.DisturbanceBehaviors;
import sortie.data.funcgroups.EpiphyticEstablishmentBehaviors;
import sortie.data.funcgroups.EstablishmentBehaviors;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.GridValue;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.funcgroups.LightBehaviors;
import sortie.data.funcgroups.ManagementBehaviors;
import sortie.data.funcgroups.MortalityBehaviors;
import sortie.data.funcgroups.MortalityUtilitiesBehaviors;
import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.funcgroups.PlantingBehaviors;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.PlotBehaviors;
import sortie.data.funcgroups.SeedPredationBehaviors;
import sortie.data.funcgroups.SnagDynamicsBehaviors;
import sortie.data.funcgroups.StateChangeBehaviors;
import sortie.data.funcgroups.SubstrateBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.gui.behaviorsetup.ParameterEdit;
import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.XMLFileFilter;
import sortie.gui.modelflowsetup.ModelFlowSetup;
import sortie.sax.ParameterFileParser;
import sortie.tools.parfileupdater.OldParFileParser;

/**
 * The GUI manager provides all behind-the-scenes functionality for the Main
 * Window.  It performs a function very similar to the Simulation Manager in
 * the C++ code.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies </p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 16, 2009: Created (LEM)
 * <br>May 14, 2015: Changed new parameter file function (LEM)
 */

public class GUIManager {

  /**Path and name of parameter currently loaded, if any.*/
  private String m_sParameterFile;

  /**Flag for whether or not a loaded parameter file was modified*/
  private boolean m_bWasParameterFileModified;

  /**This is an array of behaviors and populations, in the order in which
     they would appear in the parameter file. I have this as protected instead
     of private so I can do outrageous testing manipulations*/
  protected BehaviorTypeBase[] mp_oManagedObjects;

  /**Array of grid objects. BehaviorTypeBase objects create the members of
   * this grid.*/
  private ArrayList<Grid> mp_oManagedGrids = new ArrayList<Grid>(0);

  /**Pointer to main application window.*/
  private MainWindow m_oMainWindow;

  /**This stands for the parameter file XML file type. This matches the
   * value in the C++ file "DataTypes.h".*/
  protected final String PARAMETER_FILE = "01";

  /**This stands for the detailed output file XML file type. This matches the
   * value in the C++ file "DataTypes.h".*/
  protected final String DETAILED_OUTPUT_FILE = "06";

  /**This stands for the detailed output timestep file XML file type. This
   * matches the value in the C++ file "DataTypes.h".*/
  protected final String DETAILED_OUTPUT_TIMESTEP = "07";

  /**This stands for the batch file XML file type.  This
   * matches the value in the C++ file "DataTypes.h".*/
  protected final String BATCH_FILE = "04";

  /**
   * Constructor. This creates the objects with no data in them so they can
   * be filled during a new parameter file setup.
   * @param oWindow Main application window.
   * @throws ModelException if something goes wrong with object setup.
   */
  public GUIManager(MainWindow oWindow) throws ModelException {
    m_oMainWindow = oWindow;
    clearCurrentData();
  }

  /**
   * Whether or not the current parameter file has been modified.
   * @return true if modified, false if not.
   */
  public boolean hasParameterFileBeenModified() {
    return m_bWasParameterFileModified;
  }

  /**
   * Gets the HelpBroker object which manages help display for the entire
   * application.
   * @return System HelpBroker object.
   */
  public HelpBroker getHelpBroker() {
    return m_oMainWindow.m_oHelpBroker;
  }

  /**
   * This can be used to propose that a new parameter file is being created.
   * If there is currently no parameter file name, this will accept the
   * proposal and place the key "<New>" in the parameter file name.  Then,
   * everything is set as through there is a parameter file entered.
   * 
   * If it is not time to create a new parameter file, nothing happens.
   * 
   * This is called by the TreeSetup dialog when changes have been made because
   * it's the first step in creating a new file.  It does not know whether it's
   * modifying an existing file or creating a new one, so it calls this so
   * the GUIManager object can decide.
   * @throws ModelException if anything goes wrong with setup.
    */
  public void proposeNewParameterFile() throws ModelException {
    if (m_sParameterFile.length() == 0) {
      m_sParameterFile = "<New>";
      m_oMainWindow.setModelState(MainWindowStateSetter.PAR_FILE_LOADED);
      m_bWasParameterFileModified = true;
      //doSetup(); Don't do this - it's already been called from tree species window
    }
  }

  /**
   * Gets the parameter file name.
   * @return The parameter file name.
   */
  public String getParameterFileName() {
    return m_sParameterFile;
  }

  /**
   * Display the window which allows the user to set up output options.
    */
  public void displayOutputWindow() {
    //If there's no parameter file loaded, don't do this
    if (m_sParameterFile.length() == 0) {
      JOptionPane.showMessageDialog(m_oMainWindow, "Please load a parameter file.",
                              "Error",
                              JOptionPane.ERROR_MESSAGE);
      return;
    }

    getOutputBehaviors().displayWindow(m_oMainWindow);
    m_bWasParameterFileModified = true;
  }

  /**
   * Displays the window which allows the user to set up tree species.
   */
  public void displayTreeSpeciesSetupWindow() {
    TreeSpeciesSetup oTreeWindow = new TreeSpeciesSetup(m_oMainWindow, getTreePopulation());
    oTreeWindow.pack();
    oTreeWindow.setLocationRelativeTo(null);
    oTreeWindow.setVisible(true);
    m_bWasParameterFileModified = true;
  }
  
  /**
   * Displays the window which allows the user to set up tree initial density
   * size classes.
   */
  public void displayTreeSizeClassEditor() {
    SizeClassEditor oEditor = new SizeClassEditor(m_oMainWindow, getTreePopulation());
    oEditor.pack();
    oEditor.setLocationRelativeTo(null);
    oEditor.setVisible(true);
    m_bWasParameterFileModified = true;
  }
    
  /**
   * Displays the window which allows the user to manage tree maps.
   */
  public void displayManageTreeMap() throws ModelException {
    ManageTreeMap oEditor = new ManageTreeMap(m_oMainWindow, getTreePopulation());
    oEditor.pack();
    oEditor.setLocationRelativeTo(null);
    oEditor.setVisible(true);
    m_bWasParameterFileModified = true;
  }

  /**
   * Displays the window which allows the user to set up grids.
   */
  public void displayGridSetupWindow() {
    GridSetup oGridWindow = new GridSetup(m_oMainWindow, this);
    oGridWindow.pack();
    oGridWindow.setLocationRelativeTo(null);
    oGridWindow.setVisible(true);
    m_bWasParameterFileModified = true;
  }

  /**
   * This passes a tree map file on to the tree population.
   * @param sFileName Name of file.
   * @throws ModelException if the tree population rejects the file.
   */
  public void inputTreeMap(String sFileName) throws ModelException {

    getTreePopulation().addTabDelimTreeMapFile(m_oMainWindow, sFileName);
    m_bWasParameterFileModified = true;
  }

  /**
   * Accepts an XML file for input.  The file type is determined, and then
   * action is taken depending on type.  If the file type is a parameter file
   * or a detailed output setup file, existing data is cleared and then the
   * file is parsed.  If the file type is detailed output timestep, the user is
   * asked whether they want to replace or add to existing data.  The
   * appropriate step is then taken.
   * @param sFileName File name of file to input.
   * @param oWindow Parent window.
   * @throws ModelException if the file cannot be recognized, cannot be parsed,
   * or is invalid; or if this is a data file and there is no parameter file
   * loaded.
   * @return boolean Whether the file that was read was a data file.  A batch
   * file causes this to be false; all else is true (assuming no errors).
   */
  public boolean inputXMLFile(String sFileName, JFrame oWindow) throws
      ModelException {
    FileReader oIn = null;
    boolean bReadDataFile = false;
    try {
      //Determine the file type
      oIn = new FileReader(sFileName);
      char[] cBuf = new char[100];
      int iRead = 0, iModelVersion = 7;
      String sTag = "fileCode=\"",
          sFileType = "";

      //Look for the file code
      while (iRead > -1) {
        iRead = oIn.read(cBuf, 0, 100);
        String sBuf = new String(cBuf);
        int iIndex = sBuf.indexOf(sTag);
        if (iIndex > -1) {
          int iStarter = iIndex + sTag.length() + 4;
          sFileType = sBuf.substring(iStarter, iStarter + 2);
          
          //Model major version
          iStarter = iIndex + sTag.length();
          iModelVersion = Integer.parseInt(sBuf.substring(iStarter, iStarter + 2));
          break;
        }
      }

      if (sFileType.equals(PARAMETER_FILE) ||
          sFileType.equals(DETAILED_OUTPUT_FILE)) {
        if (iModelVersion < 7) {
          //Let the user save as a new parameter file
          int iChoice = JOptionPane.showConfirmDialog(m_oMainWindow, 
              "This file must be converted to a version 7 file. Do you\n" +
              "wish to save a new version now?", "SORTIE", 
              JOptionPane.YES_NO_OPTION);
          if (iChoice == JOptionPane.NO_OPTION) return false;
          
          ModelFileChooser jChooser = new ModelFileChooser();
          jChooser.setFileFilter(new XMLFileFilter());

          iChoice = jChooser.showSaveDialog(m_oMainWindow);
          if (iChoice == JFileChooser.APPROVE_OPTION) {

            //User chose a file - does it already exist?
            File oFile = jChooser.getSelectedFile();
            if (oFile.exists()) {
              iChoice = JOptionPane.showConfirmDialog(m_oMainWindow,
                           "Do you wish to overwrite the existing file?",
                           "SORTIE",
                           JOptionPane.YES_NO_OPTION);
            }
            if (iChoice == JOptionPane.NO_OPTION) return false;
            String sNewFileName = oFile.getAbsolutePath();
            if (!sNewFileName.endsWith(".xml")) {
              sNewFileName = sNewFileName.concat(".xml");
            }
            convertPre7ParameterFile(sFileName, sNewFileName);
            sFileName = sNewFileName;
          }
        }
        inputXMLParameterFile(sFileName);
        bReadDataFile = true;
      }
      else if (sFileType.equals(DETAILED_OUTPUT_TIMESTEP)) {
        //Make sure there's a parameter file loaded
        if (m_oMainWindow.getModelState() ==
            MainWindowStateSetter.PAR_FILE_LOADED) {
          inputXMLDataFile(sFileName, oWindow);
          bReadDataFile = true;
        }
        else {
          throw (new ModelException(ErrorGUI.BAD_COMMAND, "JAVA",
                                    "You must load a parameter file before loading a file of this type."));
        }
      }
      else if (sFileType.equals(BATCH_FILE)) {
        //Load and display the batch file window

        BatchSetup oBatchWindow = new BatchSetup(m_oMainWindow, this, sFileName);
        oBatchWindow.pack();
        oBatchWindow.setLocationRelativeTo(null);
        oBatchWindow.setVisible(true);
      }
      else {
        throw(new ModelException(ErrorGUI.BAD_FILE_TYPE, "JAVA",
                                 "The file " + sFileName +
                                 " cannot be recognized as a SORTIE file."));
      }

      return bReadDataFile;

    }
    catch (IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "InputXMLFile - JAVA",
                               sFileName));
    }
    finally {
      try {
        if (oIn != null) {
          oIn.close();
        }
      }
      catch (java.io.IOException e) {
        ;
      }
    }
  }
  
  /**
   * Converts a pre-version 7 parameter file to version 7.
   * @param sOldFileName Filename of file to convert.
   * @param sNewFileName Filename of version 7 file to write.
   * @throws ModelException if the file cannot be parsed or is invalid.
   */
  public void convertPre7ParameterFile(String sOldFileName, String sNewFileName) throws ModelException {
    FileInputStream oFileStream = null;
    FileOutputStream jOutputStream = null;
    OutputStreamWriter jOutStreamWriter = null;
    BufferedWriter jOut = null;

    //Build a parser and parse the file
    try {
      //Make the output stream to write the file to
      jOutputStream = new FileOutputStream(sNewFileName);
      //Write the file in UTF-8 characters because that's what the XML parser
      //wants
      jOutStreamWriter = new OutputStreamWriter(jOutputStream, "UTF-8");
      jOut = new BufferedWriter(jOutStreamWriter);
      //Create our parser
      //Create the parser this way - don't go through the SAXParserFactory!
      //Parsers created that way throw MalformedURLExceptions when asked to
      //read local files.
      XMLReader oParser = XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new OldParFileParser(jOut);
      oParser.setContentHandler(oHandler);
      //Parse the file
      //Create a FileInputStream object to wrap in an InputSource object.
      //This method allows characters with diacritical marks in the filename,
      //whereas simply feeding the filename to InputSource directly does not
      oFileStream = new FileInputStream(sOldFileName);
      InputSource oToParse = new InputSource(oFileStream);
      oParser.parse(oToParse);      
    }
    catch (SAXException oE) {
      throw(new ModelException(ErrorGUI.BAD_XML_FILE, "JAVA", oE.getMessage()));
    }
    catch (IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE,
                               "convertPre7ParameterFile - JAVA",
                               sOldFileName));
    }
    finally {
      try {
        if (oFileStream != null) {oFileStream.close();}
      } catch (java.io.IOException oErr) {;}
      try {
        if (jOut != null) {jOut.close();}
      } catch (java.io.IOException oErr) {;}
      try {
        if (jOutStreamWriter != null) {jOutStreamWriter.close();}
      } catch (IOException oErr) {;}
      try {
        if (jOutputStream != null) {jOutputStream.close();}
      } catch (IOException oErr) {;}
    }
  }

  /**
   * Inputs an XML parameter file.
   * @param sFileName XML parameter file name
   * @throws ModelException if the file cannot be parsed or is invalid.
   */
  public void inputXMLParameterFile(String sFileName) throws ModelException {
    clearCurrentData();
    FileInputStream oFileStream = null;

    //Build a parser and parse the file
    try {
      //Create our parser
      //Create the parser this way - don't go through the SAXParserFactory!
      //Parsers created that way throw MalformedURLExceptions when asked to
      //read local files.
      XMLReader oParser = XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new ParameterFileParser(this);
      oParser.setContentHandler(oHandler);
      //Parse the file
      //Create a FileInputStream object to wrap in an InputSource object.
      //This method allows characters with diacritical marks in the filename,
      //whereas simply feeding the filename to InputSource directly does not
      oFileStream = new FileInputStream(sFileName);
      InputSource oToParse = new InputSource(oFileStream);
      oParser.parse(oToParse);
      for (int i = 0; i < mp_oManagedObjects.length; i++) {
        mp_oManagedObjects[i].endOfParameterFileRead();
      }

      m_sParameterFile = sFileName;
      m_bWasParameterFileModified = false;
      
      fixListPositions();

      //Don't validate here - users should have the chance to load a file and
      //fix it
    }
    catch (SAXException oE) {
      throw(new ModelException(ErrorGUI.BAD_XML_FILE, "JAVA", oE.getMessage()));
    }
    catch (IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE,
                               "InputXMLParameterFile - JAVA",
                               sFileName));
    }
    finally {
      try {
        if (oFileStream != null) {
          oFileStream.close();
        }
      }
      catch (IOException oErr) {
        ; //do nothing
      }
    }
  }

  /**
   * Inputs an XML file which is not a parameter type.  This would probably be
   * a detailed output timestep file, a tree map, or a grid map.  This method
   * does not clear out existing data before parsing the file.
   * @param sFileName XML parameter file name
   * @param oWindow Parent application window
   * @throws ModelException if the file cannot be parsed or is invalid.
    */
  public void inputXMLDataFile(String sFileName, JFrame oWindow) throws
      ModelException {

    //Determine if the user wants to add to or replace any current data
    Object[] oButtonOptions = {
        "Replace", "Add To", "Cancel"};
    int iChoice = JOptionPane.showOptionDialog(oWindow,
      "What would you like to do with existing tree map and grid map data?",
      "", JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE,
      null, //don't use a custom Icon
      oButtonOptions, //the titles of buttons
      oButtonOptions[0]); //default button title

    if (iChoice == 2) {
      return;
    }
    if (iChoice == 0) {

      //Clean existing data but not settings
      getTreePopulation().clearTrees();

      int i;
      for (i = 0; i < mp_oManagedGrids.size(); i++) {
        mp_oManagedGrids.get(i).clearMapValues();
      }
    }

    //Build a parser and parse the file
    java.io.FileInputStream oFileStream = null;
    try {
      //Create our parser
      //Create the parser this way - don't go through the SAXParserFactory!
      //Parsers created that way throw MalformedURLExceptions when asked to
      //read local files.
      XMLReader oParser = XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new ParameterFileParser(this);
      oParser.setContentHandler(oHandler);
      //Parse the file
      //Create a FileInputStream object to wrap in an InputSource object.
      //This method allows characters with diacritical marks in the filename,
      //whereas simply feeding the filename to InputSource directly does not
      oFileStream = new java.io.FileInputStream(sFileName);
      InputSource oToParse = new InputSource(oFileStream);
      oParser.parse(oToParse);

      m_bWasParameterFileModified = true;
    }
    catch (SAXException oE) {
      throw(new ModelException(ErrorGUI.BAD_XML_FILE, "JAVA", oE.getMessage()));
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_XML_FILE,
                               "InputXMLParameterFile - JAVA", sFileName));
    }
    finally {
      try {
        if (oFileStream != null) {
          oFileStream.close();
        }
      }
      catch (java.io.IOException oErr) {
        ; //do nothing
      }
    }
  }

  /**
   * Gets whether or not the run is snag-aware.  This searches through all
   * behaviors looking for any that are applied to snags.  If none, then the
   * run is not snag-aware.
   * @return True if the run has any snags anywhere; false if it doesn't.
   * @throws ModelException passed through from called methods.  Should never
   * be thrown.
   */
  public boolean getSnagAwareness() throws ModelException {
    int i, j, k;

    BehaviorTypeBase[] p_oBehaviorGroups = getAllObjects();
    for (i = 0; i < p_oBehaviorGroups.length; i++) {
      ArrayList<Behavior> p_oBehaviors = p_oBehaviorGroups[i].getAllInstantiatedBehaviors();
      if (p_oBehaviors != null) {
        for (j = 0; j < p_oBehaviors.size(); j++) {
          for (k = 0; k < p_oBehaviors.get(j).getNumberOfCombos(); k++) {
            if (p_oBehaviors.get(j).getSpeciesTypeCombo(k).getType() ==
                TreePopulation.SNAG) {
              return true;
            }
          }          
        }
      }
    }
    return false;
  }

  /**
   * Checks to see if the currently loaded dataset is adequate for creating a
   * run and internally valid.  The dataset is valid if there is at least
   * one behavior enabled, and each managed object successfully validates its
   * internal data.
   * @throws ModelException if the dataset is not valid.
   */
  protected void validateDataSet() throws ModelException {

    TreePopulation oPop = getTreePopulation();
    int i;
    boolean bAnyEnabled = false;    

    //Make sure there are enabled behaviors
    BehaviorTypeBase[] p_oAllBehaviors = getAllObjects();
    for (i = 0; i < p_oAllBehaviors.length; i++) {
      if (p_oAllBehaviors[i].anyBehaviorsEnabled()) {
        bAnyEnabled = true;
      }
    }

    if (!bAnyEnabled) {
      throw(new ModelException(ErrorGUI.ILLEGAL_OP, "JAVA",
                               "The current run has no behaviors. Please use "
                               + "Edit->Model Flow to define behaviors for "
                               + "this run."));
    }

    //Have each object validate its data
    for (i = 0; i < mp_oManagedObjects.length; i++) {
      mp_oManagedObjects[i].validateData(oPop);
    }
    
    fixListPositions();
  }

  /**
   * Writes out the XML parameter file with the current settings.  Note that
   * it is not required that the data set be adequate or valid; this way
   * work can be saved in progress.  However, if the dataset is invalid,
   * the user will be warned of that fact.
   * @param sFileName  Filename of new file.
   * @return Whether or not a file was saved.
   * @throws ModelException If there is a problem validating the data or
   * writing the file.
    */
  public boolean writeParameterFile(String sFileName) throws ModelException {
    FileOutputStream jOutputStream = null;
    OutputStreamWriter jOutStreamWriter = null;
    BufferedWriter jOut = null;
    try {
      TreePopulation oPop = getTreePopulation();
      Plot oPlot = getPlot();

      //See if the dataset is valid; if it's not, warn the user
      try {
        validateDataSet();
      }
      catch (ModelException oErr) {

        //Invalid - warn user
        int iReturnValue = JOptionPane.showConfirmDialog(m_oMainWindow,
            "The data is not valid for a run.  Message:  " + oErr.getMessage() +
            " Would you like to save this file anyway?", "Continue with save?",
            JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        if (iReturnValue == JOptionPane.NO_OPTION) {
          return false;
        }
      }
      int i;

      jOutputStream = new FileOutputStream(sFileName);
      //Write the file in UTF-8 characters because that's what the XML parser
      //wants
      jOutStreamWriter = new OutputStreamWriter(jOutputStream, "UTF-8");
      jOut = new BufferedWriter(jOutStreamWriter);

      //Write the opening tag
      jOut.write(
          "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      jOut.write("<paramFile fileCode=\"07030101\">");

      //Write the plot info
      oPlot.writeXML(jOut, oPop);

      //Write the tree info
      oPop.getTreeBehavior().writeXML(jOut, oPop);

      //Write the behavior list
      jOut.write("<behaviorList>");

      //Write out all behaviors
      for (i = 0; i < mp_oManagedObjects.length; i++) {
        mp_oManagedObjects[i].writeBehaviorNodes(jOut, oPop);
      }

      jOut.write("</behaviorList>");
      
      //Write allometry
      oPop.getAllometry().writeXML(jOut, oPop);
      
      for (i = 0; i < mp_oManagedGrids.size(); i++) {
        mp_oManagedGrids.get(i).writeXML(jOut, oPlot);
      }

      //Write all other data objects
      for (i = 0; i < mp_oManagedObjects.length; i++) {
        if (! (mp_oManagedObjects[i] instanceof TreePopulation) &&
            ! (mp_oManagedObjects[i] instanceof PlotBehaviors)) {
          mp_oManagedObjects[i].writeXML(jOut, oPop);
        }
      }

      //Write the closing tag
      jOut.write("</paramFile>");
      jOut.flush();

      //Set the newly written file as the current parameter file
      m_sParameterFile = sFileName;
      //Set the modified flag to false
      m_bWasParameterFileModified = false;
      return true;
    }
    catch (IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
                           "There was a problem writing the parameter file."));
    }
    finally {
      try {
        if (jOut != null) {
          jOut.close();
        }
      }
      catch (java.io.IOException oErr) {
        ; //do nothing
      }
      try {
        if (jOutStreamWriter != null) {
          jOutStreamWriter.close();
        }
      }
      catch (java.io.IOException oErr) {
        ; //do nothing
      }
      try {
        if (jOutputStream != null) {
          jOutputStream.close();
        }
      }
      catch (java.io.IOException oErr) {
        ; //do nothing
      }
    }
  }

  /**
   * Gets the MainWindow object.
   * @return MainWindow MainWindow object.
   */
  public MainWindow getMainWindow() {
    return m_oMainWindow;
  }

  /**
   * Takes all loaded data and prepares a parameter file to pass to the core to
   * run, returning the filename of the parameter file.
   * @return The filename of the parameter file which can be passed to the core
   * code.
   * @throws ModelException if there are any data validation problems.
    */
  public String prepToRun() throws ModelException {

    //What was input?  If there's no parameter file name, throw an exception
    if (m_sParameterFile.length() == 0) {
      throw(new ModelException(ErrorGUI.MODEL_NOT_READY, "JAVA",
                               "Please enter a parameter file."));
    }

    //Validate the dataset
    validateDataSet();

    //If our file was not modified, pass it through
    if (!m_bWasParameterFileModified) {
      return m_sParameterFile;
    }

    //Ask if they want to save this file
    int iReturnVal = JOptionPane.showConfirmDialog(m_oMainWindow,
        "Do you wish to save a copy of this file now?", "Model",
        JOptionPane.YES_NO_OPTION);

    if (iReturnVal == JOptionPane.YES_OPTION) {
      m_oMainWindow.doFileSave();
      if (!m_bWasParameterFileModified) { //they may have cancelled out
        return m_sParameterFile;
      }
    }

    //The file was modified but the user doesn't want to save; so we'll have
    //to write a temp file.
    //This is just a temporary filename I've made up.  We'll always need one
    //if parameter file data has been modified but not saved.

    String sFilename = "sortie_temp.xml";
    writeParameterFile(sFilename);

    return sFilename;
  }

  /**
   * Performs the actions necessary to start a new parameter file.
   * @throws ModelException if there is a problem with setup.
    */
  public void createNewParameterFile() throws ModelException {

    //Trigger a save if an existing file was being edited
    if (m_bWasParameterFileModified) {
      int iReturnVal = JOptionPane.showConfirmDialog(m_oMainWindow,
          "Do you wish to save a copy of the current file?", "Model",
          JOptionPane.YES_NO_OPTION);

      if (iReturnVal == JOptionPane.YES_OPTION) {
        m_oMainWindow.doFileSave();
        if (m_bWasParameterFileModified) { //they may have cancelled out
          return;
        }
      }
    }

    clearCurrentData();
    m_oMainWindow.setModelState(MainWindowStateSetter.NO_PAR_FILE);

    //Open the tree setup window
    displayTreeSpeciesSetupWindow();

    //If they successfully completed it, ask them to open the model
    //flow window
    if (m_sParameterFile.equals("<New>")) {
      displayModelFlowWindow();
    }
  }

  /**
   * This clears old settings in order to accept a new parameter file.  If
   * there's no current data, this will make sure everything is set up to
   * accept new data.
   * 
   * The parameter file string is set to empty, and the parameter file modified
   * flag is set to false.
   * @throws ModelException if there are any problems.
    */
  public void clearCurrentData() throws ModelException {
    m_sParameterFile = "";
    m_bWasParameterFileModified = false;

    if (mp_oManagedObjects != null) {

      //Garbage-collect everything currently in there
      int i;

      for (i = 0; i < mp_oManagedObjects.length; i++) {
        mp_oManagedObjects[i] = null;
      }
      mp_oManagedGrids.clear();

      mp_oManagedObjects = null;
    }

    mp_oManagedObjects = new BehaviorTypeBase[18];
    mp_oManagedObjects[0] = new PlotBehaviors(this);
    mp_oManagedObjects[1] = new TreePopulation(this);
    mp_oManagedObjects[2] = new DisturbanceBehaviors(this);
    mp_oManagedObjects[3] = new ManagementBehaviors(this);
    mp_oManagedObjects[4] = new StateChangeBehaviors(this);
    mp_oManagedObjects[5] = new LightBehaviors(this);
    mp_oManagedObjects[6] = new GrowthBehaviors(this);
    mp_oManagedObjects[7] = new MortalityBehaviors(this);
    mp_oManagedObjects[8] = new SnagDynamicsBehaviors(this);
    mp_oManagedObjects[9] = new SubstrateBehaviors(this);
    mp_oManagedObjects[10] = new MortalityUtilitiesBehaviors(this);
    mp_oManagedObjects[11] = new DisperseBehaviors(this);
    mp_oManagedObjects[12] = new SeedPredationBehaviors(this);
    mp_oManagedObjects[13] = new EstablishmentBehaviors(this);
    mp_oManagedObjects[14] = new EpiphyticEstablishmentBehaviors(this);
    mp_oManagedObjects[15] = new PlantingBehaviors(this);
    mp_oManagedObjects[16] = new AnalysisBehaviors(this);
    mp_oManagedObjects[17] = new OutputBehaviors(this);

  }

  /**
   * Gets the OutputBehaviors object, or null if none exists.
   * @return OutputBehaviors object.
   */
  public OutputBehaviors getOutputBehaviors() {

    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof OutputBehaviors) {
        return (OutputBehaviors) mp_oManagedObjects[i];
      }
    }

    return null;
  }

  /**
   * Gets the DisperseBehaviors object, or null if none exists.
   * @return DisperseBehaviors object.
   */
  public DisperseBehaviors getDisperseBehaviors() {

    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof DisperseBehaviors) {
        return (DisperseBehaviors) mp_oManagedObjects[i];
      }
    }

    return null;
  }
  
  /**
   * Gets the SeedPredationBehaviors object, or null if none exists.
   * @return SeedPredationBehaviors object.
   */
  public SeedPredationBehaviors getSeedPredationBehaviors() {

    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof SeedPredationBehaviors) {
        return (SeedPredationBehaviors) mp_oManagedObjects[i];
      }
    }

    return null;
  }

  /**
   * Gets the SnagDynamicsBehaviors object, or null if none exists.
   * @return SnagDynamicsBehaviors object.
   */
  public SnagDynamicsBehaviors getSnagDynamicsBehaviors() {

    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof SnagDynamicsBehaviors) {
        return (SnagDynamicsBehaviors) mp_oManagedObjects[i];
      }
    }

    return null;
  }

  /**
   * Gets the AnalysisBehaviors object, or null if none exists.
   * @return AnalysisBehaviors object.
    */
  public AnalysisBehaviors getAnalysisBehaviors() {

    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof AnalysisBehaviors) {
        return (AnalysisBehaviors) mp_oManagedObjects[i];
      }
    }

    return null;
  }

  /**
   * Gets the EstablishmentBehaviors object, or null if none exists.
   * @return EstablishmentBehaviors object.
    */
  public EstablishmentBehaviors getEstablishmentBehaviors() {

    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof EstablishmentBehaviors) {
        return (EstablishmentBehaviors) mp_oManagedObjects[i];
      }
    }

    return null;
  }
  
  /**
   * Gets the EpiphyticEstablishmentBehaviors object, or null if none exists.
   * @return EpiphyticEstablishmentBehaviors object.
    */
  public EpiphyticEstablishmentBehaviors getEpiphyticEstablishmentBehaviors() {

    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof EpiphyticEstablishmentBehaviors) {
        return (EpiphyticEstablishmentBehaviors) mp_oManagedObjects[i];
      }
    }

    return null;
  }
  
  /**
   * Gets the ManagementBehaviors object, or null if none exists.
   * @return ManagementEstablishmentBehaviors object.
    */
  public ManagementBehaviors getManagementBehaviors() {

    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof ManagementBehaviors) {
        return (ManagementBehaviors) mp_oManagedObjects[i];
      }
    }

    return null;
  }
  
  /**
   * Gets the MortalityUtilitiesBehaviors object, or null if none exists.
   * @return MortalityUtilitiesBehaviors object.
    */
  public MortalityUtilitiesBehaviors getMortalityUtilitiesBehaviors() {

    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof MortalityUtilitiesBehaviors) {
        return (MortalityUtilitiesBehaviors) mp_oManagedObjects[i];
      }
    }

    return null;
  }
  
  /**
   * Gets the StateChangeBehaviors object, or null if none exists.
   * @return ManagementEstablishmentBehaviors object.
    */
  public StateChangeBehaviors getStateChangeBehaviors() {

    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof StateChangeBehaviors) {
        return (StateChangeBehaviors) mp_oManagedObjects[i];
      }
    }

    return null;
  }

  /**
   * Gets the Substrate object.
   * @return Substrate object, or null if none exists.
    */
  public SubstrateBehaviors getSubstrateBehaviors() {

    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof SubstrateBehaviors) {
        return (SubstrateBehaviors) mp_oManagedObjects[i];
      }
    }

    return null;
  }

  /**
   * Gets the MortalityBehaviors object.
   * @return MortalityBehaviors object, or null if none exists.
    */
  public MortalityBehaviors getMortalityBehaviors() {

    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof MortalityBehaviors) {
        return (MortalityBehaviors) mp_oManagedObjects[i];
      }
    }

    return null;
  }

  /**
   * Gets the GrowthBehaviors object.
   * @return GrowthBehaviors object, or null if none exists.
   */
  public GrowthBehaviors getGrowthBehaviors() {

    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof GrowthBehaviors) {
        return (GrowthBehaviors) mp_oManagedObjects[i];
      }
    }

    return null;
  }

  /**
   * Gets the Plot object.
   * @return Plot object, or null if none exists.
    */
  public Plot getPlot() {

    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof PlotBehaviors) {
        return (Plot) mp_oManagedObjects[i].getAllInstantiatedBehaviors().get(0);
      }
    }

    return null;
  }

  /**
   * Gets the tree population object.
   * @return TreePopulation object, or null if none exists.
    */
  public TreePopulation getTreePopulation() {

    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof TreePopulation) {
        return (TreePopulation) mp_oManagedObjects[i];
      }
    }
    return null;
  }
  

  /**
   * Gets the disturbance behaviors object, or null if none exists.
   * @return DisturbanceBehaviors object, or null if none exists.
    */
  public DisturbanceBehaviors getDisturbanceBehaviors() {
    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof DisturbanceBehaviors) {
        return (DisturbanceBehaviors) mp_oManagedObjects[i];
      }
    }
    return null;
  }

  /**
   * Gets the planting behaviors object, or null if none exists.
   * @return PlantingBehaviors object, or null if none exists.
     */
  public PlantingBehaviors getPlantingBehaviors() {
    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof PlantingBehaviors) {
        return (PlantingBehaviors) mp_oManagedObjects[i];
      }
    }
    return null;
  }

  /**
   * Gets the light behaviors object.
   * @return LightBehaviors object, or null if none exists.
    */
  public LightBehaviors getLightBehaviors() {
    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof LightBehaviors) {
        return (LightBehaviors) mp_oManagedObjects[i];
      }
    }
    return null;
  }

  /**
   * Gets the remove dead behaviors object.
   * @return RemoveDeadBehaviors object, or null if none exists.
   */
  public MortalityUtilitiesBehaviors getRemoveDeadBehaviors() {
    for (int i = 0; i < mp_oManagedObjects.length; i++) {
      if (mp_oManagedObjects[i] instanceof MortalityUtilitiesBehaviors) {
        return (MortalityUtilitiesBehaviors) mp_oManagedObjects[i];
      }
    }
    return null;
  }

  /**
   * Gets all of the Grid objects.
   * @return An array of behaviors.
    */
  public Grid[] getAllGrids() {
    Grid[] p_oToReturn = new Grid[mp_oManagedGrids.size()];
    int i;

    if (mp_oManagedGrids.size() == 0) {
      return null;
    }

    for (i = 0; i < p_oToReturn.length; i++) {
      p_oToReturn[i] = mp_oManagedGrids.get(i);
    }
    return p_oToReturn;
  }

  /**
   * Gets the array of managed objects.
   * @return The array of managed objects.
    */
  public BehaviorTypeBase[] getAllObjects() {
    return mp_oManagedObjects;
  }

  /**
   * Allows the user to edit parameters.
   */
  public void displayRunParameters() {
    ParameterEdit oEditWindow = new ParameterEdit(m_oMainWindow, this);
    oEditWindow.pack();
    oEditWindow.setLocationRelativeTo(null);
    oEditWindow.setVisible(true);
    m_bWasParameterFileModified = true;
  }

  /**
   * Allows the user to edit simulation flow and behavior order.
    */
  public void displayModelFlowWindow() {
    ModelFlowSetup oFlowWindow = new ModelFlowSetup(m_oMainWindow, this);
    oFlowWindow.pack();
    oFlowWindow.setLocationRelativeTo(null);
    oFlowWindow.setVisible(true);
    m_bWasParameterFileModified = true;
  }
  
  /**
   * This updates the list position numbers of all enabled behaviors.
   */
  private void fixListPositions() throws ModelException {
    int iPos = 1, i, j;
    for (i = 0; i < mp_oManagedObjects.length; i++) {
      if (!(mp_oManagedObjects[i] instanceof TreePopulation) && 
          !(mp_oManagedObjects[i] instanceof PlotBehaviors)) {
        ArrayList<Behavior> p_oBehaviors = mp_oManagedObjects[i].getAllInstantiatedBehaviors();
        for (j = 0; j < p_oBehaviors.size(); j++) {
          p_oBehaviors.get(j).setListPosition(iPos);
          iPos++;
        }
      }
    }
  }

  /**
   * Triggers the DoSetup() methods of all BehaviorTypeBase objects under
   * management.
   * @throws ModelException passed through from the BehaviorTypeBase objects.
    */
  public void doSetup() throws ModelException {
    TreePopulation oPop = getTreePopulation();
    if (oPop == null) {
      return;
    }
    int i;
    for (i = 0; i < mp_oManagedObjects.length; i++) {
      mp_oManagedObjects[i].doSetup(oPop);
    }
  }

  /**
   * This adds a new grid object to the list.
   * @param oNewGrid Grid Grid to add.
   * @param bReplace If true, this grid replaces any existing grids with this
   * name. If false, if it has the same name as
   * another grid object, the first grid is returned and this grid is dropped.
   * @return Grid added, or the existing grid if it already existed.
   */
  public Grid addGrid(Grid oNewGrid, boolean bReplace) {
    String sNewGrid = oNewGrid.getName(),
        sExistingGrid;
    int i;

    for (i = 0; i < mp_oManagedGrids.size(); i++) {
      sExistingGrid = mp_oManagedGrids.get(i).getName();
      if (sNewGrid.equals(sExistingGrid)) {
        if (bReplace) {
          mp_oManagedGrids.remove(i);
          i--;
        } else {
          return mp_oManagedGrids.get(i);
        }
      }
    }

    mp_oManagedGrids.add(oNewGrid);
    return oNewGrid;
  } 

  /**
   * Finds a grid when provided with the grid's name.
   * @param sName String Grid name.
   * @return Grid Grid, or null if there is no grid by that name.
   */
  public Grid getGridByName(String sName) {
    int i, iNumGrids = mp_oManagedGrids.size();

    for (i = 0; i < iNumGrids; i++) {
      if (mp_oManagedGrids.get(i).getName().equals(sName)) {
        return mp_oManagedGrids.get(i);
      }
    }
    return null;
  }
  
  /**
   * Finds a grid when provided with the grid's hashcode.
   * @param iHash Grid hash code.
   * @return Grid Grid, or null if there is no grid with that hash code.
   */
  public Grid getGridByHash(Integer iHash) {
    int i, iNumGrids = mp_oManagedGrids.size();

    for (i = 0; i < iNumGrids; i++) {
      if (mp_oManagedGrids.get(i).hashCode() == iHash.intValue()) {
        return mp_oManagedGrids.get(i);
      }
    }
    return null;
  }
  

  /**
   * Changes the list of species names.  This will change any names on the
   * grids and then call the same function for all WorkerBase-descended
   * objects.  This is called when everything about the species remains the
   * same except for the name.  This is an easier process than actually
   * changing the species list.
   * @param sOldSpecies String Old name of the species, with underscores
   * instead of spaces (like the species names would come from the tree
   * population)
   * @param sNewSpecies String New name of the species, with underscores
   * instead of spaces (like the species names would come from the tree
   * population)
   * @throws ModelException if there is a problem.
   */
  public void changeOfSpeciesName(String sOldSpecies, String sNewSpecies) throws
      ModelException {
    //Change the name of all grids
    if (mp_oManagedGrids != null || mp_oManagedGrids.size() > 0) {
      sOldSpecies = sOldSpecies.replace('_', ' ');
      sNewSpecies = sNewSpecies.replace('_', ' ');

      DataMember[] p_oDataMembers, p_oPackageDataMembers;
      String sDisplayName;
      int iPos, i, j;
      boolean bChanged, bPackageChanged;

      //Go through all the grids and look for display names with the species
      //in them
      for (i = 0; i < mp_oManagedGrids.size(); i++) {
        bChanged = false;
        bPackageChanged = false;
        p_oDataMembers = mp_oManagedGrids.get(i).getDataMembers();
        if (null != p_oDataMembers) {
          for (j = 0; j < p_oDataMembers.length; j++) {
            if (p_oDataMembers[j].getDisplayName().indexOf(sOldSpecies) > -1) {
              sDisplayName = p_oDataMembers[j].getDisplayName();
              iPos = sDisplayName.indexOf(sOldSpecies);
              sDisplayName = sDisplayName.substring(0, iPos) + sNewSpecies +
                  sDisplayName.substring(iPos + sOldSpecies.length());
              p_oDataMembers[j].setDisplayName(sDisplayName);
              bChanged = true;
            }
          }          
        }
        //Repeat with packages
        p_oPackageDataMembers = mp_oManagedGrids.get(i).getPackageDataMembers();
        if (null != p_oPackageDataMembers) {
          for (j = 0; j < p_oPackageDataMembers.length; j++) {
            if (p_oPackageDataMembers[j].getDisplayName().indexOf(sOldSpecies) > -1) {
              sDisplayName = p_oPackageDataMembers[j].getDisplayName();
              iPos = sDisplayName.indexOf(sOldSpecies);
              sDisplayName = sDisplayName.substring(0, iPos) + sNewSpecies +
                  sDisplayName.substring(iPos + sOldSpecies.length());
              p_oPackageDataMembers[j].setDisplayName(sDisplayName);
              bPackageChanged = true;
            }
          }          
        }
        if (bChanged || bPackageChanged) {
          mp_oManagedGrids.get(i).setDataMembers(p_oDataMembers, p_oPackageDataMembers);
        }
      }
      sOldSpecies = sOldSpecies.replace(' ', '_');
      sNewSpecies = sNewSpecies.replace(' ', '_');
    }

    //Trigger the change of species name
    int i;
    for (i = 0; i < mp_oManagedObjects.length; i++) {
      mp_oManagedObjects[i].changeOfSpeciesName(sOldSpecies, sNewSpecies);
    }
  }

  /**
   * Performs any necessary tasks associated with copying one species to
   * another. This causes the change to propagate throughout the WorkerBase
   * objects.
   * @param iSpeciesCopyFrom int Species to copy.
   * @param iSpeciesCopyTo int Species that is the copy.
   * @throws ModelException if there is a problem.
   */
  public void copySpecies(int iSpeciesCopyFrom, int iSpeciesCopyTo) throws
      ModelException {

    //Trigger the species copy
    int i;
    for (i = 0; i < mp_oManagedObjects.length; i++) {
      mp_oManagedObjects[i].copySpecies(iSpeciesCopyFrom, iSpeciesCopyTo);
    }
  }

  /**
   * Changes the list of species names. This will call the same function for all
   * WorkerBase-descended objects.
   * @param iOldNumSpecies says how many species there used to be.
   * @param p_iIndexer is an array, sized to the new number of species.  For
   * each bucket (representing the index number of a species on the new list),
   * the value is either the index of that same species in the old species
   * list, or -1 if the species is new.
   * @param p_sNewSpecies the new species list, as the tree population would
   * use it, with underscores instead of spaces.
   * @throws ModelException if there is a problem.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer, String[] p_sNewSpecies) throws
      ModelException {

    int i;
    //Trigger the change of species
    for (i = 0; i < mp_oManagedObjects.length; i++) {
      mp_oManagedObjects[i].changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    }
  }

  /**
   * This takes a change of plot resolution and deals with any grid maps found.
   * If the plot shrinks, the maps are trimmed to fit.
   * @param fOldX float Old plot X length.
   * @param fOldY float Old plot Y length.
   * @param fNewX float New plot X length.
   * @param fNewY float New plot Y length.
   * @throws ModelException if there is a problem.
   */
  public void changeOfPlotResolution(float fOldX, float fOldY, float fNewX,
                                     float fNewY) throws ModelException {
    Grid oGrid;
    GridValue oValue;
    float fXCellLength, fYCellLength;
    int i, j, iNewMaxX, iNewMaxY;
    boolean bMaps = false;
    for (i = 0; i < mp_oManagedGrids.size(); i++) {
      oGrid = mp_oManagedGrids.get(i);
      if (oGrid.mp_oGridVals != null && oGrid.mp_oGridVals.size() > 0) {
        bMaps = true;
        fXCellLength = oGrid.getXCellLength();
        fYCellLength = oGrid.getYCellLength();
        iNewMaxX = (int)Math.ceil(fNewX / fXCellLength);
        iNewMaxY = (int)Math.ceil(fNewY / fYCellLength);
        for (j = 0; j < oGrid.mp_oGridVals.size(); j++) {
          oValue = oGrid.mp_oGridVals.get(j);
          if (oValue.getCell().getX() >= iNewMaxX ||
              oValue.getCell().getY() >= iNewMaxY ) {
            oGrid.mp_oGridVals.remove(j);
            j--;
          }
        }
      }
    }

    //Trigger the change of species
    for (i = 0; i < mp_oManagedObjects.length; i++) {
      mp_oManagedObjects[i].changeOfPlotResolution(fOldX, fOldY, fNewX, fNewY);
    }

    //Did the plot grow when there were maps? If so, kick out a warning to
    //the user
    if (bMaps && (fNewX > fOldX || fNewY > fOldY)) {
      JOptionPane.showMessageDialog(null, "Warning: Existing grid maps no " +
                                    "longer cover the entire plot.");
    }
  }
  
  /**
   * Writes out a tab-delimited text file. 
   */
  public void writeTextVersionOfParameterFile(String sFileName) throws ModelException {
    FileWriter jOut = null;
    try {
      jOut = new FileWriter(sFileName);
      
      TreePopulation oPop = getTreePopulation();
      
      for (BehaviorTypeBase oBase : mp_oManagedObjects) {
        ArrayList<Behavior> oBehs = oBase.getAllInstantiatedBehaviors();
        for (Behavior oBeh : oBehs) {
          oBeh.writeParametersToTextFile(jOut, oPop);
        }
      }
    } catch (IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
          "There was a problem writing the file " + sFileName + ". Message: " +
          e.getMessage()));      
    } finally {
      try {if (jOut != null) jOut.close();} catch (IOException e) {;}
    }
  }
}
