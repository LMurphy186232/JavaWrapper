package sortie.gui.components;

import javax.swing.JTextArea;
import javax.swing.UIManager;



/**
 * Renders a multi-line "JLabel". I got this code from
 * a now-defunct website.
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */
public class MultilineLabel
    extends JTextArea {
  
  
  
  public MultilineLabel() {
    initialize();
  }

  /**
   * Constructor.
   * @param s Text for the label.
   */
  public MultilineLabel(String s) {
    super(s);
    initialize();
  }

  /**
   * Formats the label.
   */
  public void initialize() {
    // turn on wrapping and disable editing and highlighter
    setLineWrap(true);
    setWrapStyleWord(true);
    setHighlighter(null);
    setEditable(false);

    // make the text area look like a label
    setBackground(UIManager.getColor("Label.background"));
    setForeground(UIManager.getColor("Label.foreground"));
    setFont(UIManager.getFont("Label.font"));
    setBorder(UIManager.getBorder("Label.border"));
    setFont(new SortieFont());
  }
}
