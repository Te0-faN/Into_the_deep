package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class TeleOP extends LinearOpMode {
    DcMotor front_left;
    DcMotor front_right;
    DcMotor back_left;
    DcMotor back_right;
    DcMotor intake_slider;
    DcMotor pivoter;

    Servo yaw;
    Servo pivot;
    Servo grip;
    Servo pitch;

    ElapsedTime elapsed_grip = new ElapsedTime();
    ElapsedTime elapsed_pitch = new ElapsedTime();
    final double TICKS_435 = 384.5;
    final double TICKS_30 = 5281.1;
    final double MAX_LENGTH = 3 * TICKS_435;
    boolean open = true;
    boolean up = true;

    private void InitWheels()
    {
        front_left = hardwareMap.dcMotor.get("frontLeftMotor");
        front_right = hardwareMap.dcMotor.get("frontRightMotor");
        back_left = hardwareMap.dcMotor.get("backLeftMotor");
        back_right = hardwareMap.dcMotor.get("backRightMotor");

        front_right.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right.setDirection(DcMotorSimple.Direction.REVERSE);
        front_left.setDirection(DcMotorSimple.Direction.REVERSE);
        back_left.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void InitDcs() {
        intake_slider = hardwareMap.dcMotor.get("intake-slide");
        pivoter = hardwareMap.dcMotor.get("pivoter");

        intake_slider.setDirection(DcMotorSimple.Direction.REVERSE);
        intake_slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Use encoder for normal operation
        pivoter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake_slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void InitSerovs() {
        pivot = hardwareMap.servo.get("pivot");
        grip = hardwareMap.servo.get("grip");
        yaw = hardwareMap.servo.get("yaw");
        pitch = hardwareMap.servo.get("pitch");
    }

    private void SetWheelsPower() {
        double left_x = gamepad1.left_stick_x;
        double left_y = -gamepad1.left_stick_y;
        double right_x = gamepad1.right_stick_x;

        double normalizer = Math.max(Math.abs(left_x) +
                Math.abs(left_y) +
                Math.abs(right_x), 1.0);

        double front_left_pw = (left_y + left_x + right_x) / normalizer;
        double back_left_pw = (left_y - left_x + right_x) / normalizer;
        double front_right_pw = (left_y - left_x - right_x) / normalizer;
        double back_right_pw = (left_y + left_x - right_x) / normalizer;

        front_left.setPower(front_left_pw);
        back_left.setPower(back_left_pw);
        front_right.setPower(front_right_pw);
        back_right.setPower(back_right_pw);
    }


    private void MoveDcs()
    {
        double left_y = -gamepad2.left_stick_y;

        if (Math.abs(left_y) > 0.1) {
            double target = intake_slider.getCurrentPosition()
                          + (int) (left_y * TICKS_435);
            if (target >= MAX_LENGTH)
                target = MAX_LENGTH;
            if (target <= 0)
                target = 0;
            intake_slider.setTargetPosition((int) target);
            intake_slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            intake_slider.setPower(0.4 * left_y);
        } else
            intake_slider.setPower(0);

        if (gamepad2.left_bumper) {
            double target = pivoter.getCurrentPosition()
                          - (int) (0.05*TICKS_30);

            pivoter.setTargetPosition((int) target);
            pivoter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            pivoter.setPower(0.5); /* TODO - calibrare */
        } else if (gamepad2.right_bumper) {
            double target = pivoter.getCurrentPosition()
                          + (int) (0.05*TICKS_30);

            pivoter.setTargetPosition((int) target);
            pivoter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            pivoter.setPower(0.5); /* TODO - calibrare */
        } else
            pivoter.setPower(0);
    }

    private void MoveServos()
    {
        double right_x = gamepad2.right_stick_x;

        if (right_x != 0) {
            final double low  = 0.1517;
            final double high = 0.7839;

            double newPos = pivot.getPosition() - right_x * 0.005;
            if (newPos <= low) {
                newPos = low;
            } else if (newPos >= high)
                newPos = high;
            pivot.setPosition(newPos);
            yaw.setPosition(1-newPos);
        }

        if (gamepad2.cross && elapsed_grip.seconds() > 0.5) {
            grip.setPosition(open ? 0.3 : 0);
            open = !open;
            elapsed_grip.reset();
        }

        if (gamepad2.triangle && elapsed_pitch.seconds() > 0.5) {
            pitch.setPosition(up ? 0.35 : 0.05); /* TODO - Nu e pus bine servoul */
            up = !up;
            elapsed_pitch.reset();
        }

        double left_trig = gamepad2.left_trigger;
        if(left_trig > 0)
            yaw.setPosition(yaw.getPosition() + 0.01*left_trig);

        double right_trig = gamepad2.right_trigger;
        if(right_trig > 0)
            yaw.setPosition(yaw.getPosition() - 0.01*right_trig);
    }

    private void UpdateTelemetry()
    {
        telemetry.addData("Time", gamepad2.timestamp);
        telemetry.update();
    }


    public void runOpMode()
    {
        waitForStart();

        InitWheels();
        InitDcs();
        InitSerovs();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            SetWheelsPower();
            MoveDcs();
            MoveServos();

            UpdateTelemetry();
        }

    }
}
