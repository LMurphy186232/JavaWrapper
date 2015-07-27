package sortie.gui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.awt.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

import sortie.data.simpletypes.ModelException;
import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;
import sortie.gui.components.XMLFileFilter;
import sortie.sax.SaxParseTools;


/**
* Window for working with batch files.
* <p>Copyright: Copyright (c) Charles D. Canham 2005</p>
* <p>Company: Cary Institute of Ecosystem Studies</p>
* @author Lora E. Murphy
* @version 1.0
* <br>Edit history:
* <br>------------------
* <br>March 31, 2005: Created (LEM)
* <br>May 26, 2006:  Improved - changed some things that annoyed me (LEM)
* <br>April 5, 2007: Support for multi-file select (LEM)
* <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
*/
public class BatchSetup
    extends JDialog
    implements ActionListener {
  
  

  /**GUI Manager*/
  private GUIManager m_oManager;

  /**List model of parameter files currently added to file*/
  private DefaultListModel<BatchParFile> m_jParFilesListModel = new DefaultListModel<BatchParFile>();

  /**List of parameter files currently added to file*/
  private JList<BatchParFile> m_jParFilesList = new JList<BatchParFile>(m_jParFilesListModel);

  /**Text field holding a proposed parameter file path and name*/
  private JTextField m_jParFileEdit = new JTextField();

  /**Text field holding the batch file path and name*/
  private JTextField m_jBatchFileEdit = new JTextField();

  /**Holds number of times to run a parameter file*/
  private JTextField m_jNumTimesToRun = new JTextField();

  /**Help topic ID*/
  private String m_sHelpID = "windows.batch_setup_window";

  /**
   * Constructor.  Creates the window and displays it.
   * @param jParent JFrame Parent frame for this dialog.
   * @param oManager GUIManager object.
   */
  public BatchSetup(JFrame jParent, GUIManager oManager) {
    super(jParent, "Batch File Setup", true);

    m_oManager = oManager;

    //Help ID
    oManager.getHelpBroker().enableHelpKey(this.getRootPane(), m_sHelpID, null);

    /**********************
     * Create GUI
     *********************/

    /**********************
     * Top portion:  Place to enter batch file name
     *********************/
    JPanel jBatchFileNamePanel = new JPanel();
    jBatchFileNamePanel.setLayout(new BoxLayout(jBatchFileNamePanel,
                                                BoxLayout.PAGE_AXIS));
    jBatchFileNamePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    //Label for batch file name
    JLabel jLabel = new JLabel("Batch file name:");
    jLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    JPanel jTempPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.
        LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jTempPanel.add(jLabel);
    jBatchFileNamePanel.add(jTempPanel);

    //Panel with a file name and browse button for the new batch file
    //Package the filename edit box and the browse button in a panel so
    //they'll be side-by-side
    jTempPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    m_jBatchFileEdit.setFont(new SortieFont());
    m_jBatchFileEdit.setPreferredSize(new java.awt.Dimension(300, 25));

    JButton jButton = new JButton("Browse");
    jButton.setFont(new SortieFont());
    jButton.addActionListener(this);
    jButton.setActionCommand("BrowseBatch");

    jTempPanel.add(m_jBatchFileEdit);
    jTempPanel.add(jButton);
    jBatchFileNamePanel.add(jTempPanel);
    jBatchFileNamePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
        java.awt.Color.black));

    /**********************
     * Middle portion:  Place to add new parameter files
     *********************/
    JPanel jNewParFilePanel = new JPanel();
    jNewParFilePanel.setLayout(new BoxLayout(jNewParFilePanel,
                                             BoxLayout.PAGE_AXIS));
    jNewParFilePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    //Label for adding a new parameter file
    jLabel = new JLabel("Add a new parameter file");
    jLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jTempPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jTempPanel.add(jLabel);
    jTempPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
    jNewParFilePanel.add(jTempPanel);

    //Label for entering a new parameter file
    jLabel = new JLabel("Parameter file name:");
    jLabel.setFont(new SortieFont());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jTempPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jTempPanel.add(jLabel);
    jNewParFilePanel.add(jTempPanel);

    //Package the filename edit box and the browse button in a panel so
    //they'll be side-by-side
    jTempPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jParFileEdit.setFont(new SortieFont());
    m_jParFileEdit.setPreferredSize(new java.awt.Dimension(300, 25));
    m_jParFileEdit.setAlignmentX(Component.LEFT_ALIGNMENT);

    jButton = new JButton("Browse");
    jButton.setFont(new SortieFont());
    jButton.addActionListener(this);
    jButton.setActionCommand("BrowseParFile");
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);

    jTempPanel.add(m_jParFileEdit);
    jTempPanel.add(jButton);
    jTempPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
    jNewParFilePanel.add(jTempPanel);

    //Label for number of times to run
    jTempPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel = new JLabel("Number of times to run this file:");
    jLabel.setFont(new SortieFont());
    jTempPanel.add(jLabel);

    //Number of times to run
    m_jNumTimesToRun.setFont(new SortieFont());
    m_jNumTimesToRun.setPreferredSize(new java.awt.Dimension(50, 25));
    jTempPanel.add(m_jNumTimesToRun);
    jTempPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

    jNewParFilePanel.add(jTempPanel);

    //Add a button for adding a new parameter file combo
    jButton = new JButton("Add new parameter file");
    jButton.setFont(new SortieFont());
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.addActionListener(this);
    jButton.setActionCommand("Add");
    jTempPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jTempPanel.add(jButton);
    jNewParFilePanel.add(jTempPanel);

    jNewParFilePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
        java.awt.Color.black));

    /**********************
     * Panel displaying the current list of parameter files
     *********************/
    JPanel jParFileListPanel = new JPanel();
    jParFileListPanel.setLayout(new BoxLayout(jParFileListPanel,
                                              BoxLayout.PAGE_AXIS));
    jParFileListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jParFileListPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
        java.awt.Color.black));

    //Label to identify the list of current files
    jTempPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel = new JLabel("Current parameter files in this batch:");
    jLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jTempPanel.add(jLabel);
    jParFileListPanel.add(jTempPanel);

    //List of current files
    jTempPanel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jParFilesList.setFont(new SortieFont());
    JScrollPane jScroller = new JScrollPane(m_jParFilesList);
    jScroller.getViewport().setPreferredSize(new java.awt.Dimension(300,
        (int) jScroller.getViewport().getPreferredSize().getHeight()));
    jTempPanel.add(jScroller);

    //Button for removing a parameter file
    jButton = new JButton("Remove");
    jButton.setFont(new SortieFont());
    jButton.addActionListener(this);
    jButton.setActionCommand("Remove");
    jTempPanel.add(jButton);

    jParFileListPanel.add(jTempPanel);

    /**********************
     * Panel packaging together all controls
     *********************/
    JPanel jContentPanel = new JPanel();
    jContentPanel.setLayout(new BoxLayout(jContentPanel, BoxLayout.PAGE_AXIS));
    jContentPanel.add(jBatchFileNamePanel);
    jContentPanel.add(jNewParFilePanel);
    jContentPanel.add(jParFileListPanel);
    jContentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    this.getContentPane().setLayout(new java.awt.BorderLayout());
    this.getContentPane().add(jContentPanel, java.awt.BorderLayout.CENTER);
    this.getContentPane().add(new OKCancelButtonPanel(this,
        oManager.getHelpBroker(), m_sHelpID), java.awt.BorderLayout.SOUTH);

  }

  /**
   * Constructor.  This reads an existing batch file in so it can be edited.
   * @param jParent JFrame Parent frame for this dialog.
   * @param oManager GUIManager object.
   * @param sBatchFile String Batch filename.
   * @throws ModelException Wrapping parse exceptions.
   */
  public BatchSetup(JFrame jParent, GUIManager oManager, String sBatchFile) throws ModelException {

    //Let the other constructor do setup
    this(jParent, oManager);

    //Build a parser and parse the file
    java.io.FileInputStream oFileStream = null;
    try {
      //Create our parser
      //Create the parser this way - don't go through the SAXParserFactory!
      //Parsers created that way throw MalformedURLExceptions when asked to
      //read local files.
      org.xml.sax.XMLReader oParser = org.xml.sax.helpers.XMLReaderFactory.createXMLReader(
          "org.apache.xerces.parsers.SAXParser");
      //Create the handler that looks for setup data and register it
      DefaultHandler oHandler = new BatchFileParser(this);
      oParser.setContentHandler(oHandler);
      //Parse the file
      //Create a FileInputStream object to wrap in an InputSource object.
      //This method allows characters with diacritical marks in the filename,
      //whereas simply feeding the filename to InputSource directly does not
      oFileStream = new java.io.FileInputStream(sBatchFile);
      org.xml.sax.InputSource oToParse = new org.xml.sax.InputSource(oFileStream);
      oParser.parse(oToParse);

      //Put the filename in the batch filename spot
      m_jBatchFileEdit.setText(sBatchFile);
    }
    catch (SAXException oE) {
      throw(new ModelException(ErrorGUI.BAD_XML_FILE, "JAVA", oE.getMessage()));
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE,
                               "InputXMLParameterFile - JAVA", sBatchFile));
    }
    finally {
      try {
        if (oFileStream != null) {
          oFileStream.close();
        }
      }
      catch (java.io.IOException oErr) {
        ; //do nothing
      }
    }
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
    }
    if (sCommand.equals("OK")) {

      //Make sure there's a batch filename
      if (m_jBatchFileEdit.getText().length() == 0) {
        JOptionPane.showMessageDialog(this, "Please specify a batch filename.",
                                      "SORTIE-ND", JOptionPane.ERROR_MESSAGE);
        return;
      }

      //Make sure all the files are good
      int i, iNumFiles = m_jParFilesListModel.size();

      if (iNumFiles == 0) {
        JOptionPane.showMessageDialog(this,
                                      "You must add some parameter files to the batch.",
                                      "SORTIE-ND",
                                      JOptionPane.ERROR_MESSAGE);
        return;
      }

      BatchParFile oParFile;
      for (i = 0; i < iNumFiles; i++) {
        oParFile = (BatchParFile) m_jParFilesListModel.elementAt(i);
        File oFile = new File(oParFile.getParFile());
        if (oFile.exists() == false) {
          JOptionPane.showMessageDialog(this,
                                        "I cannot find the parameter file \n\"" +
                                        oParFile.getParFile() + "\".",
                                        "SORTIE-ND", JOptionPane.ERROR_MESSAGE);
          return;
        }
      }

      try {
        writeFile();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
      this.setVisible(false);
      this.dispose();
    }
    else if (sCommand.equals("BrowseParFile")) {
      ModelFileChooser jChooser = new ModelFileChooser();
      jChooser.setFileFilter(new XMLFileFilter());
      jChooser.setMultiSelectionEnabled(true);
      int returnVal = jChooser.showOpenDialog(this), i;
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        //User chose one or more file - put the filename in the edit box
        File[] p_oFile = jChooser.getSelectedFiles();
        for (i = 0; i < p_oFile.length; i++) {
          if (false == p_oFile[i].exists()) {
            JOptionPane.showMessageDialog(this,
                                          "I could not find the parameter file \"" +
                                          p_oFile[i].getName() + "\".",
                                          "SORTIE-ND",
                                          JOptionPane.ERROR_MESSAGE);
            return;
          }
        }

        String sFileName = p_oFile[0].getAbsolutePath();
        for (i = 1; i < p_oFile.length; i++) {
          sFileName += ";";
          sFileName += p_oFile[i].getAbsolutePath();
        }
        m_jParFileEdit.setText(sFileName);
       }
    }
    else if (sCommand.equals("BrowseBatch")) {
      ModelFileChooser jChooser = new ModelFileChooser();

      //If there's a value for the batch file, navigate to that directory
      File oDir = new File(m_jBatchFileEdit.getText());
      if (oDir.getParentFile() != null && oDir.getParentFile().exists()) {
        jChooser.setCurrentDirectory(oDir.getParentFile());
      }

      jChooser.setSelectedFile(new File(oDir.getName()));
      jChooser.setFileFilter(new XMLFileFilter());
      int returnVal = jChooser.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        //User chose a file - put the filename in the edit box
        File oFile = jChooser.getSelectedFile();
        String sFileName = oFile.getAbsolutePath();

        //Does this file already exist?
        if (oFile.exists()) {
          returnVal = JOptionPane.showConfirmDialog(this,
              "Do you wish to overwrite the existing file?",
              "SORTIE-ND",
              JOptionPane.YES_NO_OPTION);
          if (returnVal == JOptionPane.YES_OPTION) {
            //oFile.delete();
            m_jBatchFileEdit.setText(sFileName);
          }
        }
        else {
          //File didn't exist
          m_jBatchFileEdit.setText(sFileName);
        }
      }
    }
    else if (sCommand.equals("Add")) {
      addFile();
    }
    else if (sCommand.equals("Remove")) {

      List<BatchParFile> p_oSelected; //selected options from parameter file list
      int i, j;

      p_oSelected = m_jParFilesList.getSelectedValuesList();
      //No parameter files selected?  Warn user
      if (p_oSelected == null || p_oSelected.size() == 0) {
        JOptionPane.showMessageDialog(this, "Please select a parameter file.");
        return;
      }

      //Remove the selected parameter files from the list
      for (i = 0; i < p_oSelected.size(); i++) {
        String sItem = p_oSelected.get(i).toString();
        for (j = 0; j < m_jParFilesListModel.size(); j++) {
          if (sItem.equals(m_jParFilesListModel.elementAt(j).toString())) {
            m_jParFilesListModel.removeElementAt(j);
            break;
          }
        }
      }
    }
  }

  /**
   * Adds a parameter file and number of times to the batch list.  This alerts
   * the user if anything's missing or the data cannot be understood.
   */
  private void addFile() {
    //Make sure there's something in the parameter file area - if not
    //warn user
    String sParFile = m_jParFileEdit.getText();
    if (sParFile.length() == 0) {
      JOptionPane.showMessageDialog(this,
                                    "Please enter a parameter file name.",
                                    "SORTIE-ND", JOptionPane.ERROR_MESSAGE);
      return;
    }

    //Make sure that there's a proper number in the number of times to run
    //field
    int iNumTimesToRun;
    try {
      iNumTimesToRun = new Integer(m_jNumTimesToRun.getText()).intValue();
    }
    catch (java.lang.NumberFormatException oErr) {
      JOptionPane.showMessageDialog(this,
                                    "I could not understand the number\n of times to run this parameter file.",
                                    "SORTIE-ND", JOptionPane.ERROR_MESSAGE);
      return;
    }

    //Make sure the number is greater than 0
    if (iNumTimesToRun <= 0) {
      JOptionPane.showMessageDialog(this,
                                    "The number of times to run must be greater than 0.",
                                    "SORTIE-ND", JOptionPane.ERROR_MESSAGE);
      return;
    }

    //Add the new entry(ies)
    int iPos = sParFile.indexOf(";");
    while (iPos > -1) {
      m_jParFilesListModel.addElement(new BatchParFile(sParFile.substring(0, iPos), iNumTimesToRun));
      sParFile = sParFile.substring(iPos+1);
      iPos = sParFile.indexOf(";");
    }
    m_jParFilesListModel.addElement(new BatchParFile(sParFile, iNumTimesToRun));
  }

  /**
   * Writes the batch file.
   * @throws ModelException wrapping an IOException.
   */
  private void writeFile() throws ModelException {
    try {
      String sBatchFile = m_jBatchFileEdit.getText();

      //Make sure the xml extension is on the filename
      if (sBatchFile.endsWith(".xml") == false) {
        sBatchFile = sBatchFile + ".xml";
      }

      java.io.FileWriter oOut = new java.io.FileWriter(sBatchFile);
      BatchParFile oParFile;

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      oOut.write("<batchFile fileCode=\"06010401\">");
      for (int i = 0; i < m_jParFilesListModel.size(); i++) {
        oParFile = (BatchParFile) m_jParFilesListModel.elementAt(i);
        oOut.write("<ba_parFile>");
        oOut.write("<ba_fileName>" + oParFile.getParFile() + "</ba_fileName>");
        oOut.write("<ba_numTimesToRun>" +
                   String.valueOf(oParFile.getNumberOfTimesToRun()) +
                   "</ba_numTimesToRun>");
        oOut.write("</ba_parFile>");
      }
      oOut.write("</batchFile>");
      oOut.close();
      m_oManager.getMainWindow().m_jMessagesField.setText(
          "Successfully wrote batch file \"" + sBatchFile + "\".");

    }
    catch (java.io.IOException oErr) {
      throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA",
                                "I could not write the batch file.\n It may be in use by another program."));
    }
  }

  /**
   * Adds a new parameter file to the list for this batch.
   * @param sParFileName String Filename of new parameter file.
   * @param iNumTimesToRun int Number of times to run the file.
   */
  public void addParFile(String sParFileName, int iNumTimesToRun) {
    m_jParFilesListModel.addElement(new BatchParFile(sParFileName,
        iNumTimesToRun));
  }
}

/**
 * Encapsulates a parameter file and the number of times to run it.
 * <p>Copyright: Copyright (c) Charles D. Canham 2005</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
class BatchParFile {

  /**Path and filename of parameter file*/
  private String m_sParFilePath;

  /**Number of times to run this file in the batch*/
  private int m_iNumTimesToRun;

  /**
   * Constructor.
   * @param sParFile String Parameter file name and path.
   * @param iNumTimesToRun int Number of times to run this file.
   */
  BatchParFile(String sParFile, int iNumTimesToRun) {
    m_sParFilePath = sParFile;
    m_iNumTimesToRun = iNumTimesToRun;
  }

  /**
   * Gets the parameter file path and file name.
   * @return String Parameter file path and file name.
   */
  public String getParFile() {
    return m_sParFilePath;
  }

  /**
   * Gets the number of times to run this parameter file.
   * @return int Number of times to run this parameter file.
   */
  public int getNumberOfTimesToRun() {
    return m_iNumTimesToRun;
  }

  /**
   * How this class will display.  This is the file name (no path) with
   * the number of times to run as well.
   * @return String The parameter file and number of times to run it.
   */
  public String toString() {
    String sSeparator = System.getProperty("file.separator");
    return m_sParFilePath.substring(m_sParFilePath.lastIndexOf(sSeparator) + 1) +
        " - " + String.valueOf(m_iNumTimesToRun);
  }
}

/**
 * SAX batch file parse handler.
 *
 * <p>Copyright: Copyright (c) Charles D. Canham 2005</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>March 31, 2005: Created (LEM)
 *
 */

class BatchFileParser
    extends DefaultHandler {

  /**Object to transfer the collected parameter files to*/
  private BatchSetup m_oBatchSetup;

  /**String buffer to collect data in our parser*/
  private StringBuffer m_sBuf = new StringBuffer();

  /**Parameter file value*/
  private String m_sParFile;

  /**Number of times to run a parameter file*/
  private int m_iNumTimesToRun;

  /**
   * Constructor.
   * @param oBatchSetup BatchSetup object to transfer parameter files to.
   */
  public BatchFileParser(BatchSetup oBatchSetup) {
    m_oBatchSetup = oBatchSetup;
  }

  /**
   * Function called when an opening tag is encountered.  This initializes our
   * StringBuffer to get it ready to hold the tag's character data.
   * @param sURI the Namespace URI (ignored)
   * @param sLocalName the local name (what this function looks at)
   * @param sQName the qualified (prefixed) name (ignored)
   * @param oAttributes The tag's attributes
   * @throws SAXException if there are any problems.
   */
  public void startElement(java.lang.String sURI,
                           java.lang.String sLocalName,
                           java.lang.String sQName,
                           Attributes oAttributes) throws SAXException {

    //Initialize our string buffer if necessary
    if (m_sBuf.length() > 0) {
      m_sBuf = null;
      m_sBuf = new StringBuffer();
    }
  }

  /**
   * Reads character data from the XML file.  The data is appended to the
   * string buffer.  This is done because, according to the SAX parser specs,
   * it is free to call this function multiple times per tag if it wants.  So
   * this function collects the data into a single buffer.
   * @param p_cCh The characters from the XML document.
   * @param iStart - The start position in the array.
   * @param iLength - The number of characters to read from the array.
   * @throws SAXException if any of the described cases above is true.
   */
  public void characters(char[] p_cCh,
                         int iStart,
                         int iLength) throws SAXException {

    //Trim the string of both characters that don't matter to XML and of
    //spaces
    String sNewData = SaxParseTools.XMLTrim(new String(p_cCh, iStart, iLength));
    //If there is already data in the buffer, append the new string
    if (m_sBuf.length() > 0) {
      m_sBuf.append(sNewData);
    }
    else {

      //Try trimming the string of spaces - if there's nothing left, exit;
      //otherwise, append the untrimmed string
      if (sNewData.trim().length() > 0) {
        m_sBuf.append(sNewData);
      }
    }
  }

  /**
   * Called at the end of an XML tag.  If this is a parameter file name tag,
   * this stashes the value in m_sBuf into m_sParFile.  If this is a number-of-
   * times-to-run tag, this converts the value in m_sBuf into an integer and
   * stashes it in m_iNumTimesToRun.  If this is the end of a parameter file
   * couplet, then this passes the accumulated data on to the BatchSetup
   * object.
   * @param sURI the Namespace URI (ignored)
   * @param sLocalName the local name (what this function looks at)
   * @param sQName the qualified (prefixed) name (ignored)
   * @throws SAXException if there were problems assigning the data.
   */
  public void endElement(java.lang.String sURI,
                         java.lang.String sLocalName,
                         java.lang.String sQName) throws SAXException {

    if (sLocalName.equals("ba_fileName")) {
      m_sParFile = m_sBuf.toString();
    }
    else if (sLocalName.equals("ba_numTimesToRun")) {
      m_iNumTimesToRun = new Integer(m_sBuf.toString()).intValue();
    }
    else if (sLocalName.equals("ba_parFile")) {
      m_oBatchSetup.addParFile(m_sParFile, m_iNumTimesToRun);
    }

  }
}
