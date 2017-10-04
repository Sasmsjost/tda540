/**
 * Created by johlenni on 2017-10-01.
 */

public class CleanerF {
    static Robot robot;

    public static void main(String[] args) {
        CleanerF cleaner = new CleanerF();
        cleaner.createEnviroment();

        Location endPoint = new Location(1, 8);
        boolean foundTarget = cleaner.cleanCorridorUpToPosition(endPoint);

        if(foundTarget) {
            System.out.println("The target position was reached!");
        } else {
            System.out.println("Sorry mate! Could not find what you where looking for, cleaned the whole place instead.");

        }
    } //main

    private void createEnviroment() {
        RobotWorld world = RobotWorld.load("src/loop.txt");
        robot = new Robot(1, world.getNumCols() - 4, Robot.WEST, world);
        robot.setDelay(250);
    }//createEnviroment

    //before: The corridors form a closed loop.
    //        The robot is located somewhere in one of the corridors, independent of start-rotation
    //        in clockwise or counter-clockwise direction.
    //        Each corridor has a length of more than one cell.
    //        Some cells in the corridors are dark.
    //after:  All cells the robot have passed to reach the target position in the corridors are light,
    //        if the target position was not found, all cells in the corridor will be cleaned.
    //        The robot has the same location and facing the same direction, if target position WAS NOT found
    //        The robot is facing a corridor and at location targetPosition, if target position WAS found
    private boolean cleanCorridorUpToPosition(Location targetPosition) {
        Location robotStartPosition = robot.getLocation();
        int endDirection = robot.getDirection();
        boolean foundTargetPosition = false;

        do {
            findCorridor();
            robot.move();
            tryMakeLight();
            foundTargetPosition = robot.getLocation().equals(targetPosition);
        } while(!foundTargetPosition && !robot.getLocation().equals(robotStartPosition));

        if(!foundTargetPosition) {
            while (robot.getDirection() != endDirection) {
                robot.turnLeft();
            }
        } else {
            findCorridor();
        }

        return foundTargetPosition;
    }

    private void tryMakeLight() {
        if (robot.onDark()) {
            robot.makeLight();
        }
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



