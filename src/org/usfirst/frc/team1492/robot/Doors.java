package org.usfirst.frc.team1492.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Doors {

    Solenoid epiglottis;
    Solenoid outfeed;
    Solenoid infeed;

    public Doors(int epiglottisChannel, int outfeedChannel, int infeedChannel) {
        epiglottis = new Solenoid(epiglottisChannel);
        outfeed = new Solenoid(outfeedChannel);
        infeed = new Solenoid(infeedChannel);
    }

    public void epiglottisSwitch(boolean down) {
        epiglottis.set(down);
    }

    public void outfeedOpen(boolean open) {
        outfeed.set(open);
    }

    public void infeedOpen(boolean open) {
        infeed.set(open);
    }
}
