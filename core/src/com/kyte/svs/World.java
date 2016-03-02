package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Enthält die Karte, Spieler, Mobs usw.
 */
public class World {
    TiledMap _tiledMap;
    OrthographicCamera _camera;
    TiledMapRenderer _tiledMapRenderer;
    Texture _texture;
    MapLayer _objectLayer;
    TextureRegion textureRegion;
    Player _player;
    TextureMapObject _playerTMO;
    Bullet bullet;

    public World(Player player, OrthographicCamera camera)
    {
        _player = player;
        _camera = camera;
        createMap();
    }




    public void createMap()
    {
        _tiledMap = new TmxMapLoader().load("ersteMap.tmx");
        _tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(_tiledMap);

        _texture = _player.getTexture();

        _objectLayer = _tiledMap.getLayers().get("objects");
        textureRegion = new TextureRegion(_texture, 32, 32);

        TextureMapObject _playerTMO = new TextureMapObject(textureRegion);
        _playerTMO.setName("Player");
        _playerTMO.setX(_player.getX());
        _playerTMO.setY(_player.getY());
        _playerTMO.setRotation(3.49f);
        _objectLayer.getObjects().add(_playerTMO);


    }

    public void renderMap()
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //_camera.update();

        updatePlayer();
        _tiledMapRenderer.setView(_camera);
        _tiledMapRenderer.render();
    }

    /**
     * Zieht das TextureMapObjekt für den Spieler aus dem ObjectLayer und updatet seine
     * Position und Rotation
     */
    private void updatePlayer()
    {
        TextureMapObject tmpTMO = (TextureMapObject) _objectLayer.getObjects().get("Player");

        tmpTMO.setX(_player.getX());
        tmpTMO.setY(_player.getY());

        tmpTMO.setOriginX(_player.getOriginX());
        tmpTMO.setOriginY(_player.getOriginY());

        float radian = _player.getRotation();

        tmpTMO.setRotation(radian);
    }

    private void updateBullet()
    {
        if(bullet.existiert){

        }

    }

    public void createBullet(){

        bullet = new Bullet(_player.getX(),_player.getY(),_player.getRotation(),new Sprite(new Texture(Gdx.files.internal("data/Player.png")),5,5));

    }

}

