public class Player2 {
    private String name;
    private DiceCup diceCup;
    private int accumulatedResult = 0;

    public Player2(String playerName, DiceCup diceCup) {
        this.name = playerName;
        this.diceCup = diceCup;
    }

    public String getName() {
        return name;
    }

    public int rollDice() {
        accumulatedResult += diceCup.rollCup();
        return accumulatedResult;
    }

    public int getAccumulatedResult() {
        return accumulatedResult;
    }

    @Override
    public String toString() {
        return String.format("Player{name=\"%s\", result=%d}", name, accumulatedResult);
    }
}
