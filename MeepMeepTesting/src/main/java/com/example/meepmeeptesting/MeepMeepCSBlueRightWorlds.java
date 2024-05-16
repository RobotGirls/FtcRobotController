package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepCSBlueRightWorlds {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Required: Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                                drive.trajectorySequenceBuilder(new Pose2d(-35, 58, Math.toRadians(90)))
                                        .lineToLinearHeading(new Pose2d(-35,27, Math.toRadians(90)))

                                //LEFT SPIKE
                                        /*
                                        .forward(2)
                                        .turn(Math.toRadians(90))
                                        .forward(-0.5)
                                        .waitSeconds(0.5)
                                        .forward(2)
                                        .lineToLinearHeading(new Pose2d(-35,55, Math.toRadians(180)))
                                        .lineToLinearHeading(new Pose2d(30, 55, Math.toRadians(180)))
                                        // * deploy yellow pixel
                                        .waitSeconds(1)
                                        .lineToLinearHeading(new Pose2d(49, 31, Math.toRadians(180)))
                                        .waitSeconds(1)

                                        .waitSeconds(0.5)
                                        .forward(3)
                                        .strafeRight(18)

                                         */





                                // CENTER SPIKE
/*
                                        .forward(3)
                                        // * deploy purple pixel
                                        .waitSeconds(0.3)
                                        .forward(5)
                                        .lineToLinearHeading(new Pose2d(-45, 55, Math.toRadians(180)))
                                        .lineToLinearHeading(new Pose2d(30, 55, Math.toRadians(180)))
                                        .lineToLinearHeading(new Pose2d(49, 33, Math.toRadians(180)))
                                        // * deploy yellow pixel
                                        .forward(1)
                                        .waitSeconds(1)
                                        .forward(2)
                                        .strafeRight(22)

 */


                                // RIGHT SPIKE

                                        .turn(Math.toRadians(-90))
                                        .forward(-2)
                                        .waitSeconds(1)
                                        .forward(2)
                                        .lineToLinearHeading(new Pose2d(-40,55,Math.toRadians(0)))
                                        .turn(Math.toRadians(180))
                                        .lineToLinearHeading(new Pose2d(25,55,Math.toRadians(180)))
                                        .waitSeconds(1)
                                        .splineToConstantHeading(new Vector2d(54, 35), Math.toRadians(0))
                                        // * deploy yellow pixel
                                        .waitSeconds(1)
                                        .forward(5)
                                        .strafeRight(30)


                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
