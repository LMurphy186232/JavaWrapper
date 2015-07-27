package sortie.tools.batchoutput;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.io.File;

import javax.help.CSH;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sortie.data.simpletypes.ModelException;
import sortie.gui.DetailedOutputFileFilter;
import sortie.gui.ErrorGUI;
import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.SortieFont;

/**
 * Shows the window for choosing a set of detailed output files - step 1 of
 * the wizard.
 *
 */
public class FileChoosingWindow extends JDialog implements ActionListener {
  
  /**File chooser for detailed output files*/
  ModelFileChooser m_oChooser = new ModelFileChooser();
  
  /** List model of detailed output files currently in batch */
  private DefaultListModel<File> m_jFilesListModel;

  /** List of detailed output currently added to batch */
  private JList<File> m_jFilesList;
   
  /** Master batch class */
  BatchDetailedOutput m_oMaster;
  
  /**Help ID string*/
  private String m_sHelpID = "windows.batch_extract_detailed_output_files_window";
  
  /**
   * Constructor. Builds the window.
   */
  FileChoosingWindow(BatchDetailedOutput oMaster) {
    super(oMaster.m_oParent, "Choose detailed output files to extract from", true);

    m_oMaster = oMaster;
    
    JPanel jContentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));      
    
    //Panel displaying file chooser
    m_oChooser.setFileFilter(new DetailedOutputFileFilter());
    m_oChooser.setMultiSelectionEnabled(true);
    m_oChooser.setControlButtonsAreShown(false);
    jContentPanel.add(m_oChooser);

    //Panel with add and remove buttons
    JPanel jTempPanel = new JPanel(new GridLayout(0, 1));
    JButton jButton = new JButton("Add files to batch", 
        new ModelIcon(15, 15, ModelIcon.RIGHT_TRIANGLE));
    jButton.setToolTipText("Add selected file(s)");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Add");
    jButton.addActionListener(this);
    jTempPanel.add(jButton);
    
    jButton = new JButton("Remove files from batch",
        new ModelIcon(15, 15, ModelIcon.LEFT_TRIANGLE));
    jButton.setToolTipText("Remove selected file(s)");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Remove");
    jButton.addActionListener(this);
    jTempPanel.add(jButton);
    
    jContentPanel.add(jTempPanel);            

    // Panel displaying the current list of detailed output files
    jTempPanel = new JPanel();
    jTempPanel.setLayout(new BoxLayout(jTempPanel, BoxLayout.PAGE_AXIS));
    // Label to identify the list of current files
    JLabel jLabel = new JLabel("Files currently in batch:");
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jTempPanel.add(jLabel);
    
    // List of current files
    m_jFilesListModel = new DefaultListModel<File>();
    m_jFilesList = new JList<File>(m_jFilesListModel);
    m_jFilesList.setFont(new SortieFont());
    JScrollPane jScroller = new JScrollPane(m_jFilesList);
    jScroller.getViewport().setPreferredSize(
        new java.awt.Dimension(300, (int) m_oChooser.getPreferredSize().getHeight()));
    jTempPanel.add(jScroller);
    
    jContentPanel.add(jTempPanel);
   
    //Control buttons panel
    JPanel jControlButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    //Next button
    JButton jNextButton = new JButton("Next");
    jNextButton.setFont(new SortieFont());
    jNextButton.setActionCommand("Next");
    jNextButton.addActionListener(this);
    jNextButton.setMnemonic(java.awt.event.KeyEvent.VK_N);
    jControlButtonsPanel.add(jNextButton);

    //Cancel button
    JButton jCancelButton = new JButton("Cancel");
    jCancelButton.setFont(new SortieFont());
    jCancelButton.setActionCommand("Cancel");
    jCancelButton.addActionListener(this);
    jCancelButton.setMnemonic(java.awt.event.KeyEvent.VK_C);
    jControlButtonsPanel.add(jCancelButton);
    
    JButton jHelpButton = new JButton("Help");
    jHelpButton.setFont(new SortieFont());
    jHelpButton.addActionListener(new CSH.DisplayHelpFromSource(oMaster.getHelpBroker()));
    CSH.setHelpIDString(jHelpButton, m_sHelpID);
    jHelpButton.setMnemonic(java.awt.event.KeyEvent.VK_H);
    jControlButtonsPanel.add(jHelpButton);
         
    jControlButtonsPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
                                              java.awt.Color.BLACK));

    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(jContentPanel, BorderLayout.CENTER);
    this.getContentPane().add(jControlButtonsPanel, BorderLayout.SOUTH);
  }
  
  /**
   * Adds a set of detailed output files to the batch list.
   */
  private void addFiles() {
    // Make sure there's something in the parameter file area - if not
    // warn user
    File[] p_jFiles = m_oChooser.getSelectedFiles();
    
    if (p_jFiles.length == 0) {
      JOptionPane.showMessageDialog(this,
          "No detailed output files selected.", "SORTIE-ND",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    // Add the new entry(ies)      
    for (int i = 0; i < p_jFiles.length; i++) {
      boolean bFound = false;
      for (int j = 0; j < m_jFilesListModel.size(); j++) {
        if (m_jFilesListModel.elementAt(j).equals(p_jFiles[i])) {
          bFound = true;
          break;
        }
      }
      if (!bFound) m_jFilesListModel.addElement(p_jFiles[i]);
    }
  }
  
  /**
   * Removes a set of detailed output files to the batch list.
   */
  private void removeFiles() {
    // Make sure there's something in the parameter file area - if not
    // warn user
    List<File> p_jFiles = m_jFilesList.getSelectedValuesList();
    
    if (p_jFiles.size() == 0) {
      JOptionPane.showMessageDialog(this,
          "No detailed output files selected.", "SORTIE-ND",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    // Remove the entry(ies)      
    for (int i = 0; i < p_jFiles.size(); i++) {
      m_jFilesListModel.removeElement(p_jFiles.get(i));        
    }
  }
  
  /**
   * Controls actions for this window.
   * 
   * @param oEvent ActionEvent.
   */
  public void actionPerformed(ActionEvent oEvent) {
    String sCommand = oEvent.getActionCommand();
    if (sCommand.equals("Cancel")) {
      try {
        m_oMaster.cleanup();
      } catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
      this.setVisible(false);
      this.dispose();
      
    } else if (sCommand.equals("Next")) {

      if (m_jFilesListModel.size() == 0) {
        JOptionPane.showMessageDialog(this,
            "No detailed output files selected for processing.", "SORTIE-ND",
            JOptionPane.ERROR_MESSAGE);
        return;
      }
      this.setVisible(false);      
      m_oMaster.doStep1Next(m_jFilesListModel.toArray());
      this.dispose();
      
    } else if (sCommand.equals("Add")) {
      
      addFiles();
      
    }
    if (sCommand.equals("Remove")) {

      removeFiles();
      
    }
  }
}