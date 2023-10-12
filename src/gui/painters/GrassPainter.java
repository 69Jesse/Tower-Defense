package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import java.awt.Color;


/**
 * The grass and path painter class.
 */
public class GrassPainter extends Painter {
    /**
     * The constructor.
     */
    public GrassPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    private Color grassColor1 = new Color(0xACCE5E);
    private Color grassColor2 = new Color(0x72B76A);

    @Override
    public void paint(BetterGraphics graphics) {
        graphics.setColor(this.grassColor1);
        graphics.fillRect(
            0, 0,
            this.game.field.width,
            this.game.field.height
        );
        graphics.setColor(this.grassColor2);
        for (int i = 0; i < this.game.field.width; i++) {
            for (int j = 0; j < this.game.field.height; j++) {
                if ((i + j) % 2 != 0) {
                    continue;
                }
                graphics.fillRect(
                    i, j, 1.01, 1.01
                );
            }
        }
    }
}