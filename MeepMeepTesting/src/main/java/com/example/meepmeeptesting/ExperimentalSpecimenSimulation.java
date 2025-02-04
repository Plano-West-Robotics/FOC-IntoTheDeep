package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

// TODO: READ FROM THE VERY TOP
public class ExperimentalSpecimenSimulation {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);


        // TODO: PAY ATTENTION!! SHOULD START AT THE CENTER!!
        Pose2d initPose = new Pose2d(0, -63, tR(90));

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(70, 70, Math.toRadians(225), 5, 12)
            // TODO: minAccel should be less - around -45
                .build();

        // TODO: CHECK EVERY
        //  SINGLE
        //  CHANGE
        //  IN DETAIL!!!
        myBot.runAction(myBot.getDrive().actionBuilder(initPose)
                .splineToConstantHeading(new Vector2d(0, -28), tR(90)) // go to the bar with preload 1
                .splineToConstantHeading(new Vector2d(12, -42), tR(0)) // intermediate stage in spline
                .splineToConstantHeading(new Vector2d(33, -36), tR(90)) // reached in between submersible and sample
                .splineToConstantHeading(new Vector2d(42, -16), 0)
                .splineToConstantHeading(new Vector2d(47, -20), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(47, -36), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(47, -16), tR(90))
                .splineToConstantHeading(new Vector2d(50, -10), 0)
                .splineToConstantHeading(new Vector2d(54, -14), tR(270))
                .splineToConstantHeading(new Vector2d(54, -40), tR(270))
                .splineToConstantHeading(new Vector2d(54, -14), tR(90))
                .splineToConstantHeading(new Vector2d(57, -10), tR(0))
                .splineToConstantHeading(new Vector2d(59, -16), tR(270))
                .splineToConstantHeading(new Vector2d(59, -40), tR(270)) // end of pushing path

                .splineToConstantHeading(new Vector2d(57, -50), tR(180)) // intermediate to pickup
                .splineToConstantHeading(new Vector2d(53, -64.3), tR(270)) // goes to pickup

                //.strafeToConstantHeading(new Vector2d(-1, -27))
                .setTangent(tR(90))
                .splineToConstantHeading(new Vector2d(2, -27), tR(90))

                //.strafeToConstantHeading(new Vector2d(38, -64.3))
                .setTangent(tR(270))
                .splineToConstantHeading(new Vector2d(38, -64.3), tR(270))

                .setTangent(tR(90))
                .splineToConstantHeading(new Vector2d(3, -27), tR(90))

                //.strafeToConstantHeading(new Vector2d(38, -64.3))
                .setTangent(tR(270))
                .splineToConstantHeading(new Vector2d(38, -64.3), tR(270))

                .setTangent(tR(90))
                .splineToConstantHeading(new Vector2d(5, -27), tR(90))

                .setTangent(tR(270))
                .splineToConstantHeading(new Vector2d(38, -64.3), tR(270))

                .setTangent(tR(90))
                .splineToConstantHeading(new Vector2d(6, -27), tR(90))

                /*
                .strafeToConstantHeading(new Vector2d(2, -27))
                .strafeToConstantHeading(new Vector2d(38, -64.3))
                .strafeToConstantHeading(new Vector2d(5, -27))
                 */
                .build());



        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }

    public static double tR(double degrees)
    {
        return Math.toRadians(degrees);
    }

}