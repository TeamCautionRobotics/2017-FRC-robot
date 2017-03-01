package org.usfirst.frc.team1492.robot.autonomous.commands;


import org.usfirst.frc.team1492.robot.DriveBase;
import org.usfirst.frc.team1492.robot.autonomous.Command;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;

public class MoveStraightCommand implements Command{
	
	private DriveBase driveBase;
	
	private Timer timer;
	
	private boolean highGear;
	private double speed;
	private double time;
	
	private boolean needsToStart;
	private boolean complete;

	private double heading;

	private Preferences preferences;

	public MoveStraightCommand(DriveBase driveBase, boolean highGear, double speed, double time) {
		this.driveBase = driveBase;
		
		this.highGear = highGear;
		this.speed = speed;
		this.time = time;
		
		timer = new Timer();
		
		preferences = Preferences.getInstance();
		preferences.putDouble("gyroGain", 0.03);
		preferences.putDouble("leftGyroGain", 0);

		reset();
	}

	@Override
	public boolean run() {
		if(needsToStart){
			timer.reset();
			timer.start();
			driveBase.useHighGear(highGear);
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
				System.out.println("Angle: "+angle+"  Heading: "+heading);
				double gain = preferences.getDouble("gyroGain", 0.03);
				double leftGain = preferences.getDouble("leftGyroGain", 0);
				driveBase.drive(speed + angle*leftGain, speed - angle*gain);
			}
			return false;
		}
	}

	@Override
	public void reset() {
		needsToStart = true;
		complete = false;
	}

}
