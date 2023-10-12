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

    Color pathColor1 = new Color(0xC0971B);
    Color pathColor2 = new Color(0xDBB12C);

    @Override
    public void paint(BetterGraphics graphics) {
        for (int i = 0; i < 2; i++) {
            double radius = Math.max(
                this.game.field.width, this.game.field.height
            ) * (
                i == 0 ? 0.02 : 0.015
            );
            graphics.setColor(i == 0 ? this.pathColor1 : this.pathColor2);
            for (Location location : this.game.field.path) {
                graphics.fillOval(
                    location.x - radius,
                    location.y - radius,
                    2 * radius,
                    2 * radius
                );
            }
        }
    }
}
