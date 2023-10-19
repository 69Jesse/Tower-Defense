package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import java.awt.Color;


/**
 * The start painter class.
 */
public final class StartPainter extends Painter {
    /**
     * The constructor.
     * 
     * @param game  The game.
     * @param frame The frame.
     * @param panel The panel.
     */
    public StartPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    private final Color textColor = Color.WHITE;
    private final Color boxColor = Color.BLACK;

    @Override
    public void paint(BetterGraphics graphics) {
        if (!game.gameStarted) {
            BetterGraphics.Box box = graphics.new Box(this.boxColor, 0.7, 0.5);

            graphics.setColor(textColor);
            graphics.setFont(4.0);
            graphics.drawStringCenteredWithBox(
                "Press enter to start the game.", 
                this.game.field.width / 2.0,
                this.game.field.height / 2.0,
                box
            );
        }
    }
}
