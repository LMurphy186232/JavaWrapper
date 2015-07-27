package sortie.tools.batchoutput;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import sortie.gui.components.SortieFont;

/**
 * A small progress dialog to inform the user of file option extraction 
 * progress.
 */
public class ProgressDialog extends JDialog implements ActionListener {
  
  /** Label with verbal progress description */
  public JLabel m_jProgress;
  
  /** Progress bar */
  public JProgressBar m_jProgressBar;
  
  /** Whether or not the user canceled the process */
  public boolean m_bCanceled = false;

  /**
   * Constructor.
   * @param iMax Maximum value for the progress bar. 
   * @param sMessage Message to be displayed
   */
  public ProgressDialog(int iMax, String sMessage) {
    this.setTitle("Process Output");
    
    m_jProgress = new JLabel("Starting...");
    m_jProgress.setFont(new SortieFont());
    
    m_jProgressBar = new JProgressBar(0, iMax);
    m_jProgressBar.setValue(0);

    JPanel jMainPanel = new JPanel();
    jMainPanel.setLayout(new BoxLayout(jMainPanel, BoxLayout.PAGE_AXIS));

    JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel jLabel = new JLabel(sMessage);
    jLabel.setFont(new SortieFont());
    jLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    jTempPanel.add(jLabel);
    jMainPanel.add(jTempPanel);

    
    
    jTempPanel = new JPanel();
    jTempPanel.setLayout(new BoxLayout(jTempPanel, BoxLayout.PAGE_AXIS));
    m_jProgress.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    m_jProgressBar.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.add(m_jProgress);
    jTempPanel.add(m_jProgressBar);
    jTempPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    jMainPanel.add(jTempPanel);

    jTempPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton jButton = new JButton("Cancel");
    jButton.addActionListener(this);
    jButton.setActionCommand("Cancel");
    jTempPanel.add(jButton);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
        java.awt.Color.black));
    jMainPanel.add(jTempPanel);

    this.setContentPane(jMainPanel);
  }
  
  /**
   * Controls actions for this window.
   * 
   * @param oEvent ActionEvent.
   */
  public void actionPerformed(ActionEvent oEvent) {
    String sCommand = oEvent.getActionCommand();
    if (sCommand.equals("Cancel")) {
      m_bCanceled = true;      
    }
  }
}