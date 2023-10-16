package gui.painters;

import enemies.Enemy;
import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import java.awt.Color;
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

    private void drawEnemy(BetterGraphics graphics, Enemy enemy) {
        Location location = enemy.getLocation();
        graphics.drawImageCentered(
            enemy.getImagePath(),
            location.x,
            location.y,
            enemy.size,
            enemy.size
        );
    }

    private double healthBarYOffset = 2.5;
    private double healthBarWidth = 3.0;
    private double healthBarHeight = 0.8;
    private double healthBarPadding = 0.15;
    private Color healthBarPaddingColor = new Color(0x000000);
    private Color healthBarFilledColor = new Color(0x00FF00);
    private Color healthBarEmptyColor = new Color(0xFF0000);

    private void drawHealthBar(BetterGraphics graphics, Enemy enemy) {
        final Location location = enemy.getLocation();

        final double healthPercentage = Math.max(0.0, Math.min(1.0,
            enemy.getHealth() / (double) enemy.maxHealth
        ));

        final double topLeftX = location.x - this.healthBarWidth / 2;
        final double topLeftY = location.y - this.healthBarYOffset - this.healthBarHeight / 2;

        graphics.setColor(this.healthBarPaddingColor);
        graphics.fillRect(
            topLeftX,
            topLeftY,
            this.healthBarWidth,
            this.healthBarHeight
        );
        graphics.setColor(this.healthBarEmptyColor);
        graphics.fillRect(
            topLeftX + this.healthBarPadding,
            topLeftY + this.healthBarPadding,
            (this.healthBarWidth - this.healthBarPadding * 2),
            this.healthBarHeight - this.healthBarPadding * 2
        );
        graphics.setColor(this.healthBarFilledColor);
        graphics.fillRect(
            topLeftX + this.healthBarPadding,
            topLeftY + this.healthBarPadding,
            (this.healthBarWidth - this.healthBarPadding * 2) * healthPercentage,
            this.healthBarHeight - this.healthBarPadding * 2
        );
    }

    @Override
    public void paint(BetterGraphics graphics) {
        for (Enemy enemy : this.game.field.enemies) {
            this.drawEnemy(graphics, enemy);
            this.drawHealthBar(graphics, enemy);
        }
    }
}
