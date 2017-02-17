package org.usfirst.frc.team1492.robot.autonomous;


import org.usfirst.frc.team1492.robot.DriveBase;

import edu.wpi.first.wpilibj.Timer;

public class MoveStraightCommand implements Command{
	
	private DriveBase driveBase;
	
	private Timer timer;
	
	private double speed;
	private double time;
	
	private boolean needsToStart;
	private boolean complete;

	private double heading;

	public MoveStraightCommand(DriveBase driveBase, double speed, double time) {
		this.driveBase = driveBase;
		
		this.speed = speed;
		this.time = time;
		needsToStart = true;
		complete = false;
		
		timer = new Timer();
	}

	@Override
	public boolean run() {
		if(needsToStart){
			timer.reset();
			timer.start();
			heading = driveBase.getGyroAngle();
			needsToStart = false;
		}
		
		if(complete){
			return true;
		}else{
			if(timer.get() >= time){
				driveBase.drive(0);
				timer.stop();
				complete = true;
			}else{
				double angle = heading - driveBase.getGyroAngle();
				driveBase.drive(speed, speed - angle*0.03);
			}
			return false;
		}
	}

}
