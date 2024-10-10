// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Vision extends SubsystemBase {
  private UsbCamera camera;
  /** Creates a new Vision. */
  public Vision() {
    camera = CameraServer.startAutomaticCapture();
    
  }

  @Override
  public void periodic() {
    SmartDashboard.putData("Camera", (Sendable) camera);
  }
}
