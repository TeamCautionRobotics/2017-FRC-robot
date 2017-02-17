package org.usfirst.frc.team1492.robot.autonomous;

import org.usfirst.frc.team1492.robot.Doors;
import org.usfirst.frc.team1492.robot.DriveBase;
import org.usfirst.frc.team1492.robot.GearPiston;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class CommandFactory {
	private DriveBase driveBase;
	private GearPiston gearPiston;
	private Doors doors;
	
	public CommandFactory(DriveBase driveBase, GearPiston gearPiston, Doors doors) {
		this.driveBase = driveBase;
		this.gearPiston = gearPiston;
		this.doors = doors;
	}
	
	public MoveStraightCommand makeMoveStraightCommand(double speed, double time){
		return new MoveStraightCommand(driveBase, speed, time);
	}

}
