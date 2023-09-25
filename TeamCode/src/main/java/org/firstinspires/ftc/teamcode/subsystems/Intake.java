package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {

    DcMotor intake0;

    public Intake(HardwareMap hardwareMap) {
        intake0 = hardwareMap.get(DcMotor.class, "intake0");
        motorConfig(intake0);
        intake0.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void teleop(Gamepad gamepad1) {
        double outtake = gamepad1.left_trigger;
        double intake = gamepad1.right_trigger;

        setPower(intake - outtake);
    }


    public void setPower(double power) {
        intake0.setPower(power);
    }

    public double getPower() {
        return intake0.getPower();
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Intake Motors");
        telemetry.addLine("Intake Power: " + getPower());
    }

    /**
     * Standard motor config for all drivetrain motors
     * @param motor DcMotor to  configure
     * @return configured DcMotor
     */
    public DcMotor motorConfig(DcMotor motor) {
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        return motor;
    }
}
