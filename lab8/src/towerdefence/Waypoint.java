package towerdefence;

import java.util.ArrayList;

public class Waypoint {
    private Waypoint previous;
    private Waypoint next;
    private TilePosition position;

    public Waypoint(TilePosition position) {
        this.position = position;
    }

    public TilePosition getDirection() {
        return new TilePosition(position.x - previous.position.x, position.y - previous.position.y);
    }

    public boolean hasPassedPosition(WorldPosition position) {
        TilePosition direction = getDirection();
        int dx = this.position.x - (int)(float)position.x;
        int dy = this.position.y - (int)(float)position.y;

        if(direction.x > 0 && dx > 0){
            return false;
        } else if(direction.x < 0 && dx <= 0){
            return false;
        }

        if(direction.y > 0 && dy > 0){
            return false;
        } else if(direction.y < 0 && dy < 0){
            return false;
        }

        return true;
    }

    private void setNext(Waypoint waypoint) {next = waypoint;}

    private void setPrevious(Waypoint waypoint) {
        previous = waypoint;
    }

    public TilePosition getPosition() {
        return position;
    }

    public Waypoint getNext() {
        return next;
    }

    public static Waypoint generatePath(TilePosition start, int[][] map) {
        ArrayList<TilePosition> tilePositions = new ArrayList<>();

        tilePositions.add(start);
        Waypoint currentWaypoint = new Waypoint(start);
        currentWaypoint.setPrevious(currentWaypoint);
        Waypoint firstWaypoint = currentWaypoint;

        TilePosition currentPos = start;
        while(map[currentPos.x][currentPos.y] != World.GOAL) {
            System.out.println(String.format("Checking [%d, %d]", currentPos.x, currentPos.y));

            ArrayList<TilePosition> goals = getNeighborsOfType(World.GOAL, currentPos, map);
            if(goals.size() > 0) {
                currentPos = goals.get(0);
            } else {
                ArrayList<TilePosition> neightbours = getNeighborsOfType(World.ROAD, currentPos, map);
                TilePosition[] unvisited = neightbours.stream()
                        .filter(n -> !tilePositions.contains(n))
                        .toArray(TilePosition[]::new);

                if (unvisited.length <= 0) {
                    System.out.println("Can not find end of path");
                    System.exit(1);
                }

                int index = (int)Math.floor((unvisited.length-1) * Math.random() + 0.5);
                currentPos = unvisited[index];
            }
            tilePositions.add(currentPos);

            Waypoint nextWaypoint = new Waypoint(currentPos);
            currentWaypoint.setNext(nextWaypoint);
            nextWaypoint.setPrevious(currentWaypoint);
            currentWaypoint = nextWaypoint;
        }

        return firstWaypoint;
    }

    private static ArrayList<TilePosition> getNeighborsOfType(int type, TilePosition p, int[][] map) {
        int x = p.x;
        int y = p.y;

        int up = p.y - 1;
        int down = p.y + 1;
        int left = p.x - 1;
        int right = p.x + 1;

        ArrayList<TilePosition> neightbours = new ArrayList<>();

        if(up > 0 && map[x][up] == type) neightbours.add(new TilePosition(x, up));
        if(left > 0 && map[left][y] == type) neightbours.add(new TilePosition(left, y));
        if(down < map.length && map[x][down] == type) neightbours.add(new TilePosition(x, down));
        if(right < map.length && map[right][y] == type) neightbours.add(new TilePosition(right, y));

        return neightbours;
    }
}
