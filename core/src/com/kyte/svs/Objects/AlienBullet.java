package com.kyte.svs.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Kugel für Standardwaffe
 */
public class AlienBullet extends Projectile
{
    public AlienBullet(float x, float y, float rotation,Vector2 directionVector)
    {
        super(new TextureRegion(new Texture("Projektile/Projektil.Alienwaffe.png"), 5, 10), 100, 400, directionVector);
        setX(x);
        setY(y);
        setRotation(rotation);
    }
}
