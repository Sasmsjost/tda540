import java.util.Random;

public class LCRdice {
    // Reusing an instance of random ensures that we get an even distribution of random numbers.
    private String[] sides = {"L", "C", "R", ".", ".", "."};
    private String result;

    public String[] getSides(){
            return this.sides;
        }

    public String roll(){
        String randomeSide = sides[new Random().nextInt(sides.length)];
        this.result=randomeSide;
        return randomeSide;
    }

    @Override
    public String toString() {
            return String.format("Dice{result=%s}", result);
        }
}
