package towerdefence;

import towerdefence.go.GameObject;
import towerdefence.go.Goal;
import towerdefence.go.Monster;
import towerdefence.go.Tower;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * A simulatable world describing the current state of the game
 */
public class RikardsWorld implements World {
    private WorldMap worldMap;
    private ArrayList<GameObject> gameObjects;
    private long time = 0;
    private long delta = 0;

    public RikardsWorld(WorldMap worldMap) {
        if (worldMap == null) {
            throw new IllegalArgumentException("A valid map must be provided to the world, null found");
        }

        this.worldMap = worldMap;
        gameObjects = new ArrayList<>();
    }

    /**
     * @param delta The amount of time which should be simulated,
     *              lower values mean more precise simulations
     * @throws IllegalArgumentException if delta < 0
     */
    public void step(long delta) {
        if (delta < 0) {
            throw new IllegalArgumentException("Delta must be more than 0");
        }
        this.delta = delta;
        time += delta;
        gameObjects.forEach(go -> go.act(this));
    }

    /**
     * Get the current time in the game, comparable to "System.currentTimeMillis();"
     *
     * @return Current timestamp
     */
    public long now() {
        return time;
    }

    /**
     * Get the delta between the current and previous frame,
     * this will be the value provided to step when simulating
     * a frame.
     *
     * @return Delta time
     */
    public long delta() {
        return delta;
    }

    /**
     * Get the current map of the world
     *
     * @return The map
     */
    public WorldMap getMap() {
        return worldMap;
    }

    /**
     * Get gameobjects of a certain type
     *
     * @param type The class of the type of object which will be returned
     * @param <T>  The type of objects to filter by
     * @return A stream with game objects of a certain type
     */
    private <T extends GameObject> Stream<T> get(Class<T> type) {
        return gameObjects.stream()
                .filter(type::isInstance)
                .map(go -> (T) go);
    }

    /**
     * Helper method for #get
     * @return A stream of all towers in the game
     */
    public Stream<Tower> getTowers() {
        return get(Tower.class);
    }

    /**
     * Helper method for #get
     * @return A stream of all monster in the game
     */
    public Stream<Monster> getMonsters() {
        return get(Monster.class);
    }

    /**
     * Helper method for #get
     * @return A stream of all goals in the game
     */
    public Stream<Goal> getGoal() {
        return get(Goal.class);
    }

    /**
     * The returns all types of game objects, including towers, monsters and goals
     * @return A stream of all game objects in the game
     */
    public Stream<GameObject> getGameObjects() {
        return gameObjects.stream();
    }

    /**
     * Returns true if the game is won, false otherwise
     * @return If game is won
     */
    public boolean isGameWon() {
        return this.getMonsters().allMatch(Monster::isDead);
    }

    /**
     * Returns true if the game is lost, false otherwise
     * @return If game is lost
     */
    public boolean isGameLost() {
        return this.getMonsters().anyMatch(Monster::isAtEnd);
    }

    /**
     * Add a gameobject to the game.
     * @param gameObject The game object to add
     * @throws IllegalArgumentException if game object is null
     */
    public void add(GameObject gameObject) {
        if (gameObject == null) {
            throw new IllegalArgumentException("Can not add a game object of type null to the game");
        }

        gameObject.addToWorld(this);
        gameObjects.add(gameObject);
    }
}
