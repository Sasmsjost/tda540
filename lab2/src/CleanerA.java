public class CleanerA {
    private Robot robot;

    public static void main(String[] args) {
        CleanerA cleanerA = new CleanerA();
        cleanerA.createEnviroment();
        cleanerA.cleanCorridors();
    } //main

    private void createEnviroment() {
        RobotWorld world = RobotWorld.load("src/square.txt");
        robot = new Robot(1, world.getNumCols() - 2, Robot.WEST, world);
        robot.setDelay(250);
    }//createEnviroment

    //before: The room has four corridors, forming a square
    //        The robot is located in beginning of one of the corridors, facing the corridor
    //        in counter-clockwise direction.
    //        Each corridor has a length of five cells.
    //        All cells in the corridors are dark.
    //after:  All cells in the corridors are light.
    //        The robot has the same location and facing the same direction
    private void cleanCorridors() {
        for (int i = 0; i < 4; i++) {
            clearCorridorInFront(5);
            robot.turnLeft();
        }

    }//cleanCorridors
//CleanerA

    private void clearCorridorInFront(int corridorLength) {
        for (int i = 0; i <= corridorLength-2; i++) {
            robot.makeLight();
            robot.move();
        }
    }
}
