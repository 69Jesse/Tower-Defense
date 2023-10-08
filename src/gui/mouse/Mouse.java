package gui.mouse;

import game.Game;
import gui.frame.Frame;


/**
 * The mouse class.
 */
public class Mouse extends BaseMouse {
    private Frame frame;
    private Game game;

    /**
     * The constructor.
     * 
     * @param frame The frame.
     * @param game  The game.
     */
    public Mouse(Frame frame, Game game) {
        super(frame);
        this.game = game;
    }
}
