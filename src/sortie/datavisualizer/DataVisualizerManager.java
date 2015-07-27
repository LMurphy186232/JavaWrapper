package sortie.datavisualizer;

import javax.swing.*;

import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.MainWindow;
import sortie.gui.ChartFrameInfo;

import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

/**
 * This class takes care of managing data visualization. It keeps track of open
 * files and causes their data windows to be placed on the main application
 * window.
 * 
 * A current run may be opened for visualization as well. These files will be
 * kept track of separately and updated on command.
 * 
 * When a new window is opened, if it is the first window open, it is placed at
 * the upper right of the screen, just to the right of its legend, which is in
 * the upper right corner. When the next window is opened, if a window is
 * already in that position, it opens itself 20 pixels down and to the left.
 * Subsequent windows open themselves 20 pixels down and to the left until the
 * middle of the screen is reached, at which point the cycle returns to the
 * upper right. If a window is opened and then moved, the next window open will
 * grab its former spot.
 * 
 * Copyright: Copyright (c) Charles D. Canham 2012
 * Company: Cary Institute of Ecosystem Studies
 * 
 * 
 * @author Lora E. Murphy
 * @version 1.0
 * 
 * <br>Edit history:
 * <br>------------------ 
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class DataVisualizerManager {
  /** Main window - for checking statuses */
  MainWindow m_oMain;
  /** Here's where this class will put windows */
  JDesktopPane m_jDesktop;
  /** Listener to be assigned to all internal frames added by this class */
  DesktopListener m_oDesktopListener;
  /** Data file managers - one for each open data file */
  ArrayList<DataFileManager> mp_oDataManagers = new ArrayList<DataFileManager>(0);
  /**
   * Data file managers - one for each output file of the current run - this is
   * a subset of mp_oDataManagers
   */
  ArrayList<DataFileManager> mp_oCurrentRunDataManagers = new ArrayList<DataFileManager>(0);
  /** The number of positions available in which to put a newly opened window */
  private int m_iNumOpenPositions = 0;

  /**
   * Constructor.
   * @param oMain Main window.
   * @param jDesktop Desktop pane.
   * @param jScroller Scroll pane enclosing jDesktop.
   */
  public DataVisualizerManager(MainWindow oMain, JDesktopPane jDesktop, 
      JScrollPane jScroller) {
    m_oMain = oMain;
    m_jDesktop = jDesktop;
    m_oDesktopListener = new DesktopListener(m_jDesktop, jScroller);

  }
  
  /**
   * Gets the data manager in charge of visualization for an output file.
   * @param sFile File for which to find the data manager.
   * @return Data manager.
   */
  public DataFileManager getDataManagerForFile(String sFile) {
    
    // Find the manager that belongs to this file
    for (DataFileManager oManager : mp_oDataManagers) {
      if (oManager.getFileName().equalsIgnoreCase(sFile)) {
        return oManager;
      }
    }

    return null;
  }

  /**
   * Draws a chart on the desktop.
   * 
   * @param sFile The filename from which to draw the chart.
   * @param sChartChoice The chart to draw on the desktop.
   * @param oInfo Chart frame info.
   * @throws ModelException If the filename is not valid, or the chart choice 
   * cannot be drawn, or wrapping another error.
   */
  public void drawChart(String sFile, String sChartChoice, ChartFrameInfo oInfo)
      throws ModelException {
    try {
      if (mp_oDataManagers.size() == 0) {
        return;
      }
      DataFileManager oManager = getDataManagerForFile(sFile);
      int i, j, // loop counters
      iXPos, iYPos, // for positioning windows
      iLegendXPos;
      boolean bExists, bSomeoneHere, // for positioning windows
      bFoundAPlace; // for positioning windows
      
      if (oManager ==  null) {
        throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA",
            "Can't find output file " + sFile));
      }

      // If there's no chart choice passed, or it starts with three dashes
      // (which isn't a choice), ignore
      if (sChartChoice == null || sChartChoice.length() == 0
          || sChartChoice.startsWith("---")) {
        return;
      }

      // Pass the choice to the data viewer and get the internal frame returned
      JInternalFrame oNewGraph = oManager.createNewChart(sChartChoice);
      if (oNewGraph != null) {
        // Add the legend to the upper right corner
        JInternalFrame oLegend = oManager.getLegend();

        // Is the legend already on the desktop?
        JInternalFrame[] oFrames = m_jDesktop.getAllFrames();
        bExists = false;
        for (i = 0; i < oFrames.length; i++) {
          if (oFrames[i].equals(oLegend)) {
            bExists = true;
            break;
          }
        }
        if (!bExists) {
          
          oLegend.pack();
          oLegend.addComponentListener(m_oDesktopListener);
          
          // Adjust the bounds if necessary to fit on the screen
          if (oLegend.getSize().height > m_jDesktop.getSize().height
              || oLegend.getSize().width > m_jDesktop.getSize().width) {

            int iWidth = java.lang.Math.min(oLegend.getSize().width, m_jDesktop
                .getSize().width - oLegend.getWidth());
            int iHeight = java.lang.Math.min(oLegend.getSize().height,
                m_jDesktop.getSize().height);

            oLegend.setBounds(oLegend.getBounds().x, oLegend.getBounds().y,
                iWidth, iHeight);
          }
          
          
          m_jDesktop.add(oLegend);
        }
        oLegend.setVisible(true);
        try {
          oLegend.setMaximum(true);              
          oLegend.setMaximum(false);
        } catch (PropertyVetoException e) {;}
        // Is the graph already on the desktop?
        bExists = false;
        for (i = 0; i < oFrames.length; i++) {
          if (oFrames[i].equals(oNewGraph)) {
            bExists = true;
            try {
              oFrames[i].setVisible(true);
              //Have to do both true then false for it to "take"
              oFrames[i].setMaximum(true);              
              oFrames[i].setMaximum(false); 
            } catch (PropertyVetoException e) {;}              
            break;
          }
        }
        if (bExists) {
          return;
        }
        
        oNewGraph.pack();
        if (oNewGraph instanceof ModelInternalFrame) {
          ((ModelInternalFrame)oNewGraph).setChartFrameInfo(oInfo);  
        }        
        oNewGraph.addInternalFrameListener(m_oMain);
        oNewGraph.addComponentListener(m_oDesktopListener);

        // Adjust the bounds if necessary to fit on the screen
        if (oNewGraph.getSize().height > m_jDesktop.getSize().height
            || oNewGraph.getSize().width > m_jDesktop.getSize().width) {

          int iWidth = java.lang.Math.min(oNewGraph.getSize().width, m_jDesktop
              .getSize().width
              - oLegend.getWidth());
          int iHeight = java.lang.Math.min(oNewGraph.getSize().height,
              m_jDesktop.getSize().height);

          oNewGraph.setBounds(oNewGraph.getBounds().x, oNewGraph.getBounds().y,
              iWidth, iHeight);
        }

        m_jDesktop.add(oNewGraph);

        // Now place the graph in the first unoccupied window location
        bFoundAPlace = false;
        iLegendXPos = (int) oLegend.getSize().getWidth();
        for (i = 0; i < m_iNumOpenPositions; i++) {
          iXPos = iLegendXPos + (i * 20);
          iYPos = i * 20;

          // Is there anyone at this position?
          bSomeoneHere = false;
          for (j = 0; j < oFrames.length; j++) {
            if (oFrames[j].getX() == iXPos && oFrames[j].getY() == iYPos) {
              bSomeoneHere = true;
              break;
            }
          }
          if (!bSomeoneHere) {
            bFoundAPlace = true;
            oNewGraph.setLocation(iXPos, iYPos);
            break;
          }
        }

        // All locations taken? Just go back to the upper right.
        if (!bFoundAPlace) {
          oNewGraph.setLocation(iLegendXPos, 0);
        }
        oNewGraph.setVisible(true);
        // Cause the desktop to be resized
        m_oDesktopListener.resizeDesktop();
      }
    } catch (ModelException e) {
      throw (e);
    }
  }

  /**
   * Closes a detailed output file. If the file doesn't match any files under
   * management, nothing happens.
   * 
   * @param sFileName File to close.
   */
  public void closeFile(String sFileName) throws ModelException {
    DataFileManager oFileManager = null;
    int i;
    for (i = 0; i < mp_oCurrentRunDataManagers.size(); i++) {
      oFileManager = mp_oCurrentRunDataManagers.get(i);
      if (oFileManager.getFileName().equals(sFileName)) {
        mp_oCurrentRunDataManagers.remove(i);
        break;
      }
    }
    for (i = 0; i < mp_oDataManagers.size(); i++) {
      oFileManager = mp_oDataManagers.get(i);
      if (oFileManager.getFileName().equals(sFileName)) {
        oFileManager.closeAllCharts();
        oFileManager.cleanUp();
        mp_oDataManagers.remove(i);
        break;
      }
    }
  }

  /**
   * Causes all objects under management to perform any cleanup operations. Each
   * should have its own CleanUp function; this will be called for each.
   */
  public void cleanUp() throws ModelException {
    DataFileManager oData;
    int i;
    for (i = 0; i < mp_oDataManagers.size(); i++) {
      oData = mp_oDataManagers.get(i);
      oData.cleanUp();
    }
  }

  /**
   * Notifies this object of a new output file. This should be a file not being
   * generated by the current run.
   * 
   * @param sFileName  The filename.
   * @throws ModelException if this is not a recognized output file.
   */
  public void addFile(String sFileName) throws ModelException {
    mp_oDataManagers.add(makeDataManager(sFileName));
  }

  /**
   * Notifies this object of a new output file for the current run. This file is
   * subject to continuous updating.
   * 
   * @param sFileName The filename.
   * @throws ModelException if this is not a recognized output file.
   */
  public void addCurrentRunFile(String sFileName) throws ModelException {
    DataFileManager oNewManager = makeDataManager(sFileName);
    if (oNewManager != null) {
      mp_oDataManagers.add(oNewManager);
      mp_oCurrentRunDataManagers.add(oNewManager);
    }
  }

  /**
   * Creates a new data manager. This checks for the prior existence of a
   * manager for the given file; if it already exists, the user is warned and
   * nothing is done.
   * 
   * @param sFileName String The file name for which to create a new file 
   * manager
   * @return DataFileManager The newly created manager, or null if the file
   * already has a manager.
   * @throws ModelException if the file type is not recognized, or if there is 
   * a problem creating the manager.
   */
  private DataFileManager makeDataManager(String sFileName)
      throws ModelException {
    DataFileManager oNewManager = null;

    if (m_iNumOpenPositions == 0) {
      // Determine the number of open-window positions we can cycle through
      // We have to do this here instead of the constructor because in the
      // constructor the main window has no size yet
      m_iNumOpenPositions = java.lang.Math.min(m_jDesktop.getWidth() / 2,
          m_jDesktop.getHeight() / 2) / 20;
    }

    // Has this file already been added?
    for (int i = 0; i < mp_oDataManagers.size(); i++) {
      oNewManager = mp_oDataManagers.get(i);
      if (oNewManager.getFileName().equalsIgnoreCase(sFileName)) {
        throw (new ModelException(ErrorGUI.BAD_COMMAND, "JAVA",
            "The output file " + sFileName + " is already open."));
      }
    }

    if (sFileName.endsWith(".out")) {
      oNewManager = new ShortOutputFileManager(this, sFileName);
    } else if (sFileName.endsWith(".tar")) {
      oNewManager = new DetailedOutputFileManager(this, sFileName);
    } else {

      // Throw an error - this is not a recognized output file
      throw (new ModelException(ErrorGUI.BAD_FILE_TYPE, "JAVA", sFileName
          + " is not a recognized output file."));
    }
    return oNewManager;
  }

  /**
   * Tells whether there are any charts open for the current run (for real-time
   * data visualization).
   * 
   * @return boolean True if there are any charts open for real-time data
   * visualization. Returns false if there are no current run files
   * loaded, or if they have no charts open.
   */
  public boolean areCurrentRunChartsOpen() {
    int iSize = mp_oCurrentRunDataManagers.size();
    if (iSize == 0) {
      return false;
    }
    for (int i = 0; i < iSize; i++) {
      if (mp_oCurrentRunDataManagers.get(i).getNumberOpenCharts() > 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * Updates any charts open for the current run. If there are no open charts
   * for the current run, this does nothing. This allows the file managers to
   * decide how best to handle this, but the expectation is that they will
   * display the most recent available data.
   * 
   * @throws ModelException if there is a problem drawing the charts.
   */
  public void updateCurrentRunCharts() throws ModelException {
    int i;
    for (i = 0; i < mp_oCurrentRunDataManagers.size(); i++) {
      mp_oCurrentRunDataManagers.get(i).updateCurrentRunCharts();
    }
  }

  /**
   * Closes all charts associated with the current run.
   * 
   * @param oWindow Window so we can tell it to get rid of the file names from 
   * its options window
   */
  public void closeCurrentRunCharts(MainWindow oWindow) {
    while (mp_oCurrentRunDataManagers.size() > 0) {
      String sFileName = mp_oCurrentRunDataManagers.get(0).getFileName();
      oWindow.closeOneDataFile(sFileName);
    }
  }

  /**
   * Gets the list of chart options for an output file, ready to display in a
   * combo box.
   * 
   * @param sFileName The filename.
   * @param sActionListener Action listener that will respond to graphs.
   * @return The file choices to display for this file.
   * @throws ModelException if the file cannot be found.
   */
  public JPopupMenu getLineGraphOptionsForFile(String sFileName, ActionListener sActionListener)
      throws ModelException {
    DataFileManager oManager = null;
    int i;

    // Go through and find the manager that goes with this filename
    for (i = 0; i < mp_oDataManagers.size(); i++) {
      oManager = mp_oDataManagers.get(i);
      if (oManager.getFileName().equalsIgnoreCase(sFileName)) {
        break;
      }
    }

    if (oManager == null) {
      throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA", "The output file "
          + sFileName + " is not open."));
    }

    // We want to make sure that an option that exists is always put at the
    // top - so we'll add those options that exist to the beginning of the list

    return oManager.getLineGraphOptions(sActionListener);
  }
  
  /**
   * Gets the list of chart options for an output file, ready to display in a
   * combo box.
   * 
   * @param sFileName The filename.
   * @param sActionListener Action listener that will respond to graphs.
   * @return The file choices to display for this file.
   * @throws ModelException if the file cannot be found.
   */
  public JPopupMenu getHistogramOptionsForFile(String sFileName, ActionListener sActionListener)
      throws ModelException {
    DataFileManager oManager = null;
    int i;

    // Go through and find the manager that goes with this filename
    for (i = 0; i < mp_oDataManagers.size(); i++) {
      oManager = mp_oDataManagers.get(i);
      if (oManager.getFileName().equalsIgnoreCase(sFileName)) {
        break;
      }
    }

    if (oManager == null) {
      throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA", "The output file "
          + sFileName + " is not open."));
    }

    return oManager.getHistogramOptions(sActionListener);
  }
  
  /**
   * Gets the list of chart options for an output file, ready to display in a
   * combo box.
   * 
   * @param sFileName The filename.
   * @param sActionListener Action listener that will respond to graphs.
   * @return The file choices to display for this file.
   * @throws ModelException if the file cannot be found.
   */
  public JPopupMenu getTableOptionsForFile(String sFileName, ActionListener sActionListener)
      throws ModelException {
    DataFileManager oManager = null;
    int i;

    // Go through and find the manager that goes with this filename
    for (i = 0; i < mp_oDataManagers.size(); i++) {
      oManager = mp_oDataManagers.get(i);
      if (oManager.getFileName().equalsIgnoreCase(sFileName)) {
        break;
      }
    }

    if (oManager == null) {
      throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA", "The output file "
          + sFileName + " is not open."));
    }

    return oManager.getTableOptions(sActionListener);
  }
  
  /**
   * Gets the list of chart options for an output file, ready to display in a
   * combo box.
   * 
   * @param sFileName The filename.
   * @param sActionListener Action listener that will respond to graphs.
   * @return The file choices to display for this file.
   * @throws ModelException if the file cannot be found.
   */
  public JPopupMenu getMapOptionsForFile(String sFileName, ActionListener sActionListener)
      throws ModelException {
    DataFileManager oManager = null;
    int i;

    // Go through and find the manager that goes with this filename
    for (i = 0; i < mp_oDataManagers.size(); i++) {
      oManager = mp_oDataManagers.get(i);
      if (oManager.getFileName().equalsIgnoreCase(sFileName)) {
        break;
      }
    }

    if (oManager == null) {
      throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA", "The output file "
          + sFileName + " is not open."));
    }

    return oManager.getMapOptions(sActionListener);
  }

  /**
   * Gets the current window state.
   * @return int Current state.  This matches one of the choices in
   * MainWindowStateSetter.
   */
  public int getModelState() {
    return m_oMain.getModelState();
  }
}
