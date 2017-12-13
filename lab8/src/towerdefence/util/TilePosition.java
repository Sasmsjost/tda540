package towerdefence.util;

/**
 * Represents a position in a grid
 */
public class TilePosition extends Position<Integer> {
    public TilePosition(int x, int y) {
        super(x, y);
    }

    /**
     * @param position The position to the x/y values from
     * @throws IllegalArgumentException If position is null
     */
    public TilePosition(WorldPosition position) {
        super(0, 0);
        if (position == null) {
            throw new IllegalArgumentException("A valid position must be provided when constructing a TilePosition");
        } else {
            this.setX(Math.round(position.x));
            this.setY(Math.round(position.y));
        }
    }
}
