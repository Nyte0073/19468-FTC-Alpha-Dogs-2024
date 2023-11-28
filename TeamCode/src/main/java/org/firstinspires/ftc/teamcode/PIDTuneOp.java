package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Mecanum;
import org.firstinspires.ftc.teamcode.subsystems.Winch;

@TeleOp(name = Constants.OpModes.pidOp, group = Constants.OpModes.linearOp)
public class PIDTuneOp extends LinearOpMode {

    subsystems[] options = {subsystems.MECANUMY, subsystems.MECANUMX, subsystems.MECANUMR, subsystems.WINCH};
    int currentOption = 0;

    PID[] pid = {PID.P, PID.I, PID.D};
    int currentPID = 0;

    double p = 0;
    double i = 0;
    double d = 0;

    double interval = 0.025;

    double setPoint = 0;
    double measurement = 0;
    boolean isRunning = false;

    @Override
    public void runOpMode() throws InterruptedException {

        while (opModeIsActive()) {

            //Subsystem Selector
            if (gamepad1.left_bumper) {
                currentOption = (int) Utilities.clip(currentOption - 1, options.length, 0);
            } else if (gamepad1.right_bumper) {
                currentOption = (int) Utilities.clip(currentOption + 1, options.length, 0);
            }

            //PID Selector
            if (gamepad1.dpad_left) {
                currentPID = (int) Utilities.clip(currentPID - 1, pid.length, 0);
            } else if (gamepad1.dpad_right) {
                currentPID = (int) Utilities.clip(currentPID + 1, pid.length, 0);
            }

            //PID Selector
            if (gamepad1.dpad_up) {

                switch (pid[currentPID]) {
                    case P:

                    break;
                    case I:

                    break;
                    case D:

                    break;
                }

            } else if (gamepad1.dpad_right) {
                currentPID = (int) Utilities.clip(currentPID + 1, pid.length, 0);
            }

            if (gamepad1.a) {
                PIDFController pid = new PIDFController(new PIDCoefficients(p, i, d), 0);

                isRunning = true;

                Mecanum drive = new Mecanum(hardwareMap);
                Winch winch = new Winch(hardwareMap);

                switch (options[currentOption]) {
                    case MECANUMY:
                        pid.setTargetPosition(15);
                        while (Utilities.withinBounds(drive.getY(), pid.getTargetPosition(), 0.5)) {
                            measurement = drive.getY();
                            drive.drive(pid.update(drive.getY()), 0, 0, true);
                            periodic();
                        }
                        break;
                    case MECANUMX:
                        pid.setTargetPosition(15);
                        while (Utilities.withinBounds(drive.getX(), pid.getTargetPosition(), 0.5)) {
                            measurement = drive.getX();
                            drive.drive(0, pid.update(drive.getX()), 0, true);
                            periodic();
                        }
                        break;
                    case MECANUMR:
                        pid.setTargetPosition(30);
                        while (Utilities.withinBounds(drive.getYaw(), pid.getTargetPosition(), 1.5)) {
                            measurement = drive.getYaw();
                            drive.drive(0, 0, pid.update(drive.getYaw()), true);
                            periodic();
                        }
                        break;
                    case WINCH:
                        pid.setTargetPosition(15);
                        while (Utilities.withinBounds(winch.getPosition(), pid.getTargetPosition(), 1)) {
                            measurement = winch.getPosition();
                            winch.setPower(pid.update(winch.getPosition()));
                            periodic();
                        }
                        break;
                }

            }

            isRunning = false;

            periodic();
        }

    }

    public void periodic() {
        telemetry.addData("Current Subsystem: ", options[currentOption]);
        telemetry.addData("Current Edit: ", pid[currentPID]);
        telemetry.addData("P: ", p);
        telemetry.addData("I ", i);
        telemetry.addData("D ", d);
        telemetry.addData("Setpoint ", setPoint);
        telemetry.addData("Measurement", measurement);
        telemetry.addData("Running ", isRunning);
    }

    enum subsystems {
        MECANUMY, MECANUMX, MECANUMR, WINCH;
    }

    enum PID {
        P, I, D;
    }
}
