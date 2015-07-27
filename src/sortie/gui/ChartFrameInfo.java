package sortie.gui;

import sortie.datavisualizer.DataFileManager;

/**
 * This class bundles information and methods handy when dealing with chart 
 * frames. Objects of this class can be used in a combo box object, because they 
 * can produce a nice display string. 
 * @author lora
 *
 */
public class ChartFrameInfo {
  
  /**The data manager associated with this chart.*/
  public DataFileManager m_oManager;
  
  /**This chart's name.*/
  public String m_sChartName;
  
  /**The filename that this chart is from. */
  public String m_sFileName;
  
  /**
   * Constructor.
   * @param oManager The data manager associated with this chart.
   * @param sChartName This chart's name. 
   * @param sFileName The file name that this chart is from.
   */
  public ChartFrameInfo(DataFileManager oManager, String sChartName, 
      String sFileName) {
    m_oManager = oManager;
    m_sChartName = sChartName;
    m_sFileName = sFileName;
  }

  /**
   * Produces a display string from the chart's name and file name. It is maxed 
   * at 63 characters.
   * @return Display string.
   */
  public String toString() {
    String sText = m_sFileName + ": " + m_sChartName;
    if (sText.length() > 60) {
      //Take the LAST 60
      sText = sText.substring(sText.length() - 60);
      return "..." + sText;
    }
    else {
      return sText;
    }     
  }
  
  /**
   * Tests an object of this class for equality with another object. If the 
   * chart name and file name match, this is considered equality.
   * @return True if the objects are equal, false if they are not.
   */
  public boolean equals(Object oObj) {
    if (oObj instanceof ChartFrameInfo) {
      ChartFrameInfo oObj2 = (ChartFrameInfo) oObj;
      if (oObj2.m_sChartName.equals(m_sChartName) && 
          oObj2.m_sFileName.equals(m_sFileName)) return true;
    }
    return false;
  }
}