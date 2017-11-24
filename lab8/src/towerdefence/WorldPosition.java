package towerdefence;

public class WorldPosition extends Position<Float> {
    public WorldPosition(float x, float y) {
        super(x, y);
    }

    public float distance(WorldPosition other) {
        return (float)Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2)) ;
    }

    public void add(float f) {
        x += f;
        y += f;
    }
}
