package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepCSBlueRight {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Required: Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, 58, Math.toRadians(270)))

                                //LEFT SPIKE
                                .forward(27)
                                .turn(Math.toRadians(90))
                                // * deploy purple pixel
                                .forward(-3)
                                //.strafeRight(30)
                                .lineToLinearHeading(new Pose2d(-39, 10, Math.toRadians(180)))
                                .forward(-87)
                                .strafeRight(30)
                                // * deploy yellow pixel



                                // CENTER SPIKE
/*
                                .forward(24)
                                // * deploy purple pixel
                                .forward(-5)
                                .strafeRight(20)
                                .forward(28)
                                .turn(Math.toRadians(-90))
                                .forward(-103)
                                .strafeRight(22)
                                // * deploy yellow pixel
 */

                                // RIGHT SPIKE
                                /*
                                .forward(26)
                                .turn(Math.toRadians(-90))
                                .forward(-3)
                                .lineToLinearHeading(new Pose2d(-34, 10, Math.toRadians(180)))
                                .forward(-85)
                                .strafeRight(22)

                                 */

                                .build()


                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}