package towerdefence;

import com.sun.istack.internal.NotNull;
import towerdefence.util.TilePosition;
import towerdefence.util.Waypoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class WorldMap {
    public static final int GRASS = 0;
    public static final int ROAD = 1;
    public static final int TOWER = 2;
    public static final int MONSTER = 3;
    public static final int GOAL = 4;

    private int[][] map;

    /**
     * @param map An array to use as the map. The map will be transposed
     *            to allow for nicer array representation in Java.
     */
    public WorldMap(@NotNull int[][] map) {
        if (map.length == 0) {
            throw new IllegalArgumentException("The provided map must be at least a 1x1");
        }
        this.map = map;
    }

    public int getWidth() {
        return this.map[0].length;
    }

    public int getHeight() {
        return this.map.length;
    }

    public int getTypeAt(TilePosition pos) {
        return getTypeAt(pos.getX(), pos.getY());
    }

    public int getTypeAt(int x, int y) {
        // Intentional flip of dimensions
        return map[y][x];
    }

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

    public Waypoint generatePathTo(TilePosition start, TilePosition[] end, int targetType) {
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

            int index = (int) Math.floor((unvisitedNeighbours.length - 1) * Math.random() + 0.5);
            currentPos = unvisitedNeighbours[index];
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
