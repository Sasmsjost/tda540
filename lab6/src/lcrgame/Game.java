package lcrgame;

public class Game {
    private Player[] players;
    private Dice[] dices;
    private int currentPlayerIndex = 0;
    // If this would be made into an array list of String[], the whole game could be
    // captured and replayed.
    private String[] lastResult = new String[]{};

    public Game(Player[] players, Dice[] dices){
       this.players = players;
       this.dices = dices;
    }

    // Get the result from the previous round
    public String[] getResult(){
        return lastResult;
    }

    public Player[] getPlayers(){
        return players;
    }


    public Player getCurrentPlayer(){
        return players[currentPlayerIndex];
    }

    private Player getPlayerToTheLeft(){
        if (currentPlayerIndex == 0){
            return players[players.length-1];
        } else {
            return players[currentPlayerIndex -1];
        }
    }

    private Player getPlayerToTheRight(){
        int maxIndex = players.length-1;
        if (currentPlayerIndex >= maxIndex){
            return players[0];
        } else {
            return players[currentPlayerIndex +1];
        }
    }


    // Simulate one round and move to the next player.
    // We ensure that the player will never give away more
    // badges than they own since we can not roll more
    // dices than the badge count
    public void step(){
        Player player = getCurrentPlayer();
        int rolls = getNumAllowedRolls();
        String[] result = roll(rolls);
        for (String diceResult : result){
            switch (diceResult) {
                case "L":
                    getPlayerToTheLeft().addBadge();
                    player.removeBadge();
                    break;
                case "C":
                    player.removeBadge();
                    break;
                case "R":
                    getPlayerToTheRight().addBadge();
                    player.removeBadge();
                    break;
                default:
            }
        }
        lastResult = result;
        endRound();
    }

    private int getNumAllowedRolls() {
        int rolls = getCurrentPlayer().getBadgeCount();
        int maxRolls = dices.length;
        if(rolls > maxRolls) {
            rolls = maxRolls;
        }

        return rolls;
    }

    private String[] roll(int rolls) {
        String[] result = new String[rolls];
        for (int i = 0; i < rolls; i++) {
            result[i] = dices[i].roll();
        }
        return result;
    }

    private void endRound() {
        currentPlayerIndex = (currentPlayerIndex+1) % players.length;

        // #hasWinner() ensures that there will be at least one player
        // who can play so this will not recurse forever.
        int badges = getCurrentPlayer().getBadgeCount();
        if(badges == 0 && !hasWinner()) {
            // End round immediately if the player is unable to play
            endRound();
        }
    }


    // Get the winner of the game, will return null if no winner exists
    public Player getWinner(){
        if(hasWinner()) {
            for (Player player : players) {
                if (player.getBadgeCount() > 0) {
                    return player;
                }
            }
        }
        return null;
    }

    private boolean hasWinner(){
        int playersWithBadges = 0;
        for (Player player: players) {
            if(player.getBadgeCount() > 0){
                playersWithBadges++;
            }
        }
        return playersWithBadges <= 1;
    }
}
