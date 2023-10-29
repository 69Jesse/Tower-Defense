/*
 * Properly launch the game with this file.
 * Make sure your current working directory is the same as the directory
 * of this file to make sure the game can find all the images it will use.
 * 
 * @author Aniek Goeree
 * @ID 1974459
 * @author Jesse Janssen
 * @ID 1992538
 * 
 */

import game.Game;


/**
 * The launcher of the game.
 */
public class Launcher {
    public static void main(String[] args) {
        Game game = new Game();
        game.run(getSeed(args));
    }

    /**
     * Returns the seed from the given arguments, or null if no seed was given.
     * 
     * @param args The arguments.
     * @return     The seed from the given arguments, or null.
     */
    public static Long getSeed(String[] args) {
        if (args.length == 0) {
            return null;
        }
        if (args.length > 1) {
            System.out.println("Too many arguments.");
            System.exit(1);
        }
        try {
            return Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            System.out.println(String.format("'%s' is not a valid seed.", args[0]));
            System.exit(1);
        }
        return null;
    }
}
