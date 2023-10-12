package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


/**
 * The background painter class.
 */
public class GoldLabelPainter extends Painter {
    /**
     * The constructor.
     */
    public GoldLabelPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
        this.label = new JLabel();
        //this.label.setBounds(100, 1000, 200, 100);
        this.label.setOpaque(true);
        this.label.setHorizontalTextPosition(SwingConstants.LEFT);
        this.label.setBackground(Color.MAGENTA);
        this.panel.add(label);
    }

    private JLabel label;

    @Override
    public void paint(BetterGraphics graphics) {
        this.label.setText(String.format("%d gold", this.game.getGold()));
    }
}
