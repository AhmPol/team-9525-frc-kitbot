package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
//import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;

/* This class declares the subsystem for the robot drivetrain if controllers are connected via CAN. Make sure to go to
* RobotContainer and uncomment the line declaring this subsystem and comment the line for PWMDrivetrain.
*
* The subsystem contains the objects for the hardware contained in the mechanism and handles low level logic
* for control. Subsystems are a mechanism that, when used in conjuction with command "Requirements", ensure
* that hardware is only being used by 1 command at a time.
*/
 public class Drivetrain extends SubsystemBase {
  private final CANSparkMax leftFront;
  private final CANSparkMax leftRear;
  private final CANSparkMax rightFront; 
  private final CANSparkMax rightRear; 
  //private final SlewRateLimiter leftLimiter;
  //private final SlewRateLimiter rightLimiter;
  private final DifferentialDrive drivetrain;

  /*Constructor. This method is called when an instance of the class is created. This should generally be used to set up
    * member variables and perform any configuration or set up necessary on hardware.
    */
  public Drivetrain() {
    leftFront = new CANSparkMax(DrivetrainConstants.kLeftFrontID, MotorType.kBrushed);
    leftRear = new CANSparkMax(DrivetrainConstants.kLeftRearID, MotorType.kBrushed);
    rightFront = new CANSparkMax(DrivetrainConstants.kRightFrontID, MotorType.kBrushed);
    rightRear = new CANSparkMax(DrivetrainConstants.kRightRearID, MotorType.kBrushed);

    /*Sets current limits for the drivetrain motors. This helps reduce the likelihood of wheel spin, reduces motor heating
    *at stall (Drivetrain pushing against something) and helps maintain battery voltage under heavy demand */
    leftFront.setSmartCurrentLimit(DrivetrainConstants.kCurrentLimit);
    leftRear.setSmartCurrentLimit(DrivetrainConstants.kCurrentLimit);
    rightFront.setSmartCurrentLimit(DrivetrainConstants.kCurrentLimit);
    rightRear.setSmartCurrentLimit(DrivetrainConstants.kCurrentLimit);

    //Set the rear motors to follow the front motors.
    leftRear.follow(leftFront);
    rightRear.follow(rightFront);

    drivetrain = new DifferentialDrive(leftFront, rightFront);

    //rightLimiter = new SlewRateLimiter(DrivetrainConstants.kSlewRateLimit);
    //leftLimiter = new SlewRateLimiter(DrivetrainConstants.kSlewRateLimit);
  
    //Invert the left side so both side drive forward with positive motor outputs
    leftFront.setInverted(false);
    rightFront.setInverted(true);
  }

   /*Method to control the drivetrain using arcade drive. Arcade drive takes a speed in the X (forward/back) direction
    * and a rotation about the Z (turning the robot about it's center) and uses these to control the drivetrain motors */
  public void arcadeDrive(double speed, double rotation) {
    //speed = leftLimiter.calculate(speed);
    //rotation = rightLimiter.calculate(rotation);
    drivetrain.arcadeDrive(speed, rotation);
  }

  public void stop()
  {
    drivetrain.arcadeDrive(0, 0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Right Bus Voltage", rightFront.getBusVoltage());
    SmartDashboard.putNumber("Left Bus Voltage", leftFront.getBusVoltage());
    SmartDashboard.putNumber("Right Current", rightFront.getOutputCurrent());
    SmartDashboard.putNumber("Left Current", leftFront.getOutputCurrent());
  }
}
