import java.util.Random;

public class Dice {
    private int sides;
    public int result;

    public Dice(int sides){
        this.sides = sides;
    }

    public int getSides(){
        return this.sides;
    }

    public int roll(){
        Random r = new Random();
        int ran = r.nextInt(this.sides)+1;
        this.result = ran;
        return ran;
    }

}
