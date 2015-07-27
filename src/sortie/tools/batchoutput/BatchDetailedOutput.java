package sortie.tools.batchoutput;
import java.util.ArrayList;
import java.util.List;

import javax.help.HelpBroker;
import javax.swing.*;

import sortie.data.simpletypes.ModelException;
import sortie.datavisualizer.*;
import sortie.gui.MainWindow;

/**
 * This class allows the user to set up batch extraction of data from detailed
 * output files. This uses a wizard format.
 * 
 * @author Lora E. Murphy
 * Edit history: <br>
 * ------------------ <br>
 * June 28, 2011: Created (LEM)
 * 
 */
public class BatchDetailedOutput {

  /** Main window parent */
  protected MainWindow m_oParent;
    
  /** Collection of detailed output file managers for each file */
  List<DetailedOutputFileManager> m_oManagers = new ArrayList<DetailedOutputFileManager>();
  
  /**Menu with the line graph choices*/
  protected MenuElement[] mp_jLineGraphChoices;
  /**Menu with the histogram choices*/
  protected MenuElement[] mp_jHistogramChoices;
  /**Menu with the table choices*/
  protected MenuElement[] mp_jTableChoices;
  /**Menu with the map choices*/
  protected MenuElement[] mp_jMapChoices;
  
  /**Help broker*/
  private HelpBroker m_jBroker;
    
   
  

  /**
   * Starts the batch extraction wizard.
   * @param oParent Parent window.
   * @param jBroker Help broker for providing help information.
   */
  public BatchDetailedOutput(MainWindow oParent, HelpBroker jBroker) {
    m_oParent = oParent;
    m_jBroker = jBroker;
    FileChoosingWindow oWindow = new FileChoosingWindow(this);
    oWindow.pack();
    oWindow.setLocationRelativeTo(null);
    oWindow.setVisible(true);
  }  
  
  /**
   * Causes cleanup of all detailed output files.
   */
  public void cleanup() throws ModelException {
    int i;
    for (i = 0; i < m_oManagers.size(); i++) {
      m_oManagers.get(i).cleanUp();      
    }
  }
  
  public HelpBroker getHelpBroker() {return m_jBroker;}
  
  
  /**
   * This is called when step one of the batch extraction wizard has completed.
   * This launches the background thread process that extracts the options from 
   * the chosen detailed output files.
   * @param p_jFiles The set of detailed output files as "File" objects to pass 
   * to the worker thread.
   */
  public void doStep1Next(Object[] p_jFiles) {
    
    ProgressDialog oDialog = new ProgressDialog(p_jFiles.length, 
        "Analyzing output files for data options:");
    oDialog.pack();
    oDialog.setLocationRelativeTo(null);
    oDialog.setVisible(true);
    
    FileAnalysisBackgroundProcess task = 
      new FileAnalysisBackgroundProcess(this, p_jFiles, oDialog);
    task.addPropertyChangeListener(new ProgressChangeListener(oDialog));
    task.execute();

  }
  
  /**
   * This performs the final step of the wizard: writing all of the output 
   * files. 
   */
  public void doFinish() {
    ProgressDialog oDialog = new ProgressDialog(m_oManagers.size(), 
    "Writing output:");
    oDialog.pack();
    oDialog.setLocationRelativeTo(null);
    oDialog.setVisible(true);

    FileWritingBackgroundProcess task = 
      new FileWritingBackgroundProcess(this, oDialog);
    task.addPropertyChangeListener(new ProgressChangeListener(oDialog));
    task.execute();
  }
}