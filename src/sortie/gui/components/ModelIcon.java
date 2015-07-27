package sortie.gui.components;

import javax.swing.Icon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

/**
 * Creates different icons needed by the model.  Feel free to throw some more
 * in here as needed.
 * I originally created this class because Java doesn't seem to let me set
 * button colors anymore, so now I slap on a colored icon instead.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */

public class ModelIcon
    implements Icon {

  /**Icon is a rectangle*/
  public final static int RECTANGLE = 1;

  /**Icon is a triangle pointing up*/
  public final static int UP_TRIANGLE = 2;

  /**Icon is a triangle pointing down*/
  public final static int DOWN_TRIANGLE = 3;

  /**Icon is a triangle pointing left*/
  public final static int LEFT_TRIANGLE = 4;

  /**Icon is a triangle pointing right*/
  public final static int RIGHT_TRIANGLE = 5;

  /**Icon is a pause symbol - two rectangles next to each other*/
  public final static int PAUSE = 6;

  /**Icon is a step forward symbol - two triangles next to each other pointing
   * forward*/
  public final static int STEP_FORWARD = 7;

  /**Icon is a step back symbol - two triangles next to each other pointing
   * backwards*/
  public final static int STEP_BACKWARD = 8;

  /**Icon's color.  Defaults to black.*/
  private Color m_oColor;

  /**Icon's width, in pixels.*/
  private int m_iWidth;

  /**Icon's height, in pixels.*/
  private int m_iHeight;

  /**Icon's shape.*/
  private int m_iShape;

  /**
   * Creates an icon.
   * @param iWidth int Width of the icon, in pixels.
   * @param iHeight int Height of the icon, in pixels.
   * @param iShape int Desired icon shape - RECTANGLE, TRIANGLE, etc.  If the
   * value is not recognized a rectangle is drawn.
   */
  public ModelIcon(int iWidth, int iHeight, int iShape) {
    this(iWidth, iHeight, iShape, java.awt.Color.BLACK);
  }

  /**
   * Creates an icon.
   * @param iWidth int Width of the icon, in pixels.
   * @param iHeight int Height of the icon, in pixels.
   * @param iShape int Desired icon shape - RECTANGLE, TRIANGLE, etc.  If the
   * value is not recognized a rectangle is drawn.
   * @param oColor Color of icon.
   */
  public ModelIcon(int iWidth, int iHeight, int iShape, Color oColor) {
    m_iWidth = iWidth;
    m_iHeight = iHeight;
    m_oColor = oColor;
    if (iShape < RECTANGLE || iShape > STEP_BACKWARD) {
      m_iShape = RECTANGLE;
    }
    else {
      m_iShape = iShape;
    }
  }

  /**
   * Gets the height of the icon, in pixels.
   * @return int Height of the icon in pixels.
   */
  public int getIconHeight() {
    return m_iHeight;
  }

  /**
   * Gets the width of the icon, in pixels.
   * @return int Width of the icon in pixels.
   */
  public int getIconWidth() {
    return m_iWidth;
  }

  /**
   * Create the icon.
   * @param c Component Component.
   * @param g Graphics Graphics.
   * @param x int X coordinte to paint at.
   * @param y int Y coordinate to paint at.
   */
  public void paintIcon(Component c, Graphics g, int x, int y) {
    if (m_iShape == RECTANGLE) {
      paintRectangle(g, x, y);
    }
    else if (m_iShape == UP_TRIANGLE) {
      paintUpTriangle(g, x, y);
    }
    else if (m_iShape == DOWN_TRIANGLE) {
      paintDownTriangle(g, x, y);
    }
    else if (m_iShape == LEFT_TRIANGLE) {
      paintLeftTriangle(g, x, y);
    }
    else if (m_iShape == RIGHT_TRIANGLE) {
      paintRightTriangle(g, x, y);
    }
    else if (m_iShape == PAUSE) {
      paintPause(g, x, y);
    }
    else if (m_iShape == STEP_FORWARD) {
      paintStepForward(g, x, y);
    }
    else if (m_iShape == STEP_BACKWARD) {
      paintStepBackward(g, x, y);
    }
  }

  /**
   * Paints a rectangle.
   * @param g Graphics Graphics object for painting.
   * @param x int X coordinate to start painting at.
   * @param y int Y coordinate to start painting at.
   */
  private void paintRectangle(Graphics g, int x, int y) {
    Graphics jCopy = g.create();
    jCopy.setColor(m_oColor);
    jCopy.fillRect(x, y, m_iWidth, m_iHeight);
    jCopy.dispose();
  }

  /**
   * Creates a triangle with the point up.
   * @param g Graphics Graphics object.
   * @param x int X coordinate of the left corner of the triangle.
   * @param y int Y coordinate of the top corner of the triangle.
   */
  private void paintUpTriangle(Graphics g, int x, int y) {

    Graphics jCopy = g.create();
    jCopy.setColor(m_oColor);
    int[] p_iXPoints = new int[3]; //x coordinates of triangle vertexes
    int[] p_iYPoints = new int[3]; //y coordinates of triangle vertexes
    int iNumPoints = 3;

    //Up point
    p_iXPoints[0] = x + (int) (m_iWidth / 2);
    p_iYPoints[0] = y;

    //Left point
    p_iXPoints[1] = x;
    p_iYPoints[1] = y + m_iHeight;

    //Right point
    p_iXPoints[2] = x + m_iWidth;
    p_iYPoints[2] = y + m_iHeight;

    jCopy.fillPolygon(p_iXPoints, p_iYPoints, iNumPoints);
    jCopy.dispose();
  }

  /**
   * Creates a triangle with the point down.
   * @param g Graphics Graphics object.
   * @param x int X coordinate of the upper left corner of the triangle.
   * @param y int Y coordinate of the upper left corner of the triangle.
   */
  private void paintDownTriangle(Graphics g, int x, int y) {

    Graphics jCopy = g.create();
    jCopy.setColor(m_oColor);
    int[] p_iXPoints = new int[3]; //x coordinates of triangle vertexes
    int[] p_iYPoints = new int[3]; //y coordinates of triangle vertexes
    int iNumPoints = 3;

    p_iXPoints[0] = x;
    p_iYPoints[0] = y;

    p_iXPoints[1] = x + m_iWidth;
    p_iYPoints[1] = y;

    p_iXPoints[2] = x + (int) (m_iWidth / 2);
    p_iYPoints[2] = y + m_iHeight;

    jCopy.fillPolygon(p_iXPoints, p_iYPoints, iNumPoints);
    jCopy.dispose();
  }

  /**
   * Creates a triangle with the point to the left.
   * @param g Graphics Graphics object.
   * @param x int X coordinate of the triangle.
   * @param y int Y coordinate of the triangle.
   */
  private void paintLeftTriangle(Graphics g, int x, int y) {

    Graphics jCopy = g.create();
    jCopy.setColor(m_oColor);
    int[] p_iXPoints = new int[3]; //x coordinates of triangle vertexes
    int[] p_iYPoints = new int[3]; //y coordinates of triangle vertexes
    int iNumPoints = 3;

    p_iXPoints[0] = x + m_iWidth;
    p_iYPoints[0] = y;

    p_iXPoints[1] = x + m_iWidth;
    p_iYPoints[1] = y + m_iHeight;

    p_iXPoints[2] = x;
    p_iYPoints[2] = y + (int) (m_iHeight / 2);

    jCopy.fillPolygon(p_iXPoints, p_iYPoints, iNumPoints);
    jCopy.dispose();
  }

  /**
   * Creates a triangle with the point to the right.
   * @param g Graphics Graphics object.
   * @param x int X coordinate of the triangle.
   * @param y int Y coordinate of the triangle.
   */
  private void paintRightTriangle(Graphics g, int x, int y) {

    Graphics jCopy = g.create();
    jCopy.setColor(m_oColor);
    int[] p_iXPoints = new int[3]; //x coordinates of triangle vertexes
    int[] p_iYPoints = new int[3]; //y coordinates of triangle vertexes
    int iNumPoints = 3;

    //The x and y are the triangle's upper right corner
    p_iXPoints[0] = x;
    p_iYPoints[0] = y;

    p_iXPoints[1] = x;
    p_iYPoints[1] = y + m_iHeight;

    p_iXPoints[2] = x + m_iWidth;
    p_iYPoints[2] = y + (int) (m_iHeight / 2);

    jCopy.fillPolygon(p_iXPoints, p_iYPoints, iNumPoints);
    jCopy.dispose();
  }

  /**
   * Create a "pause" icon with two rectangles.
   * @param g Graphics Graphics object.
   * @param x int X coordinate of pause icon.
   * @param y int Y coordinate of pause icon.
   */
  private void paintPause(Graphics g, int x, int y) {
    Graphics jCopy = g.create();
    jCopy.setColor(m_oColor);

    //Divide the width up.  The rectangles should each be 40% of the width,
    //with the gap the remaining 20%
    int iGapWidth = (int) m_iWidth / 5, iRectWidth = iGapWidth * 2;
    //Draw first rectangle
    jCopy.fillRect(x, y, iRectWidth, m_iHeight);
    //Draw second rectangle
    jCopy.fillRect(x + iGapWidth + iRectWidth, y, iRectWidth, m_iHeight);

    jCopy.dispose();
  }

  /**
   * Creates a "step forward" icon with two triangles pointing right.
   * @param g Graphics Graphics object.
   * @param x int X coordinate of the icon.
   * @param y int Y coordinate of the icon.
   */
  private void paintStepForward(Graphics g, int x, int y) {
    //Paint two right-facing triangles each 1/2 of the width
    int iWidth = m_iWidth;
    m_iWidth = m_iWidth/2;
    paintRightTriangle(g, x, y);
    paintRightTriangle(g, x + m_iWidth, y);
    m_iWidth = iWidth;
  }

  /**
   * Creates a "step backward" icon with two triangles pointing left.
   * @param g Graphics Graphics object.
   * @param x int X coordinate of icon.
   * @param y int Y coordinate of icon.
   */
  private void paintStepBackward(Graphics g, int x, int y) {
    //Paint two left-facing triangles each 1/2 of the width
    int iWidth = m_iWidth;
    m_iWidth = m_iWidth/2;
    paintLeftTriangle(g, x, y);
    paintLeftTriangle(g, x + m_iWidth, y);
    m_iWidth = iWidth;
  }

}
