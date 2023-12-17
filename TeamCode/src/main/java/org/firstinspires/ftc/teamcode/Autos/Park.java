package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Utilities;
import org.firstinspires.ftc.teamcode.commands.PlaceSpike;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Mecanum;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

@Autonomous(name = Constants.OpModes.parkAuto, group = Constants.OpModes.linearOp)
public class Park extends LinearOpMode {

    Mecanum s_Drivetrain;
    Vision s_Vision;
    Wrist s_Wrist;
    Claw s_Claw;
    Arm s_Arm;

    boolean blueAlliance = false;

    int phase = 0;

    private ElapsedTime runtime = new ElapsedTime();
    PlaceSpike spike;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        s_Drivetrain = new Mecanum(hardwareMap);
        s_Vision = new Vision(hardwareMap);
        s_Wrist = new Wrist(hardwareMap);
        s_Arm = new Arm(hardwareMap);
        s_Claw = new Claw(hardwareMap);

        phase = 0;

        spike = new PlaceSpike(s_Drivetrain, s_Arm, s_Wrist, s_Claw, s_Vision);

        PIDFController yawPID = new PIDFController(Constants.MecanumConstants.yawPID, 0);

        s_Drivetrain.resetEncoders();

        while(!opModeIsActive() && !gamepad1.a) {
            if (gamepad1.left_bumper) blueAlliance = true;
            if (gamepad1.right_bumper) blueAlliance = false;
            periodic();
        }

        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {

            switch (phase) {
                case 0:
                    spike.initialize();
                    spike.execute();
                    phase += spike.onEnd() ? 1 : 0;
                    break;
                case 1:
                    s_Drivetrain.resetEncoders();
                    phase++;
                    break;
                case 2:
                    if (spike.getSpike() == Vision.Position.MIDDLE) {
                        if (blueAlliance) {
                            yawPID.setTargetPosition(90);
                        } else {
                            yawPID.setTargetPosition(-90);
                        }
                        s_Drivetrain.drive(0, 0, -Utilities.clip(yawPID.update(s_Drivetrain.getYaw()), 0.1, -0.1), false);
                        phase += Utilities.withinBounds(s_Drivetrain.getYaw(), yawPID.getTargetPosition(), 1.5) ? 1 : 0;
                    } else {
                        s_Drivetrain.driveToPos(600);

                        if (s_Drivetrain.atTarget()) {
                            s_Drivetrain.setPower(0, 0, 0, 0);
                        } else {
                            s_Drivetrain.setPower(0.2, 0.2, 0.2, 0.2);
                        }

                        phase += s_Drivetrain.atTarget() ? 1 : 0;
                    }
                    break;
                case 3:
                    s_Drivetrain.setPower(0, 0, 0, 0);
                    s_Drivetrain.resetEncoders();
                    phase++;
                    break;
                case 4:
                    if (spike.getSpike() == Vision.Position.MIDDLE) {
                        s_Drivetrain.driveToPos(1500);

                        if (s_Drivetrain.atTarget()) {
                            s_Drivetrain.setPower(0, 0, 0, 0);
                        } else {
                            s_Drivetrain.setPower(0.2, 0.2, 0.2, 0.2);
                        }

                        phase = s_Drivetrain.atTarget() ? 7 : phase; //end here
                    } else {
                        if (blueAlliance) {
                            yawPID.setTargetPosition(90);
                        } else {
                            yawPID.setTargetPosition(-90);
                        }
                        s_Drivetrain.drive(0, 0, -Utilities.clip(yawPID.update(s_Drivetrain.getYaw()), 0.1, -0.1), false);
                        phase += Utilities.withinBounds(s_Drivetrain.getYaw(), yawPID.getTargetPosition(), 1.5) ? 1 : 0;

                    }
                    break;
                case 5:
                    s_Drivetrain.resetEncoders();
                    phase++;
                    break;
                case 6:
                    s_Drivetrain.driveToPos(1000);

                    if (s_Drivetrain.atTarget()) {
                        s_Drivetrain.setPower(0, 0, 0, 0);
                    } else {
                        s_Drivetrain.setPower(0.2, 0.2, 0.2, 0.2);
                    }

                    phase += s_Drivetrain.atTarget() ? 1 : 0;
                    break;
                case 7:
                    s_Drivetrain.setPower(0, 0, 0, 0);
                    s_Wrist.setAngle(Constants.WristConstants.pickupAngle);
                    phase += runtime.seconds() > 25 ? 1 : 0;
                    break;
                case 8:
                    s_Claw.openRClaw();
                    break;

            }


            periodic();
        }

    }

    public void periodic() {
        s_Drivetrain.periodic(telemetry);
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("alliance:", blueAlliance ? "blue" : "red");
        telemetry.addData("big phase", phase);
        telemetry.addData("atTarget", s_Drivetrain.atTarget());
        telemetry.addLine("Spike:");
        spike.periodic(telemetry);
        telemetry.update();
    }
}