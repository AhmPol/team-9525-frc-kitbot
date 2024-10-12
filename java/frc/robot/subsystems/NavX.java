// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SPI;

import com.kauailabs.navx.frc.AHRS;

public class NavX extends SubsystemBase {
  /** Creates a new NavX. */
  private final AHRS navx;
  public NavX() {
    navx = new AHRS(SPI.Port.kMXP);
  }

  public double getYaw() {
    return navx.getYaw(); // Returns the current yaw angle
  }

  public double getPitch() {
    return navx.getPitch(); // Returns the current pitch angle
  }

  public double getRoll() {
    return navx.getRoll(); // Returns the current roll angle
  }

  public boolean isConnected() {
    return navx.isConnected(); // Check if the NavX is connected
  }
  
  public double getAngle() {
    return navx.getAngle();
  }

  @Override
  public void periodic() {
  }
}
