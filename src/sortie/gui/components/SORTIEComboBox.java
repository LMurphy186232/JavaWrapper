package sortie.gui.components;

import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;


/**
 * Improves the basic combo box by allowing the popup area to be bigger than 
 * the box itself. I got this from 
 * http://www.jroller.com/santhosh/entry/make_jcombobox_popup_wide_enough 
 * which got it from the following bug: 
 * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4618607
 * 
 * The only problem is that it doesn't auto-resize after new items are added.
 * 
 * @author Lora Murphy (kind of!)
 */
public class SORTIEComboBox<T> extends JComboBox<T>{ 
  

  public SORTIEComboBox() {
    setFont(new SortieFont());
    /*addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
      public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e){;};
      public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e){;};
      public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e){
        JComboBox box = (JComboBox) e.getSource();
        Object comp = box.getUI().getAccessibleChild(box, 0);
        if (!(comp instanceof javax.swing.JPopupMenu)) return;
        javax.swing.JComponent scrollPane = (javax.swing.JComponent) ((javax.swing.JPopupMenu) comp).getComponent(0);
        Dimension dim = new Dimension();
        dim.width = box.getPreferredSize().width;
        java.awt.FontMetrics fm = getFontMetrics(getFont());
        javax.swing.plaf.basic.BasicComboPopup popup = (javax.swing.plaf.basic.BasicComboPopup) 
             box.getUI().getAccessibleChild(box, 0);// Popup
        if (popup == null)
          return;
        int size = (int) box.getPreferredSize().getWidth();
        for (int i = 0; i < getItemCount(); i++) {
          String str = (String) getItemAt(i);
          if (size < fm.stringWidth(str))
            size = fm.stringWidth(str);
        }
        int offset = 0;
        if (((javax.swing.JScrollPane)scrollPane).getVerticalScrollBar().isVisible())
          offset += ((javax.swing.JScrollPane)scrollPane).getVerticalScrollBar().getWidth();
        dim.width = size;
        dim.height = scrollPane.getPreferredSize().height;
        scrollPane.setPreferredSize(dim);
        }; 
    });*/
  } 

  public SORTIEComboBox(final T items[]){
    super(items); 
    setFont(new SortieFont());
  } 

  public SORTIEComboBox(ComboBoxModel<T> aModel) { 
    super(aModel);
    setFont(new SortieFont());
  } 

  private boolean layingOut = false; 

  public void doLayout(){ 
    try{ 
      layingOut = true; 
      super.doLayout(); 
    } finally{ 
      layingOut = false; 
    } 
  } 

  public Dimension getSize(){ 
    Dimension dim = super.getSize(); 
    if(!layingOut) 
      dim.width = Math.max(dim.width, getPreferredSize().width); 
    return dim; 
  }
}

/**
 * This approach was fine - but it showed too many rows and wouldn't show fewer
 * rows when there were only a couple choices.
 */
/*public class SORTIEComboBox extends JComboBox {

  

  /**
   * Constructor.
   * 
   * @param aModel
   *          Combo box model for this combo box.
   */
/*  public SORTIEComboBox(ComboBoxModel aModel) {
    super(aModel);
    setFont(new ModelFont());
  } */

  /**
   * Constructor.
   */
/*  public SORTIEComboBox() {
    super();
    setFont(new ModelFont());
  }*/

  /**
   * Causes the popup to resize.
   */
/*  public void updateUI() {
    super.updateUI();
    resizeComboPopup();
  }*/

  /**
   * Causes the popup to resize.
   */
/*  public void firePopupMenuWillBecomeVisible() {
    resizeComboPopup();
    super.firePopupMenuWillBecomeVisible();
  }*/

  /**
   * Makes the popup resize to fit the biggest item. I got this from the 
   * Internet - 
   * http://forum.java.sun.com/thread.jspa?threadID=522572&messageID=2501520
   */
/*  private void resizeComboPopup() {
    java.awt.FontMetrics fm = getFontMetrics(getFont());
    BasicComboPopup popup = (BasicComboPopup) getUI().getAccessibleChild(this,
        0);// Popup
    if (popup == null)
      return;
    int size = (int) getPreferredSize().getWidth();
    for (int i = 0; i < getItemCount(); i++) {
      String str = (String) getItemAt(i);
      if (size < fm.stringWidth(str))
        size = fm.stringWidth(str);
    }
    java.awt.Component comp = popup.getComponent(0);// JScrollPane
    javax.swing.JScrollPane scrollpane = null;
    int offset = 0;
    if (comp instanceof javax.swing.JScrollPane) {
      scrollpane = (javax.swing.JScrollPane) comp;
      if (scrollpane.getVerticalScrollBar().isVisible())
        offset += scrollpane.getVerticalScrollBar().getWidth();
    }
    popup.setPreferredSize(new Dimension(size + offset, popup
        .getPreferredSize().height));
    popup.setLayout(new java.awt.BorderLayout());
    popup.add(comp, java.awt.BorderLayout.CENTER);
  }

}

/**
 * Causes combo box drop-down pop-ups to expand to correctly display their
 * choices. This class is a good idea but I couldn't get the look-and-feel
 * of the buttons to match.
 * 
 * @author Lora Murphy
 */
/*class BigPopupComboBoxUI extends BasicComboBoxUI {
  /**
   * Creates the arrow button
   */
/*  protected JButton createArrowButton() {
    JButton button = new BasicArrowButton(BasicArrowButton.SOUTH);
    button.setUI((javax.swing.plaf.ButtonUI) javax.swing.UIManager
        .getUI(button));
    return button;
  }*/

  /**
   * Creates our custom combo box pop-up
   * 
   * @return Custom pop-up
   */
/*  protected ComboPopup createPopup() {
    Popup popup = new Popup(comboBox);
    popup.getAccessibleContext().setAccessibleParent(comboBox);
    return popup;
  }*/

  /**
   * Displays a pop-up that's big enough to display fully all the choices
   * 
   * @author Lora Murphy
   */
/*  class Popup extends BasicComboPopup {
    

    /**
     * Constructor.
     * 
     * @param comboBox
     *          The combo box this is attached to.
     */
/*    public Popup(JComboBox comboBox) {
      super(comboBox);
      this.setLayout(new GridLayout(comboBox.getModel().getSize(), 1, 5, 5));
    }*/

    /**
     * Makes the popup the length of the longest item.
     * 
     * @return Dimension long enough for the longest combo box item.
     */
/*    public Dimension getPreferredSize() {
      Dimension size = super.getPreferredSize();
      int i;
      for (i = 0; i < comboBox.getModel().getSize(); i++) {
        JLabel jLabel = new JLabel(comboBox.getModel().getElementAt(i)
            .toString());
        jLabel.setFont(new ModelFont());
        if (jLabel.getPreferredSize().getWidth() + 10 > size.width) {
          size.width = (int) jLabel.getPreferredSize().getWidth() + 10;
        }
      }
      return size;
    }
  }
} */
