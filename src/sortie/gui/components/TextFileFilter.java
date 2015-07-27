package sortie.gui.components;

import javax.swing.filechooser.*;
import java.io.File;

/**
 * File filter for text files.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */

public class TextFileFilter
    extends FileFilter {
  /**
   * Whether or not to accept any given file and show it in the file
   * chooser.
   * @param oPathname File's path and name.
   * @return True if the file is to be shown in the chooser, and false if it
   * is not.
   */
  public boolean accept(File oPathname) {
    if (oPathname.isDirectory()) {
      return true;
    }

    String sFile = oPathname.getAbsolutePath();
    if (sFile != null) {
      if (sFile.endsWith(".txt")) {
        return true;
      }
      else {
        return false;
      }
    }

    return false;

  }

  /**
   * Gets the file descriptor string to be shown in the file chooser.
   * @return File descriptor string.
   */
  public String getDescription() {
    return "Text files";
  }
}
