package enemies.implementations;

import enemies.Enemy;
import game.Game;


/**
 * A tank enemy.
 */
public class TankEnemy extends Enemy {
    private static final int VALUE = 50;
    private static final int WEIGHT = 3;
    private static final int MAX_HEALTH = 1000;
    private static final double SPEED = 0.02;
    private static final double SIZE = 4.5;
    private static final boolean FLYING = false;

    /**
     * Constructs a tank enemy.
     * 
     * @param game The game this enemy is in.
     */
    public TankEnemy(Game game) {
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
        return "./assets/enemies/tank_enemy.png";
    }
}
