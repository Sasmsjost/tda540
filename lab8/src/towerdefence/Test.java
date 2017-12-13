package towerdefence;

import towerdefence.go.Goal;
import towerdefence.go.Monster;
import towerdefence.go.RikardsGoal;
import towerdefence.go.RikardsMonster;
import towerdefence.levels.Level;
import towerdefence.levels.Level1;
import towerdefence.util.TilePosition;
import towerdefence.util.Waypoint;
import towerdefence.util.WorldPosition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * This is a program (file) for *all* tests (testing the model)
 */
public class Test {

    public static void main(String[] args) {
        System.out.println("towerdefence.Test Start");
        runTests();
        System.out.println("towerdefence.Test Finished");
    }

    private static void runTests() {
        Test test = new Test();
        Method[] methods = Test.class.getMethods();
        for (Method method : methods) {
            if (method.getName().contains("test")) {
                try {
                    method.invoke(test);
                    System.out.printf("Ok\t\t%s\n", method.getName());
                    System.out.flush();
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.err.printf("Failed\t\t%s - %s\n", method.getName(), e.getCause());
                    e.printStackTrace();
                    System.err.flush();
                }
            }
        }
    }

    // Write your test methods here, see PigWTest for example
    // towerdefence.Test method should end with call to some Helper (below)
    // i.e. test should pass or exception

    // ------- Helpers -------------------

    private static void assertNotEquals(Number n1, Number n2) {
        if (n1.equals(n2)) {
            throw new IllegalStateException(n1 + "==" + n2);
        }
    }

    private static void assertEquals(Number n1, Number n2) {
        if (!n1.equals(n2)) {
            throw new IllegalStateException(n1 + "!=" + n2);
        }
    }

    private static void assertEquals(Object o1, Object o2) {
        if (!o1.equals(o2)) {
            throw new IllegalStateException(o1 + "!=" + o2);
        }
    }

    private static void assertEquals(boolean b1, boolean b2) {
        if (b1 != b2) {
            throw new IllegalStateException(b1 + "!=" + b2);
        }
    }

    private static void assertNotEquals(boolean b1, boolean b2) {
        if (b1 == b2) {
            throw new IllegalStateException(b1 + "==" + b2);
        }
    }

    private static void assertException(Runnable fn) {
        try {
            fn.run();
            throw new IllegalStateException("Exception was not thrown");
        } catch (Exception e) {
        }
    }

    //====Test of utils====

    public void testTilePosition() {
        TilePosition tp1 = new TilePosition(1, 3);
        TilePosition tp2 = new TilePosition(1, 3);

        assertEquals(tp1, tp2);
    }

    public void testWorldPosition() {
        TilePosition tp1 = new TilePosition(new WorldPosition(1, 3));
        TilePosition tp2 = new TilePosition(new WorldPosition(1, 3));

        assertEquals(tp1, tp2);
    }

    public void testWaypointGetDirection() {
        Waypoint currentWaypoint = new Waypoint(new TilePosition(2,1));
        Waypoint prevWaypoint = new Waypoint(new TilePosition(4,2));

        currentWaypoint.setPrevious(prevWaypoint);
        TilePosition direction = currentWaypoint.getDirection();
        assertEquals(direction.getX(), -2);
        assertEquals(direction.getY(), -1);
    }

    public void testPassedPosition() {
        Waypoint prevWaypoint = new Waypoint(new TilePosition(0, 0));
        Waypoint waypoint = new Waypoint(new TilePosition(1, 1));
        waypoint.setPrevious(prevWaypoint);

        assertEquals(false, waypoint.hasPassedPosition(new WorldPosition(-1, -1)));
        assertEquals(false, waypoint.hasPassedPosition(new WorldPosition(0, 0)));
        assertEquals(false, waypoint.hasPassedPosition(new WorldPosition(0, 1)));
        assertEquals(false, waypoint.hasPassedPosition(new WorldPosition(1, 0)));
        assertEquals(true, waypoint.hasPassedPosition(new WorldPosition(1, 1)));
        assertEquals(true, waypoint.hasPassedPosition(new WorldPosition(2, 2)));
    }

    public void testPathGenerationOk() {
        WorldMap map = new RikardsWorldMap(new int[][]{
                {1, 1, 1}
        }, 0);

        Waypoint waypoint = map.generatePathTo(new TilePosition(0, 0), new TilePosition[]{new TilePosition(2, 0)}, 1);
        assertEquals(waypoint.getLength(), 2);
    }

    public void testPathGenerationFail() {
        WorldMap map = new RikardsWorldMap(new int[][]{
                {1, 0, 1}
        }, 0);

        TilePosition start = new TilePosition(0, 0);
        TilePosition[] goal = new TilePosition[]{new TilePosition(0, 0)};
        assertException(() -> map.generatePathTo(start, goal, 1));
    }

    public void testGameWillEnd() {
        Level level = Level1.get();
        WorldMap map = new RikardsWorldMap(Level1.get().getMap(), 1);
        World world = new RikardsWorld(map);
        Monster monster = new RikardsMonster(level.getMonsters()[0], 100);
        Goal goal = new RikardsGoal(level.getGoals()[0]);

        world.add(goal);
        world.add(monster);

        int maxTime = 100000;
        for (int i = 0; i < maxTime; i++) {
            world.step(1);
            if (world.isGameWon() || world.isGameLost()) {
                return;
            }
        }

        throw new IllegalStateException("Game did not end in time");
    }

    //====Test of Monster====
    public void test_damage(){
        RikardsMonster monster = new RikardsMonster(
                new WorldPosition(1,2),
                100
        );
        monster.damage(120);
        assert monster.getHealth() == 0;
    }
}
