package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import java.awt.Color;


/**
 * The win painter class.
 */
public final class WinPainter extends Painter {
    /**
     * The constructor.
     * 
     * @param game  The game.
     * @param frame The frame.
     * @param panel The panel.
     */
    public WinPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    private final Color textColor = Color.GREEN;
    private final Color boxColor = Color.BLACK;

    @Override
    public void paint(BetterGraphics graphics) {
        if (game.gameWon) {
            BetterGraphics.Box box = graphics.new Box(this.boxColor, 0.7, 0.5);

            graphics.setColor(textColor);
            graphics.setFont(4.0);
            graphics.drawStringCenteredWithBox(
                "You won the game!", 
                this.game.field.width / 2.0,
                this.game.field.height / 8.0 * 3.0,
                box
            );

            BetterGraphics.Box box2 = graphics.new Box(this.boxColor, 0.5, 0.5);
            graphics.setFont(2.5);
            graphics.drawStringCenteredWithBox(
                "Press enter to play again", 
                this.game.field.width / 2.0,
                this.game.field.height / 8.0 * 4.0,
                box2
            );
        }
    }
}
