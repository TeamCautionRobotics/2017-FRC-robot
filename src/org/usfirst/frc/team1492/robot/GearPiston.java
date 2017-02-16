package org.usfirst.frc.team1492.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class GearPiston {
    Solenoid gearLatch;

    public GearPiston(int latchChannel) {
        gearLatch = new Solenoid(latchChannel);
    }

    public void latchGear(boolean latch) {
        gearLatch.set(latch);
    }
}
