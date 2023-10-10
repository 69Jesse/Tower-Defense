package gui.panel.painters;

import game.Game;
import gui.frame.Frame;
import gui.panel.Painter;
import gui.panel.Panel;
import java.awt.Graphics;
import javax.swing.JLabel;


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
        this.label.setBounds(50, 50, 100, 50);
        this.panel.add(label);
    }

    private JLabel label;

    @Override
    public void paint(Graphics g) {
        this.label.setText(String.format("%d gold", this.game.getGold()));
    }
}
