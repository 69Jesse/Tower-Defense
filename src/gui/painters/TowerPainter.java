package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;


/**
 * The background painter class.
 */
public class TowerPainter extends Painter {
    /**
     * The constructor.
     */
    public TowerPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    private 

    @Override
    public void paint(BetterGraphics graphics) {
        graphics.setColor(BackgroundPainter.backgroundColor);
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
