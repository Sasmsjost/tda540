import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Badge> badgeList = new ArrayList<Badge>(); //you probably have a neater solution
    private String[] result = new String[3];

    public Player() {
        badgeList.add(new Badge());
        badgeList.add(new Badge());
        badgeList.add(new Badge());
    }

    public void rollBadgeDices() {
        String[] result = new String[badgeList.size()];
        for (int i = 0; i < badgeList.size(); i++) {
            result[i] = badgeList.get(i).rollDice();
        }
        this.result = result;
    }

    public String[] getResult(){
        return this.result;
    }

    public List<Badge> getBadeges(){
        return badgeList;
    }

    public Badge giveAwayBadge(){
        int lastIndex = badgeList.size() -1;
        Badge temp = badgeList.get(lastIndex);
        badgeList.remove(lastIndex);
        return temp;
    }

    public void recieveBadge(Badge badge) throws Exception{
        if (badgeList.size() == 3){
            throw new Exception();
        }
        else {
            badgeList.add(badge);
        }
    }
}
