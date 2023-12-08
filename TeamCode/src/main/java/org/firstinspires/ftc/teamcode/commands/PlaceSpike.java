package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.util.ElapsedTime;

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

    PIDFController yPID = new PIDFController(Constants.MecanumConstants.yPID);
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
    protected void execute() {
        switch (phase) {
            case 0:
                spike = vision.getPiecePosition();
                yPID.setTargetPosition(Constants.AutoPoses.spikeY);

                switch (spike) {
                    case MIDDLE:
                        yawPID.setTargetPosition(0);
                        break;
                    case RIGHT:
                        yawPID.setTargetPosition(Constants.AutoPoses.rightSpike);
                        break;
                    default:
                        yawPID.setTargetPosition(Constants.AutoPoses.leftSpike);
                        break;
                }

                phase += !(spike == null) ? 1 : 0;
                break;
            case 1:
                drive.drive(yPID.update(drive.getAvgDist()), 0, 0, true);
                phase += Utilities.withinBounds(drive.getAvgDist(), yPID.getTargetPosition(), 1) ? 1 : 0;
                break;
            case 2:
                drive.drive(0, 0, yawPID.update(drive.getYaw()), true);
                phase += Utilities.withinBounds(drive.getYaw(), yawPID.getTargetPosition(), 1.5) ? 1 : 0;
                break;
            case 3:
                wrist.setAngle(Constants.WristConstants.pickupAngle);
                phase++;
                break;
            case 4:
                claw.openLClaw();
                phase++;
                break;
            case 5:
                claw.closeLClaw();
                wrist.setAngle(Constants.WristConstants.homeAngle);
                phase++;
                break;
        }
    }

    @Override
    public boolean onEnd() {
        return phase == 6;
    }
}