package sortie.gui.components;

import javax.swing.filechooser.*;
import java.io.File;

/**
 * This filters to only XML files for file choosers.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */

public class XMLFileFilter
    extends FileFilter {

  /**
   * Whether or not to accept any given file and show it in the file
   * chooser.
   * @param pathname File's path and name.
   * @return True if the file is to be shown in the chooser, and false if it
   * is not.
   *
   * <br>Edit history:
   * <br>------------------
   * <br>April 28, 2004: Submitted in beta version (LEM)
   */
  public boolean accept(File pathname) {
    if (pathname.isDirectory()) {
      return true;
    }

    String extension = getExtension(pathname);
    if (extension != null) {
      if (extension.equalsIgnoreCase("xml")) {
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
   *
   * <br>Edit history:
   * <br>------------------
   * <br>April 28, 2004: Submitted in beta version (LEM)
   */
  public String getDescription() {
    return "XML files";
  }

  /**
   * I got this method from Utils.java from the java tutorial on using file
   * filtering.
   * @param f Filename
   * @return Extension
   *
   * <br>Edit history:
   * <br>------------------
   * <br>April 28, 2004: Submitted in beta version (LEM)
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