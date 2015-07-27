package sortie.sax;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */

public class SaxParseTools {
  /**
   * Trims out characters that might mess with the reading of the string.
   * These are new lines, carriage returns, and tabs at the
   * beginning and end of the string.  Since XML files are text files,
   * sometimes these get stuck into values.
   * @param sToTrim String to trim.
   * @return Trimmed string.
   *
   * <br>Edit history:
   * <br>------------------
   * <br>April 28, 2004: Submitted in beta version (LEM)
   */
  public static String XMLTrim(String sToTrim) {
    if (sToTrim == null || sToTrim.length() == 0) {
      return sToTrim;
    }

    String sTrimmed;

    //Clean the beginning of the string
    int i, iIndex = sToTrim.length();
    for (i = 0; i < sToTrim.length(); i++) {
      char cChar = sToTrim.charAt(i);
      if (cChar != '\n' && cChar != '\r' && cChar != '\t') {
        iIndex = i;
        break;
      }
    }

    sTrimmed = sToTrim.substring(iIndex);
    if (sTrimmed.length() == 0) {
      return sTrimmed;
    }

    //Clean the end of the string
    iIndex = 0;
    for (i = sTrimmed.length() - 1; i >= 0; i--) {
      char cChar = sTrimmed.charAt(i);
      if (cChar != '\n' && cChar != '\r' && cChar != '\t') {
        iIndex = i;
        break;
      }
    }
    return sTrimmed.substring(0, iIndex + 1);
  }
}
