package sortie.gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import java.io.File;
import javax.help.*;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelMessage;
import sortie.datavisualizer.DataGrapher;
import sortie.datavisualizer.DataVisualizerManager;
import sortie.datavisualizer.ModelInternalFrame;
import sortie.fileops.Tarball;
import sortie.gui.components.FileOpenFilter;
import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.SORTIEComboBox;
import sortie.gui.components.SortieFont;
import sortie.gui.components.SortieMenuItem;
import sortie.gui.components.TextFileFilter;
import sortie.gui.components.XMLFileFilter;
import sortie.parfile.Interface;
import sortie.tools.batchoutput.BatchDetailedOutput;

import java.net.URL;
import java.util.prefs.Preferences;

/**
 * Main application window.
 *
 * The main application window has two main functions; creating and editing
 * parameter files for running the model, and viewing the output generated.
 * To run these functions, it has a separate manager object for each.  The
 * rest of the components in this window are for GUI display.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.1
 * @todo Window icon
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 * <br>November 19, 2012: Changed the window closing event method because the
 * old method stopped working (LEM)
 */

public class MainWindow
    extends JFrame
    implements ActionListener, InternalFrameListener, WindowListener {
  
  //*********************************
  // Menu bar
  //*********************************
  /** File - new menu item */
  protected JMenuItem m_jMenuFileNew = new JMenuItem("New parameter file",
      KeyEvent.VK_N);
  /** File - new batch file menu item */
  protected JMenuItem m_jMenuFileBatchNew = new JMenuItem("New batch file",
      KeyEvent.VK_B);
  /** File - open file menu item */
  protected JMenuItem m_jMenuFileOpen = new JMenuItem("Open file",
      KeyEvent.VK_O);
  /** File - file save menu item */
  protected JMenuItem m_jMenuFileSave = new JMenuItem("Save parameter file",
      KeyEvent.VK_S);
  /** File - text file save menu item */
  protected JMenuItem m_jMenuTextFileSave = new JMenuItem("Save parameter file as text",
      KeyEvent.VK_T);
  /** File - open output file menu item */
  protected JMenuItem m_jMenuFileOpenData = new JMenuItem("Open output file",
      KeyEvent.VK_R);
  /** File - close output file menu item */
  protected JMenuItem m_jMenuFileCloseData = new JMenuItem(
      "Close output file", KeyEvent.VK_C);
  /** File - set working directory menu item */
  protected JMenuItem m_jMenuFileSetDir = new JMenuItem(
      "Set working directory", KeyEvent.VK_D);
  /** File - recent file 1 menu item */
  protected JMenuItem m_jMenuFileRecentFile1 = new JMenuItem("", KeyEvent.VK_1);
  /** File - recent file 2 menu item */
  protected JMenuItem m_jMenuFileRecentFile2 = new JMenuItem("", KeyEvent.VK_2);
  /** File - recent file 3 menu item */
  protected JMenuItem m_jMenuFileRecentFile3 = new JMenuItem("", KeyEvent.VK_3);
  /** File - recent file 4 menu item */
  protected JMenuItem m_jMenuFileRecentFile4 = new JMenuItem("", KeyEvent.VK_4);
  /** File - recent output file 1 menu item */
  protected JMenuItem m_jMenuFileRecentOutputFile1 = new JMenuItem("", KeyEvent.VK_5);
  /** File - recent output file 2 menu item */
  protected JMenuItem m_jMenuFileRecentOutputFile2 = new JMenuItem("", KeyEvent.VK_6);
  /** File - recent output file 3 menu item */
  protected JMenuItem m_jMenuFileRecentOutputFile3 = new JMenuItem("", KeyEvent.VK_7);
  /** File - recent output file 4 menu item */
  protected JMenuItem m_jMenuFileRecentOutputFile4 = new JMenuItem("", KeyEvent.VK_8);
  /** File - exit menu item */
  protected JMenuItem m_jMenuFileExit = new JMenuItem("Exit", KeyEvent.VK_X);

  /** Edit - tree setup submenu */
  protected JMenu m_jMenuEditTree = new JMenu("Tree Population");
  /** Edit - grid setup menu item */
  protected JMenuItem m_jMenuEditGrid = new JMenuItem("Grid layer setup",
      KeyEvent.VK_G);
  /** Edit - model flow menu item */
  protected JMenuItem m_jMenuEditFlow = new JMenuItem("Model flow",
      KeyEvent.VK_M);
  /** Edit - parameters menu item */
  protected JMenuItem m_jMenuEditParameters = new JMenuItem("Model Settings",
      KeyEvent.VK_B);
  /** Edit - output options menu item */
  protected JMenuItem m_jMenuEditOutput = new JMenuItem("Output options",
      KeyEvent.VK_O);
  
  /** Tree submenu item - edit species list*/
  protected JMenuItem m_jMenuTreeSpecies = 
                      new JMenuItem("Edit species list", KeyEvent.VK_S);
  /** Tree submenu item - edit initial density size classes*/
  protected JMenuItem m_jMenuTreeSizeClasses = 
          new JMenuItem("Edit initial density size classes", KeyEvent.VK_I);
  /** Tree submenu item - manage tree maps*/
  protected JMenuItem m_jMenuTreeTreeMaps = new JMenuItem("Manage tree maps", 
      KeyEvent.VK_M);

  /** Model - Run menu item */
  protected JMenuItem m_jMenuModelRun = new JMenuItem("Run", KeyEvent.VK_R);
  /** Model - Run batch menu item */
  protected JMenuItem m_jMenuModelRunBatch = new JMenuItem("Run Batch...");
  /** Model - Pause menu item */
  protected JMenuItem m_jMenuModelPause = new JMenuItem("Pause", KeyEvent.VK_P);
  /** Model - Stop menu item */
  protected JMenuItem m_jMenuModelStop = new JMenuItem("Stop run",
      KeyEvent.VK_S);     

  /** Help - contents menu item */
  protected JMenuItem m_jMenuHelpContents = new JMenuItem("Contents",
      KeyEvent.VK_C);
  /** Help - about menu item */
  //protected JMenuItem m_jMenuHelpAbout = new JMenuItem("About", KeyEvent.VK_A);
  
  /** Tools - rename detailed output file menu item */
  protected JMenuItem m_jMenuToolsRename = new JMenuItem(
      "Copy Detailed Output File", KeyEvent.VK_C);
  /** Tools - batch detailed output data extraction */
  protected JMenuItem m_jMenuToolsBatchOut = new JMenuItem(
      "Batch Extract Detailed Output Files", KeyEvent.VK_B);

  

  /**Object managing the interface between this application and the C++
   * model core*/
  private Interface m_oInterface = null;

  /**Object that allows us to update the GUI while the core model is
   * running*/
  private Timer m_oTimer;

  /**Manager controlling all functions related to creating and editing
   * parameter files and running the model*/
  protected GUIManager m_oDataManager;

  /**Manager controlling all functions related to output data visualization*/
  protected DataVisualizerManager m_oDataVisualizerManager;

  /**The HelpSet object for the help file - class is from JavaHelp*/
  protected HelpSet m_oHelpSet;

  /**JavaHelp class for managing help displays*/
  public HelpBroker m_oHelpBroker;
  
  /**Menu with the line graph choices for the current output file*/
  private JPopupMenu m_jLineGraphChoices;
  /**Button which opens the line graph choices menu*/
  private JButton m_jLineGraphMenuButton;
  /**Menu with the histogram choices for the current output file*/
  private JPopupMenu m_jHistogramChoices;
  /**Button which opens the histogram choices menu*/
  private JButton m_jHistogramMenuButton;
  /**Menu with the table choices for the current output file*/
  private JPopupMenu m_jTableChoices;
  /**Button which opens the table choices menu*/
  private JButton m_jTableMenuButton;
  /**Menu with the map choices for the current output file*/
  private JPopupMenu m_jMapChoices;
  /**Button which opens the map choices menu*/
  private JButton m_jMapMenuButton;
  
  /**Menu with the recent chart choices*/
  private JPopupMenu m_jRecentChoices;
  /**Button which opens the recent choices menu*/
  private JButton m_jRecentMenuButton;
  
  /**File loaded, no file loaded, running, etc.*/
  private int m_iState; 

  /**Desktop pane - allows the use of JInternalFrames*/
  private JDesktopPane m_oDesktop;
  /** Scroll pane for the desktop */
  private JScrollPane m_jScroller;  

  /**Holds the list of currently open output files*/
  protected SORTIEComboBox<String> m_jFileChoicesComboBox = new SORTIEComboBox<String>();
  
  /**Holds the list of currently open display windows*/
  protected SORTIEComboBox<ChartFrameInfo> m_jWindowsComboBox = new SORTIEComboBox<ChartFrameInfo>();

  /**Field in the status bar holding the parameter file*/
  protected JTextField m_jParameterFileField = new JTextField();

  /**Field holding the model status*/
  protected JTextField m_jModelStatusField = new JTextField();

  /**Field holding model messages*/
  protected JTextField m_jMessagesField = new JTextField();

  /**Button for running model*/
  protected JButton m_jModelRunButton = new JButton(new ModelIcon(15, 15,
      ModelIcon.RIGHT_TRIANGLE));

  /**Button for stopping model*/
  protected JButton m_jModelStopButton = new JButton(new ModelIcon(15, 15,
      ModelIcon.RECTANGLE));

  /**Button for pausing model*/
  protected JButton m_jModelPauseButton = new JButton(new ModelIcon(15, 15,
      ModelIcon.PAUSE));

  /**Button for stepping model*/
  protected JButton m_jModelStepForwardButton = new JButton(new ModelIcon(15,
      15,
      ModelIcon.STEP_FORWARD));

  /**Button for loading run's output*/
  protected JButton m_jModelLoadOutputButton = new JButton(
      "View this run's output");

  /**Flag for whether or not the user is doing real-time data visualization
   * of the current run*/
  private boolean m_bViewingRunOutput = false;

  /**Flag for whether, during real-time data visualization, a new run
   * timestep should be triggered after data updates.  If the user has run the
   * model instead of pausing it or stepping forward one timestep, this
   * is set to true.  Otherwise, it is false and no new timestep will be
   * triggered after the charts have been refreshed.*/
  private boolean m_bKeepRunning = false;
  
  /**The height of the data visualizer panel when the window is constructed. 
   * This will allow us to resize it if the user changes the size of the window.
   * This is to avoid losing items off the screen.
   */
  private int m_iPanelHeight = -1;

  /**
   * Constructor.  Draws the window.
   */
  public MainWindow() {
    
    addWindowListener(this);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Preferences jPrefs = Preferences.userNodeForPackage(getClass());
    /*try {
    jPrefs.clear();
    } catch (java.util.prefs.BackingStoreException e) {
      JOptionPane.showMessageDialog(this, "Can't clear prefs.");
    }*/
    
    m_jRecentChoices = new JPopupMenu();      
    int i;
    for (i = 1; i < 11; i++) {
      String sFile1 = jPrefs.get("RecentCharts"+i, "");
      if (sFile1.length() > 0) {
        SortieMenuItem jMenuItem = new SortieMenuItem(
            sFile1, "RecentCharts" + sFile1, this);
        m_jRecentChoices.add(jMenuItem);
      }
    }    
    
    createGUI();
    addComponentListener(new ComponentAdapter() {    
      public void componentResized(ComponentEvent e) {resizeGUI();}});
    try {
      m_oDataManager = new GUIManager(this);
      setModelState(MainWindowStateSetter.NO_PAR_FILE);
      m_oDataVisualizerManager = new DataVisualizerManager(this, m_oDesktop,
          m_jScroller);
      
      //Fire up the help system
      loadHelp();
      
      //Fire up the preferences
      //Working directory                       
      String sSavedWorkingDir = jPrefs.get("WorkingDirectory", "");
      if (sSavedWorkingDir != "") 
        ModelFileChooser.m_oWorkingDirectory = new File(sSavedWorkingDir);         
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
  }

  /**
   * Loads the SORTIE help file so that it can be opened from buttons and
   * menu commands.
   * @throws ModelException if the help is not found.
   */
  private void loadHelp() throws ModelException {
    // Find the HelpSet file and create the HelpSet object.  The SORTIE help
    //jar file should be included into the project as a required library so that
    //it will be added to the classpath
    String sHelpSetFileName = "help/sortiehelp.hs"; //path within the JAR file
    try {
      URL hsURL = HelpSet.findHelpSet(null, sHelpSetFileName);
      m_oHelpSet = new HelpSet(null, hsURL);
    }
    catch (Exception ee) {
      // Say what the exception really is
      throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
                               "I can't find the SORTIE help file."));
    }
    // Create a HelpBroker object:
    m_oHelpBroker = m_oHelpSet.createHelpBroker();
    //Hook up the help menu item
    m_jMenuHelpContents.addActionListener(new CSH.DisplayHelpFromSource(
        m_oHelpBroker));
    CSH.setHelpIDString(m_jMenuHelpContents, "license.license");

    //Set the window-level context-sensitive help
    m_oHelpBroker.enableHelpKey(this.getRootPane(), "intro", null);
  }

  /**
   * SendMessage accepts a message from the interface.
   * @param oMsg Message to accept.
   */
  public void sendMessage(ModelMessage oMsg) {
    m_jMessagesField.setText(oMsg.sMessage);
  }
  
  /**
   * Causes the GUI to resize in a smart way.
   */
  private void resizeGUI() {
    JLabel jOutputFilesLabel = new JLabel("Open output files:");
    jOutputFilesLabel.setFont(new SortieFont());    
    int iWindowWidth = getWidth(),
    iComponentsWidth = (int)(jOutputFilesLabel.getPreferredSize().getWidth() +
                        m_jLineGraphMenuButton.getPreferredSize().getWidth() +
                        m_jHistogramMenuButton.getPreferredSize().getWidth() +
                        m_jTableMenuButton.getPreferredSize().getWidth() + 
                        m_jRecentMenuButton.getPreferredSize().getWidth()),
    iAvailableWidth = iWindowWidth - (iComponentsWidth + 50),
    iBoxWidth = (int)java.lang.Math.max((iAvailableWidth * 0.66), 200);
    //File choices gets 2/3 of what's left, chart choices the rest
    m_jFileChoicesComboBox.setPreferredSize(new Dimension( 
         iBoxWidth, m_jFileChoicesComboBox.getPreferredSize().height));
    if (iWindowWidth < (iComponentsWidth + iBoxWidth + 75)) {
      JPanel jPanel = (JPanel)DataGrapher.findNamedComponent(this, "data_visualizer_panel");
      jPanel.setPreferredSize(new Dimension((int)jPanel.getPreferredSize().width, 
          m_iPanelHeight*2));
      //This line is required
      jPanel.setSize(jPanel.getPreferredSize());
    } else {
      JPanel jPanel = (JPanel)DataGrapher.findNamedComponent(this, "data_visualizer_panel");
      jPanel.setPreferredSize(new Dimension((int)jPanel.getPreferredSize().width, 
          m_iPanelHeight));
      //This line is required
      jPanel.setSize(jPanel.getPreferredSize());
    }
    //Call validate only! No repaint, no pack.
    validate();
    
  }
  
  /**
   * Creates the menu bar.
   */
  private void createMenu() {    
    
    JMenuBar jMenuBar = new JMenuBar();
    
    //Declare "Edit" menu
    JMenu jMenuEdit = new JMenu("Edit");
    //"Model" menu
    JMenu jMenuModel = new JMenu("Model");
    //"Tools" menu
    JMenu jMenuTools = new JMenu("Tools");
    //"Help" menu
    JMenu jMenuHelp = new JMenu("Help");    

    //Add file options
    //File New
    m_jMenuFileNew.setBackground(SystemColor.control);
    m_jMenuFileNew.setFont(new SortieFont());
    m_jMenuFileNew.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    m_jMenuFileNew.setActionCommand("FileNew");
    m_jMenuFileNew.addActionListener(this);

    //File New Batch
    m_jMenuFileBatchNew.setBackground(SystemColor.control);
    m_jMenuFileBatchNew.setFont(new SortieFont());
    m_jMenuFileBatchNew.setActionCommand("FileNewBatch");
    m_jMenuFileBatchNew.addActionListener(this);

    //File Open
    m_jMenuFileOpen.setBackground(SystemColor.control);
    m_jMenuFileOpen.setFont(new SortieFont());
    m_jMenuFileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
        ActionEvent.CTRL_MASK));
    m_jMenuFileOpen.setActionCommand("FileOpen");
    m_jMenuFileOpen.addActionListener(this);

    //File Open Output
    m_jMenuFileOpenData.setBackground(SystemColor.control);
    m_jMenuFileOpenData.setFont(new SortieFont());
    m_jMenuFileOpenData.setMnemonic(KeyEvent.VK_P);
    m_jMenuFileOpenData.setActionCommand("FileOpenData");
    m_jMenuFileOpenData.addActionListener(this);

    //File Close Output
    m_jMenuFileCloseData.setBackground(SystemColor.control);
    m_jMenuFileCloseData.setFont(new SortieFont());
    m_jMenuFileCloseData.setActionCommand("FileCloseData");
    m_jMenuFileCloseData.addActionListener(this);

    //File Set working directory
    m_jMenuFileSetDir.setBackground(SystemColor.control);
    m_jMenuFileSetDir.setFont(new SortieFont());
    m_jMenuFileSetDir.setActionCommand("FileSetDir");
    m_jMenuFileSetDir.addActionListener(this);

    //File Save
    m_jMenuFileSave.setBackground(SystemColor.control);
    m_jMenuFileSave.setFont(new SortieFont());
    m_jMenuFileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
        ActionEvent.CTRL_MASK));
    m_jMenuFileSave.setActionCommand("FileSave");
    m_jMenuFileSave.addActionListener(this);
    
    //File Save as Text
    m_jMenuTextFileSave.setBackground(SystemColor.control);
    m_jMenuTextFileSave.setFont(new SortieFont());
    m_jMenuTextFileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
        ActionEvent.CTRL_MASK));
    m_jMenuTextFileSave.setActionCommand("FileSaveText");
    m_jMenuTextFileSave.addActionListener(this);

    //File recent files
    m_jMenuFileRecentFile1.setBackground(SystemColor.control);
    m_jMenuFileRecentFile1.setFont(new SortieFont());
    m_jMenuFileRecentFile1.setActionCommand("RecentFile1");
    m_jMenuFileRecentFile1.addActionListener(this);

    m_jMenuFileRecentFile2.setBackground(SystemColor.control);
    m_jMenuFileRecentFile2.setFont(new SortieFont());
    m_jMenuFileRecentFile2.setActionCommand("RecentFile2");
    m_jMenuFileRecentFile2.addActionListener(this);

    m_jMenuFileRecentFile3.setBackground(SystemColor.control);
    m_jMenuFileRecentFile3.setFont(new SortieFont());
    m_jMenuFileRecentFile3.setActionCommand("RecentFile3");
    m_jMenuFileRecentFile3.addActionListener(this);

    m_jMenuFileRecentFile4.setBackground(SystemColor.control);
    m_jMenuFileRecentFile4.setFont(new SortieFont());
    m_jMenuFileRecentFile4.setActionCommand("RecentFile4");
    m_jMenuFileRecentFile4.addActionListener(this);

    m_jMenuFileRecentOutputFile1.setBackground(SystemColor.control);
    m_jMenuFileRecentOutputFile1.setFont(new SortieFont());
    m_jMenuFileRecentOutputFile1.setActionCommand("RecentOutputFile1");
    m_jMenuFileRecentOutputFile1.addActionListener(this);

    m_jMenuFileRecentOutputFile2.setBackground(SystemColor.control);
    m_jMenuFileRecentOutputFile2.setFont(new SortieFont());
    m_jMenuFileRecentOutputFile2.setActionCommand("RecentOutputFile2");
    m_jMenuFileRecentOutputFile2.addActionListener(this);

    m_jMenuFileRecentOutputFile3.setBackground(SystemColor.control);
    m_jMenuFileRecentOutputFile3.setFont(new SortieFont());
    m_jMenuFileRecentOutputFile3.setActionCommand("RecentOutputFile3");
    m_jMenuFileRecentOutputFile3.addActionListener(this);

    m_jMenuFileRecentOutputFile4.setBackground(SystemColor.control);
    m_jMenuFileRecentOutputFile4.setFont(new SortieFont());
    m_jMenuFileRecentOutputFile4.setActionCommand("RecentOutputFile4");
    m_jMenuFileRecentOutputFile4.addActionListener(this);

    //File Exit
    m_jMenuFileExit.setBackground(SystemColor.control);
    m_jMenuFileExit.setFont(new SortieFont());
    m_jMenuFileExit.setActionCommand("FileExit");
    m_jMenuFileExit.addActionListener(this);

    //Edit options text
    jMenuEdit.setBackground(SystemColor.control);
    jMenuEdit.setFont(new SortieFont());
    jMenuEdit.setFocusPainted(false);
    jMenuEdit.setMnemonic(KeyEvent.VK_E);

    //Edit Tree
    m_jMenuEditTree.setBackground(SystemColor.control);
    m_jMenuEditTree.setFont(new SortieFont());
    m_jMenuEditTree.setActionCommand("EditTree");
    m_jMenuEditTree.addActionListener(this);
    m_jMenuEditTree.setMnemonic(KeyEvent.VK_T);

    //Edit Grid
    m_jMenuEditGrid.setBackground(SystemColor.control);
    m_jMenuEditGrid.setFont(new SortieFont());
    m_jMenuEditGrid.setActionCommand("EditGrid");
    m_jMenuEditGrid.addActionListener(this);

    //Edit Model Flow
    m_jMenuEditFlow.setBackground(SystemColor.control);
    m_jMenuEditFlow.setFont(new SortieFont());
    m_jMenuEditFlow.setActionCommand("EditFlow");
    m_jMenuEditFlow.addActionListener(this);

    //Edit Model Parameters
    m_jMenuEditParameters.setBackground(SystemColor.control);
    m_jMenuEditParameters.setFont(new SortieFont());
    m_jMenuEditParameters.setActionCommand("EditParameters");
    m_jMenuEditParameters.addActionListener(this);
    
    //Edit Output options
    m_jMenuEditOutput.setBackground(SystemColor.control);
    m_jMenuEditOutput.setFont(new SortieFont());
    m_jMenuEditOutput.setActionCommand("EditOutput");
    m_jMenuEditOutput.addActionListener(this);
    
    //Tree submenu - edit species list*/
    m_jMenuTreeSpecies.setBackground(SystemColor.control);
    m_jMenuTreeSpecies.setFont(new SortieFont());
    m_jMenuTreeSpecies.setActionCommand("EditTreeSpecies");
    m_jMenuTreeSpecies.addActionListener(this);

    //Tree submenu - edit initial density size classes*/
    m_jMenuTreeSizeClasses.setBackground(SystemColor.control);
    m_jMenuTreeSizeClasses.setFont(new SortieFont());
    m_jMenuTreeSizeClasses.setActionCommand("EditTreeDensityClasses");
    m_jMenuTreeSizeClasses.addActionListener(this);

    //Tree submenu - manage tree maps*/
    m_jMenuTreeTreeMaps.setBackground(SystemColor.control);
    m_jMenuTreeTreeMaps.setFont(new SortieFont());
    m_jMenuTreeTreeMaps.setActionCommand("EditTreeMaps");
    m_jMenuTreeTreeMaps.addActionListener(this);

    //Model options text
    jMenuModel.setBackground(SystemColor.control);
    jMenuModel.setFont(new SortieFont());
    jMenuModel.setMnemonic(KeyEvent.VK_M);

    //Model Run
    m_jMenuModelRun.setBackground(SystemColor.control);
    m_jMenuModelRun.setFont(new SortieFont());
    m_jMenuModelRun.setActionCommand("ModelRun");
    m_jMenuModelRun.addActionListener(this);

    //Model Run Batch
    m_jMenuModelRunBatch.setBackground(SystemColor.control);
    m_jMenuModelRunBatch.setFont(new SortieFont());
    m_jMenuModelRunBatch.setActionCommand("ModelRunBatch");
    m_jMenuModelRunBatch.addActionListener(this);

    //Model Pause
    m_jMenuModelPause.setBackground(SystemColor.control);
    m_jMenuModelPause.setFont(new SortieFont());
    m_jMenuModelPause.setActionCommand("ModelPause");
    m_jMenuModelPause.addActionListener(this);

    //Model Stop Run
    m_jMenuModelStop.setBackground(SystemColor.control);
    m_jMenuModelStop.setFont(new SortieFont());
    m_jMenuModelStop.setActionCommand("ModelStop");
    m_jMenuModelStop.addActionListener(this);

    //Tools options
    jMenuTools.setBackground(SystemColor.control);
    jMenuTools.setFont(new SortieFont());
    jMenuTools.setMnemonic(KeyEvent.VK_T);

    //Tools Rename Detailed Output File
    m_jMenuToolsRename.setBackground(SystemColor.control);
    m_jMenuToolsRename.setFont(new SortieFont());
    m_jMenuToolsRename.setActionCommand("ToolsRename");
    m_jMenuToolsRename.addActionListener(this);

    //Tools Batch Extract Detailed Output File
    m_jMenuToolsBatchOut.setBackground(SystemColor.control);
    m_jMenuToolsBatchOut.setFont(new SortieFont());
    m_jMenuToolsBatchOut.setActionCommand("ToolsBatchOut");
    m_jMenuToolsBatchOut.addActionListener(this);

    //Help options
    jMenuHelp.setBackground(SystemColor.control);
    jMenuHelp.setFont(new SortieFont());
    jMenuHelp.setMnemonic(KeyEvent.VK_H);

    m_jMenuHelpContents.setBackground(SystemColor.control);
    m_jMenuHelpContents.setFont(new SortieFont());

    //Build the menu bar
    jMenuBar.setBackground(SystemColor.control);
    jMenuBar.setFont(new SortieFont());

    //Build the tree submenu
    m_jMenuEditTree.add(m_jMenuTreeSpecies);
    m_jMenuEditTree.add(m_jMenuTreeSizeClasses);
    m_jMenuEditTree.add(m_jMenuTreeTreeMaps);

    //Build the edit menu
    jMenuEdit.add(m_jMenuEditParameters);
    jMenuEdit.addSeparator();
    jMenuEdit.add(m_jMenuEditTree);
    jMenuEdit.add(m_jMenuEditGrid);
    jMenuEdit.add(m_jMenuEditFlow);
    jMenuEdit.addSeparator();
    jMenuEdit.add(m_jMenuEditOutput);

    //Build the model menu
    jMenuModel.add(m_jMenuModelRun);
    jMenuModel.add(m_jMenuModelRunBatch);
    jMenuModel.add(m_jMenuModelPause);
    jMenuModel.add(m_jMenuModelStop);

    //Build the tools menu
    jMenuTools.add(m_jMenuToolsRename);
    jMenuTools.add(m_jMenuToolsBatchOut);

    //Build the help menu
    jMenuHelp.add(m_jMenuHelpContents);

    jMenuBar.add(makeFileMenu());
    jMenuBar.add(jMenuEdit);
    jMenuBar.add(jMenuModel);
    jMenuBar.add(jMenuTools);
    jMenuBar.add(jMenuHelp);

    this.setJMenuBar(jMenuBar);
  }
  
  private JMenu makeFileMenu() {
    //Declare "File menu"
    JMenu jMenuFile = new JMenu("File");
    //File options text
    jMenuFile.setBackground(SystemColor.control);
    jMenuFile.setFont(new SortieFont());
    jMenuFile.setMnemonic(KeyEvent.VK_F);
    
  //Get the recent files from preferences
    Preferences jPrefs = Preferences.userNodeForPackage(getClass());
    String sFile1 = jPrefs.get("RecentFiles1", ""),
           sFile2 = jPrefs.get("RecentFiles2", ""),
           sFile3 = jPrefs.get("RecentFiles3", ""),
           sFile4 = jPrefs.get("RecentFiles4", ""),
           sOutputFile1 = jPrefs.get("RecentOutputFiles1", ""),
           sOutputFile2 = jPrefs.get("RecentOutputFiles2", ""),
           sOutputFile3 = jPrefs.get("RecentOutputFiles3", ""),
           sOutputFile4 = jPrefs.get("RecentOutputFiles4", "");
    
    if (sFile1.length() > 0) {
      if (sFile1.indexOf('\\') > -1) 
        sFile1 = sFile1.substring(sFile1.lastIndexOf('\\')+1);
      if (sFile1.indexOf('/') > -1) 
        sFile1 = sFile1.substring(sFile1.lastIndexOf('/')+1);
      m_jMenuFileRecentFile1.setText("1: " + sFile1);
    }
    if (sFile2.length() > 0) {
      if (sFile2.indexOf('\\') > -1) 
        sFile2 = sFile2.substring(sFile2.lastIndexOf('\\')+1);
      if (sFile2.indexOf('/') > -1) 
        sFile2 = sFile2.substring(sFile2.lastIndexOf('/')+1);
      m_jMenuFileRecentFile2.setText("2: " + sFile2);
    }
    if (sFile3.length() > 0) {
      if (sFile3.indexOf('\\') > -1) 
        sFile3 = sFile3.substring(sFile3.lastIndexOf('\\')+1);
      if (sFile3.indexOf('/') > -1) 
        sFile3 = sFile3.substring(sFile3.lastIndexOf('/')+1);
      m_jMenuFileRecentFile3.setText("3: " + sFile3);
    }
    if (sFile4.length() > 0) {
      if (sFile4.indexOf('\\') > -1) 
        sFile4 = sFile4.substring(sFile4.lastIndexOf('\\')+1);
      if (sFile4.indexOf('/') > -1) 
        sFile4 = sFile4.substring(sFile4.lastIndexOf('/')+1);
      m_jMenuFileRecentFile4.setText("4: " + sFile4);
    }
    
    if (sOutputFile1.length() > 0) {
      m_jMenuFileRecentOutputFile1.setText("5: " + sOutputFile1);
    }
    if (sOutputFile2.length() > 0) {
      m_jMenuFileRecentOutputFile2.setText("6: " + sOutputFile2);
    }  
    if (sOutputFile3.length() > 0) {
      m_jMenuFileRecentOutputFile3.setText("7: " + sOutputFile3);
    }
    if (sOutputFile4.length() > 0) {
      m_jMenuFileRecentOutputFile4.setText("8: " + sOutputFile4);
    }
    
    jMenuFile.add(m_jMenuFileNew);
    jMenuFile.add(m_jMenuFileBatchNew);
    jMenuFile.addSeparator();
    jMenuFile.add(m_jMenuFileOpen);
    jMenuFile.add(m_jMenuFileSave);
    jMenuFile.add(m_jMenuTextFileSave);
    jMenuFile.addSeparator();
    jMenuFile.add(m_jMenuFileSetDir);
    jMenuFile.addSeparator();
    jMenuFile.add(m_jMenuFileOpenData);
    jMenuFile.add(m_jMenuFileCloseData);
    jMenuFile.addSeparator();
    
    //Recent files - maybe
    if (m_jMenuFileRecentFile1.getText().length() > 0 ||
        m_jMenuFileRecentFile2.getText().length() > 0 ||
        m_jMenuFileRecentFile3.getText().length() > 0 ||
        m_jMenuFileRecentFile4.getText().length() > 0) {
      if (m_jMenuFileRecentFile1.getText().length() > 0)
        jMenuFile.add(m_jMenuFileRecentFile1);
      if (m_jMenuFileRecentFile2.getText().length() > 0)
        jMenuFile.add(m_jMenuFileRecentFile2);
      if (m_jMenuFileRecentFile3.getText().length() > 0)
        jMenuFile.add(m_jMenuFileRecentFile3);
      if (m_jMenuFileRecentFile4.getText().length() > 0)
        jMenuFile.add(m_jMenuFileRecentFile4);
      jMenuFile.addSeparator();
    }
    
    if (m_jMenuFileRecentOutputFile1.getText().length() > 0 ||
        m_jMenuFileRecentOutputFile2.getText().length() > 0 ||
        m_jMenuFileRecentOutputFile3.getText().length() > 0 ||
        m_jMenuFileRecentOutputFile4.getText().length() > 0) {
      if (m_jMenuFileRecentOutputFile1.getText().length() > 0)
        jMenuFile.add(m_jMenuFileRecentOutputFile1);
      if (m_jMenuFileRecentOutputFile2.getText().length() > 0)
        jMenuFile.add(m_jMenuFileRecentOutputFile2);
      if (m_jMenuFileRecentOutputFile3.getText().length() > 0)
        jMenuFile.add(m_jMenuFileRecentOutputFile3);
      if (m_jMenuFileRecentOutputFile4.getText().length() > 0)
        jMenuFile.add(m_jMenuFileRecentOutputFile4);
      jMenuFile.addSeparator();
    }
    jMenuFile.add(m_jMenuFileExit);

    return jMenuFile;
  }

  /**
   * Component initialization and GUI construction.
   */
  private void createGUI() {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int iInset = 50;
    setBounds(iInset, iInset,
              screenSize.width - iInset * 2,
              screenSize.height - iInset * 2);
    this.setTitle("SORTIE-ND Version 7.05.07");

    createMenu();

    m_oDesktop = new JDesktopPane();
    m_jScroller = new JScrollPane(m_oDesktop);

    //****************************************************************
     //Build the status bar
     //****************************************************************
    m_jParameterFileField.setBackground(SystemColor.control);
    m_jParameterFileField.setFont(new SortieFont());
    m_jParameterFileField.setAlignmentX( (float) 0.5);
    m_jParameterFileField.setDisabledTextColor(Color.black);
    m_jParameterFileField.setEditable(false);
    m_jParameterFileField.setText("No parameter file loaded");
    m_jModelStatusField.setBackground(SystemColor.control);
    m_jModelStatusField.setFont(new SortieFont());
    m_jModelStatusField.setDisabledTextColor(Color.black);
    m_jModelStatusField.setEditable(false);
    m_jModelStatusField.setText("Not ready to run");
    m_jMessagesField.setBackground(SystemColor.control);
    m_jMessagesField.setEnabled(false);
    m_jMessagesField.setFont(new SortieFont());
    m_jMessagesField.setDisabledTextColor(Color.black);
    m_jMessagesField.setSelectionStart(0);
    m_jMessagesField.setText("");

    //****************************************************************
    //Build the main panel
    //****************************************************************
    JPanel jMainPanel = new JPanel(new BorderLayout());
    jMainPanel.setMinimumSize(new Dimension(18, 25));
    jMainPanel.add(m_jParameterFileField, BorderLayout.WEST);
    jMainPanel.add(m_jMessagesField, BorderLayout.CENTER);
    jMainPanel.add(m_jModelStatusField, BorderLayout.EAST);

    //Add the desktop
    m_oDesktop.setBackground(SystemColor.control);
    m_oDesktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

    //****************************************************************
    //Build the data visualizer panel
    //****************************************************************
    JPanel jDataVisualizerPanel = new JPanel(new FlowLayout());
    jDataVisualizerPanel.setName("data_visualizer_panel");
    jDataVisualizerPanel.setBackground(SystemColor.control);
    jDataVisualizerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
        Color.BLACK));
    
    m_jLineGraphChoices = new JPopupMenu();
    m_jHistogramChoices = new JPopupMenu();
    m_jMapChoices = new JPopupMenu();
    m_jTableChoices = new JPopupMenu();    

    m_jLineGraphMenuButton = new JButton("Line graphs");
    m_jLineGraphMenuButton.setFont(new SortieFont());
    m_jLineGraphMenuButton.addMouseListener(new PopupListener(m_jLineGraphMenuButton,
        m_jLineGraphChoices));
    m_jLineGraphMenuButton.setEnabled(false);
    m_jLineGraphMenuButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    
    m_jHistogramMenuButton = new JButton("Histograms");
    m_jHistogramMenuButton.setFont(new SortieFont());
    m_jHistogramMenuButton.addMouseListener(new PopupListener(m_jHistogramMenuButton,
        m_jHistogramChoices));
    m_jHistogramMenuButton.setEnabled(false);
    //m_jHistogramMenuButton.setPreferredSize(m_jLineGraphMenuButton.getPreferredSize());
    m_jHistogramMenuButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    
    m_jTableMenuButton = new JButton("Tables");
    m_jTableMenuButton.setFont(new SortieFont());
    //m_jTableMenuButton.setPreferredSize(m_jLineGraphMenuButton.getPreferredSize());
    m_jTableMenuButton.addMouseListener(new PopupListener(m_jTableMenuButton, m_jTableChoices));
    m_jTableMenuButton.setEnabled(false);
    m_jTableMenuButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    
    m_jMapMenuButton = new JButton("Maps");
    m_jMapMenuButton.setFont(new SortieFont());
    //m_jMapMenuButton.setPreferredSize(m_jLineGraphMenuButton.getPreferredSize());
    m_jMapMenuButton.addMouseListener(new PopupListener(m_jMapMenuButton, m_jMapChoices));
    m_jMapMenuButton.setEnabled(false);
    m_jMapMenuButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
      
    m_jRecentMenuButton = new JButton("Recent");
    m_jRecentMenuButton.setFont(new SortieFont());
    //m_jRecentMenuButton.setPreferredSize(m_jLineGraphMenuButton.getPreferredSize());
    m_jRecentMenuButton.addMouseListener(new PopupListener(m_jRecentMenuButton, m_jRecentChoices));
    m_jRecentMenuButton.setEnabled(false);
    m_jRecentMenuButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    
    //Label for output files
    JLabel jOutputFilesLabel = new JLabel("Open output files:");
    jOutputFilesLabel.setFont(new SortieFont());

    
    m_jFileChoicesComboBox.setFont(new SortieFont());
    m_jFileChoicesComboBox.setEnabled(false);
    m_jFileChoicesComboBox.addActionListener(this);

    //Size things
    int iScreenWidth = getBounds().width;
    iScreenWidth -= jOutputFilesLabel.getPreferredSize().getWidth();
    iScreenWidth -= (m_jLineGraphMenuButton.getPreferredSize().getWidth() +
                     m_jHistogramMenuButton.getPreferredSize().getWidth() +
                     m_jTableMenuButton.getPreferredSize().getWidth() +
                     m_jMapMenuButton.getPreferredSize().getWidth() +
                     m_jRecentMenuButton.getPreferredSize().getWidth());
    iScreenWidth -= 50;
    
    //File choices gets 2/3 of what's left, chart choices the rest
    m_jFileChoicesComboBox.setPreferredSize(new Dimension( 
        (int) (iScreenWidth * 0.66),
        m_jFileChoicesComboBox.getPreferredSize().height));   

    //Set the renderers AFTER we've set the preferred size
    m_jFileChoicesComboBox.setRenderer(new ComboBoxRenderer(
        m_jFileChoicesComboBox));    

    jDataVisualizerPanel.add(jOutputFilesLabel);
    jDataVisualizerPanel.add(m_jFileChoicesComboBox, null);
    jDataVisualizerPanel.add(m_jLineGraphMenuButton);
    jDataVisualizerPanel.add(m_jHistogramMenuButton);    
    jDataVisualizerPanel.add(m_jTableMenuButton);
    jDataVisualizerPanel.add(m_jMapMenuButton);
    jDataVisualizerPanel.add(m_jRecentMenuButton);
    
    //****************************************************************
    //Build the run controls button panel
    //****************************************************************
    JPanel jButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jButtonPanel.setName("run_button_panel");
    m_jModelRunButton.setActionCommand("ModelRun");
    m_jModelRunButton.addActionListener(this);
    m_jModelRunButton.setToolTipText("Run model");
    m_jModelRunButton.setDisabledIcon(new ModelIcon(15, 15,
        ModelIcon.RIGHT_TRIANGLE, Color.GRAY));
    m_jModelStopButton.setActionCommand("ModelStop");
    m_jModelStopButton.addActionListener(this);
    m_jModelStopButton.setToolTipText("Quit model run");
    m_jModelStopButton.setDisabledIcon(new ModelIcon(15, 15,
        ModelIcon.RECTANGLE, Color.GRAY));
    m_jModelPauseButton.setActionCommand("ModelPause");
    m_jModelPauseButton.addActionListener(this);
    m_jModelPauseButton.setToolTipText("Pause model run");
    m_jModelPauseButton.setDisabledIcon(new ModelIcon(15, 15, ModelIcon.PAUSE,
        Color.GRAY));
    m_jModelStepForwardButton.setActionCommand("ModelStepForward");
    m_jModelStepForwardButton.addActionListener(this);
    m_jModelStepForwardButton.setToolTipText("Run model one timestep");
    m_jModelStepForwardButton.setDisabledIcon(new ModelIcon(15, 15,
        ModelIcon.STEP_FORWARD, Color.GRAY));
    m_jModelLoadOutputButton.setActionCommand("OpenRunOutput");
    m_jModelLoadOutputButton.addActionListener(this);
    m_jModelLoadOutputButton.setFont(new SortieFont());
    m_jModelLoadOutputButton.setToolTipText("View the data for this run");
    //Make all the buttons the same height
    int iButtonHeight = (int) m_jModelLoadOutputButton.getPreferredSize().
        getHeight();
    m_jModelRunButton.setPreferredSize(new Dimension( (int) m_jModelRunButton.
        getPreferredSize().getWidth(), iButtonHeight));
    m_jModelStopButton.setPreferredSize(new Dimension( (int) m_jModelStopButton.
        getPreferredSize().getWidth(), iButtonHeight));
    m_jModelPauseButton.setPreferredSize(new Dimension( (int)
        m_jModelPauseButton.getPreferredSize().getWidth(), iButtonHeight));
    m_jModelStepForwardButton.setPreferredSize(new Dimension( (int)
        m_jModelStepForwardButton.getPreferredSize().getWidth(), iButtonHeight));
    jButtonPanel.add(m_jModelStopButton);
    jButtonPanel.add(m_jModelPauseButton);
    jButtonPanel.add(m_jModelRunButton);
    jButtonPanel.add(m_jModelStepForwardButton);
    jButtonPanel.add(m_jModelLoadOutputButton);
    
    JLabel jLabel = new JLabel("Open windows:");
    jLabel.setFont(new SortieFont());
    jButtonPanel.add(jLabel);
    
    m_jWindowsComboBox.setFont(new SortieFont());
    m_jWindowsComboBox.setEnabled(false);
    m_jWindowsComboBox.setName("Windows combo box");    
    m_jWindowsComboBox.addActionListener(this);
    m_jWindowsComboBox.setPreferredSize(new Dimension(450, 
        m_jWindowsComboBox.getPreferredSize().height));
    //m_jWindowsComboBox.setRenderer(new ComboBoxRenderer(m_jWindowsComboBox));
    jButtonPanel.add(m_jWindowsComboBox);
    jButtonPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
        java.awt.Color.BLACK));

    //Package together the data visualizer and buttons panels
    JPanel jTempPanel = new JPanel(new BorderLayout());
    jTempPanel.setName("north_panel");
    jTempPanel.add(jButtonPanel, BorderLayout.NORTH);
    jTempPanel.add(jDataVisualizerPanel, BorderLayout.CENTER);
    m_iPanelHeight = jDataVisualizerPanel.getPreferredSize().height;
    

    //Add it to the main window
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jTempPanel, BorderLayout.NORTH);
    getContentPane().add(m_jScroller, BorderLayout.CENTER);            
    getContentPane().add(jMainPanel, BorderLayout.SOUTH);
  }
  
  private void bringChartToFront(ChartFrameInfo oInfo) throws ModelException {
    JInternalFrame oNewGraph = oInfo.m_oManager.createNewChart(oInfo.m_sChartName);
    try {
      oNewGraph.setVisible(true);
      //Have to do both true then false for it to "take"
      oNewGraph.setMaximum(true);              
      oNewGraph.setMaximum(false); 
    } catch (PropertyVetoException e) {;}              
  
  }

  /**
   * Draws a chart upon selection in the data visualizer panel.
   */
  void doDrawChart(String sChartChoice) {
    try {

      //Get the selected string from the file combo box
      String sFileChoice = (String) m_jFileChoicesComboBox.getSelectedItem();
      if (null == sFileChoice || sFileChoice.startsWith("---")) {
        throw (new ModelException(ErrorGUI.BAD_COMMAND, "JAVA",
                                  "Please select a file for which to draw a chart."));
      }

      if (null == sChartChoice || sChartChoice.startsWith("---")) {
        throw (new ModelException(ErrorGUI.BAD_COMMAND, "JAVA",
                                  "Please select a chart to draw."));
      }
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

      //If the run is currently running (thus signalling real-time data
      //visualization), and the user didn't pause the run before opening this
      //chart, and this is the first chart to be opened, we want to pause the
      //run and then re-start so the flags will be set to keep the charts
      //updated
      if (m_bViewingRunOutput == true &&
          m_iState == MainWindowStateSetter.RUNNING
          && m_oDataVisualizerManager.areCurrentRunChartsOpen() == false) {
        doModelPause(false);
        doModelRun(0);
      }
      
      ChartFrameInfo oInfo = new ChartFrameInfo(
          m_oDataVisualizerManager.getDataManagerForFile(sFileChoice), 
          sChartChoice, sFileChoice);
      m_oDataVisualizerManager.drawChart(sFileChoice, sChartChoice, oInfo);
      m_jWindowsComboBox.addItem(oInfo);
      m_jWindowsComboBox.setEnabled(true);
      
      //Update recent charts list             
      Preferences jPrefs = Preferences.userNodeForPackage(getClass());
      String sRecentChart, sFileAbove;
      int j;
      boolean bExists = false;
      for (j = 1; j < 11; j++) {
        sRecentChart = jPrefs.get("RecentCharts" + String.valueOf(j), "");
        if (sRecentChart.equals(sChartChoice)) {
          bExists = true;
          break;
        }
      }
      if (!bExists) {
        sFileAbove = jPrefs.get("RecentCharts1", "");
        for (j = 2; j < 11; j++) {
          sRecentChart = jPrefs.get("RecentCharts" + String.valueOf(j), "");
          jPrefs.put("RecentCharts" + String.valueOf(j), sFileAbove);
          sFileAbove = sRecentChart;
        }
        jPrefs.put("RecentCharts1", sChartChoice);        
      }
      
      m_jRecentChoices.removeAll();
      int i;
      for (i = 1; i < 11; i++) {
        String sFile1 = jPrefs.get("RecentCharts"+i, "");
        if (sFile1.length() > 0) {
          SortieMenuItem jMenuItem = new SortieMenuItem(
              sFile1, "RecentCharts" + sFile1, this);
          m_jRecentChoices.add(jMenuItem);
        }
      }       

    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
    finally {
      this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }

  /**
   * Gets the GUI manager.
   * @return The GUI manager.
   */
  public GUIManager getDataManager() {
    return m_oDataManager;
  }

  /**
   * File | Exit action performed.  This prompts a save if the user has
   * changed the parameter file, and causes the data visualizer manager to
   * perform cleanup operations.
   */
  private void doFileExit() {

    try {
      m_oDataVisualizerManager.cleanUp();
    } catch (ModelException e) {
      int iReturnVal = JOptionPane.showConfirmDialog(this,
          "An error occurred with the following message: " + 
          e.getMessage() + " Continue shutdown?", "Model",
          JOptionPane.YES_NO_OPTION);

      if (iReturnVal == JOptionPane.NO_OPTION) {
        return;
      }
    }

    //If we have a currently running model, kill it
    if (m_oInterface != null && m_oInterface.isDone() == false) {
      m_oInterface.killProcess();
    }

    //See if they'd like to save any existing files
    //Trigger a save if an existing file was being edited
    if (m_oDataManager.hasParameterFileBeenModified()) {
      int iReturnVal = JOptionPane.showConfirmDialog(this,
          "Do you wish to save a copy of the current file?", "Model",
          JOptionPane.YES_NO_OPTION);

      if (iReturnVal == JOptionPane.YES_OPTION) {
        doFileSave();
        if (m_oDataManager.hasParameterFileBeenModified()) { //they may have cancelled out
          return;
        }
      }
    }

    System.exit(0);
  }

  /**
   * File | New Parameter File action performed
   */
  private void doFileNew() {
    try {
      m_oDataManager.createNewParameterFile();
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
  }

  /**
   * File | Set Working Directory action performed
   */
  private void doFileSetWorkingDirectory() {
    //Launch a file chooser to pick the directory
    ModelFileChooser oChooser = new ModelFileChooser();
    oChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int iReturnVal = oChooser.showOpenDialog(this);
    if (iReturnVal == JFileChooser.APPROVE_OPTION) {
      ModelFileChooser.m_oWorkingDirectory = oChooser.getSelectedFile();
      //Set into the preferences
      Preferences jPrefs = Preferences.userNodeForPackage(getClass());
      jPrefs.put("WorkingDirectory", oChooser.getSelectedFile().getAbsolutePath());
    }
  }

  /**
   * File | New Batch File action performed
   */
  private void doFileBatchNew() {
    BatchSetup oWindow = new BatchSetup(this, m_oDataManager);
    oWindow.pack();
    oWindow.setLocationRelativeTo(null);
    oWindow.setVisible(true);
  }
  
  /**
   * Tools | Rename detailed output file action performed
   */
  private void doToolsRename() {
    DetailedOutputFileCopy oWindow = new DetailedOutputFileCopy(this);
    oWindow.pack();
    oWindow.setLocationRelativeTo(null);
    oWindow.setVisible(true);
  }
  
  /**
   * Tools | Batch extract detailed output file action performed
   */
  private void doToolsBatchOut() {
    new BatchDetailedOutput(this, m_oHelpBroker);    
  }

  /**
   * Responds to the click of the button for viewing the current run's output.
   * If there is no parameter file loaded, this tells the user that it can't
   * do the requested operation.  If there's no output to load, this tells the
   * user that.  Otherwise, this will load all currently created output files
   * for the current run.
   * @throws ModelException if there is a problem opening the output.
   */
  private void doViewRunOutput() throws ModelException {

    try {
      //Check the state - if there's no parameter file loaded, just tell the
      //user there's nothing to do
      if (m_iState == MainWindowStateSetter.NO_PAR_FILE) {
        JOptionPane.showMessageDialog(this,
                                      "You must load a parameter file and run " +
                                      "it to view its data.", "Model",
                                      JOptionPane.ERROR_MESSAGE);
        return;
      }

      //Get the names of the output files to load
      String sDetailedOutputFile = m_oDataManager.getOutputBehaviors().getDetailedOutput().
          getDetailedOutputFileName();
      String sShortOutFile = m_oDataManager.getOutputBehaviors().getShortOutput().
          getShortOutputFileName();

      //If there are no output files set up, warn the user and exit
      if ( (sDetailedOutputFile == null || sDetailedOutputFile.length() == 0) &&
          (sShortOutFile == null || sShortOutFile.length() == 0)) {
        //If the run's already going, tell them they have to stop it
        if (m_iState == MainWindowStateSetter.RUNNING ||
            m_iState == MainWindowStateSetter.PAUSED) {

          JOptionPane.showMessageDialog(this, "The data viewing reads from " +
                                        "output files.\nYou must stop the run " +
                                        "and add output files to your " +
                                        "parameter file to use this option.",
                                        "Model", JOptionPane.ERROR_MESSAGE);
          return;
        }
        else {
          JOptionPane.showMessageDialog(this,
                                        "The data viewing reads from output " +
                                        "files.  You must set up output files to " +
                                        "use this option.", "Model",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }
      }

      //If the run hasn't started, tell them to run a timestep and try again
      if (m_iState == MainWindowStateSetter.PAR_FILE_LOADED) {
        JOptionPane.showMessageDialog(this, "Run the run for at least one " +
                                      "timestep in order to create output to " +
                                      "view.\nIt is safest to open charts when " +
                                      "the model is paused.", "Model",
                                      JOptionPane.INFORMATION_MESSAGE);
        return;
      }

      //If the state is running, tell them to pause before opening charts (but
      //this time don't return)
      if (m_iState == MainWindowStateSetter.RUNNING) {
        JOptionPane.showMessageDialog(this, "It is recommended that you pause " +
                                      "the model before opening new charts.",
                                      "Model", JOptionPane.INFORMATION_MESSAGE);
      }

      //Add the output files to the data visualization manager and set our
      //flag
      m_bViewingRunOutput = true;
      if (sDetailedOutputFile != null && sDetailedOutputFile.length() > 0) {
        m_oDataVisualizerManager.addCurrentRunFile(sDetailedOutputFile);
        //Add this file to the file choices combo box
        //if (m_jFileChoicesComboBox.getItemAt(0).equals("---Load data file---")) {
        //  m_jFileChoicesComboBox.removeItemAt(0);
        //}
        m_jFileChoicesComboBox.setEnabled(true);
        m_jFileChoicesComboBox.insertItemAt(sDetailedOutputFile, 0);
        m_jFileChoicesComboBox.setSelectedIndex(0);

      }
      if (sShortOutFile != null && sShortOutFile.length() > 0) {
        m_oDataVisualizerManager.addCurrentRunFile(sShortOutFile);
        //Add this file to the file choices combo box
        //if (m_jFileChoicesComboBox.getItemAt(0).equals("---Load data file---")) {
        //  m_jFileChoicesComboBox.removeItemAt(0);
        //}
        m_jFileChoicesComboBox.setEnabled(true);
        m_jFileChoicesComboBox.insertItemAt(sShortOutFile, 0);
        m_jFileChoicesComboBox.setSelectedIndex(0);
      }
    }
    catch (ModelException oErr) {
      //If there is a problem, reset the flag and rethrow the error
      m_bViewingRunOutput = false;
      throw (oErr);
    }

  }
  
  /**
   * File | recent file action performed.
   * @param sCommandString Command string from action performed. This will let 
   * us know which file was chosen.
   */
  private void doFileOpenRecentFile(String sCommandString) {
    
    //Get which recent file was chosen.
    String sTemp = sCommandString.substring(sCommandString.length() - 1);
    int iIndex = -1;
    try {
      iIndex = Integer.valueOf(sTemp).intValue();
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "I cannot understand which file to open.");
      return;
    }
    Preferences jPrefs = Preferences.userNodeForPackage(getClass());
    String sFileName = jPrefs.get("RecentFiles"+iIndex, ""),
    sFilePath = jPrefs.get("RecentFilesPath"+iIndex, "");

    if (sFileName == null || sFileName.length() == 0) return;
    if (sFilePath.endsWith(".gz.tar")) {
      if (!new File(sFilePath).exists()) {
        JOptionPane.showMessageDialog(this, "The file could not be found.\n"+
            sFilePath);
        return;
      }
      openFile(sFileName, sFilePath);
    } else {
      if (!new File(sFilePath).exists()) {
        JOptionPane.showMessageDialog(this, "The file could not be found.\n"+
            sFilePath);
        return;
      }
      openFile(sFilePath, "");
    }
    /*try {
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

      if (sFilePath.endsWith(".gz.tar")) {

        if (!new File(sFilePath).exists()) {
          JOptionPane.showMessageDialog(this, "The file could not be found.\n"+
              sFilePath);
          return;
        }

        //It's a detailed output file - extract the file
        File oDetailedOutputFile = null;
        String sExtractedFile = null;
        try {
          sExtractedFile = Tarball.extractTarballFile(sFileName, sFilePath);
          if (sExtractedFile != null) {
            m_oDataManager.inputXMLFile(sExtractedFile, this);
            //Erase the file - it's a temp
            oDetailedOutputFile = new File(sExtractedFile);                          
          }
        }
        finally {
          if (oDetailedOutputFile != null) {
            //Make sure the file gets deleted, even in the event of
            //problems
            File jFileTempPath = oDetailedOutputFile.getParentFile(),
            jTarballRootPath = new File(sFileName).getParentFile();
            oDetailedOutputFile.delete();
            //If there were any temp directories created, delete them too
            if (!jFileTempPath.equals(jTarballRootPath) && 
                jFileTempPath.getPath().indexOf(jTarballRootPath.getPath())
                >= 0) {
              File jFile;
              while (!jFileTempPath.equals(jTarballRootPath)) {
                jFile = jFileTempPath.getParentFile();
                jFileTempPath.delete();
                jFileTempPath = jFile;
              }
            }
          }
        }
      } else if (sFileName.endsWith(".xml")) {
        if (!new File(sFilePath).exists()) {
          JOptionPane.showMessageDialog(this, "The file could not be found.\n"+
              sFilePath);
          return;
        }

        //It's a plaintext XML file        
        m_oDataManager.inputXMLFile(sFilePath, this);
      
        
      }
    } catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
    finally {
      this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }*/
  }

  /**
   * File | Open Parameter File action performed.  Allows the user to input a
   * parameter file of either the old or new type.  Any selected file is
   * passed to the GUI manager for processing.
   */
  /*private void doFileOpen() {
    try {
      boolean bFileRead = false;
      ModelFileChooser chooser = new ModelFileChooser();
      chooser.setFileFilter(new FileOpenFilter());
      int returnVal = chooser.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        //User chose a file - what kind?
        File oFile = chooser.getSelectedFile();
        String sFileName = oFile.getAbsolutePath();
        String sDisplayFile = oFile.getName(); //name we'll display to user

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        if (sFileName.endsWith(".xml")) {

          //It's a plaintext XML file
          bFileRead = m_oDataManager.inputXMLFile(sFileName, this);
        }
        else if (sFileName.endsWith(".gz.tar")) {

          //It's a detailed output file - display for the user the filenames to
          //choose from
          String[] p_sTarFiles = Tarball.getTarballEntries(sFileName);
          if (p_sTarFiles != null) {
            File oDetailedOutputFile = null;
            String sExtractedFile = null;
            try {
              String sTarName = (String) JOptionPane.showInputDialog(
                  this, //component in which to display
                  "Choose the file to add", //user message
                  "SORTIE", //window title
                  JOptionPane.INFORMATION_MESSAGE, //message type
                  null, //icon
                  p_sTarFiles, //list of possibilities to display
                  p_sTarFiles[0]); //initial selection

              if (sTarName == null || sTarName.length() == 0) {
                return;
              }

              sExtractedFile = Tarball.extractTarballFile(sFileName, sTarName);
              if (sExtractedFile != null) {
                m_oDataManager.inputXMLFile(sExtractedFile, this);
                //Erase the file - it's a temp
                oDetailedOutputFile = new File(sExtractedFile);
                bFileRead = true;
                sDisplayFile = new File(sExtractedFile).getName();                
              }
            }
            finally {
              if (oDetailedOutputFile != null) {
                //Make sure the file gets deleted, even in the event of
                //problems
              	File jFileTempPath = oDetailedOutputFile.getParentFile(),
              	     jTarballRootPath = new File(sFileName).getParentFile();
                oDetailedOutputFile.delete();
                //If there were any temp directories created, delete them too
                if (!jFileTempPath.equals(jTarballRootPath) && 
                		jFileTempPath.getPath().indexOf(jTarballRootPath.getPath())
                		  >= 0) {
                	File jFile;
                  while (!jFileTempPath.equals(jTarballRootPath)) {
                    jFile = jFileTempPath.getParentFile();
                    jFileTempPath.delete();
                    jFileTempPath = jFile;
                  }
                }
              }
            }

          }
          else {
            //Invalid file - throw an error
            throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
                                     "This file is empty."));
          }
        }
        else if (sFileName.endsWith(".txt")) {
          //Assume this is a tree map file

          //Make sure a parameter file has been loaded
          if (m_iState == MainWindowStateSetter.PAR_FILE_LOADED) {
            m_oDataManager.inputTreeMap(sFileName);
            m_jMessagesField.setText("Added tree map " + sFileName);
          }
          else {
            throw (new ModelException(ErrorGUI.BAD_COMMAND, "JAVA",
                                      "You must load a parameter file before loading a file of this type."));
          }
        }
        else {
          //This file is not a recognized type
          throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA", "This is not a " +
                                    "recognized file type."));
        }

        
        if (bFileRead) {

          //Add a message to the message bar
          m_jMessagesField.setText("Opened file " + sDisplayFile);

          //Display the last 40 characters
          String sParFileName = m_oDataManager.getParameterFileName();
          if (sParFileName.length() > 40) {
            sParFileName = "..." +
                sParFileName.substring(sParFileName.length() - 40);
          }
          m_jParameterFileField.setText(sParFileName);

          //If we're coming from a no-file-loaded state, switch to file loaded
          if (m_iState == MainWindowStateSetter.NO_PAR_FILE) {
            setModelState(MainWindowStateSetter.PAR_FILE_LOADED);
          }
          
          //Update the recent files list
          Preferences jPrefs = Preferences.userNodeForPackage(getClass());          
          String sFileAbove, sFile;
          boolean bExists = false;
          for (int i = 1; i < 5; i++) {
            sFile = jPrefs.get("RecentFiles" + String.valueOf(i), "");
            if (sFile.equals(sDisplayFile)) {
              bExists = true;
              break;
            }
          }
          if (!bExists) {
            sFileAbove = jPrefs.get("RecentFiles1", "");
            for (int i = 2; i < 5; i++) {
              sFile = jPrefs.get("RecentFiles" + String.valueOf(i), "");
              jPrefs.put("RecentFiles" + String.valueOf(i), sFileAbove);
              sFileAbove = sFile;
            }
            sFileAbove = jPrefs.get("RecentFilesPath1", "");
            for (int i = 2; i < 5; i++) {
              sFile = jPrefs.get("RecentFilesPath" + String.valueOf(i), "");
              jPrefs.put("RecentFilesPath" + String.valueOf(i), sFileAbove);
              sFileAbove = sFile;
            }
            jPrefs.put("RecentFiles1", sDisplayFile);
            jPrefs.put("RecentFilesPath1", sFileName);
            JMenuBar jBar = this.getJMenuBar();
            jBar.remove(0);
            jBar.add(makeFileMenu(), 0);
            jBar.validate();            
          }          

          //Reset all real-time output visualization flags
          m_bKeepRunning = false;
          m_bViewingRunOutput = false;
        }
      }
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
    finally {
      this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }*/
  
  /**
   * File | Open Parameter File action performed.  Allows the user to input a
   * parameter file of either the old or new type.  Any selected file is
   * passed to the GUI manager for processing.
   */
  private void doFileOpen() {
    try {
      ModelFileChooser chooser = new ModelFileChooser();
      chooser.setFileFilter(new FileOpenFilter());
      int returnVal = chooser.showOpenDialog(this);
      if (returnVal != JFileChooser.APPROVE_OPTION) return;
      //User chose a file - what kind?
      File oFile = chooser.getSelectedFile();
      String sFileName = oFile.getAbsolutePath();

      if (sFileName.endsWith(".xml") || sFileName.endsWith(".txt")) {
        openFile(sFileName, "");
      }
      else if (sFileName.endsWith(".gz.tar")) {

        //It's a detailed output file - display for the user the filenames to
        //choose from
        String[] p_sTarFiles = Tarball.getTarballEntries(sFileName);
        if (p_sTarFiles != null) {
          String sTarName = (String) JOptionPane.showInputDialog(
              this, //component in which to display
              "Choose the file to add", //user message
              "SORTIE", //window title
              JOptionPane.INFORMATION_MESSAGE, //message type
              null, //icon
              p_sTarFiles, //list of possibilities to display
              p_sTarFiles[0]); //initial selection

          if (sTarName == null || sTarName.length() == 0) {
            return;
          }

          openFile(sTarName, sFileName);                          
        }
        else {
          //Invalid file - throw an error
          throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
          "This file is empty."));
        }
      }
      else {
        //This file is not a recognized type
        throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA", "This is not a " +
        "recognized file type."));
      }
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
  }
  
  /**
   * Opens a parameter file. This makes sure state setting, recent files, etc. 
   * are updated.
   */
  private void openFile(String sFileName, String sTarName) {
    try {
      String sDisplayFile = "", sPrefsString1 = "", sPrefsString2 = "";
      boolean bFileRead = false;
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

      if (sTarName != null && sTarName.length() > 0 &&
          sTarName.endsWith(".gz.tar")) {

        File oDetailedOutputFile = null;
        String sExtractedFile = null;
        try {

          if (sTarName == null || sTarName.length() == 0) {
            return;
          }

          sExtractedFile = Tarball.extractTarballFile(sTarName, sFileName);
          if (sExtractedFile != null) {
            m_oDataManager.inputXMLFile(sExtractedFile, this);
            //Erase the file - it's a temp
            oDetailedOutputFile = new File(sExtractedFile);
            bFileRead = true;
            sDisplayFile = new File(sExtractedFile).getName(); 
            sPrefsString2 = new File(sTarName).getParentFile().getPath();
            if (sExtractedFile.startsWith(sPrefsString2))
              sPrefsString1 = sExtractedFile.substring(sPrefsString2.length()+1);
            else sPrefsString1 = sExtractedFile;
            sPrefsString2 = sTarName;
          }
        }
        finally {
          if (oDetailedOutputFile != null) {
            //Make sure the file gets deleted, even in the event of
            //problems
            File jFileTempPath = oDetailedOutputFile.getParentFile(),
            jTarballRootPath = new File(sTarName).getParentFile();
            oDetailedOutputFile.delete();
            //If there were any temp directories created, delete them too
            if (!jFileTempPath.equals(jTarballRootPath) && 
                jFileTempPath.getPath().indexOf(jTarballRootPath.getPath())
                >= 0) {
              File jFile;
              while (!jFileTempPath.equals(jTarballRootPath)) {
                jFile = jFileTempPath.getParentFile();
                jFileTempPath.delete();
                jFileTempPath = jFile;
              }
            }
          }
        }
      } 
      else if (sFileName.endsWith(".xml")) {

        //It's a plaintext XML file
        bFileRead = m_oDataManager.inputXMLFile(sFileName, this);
        sDisplayFile = new File(sFileName).getName();
        sPrefsString1 = sDisplayFile;
        sPrefsString2 = sFileName;
      }
      else if (sFileName.endsWith(".txt")) {
        //Assume this is a tree map file

        //Make sure a parameter file has been loaded
        if (m_iState == MainWindowStateSetter.PAR_FILE_LOADED) {
          m_oDataManager.inputTreeMap(sFileName);
          m_jMessagesField.setText("Added tree map " + sFileName);
        }
        else {
          throw (new ModelException(ErrorGUI.BAD_COMMAND, "JAVA",
          "You must load a parameter file before loading a file of this type."));
        }
        sPrefsString1 = sDisplayFile;
        sPrefsString2 = sFileName;
      }
      else {
        //This file is not a recognized type
        throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA", "This is not a " +
        "recognized file type."));
      }


      if (bFileRead) {

        //Add a message to the message bar
        m_jMessagesField.setText("Opened file " + sDisplayFile);

        //Display the last 40 characters
        String sParFileName = m_oDataManager.getParameterFileName();
        if (sParFileName.length() > 40) {
          sParFileName = "..." +
          sParFileName.substring(sParFileName.length() - 40);
        }
        m_jParameterFileField.setText(sParFileName);

        //If we're coming from a no-file-loaded state, switch to file loaded
        if (m_iState == MainWindowStateSetter.NO_PAR_FILE) {
          setModelState(MainWindowStateSetter.PAR_FILE_LOADED);
        }

        //Update the recent files list
        Preferences jPrefs = Preferences.userNodeForPackage(getClass());          
        String sFileAbove, sFile;
        
        //If this is already the first file, don't do anything else
        sFile = jPrefs.get("RecentFiles1", "");
        if (!sFile.equals(sPrefsString1)) {

          sFileAbove = jPrefs.get("RecentFiles1", "");
          for (int i = 2; i < 5; i++) {
            sFile = jPrefs.get("RecentFiles" + String.valueOf(i), "");
            if (!sFileAbove.equals(sPrefsString1)) {
              jPrefs.put("RecentFiles" + String.valueOf(i), sFileAbove);            
            }
            sFileAbove = sFile;
          }
          sFileAbove = jPrefs.get("RecentFilesPath1", "");
          for (int i = 2; i < 5; i++) {
            sFile = jPrefs.get("RecentFilesPath" + String.valueOf(i), "");
            if (!sFileAbove.equals(sPrefsString2)) {
              jPrefs.put("RecentFilesPath" + String.valueOf(i), sFileAbove);
            }
            sFileAbove = sFile;
          }
          //jPrefs.put("RecentFiles1", sDisplayFile);
          //jPrefs.put("RecentFilesPath1", sFileName);
          jPrefs.put("RecentFiles1", sPrefsString1);
          jPrefs.put("RecentFilesPath1", sPrefsString2);
          JMenuBar jBar = this.getJMenuBar();
          jBar.remove(0);
          jBar.add(makeFileMenu(), 0);
          jBar.validate();            
        }

        
        
        
        //Update the recent files list
        /*Preferences jPrefs = Preferences.userNodeForPackage(getClass());          
        String sFileAbove, sFile;
        boolean bExists = false;
        for (int i = 1; i < 5; i++) {
          sFile = jPrefs.get("RecentFiles" + String.valueOf(i), "");
          if (sFile.equals(sDisplayFile)) {
            bExists = true;
            break;
          }
        }
        if (!bExists) {
          sFileAbove = jPrefs.get("RecentFiles1", "");
          for (int i = 2; i < 5; i++) {
            sFile = jPrefs.get("RecentFiles" + String.valueOf(i), "");
            jPrefs.put("RecentFiles" + String.valueOf(i), sFileAbove);
            sFileAbove = sFile;
          }
          sFileAbove = jPrefs.get("RecentFilesPath1", "");
          for (int i = 2; i < 5; i++) {
            sFile = jPrefs.get("RecentFilesPath" + String.valueOf(i), "");
            jPrefs.put("RecentFilesPath" + String.valueOf(i), sFileAbove);
            sFileAbove = sFile;
          }
          //jPrefs.put("RecentFiles1", sDisplayFile);
          //jPrefs.put("RecentFilesPath1", sFileName);
          jPrefs.put("RecentFiles1", sPrefsString1);
          jPrefs.put("RecentFilesPath1", sPrefsString2);
          JMenuBar jBar = this.getJMenuBar();
          jBar.remove(0);
          jBar.add(makeFileMenu(), 0);
          jBar.validate();            
        } */         

        
        
        
        

        //Reset all real-time output visualization flags
        m_bKeepRunning = false;
        m_bViewingRunOutput = false;
      }
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
    finally {
      this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }

  /**
   * Model | Run Batch action performed.  Allows the user to input a
   * batch file and run it.
   */
  private void doModelRunBatch() {
    try {
      ModelFileChooser jChooser = new ModelFileChooser();
      jChooser.setFileFilter(new XMLFileFilter());
      int returnVal = jChooser.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {

        File oFile = jChooser.getSelectedFile();
        String sFileName = oFile.getAbsolutePath();

        //Grab the directory so we can go there again
        //m_oFileDirectory = oFile.getParentFile();
        //Display the last 40 characters
        String sParFileName = m_oDataManager.getParameterFileName();
        if (sParFileName.length() > 40) {
          sParFileName = "..." +
              sParFileName.substring(sParFileName.length() - 40);
        }
        m_jParameterFileField.setText(sParFileName);

        //Reset all real-time output visualization flags
        m_bKeepRunning = false;
        m_bViewingRunOutput = false;

        //If there's a run in progress, do nothing
        if (m_oInterface != null && m_oInterface.isDone() == false &&
            m_oInterface.isPaused() == false) {
          return;
        }

        if (m_oInterface == null) {
          m_oInterface = null;
          m_oInterface = new Interface();
          //Close all open charts
          m_oDataVisualizerManager.closeCurrentRunCharts(this);
        }
        try {

          this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
          setModelState(MainWindowStateSetter.RUNNING);
          //Disable output visualization and pausing
          m_jModelLoadOutputButton.setEnabled(false);
          m_jModelPauseButton.setEnabled(false);
          m_jMenuModelPause.setEnabled(false);

          //Create a timer.  This will put the model run in another thread,
          //which allows the GUI to update during the run, meaning that the
          //run's messages will be displayed.  This timer will trigger once
          //every second.
          m_oTimer = new Timer(1000, new RunListener(this));

          m_oInterface.run(this, sFileName, 0);
          m_oTimer.start();
        }
        finally {
          this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
      }
    }
    finally {
      this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }

  /**
   * Opens an output file.  Any file that was chosen by the user gets passed
   * to the Data Visualization Manager for processing.
   * @throws ModelException if anything goes wrong with the data visualization.
   */
  private void doFileOpenData() throws ModelException {
    try {
      ModelFileChooser jChooser = new ModelFileChooser();
      jChooser.setFileFilter(new sortie.gui.components.OutputFileFilter());
      jChooser.setMultiSelectionEnabled(true);
      int iReturnVal = jChooser.showOpenDialog(this), i, j;
      if (iReturnVal != JFileChooser.APPROVE_OPTION) return;

      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      String sFileName;
      
      Preferences jPrefs = Preferences.userNodeForPackage(getClass());

      //Pass this file to the output manager
      File[] p_jFile = jChooser.getSelectedFiles();
      for (i = 0; i < p_jFile.length; i++) {
        sFileName = p_jFile[i].getAbsolutePath();
        m_oDataVisualizerManager.addFile(sFileName);

        m_jFileChoicesComboBox.insertItemAt(sFileName, 0);
        m_jFileChoicesComboBox.setSelectedIndex(0);
        m_jFileChoicesComboBox.setEnabled(true);


        //Update recent output files list                
        String sDisplayFile = new File(sFileName).getName(), sFileAbove, sFile;
        boolean bExists = false;
        for (j = 1; j < 5; j++) {
          sFile = jPrefs.get("RecentOutputFiles" + String.valueOf(j), "");
          if (sFile.equals(sDisplayFile)) {
            bExists = true;
            break;
          }
        }
        if (!bExists) {
          sFileAbove = jPrefs.get("RecentOutputFiles1", "");
          for (j = 2; j < 5; j++) {
            sFile = jPrefs.get("RecentOutputFiles" + String.valueOf(j), "");
            jPrefs.put("RecentOutputFiles" + String.valueOf(j), sFileAbove);
            sFileAbove = sFile;
          }
          sFileAbove = jPrefs.get("RecentOutputFilesPath1", "");
          for (j = 2; j < 5; j++) {
            sFile = jPrefs.get("RecentOutputFilesPath" + String.valueOf(j), "");
            jPrefs.put("RecentOutputFilesPath" + String.valueOf(j), sFileAbove);
            sFileAbove = sFile;
          }
          jPrefs.put("RecentOutputFiles1", sDisplayFile);
          jPrefs.put("RecentOutputFilesPath1", sFileName);
        }
        JMenuBar jBar = this.getJMenuBar();
        jBar.remove(0);
        jBar.add(makeFileMenu(), 0);
        jBar.validate();            
      }
    }
    finally {
      this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }
  
  /**
   * Opens an output file.  Any file that was chosen by the user gets passed
   * to the Data Visualization Manager for processing.
   * @throws ModelException if anything goes wrong with the data visualization.
   */
  private void doFileOpenRecentData(String sCommandString) throws ModelException {
    try {
      
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      
      //Get which recent file was chosen.
      String sTemp = sCommandString.substring(sCommandString.length() - 1);
      int iIndex = -1, i;
      try {
        iIndex = Integer.valueOf(sTemp).intValue();
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "I cannot understand which file to open.");
        return;
      }
      Preferences jPrefs = Preferences.userNodeForPackage(getClass());
      String sFileName = jPrefs.get("RecentOutputFilesPath"+iIndex, "");
      
      //Check to see if it's already open
      for (i = 0; i < m_jFileChoicesComboBox.getItemCount(); i++) {
        if (m_jFileChoicesComboBox.getItemAt(i).equals(sFileName)) {
          JOptionPane.showMessageDialog(this, "This file is already open.");
          return;
        }
      }

      if (sFileName == null || sFileName.length() == 0) return;
      if (!new File(sFileName).exists()) {
        JOptionPane.showMessageDialog(this, "The file could not be found.\n"+
            sFileName);
        return;
      }
      
      //Pass this file to the output manager
      m_oDataVisualizerManager.addFile(sFileName);

      m_jFileChoicesComboBox.insertItemAt(sFileName, 0);
      m_jFileChoicesComboBox.setSelectedIndex(0);
      m_jFileChoicesComboBox.setEnabled(true);
         
    }
    finally {
      this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }
  
  /**
   * Opens an output file.  Any file that was chosen by the user gets passed
   * to the Data Visualization Manager for processing.
   * @throws ModelException if anything goes wrong with the data visualization.
   */
  /*private void doFileOpenData() throws ModelException {
    try {
      ModelFileChooser jChooser = new ModelFileChooser();
      jChooser.setFileFilter(new sortie.gui.OutputFileFilter());
      jChooser.setMultiSelectionEnabled(true);
      int iReturnVal = jChooser.showOpenDialog(this), i;
      if (iReturnVal == JFileChooser.APPROVE_OPTION) {

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String sFileName;

        //Pass this file to the output manager
        File[] p_jFile = jChooser.getSelectedFiles();
        for (i = 0; i < p_jFile.length; i++) {
          sFileName = p_jFile[i].getAbsolutePath();
          m_oDataVisualizerManager.addFile(sFileName);

          //Add the file to the file choices combo box
          //if (m_jFileChoicesComboBox.getItemAt(0).equals("---Load data file---")) {
          //  m_jFileChoicesComboBox.removeItemAt(0);
          //}
          m_jFileChoicesComboBox.insertItemAt(sFileName, 0);
          m_jFileChoicesComboBox.setSelectedIndex(0);
          m_jFileChoicesComboBox.setEnabled(true);
        }
      }
    }
    finally {
      this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }*/

  /**
   * Closes an open output file.  All open windows related to this file are
   * closed as well.
   */
  protected void doFileCloseData() {

    //Construct a window listing the files to close
    //List of files, with an "all" option if there are at least two
    String[] p_sFilesToClose = null;
    String sName;
    int i,
        iNumFiles = m_jFileChoicesComboBox.getItemCount();

    for (i = 0; i < iNumFiles; i++) {
      sName = (String) m_jFileChoicesComboBox.getItemAt(i);
      if (sName.startsWith("--")) {
        iNumFiles--;
      }
    }

    if (iNumFiles == 0) {
      return;
    }

    if (iNumFiles == 1) {
      for (i = 0; i < iNumFiles; i++) {
        sName = (String) m_jFileChoicesComboBox.getItemAt(i);
        if (!sName.startsWith("--")) {
          closeOneDataFile(sName);          
          return;
        }
      }
    }

    //Put in an "all" option at the top if there are two or more
    if (iNumFiles > 1) {
      iNumFiles++;
      p_sFilesToClose = new String[iNumFiles];
      p_sFilesToClose[0] = "All";
      for (i = 1; i < iNumFiles; i++) {
        sName = (String) m_jFileChoicesComboBox.getItemAt(i - 1);
        if (!sName.startsWith("--")) {
          p_sFilesToClose[i] = sName;
        }
      }
    }
    else {
      p_sFilesToClose = new String[iNumFiles];
      for (i = 0; i < iNumFiles; i++) {
        sName = (String) m_jFileChoicesComboBox.getItemAt(i);
        if (!sName.startsWith("--")) {
          p_sFilesToClose[i] = sName;
        }
      }
    }

    //Find out what the user wants to close
    String sFileName = (String) JOptionPane.showInputDialog(
        this, //component in which to display
        "Choose the file to close", //user message
        "SORTIE", //window title
        JOptionPane.INFORMATION_MESSAGE, //message type
        null, //icon
        p_sFilesToClose, //list of possibilities to display
        p_sFilesToClose[0]); //initial selection

    if (sFileName == null || sFileName.length() == 0) {
      return;
    }

    if (sFileName.equals("All")) {
      for (i = 1; i < iNumFiles; i++) {
        closeOneDataFile(p_sFilesToClose[i]);        
      }
    } else {
      closeOneDataFile(sFileName);
    }
  }

  /**
   * Closes a single output file and manages the file list.
   * @param sFileName String File to close.
   */
  public void closeOneDataFile(String sFileName) {

    //Ask the data visualization manager to close the file
    try {
      m_oDataVisualizerManager.closeFile(sFileName);
    } catch (ModelException e) {
      ErrorGUI oGui = new ErrorGUI(this);
      oGui.writeErrorMessage(e);
    }

    int i,
        iNumFiles = m_jFileChoicesComboBox.getItemCount();

    //Remove the choice from the combo file
    for (i = 0; i < iNumFiles; i++) {
      if (sFileName.equals( (String) m_jFileChoicesComboBox.getItemAt(i))) {
        m_jFileChoicesComboBox.removeItemAt(i);
        break;
      }
    }
    if (m_jFileChoicesComboBox.getItemCount() == 0) {
      m_jFileChoicesComboBox.setEnabled(false);            
    }

    //If we were doing real-time visualization, check to see if this closes
    //all files for the current run; if so, turn off real-time visualization
    if (m_bViewingRunOutput) {

      //If there are no files open, turn off real-time visualization
      if (m_jFileChoicesComboBox.getItemCount() == 1 &&
          ( (String) m_jFileChoicesComboBox.getItemAt(0)).startsWith("---")) {
        m_bKeepRunning = true; //tell the run to keep going from now on
        m_bViewingRunOutput = false;
        return;
      }

      //Get the current run output files and check to see if any of them
      //are still open
      boolean bOpen = false;
      String sDetailedOutputFile = m_oDataManager.getOutputBehaviors().getDetailedOutput().
          getDetailedOutputFileName();
      String sShortOutFile = m_oDataManager.getOutputBehaviors().getShortOutput().
          getShortOutputFileName();
      if (sDetailedOutputFile == null) {
        sDetailedOutputFile = "";
      }
      if (sShortOutFile == null) {
        sShortOutFile = "";
      }
      iNumFiles = m_jFileChoicesComboBox.getItemCount();
      for (i = 0; i < iNumFiles; i++) {
        String sName = (String) m_jFileChoicesComboBox.getItemAt(i);
        if (sDetailedOutputFile.equals(sName) || sShortOutFile.equals(sName)) {
          bOpen = true;
          break;
        }
      }
      if (bOpen == false) {
        //No files from the current run are open - turn off real-time
        //visualization
        m_bKeepRunning = true; //tell the run to keep going from now on
        m_bViewingRunOutput = false;
      }
    }
  }

  /**
   * File | Save Parameter File action performed. This is a request to save
   * a parameter file. The request is passed to the GUI manager for
   * processing.
   */
  protected void doFileSave() {
    try {

      ModelFileChooser jChooser = new ModelFileChooser();
      jChooser.setSelectedFile(new File(m_oDataManager.getParameterFileName()));
      jChooser.setFileFilter(new XMLFileFilter());

      int returnVal = jChooser.showSaveDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {

        //User chose a file - does it already exist?
        File oFile = jChooser.getSelectedFile();
        if (oFile.exists()) {
          returnVal = JOptionPane.showConfirmDialog(this,
                                             "Do you wish to overwrite the existing file?",
                                             "Model",
                                             JOptionPane.YES_NO_OPTION);
        }
        if (!oFile.exists() || returnVal == JOptionPane.YES_OPTION) {
          String sFileName = oFile.getAbsolutePath();
          if (!sFileName.endsWith(".xml")) {
            sFileName = sFileName.concat(".xml");
          }
          //Grab the directory so we can go there again
          //m_oFileDirectory = oFile.getParentFile();

          if (m_oDataManager.writeParameterFile(sFileName)) {

            m_jMessagesField.setText("Successfully saved file " + sFileName);

            String sParFileName = m_oDataManager.getParameterFileName();
            if (sParFileName.length() > 40) {
              sParFileName = "..." +
                  sParFileName.substring(sParFileName.length() - 40);
            }
            m_jParameterFileField.setText(sParFileName);
          }
        }
      }
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
  }

  /**
   * File | Save Parameter File as text action performed.  This is a request to 
   * save a parameter file. The request is passed to the GUI manager for
   * processing.
   */
  protected void doFileTextSave() {
    try {

      ModelFileChooser jChooser = new ModelFileChooser();
      String sSuggestedFile = m_oDataManager.getParameterFileName();
      sSuggestedFile = sSuggestedFile.replace(".xml", ".txt");
      jChooser.setSelectedFile(new File(sSuggestedFile));
      jChooser.setFileFilter(new TextFileFilter());

      int returnVal = jChooser.showSaveDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {

        //User chose a file - does it already exist?
        File oFile = jChooser.getSelectedFile();
        if (oFile.exists()) {
          returnVal = JOptionPane.showConfirmDialog(this,
                                             "Do you wish to overwrite the existing file?",
                                             "SORTIE",
                                             JOptionPane.YES_NO_OPTION);
        }
        if (!oFile.exists() || returnVal == JOptionPane.YES_OPTION) {
          String sFileName = oFile.getAbsolutePath();
          
          m_oDataManager.writeTextVersionOfParameterFile(sFileName);
          m_jMessagesField.setText("Successfully saved file " + sFileName);
        }
      }
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
  }
  
  
  /**
   * Edit | Edit model flow action performed
   */
  private void doEditModelFlow() {
    m_oDataManager.displayModelFlowWindow();
  }

  /**
   * Edit | Run parameters data action performed
   */
  private void doEditParameters() {
    m_oDataManager.displayRunParameters();
  }

  /**
   * Edit | Edit output options action performed
   */
  private void doEditOutput() {
    m_oDataManager.displayOutputWindow();
  }

  /**
   * Sets the current window state.
   * @param iState State to set to.  Should be one of the choices in
   * MainWindowStateSetter.
   */
  public void setModelState(int iState) {
    m_iState = iState;
    MainWindowStateSetter.goToState(iState, this);
  }

  /**
   * Gets the current window state.
   * @return int Current state.  This matches one of the choices in
   * MainWindowStateSetter.
   */
  public int getModelState() {
    return m_iState;
  }

  /**
   * Model | Pause action performed.  Pauses the model.
   * @throws ModelException passing through from called methods.
   * @param bAlert Whether or not to alert the user to the pause.
   */
  private void doModelPause(boolean bAlert) throws ModelException {
    if (m_oInterface != null && m_oInterface.isDone() == false) {
      m_bKeepRunning = false;
      m_oInterface.pause();
      setModelState(MainWindowStateSetter.PAUSED);
      if (bAlert) {
        JOptionPane.showMessageDialog(this,
                                      "The model will pause at the end of the current timestep.\n  Watch the messages panel at the bottom of the screen.",
                                      "Model", JOptionPane.PLAIN_MESSAGE);
      }
    }
  }

  /**
   * Model | Stop Run action performed.  Stops the currently executing run.
   * @throws ModelException passed through from called functions.
   */
  private void doModelStop() throws ModelException {
    if (m_oInterface != null) {
      if (m_iState == MainWindowStateSetter.RUNNING) {
        JOptionPane.showMessageDialog(this,
                                      "The model will stop at the end of the current timestep.");
      }
      m_oInterface.stop();
      //Get any messages from the model run and display them
      String sMessage = m_oInterface.getMessage();
      if (sMessage != null && sMessage.length() > 0) {
        m_jMessagesField.setText(sMessage);
      }
    }
  }

  /**
   * Model | Run action performed.  This runs the model.  The
   * request is passed off to the Interface, which handles the communications
   * with the C++ core.
   *
   * If there is an existing Interface object whose run has been paused, then
   * this will pass it the run command and let it continue.  If the model is
   * already running, this does nothing.
   *
   * @param iNumStepsToRun Number of timesteps to run the model.  Set to 0 if
   * the model should run without interruption.
   */
  private void doModelRun(int iNumStepsToRun) {
    //If there's a run in progress, do nothing
    if (m_oInterface != null && m_oInterface.isDone() == false &&
        m_oInterface.isPaused() == false) {
      return;
    }

    //If the number of timesteps is 0 and we're doing real-time data
    //visualization, set the flag that keeps the run going after data
    //updates
    if (m_bViewingRunOutput && iNumStepsToRun == 0) {
      m_bKeepRunning = true;
    }
    else {
      m_bKeepRunning = false;
    }

    //First check to see if there's a run that's paused - don't interrupt if
    //there is
    boolean bWasPaused = false;
    if (m_oInterface != null) {
      bWasPaused = m_oInterface.isPaused();

    }
    if (m_oInterface == null || bWasPaused == false) {
      m_oInterface = null;
      m_oInterface = new Interface();
      //Close all open charts
      m_oDataVisualizerManager.closeCurrentRunCharts(this);
    }
    try {
      if (m_iState == MainWindowStateSetter.NO_PAR_FILE) {
        //Error message
        throw(new ModelException(ErrorGUI.MODEL_NOT_READY, "JAVA",
                                 "The model is not ready to run."));
      }
      //Get a parameter file name to load
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      final String sFileName = m_oDataManager.prepToRun();
      setModelState(MainWindowStateSetter.RUNNING);

      //Create a timer.  This will put the model run in another thread,
      //which allows the GUI to update during the run, meaning that the
      //run's messages will be displayed.  This timer will trigger once
      //every second.
      if (bWasPaused == false) {
        m_oTimer = new Timer(1000, new RunListener(this));
      }

      //If we're viewing output, ignore the actual number of timesteps and run
      //for one timestep.  The timer code will catch the pause, update the
      //charts, and run it some more if necessary.
      if (m_bViewingRunOutput) {
        m_oInterface.run(this, sFileName, 1);
      }
      else {
        m_oInterface.run(this, sFileName, iNumStepsToRun);
      }

      if (bWasPaused == false) {
        m_oTimer.start();
      }
    }
    catch (ModelException oErr) {
      //If there's a process, kill it and set the interface back to null
      if (m_oInterface != null) {
        m_oInterface.killProcess();
        m_oInterface = null;
        setModelState(MainWindowStateSetter.PAR_FILE_LOADED);
      }
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
      setModelState(MainWindowStateSetter.PAR_FILE_LOADED);
    }
    finally {
      this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }

  /**
   * Changes the cursor to the default.  This is a separate
   * function so it can be called from the private inner class created in
   * doModelRun().
   */
  protected void setCursorToDefault() {
    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
  }

  /**
   * Manages window events.
   * @param oEvent Event triggered.
   */
  public void actionPerformed(ActionEvent oEvent) {
    try {
      String sCommandString = oEvent.getActionCommand();
      if (sCommandString.equals("FileExit")) {
        doFileExit();
      }
      else if (sCommandString.equals("FileOpen")) {
        doFileOpen();
      }
      else if (sCommandString.equals("FileNew")) {
        doFileNew();
      }
      else if (sCommandString.equals("FileOpenData")) {
        doFileOpenData();
      }
      else if (sCommandString.equals("FileCloseData")) {
        doFileCloseData();
      }
      else if (sCommandString.equals("FileSetDir")) {
        doFileSetWorkingDirectory();
      }
      else if (sCommandString.equals("FileSave")) {
        doFileSave();
      }
      else if (sCommandString.equals("FileSaveText")) {
        doFileTextSave();
      }
      else if (sCommandString.equals("EditGrid")) {
        doEditGrid();
      }
      else if (sCommandString.equals("FileNewBatch")) {
        doFileBatchNew();
      }
      else if (sCommandString.equals("ModelRun")) {
        doModelRun(0);
      }
      else if (sCommandString.equals("ModelRunBatch")) {
        doModelRunBatch();
      }
      else if (sCommandString.equals("ModelStepForward")) {
        doModelRun(1);
      }
      else if (sCommandString.equals("ModelPause")) {
        doModelPause(true);
      }
      else if (sCommandString.equals("ModelStop")) {
        doModelStop();
      }
      else if (sCommandString.equals("EditFlow")) {
        doEditModelFlow();
      }
      else if (sCommandString.equals("EditOutput")) {
        doEditOutput();
      }
      else if (sCommandString.equals("EditParameters")) {
        doEditParameters();
      }
      else if (sCommandString.equals("EditTreeSpecies")) {
        doEditTreeSpecies();
      }
      else if (sCommandString.equals("EditTreeDensityClasses")) {
        doEditTreeDensityClasses(); 
      }
      else if (sCommandString.equals("EditTreeMaps")) {
        doEditTreeManageTreeMap(); 
      }
      else if (sCommandString.equals("ToolsRename")) {
        doToolsRename();
      }
      else if (sCommandString.equals("ToolsBatchOut")) {
        doToolsBatchOut();
      }
      else if (sCommandString.equals("OpenRunOutput")) {
        doViewRunOutput();
      }
      else if (sCommandString.startsWith("RecentFile")) {
        doFileOpenRecentFile(sCommandString); 
      }
      else if (sCommandString.startsWith("RecentOutputFile")) {
        doFileOpenRecentData(sCommandString); 
      }
      else if (oEvent.getSource() instanceof JMenuItem) {
        if (sCommandString.startsWith("RecentCharts")) 
          doDrawChart(sCommandString.substring(12));
        else
          doDrawChart(sCommandString);
      }
      
      else if (oEvent.getSource().equals(m_jFileChoicesComboBox)) {
        updateChartChoices();
      } else if (oEvent.getSource().equals(m_jWindowsComboBox)) {
        if (m_jWindowsComboBox.getItemCount() > 0) {
          Object oObj = m_jWindowsComboBox.getSelectedItem();
          if (oObj != null) bringChartToFront((ChartFrameInfo) oObj);
        }        
      }
      
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
    catch (Error oErr) {
      String sMessage = oErr.getMessage();
      if (oErr.getClass() != null) {
        sMessage += ", " + oErr.getClass();
      }
      if (oErr.getCause() != null) {
        sMessage += ", " + oErr.getCause().getMessage();
      }
      ModelException oExp = new ModelException(ErrorGUI.UNKNOWN, "", sMessage);
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oExp);
    }
    catch (Exception oErr) {
      ModelException oExp = new ModelException(ErrorGUI.UNKNOWN, "",
                        oErr.getMessage() + oErr.getClass());
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oExp);
    } 
  }

  /**
   * Displays the tree species editing window.
   */
  private void doEditTreeSpecies() {
    m_oDataManager.displayTreeSpeciesSetupWindow();
  }
    
  /**
   * Displays the tree initial density classes window.
   */
  private void doEditTreeDensityClasses() {
    m_oDataManager.displayTreeSizeClassEditor();
  }
  
  /**
   * Displays the tree map management window.
   */
  private void doEditTreeManageTreeMap() throws ModelException {
    m_oDataManager.displayManageTreeMap();
  }

  /**
   * Displays the Grid editing window.
   */
  private void doEditGrid() {
    m_oDataManager.displayGridSetupWindow();
  }

  /**
   * Updates the chart choices in the data visualization panel according to the
   * file which is selected in the file list.
   */
  public void updateChartChoices() {
    try {
      JMenuItem jMenuItem;
      //Get the data options for this file to display
      String sFileName = (String) m_jFileChoicesComboBox.getSelectedItem();
      m_jLineGraphChoices.removeAll();
      m_jHistogramChoices.removeAll();
      m_jTableChoices.removeAll();
      m_jMapChoices.removeAll();

      if (sFileName == null || sFileName.length() == 0 ||
          sFileName.startsWith("---")) {
        m_jLineGraphMenuButton.setEnabled(false);
        m_jHistogramMenuButton.setEnabled(false);
        m_jTableMenuButton.setEnabled(false);
        m_jMapMenuButton.setEnabled(false);
        return;
      }
      m_jLineGraphMenuButton.setEnabled(true);
      m_jHistogramMenuButton.setEnabled(true);
      m_jTableMenuButton.setEnabled(true);
      m_jMapMenuButton.setEnabled(true);
      
      JPopupMenu jMenu = m_oDataVisualizerManager.getLineGraphOptionsForFile(sFileName, this);
      if (jMenu == null || jMenu.getSubElements().length == 0) {
        jMenuItem = new JMenuItem("---None available---");
        jMenuItem.setFont(new SortieFont());
        m_jLineGraphChoices.add(jMenuItem);        
      } else { 
        m_jLineGraphChoices = jMenu;      
        m_jLineGraphMenuButton.removeMouseListener(m_jLineGraphMenuButton.getMouseListeners()[0]);
        m_jLineGraphMenuButton.addMouseListener(new PopupListener(
            m_jLineGraphMenuButton, m_jLineGraphChoices));
      } 
            
      jMenu = m_oDataVisualizerManager.getHistogramOptionsForFile(sFileName, this);
      if (jMenu == null || jMenu.getSubElements().length == 0) {
        jMenuItem = new JMenuItem("---None available---");
        jMenuItem.setFont(new SortieFont());
        m_jHistogramChoices.add(jMenuItem);        
      } else {
        m_jHistogramChoices = jMenu;      
        m_jHistogramMenuButton.removeMouseListener(m_jHistogramMenuButton.getMouseListeners()[0]);
        m_jHistogramMenuButton.addMouseListener(new PopupListener(
            m_jHistogramMenuButton, m_jHistogramChoices));
      }
      
      jMenu = m_oDataVisualizerManager.getTableOptionsForFile(sFileName, this);
      if (jMenu == null || jMenu.getSubElements().length == 0) {
        jMenuItem = new JMenuItem("---None available---");
        jMenuItem.setFont(new SortieFont());
        m_jTableChoices.add(jMenuItem);
        
      } else {
        m_jTableChoices = jMenu;      
        m_jTableMenuButton.removeMouseListener(m_jTableMenuButton.getMouseListeners()[0]);
        m_jTableMenuButton.addMouseListener(new PopupListener(
            m_jTableMenuButton, m_jTableChoices));
      }
      
      jMenu = m_oDataVisualizerManager.getMapOptionsForFile(sFileName, this);
      if (jMenu == null || jMenu.getSubElements().length == 0) {
        jMenuItem = new JMenuItem("---None available---");
        jMenuItem.setFont(new SortieFont());
        m_jMapChoices.add(jMenuItem);
        
      } else {
        m_jMapChoices = jMenu;      
        m_jMapMenuButton.removeMouseListener(m_jMapMenuButton.getMouseListeners()[0]);
        m_jMapMenuButton.addMouseListener(new PopupListener(
            m_jMapMenuButton, m_jMapChoices));
      }
      
      if (m_jRecentChoices.getSubElements().length == 0) 
        m_jRecentMenuButton.setEnabled(false);
      else 
        m_jRecentMenuButton.setEnabled(true);
    }
    catch (ModelException e) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(e);
    }
  }
  
  /** Not implemented.
   */
  public void windowActivated(WindowEvent e) {;}

  /** Not implemented.
   */
  public void windowClosed(WindowEvent e) {;}

  /** Ensures the application closes when this window closes.
   */
  public void windowClosing(WindowEvent e) {
    doFileExit();    
  }

  /** Not implemented.
   */
  public void windowDeactivated(WindowEvent e) {;}

  /** Not implemented.
   */
  public void windowDeiconified(WindowEvent e) {;}

  /** Not implemented.
   */
  public void windowIconified(WindowEvent e) {;}

  /** Not implemented.
   */
  public void windowOpened(WindowEvent e) {;}

  /**
   * Runs the window's timer code while the C++ core is running.  This code
   * will execute at specified intervals to accept messages from the C++
   * core.
   * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
   * <p>Company: Cary Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   */
  class RunListener
      implements ActionListener {
    /** Main window object */
    MainWindow m_oWindow;
    /** Whether or not charts have been updated */
    boolean m_bUpdatedCharts = false;

    /**
     * Constructor
     * @param oWindow MainWindow object
     */
    public RunListener(MainWindow oWindow) {
      m_oWindow = oWindow;
    }

    /**
     * This is the code that will trigger every interval
     * @param evt Event that triggers this function.
     */
    public void actionPerformed(ActionEvent evt) {
      try {

        //Get any messages from the model run and display them
        String sMessage = m_oInterface.getMessage();
        if (sMessage != null && sMessage.length() > 0) {
          //If the message contains the word "paused" assume that the run
          //is paused
          if (sMessage.indexOf("paused") > -1) {
            setModelState(MainWindowStateSetter.PAUSED);
            m_bUpdatedCharts = false;
          }
          m_jMessagesField.setText(sMessage);
        }

        //If the run is over, reset everything and notify the user
        if (m_oInterface.isDone()) {
          m_bKeepRunning = false;
          //        if (m_bViewingRunOutput == true) {
          //          m_oDataVisualizerManager.UpdateCurrentRunCharts();
          //        }
          setCursorToDefault();
          setModelState(MainWindowStateSetter.PAR_FILE_LOADED);
          m_oTimer.stop();
          if (m_oInterface.isOK()) {
            JOptionPane.showMessageDialog(m_oWindow, "Run complete.", "SORTIE",
                                          JOptionPane.INFORMATION_MESSAGE);
          }
          return;
        }

        //If we are doing realtime data visualization and the model is
        //paused, update the charts
        if (m_iState == MainWindowStateSetter.PAUSED && m_bViewingRunOutput
            && m_bUpdatedCharts == false) {
          m_oDataVisualizerManager.updateCurrentRunCharts();
          m_bUpdatedCharts = true;
        }

        //If we are doing realtime data visualization, the model is paused,
        //and the user wants to keep running timesteps, run the next one
        if (m_iState == MainWindowStateSetter.PAUSED && m_bKeepRunning) {
          if (m_bViewingRunOutput) {
            doModelRun(1);
            //doModelRun will reset this since the number of timesteps was 1 -
            //so set it back
            m_bKeepRunning = true;
          }
          else {
            //This situation means that data visualization was just turned OFF,
            //by closing all the output files.  So trigger a regular run
            doModelRun(0);
          }
        }

      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(m_oWindow);
        oHandler.writeErrorMessage(oErr);
      }

    }
  }
  
   
  /**
   * Handles the closing of chart windows. When a chart window is closed, this 
   * removes it from the list of open windows.
   * @param e InternalFrameEvent
   */
  public void internalFrameClosed(InternalFrameEvent e){
    JInternalFrame jFrame = e.getInternalFrame();
    if (jFrame instanceof ModelInternalFrame) {
      ChartFrameInfo oInfo  = ((ModelInternalFrame)jFrame).getChartFrameInfo();
      if (oInfo == null) return;
      for (int i = 0; i < m_jWindowsComboBox.getItemCount(); i++) {
        ChartFrameInfo oOpenInfo = (ChartFrameInfo) m_jWindowsComboBox.getItemAt(i);
        if (oOpenInfo.equals(oInfo)) {
          m_jWindowsComboBox.removeItemAt(i);
          if (m_jWindowsComboBox.getItemCount() == 0) 
            m_jWindowsComboBox.setEnabled(false);
          return;
        }
      }
    }
  }

  public void internalFrameClosing(InternalFrameEvent e) {}
  public void internalFrameDeactivated(InternalFrameEvent e) {}
  public void internalFrameDeiconified(InternalFrameEvent e) {}
  public void internalFrameIconified(InternalFrameEvent e) {}
  public void internalFrameOpened(InternalFrameEvent e) {}
  
  /**
   * When a chart window is activated, this also brings its legend to the top.
   * @param e InternalFrameEvent
   */
  public void internalFrameActivated(InternalFrameEvent e) {
    JInternalFrame jFrame = e.getInternalFrame();
    if (jFrame instanceof ModelInternalFrame) {
      ModelInternalFrame oFrame = (ModelInternalFrame)jFrame;
      oFrame.getLegend().toFront();
      oFrame.toFront();
    }
  }

}

/**
 * This limits values displayed in the file choices combo box to 50 characters
 * (the last 50, so filenames are displayed).
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */
class ComboBoxRenderer
    extends JLabel
    implements ListCellRenderer<String> {

  int m_iMaxChars = 50; /**<Max chars to display*/

  /**
   * Constructor.
   * @param oBox JComboBox Combo box to display
   */
  public ComboBoxRenderer(JComboBox<String> oBox) {
    setOpaque(true);
    setHorizontalAlignment(CENTER);
    setVerticalAlignment(CENTER);

    //Figure out the max chars to display.
    //How wide is 10 chars?
    JLabel jTempLabel = new JLabel("XXXXXXXXXX");
    jTempLabel.setFont(new SortieFont());
    m_iMaxChars = (int) ( (10 * oBox.getPreferredSize().getWidth()) /
                         jTempLabel.getPreferredSize().getWidth());
  }

  /**
   * This method displays the file choice, with a max of 50 chars.
   * @param list List object
   * @param value value to display
   * @param index Index selected
   * @param isSelected Whether it's selected
   * @param cellHasFocus Whether the cell has focus
   * @return Formatted component
   */
  public Component getListCellRendererComponent(
      JList<? extends String> list,
      String value,
      int index,
      boolean isSelected,
      boolean cellHasFocus) {

    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    }
    else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }

    //Get the string to display
    String sText = (String) value;
    String sTextToDisplay = sText;

    setToolTipText(sText);

    //Truncate to max chars
    if (sText != null) {
      if (sText.length() > m_iMaxChars) {
        //Take the LAST 50
        sText = sText.substring(sText.length() - m_iMaxChars, sText.length());
        sTextToDisplay = "..." + sText;
      }
    }

    setText(sTextToDisplay);

    return this;
  }
  
}

class PopupListener extends MouseAdapter {
  private JButton jButton;
  private JPopupMenu jMenu;
  public PopupListener(JButton jButton, JPopupMenu jMenu) {
    this.jButton = jButton;
    this.jMenu = jMenu;
  }
  public void mouseClicked(MouseEvent e) {
    if (jButton.isEnabled())
      jMenu.show(jButton, 0, jButton.getHeight());
  }
  public void mouseEntered(MouseEvent arg0) {
    if (jButton.isEnabled())
      jButton.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
  }
  public void mouseExited(MouseEvent arg0) {
    if (jButton.isEnabled())
      jButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
  }
  
}