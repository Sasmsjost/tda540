package towerdefence.go;

public interface Tower extends GameObject {
    boolean isLastShotHit();

    long getLastShot();

    int getShotDelay();

    GameObject getLastTarget();
}
