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
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 18)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, 58, Math.toRadians(90)))
                                //LEFT SPIKE
                                .lineToLinearHeading(new Pose2d(-35,32, Math.toRadians(90)))
                                .turn(Math.toRadians(90))
                                // * deploy purple pixel
                                .forward(-3)
                                .lineToLinearHeading(new Pose2d(-58, 35, Math.toRadians(180)))
                                // * INTAKE FROM STACK
                                .waitSeconds(0.2)
                                .setTangent(0)
                                .splineToConstantHeading(new Vector2d(-34,59),Math.toRadians(0))
                                .lineToLinearHeading(new Pose2d(25,59,Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(30,59),Math.toRadians(180))
                                .setTangent(0)
                                .splineToConstantHeading(new Vector2d(49, 38), Math.toRadians(0))
                                // * DEPLOY YELLOW
                                .waitSeconds(0.4)
                                .setTangent(90)
                                .splineToConstantHeading(new Vector2d(30,59),Math.toRadians(180))
                                .lineToLinearHeading(new Pose2d(-34,59,Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(-58,35),Math.toRadians(200))
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


                                /*
                                // CENTER SPIKE
                                .lineToLinearHeading(new Pose2d(-35,30, Math.toRadians(90)))
                                // * deploy purple pixel
                                .forward(4)
                                .lineToLinearHeading(new Pose2d(-58, 35, Math.toRadians(180)))
                                // * INTAKE FROM STACK
                                .waitSeconds(0.2)
                                .lineToLinearHeading(new Pose2d(-30,59,Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(35,59,Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(40, 38), Math.toRadians(180))
                                .lineToLinearHeading(new Pose2d(49,38, Math.toRadians(180)))
                                // * DEPLOY YELLOW
                                .waitSeconds(0.4)
                                .splineTo(new Vector2d(35,59),Math.toRadians(180))
                                .lineToLinearHeading(new Pose2d(-53,59,Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(-58,42,Math.toRadians(200)))
                                // * INTAKE STACK PIXEL
                                .lineToLinearHeading(new Pose2d(-50,59,Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(35,59,Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(35, 38), Math.toRadians(180))
                                .lineToLinearHeading(new Pose2d(49,38, Math.toRadians(180)))
                                // * DEPLOY 2 STACK PIXELS
                                .lineToLinearHeading(new Pose2d(49,50, Math.toRadians(180)))

                                 */

                                // RIGHT SPIKE
                                /*
                                .lineToLinearHeading(new Pose2d(-35,32, Math.toRadians(90)))
                                .turn(Math.toRadians(-90))
                                // * deploy purple pixel
                                .forward(-3)
                                .forward(4)
                                .lineToLinearHeading(new Pose2d(-35,55, Math.toRadians(0)))
                                .lineToLinearHeading(new Pose2d(-58,42,Math.toRadians(200)))
                                // * INTAKE FROM STACK
                                .waitSeconds(0.2)
                                .lineToLinearHeading(new Pose2d(-34,59,Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(35,59,Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(35, 38), Math.toRadians(180))
                                .lineToLinearHeading(new Pose2d(49,38, Math.toRadians(180)))
                                // * DEPLOY YELLOW
                                .waitSeconds(0.4)
                                .splineToConstantHeading(new Vector2d(35,59),Math.toRadians(180))
                                .lineToLinearHeading(new Pose2d(-53,59,Math.toRadians(180)))
                                .turn(Math.toRadians(45))
                                .lineToLinearHeading(new Pose2d(-58,42,Math.toRadians(200)))
                                // * INTAKE STACK PIXEL
                                .lineToLinearHeading(new Pose2d(-50,59,Math.toRadians(180)))
                                .lineToLinearHeading(new Pose2d(35,59,Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(35, 38), Math.toRadians(180))
                                .lineToLinearHeading(new Pose2d(49,38, Math.toRadians(180)))
                                // * DEPLOY 2 STACK PIXELS
                                .lineToLinearHeading(new Pose2d(49,50, Math.toRadians(180)))

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