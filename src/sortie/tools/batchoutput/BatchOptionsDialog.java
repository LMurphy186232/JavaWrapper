package sortie.tools.batchoutput;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;
import sortie.gui.components.TextFileFilter;

/**
 * This class collects options needed for a data request for batch writing. It
 * accepts a panel of options so that a data request can add extra options to 
 * collect.
 */
public class BatchOptionsDialog extends JDialog implements ActionListener {
  
  /** Master calling window.*/
  private OptionChoosingWindow m_oMaster;
  
  private JPanel m_jPanel;
  
  /** Graph name string.*/
  private String m_sGraphName;
  
  /** Text field holding the root file path and name*/
  private JTextField m_jBatchFileEdit = new JTextField();
  
  /** Whether or not the user canceled the dialog. */
  private boolean m_bCanceled = false;
  
  /**
   * Constructor. Creates the GUI that shows the user a request for a file name 
   * plus any optional additional controls.
   * @param sGraphName Graph name.
   * @param oMaster Controlling window for this.
   * @param jPanel Panel containing any extra controls (which should be named 
   * for later retrieval). Null is acceptable if there are no extra controls.
   */
  public BatchOptionsDialog(String sGraphName, OptionChoosingWindow oMaster,
      JPanel jPanel) {    
    
    m_oMaster = oMaster;
    m_sGraphName = sGraphName;
    m_jPanel = jPanel;
    
    setTitle(sGraphName);
    
    JPanel jBatchFileNamePanel = new JPanel();
    jBatchFileNamePanel.setLayout(new BoxLayout(jBatchFileNamePanel,
                                                BoxLayout.PAGE_AXIS));
    //Label for batch file name
    JLabel jLabel = new JLabel("Location and file name root for this type of output:");
    jLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jBatchFileNamePanel.add(jLabel);
    
    JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    m_jBatchFileEdit.setFont(new SortieFont());
    m_jBatchFileEdit.setPreferredSize(new java.awt.Dimension(300, 25));

    JButton jButton = new JButton("Browse");
    jButton.setFont(new SortieFont());
    jButton.addActionListener(this);
    jButton.setActionCommand("Browse");

    jTempPanel.add(m_jBatchFileEdit);
    jTempPanel.add(jButton);
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jBatchFileNamePanel.add(jTempPanel);
    
    this.getContentPane().setLayout(new BorderLayout());
    if (jPanel == null) {          
      this.getContentPane().add(jBatchFileNamePanel, BorderLayout.CENTER);
    } else {
      jBatchFileNamePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      this.getContentPane().add(jBatchFileNamePanel, BorderLayout.NORTH);
      this.getContentPane().add(jPanel, BorderLayout.CENTER);
    }
    this.getContentPane().add(new OKCancelButtonPanel(this, null, ""), 
        BorderLayout.SOUTH);
  }
  
  /**
   * Controls actions for this window. This will not dispose of itself. That is 
   * up to the calling object.
   * @param oEvent ActionEvent.
   */
  public void actionPerformed(ActionEvent oEvent) {
    String sCommand = oEvent.getActionCommand();
    if (sCommand.equals("Browse")) {
      ModelFileChooser jChooser = new ModelFileChooser();
      
      jChooser.setFileFilter(new TextFileFilter());
      int returnVal = jChooser.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        //User chose a file - put the filename in the edit box
        File oFile = jChooser.getSelectedFile();
        String sFileName = oFile.getAbsolutePath();

        //Does this file already exist?
        if (oFile.exists()) {
          returnVal = JOptionPane.showConfirmDialog(this,
              "Do you wish to overwrite the existing file?",
              "SORTIE-ND",
              JOptionPane.YES_NO_OPTION);
          if (returnVal == JOptionPane.YES_OPTION) {
            m_jBatchFileEdit.setText(sFileName);
          }
        }
        else {
          //File didn't exist
          m_jBatchFileEdit.setText(sFileName);
        }
      }
    }
    else {
      if (sCommand.equals("Cancel")) {    
        m_bCanceled = true;      
      } else {
        if (m_jBatchFileEdit.getText().length() == 0) {
          JOptionPane.showMessageDialog(this, "Please enter a filename root.");
          return;
        }
      }
      m_oMaster.dialogAdd(this);
    }
  }
  
  public String getFilename() {return m_jBatchFileEdit.getText();}
  public boolean canceled() {return m_bCanceled;}
  public String getGraphName() {return m_sGraphName;}
  public JPanel getExtraOptionsPanel() {return m_jPanel;}

}
