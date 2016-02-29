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
   // Texture texture = new Texture(Gdx.files.internal("data/Player.png"));
    //Sprite _playerSprite = new Sprite(texture, 20, 20, 50, 50);
    int speed = 10;

    public Player()
    {
        super(new Sprite());
        //this.set(_playerSprite);
    }

    public void move(Vector2 direction)
    {
        super.setPosition(direction.x * speed+ this.getX(),direction.y * speed + this.getY());
    }


}
