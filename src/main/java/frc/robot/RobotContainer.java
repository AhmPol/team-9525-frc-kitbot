// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.LaunchNote;
import frc.robot.commands.PrepareLaunch;
import frc.robot.subsystems.CANClimber;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.CANLauncher;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;


// import frc.robot.subsystems.CANDrivetrain;
// import frc.robot.subsystems.CANLauncher;

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

  // The robot's subsystems are defined here.
  private final CANDrivetrain drivetrain = new CANDrivetrain();
  // private final CANDrivetrain drivetrain = new CANDrivetrain();
  private final CANLauncher launcher = new CANLauncher();
  // private final CANLauncher launcher = new CANLauncher();

  private final CANClimber climber = new CANClimber();

  /*
   * The gamepad provided in the KOP shows up like an XBox controller if the mode
   * switch is set to X mode using the
   * switch on the top.
   */
  private final CommandXboxController driverController = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);

  private final Command shootAndSitAuto = Autos.shootAndSitAuto(
      launcher);

  private final Command shootAndDriveDiagonalBackwardAuto = Autos.shootAndDriveDiagonalBackwardAuto(
      drivetrain,
      launcher);

  private final Command shootAndDriveStraightBackwardAuto = Autos.shootAndDriveStraightBackwardAuto(
      drivetrain,
      launcher);
  // A chooser for autonomous commands
  SendableChooser<Command> chooser = new SendableChooser<>();


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    // Add commands to the autonomous command chooser
    chooser.setDefaultOption("Shoot and Sit Auto", shootAndSitAuto);
    chooser.setDefaultOption(
        "Shoot and Drive DiagonalBackward Auto",
        shootAndDriveDiagonalBackwardAuto);
    chooser.setDefaultOption(
        "Shoot and Drive Straight Backward Auto",
        shootAndDriveStraightBackwardAuto);

    SmartDashboard.putData("Auto choices", chooser);
    SmartDashboard.putNumber("Joystick Left X value", driverController.getLeftX());
    SmartDashboard.putNumber("Joystick Left Y value", driverController.getLeftY());
    SmartDashboard.putNumber("Joystick Right X value", driverController.getRightX());
    SmartDashboard.putNumber("Joystick Right Y value", driverController.getRightY());
    

    // Put subsystems to dashboard.
    Shuffleboard.getTab("Drivetrain").add(drivetrain);
    Shuffleboard.getTab("Launcher").add(launcher);
    Shuffleboard.getTab("Climber").add(climber);

    // Starts recording to data log
DataLogManager.start();

// Record both DS control and joystick data
DriverStation.startDataLog(DataLogManager.getLog());

// (alternatively) Record only DS control data
DriverStation.startDataLog(DataLogManager.getLog(), false);
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
    drivetrain.setDefaultCommand(
        new RunCommand(
            () -> drivetrain.arcadeDrive(
                -driverController.getLeftY(),
                -driverController.getRightX()),
            drivetrain));

    // 2 - INTAKE CONFIGURATION
    // Set up a binding to run the intake command while the operator is pressing and
    // holding the
    // left Bumper
    driverController.leftBumper().whileTrue(launcher.getIntakeCommand());

    // 3 - LAUNCH CONFIGURATION
    /*
     * Create an inline sequence to run when the operator presses and holds the A
     * (green) button. Run the PrepareLaunch
     * command for 1 seconds and then run the LaunchNote command
     */
    driverController
        .a()
        .onTrue(
            new PrepareLaunch(launcher)
                .withTimeout(LauncherConstants.kLauncherDelay)
                .andThen(new LaunchNote(launcher))
                .withTimeout(LauncherConstants.kLauncherRunDuration)
                .andThen(() -> launcher.stop())
                .handleInterrupt(() -> launcher.stop()));
        
    // // ******* TRY THIS APPROACH *************
    // Define the command sequence for launching
    // Command launchSequence = new PrepareLaunch(launcher)
    // .withTimeout(LauncherConstants.kLauncherDelay)
    // .andThen(new LaunchNote(launcher))
    // .withTimeout(LauncherConstants.kLauncherRunDuration)
    // .andThen(() -> launcher.stop())
    // .handleInterrupt(() -> launcher.stop());

    // driverController.a().whenPressed(launchSequence);

    // 4 - CLIMB CONFIGURATION
    driverController
        .y()
        .whileTrue(climber.ascend().handleInterrupt(() -> climber.stop()));
    driverController
        .b()
        .whileTrue(climber.descend().handleInterrupt(() -> climber.stop()));

    // 5 - SLOW LAUNCH CONFIGURATION
    // // Set up a binding to change the speed of the intake command when the
    // operator presses the X button
    // driverController
    // .x()
    // .onTrue(
    // new PrepareSlowLaunch(launcher)
    // .withTimeout(LauncherConstants.kLauncherDelay)
    // .andThen(new SlowLaunchNote(launcher))
    // .withTimeout(LauncherConstants.kLauncherRunDuration)
    // .andThen(() -> launcher.stop())
    // .handleInterrupt(() -> launcher.stop())
    // );

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // return Autos.exampleAuto(drivetrain);
    return chooser.getSelected();
  }
}
