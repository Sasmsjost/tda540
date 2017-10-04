//import org.omg.CORBA.PUBLIC_MEMBER;

// The Whole shit could have been solved with a turnRight function.

public class MazeFinder {
    private Robot robot;
    private Location mazeStart;

    public static void main(String[] args) {
        MazeFinder finder = new MazeFinder();
        finder.createEnviroment();
        finder.findExit();
    }//main

    private void createEnviroment() {
        RobotWorld world = RobotWorld.load("src/maze.txt");
        robot = new Robot(1, 2, Robot.EAST, world);
        robot.setDelay(1);
        mazeStart = new Location(7, -1);
    }//createEnviroment

    // The robot finds the way through a simply connected maze
    //before: The maze is simply connected.
    //        The robot is at the entrance of the maze.
    //after:  The robot is at the exit of the maze
    private void findExit() {
        while (!atEndOfWorld()) {
            findDirection();
        }

    }// findExit

    private boolean atEndOfWorld() {
        return robot.atEndOfWorld() && robot.getLocation() != mazeStart;
    }

    private void findDirection() {
        boolean didSomething =
                tryGoLeft() ||
                tryGoForward() ||
                tryGoRight() ||
                handleDeadEnd();

        if(!didSomething) {
            System.out.println("Hm... We are stuck!!!");
            System.exit(1);
        }
    }

    private boolean tryGoLeft() {
        robot.turnLeft();
        if(robot.frontIsClear()) {
            robot.move();
            return true;
        } else {
            turnRight();
            return false;
        }
    }

    private boolean tryGoForward() {
        if(robot.frontIsClear()) {
            robot.move();
            return true;
        } else {
            return false;
        }
    }

    private boolean tryGoRight() {
        turnRight();
        if (robot.frontIsClear()) {
            robot.move();
            return true;
        } else {
            robot.turnLeft();
            return false;
        }
    }

    private boolean handleDeadEnd() {
        if (isDeadEnd()) {
            turnAround();
            return true;
        } else {
            return false;
        }
    }

    private boolean isDeadEnd() {
        boolean frontClear = robot.frontIsClear();

        robot.turnLeft();
        boolean leftClear = robot.frontIsClear();

        turnAround();
        boolean rightClear = robot.frontIsClear();

        robot.turnLeft();
        return !frontClear && ! leftClear && !rightClear;
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
