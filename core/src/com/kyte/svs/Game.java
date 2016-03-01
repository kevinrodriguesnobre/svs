package com.kyte.svs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


/**
 * Haupt-Spielklasse
 */
public class Game extends ApplicationAdapter {

    float endTime;
    private World _world;
    private Joysticks _joysticks;
    private Player _player;
    private OrthographicCamera _camera;
    private SpriteBatch batch;
    private ShapeRenderer sr;
    private static final int VIRTUAL_WIDTH = 480;
    private static final int VIRTUAL_HEIGHT = 320;

    @Override
    public void create()
    {
        sr = new ShapeRenderer();
        sr.setColor(0, 1, 0, 1);
        endTime = System.nanoTime();
        Texture texture = new Texture(Gdx.files.internal("data/Player.png"));
        Sprite _playerSprite = new Sprite(texture, 32, 32);
        _camera = new CameraManager(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        _player = new Player(_playerSprite);
        _player.setPosition(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2);
        _camera.position.set(_player.getX() + VIRTUAL_WIDTH / 2, _player.getY() + VIRTUAL_HEIGHT / 2, 1);
        _camera.update();
        _world = new World(_player, _camera);
        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        _joysticks = new Joysticks();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render()
    {
        batch.setProjectionMatrix(_camera.combined);
        sr.setProjectionMatrix(_camera.combined);

        float delta = System.nanoTime() - endTime;
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Rotation des Spielers wird aktualisiert
        float tmpRot = _player.getRotation();
        _player.setRotation(_joysticks.getRotation(tmpRot));

        _player.move(_joysticks.getPositionVector(), 1E7f);//, delta);

        if (_player.getX() - VIRTUAL_WIDTH/2>= 0 && _player.getY() - VIRTUAL_HEIGHT/2 + 50>= 0){
            _camera.position.set(_player.getX(), _player.getY(), 1);
        }
        _camera.update();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        //sr.circle(_player.getX(), _player.getY(), 30);
        sr.circle(10, 10, 30);

        sr.end();

        // Zeichnen der grafischen Oberfläche
        _world.renderMap();
        _joysticks.renderJoysticks();

        endTime = System.nanoTime();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
