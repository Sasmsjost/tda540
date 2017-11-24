package towerdefence.go;

import towerdefence.World;
import towerdefence.WorldPosition;

public abstract class GameObject {
    protected WorldPosition position = new WorldPosition(0f,0f);
    protected float rotation = 0;

    public GameObject() { }

    public float getRotation(){
        return rotation;
    }

    public WorldPosition getPosition(){
        return position;
    }

    public abstract void act(World world);
    public abstract int getType();

    public void addToWorld(World world) { }
}
