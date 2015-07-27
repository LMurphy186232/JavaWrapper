
package sortie.data.funcgroups;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.xml.sax.Attributes;

import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.fileops.ModelFileFunctions;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * This holds all data relating to the tree population. It also manages the
 * windows necessary to display and retrieve information about the trees.
 * Copyright: Copyright (c) Charles D. Canham 2003
 * Company: Cary Institute of Ecosystem Studies
 * 
 * @author Lora E. Murphy
 * @version 1.1
 * 
 * <br>Edit history:
 * <br>------------------ 
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class TreePopulation extends BehaviorTypeBase {

  /** Seed tree life history stage - matches definition in C++ */
  public final static int SEED = 0;
  /** Seedling tree life history stage - matches definition in C++ */
  public final static int SEEDLING = 1;
  /** Sapling tree life history stage - matches definition in C++ */
  public final static int SAPLING = 2;
  /** Adult tree life history stage - matches definition in C++ */
  public final static int ADULT = 3;
  /** Stump tree life history stage - matches definition in C++ */
  public final static int STUMP = 4;
  /** Snag tree life history stage - matches definition in C++ */
  public final static int SNAG = 5;
  /** Woody debris tree life history stage - matches definition in C++ */
  public final static int WOODY_DEBRIS = 6;

  private ModelVector
  /**
   * List of species names - ignore XML tags since this data can't use the
   * automated XML read-write system
   */
  mp_sSpeciesNames = new ModelVector("Species names", "", "", 0,
      ModelVector.STRING);
  
  /** Holds Tree objects to be written to a tree map */
  public ArrayList<Tree> mp_oTrees = new ArrayList<Tree>(0);

  public ArrayList<ArrayList<ArrayList<String>>>
  /**
   * Tree integer data members. First array index is species, second is type.
   * The vector is a set of strings with the data member names. The position of
   * the String in the vector corresponds to the data member code in the tree
   * map.
   */
  mp_sTreeIntDataMembers,
  /**
   * Tree float data members. First array index is species, second is type. The
   * vector is a set of strings with the data member names. The position of the
   * String in the vector corresponds to the data member code in the tree map.
   */
  mp_sTreeFloatDataMembers,
  /**
   * Tree char data members. First array index is species, second is type. The
   * vector is a set of strings with the data member names. The position of the
   * String in the vector corresponds to the data member code in the tree map.
   */
  mp_sTreeCharDataMembers,
  /**
   * Tree bool data members. First array index is species, second is type. The
   * vector is a set of strings with the data member names. The position of the
   * String in the vector corresponds to the data member code in the tree map.
   */
  mp_sTreeBoolDataMembers;

  /**
   * Index positions for tree integer data members. The current tree map being
   * read may have different data member indexes, and this will help translate
   * to mp_sTreeIntDataMembers. First index is species, second is type. Vector
   * position equals the data member position in the map being read, and vector
   * value is the position in mp_sTreeIntDataMembers of the data member (and
   * thus the tree index).
   */
  protected ArrayList<ArrayList<ArrayList<Integer>>> mp_iTreeIntTransforms,
  /**
   * Index positions for tree float data members. The current tree map being
   * read may have different data member indexes, and this will help translate
   * to mp_sTreeFloatDataMembers. First index is species, second is type. Vector
   * position equals the data member position in the map being read, and vector
   * value is the position in mp_sTreeFloatDataMembers of the data member (and
   * thus the tree index).
   */
  mp_iTreeFloatTransforms,
  /**
   * Index positions for tree char data members. The current tree map being read
   * may have different data member indexes, and this will help translate to
   * mp_sTreeCharDataMembers. First index is species, second is type. Vector
   * position equals the data member position in the map being read, and vector
   * value is the position in mp_sTreeCharDataMembers of the data member (and
   * thus the tree index).
   */
  mp_iTreeCharTransforms,
  /**
   * Index positions for tree bool data members. The current tree map being read
   * may have different data member indexes, and this will help translate to
   * mp_sTreeBoolDataMembers. First index is species, second is type. Vector
   * position equals the data member position in the map being read, and vector
   * value is the position in mp_sTreeBoolDataMembers of the data member (and
   * thus the tree index).
   */
  mp_iTreeBoolTransforms;

  
  /**
   * When parsing tree maps, this will convert in case the species list is in a
   * different order. The array index is the code to convert; the value in the
   * array at that point is the corresponding old species number.
   */
  protected int[] mp_iSpeciesTransforms;

  /** Counter for tree map species list when parsing tree map files. */
  protected int m_iSpeciesCounter;

  /** When parsing tree map settings, the current tree type. */
  protected int m_iCurrentTreeType,
  /** When parsing tree map settings, the current tree species. */
  m_iCurrentSpecies;

  /**
   * Constructor
   * @param oManager GUIManager object
   */
  public TreePopulation(GUIManager oManager) throws ModelException {
    super(oManager, "Tree Population");
    
    String sDescriptor = "Tree Population",
        sParFileTag = "trees", 
        sXMLRootString = "trees";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(TreeBehavior.class, sDescriptor, sParFileTag, sXMLRootString));
    //Always instantiate behaviors
    mp_oInstantiatedBehaviors.add(new TreeBehavior(oManager, this, sDescriptor, sParFileTag, sXMLRootString));
    sDescriptor = "Allometry";
    sParFileTag = "allometry";
    sXMLRootString = "allometry";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(Allometry.class, sDescriptor, sParFileTag, sXMLRootString));
    mp_oInstantiatedBehaviors.add(new Allometry(oManager, this, sDescriptor, sParFileTag, sXMLRootString));

    m_iCurrentSpecies = -1;
    m_iCurrentTreeType = -1;

    mp_sSpeciesNames.setIsSpeciesSpecific(false);        
  }

  /**
   * Returns the number of species
   * 
   * @return Number of species
   */
  public int getNumberOfSpecies() {
    return mp_sSpeciesNames.getValue().size();
  }

  /**
   * Gets the list of trees.
   * 
   * @return A vector of Tree objects.
   */
  public ArrayList<Tree> getTrees() {
    return mp_oTrees;
  }

  /**
   * Get the total number of tree types (life history stages).
   * 
   * @return Total number of tree types
   */
  public static int getNumberOfTypes() {
    return 7;
  }
  
  /**
   * Clears any tree map parse settings that may be in place. This ensures
   * that grid values do not go to trees.
   */
  public void clearTreeMapParseSettings() {
    m_iCurrentSpecies = -1;
    m_iCurrentTreeType = -1;
  }

  /**
   * When passed the name of a species, will give back the corresponding species
   * number code. If there are spaces in the name, they should be replaced with
   * underscores BEFORE being passed.
   * 
   * @param sName The name of the species.
   * @return The code, or -1 if the name is not recognized.
   */
  public int getSpeciesCodeFromName(String sName) {
    if (sName == null) {
      return -1;
    }
    int iCode = -1, i;

    for (i = 0; i < mp_sSpeciesNames.getValue().size(); i++) {
      if (sName.equalsIgnoreCase(mp_sSpeciesNames.getValue().get(i).toString())) {
        iCode = i;
        break;
      }
    }
    return iCode;
  }

  /**
   * Adds the trees from a tab-delimited text tree map file.
   * 
   * @param sFileName The file name of the tree map file.
   * @param oWindow Window to which to display messages
   * @throws ModelException if:
   * <ul>
   * <li>There is an unrecognized species</li>
   * <li>There is an unrecognized life history stage</li>
   * <li>There is a negative diameter</li>
   * <li>There is a negative height</li>
   * </ul>
   */
  public void addTabDelimTreeMapFile(javax.swing.JFrame oWindow,
      String sFileName) throws ModelException {
    try {
      Plot oPlot = m_oManager.getPlot();
      Allometry oAllom = getAllometry();
      FileReader oIn = new FileReader(sFileName);
      int[] p_iMemberType = null;
      ArrayList<String> oColHeaders = null;
      ArrayList<String> oLine = new ArrayList<String>(0); // one line of data
      ArrayList<Tree> oNewTrees = new ArrayList<Tree>(0); // the trees contained in
                                                    // this file
      String sData = "";
      float fPlotLenX = oPlot.getPlotXLength(), 
            fPlotLenY = oPlot.getPlotYLength(), fX, fY, fDiam, 
            fTemp,
            fMaxHeight, fHeight = 0;
      int iSpecies, iType, i, j,
          iNumCols = 0,
          iTemp, iCode,
          iNumSpecies = getNumberOfSpecies(), 
          iNumTypes = getNumberOfTypes();
      int[][]
      // data member codes for X
      p_iXCodes = new int[iNumSpecies][iNumTypes],
      // data member codes for Y
      p_iYCodes = new int[iNumSpecies][iNumTypes],
      // data member codes for diameter (diam10 for saplings and DBH for all
      // others)
      p_iDiamCodes = new int[iNumSpecies][iNumTypes],
      // data member codes for height
      p_iHeightCodes = new int[iNumSpecies][iNumTypes];

      boolean bWarned = false, bReplaceTrees = false, bUseTree;
      
      // Get the maximum possible height
      fMaxHeight = -1;
      for (i = 0; i < iNumSpecies; i++) {
        fHeight = new Float(oAllom.mp_fMaxCanopyHeight.getValue().get(i).toString()).floatValue();
        if (fHeight > fMaxHeight)          
          fMaxHeight = fHeight;
      }

      // Ask the user whether they want to add the trees to the parameter file
      // or keep it separate
      Object[] oButtonOptions = { "Add To Parameter File",
          "Keep As Separate File", "Cancel" };
      int iChoice = javax.swing.JOptionPane.showOptionDialog(oWindow,
          "Would you like to add the trees to the parameter file or keep them\n"
          + "as a separate file? It is recommended you only keep it separate\n"
          + "if the file is very large.", "", JOptionPane.YES_NO_OPTION,
          JOptionPane.QUESTION_MESSAGE, null, // don't use a custom Icon
          oButtonOptions, // the titles of buttons
          oButtonOptions[0]); // default button title

      if (iChoice == 2) {
        oIn.close();
        return;
      }
      if (iChoice == 1) {
        TreeBehavior oTreeBeh = (TreeBehavior) mp_oInstantiatedBehaviors.get(0);
        oTreeBeh.m_sTextTreeMap.setValue(sFileName);
        oIn.close();
        return;
      }

      // If there are already trees, ask the user if they want to overwrite
      if (mp_oTrees.size() > 0) {
        oButtonOptions = new Object[] { "Replace", "Add To", "Cancel" };
        iChoice = javax.swing.JOptionPane.showOptionDialog(oWindow,
            "What would you like to do with existing tree map data?", "",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
            oButtonOptions, // the titles of buttons
            oButtonOptions[0]); // default button title

        if (iChoice == 2) {
          oIn.close();
          return;
        }
        if (iChoice == 0) {
          bReplaceTrees = true;
        } else {
          bReplaceTrees = false;
        }
      }

      // Get data member codes
      for (i = 0; i < iNumSpecies; i++) {
        for (j = SEEDLING; j <= SNAG; j++) {
          if (j != STUMP) {
            p_iXCodes[i][j] = getCodeForDataMember(mp_sTreeFloatDataMembers.get(i).get(j), "X");
            p_iYCodes[i][j] = getCodeForDataMember(mp_sTreeFloatDataMembers.get(i).get(j), "Y");
            p_iHeightCodes[i][j] = getCodeForDataMember(mp_sTreeFloatDataMembers.get(i).get(j), "Height");
          }
        }
      }

      for (i = 0; i < iNumSpecies; i++) {
        p_iDiamCodes[i][SEEDLING] = getCodeForDataMember(mp_sTreeFloatDataMembers.get(i).get(SEEDLING), "Diam10");
        p_iDiamCodes[i][SAPLING] = getCodeForDataMember(mp_sTreeFloatDataMembers.get(i).get(SAPLING), "DBH");
        p_iDiamCodes[i][ADULT] = getCodeForDataMember(mp_sTreeFloatDataMembers.get(i).get(ADULT), "DBH");
        p_iDiamCodes[i][SNAG] = getCodeForDataMember(mp_sTreeFloatDataMembers.get(i).get(SNAG), "DBH");
      }

      //Get the column headers
      oLine = ModelFileFunctions.ReadLine(oIn);
      iNumCols = oLine.size();
      // Verify that all data is present
      if (iNumCols < 6) {
        throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA", "The file \""
            + sFileName + "\" does not have all required columns."));
      }
      //Check for and validate any extra column headers
      if (iNumCols > 6) {
        p_iMemberType = new int[iNumCols - 6];
        oColHeaders = new ArrayList<String>(iNumCols - 6);
        for (i = 0; i < p_iMemberType.length; i++) p_iMemberType[i] = -1;
        for (i = 6; i < iNumCols; i++) {
          sData = oLine.get(i);
          oColHeaders.add((i-6), sData);
          for (iSpecies = 0; iSpecies < iNumSpecies; iSpecies++) {
            for (iType = 0; iType < iNumTypes; iType++) {

              //Is it a float?
              if (getFloatDataCode(sData, iSpecies, iType) > -1) {
                if (-1 == p_iMemberType[i-6]) {
                  p_iMemberType[i-6] = DataMember.FLOAT;
                } else if (DataMember.FLOAT != p_iMemberType[i-6]) {
                  throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA", 
                      "Tree data member \"" + sData + "\" of ambiguous type."));                  
                }
              }

              //Is it an int?
              if (getIntDataCode(sData, iSpecies, iType) > -1) {
                if (-1 == p_iMemberType[i-6]) {
                  p_iMemberType[i-6] = DataMember.INTEGER;
                } else if (DataMember.INTEGER != p_iMemberType[i-6]) {
                  throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA", 
                      "Tree data member \"" + sData + "\" of ambiguous type."));
                }
              }

              //Is it a bool?
              if (getBoolDataCode(sData, iSpecies, iType) > -1) {
                if (-1 == p_iMemberType[i-6]) {
                  p_iMemberType[i-6] = DataMember.BOOLEAN;
                } else if (DataMember.BOOLEAN != p_iMemberType[i-6]) {
                  throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA", 
                      "Tree data member \"" + sData + "\" of ambiguous type."));
                }
              }

              //Is it a char?
              if (getCharDataCode(sData, iSpecies, iType) > -1) {
                if (-1 == p_iMemberType[i-6]) {
                  p_iMemberType[i-6] = DataMember.CHAR;
                } else if (DataMember.CHAR != p_iMemberType[i-6]) {
                  throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA", 
                      "Tree data member \"" + sData + "\" of ambiguous type."));
                }
              }
            }
          }

          //Make sure we found this data member for this tree type and
          //species
          if (-1 == p_iMemberType[i-6]) {
            throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA", 
                "Tree data member \"" + sData + "\" unrecognized."));            
          }
        }
      }

      oLine = ModelFileFunctions.ReadLine(oIn);
      while (oLine.size() > 0) {
        i = 0;
        bUseTree = true;

        // Verify that all data is present
        if (oLine.size() != iNumCols) {
          throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA", "The file \""
              + sFileName + "\" has an invalid format."));
        }

        fX = 0;
        fY = 0;
        try {
          fX = Float.parseFloat(oLine.get(i));
          i++;
          fY = Float.parseFloat(oLine.get(i));
          i++;
        } catch (java.lang.NumberFormatException oErr) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The tree map"
              + " file appears to be in the wrong format."
              + "  X and Y are expected as the first two " + "columns."));
        }

        // If a tree is outside the plot, warn the user
        if (fX >= fPlotLenX || fX < 0 || fY >= fPlotLenY || fY < 0) {
          if (bWarned == false) {
            iChoice = javax.swing.JOptionPane.showConfirmDialog(oWindow,
                "One or more trees is outside the plot.\n"
                    + " These trees will be discarded.  Continue?",
                "SORTIE-ND", javax.swing.JOptionPane.YES_NO_OPTION);
            if (iChoice == javax.swing.JOptionPane.NO_OPTION) {
              javax.swing.JOptionPane.showMessageDialog(oWindow,
                  "No trees were added.");
              return;
            }
            bWarned = true;
          }
          bUseTree = false;
        }

        if (bUseTree) {

          // Get species
          sData = oLine.get(i).replace(' ', '_');
          i++;
          iSpecies = getSpeciesCodeFromName(sData);
          if (iSpecies == -1) {
            throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The file \""
                + sFileName + "\" has an invalid species: \"" + sData + "\"."));
          }

          // Get tree type
          sData = oLine.get(i);
          i++;
          iType = getTypeCodeFromName(sData);
          if (iType < SEEDLING || iType > SNAG || iType == STUMP) {
            throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The file \""
                + sFileName + "\" has an invalid tree type: \"" + sData + "\"."));
          }

          // Get diameter
          fDiam = Float.parseFloat(oLine.get(i));
          i++;
          if (fDiam <= 0) {
            throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The file \""
                + sFileName + "\" has an invalid tree diameter: \"" + fDiam
                + "\"."));
          }

          // Get the height
          fHeight = Float.parseFloat(oLine.get(i));
          if (fHeight < 0) {
            throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The file \""
                + sFileName + "\" has an invalid tree height: \"" + fHeight
                + "\"."));
          }
          if (fHeight > fMaxHeight) {
            throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
                "The following tree has a height above the maximum canopy " +
                "height: X = " + fX + ", Y = " + fY + ", Diam = " + fDiam +
                ", Species = " + getSpeciesNameFromCode(iSpecies) + ", Type = "
                + iType + ", Height = " + fHeight + "."));
          }

          Tree oTree = new Tree(iType, iSpecies, mp_sTreeFloatDataMembers
              .get(iSpecies).get(iType).size(),
              mp_sTreeIntDataMembers.get(iSpecies).get(iType)
                  .size(), mp_sTreeCharDataMembers.get(iSpecies)
                  .get(iType).size(), mp_sTreeBoolDataMembers.get(
                  iSpecies).get(iType).size(), this);
          oTree.setValue(p_iXCodes[iSpecies][iType], new Float(fX));
          oTree.setValue(p_iYCodes[iSpecies][iType], new Float(fY));
          oTree.setValue(p_iDiamCodes[iSpecies][iType], new Float(fDiam));
          if (java.lang.Math.abs(fHeight) > 0.01) {
            oTree.setValue(p_iHeightCodes[iSpecies][iType], new Float(fHeight));
          }         
          

          //Extra columns as appropriate
          i = 6;
          while (i < iNumCols) {
            sData = oLine.get(i);
            if (DataMember.FLOAT == p_iMemberType[i-6]) {              
              fTemp = Float.parseFloat(sData);
              iCode = getFloatDataCode(oColHeaders.get(i-6), iSpecies, iType);
              if (iCode > -1) {
                oTree.setValue(iCode, fTemp);
              }
            } else if (DataMember.INTEGER == p_iMemberType[i-6]) {
              iTemp = Integer.parseInt(sData);
              iCode = getIntDataCode(oColHeaders.get(i-6), iSpecies, iType);
              if (iCode > -1) {
                oTree.setValue(iCode, iTemp);
              }
            } else if (DataMember.BOOLEAN == p_iMemberType[i-6]) {
              iTemp = Integer.parseInt(sData);
              iCode = getBoolDataCode(oColHeaders.get(i-6), iSpecies, iType);
              if (iCode > -1) {
                if (iTemp == 1) oTree.setValue(iCode, true);
                else oTree.setValue(iCode, false);
              }
            } else if (DataMember.CHAR == p_iMemberType[i-6]) {
              iCode = getCharDataCode(oColHeaders.get(i-6), iSpecies, iType);
              if (iCode > -1) {
                oTree.setValue(iCode, sData);
              }
            }
            i++;
          }

          oNewTrees.add(oTree);
        }
        oLine = ModelFileFunctions.ReadLine(oIn);
      } 
      oIn.close();

      // Add the new trees
      if (bReplaceTrees) {
        mp_oTrees = null;
        mp_oTrees = new ArrayList<Tree>(0);
      }

      for (i = 0; i < oNewTrees.size(); i++) {
        mp_oTrees.add(oNewTrees.get(i));
      }
      oNewTrees = null;
    } catch (java.io.IOException e) {
      throw (new ModelException(ErrorGUI.BAD_FILE, "WriteTreeMap", sFileName));
    }
  }

  /**
   * Returns the number of size classes.
   * 
   * @return Number of size classes
   */
  public int getNumberOfSizeClasses() {
    return (getTreeBehavior().mp_fSizeClasses.getValue().size());
  }

  /**
   * Returns the size class at a specific index.
   * 
   * @param iIndex Index of size class to return.
   * @return Upper limit of size class.
   */
  public Float getSizeClass(int iIndex) {
    return (Float)(getTreeBehavior().mp_fSizeClasses.getValue().get(iIndex));
  }  

  
  /**
   * Sets the species name list. The species vector will be sized to match the
   * array of Strings passed. All spaces in the species names are replaced with
   * underscores.
   * 
   * If there are species currently defined, this calls ChangeOfSpecies() for
   * all objects.
   * 
   * This calls the GUIManager::DoSetup() method so that behavior groups can
   * adjust for the new species list if species were already defined.
   * 
   * This function also declares certain arrays, since this is the first
   * opportunity to know how many species there are.
   * 
   * @param p_sVals A String array of species names.
   * @throws ModelException if there is a problem setting the species names.
   */
  public void setSpeciesNames(String[] p_sVals) throws ModelException {
    int i;
    boolean bSpeciesExisted = false;

    // Replace the spaces in the names with underscores
    for (i = 0; i < p_sVals.length; i++) {
      p_sVals[i] = p_sVals[i].replace(' ', '_');
    }

    if (mp_sSpeciesNames.getValue().size() != 0) {

      // This is not the first time this has been called
      bSpeciesExisted = true;

      // Null the arrays to reset them before they are redeclared
      mp_sTreeIntDataMembers = null;
      mp_sTreeFloatDataMembers = null;
      mp_sTreeCharDataMembers = null;
      mp_sTreeBoolDataMembers = null;

      mp_iTreeBoolTransforms = null;
      mp_iTreeIntTransforms = null;
      mp_iTreeCharTransforms = null;
      mp_iTreeFloatTransforms = null;

      // Prepare to call ChangeOfSpecies for all objects
      int[] p_iIndexer = new int[p_sVals.length];
      int iNumOldSpecies = mp_sSpeciesNames.getValue().size(), j;

      // Set the values in the indexer
      for (i = 0; i < p_iIndexer.length; i++) {
        p_iIndexer[i] = -1;
        // Does this species already exist? If so, grab its index
        for (j = 0; j < mp_sSpeciesNames.getValue().size(); j++) {
          if (p_sVals[i].equals(mp_sSpeciesNames.getValue().get(
              j))) {
            p_iIndexer[i] = j;
          }
        }
      }

      // Now get the index translator the other way
      int[] p_iOldToNewTranslator = new int[mp_sSpeciesNames.getValue().size()];
      for (i = 0; i < p_iOldToNewTranslator.length; i++) {
        p_iOldToNewTranslator[i] = -1;

        // Does this species still exist in the new list? If so, grab its index
        for (j = 0; j < p_sVals.length; j++) {
          if (p_sVals[j].equals(mp_sSpeciesNames.getValue().get(i))) {
            p_iOldToNewTranslator[i] = j;
          }
        }
      }

      // Trigger the change of species
      m_oManager.changeOfSpecies(iNumOldSpecies, p_iIndexer, p_sVals);
    }

    int iNumSpecies, iNumTypes, j;

    mp_sSpeciesNames.getValue().clear();

    for (i = 0; i < p_sVals.length; i++) {
      mp_sSpeciesNames.getValue().add(i, new String(p_sVals[i]));
    }

    // Declare the tree data member arrays
    iNumSpecies = getNumberOfSpecies();
    iNumTypes = getNumberOfTypes();
    mp_sTreeIntDataMembers = new ArrayList<ArrayList<ArrayList<String>>>(iNumSpecies);
    mp_sTreeFloatDataMembers = new ArrayList<ArrayList<ArrayList<String>>>(iNumSpecies);
    mp_sTreeCharDataMembers = new ArrayList<ArrayList<ArrayList<String>>>(iNumSpecies);
    mp_sTreeBoolDataMembers = new ArrayList<ArrayList<ArrayList<String>>>(iNumSpecies);

    mp_iTreeBoolTransforms = new ArrayList<ArrayList<ArrayList<Integer>>>(iNumSpecies);
    mp_iTreeIntTransforms = new ArrayList<ArrayList<ArrayList<Integer>>>(iNumSpecies);
    mp_iTreeCharTransforms = new ArrayList<ArrayList<ArrayList<Integer>>>(iNumSpecies);
    mp_iTreeFloatTransforms = new ArrayList<ArrayList<ArrayList<Integer>>>(iNumSpecies);

    for (i = 0; i < iNumSpecies; i++) {
      mp_sTreeIntDataMembers.add(i, new ArrayList<ArrayList<String>>(iNumTypes));
      mp_sTreeFloatDataMembers.add(i, new ArrayList<ArrayList<String>>(iNumTypes));
      mp_sTreeCharDataMembers.add(i, new ArrayList<ArrayList<String>>(iNumTypes));
      mp_sTreeBoolDataMembers.add(i, new ArrayList<ArrayList<String>>(iNumTypes));

      mp_iTreeBoolTransforms.add(i, new ArrayList<ArrayList<Integer>>(iNumTypes));
      mp_iTreeIntTransforms.add(i, new ArrayList<ArrayList<Integer>>(iNumTypes));
      mp_iTreeCharTransforms.add(i, new ArrayList<ArrayList<Integer>>(iNumTypes));
      mp_iTreeFloatTransforms.add(i, new ArrayList<ArrayList<Integer>>(iNumTypes));
      for (j = 0; j < iNumTypes; j++) {
        mp_sTreeIntDataMembers.get(i).add(j, new ArrayList<String>(0));
        mp_sTreeFloatDataMembers.get(i).add(j, new ArrayList<String>(0));
        mp_sTreeCharDataMembers.get(i).add(j, new ArrayList<String>(0));
        mp_sTreeBoolDataMembers.get(i).add(j, new ArrayList<String>(0));

        mp_iTreeBoolTransforms.get(i).add(j, new ArrayList<Integer>(0));
        mp_iTreeIntTransforms.get(i).add(j, new ArrayList<Integer>(0));
        mp_iTreeCharTransforms.get(i).add(j, new ArrayList<Integer>(0));
        mp_iTreeFloatTransforms.get(i).add(j, new ArrayList<Integer>(0));
      }
    }

    mp_iSpeciesTransforms = new int[iNumSpecies];
    for (i = 0; i < iNumSpecies; i++) {
      mp_iSpeciesTransforms[i] = i;
    }

    // Now trigger setup
    if (!bSpeciesExisted) {
      m_oManager.doSetup();
    }
  }

  
  /**
   * Deletes all trees.
   * 
   * @param iOldNumSpecies says how many species there used to be.
   * @param p_iIndexer is an array, sized to the new number of species. For 
   * each bucket (representing the index number of a species on the new list), 
   * the value is either the index of that same species in the old species list,
   *  or -1 if the species is new.
   * @param p_sNewSpecies The new species list.
   * @throws ModelException if anything goes wrong.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) throws ModelException {

    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    
    // Delete all existing tree map trees
    mp_oTrees.clear();
  }

  /**
   * For a given species code, returns its name. If this name is for display, a
   * replace should be done on underscores to spaces.
   * 
   * @param iSpecies The species code.
   * @return The species name.
   */
  public String getSpeciesNameFromCode(int iSpecies) {
    if (iSpecies < 0 || iSpecies >= mp_sSpeciesNames.getValue().size()) {
      return "";
    }
    String sName = mp_sSpeciesNames.getValue().get(iSpecies).toString();
    return sName;
  }

  /**
   * For a given type code, returns its name.
   * 
   * @param iType The type code.
   * @return The type name, or an empty string if the code isn't recognized.
   */
  public static String getTypeNameFromCode(int iType) {
    if (iType < 0 || iType >= getNumberOfTypes()) {
      return "";
    }

    if (SEED == iType) {
      return "Seed";
    } else if (SEEDLING == iType) {
      return "Seedling";
    } else if (SAPLING == iType) {
      return "Sapling";
    } else if (ADULT == iType) {
      return "Adult";
    } else if (STUMP == iType) {
      return "Stump";
    } else if (SNAG == iType) {
      return "Snag";
    } else {
      return "Woody debris";
    }
  }

  /**
   * For a given type name, returns its code.
   * 
   * @param sName The type name.
   * @return The type code, or -1 if the name is invalid.
   */
  public static int getTypeCodeFromName(String sName) {
    if (sName == null) {
      return -1;
    }
    if (sName.equals("Seed")) {
      return SEED;
    } else if (sName.equalsIgnoreCase("seedling")) {
      return SEEDLING;
    } else if (sName.equalsIgnoreCase("Sapling")) {
      return SAPLING;
    } else if (sName.equalsIgnoreCase("Adult")) {
      return ADULT;
    } else if (sName.equalsIgnoreCase("Stump")) {
      return STUMP;
    } else if (sName.equalsIgnoreCase("Snag")) {
      return SNAG;
    } else if (sName.equalsIgnoreCase("Woody debris")) {
      return WOODY_DEBRIS;
    } else {
      return -1;
    }
  }
  
  public void writeBehaviorNodes(BufferedWriter out, TreePopulation oPop) throws
  ModelException {;}
    
  public TreeBehavior getTreeBehavior() {return (TreeBehavior) mp_oInstantiatedBehaviors.get(0);}
  
  /**
   * This makes sure all data is valid and can be used to run the model.
   * @param oPop Not used.
   * @throws ModelException if there are no species defined.           
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    int iNumSpecies = mp_sSpeciesNames.getValue().size(), i;
    
    if (iNumSpecies == 0) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "There must be some species defined."));
    }
        
    // Validate tree map trees and dump any without X, Y, and diameter or height
    Tree oTree;
    int iXCode, iYCode, iDiamCode;
    boolean bOK;

    for (i = 0; i < mp_oTrees.size(); i++) {
      oTree = mp_oTrees.get(i);
      iXCode = getFloatDataCode("X", oTree.m_iSpecies, oTree.m_iType);
      iYCode = getFloatDataCode("Y", oTree.m_iSpecies, oTree.m_iType);
      iDiamCode = -1;
      bOK = false;

      if (iXCode != -1 && oTree.getFloat(iXCode) != null && iYCode != -1
          && oTree.getFloat(iYCode) != null) {

        if (oTree.m_iType > TreePopulation.SAPLING) {
          iDiamCode = getFloatDataCode("DBH", oTree.m_iSpecies, oTree.m_iType);
          if (iDiamCode != -1 && oTree.getFloat(iDiamCode) != null)
            bOK = true;
        } else if (oTree.m_iType == TreePopulation.SAPLING) {
          // Either DBH or diam10 is acceptable
          iDiamCode = getFloatDataCode("DBH", oTree.m_iSpecies, oTree.m_iType);
          if (iDiamCode != -1 && oTree.getFloat(iDiamCode) != null)
            bOK = true;
          else {
            iDiamCode = getFloatDataCode("Diam10", oTree.m_iSpecies,
                oTree.m_iType);
            if (iDiamCode != -1 && oTree.getFloat(iDiamCode) != null)
              bOK = true;
          }
        } else if (oTree.m_iType == TreePopulation.SEEDLING) {
          iDiamCode = getFloatDataCode("Diam10", oTree.m_iSpecies,
              oTree.m_iType);
          if (iDiamCode != -1 && oTree.getFloat(iDiamCode) != null)
            bOK = true;
        }

        // If diameter was not present, check for height
        if (false == bOK) {
          iDiamCode = getFloatDataCode("Height", oTree.m_iSpecies,
              oTree.m_iType);
          if (iDiamCode != -1 && oTree.getFloat(iDiamCode) != null)
            bOK = true;
        }
      }

      if (false == bOK) {
        mp_oTrees.remove(i);
        i--;
      }
    }
    super.validateData(oPop);
  }
  
  /**
   * Writes the species list
   * @param jOut The XML file to write to.
   * @throws IOException If anything goes wrong
   */
  public void writeSpeciesList(BufferedWriter jOut) throws IOException {
    String sData;
    int i;
    // Create the species list
    jOut.write("<tr_speciesList>");
    for (i = 0; i < mp_sSpeciesNames.getValue().size(); i++) {
      sData = mp_sSpeciesNames.getValue().get(i).toString();
      jOut.write("<tr_species speciesName=\"");
      jOut.write(sData.replace(' ', '_'));
      jOut.write("\"/>");
    }
    jOut.write("</tr_speciesList>");
  }
  
  /**
   * Update the tree data members for the currently enabled behaviors. Remove
   * any that no longer apply.
   */
  private void updateTreeDataMembers() throws ModelException {
    int iSp, iTp, i, j, k;
    boolean bFound;
    
    BehaviorTypeBase[] p_oBehaviorGroups = m_oManager.getAllObjects();
    
    for (iSp = 0; iSp < getNumberOfSpecies(); iSp++) {
      for (iTp = 0; iTp < getNumberOfTypes(); iTp++) {
        for (k = 0; k < mp_sTreeFloatDataMembers.get(iSp).get(iTp).size(); k++) {
          String sDataMember = mp_sTreeFloatDataMembers.get(iSp).get(iTp).get(k);
          
          if (sDataMember.equals("X") || sDataMember.equals("Y") ||
              sDataMember.equals("Diam10") || sDataMember.equals("DBH") ||
              sDataMember.equals("Height") ||
              sDataMember.equals("Crown Radius") || 
              sDataMember.equals("Crown Depth")) continue;  
        
          bFound = false;
          for (BehaviorTypeBase oBehaviorGroup : p_oBehaviorGroups) {
            ArrayList<Behavior> p_oBehaviors = oBehaviorGroup.getAllInstantiatedBehaviors(); 
            if (p_oBehaviors == null || p_oBehaviors.size() == 0) continue;
            for (Behavior oBeh : p_oBehaviors) {

              //Only collect data member data for enabled behaviors
              for (i = 0; i < oBeh.getNumberNewDataMembers(); i++) {
                DataMember oDataMember = oBeh.getNewTreeDataMember(i);
                if (oDataMember.getCodeName().equals(sDataMember) &&
                    oDataMember.getType() == DataMember.FLOAT) {
                  //Found it - is this behavior applied to this species and type?
                  for (j = 0; j < oBeh.getNumberOfCombos(); j++) {
                    SpeciesTypeCombo oCombo = oBeh.getSpeciesTypeCombo(j);
                    if (oCombo.getSpecies() == iSp && oCombo.getType() == iTp) {
                      //Yep - this is a valid data member.
                      bFound = true;
                      break;
                    }
                  }
                }
              }
            }
          }
          if (!bFound) {
            mp_sTreeFloatDataMembers.get(iSp).get(iTp).remove(k);
            for (Tree oTree : mp_oTrees) {
              if (oTree.getSpecies() == iSp && oTree.getType() == iTp) {
                oTree.removeFloat(k);
              }
            }
            k--;
          }
        }
      }
    }
        
    for (iSp = 0; iSp < getNumberOfSpecies(); iSp++) {
      for (iTp = 0; iTp < getNumberOfTypes(); iTp++) {
        for (k = 0; k < mp_sTreeIntDataMembers.get(iSp).get(iTp).size(); k++) {
          String sDataMember = mp_sTreeIntDataMembers.get(iSp).get(iTp).get(k);
          
          if (sDataMember.equals("Age") || sDataMember.equals("Why dead")) 
            continue;  
          
          bFound = false;
          for (BehaviorTypeBase oBehaviorGroup : p_oBehaviorGroups) {
            ArrayList<Behavior> p_oBehaviors = oBehaviorGroup.getAllInstantiatedBehaviors(); 
            if (p_oBehaviors == null) continue;
            for (Behavior oBeh : p_oBehaviors) {

              //Only collect data member data for enabled behaviors
              for (i = 0; i < oBeh.getNumberNewDataMembers(); i++) {
                DataMember oDataMember = oBeh.getNewTreeDataMember(i);
                if (oDataMember.getCodeName().equals(sDataMember) &&
                    oDataMember.getType() == DataMember.INTEGER) {
                  //Found it - is this behavior applied to this species and type?
                  for (j = 0; j < oBeh.getNumberOfCombos(); j++) {
                    SpeciesTypeCombo oCombo = oBeh.getSpeciesTypeCombo(j);
                    if (oCombo.getSpecies() == iSp && oCombo.getType() == iTp) {
                      //Yep - this is a valid data member.
                      bFound = true;
                      break;
                    }
                  }
                }
              }
            }
          }
          if (!bFound) {
            mp_sTreeIntDataMembers.get(iSp).get(iTp).remove(k);
            for (Tree oTree : mp_oTrees) {
              if (oTree.getSpecies() == iSp && oTree.getType() == iTp) {
                oTree.removeInt(k);
              }
            }
            k--;
          }
        }
      }
    }
    
    for (iSp = 0; iSp < getNumberOfSpecies(); iSp++) {
      for (iTp = 0; iTp < getNumberOfTypes(); iTp++) {   
        for (k = 0; k < mp_sTreeBoolDataMembers.get(iSp).get(iTp).size(); k++) {
          String sDataMember = mp_sTreeBoolDataMembers.get(iSp).get(iTp).get(k);
          bFound = false;
          for (BehaviorTypeBase oBehaviorGroup : p_oBehaviorGroups) {
            ArrayList<Behavior> p_oBehaviors = oBehaviorGroup.getAllInstantiatedBehaviors(); 
            if (p_oBehaviors == null) continue;
            for (Behavior oBeh : p_oBehaviors) {

              //Only collect data member data for enabled behaviors
              for (i = 0; i < oBeh.getNumberNewDataMembers(); i++) {
                DataMember oDataMember = oBeh.getNewTreeDataMember(i);
                if (oDataMember.getCodeName().equals(sDataMember) &&
                    oDataMember.getType() == DataMember.BOOLEAN) {
                  //Found it - is this behavior applied to this species and type?
                  for (j = 0; j < oBeh.getNumberOfCombos(); j++) {
                    SpeciesTypeCombo oCombo = oBeh.getSpeciesTypeCombo(j);
                    if (oCombo.getSpecies() == iSp && oCombo.getType() == iTp) {
                      //Yep - this is a valid data member.
                      bFound = true;
                      break;
                    }
                  }
                }
              }
            }
          }
          if (!bFound) {
            mp_sTreeBoolDataMembers.get(iSp).get(iTp).remove(k);
            for (Tree oTree : mp_oTrees) {
              if (oTree.getSpecies() == iSp && oTree.getType() == iTp) {
                oTree.removeBool(k);
              }
            }
            k--;
          }
        }
      }
    }
     
    for (iSp = 0; iSp < getNumberOfSpecies(); iSp++) {
      for (iTp = 0; iTp < getNumberOfTypes(); iTp++) {
        for (k = 0; k < mp_sTreeCharDataMembers.get(iSp).get(iTp).size(); k++) {
          String sDataMember = mp_sTreeCharDataMembers.get(iSp).get(iTp).get(k);
          bFound = false;
          for (BehaviorTypeBase oBehaviorGroup : p_oBehaviorGroups) {
            ArrayList<Behavior> p_oBehaviors = oBehaviorGroup.getAllInstantiatedBehaviors(); 
            if (p_oBehaviors == null) continue;
            for (Behavior oBeh : p_oBehaviors) {

              //Only collect data member data for enabled behaviors
              for (i = 0; i < oBeh.getNumberNewDataMembers(); i++) {
                DataMember oDataMember = oBeh.getNewTreeDataMember(i);
                if (oDataMember.getCodeName().equals(sDataMember) &&
                    oDataMember.getType() == DataMember.CHAR) {
                  //Found it - is this behavior applied to this species and type?
                  for (j = 0; j < oBeh.getNumberOfCombos(); j++) {
                    SpeciesTypeCombo oCombo = oBeh.getSpeciesTypeCombo(j);
                    if (oCombo.getSpecies() == iSp && oCombo.getType() == iTp) {
                      //Yep - this is a valid data member.
                      bFound = true;
                      break;
                    }
                  }
                }
              }
            }
          }
          if (!bFound) {
            mp_sTreeCharDataMembers.get(iSp).get(iTp).remove(k);
            for (Tree oTree : mp_oTrees) {
              if (oTree.getSpecies() == iSp && oTree.getType() == iTp) {
                oTree.removeChar(k);
              }
            }
            k--;
          }
        }        
      }
    }
  }

  /**
   * Writes an XML tree map if there are any trees to write.
   * 
   * @param jOut The XML file to write to.
   * @throws ModelException If anything goes wrong. This could wrap another exception.
   * @todo subplot support
   */
  public void writeTreeMap(BufferedWriter jOut) throws ModelException {
    try {
      if (mp_oTrees.size() == 0) {
        return;
      }
      int i, j, k;
      
      updateTreeDataMembers();

      // Write the tree map header
      jOut.write("<tr_treemap>");

      // Tree settings for each species
      for (i = 0; i < getNumberOfSpecies(); i++) {
        for (j = 0; j < getNumberOfTypes(); j++) {

          // Only include if there are any settings
          if (mp_sTreeBoolDataMembers.get(i).get(j).size() > 0
              || mp_sTreeFloatDataMembers.get(i).get(j).size() > 0
              || mp_sTreeIntDataMembers.get(i).get(j).size() > 0
              || mp_sTreeCharDataMembers.get(i).get(j).size() > 0) {

            jOut.write("<tm_treeSettings sp=\"" + getSpeciesNameFromCode(i)
                + "\" tp=\"" + String.valueOf(j) + "\">");

            // Int data members
            if (mp_sTreeIntDataMembers.get(i).get(j).size() > 0) {
              jOut.write("<tm_intCodes>");

              for (k = 0; k < mp_sTreeIntDataMembers.get(i).get(j)
                  .size(); k++) {
                jOut.write("<tm_intCode label=\""
                    + mp_sTreeIntDataMembers.get(i).get(j)
                        .get(k) + "\">" + k + "</tm_intCode>");
              }
              jOut.write("</tm_intCodes>");
            }

            // Float data members
            if (mp_sTreeFloatDataMembers.get(i).get(j).size() > 0) {
              jOut.write("<tm_floatCodes>");

              for (k = 0; k < mp_sTreeFloatDataMembers.get(i)
                  .get(j).size(); k++) {
                jOut.write("<tm_floatCode label=\""
                    + mp_sTreeFloatDataMembers.get(i).get(
                        j).get(k) + "\">" + k + "</tm_floatCode>");
              }
              jOut.write("</tm_floatCodes>");
            }

            // Char data members
            if (mp_sTreeCharDataMembers.get(i).get(j).size() > 0) {
              jOut.write("<tm_charCodes>");

              for (k = 0; k < mp_sTreeCharDataMembers.get(i).get(j)
                  .size(); k++) {
                jOut.write("<tm_charCode label=\""
                    + mp_sTreeCharDataMembers.get(i)
                        .get(j).get(k) + "\">" + k
                    + "</tm_charCode>");
              }
              jOut.write("</tm_charCodes>");
            }

            // Bool data members
            if (mp_sTreeBoolDataMembers.get(i).get(j).size() > 0) {
              jOut.write("<tm_boolCodes>");

              for (k = 0; k < mp_sTreeBoolDataMembers.get(i).get(j)
                  .size(); k++) {
                jOut.write("<tm_boolCode label=\""
                    + mp_sTreeBoolDataMembers.get(i)
                        .get(j).get(k) + "\">" + k
                    + "</tm_boolCode>");
              }
              jOut.write("</tm_boolCodes>");
            }

            jOut.write("</tm_treeSettings>");
          }
        }
      }

      // Now write all the trees
      Tree oTree;
      for (i = 0; i < mp_oTrees.size(); i++) {
        oTree = mp_oTrees.get(i);
        oTree.writeXML(jOut);
      }

      jOut.write("</tr_treemap>");
    } catch (java.io.IOException e) {
      ModelException oErr = new ModelException();
      oErr.iErrorCode = ErrorGUI.BAD_FILE;
      oErr.sCallingFunction = "WriteTreeMap";
      throw oErr;
    }
  }
  
  /**
   * Gets the data member code for a data member label. If this is not a
   * currently listed data member, it will be added.
   * 
   * @param p_oMemberList List of data members.
   * @param sDataMember Data member name.
   * @return Data member code.
   */
  protected int getCodeForDataMember(ArrayList<String> p_oMemberList,
      String sDataMember) {

    int i;
    for (i = 0; i < p_oMemberList.size(); i++) {
      if (sDataMember.equals(p_oMemberList.get(i))) {
        return i;
      }
    }

    p_oMemberList.add(sDataMember);
    return p_oMemberList.size() - 1;
  }

  /**
   * Gets the data code for accessing a float tree data member. You can use the
   * return code to get the data from a Tree class object. If the data member 
   * does not belong to the tree population, it will search enabled behaviors
   * for the data.
   * 
   * @param sDataMember Name of data member.
   * @param iSpecies Tree species.
   * @param iType Tree type.
   * @return Data code, or -1 if the data member name is unrecognized.
   */
  public int getFloatDataCode(String sDataMember, int iSpecies, int iType) throws ModelException {
    int i, j, k, m, iReturnCode = -1;
    for (i = 0; i < mp_sTreeFloatDataMembers.get(iSpecies).get(
        iType).size(); i++) {
      if (sDataMember.equalsIgnoreCase(mp_sTreeFloatDataMembers.get(
          iSpecies).get(iType).get(i).toString())) {
        return i;
      }
    }

    //Search enabled behaviors for this data member
    BehaviorTypeBase[] p_oBehaviorGroups = m_oManager.getAllObjects();
    for (i = 0; i < p_oBehaviorGroups.length; i++) {
      ArrayList<Behavior> p_oBehaviors = p_oBehaviorGroups[i].getAllInstantiatedBehaviors();
      if (p_oBehaviors != null && p_oBehaviors.size() > 0) {
        for (j = 0; j < p_oBehaviors.size(); j++) {

          //Only collect data member data for enabled behaviors
          for (k = 0; k < p_oBehaviors.get(j).getNumberNewDataMembers(); k++) {
            DataMember oDataMember = p_oBehaviors.get(j).getNewTreeDataMember(k);
            if (oDataMember.getCodeName().equals(sDataMember) &&
                oDataMember.getType() == DataMember.FLOAT) {
              //Found it - is this behavior applied to this species and type?
              for (m = 0; m < p_oBehaviors.get(j).getNumberOfCombos(); m++) {
                SpeciesTypeCombo oCombo = p_oBehaviors.get(j).getSpeciesTypeCombo(m);
                if (oCombo.getSpecies() == iSpecies && oCombo.getType() == iType) {
                  //Yep - this is a valid data member. Add it to our tree pop
                  //list and return the code
                  iReturnCode = getCodeForDataMember(
                      mp_sTreeFloatDataMembers.get(iSpecies).get(iType), 
                      sDataMember);
                  return iReturnCode;
                }
              }
            }
          }                
        }
      }
    }
    return iReturnCode;
  }
  
  /**
   * Gets the data code for accessing an int tree data member. You can use the
   * return code to get the data from a Tree class object. If the data member 
   * does not belong to the tree population, it will search enabled behaviors
   * for the data.
   * 
   * @param sDataMember Name of data member.
   * @param iSpecies Tree species.
   * @param iType Tree type.
   * @return Data code, or -1 if the data member name is unrecognized.
   */
  public int getIntDataCode(String sDataMember, int iSpecies, int iType) throws ModelException {
    int i, j, k, m, iReturnCode = -1;
    for (i = 0; i < mp_sTreeIntDataMembers.get(iSpecies).get(
        iType).size(); i++) {
      if (sDataMember.equalsIgnoreCase(mp_sTreeIntDataMembers.get(
          iSpecies).get(iType).get(i).toString())) {
        return i;
      }
    }

    //Search enabled behaviors for this data member
    BehaviorTypeBase[] p_oBehaviorGroups = m_oManager.getAllObjects();
    for (i = 0; i < p_oBehaviorGroups.length; i++) {
      ArrayList<Behavior> p_oBehaviors = p_oBehaviorGroups[i].getAllInstantiatedBehaviors();
      if (p_oBehaviors != null) {
        for (j = 0; j < p_oBehaviors.size(); j++) {

          //Only collect data member data for enabled behaviors
          for (k = 0; k < p_oBehaviors.get(j).getNumberNewDataMembers(); k++) {
            DataMember oDataMember = p_oBehaviors.get(j).getNewTreeDataMember(k);
            if (oDataMember.getCodeName().equals(sDataMember) &&
                oDataMember.getType() == DataMember.INTEGER) {
              //Found it - is this behavior applied to this species and type?
              for (m = 0; m < p_oBehaviors.get(j).getNumberOfCombos(); m++) {
                SpeciesTypeCombo oCombo = p_oBehaviors.get(j).getSpeciesTypeCombo(m);
                if (oCombo.getSpecies() == iSpecies && oCombo.getType() == iType) {
                  //Yep - this is a valid data member. Add it to our tree pop
                  //list and return the code
                  iReturnCode = getCodeForDataMember(
                      mp_sTreeIntDataMembers.get(iSpecies).get(iType), 
                      sDataMember);
                  return iReturnCode;
                }
              }
            }
          }
        }      
      }
    }
    return iReturnCode;
  }
  
  /**
   * Gets the data code for accessing a bool tree data member. You can use the
   * return code to get the data from a Tree class object. If the data member 
   * does not belong to the tree population, it will search enabled behaviors
   * for the data.
   * 
   * @param sDataMember Name of data member.
   * @param iSpecies Tree species.
   * @param iType Tree type.
   * @return Data code, or -1 if the data member name is unrecognized.
   */
  public int getBoolDataCode(String sDataMember, int iSpecies, int iType) throws ModelException {
    int i, j, k, m, iReturnCode = -1;
    for (i = 0; i < mp_sTreeBoolDataMembers.get(iSpecies).get(
        iType).size(); i++) {
      if (sDataMember.equalsIgnoreCase(mp_sTreeBoolDataMembers.get(
          iSpecies).get(iType).get(i).toString())) {
        return i;
      }
    }

    //Search enabled behaviors for this data member
    BehaviorTypeBase[] p_oBehaviorGroups = m_oManager.getAllObjects();
    for (i = 0; i < p_oBehaviorGroups.length; i++) {
      ArrayList<Behavior> p_oBehaviors = p_oBehaviorGroups[i].getAllInstantiatedBehaviors();
      if (p_oBehaviors != null && p_oBehaviors.size() > 0) {
        for (j = 0; j < p_oBehaviors.size(); j++) {

          //Only collect data member data for enabled behaviors
          for (k = 0; k < p_oBehaviors.get(j).getNumberNewDataMembers(); k++) {
            DataMember oDataMember = p_oBehaviors.get(j).getNewTreeDataMember(k);
            if (oDataMember.getCodeName().equals(sDataMember) &&
                oDataMember.getType() == DataMember.BOOLEAN) {
              //Found it - is this behavior applied to this species and type?
              for (m = 0; m < p_oBehaviors.get(j).getNumberOfCombos(); m++) {
                SpeciesTypeCombo oCombo = p_oBehaviors.get(j).getSpeciesTypeCombo(m);
                if (oCombo.getSpecies() == iSpecies && oCombo.getType() == iType) {
                  //Yep - this is a valid data member. Add it to our tree pop
                  //list and return the code
                  iReturnCode = getCodeForDataMember(
                      mp_sTreeBoolDataMembers.get(iSpecies).get(iType), 
                      sDataMember);
                  return iReturnCode;
                }
              }
            }
          }
        }
      }
    }
    return iReturnCode;
  }
  
  /**
   * Gets the data code for accessing a float tree data member. You can use the
   * return code to get the data from a Tree class object. If the data member 
   * does not belong to the tree population, it will search enabled behaviors
   * for the data.
   * 
   * @param sDataMember Name of data member.
   * @param iSpecies Tree species.
   * @param iType Tree type.
   * @return Data code, or -1 if the data member name is unrecognized.
   */
  public int getCharDataCode(String sDataMember, int iSpecies, int iType) throws ModelException {
    int i, j, k, m, iReturnCode = -1;
    for (i = 0; i < mp_sTreeCharDataMembers.get(iSpecies).get(
        iType).size(); i++) {
      if (sDataMember.equalsIgnoreCase(mp_sTreeCharDataMembers.get(
          iSpecies).get(iType).get(i).toString())) {
        return i;
      }
    }

    //Search enabled behaviors for this data member
    BehaviorTypeBase[] p_oBehaviorGroups = m_oManager.getAllObjects();
    for (i = 0; i < p_oBehaviorGroups.length; i++) {
      ArrayList<Behavior> p_oBehaviors = p_oBehaviorGroups[i].getAllInstantiatedBehaviors();
      if (p_oBehaviors != null) {
        for (j = 0; j < p_oBehaviors.size(); j++) {

          //Only collect data member data for enabled behaviors
          for (k = 0; k < p_oBehaviors.get(j).getNumberNewDataMembers(); k++) {
            DataMember oDataMember = p_oBehaviors.get(j).getNewTreeDataMember(k);
            if (oDataMember.getCodeName().equals(sDataMember) &&
                oDataMember.getType() == DataMember.CHAR) {
              //Found it - is this behavior applied to this species and type?
              for (m = 0; m < p_oBehaviors.get(j).getNumberOfCombos(); m++) {
                SpeciesTypeCombo oCombo = p_oBehaviors.get(j).getSpeciesTypeCombo(m);
                if (oCombo.getSpecies() == iSpecies && oCombo.getType() == iType) {
                  //Yep - this is a valid data member. Add it to our tree pop
                  //list and return the code
                  iReturnCode = getCodeForDataMember(
                      mp_sTreeCharDataMembers.get(iSpecies).get(iType), 
                      sDataMember);
                  return iReturnCode;
                }
              }
            }
          }
        }      
      }
    }
    return iReturnCode;
  }

  
  /**
   * Clears all trees
   */
  public void clearTrees() {
    mp_oTrees.clear();
  }
  
  /**
   * Reads tree map settings from XML parsing. This method is looking for 
   * tm_floatCode, tm_intCode, tm_charCode, tm_boolCode, fl, int, ch, and bl. 
   * For the last four, they are only set if m_iCurrentSpecies and 
   * m_iCurrentTreeType are greater than -1 but will return true in either case.
   * This is so missing values will be ignored without consequence. This depends
   * on grids being read first!
   * @param sXMLTag XML tag of data object whose value is to be set.
   * @param oAttributes The attributes of this object.
   * @param oData Data value appropriate to the data type
   * @return true if the value was set successfully; false if the value could 
   * not be found. (This would not be an error, because I need a way to cycle 
   * through the objects until one of the objects comes up with a match.)
   * @throws ModelException if there is a problem reading this data.
   */
  public boolean readTreeMapSettings(String sXMLTag, Attributes oAttributes, 
      Object oData) throws ModelException {
    try {
      if (sXMLTag.equals("fl")) {
        if (m_iCurrentSpecies > -1 && m_iCurrentTreeType > -1) {
          // Get the last added tree
          Tree oTree = mp_oTrees.get(mp_oTrees.size() - 1);
          // Get the code
          int iCode = new Integer(oAttributes.getValue("c")).intValue();
          Float fValue = new Float(String.valueOf(oData));
          Integer iIndex = mp_iTreeFloatTransforms.get(
              m_iCurrentSpecies).get(m_iCurrentTreeType).get(
                  iCode);
          if (iIndex == null) {
            throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                "There was an error reading the tree map. One "
                + "of the float data codes appears to be invalid"
                + " for species "
                + getSpeciesNameFromCode(m_iCurrentSpecies) + ", type "
                + getTypeNameFromCode(m_iCurrentTreeType) + "."));
          }

          // Validate if X or Y
          int iIndVal = iIndex.intValue();
          float fVal = fValue.floatValue();
          String sName = mp_sTreeFloatDataMembers.get(
              oTree.m_iSpecies).get(oTree.m_iType).get(iIndVal);
          if ((sName.equals("X") && (fVal < 0 || fVal >= m_oManager.getPlot()
              .getPlotXLength()))
              || (sName.equals("Y") && (fVal < 0 || fVal >= m_oManager
                  .getPlot().getPlotYLength()))) {
            ModelException oErr = new ModelException(ErrorGUI.BAD_DATA,
                "JAVA",
            "The tree map contains a tree whose X or Y coordinate is outside the plot.");
            throw (oErr);
          }

          oTree.setValue(iIndVal, fValue);          
        }
        return true;
      } else if (sXMLTag.equals("int")) {
        if (m_iCurrentSpecies > -1 && m_iCurrentTreeType > -1) {
          // Get the last added tree
          Tree oTree = mp_oTrees.get(mp_oTrees.size() - 1);
          // Get the code
          int iCode = new Integer(oAttributes.getValue("c")).intValue();
          Integer iValue = new Integer(String.valueOf(oData)), 
                  iIndex = (Integer) mp_iTreeIntTransforms.get(m_iCurrentSpecies).get(m_iCurrentTreeType).get(iCode);
          if (iIndex == null) {
            throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                "There was an error reading the tree map.  "
                + "One of the integer data codes appears to be "
                + "invalid for species "
                + getSpeciesNameFromCode(m_iCurrentSpecies) + ", type "
                + getTypeNameFromCode(m_iCurrentTreeType) + "."));
          }
          oTree.setValue(iIndex.intValue(), iValue);          
        }
        return true;
      } else if (sXMLTag.equals("ch")) {
        if (m_iCurrentSpecies > -1 && m_iCurrentTreeType > -1) {
          // Get the last added tree
          Tree oTree = mp_oTrees.get(mp_oTrees.size() - 1);
          // Get the code
          int iCode = new Integer(oAttributes.getValue("c")).intValue();
          Integer iIndex = (Integer) mp_iTreeCharTransforms.get(m_iCurrentSpecies).get(m_iCurrentTreeType).get(iCode);
          if (iIndex == null) {
            throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                "There was an error reading the tree map. One "
                + "of the string data codes appears to be invalid"
                + " for species "
                + getSpeciesNameFromCode(m_iCurrentSpecies) + ", type "
                + getTypeNameFromCode(m_iCurrentTreeType) + "."));
          }
          oTree.setValue(iIndex.intValue(), String.valueOf(oData));          
        }
        return true;
      } else if (sXMLTag.equals("bl")) {
        // 

        if (m_iCurrentSpecies > -1 && m_iCurrentTreeType > -1) {
          // Get the last added tree
          Tree oTree = mp_oTrees.get(mp_oTrees.size() - 1);
          // Get the code
          int iCode = new Integer(oAttributes.getValue("c")).intValue();
          Boolean bValue = new Boolean(String.valueOf(oData));
          Integer iIndex = (Integer) mp_iTreeBoolTransforms.get(m_iCurrentSpecies).get(m_iCurrentTreeType).get(iCode);
          if (iIndex == null) {
            throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                "There was an error reading the tree map. One "
                + "of the bool data codes appears to be invalid"
                + " for species "
                + getSpeciesNameFromCode(m_iCurrentSpecies) + ", type "
                + getTypeNameFromCode(m_iCurrentTreeType) + "."));

          }
          oTree.setValue(iIndex.intValue(), bValue);          
        }
        return true;
      } else if (sXMLTag.equals("tm_floatCode")) {
        String sLabel = oAttributes.getValue("label");
        int iIndex = new Integer(oData.toString()).intValue(), 
            iOfficialIndex = getCodeForDataMember(mp_sTreeFloatDataMembers.get(m_iCurrentSpecies).get(m_iCurrentTreeType), sLabel);
        if (mp_iTreeFloatTransforms.get(m_iCurrentSpecies).get(m_iCurrentTreeType).size() <= iIndex) {
          Behavior.ensureSize(mp_iTreeFloatTransforms.get(m_iCurrentSpecies).get(m_iCurrentTreeType), iIndex + 1);
        }
        mp_iTreeFloatTransforms.get(m_iCurrentSpecies).get(
            m_iCurrentTreeType).set(iIndex, new Integer(iOfficialIndex));

        return true;
      } else if (sXMLTag.equals("tm_intCode")) {
        String sLabel = oAttributes.getValue("label");
        int iIndex = new Integer(oData.toString()).intValue(), iOfficialIndex = getCodeForDataMember(
            mp_sTreeIntDataMembers.get(m_iCurrentSpecies).get(
                m_iCurrentTreeType), sLabel);
        if (mp_iTreeIntTransforms.get(m_iCurrentSpecies).get(
            m_iCurrentTreeType).size() <= iIndex) {
          Behavior.ensureSize(mp_iTreeIntTransforms.get(m_iCurrentSpecies).get(
              m_iCurrentTreeType), iIndex + 1);
        }
        mp_iTreeIntTransforms.get(m_iCurrentSpecies).get(
            m_iCurrentTreeType).set(iIndex, new Integer(iOfficialIndex));

        return true;
      } else if (sXMLTag.equals("tm_boolCode")) {
        String sLabel = oAttributes.getValue("label");

        //Trap for "dead" changing to int from bool - just ignore 
        if (sLabel.equals("dead")) {
          return true;
        }

        int iIndex = new Integer(oData.toString()).intValue(), iOfficialIndex = getCodeForDataMember(
            mp_sTreeBoolDataMembers.get(m_iCurrentSpecies).get(
                m_iCurrentTreeType), sLabel);
        if (mp_iTreeBoolTransforms.get(m_iCurrentSpecies).get(
            m_iCurrentTreeType).size() <= iIndex) {
          Behavior.ensureSize(mp_iTreeBoolTransforms.get(m_iCurrentSpecies).get(
              m_iCurrentTreeType), iIndex + 1);
        }
        mp_iTreeBoolTransforms.get(m_iCurrentSpecies).get(
            m_iCurrentTreeType).set(iIndex, new Integer(iOfficialIndex));

        return true;
      } else if (sXMLTag.equals("tm_charCode")) {
        String sLabel = oAttributes.getValue("label");
        int iIndex = new Integer(oData.toString()).intValue(), iOfficialIndex = getCodeForDataMember(
            mp_sTreeCharDataMembers.get(m_iCurrentSpecies).get(
                m_iCurrentTreeType), sLabel);
        if (mp_iTreeCharTransforms.get(m_iCurrentSpecies).get(
            m_iCurrentTreeType).size() <= iIndex) {
          Behavior.ensureSize(mp_iTreeCharTransforms.get(m_iCurrentSpecies).get(
              m_iCurrentTreeType), iIndex + 1);
        }
        mp_iTreeCharTransforms.get(m_iCurrentSpecies).get(
            m_iCurrentTreeType).set(iIndex, new Integer(iOfficialIndex));

        return true;
      }
      return false;
    } catch (java.lang.NumberFormatException oE) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Could not turn the value \"" + oData + "\" into a number."));
    }
  }
  
  /**
   * Reads tree species from XML parsing.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if there is a problem reading this data.
   */
  public void readSpecies(Attributes oAttributes) throws ModelException {
    // This is a species designator in our tree map
    // Extract the species name and turn into a code
    int iSpeciesCode = getSpeciesCodeFromName(oAttributes.getValue("speciesName"));

    // If unrecognized, throw an error
    if (iSpeciesCode == -1) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Unrecognized or absent species value in tree map species list."));
    }

    // Put the number in the transform list at the species counter
    mp_iSpeciesTransforms[m_iSpeciesCounter] = iSpeciesCode;
    m_iSpeciesCounter++;
  }
  
  /**
   * Reads tree settings from XML parsing.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if there is a problem reading this data.
   */
  public void readTreeSettings(Attributes oAttributes) throws ModelException {
    // Extract species and type
    m_iCurrentSpecies = getSpeciesCodeFromName(oAttributes.getValue("sp"));
    m_iCurrentTreeType = new Integer(oAttributes.getValue("tp")).intValue();

    if (m_iCurrentSpecies == -1 || 
        m_iCurrentTreeType < 0 || 
        m_iCurrentTreeType >= getNumberOfTypes()) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "tm_treeSettings has missing or invalid attributes \""
              + "sp\" or \"tp\""));
    }
  }
  
  /**
   * Reads tree parent from XML parsing.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if there is a problem reading this data.
   */
  public void readTreeParent(Attributes oAttributes) throws ModelException {
    // Extract species and type
    m_iCurrentSpecies = new Integer(oAttributes.getValue("sp")).intValue();
    m_iCurrentTreeType = new Integer(oAttributes.getValue("tp")).intValue();

    if (m_iCurrentSpecies < 0 || 
        m_iCurrentSpecies >= getNumberOfSpecies() || 
        m_iCurrentTreeType < 0 || 
        m_iCurrentTreeType >= getNumberOfTypes()) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "tm_tree has missing or invalid attributes \"" + "sp\" or \"tp\""));
    }

    // Put the current species through the transform list
    m_iCurrentSpecies = mp_iSpeciesTransforms[m_iCurrentSpecies];

    // Create the new tree
    Tree oTree = new Tree(m_iCurrentTreeType, m_iCurrentSpecies,
        mp_sTreeFloatDataMembers.get(m_iCurrentSpecies).get(
            m_iCurrentTreeType).size(), mp_sTreeIntDataMembers.get(
            m_iCurrentSpecies).get(m_iCurrentTreeType).size(),
        mp_sTreeCharDataMembers.get(m_iCurrentSpecies).get(
            m_iCurrentTreeType).size(), mp_sTreeBoolDataMembers.get(
            m_iCurrentSpecies).get(m_iCurrentTreeType).size(), this);
    mp_oTrees.add(oTree);
  }

  
  /**
   * Changes the name of a species. Everything about the species remains the
   * same except for the name. This is an easier process than actually changing
   * the species list.
   * 
   * @param sOldSpecies String Old name of the species
   * @param sNewSpecies String New name of the species
   * @throws ModelException if there is a problem.
   */
  public void changeSpeciesName(String sOldSpecies, String sNewSpecies)
      throws ModelException {
    int i;
    boolean bSpeciesExisted = false;

    // Replace the spaces in the names with underscores
    sOldSpecies = sOldSpecies.replace(' ', '_');
    sNewSpecies = sNewSpecies.replace(' ', '_');

    // Make sure the species exists before we try to change it
    for (i = 0; i < mp_sSpeciesNames.getValue().size(); i++) {
      if ((mp_sSpeciesNames.getValue().get(i))
          .equals(sOldSpecies)) {
        bSpeciesExisted = true;
        mp_sSpeciesNames.getValue().set(i, sNewSpecies);
        break;
      }
    }

    if (false == bSpeciesExisted)
      return;
    m_oManager.changeOfSpeciesName(sOldSpecies, sNewSpecies);
  }

  /**
   * Makes one species exactly like another. Any difference from the target
   * species is obliterated. Both species names must already exist (i.e. add a
   * new species before making it into a copy of another). This triggers the
   * change through the other GUI objects.
   * 
   * @param sCopyFrom String Name of species to copy.
   * @param sCopyTo String Name of species that is the copy.
   * @throws ModelException
   */
  public void doCopySpecies(String sCopyFrom, String sCopyTo)
      throws ModelException {
    // Verify that the old and new species both exist
    int iCopyFrom = getSpeciesCodeFromName(sCopyFrom), iCopyTo = getSpeciesCodeFromName(sCopyTo);
    if (-1 == iCopyFrom || -1 == iCopyTo) {
      throw new ModelException(ErrorGUI.BAD_COMMAND, "JAVA",
          "I can't copy species \"" + sCopyFrom + "\" to species \"" + sCopyTo
              + "\" because they don't both exist.");
    }

    m_oManager.copySpecies(iCopyFrom, iCopyTo);
  }
  
  /**
   * Gets the allometry object.
   * @return Allometry object, or null if none exists.
    */
  public Allometry getAllometry() {

    for (int i = 0; i < mp_oInstantiatedBehaviors.size(); i++) {
      if (mp_oInstantiatedBehaviors.get(i) instanceof Allometry) {
        return (Allometry) mp_oInstantiatedBehaviors.get(i);
      }
    }
    return null;
  }

  /**
   * Overridden to ignore position.
   */
  public Behavior getBehaviorByXMLParametersParentTag(String sXMLTag, int iPos) {
    if (mp_oInstantiatedBehaviors != null) {
      Behavior oBeh;
      int i;
      for (i = 0; i < mp_oInstantiatedBehaviors.size(); i++) {
        oBeh = mp_oInstantiatedBehaviors.get(i);
        if (oBeh.getXMLParametersRoot().equals(sXMLTag)) {
          return oBeh;
        }
      }
    }
    return null;
  }
  
  

  /**
   * Overridden to not create.
   */
  public Behavior createBehaviorFromParameterFileTag(String sParameterFileTag)
      throws ModelException {
    if (mp_oInstantiatedBehaviors != null) {
      Behavior oBeh;
      int i;
      for (i = 0; i < mp_oInstantiatedBehaviors.size(); i++) {
        oBeh = mp_oInstantiatedBehaviors.get(i);
        if (oBeh.getParameterFileBehaviorName().equals(sParameterFileTag)) {
          return oBeh;
        }
      }
    }
    return null;
  }

  /**
   * Overridden to let allometry do setup.
   */
  public void doSetup(TreePopulation oPop) throws ModelException {
    super.doSetup(oPop);
    getAllometry().doSetup(oPop);
  }
  
  
}