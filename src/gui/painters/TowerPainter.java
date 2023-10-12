package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import java.awt.Image;
import location.Location;
import towers.RangeDamageTower;
import towers.Tower;
import java.awt.Color;


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

    public static final int SIZE = 4;

    @Override
    public void paint(BetterGraphics graphics) {
        for (Location location : this.game.field.placeable) {
            Tower tower = this.game.field.towers.getOrDefault(location, null);
            Image image = tower == null ? Tower.UNPLACED_IMAGE : tower.image;
            graphics.drawImageMiddle(
                image,
                location.x,
                location.y,
                SIZE,
                SIZE
            );
            System.out.println(this.game.selectedLocation);
            if (this.game.selectedLocation == location) {
                this.drawSelectedTower(graphics, location, tower);
            }
        }
    }

    private final Color rangeColor = new Color(0x000000);

    private void drawSelectedTower(BetterGraphics graphics, Location location, Tower tower) {
        if (tower instanceof RangeDamageTower) {
            RangeDamageTower casted = (RangeDamageTower) tower;
            graphics.setColor(this.rangeColor);
            graphics.setLineWidth(0.5);
            graphics.drawOval(
                location.x,
                location.y,
                casted.range * 2
            );
            System.out.println("Drawing range");
        }
    }
}
