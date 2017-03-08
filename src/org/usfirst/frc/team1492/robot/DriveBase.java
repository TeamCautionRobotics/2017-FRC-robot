package org.usfirst.frc.team1492.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;

public class DriveBase implements PIDOutput, PIDSource {

    private VictorSP driveLeft;
    private VictorSP driveRight;
    private Solenoid shifter;

    private Encoder leftEncoder;
    private Encoder rightEncoder;

    private ADXRS450_Gyro gyro;

    private double heading;

    public DriveBase(int left, int right, int shifterChannel, int leftA, int leftB, int rightA, int rightB) {
        driveLeft = new VictorSP(left);
        driveRight = new VictorSP(right);

        leftEncoder = new Encoder(leftA, leftB, false, EncodingType.k4X);
        rightEncoder = new Encoder(rightA, rightB, true, EncodingType.k4X);

        leftEncoder.setDistancePerPulse((4 * Math.PI) / 1024);
        rightEncoder.setDistancePerPulse((4 * Math.PI) / 1024);

        shifter = new Solenoid(shifterChannel);
        
        gyro = new ADXRS450_Gyro();
        gyro.calibrate();
        heading = gyro.getAngle();
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

    public void resetEncoders() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public double getDistance() {
        return getRightDistance();
    }

    public double getRightDistance() {
        return rightEncoder.getDistance();
    }

    public double getRightSpeed() {
        return rightEncoder.getRate();
    }


    public void pidInit() {
        heading = getGyroAngle();
    }

    @Override
    public void pidWrite(double speed) {
        double angle = heading - getGyroAngle();
        drive(speed, speed - angle * 0.03);
    }


    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        // TODO Auto-generated method stub
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        return getDistance();
    }
}
