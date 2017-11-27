package towerdefence.graphics;

import towerdefence.World;
import towerdefence.WorldMap;
import towerdefence.go.GameObject;
import towerdefence.go.Tower;
import towerdefence.util.WorldPosition;

import java.awt.*;

public class JTower extends JTile {
    private Tower tower;

    public JTower(Tower tower, World world) {
        super(Texture.get(WorldMap.TOWER), world);
        this.tower = tower;
    }

    private float getAngleToTarget() {
        GameObject target = tower.getLastTarget();
        WorldPosition position = tower.getPosition();

        float dx = position.getX() - target.getPosition().getX();
        float dy = position.getY() - target.getPosition().getY();

        float angle = (float) Math.atan(dy / dx) + (float) Math.PI / 2;
        if (dx >= 0) {
            angle = angle + (float) Math.PI;
        }

        return angle;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (tower.getLastTarget() != null) {
            setRotation(getAngleToTarget());
        }
        super.paintComponent(g);
    }
}
