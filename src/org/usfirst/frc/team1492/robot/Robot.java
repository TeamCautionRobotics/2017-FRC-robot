package org.usfirst.frc.team1492.robot;

import org.usfirst.frc.team1492.robot.Gamepad.Axis;
import org.usfirst.frc.team1492.robot.Gamepad.Button;

import edu.wpi.first.wpilibj.IterativeRobot;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the IterativeRobot documentation. If you change the name of this class
 * or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class Robot extends IterativeRobot {

    DriveBase driveBase;

    Gamepad driver;
    Gamepad manipulator;

    GearPiston gearPiston;

    Winch winch;

    Doors doors;

    boolean shiftButtonPressed = false;
    boolean driveHighGear = false;

    boolean gearPistonButtonPressed = false;
    boolean gearPistonActivated = false;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        driveBase = new DriveBase(0, 1, 0);
        driveBase.useHighGear(false);

        gearPiston = new GearPiston(1);

        // TODO: Limit switch for winch
        winch = new Winch(3);

        doors = new Doors(2, 3, 4);

        driver = new Gamepad(0);
        manipulator = new Gamepad(1);
    }

    /**
     * This function is called once at the beginning of autonomous.
     */
    @Override
    public void autonomousInit() {
    	driveBase.resetGyro();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {
        driveHighGear = false;
        driveBase.useHighGear(false);
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {

        driveBase.drive(driver.getAxis(Axis.LEFT_Y), driver.getAxis(Axis.RIGHT_Y));

        if (driver.getButton(Button.LEFT_BUMPER)) {
            driveHighGear = false;
        }
        if (driver.getButton(Button.RIGHT_BUMPER)) {
            driveHighGear = true;
        }

        driveBase.useHighGear(driveHighGear);

        winch.moveWinch(manipulator.getButton(Button.X));

        boolean gearPistonButton = manipulator.getButton(Button.RIGHT_BUMPER);
        if (gearPistonButton != gearPistonButtonPressed) {
            gearPistonButtonPressed = gearPistonButton;
            if (gearPistonButton) {
                gearPistonActivated = !gearPistonActivated;
                gearPiston.latchGear(gearPistonActivated);
            }
        }

        doors.infeedOpen(manipulator.getButton(Button.Y));
        doors.outfeedOpen(manipulator.getButton(Button.A));

    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {}
}

