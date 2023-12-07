package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Mecanum;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

@TeleOp(name = Constants.OpModes.teleop, group = Constants.OpModes.linearOp)
public class RobotTeleOp extends LinearOpMode {

    Mecanum s_Drivetrain;
    Arm s_Arm;
    Vision s_Vision;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        s_Drivetrain = new Mecanum(hardwareMap);
        s_Arm = new Arm(hardwareMap);
        s_Vision = new Vision(hardwareMap);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            s_Drivetrain.teleop(gamepad1);
            s_Arm.teleop(gamepad2);

            s_Drivetrain.periodic(telemetry);
            s_Arm.periodic(telemetry);
            s_Vision.periodic(telemetry);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }

}