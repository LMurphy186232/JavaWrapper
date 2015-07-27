package sortie.gui.behaviorsetup;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;

import sortie.data.funcgroups.Allometry;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.components.OKCancelButtonPanel;

/**
 * Makes a window for editing tree allometry functions.
 * 
 * Copyright: Copyright (c) Charles D. Canham 2008
 * Company: Cary Institute of Ecosystem Studies
 * 
 * @author Lora E. Murphy
 * @version 1.0
 * 
 * <br>
 * <br>Edit history: 
 * <br>------------------ 
 * <br>February 27, 2008: Created (LEM)
 */
public class AllometryFunctionEditor extends JDialog implements ActionListener,
        EnhancedTableWindow {
  
  /**Help ID string*/
  private String m_sHelpID = "windows.edit_allometry_functions_window";
  
  /** Tree population */
  private TreePopulation m_oPop;
  
  /** Allometry object */
  private Allometry m_oAllom;
  
  /** Table holding the allometry function settings for display */
  EnhancedTable m_oTable;
  
  /** Calling window */
  AllometryParameterEdit m_oParentEdit;
  
  public AllometryFunctionEditor(TreePopulation oPop,
      Allometry oAllom, AllometryParameterEdit oEdit) {
    super(oEdit, "Edit Allometry Functions", true);
    
    m_oPop = oPop;
    m_oAllom = oAllom;
    m_oParentEdit = oEdit;
    
    //Help ID
    m_oPop.getGUIManager().getHelpBroker().enableHelpKey(this.getRootPane(), m_sHelpID, null);
    
    Object[][] p_oTableData = null;
    Object[] p_oHeader;
    boolean[] p_bAppliesTo = new boolean[m_oPop.getNumberOfSpecies()];
    int i;
    
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = true;
    }
    
    // Assemble the allometry functions data
    p_oTableData = Behavior.formatDataForTable(p_oTableData, m_oAllom.getWhatAdultHDFunction());
    p_oTableData = Behavior.formatDataForTable(p_oTableData, m_oAllom.getWhatSaplingHDFunction());
    p_oTableData = Behavior.formatDataForTable(p_oTableData, m_oAllom.getWhatSeedlingHDFunction());
    p_oTableData = Behavior.formatDataForTable(p_oTableData, m_oAllom.getWhatAdultCRDFunction());
    p_oTableData = Behavior.formatDataForTable(p_oTableData, m_oAllom.getWhatSaplingCRDFunction());
    p_oTableData = Behavior.formatDataForTable(p_oTableData, m_oAllom.getWhatAdultCDHFunction());
    p_oTableData = Behavior.formatDataForTable(p_oTableData, m_oAllom.getWhatSaplingCDHFunction());
    
    p_oHeader = Behavior.formatSpeciesHeaderRow(p_bAppliesTo, m_oPop);
    
    // Create the table from the data
    m_oTable = new EnhancedTable(p_oTableData, p_oHeader, this, 
        "Allometry Functions");
     
    // Create a table panel with an empty panel in CENTER so that the
    // table won't automatically fill the space
    JPanel jTablePanel = new JPanel(new BorderLayout());
    jTablePanel.setName("Table Panel");
    jTablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    jTablePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jTablePanel.add(m_oTable, BorderLayout.LINE_START);
    jTablePanel.add(new JPanel(), BorderLayout.CENTER);

    // Assemble the window
    JPanel jContentPanel = (JPanel) getContentPane();
    jContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    getContentPane().setLayout(new java.awt.BorderLayout());
    getContentPane().add(jTablePanel, BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this,
                                                 m_oPop.getGUIManager().getHelpBroker(),
                                                 m_sHelpID),
                                                 BorderLayout.PAGE_END);
  }
  

  /**
   * Controls actions for this window.
   * @param oEvent ActionEvent.
   */
  public void actionPerformed(ActionEvent oEvent) {
    String sCommand = oEvent.getActionCommand();
    if (sCommand.equals("Cancel")) {
      this.setVisible(false);
      this.dispose();
    } else if (sCommand.equals("OK")) {
      
      try {
        //Capture any in-process edits
        if (m_oTable.isEditing()) {
          m_oTable.getCellEditor(m_oTable.getEditingRow(),
                                 m_oTable.getEditingColumn()).stopCellEditing();
        }
      
        //Extract the data and feed it back to allometry
        BehaviorParameterDisplay oNew = new BehaviorParameterDisplay();
        oNew.mp_oTableData = new ArrayList<TableData>(1);
        oNew.mp_oTableData.add(m_oTable.getData());
        ArrayList<BehaviorParameterDisplay> oToAdd = new ArrayList<BehaviorParameterDisplay>(1);
        oToAdd.add(oNew);
        m_oAllom.readDataFromDisplay(oToAdd, m_oPop);
        m_oParentEdit.refreshParametersDisplay();
        this.setVisible(false);
        this.dispose();   
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }  
  }
  
  /**
   * Sets a table as last touched by the user.
   * @param oTable Table to be set as last touched.
   */
  public void setLastTouched(EnhancedTable oTable) {
    oTable.setLastTouched(true);
  }
}
