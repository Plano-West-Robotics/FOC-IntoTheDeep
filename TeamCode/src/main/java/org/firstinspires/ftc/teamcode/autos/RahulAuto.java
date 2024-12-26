package org.firstinspires.ftc.teamcode.autos;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

@Config
@Autonomous(name = "RahulAuto", group = "Roadrunner")
public class RahulAuto extends LinearOpMode {
    ElapsedTime oArmTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    ElapsedTime oClawTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    public class OArm {
        public Servo oArm;
        public double minPos = 0; // needs manual testing II
        public double maxPos = 0.82; // needs manual testing II

        public OArm(HardwareMap hardwareMap) {
            oArm = hardwareMap.get(Servo.class, "oArm");
        }

        public class ExtendOArm implements Action {
            boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    oArmTimer.reset();
                    oArm.setPosition(minPos);
                    initialized = true;
                }
                telemetryPacket.put("Outtake claw position", minPos);
                return oArmTimer.milliseconds() < 800;
            }
        }

        public Action extendOArm() {
            return new ExtendOArm();
        }

        public class RetractOArm implements Action {
            boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    oArmTimer.reset();
                    oArm.setPosition(maxPos);
                    initialized = true;
                }
                telemetryPacket.put("Outtake claw position", maxPos);
                return oArmTimer.milliseconds() < 800;
            }
        }

        public Action retractOArm() {
            return new RetractOArm();
        }

    }

    public class OClaw {
        public Servo oClaw;
        public double minPos = 0.4; // needs manual testing II
        public double maxPos = 0.55; // needs manual testing II

        public OClaw(HardwareMap hardwareMap) {
            oClaw = hardwareMap.get(Servo.class, "oClaw");
        }

        public class OpenOClaw implements Action {
            boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    oClawTimer.reset();
                    oClaw.setPosition(minPos);
                    initialized = true;
                }
                telemetryPacket.put("Outtake claw position", minPos);
                return oClawTimer.milliseconds() < 400;
            }
        }

        public Action openOClaw() {
            return new OpenOClaw();
        }

        public class CloseOClaw implements Action {
            boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    oClawTimer.reset();
                    oClaw.setPosition(maxPos);
                    initialized = true;
                }

                telemetryPacket.put("Outtake claw position", maxPos);
                return oClawTimer.milliseconds() < 400;
            }
        }

        public Action closeOClaw() {
            return new CloseOClaw();
        }
    }

    public class OSlides {
        public DcMotor oSlideL, oSlideR;

        public OSlides(HardwareMap hardwareMap) {
            oSlideL = hardwareMap.get(DcMotor.class, "oSlideL");
            oSlideR = hardwareMap.get(DcMotor.class, "oSlideR");

            oSlideL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            oSlideL.setDirection(DcMotorSimple.Direction.REVERSE);

            oSlideR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            oSlideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            oSlideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            oSlideR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            oSlideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        public class OSlidesBottom implements Action {

            public boolean initialized = false;
            public static final int BOTTOM_OUTTAKE_SLIDE_POS = 0; // needs manual testing II
            public static final double OUTTAKE_SLIDE_POWER = 0.5;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                if (!initialized) {
                    oSlideL.setPower(-1 * OUTTAKE_SLIDE_POWER);
                    oSlideR.setPower(-1 * OUTTAKE_SLIDE_POWER);
                    initialized = !initialized;
                }

                int posL = oSlideL.getCurrentPosition();
                int posR = oSlideR.getCurrentPosition();

                telemetryPacket.put("Left outtake slide position", posL);
                telemetryPacket.put("Right outtake slide position", posR);

                if (posL > BOTTOM_OUTTAKE_SLIDE_POS && posR > BOTTOM_OUTTAKE_SLIDE_POS) {
                    return true;
                } else {
                    oSlideL.setPower(0);
                    oSlideR.setPower(0);
                    return false;
                }
            }
        }

        public Action oSlidesBottom() {
            return new OSlidesBottom();
        }

        public class ExtendOSlidesHBar implements Action {

            public boolean initialized = false;
            public static final int HBAR_OUTTAKE_SLIDE_POS = 2150; // needs manual testing II
            public static final double OUTTAKE_SLIDE_POWER = 0.5;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                if (!initialized) {
                    oSlideL.setPower(OUTTAKE_SLIDE_POWER);
                    oSlideR.setPower(OUTTAKE_SLIDE_POWER);
                    initialized = !initialized;
                }

                int posL = oSlideL.getCurrentPosition();
                int posR = oSlideR.getCurrentPosition();

                telemetryPacket.put("Left outtake slide position", posL);
                telemetryPacket.put("Right outtake slide position", posR);

                if (posL < HBAR_OUTTAKE_SLIDE_POS && posR < HBAR_OUTTAKE_SLIDE_POS) {
                    return true;
                } else {
                    oSlideL.setPower(0);
                    oSlideR.setPower(0);
                    return false;
                }
            }
        }

        public Action extendOSlidesHBar() {
            return new ExtendOSlidesHBar();
        }

        public class LowerOSlidesToHook implements Action {

            public boolean initialized = false;
            public static final int HBAR_OUTTAKE_SLIDE_POS = 1600; // needs manual testing II
            public static final double OUTTAKE_SLIDE_POWER = 0.7;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                if (!initialized) {
                    oSlideL.setPower(-1 * OUTTAKE_SLIDE_POWER);
                    oSlideR.setPower(-1 * OUTTAKE_SLIDE_POWER);
                    initialized = !initialized;
                }

                int posL = oSlideL.getCurrentPosition();
                int posR = oSlideR.getCurrentPosition();

                telemetryPacket.put("Left outtake slide position", posL);
                telemetryPacket.put("Right outtake slide position", posR);

                if (posL > HBAR_OUTTAKE_SLIDE_POS || posR > HBAR_OUTTAKE_SLIDE_POS) {
                    return true;
                } else {
                    oSlideL.setPower(0);
                    oSlideR.setPower(0);
                    return false;
                }
            }
        }

        public Action lowerOSlidesToHook() {
            return new LowerOSlidesToHook();
        }

        public class ExtendOSlidesPickup implements Action {

            public boolean initialized = false;
            public static final int HBAR_OUTTAKE_SLIDE_POS = 800; // needs manual testing II
            public static final double OUTTAKE_SLIDE_POWER = 0.5;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                if (!initialized) {
                    oSlideL.setPower(OUTTAKE_SLIDE_POWER);
                    oSlideR.setPower(OUTTAKE_SLIDE_POWER);
                    initialized = !initialized;
                }

                int posL = oSlideL.getCurrentPosition();
                int posR = oSlideR.getCurrentPosition();

                telemetryPacket.put("Left outtake slide position", posL);
                telemetryPacket.put("Right outtake slide position", posR);

                if (posL < HBAR_OUTTAKE_SLIDE_POS && posR < HBAR_OUTTAKE_SLIDE_POS) {
                    return true;
                } else {
                    oSlideL.setPower(0);
                    oSlideR.setPower(0);
                    return false;
                }
            }
        }

        public Action extendOSlidesPickup() {
            return new ExtendOSlidesPickup();
        }

        public class LowerOSlidesPickup implements Action {

            public boolean initialized = false;
            public static final int HBAR_OUTTAKE_SLIDE_POS = 800; // needs manual testing II
            public static final double OUTTAKE_SLIDE_POWER = 0.5;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {

                if (!initialized) {
                    oSlideL.setPower(OUTTAKE_SLIDE_POWER);
                    oSlideR.setPower(OUTTAKE_SLIDE_POWER);
                    initialized = !initialized;
                }

                int posL = oSlideL.getCurrentPosition();
                int posR = oSlideR.getCurrentPosition();

                telemetryPacket.put("Left outtake slide position", posL);
                telemetryPacket.put("Right outtake slide position", posR);

                if (posL > HBAR_OUTTAKE_SLIDE_POS && posR > HBAR_OUTTAKE_SLIDE_POS) {
                    return true;
                } else {
                    oSlideL.setPower(0);
                    oSlideR.setPower(0);
                    return false;
                }
            }
        }

        public Action lowerOSlidesPickup() {
            return new LowerOSlidesPickup();
        }

    }


    @Override
    public void runOpMode() {
        double ninety = Math.toRadians(90);
        double oneEighty = Math.toRadians(180);
        double twoSeventy = Math.toRadians(270);

        // instantiate your MecanumDrive at a particular pose.
        Pose2d initialPose = new Pose2d(24, -61, ninety); // needs manual testing II

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        OArm oArm = new OArm(hardwareMap);

        OClaw oClaw = new OClaw(hardwareMap);

        OSlides oSlides = new OSlides(hardwareMap);

        /*

        //https://learnroadrunner.com/trajectories.html#slowing-down-a-trajectory to adjust trajectory speed
        TrajectoryActionBuilder tab0 = drive.actionBuilder(initialPose)
                .splineToSplineHeading(new Pose2d(10, -34, twoSeventy), ninety) // position at the bar for the preload
                .waitSeconds(1)
                .splineToSplineHeading(new Pose2d(36, -30, ninety), ninety) // intermediate stage in the path to first block
                .splineToConstantHeading(new Vector2d(48, -10), 0) // arrives north of the first block
                .setTangent(ninety)
                .lineToY(-56) // pushes the block
                .lineToY(-16) // goes up to try and reach the second block
                .splineToConstantHeading(new Vector2d(58, -14), Math.toRadians(300)) // goes north of the second block
                .setTangent(ninety)
                .lineToY(-56) // pushes the second block down
                .strafeToConstantHeading(new Vector2d(36, -61)) // goes to the pickup position for the first block
                .waitSeconds(0.2)
                .splineToSplineHeading(new Pose2d(10, -34, twoSeventy), ninety) // goes to the bar
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(36, -61), ninety) // goes to the pickup position for the second block
                .waitSeconds(0.2)
                .splineToSplineHeading(new Pose2d(10, -34, twoSeventy), ninety) // goes to the bar
                .waitSeconds(1)
                .strafeTo(new Vector2d(61, -61));// parks

         */

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose).
                splineToSplineHeading(new Pose2d(5, -31, twoSeventy), ninety);

        TrajectoryActionBuilder tab2 = drive.actionBuilder(new Pose2d(5, -31, twoSeventy))
                .splineToSplineHeading(new Pose2d(36, -30, ninety), ninety) // intermediate stage in the path to first block
                .splineToConstantHeading(new Vector2d(54, -10), 0) // arrives north of the first block
                .setTangent(ninety)
                .lineToY(-50) // pushes the block
                .lineToY(-16) // goes up to try and reach the second block
                .splineToConstantHeading(new Vector2d(62, -14), Math.toRadians(300)) // goes north of the second block
                .setTangent(ninety)
                .lineToY(-50); //pushes the second block down

        TrajectoryActionBuilder tab3 = drive.actionBuilder(new Pose2d(58, -50, ninety))
                .strafeToConstantHeading(new Vector2d(36, -61)); // goes to the pickup position for the first block

        TrajectoryActionBuilder tab4 = drive.actionBuilder(new Pose2d(36, -61, ninety))
                .splineToSplineHeading(new Pose2d(10, -34, twoSeventy), ninety); // goes to hook the first picked up block

        TrajectoryActionBuilder tab5 = drive.actionBuilder(new Pose2d(10, -34, twoSeventy))
                .strafeToSplineHeading(new Vector2d(36, -61), ninety); // goes to the pickup position for the second block

        TrajectoryActionBuilder tab6 = drive.actionBuilder(new Pose2d(36, -61, ninety))
                .splineToSplineHeading(new Pose2d(10, -34, twoSeventy), ninety); // goes to the bar

        TrajectoryActionBuilder tab7 = drive.actionBuilder(new Pose2d(10, -34, twoSeventy))
                .strafeTo(new Vector2d(61, -61)); // parks

        //init actions
        Actions.runBlocking(new SequentialAction(oClaw.closeOClaw(), oArm.retractOArm()));

        waitForStart();

        if (isStopRequested()) return;

        Action act1 = tab1.build();
        Action act2 = tab2.build();
        Action act3 = tab3.build();
        Action act4 = tab4.build();
        Action act5 = tab5.build();
        Action act6 = tab6.build();
        Action park = tab7.build();

        ParallelAction preloadGoToBar = new ParallelAction(
                act1,
                oSlides.extendOSlidesHBar(),
                oArm.extendOArm()
        );

        SequentialAction goToBarAndHook = new SequentialAction(preloadGoToBar, oSlides.lowerOSlidesToHook(),
                oClaw.openOClaw(), oArm.retractOArm());

        ParallelAction lowerSlidesAndPush = new ParallelAction(oSlides.oSlidesBottom(), act2);

        ParallelAction firstPickup = new ParallelAction(act3, oArm.extendOArm(), oSlides.extendOSlidesPickup());

        ParallelAction firstHook = new ParallelAction(act4, oSlides.extendOSlidesHBar());

        SequentialAction actuallyHook = new SequentialAction(firstHook, oSlides.lowerOSlidesToHook(),
                oClaw.openOClaw(), oArm.retractOArm());

        ParallelAction goBackToPickup = new ParallelAction(act5, oSlides.lowerOSlidesPickup(), oArm.extendOArm());

        ParallelAction lastGoToHook = new ParallelAction(act6, oSlides.extendOSlidesHBar());

        SequentialAction hookSecondTime = new SequentialAction(lastGoToHook,
                oSlides.lowerOSlidesToHook(), oClaw.openOClaw(), oArm.retractOArm()); // Even though this is technically the third time, I'm counting the preload as the 0th time


        Actions.runBlocking(
                new SequentialAction(goToBarAndHook, lowerSlidesAndPush, firstPickup, oClaw.closeOClaw()
                , actuallyHook, goBackToPickup, oClaw.closeOClaw(), hookSecondTime, park)
        );


    }

}