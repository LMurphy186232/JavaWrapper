package sortie.datavisualizer;

/**
 * Represents one (x, y, z) data item for an xyz-series. Since there's no limit
 * on what can be done with the values, the members are all public.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */

public class XYZDataItem {
  /** The x-value. */
  public double fX;

  /** The y-value. */
  public double fY;

  /** The z-value. */
  public double fZ;

  /**
   * Constructor.
   *
   * @param fX  the x-value.
   * @param fY  the y-value.
   * @param fZ  the z-value.
   */
  public XYZDataItem(double fX, double fY, double fZ) {
    this.fX = fX;
    this.fY = fY;
    this.fZ = fZ;
  }
}
