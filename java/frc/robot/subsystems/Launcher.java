// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LauncherConstants;

public class Launcher extends SubsystemBase {
  private final CANSparkMax leftLaunchWheel;
  private final CANSparkMax leftFeedWheel;
  private final CANSparkMax rightLaunchWheel;
  private final CANSparkMax rightFeedWheel;
  private final RelativeEncoder leftRelativeEncoder;
  private final RelativeEncoder rightRelativeEncoder;

  /** Creates a new Launcher. */
  public Launcher() {
    leftLaunchWheel = new CANSparkMax(LauncherConstants.kLeftLauncherID, MotorType.kBrushless);
    leftFeedWheel = new CANSparkMax(LauncherConstants.kLeftFeederID, MotorType.kBrushless);
    rightLaunchWheel = new CANSparkMax(LauncherConstants.kRightLauncherID, MotorType.kBrushless);
    rightFeedWheel = new CANSparkMax(LauncherConstants.kRightFeederID, MotorType.kBrushless);

    leftLaunchWheel.setSmartCurrentLimit(LauncherConstants.kLauncherCurrentLimit);
    leftFeedWheel.setSmartCurrentLimit(LauncherConstants.kFeedCurrentLimit);
    rightLaunchWheel.setSmartCurrentLimit(LauncherConstants.kLauncherCurrentLimit);
    rightFeedWheel.setSmartCurrentLimit(LauncherConstants.kFeedCurrentLimit);

    leftRelativeEncoder = leftLaunchWheel.getEncoder();
    rightRelativeEncoder = rightLaunchWheel.getEncoder();
  }

  /**
   * This method is an example of the 'subsystem factory' style of command creation. A method inside
   * the subsytem is created to return an instance of a command. This works for commands that
   * operate on only that subsystem, a similar approach can be done in RobotContainer for commands
   * that need to span subsystems. The Subsystem class has helper methods, such as the startEnd
   * method used here, to create these commands.
   */

  // An accessor method to set the speed (technically the output percentage) of the launch wheel
  public void setLaunchWheel(double speed) {
    leftLaunchWheel.set(speed);
    rightLaunchWheel.set(speed);
  }

  // An accessor method to set the speed (technically the output percentage) of the feed wheel
  public void setFeedWheel(double speed) {
    leftFeedWheel.set(-speed);
    rightFeedWheel.set(speed);
  }

  public double getLeftLaunchPosition(){
    return leftRelativeEncoder.getPosition();
  }

  public double getRightLaunchPosition() {
    return rightRelativeEncoder.getPosition();
  }

  // A helper method to stop both wheels. You could skip having a method like this and call the
  // individual accessors with speed = 0 instead
  public void stop() {
    leftFeedWheel.set(0);
    leftLaunchWheel.set(0);
    rightFeedWheel.set(0);
    rightLaunchWheel.set(0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Left Launcher", getLeftLaunchPosition());
    SmartDashboard.putNumber("Right Launcher", getRightLaunchPosition());
  }
}