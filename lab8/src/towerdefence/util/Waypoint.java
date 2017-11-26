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

    // Since we only need to move in a single direction at a time,
    // we only have to ensure that a single dimensional check
    // is true to return tha the position has passed
    public boolean hasPassedPosition(WorldPosition position) {
        TilePosition direction = getDirection();
        int dx = this.position.x - (int) (float) position.x;
        int dy = this.position.y - (int) (float) position.y;

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
