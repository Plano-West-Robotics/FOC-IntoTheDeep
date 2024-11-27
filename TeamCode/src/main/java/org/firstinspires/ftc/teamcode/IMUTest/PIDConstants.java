package org.firstinspires.ftc.teamcode.IMUTest;

import com.acmerobotics.dashboard.config.Config;

@Config
public class PIDConstants
{
    /*

    Tuning guide:

    Set the I and D gains to zero
    Increase the P gain until there are oscillations around the target
    Increase the D gain until no overshoot occurs
    If there is steady state error, increase the I gain until it is corrected

     */

    public static double Kp = 2;
    public static double Ki = 0.0;
    public static double Kd = 0.0;

}
