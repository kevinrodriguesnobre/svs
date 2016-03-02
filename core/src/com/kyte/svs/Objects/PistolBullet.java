package com.kyte.svs.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Vector2;

/**
 * Kugel für Standardwaffe
 */
public class PistolBullet extends Projectile
{



    public PistolBullet(float x, float y, float rotation,Vector2 directionVector)
    {
        super(new TextureRegion(new Texture("Projektile/Projektil.Pistole.png"), 2, 2), 250, 350, directionVector);
        setX(x);
        setY(y);
        setRotation(rotation);
    }
}
