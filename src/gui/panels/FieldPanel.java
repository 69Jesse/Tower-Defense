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
        this.drawBackground(g);
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

        double widthScale = maxSize.width / this.fieldSize.width;
        double heightScale = maxSize.height / this.fieldSize.height;

        if (widthScale == heightScale) {
            this.newSize = maxSize;
        } else if (widthScale < heightScale) {
            this.newSize = new Dimension(
                maxSize.width,
                (int) (this.fieldSize.height * widthScale)
            );
        } else {
            this.newSize = new Dimension(
                (int) (this.fieldSize.width * heightScale),
                maxSize.height
            );
        }

        this.topLeft = new Dimension(
            (maxSize.width - this.newSize.width) / 2,
            (maxSize.height - this.newSize.height) / 2
        );

        this.scale = this.newSize.width / (double) this.fieldSize.width;

        System.out.println(this.scale);
    }

    private Color backgroundColor = new Color(0x000000);

    private void drawBackground(Graphics g) {
        g.setColor(this.backgroundColor);
        g.fillRect(0, 0, this.frame.getWidth(), this.frame.getHeight());
    }

    private Color fieldColor1 = new Color(0xACCE5E);
    private Color fieldColor2 = new Color(0x72B76A);

    private void drawField(Graphics g) {
        g.setColor(this.fieldColor1);
        g.fillRect(
            this.topLeft.width,
            this.topLeft.height,
            this.newSize.width,
            this.newSize.height
        );
        g.setColor(this.fieldColor2);
        for (int i = 0; i < this.fieldSize.width; i++) {
            for (int j = 0; j < this.fieldSize.height; j++) {
                if ((i + j) % 2 != 0) {
                    continue;
                }
                g.fillRect(
                    (int) (i * this.scale + this.topLeft.width),
                    (int) (j * this.scale + this.topLeft.height),
                    (int) this.scale,
                    (int) this.scale
                );
            }
        }
    }

    Color pathColor1 = new Color(0xC0971B);
    Color pathColor2 = new Color(0xDBB12C);

    private void drawPath(Graphics g) {
        for (int i = 0; i < 2; i++) {
            double radius = Math.max(
                this.fieldSize.width, this.fieldSize.height
            ) * (
                i == 0 ? 0.02 : 0.015
            ) * this.scale;
            g.setColor(i == 0 ? this.pathColor1 : this.pathColor2);
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
}
