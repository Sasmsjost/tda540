public class Escaper { 
	private Robot robot;

	public static void main(String[] args) { 
		Escaper escaper = new Escaper(); 
		escaper.createEnviroment(); 
		escaper.moveToEntrance();
	}//main 

	public void createEnviroment() { 
		RobotWorld  world = RobotWorld.load("src/room.txt");
		robot = new Robot(2, 2, Robot.EAST, world);
		robot.setDelay(250);
	}//createEnviroment 
 
	//before: robot is inside the room
	//after:  robot is in the cell representing the the dorrjamb
	public void moveToEntrance() {
        boolean hasEscaped = findWall();
        if(!hasEscaped) {
            robot.turnLeft();
            circleRoom();
        }
    }

    /**
     * Go around the room, following the sides, attempting to escape
     */
    private void circleRoom() {
        if (!robot.frontIsClear()){
            robot.turnLeft();
        }
        if(robot.frontIsClear()) {
            robot.move();
        }

        boolean hasEscaped = tryDoEscape();
        if(!hasEscaped) {
            circleRoom();
        }
    }

    /**
     * @return If we have escaped after finding the wall
     */
    private boolean findWall() {
        boolean hasEscaped = checkHasEscaped();
        boolean shouldMoveForward = !hasEscaped && robot.frontIsClear();
        
        if(shouldMoveForward) {
            robot.move();
            return findWall();
        } else {
            return hasEscaped;
        }
    }

    /**
     * @return If both sides are covered
     */
    private boolean checkHasEscaped() {
        robot.turnLeft();
        boolean leftIsBlocked = !robot.frontIsClear();
        turnAround();
        boolean rightIsBlocked = !robot.frontIsClear();
        robot.turnLeft();

        return leftIsBlocked && rightIsBlocked;
    }

    /**
     * @return If the escape was successful
     */
    private boolean tryDoEscape() {
        turnRight();

        if (robot.frontIsClear()) {
            robot.move();
            return true;
        } else {
            robot.turnLeft();
            return false;
        }
    }

    // Before: Any
    // After: Same position, 180deg turn
    private void turnAround(){
        robot.turnLeft();
        robot.turnLeft();
    }

    // Before: Any
    // After: Same position, turned right
    private void turnRight(){
	    robot.turnLeft();
        robot.turnLeft();
        robot.turnLeft();
    }
}//Escaper
