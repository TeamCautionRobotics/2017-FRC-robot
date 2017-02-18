package org.usfirst.frc.team1492.robot;

import org.usfirst.frc.team1492.robot.Gamepad.Axis;
import org.usfirst.frc.team1492.robot.Gamepad.Button;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.VictorSP;


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

    VictorSP outfeedMotor;

    Doors doors;

    boolean driveHighGear = false;

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

        outfeedMotor = new VictorSP(2);

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

        gearPiston.latchGear(driver.getButton(Button.A));

        doors.infeedOpen(manipulator.getButton(Button.Y));
        doors.outfeedOpen(manipulator.getButton(Button.A));
        doors.epiglottisSwitch(manipulator.getButton(Button.B));

        outfeedMotor.set(manipulator.getAxis(Axis.RIGHT_Y));
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {}
}

