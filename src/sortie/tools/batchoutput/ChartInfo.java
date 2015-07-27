/**
 * 
 */
package sortie.tools.batchoutput;

import javax.swing.JPanel;

public class ChartInfo {
  public String m_sGraphName;
  public String m_sFileName;
  public JPanel m_jExtraOptions;
  
  public ChartInfo(String sGraphName, String sFileName, JPanel jExtraOptions) {
    m_sGraphName = sGraphName;
    m_sFileName = sFileName;
    m_jExtraOptions = jExtraOptions;
  }
  
  public String toString() {return m_sGraphName;}
}