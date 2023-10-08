package gui.mouse;

import gui.frame.BaseFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;


/**
 * The BaseMouse class is a MouseAdapter that changed a few methods.
 */
public abstract class BaseMouse extends MouseAdapter {
    protected BaseFrame frame;

    /**
     * Constructs a new BaseMouse.
     */
    public BaseMouse(BaseFrame frame) {
        super();
        this.frame = frame;
    }

    private HashMap<Integer, Long> buttonsHeld = new HashMap<>();

    public void onLeftPressed(MouseEvent e) {}

    public void onMiddlePressed(MouseEvent e) {}

    public void onRightPressed(MouseEvent e) {}

    public void onAnyPressed(MouseEvent e) {}

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    @Override
    public final void mousePressed(MouseEvent e) {
        int button = e.getButton();
        this.buttonsHeld.put(button, e.getWhen());
        if (button == MouseEvent.BUTTON1) {
            this.onLeftPressed(e);
        } else if (button == MouseEvent.BUTTON2) {
            this.onMiddlePressed(e);
        } else if (button == MouseEvent.BUTTON3) {
            this.onRightPressed(e);
        }
        this.onAnyPressed(e);
    }

    public void onLeftReleased(MouseEvent e) {}

    public void onMiddleReleased(MouseEvent e) {}

    public void onRightReleased(MouseEvent e) {}

    public void onAnyReleased(MouseEvent e) {}

    /**
     * Invoked when a mouse button has been released on a component.
     */
    @Override
    public final void mouseReleased(MouseEvent e) {
        int button = e.getButton();
        this.buttonsHeld.remove(button);
        if (button == MouseEvent.BUTTON1) {
            this.onLeftReleased(e);
        } else if (button == MouseEvent.BUTTON2) {
            this.onMiddleReleased(e);
        } else if (button == MouseEvent.BUTTON3) {
            this.onRightReleased(e);
        }
        this.onAnyReleased(e);
    }

    /**
     * Returns whether or not a key is held.
     * 
     * @param key The key to check.
     * @return    Whether or not the key is held.
     */
    public boolean keyIsHeld(int key) {
        return this.buttonsHeld.containsKey(key);
    }

    public boolean leftIsHeld() {
        return this.keyIsHeld(MouseEvent.BUTTON1);
    }

    public boolean middleIsHeld() {
        return this.keyIsHeld(MouseEvent.BUTTON2);
    }

    public boolean rightIsHeld() {
        return this.keyIsHeld(MouseEvent.BUTTON3);
    }

    public boolean anyIsHeld() {
        return this.buttonsHeld.size() > 0;
    }

    /**
     * Returns the amount of time a key has been held for in milliseconds.
     * 
     * @param key The key to check.
     * @return    The amount of time the key has been held for in milliseconds.
     */
    public Long keyIsHeldFor(int key) {
        Long time = this.buttonsHeld.get(key);
        if (time == null) {
            return null;
        }
        return System.currentTimeMillis() - time;
    }

    public Long leftIsHeldFor() {
        return this.keyIsHeldFor(MouseEvent.BUTTON1);
    }

    public Long middleIsHeldFor() {
        return this.keyIsHeldFor(MouseEvent.BUTTON2);
    }

    public Long rightIsHeldFor() {
        return this.keyIsHeldFor(MouseEvent.BUTTON3);
    }

    @Override
    public final void mouseClicked(MouseEvent e) {}
}
