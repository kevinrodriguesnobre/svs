package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.kyte.svs.Objects.EffectSounds;
import com.kyte.svs.Objects.PistolBullet;
import com.kyte.svs.Objects.Projectile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * Haupt-Spielklasse
 */
public class Game extends ScreenAdapter {

    private World _world;
    private Joysticks _joysticks;
    private Player _player;
    private List<Projectile> _projectileSet;
    private ArrayList<Enemy> _enemyList;
    private int _enemyAmount = 10;
    private OrthographicCamera _camera;
    private SpriteBatch batch;
    private static final int VIRTUAL_WIDTH = 480;
    private static final int VIRTUAL_HEIGHT = 320;
    private START _game;
    private Rectangle backBounds;
    private Vector3 touchPoint;
    private long _lastShot;
    private EffectSounds _effectSounds;
    private Projectile _removeP;

    public Game(START game) {

        _game = game;

        _projectileSet = new LinkedList<Projectile>();


        _effectSounds = new EffectSounds();

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
        for (Enemy enemy : _enemyList) {
            enemy.setX(_world.getMapLayer().getTileWidth() * 31 - (float) Math.random() * _world.getMapLayer().getTileWidth() * 32);
            enemy.setY(_world.getMapLayer().getTileHeight() * 31 - (float) Math.random() * _world.getMapLayer().getTileHeight() * 32);
            enemy.setCollisionLayer(_world.getCollisonLayer());
        }
        _player.setCollisionLayer(_world.getCollisonLayer());

        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        _joysticks = new Joysticks();
        _lastShot = 0;
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

        shoot(deltax);

        for (int i = 0; i < _enemyList.size(); i++) {
            _enemyList.get(i).move(_player, _enemyList);
        }

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

    private void shoot(float delta) {
        float xandy = Math.abs(_joysticks.getRotationVector().x) + Math.abs(_joysticks.getRotationVector().y);
        if (_joysticks.touched() && ((System.currentTimeMillis() - _lastShot) > _player._weapon.getCurrentWeapon().getShootingFrequenz()) && (xandy > 0.999999f)) {
            switch (_player.getWeapon().getCurrentWeaponID()) {
                case 0:
                    PistolBullet pBullet = new PistolBullet(_player.getX(), _player.getY(), _player.getRotation(), _joysticks.getRotationVector());
                    _projectileSet.add(pBullet);
                    _world.getPlayerLayer().getObjects().add(pBullet);
                    _effectSounds.getPistolSound().play(80f);
                    _lastShot = System.currentTimeMillis();
                    break;
                case 1:
                    break;
                default:
                    break;

            }
        }


        Iterator<Projectile> iterator = _projectileSet.iterator();

        // Schleife iteriert über die Liste von Projektilen, bis die Liste kein Element mehr hat
        while(iterator.hasNext())
        {
            // Nächstes Element in der Liste
            Projectile tmpProjectile = iterator.next();

            // Überprüft ob jeweilige Kugel einen Gegner trifft, entfernt diese falls ja
            if(checkEnemyBulletCollision(tmpProjectile.getX(),tmpProjectile.getY()))
            {
                iterator.remove();
                _world.getPlayerLayer().getObjects().remove(tmpProjectile);
            }
            // Wenn die Position des Projektils außerhalb der Kartengröße ist
            else if (!_world.getMapRectangle().contains(tmpProjectile.getX(), tmpProjectile.getY()))
            {
                // Entferne Projektil aus dem PlayerLayer
                _world.getPlayerLayer().getObjects().remove(tmpProjectile);
                // Entferne Projektil aus der Liste
                iterator.remove();
            } else
            {
                // Aktualisiere Position des Projektils, falls es sich auf der Karte befindet
                tmpProjectile.update(delta);
            }
        }
    }

    private boolean checkEnemyBulletCollision(float bulletX, float bulletY)
    {
        boolean collision = false;
        Iterator<Enemy> iterator = _enemyList.iterator();
        while(iterator.hasNext())
        {
            Enemy tmpEnemy = iterator.next();

            Rectangle hitbox = new Rectangle(0,0,tmpEnemy.getTextureRegion().getRegionWidth()*16,tmpEnemy.getTextureRegion().getRegionHeight()*16);
            hitbox.setPosition(tmpEnemy.getX(), tmpEnemy.getY());

            if(hitbox.contains(bulletX,bulletY))
            {
                System.out.println("ÜBELST GETROFFEN AMK");
                tmpEnemy.setLife(tmpEnemy.getLife() - 30);
                if(tmpEnemy.getLife() <= 0)
                {
                    // Blutpfütze an Position des toten Gegners
                    TextureMapObject bloodpoudle = new TextureMapObject(new TextureRegion(new Texture(Gdx.files.internal("Blood.Puddle.png")),32,32));
                    bloodpoudle.setX(tmpEnemy.getX());
                    bloodpoudle.setY(tmpEnemy.getY());
                    bloodpoudle.setRotation(tmpEnemy.getRotation());
                    bloodpoudle.setOriginX(tmpEnemy.getOriginX());
                    bloodpoudle.setOriginY(tmpEnemy.getOriginY());
                    _world.getMapLayer().getObjects().add(bloodpoudle);

                    iterator.remove();
                    _world.getPlayerLayer().getObjects().remove(tmpEnemy);
                }
                collision = true;
            }
        }
        return collision;
    }
}