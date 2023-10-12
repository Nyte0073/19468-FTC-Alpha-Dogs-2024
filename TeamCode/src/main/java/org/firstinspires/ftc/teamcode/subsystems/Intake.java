package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import static org.firstinspires.ftc.teamcode.Constants.IntakeConstants;

public class Intake {

    DcMotor outerIntake0;
    Servo intakeServo2;

    public Intake(HardwareMap hardwareMap) {
        outerIntake0 = hardwareMap.get(DcMotor.class, IntakeConstants.outerIntakeMotor);
        intakeServo2 = hardwareMap.get(Servo.class, IntakeConstants.intakeServo);

        motorConfig(outerIntake0);

        outerIntake0.setDirection(IntakeConstants.outerInvert);
        intakeServo2.setDirection(IntakeConstants.servoInvert);
    }

    public void teleop(Gamepad gamepad1) {
        double outerOuttake = gamepad1.left_trigger;
        double outerIntake = gamepad1.right_trigger;

        if (gamepad1.x) toggleIntake();

        setOuterPower(outerIntake - outerOuttake);
    }

    public void toggleIntake() {
        if (intakeServo2.getPosition() > 0.5) {
            intakeServo2.setPosition(0);
        } else {
            intakeServo2.setPosition(0.6);
        }
    }

    public boolean getIntakeRetracted() {
        return intakeServo2.getPosition() > 0.5;
    }

    //For calibrating
    public double getIntakePosition() {
        return intakeServo2.getPosition();
    }

    public void setOuterPower(double power) {
        outerIntake0.setPower(power);
    }

    public double getOuterPower() {
        return outerIntake0.getPower();
    }


    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Intake Motors");
        telemetry.addLine("Outer Intake Power: " + getOuterPower());
        telemetry.addLine("Intake Retracted: " + getIntakeRetracted());
        telemetry.addLine("Intake Pos: " + getIntakePosition());
    }

    /**
     * Standard motor config for all drivetrain motors
     * @param motor DcMotor to  configure
     * @return configured DcMotor
     */
    public DcMotor motorConfig(DcMotor motor) {
        motor.setZeroPowerBehavior(IntakeConstants.neutralMode);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        return motor;
    }
}
