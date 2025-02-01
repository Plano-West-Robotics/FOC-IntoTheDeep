package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class LeftBucketPreloadOnly
{
    public static double inRad(double degrees)
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

        Pose2d initPose = new Pose2d(-24, -61, inRad(90));

        myBot.runAction(myBot.getDrive().actionBuilder(initPose)

                .splineToSplineHeading(new Pose2d(-56, -56, inRad(45)), inRad(225))

                .waitSeconds(2)

                // Move to pick up right sample.
                //.strafeTo(new Vector2d(-48, -48))

                // Move to basket to score sample.
                //.strafeToLinearHeading(new Vector2d(-56, -56), inRad(45))

                // Move to pick up middle sample.
                //.strafeToLinearHeading(new Vector2d(-58, -48), inRad(90))

                // Move to basket to score sample.
                //.strafeToLinearHeading(new Vector2d(-56, -56), inRad(45))

                .build()
        );

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .addEntity(myBot)
                .start();
    }
}