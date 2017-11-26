package towerdefence.go;

import towerdefence.World;
import towerdefence.util.WorldPosition;

public interface GameObject {

    float getRotation();

    WorldPosition getPosition();

    void act(World world);

    int getType();

    void addToWorld(World world);
}
