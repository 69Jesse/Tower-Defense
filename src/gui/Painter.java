package gui;

import game.Game;


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

    /**
     * Method to paint whatever this painter is supposed to paint.
     * 
     * @param graphics The graphics object.
     */
    public abstract void paint(BetterGraphics graphics);
}
