package sortie.gui.harvepplant;

import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;

/**
   * Class for interpreting mouse clicks on the chart for selecting cells for
   * episodic event editing.  This will detect both single clicks on the chart
   * and click-and-drag.  When a user has selected a cell or group of cells on
   * this chart, this will toggle them as selected/unselected with the parent
   * window.
   * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
   * <p>Company: Cary Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   */
public class EpisodicEventsMouseListener
      implements org.jfree.chart.ChartMouseListener,
      java.awt.event.MouseListener, java.awt.event.MouseMotionListener {

    /**Pointer to the object to exchange data with*/
    private EditWindowBase m_oParentWindow;

     /** The selection rectangle starting point (selected by the user with a
      * mouse click)
     */
    private java.awt.geom.Point2D m_jDragPoint = null;

    /** The selection rectangle (selected by the user with the mouse). */
    private java.awt.geom.Rectangle2D m_jDragRectangle = null;

    /**
     * Constructor.
     * @param oWindow Object to exchange data with.
     */
    EpisodicEventsMouseListener(EditWindowBase oWindow) {
      m_oParentWindow = oWindow;
    }

    /**
     * Returns a point based on (x, y) but constrained to be within the bounds
     * of the given rectangle.  This method could be moved to JCommon.
     *
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param area  the rectangle (<code>null</code> not permitted).
     *
     * @return A point within the rectangle.
     */
    private java.awt.Point getPointInRectangle(int x, int y, java.awt.geom.Rectangle2D area) {
        x = (int) Math.max(Math.ceil(area.getMinX()), Math.min(x,
                Math.floor(area.getMaxX())));
        y = (int) Math.max(Math.ceil(area.getMinY()), Math.min(y,
                Math.floor(area.getMaxY())));
        return new java.awt.Point(x, y);
    }

    /**
     * Interprets a mouse click on the chart.  This takes the point of the
     * click and translates the click point to chart coordinates.  The cell
     * containing the click point is toggled between a value of 0 and 1 in
     * HarvestEdit::mp_iCells.
     * @param oEvent MouseEvent from which to get the click point.
     */
    public void chartMouseClicked(org.jfree.chart.ChartMouseEvent oEvent) {
      try {
        java.awt.Point jPoint = oEvent.getTrigger().getPoint();
        setCellValues(jPoint.x, jPoint.y);
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(m_oParentWindow);
        oHandler.writeErrorMessage(oErr);
      }
    }

    /**
     * Captures the point of click for possible dragging.  I modified this code
     * from org.jfree.chart.ChartPanel::mousePressed by David Gilbert.
     * @param oEvent MouseEvent from which to get the click point.
     */
    public void mousePressed(java.awt.event.MouseEvent oEvent) {

      m_jDragPoint = getPointInRectangle(oEvent.getX(), oEvent.getY(),
          m_oParentWindow.m_oChart.getScreenDataArea());
    }

    /**
     * Handles a 'mouse dragged' event.  Draws a rectangle of the drag.  I
     * modified this code from org.jfree.chart.ChartPanel::mouseDragged by
     * David Gilbert.
     * @param oEvent  the mouse event.
     */
    public void mouseDragged(java.awt.event.MouseEvent oEvent) {

      //Get the graphics object from the CHART, not the WINDOW; otherwise the
      //rectangle coordinates won't be right.
      java.awt.Graphics2D g2 = (java.awt.Graphics2D) m_oParentWindow.m_oChart.getGraphics();

      // use XOR to erase the previous selection rectangle (if any)...
      g2.setXORMode(java.awt.Color.gray);
      if (m_jDragRectangle != null) {
        g2.draw(m_jDragRectangle);
      }

      java.awt.geom.Rectangle2D scaledDataArea = m_oParentWindow.m_oChart.getScreenDataArea();

      // selected rectangle shouldn't extend outside the data area...
      double fXMax = Math.min(oEvent.getX(), scaledDataArea.getMaxX());
      double fYMax = Math.min(oEvent.getY(), scaledDataArea.getMaxY());
      m_jDragRectangle = new java.awt.geom.Rectangle2D.Double(m_jDragPoint.getX(),
          m_jDragPoint.getY(),
          fXMax - m_jDragPoint.getX(),
          fYMax - m_jDragPoint.getY());

      if (m_jDragRectangle != null) {

        g2.draw(m_jDragRectangle);
      }
      g2.dispose();
    }

    /**
     * Handles a mouse button release event, presumably after a drag to select
     * cells.  This will take all the cells selected in the drag, and toggle
     * their value in mp_iCells between 0 and 1.  I modified the code for
     * retrieving drag coordinates from from
     * org.jfree.chart.ChartPanel::mouseReleased by David Gilbert.
     * @param oEvent Mouse event.
     */
    public void mouseReleased(java.awt.event.MouseEvent oEvent) {
      try {
        if (m_jDragRectangle != null) {

          double fWidth, fHeight;
          int iFromX, iFromY, iToX, iToY;
          java.awt.geom.Rectangle2D scaledDataArea = m_oParentWindow.m_oChart.getScreenDataArea();

          iFromX = (int) m_jDragPoint.getX();
          iFromY = (int) m_jDragPoint.getY();

          fWidth = Math.min(m_jDragRectangle.getWidth(),
                            scaledDataArea.getMaxX() - iFromX);
          fHeight = Math.min(m_jDragRectangle.getHeight(),
                             scaledDataArea.getMaxY() - iFromY);

          iToX = iFromX + (int) fWidth;
          iToY = iFromY + (int) fHeight;

          this.m_jDragPoint = null;
          this.m_jDragRectangle = null;

          setCellValues(iFromX, iFromY, iToX, iToY);
        }
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(m_oParentWindow);
        oHandler.writeErrorMessage(oErr);
      }
    }

    /**
     * Handles the selection of cells from a drag mouse event.  This takes the
     * points, translates them to plot coordinates, translates those to plot
     * cells, and then toggles the values in each cell from 0 to 1 or from 1
     * to 0 in HarvestEdit::mp_iCells.
     * @param iFromX X coordinate of the beginning point of drag, in Java2D
     * coordinates.
     * @param iFromY Y coordinate of the beginning point of drag, in Java2D
     * coordinates.
     * @param iToX X coordinate of the end point of drag, in Java2D
     * coordinates.
     * @param iToY Y coordinate of the end point of drag, in Java2D
     * coordinates.
     * @throws ModelException passing through from called methods.
     */
    private void setCellValues(int iFromX, int iFromY, int iToX, int iToY) throws
        ModelException {

      org.jfree.chart.plot.XYPlot oPlot = m_oParentWindow.m_oChart.getChart().getXYPlot();
      org.jfree.ui.RectangleEdge oXEdge = oPlot.getDomainAxisEdge();
      org.jfree.ui.RectangleEdge oYEdge = oPlot.getRangeAxisEdge();
      java.awt.geom.Rectangle2D jDataArea = m_oParentWindow.m_oChart.getScreenDataArea();
      double fPlotFromY = oPlot.getRangeAxis().java2DToValue( (double) iFromY, jDataArea, oYEdge);
      double fPlotFromX = oPlot.getDomainAxis().java2DToValue( (double) iFromX, jDataArea, oXEdge);
      double fPlotToY = oPlot.getRangeAxis().java2DToValue( (double) iToY, jDataArea, oYEdge);
      double fPlotToX = oPlot.getDomainAxis().java2DToValue( (double) iToX, jDataArea, oXEdge);

      //Translate the coordinates to cells
      int iFromXCell = (int) java.lang.Math.floor(fPlotFromX / m_oParentWindow.m_fLengthXCells);
      int iFromYCell = (int) java.lang.Math.floor(fPlotFromY / m_oParentWindow.m_fLengthYCells);
      int iToXCell = (int) java.lang.Math.floor(fPlotToX / m_oParentWindow.m_fLengthXCells);
      int iToYCell = (int) java.lang.Math.floor(fPlotToY / m_oParentWindow.m_fLengthYCells);

      int i, j;

      //Switch around the cells if we need to
      if (iFromXCell > iToXCell) {
        int iTemp = iFromXCell;
        iFromXCell = iToXCell;
        iToXCell = iTemp;
      }
      if (iFromYCell > iToYCell) {
        int iTemp = iFromYCell;
        iFromYCell = iToYCell;
        iToYCell = iTemp;
      }

      //Set the value in the data grid for this cell and refresh the grid
      if (iFromXCell >= 0 &&
          iFromXCell < m_oParentWindow.m_iNumXCells &&
          iFromYCell >= 0 &&
          iFromYCell < m_oParentWindow.m_iNumYCells &&
          iToXCell >= 0 && iToXCell < m_oParentWindow.m_iNumXCells &&
          iToYCell >= 0 && iToYCell < m_oParentWindow.m_iNumYCells) {

        for (i = iFromXCell; i <= iToXCell; i++) {
          for (j = iFromYCell; j <= iToYCell; j++) {
            //Toggle the value
            m_oParentWindow.m_oDataset.
            mp_bData[m_oParentWindow.m_iCurrentEventDataIndex][i][j] = ! 
            m_oParentWindow.m_oDataset.mp_bData[m_oParentWindow.m_iCurrentEventDataIndex][i][j];
          }
        }

        m_oParentWindow.refreshChart();
      }
    }

    /**
     * Handles the selection of a cell from a single mouse click.  This takes
     * the point, translates it to plot coordinates, translates it to plot
     * cells, and then toggles the value in the cell from 0 to 1 or from 1 to
     * 0 in HarvestEdit::mp_iCells.
     * @param iXClickPoint The X click point, in Java2D coordinates.
     * @param iYClickPoint The Y click point, in Java2D coordinates.
     * @throws ModelException Passed through from called methods.
     */
    private void setCellValues(int iXClickPoint, int iYClickPoint) throws
        ModelException {
      this.setCellValues(iXClickPoint, iYClickPoint, iXClickPoint, iYClickPoint);
    }

    /**
     * Does nothing.
     * @param oEvent Ignored.
     */
    public void mouseClicked(java.awt.event.MouseEvent oEvent) {
      ;
    }

    /**
     * Does nothing.
     * @param oEvent Ignored.
     */
    public void chartMouseMoved(org.jfree.chart.ChartMouseEvent oEvent) {
      ;
    }

    /**
     * Does nothing.
     * @param oEvent Ignored.
     */
    public void mouseEntered(java.awt.event.MouseEvent oEvent) {
      ;
    }

    /**
     * Does nothing.
     * @param oEvent Ignored.
     */
    public void mouseExited(java.awt.event.MouseEvent oEvent) {
      ;
    }

    /**
     * Does nothing.
     * @param oEvent Ignored.
     */
    public void mouseMoved(java.awt.event.MouseEvent oEvent) {
      ;
    }
  }
