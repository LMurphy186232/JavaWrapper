package sortie.gui;

import java.awt.Dimension;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JFrame;

import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;


/**
 * Displays dialog for entering size class data.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>February 27, 2008: Broke out into its own class
 */
public class SizeClassEditor
    extends JDialog
    implements ActionListener {
  
  
  
  /**TreePopulation object that data changes will be communicated to*/
  protected TreePopulation m_oPop;
  
  /**List model for size classes*/
  protected DefaultListModel<Float> m_jSizeClassListModel;

  /**List displaying the size classes*/
  protected JList<Float> m_jSizeClassList;

  /**Field for entering new size class*/
  protected JTextField m_jNewClass;

  /**Help ID string*/
  private String m_sHelpID = "windows.edit_size_classes";

  /**
   * Constructor. Creates GUI.
   * @param jParent Parent dialog in which to display this one.
   * @param oPop Tree population object.
   */
  public SizeClassEditor(JFrame jParent, TreePopulation oPop) {
    super(jParent, "Edit Size Classes", true);
    
    //Help ID
    oPop.getGUIManager().getHelpBroker().enableHelpKey(this.getRootPane(), m_sHelpID, null);

    m_oPop = oPop;
    int i;

    //List of size classes
    //Create the size class list model
    m_jSizeClassListModel = new DefaultListModel<Float>();
    for (i = 0; i < m_oPop.getNumberOfSizeClasses(); i++) {
      m_jSizeClassListModel.addElement(m_oPop.getSizeClass(i));
    }
    
    m_jSizeClassList = new JList<Float>(m_jSizeClassListModel);
    m_jSizeClassList.setFont(new SortieFont());
    JScrollPane jScroller = new JScrollPane(m_jSizeClassList);
   // m_jSizeClassList.setPreferredSize(new Dimension(75, 
    //    m_jSizeClassList.getPreferredSize().height));
    jScroller.setPreferredSize(new Dimension(75, 
        jScroller.getPreferredSize().height));

    //Panel with add/remove buttons
    JPanel jButtonPanel = new JPanel();

    JLabel jNewSpeciesLabel = new JLabel("New size class upper limit DBH");
    jNewSpeciesLabel.setFont(new SortieFont());
    JLabel jNewSpeciesLabel2 = new JLabel("(0 for seedlings):");
    jNewSpeciesLabel2.setFont(new SortieFont());

    m_jNewClass = new JTextField();
    m_jNewClass.setFont(new SortieFont());

    JButton jAddButton = new JButton("Add");
    jAddButton.setFont(new SortieFont());
    jAddButton.setActionCommand("Add");
    jAddButton.addActionListener(this);

    JButton jRemoveButton = new JButton("Remove");
    jRemoveButton.setFont(new SortieFont());
    jRemoveButton.setActionCommand("Remove");
    jRemoveButton.addActionListener(this);

    jButtonPanel.setLayout(new java.awt.GridLayout(0, 1));
    jButtonPanel.add(jNewSpeciesLabel);
    jButtonPanel.add(jNewSpeciesLabel2);
    jButtonPanel.add(m_jNewClass);
    jButtonPanel.add(jAddButton);
    jButtonPanel.add(jRemoveButton);

    //Package components into panel
    JPanel jCenterPanel = new JPanel();
    jCenterPanel.setLayout(new java.awt.FlowLayout());
    jCenterPanel.add(jScroller);
    jCenterPanel.add(jButtonPanel);

    //Finish the GUI
    getContentPane().setLayout(new java.awt.BorderLayout());
    getContentPane().add(jCenterPanel, java.awt.BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this, 
        oPop.getGUIManager().getHelpBroker(), m_sHelpID),
                         java.awt.BorderLayout.PAGE_END);
  }

  /**
   * Performs a size class add in response to the Add button click.
   * @throws ModelException if the value is not a number or is negative.
   */
  protected void addClass() throws ModelException {

    //Is there anything in the class editor field?
    String sNewClass = m_jNewClass.getText();
    sNewClass = sNewClass.trim();

    if (sNewClass.length() == 0) {
      return;
    }

    //Transform to a number
    Float fNewClass = null;
    try {
      fNewClass = new Float(sNewClass);
    }
    catch (java.lang.NumberFormatException oErr) {
      throw (new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                                "Size classes must be a number."));
    }

    //Is the number negative?
    if (fNewClass.floatValue() < 0) {
      throw (new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                                "Size classes cannot be less than zero."));
    }

    //Is the new class already present?
    int i;
    for (i = 0; i < m_jSizeClassListModel.size(); i++) {
      if (fNewClass.equals( (Float) m_jSizeClassListModel.elementAt(i))) {
        return;
      }
    }

    if (m_jSizeClassListModel.size() == 0) {
      m_jSizeClassListModel.addElement(fNewClass);
      return;
    }

    //Add it in order
    for (i = 0; i < m_jSizeClassListModel.size(); i++) {
      if (fNewClass.compareTo( (Float) m_jSizeClassListModel.elementAt(i)) <
          0) {
        //This should be added just before
        m_jSizeClassListModel.add(i, fNewClass);
        return;
      }
    }

    //This belongs in the last position
    m_jSizeClassListModel.addElement(fNewClass);
  }

 
  /**
   * Responds to button clicks
   * @param oEvent Event fired by button click
   */
  public void actionPerformed(ActionEvent oEvent) {
    if (oEvent.getActionCommand().equals("Cancel")) {
      this.setVisible(false);
      this.dispose();
    }
    else if (oEvent.getActionCommand().equals("Add")) {
      try {
        addClass();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("Remove")) {
           
      //Request to remove one or more size classes
      List<Float> p_oRemove = m_jSizeClassList.getSelectedValuesList();
      if (p_oRemove == null || p_oRemove.size() == 0) {
        return; //Nothing selected
      }
      int i;
      for (i = 0; i < p_oRemove.size(); i++) {
        m_jSizeClassListModel.removeElement(p_oRemove.get(i));
      }

    }
    else if (oEvent.getActionCommand().equals("OK")) {
      try {
      
        boolean bChanged = false;
        int i;
      
        //See if there were actually any changes made - if not, we'll leave 
        //things alone and avoid pointlessly erasing any initial density data
        if (m_jSizeClassListModel.size() != m_oPop.getNumberOfSizeClasses()) {
          bChanged = true;
        } else {
          for (i = 0; i < m_jSizeClassListModel.size(); i++) {
            if (m_oPop.getSizeClass(i).compareTo( (Float) m_jSizeClassListModel.
                                                 elementAt(i)) != 0) {
              bChanged = true;
              break;
            }
          }
        }
      
        if (bChanged == true) {
          //Translate the size classes into an array of Floats
          Float[] p_fSizeClasses = new Float[m_jSizeClassListModel.size()];
          for (i = 0; i < p_fSizeClasses.length; i++) {
            p_fSizeClasses[i] = (Float) m_jSizeClassListModel.elementAt(i);
          }

          m_oPop.getTreeBehavior().setSizeClasses(p_fSizeClasses);
        }
      
        this.setVisible(false);
        this.dispose();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
  }
}
