package lcrgame;

public class Player {
    // Badge count will always be > 0
    private int badgeCount;
    private String name;

    public Player(String name, int badgeCount) {
        this.name = name;
        setBadgeCount(badgeCount);
    }

    public int getBadgeCount() {
        return badgeCount;
    }

    private void setBadgeCount(int count) {
        if(count < 0) {
            throw new ArithmeticException("Badge count can not be less than 0");
        }

        badgeCount = count;
    }

    public String getName(){
        return name;
    }

    public void addBadge() {
        setBadgeCount(badgeCount+1);
    }

    public void removeBadge() {
        setBadgeCount(badgeCount-1);
    }

    @Override
    public String toString() {
        return String.format("{%s, %d}", name, badgeCount);
    }
}
