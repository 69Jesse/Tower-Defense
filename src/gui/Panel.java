package gui;

import game.Game;
import gui.painters.BackgroundPainter;
import gui.painters.EnemyPainter;
import gui.painters.GrassPainter;
import gui.painters.LosePainter;
import gui.painters.PathPainter;
import gui.painters.ProjectilePainter;
import gui.painters.SelectedTowerPainter;
import gui.painters.SideLabelPainter;
import gui.painters.SmoothBorderPainter;
import gui.painters.StartPainter;
import gui.painters.SpeedMultiplierPainter;
import gui.painters.TowerPainter;
import gui.painters.WinPainter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;
import location.Location;


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
            topLeft,
            frameSize,
            scale
        );

        for (Painter painter : this.painters) {
            graphics.setTransparency(1.0);
            graphics.setColor(Color.BLACK);
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
        this.painters.add(new SpeedMultiplierPainter(this.game, this.frame, this));
        this.painters.add(new TowerPainter(this.game, this.frame, this));
        this.painters.add(new EnemyPainter(this.game, this.frame, this));
        this.painters.add(new ProjectilePainter(this.game, this.frame, this));
        this.painters.add(new SelectedTowerPainter(this.game, this.frame, this));
        this.painters.add(new SideLabelPainter(this.game, this.frame, this));
        this.painters.add(new WinPainter(this.game, this.frame, this));
        this.painters.add(new LosePainter(this.game, this.frame, this));
        this.painters.add(new StartPainter(this.game, this.frame, this));
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

    /**
     * Get the mouse location, or null if the mouse is not in the frame.
     * 
     * @return The mouse location or null.
     */
    public Location getMouseLocation() {
        try {
            Point mousePosition = this.frame.getMousePosition();
            Insets insets = this.frame.getInsets();
            return this.correctXY(
                mousePosition.x - insets.left,
                mousePosition.y - insets.top
            );
        } catch (NullPointerException e) {
            return null;
        }
    }
}
