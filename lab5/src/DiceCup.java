import java.util.Arrays;

public class DiceCup {

    private Dice[] dices;

    public DiceCup(Dice[] dices) {
        this.dices = dices;
    }

    public int getSumOfRolls() {
        int sum = 0;
        for(Dice dice : dices) {
            sum += dice.roll();
        }
        return sum;
    }

    @Override
    public String toString() {
        return String.format("DiceCup{dices=%s}", Arrays.toString(dices));
    }
}
