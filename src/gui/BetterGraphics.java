package gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;


/**
 * A (sort of) wrapper of Graphics2D that allows for easier drawing.
 * This gets instantiated by the Panel class every time it needs to draw.
 */
public final class BetterGraphics {
    /**
     * Constructor.
     * 
     * @param g2d       The Graphics object to wrap.
     * @param topLeft   The top left corner of the panel.
     * @param frameSize The size of the frame.
     * @param scale     The scale to draw at.
     */
    public BetterGraphics(
        Graphics2D g2d,
        Dimension topLeft,
        Dimension frameSize,
        double scale
    ) {
        this.g2d = g2d;
        this.topLeft = topLeft;
        this.frameSize = frameSize;
        this.scale = scale;
    }

    private Graphics2D g2d;
    private Dimension topLeft;
    private Dimension frameSize;
    private double scale;

    /**
     * Set the color.
     * 
     * @param color The color to set.
     */
    public void setColor(Color color) {
        this.g2d.setColor(color);
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
        this.g2d.fillRect(
            (int) (x * this.scale + this.topLeft.width),
            (int) (y * this.scale + this.topLeft.height),
            (int) (width * this.scale),
            (int) (height * this.scale)
        );
    }

    /**
     * Fill a rectangle around the given coordinates.
     * 
     * @param x      The x coordinate of the middle in field pixels.
     * @param y      The y coordinate of the middle in field pixels.
     * @param width  The width of the rectangle in field pixels.
     * @param height The height of the rectangle in field pixels.
     */
    public void fillRectCentered(double x, double y, double width, double height) {
        this.fillRect(
            x - width / 2,
            y - height / 2,
            width,
            height
        );
    }

    /**
     * Set the line width.
     * 
     * @param width The width of the line in field pixels.
     */
    public void setLineWidth(double width) {
        this.g2d.setStroke(new BasicStroke((float) (width * this.scale)));
    }

    /**
     * Draw an oval.
     * 
     * @param x      The x coordinate of the top left corner in field pixels.
     * @param y      The y coordinate of the top left corner in field pixels.
     * @param width  The width of the oval in field pixels.
     * @param height The height of the oval in field pixels.
     */
    public void drawOval(double x, double y, double width, double height) {
        this.g2d.drawOval(
            (int) (x * this.scale + this.topLeft.width),
            (int) (y * this.scale + this.topLeft.height),
            (int) (width * this.scale),
            (int) (height * this.scale)
        );
    }

    /**
     * Draw an oval around the given coordinates.
     * 
     * @param x      The x coordinate of the middle in field pixels.
     * @param y      The y coordinate of the middle in field pixels.
     * @param radius The radius of the oval in field pixels.
     */
    public void drawOval(double x, double y, double radius) {
        this.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
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
        this.g2d.fillOval(
            (int) (x * this.scale + this.topLeft.width),
            (int) (y * this.scale + this.topLeft.height),
            (int) (width * this.scale),
            (int) (height * this.scale)
        );
    }

    /**
     * Fill an oval around the given coordinates.
     * 
     * @param x      The x coordinate of the middle in field pixels.
     * @param y      The y coordinate of the middle in field pixels.
     * @param radius The radius of the oval in field pixels.
     */
    public void fillOval(double x, double y, double radius) {
        this.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    /**
     * Fills the entire screen.
     */
    public void fillEntireScreen() {
        this.g2d.fillRect(
            0, 0,
            this.frameSize.width,
            this.frameSize.height
        );
    }

    private static final HashMap<String, Image> IMAGES = new HashMap<>();

    /**
     * Get an image, possibly from the cache.
     * 
     * @param imagePath The path to the image.
     * @return          The image.
     */
    private Image getMaybeCachedImage(String imagePath) {
        Image image = IMAGES.getOrDefault(imagePath, null);
        if (image == null) {
            image = new ImageIcon(imagePath).getImage();
            IMAGES.put(imagePath, image);
        }
        return image;
    }

    /**
     * Draw an image.
     * 
     * @param imagePath The path to the image.
     * @param x         The x coordinate of the top left corner in field pixels.
     * @param y         The y coordinate of the top left corner in field pixels.
     * @param width     The width of the image in field pixels.
     * @param height    The height of the image in field pixels.
     */
    public void drawImage(
        String imagePath,
        double x,
        double y,
        double width,
        double height
    ) {
        Image image = this.getMaybeCachedImage(imagePath);
        this.g2d.drawImage(
            image,
            (int) (x * this.scale + this.topLeft.width),
            (int) (y * this.scale + this.topLeft.height),
            (int) (width * this.scale),
            (int) (height * this.scale),
            null
        );
    }

    /**
     * Draw an image around the given coordinates.
     * 
     * @param imagePath The path to the image.
     * @param x         The x coordinate of the middle in field pixels.
     * @param y         The y coordinate of the middle in field pixels.
     * @param width     The width of the image in field pixels.
     * @param height    The height of the image in field pixels.
     */
    public void drawImageCentered(
        String imagePath,
        double x,
        double y,
        double width,
        double height
    ) {
        this.drawImage(
            imagePath,
            x - width / 2,
            y - height / 2,
            width,
            height
        );
    }

    /**
     * Set the transparency of the graphics.
     * 
     * @param alpha The alpha value in [0, 1].
     */
    public void setTransparency(double alpha) {
        alpha = Math.max(0.0, Math.min(1.0, alpha));
        this.g2d.setComposite(
            AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER,
                (float) alpha
            )
        );
    }

    public static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);

    /**
     * Set the font.
     * 
     * @param size The size of the font. Not necessarily in field pixels.
     * @param font The font.
     */
    public void setFont(
        double size,
        Font font
    ) {
        this.g2d.setFont(font.deriveFont((float) (size * this.scale)));
    }

    public void setFont(double size) {
        this.setFont(size, DEFAULT_FONT);
    }

    /**
     * A small class to store a box that needs to be drawn around a string.
     */
    public class Box {
        public Color color;
        public double alpha;
        public double padding;
        public double yPaddingMultiplier;

        private final double defaultYPaddingMultiplier = 0.5;

        /**
         * Constructor.
         * 
         * @param color              The color of the box.
         * @param alpha              The alpha value of the box in [0, 1].
         * @param padding            The padding of the box in field pixels.
         * @param yPaddingMultiplier The multiplier for the y padding.
         */
        public Box(
            Color color,
            double alpha,
            double padding,
            double yPaddingMultiplier
        ) {
            this.color = color;
            this.alpha = alpha;
            this.padding = padding;
            this.yPaddingMultiplier = yPaddingMultiplier;
        }

        /**
         * Constructor.
         * 
         * @param color   The color of the box.
         * @param alpha   The alpha value of the box in [0, 1].
         * @param padding The padding of the box in field pixels.
         */
        public Box(
            Color color,
            double alpha,
            double padding
        ) {
            this.color = color;
            this.alpha = alpha;
            this.padding = padding;
            this.yPaddingMultiplier = this.defaultYPaddingMultiplier;
        }
    }

    /**
     * An enum to store the alignment of a string.
     */
    public enum Alignment {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        CENTER;
    }

    /**
     * Private method to draw a string.
     * 
     * @param text      The text to draw.
     * @param x         The x coordinate of the top left corner in field pixels.
     * @param y         The y coordinate of the top left corner in field pixels.
     * @param alignment The alignment of the text.
     * @param box       Optional box to draw the text in.
     */
    private void drawStringPrivate(
        String text,
        double x,
        double y,
        Alignment alignment,
        Box box
    ) {
        x = x * this.scale + this.topLeft.width;
        y = y * this.scale + this.topLeft.height;

        FontMetrics metrics = this.g2d.getFontMetrics();
        int width = metrics.stringWidth(text);
        int height = metrics.getHeight();

        switch (alignment) {
            case TOP_LEFT:
                break;
            case TOP_RIGHT:
                x -= width;
                break;
            case BOTTOM_LEFT:
                y -= height;
                break;
            case BOTTOM_RIGHT:
                x -= width;
                y -= height;
                break;
            case CENTER:
                x -= width / 2;
                y -= height / 2;
                break;
            default:
                throw new IllegalArgumentException("Invalid alignment: " + alignment);
        }
    
        if (box != null) {
            Color beforeColor = this.g2d.getColor();
            double beforeAlpha = ((AlphaComposite) this.g2d.getComposite()).getAlpha();
            this.setTransparency(box.alpha);
            this.setColor(box.color);
            double yPadding = box.padding * box.yPaddingMultiplier;
            this.g2d.fillRect(
                (int) (x - (box.padding * this.scale)),
                (int) (y - (yPadding * this.scale)),
                width + (int) (2 * box.padding * this.scale),
                height + (int) (2 * yPadding * this.scale)
            );
            this.setColor(beforeColor);
            this.setTransparency(beforeAlpha);
        }

        this.g2d.drawString(
            text,
            (int) x,
            (int) (y + metrics.getAscent())
        );
    }

    /**
     * Draw a string in the top left corner.
     * 
     * @param text The text to draw.
     * @param x    The x coordinate of the top left corner in field pixels.
     * @param y    The y coordinate of the top left corner in field pixels.
     */
    public void drawStringTopLeft(String text, double x, double y) {
        this.drawStringPrivate(text, x, y, Alignment.TOP_LEFT, null);
    }

    /**
     * Draw a string in the top right corner.
     * 
     * @param text The text to draw.
     * @param x    The x coordinate of the top right corner in field pixels.
     * @param y    The y coordinate of the top right corner in field pixels.
     */
    public void drawStringTopRight(String text, double x, double y) {
        this.drawStringPrivate(text, x, y, Alignment.TOP_RIGHT, null);
    }

    /**
     * Draw a string in the bottom left corner.
     * 
     * @param text The text to draw.
     * @param x    The x coordinate of the bottom left corner in field pixels.
     * @param y    The y coordinate of the bottom left corner in field pixels.
     */
    public void drawStringBottomLeft(String text, double x, double y) {
        this.drawStringPrivate(text, x, y, Alignment.BOTTOM_LEFT, null);
    }

    /**
     * Draw a string in the bottom right corner.
     * 
     * @param text The text to draw.
     * @param x    The x coordinate of the bottom right corner in field pixels.
     * @param y    The y coordinate of the bottom right corner in field pixels.
     */
    public void drawStringBottomRight(String text, double x, double y) {
        this.drawStringPrivate(text, x, y, Alignment.BOTTOM_RIGHT, null);
    }

    /**
     * Draw a string around the given coordinates.
     * 
     * @param text The text to draw.
     * @param x    The x coordinate of the middle in field pixels.
     * @param y    The y coordinate of the middle in field pixels.
     */
    public void drawStringCentered(String text, double x, double y) {
        this.drawStringPrivate(text, x, y, Alignment.CENTER, null);
    }

    /**
     * Draw a string in the top left corner with a box.
     * 
     * @param text The text to draw.
     * @param x    The x coordinate of the top left corner in field pixels.
     * @param y    The y coordinate of the top left corner in field pixels.
     * @param box  The box to draw the text in.
     */
    public void drawStringTopLeftWithBox(String text, double x, double y, Box box) {
        this.drawStringPrivate(text, x, y, Alignment.TOP_LEFT, box);
    }

    /**
     * Draw a string in the top right corner with a box.
     * 
     * @param text The text to draw.
     * @param x    The x coordinate of the top right corner in field pixels.
     * @param y    The y coordinate of the top right corner in field pixels.
     * @param box  The box to draw the text in.
     */
    public void drawStringTopRightWithBox(String text, double x, double y, Box box) {
        this.drawStringPrivate(text, x, y, Alignment.TOP_RIGHT, box);
    }

    /**
     * Draw a string in the bottom left corner with a box.
     * 
     * @param text The text to draw.
     * @param x    The x coordinate of the bottom left corner in field pixels.
     * @param y    The y coordinate of the bottom left corner in field pixels.
     * @param box  The box to draw the text in.
     */
    public void drawStringBottomLeftWithBox(String text, double x, double y, Box box) {
        this.drawStringPrivate(text, x, y, Alignment.BOTTOM_LEFT, box);
    }

    /**
     * Draw a string in the bottom right corner with a box.
     * 
     * @param text The text to draw.
     * @param x    The x coordinate of the bottom right corner in field pixels.
     * @param y    The y coordinate of the bottom right corner in field pixels.
     * @param box  The box to draw the text in.
     */
    public void drawStringBottomRightWithBox(String text, double x, double y, Box box) {
        this.drawStringPrivate(text, x, y, Alignment.BOTTOM_RIGHT, box);
    }

    /**
     * Draw a string around the given coordinates with a box.
     * 
     * @param text The text to draw.
     * @param x    The x coordinate of the middle in field pixels.
     * @param y    The y coordinate of the middle in field pixels.
     * @param box  The box to draw the text in.
     */
    public void drawStringCenteredWithBox(String text, double x, double y, Box box) {
        this.drawStringPrivate(text, x, y, Alignment.CENTER, box);
    }

    /**
     * Draw a line.
     * 
     * @param startX The x coordinate of the start in field pixels.
     * @param startY The y coordinate of the start in field pixels.
     * @param endX   The x coordinate of the end in field pixels.
     * @param endY   The y coordinate of the end in field pixels.
     */
    public void drawLine(double startX, double startY, double endX, double endY) {
        this.g2d.drawLine(
            (int) (startX * this.scale + this.topLeft.width),
            (int) (startY * this.scale + this.topLeft.height),
            (int) (endX * this.scale + this.topLeft.width),
            (int) (endY * this.scale + this.topLeft.height)
        );
    }
}
