package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Frame;
import gui.Painter;
import gui.Panel;
import location.Location;
import towers.Tower;


/**
 * The tower painter class.
 */
public final class TowerPainter extends Painter {
    /**
     * The constructor.
     * 
     * @param game  The game.
     * @param frame The frame.
     * @param panel The panel.
     */
    public TowerPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    public static final int SIZE = 4;

    @Override
    public void paint(BetterGraphics graphics) {
        for (Location location : this.game.field.placeable) {
            Tower tower = this.game.field.towers.getOrDefault(location, null);
            String imagePath = tower == null ? Tower.UNPLACED_IMAGE_PATH : tower.getImagePath();
            graphics.drawImageCentered(
                imagePath,
                location.x,
                location.y,
                SIZE, SIZE
            );
        }
    }
}
