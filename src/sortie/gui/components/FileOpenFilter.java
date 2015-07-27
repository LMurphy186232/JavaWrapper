package sortie.gui.components;

import javax.swing.filechooser.*;
import java.io.File;

/**
* This provides a file filter which shows all potential SORTIE file types:
* .xml, .hvr, .txt, and .gz.tar files.
* <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
* <p>Company: Cary Institute of Ecosystem Studies</p>
* @author Lora E. Murphy
* @version 1.0
*
* <br>Edit history:
* <br>------------------
* <br>March 28, 2005: Created (LEM)
* <br>June 7, 2005:  Removed support for .par files (LEM)
*/
public class FileOpenFilter
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

    String sFileName = oPathname.getName().toLowerCase();
    if (sFileName != null) {
      if (sFileName.endsWith(".xml") ||
          sFileName.endsWith(".txt") ||
          sFileName.endsWith(".hvr") ||
          sFileName.endsWith(".gz.tar")) {
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
    return "SORTIE-ND Files";
  }
}
