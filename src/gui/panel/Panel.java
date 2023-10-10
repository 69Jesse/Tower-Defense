package gui.panel;

import game.Game;
import gui.frame.Frame;
import gui.panel.painters.BackgroundPainter;
import gui.panel.painters.GoldLabelPainter;
import gui.panel.painters.GrassPainter;
import gui.panel.painters.PathPainter;
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
        for (Painter painter : this.painters) {
            painter.paint(g);
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
    }

    public Dimension topLeft;
    public Dimension newSize;
    public double scale;

    private void checkDimensions() {
        Dimension maxSize = this.frame.getSize();

        double widthScale = maxSize.width / this.game.field.width;
        double heightScale = maxSize.height / this.game.field.height;

        if (widthScale == heightScale) {
            this.newSize = maxSize;
        } else if (widthScale < heightScale) {
            this.newSize = new Dimension(
                maxSize.width,
                (int) (this.game.field.height * widthScale)
            );
        } else {
            this.newSize = new Dimension(
                (int) (this.game.field.width * heightScale),
                maxSize.height
            );
        }

        this.topLeft = new Dimension(
            (maxSize.width - this.newSize.width) / 2,
            (maxSize.height - this.newSize.height) / 2
        );

        this.scale = this.newSize.width / (double) this.game.field.width;

        System.out.println(this.scale);
    }
}
