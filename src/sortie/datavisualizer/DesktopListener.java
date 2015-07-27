/*
 * JScroll - the scrollable desktop pane for Java.
 * Copyright (C) 2003 Tom Tessier
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Note:
 * Original author:  Tom Tessier
 * Modified and adapted by: Lora E. Murphy, October 2005
 *
 * I wanted to add a scroll pane to my desktop.  Tom Tessier wrote an excellent
 * package for implementing a very fancy desktop, but 1) it didn't quite work
 * with my application, 2) it looked a little funny to me, and 3) I didn't need
 * all the bells and whistles he provided.  I took this class and stripped it
 * down to just the pieces I needed, copying over and tweaking code to make it
 * just what I needed.  --LEM, 10-13-05
 *
 */

package sortie.datavisualizer;

import java.awt.event.*;
import javax.swing.*;

/**
 * This class listens for the movement of desktop frames and resizes the
 * desktop as necessary to make sure every part of it is reachable by
 * scrolling.
 */
public class DesktopListener
    implements ComponentListener, ActionListener {

  /**Desktop pane that will need resizing for scroll bars*/
  JDesktopPane m_jPane;
  /**Scroll pane enclosing m_jPane*/
  JScrollPane m_jScroller;

  /**
   * Creates the DesktopListener object.
   *
   * @param jPane The pane to resize as necessary
   * @param jScroller The scroll pane enclosing jPane
   */
  public DesktopListener(JDesktopPane jPane, JScrollPane jScroller) {
    m_jPane = jPane;
    m_jScroller = jScroller;
  }

  /**
   * updates the preferred size of the desktop when either an internal frame
   * or the scrollable desktop pane itself is resized
   *
   * @param e the ComponentEvent
   */
  public void componentResized(ComponentEvent e) {
    resizeDesktop();
  }

  /**
   * revalidates the desktop to ensure the viewport has the proper
   * height/width settings when a new component is shown upon the desktop
   *
   * @param e the ComponentEvent
   */
  public void componentShown(ComponentEvent e) {
    m_jScroller.revalidate();
  }

  /**
   * updates the preferred size of the desktop when a component is moved
   *
   * @param e the ComponentEvent
   */
  public void componentMoved(ComponentEvent e) {
    resizeDesktop();
  }

  /**
   * interface placeholder
   *
   * @param e the ComponentEvent
   */
  public void componentHidden(ComponentEvent e) {
  }

  ///
  // respond to action events...
  ///

  /**
   * common actionPerformed method that responds to both button
   * and menu events.
   * If no action command provided in the ActionEvent, selects
   * the frame associated with the current button / menu item (if any).
   *
   * @param e the ActionEvent
   */
  public void actionPerformed(ActionEvent e) {

//    JInternalFrame associatedFrame = ( e.getSource()).getAssociatedFrame();

//    if (associatedFrame != null) {
//      associatedFrame.selectFrameAndAssociatedButtons();
//      desktopMediator.centerView(associatedFrame);
//    }
  }

  /**
   * resizes the virtual desktop based upon the locations of its
   * internal frames. This updates the desktop scrollbars in real-time.
   * Executes as an "invoked later" thread for a slight perceived
   * performance boost.
   */
  public void resizeDesktop() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        // has to go through all the internal frames now and make sure none
        // off screen, and if so, add those scroll bars!
        java.awt.Rectangle viewP = m_jScroller.getViewport().getViewRect();

        int maxX = viewP.width + viewP.x;
        int maxY = viewP.height + viewP.y;
        int minX = viewP.x;
        int minY = viewP.y;

        // determine the min/max extents of all internal frames
        JInternalFrame f = null;
        JInternalFrame[] frames = m_jPane.getAllFrames();

        for (int i = 0; i < frames.length; i++) {
          f = frames[i];

          if (f.getX() < minX) { // get minimum X
            minX = f.getX();
          }

          if ( (f.getX() + f.getWidth()) > maxX) {
            maxX = f.getX() + f.getWidth();
          }

          if (f.getY() < minY) { // get minimum Y
            minY = f.getY();
          }

          if ( (f.getY() + f.getHeight()) > maxY) {
            maxY = f.getY() + f.getHeight();
          }
        }

        m_jScroller.setVisible(false); // don't update the viewport while we move

        // everything (otherwise desktop looks 'bouncy')
        if ( (minX != 0) || (minY != 0)) {
          // have to scroll it to the right or up the amount that it's off screen...
          // before scroll, move every component to the right / down by that amount
          for (int i = 0; i < frames.length; i++) {
            f = frames[i];
            f.setLocation(f.getX() - minX, f.getY() - minY);
          }

          // have to scroll (set the viewport) to the right or up the amount
          // that it's off screen...
          JViewport view = m_jScroller.getViewport();
          view.setViewSize(new java.awt.Dimension( (maxX - minX),
                                                  (maxY - minY)));
          view.setViewPosition(new java.awt.Point( (viewP.x - minX),
                                                  (viewP.y - minY)));
          m_jScroller.setViewport(view);
        }

        // resize the desktop
        m_jPane.setPreferredSize(new java.awt.Dimension(maxX - minX,
            maxY - minY));
        m_jPane.revalidate();

        m_jScroller.setVisible(true); // update the viewport again
      }
    });
  }

}
