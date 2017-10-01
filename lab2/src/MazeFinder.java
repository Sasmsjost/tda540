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
		robot.setDelay(10);
		mazeStart = new Location(7, -1);
	}//createEnviroment 

	// The robot finds the way through a simply connected maze
	//before: The maze is simply connected.
	//        The robot is at the entrance of the maze.
	//after:  The robot is at the exit of the maze
	private void findExit() {
		while (!robot.atEndOfWorld() && robot.getLocation() != mazeStart){
            findDirection();
            robot.move();
        }

	}// findExit

    private void findDirection() {
        if (clearLeft()) {
            robot.turnLeft();
        }

        else if (robot.frontIsClear())
            return;

        else if (clearRight()){
            robot.turnLeft();
            robot.turnLeft();
            robot.turnLeft();
        }

        else if (deadEnd()){
            robot.turnLeft();
            robot.turnLeft();
        }
    }

    private boolean clearLeft(){
	    boolean clear = false;
	    robot.turnLeft();
	    clear = robot.frontIsClear();
	    robot.turnLeft();
        robot.turnLeft();
        robot.turnLeft();
        return clear;
    }

    private boolean clearRight(){
        boolean clear = false;
        robot.turnLeft();
        robot.turnLeft();
        robot.turnLeft();
        clear = robot.frontIsClear();
        robot.turnLeft();
        return clear;
    }

    private boolean deadEnd() {
        return (!robot.frontIsClear() && !clearLeft() && !clearRight());
    }
}//MazeFinder
