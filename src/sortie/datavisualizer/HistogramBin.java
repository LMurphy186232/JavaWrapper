package sortie.datavisualizer;

/**
 * Copied from HistogramBin in JFreeChart by Jelai Wang with some
 * modifications.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>June 28, 2004:  Made the count a float (LEM)
 */

public class HistogramBin {
  /** The number of items in the bin. */
  private float m_fCount = 0;

  /** The start boundary. */
  private float m_fStartBoundary;

  /** The end boundary. */
  private float m_fEndBoundary;

  /**
   * Creates a new bin.
   *
   * @param m_fStartBoundary  the start boundary.
   * @param m_fEndBoundary  the end boundary.
   */
  HistogramBin(float m_fStartBoundary, float m_fEndBoundary) {
    if (m_fStartBoundary > m_fEndBoundary) {
      throw new IllegalArgumentException(
          "HistogramBin(...):  m_fStartBoundary > m_fEndBoundary."
          );
    }
    this.m_fStartBoundary = m_fStartBoundary;
    this.m_fEndBoundary = m_fEndBoundary;
  }

  /**
   * Returns the number of items in the bin.
   *
   * @return The item count.
   */
  public float getCount() {
    return m_fCount;
  }

  /**
   * Sets the number of items in the bin.  Lora added this.
   * @param fCount The item count.
   */
  public void setCount(float fCount) {
    m_fCount = fCount;
  }

  /**
   * Increments the item count.
   */
  public void incrementCount() {
    m_fCount++;
  }

  /**
   * Returns the start boundary.
   *
   * @return The start boundary.
   */
  public float getStartBoundary() {
    return m_fStartBoundary;
  }

  /**
   * Returns the end boundary.
   *
   * @return The end boundary.
   */
  public float getEndBoundary() {
    return m_fEndBoundary;
  }

  /**
   * Returns the bin width.
   *
   * @return The bin width.
   */
  public float getBinWidth() {
    return m_fEndBoundary - m_fStartBoundary;
  }

}