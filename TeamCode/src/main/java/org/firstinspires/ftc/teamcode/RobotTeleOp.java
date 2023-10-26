package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Mecanum;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.subsystems.Winch;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

@TeleOp(name = Constants.OpModes.teleop, group = Constants.OpModes.linearOp)
public class RobotTeleOp extends LinearOpMode {

    Mecanum s_Drivetrain;
    Intake s_Intake;
    Winch s_Winch;
    Wrist s_Wrist;
    //-Vision s_Vision;
    //Climber s_Climber;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        s_Drivetrain = new Mecanum(hardwareMap);
        s_Intake = new Intake(hardwareMap);
        s_Winch = new Winch(hardwareMap);
        s_Wrist = new Wrist(hardwareMap);
        //s_Vision = new Vision(hardwareMap);
        //s_Climber = new Climber(hardwareMap);

        waitForStart();
        runtime.reset();

        while(opModeIsActive()) {
            s_Drivetrain.teleop(gamepad1);
            s_Intake.teleop(gamepad1);
            s_Winch.teleop(gamepad2);
            s_Wrist.teleop(gamepad2);
            //s_Climber.teleop(gamepad2);

            s_Drivetrain.periodic(telemetry);
            s_Winch.periodic(telemetry);
            s_Intake.periodic(telemetry);
            s_Wrist.periodic(telemetry);
            //s_Climber.periodic(telemetry);

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }

}
