package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import static org.firstinspires.ftc.teamcode.Constants.IntakeConstants;

public class Intake {

    DcMotor outerIntake0;

    public Intake(HardwareMap hardwareMap) {
        outerIntake0 = hardwareMap.get(DcMotor.class, IntakeConstants.outerIntakeMotor);

        motorConfig(outerIntake0);

        outerIntake0.setDirection(IntakeConstants.outerInvert);
    }

    public void teleop(Gamepad gamepad1) {
        double outerOuttake = gamepad1.left_trigger;
        double outerIntake = gamepad1.right_trigger;

        setOuterPower(outerIntake - outerOuttake);
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
