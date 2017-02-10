package org.usfirst.frc.team3291.robot;
 
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class. The
 * SampleRobot class is the base of a robot application that will automatically
 * call your Autonomous and OperatorControl methods at the right time as
 * controlled by the switches on the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're
 * inexperienced, don't. Unless you know what you are doing, complex code will
 * be much more difficult under this system. Use IterativeRobot or Command-Based
 * instead if you're new.
 */
public class Robot extends SampleRobot {
	RobotDrive myRobot = new RobotDrive(1,3,2,0);
	Joystick drive1 = new Joystick(0);
	Talon Climber = new Talon(0);
	Talon Shooter = new Talon(1);
	Spark Sweeper = new Spark(2);
	Servo GearLeft = new Servo(3);
	Servo GearRight = new Servo(4);
	Joystick Stick = new Joystick(1);
	Joystick Stick2 = new Joystick(2);
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	AnalogInput input3 = new AnalogInput(3);
	AnalogInput input0 = new AnalogInput(0);
	double volts3 = input3.getVoltage();
	double volts0 = input0.getVoltage();
	double distance3 = ((volts3*1000)/9.8); // analog 3
	double distance0 = ((volts0*1000*5)/4.88)*(1/25.4); // analog 0
	SendableChooser<String> chooser = new SendableChooser<>();
	int GearLeftAngle = 90;
	int GearRightAngle = 90;
	boolean SweeperOnOrOff = false;

	public Robot() {
		myRobot.setExpiration(0.1);
		Climber.enableDeadbandElimination(true);
		Sweeper.enableDeadbandElimination(true);
		Shooter.enableDeadbandElimination(true);
		GearLeft.enableDeadbandElimination(true);
		GearRight.enableDeadbandElimination(true);
	}

	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto modes", chooser);
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * if-else structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomous() {
		String autoSelected = chooser.getSelected();
		// String autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);

		switch (autoSelected) {
		case customAuto:
			myRobot.setSafetyEnabled(false);
			myRobot.drive(-0.5, 1.0); // spin at half speed
			Timer.delay(2.0); // for 2 seconds
			myRobot.drive(0.0, 0.0); // stop robot
			break;
		case defaultAuto:
		default:
			myRobot.setSafetyEnabled(false);
 			gearAuto("right");
			break;
		}
	}

	/**
	 * Runs the motors with arcade steering.
	 */
	@Override
	public void operatorControl() {
		myRobot.setSafetyEnabled(true);
		
		while (isOperatorControl() && isEnabled()  ) {
			myRobot.arcadeDrive(drive1);				
			if(Stick.getRawButton(1)){
				Climber.set(1);
			}else if (Stick.getRawButton(2)){
				Shooter.set(1);
			}else if (Stick.getRawButton(3) && SweeperOnOrOff == false){
				Sweeper.set(0.5);
				SweeperOnOrOff = true;
			}else if(Stick.getRawButton(3) && SweeperOnOrOff == true){
				Sweeper.set(0.0);
				SweeperOnOrOff = false;
			}else if (Stick.getRawButton(4)){
				GearRight.enableDeadbandElimination(true);
				
				GearLeft.setAngle(75);
				GearRight.setAngle(75);
				GearLeft.set(GearLeftAngle);
				GearRight.set(GearRightAngle);
				
			}
		
			Timer.delay(0.005);
		}
	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
	
	}
	
	public void gearAuto(String path){
		if(path == "left"){

			myRobot.drive(0.0, 0.0);
		}
		if (path == "right"){
			
		}
		if(path == "stright"){
			while(distance3> 8){
				myRobot.drive(0.5, 0);
			}
			myRobot.drive(-0.1, 0);
			Timer.delay(0.2);
			myRobot.drive(0.5, 0);
		}
	}
	
}
/*			myRobot.drive(-0.5, 0.0);
			Timer.delay(2.15); 
			myRobot.drive(0.5, 1.0);
			Timer.delay(0.75);
			myRobot.drive(-0.5, 0.0);
			Timer.delay(0.45);
			myRobot.drive(0.0, 0.0);
			Timer.delay(1);
			myRobot.drive(0.5, 0.0);
			Timer.delay(.6);
			myRobot.drive(0.5, 1.0);
			Timer.delay(.22);
			myRobot.drive(-0.5, 0.0);
			Timer.delay(1.25);
			*/
