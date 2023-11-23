package org.firstinspires.ftc.teamcode.subsystems;

public class ArmConfig {

    private double armExtension, wristAngle;

    public ArmConfig(double armExtension, double wristAngle) {
        this.armExtension = armExtension;
        this.wristAngle = wristAngle;
    }

    public double getArmExtension() {
        return armExtension;
    }

    public double getWristAngle() {
        return wristAngle;
    }

}
