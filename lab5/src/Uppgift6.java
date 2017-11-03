public class Uppgift6 {
    public static void main(String[] args) {
        Dice dice1 = new Dice(6);
        Player1 player = new Player1("otto", dice1);

        System.out.println("Player is " + player.getName());
        for (int i = 0; i < 5; i++) {
            int roll = player.rollDice();
            System.out.print(roll + " ");
        }
        System.out.println("");
    }
}
