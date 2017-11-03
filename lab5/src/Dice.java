import java.util.Random;

public class Dice {
    // Reusing an instance of random ensures that we get an even distribution of random numbers.
    private static Random random = new Random();
    private int sides;
    private int lastRoll;

    public Dice(int sides){
        this.sides = sides;
    }

    public int getSides(){
        return this.sides;
    }

    public int roll(){
        int rollValue = random.nextInt(this.sides)+1;
        this.lastRoll = rollValue;
        return rollValue;
    }

}
