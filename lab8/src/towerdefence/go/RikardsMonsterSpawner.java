package towerdefence.go;

import towerdefence.World;
import towerdefence.WorldMap;
import towerdefence.util.WorldPosition;

/**
 * A moving monster which can be targeted by towers
 */
public class RikardsMonsterSpawner extends RikardsGameObject implements Monster {
    private int maxHealth;
    private int health;

    private int monsterHealth;
    private int frequency;
    private long lastSpawn;

    public RikardsMonsterSpawner(WorldPosition position, int health, int monsterHealth, int frequency) {
        super();
        this.position = position;
        this.maxHealth = health;
        this.health = health;

        this.monsterHealth = monsterHealth;
        this.frequency = frequency;
    }

    @Override
    public void damage(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public boolean isDead() {
        return health == 0;
    }

    @Override
    public boolean isAtEnd() {
        return false;
    }

    @Override
    public void act(World world) {
        if (isDead()) {
            return;
        }

        long now = world.now();
        if (now - lastSpawn < frequency) {
            return;
        }

        lastSpawn = now;

        WorldPosition monsterPosition = new WorldPosition(position.getX(), position.getY());
        Monster monster = new RikardsMonster(monsterPosition, monsterHealth);
        world.add(monster);
    }

    @Override
    public int getType() {
        return WorldMap.MONSTER;
    }

}
