package gui.frame;

import game.Game;
import gui.mouse.Mouse;
import gui.panels.FieldPanel;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;


/**
 * The frame class.
 */
public class Frame extends BaseFrame {
    private Game game;
    private Mouse mouse;

    private FieldPanel fieldPanel;

    public Frame(Game game) {
        this.game = game;
    }

    public void setInitialSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double a = 0.6;
        this.setSize(
            (int) (screenSize.width * a),
            (int) (screenSize.height * a)
        );
    }

    /**
     * Start the frame.
     */
    public void start() {
        this.mouse = new Mouse(this, this.game);
        this.setInitialSize();
        this.addMouseListener(mouse);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setupPanels();
        this.setVisible(true);

        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                tick();
            }
        };
        Timer timer = new Timer(
            1000 / this.game.ticksPerSecond,
            taskPerformer
        );
        timer.start();
    }

    /**
     * Setup the panels.
     */
    private void setupPanels() {
        this.fieldPanel = new FieldPanel(this, this.game);
        this.add(this.fieldPanel);
    }

    public void tick() {
        System.out.println("Tick");
        this.repaint();
    }
}
