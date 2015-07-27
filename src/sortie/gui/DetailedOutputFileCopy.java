package sortie.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.regex.Matcher;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sortie.data.simpletypes.ModelException;
import sortie.fileops.Tarball;
import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;

/**
 * This window will rename a detailed output file. This requires untarring, 
 * uncompressing, renaming all the individual files, recompressing, and 
 * retarring.
 * 
 * <p>Copyright: Copyright (c) Charles D. Canham 2010</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 * 
 * <br>Edit history:
 * <br>------------------
 * <br>June 14, 2011: Created (LEM) 
 */
public class DetailedOutputFileCopy extends JFrame implements ActionListener {
  
  /** File to be renamed */
  JTextField m_jDetailedOutputFileToRename = new JTextField();
  
  /** New file name */
  JTextField m_jNewFileName = new JTextField();
  
  /**Main window*/
  MainWindow m_oWindow;
  
  /**ID of help topic for this window*/
  private String m_sHelpID = "windows.tools_copy_detailed_output_file_window";

  public DetailedOutputFileCopy(MainWindow oWindow) {
    super("Copy detailed output file");
    m_oWindow = oWindow;
    
    //Panel holding all the controls
    JPanel jComponentPanel = new JPanel();
    jComponentPanel.setLayout(new javax.swing.BoxLayout(jComponentPanel,
        javax.swing.BoxLayout.PAGE_AXIS));

    JLabel jLabel = new JLabel("File to copy:");
    jLabel.setFont(new SortieFont());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(jLabel);

    //Package the detailed output edit box and the browse button in a panel so
    //they'll be side-by-side
    JPanel jTempPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.
        LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jDetailedOutputFileToRename.setFont(new SortieFont());
    m_jDetailedOutputFileToRename.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jDetailedOutputFileToRename.setPreferredSize(new Dimension(300, 25));
    jTempPanel.add(m_jDetailedOutputFileToRename);

    JButton jButton = new JButton("Browse");
    jButton.setFont(new SortieFont());
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.addActionListener(this);
    jButton.setActionCommand("BrowseToRename");
    jTempPanel.add(jButton);

    jComponentPanel.add(jTempPanel);
    jComponentPanel.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));

    jLabel = new JLabel("New copy filename:");
    jLabel.setFont(new SortieFont());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(jLabel);

    //Package the detailed output edit box and the browse button in a panel so
    //they'll be side-by-side
    jTempPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.
        LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jNewFileName.setFont(new SortieFont());
    m_jNewFileName.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jNewFileName.setPreferredSize(new Dimension(300, 25));
    jTempPanel.add(m_jNewFileName);

    jButton = new JButton("Browse");
    jButton.setFont(new SortieFont());
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.addActionListener(this);
    jButton.setActionCommand("BrowseNew");
    jTempPanel.add(jButton);

    jComponentPanel.add(jTempPanel);
    
    //Put it all together
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(jComponentPanel, BorderLayout.CENTER);
    this.getContentPane().add(new OKCancelButtonPanel(this,
        m_oWindow.m_oHelpBroker, m_sHelpID, true), BorderLayout.SOUTH);

  }
  
  /**
   * Renames the file.
   * @return True if successful, false if not.
   */
  private boolean renameFile() {
    //Put together the root temp directory - the application's directory
    //plus temp
    String sSortiePath = System.getProperty("SORTIE_PATH");
    String sSeparator = System.getProperty("file.separator");
    String sTempRoot, sTempOutRoot, sRootToReplace, sNewRoot;
    String sTarballToRename = m_jDetailedOutputFileToRename.getText();
    String sNewFile = m_jNewFileName.getText();
    
    if (new File(sTarballToRename).exists() == false) {
      JOptionPane.showMessageDialog(this, "The file to rename does not exist.",
          "SORTIE", JOptionPane.ERROR_MESSAGE);
      return false;
    }    
    if (sSortiePath == null) {
      sTempRoot = sSeparator + "temp";
    }
    else {
      if (sSortiePath.endsWith(sSeparator) == false) {
        sSortiePath += sSeparator;
      }
      sTempRoot = sSortiePath + "temp";      
    }
    //Make another temp directory, to avoid name conflicts
    sTempOutRoot = sTempRoot + sSeparator + "tempytempy";
    if (!new File(sTempOutRoot).mkdirs()) {
      JOptionPane.showMessageDialog(this, "A needed directory could not be created. Check SORTIE's file permissions.",
          "SORTIE", JOptionPane.ERROR_MESSAGE);
      return false;
    }
    
    try {
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));      
      
      //Get root filename of old file, no path, no extension
      sRootToReplace = sTarballToRename.substring(sTarballToRename.lastIndexOf(sSeparator)+1);
      if (sRootToReplace.lastIndexOf(".gz.tar") > -1)
        sRootToReplace = 
          sRootToReplace.substring(0, sRootToReplace.lastIndexOf(".gz.tar"));      
      
      //Get root filename of file to create, no path, no extension
      sNewRoot = sNewFile.substring(sNewFile.lastIndexOf(sSeparator)+1);
      if (sNewRoot.lastIndexOf(".gz.tar") > -1)
        sNewRoot = sNewRoot.substring(0, 
            sNewRoot.lastIndexOf(".gz.tar")); 
      
      //Extract all the files in the tarball so we can rename each one
      String[] p_sFiles = Tarball.getTarballEntries(sTarballToRename),
      p_sNewFiles = new String[p_sFiles.length];
      Tarball.extractTarballNoPath(sTarballToRename, sTempRoot);
      String sNewSubFile;
      for (int i = 0; i < p_sFiles.length; i++) {
        //Strip separators
        p_sFiles[i] = p_sFiles[i].replace('/', File.separatorChar);
        p_sFiles[i] = p_sFiles[i].substring(p_sFiles[i].lastIndexOf(File.separatorChar) + 1);
        String sExtracted = Tarball.unzipFile(sTempRoot + sSeparator + p_sFiles[i] + ".gz");
        //Rename the file
        sNewSubFile = sExtracted.replaceAll(Matcher.quoteReplacement(sRootToReplace), 
            sNewRoot);        
        sNewSubFile = new File(sNewSubFile).getName();        
        new File(sExtracted).renameTo(new File(sTempOutRoot + sSeparator + sNewSubFile));
        //Put the new filename in our list of entries
        p_sNewFiles[i] = sTempOutRoot + sSeparator + sNewSubFile;
        new File(sExtracted).delete();       
      }
      
      if (sNewFile.endsWith(".gz.tar") == false)
        sNewFile += ".gz.tar";
      if (new File(sNewFile).getParent() == null)
        sNewFile = new File(sTarballToRename).getParent() + sSeparator + sNewFile;  
      Tarball.makeTarball(sNewFile, p_sNewFiles);
      
      //Clean up
      for (int i = 0; i < p_sNewFiles.length; i++) {
        boolean bDeleted = new File(p_sNewFiles[i] + ".gz").delete();
        if (bDeleted) sNewFile = "";
      }
      new File(sTempOutRoot).delete();
      JOptionPane.showMessageDialog(this, "File renamed.");
      
    } catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
      return false;
    } finally {
      this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
    return true;
  }

  
  /**
   * Performs the actions of the window.
   * @param oEvent What event to respond to.
   */
  public void actionPerformed(ActionEvent oEvent) {
    if (oEvent.getActionCommand().equals("OK")) {
      if (renameFile()) {
        //Close window
        this.setVisible(false);
        this.dispose();
      }
    }
    else if (oEvent.getActionCommand().equals("Cancel")) {
      //Close window
      this.setVisible(false);
      this.dispose();
    }
    else if (oEvent.getActionCommand().equals("BrowseToRename")) {
      ModelFileChooser jChooser = new ModelFileChooser();

      jChooser.setFileFilter(new DetailedOutputFileFilter());
      int returnVal = jChooser.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        //User chose a file - put the filename in the edit box
        File oFile = jChooser.getSelectedFile();
        String sFileName = oFile.getAbsolutePath();

        m_jDetailedOutputFileToRename.setText(sFileName);
      }
    }
    else if (oEvent.getActionCommand().equals("BrowseNew")) {

      ModelFileChooser jChooser = new ModelFileChooser();
      jChooser.setFileFilter(new DetailedOutputFileFilter());
      int returnVal = jChooser.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        //User chose a file - put the filename in the edit box
        File oFile = jChooser.getSelectedFile();
        String sFileName = oFile.getAbsolutePath();

        //Does this file already exist?
        if (oFile.exists()) {
          returnVal = JOptionPane.showConfirmDialog(this,
              "Do you wish to overwrite the existing file?",
              "Model",
              JOptionPane.YES_NO_OPTION);
          if (returnVal == JOptionPane.YES_OPTION) {
            m_jNewFileName.setText(sFileName);
          }
        }
        else {
          //File didn't exist
          m_jNewFileName.setText(sFileName);
        }
      }
    }
  }
}
