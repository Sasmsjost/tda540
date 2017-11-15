package lcrgame;

public class Game {
    private static final Player discardPile = null;

    private Player[] players;
    private int currentPlayerIndex = 0;
    // If this would be made into an array list of String[], the whole game could be
    // captured and replayed.
    private String[] lastResult = new String[]{};

    public Game(Player[] players){
       this.players = players;
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
    public void step(){
        Player player = getCurrentPlayer();
        String[] result = player.roll();
        for (String diceResult : result){
            switch (diceResult) {
                case "L":
                    player.giveBadgeTo(getPlayerToTheLeft());
                    break;
                case "C":
                    player.giveBadgeTo(discardPile);
                    break;
                case "R":
                    player.giveBadgeTo(getPlayerToTheRight());
                    break;
                default:
            }
        }
        lastResult = result;
        endRound();
    }

    private void endRound() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.length) {
            currentPlayerIndex = 0;
        }

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
