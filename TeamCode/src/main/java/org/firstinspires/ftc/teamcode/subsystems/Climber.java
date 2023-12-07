package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Utilities;

public class Climber {

    Servo climbServo0;

    double teleopAngle = Constants.ClimberConstants.homeAngle;

    public Climber(HardwareMap hardwareMap) {
        climbServo0 = hardwareMap.get(Servo.class, Constants.ClimberConstants.climber0);

        climbServo0.setDirection(Constants.ClimberConstants.invert);
    }

    public double getAngle() {
        return (climbServo0.getPosition() * 300);
    }


    public void setAngle(double angle) {
        climbServo0.setPosition(angle / 300);
        teleopAngle = angle;
    }

    public double getPos() {
        return climbServo0.getPosition();
    }

    public void setPos(double position) {
        climbServo0.setPosition(position);
    }

    public void teleop(Gamepad gamepad2) {

        teleopAngle += gamepad2.right_trigger - gamepad2.left_trigger;

        teleopAngle = Utilities.clip(teleopAngle, 300, 0);

        setAngle(teleopAngle);
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Climb:");
        telemetry.addLine("Climb Angle: " + getAngle());
        telemetry.addLine("Climb Pos: " + getPos());
    }
}