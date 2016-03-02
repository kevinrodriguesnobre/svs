package com.kyte.svs.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;

/**
 * Kugel für Standardwaffe
 */
public class Bullet extends Projectile
{
    public Bullet ()
    {
        super(new TextureRegion(new Texture("data/Player.png"), 32, 32));
    }
}
