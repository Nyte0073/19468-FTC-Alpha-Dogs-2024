package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Constants.WristConstants;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Wrist {

    Servo rightWrist0, leftWrist2;

    double teleopAngle = 0;

    public Wrist(HardwareMap hardwareMap) {
        rightWrist0 = hardwareMap.get(Servo.class, WristConstants.rightWristServo);
        leftWrist2 = hardwareMap.get(Servo.class, WristConstants.leftWristServo);

        rightWrist0.setDirection(WristConstants.rightInvert);
        leftWrist2.setDirection(WristConstants.leftInvert);
    }

    public double getAngle() {
        return ((rightWrist0.getPosition() * 300) + (leftWrist2.getPosition() * 300)) / 2;
    }

    public double getRightAngle() {
        return rightWrist0.getPosition() * 300;
    }

    public double getLeftAngle() {
        return leftWrist2.getPosition() * 300;
    }

    public void setAngle(double angle) {
        rightWrist0.setPosition((angle / 300));
        leftWrist2.setPosition((angle / 300));
    }

    public double getPos() {
        return (rightWrist0.getPosition() + leftWrist2.getPosition()) / 2;
    }

    public void setPos(double position) {
        rightWrist0.setPosition(position);
        leftWrist2.setPosition(position);
    }

    public void teleop(Gamepad gamepad2) {

        if (gamepad2.a) {
            teleopAngle = WristConstants.homeAngle;
        } else if (gamepad2.x) {
            teleopAngle = WristConstants.scoreAngle;
        } else {
            teleopAngle += gamepad2.right_trigger / 2 - gamepad2.left_trigger / 2;
        }

        setAngle(teleopAngle);

    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Wrist:");
        telemetry.addLine("Wrist Angle: " + getAngle());
        telemetry.addLine("Left Wrist Angle: " + getLeftAngle());
        telemetry.addLine("Right Wrist Angle: " + getRightAngle());
        telemetry.addLine("Wrist Pos: " + getPos());
    }
}
