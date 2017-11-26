package towerdefence.levels;

import towerdefence.util.WorldPosition;

public class Level2 {
    private Level2() {
    }

    public static Level get() {
        return level;
    }

    private static final Level level = new Level(new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 4},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    }, new WorldPosition[]{
            new WorldPosition(3, 2),
            new WorldPosition(4, 4),
            new WorldPosition(6, 2)
    }, new WorldPosition[]{
            new WorldPosition(0, 3),
    });
}
