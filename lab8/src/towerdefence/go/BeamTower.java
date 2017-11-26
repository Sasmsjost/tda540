package towerdefence.go;

import towerdefence.World;
import towerdefence.WorldMap;
import towerdefence.util.WorldPosition;

import java.util.Optional;

public class BeamTower extends RikardsGameObject implements Tower {

    private GameObject lastTarget;
    private long lastShot;
    private boolean lastShotHit;
    private int shotDelay = 1500;
    private int damage = 3;
    private float hitChance = 0.5f;
    private float range = 4;

    public BeamTower(WorldPosition position) {
        super();
        this.position = position;
    }

    private Monster getClosestMonster(World world) {
        // Get closest monster
        Optional<Monster> maybeTarget = world.getMonsters().reduce((monster, closest) -> {
            if (monster.getPosition().distance(this.position) < closest.getPosition().distance(this.position)) {
                return monster;
            } else {
                return closest;
            }
        });

        if (maybeTarget.isPresent()) {
            Monster monster = maybeTarget.get();
            // Ensure monster within radius
            if (monster.getPosition().distance(this.position) < range) {
                return monster;
            }
        }
        return null;

    }

    @Override
    public void act(World world) {
        Monster monster = getClosestMonster(world);
        if (monster == null) {
            return;
        }

        turnTo(monster);
        shoot(monster, world.now());
    }

    private void shoot(Monster target, long now) {
        if (now - lastShot > shotDelay) {
            lastTarget = target;
            lastShot = now;
            if (Math.random() > hitChance) {
                lastShotHit = false;
                return;
            }
            lastShotHit = true;

            target.damage(damage);
        }

    }

    private void turnTo(Monster target) {
        float dx = position.getX() - target.getPosition().getX();
        float dy = position.getY() - target.getPosition().getY();

        float angle = (float) Math.atan(dy / dx) + (float) Math.PI / 2;
        if (dx >= 0) {
            angle = angle + (float) Math.PI;
        }

        rotation = angle;
    }

    @Override
    public int getType() {
        return WorldMap.TOWER;
    }

    public boolean isLastShotHit() {
        return lastShotHit;
    }

    public long getLastShot() {
        return lastShot;
    }

    public GameObject getLastTarget() {
        return lastTarget;
    }
}
