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
    int speed = 1;
    int _currentLife = 100;
    float _oldMoveX = 0f;
    float _oldMoveY = 0f;

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

        if (getX() < target.getX()) {
            moveX = 1;
        } else if (getX() > target.getX()) {
            moveX = -1;
        }
        if (getY() < target.getY()) {
            moveY = 1;
        } else if (getY() > target.getY()) {
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
        boolean blockedNW = false;
        boolean blockedSE = false;
        int cellX = 0;
        int cellY = 0;
        if (newX <= _collisionLayer.getWidth() * 32 && newY <= _collisionLayer.getHeight() * 32) {
            cellX = (int) (newX+32) / _collisionLayer.getWidth();
            cellY = (int) (newY) / _collisionLayer.getHeight();

            if (Math.abs(moveX) > Math.abs(moveY))
                    if (_collisionLayer.getCell(cellX , cellY).getTile().getProperties().containsKey("blocked")) {
                        blocked = true;
                        moveX = 0;
                        if (moveY > 0.05f && (_oldMoveY > 0.2 || _oldMoveY == 0)){
                            moveY = Math.max(moveY, Math.min(2, (moveY + 0.2f * sign(moveX)) * 4));
                            _oldMoveY = moveY;
                        }else if (moveY < -0.05f && (_oldMoveX < 0.2 ||_oldMoveX == 0)){
                            moveY = Math.max(-1, Math.min(moveY, (moveY + 0.2f * sign(moveX)) * 4));
                            _oldMoveY = moveY;
                        }else{
                            moveY = _oldMoveY;
                        }
                    }
            if (Math.abs(moveX) <= Math.abs(moveY) && !blocked)
                    if (_collisionLayer.getCell(cellX, cellY).getTile().getProperties().containsKey("blocked")) {
                        blocked = true;
                        moveY = 0;
                        if (moveX > 0.05f && (_oldMoveX > 0.2 || _oldMoveX == 0)){
                            moveX = Math.max(moveX, Math.min(2, (moveX + 0.2f * sign(moveX)) * 4));
                            _oldMoveX = moveX;
                        }else if (moveX < -0.05f && (_oldMoveX < 0.2 ||_oldMoveX == 0)){
                            moveX = Math.max(-1, Math.min(moveX, (moveX + 0.2f * sign(moveX)) * 4));
                            _oldMoveX = moveX;
                        }else{
                            moveX = _oldMoveX;
                        }
                    }
        }

        //Wenn Enemies gegeinander treffen
        boolean collided = false;
        for (Enemy enemy : enemyList)
            if (enemy != this && !blocked)
                if ((Math.round(enemy.getX() + 16) > Math.round(getX()) && Math.round(enemy.getX() - 16) < Math.round(getX())) &&
                        (Math.round(enemy.getY() + 16) > Math.round(getY()) && Math.round(enemy.getY() - 16) < Math.round(getY()))) {
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

    public int sign(float number){
        if(number < 0){
            return -1;
        }
        return 1;
    }
}
