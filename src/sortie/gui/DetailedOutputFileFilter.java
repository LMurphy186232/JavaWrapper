package sortie.gui;

import javax.swing.filechooser.*;
import java.io.File;

/**
 * This provides a filter for data files - .gz.tar files
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */
public class DetailedOutputFileFilter
    extends FileFilter {

  /**
   * Whether or not to accept any given file and show it in the file
   * chooser.
   * @param pathname File's path and name.
   * @return True if the file is to be shown in the chooser, and false if it
   * is not.
   */
  public boolean accept(File pathname) {
    if (pathname.isDirectory()) {
      return true;
    }
    if (pathname.getName().endsWith(".gz.tar")) {
      return true;
    }
    else {
      return false;
    }

  }

  /**
   * Gets the file descriptor string to be shown in the file chooser.
   * @return File descriptor string.
   */
  public String getDescription() {
    return "Detailed output files";
  }
}
