package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import java.awt.Color;
import location.Location;
import towers.Tower;


/**
 * The background painter class.
 */
public class TowerPainter extends Painter {
    /**
     * The constructor.
     */
    public TowerPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    private final Color unplacedTowerColor = new Color(0x7F7F7F);
    public static final int RADIUS = 2;

    @Override
    public void paint(BetterGraphics graphics) {
        for (Location location : this.game.field.placeable) {
            Tower tower = this.game.field.towers.getOrDefault(location, null);
            if (tower == null) {
                graphics.setColor(this.unplacedTowerColor);
                graphics.fillOval(
                    location.x, location.y, RADIUS
                );
            }
        }
    }
}
