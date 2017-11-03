public class Uppgift5 {
    private Dice randic = new Dice(6);

    public Uppgift5(){
        for(int i = 1; i < 101; i++){
            randic.roll();
        }
    }
}
