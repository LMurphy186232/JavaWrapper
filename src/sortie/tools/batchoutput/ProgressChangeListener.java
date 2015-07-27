package sortie.tools.batchoutput;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class listens for published progress reports and updates the progress 
 * dialog accordingly.
 */
public class ProgressChangeListener implements PropertyChangeListener {
  ProgressDialog m_oDialog;
  ProgressChangeListener(ProgressDialog oDialog) {
    this.m_oDialog = oDialog;
  }  
  public void propertyChange(PropertyChangeEvent e) {
    if ("progress".equals(e.getPropertyName())) {
      m_oDialog.m_jProgressBar.setValue((Integer)e.getNewValue());
      m_oDialog.m_jProgress.setText("Completed file " + 
          String.valueOf(e.getNewValue()));
    }
  }
}