package game;

import field.Field;
import java.awt.Image;
import javax.swing.ImageIcon;
import towers.Tower;


/**
 * The option class.
 * Options will appear around a placeable spot/tower when it is selected.
 */
public abstract class Option {
    final Game game;
    final Field field;

    public final Image image;

    /**
     * The constructor.
     *
     * @param game  The game.
     * @param field The field.
     */
    public Option(
        Game game,
        Field field
    ) {
        this.game = game;
        this.field = field;
        this.image = new ImageIcon(this.getImagePath()).getImage();
    }

    /**
     * The callback function of this option.
     * This function is called when the option is clicked.
     */
    public abstract void callback(Tower tower);

    /**
     * Returns the image path of this tower.
     * 
     * @return The image path of this tower.
     */
    public abstract String getImagePath();
}
