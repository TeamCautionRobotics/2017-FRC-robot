package org.usfirst.frc.team1492.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;

public class Winch {
	VictorSP winch;
	DigitalInput winchKill;
	
	enum WinchDirections {OUT, IN, OFF}

	public Winch(int motorChannel) {
		winch = new VictorSP(motorChannel);
	}
	
	public void moveWinch(WinchDirections direction) {
		switch (direction) {
		case OUT:
			winch.set(0.5);
			break;
		case IN:
			winch.set(-0.5);
			break;
		case OFF:
			winch.set(0);
			break;
		}
	}
	
	public void tick() {
		if (winchKill.get()) {
			moveWinch(WinchDirections.OFF);
		}
	}
	
}
