package com.kyte.svs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;

/**
 * Created by test on 02.03.2016.
 */
public class Enemy extends Character {
    TiledMapTileLayer _collisionLayer;
    int _currentLife = 100;
    int _maxLife = 100;


    public Enemy() {
        super(new TextureRegion(new Texture("Gegner/Gegner.Alien.png"), 32, 32));
        setOriginY(16);
        setOriginX(16);
    }

    public void move(Player target, ArrayList<Enemy> enemyList) {
        float differenceX = 0;
        float differenceY = 0;
        float newX = 0;
        float newY = 0;

        float deltaX = Math.abs(target.getX()) - Math.abs(getX());
        float deltaY = Math.abs(target.getY()) - Math.abs(getY());
        float sum = Math.abs(deltaX + deltaY);
        float newDeltaX = Math.abs(target.getX()) - Math.abs(getX());
        float newDeltaY = Math.abs(target.getY()) - Math.abs(getY());
        float newSum = Math.abs(newDeltaX + newDeltaY);

        //Falls zu nah am Player stopen
        if (sum < 32)
            return;

        if (getX() < target.getX()) {
            differenceX = 1;
        } else {
            differenceX = -1;
        }
        if (getY() < target.getY()) {
            differenceY = 1;
        } else {
            differenceY = -1;
        }

        //differenceX = differenceX  * (newDeltaX / newDeltaY);
        //differenceY = differenceY * (newDeltaY / newDeltaX);
        newX = getX() + differenceX;
        newY = getY() + differenceY;
        // Wenn die Kachel, auf die der Spieler möchte ein blockiertes Objekt ist
        if (newX <= _collisionLayer.getWidth() * 32 && newY <= _collisionLayer.getHeight() * 32) {
            if (_collisionLayer.getCell((int) newX / _collisionLayer.getWidth(), (int) newY / _collisionLayer.getHeight()).getTile().getProperties().containsKey("blocked")) {
                differenceX = differenceX * -20;
                differenceY = differenceY * -5;
            }
        }

        //Wenn Enemies gegeinander treffen
        boolean collided = false;
        for (Enemy enemy : enemyList)
            if (enemy != this)
                if ((Math.round(enemy.getX() + 30) > Math.round(getX()) && Math.round(enemy.getX() - 30) < Math.round(getX())) &&
                        (Math.round(enemy.getY() + 30) > Math.round(getY()) && Math.round(enemy.getY() - 30) < Math.round(getY()))) {
                    //differenceX = -differenceX;
                    //differenceY = -differenceY;
                    deltaX = Math.abs(enemy.getX()) - Math.abs(getX());
                    deltaY = Math.abs(enemy.getY()) - Math.abs(getY());
                    sum = Math.abs(deltaX + deltaY);
                    newDeltaX = Math.abs(enemy.getX()) - Math.abs(getX() + differenceX);
                    newDeltaY = Math.abs(enemy.getY()) - Math.abs(getY() + differenceY);
                    newSum = Math.abs(newDeltaX + newDeltaY);
                    //sum > newSum - falls man näher an einen anderen Gegner kommt, direction invertieren
                    if (sum > newSum) {

                        if (sum < 32)
                            return;
                        differenceX = differenceX * -0.05f;
                        differenceY = differenceY * -0.2f;
                        collided = true;
                    }
                    //return;
                }

        if (getX() + differenceX < 0 || getY() + differenceY < 0 || getX() + differenceX > _collisionLayer.getTileWidth() * 32 - 32 || getY() + differenceY > _collisionLayer.getTileHeight() * 32 - 32)
            return;

        float rotation = getRotation();
        if (differenceX > 0) {
            if (differenceY > 0) {
                rotation = (float) (-Math.atan(differenceX / differenceY) * 180 / Math.PI);
            } else if (differenceY < 0) {
                rotation = (float) -(-180 + Math.atan(differenceX / differenceY) * 180 / Math.PI);
            }
        } else if (differenceX < 0) {
            if (differenceY > 0) {
                rotation = (float) -(Math.atan(differenceX / differenceY) * 180 / Math.PI);
            } else if (differenceY < 0) {
                rotation = (float) -(-180 + Math.atan(differenceX / differenceY) * 180 / Math.PI);

            }
        }
        if (rotation < getRotation() % 320 - getRotation() % 360 / 20 || rotation > getRotation() % 360 + getRotation() % 360 / 20) {
            System.out.println(differenceX + " - " + differenceY);
            rotation = getRotation() + rotation / 20;
        }
        if (!collided)
            setRotation(rotation);

        setX(getX() + differenceX);
        setY(getY() + differenceY);
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        _collisionLayer = collisionLayer;
    }

    public void setLife(int newLife)
    {
        _currentLife = newLife;
    }

    public int getLife()
    {
        return _currentLife;
    }

    public  int getMaxLife()
    {
        return _maxLife;
    }
}
