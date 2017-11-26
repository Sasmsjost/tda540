package towerdefence.go;

import towerdefence.TilePosition;
import towerdefence.Waypoint;
import towerdefence.World;
import towerdefence.WorldPosition;

public class Monster extends GameObject {
    private int maxHealth;
    private int health;
    private Waypoint path;

    public Monster(WorldPosition position, int health) {
        super();
        this.maxHealth = health;
        this.health = health;
        this.position = position;
    }

    public void damage(int amount) {
        health -= amount;
        if(health < 0) {
            health = 0;
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public boolean isDead() {
        return health == 0;
    }

    public boolean isAtEnd() {
        return path == null;
    }

    @Override
    public void addToWorld(World world) {
        TilePosition position = new TilePosition(this.position);
        path = world.getPathFrom(position);
    }

    @Override
    public void act(World world) {
        if(isAtEnd()) {
            return;
        }
        float speed = 0.01f;
        this.position.setX(this.position.getX() + path.getDirection().getX() * speed);
        this.position.setY(this.position.getY() + path.getDirection().getY() * speed);

        if(path.hasPassedPosition(position)) {
            position.setX((float)path.getPosition().getX());
            position.setY((float)path.getPosition().getY());
            path = path.getNext();
        }
    }

    @Override
    public int getType() {
        return GameObject.MONSTER;
    }
}
