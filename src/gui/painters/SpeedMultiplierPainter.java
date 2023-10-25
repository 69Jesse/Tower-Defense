package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Painter;
import gui.Panel;
import gui.frame.Frame;


/**
 * The speed multiplier painter class.
 */
public final class SpeedMultiplierPainter extends Painter {
    /**
     * The constructor.
     * 
     * @param game  The game.
     * @param frame The frame.
     * @param panel The panel.
     */
    public SpeedMultiplierPainter(Game game, Frame frame, Panel panel) {
        super(game, frame, panel);
    }

    /**
     * Returns the image path.
     * 
     * @return The image path.
     */
    private String getImagePath() {
        switch (this.game.getSpeed()) {
            case ONE:
                return "./assets/misc/speed_1x.png";
            case TWO:
                return "./assets/misc/speed_2x.png";
            case THREE:
                return "./assets/misc/speed_3x.png";
            case FIVE:
                return "./assets/misc/speed_5x.png";
            case TEN:
                return "./assets/misc/speed_10x.png";
            default:
                throw new RuntimeException("Invalid speed: " + this.game.getSpeed());
        }
    }

    @Override
    public void paint(BetterGraphics graphics) {

    }
}
