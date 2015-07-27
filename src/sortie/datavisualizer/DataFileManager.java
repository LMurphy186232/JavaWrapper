package sortie.datavisualizer;

import javax.swing.JInternalFrame;
import javax.swing.JPopupMenu;

import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.simpletypes.ModelException;

import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
* This class manages a single data file for the data visualizer.  It is
* responsible for parsing the data out of the file, telling the data
* visualizer what charts are available, and providing requested charts.
*
* Each DataFileManager object keeps track of the charts it has created and
* placed in JInternalFrame objects.  These objects are under the direct
* management of the entitity to which they were originally passed when
* DrawChart() was called to create them, but the DataFileManager object
* retains the ability to update them per user request.
* <p>Copyright: Copyright (c) Charles D. Canham 2004</p>
* <p>Company: Cary Institute of Ecosystem Studies</p>
* @author Lora E. Murphy
* @version 1.0
*
* <br>Edit history:
* <br>------------------
* <br>April 28, 2004: Submitted in beta version (LEM)
* <br>November 18, 2004:  Added real-time data visualization (LEM)
* <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
*/

abstract public class DataFileManager {
  protected DataVisualizerManager m_oManager; /**<Manager object*/
  protected String m_sFilename; /**<File under management*/
  protected ArrayList<JInternalFrame> mp_oCharts = new ArrayList<JInternalFrame>(0); /**<All charts under management*/
  protected Legend m_oLegend; /**<The legend for this file*/
  
  /** Text equivalents of dead codes.*/
  protected String[] mp_sDeadCodeNames = new String[OutputBehaviors.NUMCODES];

  /**
   * Get the legend for this file.
   * @return The legend.
   */
  public Legend getLegend() {
    return m_oLegend;
  }

  /**
   * Gets the number of open charts for this file.
   * @return int Number of open charts for this file.
   */
  public int getNumberOpenCharts() {
    return mp_oCharts.size();
  }

  /**
   * Closes all the open charts for this window.
   */
  public void closeAllCharts() {
    JInternalFrame jFrame = null;
    int i, iNumOpenCharts = mp_oCharts.size();

    if (iNumOpenCharts == 0) return;

    for (i = iNumOpenCharts-1; i >= 0; i--) {
      Object oElement = mp_oCharts.get(i);
      if (oElement != null) {
        jFrame = (JInternalFrame) oElement;
        jFrame.setVisible(false);
        jFrame.dispose();
        mp_oCharts.remove(i);
      }
    }

    m_oLegend.setVisible(false);
    m_oLegend.dispose();
  }

  /**
   * Any clean-up tasks that must be done can be put here.  This will be
   * called before the object is destroyed.
   */
  public void cleanUp() throws ModelException {;}

  /**
   * Constructor.
   * @param oManager Data visualizer manager that manages this object.
   * @param sFileName  File name for this manager to manage.
   */
  public DataFileManager(DataVisualizerManager oManager, String sFileName) {
    m_oManager = oManager;
    m_sFilename = sFileName;
    mp_sDeadCodeNames[OutputBehaviors.NOTDEAD] = "";
    mp_sDeadCodeNames[OutputBehaviors.HARVEST] = "Harvest";
    mp_sDeadCodeNames[OutputBehaviors.NATURAL] = "Natural";
    mp_sDeadCodeNames[OutputBehaviors.DISEASE] = "Disease";
    mp_sDeadCodeNames[OutputBehaviors.FIRE] = "Fire";
    mp_sDeadCodeNames[OutputBehaviors.INSECTS] = "Insect";
    mp_sDeadCodeNames[OutputBehaviors.STORM] = "Storm";
  }

  /**
   * Gets the file being managed.
   * @return File name of managed file.
   */
  public String getFileName() {
    return m_sFilename;
  }

  /**
   * Gets a list of the line graphs this object is capable of drawing
   * @param sActionListener Action listener that will respond to menu events
   * @return Menu of line graph labels, submenus allowed, null if there are no 
   * options
   */
  abstract public JPopupMenu getLineGraphOptions(ActionListener sActionListener);

  /**
   * Gets a list of maps this object is capable of drawing
   * @param sActionListener Action listener that will respond to menu events
   * @return Menu of map labels, submenus allowed, null if there are no 
   * options
   */
  abstract public JPopupMenu getMapOptions(ActionListener sActionListener);

  /**
   * Gets a list of histograms this object is capable of drawing
   * @param sActionListener Action listener that will respond to menu events
   * @return Menu of histogram labels, submenus allowed, null if there are no 
   * options
   */
  abstract public JPopupMenu getHistogramOptions(ActionListener sActionListener);

  /**
   * Gets a list of tables this object is capable of drawing
   * @param sActionListener Action listener that will respond to menu events
   * @return Menu of table labels, submenus allowed, null if there are no 
   * options
   */
  abstract public JPopupMenu getTableOptions(ActionListener sActionListener);

  /**
   * Creates a requested chart as a JInternalFrame.  The JInternalFrame will not
   * be packed or set to visible - it will simply have its GUI components.  If
   * the chart requested already exists, the existing chart is returned.
   * @param sGraphName The string name of the chart - should be from one of the
   * "getXOptions()" methods
   * @return The chart window, or null if no such chart can be drawn.
   * @throws ModelException if there are any problems drawing the chart.
   */
  abstract protected JInternalFrame drawChart(String sGraphName) throws
      ModelException;

  /**
   * Creates a requested chart as a JInternalFrame.  If the chart already exists,
   * a new one is not created; the existing one is returned.  If the chart is
   * new, the JInternalFrame will not be packed or set to visible - it will
   * simply have its GUI components.
   * @param sGraphName The string name of the chart - should be from one of the
   * "getXOptions()" methods
   * @return The chart window, or null if no such chart can be drawn.
   * @throws ModelException if there are any problems drawing the chart
   */
  public JInternalFrame createNewChart(String sGraphName) throws ModelException {

    //Go through existing charts to see if any are already this graph name
    String sTitle;
    int i;
    JInternalFrame oChartWindow;

    for (i = 0; i < mp_oCharts.size(); i++) {

      if (mp_oCharts.get(i) != null) {
        oChartWindow = mp_oCharts.get(i);
        //Get the title and trim off the filename
        sTitle = oChartWindow.getTitle();
        int iPos = sTitle.lastIndexOf(" - ");
        if (iPos > -1) {
          sTitle = sTitle.substring(0, sTitle.lastIndexOf(" - "));
        }
        if (sTitle.equalsIgnoreCase(sGraphName)) {
          return oChartWindow;
        }
      }
    }

    //The old chart did not exist - create a new one
    oChartWindow = drawChart(sGraphName);

    //Add it to the list of charts under management by this file
    mp_oCharts.add(oChartWindow);

    return oChartWindow;
  }

  /**
   * Prompts the redrawing of all open charts.
   * @throws ModelException wrapping other exceptions
   */
  abstract public void updateCharts() throws ModelException;

  /**
  * Prompts the redrawing of open charts for the current run.  The data
  * file manager should search for and display the most recent information.
  * @throws ModelException if there is a problem drawing the charts.
  */
  abstract public void updateCurrentRunCharts() throws ModelException;

}
