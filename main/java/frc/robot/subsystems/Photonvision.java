package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;

public class VisionSystem extends SubsystemBase {
    private final PhotonCamera camera;
    
    // Constants for the camera setup (you may need to adjust these based on your actual setup)
    private final double CAMERA_HEIGHT_METERS = 1.0; // Height of the camera from the ground (meters)
    private final double TARGET_HEIGHT_METERS = 0.9; // Height of the target from the ground (meters)
    private final double CAMERA_PITCH_RADIANS = 0; // Pitch angle of the camera (radians)

    public VisionSystem() {
        // Initialize PhotonCamera with the name of the camera configured in PhotonVision
        camera = new PhotonCamera("photonvision");
    }

    @Override
    public void periodic() {
        // Get the latest camera result
        var result = camera.getLatestResult();

        // Log information to the console if AprilTags are detected
        if (result.hasTargets()) {
            PhotonTrackedTarget bestTarget = result.getBestTarget();

            // Check if the detected target is an AprilTag by getting its Fiducial ID
            int fiducialId = bestTarget.getFiducialId();
            if (fiducialId >= 0) {
                System.out.println("AprilTag Detected:");

                // Calculate distance to the target
                double distance = calculateDistanceToTarget(bestTarget.getPitch());
                System.out.println("Distance to Target: " + distance + " meters");
            } else {
                System.out.println("No AprilTags Detected.");
            } 
        }
    }

    // Calculates distance to the target using camera pitch angle and known heights
    public double calculateDistanceToTarget(double targetPitch) {
        // Using the formula to calculate the distance based on camera pitch and target height
        double angle = Math.toRadians(targetPitch) - CAMERA_PITCH_RADIANS;  // Adjust for camera pitch
        double deltaHeight = TARGET_HEIGHT_METERS - CAMERA_HEIGHT_METERS;  // Difference in height
        double distance = deltaHeight / Math.tan(angle);  // Basic trigonometric formula for distance

        // Ensure the distance is positive (if the angle is too shallow or zero, we could get negative distance)
        return Math.max(distance, 0);
    }

    public void logTargets() {
        // Manually log data for debugging
        System.out.println("Logging targets...");
    }
}
