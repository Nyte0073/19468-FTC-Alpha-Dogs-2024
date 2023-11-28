package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.ArmConfig;

public class Constants {

    public static final class OpModes {
        public static final String linearOp = "Linear Opmode";
        public static final String teleop = "Teleop";
        public static final String pidOp = "PID Tune";

        public static final String parkAuto = "Park";
    }

    public static final class MecanumConstants {

        public static final String frontLeftMotor = "frontLeft0";
        public static final String frontRightMotor = "frontRight1";
        public static final String backLeftMotor = "backLeft2";
        public static final String backRightMotor = "backRight3";

        public static final PIDCoefficients yPID = new PIDCoefficients(0, 0, 0);
        public static final PIDCoefficients xPID = new PIDCoefficients(0, 0, 0);
        public static final PIDCoefficients yawPID = new PIDCoefficients(0, 0, 0);

        public static final DcMotor.Direction invertLeft = DcMotor.Direction.REVERSE;
        public static final DcMotor.Direction invertRight = DcMotor.Direction.FORWARD;
        public static final DcMotor.ZeroPowerBehavior neutralMode = DcMotor.ZeroPowerBehavior.BRAKE;

        public static final double ticksPerRev = 8192;
        public static final double wheelRadius = 38 / 25.4; //38mm in inches
        public static final double gearRatio = 1;
        public static final double ticksToInch = ticksPerRev / (2 * wheelRadius * Math.PI);

        //TODO: get this
        public static double LATERAL_DISTANCE = 0; // in - distance between left and right
        public static double FORWARD_OFFSET = 0; // in - distance between the forward center

    }

    public static final class IntakeConstants {

        public static final String intakeMotor = "outerIntake0";

        public static final DcMotor.Direction invert = DcMotor.Direction.REVERSE;
        public static final DcMotor.ZeroPowerBehavior neutralMode = DcMotor.ZeroPowerBehavior.BRAKE;
    }

    public static final class WinchConstants {

        public static final String winch2 = "rightWinch2";
        public static final double encoderToInches = 1;
        public static final PIDCoefficients pidCoefficients = new PIDCoefficients(0, 0, 0);
        public static final DcMotor.Direction invert = DcMotor.Direction.FORWARD;
        public static final DcMotor.ZeroPowerBehavior neutralMode = DcMotor.ZeroPowerBehavior.BRAKE;
    }

    public static final class ClimberConstants {
        public static final String climber6 = "climberServo6";
        public static final String climberMotor1 = "climberMotor1";

        public static final DcMotor.Direction invert = DcMotor.Direction.FORWARD;
        public static final DcMotor.ZeroPowerBehavior neutralMode = DcMotor.ZeroPowerBehavior.BRAKE;
    }

    public static final class WristConstants {

        public static final String rightWristServo = "rightWrist2";
        public static final String wristWheel = "wristWheel0";
        public static final Servo.Direction rightInvert = Servo.Direction.FORWARD;
        public static final CRServo.Direction wheelInvert = CRServo.Direction.FORWARD;
        public static final double homeAngle = 50.0; //0
        public static final double midAngle = 23.8; //0

    }

    public static final class VisionConstants {

        public static final String scoreCam = "scoreCam";
        public static final String intakeCam = "intakeCam";

    }

    public static final class ScoringConstants {

        public static final double armLevel1 = 0.0;
        public static final double armLevel2 = 0.0;
        public static final double armLevel3 = 0.0;
        public static final double scoreAngle = 118.91; //0.2937

        public static final ArmConfig scoreLow = new ArmConfig(armLevel1, scoreAngle);
        public static final ArmConfig scoreMid = new ArmConfig(armLevel2, scoreAngle);
        public static final ArmConfig scoreHigh = new ArmConfig(armLevel3, scoreAngle);

    }

    /**
     * The pose is based on red, x should be flipped for blue
     */
    public static final class AutoPoses {
        public static final Pose2d parkClose = new Pose2d(0,0, 0);

    }

    public enum WinchLevel {
        LOW, MID, HIGH
    }

}
