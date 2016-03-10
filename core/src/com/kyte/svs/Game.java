package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kyte.svs.Objects.AlienBullet;
import com.kyte.svs.Objects.EffectSounds;
import com.kyte.svs.Objects.GameStats;
import com.kyte.svs.Objects.PistolBullet;
import com.kyte.svs.Objects.Projectile;
import com.kyte.svs.Objects.ShotgunBullet;

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
    public static String _username = "Username";
    private int _hardwarekeycounter;
    private Player _player;
    private List<Projectile> _projectileList;
    private List<TextureMapObject> _bloodObjectList;
    private ArrayList<Enemy> _enemyList;
    private int _enemyAmount = 1;
    private int _round = 1;
    private OrthographicCamera _camera;
    private SpriteBatch batch;
    private static final int VIRTUAL_WIDTH = 480;
    private static final int VIRTUAL_HEIGHT = 320;
    private START _game;
    private Rectangle _backBoundsRectangle, _weaponSwitchRectangle;
    private long _lastShot, _lastPause, _lastWeaponSwitch, _lastBloodOptimize, _deathTime;
    private EffectSounds _effectSounds;
    private String[] _enemyTexture = new String[]{"Alien", "Alien2", "Fetti", "Kaeferblob", "Roboter.Boss", "Roboter", "Zombie"};
    private GameStats _gameStats;

    public static final int GAME_READY = 0;

    public static final int GAME_RUNNING = 1;

    public static final int GAME_PAUSED = 2;

    public static final int GAME_OVER = 4;

    public static int STATE;

    public static final int WEAPON_AMOUNT = 3;

    public Game(START game) {

        STATE = GAME_READY;

        _gameStats = new GameStats(System.currentTimeMillis());
        _hud = new HUD(_gameStats);

        _game = game;

        _hardwarekeycounter = 3;

        _projectileList = new LinkedList<Projectile>();

        _bloodObjectList = new LinkedList<TextureMapObject>();

        _effectSounds = new EffectSounds();


        Gdx.input.setCatchBackKey(true);

        _enemyList = new ArrayList<Enemy>();
        for (int i = 0; i < _enemyAmount; i++) {
            Enemy enemy = new Enemy(_enemyTexture[(int)(Math.random() * 7)], _hud.getHpBar());
            _enemyList.add(enemy);
        }

        _player = new Player();
        _player.setX(VIRTUAL_WIDTH);
        _player.setY(VIRTUAL_HEIGHT);

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

        _backBoundsRectangle = new Rectangle(0, _hud._height - (_hud._height / 7), _hud._width / 7, _hud._height / 7);
        _weaponSwitchRectangle = new Rectangle(_hud._width - (_hud._width / 7), _hud._height - (_hud._width / 7), _hud._height / 7, _hud._width / 7);

        _lastShot = 0;
        _lastWeaponSwitch = 0;
        _lastBloodOptimize = 0;
        _deathTime = 0;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render(float delta) {

        // Überprüft ob Pauseknopf gedrückt wurde und ob schon genug Zeit vergangen ist
        if((System.currentTimeMillis() - _lastPause) > 500) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
                _hardwarekeycounter += 1;
                _lastPause = System.currentTimeMillis();
            }
        }

        if(!(STATE == GAME_OVER)) {
            pauseCheck();
        }

        switch (STATE) {
            case GAME_READY:
                updateReady();
                break;
            case GAME_RUNNING:
                updateRunning(delta);
                break;
            case GAME_PAUSED:
                updatePaused();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }
    }


    private void checkWeaponSwitch()
    {
        if((System.currentTimeMillis() -_lastWeaponSwitch) > 300) {
            int nextWeaponID = _player.getWeapon().getCurrentWeaponID() + 1;
            if (nextWeaponID >= WEAPON_AMOUNT) {
                nextWeaponID = 0;
            }

            if(nextWeaponID == 2)
            {
                System.out.println("DebugLine");
            }
            _player.setWeapon(nextWeaponID);
            Texture weaponTexture = new Texture(Gdx.files.internal(_player.getWeapon().getCurrentWeaponPlayerTextureString()));
            _player.getTextureRegion().getTexture().dispose();
            _player.getTextureRegion().setTexture(weaponTexture);

            _hud.drawWeaponSwitchMenu(_player);
            _lastWeaponSwitch = System.currentTimeMillis();
        }

    }

    /**
     * Fragt ab, ob eines der Menüknöpfe gedrückt wurde
     */
    public void update() {
        if (Gdx.input.isTouched()) {
            Vector3 vec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            _hud.getStage().getCamera().unproject(vec);
            if (_backBoundsRectangle.contains(vec.x, vec.y)) {
                _game.setScreen(new MainMenu(_game));
            } else if (_weaponSwitchRectangle.contains(vec.x, vec.y)) {
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
        if (_hud.getJoysticks().touched() && ((System.currentTimeMillis() - _lastShot) > _player._weapon.getCurrentWeapon().getShootingFrequenz()) && (xandy > 0.999999f))
        {
            // Insgesamt geschossene Projektile um einen erhöhen
            _gameStats.setRoundsShot(_gameStats.getRoundsShot() + 1);
            switch (_player.getWeapon().getCurrentWeaponID()) {
                case 0:
                    PistolBullet pBullet = new PistolBullet(_player.getX(), _player.getY(), _player.getRotation(), _hud.getJoysticks().getRotationVector());
                    _projectileList.add(pBullet);
                    _world.getPlayerLayer().getObjects().add(pBullet);
                    _effectSounds.getPistolSound().play(80f);
                    _lastShot = System.currentTimeMillis();
                    break;
                case 1:
                    AlienBullet aBullet = new AlienBullet(_player.getX(), _player.getY(), _player.getRotation(), _hud.getJoysticks().getRotationVector());
                    _projectileList.add(aBullet);
                    _world.getPlayerLayer().getObjects().add(aBullet);
                    _effectSounds.getAlienSound().play(80f);
                    _lastShot = System.currentTimeMillis();
                    break;
                case 2:
                    Vector2 vec1 = _hud.getJoysticks().getRotationVector();
                    ShotgunBullet sBullet = new ShotgunBullet(_player.getX(), _player.getY(), _player.getRotation(), vec1);
                    _projectileList.add(sBullet);
                    _world.getPlayerLayer().getObjects().add(sBullet);
                    ShotgunBullet sBullet1 = new ShotgunBullet(_player.getX(), _player.getY(), _player.getRotation(), new Vector2(vec1.x + 0.1f, vec1.y + 0.1f));
                    _projectileList.add(sBullet1);
                    _world.getPlayerLayer().getObjects().add(sBullet1);
                    ShotgunBullet sBullet2 = new ShotgunBullet(_player.getX(), _player.getY(), _player.getRotation(), new Vector2(vec1.x - 0.1f, vec1.y - 0.1f));
                    _projectileList.add(sBullet2);
                    _world.getPlayerLayer().getObjects().add(sBullet2);
                    ShotgunBullet sBullet3 = new ShotgunBullet(_player.getX(), _player.getY(), _player.getRotation(), new Vector2(vec1.x + 0.2f, vec1.y + 0.2f));
                    _projectileList.add(sBullet3);
                    _world.getPlayerLayer().getObjects().add(sBullet3);
                    ShotgunBullet sBullet4 = new ShotgunBullet(_player.getX(), _player.getY(), _player.getRotation(), new Vector2(vec1.x - 0.2f, vec1.y - 0.2f));
                    _projectileList.add(sBullet4);
                    _world.getPlayerLayer().getObjects().add(sBullet4);

                    _effectSounds.getShotgunSound().play(80f);
                    _lastShot = System.currentTimeMillis();
                    break;
                default:
                    break;

            }
        }


        Iterator<Projectile> iterator = _projectileList.iterator();

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

                Rectangle hitbox = new Rectangle(tmpEnemy.getX(), tmpEnemy.getY(), tmpEnemy.getTextureRegion().getRegionWidth(), tmpEnemy.getTextureRegion().getRegionHeight());

                if (hitbox.contains(bulletX, bulletY)) {
                    // Treffersound
                    _effectSounds.get_hitmarkerSound().play(25f);
                    // Leben wird abhängig vom Schaden des jeweiligen Projektils abgezogen
                    tmpEnemy.setLife(tmpEnemy.getLife() - projectile.getDamage());

                    TextureMapObject bloodHit = new TextureMapObject(new TextureRegion(new Texture(Gdx.files.internal("bloodHit01.png")), 16, 15));
                    bloodHit.setX(tmpEnemy.getX());
                    bloodHit.setY(tmpEnemy.getY());
                    bloodHit.setRotation(tmpEnemy.getRotation());
                    bloodHit.setOriginX(tmpEnemy.getOriginX());
                    bloodHit.setOriginY(tmpEnemy.getOriginY());
                    _world.getMapLayer().getObjects().add(bloodHit);
                    _bloodObjectList.add(bloodHit);

                    if (tmpEnemy.getLife() <= 0) {
                        // Blutpfütze an Position des toten Gegners
                        TextureMapObject bloodpoudle;
                        if(tmpEnemy._name.equals("Roboter") || tmpEnemy._name.equals("Roboter.Boss"))
                        {
                            bloodpoudle = new TextureMapObject(new TextureRegion(new Texture(Gdx.files.internal("Mech.Puddle.png")), 32, 32));
                        } else {
                            bloodpoudle = new TextureMapObject(new TextureRegion(new Texture(Gdx.files.internal("Blood.Puddle.png")), 32, 32));
                        }
                        bloodpoudle.setX(tmpEnemy.getX());
                        bloodpoudle.setY(tmpEnemy.getY());
                        bloodpoudle.setRotation(tmpEnemy.getRotation());
                        bloodpoudle.setOriginX(tmpEnemy.getOriginX());
                        bloodpoudle.setOriginY(tmpEnemy.getOriginY());
                        _world.getMapLayer().getObjects().add(bloodpoudle);
                        _bloodObjectList.add(bloodpoudle);

                        iterator.remove();
                        _world.getPlayerLayer().getObjects().remove(tmpEnemy);

                        // KillStats um einen erhöhen
                        _gameStats.setKillCounter(_gameStats.getKillCounter() + 1);
                    }
                    collision = true;
                }
            }
        }
        return collision;
    }

    /**
     * Optimiert die Blutverteilung auf dem Maplayer
     */
    private void optimizeBlood()
    {
        if((System.currentTimeMillis() - _lastBloodOptimize > 500) && _bloodObjectList.size() > 130)
        {
            for(int i = 0;i < 4; ++i) {
                _world.getMapLayer().getObjects().remove(i);
                _bloodObjectList.remove(i);
            }
            _lastBloodOptimize = System.currentTimeMillis();
        }

        System.out.println(_bloodObjectList.size());

    }

    private void updateReady()
    {
        if(Gdx.input.justTouched())
        {
            STATE = GAME_RUNNING;
        }
    }

    /**
     * Rendert das Spiel, wenn es am Laufen ist bzw. sich im Zustand RUNNING befindet
     * @param delta
     */
    private void updateRunning(float delta)
    {
        if (_enemyList.size() == 0) {
            _round++;
            _hud.updateLabel(_round);
            _enemyAmount++;
            for (int i = 0; i < _enemyAmount; i++) {
                Enemy enemy = new Enemy(_enemyTexture[(int) (Math.random() * 7)], _hud.getHpBar());
                _enemyList.add(enemy);
            }
            for (Enemy enemy : _enemyList) {
                enemy.setX(_world.getMapLayer().getTileWidth() * 31 - (float) Math.random() * _world.getMapLayer().getTileWidth() * 30);
                enemy.setY(_world.getMapLayer().getTileHeight() * 31 - (float) Math.random() * _world.getMapLayer().getTileHeight() * 30);
                enemy.setCollisionLayer(_world.getCollisonLayer());
            }
            _world.addEnemy(_enemyList);

        }
        batch.setProjectionMatrix(_camera.combined);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Rotation des Spielers wird aktualisiert
        float tmpRot = _player.getRotation();
        _player.setRotation(_hud.getJoysticks().getRotation(tmpRot));
        _player.move(_hud.getJoysticks().getPositionVector(), delta, _world.getMapLayer());
        _player.setOrigin();

        shoot(delta);
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

        optimizeBlood();

        // Zeichnen der grafischen Oberfläche
        _world.renderMap();
        _hud.renderHUD();
    }

    /**
     * Wird ausgeführt während das Spiel pausiert ist
     */
    private void updatePaused()
    {
        _hud.renderPauseMenu();
    }


    private void updateGameOver()
    {
        if(_deathTime == 0)
        {
            _player.getTextureRegion().setTexture(new Texture(Gdx.files.internal("Player/Player_DeathState.png")));
            _effectSounds.getDeathSound().play(90f);
            _deathTime = System.currentTimeMillis();
        }
        else if((_deathTime != 0) && (System.currentTimeMillis() - _deathTime) < 3000)
        {
            _world.renderMap();
            _hud.renderHUD();
        }
        else if(System.currentTimeMillis() -_deathTime > 3000)
        {
            _hud.renderDeathScreen();

            if (Gdx.input.isTouched()) {
                Vector3 vec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                _hud.getStage().getCamera().unproject(vec);
                if (_hud.getMenuButtonRectangle().contains(vec.x, vec.y)) {
                   // HUD.insertIntoDb(HUD._usernameLabel.getText().toString(), _gameStats.getKillCounter());
                    _game.setScreen(new MainMenu(_game));
                } else if (_hud.getRestartButtonRectangle().contains(vec.x, vec.y)) {
                    //HUD.insertIntoDb(HUD._usernameLabel.getText().toString(), _gameStats.getKillCounter());
                    _game.setScreen(new Game(_game));
                }
            }
        }

    }

    /**
     * Überprüft, ob das Spiel pausiert werden soll
     */
    private void pauseCheck()
    {
        if(_hardwarekeycounter%2 == 0)
        {
            STATE = GAME_PAUSED;
        }
        else
        {
            STATE = GAME_RUNNING;
        }
    }
}