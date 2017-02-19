package org.usfirst.frc.team1492.robot;

import edu.wpi.first.wpilibj.VictorSP;

class Winch {
	private VictorSP winch;

Winch(int motorChannel) {
        winch = new VictorSP(motorChannel);
}

    void moveWinch(boolean moveUp) {
        if (moveUp) {
            winch.set(-1);
        } else {
            winch.set(0);
        }
    }
}
