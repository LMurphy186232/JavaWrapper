package sortie.gui.behaviorsetup;

/**
 * For packaging header data in a typesafe way.
 * @author Lora Murphy
 */
public class TableData {

  /** Header row. */
  public Object[] mp_oHeaderRow;
  /** Table data. */
  public Object[][] mp_oTableData;
  /**
   * Constructor.
   * @param p_oHeaderRow Header row.
   * @param p_oTableData Table data.
   */
  public TableData(Object[] p_oHeaderRow, Object[][] p_oTableData) {
    this.mp_oHeaderRow = p_oHeaderRow;
    this.mp_oTableData = p_oTableData;
  }
}
