package opmodes;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;

import team25core.GamepadTask;
import team25core.MechanumGearedDrivetrain;
import team25core.MotorPackage;
import team25core.RobotEvent;
import team25core.SingleShotTimerTask;
import team25core.StandardFourMotorRobot;
import team25core.TeleopDriveTask;
import team25core.TwoStickMechanumControlScheme;

@TeleOp(name = "CenterstageTeleopNew")
//@Disabled
public class CenterstageTeleopNew extends StandardFourMotorRobot {
//new teleop
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

    private Servo box;
    private Servo pixelRelease;

    private DcMotor rightHang;
    private DcMotor leftHang;

    private Servo rotateClaw;
    private Servo leftClaw;
    private Servo rightClaw;

    //  @Override
    public void handleEvent(RobotEvent e) {

    }

    @Override
    public void init() {

        //super.init();
        motorMap = new HashMap<>();

        frontLeft = hardwareMap.get(DcMotorEx.class, "leftFront");
        motorMap.put(MotorPackage.MotorLocation.FRONT_LEFT, new MotorPackage(frontLeft));

        frontRight = hardwareMap.get(DcMotorEx.class, "rightFront");
        motorMap.put(MotorPackage.MotorLocation.FRONT_RIGHT, new MotorPackage(frontRight));

        backLeft = hardwareMap.get(DcMotorEx.class, "leftRear");
        motorMap.put(MotorPackage.MotorLocation.BACK_LEFT, new MotorPackage(backLeft));

        backRight = hardwareMap.get(DcMotorEx.class, "rightRear");
        motorMap.put(MotorPackage.MotorLocation.BACK_RIGHT, new MotorPackage(backRight));

        initIMU();

        intake=hardwareMap.get(DcMotor.class, "outtake");
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // flip mechanism
        box = hardwareMap.servo.get("pixelBox");
        box.setPosition(0.08);

        // pixel release mechanism (mounted on box)
        pixelRelease = hardwareMap.servo.get("pixelRelease");
        pixelRelease.setPosition(0.8);
        
        rightHang = hardwareMap.get(DcMotor.class, "rightHang");
        rightHang.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightHang.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightHang.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftHang = hardwareMap.get(DcMotor.class, "leftHang");
        leftHang.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftHang.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftHang.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
                    case LEFT_TRIGGER_DOWN:
                        backRight.setPower(0.75);
                        break;
                    case LEFT_TRIGGER_UP:
                        backRight.setPower(0);
                        break;
                    case RIGHT_TRIGGER_DOWN:
                        frontRight.setPower(0.75);
                        break;
                    case RIGHT_TRIGGER_UP:
                        frontRight.setPower(0);
                        break;
                }
            }
        });

        //gamepad2 w /nowheels only mechs
        this.addTask(new GamepadTask(this, GamepadTask.GamepadNumber.GAMEPAD_2) {
            public void handleEvent(RobotEvent e) {
                GamepadEvent gamepadEvent = (GamepadEvent) e;
                locationTlm.setValue("in gamepad2 handler");
                switch (gamepadEvent.kind) {
                    // intake in and out
                    case LEFT_TRIGGER_DOWN:
                        intake.setPower(0.75);
                        break;
                    case RIGHT_TRIGGER_DOWN:
                        intake.setPower(-0.75);
                        break;
                    case LEFT_TRIGGER_UP:
                        intake.setPower(0);
                        break;
                    case RIGHT_TRIGGER_UP:
                        intake.setPower(0);
                        break;
                    // slides up or down
                    case LEFT_BUMPER_DOWN:
                        linearLift.setPower(1);
                        break;
                    case RIGHT_BUMPER_DOWN:
                        linearLift.setPower(-1);
                        break;
                    case LEFT_BUMPER_UP:
                        linearLift.setPower(0);
                        break;
                    case RIGHT_BUMPER_UP:
                        linearLift.setPower(0);
                        break;
                    // pixel deployer box
                    case DPAD_UP_DOWN:
                        box.setPosition(0.08);
                        //deploy pixel
                        break;
                    case DPAD_DOWN_DOWN:
                        box.setPosition(0.9);
                        break;
                    case DPAD_LEFT_DOWN:
                        pixelRelease.setPosition(0.95);
                        break;
                    case DPAD_LEFT_UP:
                        pixelRelease.setPosition(0.8);
                        break;
                    case DPAD_RIGHT_DOWN:
                        pixelRelease.setPosition(0.7);
                        break;
                    case DPAD_RIGHT_UP:
                        pixelRelease.setPosition(0.8);
                        break;
                        // hanger up
                    case BUTTON_Y_DOWN:
                        leftHang.setPower(1);
                        rightHang.setPower(-1);
                        break;
                    case BUTTON_Y_UP:
                        leftHang.setPower(0);
                        rightHang.setPower(0);
                        break;
                        // hanger down
                    case BUTTON_A_DOWN:
                        leftHang.setPower(-1);
                        rightHang.setPower(1);
                        break;
                    case BUTTON_A_UP:
                        leftHang.setPower(0);
                        rightHang.setPower(0);
                        break;
                    case BUTTON_X_DOWN:
                        leftHang.setPower(1);
                        break;
                    case BUTTON_X_UP:
                        leftHang.setPower(0);
                        break;
                    case BUTTON_B_DOWN:
                        leftHang.setPower(-1);
                        break;
                    case BUTTON_B_UP:
                        leftHang.setPower(0);
                        break;
                }
            }
        });
    }
}