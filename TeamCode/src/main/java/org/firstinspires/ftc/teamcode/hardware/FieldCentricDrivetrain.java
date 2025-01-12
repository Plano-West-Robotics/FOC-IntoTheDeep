package org.firstinspires.ftc.teamcode.hardware;

import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FieldCentricDrivetrain
{
    public Motor fr, fl, br, bl;
    public Motor[] motors;
    public RevIMU imu;

    public FieldCentricDrivetrain(HardwareMap hardwareMap)
    {
        fr = new Motor(hardwareMap, "fr", Motor.GoBILDA.RPM_312);
        fl = new Motor(hardwareMap, "fl", Motor.GoBILDA.RPM_312);
        br = new Motor(hardwareMap, "br", Motor.GoBILDA.RPM_312);
        bl = new Motor(hardwareMap, "bl", Motor.GoBILDA.RPM_312);

        fl.setInverted(true);
        bl.setInverted(true);

        motors = new Motor[] {fr, fl, br, bl};

        for (Motor motor : motors)
        {
            motor.stopAndResetEncoder();
            motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            motor.stopMotor();
        }

        imu = new RevIMU(hardwareMap);
        imu.init();
    }

    public RevIMU getImu()
    {
        return imu;
    }
}
