package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Constants.WristConstants;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Wrist {

    Servo wrist;
    double teleopPos = 0;

    public Wrist(HardwareMap hardwareMap) {
        wrist = hardwareMap.get(Servo.class, WristConstants.wristServo);

        wrist.setDirection(WristConstants.invert);
    }

    public double getAngle() {
        return wrist.getPosition() * 300;
    }

    public void setAngle(double angle) {
        wrist.setPosition((angle / 300));
    }

    public void setPos(double position) {
        wrist.setPosition(position);
    }

    public void teleop(Gamepad gamepad1) {
        teleopPos += gamepad1.right_stick_y;
        setPos(teleopPos);
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Wrist:");
        telemetry.addLine("Wrist Angle: " + getAngle());
    }
}
