package sortie.data.funcgroups.nci;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * This class provides a class for NCI effects with organizing information. 
 * @author LORA
 *
 */
public class NCIEffect {
  
  /** Identifier for different types of effects */
  public enum effectType {
    crowding_effect,
    damage_effect,
    infection_effect,
    nci_term,
    nitrogen_effect,
    precipitation_effect,
    shading_effect,
    size_effect,
    temperature_effect
  }
  
  /**
   * Flag values for which shading effect term is desired.
   */
  public enum shading_effect {
    no_shading, /**<No shading (class clNoShadingEffect) */
    default_shading /**<Default shading (class clDefaultShadingEffect) */
  };

  /**
   * Flag values for which size effect term is desired.
   */
  public enum size_effect {
    no_size_effect, /**<No size effect (class clNoSizeEffect) */
    default_size_effect, /**<Default size effect (class clDefaultSizeEffect) */
    size_effect_bounded, /**<Size effect with minimum DBH (class clSizeEffectLowerBounded) */
    size_effect_power_function, /**<Power function size effect (class clSizeEffectPowerFunction) */
    size_effect_shifted_lognormal, /**<Shifted lognormal size effect (class clShiftedLognormalSizeEffect) */
    size_effect_compound_exp, /**<Compound exponential size effect (class clSizeEffectCompoundExponential) */
    size_effect_shifted_log_inf, /**<Shifted lognormal w/ infection size effect (class clSizeEffectShiftedLogInf) */
    size_effect_compound_exp_inf /**<Compound exponential w/ infection size effect (class clSizeEffectCompoundExpInf) */
  };

  /**
   * Flag values for which damage effect term is desired.
   */
  public enum damage_effect {
    no_damage_effect, /**<No damage effect (class clNoDamageEffect) */
    default_damage_effect /**<Default damage effect (class clDefaultDamageEffect)*/
  };

  /**
   * Flag values for which NCI term is desired.
   */
  public enum nci_term {
    no_nci_term, /**<No NCI term (class clNoNCITerm) */
    default_nci_term, /**<Default NCI term (class clDefaultNCITerm) */
    nci_with_neighbor_damage, /**<NCI term with neighbor damage (class clNCITermWithNeighborDamage)*/
    larger_neighbors, /**<Count of larger neighbors (class clNCILargerNeighbors)*/
    neighbor_ba, /**<Neighbor BA (class clNCINeighborBA)*/
    nci_with_seedlings, /**<NCI with seedling competition (class clNCIWithSeedlings)*/
    nci_ba_ratio, /**<NCI with ba ratio (class clNCITermBARatio)*/
    nci_ba_ratio_dbh_default, /**<NCI with ba ratio (class clNCITermBARatioDBHDefault)*/
    nci_nci_ba_ratio, /**<NCI with temp dependent lambda (class clNCITermNCIBARatio) */
    nci_nci_ba_ratio_ba_default, /**<NCI with temp dependent lambda (class clNCITermNCIBARatio) */
    nci_nci_temp_dep_ba_ratio, /**<NCI with temp dependent lambda (class clNCITermNCITempDepBARatio) */
    nci_nci_temp_dep_ba_ratio_ba_default /**<NCI with temp dependent lambda (class clNCITermNCITempDepBARatio) */
  };

  /**
   * Flag values for which crowding effect term is desired.
   */
  public enum crowding_effect {
    no_crowding_effect, /**<No crowding effect (class clNoCrowdingEffect) */
    default_crowding_effect, /**<Default crowding effect (class clDefaultCrowdingEffect) */
    crowding_effect_two, /**<Crowding effect 2 (class clCrowdingEffectTwo)*/
    crowding_effect_no_size, /**<Crowding effect with no DBH term (class clCrowdingEffectNoSize) */
    crowding_effect_temp_dep /**<Crowding effect with temperature dependence (class clCrowdingEffectTempDep) */
  };

  /**
   * Flag values for which temperature effect term is desired.
   */
  public enum temperature_effect {
    no_temp_effect, /**<No temperature effect (class clNoTemperatureEffect) */
    weibull_temp_effect, /**<Weibull temperature effect (class clTemperatureEffectWeibull)*/
    double_logistic_temp_effect /**<Double logistic temperature effect (class clTemperatureEffectDoubleLogistic)*/
  };

  /**
   * Flag values for which precipitation effect term is desired.
   */
  public enum precipitation_effect {
    no_precip_effect, /**<No precip effect (class clNoPrecipitationEffect) */
    weibull_precip_effect, /**<Weibull precipitation effect (class clPrecipitationEffectWeibull)*/
    double_logistic_precip_effect /**<Double logistic precipitation effect (class clPrecipitationEffectDoubleLogistic)*/
  };

  /**
   * Flag values for which nitrogen effect term is desired.
   */
  public enum nitrogen_effect {
    no_nitrogen_effect, /**<No N effect (class clNoNitrogenEffect) */
    gauss_nitrogen_effect /**<Gaussian nitrogen effect (class clGaussianNitrogenEffect)*/
  };

  /**
   * Flag values for which infection effect term is desired.
   */
  public enum infection_effect {
    no_infection_effect, /**<No effect (class clNoInfectionEffect) */
    infection_effect, /**<Infection effect non-size dependent (class clInfectionEffect)*/
    infection_effect_size_dep /**<Infection effect size dependent (class clInfectionEffectSizeDependent)*/
  };
  
  /** Whether this behavior requires a target diameter to function. */
  private boolean m_bRequiresDiameter = false;
  
  /**Behavior class*/
  private Class<? extends Behavior> m_oClass;
  
  /**This is what will be displayed as the name of a given behavior in the
   * GUI.  Shouldn't be too long - max 5 words or so*/
  private String m_sDescriptor;
  
  /** Corresponds to an effect enum. This is what goes in the parameter file. */
  private int m_iEffectNum;

  /**
   * Constructor.
   * @param oClass Behavior class.
   * @param sDescriptor The name of the behavior for the user.
   * @param iEffectNum Enum value of the effect.
   */
  public NCIEffect(Class<? extends Behavior> oClass, String sDescriptor, int iEffectNum) {
        
    m_oClass = oClass;
    m_sDescriptor = sDescriptor;
    m_iEffectNum = iEffectNum;
  }
  
  /**
   * Constructor.
   * @param oClass Behavior class.
   * @param sDescriptor The name of the behavior for the user.
   */
  public NCIEffect(Class<? extends Behavior> oClass, String sDescriptor, int iEffectNum, boolean bRequiresDiameter) {
        
    this(oClass, sDescriptor, iEffectNum);
    m_bRequiresDiameter = bRequiresDiameter;
  }

  /**
   * @return the bRequiresDiameter
   */
  public boolean requiresDiameter() {
    return m_bRequiresDiameter;
  }

  public String getDescriptor() {
    return m_sDescriptor;
  }

  public int getEffectNum() {
    return m_iEffectNum;
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
      
    if (m_oClass == null) return null;
    
    Class<?> partypes[] = new Class[3];
     partypes[0] = GUIManager.class;
     partypes[1] = BehaviorTypeBase.class;
     partypes[2] = String.class;
     Constructor<? extends Behavior> ct = m_oClass.getDeclaredConstructor(partypes);
     Object arglist[] = new Object[3];
     arglist[0] = oManager;
     arglist[1] = oParent;
     arglist[2] = m_sDescriptor;
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
}
