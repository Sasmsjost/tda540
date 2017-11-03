public class Uppgift5 {
    public static void main(String[] args){
        Dice dice = new Dice(6);
        for(int i = 0; i < 100; i++){
            int value = dice.roll();
            System.out.print(value + " ");
        }
        System.out.println("");
    }
}
