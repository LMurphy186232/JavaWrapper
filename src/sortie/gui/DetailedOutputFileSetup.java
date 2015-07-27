package sortie.gui;

import javax.swing.*;

import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.output.DetailedOutput;
import sortie.data.simpletypes.DetailedGridSettings;
import sortie.data.simpletypes.DetailedTreeSettings;
import sortie.data.simpletypes.ModelException;
import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Window that the user uses to set up detailed output options.
 * <p>Copyright: Copyright (c) Charlies D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class DetailedOutputFileSetup
    extends JDialog
    implements ActionListener {

  /**Output behaviors object for passing to child windows*/
  DetailedOutput m_oOutputBehaviors;

  /**Main window*/
  MainWindow m_oWindow;

  /**Field capturing the detailed output filename*/
  JTextField m_jDetailedOutputFileNameEdit = new JTextField();

  /**Label displaying what's being saved for trees*/
  JLabel m_jLiveTreeDataLabel = new JLabel();

  /**Label displaying what's being saved for dead trees*/
  JLabel m_jDeadTreeDataLabel = new JLabel();

  /**Label displaying what's being saved for grids*/
  JLabel m_jGridDataLabel = new JLabel();

  /**ID of help topic for this window*/
  private String m_sHelpID = "windows.detailed_output_setup";

  /**
   * Constructor
   * @param oParent Parent window in which to display this dialog
   * @param oOutputBehaviors OutputBehaviors object with which to exchange
   * data
   * @param oWindow MainWindow object
   */
  public DetailedOutputFileSetup(JDialog oParent,
                                OutputBehaviors oOutputBehaviors,
                                MainWindow oWindow) {
    super(oParent, "Setup Detailed Output File", true);
    m_oOutputBehaviors = oOutputBehaviors.getDetailedOutput();
    m_oWindow = oWindow;

    //Help ID
    oWindow.m_oHelpBroker.enableHelpKey(this.getRootPane(), m_sHelpID, null);

    createGUI();

  }

  /**
   * Creates the window.
   * @throws java.lang.Exception if anything goes wrong with window-building.
   */
  private void createGUI() {

    //Add the window listener that will update the saved data labels
    this.addWindowListener(new DetailedOutputFileSetup_this_windowAdapter(this));

    //Panel holding all the controls
    JPanel jComponentPanel = new JPanel();
    jComponentPanel.setLayout(new javax.swing.BoxLayout(jComponentPanel,
        javax.swing.BoxLayout.PAGE_AXIS));

    //The title label
    JLabel jLabel = new JLabel("Set Up Detailed Output File...");
    jLabel.setFont(new SortieFont(java.awt.Font.BOLD, 2));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(jLabel);
    jComponentPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 10)));

    //The grouping for collecting the file name
    jLabel = new JLabel("Output File Name:");
    jLabel.setFont(new SortieFont());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(jLabel);

    //Package the detailed output edit box and the browse button in a panel so
    //they'll be side-by-side
    JPanel jTempPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.
        LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jDetailedOutputFileNameEdit.setFont(new SortieFont());
    m_jDetailedOutputFileNameEdit.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jDetailedOutputFileNameEdit.setPreferredSize(new Dimension(300, 25));
    m_jDetailedOutputFileNameEdit.setText(m_oOutputBehaviors.getDetailedOutputFileName());
    jTempPanel.add(m_jDetailedOutputFileNameEdit);

    JButton jButton = new JButton("Browse");
    jButton.setFont(new SortieFont());
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.addActionListener(this);
    jButton.setActionCommand("Browse");
    jTempPanel.add(jButton);

    jComponentPanel.add(jTempPanel);
    jComponentPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));

    //Save everything button
    jButton = new JButton("Save everything");
    jButton.setFont(new SortieFont());
    jButton.setToolTipText("Save all data");
    jButton.setMinimumSize(new Dimension(160, 30));
    jButton.setPreferredSize(new Dimension(160, 30));
    jButton.setMaximumSize(new Dimension(160, 30));
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.setActionCommand("SaveEverything");
    jButton.addActionListener(this);
    jComponentPanel.add(jButton);
    jComponentPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));

    //Clear everything button
    jButton = new JButton("Clear everything");
    jButton.setFont(new SortieFont());
    jButton.setToolTipText("Delete all detailed output settings");
    jButton.setMinimumSize(new Dimension(160, 30));
    jButton.setPreferredSize(new Dimension(160, 30));
    jButton.setMaximumSize(new Dimension(160, 30));
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.setActionCommand("ClearEverything");
    jButton.addActionListener(this);
    jComponentPanel.add(jButton);
    jComponentPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));

    //Label for advanced options
    jLabel = new JLabel("Advanced options:");
    jLabel.setFont(new SortieFont());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(jLabel);
    jComponentPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));

    //Button for tree settings
    jButton = new JButton("Live Trees");
    jButton.setFont(new SortieFont());
    jButton.setToolTipText("Open window to edit tree settings");
    jButton.setMinimumSize(new Dimension(160, 30));
    jButton.setPreferredSize(new Dimension(160, 30));
    jButton.setMaximumSize(new Dimension(160, 30));
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.setActionCommand("Trees");
    jButton.addActionListener(this);
    jComponentPanel.add(jButton);
    jComponentPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));

    //Label with info on tree savings
    m_jLiveTreeDataLabel.setFont(new SortieFont());
    m_jLiveTreeDataLabel.setText("Currently saving no data");
    m_jLiveTreeDataLabel.setPreferredSize(new Dimension(250,
        (int) m_jLiveTreeDataLabel.getPreferredSize().getHeight()));
    m_jLiveTreeDataLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(m_jLiveTreeDataLabel);
    jComponentPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));

    //Button for grid settings
    jButton = new JButton("Grid Data Layers");
    jButton.setFont(new SortieFont());
    jButton.setToolTipText("Open window to edit grid settings");
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.setMinimumSize(new Dimension(160, 30));
    jButton.setPreferredSize(new Dimension(160, 30));
    jButton.setMaximumSize(new Dimension(160, 30));
    jButton.addActionListener(this);
    jButton.setActionCommand("Grids");
    jComponentPanel.add(jButton);
    jComponentPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));

    //Label with info on grid savings
    m_jGridDataLabel.setFont(new SortieFont());
    m_jGridDataLabel.setText("Currently saving no data");
    m_jGridDataLabel.setPreferredSize(new Dimension(250,
        (int) m_jGridDataLabel.getPreferredSize().getHeight()));
    m_jGridDataLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(m_jGridDataLabel);
    
    //Button for dead tree settings
    jButton = new JButton("Dead Trees");
    jButton.setFont(new SortieFont());
    jButton.setToolTipText("Open window to edit tree settings");
    jButton.setMinimumSize(new Dimension(160, 30));
    jButton.setPreferredSize(new Dimension(160, 30));
    jButton.setMaximumSize(new Dimension(160, 30));
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.setActionCommand("DeadTrees");
    jButton.addActionListener(this);
    jComponentPanel.add(jButton);
    jComponentPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));
    
    //Label with info on dead tree savings
    m_jDeadTreeDataLabel.setFont(new SortieFont());
    m_jDeadTreeDataLabel.setText("Currently saving no data");
    m_jDeadTreeDataLabel.setPreferredSize(new Dimension(250,
        (int) m_jDeadTreeDataLabel.getPreferredSize().getHeight()));
    m_jDeadTreeDataLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(m_jDeadTreeDataLabel);
    jComponentPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));

    //Button for editing subplot data
    jButton = new JButton("Set up subplots...");
    jButton.setFont(new SortieFont());
    jButton.setToolTipText("Open window to edit subplot settings");
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.setMinimumSize(new Dimension(160, 30));
    jButton.setPreferredSize(new Dimension(160, 30));
    jButton.setMaximumSize(new Dimension(160, 30));
    jButton.addActionListener(this);
    jButton.setActionCommand("EditSubplots");
    jComponentPanel.add(jButton);
    jComponentPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));


    jComponentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    //Put it all together
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(jComponentPanel, BorderLayout.CENTER);
    this.getContentPane().add(new OKCancelButtonPanel(this,
        m_oWindow.m_oHelpBroker, m_sHelpID, false), BorderLayout.SOUTH);
  }

  /**
   * Performs the actions of the window.
   * @param oEvent What event to respond to.
   */
  public void actionPerformed(ActionEvent oEvent) {
    try {
      if (oEvent.getActionCommand().equals("OK")) {
        if (m_oOutputBehaviors.getNumberOfDetailedLiveTreeSettings() == 0 &
            m_oOutputBehaviors.getNumberOfDetailedDeadTreeSettings() == 0 &&
            m_oOutputBehaviors.getNumberOfDetailedGridSettings() == 0 &&
            m_jDetailedOutputFileNameEdit.getText().trim().length() == 0) {
          m_oOutputBehaviors.setDetailedOutputFileName("");
          this.setVisible(false);
          this.dispose();
          return;
        }
        
        //Throw an error if there's detailed output settings but no filename
        if ( (m_oOutputBehaviors.getNumberOfDetailedLiveTreeSettings() > 0 ||
              m_oOutputBehaviors.getNumberOfDetailedDeadTreeSettings() > 0 ||
              m_oOutputBehaviors.getNumberOfDetailedGridSettings() > 0) &&
            m_jDetailedOutputFileNameEdit.getText().equals("")) {
          JOptionPane.showMessageDialog(this,
                                        "Please enter a detailed output filename.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        //Warn if there is a filename but no settings
        if (m_jDetailedOutputFileNameEdit.getText().trim().length() > 0 &&
            m_oOutputBehaviors.getNumberOfDetailedLiveTreeSettings() == 0 &&
            m_oOutputBehaviors.getNumberOfDetailedDeadTreeSettings() == 0 &&
            m_oOutputBehaviors.getNumberOfDetailedGridSettings() == 0) {
          if (JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(this,
              "You have entered a detailed output file name but no detailed output settings." +
              "\nNo detailed output file will be saved.  Contine?", "SORTIE",
              JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)) {
            return;
          }
        }                

        //Make sure that the detailed output file is not the same name as the
        //parameter file
        String sFileName = new File(m_jDetailedOutputFileNameEdit.getText()).
            getName();
        String sParamName = new File(m_oWindow.getDataManager().
                                     getParameterFileName()).getName();
        //Trim extensions
        int iExPos = sFileName.lastIndexOf(".");
        if (iExPos != -1) {
          sFileName = sFileName.substring(0, iExPos);
        }
        iExPos = sParamName.lastIndexOf(".");
        if (iExPos != -1) {
          sParamName = sParamName.substring(0, iExPos);
        }
        if (sFileName.equals(sParamName)) {
          int iReturnVal = JOptionPane.showConfirmDialog(this,
              "The detailed output file has the same name as the parameter file.\n" +
              "If they are in the same directory, the detailed output file will not work right.\n" +
              "Would you like to change the detailed output file name?", "Model",
              JOptionPane.YES_NO_OPTION);
          if (iReturnVal == JOptionPane.YES_OPTION) {
            return;
          }
        }
        File jParent = new File(m_jDetailedOutputFileNameEdit.getText()).getParentFile();
        if (jParent != null && !(jParent.exists())) {
          int response = JOptionPane.showConfirmDialog(this, 
              "The path for the detailed output file does not exist on this " +
              "machine. Running this file on this machine will cause an " +
              "error. Continue?", "SORTIE", JOptionPane.YES_NO_OPTION);
          if (response == JOptionPane.NO_OPTION) return;
        }

        //Set the detailed output filename
        m_oOutputBehaviors.setDetailedOutputFileName(m_jDetailedOutputFileNameEdit.
                                              getText());

        //Close window
        this.setVisible(false);
        this.dispose();
      }
      else if (oEvent.getActionCommand().equals("Cancel")) {
        //Close window
        this.setVisible(false);
        this.dispose();
      }
      else if (oEvent.getActionCommand().equals("Trees")) {
        //Open tree options setup window
        DetailedOutputTreeSetup oTreeFrame = new DetailedOutputTreeSetup(this,
            m_oOutputBehaviors);
        oTreeFrame.pack();
        oTreeFrame.setLocationRelativeTo(null);
        oTreeFrame.setVisible(true);
      }
      else if (oEvent.getActionCommand().equals("DeadTrees")) {
        //Open tree options setup window
        DetailedOutputDeadTreeSetup oTreeFrame = new DetailedOutputDeadTreeSetup(this,
            m_oOutputBehaviors);
        oTreeFrame.pack();
        oTreeFrame.setLocationRelativeTo(null);
        oTreeFrame.setVisible(true);
      }
      else if (oEvent.getActionCommand().equals("Grids")) {
        //Open grid options setup window
        DetailedOutputGridSetup oGridFrame = new DetailedOutputGridSetup(this,
            m_oOutputBehaviors);
        oGridFrame.pack();
        oGridFrame.setLocationRelativeTo(null);
        oGridFrame.setVisible(true);
      }
      else if (oEvent.getActionCommand().equals("Browse")) {
        ModelFileChooser jChooser = new ModelFileChooser();

        //If there's a value for the detailed output file, navigate to that directory
        File oDir = new File(m_jDetailedOutputFileNameEdit.getText());
        if (oDir.getParentFile() != null && oDir.getParentFile().exists()) {
          jChooser.setCurrentDirectory(oDir.getParentFile());
        }

        jChooser.setSelectedFile(new File(oDir.getName()));
        jChooser.setFileFilter(new DetailedOutputFileFilter());
        int returnVal = jChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          //User chose a file - put the filename in the edit box
          File oFile = jChooser.getSelectedFile();
          String sFileName = oFile.getAbsolutePath();

          //Does this file already exist?
          if (oFile.exists()) {
            returnVal = JOptionPane.showConfirmDialog(this,
                                               "Do you wish to overwrite the existing file?",
                                               "Model",
                                               JOptionPane.YES_NO_OPTION);
            if (returnVal == JOptionPane.YES_OPTION) {
              m_jDetailedOutputFileNameEdit.setText(sFileName);
            }
          }
          else {
            //File didn't exist
            m_jDetailedOutputFileNameEdit.setText(sFileName);
          }
        }
      }
      else if (oEvent.getActionCommand().equals("SaveEverything")) {
        DetailedOutputTreeSetup oTreeFrame = new DetailedOutputTreeSetup(this,
            m_oOutputBehaviors);
        DetailedOutputDeadTreeSetup oDeadTreeFrame = new DetailedOutputDeadTreeSetup(this,
            m_oOutputBehaviors);
        DetailedOutputGridSetup oGridFrame = new DetailedOutputGridSetup(this,
            m_oOutputBehaviors);
        oTreeFrame.saveAll();
        oDeadTreeFrame.saveAll();
        oGridFrame.saveAll();
        this_windowActivated(null);

      }
      else if (oEvent.getActionCommand().equals("ClearEverything")) {
        m_oOutputBehaviors.clearDetailedGridSettings();
        m_oOutputBehaviors.clearDetailedLiveTreeSettings();
        m_oOutputBehaviors.clearDetailedDeadTreeSettings();
        this_windowActivated(null);
      }
      else if (oEvent.getActionCommand().equals("EditSubplots")) {

        SubplotEdit oWindow = new SubplotEdit(this, 
            m_oOutputBehaviors.getTreePopulation().getGUIManager().getOutputBehaviors(),
            m_oOutputBehaviors.getTreePopulation().getGUIManager().getDisturbanceBehaviors(),
            m_oOutputBehaviors.getTreePopulation().getGUIManager().getPlantingBehaviors(), false);
        oWindow.pack();

        //Make sure the window's not too big - make it fit within the
        //bounds of the main application window
        if (oWindow.getSize().height > m_oWindow.getSize().height ||
            oWindow.getSize().width > m_oWindow.getSize().width) {

          int iWidth = java.lang.Math.min(oWindow.getSize().width,
                                          m_oWindow.getSize().width - 10);
          int iHeight = java.lang.Math.min(oWindow.getSize().height,
                                           m_oWindow.getSize().height - 10);

          oWindow.setBounds(oWindow.getBounds().x, oWindow.getBounds().y,
                            iWidth, iHeight);
        }

        oWindow.setLocationRelativeTo(null);
        oWindow.setVisible(true);
      }
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }

  }

  /**
   * Updates the labels on window activation.
   * @param e WindowEvent activating the window.
   */
  void this_windowActivated(WindowEvent e) {
    //Update the labels

    //Live tree
    if (m_oOutputBehaviors.getNumberOfDetailedLiveTreeSettings() == 0) {
      m_jLiveTreeDataLabel.setText("Currently saving no tree data");
    }
    else {
      String sText = "";
      DetailedTreeSettings oSettings;
      boolean bSeedlings = false, bSaplings = false, bAdults = false, bSnags = false;
      int i;
      for (i = 0; i < m_oOutputBehaviors.getNumberOfDetailedLiveTreeSettings(); i++) {
        oSettings = m_oOutputBehaviors.getDetailedLiveTreeSetting(i);
        if (oSettings.getType() == TreePopulation.SEEDLING) {
          bSeedlings = true;
        }
        else if (oSettings.getType() == TreePopulation.SAPLING) {
          bSaplings = true;
        }
        else if (oSettings.getType() == TreePopulation.ADULT) {
          bAdults = true;
        }
        else if (oSettings.getType() == TreePopulation.SNAG) {
          bSnags = true;
        }
      }

      if (bSeedlings == false && bSaplings == false && bAdults == false &&
          bSnags == false) {
        sText = "Currently saving no tree data";
      }
      else {
        if (bSeedlings) {
          sText = "seedlings";
        }
        if (bSaplings) {
          if (sText.length() == 0) {
            sText = "saplings";
          }
          else {
            sText = sText + ", saplings";
          }
        }
        if (bAdults) {
          if (sText.length() == 0) {
            sText = "adults";
          }
          else {
            sText = sText + ", adults";
          }
        }
        if (bSnags) {
          if (sText.length() == 0) {
            sText = "snags";
          }
          else {
            sText = sText + ", snags";
          }
        }
        sText = "Currently saving data for " + sText;
      }
      m_jLiveTreeDataLabel.setText(sText);
    }
    
    //Dead tree
    if (m_oOutputBehaviors.getNumberOfDetailedDeadTreeSettings() == 0) {
      m_jDeadTreeDataLabel.setText("Currently saving no tree data");
    }
    else {
      String sText = "";
      DetailedTreeSettings oSettings;
      boolean bSeedlings = false, bSaplings = false, bAdults = false, bSnags = false;
      int i;
      for (i = 0; i < m_oOutputBehaviors.getNumberOfDetailedDeadTreeSettings(); i++) {
        oSettings = m_oOutputBehaviors.getDetailedDeadTreeSetting(i);
        if (oSettings.getType() == TreePopulation.SEEDLING) {
          bSeedlings = true;
        }
        else if (oSettings.getType() == TreePopulation.SAPLING) {
          bSaplings = true;
        }
        else if (oSettings.getType() == TreePopulation.ADULT) {
          bAdults = true;
        }
        else if (oSettings.getType() == TreePopulation.SNAG) {
          bSnags = true;
        }
      }

      if (bSeedlings == false && bSaplings == false && bAdults == false &&
          bSnags == false) {
        sText = "Currently saving no tree data";
      }
      else {
        if (bSeedlings) {
          sText = "seedlings";
        }
        if (bSaplings) {
          if (sText.length() == 0) {
            sText = "saplings";
          }
          else {
            sText = sText + ", saplings";
          }
        }
        if (bAdults) {
          if (sText.length() == 0) {
            sText = "adults";
          }
          else {
            sText = sText + ", adults";
          }
        }
        if (bSnags) {
          if (sText.length() == 0) {
            sText = "snags";
          }
          else {
            sText = sText + ", snags";
          }
        }
        sText = "Currently saving data for " + sText;
      }
      m_jDeadTreeDataLabel.setText(sText);
    }

    //Grid
    if (m_oOutputBehaviors.getNumberOfDetailedGridSettings() == 0) {
      m_jGridDataLabel.setText("Currently saving no grid data");
    }
    else {
      String sText = "";
      DetailedGridSettings oSettings;
      int i;
      for (i = 0; i < m_oOutputBehaviors.getNumberOfDetailedGridSettings(); i++) {
        oSettings = (DetailedGridSettings) m_oOutputBehaviors.getDetailedGridSetting(i);
        if (sText.length() == 0) {
          sText = oSettings.getName();
        }
        else {
          sText = sText + ", " + oSettings.getName();
        }
      }
      sText = "Currently saving data for " + sText;
      m_jGridDataLabel.setText(sText);
    }

  }
}

/**
 * Triggers refresh of save information on window focus.
 * Copyright: Copyright (c) Charles D. Canham 2003
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 */
class DetailedOutputFileSetup_this_windowAdapter
    extends java.awt.event.WindowAdapter {
  DetailedOutputFileSetup adaptee; /**<Window to refresh on focus*/

  /**
   * Constructor.
   * @param adaptee DetailedOutputFileSetup Window to refresh on focus
   */
  DetailedOutputFileSetup_this_windowAdapter(DetailedOutputFileSetup adaptee) {
    this.adaptee = adaptee;
  }

  /**
   * Triggers refresh.
   * @param e WindowEvent Event that triggered refresh.
   */
  public void windowActivated(WindowEvent e) {
    adaptee.this_windowActivated(e);
  }
}
