package org.usfirst.frc.team1492.robot;

import org.usfirst.frc.team1492.robot.Gamepad.Axis;
import org.usfirst.frc.team1492.robot.Gamepad.Button;

import edu.wpi.first.wpilibj.IterativeRobot;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    DriveBase driveBase;
    
    Gamepad driver;
    Gamepad manipulator;
    
    boolean shiftButtonPressed = false;
    boolean driveHighGear = true;
    
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
	    
	    driveBase = new DriveBase(0, 1, 0);
	    driveBase.shiftHighGear(true);
	    
	    driver = new Gamepad(0);
	    
	}

	/**
	 * This function is called once at the beginning of autonomous.
	 */
	@Override
	public void autonomousInit() {
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
	    driveBase.drive(driver.getAxis(Axis.LEFT_Y), driver.getAxis(Axis.RIGHT_Y));
	    
	    boolean shiftButton = driver.getButton(Button.A);
	    if (shiftButton != shiftButtonPressed) {
            shiftButtonPressed = shiftButton;
            if (shiftButton) {
                driveHighGear = !driveHighGear;
                driveBase.shiftHighGear(driveHighGear);
            }
        }
	}
	
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

