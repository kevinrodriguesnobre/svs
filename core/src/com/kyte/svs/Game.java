package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * Haupt-Spielklasse
 */
public class Game extends ScreenAdapter {

    private World _world;
    private Joysticks _joysticks;
    private Player _player;
    private ArrayList<Enemy> _enemyList;
    private int _enemyAmount = 10;
    private OrthographicCamera _camera;
    private SpriteBatch batch;
    private static final int VIRTUAL_WIDTH = 480;
    private static final int VIRTUAL_HEIGHT = 320;
    private START _game;
    private Rectangle backBounds;
    private Vector3 touchPoint;


    public Game(START game) {

        _game = game;

        _enemyList = new ArrayList<Enemy>();
        for (int i = 0; i < _enemyAmount; i++) {
            Enemy enemy = new Enemy();
            _enemyList.add(enemy);
        }

        _player = new Player();
        _player.setX(VIRTUAL_WIDTH);
        _player.setY(VIRTUAL_HEIGHT);

        backBounds = new Rectangle(0, VIRTUAL_HEIGHT / 3, 50, 50);
        touchPoint = new Vector3();

        _camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        _camera.position.set(_player.getX() + VIRTUAL_WIDTH / 2, _player.getY() + VIRTUAL_HEIGHT / 2, 1);
        _camera.update();

        _world = new World(_player, _camera, _enemyList);
        for(Enemy enemy : _enemyList){
            enemy.setX(_world.getMapLayer().getTileWidth()*31 - (float)Math.random() * _world.getMapLayer().getTileWidth()*32);
            enemy.setY(_world.getMapLayer().getTileHeight()*31 - (float)Math.random() * _world.getMapLayer().getTileHeight()*32);
            enemy.setCollisionLayer(_world.getCollisonLayer());
        }
        _player.setCollisionLayer(_world.getCollisonLayer());

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

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Rotation des Spielers wird aktualisiert
        float tmpRot = _player.getRotation();
        _player.setRotation(_joysticks.getRotation(tmpRot));
        _player.move(_joysticks.getPositionVector(), deltax, _world.getMapLayer());
        _player.setOrigin();

        for(int i=0; i < _enemyList.size();i++){
            _enemyList.get(i).move(_player, _enemyList);
        }

        //Enemy enemy = new Enemy();
        //enemy.move();

        //update();
        //draw();

        //Auslagern
        if (_player.getX() - VIRTUAL_WIDTH / 2 >= 0 && _player.getX() + VIRTUAL_WIDTH / 2 <= _world.getMapLayer().getTileWidth() * 32) {
            _camera.position.set(_player.getX(), _camera.position.y, 1);
        }
        if (_player.getY() - VIRTUAL_HEIGHT / 2 >= 0 && _player.getY() + VIRTUAL_HEIGHT / 2 <= _world.getMapLayer().getTileHeight() * 32)
            _camera.position.set(_camera.position.x, _player.getY(), 1);
        _camera.update();

        // Zeichnen der grafischen Oberfläche
        _world.renderMap();
        _joysticks.renderJoysticks();
    }

    public void update() {
        if (Gdx.input.justTouched()) {
            _camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
                _game.setScreen(new MainMenu(_game));
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
