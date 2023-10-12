package gui.mouse;

import game.Game;
import gui.Panel;
import gui.frame.Frame;
import gui.painters.TowerPainter;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import location.Location;
import towers.Tower;
import towers.types.ArcherTower;


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
     * Return the placeable location that was clicked, or null if none was clicked.
     * 
     * @param location The location where the mouse was clicked.
     * @return         The placeable location that was clicked, or null if none was clicked.
     */
    private Location placeableClicked(Location location) {
        for (Location placeable : this.game.field.placeable) {
            double distance = placeable.distanceTo(location);
            if (distance <= TowerPainter.RADIUS) {
                return placeable;
            }
        }
        return null;
    }

    private Tower towerClicked(Location location) {
        return this.game.field.towers.getOrDefault(location, null);
    }

    private void checkTowerClicked(Location location) {
        Location placeable = this.placeableClicked(location);
        if (placeable == null) {
            return;
        }
        Tower tower = this.towerClicked(placeable);
        if (tower == null) {
            tower = new ArcherTower(this.game, placeable);
            this.game.buyTower(tower);
        } else {
            this.game.sellTower(tower);
        }
    }

    @Override
    public void onAnyPressed(MouseEvent e) {
        Location location = this.getMouseLocation(e);
        this.checkTowerClicked(location);
        this.frame.repaint();
    }
}
