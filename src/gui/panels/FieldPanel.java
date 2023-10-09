package gui.panels;

import game.Game;
import gui.frame.Frame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;


/**
 * The field panel class.
 */
public class FieldPanel extends BasePanel {
    private Game game;

    /**
     * The constructor.
     * 
     * @param frame The frame.
     * @param game  The game.
     */
    public FieldPanel(Frame frame, Game game) {
        super(frame);
        this.game = game;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);

        Dimension maxSize = this.frame.getSize();
        Dimension fieldSize = new Dimension(
            this.game.field.width,
            this.game.field.height
        );

        Dimension newSize;
        if (maxSize.width / fieldSize.width < maxSize.height / fieldSize.height) {
            newSize = new Dimension(
                maxSize.width,
                (int) (maxSize.width * fieldSize.height / fieldSize.width)
            );
        } else {
            newSize = new Dimension(
                (int) (maxSize.height * fieldSize.width / fieldSize.height),
                maxSize.height
            );
        }

        g.fillRect(
            (maxSize.width - newSize.width) / 2,
            (maxSize.height - newSize.height) / 2,
            newSize.width,
            newSize.height
        );
    }
}
