package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Constants.WinchConstants;

import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities;

public class Arm {

    DcMotor leftArmMotor0, rightArmMotor1;
    PIDFController pidController;

    public Arm(HardwareMap hardwareMap) {
        leftArmMotor0 = hardwareMap.get(DcMotor.class, WinchConstants.leftWinch1);
        rightArmMotor1 = hardwareMap.get(DcMotor.class, WinchConstants.rightWinch1);

        motorConfig(leftArmMotor0);
        motorConfig(rightArmMotor1);

        leftArmMotor0.setDirection(WinchConstants.leftInvert);
        rightArmMotor1.setDirection(WinchConstants.rightInvert);

        pidController = new PIDFController(WinchConstants.winchPID, 0);
    }

    public void teleop(Gamepad gamepad2) {

        //Manual
        double winchPower = -gamepad2.right_stick_y;

        setPower(winchPower);

    }

    public void setPower(double power) {
        leftArmMotor0.setPower(power);
        rightArmMotor1.setPower(power);
    }

    public double getPower() {
        return (leftArmMotor0.getPower() + rightArmMotor1.getPower()) / 2;

    }

    //TODO: convert to inches
    public double getPosition() {
        return rightArmMotor1.getCurrentPosition() * WinchConstants.encoderToInches;
    }

    public void setSetpoint(double position) {
        pidController.setTargetPosition(position);
    }

    public boolean atSetpoint() {
        return Utilities.withinBounds(getPosition(), pidController.getTargetPosition(), WinchConstants.tolerance);
    }

    public double getSetpointCalc() {
        return pidController.update(getPosition());
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Winch Motors");
        telemetry.addLine("Winch Power: " + getPower());
        telemetry.addData("Winch Position: ", getPosition());
    }

    /**
     * Standard motor config for all drivetrain motors
     *
     * @param motor DcMotor to  configure
     * @return configured DcMotor
     */
    public DcMotor motorConfig(DcMotor motor) {
        motor.setZeroPowerBehavior(WinchConstants.neutralMode);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        return motor;
    }


}