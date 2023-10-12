package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import java.awt.Color;


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

    public static Color backgroundColor = new Color(0x000000);

    @Override
    public void paint(BetterGraphics graphics) {
        graphics.setColor(backgroundColor);
        graphics.fillEntireScreen();
    }
}
