package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Frame;
import gui.Painter;
import gui.Panel;
import java.awt.Color;


/**
 * The gold label painter class.
 */
public final class SideLabelPainter extends Painter {
    /**
     * The constructor.
     * 
     * @param game  The game.
     * @param frame The frame.
     * @param panel The panel.
     */
    public SideLabelPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    private final Color textColor = new Color(0xFFFFFF);
    private final Color boxColor = new Color(0x000000);

    /**
     * Returns the lines of text this painter needs to display on the left.
     * 
     * @return The lines of text.
     */
    private String[] getLeftLines() {
        return new String[] {
            String.format("Gold: %d", this.game.getGold()),
            String.format("Lives: %d", this.game.getLives()),
            String.format(
                "Waves: %1d / %2d",
                this.game.waveHandler.getWaveNumber(),
                this.game.waveHandler.getMaxWave()
            )
        };
    }

    /**
     * Returns the lines of text this painter needs to display on the right.
     * 
     * @return The lines of text.
     */
    private String[] getRightLines() {
        return new String[] {
            String.format("Exp: %d", this.game.getExp()),
            String.format("Kills: %d", this.game.getEnemyKills()),
            String.format("Gold spent: %d", this.game.getGoldSpent()),
            String.format("Wave value: %d", this.game.waveHandler.getWaveValue())
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

        String[] lines = this.getLeftLines();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            graphics.drawStringTopLeftWithBox(
                line,
                this.xOffset,
                this.yOffset + this.ySpacing * i,
                box
            );
        }
        lines = this.getRightLines();
        for (int i = 0; i < lines.length; i++) {
            String string = lines[i];
            graphics.drawStringTopRightWithBox(
                string,
                this.game.field.width - this.xOffset,
                this.yOffset + this.ySpacing * i,
                box
            );
        }
    }
}
