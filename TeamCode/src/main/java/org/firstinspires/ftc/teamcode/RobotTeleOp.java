package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Mecanum;

@TeleOp(name = "Basic: Mecanum Teleop", group = "Linear Opmode")
public class RobotTeleOp extends LinearOpMode {

    Mecanum s_Drivetrain;

    @Override
    public void runOpMode() {

        s_Drivetrain = new Mecanum();

        while(opModeIsActive()) {
            s_Drivetrain.teleop();
        }

    }

}
