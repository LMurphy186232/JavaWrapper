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

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;

/**
 * This class displays the default dialog for editing the parameters for a 
 * single behavior. It shows a table with the parameters arranged by species.
 * This window makes actual changes to the behavior.
 * @author LORA
 *
 */
public class DefaultBehaviorParameterEdit extends JDialog 
  implements ActionListener, EnhancedTableWindow {

  /** The parent frame over which dialogs are displayed.*/
  protected JDialog m_jParentFrame;
  
  /** The behavior being set up */
  protected Behavior m_oBehavior;
  
  /**GUIManager object to help extract data.*/
  protected GUIManager m_oManager;
  
  /**All of the EnhancedTable objects in this window; they
   * are split out by groupings which match the groupings in mp_oAllObjects*/
  ArrayList<ArrayList<EnhancedTable>> mp_oAllTables;
  
  /** Set of objects that were displayed*/
  ArrayList<BehaviorParameterDisplay> mp_oAllObjects;
  
  
  /**
   * Constructor.
   * @param jParent Parent frame for these dialogs.
   * @param oManager GUIManager. For accessing tree population.
   * @param oWindow MainWindow. For sizing this window.
   * @param oBehavior Behavior object for which we are displaying parameters.
   */
  public DefaultBehaviorParameterEdit(JDialog jParent, GUIManager oManager, 
      MainWindow oWindow, Behavior oBehavior) {
    super(jParent, "Edit Parameters", true);
    m_jParentFrame = jParent;
    m_oBehavior = oBehavior;
    m_oManager = oManager;
    
    mp_oAllTables = new ArrayList<ArrayList<EnhancedTable>>();

    TreePopulation oPop = oManager.getTreePopulation();
    String sHelpID = "windows.default_parameter_edit_window";
    
    //Create a main panel in which to place everything else
    JPanel jMainPanel = new QuickScrollingPanel();
    jMainPanel.setName("Main Panel");
    jMainPanel.setLayout(new BoxLayout(jMainPanel, BoxLayout.Y_AXIS));
    jMainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    //Add the data to the window
    mp_oAllObjects = oBehavior.formatDataForDisplay(oPop);
    for (BehaviorParameterDisplay oBehData : mp_oAllObjects) {

      //Add a label with the object's name
      JLabel jLabel = new JLabel(oBehData.m_sBehaviorName);
      jLabel.setName("Object Name");
      jLabel.setFont(new SortieFont(Font.BOLD, 2));
      jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      jMainPanel.add(jLabel);

      //Use this html trick to word-wrap the label and keep it to a 
      //smaller width
      jLabel = new JLabel("<html><body style='width: 400px'>" + oBehData.m_sAppliesTo);
      jLabel.setFont(new SortieFont());
      jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      jMainPanel.add(jLabel);

      jLabel = new JLabel(String.valueOf(oBehData.m_iListPosition));
      jLabel.setName("Object List Position");
      jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      jLabel.setVisible(false);
      jMainPanel.add(jLabel);
      
      sHelpID = oBehData.m_sHelpString;

      ArrayList<EnhancedTable> oBehTables = new ArrayList<EnhancedTable>(oBehData.mp_oTableData.size());
      for (TableData oTableData : oBehData.mp_oTableData) {        
        
        //Create the table from the data
        EnhancedTable oTable = new EnhancedTable(oTableData.mp_oTableData, 
            oTableData.mp_oHeaderRow, this, oBehData.m_sBehaviorName);
        oBehTables.add(oTable);        

        //Create a table panel with an empty panel in CENTER so that the
        //table won't automatically fill the space
        JPanel jTablePanel = new JPanel(new BorderLayout());
        jTablePanel.setName("Table Panel");
        jTablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        jTablePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        jTablePanel.add(oTable, BorderLayout.LINE_START);
        jTablePanel.add(new JPanel(), BorderLayout.CENTER);

        //Put the table panel in the object's panel
        jMainPanel.add(jTablePanel);                
      }
      mp_oAllTables.add(oBehTables);
    }
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
    iHeight = Math.min(iHeight, oWindow.getHeight() - 100);
    iWidth = Math.min(iWidth, oWindow.getWidth() - 100);
    jScroller.setPreferredSize(new Dimension(iWidth, iHeight));

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jScroller, BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this,
        oWindow.m_oHelpBroker,
        sHelpID), BorderLayout.SOUTH);


    //Create our menu
    JMenuBar jMenuBar = new JMenuBar();
    jMenuBar.setBackground(SystemColor.control);

    JMenu jMenuFile = new JMenu();
    jMenuFile.setText("File");
    jMenuFile.setFont(new SortieFont());
    jMenuFile.setMnemonic(KeyEvent.VK_F);
    jMenuFile.setBackground(SystemColor.control);
    JMenuItem jSave = new JMenuItem("Save window as file",
        java.awt.event.KeyEvent.VK_S);
    jSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
        ActionEvent.CTRL_MASK));
    jSave.setFont(new SortieFont());
    jSave.setActionCommand("Save");
    jSave.addActionListener(this);
    jSave.setBackground(SystemColor.control);
    jMenuFile.add(jSave);

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

    //jMenuBar.add(jMenuFile);
    jMenuBar.add(jMenuEdit);
    setJMenuBar(jMenuBar);
  }
    
  /**
   * Pass the newly collected parameters to the behavior.
   */
  private void passDataToBehavior() throws ModelException {
    int i, j;
    //Make sure that we capture in-process edits
    for (i = 0; i < mp_oAllTables.size(); i++) { 
      for (EnhancedTable oTable : mp_oAllTables.get(i)) {
        if (oTable.isEditing()) {
          oTable.getCellEditor(oTable.getEditingRow(),
              oTable.getEditingColumn()).stopCellEditing();
        }
      }
    }

    TreePopulation oPop = m_oManager.getTreePopulation();
    
    //Replace the tables in the behavior display object
    for (i = 0; i < mp_oAllTables.size(); i++) {
      for (j = 0; j < mp_oAllTables.get(i).size(); j++) {
        EnhancedTable oTable = mp_oAllTables.get(i).get(j);
        TableData oDat = oTable.getData();
        mp_oAllObjects.get(i).mp_oTableData.set(j, oDat);      
      }
    }
  
    m_oBehavior.readDataFromDisplay(mp_oAllObjects, oPop);
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
    /*else if (e.getActionCommand().equals("Save")) {

      //Allow the user to save a text file
      ModelFileChooser jChooser = new ModelFileChooser();
      jChooser.setFileFilter(new TextFileFilter());

      int iReturnVal = jChooser.showSaveDialog(this);
      if (iReturnVal == JFileChooser.APPROVE_OPTION) {
        //User chose a file - trigger the save
        File oFile = jChooser.getSelectedFile();
        if (oFile.exists()) {
          iReturnVal = JOptionPane.showConfirmDialog(this,
              "Do you wish to overwrite the existing file?",
              "Model",
              JOptionPane.YES_NO_OPTION);
        }
        if (!oFile.exists() || iReturnVal == JOptionPane.YES_OPTION) {
          String sFileName = oFile.getAbsolutePath();
          if (sFileName.endsWith(".txt") == false) {
            sFileName += ".txt";
          }
          saveCurrentWindow(sFileName);
        }
      }
    } */
  }
  
  /**
   * Discovers which EnhancedTable in the parameter editing window has
   * focus.
   * @return EnhancedTable object with focus, or null if none has
   * focus.
   */
  protected EnhancedTable getLastTouchedTable() {
    for (ArrayList<EnhancedTable> jTables : mp_oAllTables)
      for (EnhancedTable oTable : jTables) if (oTable.getLastTouched()) return oTable;
          
    return null;
  }
  
  /**
   * Sets a table as last touched by the user.
   * @param oTable Table to be set as last touched.
   */
  public void setLastTouched(EnhancedTable oTable) {
    for (ArrayList<EnhancedTable> jTables : mp_oAllTables)
      for (EnhancedTable oATable : jTables) oATable.setLastTouched(false);
    
    oTable.setLastTouched(true);
  }

}
