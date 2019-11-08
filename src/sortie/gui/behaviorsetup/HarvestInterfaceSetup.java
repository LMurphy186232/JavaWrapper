package sortie.gui.behaviorsetup;
import javax.swing.*;

import sortie.data.funcgroups.*;
import sortie.data.funcgroups.disturbance.HarvestInterface;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a window allowing setup of the Harvest Interface behavior.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 * <br>Edit history:
 * <br>-------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 * <br>July 12, 2013: This is now called from the behavior, so cleaned up some
 * code that allowed for the possibility that the behavior didn't exist (can
 * no longer be true) (LEM)
 */

public class HarvestInterfaceSetup
extends JDialog
implements ActionListener { 

  /** HarvestInterface object.*/
  protected HarvestInterface m_oBehavior;

  /** Field for capturing the executable's path and filename*/
  protected JTextField m_jExecutablePathEdit = new JTextField();

  /** Field for capturing the filename that SORTIE is to write*/
  protected JTextField m_jSORTIEOutFileEdit = new JTextField();

  /** Field for capturing the executable's harvest reply file*/
  protected JTextField m_jExecutableHarvestOutFileEdit = new JTextField();

  /** Field for capturing the executable's update reply file*/
  protected JTextField m_jExecutableUpdateOutFileEdit = new JTextField();

  /** Field for capturing the batch parameters file*/
  protected JTextField m_jBatchParamsFileEdit = new JTextField();

  /** Field for capturing the file for a single run of batch parameters*/
  protected JTextField m_jBatchSingleRunParamsFileEdit = new JTextField();

  /** Field for entering new tree data members */
  protected JTextField m_jNewTreeDataMemberEdit = new JTextField();

  /** Field for capturing argument strings to pass to the executable */
  protected JTextField m_jExecutableArgs = new JTextField();

  /** Field for capturing the harvest period */
  protected JTextField m_jHarvestPeriod = new JTextField();

  /** The list displaying the new tree data members for this behavior */
  protected JList<String> m_jNewTreeDataMembersList;

  /** The list displaying the file columns */
  protected JList<String> m_jFileColumnsList;

  /** The new tree data members for this behavior */
  protected DefaultListModel<String> m_jNewTreeDataMembersListModel = new DefaultListModel<String>();

  /** The file columns for the harvest files */
  protected DefaultListModel<String> m_jFileColumnsListModel = new DefaultListModel<String>();

  /** List of existing data members, so we can make sure any added have unique
   * names */
  protected ArrayList<String> mp_sAllExistingDataMembers = new ArrayList<String>(0);

  /** The ID of the help file for this window */
  private String m_sHelpID = "windows.edit_harvest_interface_window";

  /**
   * Constructor.
   * @param oParent JFrame Parent frame
   * @param oBeh HarvestInterface object to set up for.
   * @throws ModelException Passing through from called functions.
   */
  public HarvestInterfaceSetup(JFrame oParent, HarvestInterface oBeh) throws
  ModelException {
    super(oParent, "Edit Harvest Interface", true);
    m_oBehavior = oBeh;
    
    loadData();
    createGUI();
  }

  /**
   * Extracts data from the harvest interface behavior, making it ready for
   * display
   * @throws ModelException Passing through from other called functions.
   */
  protected void loadData() throws ModelException {
    int i, j, k, m;

    //**************************
    //File names and basic data
    //**************************
    m_jExecutablePathEdit.setText(m_oBehavior.m_sHarvIntExecutable.getValue());
    m_jBatchParamsFileEdit.setText(m_oBehavior.m_sHarvIntBatchParamsFile.getValue());
    m_jSORTIEOutFileEdit.setText(m_oBehavior.m_sHarvIntSORTIEOutFile.getValue());
    m_jBatchSingleRunParamsFileEdit.setText(m_oBehavior.m_sHarvIntBatchSingleRunParamsFile.getValue());
    m_jExecutableHarvestOutFileEdit.setText(m_oBehavior.m_sHarvIntExecHarvestOutFile.getValue());
    m_jExecutableArgs.setText(m_oBehavior.m_sHarvIntExecArgs.getValue());
    m_jHarvestPeriod.setText(String.valueOf(m_oBehavior.m_iHarvIntHarvestPeriod.getValue()));
    m_jExecutableUpdateOutFileEdit.setText(m_oBehavior.m_sHarvIntExecUpdateOutFile.getValue());

    //**************************
    //File columns
    //**************************
    //Add the default column list
    m_jFileColumnsListModel.addElement("X");
    m_jFileColumnsListModel.addElement("Y");
    m_jFileColumnsListModel.addElement("Species");
    m_jFileColumnsListModel.addElement("Type");
    m_jFileColumnsListModel.addElement("Diam");
    m_jFileColumnsListModel.addElement("Height");
    //Add a dashed line to separate mandatory from additional columns
    m_jFileColumnsListModel.addElement("-------");

    for (i = 0; i < m_oBehavior.mp_sHarvIntFileColumns.getValue().size(); i++) {
      m_jFileColumnsListModel.addElement(m_oBehavior.mp_sHarvIntFileColumns.
          getValue().get(i).toString());
    }

    //**************************
    //New data members being created by this behavior
    //**************************
    for (i = 0; i < m_oBehavior.mp_sHarvIntNewTreeDataMembers.size(); i++) {
      m_jNewTreeDataMembersListModel.addElement(m_oBehavior.
          mp_sHarvIntNewTreeDataMembers.
          get(i));
    }

    //**************************
    //All existing data members defined in the behavior, whether being used
    //in this run or not
    //**************************
    //Add those data members defined in the tree population
    mp_sAllExistingDataMembers.add("X");
    mp_sAllExistingDataMembers.add("Y");
    mp_sAllExistingDataMembers.add("Diam10");
    mp_sAllExistingDataMembers.add("Height");
    mp_sAllExistingDataMembers.add("DBH");
    mp_sAllExistingDataMembers.add("Age");

    BehaviorTypeBase[] p_oGroups = m_oBehavior.getGUIManager().getAllObjects();
    ArrayList<Behavior> p_oBehaviors;
    DataMember oDataMember;
    String sDataMemberName;
    boolean bFound;
    for (i = 0; i < p_oGroups.length; i++) {
      p_oBehaviors = p_oGroups[i].getAllInstantiatedBehaviors();
      for (j = 0; j < p_oBehaviors.size(); j++) {
        for (k = 0; k < p_oBehaviors.get(j).getNumberNewDataMembers(); k++) {
          oDataMember = p_oBehaviors.get(j).getNewTreeDataMember(k);
          sDataMemberName = oDataMember.getCodeName();
          bFound = false;
          for (m = 0; m < mp_sAllExistingDataMembers.size(); m++) {
            if (sDataMemberName.equals((String)mp_sAllExistingDataMembers.get(m))) {
              bFound = true;
              break;
            }
          }
          if (false == bFound) {
            mp_sAllExistingDataMembers.add(sDataMemberName);
          }
        }
      }
    }

  }

  /**
   * Controls actions for this window.
   * @param e ActionEvent.
   */
  public void actionPerformed(ActionEvent e) {
    try {
      String sCommand = e.getActionCommand();
      if (sCommand.equals("Cancel")) {
        this.setVisible(false);
        this.dispose();
      }
      else if (sCommand.equals("OK")) {
        if (ok()) {
          this.setVisible(false);
          this.dispose();
        }
      }
      else if (sCommand.equals("Browse_BatchParamsFile")) {
        browse(m_jBatchParamsFileEdit);
      }
      else if (sCommand.equals("Browse_SORTIEOutFile")) {
        browse(m_jSORTIEOutFileEdit);
      }
      else if (sCommand.equals("Browse_BatchParamsSingleRunFile")) {
        browse(m_jBatchSingleRunParamsFileEdit);
      }
      else if (sCommand.equals("Browse_ExecHarvestOutFile")) {
        browse(m_jExecutableHarvestOutFileEdit);
      }
      else if (sCommand.equals("Browse_ExecUpdateOutFile")) {
        browse(m_jExecutableUpdateOutFileEdit);
      }
      else if (sCommand.equals("Browse_Executable")) {
        browse(m_jExecutablePathEdit);
      }
      else if (sCommand.equals("Add_Column")) {
        new FileColumnsChooser(this);
      }
      else if (sCommand.equals("Remove_Column")) {
        removeColumn();
      }
      else if (sCommand.equals("Column_Up")) {
        columnUp();
      }
      else if (sCommand.equals("Column_Down")) {
        columnDown();
      }
      else if (sCommand.equals("Add_Member")) {
        addDataMember();
      }
      else if (sCommand.equals("Remove_Member")) {
        removeDataMember();
      }
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
  }

  /**
   * Creates the window.
   */
  private void createGUI() {
    int iWidth = 300; //preferred width of file paths and scrollers

    //******************************************
    // At the top of the window: basic controls, in a gridded pattern
    //******************************************
    JPanel jNorthPanel = new JPanel(new GridLayout(0, 2));

    //*
    //Row 1, col 1: Components for collecting the path to the executable
    //*
    JPanel jCellPanel = new JPanel();
    jCellPanel.setLayout(new BoxLayout(jCellPanel, BoxLayout.PAGE_AXIS));
    JLabel jLabel = new JLabel(m_oBehavior.m_sHarvIntExecutable.getDescriptor());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(new SortieFont());
    jCellPanel.add(jLabel);
    //Text box and browse button for collecting the path to the
    //executable, packaged together in a panel so they'll be side-by-side
    JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jExecutablePathEdit.setFont(new SortieFont());
    m_jExecutablePathEdit.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jExecutablePathEdit.setPreferredSize(new Dimension(iWidth, 25));
    jTempPanel.add(m_jExecutablePathEdit);
    JButton jButton = new JButton("Browse");
    jButton.setFont(new SortieFont());
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.addActionListener(this);
    jButton.setActionCommand("Browse_Executable");
    jTempPanel.add(jButton);
    jCellPanel.add(jTempPanel);
    jNorthPanel.add(jCellPanel);
    
    //*
    //Row 1, col 2: Components for collecting the path to the batch parameters
    //file
    //*
    jCellPanel = new JPanel();
    jCellPanel.setLayout(new BoxLayout(jCellPanel, BoxLayout.PAGE_AXIS));
    jLabel = new JLabel(m_oBehavior.m_sHarvIntBatchParamsFile.getDescriptor());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(new SortieFont());
    jCellPanel.add(jLabel);
    //Text box and browse button for collecting the path to the
    //executable harvest out file, packaged together in a panel so they'll be
    //side-by-side
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jBatchParamsFileEdit.setFont(new SortieFont());
    m_jBatchParamsFileEdit.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jBatchParamsFileEdit.setPreferredSize(new Dimension(iWidth, 25));
    jTempPanel.add(m_jBatchParamsFileEdit);
    jButton = new JButton("Browse");
    jButton.setFont(new SortieFont());
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.addActionListener(this);
    jButton.setActionCommand("Browse_BatchParamsFile");
    jTempPanel.add(jButton);
    jCellPanel.add(jTempPanel);
    jNorthPanel.add(jCellPanel);

    //*
    //Row 2, col 1: Components for collecting the path to the SORTIE out file
    //*
    jCellPanel = new JPanel();
    jCellPanel.setLayout(new BoxLayout(jCellPanel, BoxLayout.PAGE_AXIS));
    jLabel = new JLabel(m_oBehavior.m_sHarvIntSORTIEOutFile.getDescriptor());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(new SortieFont());
    jCellPanel.add(jLabel);
    //Text box and browse button for collecting the path to the
    //SORTIE out file, packaged together in a panel so they'll be side-by-side
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jSORTIEOutFileEdit.setFont(new SortieFont());
    m_jSORTIEOutFileEdit.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jSORTIEOutFileEdit.setPreferredSize(new Dimension(iWidth, 25));
    jTempPanel.add(m_jSORTIEOutFileEdit);
    jButton = new JButton("Browse");
    jButton.setFont(new SortieFont());
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.addActionListener(this);
    jButton.setActionCommand("Browse_SORTIEOutFile");
    jTempPanel.add(jButton);
    jCellPanel.add(jTempPanel);
    jNorthPanel.add(jCellPanel);

    //*
    //Row 2, col 2: Components for collecting the path to the single-run batch
    //parameters file to write
    //*
    jCellPanel = new JPanel();
    jCellPanel.setLayout(new BoxLayout(jCellPanel, BoxLayout.PAGE_AXIS));
    jLabel = new JLabel(m_oBehavior.m_sHarvIntBatchSingleRunParamsFile.getDescriptor());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(new SortieFont());
    jCellPanel.add(jLabel);
    //Text box and browse button for collecting the path to the
    //executable harvest out file, packaged together in a panel so they'll be
    //side-by-side
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jBatchSingleRunParamsFileEdit.setFont(new SortieFont());
    m_jBatchSingleRunParamsFileEdit.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jBatchSingleRunParamsFileEdit.setPreferredSize(new Dimension(iWidth, 25));
    jTempPanel.add(m_jBatchSingleRunParamsFileEdit);
    jButton = new JButton("Browse");
    jButton.setFont(new SortieFont());
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.addActionListener(this);
    jButton.setActionCommand("Browse_BatchParamsSingleRunFile");
    jTempPanel.add(jButton);
    jCellPanel.add(jTempPanel);
    jNorthPanel.add(jCellPanel);

    //*
    //Row 3, col 1: Components for collecting the path to the executable harvest
    //out file
    //*
    jCellPanel = new JPanel();
    jCellPanel.setLayout(new BoxLayout(jCellPanel, BoxLayout.PAGE_AXIS));
    jLabel = new JLabel(m_oBehavior.m_sHarvIntExecHarvestOutFile.getDescriptor());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(new SortieFont());
    jCellPanel.add(jLabel);
    //Text box and browse button for collecting the path to the
    //executable harvest out file, packaged together in a panel so they'll be
    //side-by-side
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jExecutableHarvestOutFileEdit.setFont(new SortieFont());
    m_jExecutableHarvestOutFileEdit.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jExecutableHarvestOutFileEdit.setPreferredSize(new Dimension(iWidth, 25));
    jTempPanel.add(m_jExecutableHarvestOutFileEdit);
    jButton = new JButton("Browse");
    jButton.setFont(new SortieFont());
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.addActionListener(this);
    jButton.setActionCommand("Browse_ExecHarvestOutFile");
    jTempPanel.add(jButton);
    jCellPanel.add(jTempPanel);
    jNorthPanel.add(jCellPanel);

    //*
    //Row 3, col 2: Components for collecting arguments to pass to the
    //executable
    //*
    jCellPanel = new JPanel();
    jCellPanel.setLayout(new BoxLayout(jCellPanel, BoxLayout.PAGE_AXIS));
    jLabel = new JLabel(m_oBehavior.m_sHarvIntExecArgs.getDescriptor());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(new SortieFont());
    jCellPanel.add(jLabel);
    //Text box for collecting arguments to pass to the executable - doesn't
    //need a button but we'll set it up like col 1 so they line up correctly
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jExecutableArgs.setFont(new SortieFont());
    m_jExecutableArgs.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jExecutableArgs.setPreferredSize(new Dimension(iWidth, 25));
    jTempPanel.add(m_jExecutableArgs);
    jCellPanel.add(jTempPanel);
    jNorthPanel.add(jCellPanel);
    

    //******************************************
    // What's in the center-left of the window - scroll panes
    //******************************************
    JPanel jCenterWestPanel = new JPanel();
    jCenterWestPanel.setLayout(new BoxLayout(jCenterWestPanel, BoxLayout.PAGE_AXIS));
    
    //Components for collecting the harvest period
    jCellPanel = new JPanel();
    jCellPanel.setLayout(new BoxLayout(jCellPanel, BoxLayout.PAGE_AXIS));
    jLabel = new JLabel(m_oBehavior.m_iHarvIntHarvestPeriod.getDescriptor());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(new SortieFont());
    jCellPanel.add(jLabel);
    //Text box for collecting the harvest period - doesn't need a
    //button but we'll set it up like col 1 so they line up correctly
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jHarvestPeriod.setFont(new SortieFont());
    m_jHarvestPeriod.setPreferredSize(new Dimension(50, 25));
    jTempPanel.add(m_jHarvestPeriod);
    jCellPanel.add(jTempPanel);
    jCenterWestPanel.add(jCellPanel);
    
    //*
    // File columns
    //*
    jLabel = new JLabel(m_oBehavior.mp_sHarvIntFileColumns.getDescriptor());
    jLabel.setFont(new SortieFont());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jCenterWestPanel.add(jLabel);

    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jFileColumnsList = new JList<String>(m_jFileColumnsListModel);
    m_jFileColumnsList.setFont(new SortieFont());
    JScrollPane jScroller = new JScrollPane(m_jFileColumnsList);
    m_jFileColumnsList.setVisibleRowCount(5);
    jScroller.setPreferredSize(new Dimension(iWidth,
        (int) jScroller.getPreferredSize().
        getHeight()));
    jTempPanel.add(jScroller);

    //Panel containing Add/Remove buttons for the file columns list
    JPanel jButtonPanel = new JPanel();
    jButtonPanel.setLayout(new GridLayout(0, 1));

    jButton = new JButton("Add");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Add_Column");
    jButton.addActionListener(this);
    jButtonPanel.add(jButton);

    jButton = new JButton("Remove");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Remove_Column");
    jButton.addActionListener(this);
    jButtonPanel.add(jButton);
    jTempPanel.add(jButtonPanel);
    jCenterWestPanel.add(jTempPanel);

    jButton = new JButton("Up");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Column_Up");
    jButton.addActionListener(this);
    jButtonPanel.add(jButton);
    jTempPanel.add(jButtonPanel);
    jCenterWestPanel.add(jTempPanel);

    jButton = new JButton("Down");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Column_Down");
    jButton.addActionListener(this);
    jButtonPanel.add(jButton);
    jTempPanel.add(jButtonPanel);
    jCenterWestPanel.add(jTempPanel);



    //******************************************
    // Panel for optional tree updating - center east
    //******************************************
    JPanel jCenterEastPanel = new JPanel();
    jCenterEastPanel.setLayout(new BoxLayout(jCenterEastPanel, BoxLayout.PAGE_AXIS));
    //The title label
    jLabel = new JLabel("Tree Updating (optional)...");
    jLabel.setFont(new SortieFont(java.awt.Font.BOLD, 2));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jCenterEastPanel.add(jLabel);
    jCenterEastPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    //The grouping for collecting the path to the executable update out file
    jLabel = new JLabel(m_oBehavior.m_sHarvIntExecUpdateOutFile.getDescriptor());
    jLabel.setFont(new SortieFont());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jCenterEastPanel.add(jLabel);

    //Package the detailed output edit box and the browse button in a panel so
    //they'll be side-by-side
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jExecutableUpdateOutFileEdit.setFont(new SortieFont());
    m_jExecutableUpdateOutFileEdit.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jExecutableUpdateOutFileEdit.setPreferredSize(new Dimension(iWidth, 25));
    jTempPanel.add(m_jExecutableUpdateOutFileEdit);

    jButton = new JButton("Browse");
    jButton.setFont(new SortieFont());
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.addActionListener(this);
    jButton.setActionCommand("Browse_ExecUpdateOutFile");
    jTempPanel.add(jButton);
    jCenterEastPanel.add(jTempPanel);
    jCenterEastPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    // New data members
    jLabel = new JLabel("New tree data members to add:");
    jLabel.setFont(new SortieFont());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jCenterEastPanel.add(jLabel);

    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jNewTreeDataMembersList = new JList<String>(m_jNewTreeDataMembersListModel);
    m_jNewTreeDataMembersList.setFont(new SortieFont());
    m_jNewTreeDataMembersList.setVisibleRowCount(5);
    jScroller = new JScrollPane(m_jNewTreeDataMembersList);
    jScroller.setPreferredSize(new Dimension(iWidth,
        (int) jScroller.getPreferredSize().
        getHeight()));
    jTempPanel.add(jScroller);

    //Panel containing Add/Remove buttons for the new data members list
    jButtonPanel = new JPanel();
    jButtonPanel.setLayout(new GridLayout(0, 1));
    jLabel = new JLabel("New member:");
    jLabel.setFont(new SortieFont());
    jButtonPanel.add(jLabel);
    m_jNewTreeDataMemberEdit = new JTextField();
    m_jNewTreeDataMemberEdit.setFont(new SortieFont());
    jButtonPanel.add(m_jNewTreeDataMemberEdit);

    jButton = new JButton("Add");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Add_Member");
    jButton.addActionListener(this);
    jButtonPanel.add(jButton);

    jButton = new JButton("Remove");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Remove_Member");
    jButton.addActionListener(this);
    jButtonPanel.add(jButton);
    jTempPanel.add(jButtonPanel);
    jCenterEastPanel.add(jTempPanel);

    //****************************
    //Put it all together
    //****************************
    JPanel jContentPane = (JPanel)this.getContentPane();
    jContentPane.setLayout(new BorderLayout());
    jContentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
    jContentPane.add(jNorthPanel, BorderLayout.NORTH);
    JPanel jCenterPanel = new JPanel(new GridLayout(1, 2));
    jCenterPanel.add(jCenterWestPanel);
    jCenterPanel.add(jCenterEastPanel);
    jContentPane.add(jCenterPanel, BorderLayout.CENTER);
    jContentPane.add(new OKCancelButtonPanel(this,
        m_oBehavior.getGUIManager().getHelpBroker(),
        m_sHelpID, true),
        BorderLayout.SOUTH);
    this.setContentPane(jContentPane);
  }

  /**
   * Takes care of browsing
   * @param jText JTextField The file field to browse for
   */
  private void browse(JTextField jText) {
    ModelFileChooser jChooser = new ModelFileChooser();

    //If there's a value for the detailed output file, navigate to that directory
    File oDir = new File(jText.getText());
    if (oDir.getParentFile() != null && oDir.getParentFile().exists()) {
      jChooser.setCurrentDirectory(oDir.getParentFile());
    }
    jChooser.setSelectedFile(new File(oDir.getName()));
    int returnVal = jChooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      //User chose a file - put the filename in the edit box
      File oFile = jChooser.getSelectedFile();
      String sFileName = oFile.getAbsolutePath();
      jText.setText(sFileName);
    }
  }

  /**
   * Removes file columns from the list to add.
   * @throws ModelException if a column is not selected, or if the user tries
   * to remove a default column from the list.
   */
  protected void removeColumn() throws ModelException {
    List<String> p_oSelectedObjects = m_jFileColumnsList.getSelectedValuesList();
    int[] p_iSelected = m_jFileColumnsList.getSelectedIndices();
    int i, iDashedIndex = 0;

    //None selected?  Exit.
    if (p_iSelected.length == 0) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "No columns selected.  Nothing to remove."));
    }

    //Where is the dashed line?  Anything before that index cannot be removed.
    for (i = 0; i < m_jFileColumnsListModel.size(); i++) {
      if (m_jFileColumnsListModel.get(i).startsWith("--")) {
        iDashedIndex = i;
        break;
      }
    }

    //See if any of the selected indexes are default columns
    for (i = 0; i < p_iSelected.length; i++) {
      if (p_iSelected[i] <= iDashedIndex) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The default columns cannot be removed."));
      }
    }

    //Get each save string
    for (i = 0; i < p_iSelected.length; i++) {
      m_jFileColumnsListModel.removeElement(p_oSelectedObjects.get(i));
    }
  }

  /**
   * Moves a selected column up one position in the list.
   * @throws ModelException if the user has tried to disturb the default
   * columns.
   */
  protected void columnUp() throws ModelException {
    //Move the selected behavior up in the list - but it can't
    //go to a smaller group index
    int iSelectedIndex = m_jFileColumnsList.getSelectedIndex(), //selection
        iDashedIndex = 0, //index of the dashed line above which this cannot move
        iNewIndex = iSelectedIndex - 1, //proposed new position of item
        i;

    //Where is the dashed line?
    for (i = 0; i < m_jFileColumnsListModel.size(); i++) {
      if (((String)m_jFileColumnsListModel.get(i)).startsWith("--")) {
        iDashedIndex = i;
        break;
      }
    }

    //If nothing selected or the move would affect default columns,
    //warn and exit
    if (-1 == iSelectedIndex) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "Nothing selected to move."));
    }
    if (iSelectedIndex <= iDashedIndex || iNewIndex <= iDashedIndex) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The default columns cannot be changed."));
    }

    //Swap the object from old position to new
    String sItem = m_jFileColumnsListModel.getElementAt(iSelectedIndex);
    m_jFileColumnsListModel.insertElementAt(sItem, iNewIndex);
    m_jFileColumnsListModel.removeElementAt(iSelectedIndex + 1);

    //Keep the same item selected
    m_jFileColumnsList.setSelectedIndex(iNewIndex);
  }

  /**
   * Moves a selected column down one position in the list.
   * @throws ModelException if the request is to move a default column.
   */
  protected void columnDown() throws ModelException {
    //Move the selected behavior up in the list - but it can't
    //go to a smaller group index
    int iSelectedIndex = m_jFileColumnsList.getSelectedIndex(), //selection
        iDashedIndex = 0, //index of the dashed line above which this cannot move
        iNewIndex = iSelectedIndex + 2, //proposed new position of item
        i;

    //Where is the dashed line?
    for (i = 0; i < m_jFileColumnsListModel.size(); i++) {
      if (m_jFileColumnsListModel.get(i).startsWith("--")) {
        iDashedIndex = i;
        break;
      }
    }

    //If nothing selected or the move would affect the default columns, warn
    //and exit
    if (-1 == iSelectedIndex || (m_jFileColumnsListModel.getSize() - 1) == iSelectedIndex) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "Nothing selected to move."));
    }
    if (iSelectedIndex <= iDashedIndex) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The default columns cannot be changed."));
    }

    //Swap the object from old position to new
    String sItem = m_jFileColumnsListModel.getElementAt(iSelectedIndex);
    m_jFileColumnsListModel.insertElementAt(sItem, iNewIndex);
    m_jFileColumnsListModel.removeElementAt(iSelectedIndex);

    //Keep the same item selected
    m_jFileColumnsList.setSelectedIndex(iNewIndex - 1);
  }

  /**
   * Adds a new data member, to be owned by this behavior.
   * @throws ModelException if:
   * <ul>
   * <li>The data member is longer than 9 characters</li>
   * <li>It has the same name as an existing data member</li>
   * <li>It has parentheses in the name</li>
   * </ul>
   */
  protected void addDataMember() throws ModelException {
    String sNewMember = m_jNewTreeDataMemberEdit.getText().trim();
    int iMaxLabelSize = 9, i;

    if (sNewMember.length() == 0) {
      m_jNewTreeDataMemberEdit.setText("");
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "No new data member name specified."));
    }

    //Make sure the name isn't too long
    if (sNewMember.length() > iMaxLabelSize) {
      m_jNewTreeDataMemberEdit.setText("");
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Data member names cannot be longer than " +
              iMaxLabelSize + " characters."));
    }

    //Make sure the name doesn't already exist
    for (i = 0; i < mp_sAllExistingDataMembers.size(); i++) {
      if (sNewMember.equals((String)mp_sAllExistingDataMembers.get(i))) {
        m_jNewTreeDataMemberEdit.setText("");
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The name \"" +
            sNewMember + "\" is already being used for a data member name in SORTIE."));
      }
    }

    //Make sure the name hasn't already been specified as a new member to
    //add
    for (i = 0; i < m_jNewTreeDataMembersListModel.size(); i++) {
      if (sNewMember.equals((String)m_jNewTreeDataMembersListModel.get(i))) {
        m_jNewTreeDataMemberEdit.setText("");
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "This data member is already on the list of new data members."));
      }
    }

    //Make sure the name does not contain any parentheses
    if (sNewMember.indexOf('(') >= 0 ||
        sNewMember.indexOf(')') >= 0) {
      m_jNewTreeDataMemberEdit.setText("");
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The data member name cannot contain parentheses."));
    }

    //Add it
    m_jNewTreeDataMembersListModel.addElement(sNewMember);
    m_jNewTreeDataMemberEdit.setText("");
  }

  /**
   * Removes a new data member.
   * @throws ModelException if there is not a data member selected.
   */
  protected void removeDataMember() throws ModelException {
    List<String> p_oSelected = m_jNewTreeDataMembersList.getSelectedValuesList();
    int i;

    //None selected?  Exit.
    if (p_oSelected.size() == 0) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "No data members selected.  Nothing to remove."));
    }

    //Get each save string
    for (i = 0; i < p_oSelected.size(); i++) {
      m_jNewTreeDataMembersListModel.removeElement(p_oSelected.get(i));
    }
  }

  /**
   * What happens when the OK button is clicked. The data is validated and
   * passed to the DisturbanceBehaviors object as needed.
   * @return true if everything is correct; false if the process has been
   * interrupted and the window should not close.
   * @throws ModelException if:
   * <ul>
   * <li>The path is missing to the executable, the harvest out file, or the
   * harvest in file</li>
   * <li>A warning is issued if there is an update file listed but no new
   * tree data members</li>
   * <li>The harvest period is not specified, is not a number, or is
   * negative</li>
   * <li>Either the batch parameters file or the single run batch parameters
   * file is present without the other</li>
   * <li>The batch parameters file is specified but does not exist</li>
   * </ul>
   */
  protected boolean ok() throws ModelException {
    String sData;
    int i;

    //*******************
    // Data validation
    //*******************
    //Make sure the path for the harvest executable has been specified
    sData = m_jExecutablePathEdit.getText().trim();
    if (sData.length() == 0) {
      throw(new ModelException(ErrorGUI.BAD_DATA,
          "JAVA",
          "You must specify the path to the harvest executable."));
    }

    //Make sure the harvest executable exists
    //No - what if you want to make this for another computer?
    /*if (false == new File(sData).exists()) {
      throw(new ModelException(ErrorGUI.BAD_DATA,
          "JAVA",
          "I cannot find the harvest executable."));
    }*/

    //Make sure the file that SORTIE will write has been specified
    if (m_jSORTIEOutFileEdit.getText().trim().length() == 0) {
      throw(new ModelException(ErrorGUI.BAD_DATA,
          "JAVA",
          "You must specify the path to the tree file that SORTIE will write."));
    }

    //Make sure the file that the harvest executable will write has been
    //specified
    if (m_jExecutableHarvestOutFileEdit.getText().trim().length() == 0) {
      throw(new ModelException(ErrorGUI.BAD_DATA,
          "JAVA",
          "You must specify the path to the tree harvest file that the executable will write."));
    }

    //Make sure the harvest period has been specified
    if (m_jHarvestPeriod.getText().trim().length() == 0) {
      throw(new ModelException(ErrorGUI.BAD_DATA,
          "JAVA",
          "You must specify how often to harvest."));
    }

    //Make sure the harvest period is a positive integer
    try {
      Integer iTest = Integer.valueOf(m_jHarvestPeriod.getText().trim());
      if (iTest.intValue() < 0) {
        throw(new ModelException(ErrorGUI.BAD_DATA,
            "JAVA",
            "How often to harvest must be a positive integer value."));
      }
    } catch (java.lang.NumberFormatException e) {
      throw(new ModelException(ErrorGUI.BAD_DATA,
          "JAVA",
          "How often to harvest must be a positive integer value."));
    }

    //Make sure that, if either the batch parameters file or the single-run
    //batch parameters file has been specified, the other has been also
    if ((m_jBatchParamsFileEdit.getText().length() > 0) !=
        (m_jBatchSingleRunParamsFileEdit.getText().length() > 0)) {
      throw(new ModelException(ErrorGUI.BAD_DATA,
          "JAVA",
          "For SORTIE to process batch parameters, both "+
              "the parameters file for batch run and the " +
              "single-run parameters file for batch run must "+
          "be specified."));
    }

    //Make sure that the batch parameters file exists
    //No - what if you want to make this for another computer?
    /*if (m_jBatchParamsFileEdit.getText().trim().length() > 0 &&
        new File(m_jBatchParamsFileEdit.getText().trim()).exists() == false) {
      throw(new ModelException(ErrorGUI.BAD_DATA,
          "JAVA",
          "The parameters file for batch run does not exist."));
    }*/

    //If there is an update file specified but no new data members, issue a
    //warning (in the form of a dialog box instead of an error) but let the
    //assignment proceed if the user wishes
    if (m_jExecutableUpdateOutFileEdit.getText().trim().length() > 0 &&
        m_jNewTreeDataMembersListModel.size() == 0)  {
      int iReturn = JOptionPane.showConfirmDialog(this, "Because there are no "+
          "new tree data members being added, the tree update file will "+
          "be ignored. Continue?", "SORTIE", JOptionPane.YES_NO_OPTION);
      if (iReturn == JOptionPane.NO_OPTION) return false;
    }

    //If there are new tree data members specified but no update file, issue a
    //warning (in the form of a dialog box instead of an error) but let the
    //assignment proceed if the user wishes
    if (m_jExecutableUpdateOutFileEdit.getText().trim().length() == 0 &&
        m_jNewTreeDataMembersListModel.size() > 0)  {
      int iReturn = JOptionPane.showConfirmDialog(this, "Because there is no "+
          "tree update file specified, the new tree data members will not"+
          " be added. Continue?", "SORTIE", JOptionPane.YES_NO_OPTION);
      if (iReturn == JOptionPane.NO_OPTION) return false;
    }

    //*******************
    // Data assignment
    //*******************

    // Executable path
    //Replace all single slashes with float slashes
    String sPath = m_jExecutablePathEdit.getText().trim();
    //First - float all slashes - yes, four slashes means one slash! Blame
    //regular expressions syntax.
    //sPath = sPath.replaceAll("\\\\", "\\\\\\\\");
    //If that caused any quadruple slashes, replace them
    //sPath = sPath.replaceAll("\\\\\\\\\\\\\\\\", "\\\\\\\\");
    m_oBehavior.m_sHarvIntExecutable.setValue(sPath);

    // Filename that SORTIE is to write
    sPath = m_jSORTIEOutFileEdit.getText().trim();
    //sPath = sPath.replaceAll("\\\\", "\\\\\\\\");
    //sPath = sPath.replaceAll("\\\\\\\\\\\\\\\\", "\\\\\\\\");
    m_oBehavior.m_sHarvIntSORTIEOutFile.setValue(sPath);

    // Executable's harvest reply file
    sPath = m_jExecutableHarvestOutFileEdit.getText().trim();
    //sPath = sPath.replaceAll("\\\\", "\\\\\\\\");
    //sPath = sPath.replaceAll("\\\\\\\\\\\\\\\\", "\\\\\\\\");
    m_oBehavior.m_sHarvIntExecHarvestOutFile.setValue(sPath);

    // Harvest period
    m_oBehavior.m_iHarvIntHarvestPeriod.setValue(Integer.valueOf(m_jHarvestPeriod.getText().trim()).intValue());

    // Additional file columns
    m_oBehavior.mp_sHarvIntFileColumns.getValue().clear();
    //Start by finding the dashed line
    int iDashedIndex = 0;
    for (i = 0; i < m_jFileColumnsListModel.size(); i++) {
      if (((String)m_jFileColumnsListModel.get(i)).startsWith("--")) {
        iDashedIndex = i+1;
        break;
      }
    }
    for (i = iDashedIndex; i < m_jFileColumnsListModel.size(); i++) {
      m_oBehavior.mp_sHarvIntFileColumns.getValue().add(m_jFileColumnsListModel.get(i));
    }

    // Argument strings to pass to the executable
    m_oBehavior.m_sHarvIntExecArgs.setValue(m_jExecutableArgs.getText().trim());

    // Batch parameters file
    sPath = m_jBatchParamsFileEdit.getText().trim();
    //sPath = sPath.replaceAll("\\\\", "\\\\\\\\");
    //sPath = sPath.replaceAll("\\\\\\\\\\\\\\\\", "\\\\\\\\");
    m_oBehavior.m_sHarvIntBatchParamsFile.setValue(sPath);

    // File for a single run of batch parameters
    m_oBehavior.m_sHarvIntBatchSingleRunParamsFile.setValue(m_jBatchSingleRunParamsFileEdit.getText().trim());

    // Executable's update reply file - only if there are new tree data members
    if (m_jNewTreeDataMembersListModel.size() > 0) {
      m_oBehavior.m_sHarvIntExecUpdateOutFile.setValue(m_jExecutableUpdateOutFileEdit.getText().trim());
    } else {
      m_oBehavior.m_sHarvIntExecUpdateOutFile.setValue("");
    }

    // New tree data members
    //If there is no reply file indicated, do nothing even if new data members
    //have been added
    if (m_jExecutableUpdateOutFileEdit.getText().trim().length() > 0) {
      m_oBehavior.mp_sHarvIntNewTreeDataMembers.clear();
      for (i = 0; i < m_jNewTreeDataMembersListModel.size(); i++) {
        m_oBehavior.mp_sHarvIntNewTreeDataMembers.add((String)m_jNewTreeDataMembersListModel.get(i));
      }
    } else {
      m_oBehavior.mp_sHarvIntNewTreeDataMembers.clear();
    }

    return true;
  }


  /**
   * Dialog for choosing new file columns.  This depends on there being some
   * species/type combos for the behavior in order to work correctly.
   * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
   * <p>Company: Cary Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   */
  protected class FileColumnsChooser
  extends JDialog
  implements ActionListener {



    /** List displaying potential file columns */
    protected JList<String> m_jPotentialFileColumnsList;

    /** List model for displaying potential file columns */
    protected DefaultListModel<String> m_jPotentialFileColumnsListModel = new
        DefaultListModel<String>();

    /**
     * Constructor.  Updates the list of potential columns, builds the GUI,
     * and displays.
     * @param jParent Parent window
     * @throws ModelException passed through from called functions
     */
    public FileColumnsChooser(JDialog jParent) throws ModelException {
      super(jParent, "Add File Columns", true);

      //**********************************************************************
      // Get list of data members
      //**********************************************************************

      //First, get a list of all data members to which all species/types apply
      //To make it easier to check, build an array of booleans for species and
      //type
      BehaviorTypeBase[] p_oBehaviorGroups = m_oBehavior.getGUIManager().getAllObjects();
      TreePopulation oPop = m_oBehavior.getGUIManager().getTreePopulation();
      ArrayList<String> p_sFoundList = new ArrayList<String>(0); //data members we've found
      ArrayList<boolean[][]> p_bAppliesMatrix = new ArrayList<boolean[][]>(0); //boolean matrix for each
      int i, j, k,
      iBehGroup, iBeh, iDM, //loop counters
      iNumSpecies = oPop.getNumberOfSpecies(),
      iNumTypes = TreePopulation.getNumberOfTypes();
      boolean[][] p_bBehaviorTrees = new boolean[iNumSpecies][iNumTypes],
          p_bAppliesTo; //what one data member applies to
      boolean bFound;

      for (i = 0; i < iNumSpecies; i++) {
        for (j = 0; j < iNumTypes; j++) {
          p_bBehaviorTrees[i][j] = false;
        }
      }
      //Make a copy of the harvest interface behavior's combos
      for (i = 0; i < m_oBehavior.getNumberOfCombos(); i++) {
        SpeciesTypeCombo oCombo = m_oBehavior.getSpeciesTypeCombo(i);
        p_bBehaviorTrees[oCombo.getSpecies()][oCombo.getType()] = true;
      }

      //Now, go through all data members and find those that apply to all
      //species and types.  To do this, we'll build a boolean array for each
      //one we find, just like the one we did for the behavior.  Data members
      //can be spread out over multiple behaviors.

      //Start with the tree population - the only one we're not already using
      //is "Age" for snags
      p_sFoundList.add("Age");
      p_bAppliesTo = new boolean[iNumSpecies][iNumTypes];
      for (i = 0; i < iNumSpecies; i++) {
        for (j = 0; j < iNumTypes; j++) {
          p_bAppliesTo[i][j] = false;
        }
      }
      for (i = 0; i < iNumSpecies; i++) {
        p_bAppliesTo[i][TreePopulation.SNAG] = true;
      }
      p_bAppliesMatrix.add(p_bAppliesTo);

      for (iBehGroup = 0; iBehGroup < p_oBehaviorGroups.length; iBehGroup++) {
        ArrayList<Behavior> p_oBehaviors = p_oBehaviorGroups[iBehGroup].getAllInstantiatedBehaviors();
        if (p_oBehaviors != null) {
          for (iBeh = 0; iBeh < p_oBehaviors.size(); iBeh++) {

            //Only collect data member data for enabled behaviors
            for (iDM = 0;
                iDM < p_oBehaviors.get(iBeh).getNumberNewDataMembers(); iDM++) {
              DataMember oDataMember = p_oBehaviors.get(iBeh).getNewTreeDataMember(iDM);

              //Have we already seen this data member?
              bFound = false;
              for (i = 0; i < p_sFoundList.size(); i++) {
                if (oDataMember.getCodeName().equals( (String) p_sFoundList.
                    get(i))) {
                  bFound = true;
                  break;
                }
              }
              if (bFound) {

                //Add those tree species/type combos to which it applies
                p_bAppliesTo = (boolean[][]) p_bAppliesMatrix.get(i);

                for (j = 0; j < p_oBehaviors.get(iBeh).getNumberOfCombos(); j++) {
                  SpeciesTypeCombo oCombo = p_oBehaviors.get(iBeh).
                      getSpeciesTypeCombo(j);
                  p_bAppliesTo[oCombo.getSpecies()][oCombo.getType()] = true;
                }

                p_bAppliesMatrix.remove(i);
                p_bAppliesMatrix.add(i, p_bAppliesTo);

              }
              else {
                //This is a newly encountered data member
                p_sFoundList.add(oDataMember.getCodeName());

                //Create a new boolean matrix and add it as well
                p_bAppliesTo = new boolean[iNumSpecies][iNumTypes];
                for (j = 0; j < iNumSpecies; j++) {
                  for (k = 0; k < iNumTypes; k++) {
                    p_bAppliesTo[j][k] = false;
                  }
                }
                for (j = 0; j < p_oBehaviors.get(iBeh).getNumberOfCombos(); j++) {
                  SpeciesTypeCombo oCombo = p_oBehaviors.get(iBeh).
                      getSpeciesTypeCombo(j);
                  p_bAppliesTo[oCombo.getSpecies()][oCombo.getType()] = true;
                }

                p_bAppliesMatrix.add(p_bAppliesTo);
              }

            }
          }
        }
      }

      //Add all user-created data members to the file columns list
      for (i = 0; i < m_jNewTreeDataMembersListModel.size(); i++) {
        m_jPotentialFileColumnsListModel.addElement(
            m_jNewTreeDataMembersListModel.get(i));
      }

      //Now p_sFoundList is a unique list of all the data members for all
      //enabled behaviors in this run; and p_bAppliesMatrix is a list of
      //matrixes for each with those species/types to which they apply.
      //Compare the matrixes of those data members with the matrix for our
      //behavior, and choose the right data members
      boolean bOK;
      for (i = 0; i < p_bAppliesMatrix.size(); i++) {
        bOK = true;
        p_bAppliesTo = (boolean[][]) p_bAppliesMatrix.get(i);
        for (j = 0; j < iNumSpecies; j++) {
          for (k = 0; k < iNumTypes; k++) {
            if (true == p_bBehaviorTrees[j][k] && false == p_bAppliesTo[j][k]) {
              bOK = false;
              break;
            }
          }
        }

        if (bOK) {
          m_jPotentialFileColumnsListModel.addElement( (String) p_sFoundList.
              get(i));
        }
      }

      //**********************************************************************
      // Build GUI
      //**********************************************************************
      if (m_jPotentialFileColumnsListModel.size() == 0) {
        JOptionPane.showMessageDialog(jParent, "No data available to add.");
        return;
      }
      JPanel jContentPane = new JPanel(new BorderLayout());

      //Label
      JLabel jLabel = new JLabel("Select new file columns:");
      jLabel.setFont(new SortieFont());
      jContentPane.add(jLabel, BorderLayout.NORTH);
      m_jPotentialFileColumnsList = new JList<String>(m_jPotentialFileColumnsListModel);
      m_jPotentialFileColumnsList.setFont(new SortieFont());
      JScrollPane jScroller = new JScrollPane(m_jPotentialFileColumnsList);
      jContentPane.add(jScroller, BorderLayout.CENTER);
      jContentPane.add(new OKCancelButtonPanel(this, m_oBehavior.getGUIManager().getHelpBroker(),
          m_sHelpID, true),
          BorderLayout.SOUTH);
      this.setContentPane(jContentPane);
      this.pack();
      this.setLocationRelativeTo(null);
      this.setVisible(true);
    }

    /**
     * Takes care of button pushes.
     * @param e ActionEvent What event triggered this.
     */
    public void actionPerformed(java.awt.event.ActionEvent e) {
      String sCommand = e.getActionCommand();
      if (sCommand.equals("Cancel")) {
        this.setVisible(false);
        this.dispose();
      }
      else if (sCommand.equals("OK")) {

        //Add any selected columns to the list
        List<String> p_sSelected = m_jPotentialFileColumnsList.getSelectedValuesList();
        int j;
        boolean bFound;
        for (String sSelected : p_sSelected) {

          //Make sure it isn't already on the list
          bFound = false;
          for (j = 0; j < m_jFileColumnsListModel.size(); j++) {
            if (sSelected.equals(m_jFileColumnsListModel.get(j))) {
              bFound = true;
              break;
            }
          }

          if (false == bFound) {
            m_jFileColumnsListModel.addElement(sSelected);
          }
        }

        this.setVisible(false);
        this.dispose();
      }
    }
  }
}
