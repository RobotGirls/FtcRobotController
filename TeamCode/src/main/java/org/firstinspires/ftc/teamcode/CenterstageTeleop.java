/*
 * Copyright (c) September 2017 FTC Teams 25/5218
 *
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification,
 *  are permitted (subject to the limitations in the disclaimer below) provided that
 *  the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this list
 *  of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice, this
 *  list of conditions and the following disclaimer in the documentation and/or
 *  other materials provided with the distribution.
 *
 *  Neither the name of FTC Teams 25/5218 nor the names of their contributors may be used to
 *  endorse or promote products derived from this software without specific prior
 *  written permission.
 *
 *  NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 *  LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  AS IS AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 *  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 *  FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *  DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *  CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 *  TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

//import org.firstinspires.ftc.robotcore.external.telemetry;

import team25core.GamepadTask;
import team25core.MechanumGearedDrivetrain;
import team25core.PersistentTelemetryTask;
import team25core.RobotEvent;
import team25core.StandardFourMotorRobot;
import team25core.TwoStickMechanumControlScheme;
import team25core.TeleopDriveTask;

@TeleOp(name = "TestRig4 TeleOp", group="Centerstage")
public class CenterstageTeleop extends StandardFourMotorRobot {

    public enum ClawState {
        CLAW_OPEN,
        CLAW_CLOSED
    }

    ClawState clawPosition = ClawState.CLAW_CLOSED;

    private static final int CLAW_LIMIT = 260;
    private static final int INTAKE_TOP = 2;
    private static final int INTAKE_ALIGN = 108;
    private static final int INTAKE_BOTTOM = 144;


    private TeleopDriveTask drivetask;

    private DcMotor claw;
    private DcMotor intake;
//    private DcMotor arm;

    private Servo wrist;
    IMU imu;
    private MechanumGearedDrivetrain drivetrain;

    private PersistentTelemetryTask ptt;

    public void resetMotorPosition(DcMotor motor, DcMotor.RunMode mode) {
        try {
            telemetry.addData("Status", "resetting motor");
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setPower(-0.4);
            Thread.sleep(500);
            motor.setPower(0);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(mode);
            telemetry.addData("Status", "motor initialized");
        } catch (InterruptedException e) {
            telemetry.addData("error", "interrupted");
        }

    }

    public void toggleClaw() {
        switch (clawPosition) {
            case CLAW_OPEN:
                claw.setTargetPosition(0);
                clawPosition = ClawState.CLAW_CLOSED;
                break;
            case CLAW_CLOSED:
                claw.setTargetPosition(CLAW_LIMIT);
                clawPosition = ClawState.CLAW_OPEN;
            break;
            default:
                clawPosition = ClawState.CLAW_OPEN;
        }
        telemetry.addData("claw target",claw.getTargetPosition());
    }

    @Override
    public void handleEvent(RobotEvent e) {
    }

    @Override
    public void init() {
        super.init();

        // sensors
        imu = hardwareMap.get(IMU.class, "imu");
        initIMU(imu);

        //mechanisms
        drivetrain = new MechanumGearedDrivetrain(motorMap);
        
        claw = hardwareMap.get(DcMotor.class, "claw");
        intake = hardwareMap.get(DcMotor.class, "intake");
//        arm = hardwareMap.get(DcMotor.class, "arm");

        wrist = hardwareMap.get(Servo.class,"wrist");

//        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        claw.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        claw.setTargetPosition(0);
        intake.setTargetPosition(0);
        resetMotorPosition(claw, DcMotor.RunMode.RUN_TO_POSITION);
        resetMotorPosition(intake, DcMotor.RunMode.RUN_TO_POSITION);
//        claw.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        // for AndyMark motors, reverse the right-hand motors
        drivetrain.setCanonicalMotorDirection();

        // enable encoders to maintain motor velocity
        drivetrain.encodersOn();

        // left stick translation, right stick turning control scheme
        TwoStickMechanumControlScheme scheme = new TwoStickMechanumControlScheme(gamepad1, TwoStickMechanumControlScheme.StickOrientation.TRANSLATE_ON_RIGHT);

        drivetask = new TeleopDriveTask(this, scheme, frontLeft, frontRight, backLeft, backRight);
        // default to slow driving
        drivetask.slowDown(true);
        
        ptt = new PersistentTelemetryTask(this);
        
    }

    public void initIMU(IMU imu)
    {
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;

        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

        // Now initialize the IMU with this mounting orientation
        // Note: if you choose two conflicting directions, this initialization will cause a code exception.
        imu.initialize(new IMU.Parameters(orientationOnRobot));

    }

    @Override
    public void start() {

        claw.setPower(0.4);
        intake.setPower(0.6);

        //Gamepad 1
        this.addTask(drivetask);

        this.addTask(new GamepadTask(this, GamepadTask.GamepadNumber.GAMEPAD_1) {
            public void handleEvent(RobotEvent e) {
                GamepadEvent gamepadEvent = (GamepadEvent) e;

                switch (gamepadEvent.kind) {
                    case RIGHT_TRIGGER_DOWN:
                        drivetask.slowDown(false);
                        telemetry.addData("speed","fast");
                        break;
                    case RIGHT_TRIGGER_UP:
                        drivetask.slowDown(true);
                        telemetry.addData("speed","slow");
                        break;
                    case BUTTON_Y_DOWN:
                        intake.setTargetPosition(INTAKE_TOP);
                        telemetry.addData("intake","top");
                        break;
                    case BUTTON_B_DOWN:
                        intake.setTargetPosition(INTAKE_ALIGN);
                        telemetry.addData("intake","align");
                        break;
                    case BUTTON_A_DOWN:
                        intake.setTargetPosition(INTAKE_BOTTOM);
                        telemetry.addData("intake","bottom");
                        break;
                    case LEFT_BUMPER_DOWN:
                        toggleClaw();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}

