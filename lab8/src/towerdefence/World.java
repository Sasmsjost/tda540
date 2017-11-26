package towerdefence;

import towerdefence.go.GameObject;
import towerdefence.go.Monster;
import towerdefence.go.Tower;
import towerdefence.util.TilePosition;
import towerdefence.util.Waypoint;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class World {
    private int[][] map;
    private ArrayList<GameObject> gameObjects;

    public World(int[][] map) {
        this.map = map;
        gameObjects = new ArrayList<>();
    }

    public Waypoint getPathFrom(TilePosition start) {
        return Waypoint.generatePath(start, map);
    }

    public void step() {
        gameObjects.forEach(go -> go.act(this));
    }

    public int getTypeAt(TilePosition pos) {
        return map[pos.getX()][pos.getY()];
    }


    public Stream<TilePosition> getTilePositions() {
        final int size = map.length*map[0].length;
        final int width = map.length;
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

    public Stream<Monster> getMonsters() {
        return gameObjects.stream()
                .filter(gameObject -> gameObject instanceof Monster)
                .map(Monster.class::cast);
    }

    public Stream<Tower> getTowers() {
        return gameObjects.stream()
                .filter(gameObject -> gameObject instanceof Tower)
                .map(Tower.class::cast);
    }

    public Stream<GameObject> getGameObjects() {
        return gameObjects.stream();
    }


    public boolean isGameWon() {
        return getMonsters().allMatch(Monster::isDead);
    }

    public boolean isGameOver() {
        return getMonsters().anyMatch(Monster::isAtEnd);
    }

    public void add(GameObject gameObject) {
        gameObject.addToWorld(this);
        gameObjects.add(gameObject);
    }
}
