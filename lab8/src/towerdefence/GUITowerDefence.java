package towerdefence;

import towerdefence.go.GameObject;
import towerdefence.go.Monster;
import towerdefence.go.Tower;
import towerdefence.graphics.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Very, very basic GUI for very basic "Tower Defence" game
 */
public class GUITowerDefence extends JFrame {
    static final int[][] TILE_MAP = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 1, 4},
            {1, 1, 1, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 1, 0, 1, 1, 1, 4},
            {0, 0, 1, 1, 1, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };
    static final WorldPosition[] towerPositions = new WorldPosition[]{
            new WorldPosition(4, 3),
            new WorldPosition(2, 5),
            new WorldPosition(0, 4),
            new WorldPosition(7, 5)
    };
    static final WorldPosition monsterPosition = new WorldPosition(4, 0);

    private JLayeredPane gamePanel;
    private final Timer timer;
    private static final int FRAME_RATE = 16;
    private static final int PAUSE = 0;

    private final Map<GameObject, JTile> gameObjectToTile = new HashMap<>();
    private final Map<Tower, JBeam> towerToBeam = new HashMap<>();

    private World world;

    public static void main(String[] args) {
        Texture.load();
        new GUITowerDefence("Tower Defence").setVisible(true);
    }

    public GUITowerDefence(String title) {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setSize(Texture.TILE_SIZE * TILE_MAP.length, Texture.TILE_SIZE * TILE_MAP[0].length);
        // Center on screen
        this.setLocationRelativeTo(null);

        gamePanel = new JLayeredPane();
        gamePanel.setBounds(0, 0, getWidth(), getHeight());
        gamePanel.setLayout(null);
        add(gamePanel, BorderLayout.CENTER);

        world = buildTowerDefence();

        JPanel tilePanel = getLandscapePanel();
        gamePanel.add(tilePanel);
        gamePanel.moveToFront(tilePanel);

        JPanel fxPanel = getFxPanel(world);
        gamePanel.add(fxPanel);
        gamePanel.moveToFront(fxPanel);

        JPanel goPanel = getGameObjectsPanel(world);
        gamePanel.add(goPanel);
        gamePanel.moveToFront(goPanel);

        timer = new Timer(FRAME_RATE, e -> step());
        timer.setInitialDelay(PAUSE);
        timer.start();
    }

    // ---------- Event handling --------------------

    private void step() {
        world.step();

        if (world.isGameWon()) {
            System.out.println("You win!");
            System.exit(0);
        }

        if (world.isGameOver()) {
            System.out.println("You lost!");
            System.exit(0);
        }

        render();
    }

    private void render() {
        gameObjectToTile.forEach((go, tile) -> {
            tile.setRotation(go.getRotation());
            tile.setTilePosition(go.getPosition());
        });

        towerToBeam.forEach((tower, beam) -> {
            GameObject target = tower.getLastTarget();
            if(target == null) {
                return;
            }

            WorldPosition pos = tower.getPosition();
            WorldPosition targetPos = target.getPosition();

            int x = (int) (Math.min(pos.x, targetPos.x)*Texture.TILE_SIZE);
            int y = (int) (Math.min(pos.y, targetPos.y)*Texture.TILE_SIZE);
            int x2 = (int) (Math.max(pos.x, targetPos.x)*Texture.TILE_SIZE);
            int y2 = (int) (Math.max(pos.y, targetPos.y)*Texture.TILE_SIZE);

            WorldPosition relativePos = new WorldPosition(pos.x*Texture.TILE_SIZE-x, pos.y*Texture.TILE_SIZE-y);
            WorldPosition relativeTargetPos = new WorldPosition(targetPos.x*Texture.TILE_SIZE-x , targetPos.y*Texture.TILE_SIZE-y);
            relativePos.add(10);
            relativeTargetPos.add(10);

            x += Texture.TILE_SIZE/2;
            y += Texture.TILE_SIZE/2;
            x2 += Texture.TILE_SIZE/2;
            y2 += Texture.TILE_SIZE/2;
            int width = x2 - x;
            int height = y2 - y;
            beam.setBounds(x-10, y-10, width+20, height+20);

            beam.updateState(
                    relativePos,
                    relativeTargetPos,
                    tower.getLastShot(),
                    tower.isLastShotHit()
            );
        });

        world.getMonsters().forEach(monster -> {
            JMonster tile = (JMonster) gameObjectToTile.get(monster);
            tile.setHelthFraction((float) monster.getHealth() / monster.getMaxHealth());
        });

        JTile.allTiles.forEach(JTile::animate);
        getContentPane().repaint();
    }

    // ---------- Render (if actionPerformed to large) ---------------


    // ---------- Build model ----------

    private World buildTowerDefence() {
        World world = new World(TILE_MAP);

        for (WorldPosition position : towerPositions) {
            Tower tower = new Tower(position);
            world.add(tower);
        }

        Monster monster = new Monster(monsterPosition, 80);
        world.add(monster);

        return world;
    }

    // ----------- Build GUI ---------------------

    private JPanel getGameObjectsPanel(World world) {
        JPanel tiles = new JPanel();
        tiles.setBackground(null);
        tiles.setOpaque(false);
        tiles.setLayout(null);
        tiles.setBounds(0, 0, getWidth(), getHeight());

        for (GameObject go : world.getGameObjects()) {
            WorldPosition position = go.getPosition();
            Image[] texture = Texture.get(go.getType());

            JTile tile;
            if (go instanceof Monster) {
                tile = new JMonster(texture);
            } else {
                tile = new JTile(texture);
            }
            tile.setTilePosition(position);
            tiles.add(tile);

            gameObjectToTile.put(go, tile);
        }

        return tiles;
    }

    private JPanel getFxPanel(World world) {
        JPanel fx = new JPanel();
        fx.setBackground(null);
        fx.setOpaque(false);
        fx.setLayout(null);
        fx.setBounds(0, 0, getWidth(), getHeight());

        world.getTowers().forEach(tower -> {
            JBeam beam = new JBeam();
            fx.add(beam);
            towerToBeam.put(tower, beam);
        });

        return fx;
    }

    private JPanel getLandscapePanel() {
        JPanel tiles = new JPanel();
        tiles.setLayout(null);
        tiles.setBounds(0, 0, getWidth(), getHeight());

        int mapSize = TILE_MAP.length;
        for (int y = 0; y < mapSize; y++) {
            for (int x = 0; x < mapSize; x++) {
                int tileType = TILE_MAP[x][y];
                Image[] icons = Texture.get(tileType);

                JTile tile = new JTile(icons);
                tile.setTilePosition(x, y);

                tiles.add(tile);
            }
        }

        return tiles;
    }

    // -------------- Inner class ------------------
    // Use if you like
//    private class MonsterPanel extends JPanel {
//
//        private JLabel monster;
//        private JLabel health = new JLabel();
//
//        public MonsterPanel() {
//            this.setBackground(Color.WHITE);
//            this.setLayout(new BorderLayout());
//            this.monster = getIconLabel("icons/monster10.gif");
//            health.setFont(new Font("Serif", Font.BOLD, 10));
//            this.add(monster, BorderLayout.CENTER);
//            this.add(health, BorderLayout.SOUTH);
//        }
//
//        public void setHealth(int health) {
//            this.health.setText(String.valueOf(health));
//        }
//    }

}
