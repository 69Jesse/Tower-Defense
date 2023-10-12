package gui.mouse;

import java.awt.event.MouseEvent;

import game.Game;
import gui.frame.Frame;
import towers.Tower;
import towers.types.ArcherTower;
import location.Location;
import gui.Panel;

import gui.painters.TowerPainter;


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
        return this.panel.correctXY(e.getX(), e.getY());
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
