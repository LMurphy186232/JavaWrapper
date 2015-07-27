package sortie.data.funcgroups;

/**
 * This class will package together a data member and an array of booleans
 * on which species uses it.
 */
public class SpeciesSpecific {
  /** Data member */
  public Object m_oData;
  /** Which species use this data member */
  public boolean[] mp_bUsed;
}
