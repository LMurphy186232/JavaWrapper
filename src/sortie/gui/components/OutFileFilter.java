package sortie.gui.components;

import javax.swing.filechooser.*;
import java.io.File;

/**
 * File filter for short output files
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */

public class OutFileFilter
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

    String extension = getExtension(pathname);
    if (extension != null) {
      if (extension.equalsIgnoreCase("out")) {
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
    return "OUT files";
  }

  /** I got this method from Utils.java from the java tutorial on using file
   * filtering.
   * @param f Filename
   * @return Extension
   */
  public static String getExtension(File f) {
    String ext = null;
    String s = f.getName();
    int i = s.lastIndexOf('.');

    if (i > 0 && i < s.length() - 1) {
      ext = s.substring(i + 1).toLowerCase();
    }
    return ext;
  }
}