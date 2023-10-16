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
            4, 4
        );
    }

    private double healthBarYOffset = 3.0;
    private double healthBarWidth = 5.0;
    private double healthBarHeight = 2.0;
    private double healthBarPadding = 0.5;
    private Color healthBarPaddingColor = new Color(0x000000);
    private Color healthBarFilledColor = new Color(0x00FF00);
    private Color healthBarEmptyColor = new Color(0xFF0000);

    private void drawHealthBar(BetterGraphics graphics, Enemy enemy) {
        Location location = enemy.getLocation();

        double healthPercentage = Math.max(0.0, Math.min(1.0, enemy.getHealth() / enemy.maxHealth));

        double topLeftX = location.x - this.healthBarWidth / 2;
        double topLeftY = location.y - this.healthBarYOffset - this.healthBarHeight / 2;
        double bottomRightX = location.x + this.healthBarWidth / 2;
        double bottomRightY = location.y - this.healthBarYOffset + this.healthBarHeight / 2;

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
            this.healthBarWidth - this.healthBarPadding * 2,
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
