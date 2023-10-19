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
        if (this.game.gameStarted && !this.game.gameLost) {
            return;
        } else if (this.game.gameLost) {
            this.game.reset();
            this.game.gameStarted = false;
            return;
        }
        this.game.start();    
    }
}
