package com.kyte.svs;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by test on 02.03.2016.
 */
public class Enemy extends Character{
    TiledMapTileLayer _collisionLayer;

    public Enemy()
    {
        super(new TextureRegion(new Texture("Gegner/Gegner.Alien.png"), 32, 32));
    }

    public void move(Player target, ArrayList<Enemy> enemyList){
        float newX;
        float newY;

        if(getX() < target.getX()){
            newX = getX() + 1;
        }else{
            newX = getX() - 1;
        }
        if(getY() < target.getY()){
            newY = getY() + 1;
        }else{
            newY = getY() - 1;
        }

        // Wenn die Kachel, auf die der Spieler möchte ein blockiertes Objekt ist
        /*if( newX <= layerWidth && newY <= layerHeight) {
            if (_collisionLayer.getCell((int) newX/_collisionLayer.getWidth(), (int) newY/_collisionLayer.getHeight()).getTile().getProperties().containsKey("blocked"))
                return;
        } */
        if( newX < 0 || newY < 0 || newX > _collisionLayer.getTileWidth()*32 - 32 || newY > _collisionLayer.getTileHeight()*32 - 32)
            return;

        for(Enemy enemy : enemyList)
            if(enemy != this)
                if((Math.round(enemy.getX() + 30) > Math.round(getX()) &&  Math.round(enemy.getX() - 30) < Math.round(getX()))&&
                   (Math.round(enemy.getY() + 30) > Math.round(getY()) &&  Math.round(enemy.getY() - 30) < Math.round(getY()))){
                    newX = newX + 1;
                    newY = newY + 1;
                }
        setX(newX);
        setY(newY);
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer){
        _collisionLayer = collisionLayer;
    }
}
