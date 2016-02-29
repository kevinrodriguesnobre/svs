package com.kyte.svs;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by Yanneck on 29.02.2016.
 *
 * Stellt den Spieler dar, enthält sämtliche Eigenschaften,
 * wie Geschwindigkeit, mit der sich der Spieler bewegen soll,
 * o.ä..
 *
 */
public class Player extends Character
{
    private float _rotation;

    public Player(Sprite sprite)
    {
        super(sprite);
        _rotation = 0;
    }

    public void setRotation()
    {

    }

    public float getRotation()
    {
        return _rotation;
    }
}
