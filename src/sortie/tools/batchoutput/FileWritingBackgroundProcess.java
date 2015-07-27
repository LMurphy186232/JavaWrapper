package sortie.tools.batchoutput;

import javax.swing.JOptionPane;

import sortie.data.simpletypes.ModelException;
import sortie.datavisualizer.DetailedOutputFileManager;
import sortie.gui.ErrorGUI;

/**
 * This class triggers the writing of the chosen output in a background thread 
 * so that it can be reported on with the progress bar or canceled.
 *
 */
public class FileWritingBackgroundProcess extends
    javax.swing.SwingWorker<Integer, Integer> {

  /** The progress bar dialog. */
  private ProgressDialog m_oDialog;
  
  /** Error code. 0 = no error, 1 = processing error, 2 = user canceled. */
  private int m_iErrorCode = 0;
  
  /** Master batch class */
  private BatchDetailedOutput m_oBatchMaster;
    
  /**
   * Constructor.
   * @param oBatchMaster Master batch class
   * @param p_jFiles Array of "File" objects representing the detailed output 
   * files to process.
   * @param oDialog Progress bar dialog.
   */
  FileWritingBackgroundProcess(BatchDetailedOutput oBatchMaster, ProgressDialog oDialog) {
    this.m_oDialog = oDialog;
    this.m_oBatchMaster = oBatchMaster;
    
  }

  /**
   * Background work thread for assembling chart options from a list of 
   * detailed output files. This will get all of the choices and merge them 
   * union (as opposed to an intersection). The results are set back into the 
   * batch master.
   */
  public Integer doInBackground() {
        
    int i = 1;
    try {
      for (DetailedOutputFileManager oManager : m_oBatchMaster.m_oManagers) {      
        oManager.doBatchExtraction();
            
        //Notify of our progress
        setProgress(i); 
        publish(i);
        i++;
        
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
   * This function is called when the work in the background thread is done. 
   * We do window closing and opening here because this is guaranteed to wait 
   * until the background thread has completed.
   */
  protected void done() {
    try {
      m_oBatchMaster.cleanup();
    } catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(m_oDialog);
      oHandler.writeErrorMessage(oErr);
    }
    if (m_iErrorCode == 1) {
      m_oDialog.m_jProgress.setText("Completed with error.");

    } else if (m_iErrorCode == 0) {            
      JOptionPane.showMessageDialog(null, "Output batch extraction complete.");
    }
    
    if (m_iErrorCode != 1) {
      m_oDialog.setVisible(false);
      m_oDialog.dispose();
    }
  }

  
}