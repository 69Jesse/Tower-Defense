package gui.panels;

import game.Game;
import gui.frame.Frame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import location.Location;


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

    private Dimension topLeft;
    private Dimension newSize;
    private double scale;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.checkDimensions();
        this.drawField(g);
        this.drawPath(g);
    }

    private Dimension fieldSize;

    private void checkDimensions() {
        this.fieldSize = new Dimension(
            this.game.field.width,
            this.game.field.height
        );
        Dimension maxSize = this.frame.getSize();
        if (maxSize.width / this.fieldSize.width < maxSize.height / this.fieldSize.height) {
            this.newSize = new Dimension(
                maxSize.width,
                (int) (maxSize.width * this.fieldSize.height / this.fieldSize.width)
            );
        } else {
            this.newSize = new Dimension(
                (int) (maxSize.height * this.fieldSize.width / this.fieldSize.height),
                maxSize.height
            );
        }
        this.topLeft = new Dimension(
            (maxSize.width - newSize.width) / 2,
            (maxSize.height - newSize.height) / 2
        );
        this.scale = this.newSize.width / this.fieldSize.width;
    }

    private void drawField(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(
            this.topLeft.width,
            this.topLeft.height,
            this.newSize.width,
            this.newSize.height
        );
    }

    private void drawPath(Graphics g) {
        g.setColor(Color.YELLOW);
        final double radius = Math.max(
            this.fieldSize.width, this.fieldSize.height
        ) * 0.01 * this.scale;
        for (Location location : this.game.field.path) {
            g.fillOval(
                (int) (location.x * this.scale - radius + this.topLeft.width),
                (int) (location.y * this.scale - radius + this.topLeft.height),
                (int) (2 * radius),
                (int) (2 * radius)
            );
        }
    }
}