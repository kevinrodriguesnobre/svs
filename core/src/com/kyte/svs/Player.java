package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
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
    float speed = 120;
    TiledMapTileLayer _collisionLayer;

    public Player(Sprite sprite)
    {
        super(sprite);
        _collisionLayer = new TiledMapTileLayer(100,100,100,100);
    }

    public void move(Vector2 direction, float delta, TiledMapTileLayer mapLayer)
    {
        float newX = delta * speed * direction.x + this.getX();
        float newY = delta * speed * direction.y + this.getY();

        int layerWidth = _collisionLayer.getWidth() * 31;
        int layerHeight = _collisionLayer.getHeight() * 31;


        // Wenn die Kachel, auf die der Spieler möchte ein blockiertes Objekt ist
        if( newX <= layerWidth && newY <= layerHeight) {
            if (_collisionLayer.getCell((int) newX/_collisionLayer.getWidth(), (int) newY/_collisionLayer.getHeight()).getTile().getProperties().containsKey("blocked"))
                return;
        }
        if( newX < 0 || newY < 0 || newX > mapLayer.getTileWidth()*32 - 32 || newY > mapLayer.getTileHeight()*32 - 32)
            return;

        // Kachel ist begehbar und Spielerposition wird dorthin verschoben
        super.setPosition(newX, newY);

    }

    /**
     * Weißt dem Player eine Kollisionsebene zu
     * @param collisionLayer Kollisionsebene
     */
    public void setCollisionLayer(TiledMapTileLayer collisionLayer)
    {
        _collisionLayer = collisionLayer;
    }


}
