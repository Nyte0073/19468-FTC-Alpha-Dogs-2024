package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.teamcode.Constants.WristConstants;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Wrist {

    Servo wrist0;
    double teleopPos = 0;

    public Wrist(HardwareMap hardwareMap) {
        wrist0 = hardwareMap.get(Servo.class, WristConstants.wristServo);

        wrist0.setDirection(WristConstants.invert);
    }

    public double getAngle() {
        return wrist0.getPosition() * 300;
    }

    public void setAngle(double angle) {
        wrist0.setPosition((angle / 300));
    }

    public void setPos(double position) {
        wrist0.setPosition(position);
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
