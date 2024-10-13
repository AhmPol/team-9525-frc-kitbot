// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    // Port numbers for driver and operator gamepads. These correspond with the numbers on the USB
    // tab of the DriverStation 
    public static final int kDriverControllerPort = 0;
    public static final int kOperatorControllerPort = 0;
  }

  public static class DrivetrainConstants {
    // PWM ports/CAN IDs for motor controllers
    public static final int kLeftRearID = 2;
    public static final int kLeftFrontID = 1;
    public static final int kRightRearID = 3;
    public static final int kRightFrontID = 4;

    // Current limit for drivetrain motors
    public static final int kCurrentLimit = 60;

    //Slew rate limit for the motors
    public static final double kSlewRateLimit = 3.0;

    //Slow drive Speed for Robot
    public static final double kSlowDriveSpeed = 0.6;
  }

  public static class LauncherConstants {
    // PWM ports/CAN IDs for motor controllers
    public static final int kLeftFeederID = 6;
    public static final int kLeftLauncherID = 5;
    public static final int kRightFeederID = 7;
    public static final int kRightLauncherID = 8;

    // Current limit for launcher and feed wheels
    public static final int kLauncherCurrentLimit = 80;   
    public static final int kFeedCurrentLimit = 80;

    // Speeds for wheels when intaking and launching. Intake speeds are negative to run the wheels
    // in reverse
    
    // Launcher Top 7 is the top motor. 
    // Launches with Negative values
    public static final double kLauncherIntakeSpeed = 0.2;
    public static final double kLauncherLaunchSpeed = -1.0;

    // Feeder Bottom 6 is the bottom motor. 
    // Launches with Positive values
    public static final double kFeederIntakeSpeed = 0.2;
    public static final double kFeederLaunchSpeed = -1.0;

    public static final double kLauncherDelay = 0.5;
    public static final double kLauncherRunDuration = 1;

    public static final double kLauncherSlowLaunchSpeed = 0.3;
    public static final double kFeederSlowLaunchSpeed = -0.3;

    public static final double kMaxTemperature = 60.0;
  }

  public static class GroundMotorConstants{
    public static final int kGroundIntakeID = 10;
    public static final double kGroundIntakeSpeed = 0.6;
  }
  public static class AutoConstants{
    public static final double kAutoDrivePower = -0.5;
    public static final double kAutoTimeout = 1;
    public static final double kAutoDriveSpeed = -0.5;
    public static final Double kMaxSpeed = 4.0;
    public static final Double kMaxAcceleration = 4.0;

  }
  public static class ClimberConstants{
    public static final int kclimberID = 0;

    public static final double kclimberAscendSpeed = 0.7;
    public static final double kclimberDescendSpeed = -1.0;

    public static final double kclimberMinHeight = 40.0;
    public static final double kclimberMaxHeight = 70.0;

    public static final int kTopSwitchID = 1;
    public static final int kBottomSwitchID = 2;

    public static final double kCurrentHeight = 10.0;
  }

  public static class NavXConstants {
    public static final double kCorrectionFactor = 0.03;
    public static final double kSpeed = -0.8;
  }
}
