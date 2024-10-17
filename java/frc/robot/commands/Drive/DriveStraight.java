// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.NavXConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NavX;

public class DriveStraight extends Command {
  private final Drivetrain drivetrain;
  private final NavX navX;
  /** Creates a new DriveStraight. */
  public DriveStraight(Drivetrain drivetrain, NavX navX) {
    this.drivetrain = drivetrain;
    this.navX = navX;

    addRequirements(drivetrain);

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double turnAdjustment = navX.getAngle() * NavXConstants.kCorrectionFactor;

    drivetrain.arcadeDrive(NavXConstants.kSpeed, -turnAdjustment);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
