package gui.frame;

import game.Game;
import gui.actions.FullscreenToggleAction;
import gui.mouse.Mouse;
import gui.panels.FieldPanel;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;


/**
 * The frame class.
 */
public class Frame extends BaseFrame {
    private Game game;
    private Mouse mouse;

    public Frame(Game game) {
        this.game = game;
    }

    /**
     * Set the initial size of the frame.
     */
    public void setInitialSize() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double a = 0.6;
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
        this.setup();
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
     * Add a key binding.
     * 
     * @param key    The key.
     * @param action The action.
     */
    public final void addKeyBinding(String key, Action action) {
        JComponent c = (JComponent) this.getRootPane();
        c.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            javax.swing.KeyStroke.getKeyStroke(key),
            key
        );
        c.getActionMap().put(key, action);
        c.setFocusable(true);
    }

    /**
     * Setup the panels.
     */
    private void setup() {
        this.add(new FieldPanel(this, this.game));
        this.addKeyBinding("F11", new FullscreenToggleAction(this));
    }

    public void tick() {
        System.out.println("Tick");
        this.repaint();
    }
}
