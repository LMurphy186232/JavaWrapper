package sortie.tools.batchoutput;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.help.CSH;
import javax.help.HelpBroker;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import sortie.data.simpletypes.ModelException;
import sortie.datavisualizer.DetailedOutputFileManager;
import sortie.gui.ErrorGUI;
import sortie.gui.components.SortieFont;
import sortie.gui.components.SortieMenuItem;

/**
 * Shows the window for choosing save options - step 2 of the wizard.
 */
public class OptionChoosingWindow extends JDialog implements ActionListener {

  /** Master batch class */
  BatchDetailedOutput m_oMaster;
  
  /**Menu with the line graph choices*/
  private JPopupMenu m_jLineGraphChoices;
  /**Button which opens the line graph choices menu*/
  private JButton m_jLineGraphMenuButton;
  /**Menu with the histogram choices*/
  private JPopupMenu m_jHistogramChoices;
  /**Button which opens the histogram choices menu*/
  private JButton m_jHistogramMenuButton;
  /**Menu with the table choices*/
  private JPopupMenu m_jTableChoices;
  /**Button which opens the table choices menu*/
  private JButton m_jTableMenuButton;
  /**Menu with the map choices*/
  private JPopupMenu m_jMapChoices;
  /**Button which opens the map choices menu*/
  private JButton m_jMapMenuButton;
  
  /** List model of chart options files currently in batch */
  private DefaultListModel<ChartInfo> m_jOptionsListModel;

  /** List of chart options currently added to batch */
  private JList<ChartInfo> m_jOptionsList;
  
  /**Help ID string*/
  private String m_sHelpID = "windows.batch_extract_detailed_output_files_window";

  /**
   * Constructor.
   * @param oMaster Master batch class
   */
  public OptionChoosingWindow(BatchDetailedOutput oMaster) {
    m_oMaster = oMaster;            
  }
  
  /**
   * This obtains the chart options and creates the GUI. This would normally be 
   * done in the constructor, but I need to be able to do this in a separate 
   * step because of the way FileAnalysisBackgroundProcess merges menus.
   */
  public void doSetup(HelpBroker jBroker) {
    
    createChartMenus();

    createGUI(jBroker);
  }
  
   /**
   * Creates the window GUI. 
   */
  private void createGUI(HelpBroker jBroker) {
    this.setTitle("Choose extraction options");
    JPanel jContentPanel = new JPanel();
    jContentPanel.setLayout(new BoxLayout(jContentPanel, BoxLayout.PAGE_AXIS));
    
    
    JLabel jLabel = new JLabel("Select what to extract:");
    jLabel.setFont(new SortieFont(Font.BOLD));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jContentPanel.add(jLabel);        
    
    jContentPanel.add(Box.createRigidArea(new Dimension(0,5)));
    
    JPanel jDataVisualizerPanel = new JPanel(new GridLayout(1, 0));
    jDataVisualizerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jLineGraphMenuButton = new JButton("Line graphs");
    m_jLineGraphMenuButton.setFont(new SortieFont());
    m_jLineGraphMenuButton.addMouseListener(new PopupListener(m_jLineGraphMenuButton,
        m_jLineGraphChoices));

    m_jHistogramMenuButton = new JButton("Histograms");
    m_jHistogramMenuButton.setFont(new SortieFont());
    m_jHistogramMenuButton.addMouseListener(new PopupListener(m_jHistogramMenuButton,
        m_jHistogramChoices));

    m_jTableMenuButton = new JButton("Tables");
    m_jTableMenuButton.setFont(new SortieFont());
    m_jTableMenuButton.addMouseListener(new PopupListener(m_jTableMenuButton, m_jTableChoices));

    m_jMapMenuButton = new JButton("Maps");
    m_jMapMenuButton.setFont(new SortieFont());
    m_jMapMenuButton.addMouseListener(new PopupListener(m_jMapMenuButton, m_jMapChoices));

    jDataVisualizerPanel.add(m_jLineGraphMenuButton);
    jDataVisualizerPanel.add(m_jHistogramMenuButton);
    jDataVisualizerPanel.add(m_jTableMenuButton);
    jDataVisualizerPanel.add(m_jMapMenuButton);    
    jDataVisualizerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    
    jContentPanel.add(jDataVisualizerPanel);
                
                
    // Panel displaying the current list of detailed output files
    jLabel = new JLabel("Options currently selected:");
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jContentPanel.add(jLabel);
    
    // List of current files
    m_jOptionsListModel = new DefaultListModel<ChartInfo>();
    m_jOptionsList = new JList<ChartInfo>(m_jOptionsListModel);
    m_jOptionsList.setFont(new SortieFont());
    JScrollPane jScroller = new JScrollPane(m_jOptionsList);
    jScroller.setAlignmentX(Component.LEFT_ALIGNMENT);
    jContentPanel.add(jScroller);
    
    jContentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    
    JButton jButton = new JButton("Remove");
    jButton.setToolTipText("Remove selected option");
    jButton.setFont(new SortieFont());
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.setActionCommand("Remove");
    jButton.addActionListener(this);
    jContentPanel.add(jButton);  
    jContentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    
    JPanel jButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    //OK button
    JButton jOKButton = new JButton("Finish");
    jOKButton.setFont(new SortieFont());
    jOKButton.setActionCommand("Finish");
    jOKButton.addActionListener(this);
    jOKButton.setMnemonic(java.awt.event.KeyEvent.VK_F);
    jButtonPanel.add(jOKButton);

    //Cancel button
    JButton jCancelButton = new JButton("Cancel");
    jCancelButton.setFont(new SortieFont());
    jCancelButton.setActionCommand("Cancel");
    jCancelButton.addActionListener(this);
    jCancelButton.setMnemonic(java.awt.event.KeyEvent.VK_C);
    jButtonPanel.add(jCancelButton);
    
    JButton jHelpButton = new JButton("Help");
    jHelpButton.setFont(new SortieFont());
    jHelpButton.addActionListener(new CSH.DisplayHelpFromSource(jBroker));
    CSH.setHelpIDString(jHelpButton, m_sHelpID);
    jHelpButton.setMnemonic(java.awt.event.KeyEvent.VK_H);
    jButtonPanel.add(jHelpButton);

    jButtonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
                                              java.awt.Color.BLACK));
    
    this.getContentPane().setLayout(new java.awt.BorderLayout());
    this.getContentPane().add(jContentPanel, java.awt.BorderLayout.CENTER);
    this.getContentPane().add(jButtonPanel, java.awt.BorderLayout.SOUTH);
  }
  
  /**
   * Assembles the menu of chart choices.
   */
  private void createChartMenus() {
    int i;
    m_jHistogramChoices = new JPopupMenu();    
    if (m_oMaster.mp_jHistogramChoices == null || m_oMaster.mp_jHistogramChoices.length == 0) {
      JMenuItem jMenuItem = new JMenuItem("---None available---");
      jMenuItem.setFont(new SortieFont());
      m_jHistogramChoices.add(jMenuItem);  
    } else {
      for (i = 0; i < m_oMaster.mp_jHistogramChoices.length; i++) {
        if (m_oMaster.mp_jHistogramChoices[i] instanceof JMenu)
          m_jHistogramChoices.add((JMenu)m_oMaster.mp_jHistogramChoices[i]);
        else m_jHistogramChoices.add((SortieMenuItem)m_oMaster.mp_jHistogramChoices[i]);
      }    
    }
    
    m_jMapChoices = new JPopupMenu();
    if (m_oMaster.mp_jMapChoices == null || m_oMaster.mp_jMapChoices.length == 0) {
      JMenuItem jMenuItem = new JMenuItem("---None available---");
      jMenuItem.setFont(new SortieFont());
      m_jMapChoices.add(jMenuItem);  
    } else {
      for (i = 0; i < m_oMaster.mp_jMapChoices.length; i++) {
        if (m_oMaster.mp_jMapChoices[i] instanceof JMenu)
          m_jMapChoices.add((JMenu)m_oMaster.mp_jMapChoices[i]);
        else m_jMapChoices.add((SortieMenuItem)m_oMaster.mp_jMapChoices[i]);
      }    
    }
    
    m_jLineGraphChoices = new JPopupMenu();
    if (m_oMaster.mp_jLineGraphChoices == null || m_oMaster.mp_jLineGraphChoices.length == 0) {
      JMenuItem jMenuItem = new JMenuItem("---None available---");
      jMenuItem.setFont(new SortieFont());
      m_jLineGraphChoices.add(jMenuItem);  
    } else {
      for (i = 0; i < m_oMaster.mp_jLineGraphChoices.length; i++) {
        if (m_oMaster.mp_jLineGraphChoices[i] instanceof JMenu)
          m_jLineGraphChoices.add((JMenu)m_oMaster.mp_jLineGraphChoices[i]);
        else m_jLineGraphChoices.add((SortieMenuItem)m_oMaster.mp_jLineGraphChoices[i]);
      }    
    }
    
    m_jTableChoices = new JPopupMenu();
    if (m_oMaster.mp_jTableChoices == null || m_oMaster.mp_jTableChoices.length == 0) {
      JMenuItem jMenuItem = new JMenuItem("---None available---");
      jMenuItem.setFont(new SortieFont());
      m_jTableChoices.add(jMenuItem);  
    } else {
      for (i = 0; i < m_oMaster.mp_jTableChoices.length; i++) {
        if (m_oMaster.mp_jTableChoices[i] instanceof JMenu)
          m_jTableChoices.add((JMenu)m_oMaster.mp_jTableChoices[i]);
        else m_jTableChoices.add((SortieMenuItem)m_oMaster.mp_jTableChoices[i]);
      }    
    }
  }
  
  /**
   * Called when the user is finished with the dialogue displaying options for 
   * a particular data request.
   * @param oDialog Dialogue of options
   */
  public void dialogAdd(BatchOptionsDialog oDialog) {
    oDialog.setVisible(false);
    if (oDialog.canceled()) {
      oDialog.dispose();
      return;
    }
    m_jOptionsListModel.addElement(new ChartInfo(oDialog.getGraphName(),
        oDialog.getFilename(), oDialog.getExtraOptionsPanel()));
    
    oDialog.dispose();
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
      
    } else if (sCommand.equals("Finish")) {
      if (m_jOptionsListModel.size() == 0) {
        JOptionPane.showMessageDialog(this, "Please choose charts to extract.");
        return;
      }
      try {
        ChartInfo oInfo;
        for (DetailedOutputFileManager oManager : m_oMaster.m_oManagers) {
          for (int i = 0; i < m_jOptionsListModel.size(); i++) {
            oInfo = (ChartInfo)m_jOptionsListModel.elementAt(i);
            oManager.addBatchData(oInfo);
          }
        }        
      } catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
        return;
      }
      this.setVisible(false);      
      m_oMaster.doFinish();
      this.dispose();
      
    } else if (sCommand.equals("Remove")) {

      // Make sure there's something in the parameter file area - if not
      // warn user
      List<ChartInfo> p_jFiles = m_jOptionsList.getSelectedValuesList();
      
      if (p_jFiles.size() == 0) {
        JOptionPane.showMessageDialog(this,
            "No options selected.", "SORTIE-ND",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      // Remove the entry(ies)      
      for (int i = 0; i < p_jFiles.size(); i++) {
        m_jOptionsListModel.removeElement(p_jFiles.get(i));        
      }
      
    } else if (oEvent.getSource() instanceof JMenuItem) { 
      try {
        if (!sCommand.startsWith("--")) {
          //See if this option has already been added
          for (int i = 0; i < m_jOptionsListModel.size(); i++) {
            if (m_jOptionsListModel.elementAt(i).toString().equals(sCommand)) {
              return;
            }
          }
          BatchOptionsDialog oDialog = 
            new BatchOptionsDialog(sCommand, this,
                DetailedOutputFileManager.getOptionsPanel(sCommand));
          oDialog.pack();
          oDialog.setLocationRelativeTo(null);
          oDialog.setVisible(true); 
        }
      } catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
        return;
      }
    }
  }
}
