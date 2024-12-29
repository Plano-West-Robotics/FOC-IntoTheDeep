package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.control.Gamepads;

public interface Subsystem
{
    /**
     * This method will cause a subsystem to update its state (optionally reading gamepad input)
     * then perform its task, do something that affects the robot's physical state, or process logic
     * to determine and execute its next course of action.
     */
    void update(Gamepads gamepads);
}
