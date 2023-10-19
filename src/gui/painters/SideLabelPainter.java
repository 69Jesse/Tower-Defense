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
public final class SideLabelPainter extends Painter {
    /**
     * The constructor.
     */
    public SideLabelPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    private final Color textColor = new Color(0xFFFFFF);
    private final Color boxColor = new Color(0x000000);

    /**
     * Returns the strings of text this painter needs to display on the left.
     * 
     * @return The strings of text.
     */
    private String[] getLeftStrings() {
        return new String[] {
            String.format("Gold: %d", this.game.getGold())
        };
    }

    /**
     * Returns the strings of text this painter needs to display on the right.
     * 
     * @return The strings of text.
     */
    private String[] getRightString() {
        return new String[] {
            String.format("Exp: %d", this.game.getExp()),
            String.format("Kills: %d", this.game.getEnemyKills()),
            String.format("Gold spent: %d", this.game.getGoldSpent())
        };
    }

    private final double xOffset = 1.0;
    private final double yOffset = 1.0;
    private final double ySpacing = 2.5;

    @Override
    public void paint(BetterGraphics graphics) {
        graphics.setColor(this.textColor);
        graphics.setFont(1.5);

        BetterGraphics.Box box = graphics.new Box(this.boxColor, 0.5, 0.5);

        String[] strings = this.getLeftStrings();
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            graphics.drawStringTopLeftWithBox(
                string,
                this.xOffset,
                this.yOffset + this.ySpacing * i,
                box
            );
        }
        strings = this.getRightString();
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            graphics.drawStringTopRightWithBox(
                string,
                this.game.field.width - this.xOffset,
                this.yOffset + this.ySpacing * i,
                box
            );
        }
    }
}
