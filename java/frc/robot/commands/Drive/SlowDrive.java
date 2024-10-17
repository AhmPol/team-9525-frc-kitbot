// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.subsystems.Drivetrain;

public class SlowDrive extends Command {
  private Drivetrain drivetrain;
  private CommandXboxController driverController;
  /** Creates a new SlowDrive. */
  
  public SlowDrive(Drivetrain drivetrain, CommandXboxController driverController) {
    this.drivetrain = drivetrain;
    this.driverController = driverController;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
 // Resets the drivetrain's speed and acceleration back to zero. 
  @Override
  public void initialize() {
    drivetrain.stop();
  }

  // Called every time the scheduler runs while the command is scheduled.
  // Uses ArcadeDrive multiplied by SlowSpeed in order to get a slower speed.
  @Override
  public void execute() {
    drivetrain.arcadeDrive(driverController.getLeftY() * DrivetrainConstants.kSlowDriveSpeed, driverController.getRightX() * DrivetrainConstants.kSlowDriveSpeed);
    
  }

  // Called once the command ends or is interrupted. Stops the drivetrain completely.
  // Stops the drivetrain completely.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
