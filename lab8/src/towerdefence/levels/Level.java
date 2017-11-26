package towerdefence.levels;

import towerdefence.util.WorldPosition;

public class Level {
    private int[][] map;
    private WorldPosition[] towers;
    private WorldPosition[] monsters;

    public Level(int[][] map, WorldPosition[] towers, WorldPosition[] monsters) {
        this.map = map;
        this.towers = towers;
        this.monsters = monsters;
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
}
