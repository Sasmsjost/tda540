package towerdefence.graphics;

import towerdefence.World;
import towerdefence.WorldMap;
import towerdefence.go.GameObject;
import towerdefence.go.Monster;
import towerdefence.go.Tower;
import towerdefence.util.TilePosition;
import towerdefence.util.WorldPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Gui extends JLayeredPane {
    private final Map<GameObject, JTile> gameObjectToTile = new HashMap<>();
    private final Map<Tower, JBeam> towerToBeam = new HashMap<>();
    private final List<JTile> allTiles = new LinkedList<>();

    private BufferedImage backgroundImage;
    private boolean disableShader = false;

    public Gui(World world, boolean disableShader) {
        super();
        this.disableShader = disableShader;
        addPanel(createBackgroundPanel(world));
        addPanel(createGameObjectsPanel(world));
        addPanel(createFxPanel(world));

        enableAutoResize();
    }

    public void render() {
        gameObjectToTile.forEach((go, tile) -> {
            tile.setTilePosition(go.getPosition());
        });

        towerToBeam.forEach((tower, beam) -> {
            GameObject target = tower.getLastTarget();
            if (target == null) {
                beam.setBounds(0, 0, 0, 0);
                return;
            }

            WorldPosition pos = tower.getPosition();
            WorldPosition targetPos = target.getPosition();
            int padding = beam.getRenderPadding();
            beam.setBounds(computeScreenBoundingBox(pos, targetPos, padding));

            if (!disableShader) {
                beam.setBackgroundImage(backgroundImage);
            }
        });

        allTiles.forEach(JTile::animate);


        // Save render pass for shaders next round
        if (!disableShader) {
            paintAll(backgroundImage.getGraphics());
        }
        repaint();
    }

    private Rectangle computeScreenBoundingBox(WorldPosition pos1, WorldPosition pos2, int padding) {
        // Find the minimal bounding box for fx
        int x = (int) ((Math.min(pos1.getX(), pos2.getX()) + 0.5) * Texture.TILE_SIZE);
        int y = (int) ((Math.min(pos1.getY(), pos2.getY()) + 0.5) * Texture.TILE_SIZE);
        int x2 = (int) ((Math.max(pos1.getX(), pos2.getX()) + 0.5) * Texture.TILE_SIZE);
        int y2 = (int) ((Math.max(pos1.getY(), pos2.getY()) + 0.5) * Texture.TILE_SIZE);
        int width = x2 - x;
        int height = y2 - y;

        return new Rectangle(x - padding, y - padding, width + padding * 2, height + padding * 2);
    }


    private void enableAutoResize() {
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                for (Component component : Gui.this.getComponents()) {
                    component.setSize(getWidth(), getHeight());
                }

                if (!disableShader) {
                    backgroundImage = new BufferedImage(
                            getWidth(),
                            getHeight(),
                            BufferedImage.TYPE_INT_RGB
                    );
                }
            }
        });
    }

    private void addPanel(JPanel panel) {
        this.add(panel);
        this.moveToFront(panel);
    }


    private JTile createGameObjectTile(GameObject go, World world) {
        if (go instanceof Monster) {
            Monster monster = (Monster) go;
            return new JMonster(monster, world);
        } else if (go instanceof Tower) {
            Tower tower = (Tower) go;
            return new JTower(tower, world);
        } else {
            Image[] texture = Texture.get(go.getType());
            return new JTile(texture, world);
        }
    }

    private JTile createBackgroundTile(TilePosition pos, World world) {
        WorldMap map = world.getMap();
        int type = map.getTypeAt(pos);

        switch (type) {
            case WorldMap.ROAD:
                return JRoadTile.fromPosition(pos, world);
            case WorldMap.GRASS:
                return new JGrassTile(world);
            default:
                Image[] icons = Texture.get(type);
                return new JTile(icons, world);
        }
    }


    private JPanel createGameObjectsPanel(World world) {
        JPanel tiles = new JPanel();
        tiles.setBackground(null);
        tiles.setOpaque(false);
        tiles.setLayout(null);

        world.getGameObjects().forEach(go -> {
            JTile tile = createGameObjectTile(go, world);

            WorldPosition position = go.getPosition();
            tile.setTilePosition(position);

            tiles.add(tile);
            allTiles.add(tile);
            gameObjectToTile.put(go, tile);
        });

        return tiles;
    }

    private JPanel createFxPanel(World world) {
        JPanel fx = new JPanel();
        fx.setBackground(null);
        fx.setOpaque(false);
        fx.setLayout(null);

        world.getTowers().forEach(tower -> {
            JBeam beam = new JBeam(tower, world, backgroundImage, 80);
            fx.add(beam);
            towerToBeam.put(tower, beam);
        });

        return fx;
    }

    private JPanel createBackgroundPanel(World world) {
        JPanel tiles = new JPanel();
        tiles.setLayout(null);

        world.getMap().getTilePositions().forEach(pos -> {
            JTile tile = createBackgroundTile(pos, world);
            tile.setTilePosition(pos);

            allTiles.add(tile);
            tiles.add(tile);
        });
        return tiles;
    }
}
