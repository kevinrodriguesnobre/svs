package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

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
    float speed = 0.0000003f;

    public Player(Sprite sprite)
    {
        super(sprite);
    }

    public void move(Vector2 direction, float delta)
    {
        float x = delta * speed * direction.x;
        float y = delta * speed * direction.y;
        super.setPosition(x + this.getX(),y + this.getY());

    }


}
