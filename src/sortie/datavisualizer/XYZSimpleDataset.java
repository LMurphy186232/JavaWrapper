package sortie.datavisualizer;

import org.jfree.data.xy.AbstractXYZDataset;
import org.jfree.data.xy.XYZDataset;

/**
 * A simple implementation of the jorg.jfree.data.xyXYZDataset interface that
 * stores data values in arrays of bool primitives.
 *
 * Stolen and modded from the original in JFreeChart by Lora E. Murphy. I did
 * this for speed and ease, and to work with my XYSimpleCellRenderer.
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 21, 2006:  Created during upgrade to JFreeChart 1.0.3 (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 */
public class XYZSimpleDataset
    extends AbstractXYZDataset
    implements XYZDataset {
  
  

  /**
   * Storage for the series keys.
   */
  public String[] mp_sSeriesKeys;

  /**
   * Storage for the series in the dataset.  This is a grid of values sized
   * # series by # plot X cells by # plot Y cells.
   *
   * Now: We will fake the XYZ data structure of JFreeChart. That would mean
   * that each series was a collection of objects each with 3 values, one
   * being X, one being Y, and one being Z. We will pretend that we are doing
   * it that way, holding all values for the grid in order, and the indexing
   * goes (X Cell * Num X Cells) + Y Cell.
   *
   * True means a value is selected. False means  it is not.
   */
  public boolean[][][] mp_bData;

  public int m_iNumXCells = 0,  /**<Number of X cells */
             m_iNumYCells = 0;  /**<Number of Y cells */

  /**
   * Constructor.
   * @param iNumSeries Number of dataset series.
   * @param iNumXCells Number of X cells.
   * @param iNumYCells Number of Y cells.
   */
  public XYZSimpleDataset(int iNumSeries, int iNumXCells, int iNumYCells) {
    m_iNumXCells = iNumXCells;
    m_iNumYCells = iNumYCells;
    int i, j, k;
    this.mp_sSeriesKeys = new String[iNumSeries];
    this.mp_bData = new boolean[iNumSeries][iNumXCells][iNumYCells];
    for (i = 0; i < iNumSeries; i++) {
      mp_sSeriesKeys[i] = "";
      for (j = 0; j < iNumXCells; j++) {
        for (k = 0; k < iNumYCells; k++) {
          mp_bData[i][j][k] = false;
        }
      }
    }
  }

  /**
   * Returns the number of series in the dataset.
   * @return The series count.
   */
  public int getSeriesCount() {
    return mp_sSeriesKeys.length;
  }

  /**
   * Returns the key for a series.
   * @param series  the series index (in the range 0 to getSeriesCount() - 1).
   * @return The key for the series.
   * @throws IllegalArgumentException if <code>series</code> is not in the
   *     specified range.
   */
  public Comparable<String> getSeriesKey(int series) {
    if ( (series < 0) || (series >= mp_sSeriesKeys.length)) {
      throw new IllegalArgumentException("Series index out of bounds");
    }
    return mp_sSeriesKeys[series];
  }

  /**
   * Returns the index of the series with the specified key, or -1 if there
   * is no such series in the dataset.
   *
   * @param seriesKey  the series key (<code>null</code> permitted).
   *
   * @return The index, or -1.
   */
  public int indexOf(Comparable seriesKey) {
    if (null == seriesKey) return -1;
    int i;
    for (i = 0; i < mp_sSeriesKeys.length; i++) {
      if (seriesKey.equals(mp_sSeriesKeys[i])) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Returns the order of the domain (x-) values in the dataset.  In this
   * implementation, we cannot guarantee that the x-values are ordered, so
   * this method returns <code>DomainOrder.NONE</code>.
   *
   * @return <code>DomainOrder.NONE</code>.
   */
  public org.jfree.data.DomainOrder getDomainOrder() {
    return org.jfree.data.DomainOrder.NONE;
  }

  /**
   * Returns the number of items in the specified series.
   * @param series  the series index (in the range 0 to getSeriesCount() - 1).
   * @return The item count.
   * @throws IllegalArgumentException if <code>series</code> is not in the
   *     specified range.
   */
  public int getItemCount(int series) {
    if ( (series < 0) || (series >= mp_sSeriesKeys.length)) {
      throw new IllegalArgumentException("Series index out of bounds");
    }
    return mp_bData[series].length * mp_bData[series][0].length;
  }

  /**
   * Returns the x-value for an item within a series.
   * @param series  the series index (in the range 0 to getSeriesCount() - 1).
   * @param item  the item index (in the range 0 to getItemCount(series)).
   * @return The x-value.
   */
  public double getXValue(int series, int item) {
    return java.lang.Math.floor((double)item / (double)m_iNumYCells);
  }

  /**
   * Returns the x-value for an item within a series.
   * @param series  the series index (in the range 0 to getSeriesCount() - 1).
   * @param item  the item index (in the range 0 to getItemCount(series)).
   * @return The x-value.
   */
  public Number getX(int series, int item) {
    return new Float(getXValue(series, item));
  }

  /**
   * Returns the y-value for an item within a series.
   * @param series  the series index (in the range 0 to getSeriesCount() - 1).
   * @param item  the item index (in the range 0 to getItemCount(series)).
   * @return The y-value.
   */
  public double getYValue(int series, int item) {
    return item - (m_iNumYCells * (java.lang.Math.floor((double)item / (double)m_iNumYCells)));
  }

  /**
   * Returns the y-value for an item within a series.
   * @param series  the series index (in the range 0 to getSeriesCount() - 1).
   * @param item  the item index (in the range 0 to getItemCount(series)).
   * @return The y-value.
   */
  public Number getY(int series, int item) {
    return new Float(getYValue(series, item));
  }

  /**
   * Returns the z-value for an item within a series.
   * @param series  the series index (in the range 0 to getSeriesCount() - 1).
   * @param item  the item index (in the range 0 to getItemCount(series)).
   * @return The z-value, 1 for true, 0 for false.
   */
  public double getZValue(int series, int item) {
    int iX = (int) getXValue(series, item);
    int iY = (int) getYValue(series, item);
    return (mp_bData[series][iX][iY] ? 1 : 0);
  }

  /**
   * Returns the z-value for an item within a series.
   * @param series  the series index (in the range 0 to getSeriesCount() - 1).
   * @param item  the item index (in the range 0 to getItemCount(series)).
   * @return The z-value.
   */
  public Number getZ(int series, int item) {
    return new Float(getZValue(series, item));
  }
  
  public Object clone() {
    XYZSimpleDataset oClone = new XYZSimpleDataset(mp_sSeriesKeys.length, m_iNumXCells, m_iNumYCells);
    int i, j, k;
    for (i = 0; i < mp_sSeriesKeys.length; i++) {
      oClone.mp_sSeriesKeys[i] = this.mp_sSeriesKeys[i];
      for (j = 0; j < m_iNumXCells; j++) {
        for (k = 0; k < m_iNumYCells; k++) {
          oClone.mp_bData[i][j][k] = this.mp_bData[i][j][k];
        }
      }
    }
    return oClone;
  }
}
