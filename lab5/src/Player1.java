public class Player1 {
    public String name;
    public Dice dice;

    public Player1(String playerName, int sides) {
        this.name = playerName;
        this.dice = new Dice(sides);
    }



}


