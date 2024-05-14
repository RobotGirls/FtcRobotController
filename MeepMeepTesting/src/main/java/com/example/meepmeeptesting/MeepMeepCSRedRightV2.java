package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepCSRedRightV2 {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Required: Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, -60, Math.toRadians(270)))
                                //LEFT SPIKE
                                /*
                                .lineToLinearHeading(new Pose2d(12, -29, Math.toRadians(270)))
                                .forward(3.5)
                                .turn(Math.toRadians(90))
                                .forward(-3)
                                // * release purple pixel
                                .waitSeconds(0.5)
                                .forward(3)
                                .waitSeconds(0.5)
                                .lineToLinearHeading(new Pose2d(55, -29, Math.toRadians(180)))
                                // * deploy yellow pixel
                                .waitSeconds(0.5)
                                .setTangent(180)
                                .splineToConstantHeading(new Vector2d(30,-58),Math.toRadians(180))
                                .waitSeconds(0.5)
                                .lineToLinearHeading(new Pose2d(-34,-58,Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(-57.8, -39,Math.toRadians(153)))
                                // intake stack pixels
                                .lineToLinearHeading(new Pose2d(-34, -58,Math.toRadians(180)))
                                .waitSeconds(1)
                                .lineToLinearHeading(new Pose2d(30,-58,Math.toRadians(180)))
                                //.setTangent(0)
                                .splineToConstantHeading(new Vector2d(50, -34), Math.toRadians(0))

                                 */

                                // * deploy yellow pixel

                                // CENTER SPIKE
/*
                                .lineToLinearHeading(new Pose2d(12, -29, Math.toRadians(270)))
                                .forward(4)
                                // * deploy purple pixel
                                .waitSeconds(0.5)
                                .forward(8)
                                .waitSeconds(0.5)
                                .lineToLinearHeading(new Pose2d(54, -36, Math.toRadians(180)))
                                // * deploy yellow pixel
                                .waitSeconds(0.5)
                                .setTangent(90)
                                .splineToConstantHeading(new Vector2d(30,-58),Math.toRadians(180))
                                .waitSeconds(0.5)
                                .lineToLinearHeading(new Pose2d(-34,-58,Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(-47,-34),Math.toRadians(180))
                                .forward(5)
                                .waitSeconds(1)
                                // intake stack pixels
                                .back(1.5)
                                .waitSeconds(0.5)
                                .setTangent(270)
                                .splineToConstantHeading(new Vector2d(-40,-58.8),Math.toRadians(0))
                                .waitSeconds(0.5)
                                .lineToLinearHeading(new Pose2d(30,-58.8,Math.toRadians(180)))
                                .setTangent(0)
                                .splineToConstantHeading(new Vector2d(51, -34), Math.toRadians(0))
                                // * deploy yellow pixel


 */


                                // RIGHT SPIKE
                                /*
                                .lineToLinearHeading(new Pose2d(12, -29, Math.toRadians(270)))
                                .forward(3)
                                .turn(Math.toRadians(-90))
                                .forward(-4)
                                .waitSeconds(0.4)
                                .forward(2)
                                .lineToLinearHeading(new Pose2d(14,-40,Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(55, -40), Math.toRadians(360))
                                // * deploy yellow pixel
                                .waitSeconds(0.5)
                                .setTangent(180)
                                .splineToConstantHeading(new Vector2d(30,-58),Math.toRadians(270))
                                .waitSeconds(0.5)
                                .lineToLinearHeading(new Pose2d(-34,-58,Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(-47,-34),Math.toRadians(180))
                                .forward(5)
                                .waitSeconds(1)
                                // intake stack pixels
                                .waitSeconds(0.5)
                                .setTangent(270)
                                .splineToConstantHeading(new Vector2d(-40,-58.8),Math.toRadians(0))
                                .lineToLinearHeading(new Pose2d(30,-58.8,Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(50, -34), Math.toRadians(0))

                                 */
                                .lineToLinearHeading(new Pose2d(12, -29, Math.toRadians(270)))
                                .forward(3)
                                .turn(Math.toRadians(-90))
                                .forward(-3)
                                .waitSeconds(0.4)
                                .forward(2)
                                .lineToLinearHeading(new Pose2d(14,-40,Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(55, -40), Math.toRadians(0))
                                //.lineToLinearHeading(new Pose2d(55, -40, Math.toRadians(180)))
                                // * deploy yellow pixel
                                .waitSeconds(0.5)
                                .forward(5)
                                .strafeRight(27.5)


                                .build()




                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}