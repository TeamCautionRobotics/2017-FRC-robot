package org.usfirst.frc.team1492.robot.autonomous;

public interface Command {
	
	/**
	 * Executes the command
	 * 
	 * @return false if still running, true if the command is complete.
	 */
	boolean run();
	
}
