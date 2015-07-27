package sortie.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.output.ShortOutput;
import sortie.data.simpletypes.ModelException;
import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.OutFileFilter;
import sortie.gui.components.SortieFont;
import sortie.gui.components.SortieLabel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Window used by the user to set up summary (short) output files.
 * <p>Copyright: Copyright (c) 2003 Charles D. Canham</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class ShortOutputFileSetup
    extends JDialog
    implements ActionListener {
  
  

  /** Output behaviors object. */
  ShortOutput m_oOutputData;

  /** Main window object. */
  MainWindow m_oWindow;

  /** The string identifying the help topic for this window. */
  private String m_sHelpID = "windows.summary_output";

  /** Field for summary output filename. */
  JTextField m_jSummaryOutputFileName = new JTextField();
  /** Checkbox for whether to include seedling absolute density.*/
  JCheckBox m_jSeedlingAbsoluteDensityCheck = new JCheckBox();
  /** Checkbox for whether to include sapling relative basal area.*/
  JCheckBox m_jSaplingRelativeBACheck = new JCheckBox();
  /** Checkbox for whether to include sapling absolute basal area.*/
  JCheckBox m_jSaplingAbsoluteBACheck = new JCheckBox();
  /** Checkbox for whether to include sapling relative density.*/
  JCheckBox m_jSaplingRelativeDensityCheck = new JCheckBox();
  /** Checkbox for whether to include sapling absolute density.*/
  JCheckBox m_jSaplingAbsoluteDensityCheck = new JCheckBox();
  /** Checkbox for whether to include adult relative basal area.*/
  JCheckBox m_jAdultRelativeBACheck = new JCheckBox();
  /** Checkbox for whether to include adult absolute basal area.*/
  JCheckBox m_jAdultAbsoluteBACheck = new JCheckBox();
  /** Checkbox for whether to include adult relative density.*/
  JCheckBox m_jAdultRelativeDensityCheck = new JCheckBox();
  /** Checkbox for whether to include adult absolute density.*/
  JCheckBox m_jAdultAbsoluteDensityCheck = new JCheckBox();
  /** Checkbox for whether to include snag relative basal area.*/
  JCheckBox m_jSnagRelativeBACheck = new JCheckBox();
  /** Checkbox for whether to include snag absolute basal area.*/
  JCheckBox m_jSnagAbsoluteBACheck = new JCheckBox();
  /** Checkbox for whether to include snag relative density.*/
  JCheckBox m_jSnagRelativeDensityCheck = new JCheckBox();
  /** Checkbox for whether to include snag absolute density.*/
  JCheckBox m_jSnagAbsoluteDensityCheck = new JCheckBox();
  /** Checkbox for whether to include harvest dead seedling absolute density.*/
  JCheckBox m_jDeadSeedlingHarvestDen = new JCheckBox();
  /** Checkbox for whether to include harvest dead sapling absolute density.*/
  JCheckBox m_jDeadSaplingHarvestDen = new JCheckBox();
  /** Checkbox for whether to include harvest dead adult absolute density.*/
  JCheckBox m_jDeadAdultHarvestDen = new JCheckBox();
  /** Checkbox for whether to include harvest dead snag absolute density.*/
  JCheckBox m_jDeadSnagHarvestDen = new JCheckBox();
  /** Checkbox for whether to include harvest dead sapling basal area.*/
  JCheckBox m_jDeadSaplingHarvestBA = new JCheckBox();
  /** Checkbox for whether to include harvest dead adult basal area.*/
  JCheckBox m_jDeadAdultHarvestBA = new JCheckBox();
  /** Checkbox for whether to include harvest dead snag basal area.*/
  JCheckBox m_jDeadSnagHarvestBA = new JCheckBox();
  /** Checkbox for whether to include natural mortality dead seedling absolute density.*/
  JCheckBox m_jDeadSeedlingNaturalDen = new JCheckBox();
  /** Checkbox for whether to include natural mortality dead sapling absolute density.*/
  JCheckBox m_jDeadSaplingNaturalDen = new JCheckBox();
  /** Checkbox for whether to include natural mortality dead adult absolute density.*/
  JCheckBox m_jDeadAdultNaturalDen = new JCheckBox();
  /** Checkbox for whether to include natural mortality dead snag absolute density.*/
  JCheckBox m_jDeadSnagNaturalDen = new JCheckBox();
  /** Checkbox for whether to include natural mortality dead sapling basal area.*/
  JCheckBox m_jDeadSaplingNaturalBA = new JCheckBox();
  /** Checkbox for whether to include natural mortality dead adult basal area.*/
  JCheckBox m_jDeadAdultNaturalBA = new JCheckBox();
  /** Checkbox for whether to include natural mortality dead snag basal area.*/
  JCheckBox m_jDeadSnagNaturalBA = new JCheckBox();
  /** Checkbox for whether to include insect dead seedling absolute density.*/
  JCheckBox m_jDeadSeedlingInsectDen = new JCheckBox();
  /** Checkbox for whether to include insect dead sapling absolute density.*/
  JCheckBox m_jDeadSaplingInsectDen = new JCheckBox();
  /** Checkbox for whether to include insect dead adult absolute density.*/
  JCheckBox m_jDeadAdultInsectDen = new JCheckBox();
  /** Checkbox for whether to include insect dead snag absolute density.*/
  JCheckBox m_jDeadSnagInsectDen = new JCheckBox();
  /** Checkbox for whether to include insect dead sapling basal area.*/
  JCheckBox m_jDeadSaplingInsectBA = new JCheckBox();
  /** Checkbox for whether to include insect dead adult basal area.*/
  JCheckBox m_jDeadAdultInsectBA = new JCheckBox();
  /** Checkbox for whether to include insect dead snag basal area.*/
  JCheckBox m_jDeadSnagInsectBA = new JCheckBox();
  /** Checkbox for whether to include storm dead seedling absolute density.*/
  JCheckBox m_jDeadSeedlingStormDen = new JCheckBox();
  /** Checkbox for whether to include storm dead sapling absolute density.*/
  JCheckBox m_jDeadSaplingStormDen = new JCheckBox();
  /** Checkbox for whether to include storm dead adult absolute density.*/
  JCheckBox m_jDeadAdultStormDen = new JCheckBox();
  /** Checkbox for whether to include storm dead snag absolute density.*/
  JCheckBox m_jDeadSnagStormDen = new JCheckBox();
  /** Checkbox for whether to include storm dead sapling basal area.*/
  JCheckBox m_jDeadSaplingStormBA = new JCheckBox();
  /** Checkbox for whether to include storm dead adult basal area.*/
  JCheckBox m_jDeadAdultStormBA = new JCheckBox();
  /** Checkbox for whether to include storm dead snag basal area.*/
  JCheckBox m_jDeadSnagStormBA = new JCheckBox();
  /** Checkbox for whether to include disease dead seedling absolute density.*/
  JCheckBox m_jDeadSeedlingDiseaseDen = new JCheckBox();
  /** Checkbox for whether to include disease dead sapling absolute density.*/
  JCheckBox m_jDeadSaplingDiseaseDen = new JCheckBox();
  /** Checkbox for whether to include disease dead adult absolute density.*/
  JCheckBox m_jDeadAdultDiseaseDen = new JCheckBox();
  /** Checkbox for whether to include disease dead snag absolute density.*/
  JCheckBox m_jDeadSnagDiseaseDen = new JCheckBox();
  /** Checkbox for whether to include disease dead sapling basal area.*/
  JCheckBox m_jDeadSaplingDiseaseBA = new JCheckBox();
  /** Checkbox for whether to include disease dead adult basal area.*/
  JCheckBox m_jDeadAdultDiseaseBA = new JCheckBox();
  /** Checkbox for whether to include disease dead snag basal area.*/
  JCheckBox m_jDeadSnagDiseaseBA = new JCheckBox();

  /**
   * Constructor.
   * @param oParent Parent window in which to display this dialog.
   * @param oData OutputBehaviors object.
   * @param oWindow MainWindow window.
   */
  public ShortOutputFileSetup(JDialog oParent, OutputBehaviors oData,
                                MainWindow oWindow) {
    super(oParent, "Summary Output File Setup", true);

    //Help ID
    oWindow.m_oHelpBroker.enableHelpKey(this.getRootPane(), m_sHelpID, null);

    try {
      m_oOutputData = oData.getShortOutput();
      m_oWindow = oWindow;
      createGUI();
      loadData();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Responds to button clicks.
   * @param oEvent ActionEvent Event which triggered this method.
   */
  public void actionPerformed(ActionEvent oEvent) {
    if (oEvent.getActionCommand().equals("OK")) {
      try {
        File jParent = new File(m_jSummaryOutputFileName.getText()).getParentFile();
        if (jParent != null && !(jParent.exists())) {
          int response = JOptionPane.showConfirmDialog(this, 
              "The path for the short output file does not exist on this " +
              "machine. Running this file on this machine will cause an " +
              "error. Continue?", "SORTIE", JOptionPane.YES_NO_OPTION);
          if (response == JOptionPane.NO_OPTION) return;
        }
        
        //Set the file name
        m_oOutputData.setShortOutputFileName(m_jSummaryOutputFileName.
                                             getText());

        //Set seedling density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SEEDLING, 
            OutputBehaviors.NOTDEAD,
            m_jSeedlingAbsoluteDensityCheck.isSelected());
        
        //Set sapling absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SAPLING, 
              OutputBehaviors.NOTDEAD, m_jSaplingAbsoluteDensityCheck.isSelected());        

        //Set sapling relative density
        m_oOutputData.setSaveRelativeDensity(TreePopulation.SAPLING, 
              m_jSaplingRelativeDensityCheck.isSelected());
       
        //Set sapling absolute basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, 
              OutputBehaviors.NOTDEAD, m_jSaplingAbsoluteBACheck.isSelected());
        
        //Set sapling relative basal area
        m_oOutputData.setSaveRelativeBasalArea(TreePopulation.SAPLING, 
              m_jSaplingRelativeBACheck.isSelected());
        
        //Set adult absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.ADULT, 
              OutputBehaviors.NOTDEAD, 
              m_jAdultAbsoluteDensityCheck.isSelected());
        
        //Set adult relative density
        m_oOutputData.setSaveRelativeDensity(TreePopulation.ADULT, 
              m_jAdultRelativeDensityCheck.isSelected());
        
        //Set adult absolute basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.ADULT, 
              OutputBehaviors.NOTDEAD, m_jAdultAbsoluteBACheck.isSelected());

        //Set adult relative basal area
        m_oOutputData.setSaveRelativeBasalArea(TreePopulation.ADULT, 
              m_jAdultRelativeBACheck.isSelected());

        //Set snag absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SNAG, 
              OutputBehaviors.NOTDEAD, 
              m_jSnagAbsoluteDensityCheck.isSelected());
        
        //Set snag relative density
        m_oOutputData.setSaveRelativeDensity(TreePopulation.SNAG, 
              m_jSnagRelativeDensityCheck.isSelected());       

        //Set snag absolute basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.SNAG, 
              OutputBehaviors.NOTDEAD, m_jSnagAbsoluteBACheck.isSelected());
        
        //Set snag relative basal area
        m_oOutputData.setSaveRelativeBasalArea(TreePopulation.SNAG, 
              m_jSnagRelativeBACheck.isSelected());
        
        //Set harvest dead seedling absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SEEDLING, 
           OutputBehaviors.HARVEST,  m_jDeadSeedlingHarvestDen.isSelected());

        //Set harvest dead sapling absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SAPLING, 
           OutputBehaviors.HARVEST,  m_jDeadSaplingHarvestDen.isSelected());

        //Set harvest dead adult absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.ADULT, 
           OutputBehaviors.HARVEST,  m_jDeadAdultHarvestDen.isSelected());

        //Set harvest dead snag absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SNAG, 
           OutputBehaviors.HARVEST,  m_jDeadSnagHarvestDen.isSelected());

        //Set harvest dead sapling basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, 
           OutputBehaviors.HARVEST,  m_jDeadSaplingHarvestBA.isSelected());

        //Set harvest dead adult basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.ADULT, 
           OutputBehaviors.HARVEST,  m_jDeadAdultHarvestBA.isSelected());

        //Set harvest dead snag basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.SNAG, 
           OutputBehaviors.HARVEST,  m_jDeadSnagHarvestBA.isSelected());

        //Set natural mortality dead seedling absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SEEDLING, 
           OutputBehaviors.NATURAL,  m_jDeadSeedlingNaturalDen.isSelected());

        //Set natural mortality dead sapling absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SAPLING, 
           OutputBehaviors.NATURAL,  m_jDeadSaplingNaturalDen.isSelected());

        //Set natural mortality dead adult absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.ADULT, 
           OutputBehaviors.NATURAL,  m_jDeadAdultNaturalDen.isSelected());

        //Set natural mortality dead snag absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SNAG, 
           OutputBehaviors.NATURAL,  m_jDeadSnagNaturalDen.isSelected());

        //Set natural mortality dead sapling basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, 
           OutputBehaviors.NATURAL,  m_jDeadSaplingNaturalBA.isSelected());

        //Set natural mortality dead adult basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.ADULT, 
           OutputBehaviors.NATURAL,  m_jDeadAdultNaturalBA.isSelected());

        //Set natural mortality dead snag basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.SNAG, 
           OutputBehaviors.NATURAL,  m_jDeadSnagNaturalBA.isSelected());

        //Set insect dead seedling absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SEEDLING, 
           OutputBehaviors.INSECTS,  m_jDeadSeedlingInsectDen.isSelected());

        //Set insect dead sapling absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SAPLING, 
           OutputBehaviors.INSECTS,  m_jDeadSaplingInsectDen.isSelected());

        //Set insect dead adult absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.ADULT, 
           OutputBehaviors.INSECTS,  m_jDeadAdultInsectDen.isSelected());

        //Set insect dead snag absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SNAG, 
           OutputBehaviors.INSECTS,  m_jDeadSnagInsectDen.isSelected());

        //Set insect dead sapling basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, 
           OutputBehaviors.INSECTS,  m_jDeadSaplingInsectBA.isSelected());

        //Set insect dead adult basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.ADULT, 
           OutputBehaviors.INSECTS,  m_jDeadAdultInsectBA.isSelected());

        //Set insect dead snag basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.SNAG, 
           OutputBehaviors.INSECTS,  m_jDeadSnagInsectBA.isSelected());

        //Set storm dead seedling absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SEEDLING, 
           OutputBehaviors.STORM,  m_jDeadSeedlingStormDen.isSelected());

        //Set storm dead sapling absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SAPLING, 
           OutputBehaviors.STORM,  m_jDeadSaplingStormDen.isSelected());

        //Set storm dead adult absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.ADULT, 
           OutputBehaviors.STORM,  m_jDeadAdultStormDen.isSelected());

        //Set storm dead snag absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SNAG, 
           OutputBehaviors.STORM,  m_jDeadSnagStormDen.isSelected());

        //Set storm dead sapling basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, 
           OutputBehaviors.STORM,  m_jDeadSaplingStormBA.isSelected());

        //Set storm dead adult basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.ADULT, 
           OutputBehaviors.STORM,  m_jDeadAdultStormBA.isSelected());

        //Set storm dead snag basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.SNAG, 
           OutputBehaviors.STORM,  m_jDeadSnagStormBA.isSelected());

        //Set disease dead seedling absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SEEDLING, 
           OutputBehaviors.DISEASE,  m_jDeadSeedlingDiseaseDen.isSelected());

        //Set disease dead sapling absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SAPLING, 
           OutputBehaviors.DISEASE,  m_jDeadSaplingDiseaseDen.isSelected());

        //Set disease dead adult absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.ADULT, 
           OutputBehaviors.DISEASE,  m_jDeadAdultDiseaseDen.isSelected());

        //Set disease dead snag absolute density
        m_oOutputData.setSaveAbsoluteDensity(TreePopulation.SNAG, 
           OutputBehaviors.DISEASE,  m_jDeadSnagDiseaseDen.isSelected());

        //Set disease dead sapling basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, 
           OutputBehaviors.DISEASE,  m_jDeadSaplingDiseaseBA.isSelected());

        //Set disease dead adult basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.ADULT, 
           OutputBehaviors.DISEASE,  m_jDeadAdultDiseaseBA.isSelected());

        //Set disease dead snag basal area
        m_oOutputData.setSaveAbsoluteBasalArea(TreePopulation.SNAG, 
           OutputBehaviors.DISEASE,  m_jDeadSnagDiseaseBA.isSelected());
        
        // Short output
        m_oOutputData.updateIsActive();
        //m_oOutputData.validateData(null);

        //Close window
        this.setVisible(false);
        this.dispose();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("Cancel")) {
      //Close window
      this.setVisible(false);
      this.dispose();
    }
    else if (oEvent.getActionCommand().equals("Clear all live")) {
      setSelectedAllLive(false);
    }
    else if (oEvent.getActionCommand().equals("Select all live")) {
      setSelectedAllLive(true);
    }
    else if (oEvent.getActionCommand().equals("Clear all dead")) {
      setSelectedAllDead(false);
    }
    else if (oEvent.getActionCommand().equals("Select all dead")) {
      setSelectedAllDead(true);
    }
    else if (oEvent.getActionCommand().equals("EditSubplots")) {
      try {
        SubplotEdit oWindow = new SubplotEdit(this, 
            m_oOutputData.getGUIManager().getOutputBehaviors(),
            m_oOutputData.getGUIManager().getDisturbanceBehaviors(),
            m_oOutputData.getGUIManager().getPlantingBehaviors(), true);
        oWindow.pack();

        //Make sure the window's not too big - make it fit within the
        //bounds of the main application window
        if (oWindow.getSize().height > m_oWindow.getSize().height ||
            oWindow.getSize().width > m_oWindow.getSize().width) {

          int iWidth = java.lang.Math.min(oWindow.getSize().width,
                                          m_oWindow.getSize().width - 10);
          int iHeight = java.lang.Math.min(oWindow.getSize().height,
                                           m_oWindow.getSize().height - 10);

          oWindow.setBounds(oWindow.getBounds().x, oWindow.getBounds().y,
                            iWidth, iHeight);
        }

        oWindow.setLocationRelativeTo(null);
        oWindow.setVisible(true);
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("Browse")) {
      ModelFileChooser jChooser = new ModelFileChooser();

      //If there's a value for the detailed output file, navigate to that directory
      File oDir = new File(m_jSummaryOutputFileName.getText());
      if (oDir.getParentFile() != null && oDir.getParentFile().exists()) {
        jChooser.setCurrentDirectory(oDir.getParentFile());
      }

      jChooser.setSelectedFile(new File(oDir.getName()));
      jChooser.setFileFilter(new OutFileFilter());

      int returnVal = jChooser.showSaveDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        //User chose a file - does it already exist?
        File oFile = jChooser.getSelectedFile();

        //Does this file already exist?
        if (oFile.exists()) {
          returnVal = JOptionPane.showConfirmDialog(this,
                                             "This file already exists.  Do you wish to overwrite it?",
                                             "Model",
                                             JOptionPane.YES_NO_OPTION);
          if (returnVal == JOptionPane.YES_OPTION) {
            String sFileName = oFile.getAbsolutePath();
            m_jSummaryOutputFileName.setText(sFileName);
          }
        }
        else {
          //File didn't exist
          String sFileName = oFile.getAbsolutePath();
          m_jSummaryOutputFileName.setText(sFileName);
        }
      }
    }
  }

  /**
   * This loads data from the output behavior into the window.
   */
  private void loadData() {
    try {
      //Filename
      if (m_oOutputData.getShortOutputFileName().length() > 0) {
        m_jSummaryOutputFileName.setText(m_oOutputData.
                                         getShortOutputFileName());
      }

      //Seedling absolute density
      m_jSeedlingAbsoluteDensityCheck.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SEEDLING, 
              OutputBehaviors.NOTDEAD));

      //Sapling absolute density
      m_jSaplingAbsoluteDensityCheck.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SAPLING, 
              OutputBehaviors.NOTDEAD));

      //Sapling relative density
      m_jSaplingRelativeDensityCheck.setSelected(
          m_oOutputData.getSaveRelativeDensity(TreePopulation.SAPLING));

      //Sapling absolute basal area
      m_jSaplingAbsoluteBACheck.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, 
              OutputBehaviors.NOTDEAD));

      //Sapling relative basal area
      m_jSaplingRelativeBACheck.setSelected(
          m_oOutputData.getSaveRelativeBasalArea(TreePopulation.SAPLING));

      //Adult absolute density
      m_jAdultAbsoluteDensityCheck.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.ADULT, 
              OutputBehaviors.NOTDEAD));

      //Adult relative density
      m_jAdultRelativeDensityCheck.setSelected(
          m_oOutputData.getSaveRelativeDensity(TreePopulation.ADULT));

      //Adult absolute basal area
      m_jAdultAbsoluteBACheck.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.ADULT, 
              OutputBehaviors.NOTDEAD));

      //Adult relative basal area
      m_jAdultRelativeBACheck.setSelected(
          m_oOutputData.getSaveRelativeBasalArea(TreePopulation.ADULT));

      //Snag absolute density
      m_jSnagAbsoluteDensityCheck.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SNAG, 
              OutputBehaviors.NOTDEAD));

      //Snag relative density
      m_jSnagRelativeDensityCheck.setSelected(
          m_oOutputData.getSaveRelativeDensity(TreePopulation.SNAG));

      //Snag absolute basal area
      m_jSnagAbsoluteBACheck.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.SNAG, 
              OutputBehaviors.NOTDEAD));

      //Snag relative basal area
      m_jSnagRelativeBACheck.setSelected(
          m_oOutputData.getSaveRelativeBasalArea(TreePopulation.SNAG));
      
      // harvest dead seedling absolute density
      m_jDeadSeedlingHarvestDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SEEDLING, 
                        OutputBehaviors.HARVEST));

      // harvest dead sapling absolute density
      m_jDeadSaplingHarvestDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SAPLING, 
                        OutputBehaviors.HARVEST));

      // harvest dead adult absolute density
      m_jDeadAdultHarvestDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.ADULT, 
                        OutputBehaviors.HARVEST));

      // harvest dead snag absolute density
      m_jDeadSnagHarvestDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SNAG, 
                        OutputBehaviors.HARVEST));

      // harvest dead sapling basal area
      m_jDeadSaplingHarvestBA.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, 
                        OutputBehaviors.HARVEST));

      // harvest dead adult basal area
      m_jDeadAdultHarvestBA.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.ADULT, 
                        OutputBehaviors.HARVEST));

      // harvest dead snag basal area
      m_jDeadSnagHarvestBA.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.SNAG, 
                        OutputBehaviors.HARVEST));

      // natural mortality dead seedling absolute density
      m_jDeadSeedlingNaturalDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SEEDLING, 
                        OutputBehaviors.NATURAL));

      // natural mortality dead sapling absolute density
      m_jDeadSaplingNaturalDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SAPLING, 
                        OutputBehaviors.NATURAL));

      // natural mortality dead adult absolute density
      m_jDeadAdultNaturalDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.ADULT, 
                        OutputBehaviors.NATURAL));

      // natural mortality dead snag absolute density
      m_jDeadSnagNaturalDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SNAG, 
                        OutputBehaviors.NATURAL));

      // natural mortality dead sapling basal area
      m_jDeadSaplingNaturalBA.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, 
                        OutputBehaviors.NATURAL));

      // natural mortality dead adult basal area
      m_jDeadAdultNaturalBA.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.ADULT, 
                        OutputBehaviors.NATURAL));

      // natural mortality dead snag basal area
      m_jDeadSnagNaturalBA.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.SNAG, 
                        OutputBehaviors.NATURAL));

      // insect dead seedling absolute density
      m_jDeadSeedlingInsectDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SEEDLING, 
                        OutputBehaviors.INSECTS));

      // insect dead sapling absolute density
      m_jDeadSaplingInsectDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SAPLING, 
                        OutputBehaviors.INSECTS));

      // insect dead adult absolute density
      m_jDeadAdultInsectDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.ADULT, 
                        OutputBehaviors.INSECTS));

      // insect dead snag absolute density
      m_jDeadSnagInsectDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SNAG, 
                        OutputBehaviors.INSECTS));

      // insect dead sapling basal area
      m_jDeadSaplingInsectBA.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, 
                        OutputBehaviors.INSECTS));

      // insect dead adult basal area
      m_jDeadAdultInsectBA.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.ADULT, 
                        OutputBehaviors.INSECTS));

      // insect dead snag basal area
      m_jDeadSnagInsectBA.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.SNAG, 
                        OutputBehaviors.INSECTS));

      // storm dead seedling absolute density
      m_jDeadSeedlingStormDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SEEDLING, 
                        OutputBehaviors.STORM));

      // storm dead sapling absolute density
      m_jDeadSaplingStormDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SAPLING, 
                        OutputBehaviors.STORM));

      // storm dead adult absolute density
      m_jDeadAdultStormDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.ADULT, 
                        OutputBehaviors.STORM));

      // storm dead snag absolute density
      m_jDeadSnagStormDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SNAG, 
                        OutputBehaviors.STORM));

      // storm dead sapling basal area
      m_jDeadSaplingStormBA.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, 
                        OutputBehaviors.STORM));

      // storm dead adult basal area
      m_jDeadAdultStormBA.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.ADULT, 
                        OutputBehaviors.STORM));

      // storm dead snag basal area
      m_jDeadSnagStormBA.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.SNAG, 
                        OutputBehaviors.STORM));

      // disease dead seedling absolute density
      m_jDeadSeedlingDiseaseDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SEEDLING, 
                        OutputBehaviors.DISEASE));

      // disease dead sapling absolute density
      m_jDeadSaplingDiseaseDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SAPLING, 
                        OutputBehaviors.DISEASE));

      // disease dead adult absolute density
      m_jDeadAdultDiseaseDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.ADULT, 
                        OutputBehaviors.DISEASE));

      // disease dead snag absolute density
      m_jDeadSnagDiseaseDen.setSelected(
          m_oOutputData.getSaveAbsoluteDensity(TreePopulation.SNAG, 
                        OutputBehaviors.DISEASE));

      // disease dead sapling basal area
      m_jDeadSaplingDiseaseBA.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, 
                        OutputBehaviors.DISEASE));

      // disease dead adult basal area
      m_jDeadAdultDiseaseBA.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.ADULT, 
                        OutputBehaviors.DISEASE));

      // disease dead snag basal area
      m_jDeadSnagDiseaseBA.setSelected(
          m_oOutputData.getSaveAbsoluteBasalArea(TreePopulation.SNAG, 
                        OutputBehaviors.DISEASE));


    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
  }
  
  /**
   * Sets all the fonts.
   */
  private void setUpCheckboxes() {
    m_jSeedlingAbsoluteDensityCheck.setBackground(SystemColor.control);
    m_jSeedlingAbsoluteDensityCheck.setFont(new SortieFont());
    m_jSeedlingAbsoluteDensityCheck.setText("Absolute Density");
    m_jSeedlingAbsoluteDensityCheck.setAlignmentX(java.awt.Component.
                                                  LEFT_ALIGNMENT);
    
    m_jSaplingRelativeBACheck.setBackground(SystemColor.control);
    m_jSaplingRelativeBACheck.setFont(new SortieFont());
    m_jSaplingRelativeBACheck.setText("Relative Basal Area");
    m_jSaplingAbsoluteBACheck.setBackground(SystemColor.control);
    m_jSaplingAbsoluteBACheck.setFont(new SortieFont());
    m_jSaplingAbsoluteBACheck.setText("Absolute Basal Area");
    m_jSaplingRelativeDensityCheck.setBackground(SystemColor.control);
    m_jSaplingRelativeDensityCheck.setFont(new SortieFont());
    m_jSaplingRelativeDensityCheck.setText("Relative Density");
    m_jSaplingAbsoluteDensityCheck.setBackground(SystemColor.control);
    m_jSaplingAbsoluteDensityCheck.setFont(new SortieFont());
    m_jSaplingAbsoluteDensityCheck.setActionCommand("Absolute Density");
    m_jSaplingAbsoluteDensityCheck.setSelected(false);
    m_jSaplingAbsoluteDensityCheck.setText("Absolute Density");

    m_jAdultRelativeBACheck.setBackground(SystemColor.control);
    m_jAdultRelativeBACheck.setFont(new SortieFont());
    m_jAdultRelativeBACheck.setText("Relative Basal Area");
    m_jAdultAbsoluteBACheck.setBackground(SystemColor.control);
    m_jAdultAbsoluteBACheck.setFont(new SortieFont());
    m_jAdultAbsoluteBACheck.setText("Absolute Basal Area");
    m_jAdultRelativeDensityCheck.setBackground(SystemColor.control);
    m_jAdultRelativeDensityCheck.setFont(new SortieFont());
    m_jAdultRelativeDensityCheck.setText("Relative Density");
    m_jAdultAbsoluteDensityCheck.setBackground(SystemColor.control);
    m_jAdultAbsoluteDensityCheck.setFont(new SortieFont());
    m_jAdultAbsoluteDensityCheck.setText("Absolute Density");

    m_jSnagRelativeBACheck.setBackground(SystemColor.control);
    m_jSnagRelativeBACheck.setFont(new SortieFont());
    m_jSnagRelativeBACheck.setText("Relative Basal Area");
    m_jSnagAbsoluteBACheck.setBackground(SystemColor.control);
    m_jSnagAbsoluteBACheck.setFont(new SortieFont());
    m_jSnagAbsoluteBACheck.setText("Absolute Basal Area");
    m_jSnagRelativeDensityCheck.setBackground(SystemColor.control);
    m_jSnagRelativeDensityCheck.setFont(new SortieFont());
    m_jSnagRelativeDensityCheck.setText("Relative Density");
    m_jSnagAbsoluteDensityCheck.setBackground(SystemColor.control);
    m_jSnagAbsoluteDensityCheck.setFont(new SortieFont());
    m_jSnagAbsoluteDensityCheck.setText("Absolute Density");
    
    m_jDeadSeedlingHarvestDen.setBackground(SystemColor.control);
    m_jDeadSeedlingHarvestDen.setFont(new SortieFont());
    m_jDeadSeedlingHarvestDen.setText("Density");
    
    m_jDeadSaplingHarvestDen.setBackground(SystemColor.control);
    m_jDeadSaplingHarvestDen.setFont(new SortieFont());
    m_jDeadSaplingHarvestDen.setText("Density");
    
    m_jDeadAdultHarvestDen.setBackground(SystemColor.control);
    m_jDeadAdultHarvestDen.setFont(new SortieFont());
    m_jDeadAdultHarvestDen.setText("Density");
    
    m_jDeadSnagHarvestDen.setBackground(SystemColor.control);
    m_jDeadSnagHarvestDen.setFont(new SortieFont());
    m_jDeadSnagHarvestDen.setText("Density");
    
    m_jDeadSaplingHarvestBA.setBackground(SystemColor.control);
    m_jDeadSaplingHarvestBA.setFont(new SortieFont());
    m_jDeadSaplingHarvestBA.setText("Basal Area");
    
    m_jDeadAdultHarvestBA.setBackground(SystemColor.control);
    m_jDeadAdultHarvestBA.setFont(new SortieFont());
    m_jDeadAdultHarvestBA.setText("Basal Area");
    
    m_jDeadSnagHarvestBA.setBackground(SystemColor.control);
    m_jDeadSnagHarvestBA.setFont(new SortieFont());
    m_jDeadSnagHarvestBA.setText("Basal Area");
    
    m_jDeadSeedlingNaturalDen.setBackground(SystemColor.control);
    m_jDeadSeedlingNaturalDen.setFont(new SortieFont());
    m_jDeadSeedlingNaturalDen.setText("Density");
    
    m_jDeadSaplingNaturalDen.setBackground(SystemColor.control);
    m_jDeadSaplingNaturalDen.setFont(new SortieFont());
    m_jDeadSaplingNaturalDen.setText("Density");
    
    m_jDeadAdultNaturalDen.setBackground(SystemColor.control);
    m_jDeadAdultNaturalDen.setFont(new SortieFont());
    m_jDeadAdultNaturalDen.setText("Density");
    
    m_jDeadSnagNaturalDen.setBackground(SystemColor.control);
    m_jDeadSnagNaturalDen.setFont(new SortieFont());
    m_jDeadSnagNaturalDen.setText("Density");
    
    m_jDeadSaplingNaturalBA.setBackground(SystemColor.control);
    m_jDeadSaplingNaturalBA.setFont(new SortieFont());
    m_jDeadSaplingNaturalBA.setText("Basal Area");
    
    m_jDeadAdultNaturalBA.setBackground(SystemColor.control);
    m_jDeadAdultNaturalBA.setFont(new SortieFont());
    m_jDeadAdultNaturalBA.setText("Basal Area");
    
    m_jDeadSnagNaturalBA.setBackground(SystemColor.control);
    m_jDeadSnagNaturalBA.setFont(new SortieFont());
    m_jDeadSnagNaturalBA.setText("Basal Area");
    
    m_jDeadSeedlingInsectDen.setBackground(SystemColor.control);
    m_jDeadSeedlingInsectDen.setFont(new SortieFont());
    m_jDeadSeedlingInsectDen.setText("Density");
    
    m_jDeadSaplingInsectDen.setBackground(SystemColor.control);
    m_jDeadSaplingInsectDen.setFont(new SortieFont());
    m_jDeadSaplingInsectDen.setText("Density");
    
    m_jDeadAdultInsectDen.setBackground(SystemColor.control);
    m_jDeadAdultInsectDen.setFont(new SortieFont());
    m_jDeadAdultInsectDen.setText("Density");
    
    m_jDeadSnagInsectDen.setBackground(SystemColor.control);
    m_jDeadSnagInsectDen.setFont(new SortieFont());
    m_jDeadSnagInsectDen.setText("Density");
    
    m_jDeadSaplingInsectBA.setBackground(SystemColor.control);
    m_jDeadSaplingInsectBA.setFont(new SortieFont());
    m_jDeadSaplingInsectBA.setText("Basal Area");
    
    m_jDeadAdultInsectBA.setBackground(SystemColor.control);
    m_jDeadAdultInsectBA.setFont(new SortieFont());
    m_jDeadAdultInsectBA.setText("Basal Area");
    
    m_jDeadSnagInsectBA.setBackground(SystemColor.control);
    m_jDeadSnagInsectBA.setFont(new SortieFont());
    m_jDeadSnagInsectBA.setText("Basal Area");
    
    m_jDeadSeedlingStormDen.setBackground(SystemColor.control);
    m_jDeadSeedlingStormDen.setFont(new SortieFont());
    m_jDeadSeedlingStormDen.setText("Density");
    
    m_jDeadSaplingStormDen.setBackground(SystemColor.control);
    m_jDeadSaplingStormDen.setFont(new SortieFont());
    m_jDeadSaplingStormDen.setText("Density");
    
    m_jDeadAdultStormDen.setBackground(SystemColor.control);
    m_jDeadAdultStormDen.setFont(new SortieFont());
    m_jDeadAdultStormDen.setText("Density");
    
    m_jDeadSnagStormDen.setBackground(SystemColor.control);
    m_jDeadSnagStormDen.setFont(new SortieFont());
    m_jDeadSnagStormDen.setText("Density");
    
    m_jDeadSaplingStormBA.setBackground(SystemColor.control);
    m_jDeadSaplingStormBA.setFont(new SortieFont());
    m_jDeadSaplingStormBA.setText("Basal Area");
    
    m_jDeadAdultStormBA.setBackground(SystemColor.control);
    m_jDeadAdultStormBA.setFont(new SortieFont());
    m_jDeadAdultStormBA.setText("Basal Area");
    
    m_jDeadSnagStormBA.setBackground(SystemColor.control);
    m_jDeadSnagStormBA.setFont(new SortieFont());
    m_jDeadSnagStormBA.setText("Basal Area");
    
    m_jDeadSeedlingDiseaseDen.setBackground(SystemColor.control);
    m_jDeadSeedlingDiseaseDen.setFont(new SortieFont());
    m_jDeadSeedlingDiseaseDen.setText("Density");
    
    m_jDeadSaplingDiseaseDen.setBackground(SystemColor.control);
    m_jDeadSaplingDiseaseDen.setFont(new SortieFont());
    m_jDeadSaplingDiseaseDen.setText("Density");
    
    m_jDeadAdultDiseaseDen.setBackground(SystemColor.control);
    m_jDeadAdultDiseaseDen.setFont(new SortieFont());
    m_jDeadAdultDiseaseDen.setText("Density");
    
    m_jDeadSnagDiseaseDen.setBackground(SystemColor.control);
    m_jDeadSnagDiseaseDen.setFont(new SortieFont());
    m_jDeadSnagDiseaseDen.setText("Density");
    
    m_jDeadSaplingDiseaseBA.setBackground(SystemColor.control);
    m_jDeadSaplingDiseaseBA.setFont(new SortieFont());
    m_jDeadSaplingDiseaseBA.setText("Basal Area");
    
    m_jDeadAdultDiseaseBA.setBackground(SystemColor.control);
    m_jDeadAdultDiseaseBA.setFont(new SortieFont());
    m_jDeadAdultDiseaseBA.setText("Basal Area");
    
    m_jDeadSnagDiseaseBA.setBackground(SystemColor.control);
    m_jDeadSnagDiseaseBA.setFont(new SortieFont());
    m_jDeadSnagDiseaseBA.setText("Basal Area");

  }

  /**
   * Creates the GUI.
   */
  private void createGUI() {
    
    setUpCheckboxes();
    
    // Tabbed panes for live and dead trees 
    JTabbedPane jTabbedPanel = new JTabbedPane();
    JPanel jDeadPanel = new JPanel();
    JPanel jLivePanel = new JPanel();
    jLivePanel.setLayout(new BoxLayout(jLivePanel, BoxLayout.PAGE_AXIS));
    jDeadPanel.setLayout(new BoxLayout(jDeadPanel, BoxLayout.PAGE_AXIS));
    jTabbedPanel.setFont(new SortieFont());
    jDeadPanel.setFont(new SortieFont());
    jLivePanel.setFont(new SortieFont());

    //Main panel 
    JPanel jMainPanel = new JPanel();
    jMainPanel.setLayout(new BoxLayout(jMainPanel, BoxLayout.PAGE_AXIS));

    //Label at the top
    SortieLabel jTempLabel = new SortieLabel("Set Up Summary Output File...", 
        SwingConstants.LEFT, java.awt.Font.BOLD, 2);
    jTempLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jMainPanel.add(jTempLabel);

    //Filename and browse button
    jTempLabel = new SortieLabel("Output File Name:", SwingConstants.LEFT);
    jTempLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    jTempPanel.add(jTempLabel);
    jMainPanel.add(jTempPanel);

    m_jSummaryOutputFileName.setFont(new SortieFont());
    m_jSummaryOutputFileName.setText("");
    m_jSummaryOutputFileName.setPreferredSize(new Dimension(300,
        (int) m_jSummaryOutputFileName.getPreferredSize().getHeight()));
    m_jSummaryOutputFileName.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    JButton jBrowseButton = new JButton("Browse");
    jBrowseButton.setFont(new SortieFont());
    jBrowseButton.addActionListener(this);
    jBrowseButton.setActionCommand("Browse");
    jBrowseButton.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel = new JPanel();
    jTempPanel.add(m_jSummaryOutputFileName);
    jTempPanel.add(jBrowseButton);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jMainPanel.add(jTempPanel);

    //Panel for seedlings
    jTempLabel = new SortieLabel("Seedlings", SwingConstants.LEFT, 
        java.awt.Font.BOLD, 0);
    jTempLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel = new JPanel();
    jTempPanel.setLayout(new BoxLayout(jTempPanel, BoxLayout.PAGE_AXIS));
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.add(jTempLabel);
    jTempPanel.add(m_jSeedlingAbsoluteDensityCheck);
    jLivePanel.add(jTempPanel);

    //Panel for sapling and adult options
    jTempPanel = new JPanel(new GridLayout(0, 3));
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.add(new SortieLabel("Saplings", SwingConstants.LEFT, java.awt.Font.BOLD, 0));
    jTempPanel.add(new SortieLabel("Adults", SwingConstants.LEFT, java.awt.Font.BOLD, 0));
    jTempPanel.add(new SortieLabel("Snags", SwingConstants.LEFT, java.awt.Font.BOLD, 0));
    jTempPanel.add(m_jSaplingRelativeBACheck);
    jTempPanel.add(m_jAdultRelativeBACheck);
    jTempPanel.add(m_jSnagRelativeBACheck);
    jTempPanel.add(m_jSaplingAbsoluteBACheck);
    jTempPanel.add(m_jAdultAbsoluteBACheck);
    jTempPanel.add(m_jSnagAbsoluteBACheck);
    jTempPanel.add(m_jSaplingRelativeDensityCheck);
    jTempPanel.add(m_jAdultRelativeDensityCheck);
    jTempPanel.add(m_jSnagRelativeDensityCheck);
    jTempPanel.add(m_jSaplingAbsoluteDensityCheck);
    jTempPanel.add(m_jAdultAbsoluteDensityCheck);
    jTempPanel.add(m_jSnagAbsoluteDensityCheck);
    jLivePanel.add(jTempPanel);
    
    //Button to set up subplots
    JButton jSubplotButton = new JButton("Set up subplots...");
    jSubplotButton.setFont(new SortieFont());
    jSubplotButton.setActionCommand("EditSubplots");
    jSubplotButton.addActionListener(this);
    jSubplotButton.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jLivePanel.add(jSubplotButton);
    
    //Panel with options for dead trees 
    //Natural mortality 
    jTempPanel = new JPanel(new GridLayout(3, 4));
    jTempPanel.add(new SortieLabel("Seedlings"));
    jTempPanel.add(new SortieLabel("Saplings"));
    jTempPanel.add(new SortieLabel("Adults"));
    jTempPanel.add(new SortieLabel("Snags"));
    jTempPanel.add(m_jDeadSeedlingNaturalDen);
    jTempPanel.add(m_jDeadSaplingNaturalDen);
    jTempPanel.add(m_jDeadAdultNaturalDen);
    jTempPanel.add(m_jDeadSnagNaturalDen);
    jTempPanel.add(new SortieLabel(" "));
    jTempPanel.add(m_jDeadSaplingNaturalBA);
    jTempPanel.add(m_jDeadAdultNaturalBA);
    jTempPanel.add(m_jDeadSnagNaturalBA);
    jTempPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
        .createLineBorder(Color.black), "Natural Mortality",
        TitledBorder.LEFT, TitledBorder.TOP, new SortieFont(Font.BOLD),
        Color.black));
    jDeadPanel.add(jTempPanel);
    
    //Harvest mortality 
    jTempPanel = new JPanel(new GridLayout(3, 4));
    jTempPanel.add(new SortieLabel("Seedlings"));
    jTempPanel.add(new SortieLabel("Saplings"));
    jTempPanel.add(new SortieLabel("Adults"));
    jTempPanel.add(new SortieLabel("Snags"));
    jTempPanel.add(m_jDeadSeedlingHarvestDen);
    jTempPanel.add(m_jDeadSaplingHarvestDen);
    jTempPanel.add(m_jDeadAdultHarvestDen);
    jTempPanel.add(m_jDeadSnagHarvestDen);
    jTempPanel.add(new SortieLabel(" "));
    jTempPanel.add(m_jDeadSaplingHarvestBA);
    jTempPanel.add(m_jDeadAdultHarvestBA);
    jTempPanel.add(m_jDeadSnagHarvestBA);
    jTempPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
        .createLineBorder(Color.black), "Harvest Mortality",
        TitledBorder.LEFT, TitledBorder.TOP, new SortieFont(Font.BOLD),
        Color.black));
    jDeadPanel.add(jTempPanel);
    
    //Storm mortality 
    jTempPanel = new JPanel(new GridLayout(3, 4));
    jTempPanel.add(new SortieLabel("Seedlings"));
    jTempPanel.add(new SortieLabel("Saplings"));
    jTempPanel.add(new SortieLabel("Adults"));
    jTempPanel.add(new SortieLabel("Snags"));
    jTempPanel.add(m_jDeadSeedlingStormDen);
    jTempPanel.add(m_jDeadSaplingStormDen);
    jTempPanel.add(m_jDeadAdultStormDen);
    jTempPanel.add(m_jDeadSnagStormDen);
    jTempPanel.add(new SortieLabel(" "));
    jTempPanel.add(m_jDeadSaplingStormBA);
    jTempPanel.add(m_jDeadAdultStormBA);
    jTempPanel.add(m_jDeadSnagStormBA);
    jTempPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
        .createLineBorder(Color.black), "Storm Mortality",
        TitledBorder.LEFT, TitledBorder.TOP, new SortieFont(Font.BOLD),
        Color.black));
    jDeadPanel.add(jTempPanel);
    
    //Insect mortality 
    jTempPanel = new JPanel(new GridLayout(3, 4));
    jTempPanel.add(new SortieLabel("Seedlings"));
    jTempPanel.add(new SortieLabel("Saplings"));
    jTempPanel.add(new SortieLabel("Adults"));
    jTempPanel.add(new SortieLabel("Snags"));
    jTempPanel.add(m_jDeadSeedlingInsectDen);
    jTempPanel.add(m_jDeadSaplingInsectDen);
    jTempPanel.add(m_jDeadAdultInsectDen);
    jTempPanel.add(m_jDeadSnagInsectDen);
    jTempPanel.add(new SortieLabel(" "));
    jTempPanel.add(m_jDeadSaplingInsectBA);
    jTempPanel.add(m_jDeadAdultInsectBA);
    jTempPanel.add(m_jDeadSnagInsectBA);
    jTempPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
        .createLineBorder(Color.black), "Insect Mortality",
        TitledBorder.LEFT, TitledBorder.TOP, new SortieFont(Font.BOLD),
        Color.black));
    jDeadPanel.add(jTempPanel);
    
    //Disease mortality 
    jTempPanel = new JPanel(new GridLayout(3, 4));
    jTempPanel.add(new SortieLabel("Seedlings"));
    jTempPanel.add(new SortieLabel("Saplings"));
    jTempPanel.add(new SortieLabel("Adults"));
    jTempPanel.add(new SortieLabel("Snags"));
    jTempPanel.add(m_jDeadSeedlingDiseaseDen);
    jTempPanel.add(m_jDeadSaplingDiseaseDen);
    jTempPanel.add(m_jDeadAdultDiseaseDen);
    jTempPanel.add(m_jDeadSnagDiseaseDen);
    jTempPanel.add(new SortieLabel(" "));
    jTempPanel.add(m_jDeadSaplingDiseaseBA);
    jTempPanel.add(m_jDeadAdultDiseaseBA);
    jTempPanel.add(m_jDeadSnagDiseaseBA);
    jTempPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
        .createLineBorder(Color.black), "Disease Mortality",
        TitledBorder.LEFT, TitledBorder.TOP, new SortieFont(Font.BOLD),
        Color.black));
    jDeadPanel.add(jTempPanel);
    
    JScrollPane jScroller = new JScrollPane(jDeadPanel);
    jScroller.setPreferredSize(jLivePanel.getPreferredSize());
    
    jTabbedPanel.add(jLivePanel, "Live Trees");
    jTabbedPanel.add(jScroller, "Dead Trees");
    jTabbedPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jMainPanel.add(jTabbedPanel);
    
    //Select all and clear all buttons
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton jButton = new JButton("Select all live");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Select all live");
    jButton.addActionListener(this);
    jTempPanel.add(jButton);
    
    jButton = new JButton("Select all dead");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Select all dead");
    jButton.addActionListener(this);
    jTempPanel.add(jButton);
    
    jButton = new JButton("Clear all live");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Clear all live");
    jButton.addActionListener(this);
    jTempPanel.add(jButton);
    
    jButton = new JButton("Clear all dead");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Clear all dead");
    jButton.addActionListener(this);
    jTempPanel.add(jButton);
    
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jMainPanel.add(jTempPanel);

    jMainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(jMainPanel, BorderLayout.CENTER);

    this.getContentPane().add(new OKCancelButtonPanel(this,
        m_oWindow.m_oHelpBroker, m_sHelpID), BorderLayout.SOUTH);

  }
  
  
  /**
   * Selects/deselects all checkboxes for live trees.
   */
  private void setSelectedAllLive(boolean bSelected) {
    m_jSeedlingAbsoluteDensityCheck.setSelected(bSelected);
    m_jSaplingRelativeBACheck.setSelected(bSelected);
    m_jSaplingAbsoluteBACheck.setSelected(bSelected);
    m_jSaplingRelativeDensityCheck.setSelected(bSelected);
    m_jSaplingAbsoluteDensityCheck.setSelected(bSelected);
    m_jAdultRelativeBACheck.setSelected(bSelected);
    m_jAdultAbsoluteBACheck.setSelected(bSelected);
    m_jAdultRelativeDensityCheck.setSelected(bSelected);
    m_jAdultAbsoluteDensityCheck.setSelected(bSelected);
    m_jSnagRelativeBACheck.setSelected(bSelected);
    m_jSnagAbsoluteBACheck.setSelected(bSelected);
    m_jSnagRelativeDensityCheck.setSelected(bSelected);
    m_jSnagAbsoluteDensityCheck.setSelected(bSelected);
  }
  
  /**
   * Selects/deselects all checkboxes for dead trees.
   */
  private void setSelectedAllDead(boolean bSelected) {
    m_jDeadSeedlingHarvestDen.setSelected(bSelected);
    m_jDeadSaplingHarvestDen.setSelected(bSelected);
    m_jDeadAdultHarvestDen.setSelected(bSelected);
    m_jDeadSnagHarvestDen.setSelected(bSelected);
    m_jDeadSaplingHarvestBA.setSelected(bSelected);
    m_jDeadAdultHarvestBA.setSelected(bSelected);
    m_jDeadSnagHarvestBA.setSelected(bSelected);
    m_jDeadSeedlingNaturalDen.setSelected(bSelected);
    m_jDeadSaplingNaturalDen.setSelected(bSelected);
    m_jDeadAdultNaturalDen.setSelected(bSelected);
    m_jDeadSnagNaturalDen.setSelected(bSelected);
    m_jDeadSaplingNaturalBA.setSelected(bSelected);
    m_jDeadAdultNaturalBA.setSelected(bSelected);
    m_jDeadSnagNaturalBA.setSelected(bSelected);
    m_jDeadSeedlingInsectDen.setSelected(bSelected);
    m_jDeadSaplingInsectDen.setSelected(bSelected);
    m_jDeadAdultInsectDen.setSelected(bSelected);
    m_jDeadSnagInsectDen.setSelected(bSelected);
    m_jDeadSaplingInsectBA.setSelected(bSelected);
    m_jDeadAdultInsectBA.setSelected(bSelected);
    m_jDeadSnagInsectBA.setSelected(bSelected);
    m_jDeadSeedlingStormDen.setSelected(bSelected);
    m_jDeadSaplingStormDen.setSelected(bSelected);
    m_jDeadAdultStormDen.setSelected(bSelected);
    m_jDeadSnagStormDen.setSelected(bSelected);
    m_jDeadSaplingStormBA.setSelected(bSelected);
    m_jDeadAdultStormBA.setSelected(bSelected);
    m_jDeadSnagStormBA.setSelected(bSelected);
    m_jDeadSeedlingDiseaseDen.setSelected(bSelected);
    m_jDeadSaplingDiseaseDen.setSelected(bSelected);
    m_jDeadAdultDiseaseDen.setSelected(bSelected);
    m_jDeadSnagDiseaseDen.setSelected(bSelected);
    m_jDeadSaplingDiseaseBA.setSelected(bSelected);
    m_jDeadAdultDiseaseBA.setSelected(bSelected);
    m_jDeadSnagDiseaseBA.setSelected(bSelected);
  }
}
