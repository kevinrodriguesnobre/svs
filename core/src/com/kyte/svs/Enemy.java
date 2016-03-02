package com.kyte.svs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;

/**
 * Created by test on 02.03.2016.
 */
public class Enemy extends Character {
    public Enemy()
    {
        super(new TextureRegion(new Texture("Gegner/Gegner.Alien.png"), 32, 32));
    }
}
