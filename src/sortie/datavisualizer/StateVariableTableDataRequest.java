package sortie.datavisualizer;

import javax.swing.JTable;
import javax.swing.JPanel;
import sortie.data.simpletypes.ModelException;
import sortie.gui.components.SortieFont;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;

/**
 * Produces a table of state variable results.  This requires the detailed
 * output file to have data saved from the "State Variables" grid.
 *
 * Copyright: Copyright (c) Charles D. Canham 2010
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>January 10, 2010: Created (LEM)
 */

public class StateVariableTableDataRequest extends DataRequest {

  /**The precipitation for this timestep*/
  private float m_fPrecip;
  
  /**The temperature for this timestep*/
  private float m_fTemp;
  
  /**The seasonal precip for this timestep*/
  private float m_fSeasPrecip;
  
  /**The water deficit for this timestep*/
  private float m_fWD;
    
  /**The code for the precipitation value.*/
  private int m_iPrecipCode;
  
  /**The code for the temperature value.*/
  private int m_iTempCode;
  
  /**The code for the seasonal precip value.*/
  private int m_iSeasPrecipCode;
  
  /**The code for the water deficit value.*/
  private int m_iWDCode;
  
  /**Table column headers.  Putting them here makes them consistent in all
   * possible display methods.*/
  private String[] mp_sColumnNames = {"Precipitation mm", "Temperature C", 
       "Seasonal Precip", "Water Deficit"};

  /**Name of the grid from which the data will be collected.*/
  private String m_sThisGrid;

  /**Number of columns in the table*/
  private int m_iNumCols = 4;

  /**
   * Constructor
   * @param sChartName Name of the chart
   * @param oManager Detailed output file manager
   * @throws ModelException not really, but I have to declare this.
   */
  public StateVariableTableDataRequest(String sChartName, DetailedOutputFileManager oManager) throws
      ModelException {
    super(sChartName, oManager);

    m_sThisGrid = "State Variables";

  }

  /**
   * Writes the table's data to tab-delimited text.
   * @param jOut FileWriter The file to write to.
   * @throws IOException if there's a problem writing the file.
   */
  protected void writeChartDataToFile(FileWriter jOut) throws IOException {
      
      int iCol;

      //Write header row
      jOut.write(mp_sColumnNames[0]);
      for (iCol = 1; iCol < m_iNumCols; iCol++) {
        jOut.write("\t" + mp_sColumnNames[iCol]);
      }
      jOut.write("\n");

      //Write out data
      jOut.write(String.valueOf(m_fPrecip) + "\t" +
                 String.valueOf(m_fTemp) + "\t" +
                 String.valueOf(m_fSeasPrecip) + "\t" +
                 String.valueOf(m_fWD) + "\n\n");       
    }

  /**
   * This accepts float data. 
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param fVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addGridFloatData(String sGridName, int iX, int iY, int iCode,
                               float fVal, boolean bBatchMode) {
    if (sGridName.equals(m_sThisGrid)) {
      if (iCode == m_iPrecipCode) {
        m_fPrecip = fVal;
      }
      else if (iCode == m_iTempCode) {
        m_fTemp = fVal;
      }
      else if (iCode == m_iSeasPrecipCode) {
        m_fSeasPrecip = fVal;
      }
      else if (iCode == m_iWDCode) {
        m_fWD = fVal;
      }
    }
  }

  /**
   * This wants floats.
   * @return True.
   */
  public boolean wantAnyGridFloats() {
    return true;
  }

  /**
   * Accepts a grid float data member code for future reference when passed
   * float data members.
   * @param sGridName Name of the grid
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridFloatDataMemberCode(String sGridName, String sLabel,
                                         int iCode) {
    if (sGridName.equals(m_sThisGrid)) {
      
      if (sLabel.equalsIgnoreCase("Temp.C")) {
        m_iTempCode = iCode;
      }
      else if (sLabel.equalsIgnoreCase("Precip.mm")) {
        m_iPrecipCode = iCode;
      }      
      else if (sLabel.equalsIgnoreCase("SeasonalPrecip")) {
        m_iSeasPrecipCode = iCode;
      }
      else if (sLabel.equalsIgnoreCase("WaterDeficit")) {
        m_iWDCode = iCode;
      }
    }
  }

  /**
   * Creates a table of the results that have been collected.
   * @param oLegend The species legend.
   * @param sChartTitle The chart title.
   * @return A JInternalFrame with the table present in it.
   * @throws sortie.data.simpletypes.ModelException
   */
  ModelInternalFrame drawChart(Legend oLegend, String sChartTitle) throws
      ModelException {

    m_oChartFrame = new ModelInternalFrame(sChartTitle, oLegend);
    m_oChartFrame.setContentPane(createTable(oLegend));
    m_oChartFrame = DataGrapher.addFileMenu(m_oChartFrame, this, m_bShowOneTimestep);
    return m_oChartFrame;
  }

  void updateChart(Legend oLegend) throws ModelException {
    m_oChartFrame.setContentPane(createTable(oLegend));
  }

  /**
   * Creates the results table.
   * @param oLegend Legend for this chart.
   * @return Panel containing the table.
   * @throws ModelException If anything goes wrong with the drawing.
   */
  private JPanel createTable(Legend oLegend) throws ModelException {
    //I need to be able to format a range of numbers
    NumberFormat jFormat = NumberFormat.getInstance();
    jFormat.setMaximumFractionDigits(3);
         
    //Create our table data array
    Object[][] p_oData = new Object[1][];
    p_oData[0] = new Object[m_iNumCols];

    p_oData[0][0] = jFormat.format(m_fPrecip);
    p_oData[0][1] = jFormat.format(m_fTemp);
    p_oData[0][2] = jFormat.format(m_fSeasPrecip);
    p_oData[0][3] = jFormat.format(m_fWD);
        
    //Now our column names
    JTable jTable = new JTable(p_oData, mp_sColumnNames);
    jTable.setName("ResultsTable");
    jTable.setGridColor(Color.WHITE);
    jTable.setBackground(Color.WHITE);
    jTable.setFont(new SortieFont());
    jTable.getTableHeader().setFont(new SortieFont());

    //Create the panel and add the table to it.
    JPanel jPanel = new JPanel(new BorderLayout());
    jPanel.add(jTable.getTableHeader(), BorderLayout.NORTH);
    jPanel.add(jTable, BorderLayout.CENTER);
    jPanel.setBackground(Color.WHITE);
    jPanel.add(jTable);
    return jPanel;
  }
}
