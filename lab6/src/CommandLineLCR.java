import java.util.Scanner;

/**
 * This class is a skeleton, don't change the over all
 * structure just uncomment and add code where needed (TODOs)
 */
public class CommandLineLCR {

    public static void main(String[] args) {
        run();
    }

    // Uncomment below when possible
    public static void run() {
        boolean done = false;
        LCRgame game = new LCRgame();
        System.out.println("LCR started");
        System.out.println();
        Scanner s = new Scanner(System.in);
        while (!done) {
            System.out.println("Current player is " + game.getCurrentPlayer());
            System.out.print("> ");
            String cmd = s.nextLine();
            switch (cmd) {
                case "r":
                    game.playRound();
                    render(game);
                    game.nextPlayer();
                    break;
                case "q":
                    done = true;
                    break;
                default:
                    System.out.println("?");
            }
            if (game.gameOver()) {
                done = true;
                System.out.println("player " + game.getWinner() + "won");
            }
        }
    }

    public void checkGameOver() {
        if () {
            System.out.println("Game over! Winner is " + .getWinner());
        } else {
            render(lcr);
            System.out.println("Game aborted");
        }

    /*  TODO
    private ... buildLCRGame() {
        //return ...
    }
    */


        static void render(LCRgame game){
            // This needs overridden toString method to work!
            System.out.println("result for current player " + game.getCurrentPlayer() + ":");
            for (String s : game.getResultForCurrentRound()) {
                System.out.print(s + " ");
            }
            System.out.println();
            for (Player p : game.getPlayers()) {
                System.out.println(p + " is in the game, and have badges: " + p.getBadeges().size());
            }
            System.out.println();
        }
    }
