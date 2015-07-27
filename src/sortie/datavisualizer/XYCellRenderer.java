package sortie.datavisualizer;

import org.jfree.chart.renderer.xy.*;
import java.awt.*;
import java.awt.geom.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.axis.*;
import org.jfree.data.xy.*;
import org.jfree.ui.RectangleEdge;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.XYZToolTipGenerator;

import sortie.gui.ErrorGUI;

/**
 * This class fills in map cell rectangles on a plot.  The rectangle size can
 * be set; the size defaults to the same as the underlying grid size.
 *
 * The X and Y are expected to be integers which equal cell numbers in the X
 * and Y directions.  The Z is expected to be a number which
 * controls the color intensity of the cell.  The cells are filled in a
 * specified color at the specified intensity.  For a range of values, the
 * grayscale color gradient looks nice.  I would intensively test any other
 * color.
 *
 * The color gradient can be controlled by setting the minimum, maximum, and
 * knee RGB values.  Below the minimum value, all cells are minimum color
 * (defaults to black).  Above the maximum value, all cells are maximum color
 * (defaults to white).  The knee value represents the mid-point knee color
 * (defaults to 50% gray).  This lets you tweak the display if your dataset
 * is not evenly distributed.
 *
 * Copyright: Copyright (c) Charles D. Canham 2012
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class XYCellRenderer
    extends AbstractXYItemRenderer
    implements XYItemRenderer {
  
  

  /**Length of each cell in the X direction, in meters*/
  private float m_fXCellSize = 8;
  /**Length of each cell in the Y direction, in meters*/
  private float m_fYCellSize = 8;
  /**The value of a cell below which all cells are minimum color -
   * defaults to 0.  Applies to all series.*/
  private float m_fMinimumValue = 0;
  /**The value of a cell above which all cells are maximum color -
   * defaults to 100.  Applies to all series.*/
  private float m_fMaximumValue = 100;
  /**The value of a cell at the knee color - defaults to 50.  Applies to all
   * series.*/
  private float m_fKneeValue = 50;

  /**Minimum color - red value from 0 to 255*/
//  private int m_iMinRed = 0;
  /**Minimum color - green value from 0 to 255*/
//  private int m_iMinGreen = 0;
  /**Minimum color - blue value from 0 to 255*/
//  private int m_iMinBlue = 0;
  /**Maximum color - red value from 0 to 255*/
//  private int m_iMaxRed = 255;
  /**Maximum color - green value from 0 to 255*/
//  private int m_iMaxGreen = 255;
  /**Maximum color - blue value from 0 to 255*/
//  private int m_iMaxBlue = 255;
  /**The knee color - red value from 0 to 255*/
//  private int m_iKneeRed = 128;
  /**The knee color - blue value from 0 to 255*/
//  private int m_iKneeBlue = 128;
  /**The knee color - green value from 0 to 255*/
//  private int m_iKneeGreen = 128;

  /** The knee paint list*/
  private org.jfree.util.PaintList m_jKneePaintList = new org.jfree.util.PaintList();

  /**The minimum color paint list*/
  private org.jfree.util.PaintList m_jMinPaintList = new org.jfree.util.PaintList();

  /**Whether to draw the shapes with a texture (true), or in solid color
   * (false).  The color used is the same either way.*/
  //private boolean m_bTexture = false;

  /**
   * Constructor.  Sets defaults.
   */
  public XYCellRenderer() {
    super();
    setBaseToolTipGenerator(new CellTooltipGenerator());
    try {
      setMaximumColor(255, 255, 255, 0);
      setMinimumColor(0, 0, 0, 0);
      setKneeColor(128, 128, 128, 0);
      //I know I'm doing it right, so I'm not going to throw the exception
      //from here.  This gets JBuilder off my back.
    } catch (sortie.data.simpletypes.ModelException oErr) {;}
  }

  /**
   * Draws a single grid cell.  If there is no Z value, nothing is drawn.
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

    DefaultXYZDataset oCells = (DefaultXYZDataset) dataset;

    //Get this cell's X and Y coordinates
    int iX = (int)oCells.getXValue(series, item);
    int iY = (int)oCells.getYValue(series, item);
    float fZ = 0;

    //Get this cell's Z coordinate, if present; if null, nothing will be
    //drawn
    if (dataset instanceof XYZDataset) {
      XYZDataset oXYZData = (XYZDataset) dataset;
      fZ = (float)oXYZData.getZValue(series, item);
    } else return;

    //Find the coordinates of the rectangle that defines this grid cell -
    //because of the way Java grid display coordinates work, this should be the
    //upper left corner (NE)
    float fOriginX = (float) iX * m_fXCellSize;
    float fOriginY = (float) (iY + 1) * m_fYCellSize;

    //Translate them to the Java2D coordinate system
    RectangleEdge oXEdge = plot.getDomainAxisEdge();
    RectangleEdge oYEdge = plot.getRangeAxisEdge();
    double fTranslatedX = domainAxis.valueToJava2D(fOriginX,
        dataArea, oXEdge);
    double fTranslatedY = rangeAxis.valueToJava2D(fOriginY, dataArea,
        oYEdge);

    //Find the lower right corner from which to calculate width and height
    double fTranslatedXEnd = domainAxis.valueToJava2D(fOriginX +
        m_fXCellSize, dataArea, oXEdge);
    double fTranslatedYEnd = rangeAxis.valueToJava2D(fOriginY -
        m_fYCellSize, dataArea, oYEdge);

    //Find width and height of the rectangle to render
    double fWidth = java.lang.Math.abs(fTranslatedXEnd - fTranslatedX);
    double fHeight = java.lang.Math.abs(fTranslatedYEnd - fTranslatedY);

    //Define the rectangle that is the cell
    Rectangle2D jPlotCell = new Rectangle2D.Double(fTranslatedX, fTranslatedY,
        fWidth, fHeight);

    // Add entity info - this is what allows the tooltip display to work
    EntityCollection oEntities = null;
    if (info != null) {
      oEntities = info.getOwner().getEntityCollection();
      if (oEntities != null) {
        addEntity(oEntities, jPlotCell, dataset, series, item, fTranslatedX,
                    fTranslatedY);
      }
    }

    //Get the color for this box
    Paint jSeriesPaint = null;
    if (fZ <= m_fMinimumValue) {
/*      iRed = m_iMinRed;
      iGreen = m_iMinGreen;
      iBlue = m_iMinBlue; */
      jSeriesPaint = getSeriesMinPaint(series);
    }
    else if (fZ <= m_fKneeValue) {
      int iRed = 0, iGreen = 0, iBlue = 0;
      Color jKneeColor = (Color) getSeriesKneePaint(series);
      Color jMinColor = (Color) getSeriesMinPaint(series);
      iRed = (int) (((fZ - m_fMinimumValue) * (jKneeColor.getRed() - jMinColor.getRed())) /
                    (m_fKneeValue - m_fMinimumValue) + jMinColor.getRed());
      iGreen = (int) (((fZ - m_fMinimumValue) * (jKneeColor.getGreen() - jMinColor.getGreen())) /
                      (m_fKneeValue - m_fMinimumValue) + jMinColor.getGreen());
      iBlue = (int) (((fZ - m_fMinimumValue) * (jKneeColor.getBlue() - jMinColor.getBlue())) /
                     (m_fKneeValue - m_fMinimumValue) + jMinColor.getBlue());
      jSeriesPaint = new Color(iRed, iGreen, iBlue);

/*      iRed = (int) (fZ * (m_iKneeRed - m_iMinRed) /
                    (m_fKneeValue - m_fMinimumValue) + m_iMinRed);
      iGreen = (int) (fZ * (m_iKneeGreen - m_iMinGreen) /
                      (m_fKneeValue - m_fMinimumValue) + m_iMinGreen);
      iBlue = (int) (fZ * (m_iKneeBlue - m_iMinBlue) /
                     (m_fKneeValue - m_fMinimumValue) + m_iMinBlue); */
    }
    else if (fZ <= m_fMaximumValue) {
/*      iRed = (int) ( (fZ - m_fKneeValue) * (m_iMaxRed - m_iKneeRed) /
                    (m_fMaximumValue - m_fKneeValue) + m_iKneeRed);
      iGreen = (int) ( (fZ - m_fKneeValue) * (m_iMaxGreen - m_iKneeGreen) /
                      (m_fMaximumValue - m_fKneeValue) + m_iKneeGreen);
      iBlue = (int) ( (fZ - m_fKneeValue) * (m_iMaxBlue - m_iKneeBlue) /
                     (m_fMaximumValue - m_fKneeValue) + m_iKneeBlue); */

      Color jKneeColor = (Color) getSeriesKneePaint(series);
      Color jMaxColor = (Color) getSeriesPaint(series);
      int iRed = 0, iGreen = 0, iBlue = 0;
      iRed = (int) ( (fZ - m_fKneeValue) * (jMaxColor.getRed() - jKneeColor.getRed()) /
                    (m_fMaximumValue - m_fKneeValue) + jKneeColor.getRed());
      iGreen = (int) ( (fZ - m_fKneeValue) * (jMaxColor.getGreen() - jKneeColor.getGreen()) /
                      (m_fMaximumValue - m_fKneeValue) + jKneeColor.getGreen());
      iBlue = (int) ( (fZ - m_fKneeValue) * (jMaxColor.getBlue() - jKneeColor.getBlue()) /
                     (m_fMaximumValue - m_fKneeValue) + jKneeColor.getBlue());
      jSeriesPaint = new Color(iRed, iGreen, iBlue);

    }
    else {
      jSeriesPaint = getSeriesPaint(series);
/*      iRed = m_iMaxRed;
      iGreen = m_iMaxGreen;
      iBlue = m_iMaxBlue;
 */
    }
//    Color jSeriesPaint = new Color(iRed, iGreen, iBlue);

    //Paint it
    /*    if (m_bTexture) {
         java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(5, 5,
                                    java.awt.image.BufferedImage.TYPE_INT_RGB);
             Graphics2D big = bi.createGraphics();
             big.setColor(Color.blue);
             big.fillRect(0, 0, 5, 5);
             big.setColor(Color.lightGray);
             big.fillOval(0, 0, 5, 5);
             Rectangle r = new Rectangle(0,0,5,5);
             jGraphics2.setPaint(new TexturePaint(bi, r));
        } else { */
    g2.setPaint(jSeriesPaint);
//    }
    g2.fill(jPlotCell);
    //Now draw the outline.  Do this last, or it doesn't show up as well.
    g2.setColor(java.awt.Color.black);
    g2.draw(jPlotCell);
  }

  /**
   * Sets the value of a cell above which all cells will be painted the
   * maximum color.
   * @param fValue Maximum value.
   */
  public void setMaximumValue(float fValue) {
    m_fMaximumValue = fValue;
  }

  /**
   * Sets the value of a cell below which all cells will be painted the
   * minimum color.
   * @param fValue Minimum value.
   */
  public void setMinimumValue(float fValue) {
    m_fMinimumValue = fValue;
  }

  /**
   * Sets the knee value which corresponds to the knee color.
   * @param fValue Knee value.
   */
  public void setKneeValue(float fValue) {
    m_fKneeValue = fValue;
  }

  /**
   * Sets the maximum color.
   * @param iRed The RGB red value, between 0 and 255.
   * @param iGreen The RGB green value, between 0 and 255.
   * @param iBlue The RGB blue value, between 0 and 255.
   * @param iSeries The series to set the maximum color for.
   * @throws sortie.data.simpletypes.ModelException if the rgb values are invalid.
   */
  public void setMaximumColor(int iRed, int iGreen, int iBlue, int iSeries) throws
      sortie.data.simpletypes.ModelException {
    if (iRed < 0 || iRed > 255 ||
        iGreen < 0 || iGreen > 255 ||
        iBlue < 0 || iBlue > 255) {
      throw(new sortie.data.simpletypes.ModelException(ErrorGUI.BAD_ARGUMENT,
                                          "JAVA",
                "Bad RGB value passed to XYCellRenderer - SetMaximumColor"));
    }

    setSeriesPaint(iSeries, new Color(iRed, iGreen, iBlue));
/*    m_iMaxRed = iRed;
    m_iMaxGreen = iGreen;
    m_iMaxBlue = iBlue; */
  }

  /**
   * Sets the minimum color.
   * @param iRed The RGB red value, between 0 and 255.
   * @param iGreen The RGB green value, between 0 and 255.
   * @param iBlue The RGB blue value, between 0 and 255.
   * @param iSeries The series to set the minimum color for.
   * @throws sortie.data.simpletypes.ModelException if the rgb values are invalid.
   */
  public void setMinimumColor(int iRed, int iGreen, int iBlue, int iSeries) throws
      sortie.data.simpletypes.ModelException {
    if (iRed < 0 || iRed > 255 ||
        iGreen < 0 || iGreen > 255 ||
        iBlue < 0 || iBlue > 255) {
      throw(new sortie.data.simpletypes.ModelException(ErrorGUI.BAD_ARGUMENT,
          "JAVA", "Bad RGB value passed to XYCellRenderer - SetMaximumColor"));
    }

    setSeriesMinPaint(iSeries, new Color(iRed, iGreen, iBlue));
/*    m_iMinRed = iRed;
    m_iMinGreen = iGreen;
    m_iMinBlue = iBlue; */
  }

  /**
   * Sets the knee color.
   * @param iRed The RGB red value, between 0 and 255.
   * @param iGreen The RGB green value, between 0 and 255.
   * @param iBlue The RGB blue value, between 0 and 255.
   * @param iSeries The series to set the minimum color for.
   * @throws sortie.data.simpletypes.ModelException if the rgb values are invalid.
   */
  public void setKneeColor(int iRed, int iGreen, int iBlue, int iSeries) throws sortie.data.simpletypes.ModelException {
    if (iRed < 0 || iRed > 255 ||
        iGreen < 0 || iGreen > 255 ||
        iBlue < 0 || iBlue > 255) {
      throw(new sortie.data.simpletypes.ModelException(ErrorGUI.BAD_ARGUMENT,
            "JAVA", "Bad RGB value passed to XYCellRenderer - SetMaximumColor"));
    }

    setSeriesKneePaint(iSeries, new Color(iRed, iGreen, iBlue));
/*    m_iKneeRed = iRed;
    m_iKneeGreen = iGreen;
    m_iKneeBlue = iBlue; */
  }

  /**
   * Sets the knee color for a series.
   * @param jColor Color to set.
   * @param iSeries Series for which to set the color.
   */
  public void setKneeColor(Color jColor, int iSeries) {
    setSeriesKneePaint(iSeries, jColor);
  }

  /**
   * Sets the max color for a series.
   * @param jColor Color to set.
   * @param iSeries Series for which to set the color.
   */
  public void setMaximumColor(Color jColor, int iSeries) {
    setSeriesPaint(iSeries, jColor);
  }

  /**
   * Sets the minimum color for a series.
   * @param jColor Color to set.
   * @param iSeries Series for which to set the color.
   */
  public void setMinimumColor(Color jColor, int iSeries) {
    setSeriesMinPaint(iSeries, jColor);
  }

  /**
   * Sets the X cell length.
   * @param fLength X cell length, in m.
   */
  public void setXCellLength(float fLength) {
    m_fXCellSize = fLength;
  }

  /**
   * Sets the Y cell length.
   * @param fLength Y cell length, in m.
   */
  public void setYCellLength(float fLength) {
    m_fYCellSize = fLength;
  }

  /**
   * Gets the minimum value below which the minimum color is always used.
   * @return The minimum value.
   */
  public float getMinimumValue() {
    return m_fMinimumValue;
  }

  /**
   * Gets the maximum value above which the maximum color is always used.
   * @return The maximum value.
   */
  public float getMaximumValue() {
    return m_fMaximumValue;
  }

  /**
   * Gets the knee value at which the knee color is always used.
   * @return The knee value.
   */
  public float getKneeValue() {
    return m_fKneeValue;
  }

  /**
   * Gets the minimum color for a series.
   * @param iSeries The series to get the color for.
   * @return The minimum color.
   */
    public Color getMinimumColor(int iSeries) {
      return (Color) getSeriesMinPaint(iSeries);
    }


  /**
   * Gets the maximum color for a series.
   * @param iSeries The series to get the color for.
   * @return The maximum color.
   */
    public Color getMaximumColor(int iSeries) {
      return (Color) getSeriesPaint(iSeries);
    }

  /**
   * Gets the knee color for a series.
   * @param iSeries The series to get the color for.
   * @return The knee color.
   */
    public Color getKneeColor(int iSeries) {
      return (Color) getSeriesKneePaint(iSeries);
    }


  /**
   * Returns the knee color for a series.  If the series has not been
   * explicitly set, it returns the default.
   *
   * @param iSeries  the series index (zero-based).
   *
   * @return  The paint.
   */
  public Paint getSeriesKneePaint(int iSeries) {

    // Look up the paint list
    Paint jPaint = (Paint)this.m_jKneePaintList.getPaint(iSeries);
    if (jPaint == null) {
      return this.m_jKneePaintList.getPaint(0);
    }
    return jPaint;
  }

  /**
   * Returns the knee color for a series.  If the series has not been
   * explicitly set, it returns the default.
   *
   * @param iSeries  the series index (zero-based).
   *
   * @return  The paint.
   */
  public Paint getSeriesMinPaint(int iSeries) {

    // Look up the paint list
    Paint jPaint = (Paint)this.m_jMinPaintList.getPaint(iSeries);
    if (jPaint == null) {
      return this.m_jMinPaintList.getPaint(0);
    }
    return jPaint;
  }

  /**
   * Sets the minimum color for a series.
   * @param iSeries Series index (zero-based).
   * @param jColor The color to set.
   */
  protected void setSeriesMinPaint(int iSeries, Color jColor) {
    m_jMinPaintList.setPaint(iSeries, jColor);
  }

  /**
   * Sets the knee color for a series.
   * @param iSeries Series index (zero-based).
   * @param jColor The color to set.
   */
  protected void setSeriesKneePaint(int iSeries, Color jColor) {
    m_jKneePaintList.setPaint(iSeries, jColor);
  }

  /**
   * Provides a more informative tooltip string for cell values.
   * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
   * <p>Company: Cary Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   */
  private class CellTooltipGenerator implements XYZToolTipGenerator {

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
        result = "Cell (" + dataset.getX(series, item).intValue() + ", " +
                 dataset.getY(series, item).intValue() + "): " +
                 dataset.getZ(series, item);
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
