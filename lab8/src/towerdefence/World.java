package towerdefence;

import towerdefence.go.GameObject;
import towerdefence.go.Monster;
import towerdefence.go.Tower;

import java.util.ArrayList;
import java.util.stream.Stream;

public class World {
    private WorldMap worldMap;
    private ArrayList<GameObject> gameObjects;
    private long time = 0;
    private long delta = 0;

    public World(WorldMap worldMap) {
        this.worldMap = worldMap;
        gameObjects = new ArrayList<>();
    }

    public void step(long delta) {
        assert (delta >= 0);
        this.delta = delta;
        time += delta;
        gameObjects.forEach(go -> go.act(this));
    }

    public long now() {
        return time;
//        return System.currentTimeMillis();
    }

    public long delta() {
        return delta;
    }

    public WorldMap getMap() {
        return worldMap;
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

    public boolean isGameLost() {
        return getMonsters().anyMatch(Monster::isAtEnd);
    }

    public void add(GameObject gameObject) {
        gameObject.addToWorld(this);
        gameObjects.add(gameObject);
    }
}
