package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Constants {

    public static final class OpModes {
        public static final String linearOp = "Linear Opmode";
        public static final String teleop = "Teleop";
    }

    public static final class MecanumConstants {

        public static final String frontLeftMotor = "frontLeft0";
        public static final String frontRightMotor = "frontRight1";
        public static final String backLeftMotor = "backLeft2";
        public static final String backRightMotor = "backRight3";

        public static final DcMotor.Direction invertLeft = DcMotor.Direction.REVERSE;
        public static final DcMotor.Direction invertRight = DcMotor.Direction.FORWARD;
        public static final DcMotor.ZeroPowerBehavior neutralMode = DcMotor.ZeroPowerBehavior.BRAKE;

    }

    public static final class IntakeConstants {

        public static final String intakeMotor = "outerIntake0";

        public static final DcMotor.Direction invert = DcMotor.Direction.REVERSE;
        public static final DcMotor.ZeroPowerBehavior neutralMode = DcMotor.ZeroPowerBehavior.BRAKE;
    }

    public static final class WinchConstants {

        public static final String winch2 = "rightWinch2";

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
        public static final Servo.Direction rightInvert = Servo.Direction.FORWARD;
        public static final double scoreAngle = 118.91; //0.2937
        public static final double homeAngle = 50.0; //0
        public static final double midAngle = 23.8; //0

    }

    public static final class VisionConstants {

        public static final String webcam = "Webcam 1";

    }

}
