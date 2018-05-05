package edu.toronto.csc301.warehouse;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.robot.IGridRobot;
import edu.toronto.csc301.robot.IGridRobot.Direction;
public class PathPlanner implements IPathPlanner {
	public Entry<IGridRobot, Direction> nextStep(IWarehouse warehouse, Map<IGridRobot, GridCell> robot2dest) {
		
		GridCell dest = robot2dest.values().iterator().next();
		IGridRobot robot = robot2dest.keySet().iterator().next();
		GridCell roboLoc = robot.getLocation();
		if (roboLoc.equals(dest)){
			return null;
		}
		
		List<Integer> cellsX = new ArrayList<Integer>();
		List<Integer> cellsY = new ArrayList<Integer>();
		Iterator<GridCell> gridCells = warehouse.getFloorPlan().getGridCells();
		//record x and y coordinates in lists
		while (gridCells.hasNext()){
			GridCell cell = gridCells.next();
			if (!cellsX.contains(cell.x))
				cellsX.add(cell.x);
			if (!cellsY.contains(cell.y))
				cellsY.add(cell.y);
		}
		
		int maxX = Collections.max(cellsX);
		int maxY = Collections.max(cellsY);
		int grid[][] = new int[maxX+1][maxY+1];
		gridCells = warehouse.getFloorPlan().getGridCells();
		
		while (gridCells.hasNext()){
			GridCell cell = gridCells.next();
			grid[cell.x][cell.y] = cell.y; 
		}
		
		Map<GridCell, Boolean> visited = new HashMap<GridCell, Boolean>();
		Map<GridCell, GridCell> prev = new HashMap<GridCell, GridCell>();
		List<GridCell> directions = new LinkedList<GridCell>();
		Queue<GridCell> q = new LinkedList<GridCell>();
		//Main BFS traversal
		GridCell current = roboLoc;
		q.add(current);
		visited.put(current, true);
		while (!q.isEmpty()){
			current = q.remove();
			if (current.equals(dest)){
				break;
			} else {
				for (GridCell cell: getNeighbours(current, grid)){
					if (!visited.containsKey(cell)){
						Iterator<IGridRobot> robots = warehouse.getRobots();
						boolean robotFound = false;
						while (robots.hasNext()){
							IGridRobot rob = robots.next();
							if (rob.getLocation().equals(cell)){
								robotFound = true;
							}
						}
						
						if (!robotFound){
							if (warehouse.getFloorPlan().hasCell(cell)){
								q.add(cell);
								visited.put(cell, true);
								prev.put(cell, current);
							}	
						}
					}
				}
			}
		}
		
		for (GridCell cell = dest; cell != null; cell = prev.get(cell)){
			directions.add(cell);
		}
		//determining direction for next move
		Collections.reverse(directions);
		GridCell nextCell = directions.get(1);
		Direction direction = null;
		if (nextCell.y == roboLoc.y + 1){
			direction = Direction.NORTH;
		} else if (nextCell.y == roboLoc.y - 1){
			direction = Direction.SOUTH;
		}
		if (nextCell.x == roboLoc.x + 1){
			direction = Direction.EAST;
		} else if (nextCell.x == roboLoc.x - 1){
			direction = Direction.WEST;
		}
		//both steps determined
		Entry<IGridRobot, Direction> step =
				new AbstractMap.SimpleEntry<IGridRobot, Direction>(robot, direction);
		return step;
	}
	
    //return immediate neighbors of the current grid as part of BFS algorithm which will be added to queue
	private List<GridCell> getNeighbours(GridCell current, int grid[][]) {
		List<GridCell> neighbours = new ArrayList<GridCell>();
		try{
			neighbours.add(GridCell.at(current.x, grid[current.x][current.y + 1]));
		} catch (Exception e) {}
		try{
			neighbours.add(GridCell.at(current.x, grid[current.x][current.y - 1]));
		} catch (Exception e) {}
		try{
			neighbours.add(GridCell.at(current.x - 1, grid[current.x - 1][current.y]));
		} catch (Exception e){}
		try{
			neighbours.add(GridCell.at(current.x + 1, grid[current.x + 1][current.y]));
		} catch (Exception e){}
		return neighbours;
	}
}
