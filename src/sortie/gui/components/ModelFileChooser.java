package sortie.gui.components;

import java.io.File;

import javax.swing.JFileChooser;

/**
 * Manages directories for SORTIE.  It keeps track of a working directory and
 * sets itself to there.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class ModelFileChooser extends JFileChooser {
	
  /** The working directory where we will always start. The MainWindow class
   * sets this through a menu option. */
  public static File m_oWorkingDirectory;

  public ModelFileChooser() {
    super();
    setCurrentDirectory(m_oWorkingDirectory);
  }
}
