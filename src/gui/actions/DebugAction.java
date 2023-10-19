package gui.actions;

import game.Game;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;


/**
 * The debug action.
 */
public class DebugAction extends AbstractAction {
    private Game game;

    /**
     * Constructor.
     * 
     * @param game  The game.
     */
    public DebugAction(Game game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.game.reset();    
    }
}
