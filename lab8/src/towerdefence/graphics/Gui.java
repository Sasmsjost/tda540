package towerdefence.graphics;

import towerdefence.World;
import towerdefence.WorldMap;
import towerdefence.go.GameObject;
import towerdefence.go.Monster;
import towerdefence.go.MonsterSpawner;
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

public class Gui extends JPanel {
    private final Map<GameObject, JTile> gameObjectToTile = new HashMap<>();
    private final Map<Tower, JBeam> towerToBeam = new HashMap<>();
    private final List<JTile> allTiles = new LinkedList<>();
    private final JPanel gameObjectsPanel = new JPanel();
    private final JLayeredPane contentsPanel;

    private BufferedImage renderBuffer;
    private boolean disableShader = false;

    public Gui(World world, boolean disableShader) {
        super();
        this.disableShader = disableShader;

        contentsPanel = new JLayeredPane();
        contentsPanel.setVisible(false);
        this.add(contentsPanel);

        addPanel(createBackgroundPanel(world));
        addPanel(createGameObjectsPanel(world));
        addPanel(createFxPanel(world));

        world.addGameObjectAddedListener(go -> {
            addGameObject(go, world);
            return null;
        });

        enableAutoResize();
    }

    public void render() {
        gameObjectToTile.forEach((go, tile) -> {
            tile.setTilePosition(go.getPosition());
        });

        allTiles.forEach(JTile::animate);

        // Do render pass for shaders
        contentsPanel.setVisible(true);
        contentsPanel.paintAll(renderBuffer.getGraphics());
        contentsPanel.setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(renderBuffer, 0, 0, null);
    }

    private void enableAutoResize() {
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                contentsPanel.setBounds(0, 0, getWidth(), getHeight());
                for (Component component : contentsPanel.getComponents()) {
                    component.setSize(getWidth(), getHeight());
                }

                if (!disableShader) {
                    renderBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                }

                towerToBeam.values().forEach(beam -> {
                    beam.setBounds(0, 0, getWidth(), getHeight());
                    if (!disableShader) {
                        beam.setSampleImage(renderBuffer);
                    }
                });
            }
        });
    }

    private void addPanel(JPanel panel) {
        contentsPanel.add(panel);
        contentsPanel.moveToFront(panel);
    }


    private JTile createGameObjectTile(GameObject go, World world) {
        if (go instanceof MonsterSpawner) {
            MonsterSpawner spawner = (MonsterSpawner) go;
            return new JMonsterSpawner(spawner, world);
        } else if (go instanceof Monster) {
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

    private void addGameObject(GameObject go, World world) {
        JTile tile = createGameObjectTile(go, world);

        WorldPosition position = go.getPosition();
        tile.setTilePosition(position);

        gameObjectsPanel.add(tile);
        allTiles.add(tile);
        gameObjectToTile.put(go, tile);
    }

    private JPanel createGameObjectsPanel(World world) {
        JPanel tiles = gameObjectsPanel;
        tiles.setBackground(null);
        tiles.setOpaque(false);
        tiles.setLayout(null);

        world.getGameObjects().forEach(go -> addGameObject(go, world));

        return tiles;
    }

    private JPanel createFxPanel(World world) {
        JPanel fx = new JPanel();
        fx.setBackground(null);
        fx.setOpaque(false);
        fx.setLayout(null);

        world.getTowers().forEach(tower -> {
            JBeam beam = new JBeam(tower, world, renderBuffer);
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
