package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import java.awt.Color;
import location.Location;
import towers.RangeDamageTower;
import towers.Tower;


/**
 * The selected tower painter class.
 */
public final class SelectedTowerPainter extends Painter {
    /**
     * The constructor.
     */
    public SelectedTowerPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    private final Color rangeColor = new Color(0x000000);
    private final double rangeLineWidth = 0.25;

    @Override
    public void paint(BetterGraphics graphics) {
        Location location = this.game.selectedLocation;
        Tower tower = this.game.field.towers.getOrDefault(location, null);
        if (tower == null) {
            return;
        }

        if (tower instanceof RangeDamageTower) {
            RangeDamageTower casted = (RangeDamageTower) tower;
            graphics.setColor(this.rangeColor);
            graphics.setLineWidth(this.rangeLineWidth);
            graphics.drawOval(
                location.x,
                location.y,
                casted.range
            );
        }
    }
}
