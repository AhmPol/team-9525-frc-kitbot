package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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
 public class CANDrivetrain extends SubsystemBase {

   /*Class member variables. These variables represent things the class needs to keep track of and use between
   different method calls. */
   DifferentialDrive drivetrain;

   /*Constructor. This method is called when an instance of the class is created. This should generally be used to set up
    * member variables and perform any configuration or set up necessary on hardware.
    */
   public CANDrivetrain() {
    CANSparkMax motorLeftFront = new CANSparkMax(DrivetrainConstants.kLeftFrontID, MotorType.kBrushed);
    CANSparkMax motorLeftRear = new CANSparkMax(DrivetrainConstants.kLeftRearID, MotorType.kBrushed);
    CANSparkMax motorRightFront = new CANSparkMax(DrivetrainConstants.kRightFrontID, MotorType.kBrushed);
    CANSparkMax motorRightRear = new CANSparkMax(DrivetrainConstants.kRightRearID, MotorType.kBrushed);
    
    /*Sets current limits for the drivetrain motors. This helps reduce the likelihood of wheel spin, reduces motor heating
    *at stall (Drivetrain pushing against something) and helps maintain battery voltage under heavy demand */
    motorLeftFront.setSmartCurrentLimit(DrivetrainConstants.kCurrentLimit);
    motorLeftRear.setSmartCurrentLimit(DrivetrainConstants.kCurrentLimit);
    motorRightFront.setSmartCurrentLimit(DrivetrainConstants.kCurrentLimit);
    motorRightRear.setSmartCurrentLimit(DrivetrainConstants.kCurrentLimit);

    //Set the rear motors to follow the front motors.
     motorLeftRear.follow(motorLeftFront);
     motorRightRear.follow(motorRightFront);

    //Invert the left side so both side drive forward with positive motor outputs
     motorLeftFront.setInverted(true);
     motorRightFront.setInverted(false);

    //Put the front motors into the differential drive object. This will control all 4 motors with
    //the rears set to follow the fronts
     drivetrain = new DifferentialDrive(motorLeftFront, motorRightFront);
   }

   /*Method to control the drivetrain using arcade drive. Arcade drive takes a speed in the X (forward/back) direction
    * and a rotation about the Z (turning the robot about it's center) and uses these to control the drivetrain motors */
   public void arcadeDrive(double speed, double rotation) {
     drivetrain.arcadeDrive(speed, rotation);
   }

   @Override
   public void periodic() {
     /*This method will be called once per scheduler run. It can be used for running tasks we know we want to update each
      * loop such as processing sensor data. Our drivetrain is simple so we don't have anything to put here */
   }
 }
