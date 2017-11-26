package towerdefence.graphics;

import towerdefence.World;
import towerdefence.WorldMap;
import towerdefence.go.GameObject;
import towerdefence.go.Monster;
import towerdefence.go.Tower;
import towerdefence.util.TilePosition;
import towerdefence.util.Tuple;
import towerdefence.util.WorldPosition;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Gui extends JLayeredPane {
    private final Map<GameObject, JTile> gameObjectToTile = new HashMap<>();
    private final Map<Tower, JBeam> towerToBeam = new HashMap<>();

    public Gui(int width, int height, World world) {
        setBounds(0, 0, width, height);
        setLayout(null);

        addTilePanel(world);
        addGameObjectsPanel(world);
        addFxPanel(world);
    }

    public void render() {
        gameObjectToTile.forEach((go, tile) -> {
            tile.setRotation(go.getRotation());
            tile.setTilePosition(go.getPosition());
        });

        towerToBeam.forEach((tower, beam) -> {
            GameObject target = tower.getLastTarget();
            if (target == null) {
                return;
            }

            WorldPosition pos = tower.getPosition();
            WorldPosition targetPos = target.getPosition();
            int offset = beam.getOffset();

            // Find the minimal bounding box for fx
            int x = (int) ((Math.min(pos.getX(), targetPos.getX()) + 0.5) * Texture.TILE_SIZE);
            int y = (int) ((Math.min(pos.getY(), targetPos.getY()) + 0.5) * Texture.TILE_SIZE);
            int x2 = (int) ((Math.max(pos.getX(), targetPos.getX()) + 0.5) * Texture.TILE_SIZE);
            int y2 = (int) ((Math.max(pos.getY(), targetPos.getY()) + 0.5) * Texture.TILE_SIZE);
            int width = x2 - x;
            int height = y2 - y;

            // Update bounds
            beam.setBounds(x - offset, y - offset, width + offset * 2, height + offset * 2);
        });

        JTile.allTiles.forEach(JTile::animate);
        repaint();
    }


    private void addTilePanel(World world) {
        addPanel(createTilePanel(world));
    }

    private void addGameObjectsPanel(World world) {
        addPanel(createGameObjectsPanel(world));
    }

    private void addFxPanel(World world) {
        addPanel(createFxPanel(world));
    }

    private void addPanel(JPanel panel) {
        add(panel);
        moveToFront(panel);
    }


    private JPanel createGameObjectsPanel(World world) {
        JPanel tiles = new JPanel();
        tiles.setBackground(null);
        tiles.setOpaque(false);
        tiles.setLayout(null);
        tiles.setBounds(0, 0, getWidth(), getHeight());

        world.getGameObjects().forEach(go -> {
            WorldPosition position = go.getPosition();
            Image[] texture = Texture.get(go.getType());

            JTile tile;
            if (go instanceof Monster) {
                Monster monster = (Monster) go;
                tile = new JMonster(monster, world);
            } else {
                tile = new JTile(texture, world);
            }
            tile.setTilePosition(position);
            tiles.add(tile);

            gameObjectToTile.put(go, tile);
        });

        return tiles;
    }

    private JPanel createFxPanel(World world) {
        JPanel fx = new JPanel();
        fx.setBackground(null);
        fx.setOpaque(false);
        fx.setLayout(null);
        fx.setBounds(0, 0, getWidth(), getHeight());

        world.getTowers().forEach(tower -> {
            JBeam beam = new JBeam(tower, world, 20);
            fx.add(beam);
            towerToBeam.put(tower, beam);
        });

        return fx;
    }

    private JPanel createTilePanel(World world) {
        JPanel tiles = new JPanel();
        tiles.setLayout(null);
        tiles.setBounds(0, 0, getWidth(), getHeight());

        WorldMap map = world.getMap();

        world.getMap().getTilePositions().forEach(pos -> {
            int type = map.getTypeAt(pos);

            JTile tile;
            if (type == WorldMap.ROAD) {
                TilePosition[] neightbours = map.getNeighborsOfType(WorldMap.ROAD, pos);
                Tuple<Integer, Integer> intersection = JRoadTile.getTypeOfRoad(pos, neightbours);
                int intType = intersection.a;
                int rotation = intersection.b;
                tile = new JRoadTile(intType, rotation, world);
            } else {
                Image[] icons = Texture.get(type);
                tile = new JTile(icons, world);
            }

            tile.setTilePosition(pos);

            tiles.add(tile);
        });
        return tiles;
    }
}
