// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {
  private CANSparkMax climbMotor;
  public DigitalInput bottomSwitch;
  public DigitalInput topSwitch;
  public double currentHeight;

  /** Creates a new Climber. */
  public Climber() {
    climbMotor = new CANSparkMax(ClimberConstants.kclimberID, MotorType.kBrushed);
    currentHeight = ClimberConstants.kCurrentHeight; // Initialize the current height
    topSwitch = new DigitalInput(ClimberConstants.kTopSwitchID); // Tells the climber to stop ascending
    bottomSwitch = new DigitalInput(ClimberConstants.kBottomSwitchID); // Tells the climber to stop descending
  }

  /**
   * An accessor method to set the speed (technically the output percentage) of
   * the climb motor
   */
  public void setClimbSpeed(double speed) {
    // Stop the motor if it reaches the top or bottom switch
    if ((speed > 0 && topSwitch.get()) || (speed < 0 && bottomSwitch.get())) {
      climbMotor.set(0);
    } else {
      climbMotor.set(speed);
    }
  }

  // A helper method to stop climb motor. You could skip having a method like this
  // and call the
  // individual accessors with speed = 0 instead
  public void stop() {
    climbMotor.set(0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Switch Top", !this.topSwitch.get());
    SmartDashboard.putBoolean("Switch Bottom", !this.bottomSwitch.get());
    SmartDashboard.putNumber("Climber Motor Current", climbMotor.getOutputCurrent());
    SmartDashboard.putNumber("Climber Motor Voltage", climbMotor.getBusVoltage());
  }
}
