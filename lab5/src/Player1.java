public class Player1 {
    private String name;
    private Dice dice;

    public Player1(String playerName, Dice dice) {
        this.name = playerName;
        this.dice = dice;
    }

    public String getName() {
        return name;
    }

    public int rollDice() {
        return dice.roll();
    }



}


