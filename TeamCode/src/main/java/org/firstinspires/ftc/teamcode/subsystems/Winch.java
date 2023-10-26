package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import static org.firstinspires.ftc.teamcode.Constants.WinchConstants;

public class Winch {

    DcMotor winchMotor2;

    public Winch(HardwareMap hardwareMap) {
        winchMotor2 = hardwareMap.get(DcMotor.class, WinchConstants.winch2);

        motorConfig(winchMotor2);

        winchMotor2.setDirection(WinchConstants.invert);
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
