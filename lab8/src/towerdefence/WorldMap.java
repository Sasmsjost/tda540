package towerdefence;

import towerdefence.util.TilePosition;
import towerdefence.util.Waypoint;

import java.util.stream.Stream;

public interface WorldMap {
    int GRASS = 0;
    int ROAD = 1;
    int TOWER = 2;
    int MONSTER = 3;
    int GOAL = 4;

    int getWidth();

    int getHeight();

    int getTypeAt(TilePosition pos);

    Stream<TilePosition> getTilePositions();

    TilePosition[] getNeighborsOfType(int type, TilePosition p);

    Waypoint generatePathTo(TilePosition start, TilePosition[] end, int targetType);
}
