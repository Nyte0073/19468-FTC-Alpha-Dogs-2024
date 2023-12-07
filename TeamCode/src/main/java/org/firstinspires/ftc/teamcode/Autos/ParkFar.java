//package org.firstinspires.ftc.teamcode.Autos;
//
//import com.acmerobotics.roadrunner.control.PIDFController;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.teamcode.Constants;
//import org.firstinspires.ftc.teamcode.Pose2d;
//import org.firstinspires.ftc.teamcode.Utilities;
//import org.firstinspires.ftc.teamcode.subsystems.depreciated.Intake;
//import org.firstinspires.ftc.teamcode.subsystems.Mecanum;
//import org.firstinspires.ftc.teamcode.subsystems.Vision;
//import org.firstinspires.ftc.teamcode.subsystems.Wrist;
//
//@Autonomous(name = Constants.OpModes.parkAutoF, group = Constants.OpModes.linearOp)
//public class ParkFar extends LinearOpMode {
//
//    Mecanum s_Drivetrain;
//    Vision s_Vision;
//    Wrist s_Wrist;
//    Intake s_Intake;
//
//    boolean blueAlliance = false;
//
//    int phase = 0;
//
//    private ElapsedTime runtime = new ElapsedTime();
//    PIDFController xPID = new PIDFController(Constants.MecanumConstants.xPID, 0);
//    Pose2d parkPose = Constants.AutoPoses.RparkFar;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//
//        s_Drivetrain = new Mecanum(hardwareMap);
//        s_Vision = new Vision(hardwareMap);
//        s_Intake = new Intake(hardwareMap);
//        s_Wrist = new Wrist(hardwareMap);
//
//        phase = 0;
//
//        //TODO: test
//        while (!gamepad1.a && !opModeIsActive()) {
//            if (gamepad1.left_bumper) parkPose = Constants.AutoPoses.BparkFar; //Blue
//            if (gamepad1.right_bumper) parkPose = Constants.AutoPoses.RparkFar; //Red
//
//            periodic();
//        }
//
//        s_Drivetrain.setPose(new Pose2d(0, 0, 0));
//
//        waitForStart();
//        runtime.reset();
//        while (opModeIsActive()) {
//
//            switch (phase) {
//                case 0:
//                    xPID.setTargetPosition(parkPose.getY());
//                    phase++;
//                    break;
//                case 1:
//                    s_Drivetrain.drive(Utilities.clip(xPID.update(s_Drivetrain.getY()), 0.2, -0.2), 0, 0, false);
//                    phase += Utilities.withinBounds(s_Drivetrain.getY(), parkPose.getY(), 1.5) ? 1 : 0;
//                    break;
//                case 2:
//                    xPID.setTargetPosition(parkPose.getX());
//                    phase++;
//                    break;
//                case 3:
//                    s_Drivetrain.drive(0, Utilities.clip(xPID.update(s_Drivetrain.getX()), 0.2, -0.2), 0, false);
//                    phase += Utilities.withinBounds(s_Drivetrain.getX(), parkPose.getX(), 1.5) ? 1 : 0;
//                    break;
//                case 4:
//                    s_Drivetrain.drive(0,0,0,false);
//                    s_Wrist.setPower(-1);
//                    s_Intake.setPower(-1);
//                    s_Wrist.setAngle(40);
//                    break;
//            }
//
//
//            periodic();
//        }
//
//    }
//
//    public void periodic() {
//        s_Drivetrain.periodic(telemetry);
//        telemetry.addData("Status", "Run Time: " + runtime.toString());
//        telemetry.addData("alliance:", blueAlliance ? "blue" : "red");
//        telemetry.addData("phase ", phase);
//        telemetry.addData("power", xPID.update(s_Drivetrain.getX()));
//        telemetry.addData("at setpoint", Utilities.withinBounds(s_Drivetrain.getX(), parkPose.getX(), 1.5));
//        telemetry.update();
//    }
//}