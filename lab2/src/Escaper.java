public class Escaper { 
	private Robot robot;

	private int RIGHT = 0b0001;
	private int LEFT =  0b0010;
	private int FRONT = 0b0100;
	private int BACK =  0b1000;

	public static void main(String[] args) { 
		Escaper escaper = new Escaper(); 
		escaper.createEnviroment(); 
		escaper.moveToEntrance (); 
	}//main 

	public void createEnviroment() { 
		RobotWorld  world = RobotWorld.load("src/room.txt");
		robot = new Robot(3, 5, Robot.EAST, world);
		robot.setDelay(250);
	}//createEnviroment 
 
	//before: robot is inside the room
	//after:  robot is in the cell representing the the dorrjamb
	public void moveToEntrance() {
	    while(!tryEscape()) {
            if (!robot.frontIsClear()){
                robot.turnLeft();
            }
            if(robot.frontIsClear()) {
                forward();
            }
        }
    }

    // Before: Any
    // After: Same, if returned false
    //        At exit, if returned true
    private boolean tryEscape() {
        int surroundings = scanSurroundings();

        if(bothSidesAreBlocked(surroundings)) {
            return true;
        } else if(!sideIsBlocked(RIGHT, surroundings)) {
            return checkRightOpening();
        } else {
            return false;
        }
    }

    // Before: Any
    // After: Same, if right opening is not an exit
    //        Positioned at exit turned in exit orientation, if right opening is an exit
    private boolean checkRightOpening() {
	    turnRight();
        forward();

        int surroundings = scanSurroundings();

        if(bothSidesAreBlocked(surroundings)) {
            return true;
        } else {
            turnAround();
            forward();
            turnRight();

            return false;
        }
    }

    /**
     * Before: Any
     * After: Same
     *
     * Using a bitmask, check if both sides are blocked
     *
     * @param surroundings - An integer with the scan data from the robots surroundings,
     *                     can be retrieved from scanSurroundings
     * @return whether both sides are blocked
     */
    private boolean bothSidesAreBlocked(int surroundings) {
        boolean left = sideIsBlocked(LEFT, surroundings);
        boolean right = sideIsBlocked(RIGHT, surroundings);

        return left && right;
    }

    /**
     * Before: Any
     * After: Same
     *
     * Using a bitmask, check if side is blocked
     *
     * @param side - A bitmask for the side to check: RIGHT, LEFT, TOP, BOTTOM
     * @param surroundings - A bit field with the scan data from the robots surroundings,
     *                     can be retrieved from scanSurroundings
     * @return whether the side is blocked
     */
    private boolean sideIsBlocked(int side, int surroundings) {
	    return (surroundings & side) == side;
    }

    /**
     * Before: Any
     * After: Same
     *
     * @return A bit field representing the surroundings. A bit set to 1 means blocked
     */
    private int scanSurroundings() {
	    int value = robot.frontIsClear() ? 0:FRONT;
	    robot.turnLeft();

        value += robot.frontIsClear() ? 0:LEFT;
        robot.turnLeft();

        value += robot.frontIsClear() ? 0:BACK;
        robot.turnLeft();

        value += robot.frontIsClear() ? 0:RIGHT;
        robot.turnLeft();

        return value;
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

    // Before: Any without a wall in front
    // After: Same orientation, on cell forward
    private void forward() {
        robot.move();
    }
	// moveToEntrance
}//Escaper
