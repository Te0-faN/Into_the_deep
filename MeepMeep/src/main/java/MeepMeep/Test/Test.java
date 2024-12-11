package MeepMeep.Test;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Test
{
    static final float ROBOT_LEN       = 14f;
    static final float TILE_LEN        = 24f;
    static final float BETWEEN_SAMPLES = 10f;
    static final float SAMPLES_WITH    = 3.5f;
    static final float INTAKE          = ROBOT_LEN / 2;
    static final float OUTAKE          = ROBOT_LEN / 2;

    static final Pose2d start_pos = new Pose2d(-TILE_LEN, 3*TILE_LEN - 0.5*ROBOT_LEN, Math.toRadians(270));
    static final Pose2d outake_pos = new Pose2d( 2*TILE_LEN + OUTAKE, 2.5*TILE_LEN, Math.toRadians(45));
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
                        .splineToLinearHeading(new Pose2d(2*TILE_LEN + OUTAKE, 2.5*TILE_LEN, Math.toRadians(45)),
                                Math.toRadians(-10))
                        /* outake */
                        .lineToSplineHeading(new Pose2d( INTAKE - 2*TILE_LEN, TILE_LEN + 0.5*SAMPLES_WITH, Math.toRadians(0)))
                        /* intake */
                        .lineToSplineHeading(outake_pos)
                        /* outake */
                        .lineToSplineHeading(new Pose2d( INTAKE - 2*TILE_LEN - BETWEEN_SAMPLES, TILE_LEN + 0.5*SAMPLES_WITH, Math.toRadians(0)))
                        /* intake */
                        .lineToSplineHeading(outake_pos)
                        /* outake */
                        .lineToSplineHeading(new Pose2d( INTAKE - 2*TILE_LEN - 2*BETWEEN_SAMPLES, TILE_LEN + 0.5*SAMPLES_WITH, Math.toRadians(0)))
                        /* intake */
                        .lineToSplineHeading(outake_pos)
                        /* outake */
                        .build());


        mm.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(b)
                .start();
    }
}