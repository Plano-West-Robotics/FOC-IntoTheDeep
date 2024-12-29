package org.firstinspires.ftc.teamcode.wrappers;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class ServoPair
{
    public Servo leftServo, rightServo;

    public ServoPair(HardwareMap hardwareMap, String leftServoName, String rightServoName)
    {
        leftServo = new Servo(hardwareMap, leftServoName);
        rightServo = new Servo(hardwareMap, rightServoName);
    }

    /**
     * Remember that servos do not have encoders!
     * @return the arguments of the left and right servos' last setPosition() call
     */
    public double[] getPosition()
    {
        return new double[] {leftServo.getPosition(), rightServo.getPosition()};
    }

    public void setPosition(double position)
    {
        setPosition(position, 1 - position);
    }

    public void setPosition(double leftPosition, double rightPosition)
    {
        leftServo.setPosition(leftPosition);
        rightServo.setPosition(rightPosition);
    }

    public Servo getLeft()
    {
        return leftServo;
    }

    public Servo getRight()
    {
        return rightServo;
    }
}
