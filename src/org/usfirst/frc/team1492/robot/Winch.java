package org.usfirst.frc.team1492.robot;

import edu.wpi.first.wpilibj.VictorSP;

public class Winch {
	VictorSP winch;
	
	enum WinchDirections {UP, DOWN, STOP}

	public Winch(int motorChannel) {
		winch = new VictorSP(motorChannel);
	}
	
	public void moveWinch(WinchDirections direction) {
		switch (direction) {
		case UP:
			winch.set(0.5);
			break;
		case DOWN:
			winch.set(-0.5);
			break;
		case STOP:
			winch.set(0);
			break;
		}
	}	
}
