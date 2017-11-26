package towerdefence.graphics;

import towerdefence.World;
import towerdefence.WorldMap;
import towerdefence.util.TilePosition;
import towerdefence.util.Tuple;

import java.awt.*;

public class JRoadTile extends JTile {
    public static final int STRAIGHT = 0;
    public static final int CORNER = 1;
    public static final int TINTERSECTION = 2;
    public static final int END = 3;
    public static final int ALONE = 4;

    /**
     * @param type     The type of tile, ex. corner, straight, etc. Found in JRoadTile.CORNER
     * @param rotation Rotation of the tile, 0 <= rotation <= 3
     * @param world    The world context the tile lies in
     */
    public JRoadTile(int type, int rotation, World world) {
        super(getTexture(type), world);
        setRotation((float) (rotation * Math.PI / 2f));
    }

    private static Image[] getTexture(int type) {
        assert (type >= 0 && type <= 4);
        Image[] texture = Texture.get(WorldMap.ROAD);
        return new Image[]{texture[type]};
    }

    public static Tuple<Integer, Integer> getTypeOfRoad(TilePosition pos, TilePosition[] neighbours) {
        if (neighbours.length == 1) {
            return new Tuple<>(END, getEndRotation(pos, neighbours));
        } else if (neighbours.length == 2) {
            boolean xStraight = neighbours[0].getX().equals(neighbours[1].getX());
            boolean yStraight = neighbours[0].getY().equals(neighbours[1].getY());

            if (xStraight || yStraight) {
                return new Tuple<>(STRAIGHT, getStraightRotation(pos, neighbours));
            } else {
                return new Tuple<>(CORNER, getCornerRotation(pos, neighbours));
            }
        } else if (neighbours.length == 3) {
            return new Tuple<>(TINTERSECTION, getTIntersectionRotation(pos, neighbours));
        }
        throw new IllegalArgumentException("A road tile can only be connected to 1, 2 or 3 other roads");
    }

    private static int getEndRotation(TilePosition pos, TilePosition[] neighbours) {
        int nx = neighbours[0].getX();
        int ny = neighbours[0].getY();
        int px = pos.getX();
        int py = pos.getY();

        if (px == nx) {
            if (py < ny) {
                return 2;
            } else {
                return 0;
            }
        } else {
            if (px < nx) {
                return 1;
            } else {
                return 3;
            }
        }
    }

    private static int getStraightRotation(TilePosition pos, TilePosition[] neighbours) {
        int nx = neighbours[0].getX();
        int px = pos.getX();

        if (nx == px) {
            return 0;
        } else {
            return 1;
        }
    }

    private static int getCornerRotation(TilePosition pos, TilePosition[] neighbours) {
        int n1x = neighbours[0].getX();
        int n1y = neighbours[0].getY();
        int n2x = neighbours[1].getX();
        int n2y = neighbours[1].getY();

        int px = pos.getX();
        int py = pos.getY();
        int vy = (n1x == px) ? n1y : n2y;
        int hx = (n1y == py) ? n1x : n2x;

        if (hx < px) {
            if (vy < py) {
                //  |
                // -+
                return 3;
            } else {
                // -+
                //  |
                return 2;
            }
        } else {
            if (vy < py) {
                // |
                // +-
                return 0;
            } else {
                // +-
                // |
                return 1;
            }
        }
    }

    private static int getTIntersectionRotation(TilePosition pos, TilePosition[] neighbours) {
        return 0;
    }
}
