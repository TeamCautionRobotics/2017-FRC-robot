package org.usfirst.frc.team1492.robot.autonomous;

import java.util.ArrayList;

public class Mission {
	
	int step = 0;
	
	ArrayList<Command> commands = new ArrayList<Command>();
	
	void reset(){
		step = 0;
	}
	
	/**
	 * Executes the mission
	 * 
	 * @return false if still running, true if the mission is complete.
	 */
	boolean run(){
		if(step < commands.size()){
			if(commands.get(step).run()){
				step++;
			}
			return false;
		}else{
			return true;
		}
	}
	
	

}
