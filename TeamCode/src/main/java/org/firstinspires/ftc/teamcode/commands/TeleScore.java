package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.ArmConfig;
import org.firstinspires.ftc.teamcode.subsystems.Winch;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class TeleScore extends CommandBase {

    ArmConfig scoreConfig;
    Winch winch;
    Wrist wrist;
    int phase;
    Telemetry t;

    public TeleScore(Winch winch, Wrist wrist, ArmConfig scoreConfig) {
        this.winch = winch;
        this.wrist = wrist;
        this.scoreConfig = scoreConfig;
    }

    public TeleScore(Winch winch, Wrist wrist, Constants.WinchLevel level, Telemetry t) {
        this.winch = winch;
        this.wrist = wrist;
        this.t = t;

        setLevel(level);
    }

    @Override
    public void initialize() {
        if (firstRun) phase = 0;
        super.initialize();
    }

    @Override
    public void execute() {
        t.addLine("phase " + phase);
        switch (phase) {
            case 0:
                wrist.setAngle(Constants.WristConstants.homeAngle);
                phase++;
                break;
            case 1:
                winch.setSetpoint(scoreConfig.getArmExtension());
                phase++;
                break;
            case 2:
                winch.setPower(winch.getSetpointCalc());
                phase += winch.atSetpoint() ? 1 : 0;
                break;
            case 3:
                wrist.setAngle(Constants.ScoringConstants.scoreAngle);
                phase++;
                break;
        }

        if (onEnd()) {
            firstRun = false;
        }
    }

    @Override
    public boolean onEnd() {
        return (winch.atSetpoint() && phase == 4);
    }


    public void setLevel(Constants.WinchLevel level) {
        switch (level) {
            case HIGH:
                scoreConfig = Constants.ScoringConstants.scoreHigh;
                break;
            case MID:
                scoreConfig = Constants.ScoringConstants.scoreMid;
                break;
            case LOW:
                scoreConfig = Constants.ScoringConstants.scoreLow;
                break;
        }
    }

    public void isFirstRun(boolean yes) {
        firstRun = yes;
    }
}