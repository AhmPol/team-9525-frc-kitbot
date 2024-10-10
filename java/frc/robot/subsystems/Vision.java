// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;

public class Vision extends SubsystemBase {
  private UsbCamera camera;
  private CvSource outputStream;
  /** Creates a new Vision. */
  public Vision() {
    camera = CameraServer.startAutomaticCapture();
    camera.setResolution(640, 480);
    camera.setFPS(30);

    outputStream = CameraServer.putVideo("Camera", 640, 480);
  }

  @Override
  public void periodic() {
  }
}
