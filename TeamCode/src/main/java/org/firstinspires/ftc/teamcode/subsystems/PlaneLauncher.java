package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Constants.WristConstants;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Utilities;

public class PlaneLauncher {

    Servo launcher;
    double teleopAngle = Constants.PlaneConstants.hold;
    int launcherSwap = 0;

    public PlaneLauncher(HardwareMap hardwareMap) {
        launcher = hardwareMap.get(Servo.class, Constants.PlaneConstants.launcher0);

        launcher.setDirection(Constants.PlaneConstants.inverted);
    }

    public double getAngle() {
        return (launcher.getPosition() * 300);
    }

    public void setAngle(double angle) {
        launcher.setPosition((angle / 300));
        teleopAngle = angle;
    }

    public double getPos() {
        return launcher.getPosition();
    }

    public void setPos(double position) {
        launcher.setPosition(position);
    }

    public void teleop(Gamepad gamepad2) {
        if (gamepad2.y && launcherSwap == 0) {

            if (getAngle() == Constants.PlaneConstants.hold) {
                teleopAngle = Constants.PlaneConstants.release;
            } else {
                teleopAngle = Constants.PlaneConstants.hold;
            }
            launcherSwap++;
        } else  if (!gamepad2.y && launcherSwap != 0) {
            launcherSwap = 0;
        } else {
            teleopAngle += gamepad2.right_trigger - gamepad2.left_trigger;
        }

        teleopAngle = Utilities.clip(teleopAngle, 300, 0);

        setAngle(teleopAngle);
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Plane:");
        telemetry.addLine("Wrist Angle: " + getAngle());
    }
}