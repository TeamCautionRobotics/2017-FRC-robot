package org.usfirst.frc.team1492.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveBase {

    VictorSP driveLeft;
    VictorSP driveRight;
    Solenoid shifter;
    
    public DriveBase(int left, int right, int shifterChannel) {
        
        driveLeft = new VictorSP(left);
        driveRight = new VictorSP(right);
        shifter = new Solenoid(shifterChannel);
        
    }
    
    public void drive(double left, double right){
        
        driveLeft.set(left);
        driveRight.set(right);
        
    }

    public void drive(double speed){
        
        drive(speed, speed);
        
    }
    
    public void shiftHighGear(boolean highGear) {
        shifter.set(highGear);
    }
}
