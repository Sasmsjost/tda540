package towerdefence.go;

import towerdefence.World;
import towerdefence.WorldMap;
import towerdefence.util.WorldPosition;

import java.util.Optional;

/**
 * The tower in tower defence.
 * Shoots monsters within range
 */
public class OurTower extends OurGameObject implements Tower {

    private GameObject lastTarget;
    private int shotDelay;
    private int damage;
    private float hitChance;
    private float range;

    private boolean lastShotHit;
    // Start shooting immediately
    private long lastShot = Integer.MIN_VALUE;

    /**
     * @param position  The position of the tower
     * @param range     The range of the tower
     * @param hitChance The chance of a tower hitting the target when firing. 1 is always, 0 is never
     * @param damage    The amount the tower should damage the Monster it's firing on
     * @param shotDelay How long time there is between the tower tries to shoot the monster
     */
    public OurTower(WorldPosition position, float range, float hitChance, int damage, int shotDelay) {
        super();
        if (position == null) {
            throw new IllegalArgumentException("A valid position must be provided when constructing a tower");
        }
        this.position = position;
        this.range = range;
        this.hitChance = hitChance;
        this.damage = damage;
        this.shotDelay = shotDelay;
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
        long now = world.now();
        if (now - lastShot > shotDelay) {
            Monster monster = getClosestMonster(world);
            shoot(monster, now);
        }
    }

    private void shoot(Monster target, long now) {
        lastShot = now;
        lastTarget = target;
        double shotValue = Math.random();
        if (target == null || shotValue < hitChance) {
            lastShotHit = false;
        } else {
            lastShotHit = true;
            target.damage(damage);
        }
    }

    @Override
    public int getType() {
        return WorldMap.TOWER;
    }

    public int getShotDelay() {
        return shotDelay;
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
