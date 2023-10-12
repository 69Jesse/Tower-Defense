package gui;

import game.Game;
import gui.frame.Frame;


/**
 * The base painter class.
 */
public abstract class Painter {
    protected Game game;
    protected Frame frame;
    protected Panel panel;

    /**
     * The constructor.
     * 
     * @param game  The game.
     * @param frame The frame.
     * @param panel The panel.
     */
    public Painter(Game game, Frame frame, Panel panel) {
        this.game = game;
        this.frame = frame;
        this.panel = panel;
    }

    public abstract void paint(BetterGraphics graphics);
}
