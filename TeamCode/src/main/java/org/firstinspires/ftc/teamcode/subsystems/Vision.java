package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Constants.VisionConstants;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

public class Vision {

    private HuskyLens huskyLens;
    private Deadline rateLimit;


    public Vision(HardwareMap hardwareMap) {
        huskyLens = hardwareMap.get(HuskyLens.class, VisionConstants.huskyLens);
        rateLimit = new Deadline(VisionConstants.readPeriod, TimeUnit.SECONDS);
        rateLimit.expire();

        huskyLens.selectAlgorithm(HuskyLens.Algorithm.OBJECT_TRACKING);
    }

    public HuskyLens.Block[] getBlocks() {
        return huskyLens.blocks();
    }

    public double getX() {
        return hasBlock() ? getBlocks()[0].x : 0;
    }

    public double getY() {
        return hasBlock() ? getBlocks()[0].y : 0;
    }

    public int getID() {
        return hasBlock() ? getBlocks()[0].id : 0;
    }

    public boolean hasBlock() {
        return getBlocks().length > 0;
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addData("Block count", getBlocks().length);
        telemetry.addData("Block ID", getID());
        telemetry.addData("Block X", getX());
        telemetry.addData("Block Y", getY());
    }


}