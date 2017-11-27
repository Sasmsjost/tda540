package towerdefence.go;

import towerdefence.WorldMap;
import towerdefence.util.WorldPosition;

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
