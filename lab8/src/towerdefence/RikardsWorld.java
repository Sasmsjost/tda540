package towerdefence;

import towerdefence.go.GameObject;
import towerdefence.go.Goal;
import towerdefence.go.Monster;
import towerdefence.go.Tower;

import java.util.ArrayList;
import java.util.stream.Stream;

public class RikardsWorld implements World {
    private WorldMap worldMap;
    private ArrayList<GameObject> gameObjects;
    private long time = 0;
    private long delta = 0;

    public RikardsWorld(WorldMap worldMap) {
        this.worldMap = worldMap;
        gameObjects = new ArrayList<>();
    }

    public void step(long delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("Delta must be more than 0");
        }
        this.delta = delta;
        time += delta;
        gameObjects.forEach(go -> go.act(this));
    }

    public long now() {
        return time;
    }

    public long delta() {
        return delta;
    }

    public WorldMap getMap() {
        return worldMap;
    }

    private <T extends GameObject> Stream<T> get(Class<T> type) {
        return gameObjects.stream()
                .filter(type::isInstance)
                .map(go -> (T) go);
    }

    public Stream<Tower> getTowers() {
        return get(Tower.class);
    }

    public Stream<Monster> getMonsters() {
        return get(Monster.class);
    }

    public Stream<Goal> getGoal() {
        return get(Goal.class);
    }

    public Stream<GameObject> getGameObjects() {
        return gameObjects.stream();
    }

    public boolean isGameWon() {
        return this.getMonsters().allMatch(Monster::isDead);
    }

    public boolean isGameLost() {
        return this.getMonsters().anyMatch(Monster::isAtEnd);
    }

    public void add(GameObject gameObject) {
        gameObject.addToWorld(this);
        gameObjects.add(gameObject);
    }
}
