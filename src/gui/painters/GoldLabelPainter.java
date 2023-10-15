package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import java.awt.Color;


/**
 * The gold label painter class.
 */
public final class GoldLabelPainter extends Painter {
    /**
     * The constructor.
     */
    public GoldLabelPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    private final Color textColor = new Color(0xFFFFFF);
    private final Color boxColor = new Color(0x000000);

    private String getText() {
        return String.format("Gold: %d", this.game.getGold());
    }

    @Override
    public void paint(BetterGraphics graphics) {
        graphics.setColor(this.textColor);
        graphics.setFont(2.5);
        graphics.drawStringCenteredWithBox(
            this.getText(),
            this.game.field.width / 2, 3,
            graphics.new Box(this.boxColor, 0.5, 0.5)
        );
    }
}
