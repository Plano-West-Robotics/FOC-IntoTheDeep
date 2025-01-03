package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class LeftBucket
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

        Pose2d initPose = new Pose2d(-24, -62, inRad(90));

        myBot.runAction(myBot.getDrive().actionBuilder(initPose)
                // Move to basket to score preloaded sample
                .splineToLinearHeading(new Pose2d(-54, -54, inRad(45)), inRad(225))

                // Move to pick up rightmost sample
                .strafeToLinearHeading(new Vector2d(-48, -48), inRad(90))
                // Move to basket to score sample
                .strafeToLinearHeading(new Vector2d(-54, -54), inRad(45))

                // Move to pick up middle sample
                .strafeToLinearHeading(new Vector2d(-58, -48), inRad(90))
                // Move to basket to score sample
                .strafeToLinearHeading(new Vector2d(-54, -54), inRad(45))

                // Park
                .strafeTo(new Vector2d(-56, -56))

                .build()
        );

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .addEntity(myBot)
                .start();
    }
}