package towerdefence.util;

/**
 * Represents an exact position in the world, should be used
 * when needing finer accuracy of the position than can be provided by TilePosition
 */
public class WorldPosition extends Position<Float> {
    public WorldPosition(float x, float y) {
        super(x, y);
    }

    /**
     * Get the shortest distance between the two points
     *
     * @param other The other position
     * @return The distance between the points
     * @throws NullPointerException if null is passed
     */
    public float distance(WorldPosition other) {
        return (float)Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2)) ;
    }
}
