package gui;

import game.Game;
import gui.actions.DebugAction;
import gui.actions.FullscreenToggleAction;
import gui.actions.StartAction;
import gui.mouse.Mouse;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;


/**
 * The frame class.
 */
public class Frame extends JFrame {
    private Game game;
    public Mouse mouse;
    private Panel panel;

    /**
     * The constructor.
     * 
     * @param game The game.
     */
    public Frame(Game game) {
        super("Robot Rampage");
        this.game = game;
    }

    /**
     * Set the initial size of the frame.
     * 
     * It seems to be very hard to get a good proportion
     * when undecorated is false, not really sure why.
     */
    public void setInitialSize() {
        final Dimension screenSize = this.getToolkit().getScreenSize();
        final Insets insets = this.getInsets();
        final double a = 0.6;

        // For some reason this works the best..
        final int width = (int) (screenSize.width * a - insets.left - insets.right);
        final int height = (int) (screenSize.height * a + insets.top - insets.bottom - 2);

        this.setSize(width, height);
    }

    /**
     * Start the frame.
     */
    public void start() {
        this.panel = new Panel(this, this.game);
        this.add(this.panel);

        this.mouse = new Mouse(this, this.game, this.panel);
        this.addMouseListener(mouse);

        this.setup();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setVisible(true);
        this.setInitialSize();

        new Timer(
            1000 / this.game.ticksPerSecond,
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    tick();
                }
            }
        ).start();
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
        this.addKeyBinding("F11", new FullscreenToggleAction(this));
        this.addKeyBinding("ENTER", new StartAction(this.game));
        this.addKeyBinding("SPACE", new DebugAction(this.game));
    }

    /**
     * Handle a game tick.
     */
    private void tick() {
        this.game.tick();
        this.repaint();
    }
}
