package towerdefence;

import towerdefence.go.*;
import towerdefence.graphics.Gui;
import towerdefence.graphics.Texture;
import towerdefence.levels.Level;
import towerdefence.levels.Level1;
import towerdefence.levels.Level2;
import towerdefence.util.WorldPosition;

import javax.swing.*;
import java.awt.*;

public class GUITowerDefence extends JFrame {
    // The rate which the game is redrawn
    private static final int FRAME_RATE = 16;
    // The step size on a simulation, lower values means more CPU usage
    private static final int SIMULATION_STEP_SIZE = 2;
    // How many simulations we run per frame
    private static final int SIMULATION_RATE = 8;
    // The maximum heath of a monster
    private static final int MONSTER_HEALTH = 50;

    private Gui gui;
    private World world;
    private Timer timer;

    private static final Level level = Level1.get();

    public static void main(String[] args) {
        Texture.load();
        new GUITowerDefence("Tower Defence").setVisible(true);
    }

    private GUITowerDefence(String title) {
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        world = buildWorld(level);
        gui = buildGui(world);
        this.add(gui, BorderLayout.CENTER);

        timer = new Timer(FRAME_RATE, e -> step());
        timer.start();
    }

    private Gui buildGui(World world) {
        WorldMap map = world.getMap();

        // Ensure that the map fills the screen
        this.setSize(Texture.TILE_SIZE * map.getWidth(), Texture.TILE_SIZE * map.getHeight());
        // Center on screen
        this.setLocationRelativeTo(null);

        // Create the gui for the game
        return new Gui(this.getWidth(), this.getHeight(), world);
    }

    private void step() {
        // Step the game forward one frame at a constant speed
        for (int i = 0; i < SIMULATION_RATE; i++) {
            world.step(SIMULATION_STEP_SIZE);
        }

        // Check if critical points are reached
        if (world.isGameWon()) {
            handleGameWon();
        }
        if (world.isGameLost()) {
            handleGameLost();
        }

        // Update the gui to reflect the current state of the game
        if (gui != null) {
            gui.render();
        }
    }

    private World buildWorld(Level level) {
        World world = new RikardsWorld(new RikardsWorldMap(level.getMap()));

        for (WorldPosition position : level.getGoals()) {
            Goal goal = new RikardsGoal(position);
            world.add(goal);
        }

        for (WorldPosition position : level.getTowers()) {
            Tower tower = new RikardsTower(position);
            world.add(tower);
        }

        for (WorldPosition position : level.getMonsters()) {
            Monster monster = new RikardsMonster(position, MONSTER_HEALTH);
            world.add(monster);
        }

        return world;
    }

    private void endGame() {
        timer.stop();
        gui.getParent().remove(gui);
    }

    private void handleGameWon() {
        endGame();

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, getWidth(), getHeight());

        JLabel label = new JLabel();
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);

        panel.setBackground(new Color(50, 200, 50));
        label.setForeground(Color.WHITE);
        label.setText("You won!!!");
        label.setBounds(0, 0, getWidth(), getHeight());

        panel.add(label);
        add(panel);

        repaint();
    }

    private void handleGameLost() {
        endGame();

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, getWidth(), getHeight());

        JLabel label = new JLabel();
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);

        panel.setBackground(new Color(200, 50, 50));
        label.setForeground(Color.WHITE);
        label.setText("You lost...");
        label.setBounds(0, 0, getWidth(), getHeight());

        panel.add(label);
        add(panel);

        repaint();
    }


}
