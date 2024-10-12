// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants.LauncherConstants;
import frc.robot.commands.Launcher.Launch;
import frc.robot.commands.Launcher.PrepareLaunch;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.NavX;

public class RedLeftAuto extends Command {
    private final Drivetrain drivetrain;
    private final Launcher launcher;
    private final NavX navX;

    /** Creates a new RedLeftAuto. */
    public RedLeftAuto(Drivetrain drivetrain, Launcher launcher, NavX navX) {
        this.drivetrain = drivetrain;
        this.launcher = launcher;
        this.navX = navX;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(drivetrain, launcher);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
        // Prepare the launcher
        new PrepareLaunch(launcher)
            .withTimeout(LauncherConstants.kLauncherDelay)
            .andThen(new Launch(launcher))
            .withTimeout(LauncherConstants.kLauncherRunDuration)
            .andThen(() -> launcher.stop())
            .handleInterrupt(() -> launcher.stop())
            .schedule();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
        // Go backward a little
        new RunCommand(() -> drivetrain.arcadeDrive(-0.5, 0), drivetrain)
            .withTimeout(1.0) // Adjust time based on testing
            .schedule();

        // Turn to a specified angle
        new RunCommand(() -> {
            double targetAngle = -135; // Adjust to your desired angle
            double currentAngle = navX.getAngle();
            double turnSpeed = 0.5; // Speed for turning
            double angleError = targetAngle - currentAngle;

            // If we're still not at the target angle, continue turning
            if (Math.abs(angleError) > 2.0) {
                drivetrain.arcadeDrive(0, turnSpeed * Math.signum(angleError)); // Positive or negative based on error
            } else {
                drivetrain.arcadeDrive(0, 0); // Stop turning once angle is close
            }
        }, drivetrain)
        .withTimeout(1.0) // Adjust time based on testing
        .schedule();

        // Drive forward between game pieces
        new RunCommand(() -> drivetrain.arcadeDrive(0.5, 0), drivetrain)
            .withTimeout(3.0) // Adjust time based on testing
            .schedule();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
