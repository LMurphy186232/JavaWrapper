package sortie.gui.behaviorsetup;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;

import sortie.data.funcgroups.Allometry;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;

/**
 * This class displays the dialog for editing Allometry parameters, with a
 * button for editing the function choices.
 * @author LORA
 *
 */
public class AllometryParameterEdit extends JDialog 
  implements ActionListener, EnhancedTableWindow {

  /** The parent frame over which dialogs are displayed.*/
  protected JDialog m_jParentFrame;
  
  /** The behavior being set up */
  protected Allometry m_oBehavior;
  
  /**GUIManager object to help extract data.*/
  protected GUIManager m_oManager;
  
  /**All of the EnhancedTable objects in this window; they
   * are split out by groupings which match the groupings in mp_oAllObjects*/
  private ArrayList<EnhancedTable> mp_oAllTables;
  
  /** Object that was displayed*/
  private BehaviorParameterDisplay m_oBehDisp;
  
  /**Size of parent window*/
  private Dimension m_jParentSize;
  
  private JPanel m_jButtonPanel;
  private OKCancelButtonPanel m_jOKPanel;
  
  
  /**
   * Constructor.
   * @param jParent Parent frame for these dialogs.
   * @param oManager GUIManager. For accessing tree population.
   * @param oWindow MainWindow. For sizing this window.
   * @param oBehavior Behavior object for which we are displaying parameters.
   */
  public AllometryParameterEdit(JDialog jParent, GUIManager oManager, 
      MainWindow oWindow, Allometry oBehavior) {
    super(jParent, "Edit Parameters", true);
    m_jParentFrame = jParent;
    m_oBehavior = oBehavior;
    m_oManager = oManager;
    
    m_jParentSize = oWindow.getSize();
    
    mp_oAllTables = new ArrayList<EnhancedTable>();

    String sHelpID = "";
    
    //Create a panel above the scroller that has a button for opening the
    //dialog to edit the functions
    m_jButtonPanel = new JPanel();
    m_jButtonPanel.setLayout(new BoxLayout(m_jButtonPanel, BoxLayout.Y_AXIS));
    JButton jButton = new JButton("Edit function choices...");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("EditFunctions");
    jButton.addActionListener(this);
    m_jButtonPanel.add(jButton, BorderLayout.SOUTH);
    
    //Add a label with the object's name
    JLabel jLabel = new JLabel(oBehavior.getDescriptor());
    jLabel.setName("Object Name");
    jLabel.setFont(new SortieFont(Font.BOLD, 2));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jButtonPanel.add(jLabel, BorderLayout.NORTH);
    m_jButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    if (sHelpID.length() > 0)
      m_jOKPanel = new OKCancelButtonPanel(this,
          oWindow.m_oHelpBroker,
          sHelpID);     
    else m_jOKPanel = new OKCancelButtonPanel(this, null, "");
    
    refreshParametersDisplay();
    
    //Create our menu
    JMenuBar jMenuBar = new JMenuBar();
    jMenuBar.setBackground(SystemColor.control);

    JMenu jMenuEdit = new JMenu();
    jMenuEdit.setText("Edit");
    jMenuEdit.setFont(new SortieFont());
    jMenuEdit.setMnemonic(KeyEvent.VK_E);
    jMenuEdit.setBackground(SystemColor.control);
    JMenuItem jCopy = new JMenuItem("Copy", KeyEvent.VK_C);
    jCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    jCopy.setFont(new SortieFont());
    jCopy.setActionCommand("Copy");
    jCopy.addActionListener(this);
    jCopy.setBackground(SystemColor.control);
    JMenuItem jPaste = new JMenuItem("Paste", KeyEvent.VK_P);
    jPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
    jPaste.setFont(new SortieFont());
    jPaste.setActionCommand("Paste");
    jPaste.addActionListener(this);
    jPaste.setBackground(SystemColor.control);
    jMenuEdit.add(jCopy);
    jMenuEdit.add(jPaste);

    jMenuBar.add(jMenuEdit);
    setJMenuBar(jMenuBar);
  }
  
  public void refreshParametersDisplay() {
    //This seems to be required to get the new parameters to display correctly
    //if being replaced - replacing just the table panel alone was not enough
    getContentPane().removeAll();
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(m_jButtonPanel, BorderLayout.NORTH);
    
    
    QuickScrollingPanel jMainPanel = createParametersPanel();
    //Lay out the dialog
    JScrollPane jScroller = new JScrollPane();
    jScroller.setName("Scroller");
    //Make sure window isn't larger than background window
    jScroller.getViewport().add(jMainPanel);
    jScroller.setPreferredSize(new Dimension( (int) jMainPanel.getPreferredSize().
        getWidth() + 20,
        (int) jScroller.getPreferredSize().
        getHeight()));
    int iHeight = (int) jScroller.getPreferredSize().getHeight();
    int iWidth = (int) jScroller.getPreferredSize().getWidth();
    iHeight = java.lang.Math.min(iHeight, m_jParentSize.height - 100);
    iWidth = java.lang.Math.min(iWidth, m_jParentSize.width - 100);
    jScroller.setPreferredSize(new Dimension(iWidth, iHeight));
    getContentPane().add(jScroller, BorderLayout.CENTER);
    
    getContentPane().add(m_jOKPanel, BorderLayout.SOUTH);
    
    revalidate();
    pack();
    repaint();
    
  }
  
  /**
   * Creates the panel with parameters so it can be re-created when 
   * function choices are changed.
   * @return
   */
  private QuickScrollingPanel createParametersPanel() {
    QuickScrollingPanel jPanel = new QuickScrollingPanel();
    jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
    ArrayList<BehaviorParameterDisplay> p_oAll = m_oBehavior.formatDataForDisplay(m_oManager.getTreePopulation());
    m_oBehDisp = p_oAll.get(0);
    mp_oAllTables.clear();
    
    jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    for (TableData oTableData : m_oBehDisp.mp_oTableData) {        

      //Create the table from the data
      EnhancedTable oTable = new EnhancedTable(oTableData.mp_oTableData, 
          oTableData.mp_oHeaderRow, this, m_oBehDisp.m_sBehaviorName);
      mp_oAllTables.add(oTable);        

      //Create a table panel with an empty panel in CENTER so that the
      //table won't automatically fill the space
      JPanel jTablePanel = new JPanel(new BorderLayout());
      jTablePanel.setName("Table Panel");
      jTablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
      jTablePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
      jTablePanel.add(oTable, BorderLayout.LINE_START);
      jTablePanel.add(new JPanel(), BorderLayout.CENTER);

      //Put the table panel in the object's panel
      jPanel.add(jTablePanel);    
    }
    return jPanel;
  }
  
  /**
   * Pass the newly collected parameters to the behavior.
   */
  private void passDataToBehavior() throws ModelException {
    int i;
    //Make sure that we capture in-process edits
    for (EnhancedTable oTable : mp_oAllTables) {
      if (oTable.isEditing()) {
        oTable.getCellEditor(oTable.getEditingRow(),
            oTable.getEditingColumn()).stopCellEditing();
      }
    }

    TreePopulation oPop = m_oManager.getTreePopulation();
    
    //Replace the tables in the behavior display object
    for (i = 0; i < mp_oAllTables.size(); i++) {
      EnhancedTable oTable = mp_oAllTables.get(i);
      TableData oDat = oTable.getData();
      m_oBehDisp.mp_oTableData.set(i, oDat);           
    }
  
    ArrayList<BehaviorParameterDisplay> oReader = new ArrayList<BehaviorParameterDisplay>(1);
    oReader.add(m_oBehDisp);
    m_oBehavior.readDataFromDisplay(oReader, oPop);
    m_oBehavior.validateData(oPop);
  }
  
  /**
   * Responds to button events.  If OK, then the parameter window is
   * constructed and this window is closed.  If Cancel, then this window is
   * closed.
   * @param e ActionEvent object.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Cancel")) {
      this.setVisible(false);
      this.dispose();
    }
    else if (e.getActionCommand().equals("OK")) {
      try {
        passDataToBehavior();
        this.setVisible(false);
        this.dispose();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (e.getActionCommand().equals("Copy")) {
      EnhancedTable oTable = getLastTouchedTable();
      if (oTable != null) {
        oTable.copy();
      }
    }
    else if (e.getActionCommand().equals("Paste")) {
      EnhancedTable oTable = getLastTouchedTable();
      if (oTable != null) {
        oTable.paste();
      }
    }
    else if (e.getActionCommand().equals("EditFunctions")) {
      AllometryFunctionEditor oWindow = new AllometryFunctionEditor(m_oManager.getTreePopulation(), m_oBehavior, this);
      oWindow.pack();
      oWindow.setLocationRelativeTo(null);
      oWindow.setVisible(true);            
    }
  }
  
  /**
   * Discovers which EnhancedTable in the parameter editing window has
   * focus.
   * @return EnhancedTable object with focus, or null if none has
   * focus.
   */
  protected EnhancedTable getLastTouchedTable() {
    for (EnhancedTable oTable : mp_oAllTables) if (oTable.getLastTouched()) return oTable;          
    return null;
  }
  
  /**
   * Sets a table as last touched by the user.
   * @param oTable Table to be set as last touched.
   */
  public void setLastTouched(EnhancedTable oTable) {
    for (EnhancedTable oATable : mp_oAllTables) oATable.setLastTouched(false);
    oTable.setLastTouched(true);
  }

}
