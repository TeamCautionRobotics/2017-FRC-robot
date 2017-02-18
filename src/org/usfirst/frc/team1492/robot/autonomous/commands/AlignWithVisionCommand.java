package org.usfirst.frc.team1492.robot.autonomous.commands;

import org.usfirst.frc.team1492.robot.DriveBase;
import org.usfirst.frc.team1492.robot.autonomous.Command;

public class AlignWithVisionCommand implements Command {
	
	DriveBase driveBase;

	public AlignWithVisionCommand(DriveBase driveBase) {
		this.driveBase = driveBase;
		
		reset();
	}

	@Override
	public boolean run() {
		// TODO just say it's aligned for now.
		return true;
	}

	@Override
	public void reset() {
		
	}

}
