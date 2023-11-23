package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Pose2d;

import static org.firstinspires.ftc.teamcode.Constants.MecanumConstants;

public class Mecanum {

    private DcMotor frontLeft0, frontRight1, backLeft2, backRight3;
    private IMU imu;

    public Mecanum(HardwareMap hardwareMap) {

        frontLeft0 = hardwareMap.get(DcMotor.class, MecanumConstants.frontLeftMotor);
        frontRight1 = hardwareMap.get(DcMotor.class, MecanumConstants.frontRightMotor);
        backLeft2 = hardwareMap.get(DcMotor.class, MecanumConstants.backLeftMotor);
        backRight3 = hardwareMap.get(DcMotor.class, MecanumConstants.backRightMotor);

        frontLeft0 = motorConfig(frontLeft0);
        frontRight1 = motorConfig(frontRight1);
        backLeft2 = motorConfig(backLeft2);
        backRight3 = motorConfig(backRight3);

        frontLeft0.setDirection(MecanumConstants.invertLeft);
        frontRight1.setDirection(MecanumConstants.invertRight);
        backLeft2.setDirection(MecanumConstants.invertLeft);
        backRight3.setDirection(MecanumConstants.invertRight);

        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
    }

    public void teleop(Gamepad gamepad1) {
        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        if (gamepad1.options) {
            imu.resetYaw();
        }

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        drive(rotY, rotX * 1.1, rx);
    }

    public void drive(double ySpeed, double xSpeed, double rot) {
        double denominator = Math.max(Math.abs(ySpeed) + Math.abs(xSpeed) + Math.abs(rot), 1);
        double frontLeftPower = (ySpeed + xSpeed + rot) / denominator;
        double backLeftPower = (ySpeed - xSpeed + rot) / denominator;
        double frontRightPower = (ySpeed + xSpeed - rot) / denominator;
        double backRightPower = (ySpeed - xSpeed - rot) / denominator;

        setPower(frontLeftPower, backLeftPower, frontRightPower, backRightPower);
    }

    public void setPower(double frontLeft, double backLeft, double frontRight, double backRight) {
        frontLeft0.setPower(frontLeft);
        backLeft2.setPower(backLeft);
        frontRight1.setPower(frontRight);
        backRight3.setPower(backRight);
    }

    public double[] getPower() {
        double[] power = new double[4];

        power[0] = frontLeft0.getPower();
        power[1] = backLeft2.getPower();
        power[2] = frontRight1.getPower();
        power[3] = backRight3.getPower();

        return power;
    }

    public double getX() {
        return backLeft2.getCurrentPosition();
    }

    public double getRightPosition() {
        return frontRight1.getCurrentPosition();
    }

    public double getLeftPosition() {
        return frontLeft0.getCurrentPosition();
    }

    public double getY() {
        return (getRightPosition() + getLeftPosition()) / 2;
    }
    public double getYaw() {
        return backLeft2.getCurrentPosition();
    }

    public Pose2d getPose() {
        return new Pose2d(getX(), getY(), getYaw());
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Drive:");
        telemetry.addLine("Front Left: " + getPower()[0]);
        telemetry.addLine("Back Left: " + getPower()[1]);
        telemetry.addLine("Front Right: " + getPower()[2]);
        telemetry.addLine("Back Right: " + getPower()[3]);
        telemetry.addLine("Position:");
        telemetry.addLine("Y: " + getY());
        telemetry.addLine("X: " + getX());
        telemetry.addLine("Yaw: " + getYaw());
    }

    /**
     * Standard motor config for all drivetrain motors
     * @param motor DcMotor to  configure
     * @return configured DcMotor
     */
    public DcMotor motorConfig(DcMotor motor) {
        motor.setZeroPowerBehavior(MecanumConstants.neutralMode);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        return motor;
    }

}
