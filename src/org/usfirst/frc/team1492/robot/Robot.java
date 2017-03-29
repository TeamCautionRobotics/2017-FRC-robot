package org.usfirst.frc.team1492.robot;

import org.usfirst.frc.team1492.robot.Gamepad.Axis;
import org.usfirst.frc.team1492.robot.Gamepad.Button;
import org.usfirst.frc.team1492.robot.HumanLoadLight.LightMode;
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

        // TODO: Limit switch for winch
        winch = new Winch(3);

        doors = new Doors(2, 3, 4);

        outfeed = new Outfeed(2);

        driverLeft = new EnhancedJoystick(0, 0.1);
        driverRight = new EnhancedJoystick(1, 0.1);
        manipulator = new Gamepad(2);

        humanLoadLight = new HumanLoadLight(0);
        
        commandFactory = new CommandFactory(driveBase, gearPiston, doors);

        Mission testMission = new Mission("testMission",
        commandFactory.moveStraight(false, 0.4, 2.0),
        commandFactory.turnInPlace(false, 0.4, 50),
        commandFactory.alignWithVision(),
        commandFactory.setGearPiston(true),
        commandFactory.delay(1.0),
        commandFactory.moveStraight(false, -0.4, 1.0),
        commandFactory.setGearPiston(false));
        missionChooser.addObject("testMission", testMission);

        Mission driveForward = new Mission("driveForward",
        commandFactory.moveStraightPID(100));
        missionChooser.addObject("drive forward", driveForward);

        Mission missionLeft = new Mission("missionLeft",
        commandFactory.moveStraightPID(80),
        commandFactory.turnInPlace(false, 0.4, -50),
        commandFactory.alignWithVision(),
        commandFactory.delay(0.4),
        commandFactory.setGearPiston(true),
        commandFactory.delay(1.0),
        commandFactory.moveStraightPID(-50),
        commandFactory.setGearPiston(false));
        missionChooser.addObject("mission left", missionLeft);
        
        Mission missionLeftMove = new Mission("missionLeftMove",
        commandFactory.moveStraightPID(80),
        commandFactory.turnInPlace(false, 0.4, -50));
        missionChooser.addObject("mission left move", missionLeftMove);

        Mission missionCenter = new Mission("missionCenter",
        commandFactory.moveStraightPID(false, 0.5, 78),
        commandFactory.moveStraight(false, 0.4, 0.7),
        commandFactory.delay(0.4),
//        commandFactory.alignWithVision(),
        commandFactory.setGearPiston(true),
        commandFactory.delay(0.8),
        commandFactory.moveStraight(false, -0.4, 1.0),
        commandFactory.setGearPiston(false));
        missionChooser.addObject("mission center", missionCenter);

        Mission missionCenterCamera = new Mission("missionCenterCamera",
        commandFactory.moveStraightPID(false, 0.4, 55),
//        commandFactory.delay(0.4),
        commandFactory.alignWithVision(),
        commandFactory.moveStraight(false, 0.4, 0.5),
        commandFactory.delay(0.3),
        commandFactory.setGearPiston(true),
        commandFactory.delay(0.6),
        commandFactory.moveStraight(false, -0.6, 0.7),
        commandFactory.setGearPiston(false));
        missionChooser.addObject("mission center camera", missionCenterCamera);

        Mission missionRight = new Mission("missionRight",
        commandFactory.moveStraightPID(80),
        commandFactory.turnInPlace(false, 0.4, 50),
        commandFactory.moveStraightPID(25),
        commandFactory.alignWithVision(),
        commandFactory.setGearPiston(true),
        commandFactory.delay(1.0),
        commandFactory.moveStraight(false, -0.4, 1.0),
        commandFactory.moveStraightPID(-50),
        commandFactory.setGearPiston(false));
        missionChooser.addObject("mission right", missionRight);

        Mission visionTest = new Mission("visionTest", true,   
        commandFactory.alignWithVision(true));
        missionChooser.addObject("Vision Test", visionTest);

        deployGear = new Mission("deployGear",
        commandFactory.moveStraight(false, 0, 0),
        commandFactory.alignWithVision(),
        commandFactory.moveStraight(false, 0.4, 0.5),
//        commandFactory.setGearPiston(true),
//        commandFactory.delay(0.8),
//        commandFactory.moveStraight(false, -0.4, 1.0),
//        commandFactory.setGearPiston(false),
        commandFactory.moveStraight(true, 0, 0));
        missionChooser.addObject("deploy gear", deployGear);

        Mission turnReset = new Mission("turnReset",
        commandFactory.turnInPlace(0.2, 180),
        commandFactory.delay(0.3),
        commandFactory.resetEncoders());
        missionChooser.addObject("turn, reset encoders", turnReset);

        Mission encoderReset = new Mission("encoderReset",
        commandFactory.resetEncoders());
        missionChooser.addObject("reset encoders", encoderReset);

        Mission driveForwardPID = new Mission("driveForwardPID",
        commandFactory.moveStraightPID(80));
        missionChooser.addObject("pid drive 80", driveForwardPID);

        Mission noMovePID = new Mission("noMovePID",
        commandFactory.moveStraightPID(0));
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
            System.out.println("Mission " + activeMission.getName() + " Started");
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        if(activeMission != null){
            if(activeMission.run()){
                System.out.println("Mission " + activeMission.getName() + " Complete");
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
        SmartDashboard.putNumber("right encoder num", driveBase.getRightDistance());
        SmartDashboard.putBoolean("pid arrived", driveBase.pidController.onTarget());

        if ((missionSendable.run() && missionChooser.getSelected().getName() != "visionTest"
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
}

