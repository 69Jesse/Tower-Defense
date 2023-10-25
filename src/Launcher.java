import game.Game;


/**
 * The launcher class.
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
