package sortie.data.simpletypes;

import sortie.data.funcgroups.TreePopulation;


/**
 * Combines a text description of species/type combos with the actual data,
 * for display in lists.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */
public class ComboDisplay {

  /** The value to display to users. */
  private String m_sDescriptor;
  private int
      /**Combo species*/
      m_iSpecies,
      /**Combo type*/
      m_iType;

  /**
   * Constructor.  Formats a display string.
   * @param oPop Tree population.
   * @param iSpecies Species for this combo.
   * @param iType Type for this combo.
   */
  public ComboDisplay(TreePopulation oPop, int iSpecies, int iType) {
    m_iSpecies = iSpecies;
    m_iType = iType;
    if (m_iType != TreePopulation.WOODY_DEBRIS) {
      m_sDescriptor = oPop.getSpeciesNameFromCode(iSpecies).replace('_',
          ' ') + " " + TreePopulation.getTypeNameFromCode(iType) + "s";
    }
    else {
      m_sDescriptor = oPop.getSpeciesNameFromCode(iSpecies).replace('_',
          ' ') + " " + TreePopulation.getTypeNameFromCode(iType);
    }
  }

  /**
   * Overridden so that this can be displayed in JLists.
   * @return String description
   */
  public String toString() {
    return m_sDescriptor;
  }

  /**
   * Gets the species.
   * @return The species number.
   */
  public int getSpecies() {
    return m_iSpecies;
  }

  /**
   * Gets the type.
   * @return The type number.
   */
  public int getType() {
    return m_iType;
  }
}
