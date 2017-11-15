package lcrgame;

import java.util.Random;

public class Dice {
    // Share a instance of random between classes to ensure that random actually is random.
    private static final Random random = new Random();
    // Share sides between classes and ensure that it's reference is immutable.
    private static final String[] sides = {"L", "C", "R", ".", ".", "."};

    public String roll(){
        int index = random.nextInt(sides.length);
        return sides[index];
    }
}
