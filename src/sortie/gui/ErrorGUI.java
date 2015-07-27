package sortie.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

import sortie.data.simpletypes.ModelException;

/**
 * This class handles all error message writing.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 * @todo Put in error writing functions for Java errors
 * @todo Put in documentation
 * If you wish to simply display an error message, set the calling function to
 * "JAVA".  Otherwise you will get a default error message according to the
 * error code.
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>July 19, 2004:  Expanded the number of containers this class can work
 *                     with (LEM)
 */
public class ErrorGUI {

  /**
   * Constructor
   * @param oParent A window over which to display messages
   *
   * <br>Edit history:
   * <br>------------------
   * <br>April 28, 2004: Submitted in beta version (LEM)
   */
  public ErrorGUI(JFrame oParent) {
    m_oParent = oParent;
  }

  /**
   * Constructor
   * @param oParent A window over which to display messages
   */
  public ErrorGUI(javax.swing.JInternalFrame oParent) {
   m_oParent = oParent;
  }

  /**
   * Constructor
   * @param oParent A window over which to display messages
   */
  public ErrorGUI(JDialog oParent) {
   m_oParent = oParent;
  }

  /**Parent container to display error messages in*/
  private java.awt.Component m_oParent;

  //The error codes - transcribed from C++ file Messages.h
  public final static int UNKNOWN = 0; /**<Message of unknown type*/
  public final static int NO_MESSAGE = 1; /**<You can pass no message*/

//Commands - these will probably never be used as errors
  public final static int PAUSE_RUN = 2; /**<Request to pause run*/
  public final static int RUN = 3; /**<Request to run model*/
  public final static int INPUT_FILE = 4; /**<Request to input file*/
  public final static int QUIT = 5; /**<Request to quit model*/

//For communicating model status
  public final static int MODEL_READY = 6; /**<Model is in ready state*/
  public final static int MODEL_NOT_READY = 7;
      /**<Model is not in ready state*/
  public final static int MODEL_PAUSED = 8; /**<Model is paused*/
  public final static int RUN_COMPLETE = 9; /**<Requested run is complete*/
  public final static int COMMAND_DONE = 10;
      /**<Model is finished executing command*/
  public final static int INFO = 11;
      /**<Message in the strMoreInfo string will be
    passed to the user*/

//Runtime error codes
 public final static int BAD_ARGUMENT = 12;
     /**<A bad or missing argument has been passed
    from the interface for a command*/
 public final static int BAD_COMMAND = 13;
     /**<A bad command has been passed from the
    interface*/
 public final static int CANT_FIND_OBJECT = 14;
     /**<An object could not be found*/
  public final static int TREE_WRONG_TYPE = 15;
      /**<A tree was not of an expected type*/
  public final static int ACCESS_VIOLATION = 16;
      /**<An access violation occurred*/

//File error codes
  public final static int BAD_FILE = 17; /**<Bad file name or path - couldn't
    open the file*/
 public final static int BAD_FILE_TYPE = 18;
     /**<A file was not an expected type*/
  public final static int BAD_XML_FILE = 19;
      /**<XML file is malformed or invalid*/
  public final static int NEED_FILE = 20;
      /**<Expected file name and didn't get one*/
  public final static int FILE_TOO_OLD = 26; /**<File is too old to use*/

//Data errors - add'l data should have name of data piece
  public final static int DATA_MISSING = 21;
      /**<Couldn't find needed data in file*/
  public final static int BAD_DATA = 22;
      /**<Data was scrambled, of incorrect type, or
    otherwise invalid*/
 public final static int DATA_READ_ONLY = 23;
     /**<Data isn't accessible for writing*/
  public final static int ILLEGAL_OP = 24; /**<Illegal operation*/

  //GUI errors or ones that don't come from the core code
  public final static int TAB_FILE_PREMATURE_END = 25;
      /**<Unexpected end to tab-format file*/

  /**
   * Displays a message to the screen.
   * @param sMsg Message to display.
   */
  private void showError(String sMsg) {
    //Break up the string into roughly 75 char lengths with newlines in between
    int iLoop = 75, iInsertPos;
    while (iLoop < sMsg.length()) {
      iInsertPos = sMsg.indexOf(" ", iLoop);
      if (iInsertPos == -1) break;
      //Make sure there's not already a newline in the region we're considering
      if (-1 == sMsg.substring(iLoop - 75, iInsertPos).indexOf("\n")) {
        sMsg = sMsg.substring(0, iInsertPos) + "\n" +
            sMsg.substring(iInsertPos + 1);
      }
      iLoop += 75;
    }
    JOptionPane.showMessageDialog(m_oParent, sMsg, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * This function is the one-stop shop for writing error messages to screen
   * @param oErr Error to display
   */
  public void writeErrorMessage(ModelException oErr) {
    if (oErr.sCallingFunction.equals("JAVA")) {
      writeJavaError(oErr);
      return;
    }

    if (BAD_FILE == oErr.iErrorCode) {
      writeBadFile(oErr);
    }
    else if (BAD_FILE_TYPE == oErr.iErrorCode) {
      writeBadFileType(oErr);
    }
    else if (BAD_XML_FILE == oErr.iErrorCode) {
      writeBadXMLFile(oErr);
    }
    else if (DATA_MISSING == oErr.iErrorCode) {
      writeDataMissingFromFile(oErr);
    }
    else if (BAD_DATA == oErr.iErrorCode) {
      writeBadData(oErr);
    }
    else if (ILLEGAL_OP == oErr.iErrorCode) {
      writeIllegalOp(oErr);
    }
    else if (DATA_READ_ONLY == oErr.iErrorCode) {
      writeDataReadOnly(oErr);
    }
    else if (CANT_FIND_OBJECT == oErr.iErrorCode) {
      writeCantFindObject(oErr);
    }
    else if (TREE_WRONG_TYPE == oErr.iErrorCode) {
      writeTreeWrongType(oErr);
    }
    else if (ACCESS_VIOLATION == oErr.iErrorCode) {
      writeAccessViolation(oErr);
    }
    else if (TAB_FILE_PREMATURE_END == oErr.iErrorCode) {
      writeBadTabFile(oErr);
    }
    else if (FILE_TOO_OLD == oErr.iErrorCode) {
      WriteFileTooOld(oErr);
    }
    else if (UNKNOWN == oErr.iErrorCode) {
      writeUnknownError(oErr);
    }
    else {
      writeUnknownError(oErr);
    }
  }

  /**
   * This type of error is called when a file cannot be opened or read
   * @param oErr Error to display
   */
  private void writeBadFile(ModelException oErr) {
    String message;
    message = "I could not open or read a needed file";
    if ("" != oErr.getMessage()) { //there's a file name
      message = message + ".  The filename is:  ";
      message = message + oErr.getMessage();
    }
    message = message + ".  The file may be in use.";
    showError(message);
  }

  /**
   * This is called when an XML file (probably parameter file) is not
   * well-formed or it's invalid - in other words, the parser chokes on it
   * @param oErr Error to display
   */
  private void writeBadXMLFile(ModelException oErr) {
    String message =
        "I could not read an XML input file.  The format was invalid";
    if ("" != oErr.getMessage()) { //there's a filename
      message = message + ".  The filename is:  ";
      message = message + oErr.getMessage();
    }
    message = message + ".  Please consult the help for more information.";
    showError(message);

  }

  /**
   * This is called when a behavior can't find data it needs
   * @param oErr Error to display
   */
  private void writeDataMissingFromFile(ModelException oErr) {
    String message = "The parameter file is missing data";
    if ("" != oErr.getMessage()) {
      message = message + ".  The XML tag name of the missing data is:  ";
      message = message + oErr.getMessage();
    }
    message = message + ".  Please consult the help for more information.";
    showError(message);
  }

  /**
   * This is called when a file type that the model wasn't expecting is passed.
   * @param oErr Error to display
   */
  private void writeBadFileType(ModelException oErr) {
    String message = "I do not recognize a file that was given to me";
    if ("" != oErr.getMessage()) {
      message = message + ".  The file name is:  ";
      message = message + oErr.getMessage();
    }
    message = message + ".  Please try another file.";
    showError(message);
  }

  /**
   * This is a panic error when a function in the model gets bad data -
   * nothing the user can do about this one
   * @param oErr Error to display
   */
  private void writeBadData(ModelException oErr) {
    String message = "An internal model error has occurred.\n";
    message = message + "Please report the following to tech support, and be prepared to send in the parameter file you are currently using.\n";
    message = message + "The error code is BAD_DATA.  ";
    message = message + "The function which threw the error is ";
    message = message + oErr.sCallingFunction;
    message = message + ".  The error message is ";
    message = message + oErr.getMessage();
    showError(message);
  }

  /**
   * An illegal operation was attempted in the code - nothing the user can do
   * about this one
   * @param oErr Error to display
   */
  private void writeIllegalOp(ModelException oErr) {
    String message = "An internal model error has occurred.\n";
    message = message + "Please report the following to tech support, and be prepared to send in the parameter file you are currently using.\n";
    message = message + "The error code is ILLEGAL_OP.  ";
    message = message + "The function which threw the error is ";
    message = message + oErr.sCallingFunction;
    message = message + ".  The error message is ";
    message = message + oErr.getMessage();
    showError(message);
  }

  /**
   * Some model object tried to poach somebody else's data, or something -
   * anyway, it's yet another internal thing the user can't do anything about
   * @param oErr Error to display
   */
  private void writeDataReadOnly(ModelException oErr) {
    String message = "An internal model error has occurred.\n";
    message = message + "Please report the following to tech support, and be prepared to send in the parameter file you are currently using.\n";
    message = message + "The error code is DATA_READ_ONLY.  ";
    message = message + "The function which threw the error is ";
    message = message + oErr.sCallingFunction;
    message = message + ".  The error message is ";
    message = message + oErr.getMessage();
    showError(message);
  }

  /**
   * Something can't be found.  Could be an internal error, or could refer
   * to library setup.
   * @param oErr Error to display
   */
  private void writeCantFindObject(ModelException oErr) {
    String message = "An internal model error has occurred.\n";
    message = message +
        "You may be able to fix this problem by re-installing.\n";
    message = message + "Please report the following to tech support, and be prepared to send in the parameter file you are currently using.\n";
    message = message + "The error code is CANT_FIND_OBJECT.  ";
    message = message + "The function which threw the error is ";
    message = message + oErr.sCallingFunction;
    message = message + ".  The error message is ";
    message = message + oErr.getMessage();
    showError(message);
  }

  /**
   * An internal error - some behavior got a tree type they didn't expect
   * @param oErr Error to display
   */
  private void writeTreeWrongType(ModelException oErr) {
    String message = "An internal model error has occurred.\n";
    message = message + "Please report the following to tech support, and be prepared to send in the parameter file you are currently using.\n";
    message = message + "The error code is TREE_WRONG_TYPE.  ";
    message = message + "The function which threw the error is ";
    message = message + oErr.sCallingFunction;
    message = message + ".  The error message is ";
    message = message + oErr.getMessage();
    showError(message);
  }

  /**
   * Access violation error - right now nobody calls this
   * @param oErr Error to display
   */
  private void writeAccessViolation(ModelException oErr) {
    String message = "An internal model error has occurred.\n";
    message = message + "Please report the following to tech support, and be prepared to send in the parameter file you are currently using.\n";
    message = message + "The error code is ACCESS_VIOLATION.  ";
    message = message + "The function which threw the error is ";
    message = message + oErr.sCallingFunction;
    message = message + ".  The error message is ";
    message = message + oErr.getMessage();
    showError(message);
  }

  /**
   * Unknown error type.
   * @param oErr Error to display
   */
  private void writeUnknownError(ModelException oErr) {
    String message = "An internal model error has occurred.\n";
    message = message + "Please report the following to tech support, and be prepared to send in the parameter file you are currently using.\n";
    message = message + "The error code is UNKNOWN_ERROR.  ";
    message = message + "The function which threw the error is ";
    message = message + oErr.sCallingFunction;
    message = message + ".  The error message is ";
    message = message + oErr.getMessage();
    showError(message);
  }

  /**
   * Write error about a problem with a tab-delimited input file.
   * @param oErr Error to display
   */
  private void writeBadTabFile(ModelException oErr) {
    String message = "The file " + oErr.getMessage() +
        " terminated unexpectedly.  Please check it and try again.\n";
    showError(message);
  }

  /**
   * Write an error that was thrown in the Java code, with a user message
   * which needs no additional help or formatting
   * @param oErr Error to display
   */
  private void writeJavaError(ModelException oErr) {
    String message = oErr.getMessage();
    showError(message);
  }

  /**
   * Write that a file was too old to use
   * @param oErr Error to display
   */
  private void WriteFileTooOld(ModelException oErr) {
    String message = oErr.getMessage();
    showError(message);
  }
}
