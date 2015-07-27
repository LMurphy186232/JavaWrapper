package sortie.gui;


/**
* The purpose of this class is to control the states of the main window -
* all the enabling and disabling, etc.  The states are as follows:
* <ul>
* <li>NO_PAR_FILE - there is no parameter file loaded.  The next step for the
* user is to create or load one.
* <li>PAR_FILE_LOADED - there is a parameter file successfully defined.  The user
* can go on to run the model at this point.
* <li>RUNNING - the model is in the process of running.
* </ul>
* <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
* <p>Company: Cary Institute of Ecosystem Studies</p>
* @author Lora E. Murphy
* @version 1.0
*
* <br>Edit history:
* <br>------------------
* <br>April 28, 2004: Submitted in beta version (LEM)
* <br>November 17, 2004:  Added the run controls buttons (LEM)
* <br>September 29, 2006: Added the harvest interface menu controls (LEM)
* <br>February 29, 2008: Added new tree setup window controls (LEM)
*/
public class MainWindowStateSetter {

  /** State: no parameter file has been loaded */
  public final static int NO_PAR_FILE = 0;
  /** State: parameter file has been successfully loaded */
  public final static int PAR_FILE_LOADED = 1;
  /** State: Model is running */
  public final static int RUNNING = 2;
  /** State: Model has been paused */
  public final static int PAUSED = 3;

  /**
   * Transfer the GUI from one state to another.
   * @param iState The state to which to transfer.
   * @param oWindow Main application window.
   */
  public static void goToState(int iState, MainWindow oWindow) {
    if (NO_PAR_FILE == iState) {
      goToNoParFileState(oWindow);
    }
    else if (PAR_FILE_LOADED == iState) {
      goToParFileLoadedState(oWindow);
    }
    else if (RUNNING == iState) {
      goToRunningState(oWindow);
    }
    else if (PAUSED == iState) {
      goToPausedState(oWindow);
    }
  }

  /**
   * Transition to the NO_PAR_FILE state.
   * @param oWindow Main application window.
   */
  private static void goToNoParFileState(MainWindow oWindow) {

    //Set the model status
    oWindow.m_jModelStatusField.setText("Not ready to run");

    //Set parameter file field - and claim a little extra real estate
    oWindow.m_jParameterFileField.setText(
        "No parameter file loaded                  ");

    oWindow.m_jMenuFileOpen.setEnabled(true);
    oWindow.m_jMenuTreeSpecies.setEnabled(true);
    oWindow.m_jMenuFileNew.setEnabled(true);
    //Disable model running
    oWindow.m_jMenuModelRun.setEnabled(false);
    oWindow.m_jMenuModelPause.setEnabled(false);
    oWindow.m_jMenuModelStop.setEnabled(false);
    oWindow.m_jModelRunButton.setEnabled(false);
    oWindow.m_jModelStopButton.setEnabled(false);
    oWindow.m_jModelPauseButton.setEnabled(false);
    oWindow.m_jModelStepForwardButton.setEnabled(false);
    oWindow.m_jModelLoadOutputButton.setEnabled(false);
    //Except for batch
    oWindow.m_jMenuModelRunBatch.setEnabled(true);
    //Disable parameter file saving
    oWindow.m_jMenuFileSave.setEnabled(false);
    oWindow.m_jMenuTextFileSave.setEnabled(false);
    //Disable output entering
    oWindow.m_jMenuEditOutput.setEnabled(false);
    //Disable parameter editing
    oWindow.m_jMenuEditParameters.setEnabled(false);
    oWindow.m_jMenuTreeSizeClasses.setEnabled(false);
    oWindow.m_jMenuTreeTreeMaps.setEnabled(false);
    //Disable grid editing
    oWindow.m_jMenuEditGrid.setEnabled(false);
    //Disable simulation flow editing
    oWindow.m_jMenuEditFlow.setEnabled(false);
    //Enable detailed output file renaming
    oWindow.m_jMenuToolsRename.setEnabled(true);
    oWindow.m_jMenuToolsBatchOut.setEnabled(true);
  }

  /**
   * Transition to the PAUSED state.
   * @param oWindow Main application window.
   */
  private static void goToPausedState(MainWindow oWindow) {
    //Disable everything for file loading/editing
    oWindow.m_jMenuFileOpen.setEnabled(false);
    oWindow.m_jMenuFileSave.setEnabled(false);
    oWindow.m_jMenuTextFileSave.setEnabled(false);
    oWindow.m_jMenuFileNew.setEnabled(false);
    oWindow.m_jMenuTreeSpecies.setEnabled(false);
    oWindow.m_jMenuTreeSizeClasses.setEnabled(false);
    oWindow.m_jMenuTreeTreeMaps.setEnabled(false);
    oWindow.m_jMenuEditOutput.setEnabled(false);
    oWindow.m_jMenuEditParameters.setEnabled(false);
    oWindow.m_jMenuEditGrid.setEnabled(false);
    oWindow.m_jMenuEditFlow.setEnabled(false);
    
    //Enable running and stopping
    oWindow.m_jMenuModelRun.setEnabled(true);
    oWindow.m_jModelRunButton.setEnabled(true);
    oWindow.m_jModelStepForwardButton.setEnabled(true);
    oWindow.m_jMenuModelStop.setEnabled(true);
    oWindow.m_jModelStopButton.setEnabled(true);
    oWindow.m_jModelLoadOutputButton.setEnabled(true);

    //Disable pause controls
    oWindow.m_jMenuModelPause.setEnabled(false);
    oWindow.m_jModelPauseButton.setEnabled(false);
    
    //Enable detailed output file renaming
    oWindow.m_jMenuToolsRename.setEnabled(true);
    oWindow.m_jMenuToolsBatchOut.setEnabled(true);
  }

  /**
   * Transition to the PAR_FILE_LOADED state.
   * @param oWindow Main application window.
   */
  private static void goToParFileLoadedState(MainWindow oWindow) {

    //Set the model status
    oWindow.m_jModelStatusField.setText("Ready to run");

    oWindow.m_jMenuFileNew.setEnabled(true);
    oWindow.m_jMenuFileOpen.setEnabled(true);
    oWindow.m_jMenuFileSave.setEnabled(true);
    oWindow.m_jMenuTextFileSave.setEnabled(true);
    oWindow.m_jMenuTreeSpecies.setEnabled(true);
    //Enable model running
    oWindow.m_jMenuModelRun.setEnabled(true);
    oWindow.m_jModelRunButton.setEnabled(true);
    oWindow.m_jModelStepForwardButton.setEnabled(true);
    oWindow.m_jModelLoadOutputButton.setEnabled(true);
    oWindow.m_jMenuModelRunBatch.setEnabled(true);
    //Disable other run controls
    oWindow.m_jMenuModelPause.setEnabled(false);
    oWindow.m_jMenuModelStop.setEnabled(false);
    oWindow.m_jModelStopButton.setEnabled(false);
    oWindow.m_jModelPauseButton.setEnabled(false);
    //Enable parameter file saving
    oWindow.m_jMenuFileSave.setEnabled(true);
    oWindow.m_jMenuTreeSizeClasses.setEnabled(true);
    oWindow.m_jMenuTreeTreeMaps.setEnabled(true);
    //Enable output entering
    oWindow.m_jMenuEditOutput.setEnabled(true);
    //Enable parameter editing
    oWindow.m_jMenuEditParameters.setEnabled(true);
    //Enable grid editing
    oWindow.m_jMenuEditGrid.setEnabled(true);
    //Enable simulation flow editing
    oWindow.m_jMenuEditFlow.setEnabled(true);
    //Enable detailed output file renaming
    oWindow.m_jMenuToolsRename.setEnabled(true);
    oWindow.m_jMenuToolsBatchOut.setEnabled(true);
  }

  /**
   * Transition to the RUNNING state.
   * @param oWindow Main application window.
   */
  private static void goToRunningState(MainWindow oWindow) {
    //Disable everything for file loading/editing
    oWindow.m_jMenuFileOpen.setEnabled(false);
    oWindow.m_jMenuFileSave.setEnabled(false);
    oWindow.m_jMenuTextFileSave.setEnabled(false);
    oWindow.m_jMenuFileNew.setEnabled(false);
    oWindow.m_jMenuTreeSpecies.setEnabled(false);
    oWindow.m_jMenuTreeSizeClasses.setEnabled(false);
    oWindow.m_jMenuTreeTreeMaps.setEnabled(false);
    oWindow.m_jMenuEditOutput.setEnabled(false);
    oWindow.m_jMenuEditParameters.setEnabled(false);
    oWindow.m_jMenuEditGrid.setEnabled(false);
    oWindow.m_jMenuEditFlow.setEnabled(false);
    oWindow.m_jMenuModelRun.setEnabled(false);
    oWindow.m_jModelRunButton.setEnabled(false);
    oWindow.m_jModelStepForwardButton.setEnabled(false);
    oWindow.m_jMenuModelRunBatch.setEnabled(false);
    oWindow.m_jMenuToolsRename.setEnabled(false);
    oWindow.m_jMenuToolsBatchOut.setEnabled(false);

    //Enable run controls
    oWindow.m_jMenuModelPause.setEnabled(true);
    oWindow.m_jMenuModelStop.setEnabled(true);
    oWindow.m_jModelStopButton.setEnabled(true);
    oWindow.m_jModelPauseButton.setEnabled(true);
    oWindow.m_jModelLoadOutputButton.setEnabled(true);
  }
}
