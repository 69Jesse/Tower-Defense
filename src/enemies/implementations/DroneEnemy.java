package enemies.implementations;

import enemies.Enemy;
import game.Game;


/**
 * A drone enemy.
 */
public class DroneEnemy extends Enemy {
    private static final int VALUE = 20;
    private static final int WEIGHT = 1;
    private static final int MAX_HEALTH = 100;
    private static final double SPEED = 0.15;
    private static final double SIZE = 3.0;
    private static final boolean FLYING = true;

    /**
     * Constructs a drone enemy.
     * 
     * @param game The game this enemy is in.
     */
    public DroneEnemy(Game game) {
        super(
            game,
            VALUE,
            WEIGHT,
            MAX_HEALTH,
            SPEED,
            SIZE,
            FLYING
        );
    }

    @Override
    public String getImagePath() {
        return "./assets/enemies/drone_enemy.png";
    }
}
