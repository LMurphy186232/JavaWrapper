package sortie.data.funcgroups.disturbance;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JDialog;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelString;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.behaviorsetup.HarvestInterfaceSetup;
import sortie.parfile.ParFileHelpers;

/**
 * Corresponds to the clHarvestInterface class.
 * @author lora
 */
public class HarvestInterface extends Behavior {
 
  /** Path and filename of user executable */
  public ModelString m_sHarvIntExecutable = new ModelString("", "Path and filename of the executable:",
  "hi_executable");

  /** Path and filename of file for SORTIE to write with harvestable trees */
  public ModelString m_sHarvIntSORTIEOutFile = new ModelString("", "Tree file that SORTIE will write:",
  "hi_harvestableTreesFile");

  /** Path and filename of file for executable to write with trees to harvest */
  public ModelString m_sHarvIntExecHarvestOutFile = new ModelString("", 
      "Tree harvest file that the executable will write:",
  "hi_treesToHarvestFile");

  /** Path and filename of file for executable to write with trees to update */
  public ModelString m_sHarvIntExecUpdateOutFile = new ModelString("", 
      "Tree update file that the executable will write:",
  "hi_treesToUpdateFile");

  /** Path and filename of batch parameters */
  public ModelString m_sHarvIntBatchParamsFile = new ModelString("", "Parameters file for batch run (optional):",
  "hi_batchParamsFile");

  /** Path and filename of single-run parameters file to write */
  public ModelString m_sHarvIntBatchSingleRunParamsFile = new ModelString(
      "", "Single-run parameters file for batch run (optional):", "hi_batchSingleRunParamsFile");

  /** Arguments to pass to executable */
  public ModelString m_sHarvIntExecArgs = new ModelString("", 
      "Arguments to pass to the executable (optional):",
  "hi_executableArguments");

  /** List of new tree data members to add */
  public ArrayList<String> mp_sHarvIntNewTreeDataMembers = new ArrayList<String>(0);

  /** List of file columns */
  public ModelVector mp_sHarvIntFileColumns = new ModelVector("File columns:",
      "hi_dataMembers", "hi_dataMember", 0, ModelVector.STRING);

  /** How often harvests occur, in years */
  public ModelInt m_iHarvIntHarvestPeriod = new ModelInt(1, "How often to harvest, in years:",
  "hi_harvestPeriod");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @throws ModelException Won't throw.
   */
  public HarvestInterface(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disturbance_behaviors.harvest_interface");

    m_iBehaviorSetupType = setupType.custom_display;
    m_bMustHaveTrees = true;
    
    //This is just because the setup window is not set up for multiples
    m_bCanBeDuplicated = false;

    mp_sHarvIntFileColumns.setIsSpeciesSpecific(false);

    addRequiredData(m_sHarvIntExecutable);
    addRequiredData(m_sHarvIntSORTIEOutFile);
    addRequiredData(m_sHarvIntExecHarvestOutFile);
    addRequiredData(m_sHarvIntExecUpdateOutFile);
    addRequiredData(m_sHarvIntBatchParamsFile);
    addRequiredData(m_sHarvIntBatchSingleRunParamsFile);
    addRequiredData(m_sHarvIntExecArgs);
    addRequiredData(mp_sHarvIntFileColumns);
    addRequiredData(m_iHarvIntHarvestPeriod);

  }

  public void validateData(TreePopulation oPop) throws ModelException {;}


  /**
   * Writes the harvest interface data for the parameter file. Does nothing if
   * the harvest interface behavior is not being used.
   * 
   * @param jOut FileWriter File to write to.
   * @param oPop TreePopulation object.
   * @throws ModelException if there is a problem writing the file
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop) throws
  ModelException {
    try {
      
      jOut.write("<" + m_sXMLRootString + m_iListPosition + ">");

      // Our parameters are not "assigned" to any behavior, so we have to use the
      // WorkerBase static method to write them. Simply using WriteData would
      // prevent them from being written, since it belongs to BehaviorTypeBase and
      // checks to make sure data is applied to an enabled behavior.

      // User executable filename
      writeDataToFile(jOut, m_sHarvIntExecutable);

      // Filename of harvestable trees for SORTIE to write
      writeDataToFile(jOut, m_sHarvIntSORTIEOutFile);

      // Filename of trees to harvest for the user executable
      writeDataToFile(jOut, m_sHarvIntExecHarvestOutFile);

      // How often harvests occur
      writeDataToFile(jOut, m_iHarvIntHarvestPeriod);

      // Filename of trees to update for the user executable to write, if being
      // used
      if (m_sHarvIntExecUpdateOutFile.getValue().length() > 0) {
        writeDataToFile(jOut, m_sHarvIntExecUpdateOutFile);
      }

      // Filename of batch parameters, if being used
      if (m_sHarvIntBatchParamsFile.getValue().length() > 0) {
        writeDataToFile(jOut, m_sHarvIntBatchParamsFile);
        writeDataToFile(jOut, m_sHarvIntBatchSingleRunParamsFile);
      }

      // Arguments to pass to user executable, if being used
      if (m_sHarvIntExecArgs.getValue().length() > 0) {
        writeDataToFile(jOut, m_sHarvIntExecArgs);
      }

      // List of tree data members to include in the files beyond the required
      // list, if being used
      if (mp_sHarvIntFileColumns.getValue().size() > 0) {
        ParFileHelpers.writeVectorValueToFile(jOut, mp_sHarvIntFileColumns);
      }

      jOut.write("</" + m_sXMLRootString + m_iListPosition + ">");
    } catch (java.io.IOException oExp) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
      "There was a problem writing the parameter file."));
    }
  }

  /**
   * If the harvest interface behavior is being used, this will check to see if
   * it needs to add new data members to the name. After changing the name and
   * writing the behaviors, the name will be changed back.
   * 
   * @param out BufferedWriter File to write to.
   * @param oPop TreePopulation Tree population.
   * @throws ModelException Passing through from called functions.
   */
  public void writeBehaviorNode(BufferedWriter out, TreePopulation oPop)
  throws ModelException {
    boolean bDoIt = mp_sHarvIntNewTreeDataMembers.size() > 0;
    String sXMLName = "";
    if (bDoIt) {
      sXMLName = m_sParFileTag;
      for (int i = 0; i < mp_sHarvIntNewTreeDataMembers.size(); i++) {
        m_sParFileTag += " ("
          + mp_sHarvIntNewTreeDataMembers.get(i) + ")";
      }
    }

    super.writeBehaviorNode(out, oPop);

    if (bDoIt)
      m_sParFileTag = sXMLName;
  }
  
  /**
   * Clear new tree data members.
   */
  public void clearNewTreeDataMembers() {
    mp_sHarvIntNewTreeDataMembers.clear();
    mp_oNewTreeDataMembers.clear();
  }
  
  /**
   * Adds a new tree data member.
   * @param sNew New data member.
   */
  public void addNewTreeDataMember(String sNew) throws ModelException {
    mp_sHarvIntNewTreeDataMembers.add(sNew);
    mp_oNewTreeDataMembers.add(new DataMember(sNew, sNew, DataMember.FLOAT));
  }

  /**
   * Calls the harvest interface dialog
   */
  public void callSetupDialog(JDialog jParent, MainWindow oMain) {
    try {
      HarvestInterfaceSetup oWindow = new HarvestInterfaceSetup(oMain, this);
      oWindow.pack();
      oWindow.setLocationRelativeTo(null);
      oWindow.setVisible(true);      
    } catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(jParent);
      oHandler.writeErrorMessage(oErr);
    }
  }

  /**
   * Overridden to write parameters.
   */
  public void writeParametersToTextFile(FileWriter jOut, TreePopulation oPop)
      throws IOException {
    
    jOut.write("\n" + m_sDescriptor + "\n");
    jOut.write(getAppliedToForDisplay(oPop) + "\n");

    jOut.write(m_sHarvIntExecutable.getDescriptor() + "\t" + m_sHarvIntExecutable.getValue() + "\n");
    jOut.write(m_sHarvIntSORTIEOutFile.getDescriptor() + "\t" + m_sHarvIntSORTIEOutFile.getValue() + "\n");
    jOut.write(m_sHarvIntExecHarvestOutFile.getDescriptor() + "\t" + m_sHarvIntExecHarvestOutFile.getValue() + "\n");
    jOut.write(m_iHarvIntHarvestPeriod.getDescriptor() + "\t" + m_iHarvIntHarvestPeriod.getValue() + "\n");

    if (m_sHarvIntExecUpdateOutFile.getValue().length() > 0) {
      jOut.write(m_sHarvIntExecUpdateOutFile.getDescriptor() + "\t" + m_sHarvIntExecUpdateOutFile.getValue() + "\n");      
    }

    if (m_sHarvIntBatchParamsFile.getValue().length() > 0) {
      jOut.write(m_sHarvIntBatchParamsFile.getDescriptor() + "\t" + m_sHarvIntBatchParamsFile.getValue() + "\n");
      jOut.write(m_sHarvIntBatchSingleRunParamsFile.getDescriptor() + "\t" + m_sHarvIntBatchSingleRunParamsFile.getValue() + "\n");
    }

    if (m_sHarvIntExecArgs.getValue().length() > 0) {
      jOut.write(m_sHarvIntExecArgs.getDescriptor() + "\t" + m_sHarvIntExecArgs.getValue() + "\n");
    }

    if (mp_sHarvIntFileColumns.getValue().size() > 0) {
      jOut.write(mp_sHarvIntFileColumns.getDescriptor() + "\n");
      for (int i = 0; i < mp_sHarvIntFileColumns.getValue().size(); i++)
        jOut.write(mp_sHarvIntFileColumns.getValue().get(i) + "\n");      
    }
    
    if (mp_sHarvIntNewTreeDataMembers.size() > 0) {
      jOut.write("New tree data members:\n");
      for (String sVal : mp_sHarvIntNewTreeDataMembers) jOut.write(sVal + "\n");
    }
  }
}
