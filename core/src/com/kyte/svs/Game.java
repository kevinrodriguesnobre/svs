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
import com.kyte.svs.Objects.AlienBullet;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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

    private HUD _hud;

    private Player _player;
    private List<Projectile> _projectileSet;
    private ArrayList<Enemy> _enemyList;
    private int _enemyAmount = 15;
    private OrthographicCamera _camera;
    private SpriteBatch batch;
    private static final int VIRTUAL_WIDTH = 480;
    private static final int VIRTUAL_HEIGHT = 320;
    private START _game;
    private Rectangle _backBoundsRectangle, _weaponSwitchRectangle;
    private Vector3 touchPoint;
    private long _lastShot;
    private EffectSounds _effectSounds;
    private Projectile _removeP;

    private Button _backButton;

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


        touchPoint = new Vector3();

        _camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        _camera.position.set(_player.getX() + VIRTUAL_WIDTH / 2, _player.getY() + VIRTUAL_HEIGHT / 2, 1);
        _camera.update();

        _world = new World(_player, _camera, _enemyList);

        //Enemy spawning mindestens 180Pixel? entfernt vom Spieler
        float enemyPositionX = 0;
        float enemyPositionY = 0;
        for (Enemy enemy : _enemyList) {
            do{
                enemyPositionX = _world.getMapLayer().getTileWidth() * 31 - (float) Math.random() * _world.getMapLayer().getTileWidth() * 30;
                enemyPositionY = _world.getMapLayer().getTileHeight() * 31 - (float) Math.random() * _world.getMapLayer().getTileHeight() * 30;
            }while(Math.abs(_player.getX() - enemyPositionX) < 180 || Math.abs(_player.getY() - enemyPositionY) < 180);
            enemy.setX(enemyPositionX);
            enemy.setY(enemyPositionY);
            enemy.setCollisionLayer(_world.getCollisonLayer());
        }
        _player.setCollisionLayer(_world.getCollisonLayer());

        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        _hud = new HUD();

        _backBoundsRectangle = new Rectangle(0, _hud._height - 100, 200, 100);
        _weaponSwitchRectangle = new Rectangle(_hud._width - 400, _hud._height - 200, 400, 200);

        _lastShot = 0;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render(float deltax) {
        if(_enemyList.size() == 0){
            for (int i = 0; i < _enemyAmount; i++) {
                Enemy enemy = new Enemy();
                _enemyList.add(enemy);
            }
            for (Enemy enemy : _enemyList) {
                enemy.setX(_world.getMapLayer().getTileWidth() * 31 - (float) Math.random() * _world.getMapLayer().getTileWidth() * 30);
                enemy.setY(_world.getMapLayer().getTileHeight() * 31 - (float) Math.random() * _world.getMapLayer().getTileHeight() * 30);
                enemy.setCollisionLayer(_world.getCollisonLayer());
            }
            _world.addEnemy(_enemyList);
            System.out.println("KILLED");
        }
        batch.setProjectionMatrix(_camera.combined);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Rotation des Spielers wird aktualisiert
        float tmpRot = _player.getRotation();
        _player.setRotation(_hud.getJoysticks().getRotation(tmpRot));
        _player.move(_hud.getJoysticks().getPositionVector(), deltax, _world.getMapLayer());
        _player.setOrigin();

        shoot(deltax);
        update();
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
        _hud.renderHUD();
    }


    private void checkWeaponSwitch()
    {

          int nextWeaponID = _player.getWeapon().getCurrentWeaponID() + 1;
          if (nextWeaponID > 1)
          {
              nextWeaponID = 0;
          }

          _player.setWeapon(nextWeaponID);
          Texture weaponTexture = new Texture(Gdx.files.internal(_player.getWeapon().getCurrentWeaponPlayerTextureString()));
          _player.getTextureRegion().getTexture().dispose();
          _player.getTextureRegion().setTexture(weaponTexture);

        _hud.drawWeaponSwitchMenu(_player);

    }
    public void update() {
        if (Gdx.input.isTouched()) {
            Vector3 vec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            _hud.getStage().getCamera().unproject(vec);
            if (_backBoundsRectangle.contains(vec.x, vec.y)) {
                _game.setScreen(new MainMenu(_game));
            }
            else if (_weaponSwitchRectangle.contains(vec.x,vec.y))
            {
                checkWeaponSwitch();
            }
        }
        _hud.drawWeaponSwitchMenu(_player);

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
        float xandy = Math.abs(_hud.getJoysticks().getRotationVector().x) + Math.abs(_hud.getJoysticks().getRotationVector().y);
        if (_hud.getJoysticks().touched() && ((System.currentTimeMillis() - _lastShot) > _player._weapon.getCurrentWeapon().getShootingFrequenz()) && (xandy > 0.999999f)) {
            switch (_player.getWeapon().getCurrentWeaponID()) {
                case 0:
                    PistolBullet pBullet = new PistolBullet(_player.getX(), _player.getY(), _player.getRotation(), _hud.getJoysticks().getRotationVector());
                    _projectileSet.add(pBullet);
                    _world.getPlayerLayer().getObjects().add(pBullet);
                    _effectSounds.getPistolSound().play(80f);
                    _lastShot = System.currentTimeMillis();
                    _hud.getHpBar().getDmg(1);
                    break;
                case 1:
                    AlienBullet aBullet = new AlienBullet(_player.getX(), _player.getY(), _player.getRotation(), _hud.getJoysticks().getRotationVector());
                    _projectileSet.add(aBullet);
                    _world.getPlayerLayer().getObjects().add(aBullet);
                    _effectSounds.getAlienSound().play(80f);
                    _lastShot = System.currentTimeMillis();
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
            if(checkEnemyBulletCollision(tmpProjectile))
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

    private boolean checkEnemyBulletCollision(Projectile projectile)
    {
        boolean collision = false;
        float bulletX = projectile.getX();
        float bulletY = projectile.getY();

        if(!_enemyList.isEmpty()) {
            Iterator<Enemy> iterator = _enemyList.iterator();
            while (iterator.hasNext()) {
                Enemy tmpEnemy = iterator.next();

                System.out.println("X: " + tmpEnemy.getTextureRegion().getRegionWidth() + "\nY: " + tmpEnemy.getTextureRegion().getRegionHeight());

                Rectangle hitbox = new Rectangle(tmpEnemy.getX(), tmpEnemy.getY(), tmpEnemy.getTextureRegion().getRegionWidth(), tmpEnemy.getTextureRegion().getRegionHeight());

                if (hitbox.contains(bulletX, bulletY)) {
                    tmpEnemy.setLife(tmpEnemy.getLife() - projectile.getDamage());
                    if (tmpEnemy.getLife() <= 0) {
                        // Blutpfütze an Position des toten Gegners
                        TextureMapObject bloodpoudle = new TextureMapObject(new TextureRegion(new Texture(Gdx.files.internal("Blood.Puddle.png")), 32, 32));
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
        }
        return collision;
    }
}