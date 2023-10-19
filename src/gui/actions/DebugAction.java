package gui.actions;

import game.Game;
import gui.frame.Frame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;


/**
 * The debug toggle action.
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
        if (this.game.gameStarted) {
            this.game.reset();
        } else {
            this.game.start();
        }
        
    }
}
