package towerdefence.levels;

import towerdefence.util.WorldPosition;

public class Level {
    private int[][] map;
    private WorldPosition[] towers;
    private WorldPosition[] monsters;
    private WorldPosition[] goals;

    /**
     * @param map      The 2D map to use
     * @param towers   The list of tower positions to use
     * @param monsters The list of monster positions to use
     * @param goals    The list of goal positions to use
     * @throws IllegalArgumentException If any of the input arguments are null
     */
    public Level(int[][] map, WorldPosition[] towers, WorldPosition[] monsters, WorldPosition[] goals) {
        if (map == null || towers == null || monsters == null || goals == null) {
            throw new IllegalArgumentException("A map, towers, monsters and goals must be provided when constructing a level");
        }
        this.map = map;
        this.towers = towers;
        this.monsters = monsters;
        this.goals = goals;
    }

    public int[][] getMap() {
        return map;
    }

    public WorldPosition[] getTowers() {
        return towers;
    }

    public WorldPosition[] getMonsters() {
        return monsters;
    }

    public WorldPosition[] getGoals() {
        return goals;
    }
}
