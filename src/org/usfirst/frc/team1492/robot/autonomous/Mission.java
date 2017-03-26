package org.usfirst.frc.team1492.robot.autonomous;

import java.util.ArrayList;

public class Mission {
	
	int step = 0;
	
	ArrayList<Command> commands = new ArrayList<Command>();

	private String name;
	
	public Mission(String name) {
        this.name = name;
	}
	
	public String getName(){
		return name;
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
