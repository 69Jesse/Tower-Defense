package gui.panel.painters;

import game.Game;
import gui.frame.Frame;
import gui.panel.Painter;
import gui.panel.Panel;
import java.awt.Color;
import java.awt.Graphics;
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
    public void paint(Graphics g) {
        for (int i = 0; i < 2; i++) {
            double radius = Math.max(
                this.game.field.width, this.game.field.height
            ) * (
                i == 0 ? 0.02 : 0.015
            ) * this.panel.scale;
            g.setColor(i == 0 ? this.pathColor1 : this.pathColor2);
            for (Location location : this.game.field.path) {
                g.fillOval(
                    (int) (location.x * this.panel.scale - radius + this.panel.topLeft.width),
                    (int) (location.y * this.panel.scale - radius + this.panel.topLeft.height),
                    (int) (2 * radius),
                    (int) (2 * radius)
                );
            }
        }
    }
}
