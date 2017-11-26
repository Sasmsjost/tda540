package towerdefence;

import towerdefence.go.Monster;
import towerdefence.go.Tower;
import towerdefence.graphics.Gui;
import towerdefence.graphics.Texture;
import towerdefence.util.WorldPosition;

import javax.swing.*;
import java.awt.*;

public class GUITowerDefence extends JFrame {
    private static final int[][] TILE_MAP = {
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
    private static final WorldPosition[] towerPositions = new WorldPosition[]{
            new WorldPosition(4, 3),
            new WorldPosition(2, 5),
            new WorldPosition(0, 4),
            new WorldPosition(7, 5)
    };
    private static final WorldPosition monsterPosition = new WorldPosition(4, 0);

    private static final int FRAME_RATE = 16;
    private Gui gui;
    private World world;

    public static void main(String[] args) {
        Texture.load();
        new GUITowerDefence("Tower Defence").setVisible(true);
    }

    private GUITowerDefence(String title) {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        // Ensure that the map fills the screen
        this.setSize(Texture.TILE_SIZE * TILE_MAP.length, Texture.TILE_SIZE * TILE_MAP[0].length);
        // Center on screen
        this.setLocationRelativeTo(null);

        world = buildTowerDefence();

        gui = new Gui(getWidth(), getHeight(), world);
        add(gui, BorderLayout.CENTER);

        Timer timer = new Timer(FRAME_RATE, e -> step());
        timer.start();
    }

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

        gui.render();
    }

    private World buildTowerDefence() {
        World world = new World(TILE_MAP);

        for (WorldPosition position : towerPositions) {
            Tower tower = new Tower(position);
            world.add(tower);
        }

        Monster monster = new Monster(monsterPosition, 60);
        world.add(monster);

        return world;
    }

}
