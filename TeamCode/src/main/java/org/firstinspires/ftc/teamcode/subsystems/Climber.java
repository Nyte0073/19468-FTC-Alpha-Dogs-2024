package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import static org.firstinspires.ftc.teamcode.Constants.ClimberConstants;

public class Climber {

    DcMotor climberMotor1;

    public Climber(HardwareMap hardwareMap) {
        climberMotor1 = hardwareMap.get(DcMotor.class, ClimberConstants.climber1);

        motorConfig(climberMotor1);

        climberMotor1.setDirection(ClimberConstants.invert);
    }

    public void teleop(Gamepad gamepad2) {
        double winchPower = gamepad2.left_stick_y;

        setPower(winchPower);
    }

    public void setPower(double power) {
        climberMotor1.setPower(power);
    }

    public double getPower() {
        return climberMotor1.getPower();
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Climber Motor");
        telemetry.addLine("Climber Power: " + getPower());
    }

    /**
     * Standard motor config for all drivetrain motors
     * @param motor DcMotor to  configure
     * @return configured DcMotor
     */
    public DcMotor motorConfig(DcMotor motor) {
        motor.setZeroPowerBehavior(ClimberConstants.neutralMode);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        return motor;
    }
}
