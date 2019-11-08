package sortie.data.simpletypes;

import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;

public class ModelVectorTest extends ModelTestCase {

  public void testClone() {
    int num_species = 5;

    //************************
    //Test integer vector
    ModelVector ints = new ModelVector("int vector", "intXMLTag", 
        "intChildTag", num_species, ModelVector.INTEGER, false);
    
    Integer[] intdata = new Integer[] {null, Integer.valueOf(1), Integer.valueOf(2), 
        Integer.valueOf(3), Integer.valueOf(4)};
    boolean[] applies = new boolean[] {false, true, true, true, true};
    
    Behavior.setVectorValues(ints, intdata, applies);
    
    ModelVector intclone = (ModelVector)ints.clone();
    assertEquals(ints.getChildXMLTag(), intclone.getChildXMLTag());
    assertEquals(ints.getDataType(), intclone.getDataType());
    assertEquals(ints.getDescriptor(), intclone.getDescriptor());
    assertEquals(ints.getIsSpeciesSpecific(), intclone.getIsSpeciesSpecific());
    assertEquals(ints.getMustApplyToAllSpecies(), intclone.getMustApplyToAllSpecies());
    assertEquals(ints.getXMLTag(), intclone.getXMLTag());
    
    assertNull(intclone.getValue().get(0));
    assertEquals(1, ((Integer)intclone.getValue().get(1)).intValue());
    assertEquals(2, ((Integer)intclone.getValue().get(2)).intValue());
    assertEquals(3, ((Integer)intclone.getValue().get(3)).intValue());
    assertEquals(4, ((Integer)intclone.getValue().get(4)).intValue());
    
    //Change original and verify it doesn't change the clone
    intdata = new Integer[] {Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), 
        Integer.valueOf(9), Integer.valueOf(10)};
    Behavior.setVectorValues(ints, intdata);
    assertEquals(6, ((Integer)ints.getValue().get(0)).intValue());
    assertEquals(7, ((Integer)ints.getValue().get(1)).intValue());
    assertEquals(8, ((Integer)ints.getValue().get(2)).intValue());
    assertEquals(9, ((Integer)ints.getValue().get(3)).intValue());
    assertEquals(10, ((Integer)ints.getValue().get(4)).intValue());
    
    assertNull(intclone.getValue().get(0));
    assertEquals(1, ((Integer)intclone.getValue().get(1)).intValue());
    assertEquals(2, ((Integer)intclone.getValue().get(2)).intValue());
    assertEquals(3, ((Integer)intclone.getValue().get(3)).intValue());
    assertEquals(4, ((Integer)intclone.getValue().get(4)).intValue());
    
    //************************
    //Test float vector
    ModelVector floats = new ModelVector("float vector", "floatXMLTag", 
        "floatChildTag", num_species, ModelVector.FLOAT, false);
    
    Float[] floatdata = new Float[] {null, Float.valueOf((float)1.3), Float.valueOf((float)5.6), 
        Float.valueOf((float)500), Float.valueOf((float)66)};
        
    Behavior.setVectorValues(floats, floatdata, applies);
    
    ModelVector floatclone = (ModelVector)floats.clone();
    assertEquals(floats.getChildXMLTag(), floatclone.getChildXMLTag());
    assertEquals(floats.getDataType(), floatclone.getDataType());
    assertEquals(floats.getDescriptor(), floatclone.getDescriptor());
    assertEquals(floats.getIsSpeciesSpecific(), floatclone.getIsSpeciesSpecific());
    assertEquals(floats.getMustApplyToAllSpecies(), floatclone.getMustApplyToAllSpecies());
    assertEquals(floats.getXMLTag(), floatclone.getXMLTag());
    
    assertNull(floatclone.getValue().get(0));
    assertEquals(1.3, ((Float)floatclone.getValue().get(1)).floatValue(), 0.00001);
    assertEquals(5.6, ((Float)floatclone.getValue().get(2)).floatValue(), 0.00001);
    assertEquals(500, ((Float)floatclone.getValue().get(3)).floatValue(), 0.00001);
    assertEquals(66, ((Float)floatclone.getValue().get(4)).floatValue(), 0.00001);
    
    //Change original and verify it doesn't change the clone
    floatdata = new Float[] {Float.valueOf((float)5354), Float.valueOf((float)2.3), Float.valueOf((float)6.6), 
        Float.valueOf((float)23), Float.valueOf((float)76)};
    Behavior.setVectorValues(floats, floatdata);
    assertEquals(5354, ((Float)floats.getValue().get(0)).floatValue(), 0.00001);
    assertEquals(2.3, ((Float)floats.getValue().get(1)).floatValue(), 0.00001);
    assertEquals(6.6, ((Float)floats.getValue().get(2)).floatValue(), 0.00001);
    assertEquals(23, ((Float)floats.getValue().get(3)).floatValue(), 0.00001);
    assertEquals(76, ((Float)floats.getValue().get(4)).floatValue(), 0.00001);
    
    assertNull(floatclone.getValue().get(0));
    assertEquals(1.3, ((Float)floatclone.getValue().get(1)).floatValue(), 0.00001);
    assertEquals(5.6, ((Float)floatclone.getValue().get(2)).floatValue(), 0.00001);
    assertEquals(500, ((Float)floatclone.getValue().get(3)).floatValue(), 0.00001);
    assertEquals(66, ((Float)floatclone.getValue().get(4)).floatValue(), 0.00001);
    
    //************************
    //Test model enum vector
    ModelVector enums = new ModelVector("enum vector", "enumXMLTag", 
        "enumChildTag", num_species, ModelVector.MODEL_ENUM, false);
    ModelEnum obj;
    for (int i = 0; i < num_species; i++) {
      obj = new ModelEnum(new int[]{0, 1, 2, 3}, new String[]{"0", "1", "2", "3"}, "tag", "tag");
      enums.getValue().add(obj);
    }
    
    ArrayList<String> enumdata = new ArrayList<String>(5);
    enumdata.add("3");
    enumdata.add("0");
    enumdata.add("1");
    enumdata.add("2");
    enumdata.add("3");
    try {
      Behavior.setVectorValues(enums, enumdata, new boolean[] {true, true, true, true, true});
    } catch (ModelException e) {fail(e.getMessage());}
    
    ModelVector enumclone = (ModelVector)enums.clone();
    assertEquals(enums.getChildXMLTag(), enumclone.getChildXMLTag());
    assertEquals(enums.getDataType(), enumclone.getDataType());
    assertEquals(enums.getDescriptor(), enumclone.getDescriptor());
    assertEquals(enums.getIsSpeciesSpecific(), enumclone.getIsSpeciesSpecific());
    assertEquals(enums.getMustApplyToAllSpecies(), enumclone.getMustApplyToAllSpecies());
    assertEquals(enums.getXMLTag(), enumclone.getXMLTag());
    
    obj = (ModelEnum) enumclone.getValue().get(0);
    assertEquals(3, obj.getValue());
    obj = (ModelEnum) enumclone.getValue().get(1);
    assertEquals(0, obj.getValue());
    obj = (ModelEnum) enumclone.getValue().get(2);
    assertEquals(1, obj.getValue());
    obj = (ModelEnum) enumclone.getValue().get(3);
    assertEquals(2, obj.getValue());
    obj = (ModelEnum) enumclone.getValue().get(4);
    assertEquals(3, obj.getValue());
    
    //Change original and verify it doesn't change the clone
    enumdata = new ArrayList<String>(5);
    enumdata.add("3");
    enumdata.add("2");
    enumdata.add("1");
    enumdata.add("0");
    enumdata.add("0");
    try {
      Behavior.setVectorValues(enums, enumdata, new boolean[] {true, true, true, true, true});
    } catch (ModelException e) {fail(e.getMessage());}
    obj = (ModelEnum) enums.getValue().get(0);
    assertEquals(3, obj.getValue());
    obj = (ModelEnum) enums.getValue().get(1);
    assertEquals(2, obj.getValue());
    obj = (ModelEnum) enums.getValue().get(2);
    assertEquals(1, obj.getValue());
    obj = (ModelEnum) enums.getValue().get(3);
    assertEquals(0, obj.getValue());
    obj = (ModelEnum) enums.getValue().get(4);
    assertEquals(0, obj.getValue());
    
    obj = (ModelEnum) enumclone.getValue().get(0);
    assertEquals(3, obj.getValue());
    obj = (ModelEnum) enumclone.getValue().get(1);
    assertEquals(0, obj.getValue());
    obj = (ModelEnum) enumclone.getValue().get(2);
    assertEquals(1, obj.getValue());
    obj = (ModelEnum) enumclone.getValue().get(3);
    assertEquals(2, obj.getValue());
    obj = (ModelEnum) enumclone.getValue().get(4);
    assertEquals(3, obj.getValue());
    
    //************************
    //Test string vector
    ModelVector strings = new ModelVector("string vector", "stringXMLTag", 
        "stringChildTag", num_species, ModelVector.STRING, false);
    
    String[] stringdata = new String[] {null, "zero", "one", "two", "three"};
        
    Behavior.setVectorValues(strings, stringdata, applies);
    
    ModelVector stringclone = (ModelVector)strings.clone();
    assertEquals(strings.getChildXMLTag(), stringclone.getChildXMLTag());
    assertEquals(strings.getDataType(), stringclone.getDataType());
    assertEquals(strings.getDescriptor(), stringclone.getDescriptor());
    assertEquals(strings.getIsSpeciesSpecific(), stringclone.getIsSpeciesSpecific());
    assertEquals(strings.getMustApplyToAllSpecies(), stringclone.getMustApplyToAllSpecies());
    assertEquals(strings.getXMLTag(), stringclone.getXMLTag());
    
    assertNull(stringclone.getValue().get(0));
    assertEquals("zero", stringclone.getValue().get(1).toString());
    assertEquals("one", stringclone.getValue().get(2).toString());
    assertEquals("two", stringclone.getValue().get(3).toString());
    assertEquals("three", stringclone.getValue().get(4).toString());
    
    //Change original and verify it doesn't change the clone
    stringdata = new String[] {"pip", "pop", "pope", "pulp", "poop"}; 
        
    Behavior.setVectorValues(strings, stringdata);
    assertEquals("pip", strings.getValue().get(0).toString());
    assertEquals("pop", strings.getValue().get(1).toString());
    assertEquals("pope", strings.getValue().get(2).toString());
    assertEquals("pulp", strings.getValue().get(3).toString());
    assertEquals("poop", strings.getValue().get(4).toString());
    
    assertNull(stringclone.getValue().get(0));
    assertEquals("zero", stringclone.getValue().get(1).toString());
    assertEquals("one", stringclone.getValue().get(2).toString());
    assertEquals("two", stringclone.getValue().get(3).toString());
    assertEquals("three", stringclone.getValue().get(4).toString());
    
    //************************
    //Test double vector
    ModelVector doubles = new ModelVector("double vector", "doubleXMLTag", 
        "doubleChildTag", num_species, ModelVector.DOUBLE, false);
    
    Double[] doubledata = new Double[] {null, Double.valueOf(1.3), Double.valueOf(5.6), 
        Double.valueOf(500), Double.valueOf(66)};
        
    Behavior.setVectorValues(doubles, doubledata, applies);
    
    ModelVector doubleclone = (ModelVector)doubles.clone();
    assertEquals(doubles.getChildXMLTag(), doubleclone.getChildXMLTag());
    assertEquals(doubles.getDataType(), doubleclone.getDataType());
    assertEquals(doubles.getDescriptor(), doubleclone.getDescriptor());
    assertEquals(doubles.getIsSpeciesSpecific(), doubleclone.getIsSpeciesSpecific());
    assertEquals(doubles.getMustApplyToAllSpecies(), doubleclone.getMustApplyToAllSpecies());
    assertEquals(doubles.getXMLTag(), doubleclone.getXMLTag());
    
    assertNull(doubleclone.getValue().get(0));
    assertEquals(1.3, ((Double)doubleclone.getValue().get(1)).doubleValue(), 0.00001);
    assertEquals(5.6, ((Double)doubleclone.getValue().get(2)).doubleValue(), 0.00001);
    assertEquals(500, ((Double)doubleclone.getValue().get(3)).doubleValue(), 0.00001);
    assertEquals(66, ((Double)doubleclone.getValue().get(4)).doubleValue(), 0.00001);
    
    //Change original and verify it doesn't change the clone
    doubledata = new Double[] {Double.valueOf(5354), Double.valueOf(2.3), Double.valueOf(6.6), 
        Double.valueOf(23), Double.valueOf(76)};
    Behavior.setVectorValues(doubles, doubledata);
    assertEquals(5354, ((Double)doubles.getValue().get(0)).doubleValue(), 0.00001);
    assertEquals(2.3, ((Double)doubles.getValue().get(1)).doubleValue(), 0.00001);
    assertEquals(6.6, ((Double)doubles.getValue().get(2)).doubleValue(), 0.00001);
    assertEquals(23, ((Double)doubles.getValue().get(3)).doubleValue(), 0.00001);
    assertEquals(76, ((Double)doubles.getValue().get(4)).doubleValue(), 0.00001);
    
    assertNull(doubleclone.getValue().get(0));
    assertEquals(1.3, ((Double)doubleclone.getValue().get(1)).doubleValue(), 0.00001);
    assertEquals(5.6, ((Double)doubleclone.getValue().get(2)).doubleValue(), 0.00001);
    assertEquals(500, ((Double)doubleclone.getValue().get(3)).doubleValue(), 0.00001);
    assertEquals(66, ((Double)doubleclone.getValue().get(4)).doubleValue(), 0.00001);
  }
}
