package opmodes;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import team25core.DeadReckonPath;
import team25core.DistanceSensorCriteria;
import team25core.GamepadTask;
import team25core.MechanumGearedDrivetrain;
import team25core.OneWheelDirectDrivetrain;
import team25core.OneWheelDriveTaskwLimitSwitch;
import team25core.RobotEvent;
import team25core.RunToEncoderValueTask;
import team25core.SingleShotTimerTask;
import team25core.StandardFourMotorRobot;
import team25core.TeleopDriveTask;
import team25core.TwoStickMechanumControlScheme;

@TeleOp(name = "CenterstageTeleOpNew")
//@Disabled
public class CenterstageTeleOpNew extends StandardFourMotorRobot {
    //new teleop
    public float rotateShooterPos;
    private TeleopDriveTask drivetask;

    private enum Direction {
        CLOCKWISE,
        COUNTERCLOCKWISE,
    }

    private BNO055IMU imu;

    private Telemetry.Item locationTlm;
    private Telemetry.Item targetPositionTlm;

    TwoStickMechanumControlScheme scheme;

    private MechanumGearedDrivetrain drivetrain;

    private DcMotor linearLift;
    private DcMotor intake;

    private DcMotor cam;

    private Servo box;
    private Servo pixelRelease;
    private Servo releaseHanger;
    private Servo shooter;

    private Servo rotateClaw;
    private Servo leftClaw;
    private Servo rightClaw;


    //  @Override
    public void handleEvent(RobotEvent e) {

    }

    @Override
    public void init() {

        super.init();
        initIMU();

        intake=hardwareMap.get(DcMotor.class, "outtake");
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        box = hardwareMap.servo.get("pixelBox");
        box.setPosition(0.64);

        pixelRelease = hardwareMap.servo.get("pixelRelease");
        pixelRelease.setPosition(0.25);

        //releaseHanger = hardwareMap.servo.get("releaseHanger");
        //shooter = hardwareMap.servo.get("shootDrone");

        cam = hardwareMap.get(DcMotor.class, "cam");

        releaseHanger = hardwareMap.servo.get("releaseHanger");
        releaseHanger.setPosition(0.95);

        rotateClaw = hardwareMap.servo.get("rotateClaw");
        leftClaw = hardwareMap.servo.get("leftClaw");
        rightClaw = hardwareMap.servo.get("rightClaw");


        linearLift = hardwareMap.get(DcMotor.class, "linearLift");
        linearLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        scheme = new TwoStickMechanumControlScheme(gamepad1);

        //code for forward mecanum drivetrain:
        drivetrain = new MechanumGearedDrivetrain(motorMap);
        drivetask = new TeleopDriveTask(this, scheme, frontLeft, frontRight, backLeft, backRight);
        drivetask.slowDown(false);

        drivetrain.setNoncanonicalMotorDirection();

        locationTlm = telemetry.addData("location","init");
        targetPositionTlm = telemetry.addData("target Pos","init");
    }
    public void initIMU()
    {
        // Retrieve the IMU from the hardware map
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        // Technically this is the default, however specifying it is clearer
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        // Without this, data retrieving from the IMU throws an exception
        imu.initialize(parameters);

    }

    private void delay(int delayInMsec) {
        this.addTask(new SingleShotTimerTask(this, delayInMsec) {
            @Override
            public void handleEvent(RobotEvent e) {
                SingleShotTimerEvent event = (SingleShotTimerEvent) e;
            }
        });
    }

    @Override
    public void start() {
        this.addTask(drivetask);
        locationTlm.setValue("in start");

        this.addTask(new GamepadTask(this, GamepadTask.GamepadNumber.GAMEPAD_1) {
            public void handleEvent(RobotEvent e) {
                GamepadEvent gamepadEvent = (GamepadEvent) e;
                locationTlm.setValue("in gamepad1 handler");
                switch (gamepadEvent.kind) {

                }
            }
        });

        rotateShooterPos = 0;
        //gamepad2 w /nowheels only mechs
        this.addTask(new GamepadTask(this, GamepadTask.GamepadNumber.GAMEPAD_2) {
            public void handleEvent(RobotEvent e) {
                GamepadEvent gamepadEvent = (GamepadEvent) e;
                locationTlm.setValue("in gamepad2 handler");
                switch (gamepadEvent.kind) {
                    // intake in and out
                    case LEFT_TRIGGER_DOWN:
                        intake.setPower(0.6);
                        break;
                    case RIGHT_TRIGGER_DOWN:
                        intake.setPower(-0.6);
                        break;
                    case LEFT_TRIGGER_UP:
                        intake.setPower(0);
                        break;
                    case RIGHT_TRIGGER_UP:
                        intake.setPower(0);
                        break;
//                    case DPAD_LEFT_DOWN:
//                        intake.setPower(0);
//                        break;
                    // slides up or down
                    case LEFT_BUMPER_DOWN:
                        linearLift.setPower(1);
                        break;
                    case RIGHT_BUMPER_DOWN:
                        linearLift.setPower(-1);
                        if (linearLift.getCurrentPosition() < 11) {
                            //box.setPosition(0.59);
                        }
                        else {
                            //box.setPosition(0.58);
                        }
                        break;
                    case LEFT_BUMPER_UP:
                        linearLift.setPower(0);
                        break;
                    case RIGHT_BUMPER_UP:
                        linearLift.setPower(0);
                        break;
                    // pixel deployer box
                    case DPAD_UP_DOWN:
                        box.setPosition(0.03);
                        //deploy pixel
                        break;
                    case DPAD_DOWN_DOWN:
                        box.setPosition(0.63);
                        break;
                    case DPAD_LEFT_DOWN:
                        pixelRelease.setPosition(0.2);
                        break;
                    case DPAD_RIGHT_DOWN:
                        pixelRelease.setPosition(0.5);
                        break;
                    // drone shooter and rotate mech
                    case BUTTON_B_DOWN:
                        if(rotateClaw.getPosition()==0.13){
                            rotateClaw.setPosition(0.67);
                        } else if (rotateClaw.getPosition()==0.67) {
                            rotateClaw.setPosition(0.13);
                        }
                        break;
                    case BUTTON_Y_DOWN:
                        releaseHanger.setPosition(0.5);
                        break;
                    case BUTTON_A_DOWN:
                        rightClaw.setPosition(0.47);
                        break;
                    case BUTTON_X_DOWN:
                        leftClaw.setPosition(0.37);
                        break;
                }
            }
        });
    }
}