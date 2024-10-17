// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.kauailabs.navx.frc.AHRS;

public class NavX extends SubsystemBase {
  /** Creates a new NavX. */
  private final AHRS navx;
  public NavX() {
    navx = new AHRS(SPI.Port.kMXP);

    System.out.println("NavX initialized. Is Connected: " + navx.isConnected());
    if (!navx.isConnected()) {
      System.out.println("WARNING: NavX not connected!");
    }
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
    return navx.getAngle(); // the total accumulated angle from the NavX (since last reset)
  }

  public void reset() {
    navx.reset(); // Resets the accumulated angle (yaw, pitch, roll) to zero.
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("NavX Connected", isConnected());
    SmartDashboard.putNumber("NavX Angle", getAngle());
    SmartDashboard.putNumber("NavX Yaw", getYaw());

    System.out.println("Yaw: " + getYaw());
    System.out.println("Pitch: " + getPitch());
    System.out.println("Roll: " + getRoll());
    System.out.println("Angle: " + getAngle());
    System.out.println("NavX Connected: " + isConnected());
  }
}
