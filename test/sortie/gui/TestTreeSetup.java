package sortie.gui;

import java.io.File;
import java.io.FileWriter;
import java.awt.event.ActionEvent;

import sortie.ModelTestCase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.RenamePair;
import sortie.gui.TreeSpeciesSetup;

public class TestTreeSetup extends ModelTestCase {

  /**
   * This tests species removing.
   */
  public void testSpeciesRemove() {
    String sFileName = null;
    try {
      MainWindow oWindow = new MainWindow();
      GUIManager oManager = oWindow.getDataManager();
      sFileName = writeXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();

      TreeSpeciesSetup oSetup = new TreeSpeciesSetup(null, oPop);

      //Add two new species
      oSetup.m_jNewSpecies.setText("New Fir");
      oSetup.actionPerformed(new ActionEvent(this, 0, "Add"));
      oSetup.m_jNewSpecies.setText("New Spruce");
      oSetup.actionPerformed(new ActionEvent(this, 0, "Add"));

      //Set one of the species to be a copy of another species
      RenamePair oPair = new RenamePair();
      oPair.m_sNewName = "New Fir";
      oPair.m_sOldName = "Amabilis Fir";
      oSetup.mp_oCopySpecies.add(oPair);

      //Rename the other new species
      oSetup.m_jNewSpecies.setText("Better Spruce");
      oSetup.m_jSpeciesList.setSelectedValue("New Spruce", false);
      oSetup.actionPerformed(new ActionEvent(this, 0, "Rename"));

      //Now delete the new species
      oSetup.m_jSpeciesList.setSelectedValue("New Fir", false);
      oSetup.actionPerformed(new ActionEvent(this, 0, "Remove"));
      oSetup.m_jSpeciesList.setSelectedValue("Better Spruce", false);
      oSetup.actionPerformed(new ActionEvent(this, 0, "Remove"));

      //Now say "OK"
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));

      //Make sure the tree population has the right species
      assertEquals(9, oPop.getNumberOfSpecies());
      assertEquals("Western_Hemlock", oPop.getSpeciesNameFromCode(0));
      assertEquals("Western_Redcedar", oPop.getSpeciesNameFromCode(1));
      assertEquals("Amabilis_Fir", oPop.getSpeciesNameFromCode(2));
      assertEquals("Subalpine_Fir", oPop.getSpeciesNameFromCode(3));
      assertEquals("Hybrid_Spruce", oPop.getSpeciesNameFromCode(4));
      assertEquals("Lodgepole_Pine", oPop.getSpeciesNameFromCode(5));
      assertEquals("Trembling_Aspen", oPop.getSpeciesNameFromCode(6));
      assertEquals("Black_Cottonwood", oPop.getSpeciesNameFromCode(7));
      assertEquals("Paper_Birch", oPop.getSpeciesNameFromCode(8));

      System.out.println(
        "Normal processing succeeded for testSpeciesRemove.");
      }
      catch (ModelException oErr) {
        fail("testChangeOfSpecies failed with message " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
  }

  /**
   * Writes a file to load in some species.
   * @throws ModelException if there is a problem writing the file.
   * @return String Filename just written.
   */
  private String writeXMLFile() throws ModelException {
    try {
      String sFileName = "\\loratestxml3.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>100</timesteps>");
      oOut.write("<randomSeed>0</randomSeed>");
      oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("<plot_title>Default parameter file-use for testing only</plot_title>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"Western_Hemlock\"/>");
      oOut.write("<tr_species speciesName=\"Western_Redcedar\"/>");
      oOut.write("<tr_species speciesName=\"Amabilis_Fir\"/>");
      oOut.write("<tr_species speciesName=\"Subalpine_Fir\"/>");
      oOut.write("<tr_species speciesName=\"Hybrid_Spruce\"/>");
      oOut.write("<tr_species speciesName=\"Lodgepole_Pine\"/>");
      oOut.write("<tr_species speciesName=\"Trembling_Aspen\"/>");
      oOut.write("<tr_species speciesName=\"Black_Cottonwood\"/>");
      oOut.write("<tr_species speciesName=\"Paper_Birch\"/>");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Windstorm</behaviorName>");
      oOut.write("<version>2.0</version>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
}
