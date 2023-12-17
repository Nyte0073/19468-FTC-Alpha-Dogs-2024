package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Constants.WristConstants;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities;

public class Wrist {

    Servo leftWrist0;
    Servo rightWrist2;

    double teleopAngle = WristConstants.pickupAngle;
    int wristSwap = 0;

    public Wrist(HardwareMap hardwareMap) {
        leftWrist0 = hardwareMap.get(Servo.class, WristConstants.leftWristServo);
        rightWrist2 = hardwareMap.get(Servo.class, WristConstants.rightWristServo);

        leftWrist0.setDirection(WristConstants.leftInvert);
        rightWrist2.setDirection(WristConstants.rightInvert);
    }

    public double getLeftAngle() {
        return (leftWrist0.getPosition() * 300);
    }

    public double getRightAngle() {
        return (rightWrist2.getPosition() * 300);
    }

    public void setAngle(double angle) {
        rightWrist2.setPosition(angle / 300);
        leftWrist0.setPosition(angle / 300);
        teleopAngle = angle;
    }

    public double getLPos() {
        return leftWrist0.getPosition();
    }
    public double getRPos() {
        return rightWrist2.getPosition();
    }

    public void setPos(double position) {
        rightWrist2.setPosition(position);
    }

    public void teleop(Gamepad gamepad1, Gamepad gamepad2, boolean armSetpoint) {

        if (gamepad1.x && armSetpoint) {
            teleopAngle = WristConstants.pickupAngle; //Intake
        } else if (gamepad1.b) {
            teleopAngle = WristConstants.scoreAngle; //Score
        } else if ((!armSetpoint && gamepad1.b || gamepad1.x) || gamepad1.a) {
            teleopAngle = WristConstants.homeAngle; //Home
        } else {
            teleopAngle += gamepad2.right_trigger - gamepad2.left_trigger;
        }

        teleopAngle = Utilities.clip(teleopAngle, 300, 0);

        setAngle(teleopAngle);
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Wrist:");
        telemetry.addLine("L Wrist Angle: " + getLeftAngle());
        telemetry.addLine("R Wrist Angle: " + getRightAngle());
        telemetry.addLine("L Wrist Pos: " + getLPos());
        telemetry.addLine("R Wrist Pos: " + getRPos());
    }
}