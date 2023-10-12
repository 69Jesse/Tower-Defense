package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import javax.swing.JLabel;


/**
 * The gold label painter class.
 */
public class GoldLabelPainter extends Painter {
    /**
     * The constructor.
     */
    public GoldLabelPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
        this.label = new JLabel();
        this.label.setBounds(50, 50, 100, 50);
        this.panel.add(label);
    }

    private final JLabel label;

    @Override
    public void paint(BetterGraphics graphics) {
        this.label.setText(String.format("%d gold", this.game.getGold()));
    }
}
