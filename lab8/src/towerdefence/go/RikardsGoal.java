package towerdefence.go;

import towerdefence.WorldMap;
import towerdefence.util.WorldPosition;

/**
 * A goal in the world, the monster will be trying to get here.
 * The goal is static and doesn't do anything, it's more intended
 * as a marker for other game logic
 */
public class RikardsGoal extends RikardsGameObject implements Goal {

    public RikardsGoal(WorldPosition position) {
        super();
        this.position = position;
    }

    @Override
    public int getType() {
        return WorldMap.GOAL;
    }
}
