package sortie.datavisualizer;

import org.jfree.data.xy.AbstractIntervalXYDataset;
import org.jfree.data.xy.IntervalXYDataset;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * This is a hack of the HistogramDataset in JFreeChart.  It did not suit my
 * needs - particularly the ability to modify series.  So I've made my own.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 */

public class ModelHistogramDataset
    extends AbstractIntervalXYDataset
    implements IntervalXYDataset, Cloneable {
		
  /**Histogram list*/
  private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

  /**
   * Adds a series.
   *
   * @param sName  the series name.
   * @param p_fValues the values (<code>null</code> not permitted).
   * @param iNumberOfBins  the number of bins (must be at least 1).
   * @param fBinSize the size of the bins.
   */
  public void addSeries(String sName, float[] p_fValues, int iNumberOfBins,
                        float fBinSize) {
    if (p_fValues == null) {
      throw new IllegalArgumentException(
          "HistogramDataset.addSeries(...): 'values' argument must not be null."
          );
    }
    else if (iNumberOfBins < 1) {
      throw new IllegalArgumentException(
          "HistogramDataset.addSeries(...): number of bins must be at least 1"
          );
    }
    // work out bin strategy
    /*        Arrays.sort(p_fValues);
            float minimum = p_fValues[0];
            float maximum = p_fValues[p_fValues.length - 1];
            float binWidth = (maximum - minimum) / iNumberOfBins;
     */
    // set up the bins
    float fTemp = 0;
    HistogramBin[] p_oBins = new HistogramBin[iNumberOfBins];
    for (int i = 0; i < p_oBins.length; i++) {
      HistogramBin bin = new HistogramBin(fTemp, fTemp + fBinSize);
      fTemp = fTemp + fBinSize;
      p_oBins[i] = bin;
    }
    // fill the bins
    for (int i = 0; i < p_fValues.length; i++) {
      for (int j = 0; j < p_oBins.length; j++) {
        if (p_fValues[i] >= p_oBins[j].getStartBoundary()
            && p_fValues[i] <= p_oBins[j].getEndBoundary()) {
          // note the greedy <=
          p_oBins[j].incrementCount();
          break; // break out of inner loop
        }
      }
    }
    // generic map for each series
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("name", sName);
    map.put("bins", p_oBins);
    map.put("values.length", Integer.valueOf(p_fValues.length));
    map.put("bin width", Float.valueOf((float)fBinSize));
    list.add(map);
  }

  /**
   * Adds a series that is, for each bin, a total of the values of all other
   * series' values in that bin.
   */
  public void addTotalSeries() {
    int iNumSeries = getSeriesCount();
    if (iNumSeries <= 0) return;

    //Get the number of bins in the first series (all series should have the
    //same number)
    int iNumBins = getItemCount(0), i, j;

    //Declare our totals values array and initialize to zeroes
    float fTemp = 0;
    float fBinSize = getBinWidth(0);
    HistogramBin[] p_oBins = new HistogramBin[iNumBins];
    for (i = 0; i < p_oBins.length; i++) {
      HistogramBin bin = new HistogramBin(fTemp, fTemp + fBinSize);
      fTemp = fTemp + fBinSize;
      p_oBins[i] = bin;
    }

    //Add up the values in all series in all bins
    for (i = 0; i < iNumSeries; i++) {
      int iNumItems = getItemCount(i);
      for (j = 0; j < iNumItems; j++) {
        p_oBins[j].setCount(p_oBins[j].getCount() + getY(i, j).intValue());
      }
    }

    //Add the new series to the hash map
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("name", "Total");
    map.put("bins", p_oBins);
    map.put("values.length", Integer.valueOf(iNumBins));
    map.put("bin width", Float.valueOf((float)fBinSize));
    list.add(map);
  }

  /**
   * Returns the bins for a series.
   *
   * @param series  the series index.
   *
   * @return An array of bins.
   */
  private HistogramBin[] getBins(int series) {
    Map<String, Object> map = (Map<String, Object>) list.get(series);
    return (HistogramBin[]) map.get("bins");
  }

  /**
   * Returns the total for a series.
   *
   * @param series  the series index.
   *
   * @return The total.
   */
  //private int getTotal(int series) {
  //  Map map = (Map) list.get(series);
  //  return ( (Integer) map.get("values.length")).intValue();
  //}

  /**
   * Returns the bin width for a series.
   *
   * @param series  the series index (zero based).
   *
   * @return The bin width.
   */
  private float getBinWidth(int series) {
    Map<String, Object> map = (Map<String, Object>) list.get(series);
    return ( (Float) map.get("bin width")).floatValue();
  }

  /**
   * Returns the number of series in the dataset.
   *
   * @return The series count.
   */
  public int getSeriesCount() {
    return list.size();
  }

  /**
   * Returns the name for a series.
   *
   * @param series  the series index (zero based).
   *
   * @return The series name.
   */
  public Comparable<String> getSeriesKey(int series) {
    Map<String, Object> map = (Map<String, Object>) list.get(series);
    return (String) map.get("name");
  }

  /**
   * Returns the number of data items for a series.
   *
   * @param series  the series index (zero based).
   *
   * @return The item count.
   */
  public int getItemCount(int series) {
    return getBins(series).length;
  }

  /**
   * Returns the X value for a bin.
   * 
   * This value won't be used for plotting histograms, since the renderer will ignore it.
   * But other renderers can use it (for example, you could use the dataset to create a line
   * chart).
   *
   * @param series  the series index (zero based).
   * @param item  the item index (zero based).
   *
   * @return The start value.
   */
  public Number getX(int series, int item) {
    HistogramBin[] bins = getBins(series);
    HistogramBin bin = bins[item];
    double x = (bin.getStartBoundary() + bin.getEndBoundary()) / 2.;
    return Float.valueOf((float)x);
  }

  /**
   * Returns the Y value for a bin.
   *
   * @param series  the series index (zero based).
   * @param item  the item index (zero based) - i.e. bin number.
   *
   * @return The Y value.
   */
  public Number getY(int series, int item) {
    HistogramBin[] bins = getBins(series);
    return Float.valueOf( (float) bins[item].getCount());

  }

  /**
   * Sets the Y value for a bin.  Added by Lora to allow for adjusting
   * values to be per hectare.
   * @param series the series index (zero based).
   * @param item the item index (zero based).
   * @param iValue the new value to set.
   */
  public void setYValue(int series, int item, float iValue) {
    HistogramBin[] bins = getBins(series);
    bins[item].setCount(iValue);
  }

  /**
   * Returns the start value for a bin.
   *
   * @param series  the series index (zero based).
   * @param item  the item index (zero based).
   *
   * @return The start value.
   */
  public Number getStartX(int series, int item) {
    HistogramBin[] bins = getBins(series);

    //This makes the bars render all the same width.
    return Float.valueOf(bins[item].getStartBoundary() +
                      ( ( (float) series / (float) getSeriesCount()) *
                       bins[item].getBinWidth()));
  }

  /**
   * Returns the end value for a bin.
   *
   * @param series  the series index (zero based).
   * @param item  the item index (zero based).
   *
   * @return The end value.
   */
  public Number getEndX(int series, int item) {
    HistogramBin[] bins = getBins(series);
    //This makes the bars render all the same width
    return Float.valueOf(getStartX(series, item).floatValue() +
                      bins[item].getBinWidth() / getSeriesCount());
  }

  /**
   * Returns the Y value for a bin.
   *
   * @param series  the series index (zero based).
   * @param item  the item index (zero based).
   *
   * @return The Y value.
   */
  public Number getStartY(int series, int item) {
    return getY(series, item);
  }

  /**
   * Returns the Y value for a bin.
   *
   * @param series  the series index (zero based).
   * @param item  the item index (zero based).
   *
   * @return The Y value.
   */
  public Number getEndY(int series, int item) {
    return getY(series, item);
  }

  /**
   * I added this.  I wanted to be able to remove a series.
   * @param series The series index (zero based).
   */
  public void removeSeries(int series) {
    list.remove(series);
  }

  /**
   * I added this.  This creates a deep-enough clone of the dataset.  The
   * array list is cloned but not the underlying values - it is enough to be
   * able to remove references in the clone.  I don't need it deeper, so I
   * didn't write it deeper.
   * @return The clone.
   */
  public Object clone() {
    ModelHistogramDataset oClone = new ModelHistogramDataset();

    ArrayList<Map<String, Object>> oCloneList = (ArrayList<Map<String, Object>>) list;
    oClone.list = (ArrayList<Map<String, Object>>) oCloneList.clone();

    return oClone;
  }

}
