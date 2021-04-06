// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
 
package frc.robot;
 
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
 
 
public class Robot extends TimedRobot {
  private final PWMVictorSPX m_leftMotor = new PWMVictorSPX(0);
  private final PWMVictorSPX m_leftMotor1 = new PWMVictorSPX(3);
  private final PWMVictorSPX m_rightMotor = new PWMVictorSPX(1);
  private final PWMVictorSPX m_rightMotor1 = new PWMVictorSPX(2);
  private final SpeedControllerGroup m_left = new SpeedControllerGroup(m_leftMotor, m_leftMotor1);
  private final SpeedControllerGroup m_right = new SpeedControllerGroup(m_rightMotor, m_rightMotor1);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_left, m_right);
 
  private final PWMVictorSPX intake = new PWMVictorSPX(8);
  private final PWMVictorSPX elevator = new PWMVictorSPX(5);
  private final PWMVictorSPX flywheel_left = new PWMVictorSPX(4);
  private final PWMVictorSPX flywheel_right = new PWMVictorSPX(6);
  private final SpeedControllerGroup flywheels = new SpeedControllerGroup(flywheel_left, flywheel_right);
  private final XboxController m_stick = new XboxController(0); // XboxController.Button
  private final Joystick movement = new Joystick(1);
  private final Timer m_timer = new Timer();
 
 
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }
 
  @Override
  public void teleopPeriodic() {
    m_robotDrive.arcadeDrive(movement.getY(), movement.getX());
    flywheels.set(m_stick.getY(Hand.kLeft)*0.75);
    intake.set(-m_stick.getTriggerAxis(Hand.kLeft));
    elevator.set(-m_stick.getTriggerAxis(Hand.kRight));

    if (m_stick.getBumper(Hand.kLeft)) {
      intake.set(1.0);
    } else {
      intake.set(0.0);
    }

    if (m_stick.getBumper(Hand.kRight)) {
      elevator.set(1.0);
    } else {
      elevator.set(0.0);
    }
  }
}