package towerdefence;

import towerdefence.go.*;
import towerdefence.graphics.Gui;
import towerdefence.graphics.Texture;
import towerdefence.levels.Level;
import towerdefence.util.WorldPosition;

import javax.swing.*;
import java.awt.*;

public class Game {
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

    private Runnable onWonCallback;
    private Runnable onLostCallback;

    public Game(Level level) {
        // Create the game
        world = buildWorld(level);

        // Start timer so that the game can update every FRAME_RATE milliseconds
        timer = new Timer(FRAME_RATE, e -> step());
    }

    public void run() {
        timer.start();
    }

    public void attachTo(JFrame frame) {
        WorldMap map = world.getMap();

        // Ensure that the map fills the screen
        frame.setSize(Texture.TILE_SIZE * map.getWidth(), Texture.TILE_SIZE * map.getHeight());
        // Center on screen
        frame.setLocationRelativeTo(null);

        // Create the gui for the game
        gui = new Gui(frame.getWidth(), frame.getHeight(), world);

        frame.add(gui, BorderLayout.CENTER);
    }

    public void dispose() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }

        if (gui != null) {
            gui.getParent().remove(gui);
            gui = null;
        }
    }

    public void onWon(Runnable callback) {
        onWonCallback = callback;
    }

    public void onLost(Runnable callback) {
        onLostCallback = callback;
    }

    private World buildWorld(Level level) {
        World world = new RikardsWorld(new RikardsWorldMap(level.getMap()));

        for (WorldPosition position : level.getGoals()) {
            Goal goal = new PrincessGoal(position);
            world.add(goal);
        }

        for (WorldPosition position : level.getTowers()) {
            Tower tower = new BeamTower(position);
            world.add(tower);
        }

        for (WorldPosition position : level.getMonsters()) {
            Monster monster = new BombMonster(position, MONSTER_HEALTH);
            world.add(monster);
        }

        return world;
    }

    private void step() {
        // Step the game forward one frame at a constant speed
        for (int i = 0; i < SIMULATION_RATE; i++) {
            world.step(SIMULATION_STEP_SIZE);
        }

        // Check if critical points are reached
        if (world.isGameWon() && onWonCallback != null) {
            onWonCallback.run();
        }
        if (world.isGameLost() && onLostCallback != null) {
            onLostCallback.run();
        }

        // Update the gui to reflect the current state of the game
        if (gui != null) {
            gui.render();
        }
    }
}
