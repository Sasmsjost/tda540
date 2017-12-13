package towerdefence.go;

import towerdefence.WorldMap;
import towerdefence.util.WorldPosition;

/**
 * A goal in the world, the monster will be trying to get here.
 * The goal is static and doesn't do anything, it's more intended
 * as a marker for other game logic
 */
public class OurGoal extends OurGameObject implements Goal {

    /**
     * @param position The position of the goal
     * @throws IllegalArgumentException If position is null
     */
    public OurGoal(WorldPosition position) {
        super();
        if (position == null) {
            throw new IllegalArgumentException("A valid position must be provided when constructing a goal");
        }
        this.position = position;
    }

    @Override
    public int getType() {
        return WorldMap.GOAL;
    }
}
