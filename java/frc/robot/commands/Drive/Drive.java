// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Drive extends Command {
  private Drivetrain drivetrain;
  private CommandXboxController driverController;
  /** Creates a new DeafultDrive. */
  public Drive(Drivetrain drivetrain, CommandXboxController driverController) {
    
    this.drivetrain = drivetrain;
    this.driverController = driverController;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled. Here it resets the robot back to zero acceleration and speed for a clean start.
  @Override
  public void initialize() {
    drivetrain.stop();
  }

  // Called every time the scheduler runs while the command is scheduled. Uses arcade drive
  @Override
  public void execute() { 
    drivetrain.arcadeDrive(driverController.getLeftY(), driverController.getRightX());
  }


  // Called once the command ends or is interrupted
  // If the code is interrupted, then motors stop.
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
