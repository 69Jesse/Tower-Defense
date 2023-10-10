package gui.panels;

import game.Game;
import gui.frame.Frame;
import java.awt.Graphics;
import javax.swing.JLabel;


/**
 * This panel has a label that shows the amount of gold the player has.
 */
public class GoldPanel extends BasePanel {
    private Game game;
    JLabel label;
    
    /**
     * The constuctor.
     * 
     * @param frame The frame.
     * @param game  The game.
     */
    public GoldPanel(Frame frame, Game game) {
        super(frame);
        this.game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {

    }

    /**
     * Creates the label.
     */
    public void createLabel() {
        this.label = new JLabel();
        this.updateLabel();
        this.add(label);
    }

    public void updateLabel() {
        this.label.setText(String.format("%d gold", this.game.getGold()));
    }
}
