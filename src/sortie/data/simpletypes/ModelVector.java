package sortie.data.simpletypes;

import java.util.ArrayList;

/**
* This class packages vector data with other pieces of information important
* to the model.  This allows a vector of data to self-describe to error
* messages and display windows, and to identify the data that belongs to it
* in XML files being read and written.
* <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
* <p>Company: Cary Institute of Ecosystem Studies</p>
* @author Lora E. Murphy
* @version 1.0
*
* <br>Edit history:
* <br>------------------
* <br>April 28, 2004: Submitted in beta version (LEM)
* <br>October 7, 2004:  Added the m_bMustApplyToAllSpecies member (LEM)
* <br>January 22, 2005: Added the m_bIsForSpecies member (LEM)
* <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
* <br>October 29, 2010: Add support for Float
* <br>May 31, 2013: Added cloning
*/

public class ModelVector
    extends ModelData {

  /**The data values*/
  private ArrayList<Object> mp_oData;
  /**This is the XML tag of the individual values*/
  private String m_sXMLChildTag;
  /**Data type desired by this ModelVector*/
  private int m_iDataType;
  /**Whether this is a set of species-specific values - not all vectors are*/
  private boolean m_bIsForSpecies = true;
  /**Whether or not this automatically applies to all species.  If false, then
   * which species values are contained in this vector is controlled by such
   * factors as what behaviors claim it and what species they apply to.*/
  private boolean m_bMustApplyToAllSpecies = false;

  /**This vector desires data of type Integer*/
  public final static int INTEGER = 0;

  /**This vector desires data of type Float */
  public final static int FLOAT = 1;

  /**This vector desires data of type ModelEnum*/
  public final static int MODEL_ENUM = 2;

  /**This vector desires data of type String*/
  public final static int STRING = 3;
  
  /**This vector desires data of type Float*/
  public final static int DOUBLE = 4;

  /**
   * Required overridden method.
   * @return null - this cannot be applied.
   */
  public String toString() {
    return null;
  }

  /**
   * Constructor
   * @param sDescriptor A descriptive name string for this value.  This will
   * appear in the data window and in error messages.
   * @param sXMLTag The XML tag of the whole vector
   * @param sXMLChildTag The XML tag of individual values within the vector
   * @param iSize Initial size of the vector.  0 is always safe (since vectors
   * grow to accommodate whatever data is placed in them).
   * @param iDesiredDataType Desired data type of this vector.
   */
  public ModelVector(String sDescriptor, String sXMLTag, String sXMLChildTag,
              int iSize,
              int iDesiredDataType) {
    this(sDescriptor, sXMLTag, sXMLChildTag, iSize, iDesiredDataType, false);
  }

  /**
   * Constructor
   * @param sDescriptor A descriptive name string for this value.  This will
   * appear in the data window and in error messages.
   * @param sXMLTag The XML tag of the whole vector
   * @param sXMLChildTag The XML tag of individual values within the vector
   * @param iSize Initial size of the vector.  0 is always safe (since vectors
   * grow to accommodate whatever data is placed in them).
   * @param iDesiredDataType Desired data type of this vector.
   * @param bMustApplyToAllSpecies Whether or not this vector automatically
   * applies to all species, overriding any other method of determining
   * which species it applies to.
   */
  public ModelVector(String sDescriptor, String sXMLTag, String sXMLChildTag,
              int iSize, int iDesiredDataType,
              boolean bMustApplyToAllSpecies) {
    super(sDescriptor, sXMLTag);
    mp_oData = new ArrayList<Object>(iSize);
    m_sXMLChildTag = sXMLChildTag;
    m_iDataType = iDesiredDataType;
    m_bMustApplyToAllSpecies = bMustApplyToAllSpecies;
  }

  /**
   * Gets the XML tag used to identify each piece of data in the vector.
   * @return The XML tag for this vector's data.
   */
  public String getChildXMLTag() {
    return m_sXMLChildTag;
  }

  /**
   * Gets whether this vector must apply to all tree species, regardless of
   * other factors controlling species.
   * @return boolean Whether this vector must apply to all species.
   */
  public boolean getMustApplyToAllSpecies() {
    return m_bMustApplyToAllSpecies;
  }

  /**
   * Gets whether this vector is for species-specific values.
   * @return boolean Whether this vector is for species-specific values.
   */
  public boolean getIsSpeciesSpecific() {
    return m_bIsForSpecies;
  }

  /**
   * Sets whether this vector is for species-specific values.
   * @param bIsSpeciesSpecific boolean Whether this vector is for species-
   * specific values.
   */
  public void setIsSpeciesSpecific(boolean bIsSpeciesSpecific) {
    m_bIsForSpecies = bIsSpeciesSpecific;
  }

  /**
   * Gets the Vector values.
   * @return The Vector values.
   */
  public ArrayList<Object> getValue() {
    return mp_oData;
  }

  /**
   * Gets the data type desired for this ModelVector.
   * @return The desired data type.
   */
  public int getDataType() {
    return m_iDataType;
  }

  /**
   * Clone method.
   */
  protected Object clone() {
    ModelVector clone = new ModelVector(m_sDescriptor, m_sXMLTag, 
        m_sXMLChildTag, mp_oData.size(), m_iDataType, m_bMustApplyToAllSpecies);
    
    clone.setIsSpeciesSpecific(m_bIsForSpecies);

    int i;
        
    //Clone the data
    if (INTEGER == m_iDataType) {
      Object obj;
      Integer dat; 
      for (i = 0; i < mp_oData.size(); i++) {
        obj = mp_oData.get(i);
        if (obj == null) dat = null;
        else dat = Integer.valueOf((Integer)obj);
        clone.mp_oData.add(i, dat);
      }
    } else if (FLOAT == m_iDataType) {
      Object obj;
      Float dat; 
      for (i = 0; i < mp_oData.size(); i++) {
        obj = mp_oData.get(i);
        if (obj == null) dat = null;
        else dat = Float.valueOf((Float)obj);
        clone.mp_oData.add(i, dat);
      }
    } else if (MODEL_ENUM == m_iDataType) {
      Object obj;
      ModelEnum dat;
      for (i = 0; i < mp_oData.size(); i++) {
        obj = mp_oData.get(i);
        if (obj == null) dat = null;
        else dat = (ModelEnum)((ModelEnum)obj).clone();
        clone.mp_oData.add(i, dat);
      }
    } else if (STRING == m_iDataType) {
      Object obj;
      String dat; 
      for (i = 0; i < mp_oData.size(); i++) {
        obj = mp_oData.get(i);
        if (obj == null) dat = null;
        else dat = new String((String)obj);
        clone.mp_oData.add(i, dat);
      }
    } else if (DOUBLE == m_iDataType) {
      Object obj;
      Double dat; 
      for (i = 0; i < mp_oData.size(); i++) {
        obj = mp_oData.get(i);
        if (obj == null) dat = null;
        else dat = Double.valueOf((Double)obj);
        clone.mp_oData.add(i, dat);
      }
    }
    return clone;
  }
}
