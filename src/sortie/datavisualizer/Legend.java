package sortie.datavisualizer;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import org.jfree.chart.plot.DefaultDrawingSupplier;

import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.SortieFont;

/**
 * This is an internal frame which displays a color legend for species-based
 * charts.  When the user clicks on a color, they get a color chooser that
 * lets them change a species's color.
 *
 * When species names come in, they may have underscores substituted for
 * spaces.  Thus, a separate set of display names is used that will have the
 * underscores removed.  The species names with underscores is what all other
 * objects will expect when they ask the legend for help parsing a detailed
 * output file.
 *
 * Copyright: Copyright (c) Charles D. Canham 2003</p>
 * Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 * <br>December 20, 2010: Added scroll bar to species list and cleaned up code
 * a bit (LEM)
 */

public class Legend extends JInternalFrame implements ActionListener {
  
  

  /**The names of each of the species*/
  protected String[] mp_sSpeciesNames;
  /**The names that will be displayed*/
  protected String[] mp_sDisplayNames;
  /**The color for each species*/
  protected Color[] mp_oSpeciesColors;
  /**The color display for each species*/
  protected JButton[] mp_jSpeciesColorDisplays;
  /**Checkboxes indicating whether or not a species should be visible*/
  protected JCheckBox[] mp_jSpeciesChecks;
  /**Parent data file manager*/
  protected DataFileManager m_oManager;

  /**
   * Constructor.  This will create the legend window and assign default
   * colors to all the species.
   * @param oManager The parent DataFileManager object.
   * @param sTitle Window title
   * @param p_sSpeciesNames The list of species to display in the legend.
   * @throws ModelException if the list of species has 0 items, or wrapping
   * another type of exception.
   */
  public Legend(DataFileManager oManager, String sTitle,
                String[] p_sSpeciesNames) throws ModelException {

    super(sTitle, true, true, true, true);

    m_oManager = oManager;

    if (p_sSpeciesNames == null || p_sSpeciesNames.length == 0) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                               "The legend for this file cannot be created " +
                               "without a valid list of species names."));
    }

    //Create all the other arrays to size the same as the list of species names
    mp_sSpeciesNames = p_sSpeciesNames;
    int iSizeOf = mp_sSpeciesNames.length;

    mp_sDisplayNames = new String[iSizeOf];
    mp_oSpeciesColors = new Color[iSizeOf];
    mp_jSpeciesColorDisplays = new JButton[iSizeOf];
    mp_jSpeciesChecks = new JCheckBox[iSizeOf];

    //Create the display names
    for (int i = 0; i < iSizeOf; i++) {
      mp_sDisplayNames[i] = mp_sSpeciesNames[i].replace('_', ' ');
    }

    //Pick a color for each species
    createColorList();

    try {

      createGUI();
    }
    catch (Exception e) {
      throw(new ModelException(ErrorGUI.UNKNOWN, "JAVA", e.getMessage()));
    }
  }

  /**
   * Creates the GUI and lays out all the components.
   * @throws Exception If anything goes wrong.
   */
  private void createGUI() throws Exception {
    int i;

    //Set everything to appear in a single column
    JPanel jPanel = new JPanel(new GridLayout(mp_sSpeciesNames.length, 1));
    JPanel jMiniPanel;
    JLabel jLabel;
    
    //Create all the elements for each species
    for (i = 0; i < mp_sSpeciesNames.length; i++) {

      //Create each color button and set its color
      mp_jSpeciesColorDisplays[i] = new JButton(new ModelIcon(15, 15, ModelIcon.RECTANGLE,
          mp_oSpeciesColors[i]));
      mp_jSpeciesColorDisplays[i].setContentAreaFilled(false); //don't draw the button underneath the icon
      mp_jSpeciesColorDisplays[i].setPreferredSize(new Dimension(15, 15));
      mp_jSpeciesColorDisplays[i].addActionListener(new
          Legend_ColorChooserButton_actionAdapter(this));

      //Create each text label
      jLabel = new JLabel(mp_sDisplayNames[i]);
      jLabel.setFont(new sortie.gui.components.SortieFont());

      //Create each checkbox
      mp_jSpeciesChecks[i] = new JCheckBox();
      mp_jSpeciesChecks[i].setSelected(true);
      mp_jSpeciesChecks[i].setBackground(SystemColor.control);
      //mp_jSpeciesChecks[i].addActionListener(new Legend_CheckBox_actionAdapter(this));
      mp_jSpeciesChecks[i].addActionListener(this);

      //Create each layout
      //mp_oLayouts[i] = new FlowLayout(FlowLayout.LEFT);

      //Create each panel and add its elements
      jMiniPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      jMiniPanel.setBackground(SystemColor.control);
      jMiniPanel.add(mp_jSpeciesChecks[i]);
      jMiniPanel.add(mp_jSpeciesColorDisplays[i]);
      jMiniPanel.add(jLabel);

      jPanel.add(jMiniPanel);
    }
    
    JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    JButton jButton = new JButton("Select All");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("SelectAllSpecies");
    jButton.addActionListener(this);
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jTempPanel.add(jButton);

    jButton = new JButton("Clear All");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("ClearAllSpecies");
    jButton.addActionListener(this);
    jButton.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.add(jButton);
    
    jPanel.setPreferredSize(new Dimension((int)jPanel.getPreferredSize().getWidth() + 15,
        (int)jPanel.getPreferredSize().getHeight()));
    JScrollPane jScroller = new JScrollPane(jPanel);
    
    JPanel jContentPanel = new JPanel(new BorderLayout());
    jContentPanel.add(jScroller, BorderLayout.CENTER);
    jContentPanel.add(jTempPanel, BorderLayout.NORTH);
    
    //getContentPane().setLayout(new BorderLayout());
    
    //this.getContentPane().add(jScroller);
    this.getContentPane().add(jContentPanel);
  }
  
  /**
   * Creates the GUI and lays out all the components.
   * @throws java.lang.Exception If anything goes wrong.
   */
  /*private void CreateGUI() throws Exception {
    int i;

    //Set everything to appear in a single column
    gridLayout1.setColumns(1);
    gridLayout1.setRows(mp_sSpeciesNames.length);
    this.getContentPane().setLayout(gridLayout1);

    //Create all the elements for each species
    for (i = 0; i < mp_sSpeciesNames.length; i++) {

      //Create each color button and set its color
      mp_jSpeciesColorDisplays[i] = new JButton(new ModelIcon(15, 15, ModelIcon.RECTANGLE,
          mp_oSpeciesColors[i]));
      mp_jSpeciesColorDisplays[i].setContentAreaFilled(false); //don't draw the button underneath the icon
      mp_jSpeciesColorDisplays[i].setPreferredSize(new Dimension(15, 15));
      mp_jSpeciesColorDisplays[i].addActionListener(new
          Legend_ColorChooserButton_actionAdapter(this));

      //Create each text label
      mp_jSpeciesLabels[i] = new JLabel(mp_sDisplayNames[i]);
      mp_jSpeciesLabels[i].setFont(new uihelpers.ModelFont());

      //Create each checkbox
      mp_jSpeciesChecks[i] = new JCheckBox();
      mp_jSpeciesChecks[i].setSelected(true);
      mp_jSpeciesChecks[i].setBackground(SystemColor.control);
      mp_jSpeciesChecks[i].addActionListener(new Legend_CheckBox_actionAdapter(this));

      //Create each layout
      mp_oLayouts[i] = new FlowLayout(FlowLayout.LEFT);

      //Create each panel and add its elements
      mp_jSpeciesPanels[i] = new JPanel();
      mp_jSpeciesPanels[i].setBackground(SystemColor.control);
      mp_jSpeciesPanels[i].setLayout(mp_oLayouts[i]);
      mp_jSpeciesPanels[i].add(mp_jSpeciesChecks[i]);
      mp_jSpeciesPanels[i].add(mp_jSpeciesColorDisplays[i]);
      mp_jSpeciesPanels[i].add(mp_jSpeciesLabels[i]);

      this.getContentPane().add(mp_jSpeciesPanels[i]);
    }
  }*/

  /**
   * This picks a default color for each of our species.
   */
  protected void createColorList() {

    DefaultDrawingSupplier oDrawer = new DefaultDrawingSupplier();
    int i;

    for (i = 0; i < mp_oSpeciesColors.length; i++) {
      mp_oSpeciesColors[i] = (Color) oDrawer.getNextPaint();
    }
  }

  /**
   * Gets the color associated with a particular species.
   * @param iIndex Species index number.
   * @return This species' color.
   * @throws ModelException if the species index is not valid.
   */
  public Color getSpeciesColor(int iIndex) throws ModelException {
    if (iIndex < 0 || iIndex > mp_sSpeciesNames.length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                               "Invalid species for this legend."));
    }
    return mp_oSpeciesColors[iIndex];
  }

  /**
   * Gets the color associated with a particular species.
   * @param sName Species' name.
   * @return This species' color.
   * @throws ModelException ModelException if the species name is not valid.
   */
  public Color getSpeciesColor(String sName) throws ModelException {
    int i;
    for (i = 0; i < mp_sSpeciesNames.length; i++) {
      if (sName.equals(mp_sSpeciesNames[i])) {
        return mp_oSpeciesColors[i];
      }
    }
    throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                             "Invalid species name for this legend."));
  }

  /**
   * Get whether or not a species' checkbox indicates that it is selected.
   * @param iIndex Species index number.
   * @return Whether or not the species is selected and should be shown in
   * graphs.
   * @throws ModelException if the species index is not valid.
   */
  public boolean getIsSpeciesSelected(int iIndex) throws ModelException {
    if (iIndex < 0 || iIndex > mp_sSpeciesNames.length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                             "Invalid species for this legend."));
    }
    return mp_jSpeciesChecks[iIndex].isSelected();
  }

  /**
   * Gets the total number of species for this legend.
   * @return Number of species.
   */
  public int getNumberOfSpecies() {
    return mp_sSpeciesNames.length;
  }

  
  /**
   * Triggered when a checkbox is toggled on or off.  All open charts are
   * redrawn.
   * @param e ActionEvent.  Ignored.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("SelectAllSpecies")) {
      for (int i = 0; i < mp_jSpeciesChecks.length; i++) {
        mp_jSpeciesChecks[i].setSelected(true);
      }
    }
    else if (e.getActionCommand().equals("ClearAllSpecies")) {
      for (int i = 0; i < mp_jSpeciesChecks.length; i++) {
        mp_jSpeciesChecks[i].setSelected(false);
      }
    }
    try {
      m_oManager.updateCharts();
    }
    catch (ModelException oErr) {
      //We have to do this as a runtime exception to get around the action
      //listener's inability to throw an error
      RuntimeException oExp = new RuntimeException(oErr.getMessage(), oErr);
      throw (oExp);
    }
  }

  /**
   * Gets the internal code species name for a given species index.
   * @param i Species index number.
   * @return Species name.
   * @throws ModelException If the species index is not valid.
   */
  public String getSpeciesCodeName(int i) throws ModelException {
    if (i < 0 || i >= mp_sSpeciesNames.length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                             "Bad species index passed to legend."));
    }
    return mp_sSpeciesNames[i];
  }

  /**
   * Gets the species display name for a given species index.
   * @param i Species index number.
   * @return Species name.
   * @throws ModelException If the species index is not valid.
   */
  public String getSpeciesDisplayName(int i) throws ModelException {
    if (i < 0 || i >= mp_sSpeciesNames.length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                             "Bad species index passed to legend."));
    }
    return mp_sDisplayNames[i];
  }

  /**
   * Displays the color chooser when the user clicks the color chooser button,
   * and extracts the chosen color.
   * @param e ActionEvent
   */
  public void chooseColor(ActionEvent e) {
    try {
      //Find out which button fired this event
      Object o = e.getSource();
      int i, iSpecies = -1;
      for (i = 0; i < mp_jSpeciesColorDisplays.length; i++) {
        if (mp_jSpeciesColorDisplays[i].equals(o)) {
          iSpecies = i;
        }
      }

      if (iSpecies == -1) {
        return;
      }

      //Create a color chooser
      Color oColor = JColorChooser.showDialog(this, mp_sSpeciesNames[iSpecies].replace('_', ' '),
                                         mp_oSpeciesColors[iSpecies]);

      //If the color is different, update
      if (null != oColor && !oColor.equals(mp_oSpeciesColors[iSpecies])) {
        //Set the new button background
        mp_jSpeciesColorDisplays[iSpecies].setIcon(new ModelIcon(15, 15, ModelIcon.RECTANGLE,
          oColor));
        mp_oSpeciesColors[iSpecies] = oColor;
        m_oManager.updateCharts();
      }
    }
    catch (ModelException oErr) {
      //We have to do this as a runtime exception to get around the action
      //listener's inability to throw an error
      RuntimeException oExp = new RuntimeException(oErr.getMessage(), oErr);
      throw (oExp);
    }
  }
}


/**
 * Triggered when the user clicks a color button.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
class Legend_ColorChooserButton_actionAdapter implements ActionListener {
  Legend adaptee; /**<Window to listen for clicks*/

  /**
   * Constructor
   * @param adaptee Legend Window to listen for clicks
   */
  Legend_ColorChooserButton_actionAdapter(Legend adaptee) {
    this.adaptee = adaptee;
  }

  /**
   * Triggers the color chooser.
   * @param e ActionEvent Event that triggered this.
   */
  public void actionPerformed(ActionEvent e) {
    adaptee.chooseColor(e);
  }
}
