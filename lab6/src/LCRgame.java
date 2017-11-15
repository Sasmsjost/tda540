public class LCRgame {
    private Player[] players = new Player[3];
    private int playerIndex = 0;

    public LCRgame(){
        players[0] = new Player();
        players[1] = new Player();
        players[2] = new Player();
    }

    public String[] getResultForCurrentRound(){
        return players[playerIndex].getResult();
    }

    public Player[] getPlayers(){
        return players;
    }

    public void nextPlayer() {
        if (playerIndex < 2) {
            System.out.println(playerIndex);
            playerIndex++;
        } else {
            playerIndex = 0;
        }
    }

    public Player getCurrentPlayer(){
        return players[playerIndex];
    }

    public Player getPlayerToTheLeft(){
        int index = getCurrentPlayerIndex();
        if (index == 0){
            return players[2];
        } else {
            return players[index-1];
        }
    }

    public Player getPlayerToTheRight(){
        int index = getCurrentPlayerIndex();
        if (index == 2){
            return players[0];
        } else {
            return players[index+1];
        }
    }

    public int getCurrentPlayerIndex(){
        return playerIndex;
    }

    public void playRound(){
        Player player = players[playerIndex];
        player.rollBadgeDices();
        for (String result : player.getResult()){
            switch (result) {
                case "L":
                    caseL();
                    break;
                case "C":
                    caseC();
                    break;
                case "R":
                    caseR();
                    break;
                default:
            }
        }
    }

    private void caseL(){
        Badge badge = getCurrentPlayer().giveAwayBadge();
        Player playerToTheLeft = getPlayerToTheLeft();
        try {
            playerToTheLeft.recieveBadge(badge);
            System.out.println("gave away one badge to the left");
        } catch (Exception e) {
            System.out.println("Dice showed L, but the player to the left " +
                    "already had 3 badges");
        }
    }

    private void caseC(){
        getCurrentPlayer().giveAwayBadge();
        System.out.println("threw away one badge");
    }

    private void caseR() {
        Badge badge = getCurrentPlayer().giveAwayBadge();
        Player playerToTheRight = getPlayerToTheRight();
        try {
            playerToTheRight.recieveBadge(badge);
            System.out.println("gave away one badge to the left");
        } catch (Exception e) {
            System.out.println("Dice showed L, but the player to the left " +
                    "already had 3 badges");
        }
    }

    public boolean gameOver(){
        int badgeMem = 0;
        for (Player player: players) {
            if (player.getBadeges().size() == 0) {
                badgeMem++;
            }

        }
        return (badgeMem == 2);
    }

    public Player getWinner(){
        Player temp = null;
        for (Player player: players) {
            if (player.getBadeges().size() == 1) {
                return player;
            }
        }
        return temp;
    }
}
