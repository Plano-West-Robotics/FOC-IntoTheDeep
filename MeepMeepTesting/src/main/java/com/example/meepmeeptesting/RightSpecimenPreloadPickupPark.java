package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class RightSpecimenPreloadPickupPark
{
    public static double inRad(double degrees)
    {
        return Math.toRadians(degrees);
    }

    public static void main(String[] args)
    {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, trackWidth
                .setConstraints(50, 15, inRad(135), inRad(135), 15)
                .build();

        Pose2d initPose = new Pose2d(24, -61, inRad(90));

        /*
         On init: the outtake arm of the robot should be retracted (to fit in the limit)
         and the outtake claw should clamp down on the sample that a human will hold

         On start:
         as it moves towards the bar the outtake arm should extend
         to its forward outward position (the special mechanism),
         the slides should move to the bar position

         it should then move up the v slides to hook the specimen from upside down
         then it should open the outtake claw and reset the outtake arm

         as the robot moves to the first pickup position, the outtake arm should be
         extended backwards and the slides should lower to the pickup position

         when it reaches the pickup position the outtake claw should close

         as it moves to the bar again it should extend the outtake arm forward, move the
         slides to the bar position,

         same as above: it should then move up the v slides to hook the specimen from upside down
         then it should open the outtake claw and reset the outtake arm

         then it'll just strafe to the parking zone

         */
        myBot.runAction(myBot.getDrive().actionBuilder(initPose)
                .splineToConstantHeading(new Vector2d(10, -35), inRad(90))
                        .waitSeconds(2)
                .strafeTo(new Vector2d(34, -61))
                        .waitSeconds(2)
                .strafeTo(new Vector2d(10, -35))
                        .waitSeconds(2)
                .strafeTo(new Vector2d(58, -58))
                .build()
        );

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .addEntity(myBot)
                .start();
    }
}