package sortie.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.chart.axis.NumberAxis;


import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.Tree;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.datavisualizer.DataGrapher;
import sortie.datavisualizer.XYZDataItem;
import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;
import sortie.gui.components.TextFileFilter;

/**
 * Allows management of tree maps.
 * @author Lora Murphy
 * <br>Edit history:
 * <br>------------------
 * <br>February 27, 2008: Created by breaking off of a single tree setup 
 * window (LEM)
 * <br>March 5, 2008: Added tree map display (LEM)
 */
public class ManageTreeMap extends JDialog implements ActionListener {

  
  
  /**Tree dataset*/
  //DefaultXYZDataset m_oDataset = new DefaultXYZDataset();
  
  /**Panel container for the chart*/
  protected JPanel m_jChartPanel = new JPanel();
  
  /**Colors for each species*/
  protected Color[] mp_jSpeciesColors;
  
  /**TreePopulation object that data changes will be communicated to*/
  protected TreePopulation m_oPop;
  
  /**Button for clearing tree map trees*/
  protected JButton m_jClearTreeMap;

  /**Button for writing a tree map as tab-delimited text*/
  protected JButton m_jWriteTreeMap;
  
  /**Field displaying the DBH scale factor*/
  private JTextField m_jDBHScale = new JTextField("");

  /**Field displaying the minimum DBH*/
  private JTextField m_jMinDbh = new JTextField("");
  
  /**Help ID string*/
  private String m_sHelpID = "windows.edit_manage_tree_maps_window";
  
  /**Minimum dbh to draw*/
  private float m_fMinDbh;
  
  /**DBH scale factor*/
  private float m_fDBHScale = (float)1.0;
  
  /**X plot length in meters*/
  private float m_fXPlotLength;
  /**Y plot length in meters*/
  private float m_fYPlotLength;
  
  private final static int LEFT = 1; /**<Scrolling left*/
  private final static int RIGHT = 2; /**<Scrolling right*/
  private final static int UP = 3; /**<Scrolling up*/
  private final static int DOWN = 4; /**<Scrolling down*/
  
  /**
   * Constructor. Constructs and displays the GUI.
   * @param jParent Parent window in which to display this dialog.
   * @param oPop TreePopulation object.
   */
  public ManageTreeMap(JFrame jParent, TreePopulation oPop) throws ModelException{
    super(jParent, "Manage Tree Maps", true);
    int i;
    
    //Help ID
    oPop.getGUIManager().getHelpBroker().enableHelpKey(this.getRootPane(), m_sHelpID, null);

    m_oPop = oPop;
    Plot oPlot = m_oPop.getGUIManager().getPlot();
    m_fXPlotLength = oPlot.getPlotXLength();
    m_fYPlotLength = oPlot.getPlotYLength();
    
    //-----------------------------------
    //Set up drawing
    mp_jSpeciesColors = new Color[m_oPop.getNumberOfSpecies()];
    DefaultDrawingSupplier oDrawer = new DefaultDrawingSupplier();
    for (i = 0; i < m_oPop.getNumberOfSpecies(); i++) {
      mp_jSpeciesColors[i] = (Color) oDrawer.getNextPaint();
    }
    
    //-----------------------------------
    //Panel with tree map management buttons
    m_jClearTreeMap = new JButton();
    m_jClearTreeMap.setFont(new SortieFont());
    m_jClearTreeMap.setActionCommand("ClearTreeMap");
    m_jClearTreeMap.addActionListener(this);

    m_jWriteTreeMap = new JButton();
    m_jWriteTreeMap.setFont(new SortieFont());
    m_jWriteTreeMap.setActionCommand("WriteTreeMap");
    m_jWriteTreeMap.addActionListener(this);

    JButton jTreeMapButton = new JButton("Add tree map");
    jTreeMapButton.setFont(new SortieFont());
    jTreeMapButton.setActionCommand("AddTreeMap");
    jTreeMapButton.addActionListener(this);

    if (m_oPop.mp_oTrees.size() == 0) {
      m_jClearTreeMap.setText("No tree map trees");
      m_jClearTreeMap.setEnabled(false);

      m_jWriteTreeMap.setText("No tree map trees");
      m_jWriteTreeMap.setEnabled(false);
    }
    else {
      m_jClearTreeMap.setText("Remove tree map trees");
      m_jWriteTreeMap.setText("Write tree map to text file");
    }

    //Panel with buttons with additional tree tasks
    JPanel jButtonsPanel = new JPanel();
    jButtonsPanel.setLayout(new FlowLayout());
    jButtonsPanel.add(jTreeMapButton);
    jButtonsPanel.add(m_jClearTreeMap);
    jButtonsPanel.add(m_jWriteTreeMap);
    jButtonsPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
    
    //------------------------------------------
    // Tree map display panel
    m_jChartPanel.setLayout(new BorderLayout());
    //Make a panel for other controls
    JPanel jButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    //Make a button for restoring the original view
    JButton jRestoreButton = new JButton("Restore Original View");
    jRestoreButton.setFont(new sortie.gui.components.SortieFont());
    jRestoreButton.setActionCommand("Restore");
    jRestoreButton.addActionListener(this);
    //Make a control for the DBH scale factor
    JLabel jTempLabel = new JLabel("DBH Scale Factor:");
    jTempLabel.setFont(new sortie.gui.components.SortieFont());
    m_jDBHScale.setFont(new sortie.gui.components.SortieFont());
    m_jDBHScale.setText(String.valueOf(m_fDBHScale));
    m_jDBHScale.setPreferredSize(m_jDBHScale.getPreferredSize());
    JButton jChangeButton = new JButton("Update Map");
    jChangeButton.setFont(new sortie.gui.components.SortieFont());
    jChangeButton.setActionCommand("ChangeMapControls");
    jChangeButton.addActionListener(this);
    jButtonPanel.add(jRestoreButton);
    jButtonPanel.add(jTempLabel);
    jButtonPanel.add(m_jDBHScale);
    //Make a control for the minimum DBH
    jTempLabel = new JLabel("Minimum DBH to Display:");
    jTempLabel.setFont(new sortie.gui.components.SortieFont());
    m_jMinDbh.setFont(new sortie.gui.components.SortieFont());
    m_jMinDbh.setText(String.valueOf(m_fMinDbh));
    jButtonPanel.add(jTempLabel);
    jButtonPanel.add(m_jMinDbh);
    jButtonPanel.add(jChangeButton);

    //Make a pane for scrolling around the map
    JButton jUpButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.UP_TRIANGLE, Color.BLACK));
    jUpButton.addActionListener(this);
    jUpButton.setActionCommand("ScrollUp");
    jUpButton.setToolTipText("Scroll north");
    JButton jDownButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.DOWN_TRIANGLE, Color.BLACK));
    jDownButton.addActionListener(this);
    jDownButton.setActionCommand("ScrollDown");
    jDownButton.setToolTipText("Scroll south");
    JButton jLeftButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.LEFT_TRIANGLE, Color.BLACK));
    jLeftButton.addActionListener(this);
    jLeftButton.setActionCommand("ScrollLeft");
    jLeftButton.setToolTipText("Scroll west");
    JButton jRightButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.RIGHT_TRIANGLE, Color.BLACK));
    jRightButton.addActionListener(this);
    jRightButton.setActionCommand("ScrollRight");
    jRightButton.setToolTipText("Scroll east");
    //Make all the buttons the same thickness
    int iHeight = (int) jUpButton.getPreferredSize().getHeight();
    jLeftButton.setPreferredSize(new Dimension(iHeight,
        (int) jLeftButton.getPreferredSize().getHeight()));
    jRightButton.setPreferredSize(new Dimension(iHeight,
        (int) jRightButton.getPreferredSize().getHeight()));

    m_jChartPanel.add(jUpButton, BorderLayout.NORTH);
    m_jChartPanel.add(jLeftButton, BorderLayout.WEST);
    m_jChartPanel.add(jRightButton, BorderLayout.EAST);
    m_jChartPanel.add(jDownButton, BorderLayout.SOUTH);
    m_jChartPanel.add(DataGrapher.drawTreeMap(createTreeMapDataset(), "East ->",
        "",
        "",
        (int) oPlot.getPlotXLength(),
        (int) oPlot.getPlotYLength(), 
        m_fDBHScale,
        m_fMinDbh, oPlot.getPlotXLength(),
        oPlot.getPlotYLength(), 
        this, mp_jSpeciesColors), BorderLayout.CENTER);
    JPanel jTreePanel = new JPanel(new BorderLayout());
    jTreePanel.add(jButtonPanel, BorderLayout.NORTH);
    jTreePanel.add(m_jChartPanel, BorderLayout.CENTER);

    //------------------------------------------
    // Legend panel
    JPanel jLegendPanel = makeTreeLegendPanel();

    //Put it all together
    JPanel jContentPanel = new JPanel(new BorderLayout());

    jContentPanel.add(jButtonsPanel, BorderLayout.NORTH);
    
    if (jLegendPanel.getPreferredSize().height > m_jChartPanel.getPreferredSize().height) {
      JScrollPane jScroller = new JScrollPane(jLegendPanel);
      jScroller.setPreferredSize(new Dimension((int)jLegendPanel.getPreferredSize().getWidth() + 20,
          m_jChartPanel.getPreferredSize().height));
      jContentPanel.add(jScroller, BorderLayout.EAST);
    }
    else {
      jContentPanel.add(jLegendPanel, BorderLayout.EAST);
    }
    jContentPanel.add(jTreePanel, BorderLayout.CENTER);
    
    
    //Put everything together in the GUI
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jContentPanel, BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this,
                                                 oPop.getGUIManager().getHelpBroker(),
                                                 m_sHelpID, false),
                         BorderLayout.PAGE_END);
  }
  
  /**
   * Controls actions for this window.
   * @param e ActionEvent.
   */
  public void actionPerformed(ActionEvent e) {
    try {
      String sCommand = e.getActionCommand();
      if (sCommand.equals("OK")) {
        this.setVisible(false);
        this.dispose();
      }
      else if (sCommand.equals("ClearTreeMap")) {
        m_oPop.clearTrees();
        m_oPop.getTreeBehavior().m_sTextTreeMap.setValue("");
        m_jClearTreeMap.setText("No tree map trees");
        m_jClearTreeMap.setEnabled(false);
        m_jWriteTreeMap.setText("No tree map trees");
        m_jWriteTreeMap.setEnabled(false);
        refreshChart();
      }
      else if (sCommand.equals("WriteTreeMap")) {
        writeTreeMap();
      }
      else if (sCommand.equals("AddTreeMap")) {
        ModelFileChooser jChooser = new ModelFileChooser();
        jChooser.setFileFilter(new TextFileFilter());
        int returnVal = jChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          //User chose a file
          java.io.File oFile = jChooser.getSelectedFile();
          String sFileName = oFile.getAbsolutePath();
          this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
          //Make sure a parameter file has been loaded
          m_oPop.getGUIManager().inputTreeMap(sFileName);
          m_oPop.getGUIManager().getMainWindow().m_jMessagesField.setText(
              "Added tree map " + sFileName);
          if (m_oPop.mp_oTrees.size() == 0) {
            m_jClearTreeMap.setText("No tree map trees");
            m_jClearTreeMap.setEnabled(false);

            m_jWriteTreeMap.setText("No tree map trees");
            m_jWriteTreeMap.setEnabled(false);
          }
          else {
            m_jClearTreeMap.setText("Remove tree map trees");
            m_jClearTreeMap.setEnabled(true);

            m_jWriteTreeMap.setText("Write tree map to text file");
            m_jWriteTreeMap.setEnabled(true);
          }
          refreshChart();
        }
      }
      else if (sCommand.equals("Restore")) {
        restoreOriginalView();
      }
      else if (sCommand.equals("ScrollUp")) {
        scroll(UP);
      }
      else if (sCommand.equals("ScrollDown")) {
        scroll(DOWN);
      }
      else if (sCommand.equals("ScrollLeft")) {
        scroll(LEFT);
      }
      else if (sCommand.equals("ScrollRight")) {
        scroll(RIGHT);
      }
      else if (sCommand.equals("ChangeMapControls")) {

        //Go ahead and replace both the DBH scale factor and the minimum DBH,
        //even though they may not have both changed

        //Get the DBH scale factor
        float fNewScale, fNewDbh;
        try {
          Float oNewScale = Float.valueOf(m_jDBHScale.getText());
          fNewScale = oNewScale.floatValue();
        }
        catch (java.lang.NumberFormatException oErr) {
          JOptionPane.showMessageDialog(this,
                         "The value for DBH scale is not a recognized number.");
          m_jDBHScale.setText(String.valueOf(m_fDBHScale));
          m_jMinDbh.setText(String.valueOf(m_fMinDbh));
          return;
        }

        if (fNewScale <= 0) {
          JOptionPane.showMessageDialog(this,
                         "The values for DBH scale must be greater than zero.");
          m_jDBHScale.setText(String.valueOf(m_fDBHScale));
          m_jMinDbh.setText(String.valueOf(m_fMinDbh));
          return;
        }

        //Get the new minimum DBH
        try {
          Float oNewDbh = Float.valueOf(m_jMinDbh.getText());
          fNewDbh = oNewDbh.floatValue();
        }
        catch (java.lang.NumberFormatException oErr) {
          JOptionPane.showMessageDialog(this,
                       "The value for minimum DBH is not a recognized number.");
          m_jDBHScale.setText(String.valueOf(m_fDBHScale));
          m_jMinDbh.setText(String.valueOf(m_fMinDbh));
          return;
        }

        if (fNewDbh < 0) {
          JOptionPane.showMessageDialog(this,
                      "The values for minimum DBH must be greater than zero.");
          m_jDBHScale.setText(String.valueOf(m_fDBHScale));
          m_jMinDbh.setText(String.valueOf(m_fMinDbh));
          return;
        }
 
        m_fDBHScale = fNewScale;
        m_fMinDbh = fNewDbh;
      }
      refreshChart();
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
    finally {
      this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.
          DEFAULT_CURSOR));
    }
  }
  
  /**
   * Restores the original view of the tree map with the scale at which it
   * was first displayed.
   */
  protected void restoreOriginalView() {
    ChartPanel jPanel = (ChartPanel) DataGrapher.findNamedComponent(m_jChartPanel, "tree_map_panel");
    if (jPanel == null) {
      return;
    }
    Plot oPlot = m_oPop.getGUIManager().getPlot();

    NumberAxis oXAxis = (NumberAxis) jPanel.getChart().getXYPlot().getDomainAxis();
    oXAxis.setLowerBound(0);
    oXAxis.setUpperBound( (float) oPlot.getPlotXLength());

    NumberAxis oYAxis = (NumberAxis) jPanel.getChart().getXYPlot().getRangeAxis();
    oYAxis.setLowerBound(0);
    oYAxis.setUpperBound( (float)oPlot.getPlotYLength());
  }

  /**
   * Scrolls the view 10% in the specified direction.  If the view is full-size
   * nothing happens.  If the view is already scrolled all the way over in the
   * specified direction nothing happens.
   * @param iDirection int Direction in which to scroll.
   */
  protected void scroll(int iDirection) throws ModelException {
    ChartPanel jPanel = (ChartPanel) DataGrapher.findNamedComponent(m_jChartPanel, "tree_map_panel");
    if (jPanel == null) {
      return;
    }
    
    Plot oPlot = m_oPop.getGUIManager().getPlot();

    double fUpMax = oPlot.getPlotYLength(),
        fRightMax = oPlot.getPlotXLength(),
        fDownMax = 0, fLeftMax = 0, fNewMin = 0, fNewMax = 0, fDirectionInc,
        fCurrentMin, fCurrentMax;

    NumberAxis oXAxis = (NumberAxis) jPanel.getChart().getXYPlot().getDomainAxis();
    NumberAxis oYAxis = (NumberAxis) jPanel.getChart().getXYPlot().getRangeAxis();
    NumberAxis oAxis;

    if (iDirection != LEFT && iDirection != RIGHT && iDirection != UP &&
        iDirection != DOWN) {
      return;
    }

    //Get the current bounds
    if (iDirection == LEFT || iDirection == RIGHT) {
      oAxis = oXAxis;
    }
    else {
      oAxis = oYAxis;
    }

    fCurrentMin = oAxis.getLowerBound();
    fCurrentMax = oAxis.getUpperBound();
    fDirectionInc = (fCurrentMax - fCurrentMin) / 10;

    //Exit if we're already scrolled
    if (iDirection == LEFT) {
      if (fCurrentMin == fLeftMax) {
        return;
      }

      fNewMin = java.lang.Math.max(fCurrentMin - fDirectionInc, fLeftMax);
      fNewMax = fCurrentMax - fDirectionInc;
    }
    else if (iDirection == RIGHT) {
      if (fCurrentMax == fRightMax) {
        return;
      }

      fNewMin = fCurrentMin + fDirectionInc;
      fNewMax = java.lang.Math.min(fCurrentMax + fDirectionInc, fRightMax);

    }
    else if (iDirection == UP) {
      if (fCurrentMax == fUpMax) {
        return;
      }

      fNewMin = fCurrentMin + fDirectionInc;
      fNewMax = java.lang.Math.min(fCurrentMax + fDirectionInc, fUpMax);
    }
    else if (iDirection == DOWN) {
      if (fCurrentMin == fDownMax) {
        return;
      }

      fNewMin = java.lang.Math.max(fCurrentMin - fDirectionInc, fDownMax);
      fNewMax = fCurrentMax - fDirectionInc;
    }

    oAxis.setLowerBound(fNewMin);
    oAxis.setUpperBound(fNewMax);
  }
  
  /**
   * Writes the tree map trees to a tab-delimited text file.  It first displays
   * to the user a dialog for specifying the file name.  It then organizes
   * a 2-D array of strings for all possible tree data members that have been
   * defined.  Each tree contributes its applicable values to the table.  Then
   * the table is written out.
   * @throws ModelException if the file cannot be written.
   */
  protected void writeTreeMap() throws ModelException {
    try {

      //***************************
      // Get the user to select their filename
      //**************************
      ModelFileChooser jChooser = new ModelFileChooser();
      jChooser.setFileFilter(new TextFileFilter());
      String sFileName;

      int returnVal = jChooser.showSaveDialog(this);
      if (returnVal != JFileChooser.APPROVE_OPTION) {
        return;
      }

      //User chose a file - does it already exist?
      java.io.File oFile = jChooser.getSelectedFile();
      if (oFile.exists()) {
        returnVal = JOptionPane.showConfirmDialog(this,
                                           "Do you wish to overwrite the existing file?",
                                           "Model",
                                           JOptionPane.YES_NO_OPTION);
        if (returnVal == JOptionPane.NO_OPTION) {
          return;
        }
      }

      sFileName = oFile.getAbsolutePath();
      if (!sFileName.endsWith(".txt")) {
        sFileName = sFileName.concat(".txt");
      }

      //***************************
      // Compile the list of tree data members into a vector - this will
      // become the table header
      //**************************
      ArrayList<String> p_oColumnHeader = new ArrayList<String>(2);
      //These are distances to the first column in the table of each type,
      //starting at zero.  The first two columns are tree species and type.
      int iStartFloatIndex;
      int iStartIntIndex;
      int iStartCharIndex;
      int iStartBoolIndex;
      int i, j, k, m;

      //Add species and type
      p_oColumnHeader.add("Species");
      p_oColumnHeader.add("Type");

      //Now add each data member uniquely
      String sTemp, sTemp2;
      boolean bFound;

      //Floats
      iStartFloatIndex = p_oColumnHeader.size();
      for (i = 0; i < m_oPop.mp_sTreeFloatDataMembers.size(); i++) {
        for (j = 0; j < m_oPop.mp_sTreeFloatDataMembers.get(i).size(); j++) {
          for (k = 0; k < m_oPop.mp_sTreeFloatDataMembers.get(i).get(j).size(); k++) {
            sTemp2 = (String) m_oPop.mp_sTreeFloatDataMembers.get(i).get(j).get(k);
            bFound = false;
            for (m = 0; m < p_oColumnHeader.size(); m++) {
              sTemp = (String) p_oColumnHeader.get(m);
              if (sTemp.equals(sTemp2)) {
                bFound = true;
                break;
              }
            }
            if (!bFound) {
              p_oColumnHeader.add(sTemp2);
            }
          }
        }
      }

      //Ints
      iStartIntIndex = p_oColumnHeader.size();
      for (i = 0; i < m_oPop.mp_sTreeIntDataMembers.size(); i++) {
        for (j = 0; j < m_oPop.mp_sTreeIntDataMembers.get(i).size(); j++) {
          for (k = 0; k < m_oPop.mp_sTreeIntDataMembers.get(i).get(j).size(); k++) {
            sTemp2 = (String) m_oPop.mp_sTreeIntDataMembers.get(i).get(j).get(k);
            bFound = false;
            for (m = 0; m < p_oColumnHeader.size(); m++) {
              sTemp = (String) p_oColumnHeader.get(m);
              if (sTemp.equals(sTemp2)) {
                bFound = true;
                break;
              }
            }
            if (!bFound) {
              p_oColumnHeader.add(sTemp2);
            }
          }
        }
      }

      //Bools
      iStartBoolIndex = p_oColumnHeader.size();
      for (i = 0; i < m_oPop.mp_sTreeBoolDataMembers.size(); i++) {
        for (j = 0; j < m_oPop.mp_sTreeBoolDataMembers.get(i).size(); j++) {
          for (k = 0; k < m_oPop.mp_sTreeBoolDataMembers.get(i).get(j).size(); k++) {
            sTemp2 = (String) m_oPop.mp_sTreeBoolDataMembers.get(i).get(j).get(k);
            bFound = false;
            for (m = 0; m < p_oColumnHeader.size(); m++) {
              sTemp = (String) p_oColumnHeader.get(m);
              if (sTemp.equals(sTemp2)) {
                bFound = true;
                break;
              }
            }
            if (!bFound) {
              p_oColumnHeader.add(sTemp2);
            }
          }
        }
      }

      //Chars
      iStartCharIndex = p_oColumnHeader.size();
      for (i = 0; i < m_oPop.mp_sTreeCharDataMembers.size(); i++) {
        for (j = 0; j < m_oPop.mp_sTreeCharDataMembers.get(i).size(); j++) {
          for (k = 0; k < m_oPop.mp_sTreeCharDataMembers.get(i).get(j).size(); k++) {
            sTemp2 = (String) m_oPop.mp_sTreeCharDataMembers.get(i).get(j).get(k);
            bFound = false;
            for (m = 0; m < p_oColumnHeader.size(); m++) {
              sTemp = (String) p_oColumnHeader.get(m);
              if (sTemp.equals(sTemp2)) {
                bFound = true;
                break;
              }
            }
            if (!bFound) {
              p_oColumnHeader.add(sTemp2);
            }
          }
        }
      }

      //*******************************
       //Now create the table of values to write out
       //******************************

        String[][] p_sTable = new String[m_oPop.mp_oTrees.size()][
            p_oColumnHeader.size()];
      String[] p_sColumnHeader = new String[p_oColumnHeader.size()];
      for (i = 0; i < p_sColumnHeader.length; i++) {
        p_sColumnHeader[i] = (String) p_oColumnHeader.get(i);

      }
      Tree oTree;
      for (i = 0; i < m_oPop.mp_oTrees.size(); i++) {
        oTree = (Tree) m_oPop.mp_oTrees.get(i);

        //Species and type
        p_sTable[i][0] = m_oPop.getSpeciesNameFromCode(oTree.getSpecies()).
            replace('_', ' ');
        p_sTable[i][1] = TreePopulation.getTypeNameFromCode(oTree.getType());

        for (j = 0; j < oTree.getNumberOfFloats(); j++) {
          if (oTree.getFloat(j) != null) {
            //Get the name for this data member
            sTemp = m_oPop.mp_sTreeFloatDataMembers.get(oTree.getSpecies()).get(oTree.getType()).get(j);
            //Find its column in the table
            for (k = iStartFloatIndex; k < p_sColumnHeader.length; k++) {
              if (p_sColumnHeader[k].equals(sTemp)) {
                p_sTable[i][k] = String.valueOf(oTree.getFloat(j));
                break;
              }
            }
          }
        }

        for (j = 0; j < oTree.getNumberOfInts(); j++) {
          if (oTree.getInt(j) != null) {
            //Get the name for this data member
            sTemp = m_oPop.mp_sTreeIntDataMembers.get(oTree.getSpecies()).get(oTree.getType()).get(j);
            //Find its column in the table
            for (k = iStartIntIndex; k < p_sColumnHeader.length; k++) {
              if (p_sColumnHeader[k].equals(sTemp)) {
                p_sTable[i][k] = String.valueOf(oTree.getInt(j));
                break;
              }
            }
          }
        }

        for (j = 0; j < oTree.getNumberOfBools(); j++) {
          if (oTree.getBool(j) != null) {
            //Get the name for this data member
            sTemp = m_oPop.mp_sTreeBoolDataMembers.get(oTree.getSpecies()).get(oTree.getType()).get(j);
            //Find its column in the table
            for (k = iStartBoolIndex; k < p_sColumnHeader.length; k++) {
              if (p_sColumnHeader[k].equals(sTemp)) {
                p_sTable[i][k] = String.valueOf(oTree.getBool(j));
                break;
              }
            }
          }
        }

        for (j = 0; j < oTree.getNumberOfChars(); j++) {
          if (oTree.getChar(j) != null) {
            //Get the name for this data member
            sTemp = m_oPop.mp_sTreeCharDataMembers.get(oTree.getSpecies()).get(oTree.getType()).get(j);
            //Find its column in the table
            for (k = iStartCharIndex; k < p_sColumnHeader.length; k++) {
              if (p_sColumnHeader[k].equals(sTemp)) {
                p_sTable[i][k] = String.valueOf(oTree.getChar(j));
                break;
              }
            }
          }
        }
      }

      //*******************************
       //Write the file
       //******************************
        java.io.FileWriter oOut = new java.io.FileWriter(sFileName);
      oOut.write("Tree map for parameter file " +
                 m_oPop.getGUIManager().getParameterFileName() + "\n");

      //Header
      if (p_oColumnHeader.size() > 0) {
        oOut.write(p_oColumnHeader.get(0));
      }
      for (i = 1; i < p_oColumnHeader.size(); i++) {
        oOut.write("\t" + p_oColumnHeader.get(i));
      }

      //Values
      for (i = 0; i < p_sTable.length; i++) {
        oOut.write("\n");
        if (p_sTable[i].length > 0) {
          if (p_sTable[i][0] == null) {
            oOut.write("--");
          }
          else {
            oOut.write(p_sTable[i][0]);
          }
        }
        for (j = 1; j < p_sTable[i].length; j++) {
          if (p_sTable[i][j] == null) {
            oOut.write("\t--");
          }
          else {
            oOut.write("\t" + p_sTable[i][j]);
          }
        }
      }

      oOut.close();

      //Tell the user
      JOptionPane.showMessageDialog(this, "File has been saved.");

    }
    catch (java.io.IOException oExp) {
      throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA",
                                "There was a problem writing the file."));
    }
  }
  
  /**
   * Refreshes the chart to show the data currently held in the dataset.
   * @throws ModelException if the chart cannot be created.
   */
   protected void refreshChart() throws ModelException {
    DataGrapher.updateTreeMap(createTreeMapDataset(), m_jChartPanel, 
        mp_jSpeciesColors, m_fDBHScale, m_fMinDbh, m_fXPlotLength,
        m_fYPlotLength );
    //For some reason I have to add the next two lines to get the chart to
    //redisplay.
    m_jChartPanel.validate();
    m_jChartPanel.repaint();
  }
  
  /**
   * Creates the tree map dataset.
   */
  private DefaultXYZDataset createTreeMapDataset() throws ModelException {
    //Create the dataset
    DefaultXYZDataset oDataset = new DefaultXYZDataset();
    ArrayList<ArrayList<XYZDataItem>> p_oTreesBySpecies = new ArrayList<ArrayList<XYZDataItem>>(m_oPop.getNumberOfSpecies());
    ArrayList<Tree> p_oTrees = m_oPop.getTrees();
    Tree oTree;
    XYZDataItem oItem;
    int i, j;
    
    //Load the trees from the tree population into the vectors
    for (i = 0; i < m_oPop.getNumberOfSpecies(); i++) {
      p_oTreesBySpecies.add(i, new ArrayList<XYZDataItem>(0));
    }
    for (i = 0; i < p_oTrees.size(); i++) {
      oTree = p_oTrees.get(i);
      int iXCode = m_oPop.getFloatDataCode("X", oTree.getSpecies(), oTree.getType()),
          iYCode = m_oPop.getFloatDataCode("Y", oTree.getSpecies(), oTree.getType()),
          iDiamCode = -1;
      if (oTree.getType() > TreePopulation.SEEDLING) {
        iDiamCode = m_oPop.getFloatDataCode("DBH", oTree.getSpecies(),
                                          oTree.getType());
      }
      if (iXCode > -1 && iYCode > -1 && iDiamCode > -1 &&
           null != oTree.getFloat(iXCode) &&
           null != oTree.getFloat(iYCode) &&
           null != oTree.getFloat(iDiamCode)) {  
        
        p_oTreesBySpecies.get(oTree.getSpecies()).add(new XYZDataItem(
            oTree.getFloat(iXCode).floatValue(),
            oTree.getFloat(iYCode).floatValue(),
            oTree.getFloat(iDiamCode).floatValue()/100.0));//diam in m
      }
    }

    //Now create the dataset from the vectors - empty series OK because they
    //act as placeholders
    for (i = 0; i < p_oTreesBySpecies.size(); i++) {
      double[][] p_oSeries = new double[3][p_oTreesBySpecies.get(i).size()];
      for (j = 0; j < p_oTreesBySpecies.get(i).size(); j++) {
        oItem = (XYZDataItem) p_oTreesBySpecies.get(i).get(j);
        p_oSeries[0][j] = oItem.fX;
        p_oSeries[1][j] = oItem.fY;
        p_oSeries[2][j] = oItem.fZ;
      }
      oDataset.addSeries(m_oPop.getSpeciesNameFromCode(i), p_oSeries);
    }
    return oDataset;
  }
  
  /**
   * Creates a tree legend panel.
   * @return JPanel Panel with tree legend on it.
   */
  protected JPanel makeTreeLegendPanel() {
    int i;

    JPanel jLegendPanel = new JPanel(new GridLayout(0, 1));
    for (i = 0; i < m_oPop.getNumberOfSpecies(); i++) {
      JPanel jTemp = new JPanel(new FlowLayout(FlowLayout.LEFT));
      JButton jColorButton = new JButton(new ModelIcon(15, 15, ModelIcon.RECTANGLE, mp_jSpeciesColors[i]));
      jColorButton.setContentAreaFilled(false);
      jColorButton.setPreferredSize(new java.awt.Dimension(15, 15));
      JLabel jTempSpLabel = new JLabel(m_oPop.getSpeciesNameFromCode(i).replace(
          '_', ' '));
      jTempSpLabel.setFont(new SortieFont());
      jTemp.add(jColorButton);
      jTemp.add(jTempSpLabel);
      jLegendPanel.add(jTemp);
    }
    return jLegendPanel;
  }

}
