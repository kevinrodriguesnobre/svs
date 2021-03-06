package com.kyte.svs.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Kugel für Standardwaffe
 */
public class PistolBullet extends Projectile
{
    public PistolBullet(float x, float y, float rotation,Vector2 directionVector)
    {
        super(new TextureRegion(new Texture("Projektile/Projektil.Pistole.png"), 5, 5), 500, 600, directionVector);
        setX(x);
        setY(y);
        setRotation(rotation);
        _damage = 35;
    }
}