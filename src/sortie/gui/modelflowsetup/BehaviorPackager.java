package sortie.gui.modelflowsetup;

import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.simpletypes.SpeciesTypeCombo;

/**
 * Class for tracking behaviors. This packages behaviors with the information
 * needed to track the changes that have been made to them.
 * @author LORA
 *
 */
public class BehaviorPackager {
  /**Descriptor string - what the user will see*/
  public String m_sDescriptor;
  /**Parameter file tag - so we can pass this to BehaviorTypeBase when 
   * creating new behaviors*/
  public String m_sParameterFileTag;
  /**Species-type combos as assigned in these windows*/
  public ArrayList<SpeciesTypeCombo> mp_oSpeciesTypes = new ArrayList<SpeciesTypeCombo>(0);
  /**Behavior being edited, or null if it is to be instantiated */
  public Behavior m_oBehavior = null;
  /**Flag indicating a behavior is to be deleted */
  public boolean m_bDeleted = false;
  /**Group number of BehaviorTypeBase grouping - indicates how to sort */
  public int m_iGroupNumber = -1;
  /**Index matcher. Can be used to track across different lists */
  public int m_iIndexMatcher = -1;
  
  public static final String SEPARATOR = "-----------------";

  /**
   * Constructor.
   */
  public BehaviorPackager() {;}

  /**
   * Constructor - convenience
   * @param sDescriptor
   * @param sParFileTag Parameter file tag for behavior
   * @param iGroupNumber
   */
  public BehaviorPackager(String sDescriptor, String sParFileTag, int iGroupNumber) {
    m_sDescriptor = sDescriptor;
    m_sParameterFileTag = sParFileTag;
    m_iGroupNumber = iGroupNumber;
  }

  public String toString() {return m_sDescriptor;}

  public Object clone() {
    BehaviorPackager clone = new BehaviorPackager();
    clone.m_sDescriptor = this.m_sDescriptor;
    clone.m_sParameterFileTag = this.m_sParameterFileTag;
    clone.m_oBehavior = this.m_oBehavior;
    clone.m_bDeleted = this.m_bDeleted;
    clone.m_iGroupNumber = this.m_iGroupNumber;
    clone.mp_oSpeciesTypes = new ArrayList<SpeciesTypeCombo>(this.mp_oSpeciesTypes.size());
    for (SpeciesTypeCombo oCombo : this.mp_oSpeciesTypes)
      clone.mp_oSpeciesTypes.add((SpeciesTypeCombo)oCombo.clone());

    return clone;
  }

  public boolean equals(Object obj) {
    if (this.hashCode() == obj.hashCode()) return true;
    return false;      
  }
}