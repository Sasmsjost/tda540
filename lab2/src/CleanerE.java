public class CleanerE {
    private Robot robot;

    public static void main(String[] args) {
        CleanerE cleaner = new CleanerE();
        cleaner.createEnviroment();
        cleaner.cleanCorridors();
    } //main

    private void createEnviroment() {
        RobotWorld world = RobotWorld.load("src/square3.txt");
        robot = new Robot(1, world.getNumCols() - 4, Robot.SOUTH, world);
        robot.setDelay(250);
    }//createEnviroment

    private void cleanCorridors() {
        Location endPosition = robot.getLocation();
        int endDirection = robot.getDirection();

        cleanCorridorUpToPosition(endPosition, endDirection);
    }

    //before: The room has four corridors, forming a square
    //        The robot is located somewhere in one of the corridors, independent of start-rotation
    //        in clockwise or counter-clockwise direction.
    //        Each corridor has a length of more than one cell.
    //        Some cells in the corridors are dark.
    //after:  All cells in the corridors are light.
    //        The robot has the same location and facing the same direction
    private void cleanCorridorUpToPosition(Location endPosition, int endDirection) {
        do {
            findCorridor();
            robot.move();
            tryMakeLight();
        } while(!robot.getLocation().equals(endPosition));

        while(robot.getDirection() != endDirection) {
            robot.turnLeft();
        }
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



