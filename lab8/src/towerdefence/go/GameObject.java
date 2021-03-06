package towerdefence.go;

import towerdefence.World;
import towerdefence.util.WorldPosition;

public interface GameObject {

    WorldPosition getPosition();

    void act(World world);

    int getType();

    void addToWorld(World world);
}
