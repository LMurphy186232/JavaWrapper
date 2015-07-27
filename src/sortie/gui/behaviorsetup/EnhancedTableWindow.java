package sortie.gui.behaviorsetup;


/**
 * Interface for classes wishing to use the EnhancedTable class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public interface EnhancedTableWindow {

  /**
   * A table calls this function to let the parent class know that it was the
   * last one with focus.  This is useful if you are going to implement
   * copy/paste operations.
   * @param oTable EnhancedTable Table that last had focus.
   */
  public void setLastTouched(EnhancedTable oTable);
}