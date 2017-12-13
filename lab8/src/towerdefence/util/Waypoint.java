package towerdefence.util;

/**
 * A doubly linked list node describing a waypoint.
 * The succeeding node can be gotten using Waypoint#getNext()
 *
 * Because doubly linked lists are needed.
 */
public class Waypoint {
    private Waypoint previous;
    private Waypoint next;
    private TilePosition position;

    /**
     * @param position The position of a certain waypoint
     * @throws IllegalArgumentException Will be thrown if position is null
     */
    public Waypoint(TilePosition position) {
        if (position == null) {
            throw new IllegalArgumentException("A valid position must be provided when constructing a Waypoint");
        }
        this.position = position;
    }

    public TilePosition getDirection() {
        return new TilePosition(position.x - previous.position.x, position.y - previous.position.y);
    }

    public int getLength() {
        int count = 0;
        Waypoint child = next;
        while (child != null) {
            count++;
            child = child.next;
        }
        return count;
    }


    /**
     * Since we only need to move in a single direction at a time,
     * we only have to ensure that a single dimensional check
     * is true to return tha the position has passed
     *
     * @param position The position to check, usually the position of a GameObject
     * @return Weather the position has been passed
     */
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
