package lcrgame;

public class Player {
    private String[] result;
    private int badgeCount;
    private String name;
    private Dice[] dices;

    public Player(String name, Dice[] dices) {
        this.name = name;
        this.dices = dices;

        result = new String[]{};
        badgeCount = 3;
    }

    // The player rolls the dices.
    public String[] roll() {
        int rolls = badgeCount;
        int maxRolls = dices.length;
        if(rolls > maxRolls) {
            rolls = maxRolls;
        }

        String[] result = new String[rolls];
        for (int i = 0; i < rolls; i++) {
            result[i] = dices[i].roll();
        }
        this.result = result;
        return result;
    }

    // Needed to know if player is out.
    public int getBadgeCount() {
        return badgeCount;
    }

    // Used for rendering the current state of the game.
    public String[] getResult(){
        return this.result;
    }

    // Ensures that we don't increment or decrement.
    // There is a danger with using player=null as the throw pile,
    // but under current restrictions this will do.
    public void giveBadgeTo(Player player) {
        if(player != null) {
            player.receiveBadge();
        }
        badgeCount--;
    }

    // Should only be called from another player when giving badges.
    private void receiveBadge() {
        badgeCount++;
    }

    @Override
    public String toString() {
        return String.format("{%s, %d}", name, badgeCount);
    }
}
