public class Uppgift6 {
    public static void main(String[] args) {
        Player1 player = new Player1("otto", 5);
        int n = 0;
        while (n < 20) {
            System.out.print(player.dice.roll());
            System.out.print(player.name);
            n++;
        }
    }
}
