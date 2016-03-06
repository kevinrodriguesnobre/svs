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
        float moveX = 0;
        float moveY = 0;
        float newX = 0;
        float newY = 0;
        float newDeltaX = 0;
        float newDeltaY = 0;
        float newSum = 0;

        float deltaX = Math.abs(target.getX() - getX());
        float deltaY = Math.abs(target.getY() - getY());
        float distance = deltaX + deltaY;

        //Falls zu nah am Player stopen
        if (distance < 16)
            return;

        if(getX() < target.getX()){
            moveX = 1;
        }else if(getX() > target.getX()){
            moveX = -1;
        }
        if(getY() < target.getY()){
            moveY = 1;
        }else if(getY() > target.getY()){
            moveY = -1;
        }

        if (deltaX != 0)
            if ((deltaX / deltaY) < 1 && (deltaX / deltaY) > 0)
                moveX = moveX * (deltaX / deltaY);
        if (deltaY != 0)
            if ((deltaY / deltaX) < 1 && (deltaY / deltaX) > 0)
                moveY = moveY * (deltaY / deltaX);

        newX = getX() + moveX;
        newY = getY() + moveY;


        //Rotierung muss am Anfang stehen
        float rotation = getRotation();
        if (moveX > 0) {
            if (moveY > 0) {
                rotation = (float) (-Math.atan(moveX / moveY) * 180 / Math.PI);
            } else if (moveY < 0) {
                rotation = (float) -(-180 + Math.atan(moveX / moveY) * 180 / Math.PI);
            }
        } else if (moveX < 0) {
            if (moveY > 0) {
                rotation = (float) -(Math.atan(moveX / moveY) * 180 / Math.PI);
            } else if (moveY < 0) {
                rotation = (float) -(-180 + Math.atan(moveX / moveY) * 180 / Math.PI);

            }
        }

        setRotation(rotation);

        // Wenn die Kachel, auf die der Gegner möchte ein blockiertes Objekt ist
        boolean blocked = false;
        if (newX <= _collisionLayer.getWidth() * 32 && newY <= _collisionLayer.getHeight() * 32) {
            for (int i = 1; i < 6; i++) {
                if (_collisionLayer.getCell((int) newX / _collisionLayer.getWidth(), (int) newY / _collisionLayer.getHeight()).getTile().getProperties().containsKey("blocked")) {
                    blocked = true;
                }
                if(blocked){
                    moveX = moveX * -0.8f;
                    moveY = moveY * -0.2f;
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
                    distance = Math.abs(deltaX + deltaY);
                    newDeltaX = Math.abs(enemy.getX()) - Math.abs(getX() + moveX);
                    newDeltaY = Math.abs(enemy.getY()) - Math.abs(getY() + moveY);
                    newSum = Math.abs(newDeltaX + newDeltaY);
                    //sum > newSum - falls man näher an einen anderen Gegner kommt, direction invertieren
                    if (distance > newSum) {

                        if (distance < 32)
                            return;
                        moveX = moveX * -0.6f * (float) Math.random();
                        moveY = moveY * -0.6f * (float) Math.random();
                    }
                    //return;
                }

        if (getX() + moveX < 0 || getY() + moveY < 0 || getX() + moveX > _collisionLayer.getTileWidth() * 32 - 32 || getY() + moveY > _collisionLayer.getTileHeight() * 32 - 32)
            return;


        setX(getX() + moveX * speed);
        setY(getY() + moveY * speed);
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
