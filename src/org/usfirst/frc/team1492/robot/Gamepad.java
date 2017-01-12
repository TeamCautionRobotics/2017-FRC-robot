package org.usfirst.frc.team1492.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Gamepad extends Joystick {
    double deadbandSize;

    public Gamepad(int port) {
        this(port, 0.2);
    }

    public Gamepad(int port, double deadband) {
        super(port);
        this.deadbandSize = deadband;
    }
    
    double getAxis(Axis axis) {
        return deadband(getRawAxis(axis.ordinal()));
    }
    
    boolean getButton(Button button) {
        return getRawButton(button.ordinal() + 1);
    }
    
    private double deadband(double value) {
        if (value > deadbandSize) {
            return (value - deadbandSize) / (1 - deadbandSize);
        }
        if (value < -deadbandSize) {
            return (value + deadbandSize) / (1 - deadbandSize);
        }
        return 0;
    }


    enum Axis {
        LEFT_X, LEFT_Y, LEFT_TRIGGER, RIGHT_TRIGGER, RIGHT_X, RIGHT_Y
    }

    enum Button {
        A, B, X, Y, LEFT_BUMPER, RIGHT_BUMPER, BACK, START,
        // Joystick click
        LEFT_JOYSTICK, RIGHT_JOYSTICK
    }
}
