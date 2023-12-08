package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Pose2d;
import org.firstinspires.ftc.teamcode.Utilities;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Mecanum;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

@Autonomous(name = Constants.OpModes.parkAuto, group = Constants.OpModes.linearOp)
public class Park extends LinearOpMode {

    Mecanum s_Drivetrain;
    Wrist s_Wrist;
    Claw s_Claw;

    int phase = 0;

    private ElapsedTime runtime = new ElapsedTime();
    PIDFController yPID = new PIDFController(Constants.MecanumConstants.yPID, 0);
    Pose2d parkPose = Constants.AutoPoses.parkClose;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        s_Drivetrain = new Mecanum(hardwareMap);
        s_Claw = new Claw(hardwareMap);
        s_Wrist = new Wrist(hardwareMap);

        phase = 0;

        s_Drivetrain.resetEncoders();

        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {

            switch (phase) {
                case 0:
                    yPID.setTargetPosition(parkPose.getY());
                    phase++;
                    break;
                case 1:
                    s_Drivetrain.drive(Utilities.clip(yPID.update(s_Drivetrain.getAvgDist()), 0.2, -0.2), 0, 0, true);
                    phase += Utilities.withinBounds(s_Drivetrain.getAvgDist(), parkPose.getY(), 1.5) ? 1 : 0;
                    break;
                case 2:
                    runtime.reset();
                    phase++;
                    break;
                case 3:
                    s_Drivetrain.drive(0,0,0,false);
                    s_Wrist.setAngle(Constants.WristConstants.pickupAngle);
                    s_Claw.openLClaw();
                    s_Claw.openRClaw();
                    phase += runtime.seconds() > 3 ? 1 : 0;
                    break;
                case 4:
                    s_Claw.closeLClaw();
                    s_Claw.closeRClaw();
                    s_Wrist.setAngle(Constants.WristConstants.homeAngle);
                    phase++;
                    break;
            }


            periodic();
        }

    }

    public void periodic() {
        s_Drivetrain.periodic(telemetry);
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("phase ", phase);
        telemetry.addData("power", yPID.update(s_Drivetrain.getAvgDist()));
        telemetry.addData("at setpoint", Utilities.withinBounds(s_Drivetrain.getAvgDist(), parkPose.getX(), 1.5));
        telemetry.update();
    }
}