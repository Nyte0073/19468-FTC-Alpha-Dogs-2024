package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Utilities;

public class Climber {

    Servo climbServo4;
    DcMotor winchMotor3;

    double teleopAngle = Constants.ClimberConstants.homeAngle;
    int climbSwap = 0;

    public Climber(HardwareMap hardwareMap) {
        climbServo4 = hardwareMap.get(Servo.class, Constants.ClimberConstants.climber4);

        climbServo4.setDirection(Constants.ClimberConstants.invert);

        winchMotor3 = hardwareMap.get(DcMotor.class, Constants.ClimberConstants.winchMotor3);
    }

    public double getAngle() {
        return (climbServo4.getPosition() * 300);
    }


    public void setAngle(double angle) {
        climbServo4.setPosition(angle / 300);
        teleopAngle = angle;
    }

    public double getPos() {
        return climbServo4.getPosition();
    }

    public void setPos(double position) {
        climbServo4.setPosition(position);
    }

    public void teleop(Gamepad gamepad2) {

        if (climbSwap == 0) {
            climbSwap++;
            if (gamepad2.y && Utilities.withinBounds(getAngle(), Constants.ClimberConstants.homeAngle, 1)) {
                teleopAngle = Constants.ClimberConstants.climb;
            } else if (gamepad2.y && Utilities.withinBounds(getAngle(), Constants.ClimberConstants.climb, 1)) {
                teleopAngle = Constants.ClimberConstants.homeAngle;
            } else {
                climbSwap = 0;
            }
        } else {
            climbSwap = 0;
        }

        teleopAngle = Utilities.clip(teleopAngle, 300, 0);

        setAngle(teleopAngle);
        setPower(gamepad2.right_trigger - gamepad2.left_trigger);
    }

    public void setPower(double power) {
        winchMotor3.setPower(power);
    }


    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Climb:");
        telemetry.addLine("Climb Angle: " + getAngle());
        telemetry.addLine("Climb Pos: " + getPos());
    }
}