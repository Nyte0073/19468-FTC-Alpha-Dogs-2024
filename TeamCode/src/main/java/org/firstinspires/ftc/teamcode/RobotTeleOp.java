package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Mecanum;

@TeleOp(name = "Basic: Mecanum Teleop", group = "Linear Opmode")
public class RobotTeleOp extends LinearOpMode {

    Mecanum s_Drivetrain;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        s_Drivetrain = new Mecanum(hardwareMap);

        waitForStart();
        runtime.reset();

        while(opModeIsActive()) {
            s_Drivetrain.teleop(gamepad1);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }

}
