package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import static org.firstinspires.ftc.teamcode.Constants.IntakeConstants;

public class Intake {

    DcMotor outerIntake0;
    DcMotor innerIntake2;

    public Intake(HardwareMap hardwareMap) {
        outerIntake0 = hardwareMap.get(DcMotor.class, IntakeConstants.outerIntakeMotor);
        innerIntake2 = hardwareMap.get(DcMotor.class, IntakeConstants.innerIntakeMotor);

        motorConfig(outerIntake0);
        motorConfig(innerIntake2);
    }

    public void teleop(Gamepad gamepad1) {
        double outerOuttake = gamepad1.left_trigger;
        double outerIntake = gamepad1.right_trigger;
        double innerOuttake = gamepad1.left_bumper ? 1 : 0;
        double innerIntake = gamepad1.right_bumper ? 1 : 0;

        setOuterPower(outerIntake - outerOuttake);
        setInnerPower(innerIntake - innerOuttake);
    }

    public void setOuterPower(double power) {
        outerIntake0.setPower(power);
    }

    public double getOuterPower() {
        return outerIntake0.getPower();
    }

    public void setInnerPower(double power) {
        innerIntake2.setPower(power);
    }

    public double getInnerPower() {
        return innerIntake2.getPower();
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Intake Motors");
        telemetry.addLine("Outer Intake Power: " + getOuterPower());
        telemetry.addLine("Inner Intake Power: " + getInnerPower());
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
        motor.setDirection(IntakeConstants.invert);

        return motor;
    }
}
