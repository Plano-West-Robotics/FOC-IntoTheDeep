package org.firstinspires.ftc.teamcode;

public final class RobotParameters
{
    // Mecanum drive.
    public static final double INITIAL_DRIVE_SPEED = 0.4;

    // Intake linear slides.
    public static final double INTAKE_SLIDE_MAX_SPEED = 0.6; // TODO: Tune.
    public static final int INTAKE_SLIDE_MAX_TICKS = 1106;
    public static final int INTAKE_SLIDE_SLOWDOWN_THRESHOLD = 70; // TODO: Tune.
    public static final double MAX_TICKS_PER_SECOND = 2796; // Given a 312 RPM motor and 38.2 diameter pulley

    // Outtake linear slides.
    public static final double OUTTAKE_SLIDE_MAX_SPEED = 0.4; // TODO: Tune.
    public static final int OUTTAKE_SLIDE_MAX_TICKS = 4310; // TODO: Make sure left and right slides have the same max encoder reading.
    // TODO: Figure out scoring heights
    public static final int LOW_CHAMBER_HEIGHT = 0;
    public static final int HIGH_CHAMBER_HEIGHT = 0;
    public static final int LOW_BASKET_HEIGHT = 0;
    public static final int HIGH_BASKET_HEIGHT = 0;

    // Intake.
    public static final double INTAKE_WHEEL_SPEED = 0.1; // TODO: Tune.

    private RobotParameters() {}
}