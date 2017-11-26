package towerdefence.go;

import towerdefence.WorldMap;
import towerdefence.util.WorldPosition;

public class PrincessGoal extends RikardsGameObject implements Goal {

    public PrincessGoal(WorldPosition position) {
        super();
        this.position = position;
    }

    @Override
    public int getType() {
        return WorldMap.GOAL;
    }
}
