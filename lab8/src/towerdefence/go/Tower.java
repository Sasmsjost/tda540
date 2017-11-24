package towerdefence.go;

import towerdefence.World;
import towerdefence.WorldPosition;

import java.util.Optional;

public class Tower extends GameObject {

    private long lastShot;
    private int shotDelay = 500;
    private int damage = 3;
    private float hitChance = 0.5f;

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

        return maybeTarget.orElse(null);
    }

    @Override
    public void act(World world) {
        Monster target = getClosestMonster(world);
        if(target == null) {
            return;
        }

        turnTo(target);
        shoot(target);
    }

    private void shoot(Monster target) {
        if(System.currentTimeMillis() - lastShot > shotDelay) {
            lastShot = System.currentTimeMillis();
            if(Math.random() > hitChance) {
                return;
            }

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
        return World.TOWER;
    }
}
