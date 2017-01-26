package org.usfirst.frc.team1492.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveBase {

    VictorSP driveLeftA;
    VictorSP driveLeftB;
    VictorSP driveRightA;
    VictorSP driveRightB;
    Solenoid shifter;
    
    public DriveBase(int leftA, int leftB, int rightA, int rightB, int shifterChannel) {
        driveLeftA = new VictorSP(leftA);
        driveLeftB = new VictorSP(leftB);
        driveRightA = new VictorSP(rightA);
        driveRightB = new VictorSP(rightB);
        shifter = new Solenoid(shifterChannel);
    }
    
    public void drive(double left, double right){
        driveLeftA.set(-left);
        driveLeftB.set(-left);
        driveRightA.set(right);
        driveRightB.set(right);
    }

    public void drive(double speed){
        drive(speed, speed);
    }
    
    public void useHighGear(boolean highGear) {
        shifter.set(!highGear);
    }
}
