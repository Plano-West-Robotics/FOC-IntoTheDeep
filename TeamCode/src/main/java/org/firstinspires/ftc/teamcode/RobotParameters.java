package org.firstinspires.ftc.teamcode;

public final class RobotParameters
{
    // Mecanum drive.
    public static final double INITIAL_DRIVE_SPEED = 0.4;

    // Linear slides.
    public static final int SLIDE_SLOWDOWN_THRESHOLD = 100; // Ticks.
    // TODO: Tune slide speed.
    public static final double INTAKE_SLIDE_MAX_SPEED = 0.4;
    public static final double OUTTAKE_SLIDE_MAX_SPEED = 0.4;
    public static final int INTAKE_SLIDE_MAX_TICKS = 1100;
    // TODO: Re-test and ensure both outtake slides have the same max position.
    public static final int OUTTAKE_SLIDE_MAX_TICKS = 4310;
    // TODO: Find the heights (in ticks) for each scoring elevation.
    public static final int LOW_CHAMBER_HEIGHT = 0;
    public static final int HIGH_CHAMBER_HEIGHT = 0;
    public static final int LOW_BASKET_HEIGHT = 0;
    public static final int HIGH_BASKET_HEIGHT = 0;

    private RobotParameters() {}
}