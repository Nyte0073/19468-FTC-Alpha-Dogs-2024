package org.firstinspires.ftc.teamcode.commands;

public abstract class CommandBase {

    protected boolean firstRun = true;


    protected void initialize() {
        if (!firstRun) {
            return;
        } else {
            firstRun = false;
        }
    }

    protected abstract void execute();

    protected boolean onEnd() {
        return true;
    }

    protected boolean runCommand() {
        while (!onEnd()) {
            initialize();
            execute();
        }

        return onEnd();
    }
}
