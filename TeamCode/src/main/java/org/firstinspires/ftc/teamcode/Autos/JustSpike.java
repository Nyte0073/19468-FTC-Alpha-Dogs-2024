package org.firstinspires.ftc.teamcode.Autos;

import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.commands.PlaceSpike;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Mecanum;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

@Autonomous(name = Constants.OpModes.spikeAuto, group = Constants.OpModes.linearOp)
public class JustSpike extends LinearOpMode {

    Mecanum s_Drivetrain;
    Vision s_Vision;
    Wrist s_Wrist;
    Claw s_Claw;
    Arm s_Arm;


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


        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {

            switch (phase) {
                case 0:
                    spike.initialize();
                    spike.execute();
                    periodic();
                    phase += spike.onEnd() ? 1 : 0;
                    break;
            }
        }

    }

    public void periodic() {
        s_Drivetrain.periodic(telemetry);
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("big phase", phase);
        telemetry.addData("atTarget", s_Drivetrain.atTarget());
        telemetry.addLine("Spike:");
        spike.periodic(telemetry);
        telemetry.update();
    }
}