package sortie.parfile;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;

import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.MainWindow;

/**
 * Interface between the Java GUI and the C++ application core.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>November 8, 2004:  Put in pause and stop buttons (LEM)
 * <br>February 20, 2008: Made it so that any existing pause and stop messages
 * were cleaned out at the beginning of a new run (LEM)
 */

public class Interface {

  /**Currently running C++ process, or null if none is running*/
  private Process m_oProcess = null;

  /**Messages received from the C++ program are stored here*/
  private String m_sMessage;

  /**Whether or not the program is finished running*/
  private boolean m_bIsDone = false;

  /**Whether or not the last completed run went OK*/
  private boolean m_bIsOK = false;

  /**Whether or not the run is paused*/
  private boolean m_bIsPaused = false;

  /**
   * Pauses the run by sending a "pause" command to the process.
   * @throws ModelException if the process could not be written to.
   */
  public void pause() throws ModelException {
    if (m_bIsPaused == true) return;

    FileWriter oIn = null;
    try {
      String sPath = System.getProperty("SORTIE_PATH");
      String sSeparator = System.getProperty("file.separator");
      if (sPath == null) {
        sPath = "";
      }
      else {
        if (sPath.endsWith(sSeparator) == false) {
          sPath += sSeparator;
        }
      }

      String sFileName = sPath + sSeparator + "bin" + sSeparator +"messages.txt";
      oIn = new FileWriter(sFileName);
      oIn.write("p");
      m_bIsPaused = true;
    }
    catch (IOException oE) {
      m_bIsPaused = false;
      throw (new ModelException(ErrorGUI.UNKNOWN, "JAVA",
          "Model could not be paused.  More information:  " + oE.getMessage()));
    }
    finally {
      if (oIn != null) {
        try {
          oIn.close();
        }
        catch (IOException oE) {
          ;
        }
      }
    }
  }

  /**
   * Stops the currently executing process.  If the run is currently paused,
   * the command "quit" will be sent to the process's stdin stream.  If the
   * run is currently running, the messages file will be created with a "q"
   * in the first position.
   * @throws ModelException if there are problems writing to the file or to
   * the process.
   */
  public void stop() throws ModelException {

    if (m_bIsDone == true) return;

    if (m_bIsPaused == true) {
      try {
        //Send a message to the process to keep going
        BufferedWriter oIn = new BufferedWriter(new 
            OutputStreamWriter(m_oProcess.getOutputStream()));
        oIn.write("quit");
        oIn.newLine();
        oIn.flush();
        m_bIsPaused = false;
        return;
      } catch (IOException e) {
          throw(new ModelException(ErrorGUI.BAD_FILE,
              "JAVA",
              "I couldn't stop the model.  Message: " + e.getMessage()));
        }
    }

    FileWriter oIn = null;
    try {
      String sPath = System.getProperty("SORTIE_PATH");
      String sSeparator = System.getProperty("file.separator");
      if (sPath == null) {
        sPath = "";
      }
      else {
        if (sPath.endsWith(sSeparator) == false) {
          sPath += sSeparator;
        }
      }

      String sFileName = sPath + sSeparator + "bin" + sSeparator +"messages.txt";
      oIn = new FileWriter(sFileName);
      oIn.write("q");
    }
    catch (IOException oE) {
      throw (new ModelException(ErrorGUI.UNKNOWN, "JAVA",
          "Model could not be stopped.  More information:  " + oE.getMessage()));
    }
    finally {
      if (oIn != null) {
        try {
          oIn.close();
        }
        catch (IOException oE) {
          ;
        }
      }
    }
  }

  /**
   * Runs the model.  This launches the executable and passes in the user's
   * chosen file.  This function will display any errors that occurred.
   * @param oWindow MainWindow, for displaying messages.
   * @param sFileName String Filename to run.
   * @param iNumStepsToRun Number of timesteps to run.  Set to 0 if the model
   * is to run to conclusion.
   */
  public void run(final MainWindow oWindow, final String sFileName, final int iNumStepsToRun) {

    if (m_bIsPaused == true) {
      try {
        //Send a message to the process to keep going
        BufferedWriter oIn = new BufferedWriter(new 
            OutputStreamWriter(m_oProcess.getOutputStream()));
        if (iNumStepsToRun > 0) {
          oIn.write("run " + iNumStepsToRun);
        } else {
          oIn.write("run");
        }
        oIn.newLine();
        oIn.flush();
        m_bIsPaused = false;
        return;
      } catch (IOException e) {
          ErrorGUI oHandler = new ErrorGUI(oWindow);
          oHandler.writeErrorMessage(new ModelException(ErrorGUI.BAD_FILE,
              "JAVA",
              "I couldn't run the model.  Message: " + e.getMessage()));
        }
    }
    m_bIsDone = false;
    m_bIsOK = false;
    m_bIsPaused = false;
    m_sMessage = null;

    final SwingWorker worker = new SwingWorker() {
      public Object construct() {

        try {

          //Get the main project directory - this property is set by the
          //.exe wrapper
          String sPath = System.getProperty("SORTIE_PATH");
          String sSeparator = System.getProperty("file.separator");
          if (sPath == null) {
            sPath = "";
          }
          else {
            if (sPath.endsWith(sSeparator) == false) {
              sPath += sSeparator;
            }
          }

          //Format the command to run the core model
          Runtime oRuntime = Runtime.getRuntime();
          String[] p_sCommands = null;
          if (iNumStepsToRun > 0) {
            p_sCommands = new String[] {
                sPath + "bin" + sSeparator + "coremodel.exe", sFileName,
                String.valueOf(iNumStepsToRun)};
          }
          else {
            p_sCommands = new String[] {
                sPath + "bin" + sSeparator + "coremodel.exe", sFileName};
          }
          
          //Delete any existing messages for pausing or stopping
          new File(sPath + sSeparator + "bin" + sSeparator + "messages.txt").delete();
          
          m_oProcess = oRuntime.exec(p_sCommands);

          //Capture the standard output and error stream
          BufferedReader oOut = new BufferedReader(new 
              InputStreamReader(m_oProcess.getInputStream()));
          BufferedReader oErr = new BufferedReader(new 
              InputStreamReader(m_oProcess.getErrorStream()));
          String sOutLine = null, sErrLine = null;

          //Empty the output stream to the messages string - overwrite the
          //last message - we don't have to display them all if they come in
          //too fast
          while ( (sOutLine = oOut.readLine()) != null) {
            if (sOutLine.length() > 0) {
              m_sMessage = sOutLine;
              //Check the message.  If it contains the word "paused", the
              //model is pausing.  Set our paused flag to true.
              if (m_sMessage.indexOf("paused") > -1) {
                m_bIsPaused = true;
              }
            }
          }

          //Empty the error stream to displayed exceptions
          while ( (sErrLine = oErr.readLine()) != null) {
            if (sErrLine.length() > 0) {
              //Reset all flags
              m_bIsOK = false;
              m_bIsDone = true;
              //Display the error to the user
              ErrorGUI oHandler = new ErrorGUI(oWindow);
              String sMessage = "An internal model error has occurred.\n";
              sMessage = sMessage + "Please report the following to tech " +
                  "support, and be prepared to send in the parameter file " +
                  "you are currently using.\n";
              sMessage = sMessage + sErrLine;
              oHandler.writeErrorMessage(new ModelException(ErrorGUI.BAD_DATA,
                  "JAVA", sMessage));
            }
          }

          //Allow the process to end
          int iReturnVal = m_oProcess.waitFor();
          if (iReturnVal != 0) {
            ErrorGUI oHandler = new ErrorGUI(oWindow);
            String sMessage =
                "There was a problem with the model run.  Code:  " +
                String.valueOf(iReturnVal) + ".\n";
            oHandler.writeErrorMessage(new ModelException(ErrorGUI.BAD_DATA,
                "JAVA", sMessage));
          }
          else {
            m_bIsOK = true;
          }
          m_bIsDone = true;
          m_oProcess = null;

        }
        catch (IOException e) {
          ErrorGUI oHandler = new ErrorGUI(oWindow);
          oHandler.writeErrorMessage(new ModelException(ErrorGUI.BAD_FILE,
              "JAVA",
              "I couldn't run the model.  Message: " + e.getMessage()));
        }
        catch (java.lang.InterruptedException e) {
          ErrorGUI oHandler = new ErrorGUI(oWindow);
          oHandler.writeErrorMessage(new ModelException(ErrorGUI.BAD_FILE,
              "JAVA",
              "I couldn't run the model.  Message: " + e.getMessage()));
        }
        return null;
      }
    };
    worker.start();
  }

  /**
   * Gets any message waiting in the queue from the model run.  This will
   * erase the message after sending it to ensure that messages can be uniquely
   * identified.
   * @return String The last message sent from the C++ program.
   */
  public String getMessage() {
    String sMessage = m_sMessage;
    m_sMessage = "";
    return sMessage;
  }

  /**
   * Gets whether or not the currently executing model run is finished.
   * @return boolean True if complete, false if still executing.
   */
  public boolean isDone() {
    return m_bIsDone;
  }

  /**
   * Gets whether or not there is a current run that is paused.
   * @return boolean True if there is a paused in-progress run, false otherwise.
   */
  public boolean isPaused() {
    return m_bIsPaused;
  }

  /**
   * Hard-kills any currently running model process.
   */
  public void killProcess() {
    if (m_oProcess != null) {
      m_oProcess.destroy();
    }
  }

  /**
   * Gets whether or not the last executed model run finished without errors.
   * @return boolean True if it finished without errors, false otherwise.
   */
  public boolean isOK() {
    return m_bIsOK;
  }
}
