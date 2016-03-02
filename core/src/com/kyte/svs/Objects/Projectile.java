package com.kyte.svs.Objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;

/**
 * Oberklasse für sämtliche Projektile
 */
public class Projectile extends TextureMapObject
{
    public Projectile(TextureRegion textureRegion)
    {
        super(textureRegion);
    }
}
