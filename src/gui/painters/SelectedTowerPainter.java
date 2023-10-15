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
    private final Color optionLabelEnabledColor = new Color(0xFFFFFF);
    private final Color optionLabelDisabledColor = new Color(0xBFBFBF);
    private final Color optionBoxColor = new Color(0x000000);
    private final double optionBoxYOffset = 1.0;
    private final double optionBoxAlpha = 0.5;
    private final double optionBoxPadding = 0.25;

    /**
     * Draw the range of the tower if the tower has a range.
     * 
     * @param graphics The graphics.
     * @param tower    The tower.
     */
    private void drawRange(
        BetterGraphics graphics,
        Tower tower
    ) {
        if (!(tower instanceof RangeDamageTower)) {
            return;
        }
        RangeDamageTower casted = (RangeDamageTower) tower;
        Location towerLocation = tower.getLocation();
        graphics.setColor(this.rangeColor);
        graphics.setLineWidth(this.rangeLineWidth);
        graphics.drawOval(
            towerLocation.x,
            towerLocation.y,
            casted.getRange()
        );
    }

    /**
     * Draw the option label.
     * 
     * @param graphics      The graphics.
     * @param option        The option.
     * @param towerLocation The location of the placeable spot/tower.
     * @param mouseLocation The mouse location.
     * @param enabled       Whether the option is enabled.
     */
    private void drawOptionLabel(
        BetterGraphics graphics,
        Option option,
        Location towerLocation,
        Location mouseLocation,
        boolean enabled
    ) {
        graphics.setFont(1.0);
        Color color = enabled
            ? this.optionLabelEnabledColor
            : this.optionLabelDisabledColor;
        graphics.setColor(color);
        graphics.drawStringCenteredWithBox(
            option.getLabel(towerLocation),
            mouseLocation.x,
            mouseLocation.y - this.optionBoxYOffset,
            graphics.new Box(
                this.optionBoxColor,
                this.optionBoxAlpha,
                this.optionBoxPadding
            )
        );
    }

    /**
     * Draw the options.
     * 
     * @param graphics The graphics.
     * @param location The location of the placeable spot/tower.
     * @param tower    The tower.
     */
    private void drawOptions(
        BetterGraphics graphics,
        Location location,
        Tower tower
    ) {
        Location mouseLocation = this.panel.getMouseLocation();
        Option hoveringOption = null;
        boolean hoveringEnabled = false;

        ArrayList<Option> options = this.game.getSelectedOptions();
        for (int i = 0; i < options.size(); i++) {
            Option option = options.get(i);
            Location optionLocation = option.getLocation(location, i, options.size());
            boolean enabled = option.shouldBeEnabled(location);
            if (enabled) {
                graphics.setTransparency(1.0);
            } else {
                graphics.setTransparency(this.disabledTransparency);
            }
            graphics.drawImageCentered(
                option.getImagePath(),
                optionLocation.x,
                optionLocation.y,
                Option.RADIUS * 2,
                Option.RADIUS * 2
            );

            if (
                mouseLocation != null
                    && option.isHovering(mouseLocation, location, i, options.size())
            ) {
                hoveringOption = option;
                hoveringEnabled = enabled;
            }
        }

        if (hoveringOption != null) {
            graphics.setTransparency(1.0);
            this.drawOptionLabel(
                graphics,
                hoveringOption,
                location,
                mouseLocation,
                hoveringEnabled
            );
        }
    }

    @Override
    public void paint(BetterGraphics graphics) {
        Location location = this.game.selectedLocation;
        Tower tower = this.game.field.towers.getOrDefault(location, null);
        this.drawRange(graphics, tower);
        this.drawOptions(graphics, location, tower);
    }
}
