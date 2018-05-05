package edu.toronto.csc301.warehouse;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.robot.IGridRobot;
import edu.toronto.csc301.robot.IGridRobot.Direction;
import edu.toronto.csc301.robot.GridRobot;

public class Warehouse implements IWarehouse,IGridRobot.StepListener {
	
    IGrid<Rack>obj1;
    ArrayList<IGridRobot> list = new ArrayList<IGridRobot>();
    HashMap<IGridRobot,Direction> mp = new HashMap<IGridRobot,Direction>();
    List<Consumer<IWarehouse>> observ  = new ArrayList<Consumer<IWarehouse>>();
    
	public Warehouse(IGrid<Rack> obj){
		if(obj ==  null){
			throw new NullPointerException();
		}
		this.obj1 = obj;
	}

	@Override
	public IGrid<Rack> getFloorPlan() {
		return this.obj1;
		
	}

	@Override
	public IGridRobot addRobot(GridCell initialLocation) {
		//check that robot motion cannot be changed from the outside - check if a gridCell in list of gridCells
		Iterator<GridCell> itr = obj1.getGridCells();
		int indicator = 0;
		
		//loop over the gridCells
		while(itr.hasNext()){
			if(itr.next().equals(initialLocation)){
				indicator = 1;
			}
		}
		
		//initialLocation not in Rack
		if(indicator == 0){
			throw new IllegalArgumentException();
		}
		
		//check if Robot is out of the cell
		if(!obj1.hasCell(initialLocation)){
			throw new IllegalArgumentException();
		}
		
		//check if none of the existing robots are at the same location or not
		for(IGridRobot obj3: list){
			if(obj3.getLocation() == initialLocation){
				throw new IllegalArgumentException();
			}
		}
		
		//create IGridRobot and add to collection
		IGridRobot ro = new GridRobot(initialLocation);
		for(Consumer<IWarehouse> observer:observ){
			observer.accept(this);
		}
		
		ro.startListening(this);
		list.add(ro);
		return ro;
	}

	@Override
	public Iterator<IGridRobot> getRobots() {
	    return list.iterator();
		
	}

	@Override
	public Map<IGridRobot, Direction> getRobotsInMotion() {
		
		return new HashMap<IGridRobot,Direction>(mp);
	}

	@Override
	public void subscribe(Consumer<IWarehouse> observer) {
		observ.add(observer);
		
	}

	@Override
	public void unsubscribe(Consumer<IWarehouse> observer) {
		//remove observers
		observ.remove(observer);
		
		
	}

	@Override
	public void onStepStart(IGridRobot robot, Direction direction) {
		//put the robot in motion-add to list
		mp.put(robot, direction);
		
		for(Consumer<IWarehouse> observer:observ){
			observer.accept(this);
		}
	}

	@Override
	public void onStepEnd(IGridRobot robot, Direction direction) {
		//remove robot from robots in motion list
		mp.remove(robot);
		//notify observer
		for(Consumer<IWarehouse> observer:observ){
			observer.accept(this);
		}
	}

}
