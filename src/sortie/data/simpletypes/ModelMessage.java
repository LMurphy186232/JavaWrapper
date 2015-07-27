package sortie.data.simpletypes;

/**
 * Structure for message passing.
 * <p>Copyright: Copyright (c) 2003 Charles D. Canham</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */

public class ModelMessage {

  /**
   * Constructor.
   */
  public ModelMessage() {
    iMessageCode = -1;
    sCallingFunction = "";
    sMessage = "";
  }

  /** Message Code. */
  int iMessageCode;
  /** The function that passed this message. */
  String sCallingFunction;
  /** Message being passed. */
  public String sMessage;
}
