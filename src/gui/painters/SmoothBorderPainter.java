package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Frame;
import gui.Painter;
import gui.Panel;


/**
 * The smooth border painter class.
 */
public final class SmoothBorderPainter extends Painter {
    /**
     * The constructor.
     * 
     * @param game  The game.
     * @param frame The frame.
     * @param panel The panel.
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
