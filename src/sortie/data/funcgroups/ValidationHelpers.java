package sortie.data.funcgroups;

import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;

public class ValidationHelpers {
  /**
   * Makes sure that all the values in a vector fall between two bounds.  Only
   * those indexes for which p_bAppliesTo = true at the same index are checked.
   * @param oData ModelVector Vector to check.
   * @param p_bAppliesTo boolean[] Set of flags for which values to check.
   * @param fLowerBound Float Minimum acceptable value.
   * @param fUpperBound Float Maximum acceptable value.
   * @throws ModelException If any value falls outside of the range.
   */
  static public void makeSureAllAreBounded(ModelVector oData, boolean[] p_bAppliesTo, float fLowerBound, float fUpperBound) throws ModelException {
    int i;
    Number oNumber;
    for (i = 0; i < oData.getValue().size(); i++) {
      if (p_bAppliesTo[i]) {
        oNumber = (Number) oData.getValue().get(i);
        if (oNumber == null || oNumber.doubleValue() < fLowerBound ||
            oNumber.doubleValue() > fUpperBound) {
          throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "All values in " 
              + oData.getDescriptor() + " must be between " + fLowerBound +
              " and " + fUpperBound + "."));
        }
      }
    }
  }

  /**
   * Makes sure the elements of a float array are positive numbers (greater
   * than zero).  Only those indexes for which p_bAppliesTo = true at
   * the same index are checked.
   * @param p_fData The array to check.
   * @param sName The name of the vector, to put in the error message.
   * @param p_bAppliesTo Set of flags for which values to check
   * @throws ModelException if there are any non-positive number values in the
   * vector.
   */
  static public void makeSureAllPositive(Float[] p_fData, String sName,
                                  boolean[] p_bAppliesTo) throws
      ModelException {
    int i;
    for (i = 0; i < p_fData.length; i++) {
      if (p_bAppliesTo[i]) {
        if (p_fData[i] == null || p_fData[i].compareTo(new Float(0)) <= 0) {
          throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", "All values in " 
              + sName + " must be greater than zero."));
        }
      }
    }
  }

  /**
   * Makes sure the elements of a float array are positive numbers (greater
   * than zero).
   * @param p_fData The array to check.
   * @param sName The name of the vector, to put in the error message.
   * @throws ModelException if there are any non-positive number values in the
   * vector.
   */
  static public void makeSureAllPositive(Float[] p_fData, String sName) throws
      ModelException {
    boolean[] p_bAppliesTo = new boolean[p_fData.length];
    int i;
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = true;
    }
    makeSureAllPositive(p_fData, sName, p_bAppliesTo);
  }

  /**
   * Makes sure the elements of a float array are non-negative numbers
   * (greater than or equal to zero).  Only those indexes for which
   * p_bAppliesTo = true at the same index are checked.
   * @param p_fData The array to check.
   * @param sName The name of the vector, to put in the error message.
   * @param p_bAppliesTo Set of flags for which values to check
   * @throws ModelException if there are any negative number values in the
   * vector.
   */
  static public void makeSureAllNonNegative(Float[] p_fData, String sName,
                                     boolean[] p_bAppliesTo) throws
      ModelException {
    int i;
    for (i = 0; i < p_fData.length; i++) {
      if (p_bAppliesTo[i]) {
        if (p_fData[i] == null || p_fData[i].compareTo(new Float(0)) < 0) {
          throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", "All values in " 
              + sName + " must be greater than or equal to zero."));
        }
      }
    }
  }

  /**
   * Makes sure the elements of a float array are non-negative numbers
   * (greater than or equal to zero).
   * @param p_fData The array to check.
   * @param sName The name of the vector, to put in the error message.
   * @throws ModelException if there are any negative number values in the
   * vector.
   */
  static public void makeSureAllNonNegative(Float[] p_fData, String sName) throws
      ModelException {
    boolean[] p_bAppliesTo = new boolean[p_fData.length];
    int i;
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = true;
    }
    makeSureAllNonNegative(p_fData, sName, p_bAppliesTo);
  }

  /**
   * Makes sure the elements of an integer array are positive numbers
   * (greater than zero).  Only those indexes for which p_bAppliesTo = true at
   * the same index are checked.
   * @param p_iData The array to check.
   * @param sName The name of the vector, to put in the error message.
   * @param p_bAppliesTo Set of flags for which values to check
   * @throws ModelException if there are any non-positive number values in the
   * vector.
   */
  static public void makeSureAllPositive(Integer[] p_iData, String sName,
                                  boolean[] p_bAppliesTo) throws
      ModelException {
    int i;
    for (i = 0; i < p_iData.length; i++) {
      if (p_bAppliesTo[i]) {
        if (p_iData[i] == null || p_iData[i].compareTo(new Integer(0)) <= 0) {
          throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", "All values in " 
              + sName + " must be greater than zero."));
        }
      }
    }
  }

  /**
   * Makes sure the elements of an integer array are positive numbers
   * (greater than zero).
   * @param p_iData The array to check.
   * @param sName The name of the vector, to put in the error message.
   * @throws ModelException if there are any non-positive number values in the
   * vector.
   */
  static public void makeSureAllPositive(Integer[] p_iData, String sName) throws
      ModelException {
    boolean[] p_bAppliesTo = new boolean[p_iData.length];
    int i;
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = true;
    }
    makeSureAllPositive(p_iData, sName, p_bAppliesTo);
  }

  /**
   * Makes sure the elements of an integer array are non-negative numbers
   * (greater than or equal to zero).  Only those indexes for which
   * p_bAppliesTo = true at the same index are checked.
   * @param p_iData The array to check.
   * @param sName The name of the vector, to put in the error message.
   * @param p_bAppliesTo Set of flags for which values to check
   * @throws ModelException if there are any negative number values in the
   * vector.
   */
  static public void makeSureAllNonNegative(Integer[] p_iData, String sName,
                                     boolean[] p_bAppliesTo) throws
      ModelException {
    int i;
    for (i = 0; i < p_iData.length; i++) {
      if (p_bAppliesTo[i]) {
        if (p_iData[i] == null || p_iData[i].compareTo(new Integer(0)) < 0) {
          throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", "All values in " 
              + sName + " must be greater than or equal to zero."));
        }
      }
    }
  }

  /**
   * Makes sure the elements of an integer array are non-negative numbers
   * (greater than or equal to zero).
   * @param p_iData The array to check.
   * @param sName The name of the vector, to put in the error message.
   * @throws ModelException if there are any negative number values in the
   * vector.
   */
  static public void makeSureAllNonNegative(Integer[] p_iData, String sName) throws
      ModelException {
    boolean[] p_bAppliesTo = new boolean[p_iData.length];
    int i;
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = true;
    }
    makeSureAllNonNegative(p_iData, sName, p_bAppliesTo);
  }

   
  /**
   * Makes sure a value is greater than a certain value.
   * @param oValue The value to check.
   * @param fLowerBound The value the test value must be greater than.
   * @throws ModelException if the value is not greater than fLowerBound.
   */
  static public void makeSureGreaterThan(ModelFloat oValue, float fLowerBound) throws ModelException {
    if (oValue.getValue() <= fLowerBound) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
            oValue.getDescriptor() + " must be greater than " + 
            fLowerBound + "."));
    }
  }
  
  /**
   * Makes sure a value is greater than or equal to a certain value.
   * @param oValue The value to check.
   * @param fLowerBound The value the test value must be greater than or equal
   * to.
   * @throws ModelException if the value is not greater than fLowerBound.
   */
  static public void makeSureGreaterThanEqualTo(ModelFloat oValue, float fLowerBound) throws ModelException {
    if (oValue.getValue() < fLowerBound) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          oValue.getDescriptor() + " must be greater than or equal to " + 
                                fLowerBound + "."));
    }
  }
  
  /**
   * Makes sure a value is greater than a certain value.
   * @param oValue The value to check.
   * @param fLowerBound The value the test value must be greater than.
   * @throws ModelException if the value is not greater than fLowerBound.
   */
  static public void makeSureGreaterThan(ModelInt oValue, float fLowerBound) throws ModelException {
    if (oValue.getValue() <= fLowerBound) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          oValue.getDescriptor() + " must be greater than " + 
          fLowerBound + "."));
    }
  }
  
  /**
   * Makes sure a value is greater than or equal to a certain value.
   * @param oValue The value to check.
   * @param fLowerBound The value the test value must be greater than or equal
   * to.
   * @throws ModelException if the value is not greater than fLowerBound.
   */
  static public void makeSureGreaterThanEqualTo(ModelInt oValue, float fLowerBound) throws ModelException {
    if (oValue.getValue() < fLowerBound) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          oValue.getDescriptor() + " must be greater than or equal to " + 
                                fLowerBound + "."));
    }
  }
  
  /**
   * Makes sure a value is less than a certain value.
   * @param oValue The value to check.
   * @param fUpperBound The value the test value must be less than.
   * @throws ModelException if the value is not less than fUpperBound.
   */
  static public void makeSureLessThan(ModelFloat oValue, float fUpperBound) throws ModelException {
    if (oValue.getValue() >= fUpperBound) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          oValue.getDescriptor() + " must be less than " + fUpperBound + "."));
    }
  }
  
  /**
   * Makes sure a value is less than a certain value.
   * @param oValue The value to check.
   * @param fUpperBound The value the test value must be less than.
   * @throws ModelException if the value is not less than fUpperBound.
   */
  static public void makeSureLessThan(ModelInt oValue, float fUpperBound) throws ModelException {
    if (oValue.getValue() >= fUpperBound) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          oValue.getDescriptor() + " must be less than " + fUpperBound + "."));
    }
  }
  
  /**
   * Makes sure a value is less than or equal to a certain value.
   * @param oValue The value to check.
   * @param fUpperBound The value the test value must be less than or equal to.
   * @throws ModelException if the value is not greater than fUpperBound.
   */
  static public void makeSureLessThanEqualTo(ModelFloat oValue, float fUpperBound) throws ModelException {
    if (oValue.getValue() > fUpperBound) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          oValue.getDescriptor() + " must be less than or equal to " + 
                                fUpperBound + "."));
    }
  }
  
  /**
   * Makes sure a value is less than or equal to a certain value.
   * @param oValue The value to check.
   * @param fUpperBound The value the test value must be less than or equal to.
   * @throws ModelException if the value is not greater than fUpperBound.
   */
  static public void makeSureLessThanEqualTo(ModelInt oValue, float fUpperBound) throws ModelException {
    if (oValue.getValue() > fUpperBound) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          oValue.getDescriptor() +" must be less than or equal to " + 
                                fUpperBound + "."));
    }
  }
  
  /**
   * Makes sure a value is not equal to another value.
   * @param oValue The value to check.
   * @param fForbidden The value the test value cannot equal.
   * @throws ModelException if the value is equal to the specified value,
   * within a very small number.
   */
  static public void makeSureNotEqualTo(ModelFloat oValue, float fForbidden) throws ModelException {
    if (java.lang.Math.abs(oValue.getValue() - fForbidden) < 1.0E-10) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
           oValue.getDescriptor() + " cannot be equal to " + fForbidden + "."));
    }
  }
  
  /**
   * Makes sure a value is not equal to another value.
   * @param oValue The value to check.
   * @param fForbidden The value the test value cannot equal.
   * @throws ModelException if the value is equal to the specified value,
   * within a very small number.
   */
  static public void makeSureNotEqualTo(ModelInt oValue, float fForbidden) throws ModelException {
    if (java.lang.Math.abs(oValue.getValue() - fForbidden) < 1.0E-10) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          oValue.getDescriptor() + " cannot be equal to " + fForbidden + "."));
    }
  }

  /**
   * Makes sure a value falls in an acceptable range.
   * @param oValue The value to check.
   * @param fLowerBound float Minimum acceptable value.
   * @param fUpperBound float Maximum acceptable value.
   * @throws ModelException If any value falls outside of the range.
   */
  static public void makeSureIsBounded(ModelFloat oValue, float fLowerBound, float fUpperBound) throws ModelException {
    float fValue = oValue.getValue();
    if (fValue < fLowerBound || fValue > fUpperBound) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", "All values in " + oValue.getDescriptor() +
                                " must be between " + fLowerBound + " and " +
                                fUpperBound + "."));
    }
  }

  /**
   * Makes sure a value falls in an acceptable range.
   * @param oValue The value to check.
   * @param fLowerBound float Minimum acceptable value.
   * @param fUpperBound float Maximum acceptable value.
   * @throws ModelException If any value falls outside of the range.
   */
  static public void makeSureIsBounded(ModelInt oValue, float fLowerBound, float fUpperBound) throws ModelException {
    int iValue = oValue.getValue();
    if (iValue < fLowerBound || iValue > fUpperBound) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          oValue.getDescriptor() + " must be between " + fLowerBound + " and " +
                                fUpperBound + "."));
    }
  }

  
  /**
   * Makes sure a value is a proportion between 0 and 1 (inclusive).
   * @param fValue Value to check.
   * @throws ModelException if the value is not a valid proportion.
   */
  static public void makeSureIsProportion(ModelFloat fValue) throws ModelException {
    if (fValue.getValue() < 0 || fValue.getValue() > 1) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          fValue.getDescriptor() + " must be a value between 0 and 1."));
    }
  }

  /**
   * Makes sure the data in a vector are proportions between 0
   * and 1 (inclusive).  Only those indexes for which p_bAppliesTo = true at
   * the same index are checked.
   * @param oData Vector to check
   * @param p_bAppliesTo Set of flags for which values to check
   * @throws ModelException if any value in the vector is not a valid
   * proportion.
   */
  static public void makeSureAllAreProportions(ModelVector oData,
                                        boolean[] p_bAppliesTo) throws
      ModelException {
    int i;
    Float fNumber;
    for (i = 0; i < oData.getValue().size(); i++) {
      if (p_bAppliesTo[i]) {
        fNumber = (Float) oData.getValue().get(i);
        if (fNumber == null || fNumber.doubleValue() < 0 ||
            fNumber.doubleValue() > 1) {
          throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", "All values in " + oData.getDescriptor()
                                    + " must be values between 0 and 1."));
        }
      }
    }
  }

  /**
   * Makes sure all the data in a vector are proportions between 0
   * and 1 (inclusive).
   * @param oData Vector to check
   * @throws ModelException if any value in the vector is not a valid
   * proportion.
   */
  static public void makeSureAllAreProportions(ModelVector oData) throws
      ModelException {
    boolean[] p_bAppliesTo = new boolean[oData.getValue().size()];
    int i;
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = true;
    }
    makeSureAllAreProportions(oData, p_bAppliesTo);
  }

  /**
   * Makes sure that a vector is of a certain size.
   * @param oData Vector to validate.
   * @param iLength Expected length.
   * @throws ModelException if the vector is not of the correct length.
   */
  static public void makeSureRightSize(ModelVector oData, int iLength) throws
      ModelException {
    if (oData.getValue().size() != iLength) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", "There is the wrong number of values in " +
                                oData.getDescriptor() + ".  Expected: " +
                                iLength + "; actual: " +
                                oData.getValue().size() + "."));
    }
  }

  /**
   * Makes sure the elements of a vector are non-negative numbers (greater
   * than or equal to zero).
   * @param oData The vector to check.
   * @throws ModelException if there are any negative number values in the
   * vector.
   */
  static public void makeSureAllNonNegative(ModelVector oData) throws
      ModelException {
    boolean[] p_bAppliesTo = new boolean[oData.getValue().size()];
    int i;
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = true;
    }
    makeSureAllNonNegative(oData, p_bAppliesTo);
  }

  /**
   * Makes sure the elements of a vector are non-zero.
   * @param oData The vector to check.
   * @throws ModelException if there are any 0 values in the
   * vector.
   */
  static public void makeSureAllNonZero(ModelVector oData) throws
      ModelException {
    boolean[] p_bAppliesTo = new boolean[oData.getValue().size()];
    int i;
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = true;
    }
    makeSureAllNonZero(oData, p_bAppliesTo);
  }

  /**
   * Makes sure the elements of a vector are non-zero numbers.  Only those
   * indexes for which p_bAppliesTo = true at the same index are checked.
   * @param oData The vector to check.
   * @param p_bAppliesTo Set of flags for which values to check
   * @throws ModelException if there are any negative number values in the
   * vector.
   */
  static public void makeSureAllNonZero(ModelVector oData, boolean[] p_bAppliesTo) throws
      ModelException {
    int i;
    Number oNumber;
    for (i = 0; i < oData.getValue().size(); i++) {
      if (p_bAppliesTo[i]) {
        oNumber = (Number) oData.getValue().get(i);
        if (oNumber == null || oNumber.doubleValue() == 0) {
          throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "No values in " + oData.getDescriptor() +
                                   " may equal zero."));
        }
      }
    }
  }

  /**
   * Makes sure the elements of a vector are positive numbers (greater than
   * zero).  Only those indexes for which p_bAppliesTo = true at
   * the same index are checked.
   * @param oData The vector to check.
   * @param p_bAppliesTo Set of flags for which values to check
   * @throws ModelException if there are any non-positive number values in the
   * vector.
   */
  static public void makeSureAllPositive(ModelVector oData, boolean[] p_bAppliesTo) throws
      ModelException {
    int i;
    Number oNumber;
    for (i = 0; i < oData.getValue().size(); i++) {
      if (p_bAppliesTo[i]) {
        Object oObject = oData.getValue().get(i);
        if (oObject instanceof Number) {
          oNumber = (Number) oData.getValue().get(i);
          if (oNumber == null || oNumber.doubleValue() <= 0) {
            throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", "All values in " +
                                      oData.getDescriptor() +
                                      " must be greater than zero."));
          }
        }
        else {
          ModelException oErr = new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "All values in " + oData.getDescriptor() + " must be numbers.");
          throw (oErr);

        }
      }
    }
  }

  /**
   * Makes sure the elements of a vector are positive numbers (greater than
   * zero).
   * @param oData The vector to check.
   * @throws ModelException if there are any non-positive number values in the
   * vector.
   */
  static public void makeSureAllPositive(ModelVector oData) throws
      ModelException {
    boolean[] p_bAppliesTo = new boolean[oData.getValue().size()];
    int i;
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = true;
    }
    makeSureAllPositive(oData, p_bAppliesTo);
  }

  /**
   * Makes sure the elements of a vector are non-negative numbers (greater
   * than or equal to zero).  Only those indexes for which p_bAppliesTo = true
   * at the same index are checked.
   * @param oData The vector to check.
   * @param p_bAppliesTo Set of flags for which values to check
   * @throws ModelException if there are any negative number values in the
   * vector.
   */
  static public void makeSureAllNonNegative(ModelVector oData, boolean[] p_bAppliesTo) throws
      ModelException {
    int i;
    Number oNumber;
    for (i = 0; i < oData.getValue().size(); i++) {
      if (p_bAppliesTo[i]) {
        oNumber = (Number) oData.getValue().get(i);
        if (oNumber == null || oNumber.doubleValue() < 0) {
          throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", "All values in " +
                                    oData.getDescriptor() +
                                    " must be greater than or equal to zero."));
        }
      }
    }
  }

}
