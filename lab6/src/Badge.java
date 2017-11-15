public class Badge {
    private String id;
    private LCRdice dice;

    public Badge(){
        this.id = toString();
        this.dice = new LCRdice();
    }

    public String rollDice(){
        return this.dice.roll();
    }

    public String getId() {
        return id;
    }
}
