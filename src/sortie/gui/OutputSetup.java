package sortie.gui;


import javax.swing.*;

import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.funcgroups.output.DetailedOutput;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;


import java.awt.*;
import java.awt.event.*;

/**
 * Window used by the user to start setting up run output options.
 * <p>Copyright: Copyright (c) 2003 Charles D. Canham</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class OutputSetup
    extends JDialog
    implements ActionListener {
  
  
  
  /**OutputBehaviors object for transferring data*/
  OutputBehaviors m_oOutputBehaviors;
  
  MainWindow m_oWindow; /**<Main application window*/

  protected JLabel
      /**Label with text describing the current summary output save settings*/
      m_jShortOutputLabel,
      /**Label with text describing the current detailed output save settings*/
      m_jDetailedOutputLabel;

  /**Help ID string*/
  private String m_sHelpID = "windows.output_setup_window";

  /**
   * Constructor.
   * @param oOutputBehaviors OutputBehaviors object.
   * @param oWindow Main application window.
   */
  public OutputSetup(OutputBehaviors oOutputBehaviors, MainWindow oWindow) {
    super(oWindow, "Output Setup", true);

    m_oOutputBehaviors = oOutputBehaviors;
    m_oWindow = oWindow;

    //Help ID
    oWindow.m_oHelpBroker.enableHelpKey(this.getRootPane(), m_sHelpID, null);

    //This allows our save labels to be updated
    this.addWindowListener(new OutputSetup_this_windowAdapter(this));

    //Create the GUI
    this.setLocale(java.util.Locale.getDefault());
    this.getContentPane().setLayout(new BorderLayout());

    //Main panel with setup options
    JPanel jComponentPanel = new JPanel();
    jComponentPanel.setLayout(new BoxLayout(jComponentPanel, BoxLayout.Y_AXIS));
    jComponentPanel.setBackground(SystemColor.control);

    //Label for setup options
    JLabel jSetupLabel = new JLabel("Set up output options...");
    jSetupLabel.setFont(new java.awt.Font("Dialog", 1, 16));

    //Button which opens the dialog for summary output options
    JButton jSummaryOptionsButton = new JButton(
        "Save Summary Output File (.out)");
    jSummaryOptionsButton.setFont(new SortieFont());
    //  jSummaryOptionsButton.setPreferredSize(new Dimension(241, 29));
    jSummaryOptionsButton.setActionCommand("SummaryOptions");
    jSummaryOptionsButton.addActionListener(this);

    //Button which opens the dialog for detailed output options
    JButton jDetailedOptionsButton = new JButton("Save Detailed Output File");
    jDetailedOptionsButton.setFont(new SortieFont());
    //jDetailedOptionsButton.setPreferredSize(new Dimension(241, 29));
    jDetailedOptionsButton.setActionCommand("DetailedOutputOptions");
    jDetailedOptionsButton.addActionListener(this);

    //Label for what summary output options are set
    m_jShortOutputLabel = new JLabel("Not saving summary output");
    m_jShortOutputLabel.setFont(new SortieFont());

    //Label for what detailed output options are set
    m_jDetailedOutputLabel = new JLabel("Not saving detailed output");
    m_jDetailedOutputLabel.setFont(new SortieFont());

    jComponentPanel.add(jSetupLabel);
    jComponentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    jComponentPanel.add(jSummaryOptionsButton);
    jComponentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    jComponentPanel.add(m_jShortOutputLabel);
    jComponentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    jComponentPanel.add(jDetailedOptionsButton);
    jComponentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    jComponentPanel.add(m_jDetailedOutputLabel);
    jComponentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    this.getContentPane().add(jComponentPanel, BorderLayout.CENTER);
    this.getContentPane().add(new OKCancelButtonPanel(this,
        m_oWindow.m_oHelpBroker, m_sHelpID, false), BorderLayout.SOUTH);

  }

  /**
   * Performs the window events.
   * @param oEvent Action triggered.
   */
  public void actionPerformed(ActionEvent oEvent) {
    if (oEvent.getActionCommand().equals("OK") ||
        oEvent.getActionCommand().equals("Cancel")) {
      //Close this window
      this.setVisible(false);
      this.dispose();
    }
    else if (oEvent.getActionCommand().equals("SummaryOptions")) {
      ShortOutputFileSetup oOutputFrame = new ShortOutputFileSetup(this,
          m_oOutputBehaviors, m_oWindow);
      oOutputFrame.pack();
      oOutputFrame.setLocationRelativeTo(null);
      oOutputFrame.setVisible(true);
    }
    else if (oEvent.getActionCommand().equals("DetailedOutputOptions")) {
      //Open detailed output file setup window
      DetailedOutputFileSetup outputFrame = new DetailedOutputFileSetup(this,
          m_oOutputBehaviors, m_oWindow);
      outputFrame.pack();
      outputFrame.setLocationRelativeTo(null);
      outputFrame.setVisible(true);
    }
  }

  /**
   * Updates the saving labels
   */
  void this_windowActivated() {
    if (m_oOutputBehaviors.savingShortOutputData() == false) {
      m_jShortOutputLabel.setText("Not saving summary output");
    }
    else {
      m_jShortOutputLabel.setText("Saving summary output");
    }

    DetailedOutput oOutput = m_oOutputBehaviors.getDetailedOutput();
    if (oOutput.getNumberOfDetailedLiveTreeSettings() == 0 &&
        oOutput.getNumberOfDetailedDeadTreeSettings() == 0 &&
        oOutput.getNumberOfDetailedGridSettings() == 0) {
      m_jDetailedOutputLabel.setText("Not saving detailed output");
    }
    else {
      m_jDetailedOutputLabel.setText("Saving detailed output");
    }
  }
}

/**
 * Allows us to refresh the saving information on window focus.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
class OutputSetup_this_windowAdapter
    extends java.awt.event.WindowAdapter {
  OutputSetup adaptee; /**<Window to watch for refreshes*/

  /**
   * Constructor
   * @param adaptee OutputSetup Window to watch for refreshes
   * @return Constructor
   */
  OutputSetup_this_windowAdapter(OutputSetup adaptee) {
    this.adaptee = adaptee;
  }

  /**
   * Activates the window refresh code
   * @param e WindowEvent Ignored
   */
  public void windowActivated(WindowEvent e) {
    adaptee.this_windowActivated();
  }
}
