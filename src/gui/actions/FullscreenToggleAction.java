package gui.actions;

import gui.frame.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * The fullscreen toggle action.
 */
public class FullscreenToggleAction extends AbstractAction {
    private Frame frame;
    private GraphicsDevice device;

    /**
     * Constructor.
     * 
     * @param frame The frame.
     */
    public FullscreenToggleAction(Frame frame) {
        this.frame = frame;
        this.device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.frame.dispose();

        if (frame.isUndecorated()) {
            this.device.setFullScreenWindow(null);
            this.frame.setUndecorated(false);
        } else {
            this.frame.setUndecorated(true);
            this.device.setFullScreenWindow(this.frame);
        }

        this.frame.setVisible(true);
    }
}
