package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

public class Mecanum {

    DcMotor frontLeft0, frontRight1, backLeft2, backRight3;

    public void Mecanum() {

        frontLeft0 = HardwareMap.get(DcMotor.class, "frontLeft0");
        frontLeft0 = HardwareMap.get(DcMotor.class, "frontRight1");
        frontLeft0 = HardwareMap.get(DcMotor.class, "backLeft2");
        frontLeft0 = HardwareMap.get(DcMotor.class, "backRight3");

        frontLeft0 = motorConfig(frontLeft0);
        frontRight1 = motorConfig(frontRight1);
        backLeft2 = motorConfig(backLeft2);
        backRight3 = motorConfig(backRight3);

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
