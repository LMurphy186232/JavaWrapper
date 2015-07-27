package sortie.datavisualizer;

import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.AbstractXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRendererState;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleEdge;

import sortie.data.simpletypes.ModelException;

import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

/**
 * This class fills in map cell rectangles on a plot.  The rectangle size can
 * be set; the size defaults to the same as the underlying grid size.
 *
 * The X and Y are expected to be integers which equal cell numbers in the X
 * and Y directions.  The Z is expected to be a number which controls the
 * presence or absence of fill in a cell. true means fill; false means
 * don't fill. Each filled cell is filled in the same color. A non-filled cell
 * is not filled in white; it is not filled at all, which will allow anything
 * underneath to show through.
 *
 * The color can be controlled by setting the RGB value.
 *
 * Originally this task was performed by XYCellRenderer. But then JFreeChart
 * upgraded and took away my only way to find out if a cell was not to be
 * filled, so I made this class for subplots, and simplified it for the job
 * while I was at it.
 *
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 21, 2006:  Created (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 */

public class XYSimpleCellRenderer extends AbstractXYItemRenderer
    implements XYItemRenderer {
  
  

  /**Length of each cell in the X direction, in meters*/
  private float m_fXCellSize = 8;

  /**Length of each cell in the Y direction, in meters*/
  private float m_fYCellSize = 8;

  /** The paint list*/
  private org.jfree.util.PaintList m_jPaintList = new org.jfree.util.PaintList();

  /**
   * Constructor.  Sets defaults.
   */
  public XYSimpleCellRenderer() {
    super();
    try {
      setColor(255, 255, 255, 0);
      //I know I'm doing it right, so I'm not going to throw the exception
      //from here.  This gets JBuilder off my back.
    }
    catch (sortie.data.simpletypes.ModelException oErr) {
      ;
    }
  }

  /**
   * Draws a single grid cell.  If the Z value is not true, nothing is drawn.
   * @param g2 - the graphics device.
   * @param state - the renderer state.
   * @param dataArea - the area within which the data is being rendered.
   * @param info - collects drawing info.
   * @param plot - the plot.
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

    if (!(dataset instanceof XYZSimpleDataset)) return;
    XYZSimpleDataset oCells = (XYZSimpleDataset) dataset;

    //Get this cell's X and Y coordinates
    int iX = (int) oCells.getXValue(series, item);
    int iY = (int) oCells.getYValue(series, item);

    if (false == oCells.mp_bData[series][iX][iY]) return;

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

    //Get the color for this box
    Paint jSeriesPaint = null;
    jSeriesPaint = getSeriesPaint(series);
    g2.setPaint(jSeriesPaint);
    g2.fill(jPlotCell);
    //Now draw the outline.  Do this last, or it doesn't show up as well.
    g2.setColor(java.awt.Color.black);
    g2.draw(jPlotCell);
  }

  /**
   * Sets the fill color for a series.
   * @param iRed The RGB red value, between 0 and 255.
   * @param iGreen The RGB green value, between 0 and 255.
   * @param iBlue The RGB blue value, between 0 and 255.
   * @param iSeries The series to set the color for.
   * @throws sortie.data.simpletypes.ModelException if the rgb values are invalid.
   */
  public void setColor(int iRed, int iGreen, int iBlue, int iSeries) throws
      ModelException {
    if (iRed < 0 || iRed > 255 ||
        iGreen < 0 || iGreen > 255 ||
        iBlue < 0 || iBlue > 255) {
      throw(new sortie.data.simpletypes.ModelException(sortie.gui.ErrorGUI.BAD_ARGUMENT,
           "JAVA", "Bad RGB value passed to XYCellRenderer - SetMaximumColor"));
    }

    setSeriesPaint(iSeries, new Color(iRed, iGreen, iBlue));
  }

  /**
   * Sets the fill color for a series.
   * @param jColor Color to set.
   * @param iSeries Series for which to set the color.
   */
  public void setColor(Color jColor, int iSeries) {
    setSeriesPaint(iSeries, jColor);
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
   * Gets the color for a series.
   * @param iSeries The series to get the color for.
   * @return The color.
   */
  public Color getColor(int iSeries) {
    return (Color) getSeriesPaint(iSeries);
  }

  /**
   * Returns the color for a series.  If the series has not been
   * explicitly set, it returns the default.
   *
   * @param iSeries  the series index (zero-based).
   *
   * @return  The paint.
   */
  public Paint GetSeriesPaint(int iSeries) {

    // Look up the paint list
    Paint jPaint = (Paint)this.m_jPaintList.getPaint(iSeries);
    if (jPaint == null) {
      return this.m_jPaintList.getPaint(0);
    }
    return jPaint;
  }
}
