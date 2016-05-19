package sortie.gui.modelflowsetup;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import sortie.gui.components.MultilineLabel;
import sortie.gui.components.SortieFont;

/**
 * Provides multi-line text wrapping to our list boxes
 * Copyright: Copyright (c) Charles D. Canham 2003
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 */
class ListRenderer
extends MultilineLabel
implements ListCellRenderer<BehaviorPackager> {



  /**The width of the component in which this will be displayed.  This is
   * float to save a casting step later when rendering.*/
  private float m_fComponentWidth;

  /**
   * Constructor.
   * @param iWidth Width of the component in which this renderer will display
   * text.
   */
  public ListRenderer(int iWidth) {
    m_fComponentWidth = iWidth;
  }

  /**
   * This method finds the image and text corresponding
   * to the selected value and returns the label, set up
   * to display the text and image.
   * @param list JList List object.
   * @param value Object Object to render for
   * @param index int Index of object to render
   * @param isSelected boolean Whether or not the object to render is
   * selected
   * @param cellHasFocus boolean Whether or not the object to render has
   * focus
   * @return Component Properly formatted component
   */
  public Component getListCellRendererComponent(
      JList<? extends BehaviorPackager> list,
      BehaviorPackager value,
      int index,
      boolean isSelected,
      boolean cellHasFocus) {

    String sVal = String.valueOf(value);

    this.setText(sVal);
    if (isSelected) {

      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    }
    else {
      if (index % 2 == 1) {
        setBackground(new java.awt.Color(225, 225, 225));
      }
      else {
        setBackground(list.getBackground());
      }
      setForeground(list.getForeground());
    }

    //Make the label display enough rows to show all the text
    JLabel jNotUsedField = new JLabel(sVal);
    jNotUsedField.setFont(new SortieFont());
    //float fWidth = (float) jNotUsedField.getPreferredSize().getWidth() - 15;
    //Sometimes the line above did not allow the ends of names to display. 
    //The line below seems to allow all endings to be visible without 
    //introducing extraneous line wrappings  
    float fWidth = (float) jNotUsedField.getPreferredSize().getWidth()+15;
    int iNumRows = (int) java.lang.Math.ceil(fWidth / m_fComponentWidth);
    setRows(iNumRows);

    return this;
  }
}
