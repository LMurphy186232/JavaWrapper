package sortie.datavisualizer;

import java.awt.BasicStroke;
import java.awt.geom.Ellipse2D;
import java.text.NumberFormat;

import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.labels.XYZToolTipGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.RectangleEdge;

/**
 * A renderer that draws a circle at each data point.  The renderer expects
 * the dataset to be an XYZDataset. The XY values are the position of the 
 * ellipse; the Z is its diameter, in m.
 *
 * Original author of XYBubbleRenderer, upon which this is based - David
 * Gilbert
 * <p>Title: Data Visualizer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>May 9, 2006:  Added support for a minimum display DBH (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 * <br>March 6, 2008: Added a separate argument for plot area, because it
 * was not being calculated correctly when there had been zooming (LEM)
 */
public class XYTreeRenderer
    extends AbstractXYItemRenderer
    implements XYItemRenderer, Cloneable {
  
  

  /**Controls how large trees appear on the screen*/
  private double m_fScaleFactor = 1;

  /**The minimum Z value to display*/
  private double m_fMinZ = 0;

  /** The X axis length, whether or not the whole plot is being displayed.
   * This allows the renderer to scale the size of the tree circles. */
  private double m_fXAxisLength;
  /** The Y axis length, whether or not the whole plot is being displayed.
   * This allows the renderer to scale the size of the tree circles. */
  private double m_fYAxisLength;

  /**
   * Constructs a new renderer.
   * @param fLengthX Visible plot length in the X direction. If zoomed, this
   * may not match the whole plot length.
   * @param fLengthY Visible plot length in the Y direction. If zoomed, this
   * may not match the whole plot length.
   * @param fXAxisLength Entire plot length, X direction
   * @param fYAxisLength Entire plot length, Y direction
   */
  public XYTreeRenderer(double fLengthX, double fLengthY, double fXAxisLength, 
        double fYAxisLength) {
    super();
    setBaseToolTipGenerator(new TreeTooltipGenerator());
    m_fXAxisLength = fXAxisLength;
    m_fYAxisLength = fYAxisLength;
  }

  /**
   * Sets the scale factor, which controls how large trees appear on the
   * screen.
   * @param fScaleFactor  Scale factor.
   */
  public void setScaleFactor(double fScaleFactor) {
    m_fScaleFactor = fScaleFactor;
  }

  /**
   * Gets the scale factor, which controls how large trees appear on the
   * screen.
   * @return Scale factor.
   */
  public double getScaleFactor() {
    return m_fScaleFactor;
  }

  /**
   * Set the value for minimum Z.
   * @param fMinZ  Value for minimum Z.
   */
  public void setMinZ(double fMinZ) {
    m_fMinZ = fMinZ;
  }

  /**
   * Gets the current value for minimum Z.
   * @return  The minimum Z.
   */
  public double getMinZ() {
    return m_fMinZ;
  }

  /**
   * Draws the visual representation of a single data item.
   *
   * @param g2 - the graphics device.
   * @param state - the renderer state.
   * @param dataArea - the area within which the data is being rendered.
   * @param info - collects drawing info.
   * @param plot - the plot (can be used to obtain standard color information etc).
   * @param domainAxis - the domain axis.
   * @param rangeAxis - the range axis.
   * @param dataset - the dataset.
   * @param series - the series index (zero-based).
   * @param item - the item index (zero-based).
   * @param crosshairState - crosshair information for the plot (null permitted).
   * @param pass - the pass index.
   */
  public void drawItem(java.awt.Graphics2D g2,
                       XYItemRendererState state,
                       java.awt.geom.Rectangle2D dataArea,
                       PlotRenderingInfo info,
                       XYPlot plot,
                       ValueAxis domainAxis,
                       ValueAxis rangeAxis,
                       XYDataset dataset,
                       int series, int item,
                       CrosshairState crosshairState,
                       int pass) {

    if (!(dataset instanceof XYZDataset)) return;

    // get the data point...
    double fX = dataset.getXValue(series, item);
    double fY = dataset.getYValue(series, item);
    double fDbh = ((XYZDataset) dataset).getZValue(series, item);

    //Make sure the DBH (z) value is above our minimum
    if (fDbh < m_fMinZ) return;

    //Get the edges of the plot
    RectangleEdge domainAxisLocation = plot.getDomainAxisEdge();
    RectangleEdge rangeAxisLocation = plot.getRangeAxisEdge();

    //Translate the coordinates from XY to the Java2D coordinates where
    //the origin is at the upper right corner of the plot
    double fTransX = domainAxis.valueToJava2D(fX, dataArea,
        domainAxisLocation);
    double fTransY = rangeAxis.valueToJava2D(fY, dataArea,
        rangeAxisLocation);

    //Calculate the radius of our circle - this sizes it about right
    //Note that this is a simplification that only works if the chart is
    //sized according to the plot lengths
    //float fRadius = fDbh * m_fScaleFactor * (m_fPlotArea / fDisplayArea); //radius of circle
    double fWidth = fDbh * m_fScaleFactor * (m_fXAxisLength / (plot.getDomainAxis().getUpperBound() -
        plot.getDomainAxis().getLowerBound())); 
    double fHeight = fDbh * m_fScaleFactor * (m_fYAxisLength / (plot.getRangeAxis().getUpperBound() -
        plot.getRangeAxis().getLowerBound())); //radius of circle

    //Create our ellipse
    Ellipse2D jCircle = new Ellipse2D.Double(fTransX - fWidth, //X coordinate of rectangle enclosing circle
                                            fTransY - fHeight, //Y coordinate of rectangle enclosing circle
                                            fWidth, //width of circle
                                            fHeight); //height of circle
    g2.setStroke(new BasicStroke(1.0f));
    g2.setPaint(getItemPaint(series, item));
    g2.draw(jCircle);

    // Add entity info - this is what allows the tooltip display to work
    EntityCollection oEntities = null;
    if (info != null) {
      oEntities = info.getOwner().getEntityCollection();
      if (oEntities != null) {
        addEntity(oEntities, jCircle, dataset, series, item, fTransX, fTransY);
      }
    }
  }

  /**
   * Returns a clone of the renderer, but not a good one.
   *
   * @return A clone.
   *
   * @throws CloneNotSupportedException  if the renderer cannot be cloned.
   */
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  /**
   * Provides a more informative tooltip string for cell values.
   * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
   * <p>Company: Cary Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   */
  private class TreeTooltipGenerator implements XYZToolTipGenerator {

     private NumberFormat m_jFormat = java.text.NumberFormat.getInstance();

     public TreeTooltipGenerator() {
       super();
       m_jFormat.setMaximumFractionDigits(2);
     }

    /**
     * Generates a label string for an item in the dataset.
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The label (possibly <code>null</code>).
     */
    public String generateToolTip(XYZDataset dataset, int series, int item) {
        String result = null;
        result = ((String)dataset.getSeriesKey(series)).replace('_', ' ') +
            ": X=" + m_jFormat.format(dataset.getX(series, item)) + ", Y=" +
                 m_jFormat.format(dataset.getY(series, item)) + ", Circle diam=" +
                 m_jFormat.format(dataset.getZ(series, item)) + " m" ;
        return result;
    }

  /**
   * Generates a label string for an item in the dataset.
   *
   * @param dataset  the dataset (<code>null</code> not permitted).
   * @param series  the series (zero-based index).
   * @param item  the item (zero-based index).
   *
   * @return The label (possibly <code>null</code>).
   */
    public String generateToolTip(XYDataset dataset, int series, int item) {
        return generateToolTip((XYZDataset) dataset, series, item);
    }
  }
}
