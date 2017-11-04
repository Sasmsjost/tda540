public class Player2 {
    private String name;
    private DiceCup diceCup;
    private int result;

    public Player2(String playerName, DiceCup diceCup) {
        this.name = playerName;
        this.diceCup = diceCup;
    }

    public String getName() {
        return name;
    }

    public int rollDice() {
        result = diceCup.getSumOfRolls();
        return result;
    }

    public int getLastRoll() {
        return result;
    }

    @Override
    public String toString() {
        return String.format("Player={name=\"%s\", result=%d,\n  diceCup=%s}", name, result, diceCup);
    }
}
