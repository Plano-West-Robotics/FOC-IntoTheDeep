package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class LeftBucketPreloadPickupPark
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
                .setConstraints(50, 15, Math.toRadians(135), Math.toRadians(135), 15)
                .build();

        Pose2d initPose = new Pose2d(-24, -61, iR(90));

        myBot.runAction(myBot.getDrive().actionBuilder(initPose)
                .setTangent(iR(135))

                .splineToLinearHeading(new Pose2d(-56, -56, iR(45)), iR(225))

                .waitSeconds(2)

                .splineToSplineHeading(new Pose2d(-48, -38, iR(90)), iR(90))

                .waitSeconds(2)

                .strafeToSplineHeading(new Vector2d(-56, -56), iR(45))

                .waitSeconds(2)

                .splineToConstantHeading(new Vector2d(-36, -12), iR(90))

                .turnTo(iR(180))

                .strafeTo(new Vector2d(-25, -12))

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .addEntity(myBot)
                .start();
    }
}