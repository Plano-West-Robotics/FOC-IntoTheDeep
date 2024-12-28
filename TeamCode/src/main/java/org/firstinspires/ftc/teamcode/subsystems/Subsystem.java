package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.control.Gamepads;

public interface Subsystem
{
    /**
     * All implementations of this method <b> WILL NOT AFFECT PHYSICAL CHANGES </b> in the state
     * of the robot. This method is responsible for updating the internal fields of a subsystem
     * according by processing controller input.
     * @param gamepads the Gamepads object to read input from
     */
    void update(Gamepads gamepads);

    /**
     * This method will cause a subsystem to perform its task, do something that affects the robot's
     * physical state, or proceed to process more logic to determine its course of action.
     */
    void operate();
}
