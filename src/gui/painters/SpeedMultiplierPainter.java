package gui.painters;

import game.Game;
import gui.BetterGraphics;
import gui.Frame;
import gui.Painter;
import gui.Panel;
import location.Location;


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
        MIDDLE_LOCATION = new Location(
            this.game.field.width - this.bottomRightXOffset - SIZE / 2,
            this.game.field.height - this.bottomRightYOffset - SIZE / 2
        );
    }

    public static Location MIDDLE_LOCATION;
    public static final double SIZE = 3.0;
    private double bottomRightXOffset = 0.5;
    private double bottomRightYOffset = 0.5;

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
        graphics.drawImageCentered(
            this.getImagePath(),
            MIDDLE_LOCATION.x,
            MIDDLE_LOCATION.y,
            SIZE,
            SIZE
        );
    }
}
