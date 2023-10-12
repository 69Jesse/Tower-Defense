package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;


/**
 * The smooth border painter class.
 */
public class SmoothBorderPainter extends Painter {
    /**
     * The constructor.
     */
    public SmoothBorderPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    @Override
    public void paint(BetterGraphics graphics) {
        graphics.setColor(BackgroundPainter.BACKGROUND_COLOR);
        graphics.fillRect(
            -this.game.field.width, -this.game.field.height,
            3 * this.game.field.width, this.game.field.height
        );
        graphics.fillRect(
            -this.game.field.width, -this.game.field.height,
            this.game.field.width, 3 * this.game.field.height
        );
        graphics.fillRect(
            this.game.field.width, -this.game.field.height,
            this.game.field.width, 3 * this.game.field.height
        );
        graphics.fillRect(
            -this.game.field.width, this.game.field.height,
            3 * this.game.field.width, this.game.field.height
        );
    }
}
