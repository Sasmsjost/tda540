package towerdefence.go;

public interface Monster extends GameObject {
    void damage(int amount);

    int getMaxHealth();

    int getHealth();

    boolean isDead();

    boolean isAtEnd();
}
