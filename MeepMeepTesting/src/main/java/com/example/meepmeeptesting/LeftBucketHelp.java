package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class LeftBucketHelp
{
    public static double iR(double degrees)
    {
        return Math.toRadians(degrees);
    }

    public static void main(String[] args)
    {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(48, 35, Math.toRadians(360), Math.toRadians(270), 15)
                .build();

        Pose2d initPose = new Pose2d(-24, -64, iR(90));

        myBot.runAction(myBot.getDrive().actionBuilder(initPose)
                .setTangent(iR(135))

                .splineToLinearHeading(new Pose2d(-56, -56, iR(45)), iR(225)) // from preload to bucket

                .waitSeconds(1)

                .splineToSplineHeading(new Pose2d(-48, -38, iR(90)), iR(90)) // from bucket to first ground sample

                .waitSeconds(1)

                .strafeToSplineHeading(new Vector2d(-56, -56), iR(45)) // from first ground sample to bucket

                .waitSeconds(1)

                .strafeToSplineHeading(new Vector2d(-58, -38), iR(90)) // from bucket to picking up the second ground sample

                .waitSeconds(1)

                .strafeToSplineHeading(new Vector2d(-56, -56), iR(45)) // from picking up the second sample to the bucket

                .waitSeconds(1)

                .strafeToSplineHeading(new Vector2d(-54, -28), iR(170)) // from the bucket to picking up the third sample

                .waitSeconds(1)

                .strafeToSplineHeading(new Vector2d(-56, -56), iR(45)) // from picking up the third sample to the bucket

                .waitSeconds(1)

                .splineTo(new Vector2d(56, -56), 0) // from the bucket to parking

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .addEntity(myBot)
                .start();
    }
}