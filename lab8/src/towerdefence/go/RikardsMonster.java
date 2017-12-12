package towerdefence.go;

import towerdefence.World;
import towerdefence.WorldMap;
import towerdefence.util.TilePosition;
import towerdefence.util.Waypoint;
import towerdefence.util.WorldPosition;

/**
 * A moving monster which can be targeted by towers
 */
public class RikardsMonster extends RikardsGameObject implements Monster {
    private int maxHealth;
    private int health;
    private float speed = 1f;
    private Waypoint path;

    public RikardsMonster(WorldPosition position, int health) {
        super();
        this.maxHealth = health;
        this.health = health;
        this.position = position;
    }

    public void damage(int amount) {
        health -= amount;
        if (health < 0) {
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
        TilePosition start = new TilePosition(this.position);
        TilePosition[] goals = world.getGoal()
                .map(GameObject::getPosition)
                .map(TilePosition::new)
                .toArray(TilePosition[]::new);
        path = world.getMap().generatePathTo(start, goals, WorldMap.GOAL);
    }

    @Override
    public void act(World world) {
        if (isAtEnd() || isDead()) {
            return;
        }
        float movement = 0.001f * speed * world.delta();
        this.position.setX(this.position.getX() + path.getDirection().getX() * movement);
        this.position.setY(this.position.getY() + path.getDirection().getY() * movement);

        if (path.hasPassedPosition(position)) {
            position.setX((float) path.getPosition().getX());
            position.setY((float) path.getPosition().getY());
            path = path.getNext();
        }
    }

    @Override
    public int getType() {
        return WorldMap.MONSTER;
    }
}
