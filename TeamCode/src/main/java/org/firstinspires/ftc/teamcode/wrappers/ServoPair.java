package org.firstinspires.ftc.teamcode.wrappers;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class ServoPair
{
    public Servo left, right;
    public double positionDiff;

    public ServoPair(HardwareMap hardwareMap, String leftServoName, String rightServoName,
                     double positionDiff)
    {
        left = new Servo(hardwareMap, leftServoName);
        right = new Servo(hardwareMap, rightServoName);

        /*
        This value means the servo pair is synced up when [left=positionDiff, right=1], or
        [left=1, right=positionDiff].
         */
        this.positionDiff = positionDiff;
    }

    /**
     * Remember that servos do not have encoders!
     * @return the arguments of the left and right servos' last setPosition() call
     */
    public double[] getPosition()
    {
        return new double[] {left.getPosition(), right.getPosition()};
    }

    public void setPosition(double position)
    {
        setPosition(position, 1 + positionDiff - position);
    }

    public void setPosition(double leftPosition, double rightPosition)
    {
        left.setPosition(leftPosition);
        right.setPosition(rightPosition);
    }

    public Servo getLeft()
    {
        return left;
    }

    public Servo getRight()
    {
        return right;
    }

    public double getPositionDiff()
    {
        return positionDiff;
    }

    public void setPositionDiff(double positionDiff)
    {
        this.positionDiff = positionDiff;
    }
}
