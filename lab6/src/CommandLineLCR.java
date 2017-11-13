import java.util.Scanner;

/**
 * This class is a skeleton, don't change the over all
 * structure just uncomment and add code where needed (TODOs)
 */
public class CommandLineLCR {

  public static void main(String[] args) {
    // Use this as an test area when starting out
    // I.e. instantiate objects and test
    // Later just comment out (don't erase the test code!)


    // Uncomment this when you have a model
    //new CommandLineLCR().run();
  }

  // Uncomment below when possible
  public void run() {
    boolean done = false;
    // ... = buildLCRGame();
    System.out.println("LCR started");
    System.out.print("Players are ");
    //render( ... );
    Scanner s = new Scanner(System.in);
    while (!done) {
      //System.out.println("Player is " + ...);
      System.out.print("> ");
      String cmd = s.nextLine();
      switch (cmd) {
        case "r":
          // What to do here?
          break;
        case "q":
          done = true;
          break;
        default:
          System.out.println("?");
      }
    }

        /* TODO
        if ( ... ) {
            System.out.println("Game over! Winner is " + lcr.getWinner());
        } else {
            render(lcr);
            System.out.println("Game aborted");
        }*/
  }

    /*  TODO
    private ... buildLCRGame() {
        //return ...
    }
    */

    /* TODO
    private void render( ... ) {
        // This needs overridden toString method to work!
        for (String s : lcr.getResult()) {
            System.out.print(s + "  ");
        }
        System.out.println();
        for (Player p : lcr.getPlayers()) {
            System.out.print(p + " ");
        }
        System.out.println();
    }*/

}
