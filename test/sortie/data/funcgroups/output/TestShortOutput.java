package sortie.data.funcgroups.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import sortie.ModelTestCase;
import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.funcgroups.Subplot;
import sortie.data.funcgroups.TreeOutputSaveInfo;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

public class TestShortOutput extends ModelTestCase {
  
  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      OutputBehaviors oOb = oManager.getOutputBehaviors();
      ShortOutput oOutput = oOb.getShortOutput();

      assertEquals("Short Run 1.out", oOutput.m_sShortOutputFilename.getValue());
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.NOTDEAD));
      assertFalse(oOutput.getSaveRelativeDensity(TreePopulation.SEEDLING));
      
      assertTrue(oOutput.getSaveRelativeBasalArea(TreePopulation.SAPLING));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.NOTDEAD));
      assertTrue(oOutput.getSaveRelativeDensity(TreePopulation.SAPLING));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.NOTDEAD));
      
      assertFalse(oOutput.getSaveRelativeBasalArea(TreePopulation.ADULT));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.NOTDEAD));
      assertTrue(oOutput.getSaveRelativeDensity(TreePopulation.ADULT));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.NOTDEAD));
      
      assertTrue(oOutput.getSaveRelativeBasalArea(TreePopulation.SNAG));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.NOTDEAD));
      assertTrue(oOutput.getSaveRelativeDensity(TreePopulation.SNAG));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.NOTDEAD));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.HARVEST));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SEEDLING, OutputBehaviors.HARVEST));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.NATURAL));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SEEDLING, OutputBehaviors.NATURAL));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.DISEASE));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SEEDLING, OutputBehaviors.DISEASE));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.FIRE));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SEEDLING, OutputBehaviors.FIRE));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.INSECTS));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SEEDLING, OutputBehaviors.INSECTS));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.STORM));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SEEDLING, OutputBehaviors.STORM));
      
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.HARVEST));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.HARVEST));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.NATURAL));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.NATURAL));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.DISEASE));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.DISEASE));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.FIRE));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.FIRE));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.INSECTS));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.INSECTS));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.STORM));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.STORM));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.HARVEST));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.HARVEST));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.NATURAL));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.NATURAL));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.DISEASE));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.DISEASE));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.FIRE));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.FIRE));
      
      assertFalse(oOutput.getSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.INSECTS));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.INSECTS));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.STORM));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.STORM));
      
      assertFalse(oOutput.getSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.HARVEST));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.HARVEST));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.NATURAL));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.NATURAL));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.DISEASE));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.DISEASE));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.FIRE));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.FIRE));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.INSECTS));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.INSECTS));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.STORM));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.STORM));
      
      assertEquals(2, oOutput.getNumberOfShortSubplots());
      
      Subplot oSubplot = oOutput.getShortSubplot(0);
      assertEquals("Subplot 1", oSubplot.getSubplotName());
      
      assertEquals(107, oSubplot.getNumberOfCells());
      int i = 0;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(0, oSubplot.getCell(i).getY()); i++;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(1, oSubplot.getCell(i).getY()); i++;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(2, oSubplot.getCell(i).getY()); i++;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(3, oSubplot.getCell(i).getY()); i++;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(4, oSubplot.getCell(i).getY()); i++;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(5, oSubplot.getCell(i).getY()); i++;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(6, oSubplot.getCell(i).getY()); i++;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(7, oSubplot.getCell(i).getY()); i++;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(8, oSubplot.getCell(i).getY()); i++;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(9, oSubplot.getCell(i).getY()); i++;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(10, oSubplot.getCell(i).getY()); i++;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(11, oSubplot.getCell(i).getY()); i++;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(12, oSubplot.getCell(i).getY()); i++;
      assertEquals(1, oSubplot.getCell(i).getX()); assertEquals(0, oSubplot.getCell(i).getY()); i++;
      assertEquals(1, oSubplot.getCell(i).getX()); assertEquals(1, oSubplot.getCell(i).getY()); i++;
      assertEquals(1, oSubplot.getCell(i).getX()); assertEquals(2, oSubplot.getCell(i).getY()); i++;
      assertEquals(1, oSubplot.getCell(i).getX()); assertEquals(3, oSubplot.getCell(i).getY()); i++;
      assertEquals(1, oSubplot.getCell(i).getX()); assertEquals(4, oSubplot.getCell(i).getY()); i++;
      assertEquals(1, oSubplot.getCell(i).getX()); assertEquals(5, oSubplot.getCell(i).getY()); i++;
      assertEquals(1, oSubplot.getCell(i).getX()); assertEquals(6, oSubplot.getCell(i).getY()); i++;
      assertEquals(1, oSubplot.getCell(i).getX()); assertEquals(7, oSubplot.getCell(i).getY()); i++;
      assertEquals(1, oSubplot.getCell(i).getX()); assertEquals(8, oSubplot.getCell(i).getY()); i++;
      assertEquals(1, oSubplot.getCell(i).getX()); assertEquals(9, oSubplot.getCell(i).getY()); i++;
      assertEquals(1, oSubplot.getCell(i).getX()); assertEquals(10, oSubplot.getCell(i).getY()); i++;
      assertEquals(1, oSubplot.getCell(i).getX()); assertEquals(11, oSubplot.getCell(i).getY()); i++;
      assertEquals(1, oSubplot.getCell(i).getX()); assertEquals(12, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(0, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(1, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(2, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(3, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(4, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(5, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(6, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(7, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(8, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(9, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(10, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(11, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(12, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(0, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(1, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(2, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(3, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(4, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(5, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(6, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(7, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(8, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(9, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(10, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(11, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(12, oSubplot.getCell(i).getY()); i++;
      assertEquals(4, oSubplot.getCell(i).getX()); assertEquals(0, oSubplot.getCell(i).getY()); i++;
      assertEquals(4, oSubplot.getCell(i).getX()); assertEquals(1, oSubplot.getCell(i).getY()); i++;
      assertEquals(4, oSubplot.getCell(i).getX()); assertEquals(2, oSubplot.getCell(i).getY()); i++;
      assertEquals(4, oSubplot.getCell(i).getX()); assertEquals(3, oSubplot.getCell(i).getY()); i++;
      assertEquals(4, oSubplot.getCell(i).getX()); assertEquals(4, oSubplot.getCell(i).getY()); i++;
      assertEquals(4, oSubplot.getCell(i).getX()); assertEquals(5, oSubplot.getCell(i).getY()); i++;
      assertEquals(4, oSubplot.getCell(i).getX()); assertEquals(6, oSubplot.getCell(i).getY()); i++;
      assertEquals(4, oSubplot.getCell(i).getX()); assertEquals(7, oSubplot.getCell(i).getY()); i++;
      assertEquals(4, oSubplot.getCell(i).getX()); assertEquals(8, oSubplot.getCell(i).getY()); i++;
      assertEquals(4, oSubplot.getCell(i).getX()); assertEquals(9, oSubplot.getCell(i).getY()); i++;
      assertEquals(4, oSubplot.getCell(i).getX()); assertEquals(10, oSubplot.getCell(i).getY()); i++;
      assertEquals(4, oSubplot.getCell(i).getX()); assertEquals(11, oSubplot.getCell(i).getY()); i++;
      assertEquals(4, oSubplot.getCell(i).getX()); assertEquals(12, oSubplot.getCell(i).getY()); i++;
      assertEquals(5, oSubplot.getCell(i).getX()); assertEquals(0, oSubplot.getCell(i).getY()); i++;
      assertEquals(5, oSubplot.getCell(i).getX()); assertEquals(1, oSubplot.getCell(i).getY()); i++;
      assertEquals(5, oSubplot.getCell(i).getX()); assertEquals(2, oSubplot.getCell(i).getY()); i++;
      assertEquals(5, oSubplot.getCell(i).getX()); assertEquals(3, oSubplot.getCell(i).getY()); i++;
      assertEquals(5, oSubplot.getCell(i).getX()); assertEquals(4, oSubplot.getCell(i).getY()); i++;
      assertEquals(5, oSubplot.getCell(i).getX()); assertEquals(5, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(3, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(4, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(5, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(6, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(7, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(8, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(9, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(10, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(11, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(12, oSubplot.getCell(i).getY()); i++;
      assertEquals(7, oSubplot.getCell(i).getX()); assertEquals(0, oSubplot.getCell(i).getY()); i++;
      assertEquals(7, oSubplot.getCell(i).getX()); assertEquals(1, oSubplot.getCell(i).getY()); i++;
      assertEquals(7, oSubplot.getCell(i).getX()); assertEquals(2, oSubplot.getCell(i).getY()); i++;
      assertEquals(7, oSubplot.getCell(i).getX()); assertEquals(3, oSubplot.getCell(i).getY()); i++;
      assertEquals(7, oSubplot.getCell(i).getX()); assertEquals(4, oSubplot.getCell(i).getY()); i++;
      assertEquals(7, oSubplot.getCell(i).getX()); assertEquals(5, oSubplot.getCell(i).getY()); i++;
      assertEquals(8, oSubplot.getCell(i).getX()); assertEquals(6, oSubplot.getCell(i).getY()); i++;
      assertEquals(8, oSubplot.getCell(i).getX()); assertEquals(7, oSubplot.getCell(i).getY()); i++;
      assertEquals(8, oSubplot.getCell(i).getX()); assertEquals(8, oSubplot.getCell(i).getY()); i++;
      assertEquals(8, oSubplot.getCell(i).getX()); assertEquals(9, oSubplot.getCell(i).getY()); i++;
      assertEquals(8, oSubplot.getCell(i).getX()); assertEquals(10, oSubplot.getCell(i).getY()); i++;
      assertEquals(8, oSubplot.getCell(i).getX()); assertEquals(11, oSubplot.getCell(i).getY()); i++;
      assertEquals(10, oSubplot.getCell(i).getX()); assertEquals(0, oSubplot.getCell(i).getY()); i++;
      assertEquals(10, oSubplot.getCell(i).getX()); assertEquals(1, oSubplot.getCell(i).getY()); i++;
      assertEquals(10, oSubplot.getCell(i).getX()); assertEquals(2, oSubplot.getCell(i).getY()); i++;
      assertEquals(10, oSubplot.getCell(i).getX()); assertEquals(3, oSubplot.getCell(i).getY()); i++;
      assertEquals(10, oSubplot.getCell(i).getX()); assertEquals(4, oSubplot.getCell(i).getY()); i++;
      assertEquals(10, oSubplot.getCell(i).getX()); assertEquals(5, oSubplot.getCell(i).getY()); i++;
      assertEquals(10, oSubplot.getCell(i).getX()); assertEquals(6, oSubplot.getCell(i).getY()); i++;
      assertEquals(10, oSubplot.getCell(i).getX()); assertEquals(7, oSubplot.getCell(i).getY()); i++;
      assertEquals(11, oSubplot.getCell(i).getX()); assertEquals(7, oSubplot.getCell(i).getY()); i++;
      assertEquals(11, oSubplot.getCell(i).getX()); assertEquals(8, oSubplot.getCell(i).getY()); i++;
      assertEquals(12, oSubplot.getCell(i).getX()); assertEquals(9, oSubplot.getCell(i).getY()); i++;
      assertEquals(12, oSubplot.getCell(i).getX()); assertEquals(10, oSubplot.getCell(i).getY()); i++;
      assertEquals(12, oSubplot.getCell(i).getX()); assertEquals(11, oSubplot.getCell(i).getY()); i++;
      assertEquals(12, oSubplot.getCell(i).getX()); assertEquals(12, oSubplot.getCell(i).getY()); i++;
      
      oSubplot = oOutput.getShortSubplot(1);
      assertEquals("Subplot 2", oSubplot.getSubplotName());
      
      assertEquals(44, oSubplot.getNumberOfCells());
      i = 0;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(6, oSubplot.getCell(i).getY()); i++;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(7, oSubplot.getCell(i).getY()); i++;
      assertEquals(0, oSubplot.getCell(i).getX()); assertEquals(8, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(14, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(15, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(16, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(17, oSubplot.getCell(i).getY()); i++;
      assertEquals(2, oSubplot.getCell(i).getX()); assertEquals(18, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(6, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(7, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(8, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(9, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(12, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(13, oSubplot.getCell(i).getY()); i++;
      assertEquals(3, oSubplot.getCell(i).getX()); assertEquals(14, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(6, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(7, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(8, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(9, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(12, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(13, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(14, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(15, oSubplot.getCell(i).getY()); i++;
      assertEquals(6, oSubplot.getCell(i).getX()); assertEquals(16, oSubplot.getCell(i).getY()); i++;
      assertEquals(9, oSubplot.getCell(i).getX()); assertEquals(8, oSubplot.getCell(i).getY()); i++;
      assertEquals(9, oSubplot.getCell(i).getX()); assertEquals(9, oSubplot.getCell(i).getY()); i++;
      assertEquals(9, oSubplot.getCell(i).getX()); assertEquals(12, oSubplot.getCell(i).getY()); i++;
      assertEquals(11, oSubplot.getCell(i).getX()); assertEquals(8, oSubplot.getCell(i).getY()); i++;
      assertEquals(11, oSubplot.getCell(i).getX()); assertEquals(9, oSubplot.getCell(i).getY()); i++;
      assertEquals(13, oSubplot.getCell(i).getX()); assertEquals(8, oSubplot.getCell(i).getY()); i++;
      assertEquals(13, oSubplot.getCell(i).getX()); assertEquals(9, oSubplot.getCell(i).getY()); i++;
      assertEquals(13, oSubplot.getCell(i).getX()); assertEquals(12, oSubplot.getCell(i).getY()); i++;
      assertEquals(15, oSubplot.getCell(i).getX()); assertEquals(13, oSubplot.getCell(i).getY()); i++;
      assertEquals(15, oSubplot.getCell(i).getX()); assertEquals(14, oSubplot.getCell(i).getY()); i++;
      assertEquals(15, oSubplot.getCell(i).getX()); assertEquals(15, oSubplot.getCell(i).getY()); i++;
      assertEquals(15, oSubplot.getCell(i).getX()); assertEquals(16, oSubplot.getCell(i).getY()); i++;
      assertEquals(15, oSubplot.getCell(i).getX()); assertEquals(17, oSubplot.getCell(i).getY()); i++;
      assertEquals(15, oSubplot.getCell(i).getX()); assertEquals(18, oSubplot.getCell(i).getY()); i++;
      assertEquals(17, oSubplot.getCell(i).getX()); assertEquals(15, oSubplot.getCell(i).getY()); i++;
      assertEquals(17, oSubplot.getCell(i).getX()); assertEquals(16, oSubplot.getCell(i).getY()); i++;
      assertEquals(17, oSubplot.getCell(i).getX()); assertEquals(17, oSubplot.getCell(i).getY()); i++;
      assertEquals(18, oSubplot.getCell(i).getX()); assertEquals(16, oSubplot.getCell(i).getY()); i++;
      assertEquals(18, oSubplot.getCell(i).getX()); assertEquals(17, oSubplot.getCell(i).getY()); i++;
      assertEquals(18, oSubplot.getCell(i).getX()); assertEquals(18, oSubplot.getCell(i).getY()); i++;
      
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }

  /**
   * Tests shrinking of subplots when the plot resolution is changed.
   */
  public void testChangeOfPlotResolution() {
    String sFileName = null;
    try {
      GUIManager oManager = new GUIManager(null);

      sFileName = writeXMLFile4();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oOb = oManager.getOutputBehaviors();
      ShortOutput oOutput = oOb.getShortOutput();

      //*****************************
      //Verify the initial data
      //*****************************
      assertEquals(3, oOutput.mp_oShortOutputSubplots.size());

      //Summary output subplots
      Subplot oSubplot = oOutput.mp_oShortOutputSubplots.get(0);
      assertEquals("plot 1", oSubplot.getSubplotName());
      assertEquals(25, oSubplot.getNumberOfCells());
      assertEquals(20, oSubplot.getCell(0).getX()); assertEquals(20, oSubplot.getCell(0).getY());
      assertEquals(20, oSubplot.getCell(1).getX()); assertEquals(21, oSubplot.getCell(1).getY());
      assertEquals(20, oSubplot.getCell(2).getX()); assertEquals(22, oSubplot.getCell(2).getY());
      assertEquals(20, oSubplot.getCell(3).getX()); assertEquals(23, oSubplot.getCell(3).getY());
      assertEquals(20, oSubplot.getCell(4).getX()); assertEquals(24, oSubplot.getCell(4).getY());
      assertEquals(21, oSubplot.getCell(5).getX()); assertEquals(20, oSubplot.getCell(5).getY());
      assertEquals(21, oSubplot.getCell(6).getX()); assertEquals(21, oSubplot.getCell(6).getY());
      assertEquals(21, oSubplot.getCell(7).getX()); assertEquals(22, oSubplot.getCell(7).getY());
      assertEquals(21, oSubplot.getCell(8).getX()); assertEquals(23, oSubplot.getCell(8).getY());
      assertEquals(21, oSubplot.getCell(9).getX()); assertEquals(24, oSubplot.getCell(9).getY());
      assertEquals(22, oSubplot.getCell(10).getX()); assertEquals(20, oSubplot.getCell(10).getY());
      assertEquals(22, oSubplot.getCell(11).getX()); assertEquals(21, oSubplot.getCell(11).getY());
      assertEquals(22, oSubplot.getCell(12).getX()); assertEquals(22, oSubplot.getCell(12).getY());
      assertEquals(22, oSubplot.getCell(13).getX()); assertEquals(23, oSubplot.getCell(13).getY());
      assertEquals(22, oSubplot.getCell(14).getX()); assertEquals(24, oSubplot.getCell(14).getY());
      assertEquals(23, oSubplot.getCell(15).getX()); assertEquals(20, oSubplot.getCell(15).getY());
      assertEquals(23, oSubplot.getCell(16).getX()); assertEquals(21, oSubplot.getCell(16).getY());
      assertEquals(23, oSubplot.getCell(17).getX()); assertEquals(22, oSubplot.getCell(17).getY());
      assertEquals(23, oSubplot.getCell(18).getX()); assertEquals(23, oSubplot.getCell(18).getY());
      assertEquals(23, oSubplot.getCell(19).getX()); assertEquals(24, oSubplot.getCell(19).getY());
      assertEquals(24, oSubplot.getCell(20).getX()); assertEquals(20, oSubplot.getCell(20).getY());
      assertEquals(24, oSubplot.getCell(21).getX()); assertEquals(21, oSubplot.getCell(21).getY());
      assertEquals(24, oSubplot.getCell(22).getX()); assertEquals(22, oSubplot.getCell(22).getY());
      assertEquals(24, oSubplot.getCell(23).getX()); assertEquals(23, oSubplot.getCell(23).getY());

      oSubplot = oOutput.mp_oShortOutputSubplots.get(1);
      assertEquals("plot 2", oSubplot.getSubplotName());
      assertEquals(48, oSubplot.getNumberOfCells());
      assertEquals(0, oSubplot.getCell(0).getX()); assertEquals(0, oSubplot.getCell(0).getY());
      assertEquals(0, oSubplot.getCell(1).getX()); assertEquals(1, oSubplot.getCell(1).getY());
      assertEquals(0, oSubplot.getCell(2).getX()); assertEquals(2, oSubplot.getCell(2).getY());
      assertEquals(0, oSubplot.getCell(3).getX()); assertEquals(3, oSubplot.getCell(3).getY());
      assertEquals(0, oSubplot.getCell(4).getX()); assertEquals(4, oSubplot.getCell(4).getY());
      assertEquals(0, oSubplot.getCell(5).getX()); assertEquals(5, oSubplot.getCell(5).getY());
      assertEquals(0, oSubplot.getCell(6).getX()); assertEquals(6, oSubplot.getCell(6).getY());
      assertEquals(0, oSubplot.getCell(7).getX()); assertEquals(7, oSubplot.getCell(7).getY());
      assertEquals(0, oSubplot.getCell(8).getX()); assertEquals(8, oSubplot.getCell(8).getY());
      assertEquals(0, oSubplot.getCell(9).getX()); assertEquals(9, oSubplot.getCell(9).getY());
      assertEquals(0, oSubplot.getCell(10).getX()); assertEquals(10, oSubplot.getCell(10).getY());
      assertEquals(0, oSubplot.getCell(11).getX()); assertEquals(11, oSubplot.getCell(11).getY());
      assertEquals(0, oSubplot.getCell(12).getX()); assertEquals(12, oSubplot.getCell(12).getY());
      assertEquals(0, oSubplot.getCell(13).getX()); assertEquals(13, oSubplot.getCell(13).getY());
      assertEquals(0, oSubplot.getCell(14).getX()); assertEquals(14, oSubplot.getCell(14).getY());
      assertEquals(0, oSubplot.getCell(15).getX()); assertEquals(15, oSubplot.getCell(15).getY());
      assertEquals(0, oSubplot.getCell(16).getX()); assertEquals(16, oSubplot.getCell(16).getY());
      assertEquals(0, oSubplot.getCell(17).getX()); assertEquals(17, oSubplot.getCell(17).getY());
      assertEquals(0, oSubplot.getCell(18).getX()); assertEquals(24, oSubplot.getCell(18).getY());
      assertEquals(1, oSubplot.getCell(19).getX()); assertEquals(24, oSubplot.getCell(19).getY());
      assertEquals(2, oSubplot.getCell(20).getX()); assertEquals(0, oSubplot.getCell(20).getY());
      assertEquals(2, oSubplot.getCell(21).getX()); assertEquals(1, oSubplot.getCell(21).getY());
      assertEquals(2, oSubplot.getCell(22).getX()); assertEquals(2, oSubplot.getCell(22).getY());
      assertEquals(2, oSubplot.getCell(23).getX()); assertEquals(3, oSubplot.getCell(23).getY());
      assertEquals(2, oSubplot.getCell(24).getX()); assertEquals(4, oSubplot.getCell(24).getY());
      assertEquals(2, oSubplot.getCell(25).getX()); assertEquals(5, oSubplot.getCell(25).getY());
      assertEquals(2, oSubplot.getCell(26).getX()); assertEquals(6, oSubplot.getCell(26).getY());
      assertEquals(2, oSubplot.getCell(27).getX()); assertEquals(7, oSubplot.getCell(27).getY());
      assertEquals(2, oSubplot.getCell(28).getX()); assertEquals(8, oSubplot.getCell(28).getY());
      assertEquals(2, oSubplot.getCell(29).getX()); assertEquals(9, oSubplot.getCell(29).getY());
      assertEquals(2, oSubplot.getCell(30).getX()); assertEquals(10, oSubplot.getCell(30).getY());
      assertEquals(2, oSubplot.getCell(31).getX()); assertEquals(11, oSubplot.getCell(31).getY());
      assertEquals(2, oSubplot.getCell(32).getX()); assertEquals(12, oSubplot.getCell(32).getY());
      assertEquals(2, oSubplot.getCell(33).getX()); assertEquals(13, oSubplot.getCell(33).getY());
      assertEquals(2, oSubplot.getCell(34).getX()); assertEquals(14, oSubplot.getCell(34).getY());
      assertEquals(2, oSubplot.getCell(35).getX()); assertEquals(15, oSubplot.getCell(35).getY());
      assertEquals(2, oSubplot.getCell(36).getX()); assertEquals(16, oSubplot.getCell(36).getY());
      assertEquals(2, oSubplot.getCell(37).getX()); assertEquals(17, oSubplot.getCell(37).getY());
      assertEquals(2, oSubplot.getCell(38).getX()); assertEquals(24, oSubplot.getCell(38).getY());
      assertEquals(3, oSubplot.getCell(39).getX()); assertEquals(24, oSubplot.getCell(39).getY());
      assertEquals(4, oSubplot.getCell(40).getX()); assertEquals(24, oSubplot.getCell(40).getY());
      assertEquals(5, oSubplot.getCell(41).getX()); assertEquals(24, oSubplot.getCell(41).getY());
      assertEquals(24, oSubplot.getCell(42).getX()); assertEquals(0, oSubplot.getCell(42).getY());
      assertEquals(24, oSubplot.getCell(43).getX()); assertEquals(1, oSubplot.getCell(43).getY());
      assertEquals(24, oSubplot.getCell(44).getX()); assertEquals(2, oSubplot.getCell(44).getY());
      assertEquals(24, oSubplot.getCell(45).getX()); assertEquals(3, oSubplot.getCell(45).getY());
      assertEquals(24, oSubplot.getCell(46).getX()); assertEquals(4, oSubplot.getCell(46).getY());
      assertEquals(24, oSubplot.getCell(47).getX()); assertEquals(5, oSubplot.getCell(47).getY());

      oSubplot = oOutput.mp_oShortOutputSubplots.get(2);
      assertEquals("plot 3", oSubplot.getSubplotName());
      assertEquals(72, oSubplot.getNumberOfCells());
      assertEquals(0, oSubplot.getCell(0).getX()); assertEquals(0, oSubplot.getCell(0).getY());
      assertEquals(0, oSubplot.getCell(1).getX()); assertEquals(1, oSubplot.getCell(1).getY());
      assertEquals(0, oSubplot.getCell(2).getX()); assertEquals(2, oSubplot.getCell(2).getY());
      assertEquals(0, oSubplot.getCell(3).getX()); assertEquals(3, oSubplot.getCell(3).getY());
      assertEquals(0, oSubplot.getCell(4).getX()); assertEquals(4, oSubplot.getCell(4).getY());
      assertEquals(0, oSubplot.getCell(5).getX()); assertEquals(5, oSubplot.getCell(5).getY());
      assertEquals(1, oSubplot.getCell(6).getX()); assertEquals(0, oSubplot.getCell(6).getY());
      assertEquals(1, oSubplot.getCell(7).getX()); assertEquals(1, oSubplot.getCell(7).getY());
      assertEquals(1, oSubplot.getCell(8).getX()); assertEquals(2, oSubplot.getCell(8).getY());
      assertEquals(1, oSubplot.getCell(9).getX()); assertEquals(3, oSubplot.getCell(9).getY());
      assertEquals(1, oSubplot.getCell(10).getX()); assertEquals(4, oSubplot.getCell(10).getY());
      assertEquals(1, oSubplot.getCell(11).getX()); assertEquals(5, oSubplot.getCell(11).getY());
      assertEquals(2, oSubplot.getCell(12).getX()); assertEquals(0, oSubplot.getCell(12).getY());
      assertEquals(2, oSubplot.getCell(13).getX()); assertEquals(1, oSubplot.getCell(13).getY());
      assertEquals(2, oSubplot.getCell(14).getX()); assertEquals(2, oSubplot.getCell(14).getY());
      assertEquals(2, oSubplot.getCell(15).getX()); assertEquals(3, oSubplot.getCell(15).getY());
      assertEquals(2, oSubplot.getCell(16).getX()); assertEquals(4, oSubplot.getCell(16).getY());
      assertEquals(2, oSubplot.getCell(17).getX()); assertEquals(5, oSubplot.getCell(17).getY());
      assertEquals(3, oSubplot.getCell(18).getX()); assertEquals(0, oSubplot.getCell(18).getY());
      assertEquals(3, oSubplot.getCell(19).getX()); assertEquals(1, oSubplot.getCell(19).getY());
      assertEquals(3, oSubplot.getCell(20).getX()); assertEquals(2, oSubplot.getCell(20).getY());
      assertEquals(3, oSubplot.getCell(21).getX()); assertEquals(3, oSubplot.getCell(21).getY());
      assertEquals(3, oSubplot.getCell(22).getX()); assertEquals(4, oSubplot.getCell(22).getY());
      assertEquals(3, oSubplot.getCell(23).getX()); assertEquals(5, oSubplot.getCell(23).getY());
      assertEquals(4, oSubplot.getCell(24).getX()); assertEquals(0, oSubplot.getCell(24).getY());
      assertEquals(4, oSubplot.getCell(25).getX()); assertEquals(1, oSubplot.getCell(25).getY());
      assertEquals(4, oSubplot.getCell(26).getX()); assertEquals(2, oSubplot.getCell(26).getY());
      assertEquals(4, oSubplot.getCell(27).getX()); assertEquals(3, oSubplot.getCell(27).getY());
      assertEquals(4, oSubplot.getCell(28).getX()); assertEquals(4, oSubplot.getCell(28).getY());
      assertEquals(4, oSubplot.getCell(29).getX()); assertEquals(5, oSubplot.getCell(29).getY());
      assertEquals(5, oSubplot.getCell(30).getX()); assertEquals(0, oSubplot.getCell(30).getY());
      assertEquals(5, oSubplot.getCell(31).getX()); assertEquals(1, oSubplot.getCell(31).getY());
      assertEquals(5, oSubplot.getCell(32).getX()); assertEquals(2, oSubplot.getCell(32).getY());
      assertEquals(5, oSubplot.getCell(33).getX()); assertEquals(3, oSubplot.getCell(33).getY());
      assertEquals(5, oSubplot.getCell(34).getX()); assertEquals(4, oSubplot.getCell(34).getY());
      assertEquals(5, oSubplot.getCell(35).getX()); assertEquals(5, oSubplot.getCell(35).getY());
      assertEquals(6, oSubplot.getCell(36).getX()); assertEquals(0, oSubplot.getCell(36).getY());
      assertEquals(6, oSubplot.getCell(37).getX()); assertEquals(1, oSubplot.getCell(37).getY());
      assertEquals(6, oSubplot.getCell(38).getX()); assertEquals(2, oSubplot.getCell(38).getY());
      assertEquals(6, oSubplot.getCell(39).getX()); assertEquals(3, oSubplot.getCell(39).getY());
      assertEquals(6, oSubplot.getCell(40).getX()); assertEquals(4, oSubplot.getCell(40).getY());
      assertEquals(6, oSubplot.getCell(41).getX()); assertEquals(5, oSubplot.getCell(41).getY());
      assertEquals(7, oSubplot.getCell(42).getX()); assertEquals(0, oSubplot.getCell(42).getY());
      assertEquals(7, oSubplot.getCell(43).getX()); assertEquals(1, oSubplot.getCell(43).getY());
      assertEquals(7, oSubplot.getCell(44).getX()); assertEquals(2, oSubplot.getCell(44).getY());
      assertEquals(7, oSubplot.getCell(45).getX()); assertEquals(3, oSubplot.getCell(45).getY());
      assertEquals(7, oSubplot.getCell(46).getX()); assertEquals(4, oSubplot.getCell(46).getY());
      assertEquals(7, oSubplot.getCell(47).getX()); assertEquals(5, oSubplot.getCell(47).getY());
      assertEquals(8, oSubplot.getCell(48).getX()); assertEquals(0, oSubplot.getCell(48).getY());
      assertEquals(8, oSubplot.getCell(49).getX()); assertEquals(1, oSubplot.getCell(49).getY());
      assertEquals(8, oSubplot.getCell(50).getX()); assertEquals(2, oSubplot.getCell(50).getY());
      assertEquals(8, oSubplot.getCell(51).getX()); assertEquals(3, oSubplot.getCell(51).getY());
      assertEquals(8, oSubplot.getCell(52).getX()); assertEquals(4, oSubplot.getCell(52).getY());
      assertEquals(8, oSubplot.getCell(53).getX()); assertEquals(5, oSubplot.getCell(53).getY());
      assertEquals(9, oSubplot.getCell(54).getX()); assertEquals(0, oSubplot.getCell(54).getY());
      assertEquals(9, oSubplot.getCell(55).getX()); assertEquals(1, oSubplot.getCell(55).getY());
      assertEquals(9, oSubplot.getCell(56).getX()); assertEquals(2, oSubplot.getCell(56).getY());
      assertEquals(9, oSubplot.getCell(57).getX()); assertEquals(3, oSubplot.getCell(57).getY());
      assertEquals(9, oSubplot.getCell(58).getX()); assertEquals(4, oSubplot.getCell(58).getY());
      assertEquals(9, oSubplot.getCell(59).getX()); assertEquals(5, oSubplot.getCell(59).getY());
      assertEquals(10, oSubplot.getCell(60).getX()); assertEquals(0, oSubplot.getCell(60).getY());
      assertEquals(10, oSubplot.getCell(61).getX()); assertEquals(1, oSubplot.getCell(61).getY());
      assertEquals(10, oSubplot.getCell(62).getX()); assertEquals(2, oSubplot.getCell(62).getY());
      assertEquals(10, oSubplot.getCell(63).getX()); assertEquals(3, oSubplot.getCell(63).getY());
      assertEquals(10, oSubplot.getCell(64).getX()); assertEquals(4, oSubplot.getCell(64).getY());
      assertEquals(10, oSubplot.getCell(65).getX()); assertEquals(5, oSubplot.getCell(65).getY());
      assertEquals(11, oSubplot.getCell(66).getX()); assertEquals(0, oSubplot.getCell(66).getY());
      assertEquals(11, oSubplot.getCell(67).getX()); assertEquals(1, oSubplot.getCell(67).getY());
      assertEquals(11, oSubplot.getCell(68).getX()); assertEquals(2, oSubplot.getCell(68).getY());
      assertEquals(11, oSubplot.getCell(69).getX()); assertEquals(3, oSubplot.getCell(69).getY());
      assertEquals(11, oSubplot.getCell(70).getX()); assertEquals(4, oSubplot.getCell(70).getY());
      assertEquals(11, oSubplot.getCell(71).getX()); assertEquals(5, oSubplot.getCell(71).getY());

      //*****************************
      //Change the plot resolution
      //*****************************
      oManager.changeOfPlotResolution(200, 200, 100, 120);

      assertEquals(2, oOutput.mp_oShortOutputSubplots.size());      
      
      //Summary output subplots
      oSubplot = oOutput.mp_oShortOutputSubplots.get(0);
      assertEquals("plot 2", oSubplot.getSubplotName());
      assertEquals(30, oSubplot.getNumberOfCells());
      assertEquals(0, oSubplot.getCell(0).getX()); assertEquals(0, oSubplot.getCell(0).getY());
      assertEquals(0, oSubplot.getCell(1).getX()); assertEquals(1, oSubplot.getCell(1).getY());
      assertEquals(0, oSubplot.getCell(2).getX()); assertEquals(2, oSubplot.getCell(2).getY());
      assertEquals(0, oSubplot.getCell(3).getX()); assertEquals(3, oSubplot.getCell(3).getY());
      assertEquals(0, oSubplot.getCell(4).getX()); assertEquals(4, oSubplot.getCell(4).getY());
      assertEquals(0, oSubplot.getCell(5).getX()); assertEquals(5, oSubplot.getCell(5).getY());
      assertEquals(0, oSubplot.getCell(6).getX()); assertEquals(6, oSubplot.getCell(6).getY());
      assertEquals(0, oSubplot.getCell(7).getX()); assertEquals(7, oSubplot.getCell(7).getY());
      assertEquals(0, oSubplot.getCell(8).getX()); assertEquals(8, oSubplot.getCell(8).getY());
      assertEquals(0, oSubplot.getCell(9).getX()); assertEquals(9, oSubplot.getCell(9).getY());
      assertEquals(0, oSubplot.getCell(10).getX()); assertEquals(10, oSubplot.getCell(10).getY());
      assertEquals(0, oSubplot.getCell(11).getX()); assertEquals(11, oSubplot.getCell(11).getY());
      assertEquals(0, oSubplot.getCell(12).getX()); assertEquals(12, oSubplot.getCell(12).getY());
      assertEquals(0, oSubplot.getCell(13).getX()); assertEquals(13, oSubplot.getCell(13).getY());
      assertEquals(0, oSubplot.getCell(14).getX()); assertEquals(14, oSubplot.getCell(14).getY());
      assertEquals(2, oSubplot.getCell(15).getX()); assertEquals(0, oSubplot.getCell(15).getY());
      assertEquals(2, oSubplot.getCell(16).getX()); assertEquals(1, oSubplot.getCell(16).getY());
      assertEquals(2, oSubplot.getCell(17).getX()); assertEquals(2, oSubplot.getCell(17).getY());
      assertEquals(2, oSubplot.getCell(18).getX()); assertEquals(3, oSubplot.getCell(18).getY());
      assertEquals(2, oSubplot.getCell(19).getX()); assertEquals(4, oSubplot.getCell(19).getY());
      assertEquals(2, oSubplot.getCell(20).getX()); assertEquals(5, oSubplot.getCell(20).getY());
      assertEquals(2, oSubplot.getCell(21).getX()); assertEquals(6, oSubplot.getCell(21).getY());
      assertEquals(2, oSubplot.getCell(22).getX()); assertEquals(7, oSubplot.getCell(22).getY());
      assertEquals(2, oSubplot.getCell(23).getX()); assertEquals(8, oSubplot.getCell(23).getY());
      assertEquals(2, oSubplot.getCell(24).getX()); assertEquals(9, oSubplot.getCell(24).getY());
      assertEquals(2, oSubplot.getCell(25).getX()); assertEquals(10, oSubplot.getCell(25).getY());
      assertEquals(2, oSubplot.getCell(26).getX()); assertEquals(11, oSubplot.getCell(26).getY());
      assertEquals(2, oSubplot.getCell(27).getX()); assertEquals(12, oSubplot.getCell(27).getY());
      assertEquals(2, oSubplot.getCell(28).getX()); assertEquals(13, oSubplot.getCell(28).getY());
      assertEquals(2, oSubplot.getCell(29).getX()); assertEquals(14, oSubplot.getCell(29).getY());

      oSubplot = oOutput.mp_oShortOutputSubplots.get(1);
      assertEquals("plot 3", oSubplot.getSubplotName());
      assertEquals(72, oSubplot.getNumberOfCells());
      assertEquals(0, oSubplot.getCell(0).getX()); assertEquals(0, oSubplot.getCell(0).getY());
      assertEquals(0, oSubplot.getCell(1).getX()); assertEquals(1, oSubplot.getCell(1).getY());
      assertEquals(0, oSubplot.getCell(2).getX()); assertEquals(2, oSubplot.getCell(2).getY());
      assertEquals(0, oSubplot.getCell(3).getX()); assertEquals(3, oSubplot.getCell(3).getY());
      assertEquals(0, oSubplot.getCell(4).getX()); assertEquals(4, oSubplot.getCell(4).getY());
      assertEquals(0, oSubplot.getCell(5).getX()); assertEquals(5, oSubplot.getCell(5).getY());
      assertEquals(1, oSubplot.getCell(6).getX()); assertEquals(0, oSubplot.getCell(6).getY());
      assertEquals(1, oSubplot.getCell(7).getX()); assertEquals(1, oSubplot.getCell(7).getY());
      assertEquals(1, oSubplot.getCell(8).getX()); assertEquals(2, oSubplot.getCell(8).getY());
      assertEquals(1, oSubplot.getCell(9).getX()); assertEquals(3, oSubplot.getCell(9).getY());
      assertEquals(1, oSubplot.getCell(10).getX()); assertEquals(4, oSubplot.getCell(10).getY());
      assertEquals(1, oSubplot.getCell(11).getX()); assertEquals(5, oSubplot.getCell(11).getY());
      assertEquals(2, oSubplot.getCell(12).getX()); assertEquals(0, oSubplot.getCell(12).getY());
      assertEquals(2, oSubplot.getCell(13).getX()); assertEquals(1, oSubplot.getCell(13).getY());
      assertEquals(2, oSubplot.getCell(14).getX()); assertEquals(2, oSubplot.getCell(14).getY());
      assertEquals(2, oSubplot.getCell(15).getX()); assertEquals(3, oSubplot.getCell(15).getY());
      assertEquals(2, oSubplot.getCell(16).getX()); assertEquals(4, oSubplot.getCell(16).getY());
      assertEquals(2, oSubplot.getCell(17).getX()); assertEquals(5, oSubplot.getCell(17).getY());
      assertEquals(3, oSubplot.getCell(18).getX()); assertEquals(0, oSubplot.getCell(18).getY());
      assertEquals(3, oSubplot.getCell(19).getX()); assertEquals(1, oSubplot.getCell(19).getY());
      assertEquals(3, oSubplot.getCell(20).getX()); assertEquals(2, oSubplot.getCell(20).getY());
      assertEquals(3, oSubplot.getCell(21).getX()); assertEquals(3, oSubplot.getCell(21).getY());
      assertEquals(3, oSubplot.getCell(22).getX()); assertEquals(4, oSubplot.getCell(22).getY());
      assertEquals(3, oSubplot.getCell(23).getX()); assertEquals(5, oSubplot.getCell(23).getY());
      assertEquals(4, oSubplot.getCell(24).getX()); assertEquals(0, oSubplot.getCell(24).getY());
      assertEquals(4, oSubplot.getCell(25).getX()); assertEquals(1, oSubplot.getCell(25).getY());
      assertEquals(4, oSubplot.getCell(26).getX()); assertEquals(2, oSubplot.getCell(26).getY());
      assertEquals(4, oSubplot.getCell(27).getX()); assertEquals(3, oSubplot.getCell(27).getY());
      assertEquals(4, oSubplot.getCell(28).getX()); assertEquals(4, oSubplot.getCell(28).getY());
      assertEquals(4, oSubplot.getCell(29).getX()); assertEquals(5, oSubplot.getCell(29).getY());
      assertEquals(5, oSubplot.getCell(30).getX()); assertEquals(0, oSubplot.getCell(30).getY());
      assertEquals(5, oSubplot.getCell(31).getX()); assertEquals(1, oSubplot.getCell(31).getY());
      assertEquals(5, oSubplot.getCell(32).getX()); assertEquals(2, oSubplot.getCell(32).getY());
      assertEquals(5, oSubplot.getCell(33).getX()); assertEquals(3, oSubplot.getCell(33).getY());
      assertEquals(5, oSubplot.getCell(34).getX()); assertEquals(4, oSubplot.getCell(34).getY());
      assertEquals(5, oSubplot.getCell(35).getX()); assertEquals(5, oSubplot.getCell(35).getY());
      assertEquals(6, oSubplot.getCell(36).getX()); assertEquals(0, oSubplot.getCell(36).getY());
      assertEquals(6, oSubplot.getCell(37).getX()); assertEquals(1, oSubplot.getCell(37).getY());
      assertEquals(6, oSubplot.getCell(38).getX()); assertEquals(2, oSubplot.getCell(38).getY());
      assertEquals(6, oSubplot.getCell(39).getX()); assertEquals(3, oSubplot.getCell(39).getY());
      assertEquals(6, oSubplot.getCell(40).getX()); assertEquals(4, oSubplot.getCell(40).getY());
      assertEquals(6, oSubplot.getCell(41).getX()); assertEquals(5, oSubplot.getCell(41).getY());
      assertEquals(7, oSubplot.getCell(42).getX()); assertEquals(0, oSubplot.getCell(42).getY());
      assertEquals(7, oSubplot.getCell(43).getX()); assertEquals(1, oSubplot.getCell(43).getY());
      assertEquals(7, oSubplot.getCell(44).getX()); assertEquals(2, oSubplot.getCell(44).getY());
      assertEquals(7, oSubplot.getCell(45).getX()); assertEquals(3, oSubplot.getCell(45).getY());
      assertEquals(7, oSubplot.getCell(46).getX()); assertEquals(4, oSubplot.getCell(46).getY());
      assertEquals(7, oSubplot.getCell(47).getX()); assertEquals(5, oSubplot.getCell(47).getY());
      assertEquals(8, oSubplot.getCell(48).getX()); assertEquals(0, oSubplot.getCell(48).getY());
      assertEquals(8, oSubplot.getCell(49).getX()); assertEquals(1, oSubplot.getCell(49).getY());
      assertEquals(8, oSubplot.getCell(50).getX()); assertEquals(2, oSubplot.getCell(50).getY());
      assertEquals(8, oSubplot.getCell(51).getX()); assertEquals(3, oSubplot.getCell(51).getY());
      assertEquals(8, oSubplot.getCell(52).getX()); assertEquals(4, oSubplot.getCell(52).getY());
      assertEquals(8, oSubplot.getCell(53).getX()); assertEquals(5, oSubplot.getCell(53).getY());
      assertEquals(9, oSubplot.getCell(54).getX()); assertEquals(0, oSubplot.getCell(54).getY());
      assertEquals(9, oSubplot.getCell(55).getX()); assertEquals(1, oSubplot.getCell(55).getY());
      assertEquals(9, oSubplot.getCell(56).getX()); assertEquals(2, oSubplot.getCell(56).getY());
      assertEquals(9, oSubplot.getCell(57).getX()); assertEquals(3, oSubplot.getCell(57).getY());
      assertEquals(9, oSubplot.getCell(58).getX()); assertEquals(4, oSubplot.getCell(58).getY());
      assertEquals(9, oSubplot.getCell(59).getX()); assertEquals(5, oSubplot.getCell(59).getY());
      assertEquals(10, oSubplot.getCell(60).getX()); assertEquals(0, oSubplot.getCell(60).getY());
      assertEquals(10, oSubplot.getCell(61).getX()); assertEquals(1, oSubplot.getCell(61).getY());
      assertEquals(10, oSubplot.getCell(62).getX()); assertEquals(2, oSubplot.getCell(62).getY());
      assertEquals(10, oSubplot.getCell(63).getX()); assertEquals(3, oSubplot.getCell(63).getY());
      assertEquals(10, oSubplot.getCell(64).getX()); assertEquals(4, oSubplot.getCell(64).getY());
      assertEquals(10, oSubplot.getCell(65).getX()); assertEquals(5, oSubplot.getCell(65).getY());
      assertEquals(11, oSubplot.getCell(66).getX()); assertEquals(0, oSubplot.getCell(66).getY());
      assertEquals(11, oSubplot.getCell(67).getX()); assertEquals(1, oSubplot.getCell(67).getY());
      assertEquals(11, oSubplot.getCell(68).getX()); assertEquals(2, oSubplot.getCell(68).getY());
      assertEquals(11, oSubplot.getCell(69).getX()); assertEquals(3, oSubplot.getCell(69).getY());
      assertEquals(11, oSubplot.getCell(70).getX()); assertEquals(4, oSubplot.getCell(70).getY());
      assertEquals(11, oSubplot.getCell(71).getX()); assertEquals(5, oSubplot.getCell(71).getY());

      System.out.println("ChangeOfPlotResolution test succeeded.");
    }
    catch (ModelException oErr) {
      fail("XML rundata reading test failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Tests reading of parameter file XML settings.
   */
  public void testReadXMLShortSettings1() {
    String sFileName = null;
    try {
      GUIManager oManager = new GUIManager(null);
      sFileName = writeXMLFile2();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oOb = oManager.getOutputBehaviors();
      ShortOutput oOutput = oOb.getShortOutput();

      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.NOTDEAD));
      assertTrue(oOutput.getSaveRelativeDensity(TreePopulation.SEEDLING));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SEEDLING, OutputBehaviors.NOTDEAD));
      assertFalse(oOutput.getSaveRelativeBasalArea(TreePopulation.SEEDLING));

      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.NOTDEAD));
      assertTrue(oOutput.getSaveRelativeDensity(TreePopulation.SAPLING));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.NOTDEAD));
      assertTrue(oOutput.getSaveRelativeBasalArea(TreePopulation.SAPLING));

      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.NOTDEAD));
      assertTrue(oOutput.getSaveRelativeDensity(TreePopulation.ADULT));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.NOTDEAD));
      assertTrue(oOutput.getSaveRelativeBasalArea(TreePopulation.ADULT));

      //Subplots
      assertEquals(2, oOutput.mp_oShortOutputSubplots.size());

      Subplot oSubplot = oOutput.mp_oShortOutputSubplots.get(0);
      assertEquals("test 1", oSubplot.getSubplotName());
      assertEquals(1, oSubplot.getNumberOfCells());
      assertEquals(0, oSubplot.getCell(0).getX());
      assertEquals(12, oSubplot.getCell(0).getY());

      oSubplot = oOutput.mp_oShortOutputSubplots.get(1);
      assertEquals("test 2", oSubplot.getSubplotName());
      assertEquals(5, oSubplot.getNumberOfCells());
      assertEquals(6, oSubplot.getCell(0).getX());
      assertEquals(14, oSubplot.getCell(0).getY());
      assertEquals(7, oSubplot.getCell(1).getX());
      assertEquals(15, oSubplot.getCell(1).getY());
      assertEquals(8, oSubplot.getCell(2).getX());
      assertEquals(16, oSubplot.getCell(2).getY());
      assertEquals(9, oSubplot.getCell(3).getX());
      assertEquals(17, oSubplot.getCell(3).getY());
      assertEquals(10, oSubplot.getCell(4).getX());
      assertEquals(18, oSubplot.getCell(4).getY());

      DetailedOutput oOutput2 = oOb.getDetailedOutput();
      assertEquals(0, oOutput2.mp_oDetailedOutputSubplots.size());
      System.out.println("XML short output reading test succeeded.");
    }
    catch (ModelException oErr) {
      fail("XML short output reading test failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Tests reading of parameter file XML settings.
   */
  public void testReadXMLShortSettings2() {
    String sFileName = null;
    try {
      GUIManager oManager = new GUIManager(null);
      sFileName = writeXMLFile3();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oOb = oManager.getOutputBehaviors();
      ShortOutput oOutput = oOb.getShortOutput();

      assertFalse(oOutput.getSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.NOTDEAD));
      assertFalse(oOutput.getSaveRelativeDensity(TreePopulation.SEEDLING));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SEEDLING, OutputBehaviors.NOTDEAD));
      assertFalse(oOutput.getSaveRelativeBasalArea(TreePopulation.SEEDLING));

      assertFalse(oOutput.getSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.NOTDEAD));
      assertTrue(oOutput.getSaveRelativeDensity(TreePopulation.SAPLING));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.NOTDEAD));
      assertTrue(oOutput.getSaveRelativeBasalArea(TreePopulation.SAPLING));

      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.NOTDEAD));
      assertFalse(oOutput.getSaveRelativeDensity(TreePopulation.ADULT));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.NOTDEAD));
      assertFalse(oOutput.getSaveRelativeBasalArea(TreePopulation.ADULT));
      System.out.println("XML short output reading test succeeded.");
    }
    catch (ModelException oErr) {
      fail("XML short output reading test failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  public void testUpdateIsEnabled() {
    String sFileName = null;
    try {
      GUIManager oManager = new GUIManager(null);
      sFileName = writeXMLFile2();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oOb = oManager.getOutputBehaviors();
      ShortOutput oOutput = oOb.getShortOutput();
      
      setShortOutputSettingsToFalse(oOutput);      
      assertFalse(oOutput.isActive()); 
      
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.NOTDEAD, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.NOTDEAD, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.NOTDEAD, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      
      oOutput.setSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.NOTDEAD, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.NOTDEAD, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.NOTDEAD, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.NOTDEAD, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      
      oOutput.setSaveRelativeBasalArea(TreePopulation.SAPLING, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveRelativeBasalArea(TreePopulation.ADULT, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveRelativeBasalArea(TreePopulation.SNAG, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      
      oOutput.setSaveRelativeDensity(TreePopulation.SEEDLING, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveRelativeDensity(TreePopulation.SAPLING, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveRelativeDensity(TreePopulation.ADULT, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveRelativeDensity(TreePopulation.SNAG, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.DISEASE, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.DISEASE, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.DISEASE, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      
      oOutput.setSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.DISEASE, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.DISEASE, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.DISEASE, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.DISEASE, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.FIRE, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.FIRE, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.FIRE, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      
      oOutput.setSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.FIRE, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.FIRE, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.FIRE, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.FIRE, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.HARVEST, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.HARVEST, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.HARVEST, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      
      oOutput.setSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.HARVEST, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.HARVEST, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.HARVEST, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.HARVEST, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.INSECTS, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.INSECTS, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.INSECTS, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      
      oOutput.setSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.INSECTS, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.INSECTS, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.INSECTS, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.INSECTS, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.NATURAL, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.NATURAL, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.NATURAL, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      
      oOutput.setSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.NATURAL, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.NATURAL, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.NATURAL, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.NATURAL, true);
      assertTrue(oOutput.isActive()); 
      setShortOutputSettingsToFalse(oOutput);
      
      System.out.println("Short output enabling test succeeded.");
    }
    catch (ModelException oErr) {
      fail("XML short output reading test failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  private void setShortOutputSettingsToFalse(ShortOutput oOutput) {
    TreeOutputSaveInfo oData;
    int i, j; 
    for (i = 0; i < TreePopulation.getNumberOfTypes(); i++) {
      oData = oOutput.mp_oShortTreeSaveSettings.get(i);
      oData.bSaveABA = false;
      oData.bSaveADN = false;
      oData.bSaveRBA = false;
      oData.bSaveRDN = false;
      
      for (j = 0; j > OutputBehaviors.NUMCODES; j++) {
        oOutput.mp_oShortDeadTreeSaveSettings[i][j].bSaveABA = false;
        oOutput.mp_oShortDeadTreeSaveSettings[i][j].bSaveADN = false;
        oOutput.mp_oShortDeadTreeSaveSettings[i][j].bSaveRBA = false;
        oOutput.mp_oShortDeadTreeSaveSettings[i][j].bSaveRDN = false;
      }
    }
    oOutput.updateIsActive();
  }
  
  /**
   * This tests writing and reading short output settings, including dead. 
   */
  public void testWriteXMLShort() {
    String sFileName = null, sFileName2 = "tester.xml"; 
    try {
      GUIManager oManager = new GUIManager(null);
      sFileName = writeXMLFile3();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oOb = oManager.getOutputBehaviors();
      ShortOutput oOutput = oOb.getShortOutput();
      
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.DISEASE, true);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.DISEASE, true);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.DISEASE, true);
      
      oOutput.setSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.DISEASE, true);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.DISEASE, true);
      oOutput.setSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.DISEASE, true);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.DISEASE, true);
      
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.FIRE, true);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.FIRE, true);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.FIRE, true);
      
      oOutput.setSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.FIRE, true);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.FIRE, true);
      oOutput.setSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.FIRE, true);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.FIRE, true);
      
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.HARVEST, true);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.HARVEST, true);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.HARVEST, true);
      
      oOutput.setSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.HARVEST, true);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.HARVEST, true);
      oOutput.setSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.HARVEST, true);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.HARVEST, true);
      
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.INSECTS, true);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.INSECTS, true);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.INSECTS, true);
      
      oOutput.setSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.INSECTS, true);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.INSECTS, true);
      oOutput.setSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.INSECTS, true);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.INSECTS, true);
      
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.NATURAL, true);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.NATURAL, true);
      oOutput.setSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.NATURAL, true);
      
      oOutput.setSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.NATURAL, true);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.NATURAL, true);
      oOutput.setSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.NATURAL, true);
      oOutput.setSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.NATURAL, true);      
      
      oManager.writeParameterFile(sFileName2); 
      oManager.inputXMLParameterFile(sFileName2);
      oOb = oManager.getOutputBehaviors();
      oOutput = oOb.getShortOutput();
      
      assertFalse(oOutput.getSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.NOTDEAD));
      assertFalse(oOutput.getSaveRelativeDensity(TreePopulation.SEEDLING));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SEEDLING, OutputBehaviors.NOTDEAD));
      assertFalse(oOutput.getSaveRelativeBasalArea(TreePopulation.SEEDLING));

      assertFalse(oOutput.getSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.NOTDEAD));
      assertTrue(oOutput.getSaveRelativeDensity(TreePopulation.SAPLING));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.NOTDEAD));
      assertTrue(oOutput.getSaveRelativeBasalArea(TreePopulation.SAPLING));

      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.NOTDEAD));
      assertFalse(oOutput.getSaveRelativeDensity(TreePopulation.ADULT));
      assertFalse(oOutput.getSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.NOTDEAD));
      assertFalse(oOutput.getSaveRelativeBasalArea(TreePopulation.ADULT));
      
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.DISEASE));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.DISEASE));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.DISEASE));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.DISEASE));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.DISEASE));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.DISEASE));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.DISEASE));
      
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.FIRE));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.FIRE));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.FIRE));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.FIRE));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.FIRE));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.FIRE));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.FIRE));
      
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.HARVEST));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.HARVEST));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.HARVEST));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.HARVEST));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.HARVEST));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.HARVEST));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.HARVEST));
      
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.INSECTS));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.INSECTS));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.INSECTS));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.INSECTS));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.INSECTS));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.INSECTS));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.INSECTS));
      
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SAPLING, OutputBehaviors.NATURAL));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.ADULT, OutputBehaviors.NATURAL));
      assertTrue(oOutput.getSaveAbsoluteBasalArea(TreePopulation.SNAG, OutputBehaviors.NATURAL));
      
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SEEDLING, OutputBehaviors.NATURAL));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SAPLING, OutputBehaviors.NATURAL));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.ADULT, OutputBehaviors.NATURAL));
      assertTrue(oOutput.getSaveAbsoluteDensity(TreePopulation.SNAG, OutputBehaviors.NATURAL)); 
      System.out.println("XML short output reading test succeeded.");
    }
    catch (ModelException oErr) {
      fail("XML short output reading test failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
      new File(sFileName2).delete();
    }
  }

  /**
   * This writes an XML file to test value setting.  This file contains short
   * output settings only, for all (normal) types with everything set to true.
   * It also contains subplots.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile2() throws ModelException {
    try {
      String sFileName = "\\loratestxml2.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write(
          "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      oOut.write("<paramFile fileCode=\"07010101\">");

      //Plot - so the subplot cells can be set up
      oOut.write("<plot>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
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
      oOut.write("</trees>");

      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>ShortOutput</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");

      oOut.write("<ShortOutput>");

      //Set all save options to true for all recognized types
      oOut.write("<so_filename>testfile</so_filename>");

      oOut.write("<so_treeTypeInfo type=\"seedling\">");
      oOut.write("<so_saveRBA save=\"true\"/>"); //this shouldn't happen
      oOut.write("<so_saveABA save=\"true\"/>"); //this shouldn't happen
      oOut.write("<so_saveRDN save=\"true\"/>");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("</so_treeTypeInfo>");

      oOut.write("<so_treeTypeInfo type=\"sapling\">");
      oOut.write("<so_saveRBA save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("<so_saveRDN save=\"true\"/>");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("</so_treeTypeInfo>");

      oOut.write("<so_treeTypeInfo type=\"adult\">");
      oOut.write("<so_saveRBA save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("<so_saveRDN save=\"true\"/>");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("</so_treeTypeInfo>");

      //Now subplots
      oOut.write("<so_subplot>");
      oOut.write("<so_subplotName>test 1</so_subplotName>");
      //One point
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"0\" y=\"12\"/>");
      oOut.write("</pointSet>");
      oOut.write("</so_subplot>");

      //5 points
      oOut.write("<so_subplot>");
      oOut.write("<so_subplotName>test 2</so_subplotName>");
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"6\" y=\"14\"/>");
      oOut.write("<po_point x=\"7\" y=\"15\"/>");
      oOut.write("<po_point x=\"8\" y=\"16\"/>");
      oOut.write("<po_point x=\"9\" y=\"17\"/>");
      oOut.write("<po_point x=\"10\" y=\"18\"/>");
      oOut.write("</pointSet>");
      oOut.write("</so_subplot>");

      oOut.write("</ShortOutput>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }

  /**
   * This writes an XML file to test value setting.  This file contains short
   * output settings only, for all (normal) types with a mix of true, false,
   * and absent.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile3() throws ModelException {
    try {
      String sFileName = "\\loratestxml3.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>500</timesteps>");
      oOut.write("<rt_timestep>0</rt_timestep>");
      oOut.write("<randomSeed>0</randomSeed>");
      oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>42.5</plot_latitude>");
      oOut.write("<plot_precip_mm_yr>1000.0</plot_precip_mm_yr>");
      oOut.write("<plot_temp_C>8.0</plot_temp_C>");
      oOut.write("<plot_title>Default Location</plot_title>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"ABBA\" />");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>");
      oOut.write("<tr_minAdultDBH>");
      oOut.write("<tr_madVal species=\"ABBA\">12.7</tr_madVal>");
      oOut.write("</tr_minAdultDBH>");
      oOut.write("<tr_maxSeedlingHeight>");
      oOut.write("<tr_mshVal species=\"ABBA\">1.35</tr_mshVal>");
      oOut.write("</tr_maxSeedlingHeight>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>RemoveDead</behaviorName>");
      oOut.write("<version>2.0</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("<applyTo species=\"ABBA\" type=\"Seedling\" />");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>ShortOutput</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>2</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<allometry>");
      oOut.write("<tr_whatAdultHeightDiam>");
      oOut.write("<tr_wahdVal species=\"ABBA\">0</tr_wahdVal>");
      oOut.write("</tr_whatAdultHeightDiam>");
      oOut.write("<tr_whatSaplingHeightDiam>");
      oOut.write("<tr_wsahdVal species=\"ABBA\">0</tr_wsahdVal>");
      oOut.write("</tr_whatSaplingHeightDiam>");
      oOut.write("<tr_whatSeedlingHeightDiam>");
      oOut.write("<tr_wsehdVal species=\"ABBA\">0</tr_wsehdVal>");
      oOut.write("</tr_whatSeedlingHeightDiam>");
      oOut.write("<tr_whatAdultCrownRadDiam>");
      oOut.write("<tr_wacrdVal species=\"ABBA\">0</tr_wacrdVal>");
      oOut.write("</tr_whatAdultCrownRadDiam>");
      oOut.write("<tr_whatSaplingCrownRadDiam>");
      oOut.write("<tr_wscrdVal species=\"ABBA\">0</tr_wscrdVal>");
      oOut.write("</tr_whatSaplingCrownRadDiam>");
      oOut.write("<tr_whatAdultCrownHeightHeight>");
      oOut.write("<tr_wachhVal species=\"ABBA\">0</tr_wachhVal>");
      oOut.write("</tr_whatAdultCrownHeightHeight>");
      oOut.write("<tr_whatSaplingCrownHeightHeight>");
      oOut.write("<tr_wschhVal species=\"ABBA\">0</tr_wschhVal>");
      oOut.write("</tr_whatSaplingCrownHeightHeight>");
      oOut.write("<tr_canopyHeight>");
      oOut.write("<tr_chVal species=\"ABBA\">30.0</tr_chVal>");
      oOut.write("</tr_canopyHeight>");
      oOut.write("<tr_stdAsympCrownRad>");
      oOut.write("<tr_sacrVal species=\"ABBA\">0.1</tr_sacrVal>");
      oOut.write("</tr_stdAsympCrownRad>");
      oOut.write("<tr_stdCrownRadExp>");
      oOut.write("<tr_screVal species=\"ABBA\">1.0</tr_screVal>");
      oOut.write("</tr_stdCrownRadExp>");
      oOut.write("<tr_stdAsympCrownHt>");
      oOut.write("<tr_sachVal species=\"ABBA\">0.4</tr_sachVal>");
      oOut.write("</tr_stdAsympCrownHt>");
      oOut.write("<tr_stdCrownHtExp>");
      oOut.write("<tr_scheVal species=\"ABBA\">1.0</tr_scheVal>");
      oOut.write("</tr_stdCrownHtExp>");
      oOut.write("<tr_conversionDiam10ToDBH>");
      oOut.write("<tr_cdtdVal species=\"ABBA\">1.0</tr_cdtdVal>");
      oOut.write("</tr_conversionDiam10ToDBH>");
      oOut.write("<tr_interceptDiam10ToDBH>");
      oOut.write("<tr_idtdVal species=\"ABBA\">0.0</tr_idtdVal>");
      oOut.write("</tr_interceptDiam10ToDBH>");
      oOut.write("<tr_slopeOfAsymHeight>");
      oOut.write("<tr_soahVal species=\"ABBA\">0.063</tr_soahVal>");
      oOut.write("</tr_slopeOfAsymHeight>");
      oOut.write("<tr_slopeOfHeight-Diam10>");
      oOut.write("<tr_sohdVal species=\"ABBA\">0.03</tr_sohdVal>");
      oOut.write("</tr_slopeOfHeight-Diam10>");
      oOut.write("</allometry>");

      oOut.write("<ShortOutput>");

      //Set all save options to true for all recognized types
      oOut.write("<so_filename>testfile</so_filename>");

      oOut.write("<so_treeTypeInfo type=\"sapling\">");
      oOut.write("<so_saveRBA save=\"true\"/>");
      oOut.write("<so_saveABA save=\"false\"/>");
      oOut.write("<so_saveRDN save=\"true\"/>");
      oOut.write("</so_treeTypeInfo>");

      oOut.write("<so_treeTypeInfo type=\"adult\">");
      oOut.write("<so_saveRBA save=\"false\"/>");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("</so_treeTypeInfo>");

      oOut.write("</ShortOutput>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }


  /**
   * This writes an XML file to test value setting.  This file contains
   * detailed output only.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile4() throws ModelException {
    try {
      String sFileName = "\\loratestxml1.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write(
          "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>100</timesteps>");
      oOut.write("<randomSeed>0</randomSeed>");
      oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("<plot_title>Default parameter file</plot_title>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"Western_Hemlock\"/>");
      oOut.write("<tr_species speciesName=\"Western_Redcedar\"/>");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
      oOut.write("<tr_minAdultDBH>");
      oOut.write("<tr_madVal species=\"Western_Hemlock\">10.0</tr_madVal>");
      oOut.write("<tr_madVal species=\"Western_Redcedar\">10.0</tr_madVal>");
      oOut.write("</tr_minAdultDBH>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>ConstantGLI</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>ShortOutput</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>2</listPosition>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Output</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>3</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<allometry>");
      oOut.write("<tr_whatAdultHeightDiam>");
      oOut.write("<tr_wahdVal species=\"Western_Hemlock\">0</tr_wahdVal>");
      oOut.write("<tr_wahdVal species=\"Western_Redcedar\">0</tr_wahdVal>");
      oOut.write("</tr_whatAdultHeightDiam>");
      oOut.write("</allometry>");
      oOut.write("<ConstantGLI1>");
      oOut.write("<li_constGLI>100.0</li_constGLI>");
      oOut.write("</ConstantGLI1>");
      oOut.write("<ShortOutput>");
      oOut.write("<so_filename>C:\\lkjh.out</so_filename>");
      oOut.write("<so_treeTypeInfo type=\"Sapling\">");
      oOut.write("<so_saveRBA save=\"true\"/>");
      oOut.write("<so_saveABA save=\"false\"/>");
      oOut.write("<so_saveRDN save=\"false\"/>");
      oOut.write("<so_saveADN save=\"false\"/>");
      oOut.write("</so_treeTypeInfo>");
      oOut.write("<so_subplot>");
      oOut.write("<so_subplotName>plot 1</so_subplotName>");
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"20\" y=\"20\"/>");
      oOut.write("<po_point x=\"20\" y=\"21\"/>");
      oOut.write("<po_point x=\"20\" y=\"22\"/>");
      oOut.write("<po_point x=\"20\" y=\"23\"/>");
      oOut.write("<po_point x=\"20\" y=\"24\"/>");
      oOut.write("<po_point x=\"21\" y=\"20\"/>");
      oOut.write("<po_point x=\"21\" y=\"21\"/>");
      oOut.write("<po_point x=\"21\" y=\"22\"/>");
      oOut.write("<po_point x=\"21\" y=\"23\"/>");
      oOut.write("<po_point x=\"21\" y=\"24\"/>");
      oOut.write("<po_point x=\"22\" y=\"20\"/>");
      oOut.write("<po_point x=\"22\" y=\"21\"/>");
      oOut.write("<po_point x=\"22\" y=\"22\"/>");
      oOut.write("<po_point x=\"22\" y=\"23\"/>");
      oOut.write("<po_point x=\"22\" y=\"24\"/>");
      oOut.write("<po_point x=\"23\" y=\"20\"/>");
      oOut.write("<po_point x=\"23\" y=\"21\"/>");
      oOut.write("<po_point x=\"23\" y=\"22\"/>");
      oOut.write("<po_point x=\"23\" y=\"23\"/>");
      oOut.write("<po_point x=\"23\" y=\"24\"/>");
      oOut.write("<po_point x=\"24\" y=\"20\"/>");
      oOut.write("<po_point x=\"24\" y=\"21\"/>");
      oOut.write("<po_point x=\"24\" y=\"22\"/>");
      oOut.write("<po_point x=\"24\" y=\"23\"/>");
      oOut.write("<po_point x=\"24\" y=\"24\"/>");
      oOut.write("</pointSet>");
      oOut.write("</so_subplot>");
      oOut.write("<so_subplot>");
      oOut.write("<so_subplotName>plot 2</so_subplotName>");
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"0\" y=\"0\"/>");
      oOut.write("<po_point x=\"0\" y=\"1\"/>");
      oOut.write("<po_point x=\"0\" y=\"2\"/>");
      oOut.write("<po_point x=\"0\" y=\"3\"/>");
      oOut.write("<po_point x=\"0\" y=\"4\"/>");
      oOut.write("<po_point x=\"0\" y=\"5\"/>");
      oOut.write("<po_point x=\"0\" y=\"6\"/>");
      oOut.write("<po_point x=\"0\" y=\"7\"/>");
      oOut.write("<po_point x=\"0\" y=\"8\"/>");
      oOut.write("<po_point x=\"0\" y=\"9\"/>");
      oOut.write("<po_point x=\"0\" y=\"10\"/>");
      oOut.write("<po_point x=\"0\" y=\"11\"/>");
      oOut.write("<po_point x=\"0\" y=\"12\"/>");
      oOut.write("<po_point x=\"0\" y=\"13\"/>");
      oOut.write("<po_point x=\"0\" y=\"14\"/>");
      oOut.write("<po_point x=\"0\" y=\"15\"/>");
      oOut.write("<po_point x=\"0\" y=\"16\"/>");
      oOut.write("<po_point x=\"0\" y=\"17\"/>");
      oOut.write("<po_point x=\"0\" y=\"24\"/>");
      oOut.write("<po_point x=\"1\" y=\"24\"/>");
      oOut.write("<po_point x=\"2\" y=\"0\"/>");
      oOut.write("<po_point x=\"2\" y=\"1\"/>");
      oOut.write("<po_point x=\"2\" y=\"2\"/>");
      oOut.write("<po_point x=\"2\" y=\"3\"/>");
      oOut.write("<po_point x=\"2\" y=\"4\"/>");
      oOut.write("<po_point x=\"2\" y=\"5\"/>");
      oOut.write("<po_point x=\"2\" y=\"6\"/>");
      oOut.write("<po_point x=\"2\" y=\"7\"/>");
      oOut.write("<po_point x=\"2\" y=\"8\"/>");
      oOut.write("<po_point x=\"2\" y=\"9\"/>");
      oOut.write("<po_point x=\"2\" y=\"10\"/>");
      oOut.write("<po_point x=\"2\" y=\"11\"/>");
      oOut.write("<po_point x=\"2\" y=\"12\"/>");
      oOut.write("<po_point x=\"2\" y=\"13\"/>");
      oOut.write("<po_point x=\"2\" y=\"14\"/>");
      oOut.write("<po_point x=\"2\" y=\"15\"/>");
      oOut.write("<po_point x=\"2\" y=\"16\"/>");
      oOut.write("<po_point x=\"2\" y=\"17\"/>");
      oOut.write("<po_point x=\"2\" y=\"24\"/>");
      oOut.write("<po_point x=\"3\" y=\"24\"/>");
      oOut.write("<po_point x=\"4\" y=\"24\"/>");
      oOut.write("<po_point x=\"5\" y=\"24\"/>");
      oOut.write("<po_point x=\"24\" y=\"0\"/>");
      oOut.write("<po_point x=\"24\" y=\"1\"/>");
      oOut.write("<po_point x=\"24\" y=\"2\"/>");
      oOut.write("<po_point x=\"24\" y=\"3\"/>");
      oOut.write("<po_point x=\"24\" y=\"4\"/>");
      oOut.write("<po_point x=\"24\" y=\"5\"/>");
      oOut.write("</pointSet>");
      oOut.write("</so_subplot>");
      oOut.write("<so_subplot>");
      oOut.write("<so_subplotName>plot 3</so_subplotName>");
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"0\" y=\"0\"/>");
      oOut.write("<po_point x=\"0\" y=\"1\"/>");
      oOut.write("<po_point x=\"0\" y=\"2\"/>");
      oOut.write("<po_point x=\"0\" y=\"3\"/>");
      oOut.write("<po_point x=\"0\" y=\"4\"/>");
      oOut.write("<po_point x=\"0\" y=\"5\"/>");
      oOut.write("<po_point x=\"1\" y=\"0\"/>");
      oOut.write("<po_point x=\"1\" y=\"1\"/>");
      oOut.write("<po_point x=\"1\" y=\"2\"/>");
      oOut.write("<po_point x=\"1\" y=\"3\"/>");
      oOut.write("<po_point x=\"1\" y=\"4\"/>");
      oOut.write("<po_point x=\"1\" y=\"5\"/>");
      oOut.write("<po_point x=\"2\" y=\"0\"/>");
      oOut.write("<po_point x=\"2\" y=\"1\"/>");
      oOut.write("<po_point x=\"2\" y=\"2\"/>");
      oOut.write("<po_point x=\"2\" y=\"3\"/>");
      oOut.write("<po_point x=\"2\" y=\"4\"/>");
      oOut.write("<po_point x=\"2\" y=\"5\"/>");
      oOut.write("<po_point x=\"3\" y=\"0\"/>");
      oOut.write("<po_point x=\"3\" y=\"1\"/>");
      oOut.write("<po_point x=\"3\" y=\"2\"/>");
      oOut.write("<po_point x=\"3\" y=\"3\"/>");
      oOut.write("<po_point x=\"3\" y=\"4\"/>");
      oOut.write("<po_point x=\"3\" y=\"5\"/>");
      oOut.write("<po_point x=\"4\" y=\"0\"/>");
      oOut.write("<po_point x=\"4\" y=\"1\"/>");
      oOut.write("<po_point x=\"4\" y=\"2\"/>");
      oOut.write("<po_point x=\"4\" y=\"3\"/>");
      oOut.write("<po_point x=\"4\" y=\"4\"/>");
      oOut.write("<po_point x=\"4\" y=\"5\"/>");
      oOut.write("<po_point x=\"5\" y=\"0\"/>");
      oOut.write("<po_point x=\"5\" y=\"1\"/>");
      oOut.write("<po_point x=\"5\" y=\"2\"/>");
      oOut.write("<po_point x=\"5\" y=\"3\"/>");
      oOut.write("<po_point x=\"5\" y=\"4\"/>");
      oOut.write("<po_point x=\"5\" y=\"5\"/>");
      oOut.write("<po_point x=\"6\" y=\"0\"/>");
      oOut.write("<po_point x=\"6\" y=\"1\"/>");
      oOut.write("<po_point x=\"6\" y=\"2\"/>");
      oOut.write("<po_point x=\"6\" y=\"3\"/>");
      oOut.write("<po_point x=\"6\" y=\"4\"/>");
      oOut.write("<po_point x=\"6\" y=\"5\"/>");
      oOut.write("<po_point x=\"7\" y=\"0\"/>");
      oOut.write("<po_point x=\"7\" y=\"1\"/>");
      oOut.write("<po_point x=\"7\" y=\"2\"/>");
      oOut.write("<po_point x=\"7\" y=\"3\"/>");
      oOut.write("<po_point x=\"7\" y=\"4\"/>");
      oOut.write("<po_point x=\"7\" y=\"5\"/>");
      oOut.write("<po_point x=\"8\" y=\"0\"/>");
      oOut.write("<po_point x=\"8\" y=\"1\"/>");
      oOut.write("<po_point x=\"8\" y=\"2\"/>");
      oOut.write("<po_point x=\"8\" y=\"3\"/>");
      oOut.write("<po_point x=\"8\" y=\"4\"/>");
      oOut.write("<po_point x=\"8\" y=\"5\"/>");
      oOut.write("<po_point x=\"9\" y=\"0\"/>");
      oOut.write("<po_point x=\"9\" y=\"1\"/>");
      oOut.write("<po_point x=\"9\" y=\"2\"/>");
      oOut.write("<po_point x=\"9\" y=\"3\"/>");
      oOut.write("<po_point x=\"9\" y=\"4\"/>");
      oOut.write("<po_point x=\"9\" y=\"5\"/>");
      oOut.write("<po_point x=\"10\" y=\"0\"/>");
      oOut.write("<po_point x=\"10\" y=\"1\"/>");
      oOut.write("<po_point x=\"10\" y=\"2\"/>");
      oOut.write("<po_point x=\"10\" y=\"3\"/>");
      oOut.write("<po_point x=\"10\" y=\"4\"/>");
      oOut.write("<po_point x=\"10\" y=\"5\"/>");
      oOut.write("<po_point x=\"11\" y=\"0\"/>");
      oOut.write("<po_point x=\"11\" y=\"1\"/>");
      oOut.write("<po_point x=\"11\" y=\"2\"/>");
      oOut.write("<po_point x=\"11\" y=\"3\"/>");
      oOut.write("<po_point x=\"11\" y=\"4\"/>");
      oOut.write("<po_point x=\"11\" y=\"5\"/>");
      oOut.write("</pointSet>");
      oOut.write("</so_subplot>");
      oOut.write("</ShortOutput>");
      oOut.write("<Output>");
      oOut.write("<ou_filename>C:\\lij.gz.tar</ou_filename>");
      oOut.write("<ou_treeInfo species=\"Western_Hemlock\" type=\"Adult\">");
      oOut.write("<ou_saveFreq>1</ou_saveFreq>");
      oOut.write("<ou_float>X</ou_float>");
      oOut.write("</ou_treeInfo>");
      oOut.write("<ou_subplot>");
      oOut.write("<ou_subplotName>plot 1</ou_subplotName>");
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"20\" y=\"20\"/>");
      oOut.write("<po_point x=\"20\" y=\"21\"/>");
      oOut.write("<po_point x=\"20\" y=\"22\"/>");
      oOut.write("<po_point x=\"20\" y=\"23\"/>");
      oOut.write("<po_point x=\"20\" y=\"24\"/>");
      oOut.write("<po_point x=\"21\" y=\"20\"/>");
      oOut.write("<po_point x=\"21\" y=\"21\"/>");
      oOut.write("<po_point x=\"21\" y=\"22\"/>");
      oOut.write("<po_point x=\"21\" y=\"23\"/>");
      oOut.write("<po_point x=\"21\" y=\"24\"/>");
      oOut.write("<po_point x=\"22\" y=\"20\"/>");
      oOut.write("<po_point x=\"22\" y=\"21\"/>");
      oOut.write("<po_point x=\"22\" y=\"22\"/>");
      oOut.write("<po_point x=\"22\" y=\"23\"/>");
      oOut.write("<po_point x=\"22\" y=\"24\"/>");
      oOut.write("<po_point x=\"23\" y=\"20\"/>");
      oOut.write("<po_point x=\"23\" y=\"21\"/>");
      oOut.write("<po_point x=\"23\" y=\"22\"/>");
      oOut.write("<po_point x=\"23\" y=\"23\"/>");
      oOut.write("<po_point x=\"23\" y=\"24\"/>");
      oOut.write("<po_point x=\"24\" y=\"20\"/>");
      oOut.write("<po_point x=\"24\" y=\"21\"/>");
      oOut.write("<po_point x=\"24\" y=\"22\"/>");
      oOut.write("<po_point x=\"24\" y=\"23\"/>");
      oOut.write("<po_point x=\"24\" y=\"24\"/>");
      oOut.write("</pointSet>");
      oOut.write("</ou_subplot>");
      oOut.write("<ou_subplot>");
      oOut.write("<ou_subplotName>plot 2</ou_subplotName>");
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"0\" y=\"0\"/>");
      oOut.write("<po_point x=\"0\" y=\"1\"/>");
      oOut.write("<po_point x=\"0\" y=\"2\"/>");
      oOut.write("<po_point x=\"0\" y=\"3\"/>");
      oOut.write("<po_point x=\"0\" y=\"4\"/>");
      oOut.write("<po_point x=\"0\" y=\"5\"/>");
      oOut.write("<po_point x=\"0\" y=\"6\"/>");
      oOut.write("<po_point x=\"0\" y=\"7\"/>");
      oOut.write("<po_point x=\"0\" y=\"8\"/>");
      oOut.write("<po_point x=\"0\" y=\"9\"/>");
      oOut.write("<po_point x=\"0\" y=\"10\"/>");
      oOut.write("<po_point x=\"0\" y=\"11\"/>");
      oOut.write("<po_point x=\"0\" y=\"12\"/>");
      oOut.write("<po_point x=\"0\" y=\"13\"/>");
      oOut.write("<po_point x=\"0\" y=\"14\"/>");
      oOut.write("<po_point x=\"0\" y=\"15\"/>");
      oOut.write("<po_point x=\"0\" y=\"16\"/>");
      oOut.write("<po_point x=\"0\" y=\"17\"/>");
      oOut.write("<po_point x=\"0\" y=\"24\"/>");
      oOut.write("<po_point x=\"1\" y=\"24\"/>");
      oOut.write("<po_point x=\"2\" y=\"0\"/>");
      oOut.write("<po_point x=\"2\" y=\"1\"/>");
      oOut.write("<po_point x=\"2\" y=\"2\"/>");
      oOut.write("<po_point x=\"2\" y=\"3\"/>");
      oOut.write("<po_point x=\"2\" y=\"4\"/>");
      oOut.write("<po_point x=\"2\" y=\"5\"/>");
      oOut.write("<po_point x=\"2\" y=\"6\"/>");
      oOut.write("<po_point x=\"2\" y=\"7\"/>");
      oOut.write("<po_point x=\"2\" y=\"8\"/>");
      oOut.write("<po_point x=\"2\" y=\"9\"/>");
      oOut.write("<po_point x=\"2\" y=\"10\"/>");
      oOut.write("<po_point x=\"2\" y=\"11\"/>");
      oOut.write("<po_point x=\"2\" y=\"12\"/>");
      oOut.write("<po_point x=\"2\" y=\"13\"/>");
      oOut.write("<po_point x=\"2\" y=\"14\"/>");
      oOut.write("<po_point x=\"2\" y=\"15\"/>");
      oOut.write("<po_point x=\"2\" y=\"16\"/>");
      oOut.write("<po_point x=\"2\" y=\"17\"/>");
      oOut.write("<po_point x=\"2\" y=\"24\"/>");
      oOut.write("<po_point x=\"3\" y=\"24\"/>");
      oOut.write("<po_point x=\"4\" y=\"24\"/>");
      oOut.write("<po_point x=\"5\" y=\"24\"/>");
      oOut.write("<po_point x=\"24\" y=\"0\"/>");
      oOut.write("<po_point x=\"24\" y=\"1\"/>");
      oOut.write("<po_point x=\"24\" y=\"2\"/>");
      oOut.write("<po_point x=\"24\" y=\"3\"/>");
      oOut.write("<po_point x=\"24\" y=\"4\"/>");
      oOut.write("<po_point x=\"24\" y=\"5\"/>");
      oOut.write("</pointSet>");
      oOut.write("</ou_subplot>");
      oOut.write("<ou_subplot>");
      oOut.write("<ou_subplotName>plot 3</ou_subplotName>");
      oOut.write("<pointSet>");
      oOut.write("<po_point x=\"0\" y=\"0\"/>");
      oOut.write("<po_point x=\"0\" y=\"1\"/>");
      oOut.write("<po_point x=\"0\" y=\"2\"/>");
      oOut.write("<po_point x=\"0\" y=\"3\"/>");
      oOut.write("<po_point x=\"0\" y=\"4\"/>");
      oOut.write("<po_point x=\"0\" y=\"5\"/>");
      oOut.write("<po_point x=\"1\" y=\"0\"/>");
      oOut.write("<po_point x=\"1\" y=\"1\"/>");
      oOut.write("<po_point x=\"1\" y=\"2\"/>");
      oOut.write("<po_point x=\"1\" y=\"3\"/>");
      oOut.write("<po_point x=\"1\" y=\"4\"/>");
      oOut.write("<po_point x=\"1\" y=\"5\"/>");
      oOut.write("<po_point x=\"2\" y=\"0\"/>");
      oOut.write("<po_point x=\"2\" y=\"1\"/>");
      oOut.write("<po_point x=\"2\" y=\"2\"/>");
      oOut.write("<po_point x=\"2\" y=\"3\"/>");
      oOut.write("<po_point x=\"2\" y=\"4\"/>");
      oOut.write("<po_point x=\"2\" y=\"5\"/>");
      oOut.write("<po_point x=\"3\" y=\"0\"/>");
      oOut.write("<po_point x=\"3\" y=\"1\"/>");
      oOut.write("<po_point x=\"3\" y=\"2\"/>");
      oOut.write("<po_point x=\"3\" y=\"3\"/>");
      oOut.write("<po_point x=\"3\" y=\"4\"/>");
      oOut.write("<po_point x=\"3\" y=\"5\"/>");
      oOut.write("<po_point x=\"4\" y=\"0\"/>");
      oOut.write("<po_point x=\"4\" y=\"1\"/>");
      oOut.write("<po_point x=\"4\" y=\"2\"/>");
      oOut.write("<po_point x=\"4\" y=\"3\"/>");
      oOut.write("<po_point x=\"4\" y=\"4\"/>");
      oOut.write("<po_point x=\"4\" y=\"5\"/>");
      oOut.write("<po_point x=\"5\" y=\"0\"/>");
      oOut.write("<po_point x=\"5\" y=\"1\"/>");
      oOut.write("<po_point x=\"5\" y=\"2\"/>");
      oOut.write("<po_point x=\"5\" y=\"3\"/>");
      oOut.write("<po_point x=\"5\" y=\"4\"/>");
      oOut.write("<po_point x=\"5\" y=\"5\"/>");
      oOut.write("<po_point x=\"6\" y=\"0\"/>");
      oOut.write("<po_point x=\"6\" y=\"1\"/>");
      oOut.write("<po_point x=\"6\" y=\"2\"/>");
      oOut.write("<po_point x=\"6\" y=\"3\"/>");
      oOut.write("<po_point x=\"6\" y=\"4\"/>");
      oOut.write("<po_point x=\"6\" y=\"5\"/>");
      oOut.write("<po_point x=\"7\" y=\"0\"/>");
      oOut.write("<po_point x=\"7\" y=\"1\"/>");
      oOut.write("<po_point x=\"7\" y=\"2\"/>");
      oOut.write("<po_point x=\"7\" y=\"3\"/>");
      oOut.write("<po_point x=\"7\" y=\"4\"/>");
      oOut.write("<po_point x=\"7\" y=\"5\"/>");
      oOut.write("<po_point x=\"8\" y=\"0\"/>");
      oOut.write("<po_point x=\"8\" y=\"1\"/>");
      oOut.write("<po_point x=\"8\" y=\"2\"/>");
      oOut.write("<po_point x=\"8\" y=\"3\"/>");
      oOut.write("<po_point x=\"8\" y=\"4\"/>");
      oOut.write("<po_point x=\"8\" y=\"5\"/>");
      oOut.write("<po_point x=\"9\" y=\"0\"/>");
      oOut.write("<po_point x=\"9\" y=\"1\"/>");
      oOut.write("<po_point x=\"9\" y=\"2\"/>");
      oOut.write("<po_point x=\"9\" y=\"3\"/>");
      oOut.write("<po_point x=\"9\" y=\"4\"/>");
      oOut.write("<po_point x=\"9\" y=\"5\"/>");
      oOut.write("<po_point x=\"10\" y=\"0\"/>");
      oOut.write("<po_point x=\"10\" y=\"1\"/>");
      oOut.write("<po_point x=\"10\" y=\"2\"/>");
      oOut.write("<po_point x=\"10\" y=\"3\"/>");
      oOut.write("<po_point x=\"10\" y=\"4\"/>");
      oOut.write("<po_point x=\"10\" y=\"5\"/>");
      oOut.write("<po_point x=\"11\" y=\"0\"/>");
      oOut.write("<po_point x=\"11\" y=\"1\"/>");
      oOut.write("<po_point x=\"11\" y=\"2\"/>");
      oOut.write("<po_point x=\"11\" y=\"3\"/>");
      oOut.write("<po_point x=\"11\" y=\"4\"/>");
      oOut.write("<po_point x=\"11\" y=\"5\"/>");
      oOut.write("</pointSet>");
      oOut.write("</ou_subplot>");
      oOut.write("</Output>");
      oOut.write("</paramFile>");


      oOut.close();
      return sFileName;
    }
    catch (IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
  
  /**
   * Writes a valid version 6 file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String write6XMLValidFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06080101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>150.0</plot_lenX>");
    oOut.write("<plot_lenY>150.0</plot_lenY>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>short output</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0.0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("</allometry>");
    oOut.write("<shortoutput>");
    oOut.write("<so_filename>Short Run 1.out</so_filename>");
    oOut.write("<so_treeTypeInfo type=\"Seedling\">");
    oOut.write("<so_saveRDN save=\"false\"/>");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_treeTypeInfo>");
    oOut.write("<so_treeTypeInfo type=\"Sapling\">");
    oOut.write("<so_saveRBA save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("<so_saveRDN save=\"true\"/>");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_treeTypeInfo>");
    oOut.write("<so_treeTypeInfo type=\"Adult\">");
    oOut.write("<so_saveRBA save=\"false\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("<so_saveRDN save=\"true\"/>");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_treeTypeInfo>");
    oOut.write("<so_treeTypeInfo type=\"Snag\">");
    oOut.write("<so_saveRBA save=\"true\"/>");
    oOut.write("<so_saveABA save=\"false\"/>");
    oOut.write("<so_saveRDN save=\"true\"/>");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_treeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Seedling\" reason=\"harvest\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Seedling\" reason=\"natural\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Seedling\" reason=\"disease\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Seedling\" reason=\"fire\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Seedling\" reason=\"insects\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Seedling\" reason=\"storm\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Sapling\" reason=\"harvest\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Sapling\" reason=\"natural\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Sapling\" reason=\"disease\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Sapling\" reason=\"fire\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Sapling\" reason=\"insects\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Sapling\" reason=\"storm\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Adult\" reason=\"harvest\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Adult\" reason=\"natural\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Adult\" reason=\"disease\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Adult\" reason=\"fire\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Adult\" reason=\"insects\">");
    oOut.write("<so_saveADN save=\"false\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Adult\" reason=\"storm\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Snag\" reason=\"harvest\">");
    oOut.write("<so_saveADN save=\"false\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Snag\" reason=\"natural\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"false\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Snag\" reason=\"disease\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Snag\" reason=\"fire\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"false\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Snag\" reason=\"insects\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_deadTreeTypeInfo type=\"Snag\" reason=\"storm\">");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("<so_saveABA save=\"false\"/>");
    oOut.write("</so_deadTreeTypeInfo>");
    oOut.write("<so_subplot>");
    oOut.write("<so_subplotName>Subplot 1</so_subplotName>");
    oOut.write("<pointSet>");
    oOut.write("<po_point x=\"0\" y=\"0\"/>");
    oOut.write("<po_point x=\"0\" y=\"1\"/>");
    oOut.write("<po_point x=\"0\" y=\"2\"/>");
    oOut.write("<po_point x=\"0\" y=\"3\"/>");
    oOut.write("<po_point x=\"0\" y=\"4\"/>");
    oOut.write("<po_point x=\"0\" y=\"5\"/>");
    oOut.write("<po_point x=\"0\" y=\"6\"/>");
    oOut.write("<po_point x=\"0\" y=\"7\"/>");
    oOut.write("<po_point x=\"0\" y=\"8\"/>");
    oOut.write("<po_point x=\"0\" y=\"9\"/>");
    oOut.write("<po_point x=\"0\" y=\"10\"/>");
    oOut.write("<po_point x=\"0\" y=\"11\"/>");
    oOut.write("<po_point x=\"0\" y=\"12\"/>");
    oOut.write("<po_point x=\"1\" y=\"0\"/>");
    oOut.write("<po_point x=\"1\" y=\"1\"/>");
    oOut.write("<po_point x=\"1\" y=\"2\"/>");
    oOut.write("<po_point x=\"1\" y=\"3\"/>");
    oOut.write("<po_point x=\"1\" y=\"4\"/>");
    oOut.write("<po_point x=\"1\" y=\"5\"/>");
    oOut.write("<po_point x=\"1\" y=\"6\"/>");
    oOut.write("<po_point x=\"1\" y=\"7\"/>");
    oOut.write("<po_point x=\"1\" y=\"8\"/>");
    oOut.write("<po_point x=\"1\" y=\"9\"/>");
    oOut.write("<po_point x=\"1\" y=\"10\"/>");
    oOut.write("<po_point x=\"1\" y=\"11\"/>");
    oOut.write("<po_point x=\"1\" y=\"12\"/>");
    oOut.write("<po_point x=\"2\" y=\"0\"/>");
    oOut.write("<po_point x=\"2\" y=\"1\"/>");
    oOut.write("<po_point x=\"2\" y=\"2\"/>");
    oOut.write("<po_point x=\"2\" y=\"3\"/>");
    oOut.write("<po_point x=\"2\" y=\"4\"/>");
    oOut.write("<po_point x=\"2\" y=\"5\"/>");
    oOut.write("<po_point x=\"2\" y=\"6\"/>");
    oOut.write("<po_point x=\"2\" y=\"7\"/>");
    oOut.write("<po_point x=\"2\" y=\"8\"/>");
    oOut.write("<po_point x=\"2\" y=\"9\"/>");
    oOut.write("<po_point x=\"2\" y=\"10\"/>");
    oOut.write("<po_point x=\"2\" y=\"11\"/>");
    oOut.write("<po_point x=\"2\" y=\"12\"/>");
    oOut.write("<po_point x=\"3\" y=\"0\"/>");
    oOut.write("<po_point x=\"3\" y=\"1\"/>");
    oOut.write("<po_point x=\"3\" y=\"2\"/>");
    oOut.write("<po_point x=\"3\" y=\"3\"/>");
    oOut.write("<po_point x=\"3\" y=\"4\"/>");
    oOut.write("<po_point x=\"3\" y=\"5\"/>");
    oOut.write("<po_point x=\"3\" y=\"6\"/>");
    oOut.write("<po_point x=\"3\" y=\"7\"/>");
    oOut.write("<po_point x=\"3\" y=\"8\"/>");
    oOut.write("<po_point x=\"3\" y=\"9\"/>");
    oOut.write("<po_point x=\"3\" y=\"10\"/>");
    oOut.write("<po_point x=\"3\" y=\"11\"/>");
    oOut.write("<po_point x=\"3\" y=\"12\"/>");
    oOut.write("<po_point x=\"4\" y=\"0\"/>");
    oOut.write("<po_point x=\"4\" y=\"1\"/>");
    oOut.write("<po_point x=\"4\" y=\"2\"/>");
    oOut.write("<po_point x=\"4\" y=\"3\"/>");
    oOut.write("<po_point x=\"4\" y=\"4\"/>");
    oOut.write("<po_point x=\"4\" y=\"5\"/>");
    oOut.write("<po_point x=\"4\" y=\"6\"/>");
    oOut.write("<po_point x=\"4\" y=\"7\"/>");
    oOut.write("<po_point x=\"4\" y=\"8\"/>");
    oOut.write("<po_point x=\"4\" y=\"9\"/>");
    oOut.write("<po_point x=\"4\" y=\"10\"/>");
    oOut.write("<po_point x=\"4\" y=\"11\"/>");
    oOut.write("<po_point x=\"4\" y=\"12\"/>");
    oOut.write("<po_point x=\"5\" y=\"0\"/>");
    oOut.write("<po_point x=\"5\" y=\"1\"/>");
    oOut.write("<po_point x=\"5\" y=\"2\"/>");
    oOut.write("<po_point x=\"5\" y=\"3\"/>");
    oOut.write("<po_point x=\"5\" y=\"4\"/>");
    oOut.write("<po_point x=\"5\" y=\"5\"/>");
    oOut.write("<po_point x=\"6\" y=\"3\"/>");
    oOut.write("<po_point x=\"6\" y=\"4\"/>");
    oOut.write("<po_point x=\"6\" y=\"5\"/>");
    oOut.write("<po_point x=\"6\" y=\"6\"/>");
    oOut.write("<po_point x=\"6\" y=\"7\"/>");
    oOut.write("<po_point x=\"6\" y=\"8\"/>");
    oOut.write("<po_point x=\"6\" y=\"9\"/>");
    oOut.write("<po_point x=\"6\" y=\"10\"/>");
    oOut.write("<po_point x=\"6\" y=\"11\"/>");
    oOut.write("<po_point x=\"6\" y=\"12\"/>");
    oOut.write("<po_point x=\"7\" y=\"0\"/>");
    oOut.write("<po_point x=\"7\" y=\"1\"/>");
    oOut.write("<po_point x=\"7\" y=\"2\"/>");
    oOut.write("<po_point x=\"7\" y=\"3\"/>");
    oOut.write("<po_point x=\"7\" y=\"4\"/>");
    oOut.write("<po_point x=\"7\" y=\"5\"/>");
    oOut.write("<po_point x=\"8\" y=\"6\"/>");
    oOut.write("<po_point x=\"8\" y=\"7\"/>");
    oOut.write("<po_point x=\"8\" y=\"8\"/>");
    oOut.write("<po_point x=\"8\" y=\"9\"/>");
    oOut.write("<po_point x=\"8\" y=\"10\"/>");
    oOut.write("<po_point x=\"8\" y=\"11\"/>");
    oOut.write("<po_point x=\"10\" y=\"0\"/>");
    oOut.write("<po_point x=\"10\" y=\"1\"/>");
    oOut.write("<po_point x=\"10\" y=\"2\"/>");
    oOut.write("<po_point x=\"10\" y=\"3\"/>");
    oOut.write("<po_point x=\"10\" y=\"4\"/>");
    oOut.write("<po_point x=\"10\" y=\"5\"/>");
    oOut.write("<po_point x=\"10\" y=\"6\"/>");
    oOut.write("<po_point x=\"10\" y=\"7\"/>");
    oOut.write("<po_point x=\"11\" y=\"7\"/>");
    oOut.write("<po_point x=\"11\" y=\"8\"/>");
    oOut.write("<po_point x=\"12\" y=\"9\"/>");
    oOut.write("<po_point x=\"12\" y=\"10\"/>");
    oOut.write("<po_point x=\"12\" y=\"11\"/>");
    oOut.write("<po_point x=\"12\" y=\"12\"/>");
    oOut.write("</pointSet>");
    oOut.write("</so_subplot>");
    oOut.write("<so_subplot>");
    oOut.write("<so_subplotName>Subplot 2</so_subplotName>");
    oOut.write("<pointSet>");
    oOut.write("<po_point x=\"0\" y=\"6\"/>");
    oOut.write("<po_point x=\"0\" y=\"7\"/>");
    oOut.write("<po_point x=\"0\" y=\"8\"/>");
    oOut.write("<po_point x=\"2\" y=\"14\"/>");
    oOut.write("<po_point x=\"2\" y=\"15\"/>");
    oOut.write("<po_point x=\"2\" y=\"16\"/>");
    oOut.write("<po_point x=\"2\" y=\"17\"/>");
    oOut.write("<po_point x=\"2\" y=\"18\"/>");
    oOut.write("<po_point x=\"3\" y=\"6\"/>");
    oOut.write("<po_point x=\"3\" y=\"7\"/>");
    oOut.write("<po_point x=\"3\" y=\"8\"/>");
    oOut.write("<po_point x=\"3\" y=\"9\"/>");
    oOut.write("<po_point x=\"3\" y=\"12\"/>");
    oOut.write("<po_point x=\"3\" y=\"13\"/>");
    oOut.write("<po_point x=\"3\" y=\"14\"/>");
    oOut.write("<po_point x=\"6\" y=\"6\"/>");
    oOut.write("<po_point x=\"6\" y=\"7\"/>");
    oOut.write("<po_point x=\"6\" y=\"8\"/>");
    oOut.write("<po_point x=\"6\" y=\"9\"/>");
    oOut.write("<po_point x=\"6\" y=\"12\"/>");
    oOut.write("<po_point x=\"6\" y=\"13\"/>");
    oOut.write("<po_point x=\"6\" y=\"14\"/>");
    oOut.write("<po_point x=\"6\" y=\"15\"/>");
    oOut.write("<po_point x=\"6\" y=\"16\"/>");
    oOut.write("<po_point x=\"9\" y=\"8\"/>");
    oOut.write("<po_point x=\"9\" y=\"9\"/>");
    oOut.write("<po_point x=\"9\" y=\"12\"/>");
    oOut.write("<po_point x=\"11\" y=\"8\"/>");
    oOut.write("<po_point x=\"11\" y=\"9\"/>");
    oOut.write("<po_point x=\"13\" y=\"8\"/>");
    oOut.write("<po_point x=\"13\" y=\"9\"/>");
    oOut.write("<po_point x=\"13\" y=\"12\"/>");
    oOut.write("<po_point x=\"15\" y=\"13\"/>");
    oOut.write("<po_point x=\"15\" y=\"14\"/>");
    oOut.write("<po_point x=\"15\" y=\"15\"/>");
    oOut.write("<po_point x=\"15\" y=\"16\"/>");
    oOut.write("<po_point x=\"15\" y=\"17\"/>");
    oOut.write("<po_point x=\"15\" y=\"18\"/>");
    oOut.write("<po_point x=\"17\" y=\"15\"/>");
    oOut.write("<po_point x=\"17\" y=\"16\"/>");
    oOut.write("<po_point x=\"17\" y=\"17\"/>");
    oOut.write("<po_point x=\"18\" y=\"16\"/>");
    oOut.write("<po_point x=\"18\" y=\"17\"/>");
    oOut.write("<po_point x=\"18\" y=\"18\"/>");
    oOut.write("</pointSet>");
    oOut.write("</so_subplot>");
    oOut.write("</shortoutput>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
