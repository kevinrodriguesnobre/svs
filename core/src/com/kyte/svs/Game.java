package com.kyte.svs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Vector;


/**
 * Haupt-Spielklasse
 */
public class Game extends ScreenAdapter {

    float endTime;
    private World _world;
    private Joysticks _joysticks;
    private Player _player;
    private OrthographicCamera _camera;
    private SpriteBatch batch;
    private ShapeRenderer sr;
    private static final int VIRTUAL_WIDTH = 480;
    private static final int VIRTUAL_HEIGHT = 320;
    private START game;
    private Rectangle backBounds;
    private Vector3 touchPoint;


    public Game(START game) {
        this.game = game;
        backBounds = new Rectangle(0, VIRTUAL_HEIGHT / 3, 50, 50);
        touchPoint = new Vector3();
        sr = new ShapeRenderer();
        sr.setColor(0, 1, 0, 1);
        endTime = System.nanoTime();
        Texture texture = new Texture(Gdx.files.internal("data/Player.png"));
        Sprite _playerSprite = new Sprite(texture, 32, 32);
        _camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        _player = new Player(_playerSprite);
        _player.setPosition(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
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
    public void render(float deltax) {
        batch.setProjectionMatrix(_camera.combined);
        sr.setProjectionMatrix(_camera.combined);

        float delta = System.nanoTime() - endTime;
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Rotation des Spielers wird aktualisiert
        float tmpRot = _player.getRotation();
        _player.setRotation(_joysticks.getRotation(tmpRot));

        _player.move(_joysticks.getPositionVector(), deltax);//, delta);

        update();
        draw();

        //_world.renderMap();
        if (_player.getX() - VIRTUAL_WIDTH / 2 >= 0) {
            _camera.position.set(_player.getX(), _camera.position.y, 1);
        }
        if (_player.getY() - VIRTUAL_HEIGHT / 2 >= 0)
            _camera.position.set(_camera.position.x, _player.getY(), 1);
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

    public void update() {
        if (Gdx.input.justTouched()) {
            _camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new MainMenu(game));
                return;
            }
        }
    }

    public void draw() {
        batch.begin();
        batch.draw(new Texture(Gdx.files.internal("data/backbutton.png")), 0, VIRTUAL_HEIGHT / 3, 50, 50);
        batch.end();
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
