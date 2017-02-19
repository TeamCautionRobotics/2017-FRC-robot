package org.usfirst.frc.team1492.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class GearPiston {
	private Solenoid gearLatch;

    GearPiston(int latchChannel) {
        gearLatch = new Solenoid(latchChannel);
    }

    void latchGear(boolean latch) {
        gearLatch.set(latch);
    }
}
