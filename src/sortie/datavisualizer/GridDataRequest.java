package sortie.datavisualizer;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.Component;
import java.util.ArrayList;

import org.jfree.data.xy.DefaultXYZDataset;

import sortie.data.simpletypes.ModelException;

/**
 * Draws a map for a single grid's data member.  It can graph an integer, a
 * float, or a bool (which converts to an int of 0 or 1).  The values are
 * mapped on a grayscale.  The minimum (black) value will be 0, or the smallest
 * value if it is less than 0.  The maximum (white) value will be found at
 * each timestep, and the knee will be set between the minimum and maximum
 * value.
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */
public class GridDataRequest
    extends DataRequest {

  /**This holds the actual grid XYZ data.  X is the X cell number, Y is the Y
   * cell number, Z is the value of the cell to graph.*/
  private ArrayList<XYZDataItem> m_oSeries = new ArrayList<XYZDataItem>(0);

  /**The label of the data member to graph*/
  String m_sDataMemberLabel,
      /**The grid of the data member being graphed*/
      m_sGridName;

  /**Minimum value, in case the user sets it*/
  private float m_fMinDisplayValue,
      /**Knee value, in case the user sets it*/
      m_fKneeDisplayValue,
      /**Maximum value, in case the user sets it*/
      m_fMaxDisplayValue;

  /**Data member code of the value we're graphing*/
  private int m_iDataCode,
      /**Minimum color value, in case the user sets it*/
      m_iMinDisplayColor,
      /**Knee color value, in case the user sets it*/
      m_iKneeDisplayColor,
      /**Maximum color value, in case the user sets it*/
      m_iMaxDisplayColor;

  /**Whether or not the data member is an integer*/
  private boolean m_bDataIsInt,
      /**Whether or not the data member is a float*/
      m_bDataIsFloat,
      /**Whether or not the data member is a boolean*/
      m_bDataIsBoolean,
      /**Whether or not the user has manually set grayscale display - if true,
       * there will be no more automatic updating of the grayscale values*/
      m_bUserSetDisplay = false;

  /**
   * Constructor.
   * @param sChartName Name of chart.
   * @param sDataMemberLabel Label of data member to graph.
   * @param sGridName Name of the grid that has the data member.
   * @param oManager DetailedOutputFileManager for this file.
   */
  public GridDataRequest(String sChartName, String sGridName,
                         String sDataMemberLabel, DetailedOutputFileManager oManager) {
    super(sChartName, oManager);
    m_sDataMemberLabel = sDataMemberLabel;
    m_sGridName = sGridName;

    //Set all the data member flags except chars to true - we will find which
    //data member this is and re-set the flags
    m_bDataIsInt = true;
    m_bDataIsFloat = true;
    m_bDataIsBoolean = true;

    m_iDataCode = -1;
  }
  
  /**
   * This will be called for all open data requests just before time step 
   * parsing begins.
   * @param iTimestep Timestep to parse.
   * @param bBatchMode Whether or not this is in the context of batch 
   * extraction mode.
   */
  public void getReadyForTimestepParse(int iTimestep, boolean bBatchMode)throws ModelException {
    m_oSeries.clear();
  }

  /**
   * This will be called for all open data requests after time step 
   * parsing is completed.
   * @param bBatchMode Whether or not this is in the context of batch 
   * extraction mode.
   */
  //public abstract void timestepParseFinished(boolean bBatchMode);
  public void timestepParseFinished(boolean bBatchMode)throws ModelException {;}

  /**
   * Writes the grid map's data to tab-delimited text.
   * @param jOut java.io.FileWriter The file to write to.
   * @throws java.io.IOException if there's a problem writing the file.
   */
  protected void writeChartDataToFile(java.io.FileWriter jOut) throws java.io.
      IOException {

    if (m_oSeries == null) {
      jOut.write("This grid has no data.");
      return;
    }

    //Find the X and Y extent of the dataset
    int i, iMaxX = 0, iMaxY = 0;
    for (i = 0; i < m_oSeries.size(); i++) {
      XYZDataItem oItem = m_oSeries.get(i);
      if (iMaxX < (int)oItem.fX) {
        iMaxX = (int)oItem.fX;
      }
      if (iMaxY < oItem.fY) {
        iMaxY = (int)oItem.fY;
      }
    }
    iMaxX++; //remember max value is still an array index
    iMaxY++;

    //Create an array of strings to write.  X cell = column, Y cell = row
    String[][] p_sVals = new String[iMaxX][iMaxY];
    int iCol, iRow;
    for (iCol = 0; iCol < p_sVals.length; iCol++) {
      for (iRow = 0; iRow < p_sVals[iCol].length; iRow++) {
        p_sVals[iCol][iRow] = "";
      }
    }

    for (i = 0; i < m_oSeries.size(); i++) {
      XYZDataItem oItem = m_oSeries.get(i);
      if (m_bDataIsFloat || m_bDataIsInt) {
        p_sVals[(int)oItem.fX][(int)oItem.fY] = String.valueOf(oItem.fZ);
        iMaxY = (int)oItem.fY;
      }
      else {
        int iValue = (int)oItem.fZ;
        if (iValue == 0) {
          p_sVals[(int)oItem.fX][(int)oItem.fY] = "false";
        }
        else {
          p_sVals[(int)oItem.fX][(int)oItem.fY] = "true";
        }
      }
    }

    //Write out the values
    //Header row - remember that first array index = X = columns
    jOut.write("East->");
    for (iCol = 0; iCol < p_sVals.length; iCol++) {
      jOut.write("\t" + String.valueOf(iCol));
    }
    jOut.write("\n");

    //Write the rows backwards
    for (iRow = p_sVals[0].length - 1; iRow >= 0; iRow--) {

      jOut.write(String.valueOf(iRow));
      for (iCol = 0; iCol < p_sVals.length; iCol++) {
        jOut.write("\t" + p_sVals[iCol][iRow]);
      }
      jOut.write("\n");
    }
  }

  /**
   * Whether or not the data member we want is boolean.
   * @return True if our data member is boolean, false if not.
   */
  public boolean wantAnyGridBools() {
    return m_bDataIsBoolean;
  }

  /**
   * Whether or not the data member we want is integer.
   * @return True if our data member is integer, false if not.
   */
  public boolean wantAnyGridInts() {
    return m_bDataIsInt;
  }

  /**
   * Whether or not the data member we want is float.
   * @return True if our data member is float, false if not.
   */
  public boolean wantAnyGridFloats() {
    return m_bDataIsFloat;
  }

  /**
   * Checks to see if our data member's a float.  If the label matches, this
   * grabs the code and sets the flags.
   * @param sGridName Name of the grid
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridFloatDataMemberCode(String sGridName, String sLabel,
                                         int iCode) {
    if (sGridName.equals(m_sGridName) && sLabel.equals(m_sDataMemberLabel)) {

      //Bingo - it's this data member that we're graphing
      m_iDataCode = iCode;
      m_bDataIsBoolean = false;
      m_bDataIsInt = false;
    }
  }

  /**
   * Checks to see if our data member's an integer.  If the label matches,
   * this grabs the code and sets the flags.
   * @param sGridName Name of the grid
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridIntDataMemberCode(String sGridName, String sLabel,
                                       int iCode) {
    if (sGridName.equals(m_sGridName) && sLabel.equals(m_sDataMemberLabel)) {

      //Bingo - it's this data member that we're graphing
      m_iDataCode = iCode;
      m_bDataIsBoolean = false;
      m_bDataIsFloat = false;
    }
  }

  /**
   * Checks to see if our data member's a boolean.  If the label matches,
   * this grabs the code and sets the flags.
   * @param sGridName Name of the grid
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridBoolDataMemberCode(String sGridName, String sLabel,
                                        int iCode) {
    if (sGridName.equals(m_sGridName) && sLabel.equals(m_sDataMemberLabel)) {

      //Bingo - it's this data member that we're graphing
      m_iDataCode = iCode;
      m_bDataIsFloat = false;
      m_bDataIsInt = false;
    }
  }

  /**
   * Accepts the value of our data member from the parser, if bool.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param bVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addGridBoolData(String sGridName, int iX, int iY, int iCode,
                              boolean bVal, boolean bBatchMode) {
    if (m_bDataIsBoolean && sGridName.equals(m_sGridName) &&
        iCode == m_iDataCode) {
      if (bVal == true) {
        m_oSeries.add(new XYZDataItem(iX, iY, 1));
      }
      else {
        m_oSeries.add(new XYZDataItem(iX, iY, 0));
      }
    }
  }

  /**
   * Accepts the value of our data member from the parser, if float.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param fVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addGridFloatData(String sGridName, int iX, int iY, int iCode,
                               float fVal, boolean bBatchMode) {
    if (m_bDataIsFloat && sGridName.equals(m_sGridName) &&
        iCode == m_iDataCode) {
      m_oSeries.add(new XYZDataItem(iX, iY, fVal));
    }
  }

  /**
   * Accepts the value of our data member from the parser, if float.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param iVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addGridIntData(String sGridName, int iX, int iY, int iCode,
                             int iVal, boolean bBatchMode) {
    if (m_bDataIsInt && sGridName.equals(m_sGridName) &&
        iCode == m_iDataCode) {
      m_oSeries.add(new XYZDataItem(iX, iY, iVal));
    }
  }

  /**
   * Updates the chart with whatever's in our dataset.
   * @param oLegend Legend.  Ignored.
   * @throws sortie.data.simpletypes.ModelException If there's a problem.
   */
  void updateChart(Legend oLegend) throws sortie.data.simpletypes.ModelException {
    DefaultXYZDataset oDataset = new DefaultXYZDataset();
    XYZDataItem oItem;
    double[][] p_oSeries = new double[3][m_oSeries.size()];
    int i;
    for (i = 0; i < m_oSeries.size(); i++) {
      oItem = m_oSeries.get(i);
      p_oSeries[0][i] = oItem.fX;
      p_oSeries[1][i] = oItem.fY;
      p_oSeries[2][i] = oItem.fZ;
    }
    oDataset.addSeries("1", p_oSeries);

    DataGrapher.updateGridMap(oDataset, m_oChartFrame, oLegend,
                              getRenderer());

  }

  /**
   * Draws the grayscale grid map.
   * @param oLegend Tree legend - ignored.
   * @param sChartTitle Title of chart.
   * @return The chart in a JInternalFrame.
   * @throws sortie.data.simpletypes.ModelException
   */
  ModelInternalFrame drawChart(Legend oLegend, String sChartTitle) throws
      sortie.data.simpletypes.ModelException {

    //Add the series to the dataset
    DefaultXYZDataset oDataset = new DefaultXYZDataset();
    XYZDataItem oItem;
    double[][] p_oSeries = new double[3][m_oSeries.size()];
    int i;
    for (i = 0; i < m_oSeries.size(); i++) {
      oItem = m_oSeries.get(i);
      p_oSeries[0][i] = oItem.fX;
      p_oSeries[1][i] = oItem.fY;
      p_oSeries[2][i] = oItem.fZ;
    }
    oDataset.addSeries("1", p_oSeries);

    m_oChartFrame = DataGrapher.drawGridMap(oDataset, "East ->", "",
                                            sChartTitle,
                                            (int) m_oManager.
                                            getXPlotLength(),
                                            (int) m_oManager.
                                            getYPlotLength(),
                                            oLegend, getRenderer(), this);
    return m_oChartFrame;

  }

  /**
   * Creates the XYCellRenderer for this graph.  If the user has set grayscale
   * controls, they are passed to the renderer.  If not, this sets the defaults
   * according to the dataset.  The minimum data value is set to 0 unless there
   * is a smaller value, in which case that becomes the minimum.  The maximum
   * data value is set to the highest Z value in the dataset.  The knee value
   * is set halfway from the minimum to the maximum.  The color values are left
   * alone.
   * @return The set up XYCellRenderer.
   * @throws sortie.data.simpletypes.ModelException if the color values are rejected.
   */
  private XYCellRenderer getRenderer() throws sortie.data.simpletypes.ModelException {

    XYCellRenderer oRenderer = new XYCellRenderer();

    //If the user has set values, pass them
    if (m_bUserSetDisplay) {

      oRenderer.setMinimumValue(m_fMinDisplayValue);
      oRenderer.setKneeValue(m_fKneeDisplayValue);
      oRenderer.setMaximumValue(m_fMaxDisplayValue);
      oRenderer.setMinimumColor(m_iMinDisplayColor, m_iMinDisplayColor,
                                m_iMinDisplayColor, 0);
      oRenderer.setKneeColor(m_iKneeDisplayColor, m_iKneeDisplayColor,
                             m_iKneeDisplayColor, 0);
      oRenderer.setMaximumColor(m_iMaxDisplayColor, m_iMaxDisplayColor,
                                m_iMaxDisplayColor, 0);

    }
    else {

      //Set defaults
      //Get the highest and lowest values in the dataset
      float fMinimum = 0;
      float fMaximum = 0;
      float fKnee = 0;
      float fZ;

      int i;
      for (i = 0; i < m_oSeries.size(); i++) {
        fZ = (float)m_oSeries.get(i).fZ;

        if (fZ < fMinimum) {
          fMinimum = fZ;
        }

        if (fZ > fMaximum) {
          fMaximum = fZ;
        }

        fKnee = fMaximum - ( (fMaximum - fMinimum) / 2);
      }

      oRenderer.setMaximumValue(fMaximum);
      oRenderer.setMinimumValue(fMinimum);
      oRenderer.setKneeValue(fKnee);
    }

    //Set cell lengths
    oRenderer.setXCellLength(m_oManager.getGridXCellLength(m_sGridName));
    oRenderer.setYCellLength(m_oManager.getGridYCellLength(m_sGridName));

    return oRenderer;
  }

  /**
   * Sets the minimum display value for the grid.  Same as XYCellRenderer's
   * minimum value.
   * @param fValue Minimum display value for the grid.
   */
  public void setMinimumDisplayValue(float fValue) {
    m_fMinDisplayValue = fValue;
  }

  /**
   * Sets the knee display value for the grid.  Same as XYCellRenderer's
   * knee value.
   * @param fValue Knee display value for the grid.
   */
  public void setKneeDisplayValue(float fValue) {
    m_fKneeDisplayValue = fValue;
  }

  /**
   * Sets the maximum display value for the grid.  Same as XYCellRenderer's
   * maximum value.
   * @param fValue Maximum display value for the grid.
   */
  public void setMaximumDisplayValue(float fValue) {
    m_fMaxDisplayValue = fValue;
  }

  /**
   * Sets the minimum display color for the grid.  Same as XYCellRenderer's
   * minimum color.
   * @param iValue Minimum display color for the grid.
   */
  public void setMinimumDisplayColor(int iValue) {
    m_iMinDisplayColor = iValue;
  }

  /**
   * Sets the knee display color for the grid.  Same as XYCellRenderer's
   * knee color.
   * @param iValue Knee display color for the grid.
   */
  public void setKneeDisplayColor(int iValue) {
    m_iKneeDisplayColor = iValue;
  }

  /**
   * Sets the maximum display color for the grid.  Same as XYCellRenderer's
   * maximum color.
   * @param iValue Maximum display color for the grid.
   */
  public void setMaximumDisplayColor(int iValue) {
    m_iMaxDisplayColor = iValue;
  }

  /**
   * Sets whether or not the user set display values.
   * @param bValue Whether or not the user set display values.
   */
  public void setUserSetDisplay(boolean bValue) {
    m_bUserSetDisplay = bValue;
  }
}

/**
 * Performs the action when the checkbox for using logarithmic axis is checked
 * on a histogram.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */
class GrayscaleListener
    implements java.awt.event.ActionListener {
  GridDataRequest oAdaptee; /**<Window for which to listen for the checkbox click*/

  /**
   * Constructor
   * @param oAdaptee GridDataRequest Window for which to listen for the
   * checkbox click
   */
  GrayscaleListener(GridDataRequest oAdaptee) {
    this.oAdaptee = oAdaptee;
  }

  /**
   * Performs the action when this is triggered.
   * @param e ActionEvent Event that triggered this.
   */
  public void actionPerformed(ActionEvent e) {

    //The action will have been sent by the button - find the text values
    //that were set
    String sName;
    int i;

    //Get the panel that holds the grayscale panel
    Component jButton = (Component) e.getSource();
    JPanel jControlPanel = (JPanel) jButton.getParent().getParent();

    //Get the grayscale values - first find the jGrayscalePanel
    JPanel jGrayscalePanel = null;

    for (i = 0; i < jControlPanel.getComponentCount(); i++) {
      sName = jControlPanel.getComponent(i).getName();
      if (sName != null && sName.equals("jGrayscalePanel")) {
        jGrayscalePanel = (JPanel) jControlPanel.getComponent(i);
      }
    }

    JTextField jField = null;
    for (i = 0; i < jGrayscalePanel.getComponentCount(); i++) {
      sName = jGrayscalePanel.getComponent(i).getName();
      if (sName != null) {
        if (sName.equals("jMinValue")) {
          jField = (JTextField) jGrayscalePanel.getComponent(i);
          try {
            oAdaptee.setMinimumDisplayValue(new Float(jField.getText()).
                                            floatValue());
          } catch (java.lang.NumberFormatException oErr) {
            javax.swing.JOptionPane.showMessageDialog(oAdaptee.m_oChartFrame,
                                        "The minimum value is not a recognized number.");
            return;
          }
        }
        else if (sName.equals("jMinPix")) {
          jField = (JTextField) jGrayscalePanel.getComponent(i);
          try {
            oAdaptee.setMinimumDisplayColor(new Integer(jField.getText()).
                                            intValue());
          } catch (java.lang.NumberFormatException oErr) {
            javax.swing.JOptionPane.showMessageDialog(oAdaptee.m_oChartFrame,
                                        "The minimum grayscale value is not a recognized number.");
            return;
          }

        }
        else if (sName.equals("jKneeValue")) {
          jField = (JTextField) jGrayscalePanel.getComponent(i);
          try {
            oAdaptee.setKneeDisplayValue(new Float(jField.getText()).floatValue());
          } catch (java.lang.NumberFormatException oErr) {
            javax.swing.JOptionPane.showMessageDialog(oAdaptee.m_oChartFrame,
                                        "The knee value is not a recognized number.");
            return;
          }
        }
        else if (sName.equals("jKneePix")) {
          jField = (JTextField) jGrayscalePanel.getComponent(i);
          try {
            oAdaptee.setKneeDisplayColor(new Integer(jField.getText()).intValue());
          } catch (java.lang.NumberFormatException oErr) {
            javax.swing.JOptionPane.showMessageDialog(oAdaptee.m_oChartFrame,
                                        "The knee grayscale value is not a recognized number.");
            return;
          }
        }
        else if (sName.equals("jMaxValue")) {
          jField = (JTextField) jGrayscalePanel.getComponent(i);
          try {
            oAdaptee.setMaximumDisplayValue(new Float(jField.getText()).
                                            floatValue());
          } catch (java.lang.NumberFormatException oErr) {
            javax.swing.JOptionPane.showMessageDialog(oAdaptee.m_oChartFrame,
                                        "The maximum value is not a recognized number.");
            return;
          }
        }
        else if (sName.equals("jMaxPix")) {
          jField = (JTextField) jGrayscalePanel.getComponent(i);
          try {
            oAdaptee.setMaximumDisplayColor(new Integer(jField.getText()).
                                            intValue());
          } catch (java.lang.NumberFormatException oErr) {
            javax.swing.JOptionPane.showMessageDialog(oAdaptee.m_oChartFrame,
                                        "The maximum grayscale value is not a recognized number.");
            return;
          }
        }
      }
    }

    oAdaptee.setUserSetDisplay(true);
    try {
      oAdaptee.updateChart(null);
    }
    catch (sortie.data.simpletypes.ModelException oErr) {
    }
  }
}
