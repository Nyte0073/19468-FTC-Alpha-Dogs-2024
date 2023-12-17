package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Constants.WinchConstants;

import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Utilities;

public class Arm {

    DcMotor leftArmMotor0, rightArmMotor1;
    PIDFController pidController;
    boolean PIDToggle = true;

    public Arm(HardwareMap hardwareMap) {
        leftArmMotor0 = hardwareMap.get(DcMotor.class, WinchConstants.leftArm0);
        rightArmMotor1 = hardwareMap.get(DcMotor.class, WinchConstants.rightArm1);

        motorConfig(leftArmMotor0);
        motorConfig(rightArmMotor1);

        leftArmMotor0.setDirection(WinchConstants.leftInvert);
        rightArmMotor1.setDirection(WinchConstants.rightInvert);

        pidController = new PIDFController(WinchConstants.winchPID, 0);
    }

    public void teleop(Gamepad gamepad1) {

       if (gamepad1.x) {
            setSetpoint(WinchConstants.pickupAngle); //Intake
        } else if (gamepad1.b) {
            setSetpoint(WinchConstants.scoreAngle); //Score
        }

    }

    public void setPower(double power) {
        power = getAngle() < WinchConstants.intakeSafety && power > 0 ? 0 : (getAngle() > WinchConstants.scoreSafety && power < 0 ? 0 : power);

        power = Utilities.clip(power, WinchConstants.maxSpeed, -WinchConstants.maxSpeed);

        leftArmMotor0.setPower(power);
        rightArmMotor1.setPower(power);

    }

    public double getPower() {
        return (leftArmMotor0.getPower() + rightArmMotor1.getPower()) / 2;

    }

    //TODO: convert to inches
    public double getAngle() {
        return leftArmMotor0.getCurrentPosition() / WinchConstants.encoderToDeg;
    }

    public void setSetpoint(double position) {
        pidController.setTargetPosition(position);
    }

    public boolean atSetpoint() {
        return Utilities.withinBounds(getAngle(), pidController.getTargetPosition(), WinchConstants.tolerance);
    }

    public boolean atWSetpoint() {
        return Utilities.withinBounds(getAngle(), pidController.getTargetPosition(), WinchConstants.wristTolerance);
    }
    public double getSetpointCalc() {
        return -pidController.update(getAngle());
    }

    public void togglePID(boolean enable) {
        PIDToggle = enable;
    }

    public void periodic(Telemetry telemetry) {
        if (PIDToggle && !atSetpoint()) {
            setPower(getSetpointCalc());
        } else if (PIDToggle && atSetpoint()) {
            setPower(0);
        }

        telemetry.addLine("Arm Motors");
        telemetry.addLine("Arm Power: " + getPower());
        telemetry.addData("Arm Position: ", getAngle());
        telemetry.addData("at setpoint", atSetpoint());
        telemetry.addData("update", getSetpointCalc());
    }

    /**
     * Standard motor config for all drivetrain motors
     *
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