public class MazeFinder {
    private Robot robot;

    public static void main(String[] args) {
        MazeFinder finder = new MazeFinder();
        finder.createEnviroment();
        finder.findExit();
    }//main

    private void createEnviroment() {
        RobotWorld world = RobotWorld.load("src/maze.txt");
        robot = new Robot(1, 2, Robot.EAST, world);
        robot.setDelay(10);

    }//createEnviroment

    // The robot finds the way through a simply connected maze
    //before: The maze is simply connected.
    //        The robot is at the entrance of the maze.
    //after:  The robot is at the exit of the maze
    private void findExit() {
        do  {
            findDirection();
        } while (!robot.atEndOfWorld());
    }// findExit

    // Would give nicer abstractions using bitfields, which would separate the navigation and the scanning.
    // (Using them as a replacement for classes, or structs at least,
    // which we've assumed are not allowed for at this point in time)
    // Since using the values (canGoForward,...) of the four directions as input parameters for
    // another method as four four separate values we deem as being a worse abstraction than the proposed solution.
    private void findDirection() {
        boolean canGoForward = robot.frontIsClear();
        robot.turnLeft();
        boolean canGoLeft = robot.frontIsClear();
        robot.turnLeft();
        boolean canTurnAround = robot.frontIsClear();
        robot.turnLeft();
        boolean canGoRight = robot.frontIsClear();
        robot.turnLeft();

        if(!canGoLeft && canGoForward) {
            robot.move();
        } else if (canGoLeft) {
            robot.turnLeft();
            robot.move();
        } else if (canGoRight) {
            turnRight();
            robot.move();
        } else if(canTurnAround){
            turnAround();
            robot.move();
        } else {
            System.out.println("Shit! We are stuck at the starting position");
            System.exit(1);
        }
    }

    private void turnRight() {
        robot.turnLeft();
        robot.turnLeft();
        robot.turnLeft();
    }

    private void turnAround() {
        robot.turnLeft();
        robot.turnLeft();
    }
}//MazeFinder
