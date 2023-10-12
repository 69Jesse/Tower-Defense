package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import java.awt.Color;
import location.Location;


/**
 * The grass and path painter class.
 */
public class PathPainter extends Painter {
    /**
     * The constructor.
     */
    public PathPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    private final Color pathColor1 = new Color(0xC0971B);
    private final Color pathColor2 = new Color(0xDBB12C);
    private final double radius1 = 1.5;
    private final double radius2 = 1.2;

    @Override
    public void paint(BetterGraphics graphics) {
        for (int i = 0; i < 2; i++) {
            final double radius = i == 0 ? this.radius1 : this.radius2;
            graphics.setColor(i == 0 ? this.pathColor1 : this.pathColor2);
            for (Location location : this.game.field.path) {
                graphics.fillOval(
                    location.x,
                    location.y,
                    radius
                );
            }
        }
    }
}
