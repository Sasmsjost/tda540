package towerdefence.util;

public class Waypoint {
    private Waypoint previous;
    private Waypoint next;
    private TilePosition position;

    public Waypoint(TilePosition position) {
        this.position = position;
    }

    public TilePosition getDirection() {
        return new TilePosition(position.x - previous.position.x, position.y - previous.position.y);
    }

    //doesn't seam to be used
    public int getLength() {
        int count = 0;
        Waypoint child = next;
        while (child != null) {
            count++;
            child = child.next;
        }
        return count;
    }

    // Since we only need to move in a single direction at a time,
    // we only have to ensure that a single dimensional check
    // is true to return that the position has passed
    public boolean hasPassedPosition(WorldPosition position) {
        TilePosition direction = getDirection();
        int dx = this.position.x - position.x.intValue();
        int dy = this.position.y - position.y.intValue();

        if (direction.x > 0 && dx > 0) {
            return false;
        } else if (direction.x < 0 && dx <= 0) {
            return false;
        }

        if (direction.y > 0 && dy > 0) {
            return false;
        } else if (direction.y < 0 && dy <= 0) {
            return false;
        }

        return true;
    }

    public void setNext(Waypoint waypoint) {
        next = waypoint;
    }

    public void setPrevious(Waypoint waypoint) {
        previous = waypoint;
    }

    public TilePosition getPosition() {
        return position;
    }

    public Waypoint getNext() {
        return next;
    }
}
