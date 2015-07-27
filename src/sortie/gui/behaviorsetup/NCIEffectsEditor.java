package sortie.gui.behaviorsetup;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sortie.data.funcgroups.nci.NCIMasterBase;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;

/**
 * Makes a window for editing NCI functions.
 * 
 * Copyright: Copyright (c) Charles D. Canham 2013
 * Company: Cary Institute of Ecosystem Studies
 * 
 * @author Lora E. Murphy
 * @version 1.0
 * 
 * <br>
 * <br>Edit history: 
 * <br>------------------ 
 * <br>June 11, 2013: Created (LEM)
 */
public class NCIEffectsEditor extends JDialog implements ActionListener {
  
  /**Help ID string*/
  private String m_sHelpID = "windows.edit_nci_functions_window";  
  
  /** The behavior being set up by this window */
  private NCIMasterBase m_oBehavior;
 
  /** Table holding the allometry function settings for display */
  EnhancedTable m_oTable;
  
  /** Calling window */
  NCIParameterEdit m_oParentEdit;
  
  /** Combo box for size effect choices */
  public JComboBox<String> m_jSizeEffect = null;
  
  /** Combo box for crowding effect choices */
  public JComboBox<String> m_jCrowdingEffect = null;
  
  /** Combo box for NCI term choices */
  public JComboBox<String> m_jNCITerm = null;
  
  /** Combo box for shading effect choices */
  public JComboBox<String> m_jShadingEffect = null;
  
  /** Combo box for damage effect choices */
  public JComboBox<String> m_jDamageEffect = null;
  
  /** Combo box for temperature effect choices */
  public JComboBox<String> m_jTemperatureEffect = null;
  
  /** Combo box for precipitation effect choices */
  public JComboBox<String> m_jPrecipitationEffect = null;
  
  /** Combo box for nitrogen effect choices */
  public JComboBox<String> m_jNitrogenEffect = null;
  
  /** Combo box for infection effect choices */
  public JComboBox<String> m_jInfectionEffect = null;
  
  public NCIEffectsEditor(NCIMasterBase oBehavior, NCIParameterEdit oEdit) {
    super(oEdit, "Edit NCI Effects", true);
    
    m_oParentEdit = oEdit;
    m_oBehavior = oBehavior;
        
    String[] p_sChoices;
    JLabel jLabel;
    
    //Help ID
    //m_oPop.getGUIManager().getHelpBroker().enableHelpKey(this.getRootPane(), m_sHelpID, null);
    
    JPanel jMainPanel = new JPanel();
    GridLayout jLayout = new GridLayout(9, 2);
    jLayout.setHgap(10);
    jLayout.setVgap(10);
    jMainPanel.setLayout(jLayout);
    jMainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Size effect combo box 
    p_sChoices = m_oBehavior.getSizeEffectChoices(false);
    if (p_sChoices != null) {
      m_jSizeEffect = new JComboBox<String>(m_oBehavior.getSizeEffectChoices(false));
      m_jSizeEffect.setSelectedItem(m_oBehavior.getSizeEffectChoices(true)[m_oBehavior.getSizeEffectChoice()]);
      m_jSizeEffect.setFont(new SortieFont());

      jLabel = new JLabel("Select size effect:");
      jLabel.setFont(new SortieFont());
      jMainPanel.add(jLabel);
      jMainPanel.add(m_jSizeEffect);
    }

    // Crowding effect combo box
    p_sChoices = m_oBehavior.getCrowdingEffectChoices(false);
    if (p_sChoices != null) {
      m_jCrowdingEffect = new JComboBox<String>(m_oBehavior.getCrowdingEffectChoices(false));
      m_jCrowdingEffect.setSelectedItem(m_oBehavior.getCrowdingEffectChoices(true)[m_oBehavior.getCrowdingEffectChoice()]);
      m_jCrowdingEffect.setFont(new SortieFont());

      jLabel = new JLabel("Select crowding effect:");
      jLabel.setFont(new SortieFont());
      jMainPanel.add(jLabel);
      jMainPanel.add(m_jCrowdingEffect);
    }

    // NCI term combo box
    p_sChoices = m_oBehavior.getNCITermChoices(false);
    if (p_sChoices != null) {
      m_jNCITerm = new JComboBox<String>(m_oBehavior.getNCITermChoices(false));
      m_jNCITerm.setSelectedItem(m_oBehavior.getNCITermChoices(true)[m_oBehavior.getNCITermChoice()]);
      m_jNCITerm.setFont(new SortieFont());

      jLabel = new JLabel("Select NCI term:");
      jLabel.setFont(new SortieFont());
      jMainPanel.add(jLabel);
      jMainPanel.add(m_jNCITerm);
    }

    // Shading effect combo box
    p_sChoices = m_oBehavior.getShadingEffectChoices(false);
    if (p_sChoices != null) {
      m_jShadingEffect = new JComboBox<String>(m_oBehavior.getShadingEffectChoices(false));
      m_jShadingEffect.setSelectedItem(m_oBehavior.getShadingEffectChoices(true)[m_oBehavior.getShadingEffectChoice()]);
      m_jShadingEffect.setFont(new SortieFont());

      jLabel = new JLabel("Select shading effect:");
      jLabel.setFont(new SortieFont());
      jMainPanel.add(jLabel);
      jMainPanel.add(m_jShadingEffect);
    }

    // Damage effect combo box
    p_sChoices = m_oBehavior.getDamageEffectChoices(false);
    if (p_sChoices != null) {
      m_jDamageEffect = new JComboBox<String>(m_oBehavior.getDamageEffectChoices(false));
      m_jDamageEffect.setSelectedItem(m_oBehavior.getDamageEffectChoices(true)[m_oBehavior.getDamageEffectChoice()]);
      m_jDamageEffect.setFont(new SortieFont());

      jLabel = new JLabel("Select damage effect:");
      jLabel.setFont(new SortieFont());
      jMainPanel.add(jLabel);
      jMainPanel.add(m_jDamageEffect);
    }

    // Temperature effect combo box
    p_sChoices = m_oBehavior.getTemperatureEffectChoices(false);
    if (p_sChoices != null) {
      m_jTemperatureEffect = new JComboBox<String>(m_oBehavior.getTemperatureEffectChoices(false));
      m_jTemperatureEffect.setSelectedItem(m_oBehavior.getTemperatureEffectChoices(true)[m_oBehavior.getTemperatureEffectChoice()]);
      m_jTemperatureEffect.setFont(new SortieFont());

      jLabel = new JLabel("Select temperature effect:");
      jLabel.setFont(new SortieFont());
      jMainPanel.add(jLabel);
      jMainPanel.add(m_jTemperatureEffect);
    }

    // Precipitation effect combo box
    p_sChoices = m_oBehavior.getPrecipitationEffectChoices(false);
    if (p_sChoices != null) {
      m_jPrecipitationEffect = new JComboBox<String>(m_oBehavior.getPrecipitationEffectChoices(false));
      m_jPrecipitationEffect.setSelectedItem(m_oBehavior.getPrecipitationEffectChoices(true)[m_oBehavior.getPrecipitationEffectChoice()]);
      m_jPrecipitationEffect.setFont(new SortieFont());

      jLabel = new JLabel("Select precipitation effect:");
      jLabel.setFont(new SortieFont());
      jMainPanel.add(jLabel);
      jMainPanel.add(m_jPrecipitationEffect);
    }

    // Nitrogen effect combo box
    p_sChoices = m_oBehavior.getNitrogenEffectChoices(false);
    if (p_sChoices != null) {
      m_jNitrogenEffect = new JComboBox<String>(m_oBehavior.getNitrogenEffectChoices(false));
      m_jNitrogenEffect.setSelectedItem(m_oBehavior.getNitrogenEffectChoices(true)[m_oBehavior.getNitrogenEffectChoice()]);
      m_jNitrogenEffect.setFont(new SortieFont());

      jLabel = new JLabel("Select nitrogen effect:");
      jLabel.setFont(new SortieFont());
      jMainPanel.add(jLabel);
      jMainPanel.add(m_jNitrogenEffect);
    }

    // Infection effect combo box
    p_sChoices = m_oBehavior.getInfectionEffectChoices(false);
    if (p_sChoices != null) {
      m_jInfectionEffect = new JComboBox<String>(m_oBehavior.getInfectionEffectChoices(false));
      m_jInfectionEffect.setSelectedItem(m_oBehavior.getInfectionEffectChoices(true)[m_oBehavior.getInfectionEffectChoice()]);
      m_jInfectionEffect.setFont(new SortieFont());

      jLabel = new JLabel("Select infection effect:");
      jLabel.setFont(new SortieFont());
      jMainPanel.add(jLabel);
      jMainPanel.add(m_jInfectionEffect);
    }
    
    getContentPane().add(jMainPanel, BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this,
                       oBehavior.getGUIManager().getHelpBroker(),
                       m_sHelpID),
                       BorderLayout.PAGE_END);
  }
  

  /**
   * Controls actions for this window.
   * @param oEvent ActionEvent.
   */
  public void actionPerformed(ActionEvent oEvent) {
    String sCommand = oEvent.getActionCommand();
    if (sCommand.equals("Cancel")) {
      this.setVisible(false);
      this.dispose();
    } else if (sCommand.equals("OK")) {
      
      try {
        
        //Extract the data and feed it back to the behavior
        if (m_jSizeEffect != null)
          m_oBehavior.setSizeEffect(m_jSizeEffect.getSelectedItem().toString());
        if (m_jCrowdingEffect != null)
          m_oBehavior.setCrowdingEffect(m_jCrowdingEffect.getSelectedItem().toString());
        if (m_jNCITerm != null)
          m_oBehavior.setNCITerm(m_jNCITerm.getSelectedItem().toString());
        if (m_jShadingEffect != null)
          m_oBehavior.setShadingEffect(m_jShadingEffect.getSelectedItem().toString());
        if (m_jDamageEffect != null)
          m_oBehavior.setDamageEffect(m_jDamageEffect.getSelectedItem().toString());
        if (m_jPrecipitationEffect != null)
          m_oBehavior.setPrecipitationEffect(m_jPrecipitationEffect.getSelectedItem().toString());
        if (m_jTemperatureEffect != null)
          m_oBehavior.setTemperatureEffect(m_jTemperatureEffect.getSelectedItem().toString());
        if (m_jNitrogenEffect != null)
          m_oBehavior.setNitrogenEffect(m_jNitrogenEffect.getSelectedItem().toString());
        if (m_jInfectionEffect != null)
          m_oBehavior.setInfectionEffect(m_jInfectionEffect.getSelectedItem().toString());
                
        m_oParentEdit.refreshParametersDisplay();
        this.setVisible(false);
        this.dispose();   
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }  
  }
}
