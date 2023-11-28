package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Pose2d;
import org.firstinspires.ftc.teamcode.commands.MoveToPoint;
import org.firstinspires.ftc.teamcode.subsystems.Climber;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Mecanum;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.subsystems.Winch;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

@TeleOp(name = Constants.OpModes.parkAuto, group = Constants.OpModes.linearOp)
public class Park extends LinearOpMode {

    Mecanum s_Drivetrain;
    Vision s_Vision;

    boolean blueAlliance = false;

    int phase = 0;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        s_Drivetrain = new Mecanum(hardwareMap);
        s_Vision = new Vision(hardwareMap);

        phase = 0;

        //TODO: test
        while (!gamepad1.a) {
            if (gamepad1.left_bumper) blueAlliance = true;
            if (gamepad1.right_bumper) blueAlliance = false;
        }

        Pose2d parkPose = Constants.AutoPoses.parkClose;

        s_Drivetrain.setPose(new Pose2d(0,0,0));

        MoveToPoint parkCommand = new MoveToPoint(s_Drivetrain, s_Vision, parkPose, true);

        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {

            switch (phase) {
                case 0:
                    parkPose = blueAlliance ? new Pose2d(parkPose.getY(), -parkPose.getX(), parkPose.getYaw()) : parkPose;
                    phase++;
                    break;
                case 1:
                    parkCommand.initialize();
                    parkCommand.execute();
                    phase += parkCommand.onEnd() ? 1 : 0;
                    break;
            }

            s_Drivetrain.periodic(telemetry);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }
}
