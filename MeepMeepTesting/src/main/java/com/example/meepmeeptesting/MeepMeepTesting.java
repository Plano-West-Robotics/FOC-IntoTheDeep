package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        Pose2d initPose = new Pose2d(20, -61, Math.toRadians(90));

        Vector2d firstVector = new Vector2d(10, -34);
        Vector2d secondVector = new Vector2d(25, -40);
        Vector2d thirdVector = new Vector2d(40, -26);

        var ninety = Math.toRadians(90);
        var oneEighty = Math.toRadians(180);
        var twoSeventy = Math.toRadians(270);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 30, Math.toRadians(180), Math.toRadians(180), 15)
                .build();
/*
        myBot.runAction(myBot.getDrive().actionBuilder(initPose)
                .lineToYSplineHeading(33, Math.toRadians(0))
                .waitSeconds(2)
                .setTangent(Math.toRadians(90))
                .lineToY(48)
                .setTangent(Math.toRadians(0))
                .lineToX(32)
                .strafeTo(new Vector2d(44.5, 30))
                .turn(Math.toRadians(180))
                .lineToX(47.5)
                .waitSeconds(3)
                .build());
*/

/*
        myBot.runAction(myBot.getDrive().actionBuilder(initPose)
                .waitSeconds(1)
                .splineToConstantHeading(firstVector, ninety)
                        .waitSeconds(0.5)
                        .strafeToConstantHeading(new Vector2d(32, -42))
                        //.splineToConstantHeading(new Vector2d(32, -42), ninety)
                        .splineToConstantHeading(new Vector2d(48, -38), ninety)
                        .waitSeconds(0.4)
                        .lineToYSplineHeading(-61, 0)
                        .waitSeconds(0.8)
                        .strafeToSplineHeading(new Vector2d(58, -38), ninety)
                        .waitSeconds(0.4)
                        .setTangent(ninety)
                        .lineToY(-61)

                .build());
*/

        /*
        myBot.runAction(myBot.getDrive().actionBuilder(initPose)
                .splineToSplineHeading(new Pose2d(10, -34, Math.toRadians(270)), Math.toRadians(90))
                .waitSeconds(1)
                .splineToSplineHeading(new Pose2d(48, -38, Math.toRadians(90)), Math.toRadians(90))
                .waitSeconds(0.4)
                .lineToYSplineHeading(-61, 0)
                .waitSeconds(0.4)
                .strafeToSplineHeading(new Vector2d(58, -38), ninety)
                .waitSeconds(0.4)
                .setTangent(ninety)
                .lineToY(-61)
                .waitSeconds(0.4)
                .strafeToSplineHeading(new Vector2d(48, -58), 0)
                .strafeToSplineHeading(new Vector2d(10, -34), Math.toRadians(270))
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(58, -61), Math.toRadians(90))
                .waitSeconds(0.4)
                .strafeToSplineHeading(new Vector2d(10, -34), Math.toRadians(270))
                .waitSeconds(1)
                .strafeToConstantHeading(new Vector2d(61, -61))
                .build());
*/

        myBot.runAction(myBot.getDrive().actionBuilder(initPose)
                .splineToSplineHeading(new Pose2d(10, -34, twoSeventy), ninety)
                .waitSeconds(1)
                .splineToLinearHeading(new Pose2d(36, -30, ninety), ninety)
                .splineToConstantHeading(new Vector2d(48, -10), 0)
                .setTangent(ninety)
                .lineToY(-56)
                .lineToY(-16)
                .splineToConstantHeading(new Vector2d(58, -14), Math.toRadians(300))
                .setTangent(ninety)
                .lineToY(-56)
                .strafeToConstantHeading(new Vector2d(36, -61))
                .waitSeconds(0.2)
                .splineToSplineHeading(new Pose2d(10, -34, twoSeventy), ninety)
                .waitSeconds(1)
                .strafeToSplineHeading(new Vector2d(36, -61), ninety)
                .waitSeconds(0.2)
                .splineToSplineHeading(new Pose2d(10, -34, twoSeventy), ninety)
                .waitSeconds(1)
                .strafeTo(new Vector2d(61, -61))
                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}