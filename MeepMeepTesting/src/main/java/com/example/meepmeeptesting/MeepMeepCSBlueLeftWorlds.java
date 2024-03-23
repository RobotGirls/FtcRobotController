package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepCSBlueLeft {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Required: Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, 58, Math.toRadians(270)))
                                //LEFT SPIKE
                                /*
                                .forward(27)
                                .turn(Math.toRadians(90))
                                // * deploy purple pixel
                                .forward(-3)
                                .strafeLeft(25)
                                .lineToLinearHeading(new Pose2d(50, 36, Math.toRadians(180)))
                                // * deploy yellow pixel

                                 */
                                // CENTER SPIKE

                                // CENTER SPIKE PATH
                                .forward(33)
                                .forward(-6.5)
                                // * deploy purple pixel
                                .forward(-20)
                                .lineToLinearHeading(new Pose2d(25, 38, Math.toRadians(270))) // x 26
                                // * deploy yellow pixel
                                .forward(1)
                                .waitSeconds(2.5)
                                .forward(2)
                                // * deploy yellow pixel



                                // RIGHT SPIKE
                                /*
                                .forward(26)
                                .turn(Math.toRadians(-90))
                                .forward(-3)
                                .lineToLinearHeading(new Pose2d(50, 36, Math.toRadians(180)))
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