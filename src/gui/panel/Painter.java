package gui.panel;

import game.Game;
import gui.frame.Frame;
import java.awt.Graphics;


/**
 * The base painter class.
 */
public abstract class Painter {
    protected Game game;
    protected Frame frame;
    protected Panel panel;

    /**
     * The constructor.
     */
    public Painter(Game game, Frame frame, Panel panel) {
        this.game = game;
        this.frame = frame;
        this.panel = panel;
    }

    public abstract void paint(Graphics g);
}
