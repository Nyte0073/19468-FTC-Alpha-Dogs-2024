package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Utilities;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Mecanum;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class PlaceSpike extends CommandBase {

    Mecanum drive;
    Arm arm;
    Wrist wrist;
    Claw claw;
    Vision vision;

    int phase = 0;
    ElapsedTime time = new ElapsedTime();
    Vision.Position spike = null;

    PIDFController yawPID = new PIDFController(Constants.MecanumConstants.yawPID);

    public PlaceSpike(Mecanum drive, Arm arm, Wrist wrist, Claw claw, Vision vision) {
        this.drive = drive;
        this.arm = arm;
        this.claw = claw;
        this.wrist = wrist;
        this.vision = vision;
    }

    @Override
    public void initialize() {
        if (firstRun) {
            time.reset();
            time.startTime();
            phase = 0;
        }

        super.initialize();
    }

    @Override
    public void execute() {
        switch (phase) {
            case 0:
                wrist.setAngle(Constants.WristConstants.pickupAngle);
                spike = vision.getPiecePosition();
                phase += !(spike == null) && time.seconds() > 2 ? 1 : 0;
                drive.resetGyro();
                break;
            case 1:
                if (time.seconds() < 3) {
                    spike = vision.getPiecePosition();
                }

                drive.driveToPos(980);

                if (drive.atTarget()) {
                    drive.setPower(0, 0, 0, 0);
                } else {
                    drive.setPower(0.2, 0.2, 0.2, 0.2);
                }

                phase += drive.atTarget() ? 1 : 0;
                wrist.setAngle(Constants.WristConstants.pickupAngle);
                break;
            case 2:
                drive.resetEncoders();
                switch (spike) {
                    case MIDDLE:
                        yawPID.setTargetPosition(Constants.AutoPoses.midSpike);
                        break;
                    case RIGHT:
                        yawPID.setTargetPosition(Constants.AutoPoses.rightSpike);
                        break;
                    default:
                        yawPID.setTargetPosition(Constants.AutoPoses.leftSpike);
                        break;
                }
                drive.drive(0, 0, -Utilities.clip(yawPID.update(drive.getYaw()), 0.1, -0.1), false);

                phase += Utilities.withinBounds(drive.getYaw(), yawPID.getTargetPosition(), 1.5) ? 1 : 0;
                break;
            case 3:
                drive.drive(0, 0, 0, false);
                wrist.setAngle(Constants.WristConstants.pickupAngle);
                phase += time.seconds() > 8 ? 1 : 0;
                break;
            case 4:
                claw.openLClaw();
                phase += time.seconds() > 9 ? 1 : 0;
                break;
            case 5:
                claw.closeLClaw();
                wrist.setAngle(Constants.WristConstants.homeAngle);
                phase++;
                break;
            case 6:
                yawPID.setTargetPosition(0);
                drive.drive(0, 0, -Utilities.clip(yawPID.update(drive.getYaw()), 0.1, -0.1), false);

                phase += Utilities.withinBounds(drive.getYaw(), yawPID.getTargetPosition(), 1.5) ? 1 : 0;
                break;
            case 7:
                drive.drive(0, 0, 0, false);
                phase++;
                break;
        }
    }

    @Override
    public boolean onEnd() {
        return phase == 8;
    }

    public Vision.Position getSpike() {
        return spike;
    }

    public int getPhase() {
        return phase;
    }

    public PIDFController getData() {
        return yawPID;
    }
    public void periodic(Telemetry telemetry) {
        telemetry.addData("phase ", getPhase());
        telemetry.addData("Spike", getSpike());
        telemetry.addData("yaw target", getData().getTargetPosition());
    }
}