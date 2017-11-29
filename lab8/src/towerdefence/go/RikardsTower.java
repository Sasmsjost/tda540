package towerdefence.go;

import towerdefence.World;
import towerdefence.WorldMap;
import towerdefence.util.WorldPosition;

import java.util.Optional;

public class RikardsTower extends RikardsGameObject implements Tower {

    private GameObject lastTarget;
    private final int shotDelay = 1000;
    private final int damage = 3;
    private final float hitChance = 0.5f;
    private final float range = 4;

    private boolean lastShotHit;
    // Start shooting immediately
    private long lastShot = -shotDelay;

    public RikardsTower(WorldPosition position) {
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
            lastTarget = null;
            return;
        }

        shoot(monster, world.now());
    }

    private void shoot(Monster target, long now) {
        if (now - lastShot > shotDelay) {
            lastShot = now;
            lastTarget = target;
            if (Math.random() < hitChance) {
                lastShotHit = false;
            } else {
                lastShotHit = true;
                target.damage(damage);
            }
        }

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
