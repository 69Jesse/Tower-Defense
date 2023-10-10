package gui.panel.painters;

import game.Game;
import gui.frame.Frame;
import gui.panel.Painter;
import gui.panel.Panel;
import java.awt.Color;
import java.awt.Graphics;


/**
 * The background painter class.
 */
public class BackgroundPainter extends Painter {
    /**
     * The constructor.
     */
    public BackgroundPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    private Color backgroundColor = new Color(0x000000);

    @Override
    public void paint(Graphics g) {
        g.setColor(this.backgroundColor);
        g.fillRect(0, 0, this.frame.getWidth(), this.frame.getHeight());
    }
}