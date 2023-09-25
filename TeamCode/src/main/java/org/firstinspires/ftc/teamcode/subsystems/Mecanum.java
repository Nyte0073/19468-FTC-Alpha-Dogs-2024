package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Mecanum {

    DcMotor frontLeft0, frontRight1, backLeft2, backRight3;

    public Mecanum(HardwareMap hardwareMap) {

        frontLeft0 = hardwareMap.get(DcMotor.class, "frontLeft0");
        frontRight1 = hardwareMap.get(DcMotor.class, "frontRight1");
        backLeft2 = hardwareMap.get(DcMotor.class, "backLeft2");
        backRight3 = hardwareMap.get(DcMotor.class, "backRight3");

        frontLeft0 = motorConfig(frontLeft0);
        frontRight1 = motorConfig(frontRight1);
        backLeft2 = motorConfig(backLeft2);
        backRight3 = motorConfig(backRight3);

        frontLeft0.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight1.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft2.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight3.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void teleop(Gamepad gamepad1) {
        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y + x - rx) / denominator;
        double backRightPower = (y - x - rx) / denominator;

        setPower(new double[] {frontLeftPower, backLeftPower, frontRightPower, backRightPower});
    }

    public void setPower(double[] power) {
        frontLeft0.setPower(power[0]);
        frontRight1.setPower(power[1]);
        backLeft2.setPower(power[2]);
        backRight3.setPower(power[3]);
    }

    public double[] getPower() {
        double[] power = new double[4];

        power[0] = frontLeft0.getPower();
        power[1] = frontRight1.getPower();
        power[2] = backLeft2.getPower();
        power[3] = backRight3.getPower();

        return power;
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Drive Motors");
        telemetry.addLine("Front Left: " + getPower()[0]);
        telemetry.addLine("Front Right: " + getPower()[1]);
        telemetry.addLine("Back Left: " + getPower()[2]);
        telemetry.addLine("Back Right: " + getPower()[3]);
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
