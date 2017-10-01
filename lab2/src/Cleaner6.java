/**
 * Created by johlenni on 2017-10-01.
 */

public class Cleaner6{
    private static Robot robot;
    private static Location pointOfStart;
    private static boolean done;

    public static void main(String[] args) {
        Cleaner6 cleaner = new Cleaner6();
        cleaner.createEnviroment();
        cleaner.cleanCorridorUpToPosition();
    } //main

    private void createEnviroment() {
        RobotWorld world = RobotWorld.load("src/loop.txt");
        robot = new Robot(1, world.getNumCols() - 4, Robot.WEST, world);
        robot.setDelay(10);
        pointOfStart = robot.getLocation();
    }//createEnviroment

    //before: The room has four corridors, forming a square
    //        The robot is located in beginning of one of the corridors, facing the corridor
    //        in counter-clockwise direction.
    //        Each corridor has a length of five cells.
    //        All cells in the corridors are dark.
    //after:  All cells in the corridors are light.
    //        The robot has the same location and facing the same direction
    private void cleanCorridorUpToPosition() {
        while (!done){
            findCorridor();
            clearCorridorInFront();
        }
    }

    private void clearCorridorInFront() {
        while (robot.frontIsClear()) {
            if (robot.onDark()) {
                robot.makeLight();
            }
            robot.move();

            if (robot.getLocation().equals(pointOfStart)) {
                if (!robot.frontIsClear()) {
                    findCorridor();
                }
                done = true;
                return;
            }
        }
        done = false;
    }

    private void findCorridor(){
        if(robot.frontIsClear()){
            return;
        }
        robot.turnLeft();
        if (!robot.frontIsClear()) {
            robot.turnLeft();
            robot.turnLeft();
        }
    }
}



