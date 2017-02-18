package org.usfirst.frc.team1492.robot.autonomous;

import org.usfirst.frc.team1492.robot.Doors;
import org.usfirst.frc.team1492.robot.DriveBase;
import org.usfirst.frc.team1492.robot.GearPiston;
import org.usfirst.frc.team1492.robot.autonomous.commands.AlignWithVisionCommand;
import org.usfirst.frc.team1492.robot.autonomous.commands.DealyCommand;
import org.usfirst.frc.team1492.robot.autonomous.commands.SetGearPiston;
import org.usfirst.frc.team1492.robot.autonomous.commands.MoveStraightCommand;
import org.usfirst.frc.team1492.robot.autonomous.commands.TurnInPlaceCommand;

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
	
	public Command moveStraight(boolean highGear, double speed, double time){
		return new MoveStraightCommand(driveBase, highGear, speed, time);
	}
	
	public Command turnInPlace(boolean highGear, double speed, double targetAngle){
		return new TurnInPlaceCommand(driveBase, highGear, speed, targetAngle);
	}

	public Command alignWithVision() {
		return new AlignWithVisionCommand(driveBase);
	}

	public Command setGearPiston(boolean out) {
		return new SetGearPiston(gearPiston, out);
	}

	public Command wait(double time) {
		return new DealyCommand(time);
	}

}
