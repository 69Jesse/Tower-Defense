package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Frame;
import gui.Painter;
import gui.Panel;
import java.awt.Color;


/**
 * The background painter class.
 */
public final class BackgroundPainter extends Painter {
    /**
     * The constructor.
     */
    public BackgroundPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    public static final Color BACKGROUND_COLOR = new Color(0x000000);

    @Override
    public void paint(BetterGraphics graphics) {
        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillEntireScreen();
    }
}
