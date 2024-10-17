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

public class BlueLeftAuto extends SequentialCommandGroup {

  public BlueLeftAuto(Drivetrain drivetrain, Launcher launcher) {
    
    addCommands(
      new PrepareLaunch(launcher)
          .withTimeout(LauncherConstants.kLauncherDelay)
          .andThen(new Launch(launcher))
          .withTimeout(LauncherConstants.kLauncherRunDuration)
          .andThen(() -> launcher.stop())
          .handleInterrupt(() -> launcher.stop()),

      // Step 1: Drive forward while turning left for 2 seconds
      new RunCommand(() -> drivetrain.arcadeDrive(0.5, 0), drivetrain) // Drive forward
          .withTimeout(2.0)
          .andThen(() -> drivetrain.stop()) // Stop drivetrain after driving forward
          .handleInterrupt(() -> drivetrain.stop()),

      // Step 2: Turn left for 1.07 seconds
      new RunCommand(() -> drivetrain.arcadeDrive(0.0, -0.5), drivetrain) // Turn left
          .withTimeout(1.07)
          .andThen(() -> drivetrain.stop()) // Stop drivetrain after turning
          .handleInterrupt(() -> drivetrain.stop()),

      // Step 3: Drive backward for 6 seconds
      new RunCommand(() -> drivetrain.arcadeDrive(-0.5, 0), drivetrain) // Drive backward
          .withTimeout(3.0)
          .andThen(() -> drivetrain.stop()) // Stop drivetrain after driving backward
          .handleInterrupt(() -> drivetrain.stop())
    );
  }
}
