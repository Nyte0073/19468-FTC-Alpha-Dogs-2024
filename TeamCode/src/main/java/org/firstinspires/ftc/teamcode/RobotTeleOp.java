package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.commands.MoveToPoint;
import org.firstinspires.ftc.teamcode.commands.TeleScore;
import org.firstinspires.ftc.teamcode.subsystems.Climber;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Mecanum;
import org.firstinspires.ftc.teamcode.subsystems.PlaneLauncher;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.subsystems.Winch;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

@TeleOp (name = Constants.OpModes.teleop, group = Constants.OpModes.linearOp)
public class RobotTeleOp extends LinearOpMode {

    Mecanum s_Drivetrain;
    Intake s_Intake;
    Winch s_Winch;
    Wrist s_Wrist;
    Vision s_Vision;
    Climber s_Climber;
    PlaneLauncher s_Launcher;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        s_Drivetrain = new Mecanum(hardwareMap);
        s_Intake = new Intake(hardwareMap);
        s_Winch = new Winch(hardwareMap);
        s_Wrist = new Wrist(hardwareMap);
        s_Vision = new Vision(hardwareMap);
        s_Climber = new Climber(hardwareMap);
        s_Launcher = new PlaneLauncher(hardwareMap);

        TeleScore score = new TeleScore(s_Winch, s_Wrist, s_Winch.getLevel(), telemetry);
        MoveToPoint target = new MoveToPoint(s_Drivetrain, s_Vision, new Pose2d(0,0,0), false);

        score.setLevel(s_Winch.getLevel());

        waitForStart();
        runtime.reset();

        while(opModeIsActive()) {

            /**
             * Driver:
             * Left stick:
             * y - drive
             * x - strafe
             *
             * Right stick:
             * x - rot
             *
             * LT/RT - Main Intake/Outtake
             * LB/RB - Interal Intake/Outtake
             *
             * Buttons:
             * a - auto align
             *
             * Options - Reset Gyro
             * -------------------------------------
             * Operator:
             * Right stick:
             * y - manual winch
             *
             * Left stick:
             * y - climber
             *
             * LT/RT - manual wrist
             *
             * dpad:
             * up/down - winch level
             * left/right - tag offset left or right
             *
             * Buttons:
             * a - go to level
             * x - switch between home and score
             * b - auto score
             */

            s_Intake.teleop(gamepad1);
            s_Climber.teleop(gamepad2);

            if (gamepad2.b) {
                score.setLevel(s_Winch.getLevel());
                score.initialize();
                score.execute();
            } else {
                s_Winch.teleop(gamepad2);
                s_Wrist.teleop(gamepad1, gamepad2);
                score.isFirstRun(true);
            }

            try {
                if (s_Vision.hasTag()) target.updatePose(s_Vision.getLocation());
            } catch (Exception e) {

            }

            if (gamepad1.y) {
                target.initialize();
                target.execute();
            } else {
                s_Drivetrain.teleop(gamepad1);
            }
            s_Launcher.teleop(gamepad2);

            if (gamepad2.a) {
                s_Wrist.setAngle(Constants.WristConstants.homeAngle);
            }

            s_Drivetrain.periodic(telemetry);
            s_Winch.periodic(telemetry);
            s_Intake.periodic(telemetry);
            s_Wrist.periodic(telemetry);
            s_Vision.periodic(telemetry);
            s_Climber.periodic(telemetry);
            s_Launcher.periodic(telemetry);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }

}