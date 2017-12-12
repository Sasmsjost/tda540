package towerdefence;

import towerdefence.go.GameObject;
import towerdefence.go.Goal;
import towerdefence.go.Monster;
import towerdefence.go.Tower;

import java.util.function.Function;
import java.util.stream.Stream;

public interface World {
    void step(long delta);

    long now();

    long delta();

    WorldMap getMap();

    Stream<Tower> getTowers();

    Stream<Monster> getMonsters();

    Stream<Goal> getGoal();

    Stream<GameObject> getGameObjects();

    void addGameObjectAddedListener(Function<GameObject, Void> callback);

    boolean isGameWon();

    boolean isGameLost();

    void add(GameObject gameObject);
}
