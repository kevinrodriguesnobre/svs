package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    TiledMapTileLayer _collisionLayer;
    TextureRegion textureRegion;
    Player _player;
    TextureMapObject _playerTMO;

    public World(Player player, OrthographicCamera camera)
    {
        _player = player;
        _camera = camera;
        createMap();
    }




    public void createMap()
    {
        // Läd die Tiled Map
        _tiledMap = new TmxMapLoader().load("world1.tmx");
        _tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(_tiledMap);

        // Bezieht die Textur vom Player-Objekt
        _texture = _player.getTexture();

        // Die Objektebene aus der TiledMap wird der Variable _objectLayer zugewiesen
        _objectLayer = _tiledMap.getLayers().get("PlayerLayer");
        textureRegion = new TextureRegion(_texture, 32, 32);

        // Ebene mit Kollisionsobjekten wird initialisiert
        _collisionLayer = (TiledMapTileLayer) _tiledMap.getLayers().get("MapLayer");


        // stellt die Startposition des Players ein und fügt ihn der Objektebene hinzu
        TextureMapObject _playerTMO = new TextureMapObject(textureRegion);
        _playerTMO.setName("Player");
        _playerTMO.setX(_player.getX());
        _playerTMO.setY(_player.getY());
        _objectLayer.getObjects().add(_playerTMO);
    }

    public void renderMap()
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

    public TiledMapTileLayer getCollisonLayer()
    {
        return _collisionLayer;
    }

    public TiledMapTileLayer getMapLayer(){
        return (TiledMapTileLayer) _tiledMap.getLayers().get("MapLayer");
    }
}

