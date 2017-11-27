package towerdefence.graphics;

import towerdefence.World;
import towerdefence.WorldMap;
import towerdefence.util.TilePosition;
import towerdefence.util.Tuple;

import java.awt.*;

public class JRoadTile extends JTile {
    private static final int STRAIGHT = 0;
    private static final int CORNER = 1;
    private static final int TINTERSECTION = 2;
    private static final int END = 3;
    private static final int ALONE = 4;

    /**
     * @param type     The type of tile, ex. corner, straight, etc. Found in JRoadTile.CORNER
     * @param rotation Rotation of the tile, 0 <= rotation <= 3
     * @param world    The world context the tile lies in
     */
    public JRoadTile(int type, int rotation, World world) {
        super(getTexture(type), world);
        setRotation((float) (rotation * Math.PI / 2f));
    }

    public static JRoadTile fromPosition(TilePosition pos, World world) {
        TilePosition[] neightbours = world.getMap().getNeighborsOfType(WorldMap.ROAD, pos);
        Tuple<Integer, Integer> intersection = JRoadTile.getTypeOfRoad(pos, neightbours);
        int intType = intersection.a;
        int rotation = intersection.b;
        return new JRoadTile(intType, rotation, world);

    }

    private static Image[] getTexture(int type) {
        if (type < 0 || type > 4) {
            throw new IllegalArgumentException("Only types in JRoadTile can be used");
        }
        Image[] texture = Texture.get(WorldMap.ROAD);
        return new Image[]{texture[type]};
    }

    private static Tuple<Integer, Integer> getTypeOfRoad(TilePosition pos, TilePosition[] neighbours) {
        if (neighbours.length == 0) {
            return new Tuple<>(ALONE, 0);
        } else if (neighbours.length == 1) {
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
            if (Math.random() < 0.5) {
                return 0;
            } else {
                return 2;
            }
        } else {
            if (Math.random() < 0.5) {
                return 1;
            } else {
                return 3;
            }
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
