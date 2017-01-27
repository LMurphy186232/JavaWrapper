package sortie.data.funcgroups.statechange;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.StateChangeBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

import junit.framework.TestCase;

public class ClimateImporterTest extends TestCase {
  
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      StateChangeBehaviors oStateBeh = oManager.getStateChangeBehaviors();
      ArrayList<Behavior> p_oBehs = oStateBeh.getBehaviorByParameterFileTag("ClimateImporter");
      assertEquals(1, p_oBehs.size());
      ClimateImporter oState = (ClimateImporter) p_oBehs.get(0);

      assertEquals(-2.57, oState.mp_fTemp[0][0], 0.001);
      assertEquals(-3.46, oState.mp_fTemp[0][1], 0.001);
      assertEquals(-6.93, oState.mp_fTemp[0][2], 0.001);
      assertEquals(-7.76, oState.mp_fTemp[0][3], 0.001);
      assertEquals(-0.72, oState.mp_fTemp[0][4], 0.001);
      assertEquals(1.3, oState.mp_fTemp[0][5], 0.001);
      
      assertEquals(2.75, oState.mp_fTemp[1][0], 0.001);
      assertEquals(-8.57, oState.mp_fTemp[1][1], 0.001);
      assertEquals(1.98, oState.mp_fTemp[1][2], 0.001);
      assertEquals(-7.5, oState.mp_fTemp[1][3], 0.001);
      assertEquals(-2.44, oState.mp_fTemp[1][4], 0.001);
      assertEquals(-9.36, oState.mp_fTemp[1][5], 0.001);
      
      assertEquals(2.94, oState.mp_fTemp[2][0], 0.001);
      assertEquals(0.28, oState.mp_fTemp[2][1], 0.001);
      assertEquals(0.3, oState.mp_fTemp[2][2], 0.001);
      assertEquals(-0.78, oState.mp_fTemp[2][3], 0.001);
      assertEquals(4.85, oState.mp_fTemp[2][4], 0.001);
      assertEquals(3.78, oState.mp_fTemp[2][5], 0.001);
      
      assertEquals(2.17, oState.mp_fTemp[3][0], 0.001);
      assertEquals(5.9, oState.mp_fTemp[3][1], 0.001);
      assertEquals(7.17, oState.mp_fTemp[3][2], 0.001);
      assertEquals(8.67, oState.mp_fTemp[3][3], 0.001);
      assertEquals(5.61, oState.mp_fTemp[3][4], 0.001);
      assertEquals(4.56, oState.mp_fTemp[3][5], 0.001);
      
      assertEquals(7.25, oState.mp_fTemp[4][0], 0.001);
      assertEquals(7.6, oState.mp_fTemp[4][1], 0.001);
      assertEquals(9.46, oState.mp_fTemp[4][2], 0.001);
      assertEquals(11.26, oState.mp_fTemp[4][3], 0.001);
      assertEquals(7.6, oState.mp_fTemp[4][4], 0.001);
      assertEquals(6.9, oState.mp_fTemp[4][5], 0.001);
      
      assertEquals(11.05, oState.mp_fTemp[5][0], 0.001);
      assertEquals(17.16, oState.mp_fTemp[5][1], 0.001);
      assertEquals(11.06, oState.mp_fTemp[5][2], 0.001);
      assertEquals(17.72, oState.mp_fTemp[5][3], 0.001);
      assertEquals(14.05, oState.mp_fTemp[5][4], 0.001);
      assertEquals(13.52, oState.mp_fTemp[5][5], 0.001);
      
      assertEquals(16.82, oState.mp_fTemp[6][0], 0.001);
      assertEquals(22, oState.mp_fTemp[6][1], 0.001);
      assertEquals(18.34, oState.mp_fTemp[6][2], 0.001);
      assertEquals(20.99, oState.mp_fTemp[6][3], 0.001);
      assertEquals(15.46, oState.mp_fTemp[6][4], 0.001);
      assertEquals(15.74, oState.mp_fTemp[6][5], 0.001);
      
      assertEquals(18.32, oState.mp_fTemp[7][0], 0.001);
      assertEquals(15.97, oState.mp_fTemp[7][1], 0.001);
      assertEquals(17.54, oState.mp_fTemp[7][2], 0.001);
      assertEquals(17.46, oState.mp_fTemp[7][3], 0.001);
      assertEquals(16.69, oState.mp_fTemp[7][4], 0.001);
      assertEquals(17.48, oState.mp_fTemp[7][5], 0.001);
      
      assertEquals(22.73, oState.mp_fTemp[8][0], 0.001);
      assertEquals(20.94, oState.mp_fTemp[8][1], 0.001);
      assertEquals(15.94, oState.mp_fTemp[8][2], 0.001);
      assertEquals(18.87, oState.mp_fTemp[8][3], 0.001);
      assertEquals(22.85, oState.mp_fTemp[8][4], 0.001);
      assertEquals(19.53, oState.mp_fTemp[8][5], 0.001);
      
      assertEquals(7.92, oState.mp_fTemp[9][0], 0.001);
      assertEquals(8.18, oState.mp_fTemp[9][1], 0.001);
      assertEquals(8.43, oState.mp_fTemp[9][2], 0.001);
      assertEquals(8.68, oState.mp_fTemp[9][3], 0.001);
      assertEquals(8.94, oState.mp_fTemp[9][4], 0.001);
      assertEquals(9.19, oState.mp_fTemp[9][5], 0.001);
      
      assertEquals(2.48, oState.mp_fTemp[10][0], 0.001);
      assertEquals(2.56, oState.mp_fTemp[10][1], 0.001);
      assertEquals(2.64, oState.mp_fTemp[10][2], 0.001);
      assertEquals(2.72, oState.mp_fTemp[10][3], 0.001);
      assertEquals(2.8, oState.mp_fTemp[10][4], 0.001);
      assertEquals(2.88, oState.mp_fTemp[10][5], 0.001);
      
      assertEquals(-9.6, oState.mp_fTemp[11][0], 0.001);
      assertEquals(-6.94, oState.mp_fTemp[11][1], 0.001);
      assertEquals(-6.51, oState.mp_fTemp[11][2], 0.001);
      assertEquals(-1.59, oState.mp_fTemp[11][3], 0.001);
      assertEquals(0.19, oState.mp_fTemp[11][4], 0.001);
      assertEquals(2.49, oState.mp_fTemp[11][5], 0.001);
      
      assertEquals(161.48, oState.mp_fPpt[0][0], 0.001);
      assertEquals(152.09, oState.mp_fPpt[0][1], 0.001);
      assertEquals(152.5, oState.mp_fPpt[0][2], 0.001);
      assertEquals(152.71, oState.mp_fPpt[0][3], 0.001);
      assertEquals(78.44, oState.mp_fPpt[0][4], 0.001);
      assertEquals(123.19, oState.mp_fPpt[0][5], 0.001);
      
      assertEquals(199.32, oState.mp_fPpt[1][0], 0.001);
      assertEquals(192.94, oState.mp_fPpt[1][1], 0.001);
      assertEquals(188.1, oState.mp_fPpt[1][2], 0.001);
      assertEquals(153.95, oState.mp_fPpt[1][3], 0.001);
      assertEquals(89.79, oState.mp_fPpt[1][4], 0.001);
      assertEquals(189.67, oState.mp_fPpt[1][5], 0.001);
      
      assertEquals(169.99, oState.mp_fPpt[2][0], 0.001);
      assertEquals(105.49, oState.mp_fPpt[2][1], 0.001);
      assertEquals(141.34, oState.mp_fPpt[2][2], 0.001);
      assertEquals(84.71, oState.mp_fPpt[2][3], 0.001);
      assertEquals(188.92, oState.mp_fPpt[2][4], 0.001);
      assertEquals(93.47, oState.mp_fPpt[2][5], 0.001);
      
      assertEquals(82.02, oState.mp_fPpt[3][0], 0.001);
      assertEquals(77.49, oState.mp_fPpt[3][1], 0.001);
      assertEquals(186.32, oState.mp_fPpt[3][2], 0.001);
      assertEquals(174.4, oState.mp_fPpt[3][3], 0.001);
      assertEquals(161.25, oState.mp_fPpt[3][4], 0.001);
      assertEquals(99.76, oState.mp_fPpt[3][5], 0.001);
      
      assertEquals(132.54, oState.mp_fPpt[4][0], 0.001);
      assertEquals(121.74, oState.mp_fPpt[4][1], 0.001);
      assertEquals(178.18, oState.mp_fPpt[4][2], 0.001);
      assertEquals(102.86, oState.mp_fPpt[4][3], 0.001);
      assertEquals(102.84, oState.mp_fPpt[4][4], 0.001);
      assertEquals(172.41, oState.mp_fPpt[4][5], 0.001);
      
      assertEquals(157.67, oState.mp_fPpt[5][0], 0.001);
      assertEquals(133.89, oState.mp_fPpt[5][1], 0.001);
      assertEquals(147.29, oState.mp_fPpt[5][2], 0.001);
      assertEquals(123.74, oState.mp_fPpt[5][3], 0.001);
      assertEquals(126.66, oState.mp_fPpt[5][4], 0.001);
      assertEquals(134.35, oState.mp_fPpt[5][5], 0.001);
      
      assertEquals(97.63, oState.mp_fPpt[6][0], 0.001);
      assertEquals(141.94, oState.mp_fPpt[6][1], 0.001);
      assertEquals(173.88, oState.mp_fPpt[6][2], 0.001);
      assertEquals(166.53, oState.mp_fPpt[6][3], 0.001);
      assertEquals(140.41, oState.mp_fPpt[6][4], 0.001);
      assertEquals(128.89, oState.mp_fPpt[6][5], 0.001);
      
      assertEquals(145.57, oState.mp_fPpt[7][0], 0.001);
      assertEquals(156.69, oState.mp_fPpt[7][1], 0.001);
      assertEquals(146.55, oState.mp_fPpt[7][2], 0.001);
      assertEquals(167.73, oState.mp_fPpt[7][3], 0.001);
      assertEquals(147.67, oState.mp_fPpt[7][4], 0.001);
      assertEquals(119.52, oState.mp_fPpt[7][5], 0.001);
      
      assertEquals(134.38, oState.mp_fPpt[8][0], 0.001);
      assertEquals(89.78, oState.mp_fPpt[8][1], 0.001);
      assertEquals(173.37, oState.mp_fPpt[8][2], 0.001);
      assertEquals(146.16, oState.mp_fPpt[8][3], 0.001);
      assertEquals(108.71, oState.mp_fPpt[8][4], 0.001);
      assertEquals(90.68, oState.mp_fPpt[8][5], 0.001);
      
      assertEquals(100.17, oState.mp_fPpt[9][0], 0.001);
      assertEquals(178.11, oState.mp_fPpt[9][1], 0.001);
      assertEquals(120.16, oState.mp_fPpt[9][2], 0.001);
      assertEquals(198.43, oState.mp_fPpt[9][3], 0.001);
      assertEquals(75.22, oState.mp_fPpt[9][4], 0.001);
      assertEquals(198.3, oState.mp_fPpt[9][5], 0.001);

      assertEquals(112.34, oState.mp_fPpt[10][0], 0.001);
      assertEquals(191.22, oState.mp_fPpt[10][1], 0.001);
      assertEquals(125.47, oState.mp_fPpt[10][2], 0.001);
      assertEquals(79.8, oState.mp_fPpt[10][3], 0.001);
      assertEquals(116.29, oState.mp_fPpt[10][4], 0.001);
      assertEquals(146.75, oState.mp_fPpt[10][5], 0.001);

      assertEquals(137.22, oState.mp_fPpt[11][0], 0.001);
      assertEquals(101.29, oState.mp_fPpt[11][1], 0.001);
      assertEquals(159.21, oState.mp_fPpt[11][2], 0.001);
      assertEquals(171.75, oState.mp_fPpt[11][3], 0.001);
      assertEquals(191.54, oState.mp_fPpt[11][4], 0.001);
      assertEquals(131.83, oState.mp_fPpt[11][5], 0.001);
      
      assertEquals(7468.475, oState.m_fRadJan.getValue(), 0.001);
      assertEquals(10353.32, oState.m_fRadFeb.getValue(), 0.001);
      assertEquals(17453.07, oState.m_fRadMar.getValue(), 0.001);
      assertEquals(22721.85, oState.m_fRadApr.getValue(), 0.001);
      assertEquals(27901.37, oState.m_fRadMay.getValue(), 0.001);
      assertEquals(28677.54, oState.m_fRadJun.getValue(), 0.001);
      assertEquals(28764.65, oState.m_fRadJul.getValue(), 0.001);
      assertEquals(25075.4, oState.m_fRadAug.getValue(), 0.001);
      assertEquals(19259.27, oState.m_fRadSep.getValue(), 0.001);
      assertEquals(12609.6, oState.m_fRadOct.getValue(), 0.001);
      assertEquals(7988.013, oState.m_fRadNov.getValue(), 0.001);
      assertEquals(6307.151, oState.m_fRadDec.getValue(), 0.001);

      assertEquals(60.9, oState.m_fAWS.getValue(), 0.00001);
      
      //Write it out as a parameter file and read it back in
      assertTrue(oManager.writeParameterFile(sFileName));
      
      oManager.inputXMLParameterFile(sFileName);
      oStateBeh = oManager.getStateChangeBehaviors();
      p_oBehs = oStateBeh.getBehaviorByParameterFileTag("ClimateImporter");
      assertEquals(1, p_oBehs.size());
      oState = (ClimateImporter) p_oBehs.get(0);

      assertEquals(-2.57, oState.mp_fTemp[0][0], 0.001);
      assertEquals(-3.46, oState.mp_fTemp[0][1], 0.001);
      assertEquals(-6.93, oState.mp_fTemp[0][2], 0.001);
      assertEquals(-7.76, oState.mp_fTemp[0][3], 0.001);
      assertEquals(-0.72, oState.mp_fTemp[0][4], 0.001);
      assertEquals(1.3, oState.mp_fTemp[0][5], 0.001);
      
      assertEquals(2.75, oState.mp_fTemp[1][0], 0.001);
      assertEquals(-8.57, oState.mp_fTemp[1][1], 0.001);
      assertEquals(1.98, oState.mp_fTemp[1][2], 0.001);
      assertEquals(-7.5, oState.mp_fTemp[1][3], 0.001);
      assertEquals(-2.44, oState.mp_fTemp[1][4], 0.001);
      assertEquals(-9.36, oState.mp_fTemp[1][5], 0.001);
      
      assertEquals(2.94, oState.mp_fTemp[2][0], 0.001);
      assertEquals(0.28, oState.mp_fTemp[2][1], 0.001);
      assertEquals(0.3, oState.mp_fTemp[2][2], 0.001);
      assertEquals(-0.78, oState.mp_fTemp[2][3], 0.001);
      assertEquals(4.85, oState.mp_fTemp[2][4], 0.001);
      assertEquals(3.78, oState.mp_fTemp[2][5], 0.001);
      
      assertEquals(2.17, oState.mp_fTemp[3][0], 0.001);
      assertEquals(5.9, oState.mp_fTemp[3][1], 0.001);
      assertEquals(7.17, oState.mp_fTemp[3][2], 0.001);
      assertEquals(8.67, oState.mp_fTemp[3][3], 0.001);
      assertEquals(5.61, oState.mp_fTemp[3][4], 0.001);
      assertEquals(4.56, oState.mp_fTemp[3][5], 0.001);
      
      assertEquals(7.25, oState.mp_fTemp[4][0], 0.001);
      assertEquals(7.6, oState.mp_fTemp[4][1], 0.001);
      assertEquals(9.46, oState.mp_fTemp[4][2], 0.001);
      assertEquals(11.26, oState.mp_fTemp[4][3], 0.001);
      assertEquals(7.6, oState.mp_fTemp[4][4], 0.001);
      assertEquals(6.9, oState.mp_fTemp[4][5], 0.001);
      
      assertEquals(11.05, oState.mp_fTemp[5][0], 0.001);
      assertEquals(17.16, oState.mp_fTemp[5][1], 0.001);
      assertEquals(11.06, oState.mp_fTemp[5][2], 0.001);
      assertEquals(17.72, oState.mp_fTemp[5][3], 0.001);
      assertEquals(14.05, oState.mp_fTemp[5][4], 0.001);
      assertEquals(13.52, oState.mp_fTemp[5][5], 0.001);
      
      assertEquals(16.82, oState.mp_fTemp[6][0], 0.001);
      assertEquals(22, oState.mp_fTemp[6][1], 0.001);
      assertEquals(18.34, oState.mp_fTemp[6][2], 0.001);
      assertEquals(20.99, oState.mp_fTemp[6][3], 0.001);
      assertEquals(15.46, oState.mp_fTemp[6][4], 0.001);
      assertEquals(15.74, oState.mp_fTemp[6][5], 0.001);
      
      assertEquals(18.32, oState.mp_fTemp[7][0], 0.001);
      assertEquals(15.97, oState.mp_fTemp[7][1], 0.001);
      assertEquals(17.54, oState.mp_fTemp[7][2], 0.001);
      assertEquals(17.46, oState.mp_fTemp[7][3], 0.001);
      assertEquals(16.69, oState.mp_fTemp[7][4], 0.001);
      assertEquals(17.48, oState.mp_fTemp[7][5], 0.001);
      
      assertEquals(22.73, oState.mp_fTemp[8][0], 0.001);
      assertEquals(20.94, oState.mp_fTemp[8][1], 0.001);
      assertEquals(15.94, oState.mp_fTemp[8][2], 0.001);
      assertEquals(18.87, oState.mp_fTemp[8][3], 0.001);
      assertEquals(22.85, oState.mp_fTemp[8][4], 0.001);
      assertEquals(19.53, oState.mp_fTemp[8][5], 0.001);
      
      assertEquals(7.92, oState.mp_fTemp[9][0], 0.001);
      assertEquals(8.18, oState.mp_fTemp[9][1], 0.001);
      assertEquals(8.43, oState.mp_fTemp[9][2], 0.001);
      assertEquals(8.68, oState.mp_fTemp[9][3], 0.001);
      assertEquals(8.94, oState.mp_fTemp[9][4], 0.001);
      assertEquals(9.19, oState.mp_fTemp[9][5], 0.001);
      
      assertEquals(2.48, oState.mp_fTemp[10][0], 0.001);
      assertEquals(2.56, oState.mp_fTemp[10][1], 0.001);
      assertEquals(2.64, oState.mp_fTemp[10][2], 0.001);
      assertEquals(2.72, oState.mp_fTemp[10][3], 0.001);
      assertEquals(2.8, oState.mp_fTemp[10][4], 0.001);
      assertEquals(2.88, oState.mp_fTemp[10][5], 0.001);
      
      assertEquals(-9.6, oState.mp_fTemp[11][0], 0.001);
      assertEquals(-6.94, oState.mp_fTemp[11][1], 0.001);
      assertEquals(-6.51, oState.mp_fTemp[11][2], 0.001);
      assertEquals(-1.59, oState.mp_fTemp[11][3], 0.001);
      assertEquals(0.19, oState.mp_fTemp[11][4], 0.001);
      assertEquals(2.49, oState.mp_fTemp[11][5], 0.001);
      
      assertEquals(161.48, oState.mp_fPpt[0][0], 0.001);
      assertEquals(152.09, oState.mp_fPpt[0][1], 0.001);
      assertEquals(152.5, oState.mp_fPpt[0][2], 0.001);
      assertEquals(152.71, oState.mp_fPpt[0][3], 0.001);
      assertEquals(78.44, oState.mp_fPpt[0][4], 0.001);
      assertEquals(123.19, oState.mp_fPpt[0][5], 0.001);
      
      assertEquals(199.32, oState.mp_fPpt[1][0], 0.001);
      assertEquals(192.94, oState.mp_fPpt[1][1], 0.001);
      assertEquals(188.1, oState.mp_fPpt[1][2], 0.001);
      assertEquals(153.95, oState.mp_fPpt[1][3], 0.001);
      assertEquals(89.79, oState.mp_fPpt[1][4], 0.001);
      assertEquals(189.67, oState.mp_fPpt[1][5], 0.001);
      
      assertEquals(169.99, oState.mp_fPpt[2][0], 0.001);
      assertEquals(105.49, oState.mp_fPpt[2][1], 0.001);
      assertEquals(141.34, oState.mp_fPpt[2][2], 0.001);
      assertEquals(84.71, oState.mp_fPpt[2][3], 0.001);
      assertEquals(188.92, oState.mp_fPpt[2][4], 0.001);
      assertEquals(93.47, oState.mp_fPpt[2][5], 0.001);
      
      assertEquals(82.02, oState.mp_fPpt[3][0], 0.001);
      assertEquals(77.49, oState.mp_fPpt[3][1], 0.001);
      assertEquals(186.32, oState.mp_fPpt[3][2], 0.001);
      assertEquals(174.4, oState.mp_fPpt[3][3], 0.001);
      assertEquals(161.25, oState.mp_fPpt[3][4], 0.001);
      assertEquals(99.76, oState.mp_fPpt[3][5], 0.001);
      
      assertEquals(132.54, oState.mp_fPpt[4][0], 0.001);
      assertEquals(121.74, oState.mp_fPpt[4][1], 0.001);
      assertEquals(178.18, oState.mp_fPpt[4][2], 0.001);
      assertEquals(102.86, oState.mp_fPpt[4][3], 0.001);
      assertEquals(102.84, oState.mp_fPpt[4][4], 0.001);
      assertEquals(172.41, oState.mp_fPpt[4][5], 0.001);
      
      assertEquals(157.67, oState.mp_fPpt[5][0], 0.001);
      assertEquals(133.89, oState.mp_fPpt[5][1], 0.001);
      assertEquals(147.29, oState.mp_fPpt[5][2], 0.001);
      assertEquals(123.74, oState.mp_fPpt[5][3], 0.001);
      assertEquals(126.66, oState.mp_fPpt[5][4], 0.001);
      assertEquals(134.35, oState.mp_fPpt[5][5], 0.001);
      
      assertEquals(97.63, oState.mp_fPpt[6][0], 0.001);
      assertEquals(141.94, oState.mp_fPpt[6][1], 0.001);
      assertEquals(173.88, oState.mp_fPpt[6][2], 0.001);
      assertEquals(166.53, oState.mp_fPpt[6][3], 0.001);
      assertEquals(140.41, oState.mp_fPpt[6][4], 0.001);
      assertEquals(128.89, oState.mp_fPpt[6][5], 0.001);
      
      assertEquals(145.57, oState.mp_fPpt[7][0], 0.001);
      assertEquals(156.69, oState.mp_fPpt[7][1], 0.001);
      assertEquals(146.55, oState.mp_fPpt[7][2], 0.001);
      assertEquals(167.73, oState.mp_fPpt[7][3], 0.001);
      assertEquals(147.67, oState.mp_fPpt[7][4], 0.001);
      assertEquals(119.52, oState.mp_fPpt[7][5], 0.001);
      
      assertEquals(134.38, oState.mp_fPpt[8][0], 0.001);
      assertEquals(89.78, oState.mp_fPpt[8][1], 0.001);
      assertEquals(173.37, oState.mp_fPpt[8][2], 0.001);
      assertEquals(146.16, oState.mp_fPpt[8][3], 0.001);
      assertEquals(108.71, oState.mp_fPpt[8][4], 0.001);
      assertEquals(90.68, oState.mp_fPpt[8][5], 0.001);
      
      assertEquals(100.17, oState.mp_fPpt[9][0], 0.001);
      assertEquals(178.11, oState.mp_fPpt[9][1], 0.001);
      assertEquals(120.16, oState.mp_fPpt[9][2], 0.001);
      assertEquals(198.43, oState.mp_fPpt[9][3], 0.001);
      assertEquals(75.22, oState.mp_fPpt[9][4], 0.001);
      assertEquals(198.3, oState.mp_fPpt[9][5], 0.001);

      assertEquals(112.34, oState.mp_fPpt[10][0], 0.001);
      assertEquals(191.22, oState.mp_fPpt[10][1], 0.001);
      assertEquals(125.47, oState.mp_fPpt[10][2], 0.001);
      assertEquals(79.8, oState.mp_fPpt[10][3], 0.001);
      assertEquals(116.29, oState.mp_fPpt[10][4], 0.001);
      assertEquals(146.75, oState.mp_fPpt[10][5], 0.001);

      assertEquals(137.22, oState.mp_fPpt[11][0], 0.001);
      assertEquals(101.29, oState.mp_fPpt[11][1], 0.001);
      assertEquals(159.21, oState.mp_fPpt[11][2], 0.001);
      assertEquals(171.75, oState.mp_fPpt[11][3], 0.001);
      assertEquals(191.54, oState.mp_fPpt[11][4], 0.001);
      assertEquals(131.83, oState.mp_fPpt[11][5], 0.001);
      
      assertEquals(7468.475, oState.m_fRadJan.getValue(), 0.001);
      assertEquals(10353.32, oState.m_fRadFeb.getValue(), 0.001);
      assertEquals(17453.07, oState.m_fRadMar.getValue(), 0.001);
      assertEquals(22721.85, oState.m_fRadApr.getValue(), 0.001);
      assertEquals(27901.37, oState.m_fRadMay.getValue(), 0.001);
      assertEquals(28677.54, oState.m_fRadJun.getValue(), 0.001);
      assertEquals(28764.65, oState.m_fRadJul.getValue(), 0.001);
      assertEquals(25075.4, oState.m_fRadAug.getValue(), 0.001);
      assertEquals(19259.27, oState.m_fRadSep.getValue(), 0.001);
      assertEquals(12609.6, oState.m_fRadOct.getValue(), 0.001);
      assertEquals(7988.013, oState.m_fRadNov.getValue(), 0.001);
      assertEquals(6307.151, oState.m_fRadDec.getValue(), 0.001);

      assertEquals(60.9, oState.m_fAWS.getValue(), 0.00001);      
      
      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
  
  /**
   * Tests ValidateData().
   */
  public void testValidateData() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getStateChangeBehaviors().validateData(oManager.getTreePopulation());
      new File(sFileName).delete();
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    
        
    try {
      //Precipitation is negative
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      StateChangeBehaviors oStateBeh = oManager.getStateChangeBehaviors();
      ArrayList<Behavior> p_oBehs = oStateBeh.getBehaviorByParameterFileTag("ClimateImporter");
      ClimateImporter oState = (ClimateImporter) p_oBehs.get(0);
      oState.mp_fPpt[2][4] = -0.01;
      oManager.getStateChangeBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch negative ppt value.");
    }
    catch (ModelException oErr) {;}
    catch (IOException oE) {fail("Caught IOException.  Message: " + oE.getMessage());}
    finally {new File(sFileName).delete();}
    
    try {
      //Soil water storage is negative
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      StateChangeBehaviors oStateBeh = oManager.getStateChangeBehaviors();
      ArrayList<Behavior> p_oBehs = oStateBeh.getBehaviorByParameterFileTag("ClimateImporter");
      ClimateImporter oState = (ClimateImporter) p_oBehs.get(0);
      oState.m_fAWS.setValue((float)-0.9);
      oManager.getStateChangeBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch bad soil water storage value.");
    }
    catch (ModelException oErr) {;}
    catch (IOException oE) {fail("Caught IOException.  Message: " + oE.getMessage());}
    finally {new File(sFileName).delete();}
    
  }

  
  /**
   * Writes a file with valid settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  public static String writeValidXMLFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>6</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<plot_lenX>100</plot_lenX>");
    oOut.write("<plot_lenY>100</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1383.79729</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>6.27363808</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_seasonal_precipitation>600</plot_seasonal_precipitation>");
    oOut.write("<plot_water_deficit>500</plot_water_deficit>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ClimateImporter</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ClimateImporter1>");
    oOut.write("<sc_ciAWS>60.9</sc_ciAWS>");

    oOut.write("<sc_ciMonthlyTempJan>");
    oOut.write("<sc_cimtjanVal ts=\"1\">-2.57</sc_cimtjanVal>");
    oOut.write("<sc_cimtjanVal ts=\"2\">-3.46</sc_cimtjanVal>");
    oOut.write("<sc_cimtjanVal ts=\"3\">-6.93</sc_cimtjanVal>");
    oOut.write("<sc_cimtjanVal ts=\"4\">-7.76</sc_cimtjanVal>");
    oOut.write("<sc_cimtjanVal ts=\"5\">-0.72</sc_cimtjanVal>");
    oOut.write("<sc_cimtjanVal ts=\"6\">1.3</sc_cimtjanVal>");
    oOut.write("</sc_ciMonthlyTempJan>");

    oOut.write("<sc_ciMonthlyTempFeb>");
    oOut.write("<sc_cimtfebVal ts=\"1\">2.75</sc_cimtfebVal>");
    oOut.write("<sc_cimtfebVal ts=\"2\">-8.57</sc_cimtfebVal>");
    oOut.write("<sc_cimtfebVal ts=\"3\">1.98</sc_cimtfebVal>");
    oOut.write("<sc_cimtfebVal ts=\"4\">-7.5</sc_cimtfebVal>");
    oOut.write("<sc_cimtfebVal ts=\"5\">-2.44</sc_cimtfebVal>");
    oOut.write("<sc_cimtfebVal ts=\"6\">-9.36</sc_cimtfebVal>");
    oOut.write("</sc_ciMonthlyTempFeb>");

    oOut.write("<sc_ciMonthlyTempMar>");
    oOut.write("<sc_cimtmarVal ts=\"1\">2.94</sc_cimtmarVal>");
    oOut.write("<sc_cimtmarVal ts=\"2\">0.28</sc_cimtmarVal>");
    oOut.write("<sc_cimtmarVal ts=\"3\">0.3</sc_cimtmarVal>");
    oOut.write("<sc_cimtmarVal ts=\"4\">-0.78</sc_cimtmarVal>");
    oOut.write("<sc_cimtmarVal ts=\"5\">4.85</sc_cimtmarVal>");
    oOut.write("<sc_cimtmarVal ts=\"6\">3.78</sc_cimtmarVal>");
    oOut.write("</sc_ciMonthlyTempMar>");

    oOut.write("<sc_ciMonthlyTempApr>");
    oOut.write("<sc_cimtaprVal ts=\"1\">2.17</sc_cimtaprVal>");
    oOut.write("<sc_cimtaprVal ts=\"2\">5.9</sc_cimtaprVal>");
    oOut.write("<sc_cimtaprVal ts=\"3\">7.17</sc_cimtaprVal>");
    oOut.write("<sc_cimtaprVal ts=\"4\">8.67</sc_cimtaprVal>");
    oOut.write("<sc_cimtaprVal ts=\"5\">5.61</sc_cimtaprVal>");
    oOut.write("<sc_cimtaprVal ts=\"6\">4.56</sc_cimtaprVal>");
    oOut.write("</sc_ciMonthlyTempApr>");

    oOut.write("<sc_ciMonthlyTempMay>");
    oOut.write("<sc_cimtmayVal ts=\"1\">7.25</sc_cimtmayVal>");
    oOut.write("<sc_cimtmayVal ts=\"2\">7.6</sc_cimtmayVal>");
    oOut.write("<sc_cimtmayVal ts=\"3\">9.46</sc_cimtmayVal>");
    oOut.write("<sc_cimtmayVal ts=\"4\">11.26</sc_cimtmayVal>");
    oOut.write("<sc_cimtmayVal ts=\"5\">7.6</sc_cimtmayVal>");
    oOut.write("<sc_cimtmayVal ts=\"6\">6.9</sc_cimtmayVal>");
    oOut.write("</sc_ciMonthlyTempMay>");

    oOut.write("<sc_ciMonthlyTempJun>");
    oOut.write("<sc_cimtjunVal ts=\"1\">11.05</sc_cimtjunVal>");
    oOut.write("<sc_cimtjunVal ts=\"2\">17.16</sc_cimtjunVal>");
    oOut.write("<sc_cimtjunVal ts=\"3\">11.06</sc_cimtjunVal>");
    oOut.write("<sc_cimtjunVal ts=\"4\">17.72</sc_cimtjunVal>");
    oOut.write("<sc_cimtjunVal ts=\"5\">14.05</sc_cimtjunVal>");
    oOut.write("<sc_cimtjunVal ts=\"6\">13.52</sc_cimtjunVal>");
    oOut.write("</sc_ciMonthlyTempJun>");

    oOut.write("<sc_ciMonthlyTempJul>");
    oOut.write("<sc_cimtjulVal ts=\"1\">16.82</sc_cimtjulVal>");
    oOut.write("<sc_cimtjulVal ts=\"2\">22</sc_cimtjulVal>");
    oOut.write("<sc_cimtjulVal ts=\"3\">18.34</sc_cimtjulVal>");
    oOut.write("<sc_cimtjulVal ts=\"4\">20.99</sc_cimtjulVal>");
    oOut.write("<sc_cimtjulVal ts=\"5\">15.46</sc_cimtjulVal>");
    oOut.write("<sc_cimtjulVal ts=\"6\">15.74</sc_cimtjulVal>");
    oOut.write("</sc_ciMonthlyTempJul>");

    oOut.write("<sc_ciMonthlyTempAug>");
    oOut.write("<sc_cimtaugVal ts=\"1\">18.32</sc_cimtaugVal>");
    oOut.write("<sc_cimtaugVal ts=\"2\">15.97</sc_cimtaugVal>");
    oOut.write("<sc_cimtaugVal ts=\"3\">17.54</sc_cimtaugVal>");
    oOut.write("<sc_cimtaugVal ts=\"4\">17.46</sc_cimtaugVal>");
    oOut.write("<sc_cimtaugVal ts=\"5\">16.69</sc_cimtaugVal>");
    oOut.write("<sc_cimtaugVal ts=\"6\">17.48</sc_cimtaugVal>");
    oOut.write("</sc_ciMonthlyTempAug>");

    oOut.write("<sc_ciMonthlyTempSep>");
    oOut.write("<sc_cimtsepVal ts=\"1\">22.73</sc_cimtsepVal>");
    oOut.write("<sc_cimtsepVal ts=\"2\">20.94</sc_cimtsepVal>");
    oOut.write("<sc_cimtsepVal ts=\"3\">15.94</sc_cimtsepVal>");
    oOut.write("<sc_cimtsepVal ts=\"4\">18.87</sc_cimtsepVal>");
    oOut.write("<sc_cimtsepVal ts=\"5\">22.85</sc_cimtsepVal>");
    oOut.write("<sc_cimtsepVal ts=\"6\">19.53</sc_cimtsepVal>");
    oOut.write("</sc_ciMonthlyTempSep>");

    oOut.write("<sc_ciMonthlyTempOct>");
    oOut.write("<sc_cimtoctVal ts=\"1\">7.92</sc_cimtoctVal>");
    oOut.write("<sc_cimtoctVal ts=\"2\">8.18</sc_cimtoctVal>");
    oOut.write("<sc_cimtoctVal ts=\"3\">8.43</sc_cimtoctVal>");
    oOut.write("<sc_cimtoctVal ts=\"4\">8.68</sc_cimtoctVal>");
    oOut.write("<sc_cimtoctVal ts=\"5\">8.94</sc_cimtoctVal>");
    oOut.write("<sc_cimtoctVal ts=\"6\">9.19</sc_cimtoctVal>");
    oOut.write("</sc_ciMonthlyTempOct>");

    oOut.write("<sc_ciMonthlyTempNov>");
    oOut.write("<sc_cimtnovVal ts=\"1\">2.48</sc_cimtnovVal>");
    oOut.write("<sc_cimtnovVal ts=\"2\">2.56</sc_cimtnovVal>");
    oOut.write("<sc_cimtnovVal ts=\"3\">2.64</sc_cimtnovVal>");
    oOut.write("<sc_cimtnovVal ts=\"4\">2.72</sc_cimtnovVal>");
    oOut.write("<sc_cimtnovVal ts=\"5\">2.8</sc_cimtnovVal>");
    oOut.write("<sc_cimtnovVal ts=\"6\">2.88</sc_cimtnovVal>");
    oOut.write("</sc_ciMonthlyTempNov>");

    oOut.write("<sc_ciMonthlyTempDec>");
    oOut.write("<sc_cimtdecVal ts=\"1\">-9.6</sc_cimtdecVal>");
    oOut.write("<sc_cimtdecVal ts=\"2\">-6.94</sc_cimtdecVal>");
    oOut.write("<sc_cimtdecVal ts=\"3\">-6.51</sc_cimtdecVal>");
    oOut.write("<sc_cimtdecVal ts=\"4\">-1.59</sc_cimtdecVal>");
    oOut.write("<sc_cimtdecVal ts=\"5\">0.19</sc_cimtdecVal>");
    oOut.write("<sc_cimtdecVal ts=\"6\">2.49</sc_cimtdecVal>");
    oOut.write("</sc_ciMonthlyTempDec>");

    oOut.write("<sc_ciMonthlyPptJan>");
    oOut.write("<sc_cimpjanVal ts=\"1\">161.48</sc_cimpjanVal>");
    oOut.write("<sc_cimpjanVal ts=\"2\">152.09</sc_cimpjanVal>");
    oOut.write("<sc_cimpjanVal ts=\"3\">152.5</sc_cimpjanVal>");
    oOut.write("<sc_cimpjanVal ts=\"4\">152.71</sc_cimpjanVal>");
    oOut.write("<sc_cimpjanVal ts=\"5\">78.44</sc_cimpjanVal>");
    oOut.write("<sc_cimpjanVal ts=\"6\">123.19</sc_cimpjanVal>");
    oOut.write("</sc_ciMonthlyPptJan>");

    oOut.write("<sc_ciMonthlyPptFeb>");
    oOut.write("<sc_cimpfebVal ts=\"1\">199.32</sc_cimpfebVal>");
    oOut.write("<sc_cimpfebVal ts=\"2\">192.94</sc_cimpfebVal>");
    oOut.write("<sc_cimpfebVal ts=\"3\">188.1</sc_cimpfebVal>");
    oOut.write("<sc_cimpfebVal ts=\"4\">153.95</sc_cimpfebVal>");
    oOut.write("<sc_cimpfebVal ts=\"5\">89.79</sc_cimpfebVal>");
    oOut.write("<sc_cimpfebVal ts=\"6\">189.67</sc_cimpfebVal>");
    oOut.write("</sc_ciMonthlyPptFeb>");

    oOut.write("<sc_ciMonthlyPptMar>");
    oOut.write("<sc_cimpmarVal ts=\"1\">169.99</sc_cimpmarVal>");
    oOut.write("<sc_cimpmarVal ts=\"2\">105.49</sc_cimpmarVal>");
    oOut.write("<sc_cimpmarVal ts=\"3\">141.34</sc_cimpmarVal>");
    oOut.write("<sc_cimpmarVal ts=\"4\">84.71</sc_cimpmarVal>");
    oOut.write("<sc_cimpmarVal ts=\"5\">188.92</sc_cimpmarVal>");
    oOut.write("<sc_cimpmarVal ts=\"6\">93.47</sc_cimpmarVal>");
    oOut.write("</sc_ciMonthlyPptMar>");

    oOut.write("<sc_ciMonthlyPptApr>");
    oOut.write("<sc_cimpaprVal ts=\"1\">82.02</sc_cimpaprVal>");
    oOut.write("<sc_cimpaprVal ts=\"2\">77.49</sc_cimpaprVal>");
    oOut.write("<sc_cimpaprVal ts=\"3\">186.32</sc_cimpaprVal>");
    oOut.write("<sc_cimpaprVal ts=\"4\">174.4</sc_cimpaprVal>");
    oOut.write("<sc_cimpaprVal ts=\"5\">161.25</sc_cimpaprVal>");
    oOut.write("<sc_cimpaprVal ts=\"6\">99.76</sc_cimpaprVal>");
    oOut.write("</sc_ciMonthlyPptApr>");

    oOut.write("<sc_ciMonthlyPptMay>");
    oOut.write("<sc_cimpmayVal ts=\"1\">132.54</sc_cimpmayVal>");
    oOut.write("<sc_cimpmayVal ts=\"2\">121.74</sc_cimpmayVal>");
    oOut.write("<sc_cimpmayVal ts=\"3\">178.18</sc_cimpmayVal>");
    oOut.write("<sc_cimpmayVal ts=\"4\">102.86</sc_cimpmayVal>");
    oOut.write("<sc_cimpmayVal ts=\"5\">102.84</sc_cimpmayVal>");
    oOut.write("<sc_cimpmayVal ts=\"6\">172.41</sc_cimpmayVal>");
    oOut.write("</sc_ciMonthlyPptMay>");
    oOut.write("<sc_ciMonthlyPptJun>");
    oOut.write("<sc_cimpjunVal ts=\"1\">157.67</sc_cimpjunVal>");
    oOut.write("<sc_cimpjunVal ts=\"2\">133.89</sc_cimpjunVal>");
    oOut.write("<sc_cimpjunVal ts=\"3\">147.29</sc_cimpjunVal>");
    oOut.write("<sc_cimpjunVal ts=\"4\">123.74</sc_cimpjunVal>");
    oOut.write("<sc_cimpjunVal ts=\"5\">126.66</sc_cimpjunVal>");
    oOut.write("<sc_cimpjunVal ts=\"6\">134.35</sc_cimpjunVal>");
    oOut.write("</sc_ciMonthlyPptJun>");

    oOut.write("<sc_ciMonthlyPptJul>");
    oOut.write("<sc_cimpjulVal ts=\"1\">97.63</sc_cimpjulVal>");
    oOut.write("<sc_cimpjulVal ts=\"2\">141.94</sc_cimpjulVal>");
    oOut.write("<sc_cimpjulVal ts=\"3\">173.88</sc_cimpjulVal>");
    oOut.write("<sc_cimpjulVal ts=\"4\">166.53</sc_cimpjulVal>");
    oOut.write("<sc_cimpjulVal ts=\"5\">140.41</sc_cimpjulVal>");
    oOut.write("<sc_cimpjulVal ts=\"6\">128.89</sc_cimpjulVal>");
    oOut.write("</sc_ciMonthlyPptJul>");

    oOut.write("<sc_ciMonthlyPptAug>");
    oOut.write("<sc_cimpaugVal ts=\"1\">145.57</sc_cimpaugVal>");
    oOut.write("<sc_cimpaugVal ts=\"2\">156.69</sc_cimpaugVal>");
    oOut.write("<sc_cimpaugVal ts=\"3\">146.55</sc_cimpaugVal>");
    oOut.write("<sc_cimpaugVal ts=\"4\">167.73</sc_cimpaugVal>");
    oOut.write("<sc_cimpaugVal ts=\"5\">147.67</sc_cimpaugVal>");
    oOut.write("<sc_cimpaugVal ts=\"6\">119.52</sc_cimpaugVal>");
    oOut.write("</sc_ciMonthlyPptAug>");

    oOut.write("<sc_ciMonthlyPptSep>");
    oOut.write("<sc_cimpsepVal ts=\"1\">134.38</sc_cimpsepVal>");
    oOut.write("<sc_cimpsepVal ts=\"2\">89.78</sc_cimpsepVal>");
    oOut.write("<sc_cimpsepVal ts=\"3\">173.37</sc_cimpsepVal>");
    oOut.write("<sc_cimpsepVal ts=\"4\">146.16</sc_cimpsepVal>");
    oOut.write("<sc_cimpsepVal ts=\"5\">108.71</sc_cimpsepVal>");
    oOut.write("<sc_cimpsepVal ts=\"6\">90.68</sc_cimpsepVal>");
    oOut.write("</sc_ciMonthlyPptSep>");

    oOut.write("<sc_ciMonthlyPptOct>");
    oOut.write("<sc_cimpoctVal ts=\"1\">100.17</sc_cimpoctVal>");
    oOut.write("<sc_cimpoctVal ts=\"2\">178.11</sc_cimpoctVal>");
    oOut.write("<sc_cimpoctVal ts=\"3\">120.16</sc_cimpoctVal>");
    oOut.write("<sc_cimpoctVal ts=\"4\">198.43</sc_cimpoctVal>");
    oOut.write("<sc_cimpoctVal ts=\"5\">75.22</sc_cimpoctVal>");
    oOut.write("<sc_cimpoctVal ts=\"6\">198.3</sc_cimpoctVal>");
    oOut.write("</sc_ciMonthlyPptOct>");

    oOut.write("<sc_ciMonthlyPptNov>");
    oOut.write("<sc_cimpnovVal ts=\"1\">112.34</sc_cimpnovVal>");
    oOut.write("<sc_cimpnovVal ts=\"2\">191.22</sc_cimpnovVal>");
    oOut.write("<sc_cimpnovVal ts=\"3\">125.47</sc_cimpnovVal>");
    oOut.write("<sc_cimpnovVal ts=\"4\">79.8</sc_cimpnovVal>");
    oOut.write("<sc_cimpnovVal ts=\"5\">116.29</sc_cimpnovVal>");
    oOut.write("<sc_cimpnovVal ts=\"6\">146.75</sc_cimpnovVal>");
    oOut.write("</sc_ciMonthlyPptNov>");

    oOut.write("<sc_ciMonthlyPptDec>");
    oOut.write("<sc_cimpdecVal ts=\"1\">137.22</sc_cimpdecVal>");
    oOut.write("<sc_cimpdecVal ts=\"2\">101.29</sc_cimpdecVal>");
    oOut.write("<sc_cimpdecVal ts=\"3\">159.21</sc_cimpdecVal>");
    oOut.write("<sc_cimpdecVal ts=\"4\">171.75</sc_cimpdecVal>");
    oOut.write("<sc_cimpdecVal ts=\"5\">191.54</sc_cimpdecVal>");
    oOut.write("<sc_cimpdecVal ts=\"6\">131.83</sc_cimpdecVal>");
    oOut.write("</sc_ciMonthlyPptDec>");

    oOut.write("<sc_ciJanRad>7468.475</sc_ciJanRad>");
    oOut.write("<sc_ciFebRad>10353.32</sc_ciFebRad>");
    oOut.write("<sc_ciMarRad>17453.07</sc_ciMarRad>");
    oOut.write("<sc_ciAprRad>22721.85</sc_ciAprRad>");
    oOut.write("<sc_ciMayRad>27901.37</sc_ciMayRad>");
    oOut.write("<sc_ciJunRad>28677.54</sc_ciJunRad>");
    oOut.write("<sc_ciJulRad>28764.65</sc_ciJulRad>");
    oOut.write("<sc_ciAugRad>25075.4</sc_ciAugRad>");
    oOut.write("<sc_ciSepRad>19259.27</sc_ciSepRad>");
    oOut.write("<sc_ciOctRad>12609.6</sc_ciOctRad>");
    oOut.write("<sc_ciNovRad>7988.013</sc_ciNovRad>");
    oOut.write("<sc_ciDecRad>6307.151</sc_ciDecRad>");

    oOut.write("</ClimateImporter1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
