package gui;

import game.Game;
import gui.frame.Frame;
import gui.painters.BackgroundPainter;
import gui.painters.GoldLabelPainter;
import gui.painters.GrassPainter;
import gui.painters.PathPainter;
import gui.painters.SmoothBorderPainter;
import gui.painters.TowerPainter;
import location.Location;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Graphics2D;


/**
 * The panel class.
 */
public class Panel extends JPanel {
    private Frame frame;
    private Game game;
    private ArrayList<Painter> painters;

    /**
     * The constructor.
     * 
     * @param frame The frame.
     * @param game  The game.
     */
    public Panel(Frame frame, Game game) {
        this.frame = frame;
        this.game = game;
        this.init();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.checkDimensions();

        Graphics2D g2d = (Graphics2D) g;

        BetterGraphics graphics = new BetterGraphics(
            g2d,
            this,
            topLeft,
            frameSize,
            scale
        );

        for (Painter painter : this.painters) {
            painter.paint(graphics);
        }
    }

    /**
     * Initialize the painters, order matters.
     */
    private void init() {
        this.painters = new ArrayList<>();
        this.painters.add(new BackgroundPainter(this.game, this.frame, this));
        this.painters.add(new GrassPainter(this.game, this.frame, this));
        this.painters.add(new PathPainter(this.game, this.frame, this));
        this.painters.add(new TowerPainter(this.game, this.frame, this));
        this.painters.add(new GoldLabelPainter(this.game, this.frame, this));
        this.painters.add(new SmoothBorderPainter(this.game, this.frame, this));
    }

    public Dimension fieldSize;

    private Dimension frameSize;
    private Dimension topLeft;
    private double scale;

    /**
     * Check the dimensions of the panel, these are used to scale the game.
     */
    private void checkDimensions() {
        this.frameSize = this.getSize();

        final double widthScale = this.frameSize.width / (double) this.game.field.width;
        final double heightScale = this.frameSize.height / (double) this.game.field.height;

        final double fieldWidth;
        final double fieldHeight;

        if (widthScale == heightScale) {
            fieldWidth = this.frameSize.width;
            fieldHeight = this.frameSize.height;
        } else if (widthScale < heightScale) {
            fieldWidth = this.frameSize.width;
            fieldHeight = this.game.field.height * widthScale;
        } else {
            fieldWidth = this.game.field.width * heightScale;
            fieldHeight = this.frameSize.height;
        }

        this.fieldSize = new Dimension(
            (int) fieldWidth,
            (int) fieldHeight
        );

        this.topLeft = new Dimension(
            (int) ((this.frameSize.width - fieldWidth) / 2.0),
            (int) ((this.frameSize.height - fieldHeight) / 2.0)
        );

        this.scale = Math.max(
            fieldWidth / (double) this.game.field.width,
            fieldHeight / (double) this.game.field.height
        );
    }

    /**
     * Correct the x and y coordinates.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return  The corrected location.
     */
    public Location correctXY(int x, int y) {
        return new Location(
            (x - this.topLeft.width) / this.scale,
            (y - this.topLeft.height) / this.scale
        );
    }
}
