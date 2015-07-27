package sortie.fileops;

import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;

import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;

/**
 * This provides common file-reading functions for reading tab-delimited files.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 */

public class ModelFileFunctions {
  /**
   * Advances a file stream to the beginning of the next line.
   *
   * @param in The file stream to advance.
   *
   * @throws ModelException with error code TAB_PAR_FILE_PREMATURE_END if the
   * input stream is currently at end of file.  Reaching the end of file after
   * that point will not cause this exception to be thrown.
   */
  static public void SkipLine(FileReader in) throws ModelException {
    int iTestCh;

    try {
      //Get the first character - if it's unexpectedly end of file throw
      //an exception
      iTestCh = in.read();
      if ( -1 == iTestCh) {
        ModelException exp = new ModelException();
        exp.iErrorCode = ErrorGUI.TAB_FILE_PREMATURE_END;
        throw (exp);

      }
      while (iTestCh != '\n' && iTestCh != -1) {
        iTestCh = in.read();
      }
    }
    catch (java.io.IOException e) {
      ModelException exp = new ModelException();
      exp.iErrorCode = ErrorGUI.TAB_FILE_PREMATURE_END;
      throw (exp);
    }
  }

  /**
   * Reads a line from a tab-delimited text file.  Each cell in the line is
   * placed in a separate bucket of a Vector.  The file stream is then advanced
   * to the beginning of the next line.
   * @param in The file stream to read from.
   * @return The vector representing the file line.
   * @throws java.io.IOException - passthru from FileReader
   */

  static public ArrayList<String> ReadLine(FileReader in) throws IOException {
    ArrayList<String> p_oLine = new ArrayList<String>(0);
    String sData;
    int iTestCh;

    iTestCh = in.read();
    while (iTestCh != '\n' && iTestCh != -1) {

      sData = "";
      while (iTestCh != '\t' && iTestCh != '\r' && iTestCh != '\n' && iTestCh != -1) {
        sData = sData.concat(String.valueOf( (char) iTestCh));
        iTestCh = in.read();
      }

      //Throw away extra tab characters
      while (iTestCh == '\t') {
        iTestCh = in.read();
      }

      if (sData.length() > 0) {
        p_oLine.add(sData.trim());
      }

      if ('\r' == iTestCh) { //advance to '\n'
        iTestCh = in.read();
      }
    }

    return p_oLine;
  }

}
