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
 * This is a class for displaying a color legend for non-species-based
 * charts. They get the same capabilities as species-based, such
 * as user-chosen colors.
 * 
 * This is tied to a specific chart, so instead of a separate frame, this
 * will create a JPanel for display.
 *
 * Copyright: Copyright (c) Charles D. Canham 2016</p>
 * Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2016: Created (LEM)
 */

public class NoSpeciesLegend extends JPanel implements ActionListener {

  /**The names that will be displayed*/
  protected String[] mp_sDisplayNames;
  /**The color for each thing*/
  protected Color[] mp_oColors;
  /**The color display for each thing*/
  protected JButton[] mp_jColorDisplays;
  /**Checkboxes indicating whether or not a thing should be visible*/
  protected JCheckBox[] mp_jChecks;
  /**Parent data request*/
  protected DataRequest m_oRequest;

  /**
   * Constructor. This will create the legend window and assign default
   * colors to all the things.
   * @param oRequest The parent DataRequest object.
   * @param p_sDisplayNames The list of items to display in the legend.
   * @throws ModelException if the list of species has 0 items, or wrapping
   * another type of exception.
   */
  public NoSpeciesLegend(DataRequest oRequest, String[] p_sDisplayNames) throws ModelException {

    super(new BorderLayout());

    m_oRequest = oRequest;

    if (p_sDisplayNames == null || p_sDisplayNames.length == 0) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                               "The legend for this file cannot be created " +
                               "without a valid list of display names."));
    }

    //Create all the other arrays to size the same as the list of species names
    mp_sDisplayNames = p_sDisplayNames;
    int iSizeOf = mp_sDisplayNames.length;

    mp_oColors = new Color[iSizeOf];
    mp_jColorDisplays = new JButton[iSizeOf];
    mp_jChecks = new JCheckBox[iSizeOf];

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
  private void createGUI() {
    int i;

    //Set everything to appear in a single column
    JPanel jPanel = new JPanel(new GridLayout(mp_sDisplayNames.length, 1));
    JPanel jMiniPanel;
    JLabel jLabel;
    
    //Create all the elements for each species
    for (i = 0; i < mp_sDisplayNames.length; i++) {

      //Create each color button and set its color
      mp_jColorDisplays[i] = new JButton(new ModelIcon(15, 15, ModelIcon.RECTANGLE,
          mp_oColors[i]));
      mp_jColorDisplays[i].setContentAreaFilled(false); //don't draw the button underneath the icon
      mp_jColorDisplays[i].setPreferredSize(new Dimension(15, 15));
      mp_jColorDisplays[i].addActionListener(this);

      //Create each text label
      jLabel = new JLabel(mp_sDisplayNames[i]);
      jLabel.setFont(new sortie.gui.components.SortieFont());

      //Create each checkbox
      mp_jChecks[i] = new JCheckBox();
      mp_jChecks[i].setSelected(true);
      mp_jChecks[i].setBackground(SystemColor.control);
      mp_jChecks[i].addActionListener(this);
      
      //Create each panel and add its elements
      jMiniPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      jMiniPanel.setBackground(SystemColor.control);
      jMiniPanel.add(mp_jChecks[i]);
      jMiniPanel.add(mp_jColorDisplays[i]);
      jMiniPanel.add(jLabel);

      jPanel.add(jMiniPanel);
    }
    
    JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    JButton jButton = new JButton("Select All");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("SelectAll");
    jButton.addActionListener(this);
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jTempPanel.add(jButton);

    jButton = new JButton("Clear All");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("ClearAll");
    jButton.addActionListener(this);
    jButton.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.add(jButton);
    
    jPanel.setPreferredSize(new Dimension((int)jPanel.getPreferredSize().getWidth() + 15,
        (int)jPanel.getPreferredSize().getHeight()));
    JScrollPane jScroller = new JScrollPane(jPanel);
    
    this.add(jScroller, BorderLayout.CENTER);
    this.add(jTempPanel, BorderLayout.NORTH);        
  }
  
  /**
   * This picks a default color for each of our things.
   */
  protected void createColorList() {

    DefaultDrawingSupplier oDrawer = new DefaultDrawingSupplier();
    int i;

    for (i = 0; i < mp_oColors.length; i++) {
      mp_oColors[i] = (Color) oDrawer.getNextPaint();
    }
  }

  /**
   * Gets the color associated with a particular thing, by index.
   * @param iIndex Index number.
   * @return This thing's color.
   * @throws ModelException if the index is not valid.
   */
  public Color getColor(int iIndex) throws ModelException {
    if (iIndex < 0 || iIndex > mp_sDisplayNames.length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                               "Invalid index for this legend."));
    }
    return mp_oColors[iIndex];
  }

  /**
   * Gets the color associated with a particular thing, by name.
   * @param sName Thing name.
   * @return This species' color.
   * @throws ModelException ModelException if the species name is not valid.
   */
  public Color getColor(String sName) throws ModelException {
    int i;
    for (i = 0; i < mp_sDisplayNames.length; i++) {
      if (sName.equals(mp_sDisplayNames[i])) {
        return mp_oColors[i];
      }
    }
    throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                             "Invalid thing name for this legend."));
  }
  
  /**
   * Gets the display name for a given index.
   * @param i Index number.
   * @return Thing name.
   * @throws ModelException If the index is not valid.
   */
  public String getDisplayName(int i) throws ModelException {
    if (i < 0 || i >= mp_sDisplayNames.length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                             "Bad index passed to legend."));
    }
    return mp_sDisplayNames[i];
  }

  /**
   * Get whether or not a thing's checkbox indicates that it is selected.
   * @param iIndex Index number.
   * @return Whether or not the thing is selected and should be shown in
   * graphs.
   * @throws ModelException if the index is not valid.
   */
  public boolean getIsSelected(int iIndex) throws ModelException {
    if (iIndex < 0 || iIndex > mp_sDisplayNames.length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                             "Invalid index for this legend."));
    }
    return mp_jChecks[iIndex].isSelected();
  }

  /**
   * Gets the total number of things for this legend.
   * @return Number of things.
   */
  public int getNumberOfThings() {
    return mp_sDisplayNames.length;
  }

  
  /**
   * Triggered when a checkbox is toggled on or off.  All open charts are
   * redrawn.
   * @param e ActionEvent. Ignored.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("SelectAll")) {
      for (int i = 0; i < mp_jChecks.length; i++) {
        mp_jChecks[i].setSelected(true);
      }
    }
    else if (e.getActionCommand().equals("ClearAll")) {
      for (int i = 0; i < mp_jChecks.length; i++) {
        mp_jChecks[i].setSelected(false);
      }
    } else {
     chooseColor(e); 
    }
    try {
      m_oRequest.updateChart(m_oRequest.m_oManager.getLegend());
    }
    catch (ModelException oErr) {
      //We have to do this as a runtime exception to get around the action
      //listener's inability to throw an error
      RuntimeException oExp = new RuntimeException(oErr.getMessage(), oErr);
      throw (oExp);
    }
  }

  /**
   * Displays the color chooser when the user clicks the color chooser button,
   * and extracts the chosen color.
   * @param e ActionEvent
   */
  public void chooseColor(ActionEvent e) {
    //Find out which button fired this event
    Object o = e.getSource();
    int i, iWhichThing = -1;
    for (i = 0; i < mp_jColorDisplays.length; i++) {
      if (mp_jColorDisplays[i].equals(o)) {
        iWhichThing = i;
      }
    }

    if (iWhichThing == -1) {
      return;
    }

    //Create a color chooser
    Color oColor = JColorChooser.showDialog(this, 
        mp_sDisplayNames[iWhichThing], mp_oColors[iWhichThing]);

    //If the color is different, update
    if (null != oColor && !oColor.equals(mp_oColors[iWhichThing])) {
      //Set the new button background
      mp_jColorDisplays[iWhichThing].setIcon(new ModelIcon(15, 15, ModelIcon.RECTANGLE,
          oColor));
      mp_oColors[iWhichThing] = oColor;        
    }       
  }
}
