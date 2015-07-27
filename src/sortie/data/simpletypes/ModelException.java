package sortie.data.simpletypes;

/**
 * Model exception class.  This is what will be thrown by application
 * functions.  Other types of exceptions are wrapped in this class.  This has
 * the same structure as the C++ error structure.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 */

public class ModelException
    extends Throwable {

  /**
   * Constructor.
   */
  public ModelException() {
    super("");
    iErrorCode = -1;
    sCallingFunction = "";
  }

  /**
   * Constructor.
   * @param iErrorCode Error code for the exception.
   * @param sCallingFunction The function that threw the error.
   * @param sMessage Error message.
   */
  public ModelException(int iErrorCode, String sCallingFunction, String sMessage) {
    super(sMessage);
    this.iErrorCode = iErrorCode;
    this.sCallingFunction = sCallingFunction;
  }

  public int iErrorCode; /**<Error code.  Find a list in ErrorGUI.*/
  public String sCallingFunction; /**<The function that threw the error.*/
}
