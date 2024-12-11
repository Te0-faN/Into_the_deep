package org.firstinspires.ftc.teamcode.drive.opmode;

import static org.firstinspires.ftc.teamcode.drive.opmode.Util.ToInch;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous
class Autonom extends LinearOpMode
{
    static final int   TRAJ_NUMBER = 10;
    static final float ROBOT_LEN   = ToInch(0.5f);
    static final float TILE_LEN    = 24f;
    static final float SAMPLE_LEN  = 3.5f;
    static final float BASKET      = ToInch(0.1f);

    public void runOpMode()
    {
        SampleMecanumDrive d = new SampleMecanumDrive(hardwareMap);

        d.setPoseEstimate(new Pose2d());

        Trajectory t[] = new Trajectory[TRAJ_NUMBER];
        t[0] = d.trajectoryBuilder(new Pose2d())
            .splineTo(new Vector2d(2*TILE_LEN - ROBOT_LEN, ROBOT_LEN), Math.toRadians(-20))
            .build();
        t[1] = d.trajectoryBuilder(t[0].end())
            .build();

        waitForStart();

        if(isStopRequested()) return;

        for (int i = 0; i < t.length; i++) {
            d.followTrajectory(t[i]);
            switch (i) {
                case 0:
                    /* Pune samplelul in cos */
                    break;
            }
        }

    }

}
