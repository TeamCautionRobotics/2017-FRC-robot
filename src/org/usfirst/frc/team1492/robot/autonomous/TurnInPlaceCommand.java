package org.usfirst.frc.team1492.robot.autonomous;

import org.usfirst.frc.team1492.robot.DriveBase;

public class TurnInPlaceCommand implements Command {

	private DriveBase driveBase;
	
	private double speed;
	private double targetAngle;
	
	private boolean needsToStart;
	private boolean complete;
	
	private double startAngle;

	public TurnInPlaceCommand(DriveBase driveBase, double speed, double targetAngle) {
		this.driveBase = driveBase;
		
		this.speed = speed;
		this.targetAngle = targetAngle;
		
		needsToStart = true;
		complete = false;
	}

	@Override
	public boolean run() {
		if(needsToStart){
			startAngle = driveBase.getGyroAngle();
			needsToStart = false;
		}
		
		if(complete){
			return true;
		}else{
			if(Math.abs(startAngle - driveBase.getGyroAngle()) >= Math.abs(targetAngle)){
				driveBase.drive(0);
				complete = true;
			}else{
				if(targetAngle > startAngle){
					driveBase.drive(speed, -speed);
				}else{
					driveBase.drive(-speed, speed);
				}
			}
			return false;
		}
	}

}
