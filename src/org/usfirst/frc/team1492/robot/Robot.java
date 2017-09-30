package org.usfirst.frc.team1492.robot;

import org.usfirst.frc.team1492.robot.Gamepad.Axis;
import org.usfirst.frc.team1492.robot.Gamepad.Button;
import org.usfirst.frc.team1492.robot.HumanLoadLight.LightMode;
import org.usfirst.frc.team1492.robot.autonomous.CommandFactory;
import org.usfirst.frc.team1492.robot.autonomous.Mission;
import org.usfirst.frc.team1492.robot.autonomous.MissionSendable;

import edu.wpi.first.wpilibj.CameraServer;
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

    HumanLoadLight humanLoadLight;

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

        winch = new Winch(3);

        doors = new Doors(2, 3, 4);

        outfeed = new Outfeed(2);

        driverLeft = new EnhancedJoystick(0, 0.1);
        driverRight = new EnhancedJoystick(1, 0.1);
        manipulator = new Gamepad(2);

        humanLoadLight = new HumanLoadLight(0);

        CameraServer.getInstance().startAutomaticCapture();

        commandFactory = new CommandFactory(driveBase, gearPiston, doors);

        Mission testEncoderVision = new Mission("test encoder vision");
        testEncoderVision.add(commandFactory.alignWithVision(30));
        missionChooser.addObject("test encoder vision", testEncoderVision);

        Mission crossBaseline = new Mission("cross baseline");
        crossBaseline.add(commandFactory.moveStraightDistance(true, 0.4, 65, false));
        crossBaseline.add(commandFactory.moveStraight(true, -0.05, 0.1, true));
        missionChooser.addObject("cross baseline mission", crossBaseline);

        Mission missionCenterGearNoCamera = new Mission("center gear encoder");
        missionCenterGearNoCamera.add(commandFactory.moveStraightDistance(true, 0.4, 65, false));
        missionCenterGearNoCamera.add(commandFactory.moveStraight(true, -0.05, 0.1, true));
        missionCenterGearNoCamera.add(commandFactory.delay(0.4));
        missionCenterGearNoCamera.add(commandFactory.setGearPiston(true));
        missionCenterGearNoCamera.add(commandFactory.delay(0.8));
        missionCenterGearNoCamera.add(commandFactory.moveStraight(false, -0.4, 0.2));
        missionCenterGearNoCamera.add(commandFactory.setGearPiston(false));
        missionChooser.addObject("mission center gear, no camera", missionCenterGearNoCamera);

        Mission missionCenterGearCamera = new Mission("center gear camera");
        missionCenterGearCamera.add(commandFactory.moveStraightDistance(true, 0.4, 41.75, false));
        missionCenterGearCamera.add(commandFactory.moveStraight(false, -0.05, 0.1, true));
        missionCenterGearCamera.add(commandFactory.alignWithVision(34));
        missionCenterGearCamera.add(commandFactory.delay(0.4));
        missionCenterGearCamera.add(commandFactory.setGearPiston(true));
        missionCenterGearCamera.add(commandFactory.delay(0.8));
        missionCenterGearCamera.add(commandFactory.moveStraight(false, -0.4, 0.4));
        missionCenterGearCamera.add(commandFactory.setGearPiston(false));
        missionChooser.addObject("mission center gear with camera", missionCenterGearCamera);

        Mission missionRightCameraGear = new Mission("right gear camera");
        missionRightCameraGear.add(commandFactory.moveStraightDistance(true, 0.4, 82, false));
        missionRightCameraGear.add(commandFactory.moveStraight(true, -0.05, 0.1, true));
        missionRightCameraGear.add(commandFactory.turnInPlace(false, 0.4, howManyRoads() + 10));
        missionRightCameraGear.add(commandFactory.moveStraightDistance(true, 0.4, 15, false));
        missionRightCameraGear.add(commandFactory.moveStraight(true, -0.05, 0.1, true));
        missionRightCameraGear.add(commandFactory.moveStraight(false, 0, 0));
        missionRightCameraGear.add(commandFactory.alignWithVision(33));
        missionRightCameraGear.add(commandFactory.delay(0.4));
        missionRightCameraGear.add(commandFactory.setGearPiston(true));
        missionRightCameraGear.add(commandFactory.delay(0.8));
        missionRightCameraGear.add(commandFactory.moveStraight(false, -0.4, 0.4));
        missionRightCameraGear.add(commandFactory.setGearPiston(false));
        missionChooser.addObject("mission right camera with gear", missionRightCameraGear);


        Mission missionRightNoCameraNoGear = new Mission("right nogear encoder");
        missionRightNoCameraNoGear.add(commandFactory.moveStraightDistance(true, 0.4, 82, false));
        missionRightNoCameraNoGear.add(commandFactory.moveStraight(true, -0.05, 0.1, true));
        missionRightNoCameraNoGear.add(commandFactory.turnInPlace(false, 0.4, howManyRoads() + 10));
        missionRightNoCameraNoGear.add(commandFactory.moveStraightDistance(true, 0.4, 15, false));
        missionRightNoCameraNoGear.add(commandFactory.moveStraight(true, -0.05, 0.1, true));
        missionRightNoCameraNoGear.add(commandFactory.moveStraight(false, 0, 0));
        missionRightNoCameraNoGear.add(commandFactory.delay(1));
        missionChooser.addObject("mission right no camera, no gear", missionRightNoCameraNoGear);

        Mission missionleftCameraGear = new Mission("left gear camera");
        missionleftCameraGear.add(commandFactory.moveStraightDistance(true, 0.4, 72, false));
        missionleftCameraGear.add(commandFactory.moveStraight(true, -0.05, 0.1, true));
        missionleftCameraGear.add(commandFactory.turnInPlace(false, 0.4, -(howManyRoads() + 12)));
        missionleftCameraGear.add(commandFactory.moveStraightDistance(true, 0.4, 15, false));
        missionleftCameraGear.add(commandFactory.moveStraight(true, -0.05, 0.1, true));
        missionleftCameraGear.add(commandFactory.moveStraight(false, 0, 0));
        missionleftCameraGear.add(commandFactory.alignWithVision(28));
        missionleftCameraGear.add(commandFactory.delay(0.4));
        missionleftCameraGear.add(commandFactory.setGearPiston(true));
        missionleftCameraGear.add(commandFactory.delay(0.8));
        missionleftCameraGear.add(commandFactory.moveStraight(false, -0.4, 0.4));
        missionleftCameraGear.add(commandFactory.setGearPiston(false));
        missionChooser.addObject("mission left camera with gear", missionleftCameraGear);

        Mission missionLeftNoCameraNoGear = new Mission("left nogear encoder");
        missionLeftNoCameraNoGear.add(commandFactory.moveStraightDistance(true, 0.4, 72, false));
        missionLeftNoCameraNoGear.add(commandFactory.moveStraight(true, -0.05, 0.1, true));
        missionLeftNoCameraNoGear.add(commandFactory.turnInPlace(false, 0.4, -(howManyRoads() + 12)));
        missionLeftNoCameraNoGear.add(commandFactory.moveStraightDistance(true, 0.4, 15, false));
        missionLeftNoCameraNoGear.add(commandFactory.moveStraight(true, -0.05, 0.1, true));
        missionLeftNoCameraNoGear.add(commandFactory.moveStraight(false, 0, 0));
        missionLeftNoCameraNoGear.add(commandFactory.delay(1));
        missionChooser.addObject("mission left no camera, no gear", missionLeftNoCameraNoGear);

        Mission doNothing = new Mission("do nothing");
        missionChooser.addObject("Do nothing", doNothing);

        Mission visionTest = new Mission("vision test");
        visionTest.add(commandFactory.alignWithVision(true));
        missionChooser.addObject("Vision Test", visionTest);

        deployGear = new Mission("deployGear", commandFactory.moveStraight(false, 0, 0),
                commandFactory.alignWithVision(), commandFactory.moveStraight(false, 0.4, 0.5),
                commandFactory.moveStraight(true, 0, 0));
        missionChooser.addObject("deploy gear", deployGear);

        Mission cameraTurn = new Mission("camera turn to target");
        cameraTurn.add(commandFactory.turnToTarget());
        missionChooser.addObject("turn to target", cameraTurn);

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

        if (activeMission != null) {
            activeMission.reset();
            System.out.println("Mission '" + activeMission.getName() + "' Started");
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        SmartDashboard.putNumber("right encoder", driveBase.getRightDistance());
        SmartDashboard.putNumber("right encoder num", driveBase.getRightDistance());
        SmartDashboard.putBoolean("pid arrived", driveBase.pidController.onTarget());

        if (activeMission != null) {
            if (activeMission.run()) {
                System.out.println("Mission '" + activeMission.getName() + "' Complete");
                activeMission = null;
            }
        }
    }

    @Override
    public void teleopInit() {
        driveHighGear = false;
        driveBase.useHighGear(false);

        driveBase.resetEncoders();
        driveBase.pidController.disable();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        SmartDashboard.putNumber("right encoder", driveBase.getRightDistance());
        SmartDashboard.putNumber("left encoder", driveBase.getLeftDistance());
        SmartDashboard.putNumber("right encoder num", driveBase.getRightDistance());
        SmartDashboard.putBoolean("pid arrived", driveBase.pidController.onTarget());

        if ((missionSendable.run() && !missionChooser.getSelected().enableControls)
                || driveBase.pidController.isEnabled()) {
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
                driveBase.pidController.disable();
            }
        }

        if (gearDeployRunning) {
            gearDeployRunning = !deployGear.run();
            if (!gearDeployRunning) {
                driveBase.pidController.disable();
            }
            return;
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


        if (epiglottisUp) {
            humanLoadLight.lightOn(LightMode.FUEL);
        } else {
            if (manipulator.getAxis(Axis.LEFT_TRIGGER) > 0.5) {
                humanLoadLight.lightOn(LightMode.GEAR);
            } else {
                humanLoadLight.lightOn(LightMode.OFF);
            }
        }
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {}

    public double howManyRoads() {
        return 42;
    }
}

