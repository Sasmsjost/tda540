package towerdefence;

public class TilePosition extends Position<Integer> {
    public TilePosition(int x, int y) {
        super(x, y);
    }

    public TilePosition(WorldPosition position) {
        this(Math.round(position.x), Math.round(position.y));
    }
}
