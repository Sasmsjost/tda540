import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LCRplayer {
    public List<Badge> badgeList = new ArrayList<Badge>(); //you probably have a neater solution

    public LCRplayer() {
        badgeList.add(new Badge());
        badgeList.add(new Badge());
        badgeList.add(new Badge());
    }

    public Badge giveAwayBadge(){
        Badge temp = badgeList.get(-1);
        badgeList.remove(-1);
        return temp;
    }


}
