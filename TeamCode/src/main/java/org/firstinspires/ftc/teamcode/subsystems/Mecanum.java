package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import static org.firstinspires.ftc.teamcode.Constants.MecanumConstants;

public class Mecanum {

    DcMotor frontLeft0, frontRight1, backLeft2, backRight3;
    // Retrieve the IMU from the hardware map
    IMU imu;
    // Adjust the orientation parameters to match your robot
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

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY + rotX - rx) / denominator;
        double backRightPower = (rotY - rotX - rx) / denominator;

        frontLeft0.setPower(frontLeftPower);
        backLeft2.setPower(backLeftPower);
        frontRight1.setPower(frontRightPower);
        backRight3.setPower(backRightPower);

        //[setPower(new double[] {frontLeftPower, backLeftPower, frontRightPower, backRightPower});
    }

    public void setPower(double[] power) {
        frontLeft0.setPower(power[0]);
        backLeft2.setPower(power[1]);
        frontRight1.setPower(power[2]);
        backRight3.setPower(power[3]);
    }

    public double[] getPower() {
        double[] power = new double[4];

        power[0] = frontLeft0.getPower();
        power[1] = backLeft2.getPower();
        power[2] = frontRight1.getPower();
        power[3] = backRight3.getPower();

        return power;
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Drive Motors");
        telemetry.addLine("Front Left: " + getPower()[0]);
        telemetry.addLine("Back Left: " + getPower()[1]);
        telemetry.addLine("Front Right: " + getPower()[2]);
        telemetry.addLine("Back Right: " + getPower()[3]);
    }

    /**
     * Standard motor config for all drivetrain motors
     * @param motor DcMotor to  configure
     * @return configured DcMotor
     */
    public DcMotor motorConfig(DcMotor motor) {
        motor.setZeroPowerBehavior(MecanumConstants.neutralMode);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        return motor;
    }

}
