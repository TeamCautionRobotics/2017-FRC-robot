package org.usfirst.frc.team1492.robot.autonomous.commands;

import org.usfirst.frc.team1492.robot.DriveBase;
import org.usfirst.frc.team1492.robot.autonomous.Command;

import edu.wpi.first.wpilibj.Timer;

public class MoveStraightDistanceCommand implements Command {

    private DriveBase driveBase;

    private Timer timer;

    private boolean highGear;
    private double speed;
    private double distance;
    private boolean stopAtEnd;

    private boolean needsToStart;
    private boolean complete;

    private double heading;

    public MoveStraightDistanceCommand(DriveBase driveBase, boolean highGear, double speed, double distance, boolean stopAtEnd) {
        this.driveBase = driveBase;

        this.highGear = highGear;
        this.speed = speed;
        this.distance = distance;
        this.stopAtEnd = stopAtEnd;

        timer = new Timer();

        reset();
    }

    @Override
    public boolean run() {
        if (needsToStart) {
            // timer.reset();
            // timer.start();
            driveBase.useHighGear(highGear);
            driveBase.resetEncoders();
            heading = driveBase.getGyroAngle();

            driveBase.courseHeading = heading;

            needsToStart = false;
        }

//        complete = Math.abs(driveBase.getDistance()) >= distance;
        

        if (complete) {
            return true;
        } else {
            if (Math.abs(driveBase.getDistance()) >= distance) {
                if (stopAtEnd) {
                    driveBase.drive(0);
                }
                complete = true;
            } else {
                double angle = heading - driveBase.getGyroAngle();
                System.out.println("Angle: " + angle + "  Heading: " + heading);
                driveBase.drive(speed, speed - angle * 0.03);
            }
            return false;
        }
    }

    @Override
    public void reset() {
        needsToStart = true;
        complete = false;
    }

}
