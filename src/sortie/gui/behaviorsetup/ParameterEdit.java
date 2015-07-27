package sortie.gui.behaviorsetup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.Behavior.setupType;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;


/**
 * Window used to trigger the parameter editing process.  This displays the
 * list of parameter groups so a user can choose what to display.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>5/22/2013: Rewrote from scratch (LEM)
 */

public class ParameterEdit
extends JDialog
implements ActionListener {

  /** The parent frame over which dialogs are displayed.*/
  protected JFrame m_jParentFrame;

  /**GUIManager object from which to extract data.*/
  protected GUIManager m_oManager;

  /**Main window - we will need to communicate directories with it*/
  MainWindow m_oMainWindow;

  /**Help ID*/
  private String m_sHelpID = "windows.model_settings_window";

  /**
   * Constructor.
   * @param oWindow MainWindow object.
   * @param oManager GUIManager object.
   */
  public ParameterEdit(MainWindow oWindow, GUIManager oManager) {
    super(oWindow, "Edit Parameters", true);
    m_oManager = oManager;
    m_jParentFrame = oWindow;

    //Help ID
    oManager.getHelpBroker().enableHelpKey(this.getRootPane(), m_sHelpID, null);
    m_oMainWindow = oWindow;

    TreePopulation oPop = m_oManager.getTreePopulation();
    BehaviorTypeBase[] p_oObjects = m_oManager.getAllObjects();
    int i;
    
    //Create a main panel in which to place everything else
    JPanel jMainPanel = new QuickScrollingPanel();
    jMainPanel.setName("Main Panel");
    jMainPanel.setLayout(new BoxLayout(jMainPanel, BoxLayout.Y_AXIS));
    jMainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    //Add the data to the window
    for (i = 0; i < p_oObjects.length; i++) {
      for (Behavior oBehavior : p_oObjects[i].getAllInstantiatedBehaviors()) {
        
        if (oBehavior.getSetupType() == setupType.no_display) {
          //No display
          JPanel jObjectPanel = new JPanel(); 
          jObjectPanel.setLayout(new BoxLayout(jObjectPanel, BoxLayout.Y_AXIS));
          jObjectPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

          //Add a label with the object's name
          JLabel jLabel = new JLabel(oBehavior.getDescriptor());
          jLabel.setFont(new SortieFont(Font.BOLD, 2));
          jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
          jObjectPanel.add(jLabel);

          jLabel = new JLabel("No setup needed.");
          jLabel.setFont(new SortieFont());
          jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
          jObjectPanel.add(jLabel);
          jObjectPanel.setBorder(BorderFactory.createCompoundBorder(
              BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black),
              BorderFactory.createEmptyBorder(10, 0, 10, 0)));
          
          //Add the object's panel to the main panel
          jMainPanel.add(jObjectPanel); 
          
        } else {
                                     
          JPanel jObjectPanel = new JPanel(); 
          jObjectPanel.setLayout(new BoxLayout(jObjectPanel, BoxLayout.Y_AXIS));
          jObjectPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

          //Add a label with the object's name
          JLabel jLabel = new JLabel(oBehavior.getDescriptor());
          jLabel.setFont(new SortieFont(Font.BOLD, 2));
          jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
          jObjectPanel.add(jLabel);
          
          //Add a button for setup
          JButton jButton = new JButton("Edit Settings");          
          jButton.setFont(new SortieFont());
          jButton.setActionCommand("BEH:"+oBehavior.getParameterFileBehaviorName()+":"+oBehavior.getListPosition());
          jButton.addActionListener(this);
          jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
          jObjectPanel.add(jButton);
          
          //Use this html trick to word-wrap the label and keep it to a 
          //smaller width
          jLabel = new JLabel("<html><body style='width: 400px'>" + oBehavior.getAppliedToForDisplay(oPop));
          jLabel.setFont(new SortieFont());
          jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
          jObjectPanel.add(jLabel);                  

          jLabel = new JLabel(String.valueOf(oBehavior.getListPosition()));
          jLabel.setName("Object List Position");
          jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
          jLabel.setVisible(false);
          jObjectPanel.add(jLabel);          
          
          jObjectPanel.setBorder(BorderFactory.createCompoundBorder(
              BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black),
              BorderFactory.createEmptyBorder(10, 0, 10, 0)));
          //Add the object's panel to the main panel
          jMainPanel.add(jObjectPanel);                            
        }      
      }
    }

    //Lay out the dialog
    JScrollPane jScroller = new JScrollPane();
    jScroller.setName("Scroller");
    //Make sure window isn't larger than background window
    jScroller.getViewport().add(jMainPanel);
    jScroller.setPreferredSize(new Dimension( (int) jMainPanel.getPreferredSize().
        getWidth() + 20,
        (int) jScroller.getPreferredSize().
        getHeight()));
    int iHeight = (int) jScroller.getPreferredSize().getHeight();
    int iWidth = (int) jScroller.getPreferredSize().getWidth();
    iHeight = Math.min(iHeight, oWindow.getHeight() - 100);
    iWidth = Math.min(iWidth, oWindow.getWidth() - 100);
    jScroller.setPreferredSize(new Dimension(iWidth, iHeight));    
     
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jScroller, BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this,
        m_oMainWindow.m_oHelpBroker,
        m_sHelpID, false), BorderLayout.SOUTH);
    
    //jScroller.getVerticalScrollBar().setValue(0);
    //jScroller.getViewport().setViewPosition(new Point(0,0));

    //Create our menu
    JMenuBar jMenuBar = new JMenuBar();
    jMenuBar.setBackground(SystemColor.control);

    JMenu jMenuFile = new JMenu();
    jMenuFile.setText("File");
    jMenuFile.setFont(new SortieFont());
    jMenuFile.setMnemonic(java.awt.event.KeyEvent.VK_F);
    jMenuFile.setBackground(SystemColor.control);
    JMenuItem jSave = new JMenuItem("Save window as file",
        java.awt.event.KeyEvent.VK_S);
    jSave.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
        ActionEvent.CTRL_MASK));
    jSave.setFont(new SortieFont());
    jSave.setActionCommand("Save");
    jSave.addActionListener(this);
    jSave.setBackground(SystemColor.control);
    jMenuFile.add(jSave);

    jMenuBar.add(jMenuFile);
    //setJMenuBar(jMenuBar);
  }
  
  /**
   * Responds to button events.  If OK, then the parameter window is
   * constructed and this window is closed.  If Cancel, then this window is
   * closed.
   * @param e ActionEvent object.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("OK")) {
      this.setVisible(false);
      this.dispose();
    /*}
    else if (e.getActionCommand().equals("Save")) {

      //Allow the user to save a text file
      ModelFileChooser jChooser = new ModelFileChooser();
      jChooser.setFileFilter(new TextFileFilter());

      int iReturnVal = jChooser.showSaveDialog(this);
      if (iReturnVal == JFileChooser.APPROVE_OPTION) {
        //User chose a file - trigger the save
        File oFile = jChooser.getSelectedFile();
        if (oFile.exists()) {
          iReturnVal = JOptionPane.showConfirmDialog(this,
              "Do you wish to overwrite the existing file?",
              "Model",
              JOptionPane.YES_NO_OPTION);
        }
        if (!oFile.exists() || iReturnVal == JOptionPane.YES_OPTION) {
          String sFileName = oFile.getAbsolutePath();
          if (sFileName.endsWith(".txt") == false) {
            sFileName += ".txt";
          }
          saveCurrentWindow(sFileName);
        }
      }*/
    } else if (e.getActionCommand().startsWith("BEH")) {
      try {
        //This is a click of a button for a behavior's custom display - parse
        //out which behavior
        String s = e.getActionCommand();
        int iPos1 = s.indexOf(":"), iPos2 = s.indexOf(":", (iPos1+1)),
            iListPosition = -1;
        if (iPos1 < 0 || iPos2 < 0 || iPos2 <= iPos1) {
          throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "Can't parse string " + s));
        }
        String sBehaviorName = s.substring((iPos1+1), iPos2), 
               sInd = s.substring(iPos2+1);
        try {
          iListPosition = Integer.parseInt(sInd);
        } catch (NumberFormatException e2) {
          throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "Can't parse string " + s));
        }
        BehaviorTypeBase[] p_oObjects = m_oManager.getAllObjects();
        int i;
        boolean bFound = false;
        for (i = 0; i < p_oObjects.length; i++) {
          for (Behavior oBehavior : p_oObjects[i].getAllInstantiatedBehaviors()) {
            if (oBehavior.getParameterFileBehaviorName().equals(sBehaviorName) &&
                oBehavior.getListPosition() == iListPosition) {
              bFound = true;
              oBehavior.callSetupDialog(this, m_oMainWindow);
            }
          }
        }
        if (!bFound) {
          throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
              "Can't find behavior " + sBehaviorName + " at list position " +
               iListPosition));
        }
        
      } catch (ModelException e2) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(e2);
      }
    }
  }
}