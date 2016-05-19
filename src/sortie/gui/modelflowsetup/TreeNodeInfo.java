package sortie.gui.modelflowsetup;

/**
 * Objects set in tree nodes to give information about what to do when the
 * user clicks a node.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
class TreeNodeInfo {
  /**Display name for the node*/
  String m_sName;

  /**Node type - allowed values are BEHAVIOR, SPECIES, TYPE, or IGNORE*/
  int m_iNodeType;

  /** Behavior node */
  public static final int BEHAVIOR = 0;
  /** Species node */
  public static final int SPECIES = 1;
  /** Type node */
  public static final int TYPE = 2;
  /** Grid node */
  public static final int GRID = 3;
  /** Node to ignore */
  public static final int IGNORE = 4;

  /**
   * Constructor.
   * @param sName Display name of the node
   * @param iType Node type - allowed values are BEHAVIOR, SPECIES, TYPE,
   * or IGNORE
   */
  TreeNodeInfo(String sName, int iType) {
    m_sName = sName;
    m_iNodeType = iType;
  }

  /**
   * Overridden method from java.lang.Object
   * @return Display name of node
   */
  public String toString() {
    return m_sName;
  }

  /**
   * Gets the node type.
   * @return Node type.
   */
  public int getNodeType() {
    return m_iNodeType;
  }
}