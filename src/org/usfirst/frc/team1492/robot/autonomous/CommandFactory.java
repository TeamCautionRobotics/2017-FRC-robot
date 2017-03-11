package org.usfirst.frc.team1492.robot.autonomous;

import org.usfirst.frc.team1492.robot.Doors;
import org.usfirst.frc.team1492.robot.DriveBase;
import org.usfirst.frc.team1492.robot.GearPiston;
import org.usfirst.frc.team1492.robot.autonomous.commands.AlignWithVisionCommand;
import org.usfirst.frc.team1492.robot.autonomous.commands.DelayCommand;
import org.usfirst.frc.team1492.robot.autonomous.commands.MoveStraightCommand;
import org.usfirst.frc.team1492.robot.autonomous.commands.MoveStraightPIDCommand;
import org.usfirst.frc.team1492.robot.autonomous.commands.ResetEncoders;
import org.usfirst.frc.team1492.robot.autonomous.commands.SetGearPiston;
import org.usfirst.frc.team1492.robot.autonomous.commands.TurnInPlaceCommand;

public class CommandFactory {
	private DriveBase driveBase;
	private GearPiston gearPiston;
	private Doors doors;
	
	public CommandFactory(DriveBase driveBase, GearPiston gearPiston, Doors doors) {
		this.driveBase = driveBase;
		this.gearPiston = gearPiston;
		this.doors = doors;
	}
	
	public Command moveStraight(double speed, double time) {
	    return moveStraight(false, speed, time);
	}
	
	public Command moveStraight(boolean highGear, double speed, double time) {
		return new MoveStraightCommand(driveBase, highGear, speed, time);
	}
	
	public Command moveStraightPID(double distance) {
	    return new MoveStraightPIDCommand(driveBase, false, 1.0, distance);
	}
	
	public Command moveStraightPID(boolean highGear, double maxSpeed, double distance) {
        return new MoveStraightPIDCommand(driveBase, highGear, maxSpeed, distance);
    }

	
	public Command turnInPlace(double speed, double targetAngle) {
	    return turnInPlace(false, speed, targetAngle);
	}
	
	public Command turnInPlace(boolean highGear, double speed, double targetAngle) {
		return new TurnInPlaceCommand(driveBase, highGear, speed, targetAngle);
	}

	public Command resetEncoders() {
	    return new ResetEncoders(driveBase);
	}

	public Command alignWithVision() {
		return new AlignWithVisionCommand(driveBase, false);
	}

	public Command alignWithVision(boolean testing) {
		return new AlignWithVisionCommand(driveBase, testing);
	}

	public Command setGearPiston(boolean out) {
		return new SetGearPiston(gearPiston, out);
	}

	public Command delay(double time) {
		return new DelayCommand(time);
	}

}
