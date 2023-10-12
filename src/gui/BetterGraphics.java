package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


/**
 * A (sort of) wrapper of Graphics that allows for easier drawing.
 * This gets instantiated by the Panel class every time it needs to draw.
 */
public final class BetterGraphics {
    /**
     * Constructor.
     * 
     * @param g         The Graphics object to wrap.
     * @param panel     The panel to draw on.
     * @param topLeft   The top left corner of the panel.
     * @param frameSize The size of the frame.
     * @param scale     The scale to draw at.
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
     * 
     * @param color The color to set.
     */
    public void setColor(Color color) {
        this.g.setColor(color);
    }

    /**
     * Fill a rectangle.
     * 
     * @param x      The x coordinate of the top left corner in field pixels.
     * @param y      The y coordinate of the top left corner in field pixels.
     * @param width  The width of the rectangle in field pixels.
     * @param height The height of the rectangle in field pixels.
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
     * 
     * @param x      The x coordinate of the top left corner in field pixels.
     * @param y      The y coordinate of the top left corner in field pixels.
     * @param width  The width of the oval in field pixels.
     * @param height The height of the oval in field pixels.
     */
    public void fillOval(double x, double y, double width, double height) {
        this.g.fillOval(
            (int) (x * this.scale + this.topLeft.width),
            (int) (y * this.scale + this.topLeft.height),
            (int) (width * this.scale),
            (int) (height * this.scale)
        );
    }

    public void fillOval(double x, double y, double radius) {
        this.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
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
