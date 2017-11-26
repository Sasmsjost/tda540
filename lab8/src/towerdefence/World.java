package towerdefence;

import towerdefence.go.GameObject;
import towerdefence.go.Monster;
import towerdefence.go.Tower;

import java.util.ArrayList;
import java.util.stream.Stream;

public class World {
    int[][] map;
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

    public Iterable<GameObject> getGameObjects() {
        return gameObjects;
    }
}
