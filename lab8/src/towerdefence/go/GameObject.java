package towerdefence.go;

import towerdefence.World;
import towerdefence.WorldPosition;

public abstract class GameObject {
    public static final int GRASS = 0;
    public static final int ROAD = 1;
    public static final int TOWER = 2;
    public static final int MONSTER = 3;
    public static final int GOAL = 4;

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
