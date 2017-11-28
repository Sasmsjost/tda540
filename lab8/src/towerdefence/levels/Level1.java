package towerdefence.levels;

import towerdefence.util.WorldPosition;

public final class Level1 {
    private Level1() {
    }

    public static Level get() {
        return level;
    }

    private static final Level level = new Level(new int[][]{
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 1, 0, 1, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 0, 1, 0, 0, 0},
            {0, 1, 0, 1, 0, 0, 1, 1, 1, 0},
            {0, 1, 0, 1, 1, 1, 1, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    }, new WorldPosition[]{
            new WorldPosition(4, 4),
            new WorldPosition(5, 2),
            new WorldPosition(3, 0),
            new WorldPosition(5, 7),
    }, new WorldPosition[]{
            new WorldPosition(4, 0),
    }, new WorldPosition[]{
            new WorldPosition(6, 5),
    });
}
