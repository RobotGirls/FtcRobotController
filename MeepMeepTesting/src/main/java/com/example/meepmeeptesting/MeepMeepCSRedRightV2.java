package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepCSRedRight {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Required: Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, -64, Math.toRadians(90)))
                                //LEFT SPIKE
/*
                                .forward(32)
                                .turn(Math.toRadians(90))
                                // * deploy purple pixel
                                .forward(-3)
                                .strafeLeft(25)
                                .lineToLinearHeading(new Pose2d(50, -33, Math.toRadians(180)))
                                // * deploy yellow pixel
*/

                                // CENTER SPIKE
/*
                                .forward(32)
                                .forward(-6)
                                // * deploy purple pixel
                                .waitSeconds(1)
                                .forward(-20)
                                .lineToLinearHeading(new Pose2d(50, -33, Math.toRadians(180)))
                                // * deploy yellow pixel
*/

                                // RIGHT SPIKE

                                .forward(32)
                                .turn(Math.toRadians(-90))
                                .forward(-3)
                                .strafeRight(25)
                                .lineToLinearHeading(new Pose2d(50, -33, Math.toRadians(180)))
                                .build()


                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}