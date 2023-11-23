package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.control.PIDFController;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Pose2d;
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

    public MoveToPoint(Mecanum drive, Vision vision, Pose2d targetPose) {
        this.drive = drive;
        this.vision = vision;
        this.targetPose = targetPose;
    }

    @Override
    public void initialize() {
        yPID = new PIDFController(Constants.MecanumConstants.yPID, 0);
        xPID = new PIDFController(Constants.MecanumConstants.xPID, 0);
        yawPID = new PIDFController(Constants.MecanumConstants.yawPID, 0);

        yPID.setTargetPosition(targetPose.getY());
        xPID.setTargetPosition(targetPose.getX());
        yawPID.setTargetPosition(targetPose.getYaw());

        phase = 0;
    }

    @Override
    public void execute() {



    }

    @Override
    public boolean onEnd() {
        return Pose2d.atPose(drive.getPose(), targetPose, 0.25, 1);
    }

}
