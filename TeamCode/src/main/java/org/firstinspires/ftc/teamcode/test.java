package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="servotest")
public class test extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor slide = hardwareMap.dcMotor.get("outtake-slide");

        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        TouchSensor touch;
        touch = hardwareMap.get(TouchSensor.class, "limitSwitch");

        double ticks = 384.5;
        double target1 = 0;
        double turnage = 0;

        double ticks2 = 751.8;
        double target2 = 0;
        double turnage2 = 0;
        boolean reset2 = false;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;

            boolean X = gamepad1.x;
            boolean B = gamepad1.b;
            boolean A = gamepad1.a; //pozitia initiala slide
            boolean Y = gamepad1.y;
            double leftStickX = gamepad1.left_stick_x;
            double leftStickY = gamepad1.left_stick_y;
            double rightStickX = gamepad1.right_stick_x;
            double rightStickY = gamepad1.right_stick_y;
            boolean RB = gamepad1.right_bumper;
            boolean LB = gamepad1.left_bumper;
            float LT = gamepad1.left_trigger;
            float RT = gamepad1.right_trigger;
            boolean s = gamepad1.dpad_up;
            boolean d = gamepad1.dpad_right;
            boolean st = gamepad1.dpad_left;
            boolean j = gamepad1.dpad_down;

/*
/*
            if (X) {
                turnage = -8;
                target1 = turnage * ticks;
                slide.setTargetPosition((int) target1);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(1);
            }


            if (A) {
                turnage = 0;
                target1 = turnage * ticks;
                slide.setTargetPosition((int) target1);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(1);
            }
            if (B) {
                turnage = -4;
                target1 = turnage * ticks;
                slide.setTargetPosition((int) target1);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(1);
            }
            if (Y) {
                turnage = -1;
                target1 = turnage * ticks;
                slide.setTargetPosition((int) target1);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(1);
            }



            if (st) {
                turnage2 = -7.5;
                target2 = (turnage2 / 360) * 28 * ticks2;
                worm.setTargetPosition((int) target2);
                worm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                worm.setPower(1);
            }
            if (d) {
                turnage2 = -128;
                target2 = (turnage2 / 360) * 28 * ticks2;
                worm.setTargetPosition((int) target2);
                worm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                worm.setPower(1);
            }
            if (Y) {
                reset2 = true;
            }
            if (reset2) {
                worm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                worm.setPower(1);
                if (touch.isPressed()) {
                    worm.setPower(0);
                    reset2 = false;
                    worm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    worm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    target2 = 0;

                }
            }
*/
            if (A) {
                turnage = 0.5;
                target1 = turnage * ticks;
                slide.setTargetPosition((int) target1);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(0.5);
            }

            if (X) {
                turnage = -0.5;
                target1 = turnage * ticks;
                slide.setTargetPosition((int) target1);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(0.5);
            }


//            if (RB) {
//                target2 = target2 + ((ticks2 / 360) * 3);
//                if (target2 > 0) {
//                    target2 = 0;
//                }
//                worm.setTargetPosition((int) target2);
//                worm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                worm.setPower(1);
//                telemetry.addData("RB", RB);
//            }
//            if (LB) {
//                target2 = target2 - ((ticks2 / 360) * 3);
//                if (target2 < -8500) {
//                    target2 = -8500;
//                }
//                worm.setTargetPosition((int) target2);
//                worm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                worm.setPower(1);
//                telemetry.addData("LB", LB);
//            }
//
            if (LT > 0) {
                target1 += (ticks / 360)  * LT*5;
                if (target1 > 0) {
                    target1 = 0;
                }
                slide.setTargetPosition((int) target1);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(1);
                telemetry.addData("LT", LT);
            }

            if (RT > 0) {
                target1 -= (ticks / 360) * RT*5;
                if (target1 < -1500) {
                    target1 = -100;
                }
                slide.setTargetPosition((int) target1);
                slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide.setPower(1);
                telemetry.addData("RT", RT);
            }

            telemetry.addData("ticks", slide.getCurrentPosition());
            telemetry.addData("power", slide.getPower());
            telemetry.update();

        }
    }
}