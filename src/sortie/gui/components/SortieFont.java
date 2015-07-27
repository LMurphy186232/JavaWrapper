package sortie.gui.components;

import java.awt.Font;

/**
 * The common font for all GUI elements.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>May 11, 2004: Added support for font styles (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 */


public class SortieFont extends Font {
	
  /**
   * Constructor.  Use this for plain labels and text.
   */
  public SortieFont() {
    super("Dialog", 0, 14);
  }

  /**
   * Constructor.  Use this to customize the model font a bit.
   * @param iStyle Font style, such as BOLD or ITALIC, to be added to the basic
   * model font.
   */
  public SortieFont(int iStyle) {
    super("Dialog", iStyle, 14);
  }

  /**
   * Constructor.  Use this to customize the model font.
   * @param iStyle Font style, such as BOLD or ITALIC, to be added to the basic
   * model font.  (You can use Font.PLAIN if you don't want customizations.)
   * @param iSize The size RELATIVE TO the normal ModelFont size.  So, if you
   * wanted a size 2 points bigger than normal, use 2.
   */
  public SortieFont(int iStyle, int iSize) {
    super("Dialog", iStyle, 14+iSize);
  }

}