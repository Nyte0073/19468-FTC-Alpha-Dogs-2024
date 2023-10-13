package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Constants {

    public static final class OpModes {
        public static final String linearOp = "Linear Opmode";
        public static final String teleop = "Basic: Mecanum Teleop";
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

        public static final String outerIntakeMotor = "outerIntake0";
        public static final String intakeServo = "intakeServo2";

        public static final DcMotor.Direction outerInvert = DcMotor.Direction.FORWARD;
        public static final Servo.Direction servoInvert = Servo.Direction.FORWARD;
        public static final DcMotor.ZeroPowerBehavior neutralMode = DcMotor.ZeroPowerBehavior.BRAKE;
    }

    public static final class WinchConstants {

        public static final String rightWinch2 = "rightWinch2";

        public static final DcMotor.Direction invert = DcMotor.Direction.FORWARD;
        public static final DcMotor.ZeroPowerBehavior neutralMode = DcMotor.ZeroPowerBehavior.BRAKE;
    }

    public static final class WristConstants {

        public static final String wristServo = "wrist0";

        public static final Servo.Direction invert = Servo.Direction.REVERSE;

        public static final double homeAngle = 88.11488; //0.2937
        public static final double scoreAngle = 0; //0

    }

    public static final class VisionConstants {

        public static final String webcam = "Webcam 1";

    }

}
