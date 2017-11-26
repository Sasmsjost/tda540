package towerdefence.go;

import towerdefence.World;
import towerdefence.WorldMap;
import towerdefence.util.WorldPosition;

public class Goal extends GameObject {

    public Goal(WorldPosition position) {
        super();
        this.position = position;
    }

    @Override
    public void act(World world) {
    }

    @Override
    public int getType() {
        return WorldMap.GOAL;
    }
}
