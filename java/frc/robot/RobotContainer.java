// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.Drive;
import frc.robot.commands.SlowDrive;
import frc.robot.commands.Climber.Ascend;
import frc.robot.commands.Climber.Descend;
import frc.robot.commands.Launcher.Intake;
import frc.robot.commands.Launcher.Launch;
import frc.robot.commands.Launcher.PrepareLaunch;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Launcher;
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
  // private final CANDrivetrain drivetrain = new CANDrivetrain();
  private final Launcher launcher = new Launcher();
  // private final CANLauncher launcher = new CANLauncher();

  private final Climber climber = new Climber();

  private final Vision vision = new Vision();

  /*
   * The gamepad provided in the KOP shows up like an XBox controller if the mode
   * switch is set to X mode using the
   * switch on the top.
   */
  private final CommandXboxController driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final Command shootAndSitAuto = Autos.shootAndSitAuto(launcher);
  private final Command shootAndDriveDiagonalBackwardAuto = Autos.shootAndDriveDiagonalBackwardAuto(drivetrain, launcher);
  private final Command shootAndDriveStraightBackwardAuto = Autos.shootAndDriveStraightBackwardAuto(drivetrain, launcher);
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
    chooser.setDefaultOption("Shoot and Sit Auto", shootAndSitAuto);
    chooser.setDefaultOption("Shoot and Drive DiagonalBackward Auto", shootAndDriveDiagonalBackwardAuto);
    chooser.setDefaultOption("Shoot and Drive Straight Backward Auto", shootAndDriveStraightBackwardAuto);

    SmartDashboard.putData("Auto choices", chooser);

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

    // 4 - CLIMB CONFIGURATION
    driverController.y().whileTrue(new Ascend(climber));
    driverController.b().whileTrue(new Descend(climber));
    
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