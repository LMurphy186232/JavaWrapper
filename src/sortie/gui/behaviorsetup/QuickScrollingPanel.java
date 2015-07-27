package sortie.gui.behaviorsetup;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

/**
 * This class creates a JPanel that scrolls at a normal speed through a
 * JScrollPane.  The default scrolling increment for a JPanel is 1 pixel at a
 * time, which is wicked slow.  This boosts that number to make scrolling
 * faster.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class QuickScrollingPanel
extends JPanel
implements Scrollable {

  /**
   * Get preferred size - this is nothing special
   * @return Dimension Preferred size
   */
  public Dimension getPreferredScrollableViewportSize() {
    return getPreferredSize();
  }

  /**
   * Scrolls 75% of the visible pane when paging up or down.
   * @param r Rectangle Ignored
   * @param orientation int Ignored
   * @param direction int Ignored
   * @return int 75% of the viewable height, in pixels
   */
  public int getScrollableBlockIncrement(Rectangle r,
      int orientation, int direction) {
    return (int)(getVisibleRect().getHeight() * 0.75);
  }

  /**
   * Always false.
   * @return boolean Always false.
   */
  public boolean getScrollableTracksViewportHeight() {
    return false;
  }

  /**
   * Always false.
   * @return boolean Always false.
   */
  public boolean getScrollableTracksViewportWidth() {
    return false;
  }

  /**
   * Causes scrolling to go 20 pixels at a time when scrolling continuously.
   * @param r Rectangle Ignored
   * @param orientation int Ignored
   * @param direction int Ignored
   * @return int 20 pixels
   */
  public int getScrollableUnitIncrement(java.awt.Rectangle r,
      int orientation, int direction) {
    return 20;
  }
}
