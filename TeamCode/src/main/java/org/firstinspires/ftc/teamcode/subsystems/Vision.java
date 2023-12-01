package org.firstinspires.ftc.teamcode.subsystems;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import static org.firstinspires.ftc.teamcode.Constants.VisionConstants;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Pose2d;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.ArrayList;
import java.util.List;

public class Vision {

    WebcamName scoreCamera;
    WebcamName intakeCamera;

    AprilTagProcessor tagProcessor;
    TfodProcessor pieceProcessor;

    AprilTagLibrary tagLib;

    VisionPortal visionPortal;

    public Vision(HardwareMap hardwareMap) {
        scoreCamera = hardwareMap.get(WebcamName.class, VisionConstants.scoreCam);
        intakeCamera = hardwareMap.get(WebcamName.class, VisionConstants.intakeCam);

        tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setTagLibrary(AprilTagGameDatabase.getCurrentGameTagLibrary())
                .build();

        pieceProcessor = new TfodProcessor.Builder()
                .setMaxNumRecognitions(5)
                .setUseObjectTracker(true)
                .setTrackerMaxOverlap((float) 0.2)
                .setTrackerMinSize(16)
                .setModelAssetName(VisionConstants.MODEL_ASSET)
                .setModelLabels(VisionConstants.LABELS)
                //.setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)
                .build();

        visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .addProcessor(pieceProcessor)
                .setCamera(scoreCamera)
                .setCameraResolution(new Size(640, 480))
                .enableLiveView(true)
                .build();

    }

    public boolean hasTag() {
        return tagProcessor.getDetections().size() > 0;
    }

    public ArrayList<AprilTagDetection> getTags() {
        return hasTag() ? tagProcessor.getDetections() : new ArrayList<>();
    }

    public boolean hasTagID(int tagID) {

        for (AprilTagDetection tag : getTags()) {
            if (tag.id == tagID) {
                return true;
            }
        }

        return false;
    }

    public AprilTagPoseFtc getPose() {
        return getTags().get(0).ftcPose;
    }

    public void togglePiece(boolean enable) {
        visionPortal.setProcessorEnabled(pieceProcessor, enable);
    }

    public void toggleTag(boolean enable) {
        visionPortal.setProcessorEnabled(tagProcessor, enable);
    }

    public boolean pieceEnabled() {
        return visionPortal.getProcessorEnabled(pieceProcessor);
    }

    public boolean tagEnabled() {
        return visionPortal.getProcessorEnabled(tagProcessor);
    }

    public boolean hasPiece() {
        return pieceProcessor.getRecognitions().size() > 0;
    }

    public List<Recognition> getPieces() {
        return pieceProcessor.getRecognitions();
    }

    public Recognition getBestPiece() {
        Recognition bestPiece = pieceProcessor.getRecognitions().get(0);

        for (Recognition p : getPieces()) {
            bestPiece = p.getConfidence() > bestPiece.getConfidence() ? p : bestPiece;
        }

        return bestPiece;
    }

    public double getPieceAngle() {
        return getBestPiece().estimateAngleToObject(AngleUnit.DEGREES);
    }

    public Pose2d getLocation() {
        AprilTagPoseFtc ftcPose = getPose();

        double y = ftcPose.y;
        double x = ftcPose.x;
        double r = ftcPose.yaw;

        return new Pose2d(x, y, r);
    }

    public void updatePieceDetection() {
        pieceProcessor.getFreshRecognitions();
    }

    public void periodic(Telemetry telemetry) {
        telemetry.addLine("Vision:");
        telemetry.addLine("Tag Vision Enabled: " + tagEnabled());
        telemetry.addLine("Has Tag: " + hasTag());
        telemetry.addLine("Tag ID: " + (hasTag() ? getTags().get(0) : "N/A"));
        telemetry.addLine("Tag Pose: " + (hasTag() ? getPose().toString() : "N/A"));

        telemetry.addLine("----------------");
        telemetry.addLine("Piece Vision Enabled: " + pieceEnabled());
        telemetry.addLine("Has Piece: " + hasPiece());
        telemetry.addLine("Piece Angle: " + (hasPiece() ? getPieceAngle() : "N/A"));
        telemetry.addLine("Piece Confidence: " + getBestPiece().getConfidence());

        updatePieceDetection();
    }
}