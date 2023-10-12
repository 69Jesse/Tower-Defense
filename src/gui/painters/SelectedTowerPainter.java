package gui.painters;

import game.Game;
import game.Option;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import java.awt.Color;
import java.util.ArrayList;
import location.Location;
import towers.RangeDamageTower;
import towers.Tower;


/**
 * The selected tower painter class.
 */
public final class SelectedTowerPainter extends Painter {
    /**
     * The constructor.
     * 
     * @param game  The game.
     * @param frame The frame.
     * @param panel The panel.
     */
    public SelectedTowerPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    private final Color rangeColor = new Color(0x000000);
    private final double rangeLineWidth = 0.25;
    private final double disabledTransparency = 0.5;

    @Override
    public void paint(BetterGraphics graphics) {
        Location location = this.game.selectedLocation;
        Tower tower = this.game.field.towers.getOrDefault(location, null);

        if (tower instanceof RangeDamageTower) {
            RangeDamageTower casted = (RangeDamageTower) tower;
            graphics.setColor(this.rangeColor);
            graphics.setLineWidth(this.rangeLineWidth);
            graphics.drawOval(
                location.x,
                location.y,
                casted.getRange()
            );
        }

        ArrayList<Option> options = this.game.getSelectedOptions();
        for (int i = 0; i < options.size(); i++) {
            Option option = options.get(i);
            Location optionLocation = option.getLocation(location, i, options.size());
            if (option.shouldBeEnabled(location)) {
                graphics.setTransparency(1.0);
            } else {
                graphics.setTransparency(this.disabledTransparency);
            }
            graphics.drawImageMiddle(
                option.getImage(),
                optionLocation.x,
                optionLocation.y,
                Option.SIZE,
                Option.SIZE
            );
        }
        graphics.setTransparency(1.0);
    }
}
