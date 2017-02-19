package org.usfirst.frc.team1492.robot.autonomous;

import java.util.ArrayList;
import java.util.HashMap;

public class Mission {
	
	int step = 0;
	
	ArrayList<Command> commands = new ArrayList<Command>();

	private int id;
	
	public Mission(int id, HashMap<Integer, Mission> missions) {
        missions.put(id, this);
        this.id = id;
	}
	
	public int getID(){
		return id;
	}

	public void reset(){
		step = 0;
		for(Command command : commands){
			command.reset();
		}
	}
	
	/**
	 * Executes the mission, must be called repeatedly
	 * 
	 * @return false if still running, true if the mission is complete.
	 */
	public boolean run(){
		if(step < commands.size()){
			if(commands.get(step).run()){
				step++;
				// Step 1 is first for display purposes:
    			System.out.println("Command " + step + "/" + commands.size() + " complete.");
			}
			return false;
		}else{
			return true;
		}
	}

	public void add(Command command) {
		commands.add(command);
	}
	
	

}
