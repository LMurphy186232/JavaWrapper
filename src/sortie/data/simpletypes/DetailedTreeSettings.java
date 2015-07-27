package sortie.data.simpletypes;

import sortie.data.funcgroups.OutputBehaviors;


/**
 * This class packages together a set of tree settings.  For a given species
 * and tree type, this will tell you what is available in a tree map (or
 * detailed output file).
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>February 1, 2011: Added support for dead trees (LEM)
 */

public class DetailedTreeSettings
    extends DetailedOutputSettings
    implements Cloneable {
  
  /**Tree type*/
  private int m_iType;
  
  /**Tree species*/
  private int m_iSpecies; 
  
  /**Dead reason code - can be not dead */
  private int m_iDeadCode; 

  /**
   * Constructor
   * @param iType Tree type.
   * @param iSpecies Tree species.
   */
  public DetailedTreeSettings(int iType, int iSpecies) {
    super();
    m_iType = iType;
    m_iSpecies = iSpecies;
    m_iDeadCode = OutputBehaviors.NOTDEAD; 
  }
  
  /**
   * Constructor
   * @param iType Tree type.
   * @param iSpecies Tree species.
   * @param iDeadCode Valid dead reason code from output behaviors. 
   */
  public DetailedTreeSettings(int iType, int iSpecies, int iDeadCode) {
    super();
    m_iType = iType;
    m_iSpecies = iSpecies;
    m_iDeadCode = iDeadCode; 
  }

  /**
   * Gets the tree species.
   * @return  Tree species.
   */
  public int getSpecies() {
    return m_iSpecies;
  }

  /**
   * Sets the tree species.
   * @param iNewSpecies  Tree species.
   */
  public void setSpecies(int iNewSpecies) {
    m_iSpecies = iNewSpecies;
  }

  /**
   * Gets the dead code.
   * @return The dead code.
   */
  public int getDeadCode() {
    return m_iDeadCode;
  }
  /**
   * Gets the tree type.
   * @return Tree type.
   */
  public int getType() {
    return m_iType;
  }

  /**
   * Creates a deep clone of this object.
   * @return Clone.
   */
  public Object clone() {
    DetailedTreeSettings oClone = new DetailedTreeSettings(m_iType, m_iSpecies, m_iDeadCode);
    DetailedOutputSettings.copyData(oClone, this);

    return oClone;
  }
}
