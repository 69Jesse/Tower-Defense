package gui.panel.painters;

import game.Game;
import gui.frame.Frame;
import gui.panel.Painter;
import gui.panel.Panel;
import java.awt.Color;
import java.awt.Graphics;


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
    public void paint(Graphics g) {
        g.setColor(this.grassColor1);
        g.fillRect(
            this.panel.topLeft.width,
            this.panel.topLeft.height,
            this.panel.newSize.width,
            this.panel.newSize.height
        );
        g.setColor(this.grassColor2);
        for (int i = 0; i < this.game.field.width; i++) {
            for (int j = 0; j < this.game.field.height; j++) {
                if ((i + j) % 2 != 0) {
                    continue;
                }
                g.fillRect(
                    (int) (i * this.panel.scale + this.panel.topLeft.width),
                    (int) (j * this.panel.scale + this.panel.topLeft.height),
                    (int) this.panel.scale,
                    (int) this.panel.scale
                );
            }
        }
    }
}
