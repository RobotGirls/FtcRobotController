package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepCSBlueLeftWorlds {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Required: Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 16)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(12, 60, Math.toRadians(90)))
                                //LEFT SPIKE
                                /*
                                .setReversed(false)
                                .lineTo(new Vector2d(12,30))
                                .turn(Math.toRadians(90))
                                // * deploy purple pixel
                                .forward(-3)
                                //.lineToLinearHeading(new Pose2d(10,50,Math.toRadians(180)))
                                .waitSeconds(1)
                                .forward(1)
                                .lineToLinearHeading(new Pose2d(12,40,Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(50, 40), Math.toRadians(360))
                                //.lineToLinearHeading(new Pose2d(50, 38, Math.toRadians(180)))
                                // * DEPLOY YELLOW
                                .waitSeconds(0.5)
                                .splineToConstantHeading(new Vector2d(37,59),Math.toRadians(180))
                                .splineToConstantHeading(new Vector2d(-50,59),Math.toRadians(180))
                                .turn(Math.toRadians(45))
                                .lineToLinearHeading(new Pose2d(-58,42,Math.toRadians(200)))
                                // * INTAKE STACK PIXEL
                                .lineToLinearHeading(new Pose2d(-50,59,Math.toRadians(180)))
                                //.splineToConstantHeading(new Vector2d(50,36),Math.toRadians(180))
                                .splineToConstantHeading(new Vector2d(34,59),Math.toRadians(180))
                                .splineToConstantHeading(new Vector2d(43,36),Math.toRadians(180))
                                .splineToConstantHeading(new Vector2d(48,36),Math.toRadians(180))


                                 */
/*
                                // CENTER SPIKE
                                .setReversed(false)
                                .lineTo(new Vector2d(12,30))
                                // * DETECT
                                .lineTo(new Vector2d(12, 40))
                                // * RELEASE PURPLE
                                .lineToLinearHeading(new Pose2d(50, 36,Math.toRadians(180)))
                                // * RELEASE YELLOW
                                .waitSeconds(0.4)
                                .setTangent(90)
                                .splineToConstantHeading(new Vector2d(30,59),Math.toRadians(180))
                                .lineToLinearHeading(new Pose2d(-34,59,Math.toRadians(180)))
                                .splineToLinearHeading(new Pose2d(-58,35),Math.toRadians(180))
                                // * INTAKE STACK PIXEL
                                .waitSeconds(0.4)
                                .setTangent(0)
                                .splineToConstantHeading(new Vector2d(-34,59),Math.toRadians(0))
                                .lineToLinearHeading(new Pose2d(35,59,Math.toRadians(180)))
                                .setTangent(0)
                                .splineToConstantHeading(new Vector2d(49, 38), Math.toRadians(0))
                                // * DEPLOY 2 STACK PIXELS
                                .forward(1)
                                .lineToLinearHeading(new Pose2d(49,60, Math.toRadians(180)))
 */
                                // RIGHT SPIKE
                                .setReversed(false)
                                .lineTo(new Vector2d(12,30))
                                // * DETECT
                                .turn(Math.toRadians(-90))
                                // * RELEASE PURPLE
                                .lineToLinearHeading(new Pose2d(50, 36, Math.toRadians(180)))
                                .waitSeconds(0.3)
                                // * RELEASE YELLOW
                                .splineToConstantHeading(new Vector2d(37,59),Math.toRadians(180))
                                .lineToLinearHeading(new Pose2d(-50,59,Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(-56,45),Math.toRadians(180))
                                .turn(Math.toRadians(30))
                                // * INTAKE STACK PIXEL
                                .waitSeconds(0.2)
                                .turn(Math.toRadians(-30))
                                .splineToConstantHeading(new Vector2d(-50,59),Math.toRadians(90))
                                .lineToLinearHeading(new Pose2d(37,59,Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(50,36),Math.toRadians(0))
                                .build()

                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}