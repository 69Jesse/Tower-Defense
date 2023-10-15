package gui.painters;

import enemies.Enemy;
import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import location.Location;


/**
 * The enemy painter class.
 */
public final class EnemyPainter extends Painter {
    /**
     * The constructor.
     */
    public EnemyPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    @Override
    public void paint(BetterGraphics graphics) {
        for (Enemy enemy : this.game.field.enemies) {
            Location location = enemy.getLocation();
            graphics.drawImageCentered(
                enemy.getImagePath(),
                location.x,
                location.y,
                4, 4
            );
        }
    }
}
