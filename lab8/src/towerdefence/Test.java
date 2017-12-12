package towerdefence;

import towerdefence.go.RikardsMonster;
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
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.err.printf("Failed\t\t%s - %s\n", method.getName(), e.getCause());
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

    //====Test of utils====

    public void test_TilePosition(){
        TilePosition tp1 = new TilePosition(1, 3);
        TilePosition tp2 = new TilePosition(1, 3);
        assert tp1.equals(tp2);
    }

    public void test_WorldPosition(){
        TilePosition tp1 = new TilePosition(new WorldPosition(1, 3));
        TilePosition tp2 = new TilePosition(new WorldPosition(1, 3));
        assert tp1.equals(tp2);
    }

    public void test_Waypoint_getDirection(){
        Waypoint currentWaypoint = new Waypoint(new TilePosition(2,1));
        Waypoint prevWaypoint = new Waypoint(new TilePosition(4,2));

        currentWaypoint.setPrevious(prevWaypoint);
        TilePosition direction = currentWaypoint.getDirection();
        assert direction.getX() == -2
                && direction.getY() == -1;
    }

    public void test_hasPassedPosition_True(){
        Waypoint waypoint = new Waypoint(new TilePosition(5,4));
        Waypoint prevWaypoint = new Waypoint(new TilePosition(1,2));
        waypoint.setPrevious(prevWaypoint);

        WorldPosition passedPosition = new WorldPosition(2, 3);
        assert waypoint.hasPassedPosition(passedPosition);
    }

    //should not pass, but does
    public void test_hasPassedPosition_False() {
        Waypoint waypoint = new Waypoint(new TilePosition(5,4));
        Waypoint prevWaypoint = new Waypoint(new TilePosition(1,2));
        waypoint.setPrevious(prevWaypoint);

        WorldPosition notPassedPosition = new WorldPosition(8, 9);
        assert waypoint.hasPassedPosition(notPassedPosition);
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
