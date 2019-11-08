package sortie.gui.components;

import javax.swing.JLabel;


/**
 * Wrapper class for labels which set them up with the correct font. 
 * Copyright: Copyright (c) Charles D. Canham 2011
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>May 11, 2004: Added support for font styles (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 */
public class SortieLabel extends JLabel {
  
  public SortieLabel(String text) {
    super(text);
    this.setFont(new SortieFont());
  }

  public SortieLabel(String text, int horizontalAlignment) {
    super(text, horizontalAlignment);
    this.setFont(new SortieFont());
  }
  
  public SortieLabel(String text, int horizontalAlignment, int iStyle, int iSize) {
    super(text, horizontalAlignment);
    this.setFont(new SortieFont(iStyle, iSize));
  }
}
