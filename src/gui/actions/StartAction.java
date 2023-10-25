package gui.actions;

import game.Game;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;


/**
 * The start action.
 */
public class StartAction extends AbstractAction {
    private Game game;

    /**
     * Constructor.
     * 
     * @param game  The game.
     */
    public StartAction(Game game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean gameEnded = this.game.hasEnded();
        if (this.game.hasStarted() && !gameEnded) {
            return;
        } else if (gameEnded) {
            this.game.reset();
            return;
        }
        this.game.start();    
    }
}
