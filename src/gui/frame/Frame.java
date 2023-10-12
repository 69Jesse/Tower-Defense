package gui.frame;

import game.Game;
import gui.Panel;
import gui.actions.DebugAction;
import gui.actions.FullscreenToggleAction;
import gui.mouse.Mouse;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
    private Panel panel;

    /**
     * .
     */
    public Frame(Game game) {
        this.game = game;
        this.setLayout(new FlowLayout());
    }

    /**
     * Set the initial size of the frame.
     */
    public void setInitialSize() {
        final Dimension screenSize = this.getToolkit().getScreenSize();
        final double a = 0.6;

        final int width = (int) (screenSize.width * a);
        final int height = (int) (width / (double) this.game.field.width * this.game.field.height);
        this.setSize(
            (int) (width),
            (int) (height)
        );  // Should be a correct ratio but for some reason it is not??
    }

    /**
     * Start the frame.
     */
    public void start() {
        this.panel = new Panel(this, this.game);
        this.add(this.panel);

        this.mouse = new Mouse(this, this.game);
        this.addMouseListener(mouse);

        this.setup();
        this.setInitialSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
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
        this.panel = new Panel(this, this.game);
        this.add(this.panel);
        this.addKeyBinding("F11", new FullscreenToggleAction(this));
        this.addKeyBinding("ENTER", new DebugAction(this, this.game));
    }

    /**
     * Tick.
     */
    public void tick() {
        System.out.println("Tick");
        this.repaint();
    }
}
