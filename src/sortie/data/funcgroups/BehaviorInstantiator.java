package sortie.data.funcgroups;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Contains information for behavior instantiation.
 * @author LORA
 *
 */
public class BehaviorInstantiator {
  /**This is what will be displayed as the name of a given behavior in the
   * GUI.  Shouldn't be too long - max 5 words or so*/
  private String m_sDescriptor;
  /**String which is used to identify this behavior in the parameter file*/
  private String m_sParFileTag;
  /**XML tag to surround this behavior's data */
  private String m_sXMLRootString;
  
  /**Behavior class*/
  private Class<? extends Behavior> m_oClass;
  
  /**
   * Constructor.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @param oClass Behavior class.
   */
  public BehaviorInstantiator(Class<? extends Behavior> oClass, String sDescriptor,
      String sParFileTag, String sXMLRootString) throws ModelException {
    //Instantiate a naked temporary copy of this class to get the other data
    m_oClass = oClass;
    m_sDescriptor = sDescriptor;
    m_sParFileTag = sParFileTag;
    m_sXMLRootString = sXMLRootString;    
   }

  /**
   * Creates an instance of this behavior's class.
   * @param oManager GUI Manager.
   * @param oParent Managing behavior group object.
   * @return New behavior object.
   * @throws ModelException If there is a problem with the instantiation.
   */
  public Behavior createBehavior(GUIManager oManager, BehaviorTypeBase oParent) throws ModelException {
    try {
    Class<?> partypes[] = new Class[5];
     partypes[0] = GUIManager.class;
     partypes[1] = BehaviorTypeBase.class;
     partypes[2] = String.class;
     partypes[3] = String.class;
     partypes[4] = String.class;
     Constructor<? extends Behavior> ct = m_oClass.getDeclaredConstructor(partypes);
     Object arglist[] = new Object[5];
     arglist[0] = oManager;
     arglist[1] = oParent;
     arglist[2] = m_sDescriptor;
     arglist[3] = m_sParFileTag;
     arglist[4] = m_sXMLRootString;
     return (Behavior)ct.newInstance(arglist);
    } catch (InstantiationException e) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", 
          "Can't instantiate class " + m_oClass.getName() + 
          ". InstantiationException thrown with message: " + e.getMessage()));
    } catch (IllegalAccessException e) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", 
          "Can't instantiate class " + m_oClass.getName()  + 
          ". IllegalAccessException thrown with message: " + e.getMessage()));
    } catch (InvocationTargetException e) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", 
          "Can't instantiate class " + m_oClass.getName() +  
          ". InvocationTargetException thrown with message: " + e.getMessage()));
    } catch (NoSuchMethodException e) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", 
          "Can't instantiate class " + m_oClass.getName() + " with standard"
          + " arguments."));
    }    
  }

  /**
   * @return the descriptor
   */
  public String getDescriptor() {
    return m_sDescriptor;
  }

  /**
   * @return the ParFileTag
   */
  public String getParFileTag() {
    return m_sParFileTag;
  }

  /**
   * @return the XMLRootString
   */
  public String getXMLRootString() {
    return m_sXMLRootString;
  }

  /**
   * @return the Class
   */
  public Class<? extends Behavior> getBehaviorClass() {
    return m_oClass;
  }
  
  
}
