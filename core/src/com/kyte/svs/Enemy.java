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
    int _life;
    int speed = 1;
    int _currentLife = 100;
    int _maxLife = 100;

    public Enemy() {
        super(new TextureRegion(new Texture("Gegner/Gegner.Alien.png"), 32, 32));
        setOriginY(16);
        setOriginX(16);
        speed = speed + (int) Math.random();
    }

    public void move(Player target, ArrayList<Enemy> enemyList) {
        float differenceX = 0;
        float differenceY = 0;
        float newX = 0;
        float newY = 0;

        float deltaX = Math.abs(target.getX()) - Math.abs(getX());
        float deltaY = Math.abs(target.getY()) - Math.abs(getY());
        float sum = Math.abs(Math.abs(deltaX) + Math.abs(deltaY));
        float newDeltaX = Math.abs(target.getX()) - Math.abs(getX());
        float newDeltaY = Math.abs(target.getY()) - Math.abs(getY());
        float newSum = Math.abs(Math.abs(newDeltaX) + Math.abs(newDeltaY));

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

        if (newDeltaY != 0)
            if ((newDeltaX / newDeltaY) < 1 && (newDeltaX / newDeltaY) > 0)
                differenceX = differenceX * (newDeltaX / newDeltaY);
        if (newDeltaX != 0)
            if ((newDeltaY / newDeltaX) < 1 && (newDeltaY / newDeltaX) > 0)
                differenceY = differenceY * (newDeltaY / newDeltaX);

        newX = getX() + differenceX;
        newY = getY() + differenceY;


        //Rotierung muss am Anfang stehen
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
        /*if (rotation < getRotation() % 320 - getRotation() % 360 / 20 || rotation > getRotation() % 360 + getRotation() % 360 / 20) {
            rotation = getRotation() + rotation / 20;
        }*/

        setRotation(rotation);

        // Wenn die Kachel, auf die der Gegner möchte ein blockiertes Objekt ist
        boolean blocked = false;
        if (newX <= _collisionLayer.getWidth() * 32 && newY <= _collisionLayer.getHeight() * 32) {
            for (int i = 1; i < 6; i++) {
                if (_collisionLayer.getCell((int) newX / _collisionLayer.getWidth(), (int) newY / _collisionLayer.getHeight()).getTile().getProperties().containsKey("blocked")) {
                    blocked = true;
                }
                if(blocked){
                    differenceX = differenceX * -2;
                    differenceY = differenceY * -0.5f;
                }
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
                    collided = true;
                    deltaX = Math.abs(enemy.getX()) - Math.abs(getX());
                    deltaY = Math.abs(enemy.getY()) - Math.abs(getY());
                    sum = Math.abs(deltaX + deltaY);
                    newDeltaX = Math.abs(enemy.getX()) - Math.abs(getX() + differenceX);
                    newDeltaY = Math.abs(enemy.getY()) - Math.abs(getY() + differenceY);
                    newSum = Math.abs(newDeltaX + newDeltaY);
                    //sum > newSum - falls man näher an einen anderen Gegner kommt, direction invertieren
                    if (sum > newSum) {

                        if (sum < 32){
                            return;
                        }
                        differenceX = differenceX * -0.6f * (float) Math.random();
                        differenceY = differenceY * -0.6f * (float) Math.random();
                    }
                    //return;
                }

        if (getX() + differenceX < 0 || getY() + differenceY < 0 || getX() + differenceX > _collisionLayer.getTileWidth() * 32 - 32 || getY() + differenceY > _collisionLayer.getTileHeight() * 32 - 32){
            System.out.println(differenceX + " - BLOCKED - " + differenceY);
            return;
        }


        setX(getX() + differenceX * speed);
        setY(getY() + differenceY * speed);
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        _collisionLayer = collisionLayer;
    }

    public void setLife(int newLife) {
        _currentLife = newLife;
    }

    public int getLife() {
        return _currentLife;
    }

    public int getMaxLife() {
        return _maxLife;
    }
}
