package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


/**
 * A (sort of) wrapper of Graphics that allows for easier drawing.
 */
public final class BetterGraphics {
    /**
     * The constructor.
     */
    public BetterGraphics(
        Graphics g,
        Panel panel,
        Dimension topLeft,
        Dimension frameSize,
        double scale
    ) {
        this.g = g;
        // this.panel = panel;
        this.topLeft = topLeft;
        this.frameSize = frameSize;
        this.scale = scale;
    }

    private Graphics g;
    // private Panel panel;
    private Dimension topLeft;
    private Dimension frameSize;
    private double scale;

    /**
     * Set the color.
     */
    public void setColor(Color color) {
        this.g.setColor(color);
    }

    /**
     * Fill a rectangle.
     */
    public void fillRect(double x, double y, double width, double height) {
        this.g.fillRect(
            (int) (x * this.scale + this.topLeft.width),
            (int) (y * this.scale + this.topLeft.height),
            (int) (width * this.scale),
            (int) (height * this.scale)
        );
    }

    /**
     * Fill an oval.
     */
    public void fillOval(double x, double y, double width, double height) {
        this.g.fillOval(
            (int) (x * this.scale + this.topLeft.width),
            (int) (y * this.scale + this.topLeft.height),
            (int) (width * this.scale),
            (int) (height * this.scale)
        );
    }

    /**
     * Fills the entire screen.
     */
    public void fillEntireScreen() {
        this.g.fillRect(
            0, 0,
            this.frameSize.width,
            this.frameSize.height
        );
    }
}
