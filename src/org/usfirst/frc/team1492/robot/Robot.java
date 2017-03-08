package org.usfirst.frc.team1492.robot;

import org.usfirst.frc.team1492.robot.Gamepad.Axis;
import org.usfirst.frc.team1492.robot.Gamepad.Button;
import org.usfirst.frc.team1492.robot.autonomous.CommandFactory;
import org.usfirst.frc.team1492.robot.autonomous.Mission;
import org.usfirst.frc.team1492.robot.autonomous.MissionSendable;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the IterativeRobot documentation. If you change the name of this class
 * or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class Robot extends IterativeRobot {

    static {
        System.loadLibrary("pixy_java");
    }

    DriveBase driveBase;

    EnhancedJoystick driverLeft;
    EnhancedJoystick driverRight;
    Gamepad manipulator;

    GearPiston gearPiston;

    Winch winch;

    Doors doors;

    Mission activeMission;
    CommandFactory commandFactory;
    SendableChooser<Mission> missionChooser = new SendableChooser<Mission>();

    Outfeed outfeed;

    boolean driveHighGear = false;

    MissionSendable missionSendable;

    Mission deployGear;

    boolean infeedButtonPressed = false;
    boolean infeedOpen = false;

    boolean gearDeployButtonPressed = false;
    boolean gearDeployRunning = false;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        driveBase = new DriveBase(0, 1, 0, 0, 1, 2, 3);
        driveBase.useHighGear(false);

        SmartDashboard.putData("Drive PID", driveBase.pidController);

        gearPiston = new GearPiston(1);

        // TODO: Limit switch for winch
        winch = new Winch(3);

        doors = new Doors(2, 3, 4);

        outfeed = new Outfeed(2);

        driverLeft = new EnhancedJoystick(0, 0.1);
        driverRight = new EnhancedJoystick(1, 0.1);
        manipulator = new Gamepad(2);

        commandFactory = new CommandFactory(driveBase, gearPiston, doors);

        Mission testMission = new Mission(0);
        testMission.add(commandFactory.moveStraight(false, 0.4, 2.0));
        testMission.add(commandFactory.turnInPlace(false, 0.4, 50));
        testMission.add(commandFactory.alignWithVision());
        testMission.add(commandFactory.setGearPiston(true));
        testMission.add(commandFactory.delay(1.0));
        testMission.add(commandFactory.moveStraight(false, -0.4, 1.0));
        testMission.add(commandFactory.setGearPiston(false));
        missionChooser.addObject("testMission", testMission);

        Mission goBack = new Mission(1);
        goBack.add(commandFactory.moveStraight(true, -0.4, 3.0));
        missionChooser.addObject("goBack", goBack);
<<<<<<< HEAD
=======

        Mission missionLeft = new Mission(2);
        missionLeft.add(commandFactory.moveStraight(false, 0.4, 2.0));
        missionLeft.add(commandFactory.turnInPlace(false, 0.4, 50));
        missionLeft.add(commandFactory.alignWithVision());
        missionLeft.add(commandFactory.setGearPiston(true));
        missionLeft.add(commandFactory.delay(1.0));
        missionLeft.add(commandFactory.moveStraight(false, -0.4, 1.0));
        missionLeft.add(commandFactory.setGearPiston(false));
        missionChooser.addObject("mission left", missionLeft);

        Mission missionCenter = new Mission(4);
        missionCenter.add(commandFactory.moveStraight(false, 0.4, 0.5));
        missionCenter.add(commandFactory.alignWithVision());
        missionCenter.add(commandFactory.setGearPiston(true));
        missionCenter.add(commandFactory.delay(1.0));
        missionCenter.add(commandFactory.setGearPiston(false));
        missionChooser.addObject("mission center", missionCenter);

        Mission missionRight = new Mission(5);
        missionRight.add(commandFactory.moveStraight(false, 0.4, 2.0));
        missionRight.add(commandFactory.turnInPlace(false, 0.4, -50));
        missionRight.add(commandFactory.alignWithVision());
        missionRight.add(commandFactory.setGearPiston(true));
        missionRight.add(commandFactory.delay(1.0));
        missionRight.add(commandFactory.moveStraight(false, -0.4, 1.0));
        missionRight.add(commandFactory.setGearPiston(false));
        missionChooser.addObject("mission right", missionRight);
>>>>>>> Add initial auto commands

        Mission visionTest = new Mission(3);
        visionTest.add(commandFactory.alignWithVision(true));
        missionChooser.addObject("Vision Test", visionTest);

        deployGear = new Mission(6);
        deployGear.add(commandFactory.moveStraight(false, 0, 0));
        deployGear.add(commandFactory.alignWithVision());
        deployGear.add(commandFactory.delay(0.7));
        deployGear.add(commandFactory.setGearPiston(true));
        deployGear.add(commandFactory.delay(0.5));
        deployGear.add(commandFactory.moveStraight(-1, 0.2));
        deployGear.add(commandFactory.setGearPiston(false));
        deployGear.add(commandFactory.moveStraight(true, 0, 0));
        missionChooser.addObject("deploy gear", deployGear);

<<<<<<< HEAD
        Mission turnReset = new Mission(7);
        turnReset.add(commandFactory.turnInPlace(0.2, 180));
        turnReset.add(commandFactory.delay(0.3));
        turnReset.add(commandFactory.resetEncoders());
        missionChooser.addObject("turn, reset encoders", turnReset);

        Mission encoderReset = new Mission(8);
        encoderReset.add(commandFactory.resetEncoders());
        missionChooser.addObject("reset encoders", encoderReset);

        Mission driveForwardPID = new Mission(9);
        driveForwardPID.add(commandFactory.moveStraightPID(80));
        missionChooser.addObject("pid drive 80", driveForwardPID);
=======
        SmartDashboard.putData("auto mission", missionChooser);
>>>>>>> Add initial auto commands

        Mission noMovePID = new Mission(10);
        noMovePID.add(commandFactory.moveStraightPID(0));
        missionChooser.addObject("noMovePID", noMovePID);

        SmartDashboard.putData("auto mission", missionChooser);
        missionSendable = new MissionSendable("Teleop Mission", () -> missionChooser.getSelected());
        SmartDashboard.putData(missionSendable);
    }

    /**
     * This function is called once at the beginning of autonomous.
     */
    @Override
    public void autonomousInit() {
    	driveBase.resetGyro();
    	driveBase.resetEncoders();

        activeMission = missionChooser.getSelected();
    	
    	if(activeMission != null){
    		activeMission.reset();
			System.out.println("Mission " + activeMission.getID() + " Started");
    	}
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
    	if(activeMission != null){
    		if(activeMission.run()){
    			System.out.println("Mission " + activeMission.getID() + " Complete");
    			activeMission = null;
    		}
    	}
    }

    @Override
    public void teleopInit() {
        driveHighGear = false;
        driveBase.useHighGear(false);

        driveBase.resetEncoders();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        SmartDashboard.putNumber("right encoder", driveBase.getRightDistance());
        SmartDashboard.putNumber("right encoder num", driveBase.getRightDistance());
        SmartDashboard.putBoolean("pid arrived", driveBase.pidController.onTarget());

        if ((missionSendable.run() && missionChooser.getSelected().getID() != 3
                || driveBase.pidController.isEnabled())) {
            return;
        }

        boolean gearDeployButton = driverLeft.getTrigger();
        if (gearDeployButton != gearDeployButtonPressed) {
            gearDeployButtonPressed = gearDeployButton;
            if (gearDeployButton) {
                deployGear.reset();
                gearDeployRunning = true;
            } else {
                gearDeployRunning = false;
            }
        }

        if (gearDeployRunning) {
            gearDeployRunning = !deployGear.run();
        }

        // The joystick axes are intentionally reversed, so for the joystick the outfeed
        // is the front of the robot.
        driveBase.drive(driverRight.getY() + driverLeft.getX(), driverRight.getY() - driverLeft.getX());

        if (driverLeft.getRawButton(2)) {
            driveHighGear = false;
        }
        if (driverRight.getRawButton(2)) {
            driveHighGear = true;
        }

        driveBase.useHighGear(driveHighGear);


        boolean infeedButton = manipulator.getButton(Button.Y);
        if (infeedButton != infeedButtonPressed) {
            infeedButtonPressed = infeedButton;
            if (infeedButton) {
                infeedOpen = !infeedOpen;
            }
        }

        boolean epiglottisUp = manipulator.getButton(Button.B);
        boolean outfeedOpen = manipulator.getButton(Button.A);

        // Load fuel
        if (manipulator.getButton(Button.RIGHT_BUMPER)) {
            epiglottisUp = true;
            outfeedOpen = false;
            infeedOpen = true;
        }

        // Dispense fuel
        if (manipulator.getButton(Button.LEFT_BUMPER)) {
            outfeedOpen = true;
            infeedOpen = false;
        }

        doors.epiglottisUp(epiglottisUp);
        doors.outfeedOpen(outfeedOpen);
        doors.infeedOpen(infeedOpen);


        winch.moveWinch(manipulator.getButton(Button.X));

        gearPiston.latchGear(driverRight.getTrigger());

        outfeed.moveOutfeed(manipulator.getAxis(Axis.RIGHT_Y));
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {}
}

