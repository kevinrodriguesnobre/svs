package com.kyte.svs;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by X303 on 01.03.2016.
 */

    import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
    import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
    import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Character {

        private float lifeTime;
        private float lifeTimer;
        public boolean existiert;

        private boolean remove;

        protected float x;
        protected float y;

        protected float dx;
        protected float dy;

        protected float radians;
        protected float speed;
        protected float rotationSpeed;

        protected int width;
        protected int height;


        public Bullet(float x, float y, float radians, Sprite sprite) {
            super(sprite);
            existiert = false;
            this.x = x;
            this.y = y;
            this.radians = radians;

            float speed = 350;
            dx = MathUtils.cos(radians) * speed;
            dy = MathUtils.sin(radians) * speed;

            width = height = 2;

            lifeTimer = 0;
            lifeTime = 1;

        }


        public boolean shouldRemove() { return remove; }

    public void move(Vector2 direction, float delta)
    {
        float x = delta * speed * direction.x;
        float y = delta * speed * direction.y;
        if(getX() + x < 0 || getY() + y < 0)
            return;
        super.setPosition(x + this.getX(),y + this.getY());
    }

        public void update(float dt) {

            x += dx * dt;
            y += dy * dt;


            lifeTimer += dt;
            if(lifeTimer > lifeTime) {
                remove = true;
            }

        }


    }


