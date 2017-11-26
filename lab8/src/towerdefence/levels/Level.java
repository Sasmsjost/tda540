package towerdefence.levels;

import towerdefence.util.WorldPosition;

public class Level {
    private int[][] map;
    private WorldPosition[] towers;
    private WorldPosition[] monsters;
    private WorldPosition[] goals;

    public Level(int[][] map, WorldPosition[] towers, WorldPosition[] monsters, WorldPosition[] goals) {
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
