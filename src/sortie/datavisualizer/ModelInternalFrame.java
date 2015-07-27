package sortie.datavisualizer;

import javax.swing.JInternalFrame;
import sortie.gui.ChartFrameInfo;

/**
 * Ensures consistent behavior across graph windows.
 * Copyright: Copyright (c) Charles D. Canham 2012
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */
public class ModelInternalFrame extends JInternalFrame {
  

  /**This window's legend.*/
  private JInternalFrame m_jLegend;
  
  /**Chart information associated with this frame.*/
  private ChartFrameInfo m_oChartInfo;

  /**
   * Constructor.  Creates a window that is iconifiable, resizable, and
   * closeable.
   * @param sTitle String Title string.
   * @param jLegend JInternalFrame Legend for this frame.
   */
  public ModelInternalFrame(String sTitle, JInternalFrame jLegend) {
    super(sTitle, true, true, true, true);
    m_jLegend = jLegend;
  }
  
  /**
   * Set the chart frame information object for this frame.
   * @param oInfo Chart frame information object.
   */
  public void setChartFrameInfo(ChartFrameInfo oInfo) {m_oChartInfo = oInfo;}
  
  /**
   * Get the chart frame information object for this frame.
   * @return Chart frame information object.
   */
  public ChartFrameInfo getChartFrameInfo() {return m_oChartInfo;}
  
  /**
   * Get the legend for this frame.
   * @return Legend.
   */
  public JInternalFrame getLegend() {return m_jLegend;}

}
