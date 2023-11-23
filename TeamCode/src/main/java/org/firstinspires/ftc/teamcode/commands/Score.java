package org.firstinspires.ftc.teamcode.commands;

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

    public Score(Winch winch, Wrist wrist, ArmConfig scoreConfig) {
        this.winch = winch;
        this.wrist = wrist;
        this.scoreConfig = scoreConfig;
    }

    @Override
    public void initialize() {
        super.initialize();

        phase = 0;
    }

    @Override
    public void execute() {
        while (!onEnd()) {
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
        }

        return;
    }

    @Override
    public boolean onEnd() {
        return winch.atSetpoint() && phase == 2;
    }

}
