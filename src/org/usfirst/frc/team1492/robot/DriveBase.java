package org.usfirst.frc.team1492.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveBase {

    private VictorSP driveLeft;
    private VictorSP driveRight;
    private Solenoid shifter;
    
    private ADXRS450_Gyro gyro;

    public DriveBase(int left, int right, int shifterChannel) {
        driveLeft = new VictorSP(left);
        driveRight = new VictorSP(right);

        shifter = new Solenoid(shifterChannel);
        
        gyro = new ADXRS450_Gyro();
        gyro.calibrate();
    }

    public void drive(double left, double right) {
        driveLeft.set(left);
        driveRight.set(-right);
    }

    public void drive(double speed) {
        drive(speed, speed);
    }

    public void useHighGear(boolean highGear) {
        shifter.set(highGear);
    }

	public void resetGyro() {
        gyro.reset();
	}

	public double getGyroAngle() {
		return gyro.getAngle();
	}
}
