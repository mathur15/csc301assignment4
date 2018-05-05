package edu.toronto.csc301.robot;

import java.util.ArrayList;

import edu.toronto.csc301.grid.GridCell;

public class GridRobot implements IGridRobot {
	
	ArrayList<StepListener>steplistener = new ArrayList<StepListener>();
	GridCell obj1;
	public GridRobot(GridCell obj)
	{
		if(obj == null){
			throw new NullPointerException();
		}
		this.obj1 = obj;
		
	}

	@Override
	public GridCell getLocation() {
		return obj1;
	}

	@Override
	public void step(Direction direction) {
		
		//start the step  before making a step to notify the listener
		
		for(StepListener o:steplistener){
			o.onStepStart(this, direction);
		}
		
		//change position according to step
		if(direction == Direction.NORTH){
			GridCell obj2 = GridCell.at(obj1.x,obj1.y+1);
			obj1 = obj2;
		}
		if(direction == Direction.EAST){
			GridCell obj2 = GridCell.at(obj1.x + 1,obj1.y);
			obj1 = obj2;
		}
		if(direction == Direction.SOUTH){
			GridCell obj2 = GridCell.at(obj1.x,obj1.y-1);
			obj1 = obj2;
		}
		if(direction == Direction.WEST){
			GridCell obj2 = GridCell.at(obj1.x - 1,obj1.y);
			obj1 = obj2;
		}
		
		//end the step and notify the listener
		for(StepListener o:steplistener){
			o.onStepEnd(this, direction);
		}
		
	}

	@Override
	public void startListening(StepListener listener) {
		//add on to the list of "notifications"
		steplistener.add(listener);
	}

	@Override
	public void stopListening(StepListener listener) {
		// TODO Auto-generated method stub
		//step is over
		steplistener.remove(listener);
		
	}
}
