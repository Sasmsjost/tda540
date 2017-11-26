package towerdefence.go;

import towerdefence.World;
import towerdefence.WorldPosition;

import java.util.Optional;

public class Tower extends GameObject {

    private GameObject lastTarget;
    private long lastShot;
    private boolean lastShotHit;
    private int shotDelay = 1500;
    private int damage = 3;
    private float hitChance = 0.5f;
    private float range = 4;

    public Tower(WorldPosition position) {
        super();
        this.position = position;
    }

    private Monster getClosestMonster(World world) {
        Optional<Monster> maybeTarget = world.getMonsters().reduce((monster, closest) -> {
            if(monster.position.distance(this.position) < closest.position.distance(this.position)) {
                return monster;
            } else {
                return closest;
            }
        });

        if(maybeTarget.isPresent()) {
            Monster monster = maybeTarget.get();
            if(monster.position.distance(this.position) < 4) {
                return monster;
            }
        }
        return null;

    }

    @Override
    public void act(World world) {
        Monster monster = getClosestMonster(world);
        if(monster == null) {
            return;
        }

        turnTo(monster);
        shoot(monster);
    }

    private void shoot(Monster target) {
        if(System.currentTimeMillis() - lastShot > shotDelay) {
            lastTarget = target;
            lastShot = System.currentTimeMillis();
            if(Math.random() > hitChance) {
                lastShotHit = false;
                return;
            }
            lastShotHit = true;

            target.damage(damage);
        }

    }

    private void turnTo(Monster target) {
        float dx = position.getX() - target.position.getX();
        float dy = position.getY() - target.position.getY();

        float angle = (float)Math.atan(dy/dx)+(float) Math.PI/2;
        if(dx >= 0) {
            angle = angle + (float)Math.PI;
        }

        rotation = angle;
    }

    @Override
    public int getType() {
        return GameObject.TOWER;
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
