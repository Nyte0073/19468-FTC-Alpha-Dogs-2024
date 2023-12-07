package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Constants.WristConstants;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Utilities;

public class Claw {

    Servo leftClaw0;
    Servo rightClaw2;

    double teleopAngle = Constants.ClawConstants.closedAngle;
    int wristSwap = 0;

    public Claw(HardwareMap hardwareMap) {
        leftClaw0 = hardwareMap.get(Servo.class, Constants.ClawConstants.leftClaw0);
        rightClaw2 = hardwareMap.get(Servo.class, Constants.ClawConstants.rightClaw2);

        leftClaw0.setDirection(Constants.ClawConstants.invertL);
        rightClaw2.setDirection(Constants.ClawConstants.invertR);
    }

    public double getLeftAngle() {
        return (leftClaw0.getPosition() * 300);
    }

    public double getRightAngle() {
        return (rightClaw2.getPosition() * 300);
    }

    public void setAngle(double angle) {
        rightClaw2.setPosition(angle / 300);
        leftClaw0.setPosition(angle / 300);
        teleopAngle = angle;
    }

    public double getLPos() {
        return leftClaw0.getPosition();
    }
    public double getRPos() {
        return rightClaw2.getPosition();
    }

    public void setPos(double position) {
        rightClaw2.setPosition(position);
    }

    public void teleop(Gamepad gamepad2) {

        teleopAngle += gamepad2.right_trigger - gamepad2.left_trigger;

        teleopAngle = Utilities.clip(teleopAngle, 300, 0);

        setAngle(teleopAngle);
    }

    public void openClaw() {
        setAngle(Constants.ClawConstants.openAngle);
    }

    public void closeClaw() {
        setAngle(Constants.ClawConstants.closedAngle);
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Claw:");
        telemetry.addLine("L Claw Angle: " + getLeftAngle());
        telemetry.addLine("R Claw Angle: " + getRightAngle());
        telemetry.addLine("L Claw Pos: " + getLPos());
        telemetry.addLine("R Claw Pos: " + getRPos());
    }
}