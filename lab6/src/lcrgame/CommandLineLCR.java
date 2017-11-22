package lcrgame;

import java.util.Scanner;

/**
 * This class is a skeleton, don't change the over all
 * structure just uncomment and add code where needed (TODOs)
 */
public class CommandLineLCR {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        Game game = buildLCRGame();
        System.out.println("LCR started");
        System.out.println();
        render(game);
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("\n\nCurrent player is " + game.getCurrentPlayer());
            System.out.print("> ");
            String cmd = s.nextLine();
            switch (cmd) {
                case "r":
                    game.step();
                    render(game);
                    break;
                case "q":
                    return;
                default:
                    System.out.println("That is not a valid command. Valid commands are (r)oll or (q)uit");
            }

            Player winner = game.getWinner();
            if (winner != null) {
                System.out.println();
                System.out.println("Player " + winner + " won!");
                return;
            }
        }
    }

    private static Game buildLCRGame() {
        int startingBadges = 3;

        Dice[] dices = new Dice[3];
        for (int i = 0; i < dices.length; i++) {
            dices[i] = new Dice();
        }

        Player[] players = new Player[]{
                new Player("Bengt-Arne", startingBadges),
                new Player("Per-Bengt", startingBadges),
                new Player("Sibylla", startingBadges)
        };

        return new Game(players, dices);
    }

    static void render(Game game) {
        for (String s : game.getResult()) {
            System.out.print(s + " ");
        }
        System.out.println();

        for (Player p : game.getPlayers()) {
            System.out.print(p + " ");
        }
        System.out.println();
    }
}
