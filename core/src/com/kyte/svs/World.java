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
    MapLayer _playerLayer;
    TiledMapTileLayer _mapLayer;
    TextureRegion textureRegion;
    Player _player;
    Enemy _enemy;

    public World(Player player, OrthographicCamera camera)//, Enemy enemy)
    {
        //_enemy = enemy;
        _player = player;
        _camera = camera;
        createMap();
    }




    public void createMap()
    {
        //addEnemy();
        // Läd die Tiled Map
        _tiledMap = new TmxMapLoader().load("world1.tmx");
        _tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(_tiledMap);


        // Die Objektebene aus der TiledMap wird der Variable Player Layer zugewiesen
        _playerLayer = _tiledMap.getLayers().get("PlayerLayer");


        // Ebene mit Kollisionsobjekten wird initialisiert
        _mapLayer = (TiledMapTileLayer) _tiledMap.getLayers().get("MapLayer");


        // stellt die Startposition des Players ein und fügt ihn der Playerebene hinzu
        _playerLayer.getObjects().add(_player);
    }

    public void renderMap()
    {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _tiledMapRenderer.setView(_camera);
        _tiledMapRenderer.render();
    }


    public TiledMapTileLayer getCollisonLayer()
    {
        return _mapLayer;
    }

    public TiledMapTileLayer getMapLayer(){
        return (TiledMapTileLayer) _tiledMap.getLayers().get("MapLayer");
    }

    void addEnemy(){

        // Die Objektebene aus der TiledMap wird der Variable Player Layer zugewiesen
        _playerLayer = _tiledMap.getLayers().get("PlayerLayer");

        // Ebene mit Kollisionsobjekten wird initialisiert
        _mapLayer = (TiledMapTileLayer) _tiledMap.getLayers().get("MapLayer");


        // stellt die Startposition des Players ein und fügt ihn der Objektebene hinzu
        _enemy.setName("Enemy");
        _enemy.setX(_enemy.getX());
        _enemy.setY(_enemy.getY());
        _playerLayer.getObjects().add(_enemy);
    }
}

