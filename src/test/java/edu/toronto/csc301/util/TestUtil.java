package edu.toronto.csc301.util;

import java.util.Random;

import edu.toronto.csc301.SetupTest;
import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.robot.IGridRobot;
import edu.toronto.csc301.robot.IGridRobot.Direction;
import edu.toronto.csc301.warehouse.IPathPlanner;
import edu.toronto.csc301.warehouse.IWarehouse;
import edu.toronto.csc301.warehouse.Rack;

/**
 * Helper functions used by other test classes.
 */
public class TestUtil {
	
	
	private static Random random = new Random();
	
	

	public static IGridRobot createGridRobot(GridCell initialLocation) throws Exception {
		return Helpers.newInstance(SetupTest.GRID_ROBOT, 
				new Class<?>[]{GridCell.class}, initialLocation);
	}

	
	public static IWarehouse createWarehouse(IGrid<Rack> floorPlan) throws Exception {
		return Helpers.newInstance(SetupTest.WAREHOUSE, 
				new Class<?>[]{IGrid.class}, floorPlan);
	}
	
	public static IPathPlanner createPathPlanner() throws Exception {
		return Helpers.newInstance(SetupTest.PATH_PLANNER);
	}
	
	
	
	//
	// WARNING: Do NOT use this helper function in your application code.
	//          This helper function is part of the testing code, and an application 
	//          should NEVER depend on its testing code.
	//
	//          Feel free to copy-paste it to one of your classes under src/main,
	//          and use it from there.
	//          
	public static GridCell oneCellOver(GridCell location, Direction direction){
		switch (direction) {
		case NORTH:
			return GridCell.at(location.x, location.y + 1);
		case EAST:
			return GridCell.at(location.x + 1, location.y);
		case SOUTH:
			return GridCell.at(location.x, location.y - 1);
		case WEST:
			return GridCell.at(location.x - 1, location.y);
		default:
			return null;
		}
	}
	
	
	
	
	
	public static Direction randomDirection(){
		Direction[] directions = Direction.values();
		return directions[random.nextInt(directions.length)];
	}
	
	public static GridCell randomCell(){
		return GridCell.at(randomInt(-10000, 10000), randomInt(-10000, 10000));
	}
	
	/**
	 * Return a random integer in the range [a, b).
	 * That is, including a and excluding b.
	 */
	public static int randomInt(int a, int b){
		return a + random.nextInt(b - a);
	}

}
