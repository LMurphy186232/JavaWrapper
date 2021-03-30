package sortie.parfile;

import java.io.BufferedWriter;
import java.io.File;

import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelVector;

public class ParFileHelpers {

  /**
   * Writes a piece of data to an XML file.  If the string value is an empty
   * string, nothing is written. 
   * @param ojOut The file to write to.
   * @param oData The data being written.
   * @throws java.io.IOException Passes on exceptions from FileWriter
   */
  public static void writeDataToFile(BufferedWriter ojOut, ModelData oData) throws java.io.
      IOException {
    String sValue = oData.toString().trim();
    if (sValue.length() > 0) {
      ojOut.write("<" + oData.getXMLTag() + ">" + oData.toString() +
                  "</" + oData.getXMLTag() + ">");
    }
  }

  /**
   * Writes a vector of values that doesn't have anything to do with species.
   * @param jOut The file to write to.
   * @param p_oData The vector of data pieces.
   * @throws java.io.IOException Passes on exceptions from FileWriter
   */
  public static void writeVectorValueToFile(BufferedWriter jOut, ModelVector p_oData) throws
      java.io.IOException {

    if (p_oData == null || p_oData.getValue().size() == 0) {
      return;
    }

    int i;

    jOut.write("<" + p_oData.getXMLTag() + ">");

    for (i = 0; i < p_oData.getValue().size(); i++) {
        if (p_oData.getValue().get(i) != null) {
          jOut.write("<" + p_oData.getChildXMLTag() + ">" +
                     p_oData.getValue().get(i).toString() + "</" +
                     p_oData.getChildXMLTag() + ">");
      }
    }
    jOut.write("</" + p_oData.getXMLTag() + ">");
  }
}
