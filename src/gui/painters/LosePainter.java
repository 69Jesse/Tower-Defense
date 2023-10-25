package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import java.awt.Color;


/**
 * The lose painter class.
 */
public final class LosePainter extends Painter {
    /**
     * The constructor.
     * 
     * @param game  The game.
     * @param frame The frame.
     * @param panel The panel.
     */
    public LosePainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    private final Color textColor = new Color(0xFF0000);
    private final Color boxColor = new Color(0x000000);
    private final double opacity = 0.7;

    @Override
    public void paint(BetterGraphics graphics) {
        if (!this.game.hasLost()) {
            return;
        }

        BetterGraphics.Box box = graphics.new Box(this.boxColor, this.opacity, 0.5);

        graphics.setColor(this.textColor);
        graphics.setFont(4.0);
        graphics.drawStringCenteredWithBox(
            "You lost the game.", 
            this.game.field.width / 2.0,
            this.game.field.height / 8.0 * 3.0,
            box
        );

        box = graphics.new Box(this.boxColor, this.opacity, 0.5);
        graphics.setFont(2.5);
        graphics.drawStringCenteredWithBox(
            "Press enter to try again", 
            this.game.field.width / 2.0,
            this.game.field.height / 8.0 * 4.0,
            box
        );
    }
}
