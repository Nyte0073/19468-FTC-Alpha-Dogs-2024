// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.crte.phoenix6.hardware.TalonFX;
import src.frc.robot.util.Constants;

public class Claw extends SubsystemBase {
  /** Creates a new Claw. */
private TalonFX Claw;


public Claw(int speed) {
    Claw = new TalonFX(0);
  }

public void start(int speed) {
    Claw.set(speed);
  }

public void stop(int speed) {
    Claw.set(speed: 0);
  }

public void getSpeed(int speed) {
    return speed;
  }

public void setClawPosition(double Pos) {
    Claw.setPosition(Pos);
  } 

public void getPosition(Constants.ClawConstants.clawAngle1, double Angle) {
    double clawPosition = Angle;
  }

public void getAngle(Constants.ClawConstants.clawAngle1, double Angle) {
     double clawAngle = clawAngle1 * Angle / 350; 
  }
  
@Override
public void periodic() {
  public double getClawAngle(double clawAngle) {
      return clawAngle;
    }
  public double getClawPosition(clawPosition) {
      return clawAngle;
    }
}
}
