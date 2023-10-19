package gui.mouse;

import game.Game;
import game.Option;
import gui.Panel;
import gui.frame.Frame;
import gui.painters.TowerPainter;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import location.Location;


/**
 * The mouse class.
 */
public class Mouse extends BaseMouse {
    private Game game;
    private Panel panel;

    /**
     * The constructor.
     * 
     * @param frame The frame.
     * @param game  The game.
     * @param panel The panel.
     */
    public Mouse(Frame frame, Game game, Panel panel) {
        super(frame);
        this.game = game;
        this.panel = panel;
    }

    private Location getMouseLocation(MouseEvent e) {
        Insets insets = this.frame.getInsets();
        return this.panel.correctXY(
            e.getX() - insets.left,
            e.getY() - insets.top
        );
    }

    /**
     * Return the option that was clicked, or null if none was clicked.
     * 
     * @param location      The location where the mouse was clicked.
     * @param towerLocation The location of the placeable spot/tower.
     * @return              The option that was clicked, or null if none was clicked.
     */
    private Option clickedOnOption(Location location, Location towerLocation) {
        ArrayList<Option> options = this.game.getSelectedOptions();
        for (int i = 0; i < options.size(); i++) {
            Option option = options.get(i);
            if (option.isHovering(location, towerLocation, i, options.size())) {
                return option;
            }
        }
        return null;
    }

    /**
     * Return the placeable location that the mouse is at, or null if it is not at any.
     * 
     * @param location The location where the mouse is at this moment.
     * @return         The placeable location that the mouse is at, or null if it is not at any.
     */
    public Location hoveringOverPlaceable(Location location) {
        for (Location placeable : this.game.field.placeable) {
            if (placeable.inSameSquare(location, TowerPainter.SIZE)) {
                return placeable;
            }
        }
        return null;
    }

    private void checkClick(Location location) {
        if (this.game.gameLost) {
            // It is okay to perform a click when the game has not started, but not you have lost.
            return;
        }
        if (this.game.selectedLocation != null) {
            Option option = this.clickedOnOption(location, this.game.selectedLocation);
            if (option != null) {
                option.callback(this.game.selectedLocation);
                return;
            }
        }

        Location placeable = this.hoveringOverPlaceable(location);
        if (placeable == null) {
            this.game.selectedLocation = null;
            return;
        }

        if (this.game.selectedLocation == placeable) {
            this.game.selectedLocation = null;
        } else {
            this.game.selectedLocation = placeable;
        }
    }

    @Override
    public void onAnyPressed(MouseEvent e) {
        Location location = this.getMouseLocation(e);
        this.checkClick(location);
    }
}
