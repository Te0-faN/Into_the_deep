package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


public class AutonomGri extends LinearOpMode
{
    static final float ROBOT_LEN       = 14f; /* DE SCHIMBAT */
    static final float TILE_LEN        = 24f;
    static final float BETWEEN_SAMPLES = 10f;
    static final float SAMPLES_WITH    = 3.5f;
    static final float INTAKE          = ROBOT_LEN / 4; /* DE SCHIMBAT */
    static final float OUTTAKE         = ROBOT_LEN / 2; /* DE SCHIMBAT */

    static final Pose2d start_pos = new Pose2d(TILE_LEN - 0.5*ROBOT_LEN, 3*TILE_LEN - 0.5*ROBOT_LEN, Math.toRadians(270));
    static final Pose2d outpos    = new Pose2d(2*TILE_LEN + OUTTAKE, 2.5*TILE_LEN, Math.toRadians(-135));

    static final Pose2d inpos     = new Pose2d(2*TILE_LEN, 1.5*TILE_LEN + INTAKE, Math.toRadians(-90));

    public void runOpMode()
    {
        SampleMecanumDrive d = new SampleMecanumDrive(hardwareMap);

        d.setPoseEstimate(new Pose2d());

        /* In addDisplacementMarker trebuie sa fie codul pentru
         * outake resepectiv intake. De preferat sa foloseti
         * o functie ca sa refoloseti codul de la TeleOp.
         * */
        Trajectory t = d.trajectoryBuilder(start_pos)
                .splineToLinearHeading(new Pose2d(2*TILE_LEN + OUTTAKE, 2.5*TILE_LEN, Math.toRadians(-135)),
                        Math.toRadians(-10))
                .addDisplacementMarker(() -> {
                    /* outake */
                })
                .lineToSplineHeading(inpos)
                .addDisplacementMarker(() -> {
                    /* intake */
                })
                .lineToSplineHeading(outpos)
                .addDisplacementMarker(() -> {
                    /* outake */
                })
                .lineToSplineHeading(new Pose2d(inpos.getX() + BETWEEN_SAMPLES, inpos.getY(), inpos.getHeading()))
                .addDisplacementMarker(() -> {
                    /* intake */
                })
                .lineToSplineHeading(outpos)
                .addDisplacementMarker(() -> {
                    /* outake */
                })
                .lineToSplineHeading(new Pose2d(inpos.getX() + 2*BETWEEN_SAMPLES - OUTTAKE - INTAKE,
                        TILE_LEN + 0.5*SAMPLES_WITH, Math.toRadians(0)))
                .addDisplacementMarker(() -> {
                    /* intake */
                })
                .lineToSplineHeading(outpos)
                .addDisplacementMarker(() -> {
                    /* outake */
                })
                .build();

        waitForStart();

        if(isStopRequested()) return;

        d.followTrajectory(t);

    }

}
