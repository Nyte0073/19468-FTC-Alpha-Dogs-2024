package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Constants.WristConstants;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Utilities;

public class Wrist {

    Servo rightWrist2;
    CRServo wristWheel0;

    double teleopAngle = WristConstants.homeAngle;
    double teleopSpeed = 0.5;

    public Wrist(HardwareMap hardwareMap) {
        rightWrist2 = hardwareMap.get(Servo.class, WristConstants.rightWristServo);
        wristWheel0 = hardwareMap.get(CRServo.class, WristConstants.wristWheel);

        rightWrist2.setDirection(WristConstants.rightInvert);
        wristWheel0.setDirection(WristConstants.wheelInvert);
    }

    public double getAngle() {
        return (rightWrist2.getPosition() * 300);
    }

    public void setAngle(double angle) {
        rightWrist2.setPosition((angle / 300));
    }

    public double getPos() {
        return rightWrist2.getPosition();
    }

    public void setPos(double position) {
        rightWrist2.setPosition(position);
    }

    public void setPower(double power) {
        wristWheel0.setPower(power);
    }

    public double getPower() {
        return wristWheel0.getPower();
    }

    public void teleop(Gamepad gamepad1, Gamepad gamepad2) {
        if (gamepad2.a) {
            teleopAngle = Constants.ScoringConstants.scoreAngle;
        } else if (gamepad2.x) {
            teleopAngle = WristConstants.homeAngle;
        } else if (gamepad2.b) {
            teleopAngle = WristConstants.midAngle;
        } else {
            teleopAngle += gamepad2.right_trigger / 2 - gamepad2.left_trigger / 2;
        }

        if (gamepad1.right_bumper) {
            teleopSpeed = 1;
        } else if (gamepad1.left_bumper) {
            teleopSpeed = -1;
        } else {
            teleopSpeed = 0;
        }

        teleopAngle = Utilities.clip(teleopAngle, 300, 0);

        setAngle(teleopAngle);
        setPower(teleopSpeed);

    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Wrist:");
        telemetry.addLine("Wrist Angle: " + getAngle());
        telemetry.addLine("Wrist Pos: " + getPos());
        telemetry.addLine("Wrist Wheel Power: " + getPower());
    }
}
