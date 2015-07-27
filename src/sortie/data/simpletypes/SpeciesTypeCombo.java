package sortie.data.simpletypes;

import sortie.data.funcgroups.TreePopulation;
import sortie.gui.ErrorGUI;


/**
* This class allows you to group together a species number and a type number
* into one object that can be placed in an array or vector or passed as an
* argument.  This is useful, for instance, when a behavior object wants to
* keep a list of those species and types to which each of its sub-behaviors
* applies.
* <p>Copyright: Copyright (c) 2003</p>
* <p>Company: </p>
* @author not attributable
* @version 1.0
*
* <br>Edit history:
* <br>------------------
* <br>April 28, 2004: Submitted in beta version (LEM)
* <br>January 23, 2007: Removed problematic error processing (LEM)
*/

public class SpeciesTypeCombo {
  private int m_iSpecies, /**<Tree species.*/
      m_iType; /**<Tree type.*/

  /**
   * Constructor.
   * @param iSpecies Species number.
   * @param iType Type number.
   * @param oPop Tree population object for data validation.
   * @throws ModelException if either species or type number is invalid
   */
  public SpeciesTypeCombo(int iSpecies, int iType, TreePopulation oPop) throws
      ModelException {

    setSpecies(iSpecies);

    if (iType < 0 || iType >= TreePopulation.getNumberOfTypes()) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                               "SpeciesTypeCombo - Invalid type number \"" +
                               String.valueOf(iType) + "\"."));
    }
    m_iType = iType;

  }

  /**
   * Constructor.  Private so that the cloning process can use it without
   * having to go through validation with the TreePopulation - since
   * presumably an already existing object would have validated in its own
   * constructor.
   * @param iSpecies Tree species.
   * @param iType Tree type.
   */
  private SpeciesTypeCombo(int iSpecies, int iType) {
    m_iSpecies = iSpecies;
    m_iType = iType;
  }

  /**
   * Species setter.
   * @param iSpecies Species to set.
   */
  public void setSpecies(int iSpecies) throws ModelException {
    
    //LEM: I wanted to do error checking but it's not possible when the species
    //list is actually in flux - the tree population doesn't have the correct
    //species list yet
    
    if (iSpecies < 0) {// || iSpecies >= oPop.getNumberOfSpecies()) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                               "SpeciesTypeCombo - Invalid species number \"" +
                               String.valueOf(iSpecies) + "\"."));
    } 
    m_iSpecies = iSpecies;
  }

  /**
   * Implemented equality checker.
   * @param oToCheck Object to check for equality.
   * @return true if species and type both match, false otherwise.
   */
  public boolean equals(Object oToCheck) {
    if (! (oToCheck instanceof SpeciesTypeCombo)) {
      return false;
    }

    SpeciesTypeCombo oChecker = (SpeciesTypeCombo) oToCheck;
    if (oChecker.getSpecies() == m_iSpecies && oChecker.getType() == m_iType) {
      return true;
    }
    return false;
  }

  /**
   * Override this so clones can be made.
   * @return Clone of this object.
   */
  public Object clone() {
    SpeciesTypeCombo oClone = new SpeciesTypeCombo(m_iSpecies, m_iType);
    return oClone;
  }

  /**
   * Gets the species for this combo.
   * @return The species.
   */
  public int getSpecies() {
    return m_iSpecies;
  }

  /**
   * Gets the type for this combo.
   * @return The type.
   */
  public int getType() {
    return m_iType;
  }
}
