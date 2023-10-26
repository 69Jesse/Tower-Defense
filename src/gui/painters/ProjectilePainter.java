package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import location.Location;
import towers.projectile.ImageProjectile;
import towers.projectile.Projectile;


/**
 * The projectile painter class.
 */
public final class ProjectilePainter extends Painter {
    /**
     * The constructor.
     * 
     * @param game  The game.
     * @param frame The frame.
     * @param panel The panel.
     */
    public ProjectilePainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    private void paintImageProjectile(BetterGraphics graphics, ImageProjectile projectile) {
        Location location = projectile.getLocation();
        double y = location.y;

        if (projectile.maxCurve > 0) {
            double percentage = projectile.getPercentage();
            y -= projectile.maxCurve * Math.sin(percentage * Math.PI);
        }

        graphics.drawImageCentered(
            projectile.getImagePath(),
            location.x,
            y,
            projectile.size,
            projectile.size
        );
    }

    @Override
    public void paint(BetterGraphics graphics) {
        for (Projectile projectile : this.game.field.projectiles) {
            if (projectile instanceof ImageProjectile) {
                this.paintImageProjectile(graphics, (ImageProjectile) projectile);
            }
        }
    }
}
