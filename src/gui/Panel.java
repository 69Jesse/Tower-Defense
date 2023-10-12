package gui;

import game.Game;
import gui.frame.Frame;
import gui.painters.BackgroundPainter;
import gui.painters.GoldLabelPainter;
import gui.painters.GrassPainter;
import gui.painters.PathPainter;
import gui.painters.SmoothBorderPainter;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;


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

        BetterGraphics graphics = new BetterGraphics(
            g,
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
        this.painters.add(new GoldLabelPainter(this.game, this.frame, this));
        this.painters.add(new SmoothBorderPainter(this.game, this.frame, this));
    }

    public Dimension fieldSize;

    private Dimension frameSize;
    private Dimension topLeft;
    private double scale;

    private void checkDimensions() {
        this.frameSize = this.getSize();
        System.out.println(
            this.frameSize.width + " " + this.frameSize.height
        );

        double widthScale = this.frameSize.width / (double) this.game.field.width;
        double heightScale = this.frameSize.height / (double) this.game.field.height;
        System.out.println(
            widthScale + " " + heightScale
        );

        double fieldWidth;
        double fieldHeight;

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

        int side = this.topLeft.width;
        int top = this.topLeft.height;
    }
}
