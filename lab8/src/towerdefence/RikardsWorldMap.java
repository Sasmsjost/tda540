package towerdefence;

import towerdefence.util.TilePosition;
import towerdefence.util.Waypoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * An abstraction over the world map used by the World
 * Also provides useful helper methods to minimize boilerplate code
 */
public class RikardsWorldMap implements WorldMap {
    private int[][] map;
    private int prioritizedPath;

    /**
     * @param map An array to use as the map. The map will be treated as being
     *            transposed to allow for nicer array representation in Java.
     * @param prioritizedPath The path index to prioritize, if it does not match
     *                        the path with index 0 will be used
     * @throws IllegalArgumentException if the map is smaller than 1x1 or the prioritized path is < 0
     */
    public RikardsWorldMap(int[][] map, int prioritizedPath) {
        if (map == null || map.length == 0 || map[0] == null || map[0].length == 0) {
            throw new IllegalArgumentException("The provided map must be at least a 1x1");
        }
        if (prioritizedPath < 0) {
            throw new IllegalArgumentException("The prioritizedPath must be a value larger than 0");
        }
        this.map = map;
        this.prioritizedPath = prioritizedPath;
    }

    /**
     * @return The width of the map
     */
    public int getWidth() {
        return this.map[0].length;
    }

    /**
     * @return The height of the map
     */
    public int getHeight() {
        return this.map.length;
    }

    /**
     * Get the type of tile at a point on the map
     *
     * @param pos The position to check
     * @return An int representation of the type, should be one of the types in WorldMap
     * @throws NullPointerException           if null is provided
     * @throws ArrayIndexOutOfBoundsException if an invalid position if provided
     */
    public int getTypeAt(TilePosition pos) {
        return getTypeAt(pos.getX(), pos.getY());
    }

    /**
     * Get the type of tile at a point on the map
     * @param x The x position
     * @param y The y position
     * @return An int representation of the type, should be one of the types in WorldMap
     * @throws ArrayIndexOutOfBoundsException if an invlid position if provided
     */
    public int getTypeAt(int x, int y) {
        // Intentional flip of dimensions
        return map[y][x];
    }

    /**
     * Lazily generates a stream of tile positions, this removes the need to manually iterate
     * over tiles.
     * @return A stream of tile positions
     */
    public Stream<TilePosition> getTilePositions() {
        final int size = getWidth() * getHeight();
        final int width = getWidth();
        return Stream.generate(new Supplier<TilePosition>() {
            private int x = 0;
            private int y = 0;

            @Override
            public TilePosition get() {
                TilePosition pos = new TilePosition(x, y);
                if (x + 1 >= width) {
                    x = 0;
                    y++;
                } else {
                    x++;
                }
                return pos;
            }
        }).limit(size);
    }

    /**
     * Provides an easy way of safely getting the neighbours of a certain type at a position.
     * The array will only be as long as the neighbours found and not necessarily 4 long.
     *
     * @param type The type of tiles to look for, can be found in WorldMap
     * @param p The position to look around
     * @return An array of tiles with length depending on the found neighbours
     */
    public TilePosition[] getNeighborsOfType(int type, TilePosition p) {
        int x = p.getX();
        int y = p.getY();

        int up = y - 1;
        int down = y + 1;
        int left = x - 1;
        int right = x + 1;

        ArrayList<TilePosition> neighbours = new ArrayList<>();

        if (up >= 0 && getTypeAt(x, up) == type) neighbours.add(new TilePosition(x, up));
        if (left >= 0 && getTypeAt(left, y) == type) neighbours.add(new TilePosition(left, y));
        if (down < getHeight() && getTypeAt(x, down) == type) neighbours.add(new TilePosition(x, down));
        if (right < getWidth() && getTypeAt(right, y) == type) neighbours.add(new TilePosition(right, y));

        return neighbours.toArray(new TilePosition[]{});
    }

    /**
     * Generate a Waypoint describing the path between two points
     * @param start The start position
     * @param end A list of possible positions to stop at, will stop at the first one
     * @param targetType The type of tiles which can be treated as a path
     * @return A Waypoint describing the path
     * @throws IllegalArgumentException if start or end are null and if end is empty
     * @throws RuntimeException if no path is found
     */
    public Waypoint generatePathTo(TilePosition start, TilePosition[] end, int targetType) {
        if (start == null || end == null || end.length == 0) {
            throw new IllegalArgumentException("Both a start and an end position must be provided");
        }
        ArrayList<TilePosition> tilePositions = new ArrayList<>();

        tilePositions.add(start);
        Waypoint currentWaypoint = new Waypoint(start);
        currentWaypoint.setPrevious(currentWaypoint);
        Waypoint firstWaypoint = currentWaypoint;

        TilePosition currentPos = start;

        boolean isAtGoal = false;
        while (!isAtGoal) {
            TilePosition[] neighbours = getNeighborsOfType(WorldMap.ROAD, currentPos);
            TilePosition[] unvisitedNeighbours = Arrays.stream(neighbours)
                    .filter(n -> !tilePositions.contains(n))
                    .toArray(TilePosition[]::new);

            if (unvisitedNeighbours.length <= 0) {
                throw new RuntimeException("Can not fin end of path");
            }

            if (prioritizedPath < unvisitedNeighbours.length) {
                currentPos = unvisitedNeighbours[prioritizedPath];
            } else {
                currentPos = unvisitedNeighbours[0];
            }
            tilePositions.add(currentPos);

            Waypoint nextWaypoint = new Waypoint(currentPos);
            currentWaypoint.setNext(nextWaypoint);
            nextWaypoint.setPrevious(currentWaypoint);
            currentWaypoint = nextWaypoint;

            final TilePosition maybeGoalPos = currentPos;
            isAtGoal = Arrays.stream(end).anyMatch(pos -> pos.equals(maybeGoalPos));
        }

        return firstWaypoint;
    }
}
