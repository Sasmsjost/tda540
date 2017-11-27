package towerdefence.go;

import towerdefence.World;
import towerdefence.util.WorldPosition;

public abstract class RikardsGameObject {
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
