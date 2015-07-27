package sortie.data.funcgroups.planting;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JDialog;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreeBehavior;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.Cell;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.harvepplant.PlantingDisplayWindow;

/**
 * Corresponds to the clPlant class.
 * @author lora
 */
public class Planting extends Behavior { 
  
  /**Gridded planting.  Matches old code enum value - important!*/
  public static final int GRIDDED = 1;
  /**Random planting.  Matches old code enum value - important!*/
  public static final int RANDOM = 0;

  /**Our array of plantings*/
  protected ArrayList<PlantingData> mp_oPlantings = new ArrayList<PlantingData>(0);

  /**List of diam10 values for newly planted trees - one for each species*/
  /**Slope of growth response for each species*/
  public ModelVector mp_fInitialDiam10 = new ModelVector(
      "Avg. diameter at 10 cm for new seedlings",
      "pl_initialDiam10",
      "pl_idVal", 0,
      ModelVector.FLOAT);

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @throws ModelException 
   */
  public Planting(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "planting_behaviors.planting_behaviors");

    m_bCanBeDuplicated = false;
    m_bMustHaveTrees = false;
    addRequiredData(mp_fInitialDiam10);
    m_iBehaviorSetupType = setupType.custom_display;
    
    //Default the initial diameter at 10 cm values to the tree population's
    //value
    TreePopulation oPop = m_oManager.getTreePopulation();
    int iNumSpecies = oPop.getNumberOfSpecies(), i;
    Float[] p_fDiam10s = new Float[iNumSpecies];
    TreeBehavior oPopBeh = oPop.getTreeBehavior();
    float fDiam10 = oPopBeh.getNewSeedlingDiam10();
    for (i = 0; i < iNumSpecies; i++) {
      p_fDiam10s[i] = new Float(fDiam10);
    }
    setVectorValues(mp_fInitialDiam10, p_fDiam10s);
    String[] p_sSpeciesNames = new String[iNumSpecies];
    for (i = 0; i < p_sSpeciesNames.length; i++) 
      p_sSpeciesNames[i] = oPop.getSpeciesNameFromCode(i);
    gridSetup(p_sSpeciesNames);
  }
  
  public int getNumberPlantingEvents() {return mp_oPlantings.size();}
  public PlantingData getPlantingEvent(int iIndex) {return mp_oPlantings.get(iIndex);}
  public void clearPlantingEvents() {mp_oPlantings.clear();}
  public void addPlantingEvent(PlantingData oPlanting) {mp_oPlantings.add(oPlanting);}


  /**
   * This adds the data contained in a PlantingData object to a list of
   * planting data.  If there is data to be added, it is compared to existing
   * PlantingData objects on the list.  If they match except for the grid cell,
   * then the new object's grid cell is added to the existing object and the
   * new object thrown away.  If it is truly new data, it is added to the list.
   * 
   * IMPORTANT:  This assumes that abundances will be in species order, as they
   * should be if reading a harvest regime file.
   * @param oList The list of PlantingData objects to add to.
   * @param oNewPlanting The PlantingData object to add.
   * @return The new list with the data added.
   * @throws ModelException Passing through underlying exceptions.
   */
  public static ArrayList<PlantingData> addPlantingData(ArrayList<PlantingData> oList, PlantingData oNewPlanting) throws
      ModelException {
    PlantingData oExistingPlanting;
    int i, j, iNumSpecies;
    boolean bMatch;
    if (null == oNewPlanting) {
      return oList;
    }
    //If the list is currently null, create it
    if (null == oList) {
      oList = new ArrayList<PlantingData>(0);
      oList.add(oNewPlanting);
      return oList;
    }
    for (i = 0; i < oList.size(); i++) {
      oExistingPlanting = oList.get(i);
      //Compare all the data
      if (oExistingPlanting.getPlantSpacing() == oNewPlanting.getPlantSpacing() &&
          oExistingPlanting.getTimestep() == oNewPlanting.getTimestep() &&
          oExistingPlanting.getSpacingDistance() ==
          oNewPlanting.getSpacingDistance() &&
          oExistingPlanting.getNumberOfSpecies() ==
          oNewPlanting.getNumberOfSpecies()) {

        //Do all the species and their abundances match?
        bMatch = true;
        iNumSpecies = oExistingPlanting.getNumberOfSpecies();
        for (j = 0; j < iNumSpecies; j++) {
          if (oExistingPlanting.getSpecies(j) != oNewPlanting.getSpecies(j) ||
              oExistingPlanting.getAbundance(oExistingPlanting.getSpecies(j)) !=
              oNewPlanting.getAbundance(oNewPlanting.getSpecies(j))) {
            bMatch = false;
            break;
          }
        }

        //They are the same except for cells - add this one's grid cell to the
        //existing list
        if (bMatch) {
          for (j = 0; j < oNewPlanting.getNumberOfCells(); j++) {
            oExistingPlanting.addCell(oNewPlanting.getCell(j));
          }
          return oList;
        }
      }
    }
    //If we've gotten to here, then the new object's data is unique and should
    //be added.
    oList.add(oNewPlanting);
    return oList;
  }

  /**
   * Validates the data prior to writing it.  It causes all PlantingData
   * objects to validate themselves.
   * @param oPop TreePopulation object
   * @throws ModelException if one of the data objects is not valid.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    PlantingData oData;
    Plot oPlot = m_oManager.getPlot();
    int i, iNumObjects = mp_oPlantings.size();
    for (i = 0; i < iNumObjects; i++) {
      oData = mp_oPlantings.get(i);
      oData.validatePlant(oPop, oPlot);
    }
  }

  /**
   * Overridden because planting parameters are special.
   * @param jOut File to write to.
   * @param oPop TreePopulation object.
   * @throws ModelException if there's a problem writing the file.
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop) throws
      ModelException {
    try {
      PlantingData oData;
      Cell oCell;
      int i, iNumObjects = mp_oPlantings.size(), j, iTemp;
      if (iNumObjects == 0) {
        return;
      }

      jOut.write("<" + m_sXMLRootString + m_iListPosition + ">");

      //Write out the initial diam10s - they may not have been changed but
      //it won't hurt to write them out anyway in that case
      boolean[] p_bUse = new boolean[oPop.getNumberOfSpecies()];
      for (i = 0; i < p_bUse.length; i++) {p_bUse[i] = true;};
      writeSpeciesSpecificValue(jOut, mp_fInitialDiam10, oPop, p_bUse);


      //Write each planting object's data
      for (i = 0; i < iNumObjects; i++) {
        oData = mp_oPlantings.get(i);
        jOut.write("<pl_plantEvent>");
        //Write each species
        iTemp = oData.getNumberOfSpecies();
        for (j = 0; j < iTemp; j++) {
          jOut.write("<pl_applyToSpecies species=\"" +
                     oPop.getSpeciesNameFromCode(oData.getSpecies(j)) + "\"/>");
        }
        //Write the timestep
        jOut.write("<pl_timestep>" + String.valueOf(oData.getTimestep()) +
                   "</pl_timestep>");

        //Write the plant spacing type
        jOut.write("<pl_spaceType>");
        iTemp = oData.getPlantSpacing();
        if (iTemp == GRIDDED) {
          jOut.write("gridded");
        }
        else if (iTemp == RANDOM) {
          jOut.write("random");
        }
        else {
          throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "When writing parameter file, came across unrecognized " +
              "planting type \"" + String.valueOf(iTemp) + "\"."));
        }
        jOut.write("</pl_spaceType>");

        //Write the distance or spacing
        jOut.write("<pl_distanceOrDensity>" +
                   String.valueOf(oData.getSpacingDistance()) +
                   "</pl_distanceOrDensity>");

        //Write the amounts to plant
        jOut.write("<pl_amountToPlant>");

        iTemp = oData.getNumberOfSpecies();
        for (j = 0; j < iTemp; j++) {

          jOut.write("<pl_atpVal species=\"" +
                     oPop.getSpeciesNameFromCode(oData.getSpecies(j)) + "\">" +
                     oData.getAbundance(oData.getSpecies(j)) +
                     "</pl_atpVal>");
        }
        jOut.write("</pl_amountToPlant>");

        //Write the list of cells
        iTemp = oData.getNumberOfCells();
        for (j = 0; j < iTemp; j++) {
          oCell = (Cell) oData.getCell(j);
          jOut.write("<pl_applyToCell x=\"" + String.valueOf(oCell.getX())
                     + "\" y=\"" + String.valueOf(oCell.getY()) + "\"/>");
        }
        jOut.write("</pl_plantEvent>");
      }
      jOut.write("</" + m_sXMLRootString + m_iListPosition + ">");
    }
    catch (java.io.IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
                            "There was a problem writing the parameter file."));
    }
  }

  /**
   * Sets up the planting results grid.
   * @param p_sSpeciesNames Array of species names.
   * @throws ModelException If anything goes wrong.
   */
  private void gridSetup(String[] p_sSpeciesNames) throws ModelException {
    int iNumSpecies = p_sSpeciesNames.length,
        i;

    DataMember[] p_oDataMembers = new DataMember[iNumSpecies];
    String sGridName = "Planting Results";

    //The accessible data members are one for each cut range and species
    for (i = 0; i < iNumSpecies; i++) {

      p_oDataMembers[i] = new DataMember("Planted " +
                                         p_sSpeciesNames[i].replace('_', ' '),
                                         "planted" + i,
                                         DataMember.INTEGER);

    }
    
    //Create our grid    
    if (getNumberOfGrids() == 0) {
      Grid oNewGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);
      oNewGrid = m_oManager.addGrid(oNewGrid, true);
      addGrid(oNewGrid);
    } else {
      Grid oGrid = getGrid(0);
      oGrid.setDataMembers(p_oDataMembers, null);
    }
  }

  /**
   * Sets a data vector's value.  This function looks for the parent tag
   * "pl_amountToPlant".
   * @param sXMLTag Parent XML tag of data vector whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param p_oData Vector of data values appropriate to the data type
   * @param p_sChildXMLTags The XML tags of the child elements
   * @param p_bAppliesTo Array of booleans saying which of the vector values
   * should be set.  This is important in the case of species-specifics - the
   * vector index is the species number but not all species are set.
   * @param oParentAttributes Attributes of parent tag.  May be useful when
   * overridding this for unusual tags.
   * @param p_oAttributes Attributes passed from parser.  This may be needed
   * when overriding this function.  Basic species-specific values are
   * already handled by this function.
   * @return true if the value was set successfully; false if the value could
   * not be found.  (This would not be an error, because I need a way to
   * cycle through the objects until one of the objects comes up with a
   * match.)  If a match to a data object is made via XML tag, but the found
   * object is not a ModelVector, this returns false.
   * @throws ModelException if the value could not be assigned to the data
   * object.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
                                        ArrayList<String> p_oData,
                                        String[] p_sChildXMLTags,
                                        boolean[] p_bAppliesTo,
                                        Attributes oParentAttributes,
                                        Attributes[] p_oAttributes) throws
      ModelException {
    if (sXMLTag.equals("pl_amountToPlant")) {

      //Get the last planting event
      PlantingData oPlanting = mp_oPlantings.get(mp_oPlantings.size() - 1);

      int i;
      for (i = 0; i < p_oData.size(); i++) {
        Object oData = p_oData.get(i);
        if (oData != null) {
          float fValue = new Float(String.valueOf(oData)).floatValue();
          oPlanting.addAbundance(i, fValue);
        }
      }
      return true;
    } else {
      return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
                                        p_sChildXMLTags, p_bAppliesTo,
                                        oParentAttributes, p_oAttributes);

    }
  }


  /**
   * Accepts an XML parent tag (empty, no data) from the parser.  This method
   * watches for the following tags:
   * <ul>
   * <li>pl_applyToCell
   * <li>pl_applyToSpecies
   * <li>pl_plantEvent
   * </ul>
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if there is a problem reading this data.
   */
  public void readXMLParentTag(String sXMLTag, Attributes oAttributes) throws
      ModelException {

    if (sXMLTag.equals("pl_applyToCell")) {

      //Get the X and Y values
      int iX = new Integer(oAttributes.getValue("x")).intValue(),
          iY = new Integer(oAttributes.getValue("y")).intValue();

      //Assign them to the newest planting event
      PlantingData oPlanting = mp_oPlantings.get(mp_oPlantings.size() - 1);
      oPlanting.addCell(iX, iY, m_oManager.getPlot());

    }
    else if (sXMLTag.equals("pl_applyToSpecies")) {

      //Extract species
      TreePopulation oPop = m_oManager.getTreePopulation();
      int iSpecies = oPop.getSpeciesCodeFromName(oAttributes.getValue("species"));

      //Get the last planting event
      PlantingData oPlanting = mp_oPlantings.get(mp_oPlantings.size() - 1);

      //Add this species to that event
      oPlanting.addSpecies(iSpecies);

    }
    else if (sXMLTag.equals("pl_plantEvent")) {
      Grid oGrid = m_oManager.getGridByName("Planting Results");

      //Create a new planting event
      PlantingData oPlanting = new PlantingData(oGrid.getXCellLength(),
          oGrid.getYCellLength());
      mp_oPlantings.add(oPlanting);
    }
    else if (sXMLTag.equals("plant")) {

      //Enable the planting behavior
      //m_bIsEnabled = true;
    }
    super.readXMLParentTag(sXMLTag, oAttributes);
  }

  /**
   * This method looks for the following tags:
   * <ul>
   * <li>pl_timestep
   * <li>pl_spaceType
   * <li>pl_distanceOrDensity
   * </ul>
   * @param sXMLTag XML tag of data object whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param oAttributes Attributes of the object.  Ignored, but may be needed
   * by overriding objects.
   * @param oData Data value appropriate to the data type
   * @return true if the value was set successfully; false if the value could
   * not be found.  (This would not be an error, because I need a way to
   * cycle through the objects until one of the objects comes up with a
   * match.)
   * @throws ModelException if the value could not be assigned to the data
   * object, or if the cut type or cut type amount values are unrecognized.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
                                        Attributes oAttributes,
                                        Object oData) throws
      ModelException {
    if (sXMLTag != null) {
      if (sXMLTag.equals("pl_timestep")) {

        //Get the timestep value
        int iTimestep = new Integer(String.valueOf(oData)).intValue();

        //Get the last planting event and set this as the timestep
        PlantingData oPlanting = mp_oPlantings.get(mp_oPlantings.size() - 1);
        oPlanting.setTimestep(iTimestep);

        return true;
      }
      else if (sXMLTag.equals("pl_spaceType")) {

        //Get the space type string
        String sSpaceType = String.valueOf(oData);

        //Get the last planting event
        PlantingData oPlanting = mp_oPlantings.get(mp_oPlantings.size() - 1);

        if (sSpaceType.equalsIgnoreCase("gridded")) {
          oPlanting.setPlantSpacing(GRIDDED);
        }
        else if (sSpaceType.equalsIgnoreCase("random")) {
          oPlanting.setPlantSpacing(RANDOM);
        }
        else {
          //Unrecognized - error
          throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Planting event in file has an unrecognized plant spacing type - " +
              sSpaceType + "."));
        }

        return true;
      }
      else if (sXMLTag.equals("pl_distanceOrDensity")) {

        //Get the spacing or density value
        float fValue = new Float(String.valueOf(oData)).floatValue();

        //Get the last planting event and set this as the density - it's the
        //same as setting this as the distance if it's gridded
        PlantingData oPlanting = mp_oPlantings.get(mp_oPlantings.size() - 1);
        oPlanting.setDensity(fValue);

        return true;
      }
    }
    return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes, oData);

  }

  /**
   * Checks planting settings upon change of species.  This will
   * remove any deleted species.
   * @param iOldNumSpecies says how many species there used to be.
   * @param p_iIndexer is an array, sized to the new number of species.  For
   * each bucket (representing the index number of a species on the new list),
   * the value is either the index of that same species in the old species
   * list, or -1 if the species is new.
   * @param p_sNewSpecies The new species list.
   * @throws ModelException if anything goes wrong (not thrown by this function).
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
                              String[] p_sNewSpecies) throws ModelException {

    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);

    PlantingData oPlanting;
    int[] p_iOldCutSpecies;
    int i, j, k;

    //Go through all the harvests and check for species that have been removed
    for (i = 0; i < mp_oPlantings.size(); i++) {
      oPlanting = mp_oPlantings.get(i);
      //Make a list of the species for this cut
      p_iOldCutSpecies = new int[oPlanting.getNumberOfSpecies()];
      for (j = 0; j < p_iOldCutSpecies.length; j++) {
        p_iOldCutSpecies[j] = oPlanting.getSpecies(j);
      }
      //Now remove all the old species
      oPlanting.clearSpecies();

      //Now add back the species from the copy we saved
      for (j = 0; j < p_iOldCutSpecies.length; j++) {

        //Look for this species in the new species list - add the new species
        //number if we find it
        for (k = 0; k < p_iIndexer.length; k++) {
          if (p_iIndexer[k] == p_iOldCutSpecies[j]) {
            //Yep, this species is still on the list - change the species
            //number
            oPlanting.addSpecies(k);
            break;
          }
        }
      }

      //If this harvest event has no species left, delete it
      if (oPlanting.getNumberOfSpecies() == 0) {
        mp_oPlantings.remove(i);
        i--;
      }
    }
    
    gridSetup(p_sNewSpecies);
  }

  /**
  * Updates planting events when species are copied.
  * @param iSpeciesCopyFrom int Species to copy.
  * @param iSpeciesCopyTo int Species that is the copy.
  * @throws ModelException if there is a problem.
  */
  public void copySpecies (int iSpeciesCopyFrom, int iSpeciesCopyTo) throws ModelException {
    super.copySpecies(iSpeciesCopyFrom, iSpeciesCopyTo);

    PlantingData oPlanting;
    int[] p_iOldCutSpecies;
    int i, j;

    //Go through all the harvests and remove the species that is becoming the
    //copy
    for (i = 0; i < mp_oPlantings.size(); i++) {
      oPlanting = mp_oPlantings.get(i);
      //Make a list of the species for this cut
      p_iOldCutSpecies = new int[oPlanting.getNumberOfSpecies()];
      for (j = 0; j < p_iOldCutSpecies.length; j++) {
        p_iOldCutSpecies[j] = oPlanting.getSpecies(j);
      }
      //Remove all species
      oPlanting.clearSpecies();

      //Add back the non-copied species - and add the copied species if
      //present
      for (j = 0; j < p_iOldCutSpecies.length; j++) {
        if (iSpeciesCopyTo != p_iOldCutSpecies[j]) {
          oPlanting.addSpecies(p_iOldCutSpecies[j]);
        }
        if (iSpeciesCopyFrom == p_iOldCutSpecies[j]) {
          oPlanting.addSpecies(iSpeciesCopyTo);
        }
      }

      //If this harvest event has no species left, delete it
      if (oPlanting.getNumberOfSpecies() == 0) {
        mp_oPlantings.remove(i);
        i--;
      }
    }
  }

  /**
   * Removes cells from planting events that are outside the
   * plot.
   * @param fOldX float Old plot X length.
   * @param fOldY float Old plot Y length.
   * @param fNewX float New plot X length.
   * @param fNewY float New plot Y length.
   * @throws ModelException if anything goes wrong.
   */
  public void changeOfPlotResolution(float fOldX, float fOldY, float fNewX,
                                   float fNewY) throws ModelException {

    //If the plot has shrunk, check for cells now outside the plot
    if (fOldX > fNewX || fOldY > fNewY) {
      PlantingData oPlant;
      Cell oCell;
      float fCellLength = (float)8.0;
      int iNewMaxX = (int) java.lang.Math.ceil(fNewX / fCellLength),
          iNewMaxY = (int) java.lang.Math.ceil(fNewY / fCellLength),
          i, j;

      for (i = 0; i < mp_oPlantings.size(); i++) {
        oPlant = mp_oPlantings.get(i);
        for (j = 0; j < oPlant.getNumberOfCells(); j++) {
          oCell = (Cell) oPlant.getCell(j);
          if (oCell.getX() >= iNewMaxX || oCell.getY() >= iNewMaxY) {
            oPlant.removeCell(j);
            j--;
          }
        }
        //If there's no cells left remove this harvest episode
        if (oPlant.getNumberOfCells() == 0) {
          mp_oPlantings.remove(i);
          i--;
        }
      }
    }
  }
  
  /**
   * Call the episodic events dialog
   */
  public void callSetupDialog(JDialog jParent, MainWindow oMain) {
    try {
      PlantingDisplayWindow jWindow = new PlantingDisplayWindow(oMain,
          m_oManager.getDisturbanceBehaviors(), m_oManager.getPlantingBehaviors());
      jWindow.pack();

      //Resize if too big
      if (jWindow.getSize().height > oMain.getSize().height ||
          jWindow.getSize().width > oMain.getSize().width) {

        int iWidth = java.lang.Math.min(jWindow.getSize().width,
            oMain.getSize().width - 10);
        int iHeight = java.lang.Math.min(jWindow.getSize().height,
            oMain.getSize().height - 10);

        jWindow.setBounds(jWindow.getBounds().x, jWindow.getBounds().y,
            iWidth, iHeight);
      }
      jWindow.setLocationRelativeTo(null);
      jWindow.setVisible(true);
    } catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(jParent);
      oHandler.writeErrorMessage(oErr);
    }    
  }
  
  /**
   * Override to write something useful to the parameters file. The cells list
   * is a bit long but everything else can go in there.
   */
  public void writeParametersToTextFile(FileWriter jOut, TreePopulation oPop)
      throws IOException {
    try {
      jOut.write("\n" + m_sDescriptor + "\n");
      if (mp_oPlantings.size() == 0) {
        jOut.write("No plantings.\n");
        return;
      }
      for (PlantingData oData : mp_oPlantings) {
        jOut.write("Timestep of planting:\t" + oData.getTimestep() + "\n");
        jOut.write("Species to which this planting is applied:\tAbundance:\n");
        for (int i = 0; i < oData.getNumberOfSpecies(); i++) {
          jOut.write(oPop.getSpeciesNameFromCode(oData.getSpecies(i)).replace("_", " ") 
              + "\t" + oData.getAbundance(i) + "\n");
        }
        jOut.write("Planting type:\t");
        if (oData.getPlantSpacing() == Planting.GRIDDED) {
          jOut.write("Gridded\n");
          jOut.write("Planting distance:\t" + oData.getSpacingDistance() + "\n");
        }
        else if (oData.getPlantSpacing() == Planting.RANDOM) {
          jOut.write("Random\n");
          jOut.write("Planting density:\t" + oData.getDensity() + "\n");
        }
      }
    } catch (ModelException e) {
      throw(new IOException(e.getMessage()));
    }
  }
}
