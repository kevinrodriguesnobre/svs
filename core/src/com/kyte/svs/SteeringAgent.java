package com.kyte.svs;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by 4krodrig on 02.03.2016.
 */
public class SteeringAgent extends SteeringBehavior {
    private static final SteeringAcceleration<Vector2> steeringOutput =
            new SteeringAcceleration<Vector2>(new Vector2());

    Vector2 position;
    float orientation;
    Vector2 linearVelocity;
    float angularVelocity;
    float maxSpeed;
    boolean independentFacing;
    SteeringBehavior<Vector2> steeringBehavior;

    public SteeringAgent(Steerable<Vector2> steer){
        super(steer);
    }
    /* Here you should implement missing methods inherited from Steerable */

    public void update (float delta) {
        if (steeringBehavior != null) {
            // Calculate steering acceleration
            steeringBehavior.calculateSteering(steeringOutput);

            /*
             * Here you might want to add a motor control layer filtering steering accelerations.
             *
             * For instance, a car in a driving game has physical constraints on its movement:
             * - it cannot turn while stationary
             * - the faster it moves, the slower it can turn (without going into a skid)
             * - it can brake much more quickly than it can accelerate
             * - it only moves in the direction it is facing (ignoring power slides)
             */

            // Apply steering acceleration to move this agent
            applySteering(steeringOutput, delta);
        }
    }

    private void applySteering (SteeringAcceleration<Vector2> steering, float time) {
        // Update position and linear velocity. Velocity is trimmed to maximum speed
        this.position.mulAdd(linearVelocity, time);
        this.linearVelocity.mulAdd(steering.linear, time).limit(maxSpeed);

        // Update orientation and angular velocity
        if (independentFacing) {
            this.orientation += angularVelocity * time;
            this.angularVelocity += steering.angular * time;
        } else {
            // For non-independent facing we have to align orientation to linear velocity
            float newOrientation = calculateOrientationFromLinearVelocity(this);
            if (newOrientation != this.orientation) {
                this.angularVelocity = (newOrientation - this.orientation) * time;
                this.orientation = newOrientation;
            }
        }
    }

    private float calculateOrientationFromLinearVelocity(SteeringAgent steeringAgent) {
        return 0;
    }

    @Override
    protected SteeringAcceleration calculateRealSteering(SteeringAcceleration steering) {
        return null;
    }
}
