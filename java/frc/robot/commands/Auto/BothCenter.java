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


public class BothCenter extends SequentialCommandGroup {

  public BothCenter(Drivetrain drivetrain, Launcher launcher) {

    addCommands(
      new PrepareLaunch(launcher)
            .withTimeout(LauncherConstants.kLauncherDelay)
            .andThen(new Launch(launcher))
            .withTimeout(LauncherConstants.kLauncherRunDuration)
            .andThen(() -> launcher.stop())
            .handleInterrupt(() -> launcher.stop()),

        new RunCommand(() -> drivetrain.arcadeDrive(0.5, 0), drivetrain) // Turn left
            .withTimeout(2.0), // Adjust time based on testing
        
        // Turn for a specified duration (adjust the angle as needed)
        new RunCommand(() -> drivetrain.arcadeDrive(0.0, 0.5), drivetrain) // Turn left
            .withTimeout(1.43), // Adjust time based on testing
        
        new RunCommand(() -> drivetrain.arcadeDrive(-0.5, 0), drivetrain) // Drive backward
            .withTimeout(6.0) // Adjust time based on testing
      );
  }
} 
