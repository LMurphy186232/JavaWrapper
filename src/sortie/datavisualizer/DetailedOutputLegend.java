package sortie.datavisualizer;

import javax.swing.*;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.components.ModelIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.*;


/**
 * This extends the legend class and adds some features specific to the viewing
 * of detailed output files - specifically the ability to step through
 * timesteps.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>December 8, 2004:  Added more run controls (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 */

public class DetailedOutputLegend
    extends Legend
    implements ActionListener {
  
  

  /**Label displaying current timestep*/
  private JLabel m_jCurrentTimestepLabel = new JLabel();

  /**Field for the rate at which to step or run through the detailed output
   * file*/
  private JTextField m_jRateField = new JTextField("1");

  /**Field for the user to enter a particular timestep to jump to*/
  private JTextField m_jJumpToTimestepField = new JTextField("0");

  /**The timestep of the data currently being displayed*/
  private int m_iCurrentTimestep;

  /**The maximum timestep for this file*/
  private int m_iMaxTimestep;

  /**Flag for moving forward through timesteps*/
  private final static int FORWARD = 1;

  /**Flag for moving backward through timesteps*/
  private final static int BACKWARD = 2;

  /**Flag for whether to stop when the file is running through timesteps.*/
  private boolean m_bStop = false;

  /**Flag for whether or not we're currently running*/
  private boolean m_bRunning = false;

  /**
   * Constructor.
   * @param oManager Parent detailed output file manager.
   * @param sTitle Title to display in window.
   * @param p_sSpeciesNames List of species names.
   * @param iMaxTimesteps Maximum number of timesteps in this detailed output
   * file.
   * @throws ModelException If there is a problem drawing the window.
   */
  public DetailedOutputLegend(DetailedOutputFileManager oManager, String sTitle,
                       String[] p_sSpeciesNames, int iMaxTimesteps) throws
      ModelException {

    super(oManager, sTitle, p_sSpeciesNames);

    m_iCurrentTimestep = 0;
    m_iMaxTimestep = iMaxTimesteps;

    //Get the current content pane - we're going to make a new one
    JPanel jSpeciesPanel = (JPanel)this.getContentPane();

    //Layout manager for our new content pane
    BorderLayout oLayout = new BorderLayout();

    //Create the panel that's going to be our new content pane
    JPanel jContentPane = new JPanel(oLayout);

    //Set the timestep
    m_jCurrentTimestepLabel.setText(String.valueOf(m_iCurrentTimestep) + " of " +
                                    String.valueOf(m_iMaxTimestep));
    m_jCurrentTimestepLabel.setFont(new sortie.gui.components.SortieFont());

    //*****************************************
     //Panel for detailed output navigation controls
     //*****************************************
      //Button stepping forward one timestep
    JButton jStepForwardButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.STEP_FORWARD));
    jStepForwardButton.setPreferredSize(new java.awt.Dimension(30,
        (int) jStepForwardButton.getPreferredSize().getHeight()));
    jStepForwardButton.setFont(new sortie.gui.components.SortieFont());
    jStepForwardButton.setActionCommand("StepForward");
    jStepForwardButton.setToolTipText("Step the detailed output file forward");
    jStepForwardButton.addActionListener(this);

    //Button stepping backward one timestep
    JButton jStepBackwardButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.STEP_BACKWARD));
    jStepBackwardButton.setPreferredSize(new java.awt.Dimension(30,
        (int) jStepBackwardButton.getPreferredSize().getHeight()));
    jStepBackwardButton.setFont(new sortie.gui.components.SortieFont());
    jStepBackwardButton.setActionCommand("StepBackward");
    jStepBackwardButton.setToolTipText("Step the detailed output file backward");
    jStepBackwardButton.addActionListener(this);

    //Button running forward
    JButton jRunForwardButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.RIGHT_TRIANGLE));
    jRunForwardButton.setPreferredSize(new java.awt.Dimension(30,
        (int) jRunForwardButton.getPreferredSize().getHeight()));
    jRunForwardButton.setFont(new sortie.gui.components.SortieFont());
    jRunForwardButton.setActionCommand("RunForward");
    jRunForwardButton.setToolTipText("Play the detailed output file forward");
    jRunForwardButton.addActionListener(this);

    //Button running backward
    JButton jRunBackwardButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.LEFT_TRIANGLE));
    jRunBackwardButton.setPreferredSize(new java.awt.Dimension(30,
        (int) jRunBackwardButton.getPreferredSize().getHeight()));
    jRunBackwardButton.setFont(new sortie.gui.components.SortieFont());
    jRunBackwardButton.setActionCommand("RunBackward");
    jRunBackwardButton.setToolTipText("Play the detailed output file backward");
    jRunBackwardButton.addActionListener(this);

    //Stop button
    JButton jStopButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.RECTANGLE));
    jStopButton.setPreferredSize(new java.awt.Dimension(30,
        (int) jStopButton.getPreferredSize().getHeight()));
    jStopButton.setFont(new sortie.gui.components.SortieFont());
    jStopButton.setActionCommand("Stop");
    jStopButton.setToolTipText("Stop playing the run");
    jStopButton.addActionListener(this);

    //Put together the buttons in a strip
    JPanel jButtonsPanel = new JPanel();
    jButtonsPanel.add(jStepBackwardButton);
    jButtonsPanel.add(jRunBackwardButton);
    jButtonsPanel.add(jStopButton);
    jButtonsPanel.add(jRunForwardButton);
    jButtonsPanel.add(jStepForwardButton);

    //Panel with the rate
    JPanel jRatePanel = new JPanel();
    JLabel jLabel = new JLabel("Rate:");
    jLabel.setFont(new sortie.gui.components.SortieFont());
    m_jRateField.setFont(new sortie.gui.components.SortieFont());
    m_jRateField.setPreferredSize(new java.awt.Dimension(30,
        (int) m_jRateField.getPreferredSize().getHeight()));
    jRatePanel.add(jLabel);
    jRatePanel.add(m_jRateField);
    jLabel = new JLabel(" timestep(s)");
    jLabel.setFont(new sortie.gui.components.SortieFont());
    jRatePanel.add(jLabel);

    //Panel with the "jump to timestep" control
    JPanel jJumpPanel = new JPanel();
    jLabel = new JLabel("Go to timestep:");
    jLabel.setFont(new sortie.gui.components.SortieFont());
    m_jJumpToTimestepField.setFont(new sortie.gui.components.SortieFont());
    m_jJumpToTimestepField.setPreferredSize(new java.awt.Dimension(40,
        (int) m_jJumpToTimestepField.getPreferredSize().getHeight()));
    JButton jJumpButton = new JButton("Go");
    jJumpButton.setFont(new sortie.gui.components.SortieFont());
    jJumpButton.setActionCommand("JumpToTimestep");
    jJumpButton.addActionListener(this);
    jJumpPanel.add(jLabel);
    jJumpPanel.add(m_jJumpToTimestepField);
    jJumpPanel.add(jJumpButton);

    //Panel with the timestep labels
    JPanel jTimestepPanel = new JPanel();
    jLabel = new JLabel("Showing timestep:");
    jLabel.setFont(new sortie.gui.components.SortieFont());
    jTimestepPanel.add(jLabel);
    jTimestepPanel.add(m_jCurrentTimestepLabel);

    //Panel packaging together all controls
    JPanel jAllControlsPanel = new JPanel(new java.awt.GridLayout(0, 1));
    jAllControlsPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
        Color.BLACK));
    jAllControlsPanel.add(jTimestepPanel);
    jAllControlsPanel.add(jButtonsPanel);
    jAllControlsPanel.add(jRatePanel);
    jAllControlsPanel.add(jJumpPanel);

    //Now put everything together in the content pane
    jContentPane.add(jSpeciesPanel, BorderLayout.CENTER);
    jContentPane.add(jAllControlsPanel, BorderLayout.SOUTH);

    this.setContentPane(jContentPane);
  }

  /**
   * Steps the model in the desired direction.  The model will be stepped the
   * number of timesteps indicated in the rate field.
   * @param iDirection int Either FORWARD or BACKWARD.
   * @throws ModelException if the value in the rate field is not a positive
   * number.
   */
  private void step(int iDirection) throws ModelException {
    //If we're running, stop the other thread
    if (m_bRunning) {
      m_bStop = true;
    }

    int iRate = 0;
    try {
      iRate = Integer.valueOf(m_jRateField.getText()).intValue();
    }
    catch (java.lang.NumberFormatException e) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                "I cannot understand the value in the rate field."));
    }

    if (iRate <= 0) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                "The timestep rate must be greater than 0."));
    }

    if (iDirection == BACKWARD) {
      iRate = -iRate;
    }

    goToTimestep(m_iCurrentTimestep + iRate);
  }

  /**
   * Jumps the model to the timestep indicated in m_jJumpToTimestepField.
   * @throws ModelException if the value in the jump to field is not a positive
   * number.
   */
  private void jump() throws ModelException {
    //If we're running, stop the other thread
    if (m_bRunning) {
      m_bStop = true;
    }
    int iTimestep = 0;
    try {
      iTimestep = Integer.valueOf(m_jJumpToTimestepField.getText()).intValue();
    }
    catch (java.lang.NumberFormatException e) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                "I cannot understand the value for the timestep to which to jump."));
    }

    if (iTimestep < 0) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                "The timestep to jump to must be at least 0."));
    }

    goToTimestep(iTimestep);
  }

  /**
   * Runs the detailed output file continuously through the timesteps.  It will
   * jump at the rate specified in the m_jRateField field.  This will put the
   * running in a separate thread so we can listen if the user wants to stop.
   * @param iDirection int Direction to run in, either FORWARD or BACKWARD.
   * @throws ModelException if the value in the rate field is not a positive
   * number.
   */
  private void run(int iDirection) throws ModelException {
    int iRate = 0;
    if (m_bRunning) return;
    try {
      iRate = Integer.valueOf(m_jRateField.getText()).intValue();
    }
    catch (java.lang.NumberFormatException e) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                "I cannot understand the value in the rate field."));
    }

    if (iRate <= 0) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                "The timestep rate must be greater than 0."));
    }

    if (iDirection == FORWARD) {
      final int iIncrement = iRate;
      m_bRunning = true;
      final sortie.parfile.SwingWorker oWorker = new sortie.parfile.SwingWorker() {
        public Object construct() {
          try {

            for (int i = m_iCurrentTimestep; i <= m_iMaxTimestep;
                 i += iIncrement) {
              if (m_bStop == true) {
                m_bStop = false;
                m_bRunning = false;
                return null;
              }
              goToTimestep(i);
              //Sleep this thread for a second, both to allow the GUI to
              //finish updating and to allow the user a look at the result
              //before we charge on
              try {
                threadVar.sleep(1500);
              }
              catch (java.lang.InterruptedException e) {
                ; //don't really care
              }
            }
            //At the very end, go to the last timestep if we're not there
            //already.  This way, if the rate doesn't match the timestep
            //number exactly, we still end up at the last step.
            if (m_bStop == false && m_iCurrentTimestep != m_iMaxTimestep) {
              goToTimestep(m_iMaxTimestep);
            }
            m_bRunning = false;
          }
          catch (sortie.data.simpletypes.ModelException oErr) {
            JDialog jPanel = null;
            ErrorGUI oHandler = new ErrorGUI(jPanel);
            oHandler.writeErrorMessage(oErr);
          }
          return null;
        }
      };
      oWorker.start();
    }
    else {
      final int iIncrement = iRate;
      m_bRunning = true;
      final sortie.parfile.SwingWorker oWorker = new sortie.parfile.SwingWorker() {
        public Object construct() {
          try {

            for (int i = m_iCurrentTimestep; i >= 0; i -= iIncrement) {
              if (m_bStop == true) {
                m_bRunning = false;
                m_bStop = false;
                return null;
              }
              goToTimestep(i);
              //Sleep this thread for a second, both to allow the GUI to
              //finish updating and to allow the user a look at the result
              //before we charge on
              try {
                threadVar.sleep(1500);
              }
              catch (java.lang.InterruptedException e) {
                ; //don't really care
              }
            }

            //At the very end, go to the first timestep if we're not there
            //already.  This way, if the rate doesn't match the timestep number
            //exactly, we still end up at 0.
            if (m_bStop == false && m_iCurrentTimestep != 0) {
              goToTimestep(0);
            }
            m_bRunning = false;

          }
          catch (sortie.data.simpletypes.ModelException oErr) {
            JDialog jPanel = null;
            ErrorGUI oHandler = new ErrorGUI(jPanel);
            oHandler.writeErrorMessage(oErr);
          }
          return null;
        }
      };
      oWorker.start();
    }
    /* for (int i = m_iCurrentTimestep; i <= m_iMaxTimestep; i+= iRate) {
       if (m_bStop == true) {
         m_bStop = false;
         break;
       }
       GoToTimestep(i);
     }
         } else {
     for (int i = m_iCurrentTimestep; i >= 0; i -= iRate) {
       if (m_bStop == true) {
         m_bStop = false;
         break;
       }
       GoToTimestep(i);
     }
         }*/
  }

  /**
   * Responds to button presses.
   * @param e ActionEvent
   */
  public void actionPerformed(ActionEvent e) {
    try {
      String sCommand = e.getActionCommand();
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      if (sCommand.equals("StepForward")) {
        step(FORWARD);
      }
      else if (sCommand.equals("StepBackward")) {
        step(BACKWARD);
      }
      else if (sCommand.equals("JumpToTimestep")) {
        jump();
      }
      else if (sCommand.equals("RunForward")) {
        run(FORWARD);
      }
      else if (sCommand.equals("RunBackward")) {
        run(BACKWARD);
      }
      else if (sCommand.equals("Stop")) {
        m_bStop = true;
      }
      else {
        super.actionPerformed(e);
      }
    }
    catch (sortie.data.simpletypes.ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
    finally {
      this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }

  /**
   * Changes the display to the desired timestep.  If the requested timestep
   * is beyond the boundaries of possible timesteps, nothing happens.
   * @param iTimestep int Timestep to display.
   * @throws ModelException if there is a problem updating charts.
   */
  public void goToTimestep(int iTimestep) throws sortie.data.simpletypes.ModelException {
    if (iTimestep < 0 || iTimestep > m_iMaxTimestep) {
      return;
    }

    m_iCurrentTimestep = iTimestep;
    m_jCurrentTimestepLabel.setText(String.valueOf(m_iCurrentTimestep) +
                                    " of " +
                                    String.valueOf(m_iMaxTimestep));
    DetailedOutputFileManager oManager = (DetailedOutputFileManager) m_oManager;
    oManager.readFile(m_iCurrentTimestep);
    oManager.updateCharts();
  }

  /**
   * Gets the number of timesteps contained in this detailed output file.
   * @return int Number of timesteps for this detailed output file.
   */
  public int getNumberOfTimesteps() {
    return m_iMaxTimestep;
  }

  /**
   * Updates the legend with a new maximum number of timesteps.
   * @param iNumTimesteps int The new maximum number of timesteps.
   */
  public void setNumberOfTimesteps(int iNumTimesteps) {
    m_iMaxTimestep = iNumTimesteps;
  }

  /**
   * Gets the timestep currently being displayed.
   * @return int the timestep currently being displayed.
   */
  public int getCurrentTimestep() {
    return m_iCurrentTimestep;
  }

  /**
   * Gets the file manager for this detailed output file.
   * @return DetailedOutputFileManager The file manager for this detailed output file.
   */
  DetailedOutputFileManager getDetailedOutputFileManager() {
    return (DetailedOutputFileManager) m_oManager;
  }
}
