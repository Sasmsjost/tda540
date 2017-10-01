public class Escaper { 
	private Robot robot; 
	public static void main(String[] args) { 
		Escaper escaper = new Escaper(); 
		escaper.createEnviroment(); 
		escaper.moveToEntrance (); 
	}//main 

	public void createEnviroment() { 
		RobotWorld  world = RobotWorld.load("src/room.txt");
		robot = new Robot(3, 3, Robot.WEST, world); 
		robot.setDelay(250); 
	}//createEnviroment 
 
	//before: robot is inside the room
	//after:  robot is in the cell representing the the dorrjamb
	public void moveToEntrance() {
	    driveToWall();
	    robot.turnLeft();
	    while (true){
            robot.move();
            if (checkExit()){
                turnRight();
                robot.move();
                return;
            }
            if (!robot.frontIsClear()){
                robot.turnLeft();
            }

	    }
    }

	private boolean checkExit(){
	    boolean exit;
        robot.turnLeft();
        robot.turnLeft();
        robot.turnLeft();
        exit = robot.frontIsClear();
        robot.turnLeft();
        return exit;
    }

	private void driveToWall(){
	    while (robot.frontIsClear()){
	        robot.move();
        }
    }

    private void turnRight(){
	    robot.turnLeft();
        robot.turnLeft();
        robot.turnLeft();
    }
	// moveToEntrance
}//Escaper
