package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Constants.WristConstants;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Wrist {

    Servo rightWrist0;

    double teleopAngle = WristConstants.homeAngle;

    public Wrist(HardwareMap hardwareMap) {
        rightWrist0 = hardwareMap.get(Servo.class, WristConstants.rightWristServo);

        rightWrist0.setDirection(WristConstants.rightInvert);
    }

    public double getAngle() {
        return (rightWrist0.getPosition() * 300);
    }

    public void setAngle(double angle) {
        rightWrist0.setPosition((angle / 300));
    }

    public double getPos() {
        return rightWrist0.getPosition();
    }

    public void setPos(double position) {
        rightWrist0.setPosition(position);
    }

    public void teleop(Gamepad gamepad2) {
        teleopAngle = getAngle();
        if (gamepad2.a) {
            teleopAngle = WristConstants.scoreAngle;
        } else if (gamepad2.x) {
            teleopAngle = WristConstants.homeAngle;
        } else if (gamepad2.b) {
            teleopAngle = WristConstants.midAngle;
        } else {
            teleopAngle += gamepad2.right_trigger / 2 - gamepad2.left_trigger / 2;
        }

        setAngle(teleopAngle);

    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Wrist:");
        telemetry.addLine("Wrist Angle: " + getAngle());
        telemetry.addLine("Wrist Pos: " + getPos());
    }
}
