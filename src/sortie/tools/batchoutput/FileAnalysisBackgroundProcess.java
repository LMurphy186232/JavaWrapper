package sortie.tools.batchoutput;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;

import sortie.data.simpletypes.ModelException;
import sortie.datavisualizer.DataVisualizerManager;
import sortie.datavisualizer.DetailedOutputFileManager;
import sortie.gui.ErrorGUI;
import sortie.gui.components.SortieFont;
import sortie.gui.components.SortieMenuItem;

/**
 * This class performs the file analysis of output files in a background thread 
 * so that it can be reported on with the progress bar or canceled. The file 
 * analysis consists of getting all of the options available for chosen output 
 * files so the user can choose charts in the next step of the wizard.
 */
public class FileAnalysisBackgroundProcess extends
    javax.swing.SwingWorker<Integer, Integer> {

  /** The progress bar dialog. */
  private ProgressDialog m_oDialog;
  
  /** Error code. 0 = no error, 1 = processing error, 2 = user canceled. */
  private int m_iErrorCode = 0;
  
  /** Master batch class */
  private BatchDetailedOutput m_oBatchMaster;
  
  /** Files to process. Array of "File" objects. */
  private Object[] mp_jFiles;
  
  /** The window with the next step on the wizard. Create it so we can register 
   * it as an action listener on all menu items.
   */
  private OptionChoosingWindow m_oWindow;

  /**
   * Constructor.
   * @param oBatchMaster Master batch class
   * @param p_jFiles Array of "File" objects representing the detailed output 
   * files to process.
   * @param oDialog Progress bar dialog.
   */
  FileAnalysisBackgroundProcess(BatchDetailedOutput oBatchMaster, Object[] p_jFiles, ProgressDialog oDialog) {
    this.m_oDialog = oDialog;
    this.m_oBatchMaster = oBatchMaster;
    this.mp_jFiles = p_jFiles;
    
    //Create this here, so we can pass it as the action listener to the menus. 
    //But don't let it get set up yet.
    m_oWindow = new OptionChoosingWindow(m_oBatchMaster);
  }

  /**
   * Background work thread for assembling chart options from a list of 
   * detailed output files. This will get all of the choices and merge them 
   * union (as opposed to an intersection). The results are set back into the 
   * batch master.
   */
  public Integer doInBackground() {

    int i;
    try {
      DataVisualizerManager oTempManager = new DataVisualizerManager(m_oBatchMaster.m_oParent, null, null);
      for (i = 0; i < mp_jFiles.length; i++) {
        m_oBatchMaster.m_oManagers.add(new DetailedOutputFileManager(oTempManager,
            ((File) mp_jFiles[i]).toString()));
      }

      //Get the choices for the first detailed output file in the list
      m_oBatchMaster.mp_jHistogramChoices = m_oBatchMaster.m_oManagers.get(0).
         getHistogramOptions(m_oWindow).getSubElements();
      m_oBatchMaster.mp_jTableChoices = m_oBatchMaster.m_oManagers.get(0).getTableOptions(m_oWindow)
      .getSubElements();
      m_oBatchMaster.mp_jMapChoices = m_oBatchMaster.m_oManagers.get(0).getMapOptions(m_oWindow)
      .getSubElements();
      m_oBatchMaster.mp_jLineGraphChoices = m_oBatchMaster.m_oManagers.get(0).getLineGraphOptions(
          m_oWindow).getSubElements();

      //Now merge in the options from the other detailed output files
      for (i = 1; i < mp_jFiles.length; i++) {
        JPopupMenu jMenuChoices = m_oBatchMaster.m_oManagers.get(i).getHistogramOptions(
            m_oWindow);
        if (jMenuChoices.getSubElements().length > 0) {
          m_oBatchMaster.mp_jHistogramChoices = mergeMenus(m_oBatchMaster.mp_jHistogramChoices,
              jMenuChoices.getSubElements());
        }

        jMenuChoices = m_oBatchMaster.m_oManagers.get(i).getTableOptions(m_oWindow);
        if (jMenuChoices.getSubElements().length > 0) {
          m_oBatchMaster.mp_jTableChoices = mergeMenus(m_oBatchMaster.mp_jTableChoices,
              jMenuChoices.getSubElements());
        }

        jMenuChoices = m_oBatchMaster.m_oManagers.get(i).getMapOptions(m_oWindow);
        if (jMenuChoices.getSubElements().length > 0) {
          m_oBatchMaster.mp_jMapChoices = mergeMenus(m_oBatchMaster.mp_jMapChoices, jMenuChoices
              .getSubElements());
        }

        jMenuChoices = m_oBatchMaster.m_oManagers.get(i).getLineGraphOptions(m_oWindow);
        if (jMenuChoices.getSubElements().length > 0) {
          m_oBatchMaster.mp_jLineGraphChoices = mergeMenus(m_oBatchMaster.mp_jLineGraphChoices,
              jMenuChoices.getSubElements());
        }

        //Notify of our progress
        setProgress(i); 
        publish(i);
        
        //Check for possible cancellation
        if (m_oDialog.m_bCanceled) {
          m_iErrorCode = 2;
          return 2;
        }  
      }

    } catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(m_oDialog);
      oHandler.writeErrorMessage(oErr);
      m_iErrorCode = 1;
      return 1;
    }   
    return 0;

  }

  /**
   * Merges two menus, retaining all values from both without duplicates. Common
   * sub menus are merged recursively.
   * 
   * @param jMenu1 First menu, a combination of JMenu and SortieMenuItem items
   * @param jMenu2 Second menu, a combination of JMenu and SortieMenuItem items
   * @return Merged menus, a combination of JMenu and SortieMenuItem items
   */
  private MenuElement[] mergeMenus(MenuElement[] jMenu1, MenuElement[] jMenu2) {
    List<MenuElement> p_jMerged = new ArrayList<MenuElement>();
    int i, j, k;
    boolean bFound = false;

    // Check to see if the menus are just JPopupMenus - we need to eliminate
    // them if so
    if (jMenu1 != null && jMenu1.length > 0 && jMenu1[0] instanceof JPopupMenu) {
      for (i = 1; i < jMenu1.length; i++) {
        if (!(jMenu1[i] instanceof JPopupMenu)) {
          JOptionPane.showMessageDialog(null,
              "Mixed popup and not popup menus - help!.", "SORTIE-ND",
              JOptionPane.ERROR_MESSAGE);
          return null;
        }
      }
      List<MenuElement> p_jPopChildren = new ArrayList<MenuElement>();
      for (i = 0; i < jMenu1.length; i++) {
        for (j = 0; j < jMenu1[i].getSubElements().length; j++)
          p_jPopChildren.add(jMenu1[i].getSubElements()[j]);
      }
      jMenu1 = p_jPopChildren.toArray(new MenuElement[0]);
    }

    if (jMenu2 != null && jMenu2.length > 0 && jMenu2[0] instanceof JPopupMenu) {
      for (i = 1; i < jMenu2.length; i++) {
        if (!(jMenu2[i] instanceof JPopupMenu)) {
          JOptionPane.showMessageDialog(null,
              "Mixed popup and not popup menus - help!.", "SORTIE-ND",
              JOptionPane.ERROR_MESSAGE);
          return null;
        }
      }
      List<MenuElement> p_jPopChildren = new ArrayList<MenuElement>();
      for (i = 0; i < jMenu2.length; i++) {
        for (j = 0; j < jMenu2[i].getSubElements().length; j++)
          p_jPopChildren.add(jMenu2[i].getSubElements()[j]);
      }
      jMenu2 = p_jPopChildren.toArray(new MenuElement[0]);
    }

    // Go through all of the items in menu one, and add them
    for (i = 0; i < jMenu1.length; i++) {
      // Is this item a sub menu?
      if (jMenu1[i] instanceof JMenu) {
        bFound = false;
        // See if we can find this sub menu in the second menu
        for (j = 0; j < jMenu2.length; j++) {
          if (jMenu2[j] instanceof JMenu
              && ((JMenu) jMenu2[j]).getText().equals(
                  ((JMenu) jMenu1[i]).getText())) {
            // We found it. Merge these two menus.
            MenuElement[] p_jMerged2 = mergeMenus(jMenu1[i].getSubElements(),
                jMenu2[j].getSubElements());
            // Create a new menu with the merged items and add it
            JMenu jMenu = new JMenu(((JMenu) jMenu1[i]).getText());
            jMenu.setFont(new SortieFont());
            for (k = 0; k < p_jMerged2.length; k++) {
              if (p_jMerged2[k] instanceof JMenu)
                jMenu.add((JMenu) p_jMerged2[k]);
              else
                jMenu.add((SortieMenuItem) p_jMerged2[k]);
            }
            p_jMerged.add(jMenu);
            bFound = true;
            break;
          }
        }
        if (!bFound) {
          p_jMerged.add((JMenu) jMenu1[i]);
        }

      } else {
        if (jMenu1[i] instanceof JMenu)
          p_jMerged.add((JMenu) jMenu1[i]);
        else
          p_jMerged.add((SortieMenuItem) jMenu1[i]);
      }
    }

    // Go through all of the items in menu two, and add any that are not
    // already added
    for (i = 0; i < jMenu2.length; i++) {
      bFound = false;
      for (j = 0; j < jMenu1.length; j++) {
        if (jMenu1[j].toString().equals(jMenu2[i].toString())) {
          bFound = true;
          break;
        }
      }
      if (!bFound)
        p_jMerged.add(jMenu2[i]);
    }
    return p_jMerged.toArray(new MenuElement[0]);
  }

  /**
   * This function is called when the work in the background thread is done. 
   * We do window closing and opening here because this is guaranteed to wait 
   * until the background thread has completed.
   */
  protected void done() {
    if (m_iErrorCode == 1) {
      m_oDialog.m_jProgress.setText("Completed with error.");

    } else if (m_iErrorCode == 2) {
      //Cancelled
      m_oDialog.setVisible(false);
      m_oDialog.dispose();

    } else {

      m_oDialog.setVisible(false);
      m_oDialog.dispose();

      m_oWindow.doSetup(m_oBatchMaster.getHelpBroker());
      m_oWindow.pack();
      m_oWindow.setLocationRelativeTo(null);
      m_oWindow.setVisible(true);
    }
  }

  
}