package org.usfirst.frc.team3291.robot;
 
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.CANTalon;



public class Robot extends SampleRobot {
	RobotDrive myRobot = new RobotDrive(3,1,2,0);
	CANTalon Climber = new CANTalon(1);
	CANTalon Shooter = new CANTalon(2);
	Spark Sweeper = new Spark(6);
	Servo GearLeft = new Servo(7);
	Servo GearRight = new Servo(8);
	Joystick Stick = new Joystick(0);
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
	boolean sweeperOn = false;
	boolean gearOpen = false;
	boolean climberOn = false;
	Encoder shooterEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	int count = shooterEncoder.get();
	

	
	public Robot() {
		myRobot.setExpiration(0.1);
		Sweeper.enableDeadbandElimination(true);
		GearLeft.enableDeadbandElimination(true);
		GearRight.enableDeadbandElimination(true);
	}

	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto modes", chooser);
		//myRobot.setInvertedMotor(MotorType.kFrontRight, true);
		//myRobot.setInvertedMotor(MotorType.kRearRight, true);
		myRobot.setInvertedMotor(MotorType.kRearLeft, true);
		myRobot.setInvertedMotor(MotorType.kFrontLeft, true);
		shooterEncoder.setDistancePerPulse(((2+(7/8))*Math.PI)/4096);
	}

	
	@Override
	public void autonomous() {
		String autoSelected = chooser.getSelected();
		// String autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		
		switch (autoSelected) {
		case customAuto:
			boolean True = true;
			break;
		case defaultAuto:
		default:
			
			myRobot.setSafetyEnabled(false);
			
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
			
			//myRobot.arcadeDrive(Stick);				
			if(Stick.getRawButton(2) && climberOn == false){
				Climber.set(1);
				climberOn = true;
			}else if (Stick.getRawButton(2) && climberOn == true){
				Climber.set(0);
				climberOn = false;
			}else if (Stick.getRawButton(1)){
				Shooter.set(0.75);
				shooterEncoder.setDistancePerPulse(4);
				shooterEncoder.getRate();
			}else if (Stick.getRawButton(3) && sweeperOn == false){
				Sweeper.set(0.5);
				sweeperOn = true;
			}else if(Stick.getRawButton(3) && sweeperOn == true){
				Sweeper.set(0.0);
				sweeperOn = false;
			}else if (Stick.getRawButton(4) && gearOpen == false){
				int x = 0;
				while(x== 90){
				GearLeft.setAngle(x);
				GearRight.setAngle(x);
				x++;
				}
				gearOpen = true;
			}else if (Stick.getRawButton(4) && gearOpen == true){
				int y = 90;
				while(y== 0){
				GearLeft.setAngle(y);
				GearRight.setAngle(y);
				y++;
				}
			}else if(!Stick.getRawButton(1)){
				Shooter.set(0.0);
			}else if(Stick.getRawButton(5)){
				myRobot.drive(0.2, 0.0);
				Timer.delay(2);
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
		if(path == "left" || path == "Left"){
			
		}
		if (path == "right" || path == "Right"){
			
		}
		if(path == "straight" || path == "Straight"){
			while(distance3 > 6.5){
				myRobot.drive(0.5, 0);
			}
			myRobot.drive(-0.1, 0);
			Timer.delay(1);
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
