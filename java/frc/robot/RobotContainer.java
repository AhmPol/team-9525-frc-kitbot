// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Auto.BlueLeftAuto;
import frc.robot.commands.Auto.BlueRightAuto;
import frc.robot.commands.Auto.BothCenter;
import frc.robot.commands.Auto.RedLeftAuto;
import frc.robot.commands.Auto.RedRightAuto;
import frc.robot.commands.Climber.Ascend;
import frc.robot.commands.Climber.Descend;
import frc.robot.commands.Drive.Drive;
import frc.robot.commands.Drive.DriveBackward;
import frc.robot.commands.Drive.DriveStraight;
import frc.robot.commands.Drive.SlowDrive;
import frc.robot.commands.Launcher.Intake;
import frc.robot.commands.Launcher.Launch;
import frc.robot.commands.Launcher.PrepareLaunch;
import frc.robot.commands.Launcher.SlowLaunch;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.NavX;
import frc.robot.subsystems.Vision;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // the robot's subsystems are defined here.
  private final Drivetrain drivetrain = new Drivetrain();
  private final Launcher launcher = new Launcher();
  private final Climber climber = new Climber();
  private final NavX navX = new NavX();
  private final Vision vision = new Vision();

  /*
   * The gamepad provided in the KOP shows up like an XBox controller if the mode
   * switch is set to X mode using the
   * switch on the top.
   */
  private final CommandXboxController driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final Command RedRightAuto = new RedRightAuto(drivetrain, launcher, navX); 
  private final Command RedLeftAuto = new RedLeftAuto(drivetrain, launcher, navX);
  private final Command BlueRightAuto = new BlueRightAuto(drivetrain, launcher, navX);
  private final Command BlueLeftAuto = new BlueLeftAuto(drivetrain, launcher, navX); 
  private final Command BothCenter = new BothCenter(drivetrain, launcher, navX); 

  // A chooser for autonomous commands
  SendableChooser<Command> chooser = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    DriverStation.silenceJoystickConnectionWarning(true);
    // Configure the trigger bindings
    configureBindings();

    // Add commands to the autonomous command chooser
      chooser.setDefaultOption("RedRightAuto", RedRightAuto);
      chooser.addOption("RedLeftAuto", RedLeftAuto);
      chooser.addOption("BothCenter", BothCenter);
      chooser.setDefaultOption("BlueLeftAuto", BlueLeftAuto); 
      chooser.addOption("BlueRightAuto", BlueRightAuto);
      chooser.addOption("BothCenter", BothCenter);

    SmartDashboard.putData("Auto choices", chooser);
    SmartDashboard.putNumber("Match Time", DriverStation.getMatchTime());

    // Put subsystems to dashboard.
    Shuffleboard.getTab("Drivetrain").add(drivetrain);
    Shuffleboard.getTab("Launcher").add(launcher);
    Shuffleboard.getTab("Climber").add(climber);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * accessed via the
   * named factory methods in the Command* classes in
   * edu.wpi.first.wpilibj2.command.button (shown
   * below) or via the Trigger constructor for arbitary conditions
   */
  private void configureBindings() {
    // 1 - DRIVETRAIN CONTROLS
    // Set the default command for the drivetrain to drive using the joysticks
    drivetrain.setDefaultCommand(new Drive(drivetrain, driverController));
    driverController.rightBumper().toggleOnTrue(new SlowDrive(drivetrain, driverController));

    // 2 - INTAKE CONFIGURATION
    // Set up a binding to run the intake command while the operator is pressing and
    // holding the
    // left Bumper
    driverController.leftBumper().whileTrue(new Intake(launcher));

    // 3 - LAUNCH CONFIGURATION
    /*
     * Create an inline sequence to run when the operator presses and holds the A
     * (green) button. Run the PrepareLaunch
     * command for 1 seconds and then run the LaunchNote command
     */
    driverController
        .a()
        .whileTrue(
            new PrepareLaunch(launcher)
                .withTimeout(LauncherConstants.kLauncherDelay)
                .andThen(new Launch(launcher))
                .withTimeout(LauncherConstants.kLauncherRunDuration)
                .andThen(() -> launcher.stop())
                .handleInterrupt(() -> launcher.stop()));

    driverController
        .x()
        .whileTrue(
            new PrepareLaunch(launcher)
                .withTimeout(LauncherConstants.kLauncherDelay)
                .andThen(new SlowLaunch(launcher))
                .withTimeout(LauncherConstants.kLauncherRunDuration)
                .andThen(() -> launcher.stop())
                .handleInterrupt(() -> launcher.stop()));

    // 4 - CLIMB CONFIGURATION
    driverController.y().whileTrue(new Ascend(climber));
    driverController.b().whileTrue(new Descend(climber));

    driverController.povUp().whileTrue(new DriveStraight(drivetrain, navX));
    driverController.povDown().whileTrue(new DriveBackward(drivetrain, navX));
    
  }
  /**
  * Use this to pass the autonomous command to the main {@link Robot} class.
  *
  * @return the command to run in autonomous
  */
  public Command getAutonomousCommand() {
    return chooser.getSelected();
  }
}