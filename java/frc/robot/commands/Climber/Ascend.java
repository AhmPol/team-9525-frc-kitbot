// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ClimberConstants;
import frc.robot.subsystems.Climber;

public class Ascend extends Command {
  private Climber climber;
  /** Creates a new Ascend. */
  public Ascend(Climber climber) {
    this.climber = climber;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(climber);
  }

  // Called when the command is initially scheduled. 
  // The moment command is called climber starts ascending
  @Override
  public void initialize() {
    climber.setClimbSpeed(ClimberConstants.kclimberAscendSpeed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  // Execute is not needed as the climber should only work until a condition is achieved therefore no need for execute.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  // This occurs after the topSwitch has been achieved and returns true. Which stops the command.
  @Override
  public boolean isFinished() {
    return climber.topSwitch.get();
  }
  
}
