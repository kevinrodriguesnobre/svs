package com.kyte.svs;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;


/**
 * Haupt-Spielklasse
 */
public class Game implements ApplicationListener {


    private Sprite _playerSprite;
    private World _world;
    private Joysticks _joysticks;

    public Game() {
        _world = new World();
        _joysticks = new Joysticks();
    }

    @Override
    public void create()
    {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render()
    {
        float tmpRot = _world.getPlayer().getRotation();
        _world.getPlayer().setRotation(_joysticks.getNewRotation(tmpRot));


        _joysticks.renderJoysticks();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
