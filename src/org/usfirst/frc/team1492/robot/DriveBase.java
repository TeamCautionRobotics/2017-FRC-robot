package org.usfirst.frc.team1492.robot;

import java.lang.reflect.Constructor;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;

public class DriveBase {

    SpeedController driveLeftA;
    SpeedController driveLeftB;
    SpeedController driveRightA;
    SpeedController driveRightB;
    Solenoid shifter;

    public DriveBase(Class<? extends SpeedController> controller, int leftA, int leftB,
            int rightA, int rightB, int shifterChannel) {
        try {
            Constructor<? extends SpeedController> constructor = controller.getConstructor(int.class);
            driveLeftA = constructor.newInstance(leftA);
            driveLeftB = constructor.newInstance(leftB);
            driveRightA = constructor.newInstance(rightA);
            driveRightB = constructor.newInstance(rightB);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
