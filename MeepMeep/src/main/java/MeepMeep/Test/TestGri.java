package MeepMeep.Test;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class TestGri
{
    static final float ROBOT_LEN       = 14f;
    static final float TILE_LEN        = 24f;
    static final float BETWEEN_SAMPLES = 10f;
    static final float SAMPLES_WITH    = 3.5f;
    static final float INTAKE          = ROBOT_LEN / 4;
    static final float OUTTAKE          = ROBOT_LEN / 2;

    static final Pose2d start_pos = new Pose2d(TILE_LEN - 0.5*ROBOT_LEN, 3*TILE_LEN - 0.5*ROBOT_LEN, Math.toRadians(270));
    static final Pose2d outpos    = new Pose2d(2*TILE_LEN + OUTTAKE, 2.5*TILE_LEN, Math.toRadians(-135));

    static final Pose2d inpos     = new Pose2d(2*TILE_LEN, 1.5*TILE_LEN + INTAKE, Math.toRadians(-90));

    private static float ToInch(float a) {
        return a / 2.54f;
    }

    public static void main(String[] args) {
        MeepMeep mm = new MeepMeep(800);

        RoadRunnerBotEntity b = new DefaultBotBuilder(mm)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), ROBOT_LEN)
                .setDimensions(ROBOT_LEN, ROBOT_LEN)
                .followTrajectorySequence((d) -> d.trajectorySequenceBuilder(start_pos)
                        .splineToLinearHeading(new Pose2d(2*TILE_LEN + OUTTAKE, 2.5*TILE_LEN, Math.toRadians(-135)),
                                Math.toRadians(-10))

                        /* outake */
                        .lineToSplineHeading(inpos)
                        /* intake */
                        .lineToSplineHeading(outpos)
                        /* outake */
                        .lineToSplineHeading(new Pose2d(inpos.getX() + BETWEEN_SAMPLES, inpos.getY(), inpos.getHeading()))
                        /* intake */
                        .lineToSplineHeading(outpos)
                        /* outake */
                        .lineToSplineHeading(new Pose2d(inpos.getX() + 2*BETWEEN_SAMPLES - OUTTAKE - INTAKE,
                                                        TILE_LEN + 0.5*SAMPLES_WITH, Math.toRadians(0)))
                        /* intake */
                        .lineToSplineHeading(outpos)
                        /* outake */

                        .build());


        mm.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(b)
                .start();
    }
}
