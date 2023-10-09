package gui.panels;

import gui.frame.BaseFrame;
import javax.swing.JPanel;


/**
 * The base panel class.
 */
public class BasePanel extends JPanel {
    protected BaseFrame frame;

    /**
     * The constructor.
     * 
     * @param frame The frame.
     */
    public BasePanel(BaseFrame frame) {
        super();
        this.frame = frame;
    }
}
