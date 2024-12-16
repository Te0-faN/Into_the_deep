package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

class TeleOp extends LinearOpMode
{
    final double limit = Math.toRadians(10);
    DcMotor front_left;
    DcMotor front_right;
    DcMotor back_left;
    DcMotor back_right;
    DcMotor intake_slider;
    DcMotor pivot;


    private void InitializeWheels()
    {
        front_left  = hardwareMap.dcMotor.get("frontLeftMotor");
        front_right = hardwareMap.dcMotor.get("frontRightMotor");
        back_left   = hardwareMap.dcMotor.get("backLeftMotor");
        back_right  = hardwareMap.dcMotor.get("backRightMotor");

        front_right.setDirection(DcMotorSimple.Direction.REVERSE);
        back_right.setDirection(DcMotorSimple.Direction.REVERSE);
        front_left.setDirection(DcMotorSimple.Direction.FORWARD); /* Motorul e pus invers */
        back_left.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    private void InitDcs()
    {
        intake_slider = hardwareMap.dcMotor.get("intake-slide");
        pivot         = hardwareMap.dcMotor.get("pivoter");

        intake_slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private double calculatePower(double targetpos, double currentpos)
    {
        double distace = Math.abs(targetpos - currentpos);
        double minpw = 0.1;

        if (distace < 10)
            return minpw;

        double maxpw = 0.4;
        double pw = maxpw * distace / 100.0;

        return Math.min(pw, maxpw);
    }

    private void moveMotor(DcMotor m, double power) {
        if (Math.abs(power) > 0.05) {
            int currentpos = m.getCurrentPosition();
            int targetpos  = currentpos + (int)power;

            m.setTargetPosition(targetpos);
            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            m.setPower(calculatePower(targetpos, currentpos));
        }
    }
    private void SetWheelsPower()
    {
        double left_x  =  gamepad1.left_stick_x;
        double left_y  = -gamepad1.left_stick_y;
        double right_x =  gamepad1.right_stick_x;

        double normalizer = Math.max(Math.abs(left_x) +
                                     Math.abs(left_y) +
                                     Math.abs(right_x), 1.0);

        double front_left_pw  = (left_y + left_x + right_x) / normalizer;
        double back_left_pw   = (left_y - left_x + right_x) / normalizer;
        double front_right_pw = (left_y - left_x - right_x) / normalizer;
        double back_right_pw  = (left_y + left_x - right_x) / normalizer;

        front_left.setPower(front_left_pw);
        back_left.setPower(back_left_pw);
        front_right.setPower(front_right_pw);
        back_right.setPower(back_right_pw);
    }

    private void MoveIntakeSlider()
    {
        double pw_x  =  gamepad2.left_stick_x;
        double pw_y  = -gamepad2.left_stick_y;
        double angle = Math.abs(Math.atan(pw_x / pw_y));

        if (angle > Math.toRadians(5)) {
            if (angle > limit) {
                
            }
           moveMotor(intake_slider, pw_y);
        }
    }

    private void UpdateTelemetry()
    {
        telemetry.update();
    }


    public void runOpMode()
    {
        waitForStart();

        InitializeWheels();
        InitDcs();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            SetWheelsPower();
            MoveIntakeSlider();

            UpdateTelemetry();
        }

    }
}
