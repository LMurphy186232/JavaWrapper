package sortie.data.funcgroups;

import sortie.ModelTestCase;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;

/**
 * Tests the ValidationHelpers class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestValidationHelpers extends ModelTestCase {

  /**
   * Tests the makeSureAllAreProportions methods.
   */
  public void testmakeSureAllAreProportions() {

    //Normal processing
    Float fVal;
    int iNumElements = 4, i;
    ModelVector p_oData = new ModelVector("", "", "", iNumElements,
                                          ModelVector.FLOAT);
    boolean[] p_bAppliesTo = new boolean[iNumElements];

    try {
      //Case - all are proportions and are applied to all
      fVal = new Float(0.11);
      for (i = 0; i < iNumElements; i++) {
        fVal = new Float(fVal.floatValue() + 0.1);
        p_oData.getValue().add(i, fVal);
        p_bAppliesTo[i] = true;
      }
      ValidationHelpers.makeSureAllAreProportions(p_oData, p_bAppliesTo);

      //Case - all are proportions and none are applied
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = false;
      }
      ValidationHelpers.makeSureAllAreProportions(p_oData, p_bAppliesTo);

      //Case - some are applied and some non-proportions exist in unapplied
      //buckets
      p_bAppliesTo[2] = false;
      p_oData.getValue().remove(2);
      p_oData.getValue().add(2, new Float(2.3));
      ValidationHelpers.makeSureAllAreProportions(p_oData, p_bAppliesTo);

    }
    catch (ModelException oErr) {
      fail("makeSureAllAreProportions testing failed with message " +
           oErr.getMessage());
    }
    System.out.println(
        "makeSureAllAreProportions normal processing testing succeeded.");

    //Exception processing
    try {

      //Catches non-proportion values that are negative
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_oData = null;
      p_oData = new ModelVector("", "", "", iNumElements, ModelVector.FLOAT);
      fVal = new Float(0.11);
      for (i = 0; i < iNumElements; i++) {
        fVal = new Float(fVal.floatValue() + 0.1);
        p_oData.getValue().add(i, fVal);
        p_bAppliesTo[i] = true;
      }

      p_oData.getValue().remove(2);
      p_oData.getValue().add(2, new Float( -2.3));

      ValidationHelpers.makeSureAllAreProportions(p_oData, p_bAppliesTo);
      fail("makeSureAllAreProportions failed to catch invalid values.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {

      //Catches non-proportion values that are greater than 1
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_oData = null;
      p_oData = new ModelVector("", "", "", iNumElements, ModelVector.FLOAT);
      fVal = new Float(0.11);
      for (i = 0; i < iNumElements; i++) {
        fVal = new Float(fVal.floatValue() + 0.1);
        p_oData.getValue().add(i, fVal);
        p_bAppliesTo[i] = true;
      }

      p_oData.getValue().remove(0);
      p_oData.getValue().add(0, new Float(2.3));

      ValidationHelpers.makeSureAllAreProportions(p_oData, p_bAppliesTo);
      fail("makeSureAllAreProportions failed to catch invalid values.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "makeSureAllAreProportions exception processing testing succeeded.");
  }

  /**
   * Testing to make sure there are no negative values in a float array.
   */
  public void testmakeSureAllNonNegative() {
    int iNumElements = 4, i;
    Float[] p_fData = new Float[iNumElements];
    String sName = "Float Test";
    boolean[] p_bAppliesTo = new boolean[iNumElements];

    try {

      //Case - all are non-negative and are applied to all
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_fData[0] = new Float(0);
      p_fData[1] = new Float(2.3);
      p_fData[2] = new Float(0.74);
      p_fData[3] = new Float(5.88);
      ValidationHelpers.makeSureAllNonNegative(p_fData, sName, p_bAppliesTo);

      //Case - all are non-negative and none are applied
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = false;
      }
      ValidationHelpers.makeSureAllNonNegative(p_fData, sName, p_bAppliesTo);

      //Case - some are applied and some negatives exist in unapplied
      //buckets
      p_bAppliesTo[0] = false;
      p_fData[0] = new Float( -0.3);
      ValidationHelpers.makeSureAllNonNegative(p_fData, sName, p_bAppliesTo);

    }
    catch (ModelException oErr) {
      fail("makeSureAllNonNegative testing failed with message " +
           oErr.getMessage());
    }
    System.out.println(
        "makeSureAllNonNegative normal processing testing succeeded.");

    //Exception processing
    try {

      //Catches negative values
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_fData[0] = new Float(0);
      p_fData[1] = new Float( -2.3);
      p_fData[2] = new Float(0.74);
      p_fData[3] = new Float(5.88);

      ValidationHelpers.makeSureAllNonNegative(p_fData, sName, p_bAppliesTo);
      fail("makeSureAllNonNegative normal failed to catch invalid values.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "makeSureAllNonNegative exception processing testing succeeded.");
  }

  /**
   * Testing for ValidationHelpers.makeSureAllNonZero in ModelVectors
   */
  public void makeSureAllNonZero() {
    //Normal processing
    int iNumElements = 4, i;
    ModelVector p_oData = new ModelVector(
        "ModelVector testing for ValidationHelpers.makeSureAllNonZero", "", "", iNumElements,
        ModelVector.INTEGER);
    boolean[] p_bAppliesTo = new boolean[iNumElements];

    try {

      //Case - all are non-zero and are applied to all
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_oData.getValue().add(0, new Integer(5));
      p_oData.getValue().add(1, new Integer(2));
      p_oData.getValue().add(2, new Integer(-74));
      p_oData.getValue().add(3, new Integer(588));
      ValidationHelpers.makeSureAllNonZero(p_oData, p_bAppliesTo);

      //Case - all are non-zero and none are applied
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = false;
      }
      ValidationHelpers.makeSureAllNonZero(p_oData, p_bAppliesTo);

      //Case - some are applied and some zeroes exist in unapplied
      //buckets
      p_bAppliesTo[0] = false;
      p_oData.getValue().remove(0);
      p_oData.getValue().add(0, new Integer( 0 ));
      ValidationHelpers.makeSureAllNonZero(p_oData, p_bAppliesTo);

    }
    catch (ModelException oErr) {
      fail("ValidationHelpers.makeSureAllNonZero testing failed with message " +
           oErr.getMessage());
    }
    System.out.println(
        "ValidationHelpers.makeSureAllNonZero normal processing testing succeeded.");

    //Exception processing
    try {

      //Catches zero values
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_oData.getValue().clear();
      p_oData.getValue().add(0, new Integer(2));
      p_oData.getValue().add(1, new Integer(0));
      p_oData.getValue().add(2, new Integer(74));
      p_oData.getValue().add(3, new Integer( -588));

      ValidationHelpers.makeSureAllNonZero(p_oData, p_bAppliesTo);
      fail("ValidationHelpers.makeSureAllNonZero normal failed to catch invalid values.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "ValidationHelpers.makeSureAllNonZero exception processing testing succeeded.");

  }

  /**
   * Testing to make sure there are no negative values in an integer array.
   */
  public void testmakeSureAllNonNegative1() {
    int iNumElements = 4, i;
    Integer[] p_iData = new Integer[iNumElements];
    String sName = "Integer test";
    boolean[] p_bAppliesTo = new boolean[iNumElements];

    try {

      //Case - all are non-negative and are applied to all
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_iData[0] = new Integer(0);
      p_iData[1] = new Integer(2);
      p_iData[2] = new Integer(74);
      p_iData[3] = new Integer(588);
      ValidationHelpers.makeSureAllNonNegative(p_iData, sName, p_bAppliesTo);

      //Case - all are non-negative and none are applied
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = false;
      }
      ValidationHelpers.makeSureAllNonNegative(p_iData, sName, p_bAppliesTo);

      //Case - some are applied and some negatives exist in unapplied
      //buckets
      p_bAppliesTo[0] = false;
      p_iData[0] = new Integer( -3);
      ValidationHelpers.makeSureAllNonNegative(p_iData, sName, p_bAppliesTo);

    }
    catch (ModelException oErr) {
      fail("makeSureAllNonNegative testing failed with message " +
           oErr.getMessage());
    }
    System.out.println(
        "makeSureAllNonNegative normal processing testing succeeded.");

    //Exception processing
    try {

      //Catches negative values
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_iData[0] = new Integer(0);
      p_iData[1] = new Integer( -2);
      p_iData[2] = new Integer(74);
      p_iData[3] = new Integer(588);

      ValidationHelpers.makeSureAllNonNegative(p_iData, sName, p_bAppliesTo);
      fail("makeSureAllNonNegative normal failed to catch invalid values.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "makeSureAllNonNegative exception processing testing succeeded.");

  }

  /**
   * Testing for non-negatives in ModelVectors
   */
  public void testmakeSureAllNonNegative2() {
    //Normal processing
    int iNumElements = 4, i;
    ModelVector p_oData = new ModelVector(
        "ModelVector testing for makeSureAllNonNegative", "", "", iNumElements,
        ModelVector.INTEGER);
    boolean[] p_bAppliesTo = new boolean[iNumElements];

    try {

      //Case - all are non-negative and are applied to all
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_oData.getValue().add(0, new Integer(0));
      p_oData.getValue().add(1, new Integer(2));
      p_oData.getValue().add(2, new Integer(74));
      p_oData.getValue().add(3, new Integer(588));
      ValidationHelpers.makeSureAllNonNegative(p_oData, p_bAppliesTo);

      //Case - all are non-negative and none are applied
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = false;
      }
      ValidationHelpers.makeSureAllNonNegative(p_oData, p_bAppliesTo);

      //Case - some are applied and some negatives exist in unapplied
      //buckets
      p_bAppliesTo[0] = false;
      p_oData.getValue().remove(0);
      p_oData.getValue().add(0, new Integer( -3));
      ValidationHelpers.makeSureAllNonNegative(p_oData, p_bAppliesTo);

    }
    catch (ModelException oErr) {
      fail("makeSureAllNonNegative testing failed with message " +
           oErr.getMessage());
    }
    System.out.println(
        "makeSureAllNonNegative normal processing testing succeeded.");

    //Exception processing
    try {

      //Catches negative values
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_oData.getValue().clear();
      p_oData.getValue().add(0, new Integer(0));
      p_oData.getValue().add(1, new Integer(2));
      p_oData.getValue().add(2, new Integer(74));
      p_oData.getValue().add(3, new Integer( -588));

      ValidationHelpers.makeSureAllNonNegative(p_oData, p_bAppliesTo);
      fail("makeSureAllNonNegative normal failed to catch invalid values.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "makeSureAllNonNegative exception processing testing succeeded.");

  }

  /**
   * Testing to make sure all values in a Float array are greater than 0.
   */
  public void makeSureAllPositive() {
    int iNumElements = 4, i;
    Float[] p_fData = new Float[iNumElements];
    String sName = "Float Test";
    boolean[] p_bAppliesTo = new boolean[iNumElements];

    try {

      //Case - all are positive and are applied to all
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_fData[0] = new Float(0.66);
      p_fData[1] = new Float(2.3);
      p_fData[2] = new Float(0.74);
      p_fData[3] = new Float(5.88);
      ValidationHelpers.makeSureAllPositive(p_fData, sName, p_bAppliesTo);

      //Case - all are positive and none are applied
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = false;
      }
      ValidationHelpers.makeSureAllPositive(p_fData, sName, p_bAppliesTo);

      //Case - some are applied and some negatives exist in unapplied
      //buckets
      p_bAppliesTo[0] = false;
      p_fData[0] = new Float( -0.3);
      ValidationHelpers.makeSureAllPositive(p_fData, sName, p_bAppliesTo);

    }
    catch (ModelException oErr) {
      fail("ValidationHelpers.makeSureAllPositive testing failed with message " +
           oErr.getMessage());
    }
    System.out.println(
        "ValidationHelpers.makeSureAllPositive normal processing testing succeeded.");

    //Exception processing
    try {

      //Catches negative values
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_fData[0] = new Float(0.66);
      p_fData[1] = new Float( -2.3);
      p_fData[2] = new Float(0.74);
      p_fData[3] = new Float(5.88);

      ValidationHelpers.makeSureAllPositive(p_fData, sName, p_bAppliesTo);
      fail("ValidationHelpers.makeSureAllPositive normal failed to catch invalid values.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {

      //Catches 0 values
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_fData[0] = new Float(0.66);
      p_fData[1] = new Float(2.3);
      p_fData[2] = new Float(0);
      p_fData[3] = new Float(5.88);

      ValidationHelpers.makeSureAllPositive(p_fData, sName, p_bAppliesTo);
      fail("ValidationHelpers.makeSureAllPositive normal failed to catch invalid values.");
    }
    catch (ModelException oErr) {
      ;
    }
    System.out.println(
        "ValidationHelpers.makeSureAllPositive exception processing testing succeeded.");
  }

  /**
   * Testing to make sure all values in a vector are between two bounds.
   */
  public void testmakeSureAllAreBounded() {
    //Normal processing
    int iNumElements = 4, i;
    ModelVector p_oData = new ModelVector(
        "ModelVector testing for makeSureAllAreBounded", "", "", iNumElements,
        ModelVector.INTEGER);
    boolean[] p_bAppliesTo = new boolean[iNumElements];

    try {

      //Case - all are between the bounds and are applied to all
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_oData.getValue().add(0, new Integer(0));
      p_oData.getValue().add(1, new Integer(2));
      p_oData.getValue().add(2, new Integer(74));
      p_oData.getValue().add(3, new Integer(100));
      ValidationHelpers.makeSureAllAreBounded(p_oData, p_bAppliesTo, 0, 100);

      //Case - all are between the bounds and none are applied
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = false;
      }
      ValidationHelpers.makeSureAllPositive(p_oData, p_bAppliesTo);

      //Case - some are applied and some out-of-bounds exist in unapplied
      //buckets
      p_bAppliesTo[0] = false;
      p_oData.getValue().remove(0);
      p_oData.getValue().add(0, new Integer( -3));
      ValidationHelpers.makeSureAllNonNegative(p_oData, p_bAppliesTo);

    }
    catch (ModelException oErr) {
      fail("makeSureAllAreBounded testing failed with message " +
           oErr.getMessage());
    }
    System.out.println(
        "makeSureAllAreBounded normal processing testing succeeded.");

    //Exception processing
    try {

      //Catches below-lower-bound values
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_oData.getValue().clear();
      p_oData.getValue().add(0, new Integer(1));
      p_oData.getValue().add(1, new Integer(2));
      p_oData.getValue().add(2, new Integer(74));
      p_oData.getValue().add(3, new Integer( -588));

      ValidationHelpers.makeSureAllPositive(p_oData, p_bAppliesTo);
      fail("makeSureAllAreBounded failed to catch invalid values.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {

      //Catches above-upper-bound values
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_oData.getValue().clear();
      p_oData.getValue().add(0, new Integer(0));
      p_oData.getValue().add(1, new Integer(2));
      p_oData.getValue().add(2, new Integer(74));
      p_oData.getValue().add(3, new Integer(588));

      ValidationHelpers.makeSureAllPositive(p_oData, p_bAppliesTo);
      fail("makeSureAllAreBounded normal failed to catch invalid values.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "makeSureAllAreBounded exception processing testing succeeded.");
  }

  /**
   * Tests to make sure all values in an Integer array are greater than 0.
   */
  public void makeSureAllPositive1() {
    int iNumElements = 4, i;
    Integer[] p_iData = new Integer[iNumElements];
    String sName = "Integer Test";
    boolean[] p_bAppliesTo = new boolean[iNumElements];

    try {

      //Case - all are positive and are applied to all
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_iData[0] = new Integer(66);
      p_iData[1] = new Integer(2);
      p_iData[2] = new Integer(74);
      p_iData[3] = new Integer(588);
      ValidationHelpers.makeSureAllPositive(p_iData, sName, p_bAppliesTo);

      //Case - all are positive and none are applied
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = false;
      }
      ValidationHelpers.makeSureAllPositive(p_iData, sName, p_bAppliesTo);

      //Case - some are applied and some negatives exist in unapplied
      //buckets
      p_bAppliesTo[0] = false;
      p_iData[0] = new Integer( -3);
      ValidationHelpers.makeSureAllPositive(p_iData, sName, p_bAppliesTo);

    }
    catch (ModelException oErr) {
      fail("ValidationHelpers.makeSureAllPositive testing failed with message " +
           oErr.getMessage());
    }
    System.out.println(
        "ValidationHelpers.makeSureAllPositive normal processing testing succeeded.");

    //Exception processing
    try {

      //Catches negative values
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_iData[0] = new Integer( -66);
      p_iData[1] = new Integer(2);
      p_iData[2] = new Integer(74);
      p_iData[3] = new Integer(588);

      ValidationHelpers.makeSureAllPositive(p_iData, sName, p_bAppliesTo);
      fail("ValidationHelpers.makeSureAllPositive normal failed to catch invalid values.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {

      //Catches 0 values
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_iData[0] = new Integer(66);
      p_iData[1] = new Integer(2);
      p_iData[2] = new Integer(74);
      p_iData[3] = new Integer(0);

      ValidationHelpers.makeSureAllPositive(p_iData, sName, p_bAppliesTo);
      fail("ValidationHelpers.makeSureAllPositive normal failed to catch invalid values.");
    }
    catch (ModelException oErr) {
      ;
    }
    System.out.println(
        "ValidationHelpers.makeSureAllPositive exception processing testing succeeded.");

  }

  /**
   * Tests ValidationHelpers.makeSureAllPositive methods.
   */
  public void makeSureAllPositive2() {
    //Normal processing
    int iNumElements = 4, i;
    ModelVector p_oData = new ModelVector(
        "ModelVector testing for ValidationHelpers.makeSureAllPositive", "", "", iNumElements,
        ModelVector.INTEGER);
    boolean[] p_bAppliesTo = new boolean[iNumElements];

    try {

      //Case - all are non-negative and are applied to all
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_oData.getValue().add(0, new Integer(1));
      p_oData.getValue().add(1, new Integer(2));
      p_oData.getValue().add(2, new Integer(74));
      p_oData.getValue().add(3, new Integer(588));
      ValidationHelpers.makeSureAllPositive(p_oData, p_bAppliesTo);

      //Case - all are non-negative and none are applied
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = false;
      }
      ValidationHelpers.makeSureAllPositive(p_oData, p_bAppliesTo);

      //Case - some are applied and some negatives exist in unapplied
      //buckets
      p_bAppliesTo[0] = false;
      p_oData.getValue().remove(0);
      p_oData.getValue().add(0, new Integer( -3));
      ValidationHelpers.makeSureAllNonNegative(p_oData, p_bAppliesTo);

    }
    catch (ModelException oErr) {
      fail("ValidationHelpers.makeSureAllPositive testing failed with message " +
           oErr.getMessage());
    }
    System.out.println(
        "ValidationHelpers.makeSureAllPositive normal processing testing succeeded.");

    //Exception processing
    try {

      //Catches negative values
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_oData.getValue().clear();
      p_oData.getValue().add(0, new Integer(1));
      p_oData.getValue().add(1, new Integer(2));
      p_oData.getValue().add(2, new Integer(74));
      p_oData.getValue().add(3, new Integer( -588));

      ValidationHelpers.makeSureAllPositive(p_oData, p_bAppliesTo);
      fail("ValidationHelpers.makeSureAllPositive normal failed to catch invalid values.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {

      //Catches 0 values
      for (i = 0; i < iNumElements; i++) {
        p_bAppliesTo[i] = true;
      }
      p_oData.getValue().clear();
      p_oData.getValue().add(0, new Integer(0));
      p_oData.getValue().add(1, new Integer(2));
      p_oData.getValue().add(2, new Integer(74));
      p_oData.getValue().add(3, new Integer(588));

      ValidationHelpers.makeSureAllPositive(p_oData, p_bAppliesTo);
      fail("ValidationHelpers.makeSureAllPositive normal failed to catch invalid values.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "ValidationHelpers.makeSureAllPositive exception processing testing succeeded.");

  }

  /**
   * Tests to make sure a ModelFloat value is a proportion.
   */
  public void testmakeSureIsProportion() {
    ModelFloat fValue = new ModelFloat( (float) 0.45,
                                       "ModelFloat proportion test", "");
    //Normal processing
    try {
      ValidationHelpers.makeSureIsProportion(fValue);
    }
    catch (ModelException oErr) {
      fail("makeSureIsProportion testing failed with message " +
           oErr.getMessage());
    }
    System.out.println(
        "makeSureIsProportion normal processing testing succeeded.");

    //Exception processing
    try {
      fValue.setValue( (float) - 0.23);
      ValidationHelpers.makeSureIsProportion(fValue);
      fail("makeSureIsProportion failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }

    try {
      fValue.setValue( (float) 23);
      ValidationHelpers.makeSureIsProportion(fValue);
      fail("makeSureIsProportion failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "makeSureIsProportion exception processing testing succeeded.");
  }

  /**
   * Tests to make sure a ModelFloat value is greater than a given value
   */
  public void testmakeSureGreaterThanFloat() {
    ModelFloat fValue = new ModelFloat( (float) 0.45,
                                       "ModelFloat makeSureGreaterThan test",
                                       "");
    //Normal processing
    try {
      ValidationHelpers.makeSureGreaterThan(fValue, 0);
    }
    catch (ModelException oErr) {
      fail("makeSureGreaterThanFloat testing failed with message " +
           oErr.getMessage());
    }
    System.out.println(
        "makeSureGreaterThanFloat normal processing testing succeeded.");

    //Exception processing - less than the value
    try {
      fValue.setValue( (float) - 0.23);
      ValidationHelpers.makeSureGreaterThan(fValue, 0);
      fail("makeSureGreaterThanFloat failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }
    
    //Exception processing - equal to the value
    try {
      fValue.setValue( (float) 0);
      ValidationHelpers.makeSureGreaterThan(fValue, 0);
      fail("makeSureGreaterThanFloat failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "makeSureGreaterThanFloat exception processing testing succeeded.");
  }
  
  /**
   * Tests to make sure a ModelInt value is greater than a given value
   */
  public void testmakeSureGreaterThanInt() {
    ModelInt iValue = new ModelInt( 2,
                                       "ModelInt makeSureGreaterThan test",
                                       "");
    //Normal processing
    try {
      ValidationHelpers.makeSureGreaterThan(iValue, -1);
    }
    catch (ModelException oErr) {
      fail("makeSureGreaterThanInt testing failed with message " +
           oErr.getMessage());
    }
    System.out.println(
        "makeSureGreaterThanInt normal processing testing succeeded.");

    //Exception processing - less than the value
    try {
      iValue.setValue(-5);
      ValidationHelpers.makeSureGreaterThan(iValue, -1);
      fail("makeSureGreaterThanInt failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }
    
    //Exception processing - equal to the value
    try {
      iValue.setValue(-1);
      ValidationHelpers.makeSureGreaterThan(iValue, -1);
      fail("makeSureGreaterThanInt failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "makeSureGreaterThanInt exception processing testing succeeded.");
  }
  
  /**
   * Tests to make sure a ModelFloat value is greater than or equal to a 
   * given value
   */
  public void testmakeSureGreaterThanEqualToFloat() {
    ModelFloat fValue = new ModelFloat( (float) 0.45,
                                       "ModelFloat makeSureGreaterThanEqualTo" +
                                       " test",
                                       "");
    //Normal processing
    try {
      ValidationHelpers.makeSureGreaterThanEqualTo(fValue, 0);
    }
    catch (ModelException oErr) {
      fail("makeSureGreaterThanEqualToFloat testing failed with message " +
           oErr.getMessage());
    }
    
    //Normal processing - equal to
    try {
    	fValue.setValue( (float) 0);
      ValidationHelpers.makeSureGreaterThanEqualTo(fValue, 0);
    }
    catch (ModelException oErr) {
      fail("makeSureGreaterThanEqualToFloat testing failed with message " +
           oErr.getMessage());
    }
    
    System.out.println(
        "makeSureGreaterThanEqualToFloat normal processing testing succeeded.");

    //Exception processing - less than the value
    try {
      fValue.setValue( (float) - 0.23);
      ValidationHelpers.makeSureGreaterThanEqualTo(fValue, 0);
      fail("makeSureGreaterThanEqualToFloat failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "makeSureGreaterThanEqualToFloat exception processing testing succeeded.");
  }
  
  /**
   * Tests to make sure a ModelInt value is greater than or equal to a given 
   * value
   */
  public void testmakeSureGreaterThanEqualToInt() {
    ModelInt iValue = new ModelInt( 2,
                                       "ModelInt makeSureGreaterThanEqualTo test",
                                       "");
    //Normal processing
    try {
      ValidationHelpers.makeSureGreaterThanEqualTo(iValue, -1);
    }
    catch (ModelException oErr) {
      fail("makeSureGreaterThanEqualToInt testing failed with message " +
           oErr.getMessage());
    }
    
    //Normal processing - equal to
    try {
    	iValue.setValue(-1);
      ValidationHelpers.makeSureGreaterThanEqualTo(iValue, -1);
    }
    catch (ModelException oErr) {
      fail("makeSureGreaterThanEqualToInt testing failed with message " +
           oErr.getMessage());
    }
    
    System.out.println(
        "makeSureGreaterThanEqualToInt normal processing testing succeeded.");

    //Exception processing - less than the value
    try {
      iValue.setValue(-5);
      ValidationHelpers.makeSureGreaterThanEqualTo(iValue, -1);
      fail("makeSureGreaterThanEqualToInt failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }
    
    System.out.println(
        "makeSureGreaterThanEqualToInt exception processing testing succeeded.");
  }
  
  /**
   * Tests to make sure a ModelFloat value is less than a given value
   */
  public void testmakeSureLessThanFloat() {
    ModelFloat fValue = new ModelFloat( (float) -0.45,
                                       "ModelFloat makeSureLessThan test",
                                       "");
    //Normal processing
    try {
      ValidationHelpers.makeSureLessThan(fValue, 0);
    }
    catch (ModelException oErr) {
      fail("makeSureLessThanFloat testing failed with message " +
           oErr.getMessage());
    }
    System.out.println(
        "makeSureLessThanFloat normal processing testing succeeded.");

    //Exception processing - greater than the value
    try {
      fValue.setValue( (float) 0.23);
      ValidationHelpers.makeSureLessThan(fValue, 0);
      fail("makeSureLessThanFloat failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }
    
    //Exception processing - equal to the value
    try {
      fValue.setValue( (float) 0);
      ValidationHelpers.makeSureLessThan(fValue, 0);
      fail("makeSureLessThanFloat failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "makeSureLessThanFloat exception processing testing succeeded.");
  }
  
  /**
   * Tests to make sure a ModelInt value is less than a given value
   */
  public void testmakeSureLessThanInt() {
    ModelInt iValue = new ModelInt( -2,
                                       "ModelInt makeSureLessThan test",
                                       "");
    //Normal processing
    try {
      ValidationHelpers.makeSureLessThan(iValue, -1);
    }
    catch (ModelException oErr) {
      fail("makeSureLessThanInt testing failed with message " +
           oErr.getMessage());
    }
    System.out.println(
        "makeSureLessThanInt normal processing testing succeeded.");

    //Exception processing - greater than the value
    try {
      iValue.setValue(5);
      ValidationHelpers.makeSureLessThan(iValue, -1);
      fail("makeSureLessThanInt failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }
    
    //Exception processing - equal to the value
    try {
      iValue.setValue(-1);
      ValidationHelpers.makeSureLessThan(iValue, -1);
      fail("makeSureLessThanInt failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "makeSureLessThanInt exception processing testing succeeded.");
  }
  
  /**
   * Tests to make sure a ModelFloat value is less than or equal to a 
   * given value
   */
  public void testmakeSureLessThanEqualToFloat() {
    ModelFloat fValue = new ModelFloat( (float) -0.45,
                                       "ModelFloat makeSureLessThanEqualTo" +
                                       " test",
                                       "");
    //Normal processing
    try {
      ValidationHelpers.makeSureLessThanEqualTo(fValue, 0);
    }
    catch (ModelException oErr) {
      fail("makeSureLessThanEqualToFloat testing failed with message " +
           oErr.getMessage());
    }
    
    //Normal processing - equal to
    try {
    	fValue.setValue( (float) 0);
      ValidationHelpers.makeSureLessThanEqualTo(fValue, 0);
    }
    catch (ModelException oErr) {
      fail("makeSureLessThanEqualToFloat testing failed with message " +
           oErr.getMessage());
    }
    
    System.out.println(
        "makeSureLessThanEqualToFloat normal processing testing succeeded.");

    //Exception processing - greater than the value
    try {
      fValue.setValue( (float) 0.23);
      ValidationHelpers.makeSureLessThanEqualTo(fValue, 0);
      fail("makeSureLessThanEqualToFloat failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "makeSureLessThanEqualToFloat exception processing testing succeeded.");
  }
  
  /**
   * Tests to make sure a ModelInt value is less than or equal to a given 
   * value
   */
  public void testmakeSureLessThanEqualToInt() {
    ModelInt iValue = new ModelInt( -2,
                                       "ModelInt makeSureLessThanEqualTo test",
                                       "");
    //Normal processing
    try {
      ValidationHelpers.makeSureLessThanEqualTo(iValue, -1);
    }
    catch (ModelException oErr) {
      fail("makeSureLessThanEqualToInt testing failed with message " +
           oErr.getMessage());
    }
    
    //Normal processing - equal to
    try {
    	iValue.setValue(-1);
      ValidationHelpers.makeSureLessThanEqualTo(iValue, -1);
    }
    catch (ModelException oErr) {
      fail("makeSureLessThanEqualToInt testing failed with message " +
           oErr.getMessage());
    }
    
    System.out.println(
        "makeSureLessThanEqualToInt normal processing testing succeeded.");

    //Exception processing - greater than the value
    try {
      iValue.setValue(5);
      ValidationHelpers.makeSureLessThanEqualTo(iValue, -1);
      fail("makeSureLessThanEqualToInt failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }
    
    System.out.println(
        "makeSureLessThanEqualToInt exception processing testing succeeded.");
  }
  
  /**
   * Tests to make sure a ModelFloat value is not equal to another value
   */
  public void testmakeSureNotEqualToFloat() {
    ModelFloat fValue = new ModelFloat( (float) 0.45,
                                       "ModelFloat makeSureNotEqualTo test",
                                       "");
    //Normal processing
    try {
      ValidationHelpers.makeSureNotEqualTo(fValue, 0);
    }
    catch (ModelException oErr) {
      fail("makeSureNotEqualToFloat testing failed with message " +
           oErr.getMessage());
    }

    fValue.setValue((float)-0.2);
    try {
      ValidationHelpers.makeSureNotEqualTo(fValue, 0);
    }
    catch (ModelException oErr) {
      fail("makeSureNotEqualToFloat testing failed with message " +
           oErr.getMessage());
    }

    System.out.println(
        "makeSureNotEqualToFloat normal processing testing succeeded.");

    //Exception processing
    try {
      fValue.setValue( (float) 0);
      ValidationHelpers.makeSureNotEqualTo(fValue, 0);
      fail("makeSureNotEqualToFloat failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "makeSureNotEqualToFloat exception processing testing succeeded.");
  }
  
  /**
   * Tests to make sure a ModelInt value is not equal to another value
   */
  public void testmakeSureNotEqualToInt() {
    ModelInt iValue = new ModelInt( 4, "ModelInt makeSureNotEqualTo test",
                                       "");
    //Normal processing
    try {
      ValidationHelpers.makeSureNotEqualTo(iValue, 0);
    }
    catch (ModelException oErr) {
      fail("makeSureNotEqualToInt testing failed with message " +
           oErr.getMessage());
    }

    iValue.setValue(-2);
    try {
      ValidationHelpers.makeSureNotEqualTo(iValue, 0);
    }
    catch (ModelException oErr) {
      fail("makeSureNotEqualToInt testing failed with message " +
           oErr.getMessage());
    }

    System.out.println(
        "makeSureNotEqualToInt normal processing testing succeeded.");

    //Exception processing
    try {
    	iValue.setValue( 0);
      ValidationHelpers.makeSureNotEqualTo(iValue, 0);
      fail("makeSureNotEqualToInt failed to catch invalid value.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println(
        "makeSureNotEqualToInt exception processing testing succeeded.");
  }

  
  
}
