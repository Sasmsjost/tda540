package towerdefence.go;

import towerdefence.World;
import towerdefence.util.WorldPosition;

/**
 * The base class which all game objects are derived from
 */
public abstract class OurGameObject {
    protected WorldPosition position = new WorldPosition(0f, 0f);

    public WorldPosition getPosition() {
        return position;
    }

    public void act(World world) {
    }

    public abstract int getType();

    public void addToWorld(World world) {
    }
}
