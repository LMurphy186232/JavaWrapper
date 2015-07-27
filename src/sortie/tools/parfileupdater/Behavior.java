package sortie.tools.parfileupdater;

import java.util.ArrayList;

/**
 * Represents a single behavior in the core.
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>June 29, 2012: Created (LEM)
 */

public class Behavior {
  
  public StringBuffer m_jDataBuf = new StringBuffer();
  
  public StringBuffer m_jBehaviorListBuf = new StringBuffer();

  /**The data objects which are required when this behavior is enabled
   * (i.e. the individual pieces of data that must have values)*/
  protected ArrayList<ModelData> mp_oRequiredData = new ArrayList<ModelData>(0);
  
  /**String which is used to identify this behavior in the 6.x parameter file*/
  protected String m_sOldParFileTag;
  
  /**String which is used to identify this behavior in the 7.x parameter file*/
  protected String m_sNewParFileTag;
  
  /** String which holds the tag used to encircle the parameters*/
  protected String m_sParametersTag;
  
  protected boolean m_bEnabled = false;
  
  /**
   * Constructor.
   * @param sOldParFileTag Parameter file 6.x XML tag (no brackets);
   * @param sNewParFileTag Parameter file 7.x XML tag (no brackets).
   */
  public Behavior(String sOldParFileTag, String sNewParFileTag) {
    m_sOldParFileTag = sOldParFileTag;
    m_sNewParFileTag = sNewParFileTag;
    m_sParametersTag = sNewParFileTag;
  }
  
  /**
   * Constructor.
   * @param sOldParFileTag Parameter file 6.x XML tag (no brackets);
   * @param sNewParFileTag Parameter file 7.x XML tag (no brackets).
   * @param sParametersTag Parameter file parameters tag.
   */
  public Behavior(String sOldParFileTag, String sNewParFileTag, String sParametersTag) {
    m_sOldParFileTag = sOldParFileTag;
    m_sNewParFileTag = sNewParFileTag;
    m_sParametersTag = sParametersTag;
  }
  
  public String getOldXMLTag() {return m_sOldParFileTag;}

  /**
   * Adds a piece of required data to the list.
   * @param oData Data to add.
   */
  public void addRequiredData(ModelData oData) {
    mp_oRequiredData.add(oData);
  }
}
