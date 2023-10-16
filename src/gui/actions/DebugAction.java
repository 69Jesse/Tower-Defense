package gui.actions;

import game.Game;
import gui.frame.Frame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;


/**
 * The fullscreen toggle action.
 */
public class DebugAction extends AbstractAction {
    // private Frame frame;
    private Game game;

    /**
     * Constructor.
     * 
     * @param frame The frame.
     */
    public DebugAction(Frame frame, Game game) {
        // this.frame = frame;
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.game.reset();
    }
}
