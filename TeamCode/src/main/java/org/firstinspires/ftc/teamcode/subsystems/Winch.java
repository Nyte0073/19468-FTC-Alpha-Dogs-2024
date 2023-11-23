package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utilities;

import static org.firstinspires.ftc.teamcode.Constants.WinchConstants;

public class Winch {

    DcMotor winchMotor2;
    PIDFController pidController;

    public Winch(HardwareMap hardwareMap) {
        winchMotor2 = hardwareMap.get(DcMotor.class, WinchConstants.winch2);

        motorConfig(winchMotor2);

        winchMotor2.setDirection(WinchConstants.invert);

        pidController = new PIDFController(WinchConstants.pidCoefficients, 0);
    }

    public void teleop(Gamepad gamepad2) {
        double winchPower = gamepad2.right_stick_y;

        setPower(winchPower);
    }

    public void setPower(double power) {
        winchMotor2.setPower(power);
    }

    public double getPower() {
        return winchMotor2.getPower();
    }

    //TODO: convert to inches
    public double getPosition() {return winchMotor2.getCurrentPosition() * WinchConstants.encoderToInches;}

    public void setSetpoint(double position) {
        pidController.setTargetPosition(position);
    }

    public boolean atSetpoint() {
        return Utilities.withinBounds(getPosition(), pidController.getTargetPosition(), 0.5);
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Winch Motors");
        telemetry.addLine("Winch Power: " + getPower());
    }

    /**
     * Standard motor config for all drivetrain motors
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
