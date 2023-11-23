package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.control.PIDFController;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Pose2d;
import org.firstinspires.ftc.teamcode.Utilities;
import org.firstinspires.ftc.teamcode.subsystems.Mecanum;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

public class MoveToPoint extends CommandBase {

    Mecanum drive;
    Vision vision;
    Pose2d targetPose;

    PIDFController yPID;
    PIDFController xPID;
    PIDFController yawPID;

    int phase;
    boolean fast = false;

    double xSpeed, ySpeed, rotSpeed;

    public MoveToPoint(Mecanum drive, Vision vision, Pose2d targetPose, boolean fast) {
        this.drive = drive;
        this.vision = vision;
        this.targetPose = targetPose;

        this.fast = fast;
    }

    @Override
    public void initialize() {
        super.initialize();

        yPID = new PIDFController(Constants.MecanumConstants.yPID, 0);
        xPID = new PIDFController(Constants.MecanumConstants.xPID, 0);
        yawPID = new PIDFController(Constants.MecanumConstants.yawPID, 0);

        yPID.setTargetPosition(targetPose.getY());
        xPID.setTargetPosition(targetPose.getX());
        yawPID.setTargetPosition(targetPose.getYaw());

        yPID.reset();
        xPID.reset();
        yawPID.reset();

        phase = 0;
    }

    @Override
    public void execute() {
        ySpeed = yPID.update(drive.getY());
        xSpeed = xPID.update(drive.getX());
        rotSpeed = yawPID.update(drive.getYaw());

        if (fast) {
            switch (phase) {
                case 0:
                    drive.drive(0, 0, rotSpeed, true);
                    phase += Utilities.withinBounds(drive.getYaw(), targetPose.getYaw(), 1) ? 1 : 0;
                    break;
                case 1:
                    drive.drive(ySpeed, xSpeed, 0, true);
                    phase += Utilities.withinBounds(drive.getX(), targetPose.getX(), 0.25) && Utilities.withinBounds(drive.getY(), targetPose.getY(), 0.25) ? 1 : 0;
                    break;
            }
        } else {
            switch (phase) {
                case 0:
                    drive.drive(0, 0, rotSpeed, true);
                    phase += Utilities.withinBounds(drive.getYaw(), targetPose.getYaw(), 1) ? 1 : 0;
                    break;
                case 1:
                    drive.drive(ySpeed, 0, 0, true);
                    phase += Utilities.withinBounds(drive.getY(), targetPose.getY(), 0.25) ? 1 : 0;
                    break;
                case 2:
                    drive.drive(0, xSpeed, 0, true);
                    phase += Utilities.withinBounds(drive.getX(), targetPose.getX(), 0.25) ? 1 : 0;
                    break;
            }
        }

    }

    @Override
    public boolean onEnd() {
        return Pose2d.atPose(drive.getPose(), targetPose, 0.25, 1);
    }

}
