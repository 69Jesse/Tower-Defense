package enemies.implementations;

import enemies.Enemy;
import game.Game;


/**
 * A regular enemy.
 */
public class RegularEnemy extends Enemy {
    private static final int WORTH = 10;
    private static final int WEIGHT = 1;
    private static final int MAX_HEALTH = 50;
    private static final double SPEED = 0.05;
    private static final double SIZE = 4.0;
    private static final boolean FLYING = false;

    /**
     * Constructs a regular enemy.
     * 
     * @param game The game this enemy is in.
     */
    public RegularEnemy(Game game) {
        super(
            game,
            WORTH,
            WEIGHT,
            MAX_HEALTH,
            SPEED,
            SIZE,
            FLYING
        );
    }

    @Override
    public String getImagePath() {
        return "./assets/enemies/regular_enemy.png";
    }
}
