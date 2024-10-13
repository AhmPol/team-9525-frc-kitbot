// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants.LauncherConstants;
import frc.robot.commands.Launcher.Launch;
import frc.robot.commands.Launcher.PrepareLaunch;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.NavX;
import edu.wpi.first.math.controller.PIDController;

public class BlueRightAuto extends SequentialCommandGroup {
  private final PIDController turnController;
  
  public BlueRightAuto(Drivetrain drivetrain, Launcher launcher, NavX navX) {
    // PIDController for turning to a specific angle
    turnController = new PIDController(0.02, 0, 0); // Tuned P value, adjust based on robot testing

    // Reset NavX yaw at the start
    navX.reset();

    // Sequence of autonomous actions
    addCommands(
      // 1. Prepare launcher and shoot
      new PrepareLaunch(launcher)
          .withTimeout(LauncherConstants.kLauncherDelay)
          .andThen(new Launch(launcher))
          .withTimeout(LauncherConstants.kLauncherRunDuration)
          .andThen(() -> launcher.stop()),

      // 2. Turn right to 135 degrees
      new RunCommand(() -> {
        double targetAngle = 135; // Adjust to your desired angle
        double currentAngle = navX.getYaw();
        double turnSpeed = turnController.calculate(currentAngle, targetAngle);
        drivetrain.arcadeDrive(0, turnSpeed);
      }, drivetrain)
      .withTimeout(2.0)
      .andThen(() -> drivetrain.arcadeDrive(0, 0)), // Stop turning

      // 3. Drive forward for 3 seconds
      new RunCommand(() -> drivetrain.arcadeDrive(0.5, 0), drivetrain)
          .withTimeout(3.0)
          .andThen(() -> drivetrain.stop()) // Stop drivetrain
    );
  }
}
