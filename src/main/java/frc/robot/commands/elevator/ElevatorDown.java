package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;

public class ElevatorDown extends Command {
    
    public ElevatorDown(){
        addRequirements(Constants.ELEVATOR);
    }

    @Override
    public void initialize(){
        
   
    }
    @Override
    public void execute(){

        System.out.println("ELEVATOR");


        if(Constants.driverXbox.getLeftTriggerAxis() > 0.2){
            System.out.println("WORKING");

            Constants.elevatorHeight -= 0.002;

        }
        else if(Constants.driverXbox.getRightTriggerAxis() > 0.2){
            Constants.elevatorHeight = Constants.elevatorHeight + 0.002;
            System.out.println("WORKING");

        }



    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}