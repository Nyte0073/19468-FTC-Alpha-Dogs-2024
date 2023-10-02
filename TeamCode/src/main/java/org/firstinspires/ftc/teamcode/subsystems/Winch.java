package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import static org.firstinspires.ftc.teamcode.Constants.WinchConstants;

public class Winch {

    DcMotor rightWinch2;

    public Winch(HardwareMap hardwareMap) {
        rightWinch2 = hardwareMap.get(DcMotor.class, WinchConstants.rightWinch2);

        motorConfig(rightWinch2);

        rightWinch2.setDirection(WinchConstants.invert);
    }

    public void teleop(Gamepad gamepad1) {
        double winchPower = gamepad1.right_stick_y;

        setPower(winchPower);
    }

    public void setPower(double power) {
        rightWinch2.setPower(power);
    }

    public double getPower() {
        return rightWinch2.getPower();
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
