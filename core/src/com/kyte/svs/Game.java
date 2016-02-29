package com.kyte.svs;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by Yanneck on 29.02.2016.
 */
public class Game implements ApplicationListener {









    private Sprite _playerSprite;
    World _world;
    Player _player;
    Joysticks _joysticks;

    @Override
    public void create()
    {
        Texture texture = new Texture(Gdx.files.internal("data/Player.png"));
        _playerSprite = new Sprite(texture, 20, 20, 50, 50);

        _world = new World();
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer)_world.getCollisionLayer();

        _player = new Player(_playerSprite,collisionLayer);
        _world.set_player(_player);

        _joysticks = new Joysticks();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render()
    {
        _world.getPlayer().setRotation(_world.getPlayer().getRotation());


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
