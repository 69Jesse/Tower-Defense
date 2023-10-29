package game;

import enemies.Enemy;
import enemies.implementations.DroneEnemy;
import enemies.implementations.RegularEnemy;
import enemies.implementations.TankEnemy;
import java.util.ArrayList;
import java.util.Collections;


/**
 * The WaveHandler class is responsible for spawning enemies in waves.
 */
public class WaveHandler {
    private Game game;

    private int waveNumber;
    private ArrayList<Enemy> enemies;
    private int spawnIndex;
    private int ticksUntilNextSpawn;
    private int ticksUntilNextWave;

    /**
     * Constructs a new WaveHandler.
     * 
     * @param game The game this WaveHandler is in.
     */
    public WaveHandler(Game game) {
        this.game = game;
        this.waveNumber = 0;
        this.enemies = new ArrayList<>();
        // Wait some time before starting the first wave.
        this.ticksUntilNextWave = this.game.ticksPerSecond;
    }

    /**
     * Returns a random element from an array, with weights.
     * 
     * @param <T>     The type of the elements in the array.
     * @param array   The array to choose from.
     * @param weights The weights of the elements in the array.
     * @return        A random element from the array.
     */
    private <T> T randomChoice(
        ArrayList<T> array,
        ArrayList<Integer> weights
    ) {
        if (array.size() != weights.size()) {
            throw new RuntimeException("Array and weights must be same size");
        }

        int totalWeight = 0;
        for (int weight : weights) {
            totalWeight += weight;
        }

        int random = (int) (this.game.fieldRandom.nextDouble() * totalWeight);
        int sum = 0;
        for (int i = 0; i < array.size(); i++) {
            sum += weights.get(i);
            if (random < sum) {
                return array.get(i);
            }
        }

        throw new RuntimeException("Should never happen");
    }

    /**
     * Returns an array of enemy types that can be spawned.
     * 
     * @return An array of enemy types that can be spawned.
     */
    private Enemy[] getEnemyTypes() {
        return new Enemy[] {
            new RegularEnemy(this.game),
            new DroneEnemy(this.game),
            new TankEnemy(this.game)
        };
    }

    /**
     * Returns the value of the current wave.
     * This is used to determine which enemies to spawn.
     * 
     * @return The value of the current wave.
     */
    public int getWaveValue() {
        return (int) Math.pow(this.waveNumber, 1.1) * 10;
    }

    /**
     * Returns the maximum wave number until the game is won.
     * 
     * @return The maximum wave number.
     */
    public int getMaxWave() {
        return 100;
    }

    /**
     * Returns the current wave number.
     * 
     * @return The current wave number.
     */
    public int getWaveNumber() {
        return this.waveNumber;
    }

    private void newTicksUntilNextSpawn() {
        this.ticksUntilNextSpawn = (int) (
            this.game.ticksPerSecond * 0.1
            + this.game.fieldRandom.nextDouble() * this.game.ticksPerSecond * 0.4
            );  // Checker only accepts this indentation for some reason
    }

    /**
     * Sets the ticks until the next wave.
     * Note that this starts counting down
     * when the last wave is done spawning.
     */
    private void newTicksUntilNextWave() {
        this.ticksUntilNextWave = this.game.ticksPerSecond * 10;
    }

    /**
     * Generates a new wave of enemies.
     * Note that this does not spawn the enemies.
     */
    private void generateNewEnemies() {
        this.enemies = new ArrayList<Enemy>();

        int waveValue = this.getWaveValue();

        while (waveValue > 0) {
            ArrayList<Enemy> choices = new ArrayList<Enemy>();
            ArrayList<Integer> weights = new ArrayList<Integer>();
            for (Enemy enemy : this.getEnemyTypes()) {
                if (enemy.weight > waveValue) {
                    continue;
                }
                choices.add(enemy);
                weights.add(enemy.weight);
            }
            if (choices.size() == 0) {
                break;
            }

            Enemy enemy = this.randomChoice(choices, weights);
            this.enemies.add(enemy);
            waveValue -= enemy.weight;
        }

        Collections.shuffle(this.enemies, this.game.fieldRandom);
        this.spawnIndex = 0;
        this.newTicksUntilNextSpawn();
    }

    /**
     * Spawns the next enemy in the wave.
     */
    private void spawnNextEnemy() {
        Enemy enemy = this.enemies.get(this.spawnIndex);
        this.game.field.addEnemy(enemy);
        this.spawnIndex++;
        this.newTicksUntilNextSpawn();
    }

    /**
     * Returns whether the wave is done spawning.
     * 
     * @return Whether the wave is done spawning.
     */
    private boolean isDoneSpawning() {
        return this.spawnIndex >= this.enemies.size();
    }

    /**
     * Starts a new wave.
     */
    private void startNewWave() {
        this.waveNumber++;
        this.generateNewEnemies();
        this.newTicksUntilNextWave();
    }

    /**
     * This method is called every tick to update the wave.
     */
    public void tick() {
        if (!this.game.isRunning()) {
            // Should not happen, but just in case.
            return;
        }
        if (!this.isDoneSpawning()) {
            if (this.ticksUntilNextSpawn > 0) {
                this.ticksUntilNextSpawn--;
                return;
            }
            this.spawnNextEnemy();
            return;
        }
        if (this.maxWaveReached()) {
            return;
        }
        if (this.ticksUntilNextWave > 0) {
            this.ticksUntilNextWave--;
            if (this.game.field.noEnemiesAlive()) {
                this.ticksUntilNextWave = Math.min(
                    this.ticksUntilNextWave,
                    this.game.ticksPerSecond / 2
                );
            }
            return;
        }
        this.startNewWave();
    }

    /**
     * Returns whether the maximum wave has been reached.
     * 
     * @return Whether the maximum wave has been reached.
     */
    private boolean maxWaveReached() {
        return this.waveNumber >= this.getMaxWave();
    }

    /**
     * Returns whether the game is completely done.
     * 
     * @return Whether the game is completely done.
     */
    public boolean isCompletelyDone() {
        return this.maxWaveReached()
            && this.isDoneSpawning()
            && this.game.field.noEnemiesAlive();
    }
}
