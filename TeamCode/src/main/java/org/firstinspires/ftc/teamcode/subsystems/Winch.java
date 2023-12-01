package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Constants.WinchConstants;

import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Utilities;

public class Winch {

    DcMotor leftWinchMotor1, rightWinchMotor1;
    PIDFController pidController;

    Constants.WinchLevel level = Constants.WinchLevel.LOW;

    int levelSwap = 0;

    public Winch(HardwareMap hardwareMap) {
        leftWinchMotor1 = hardwareMap.get(DcMotor.class, WinchConstants.leftWinch1);
        rightWinchMotor1 = hardwareMap.get(DcMotor.class, WinchConstants.rightWinch1);

        motorConfig(leftWinchMotor1);
        motorConfig(rightWinchMotor1);

        leftWinchMotor1.setDirection(WinchConstants.leftInvert);
        rightWinchMotor1.setDirection(WinchConstants.rightInvert);

        pidController = new PIDFController(WinchConstants.winchPID, 0);
    }

    public void teleop(Gamepad gamepad2) {

        //Winch levels
        if (gamepad2.dpad_up && levelSwap == 0) {
            level = level == Constants.WinchLevel.HOME ? Constants.WinchLevel.LOW : (level == Constants.WinchLevel.LOW ? Constants.WinchLevel.MID : Constants.WinchLevel.HIGH);
            levelSwap++;
        } else if (gamepad2.dpad_down && levelSwap == 0) {
            level = level == Constants.WinchLevel.HIGH ? Constants.WinchLevel.MID : (level == Constants.WinchLevel.MID ? Constants.WinchLevel.LOW : Constants.WinchLevel.HOME);
            levelSwap++;
        } else if (!gamepad2.dpad_up && !gamepad2.dpad_down) {
            levelSwap = 0;
        }

        if (gamepad2.a) {
            switch (level) {
                case HOME:
                    setSetpoint(0);
                    break;
                case LOW:
                    setSetpoint(Constants.ScoringConstants.scoreLow.getArmExtension());
                    break;
                case MID:
                    setSetpoint(Constants.ScoringConstants.scoreMid.getArmExtension());
                    break;
                case HIGH:
                    setSetpoint(Constants.ScoringConstants.scoreHigh.getArmExtension());
                    break;
            }

            if (!atSetpoint()) {
                setPower(getSetpointCalc());
            } else {
                setPower(0);
            }
        } else {
            //Manual
            double winchPower = -gamepad2.right_stick_y;

            setPower(winchPower);
        }

    }

    public Constants.WinchLevel getLevel() {
        return level;
    }

    public void setPower(double power) {
        leftWinchMotor1.setPower(power);
        rightWinchMotor1.setPower(power);
    }

    public double getPower() {
        return (leftWinchMotor1.getPower() + rightWinchMotor1.getPower()) / 2;

    }

    //TODO: convert to inches
    public double getPosition() {
        return rightWinchMotor1.getCurrentPosition() * WinchConstants.encoderToInches;
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
        telemetry.addLine("Winch Level: " + getLevel());
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