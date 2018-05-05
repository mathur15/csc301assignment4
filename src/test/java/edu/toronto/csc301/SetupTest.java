package edu.toronto.csc301;

import static edu.toronto.csc301.util.Asserts.assertClassExists;
import static edu.toronto.csc301.util.Asserts.assertClassHasConstructor;
import static edu.toronto.csc301.util.Asserts.assertClassHasDefaultConstructor;
import static edu.toronto.csc301.util.Asserts.assertClassImplementsInterface;

import org.junit.Test;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.robot.IGridRobot;
import edu.toronto.csc301.warehouse.IPathPlanner;
import edu.toronto.csc301.warehouse.IWarehouse;


public class SetupTest{

	private static final String PKG = "edu.toronto.csc301.";
	
	public static final String  GRID_ROBOT  = PKG + "robot.GridRobot";
	
	public static final String  WAREHOUSE   = PKG + "warehouse.Warehouse";
	public static final String  PATH_PLANNER = PKG + "warehouse.PathPlanner";
	
	
	
	@Test
	public void checkExistence_GridRobot() {
		assertClassExists(GRID_ROBOT);
	}
	
	@Test
	public void checkExistence_Warehouse() {
		assertClassExists(WAREHOUSE);
	}
	
	@Test
	public void checkExistence_PathPlanner() {
		assertClassExists(PATH_PLANNER);
	}
	
	
	
	
	@Test
	public void checkInterface_GridRobot() throws ClassNotFoundException {
		assertClassImplementsInterface(GRID_ROBOT, IGridRobot.class);
	}
	
	@Test
	public void checkInterface_Warehouse() throws ClassNotFoundException {
		assertClassImplementsInterface(WAREHOUSE, IWarehouse.class);
	}
	
	@Test
	public void checkInterface_PathPlanner() throws ClassNotFoundException {
		assertClassImplementsInterface(PATH_PLANNER, IPathPlanner.class);
	}
	
	
	
	@Test
	public void checkConstructor_GridRobot() throws ClassNotFoundException {
		assertClassHasConstructor(GRID_ROBOT, GridCell.class);
	}
	
	
	@Test
	public void checkConstructor_Warehouse() throws ClassNotFoundException {
		// The constructor takes an IGrid<Rack> (i.e. a floor-plan) as an argument
		assertClassHasConstructor(WAREHOUSE, IGrid.class);
	}
	
	@Test
	public void checkConstructor_PathPlanner() throws ClassNotFoundException {
		assertClassHasDefaultConstructor(PATH_PLANNER);
	}
}
