package com.kyte.svs;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


/**
 * Haupt-Spielklasse
 */
public class Game implements ApplicationListener {

    private World _world;
    private Joysticks _joysticks;
    private Player _player;
    private OrthographicCamera _camera;
    private SpriteBatch batch;
    private static final int VIRTUAL_WIDTH = 480;
    private static final int VIRTUAL_HEIGHT = 320;


    public Game()
    {
        //batch = new SpriteBatch();
        //batch.getProjectionMatrix().setToOrtho2D(0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        //_camera = new CameraManager(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        //_player = new Player();
        //_player.setPosition(1, 1);
        //_world = new World();
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
        /**
        _camera.update();
        Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float tmpRot = _player.getRotation();
        _player.setRotation(_joysticks.getRotation(tmpRot));

        _player.move(_joysticks.getPositionVector());

         */
        //_world.renderMap();
        //_joysticks.renderJoysticks();
        //Draw
        //batch.begin();
        //_player.draw(batch);
        //batch.end();
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
