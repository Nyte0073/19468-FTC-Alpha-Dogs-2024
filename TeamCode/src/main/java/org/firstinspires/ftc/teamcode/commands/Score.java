package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Utilities;
import org.firstinspires.ftc.teamcode.subsystems.ArmConfig;
import org.firstinspires.ftc.teamcode.subsystems.Winch;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

public class Score extends CommandBase {

    ArmConfig scoreConfig;
    Winch winch;
    Wrist wrist;
    int phase;
    Gamepad gp;
    boolean buttonInterupt;

    public Score(Winch winch, Wrist wrist, ArmConfig scoreConfig, Gamepad gp, boolean buttonInterupt) {
        this.winch = winch;
        this.wrist = wrist;
        this.scoreConfig = scoreConfig;
        this.buttonInterupt = buttonInterupt;
        this.gp = gp;
    }

    public Score(Winch winch, Wrist wrist, Constants.WinchLevel level, Gamepad gp, boolean buttonInterupt) {
        this.winch = winch;
        this.wrist = wrist;

        setLevel(level);

        this.buttonInterupt = buttonInterupt;
        this.gp = gp;
    }

    @Override
    public void initialize() {
        super.initialize();

        phase = 0;
    }

    @Override
    public void execute() {
            switch (phase) {
                case 0:
                    wrist.setAngle(Constants.WristConstants.midAngle);
                    phase++;
                    break;
                case 1:
                    winch.setSetpoint(scoreConfig.getArmExtension());
                    phase += winch.atSetpoint() ? 1 : 0;
                    break;
                case 2:
                    wrist.setAngle(scoreConfig.getWristAngle());
                    phase++;
                    break;
            }

            if (onEnd()) {
                firstRun = false;
            }
    }

    @Override
    public boolean onEnd() {
        return (winch.atSetpoint() && phase == 2) || (buttonInterupt && !gp.b);
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
}
