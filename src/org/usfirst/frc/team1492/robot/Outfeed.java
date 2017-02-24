package org.usfirst.frc.team1492.robot;

import edu.wpi.first.wpilibj.VictorSP;

public class Outfeed {
	private VictorSP outfeed;

	Outfeed(int motorChannel) {
		outfeed = new VictorSP(motorChannel);
	}
	
	void moveOutfeed(double motorPower) {
		outfeed.set(motorPower);
	}

}
