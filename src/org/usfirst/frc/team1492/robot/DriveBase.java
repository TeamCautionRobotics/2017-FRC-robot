package org.usfirst.frc.team1492.robot;

import edu.wpi.first.wpilibj.VictorSP;

public class DriveBase {

    VictorSP driveLeft;
    VictorSP driveRight;
    
    public DriveBase(int left, int right) {
        
        driveLeft = new VictorSP(left);
        driveRight = new VictorSP(right);
        
    }
    
    public void drive(double left, double right){
        
        driveLeft.set(left);
        driveRight.set(right);
        
    }

    public void drive(double speed){
        
        drive(speed, speed);
        
    }
}
