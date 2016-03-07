package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Enthält die Karte, Spieler, Mobs usw.
 */
public class World {
    TiledMap _tiledMap;
    OrthographicCamera _camera;
    TiledMapRenderer _tiledMapRenderer;
    MapLayer _playerLayer;
    TiledMapTileLayer _mapLayer;
    Player _player;
    ArrayList<Enemy> _enemyList;
    Rectangle _mapRectangle;

    public World(Player player, OrthographicCamera camera, ArrayList<Enemy> enemyList) {
        _enemyList = enemyList;
        _player = player;
        _camera = camera;
        createMap();
    }


    public void createMap() {
        // Läd die Tiled Map
        _tiledMap = new TmxMapLoader().load("world1.tmx");
        _tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(_tiledMap);

        addEnemy();

        // Die Objektebene aus der TiledMap wird der Variable Player Layer zugewiesen
        _playerLayer = _tiledMap.getLayers().get("PlayerLayer");


        // Ebene mit Kollisionsobjekten wird initialisiert
        _mapLayer = (TiledMapTileLayer) _tiledMap.getLayers().get("MapLayer");

        // Speichert die Map als Rechteck um Positionen abzufragen
        _mapRectangle = new Rectangle(0, 0, _mapLayer.getWidth() * 32, _mapLayer.getHeight() * 32);

        // stellt die Startposition des Players ein und fügt ihn der Playerebene hinzu
        _playerLayer.getObjects().add(_player);
    }

    public void renderMap() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _tiledMapRenderer.setView(_camera);
        _tiledMapRenderer.render();
    }


    public TiledMapTileLayer getCollisonLayer() {
        return _mapLayer;
    }

    public TiledMapTileLayer getMapLayer() {
        return (TiledMapTileLayer) _tiledMap.getLayers().get("MapLayer");
    }

    public MapLayer getPlayerLayer() {
        return _playerLayer;
    }

    void addEnemy() {

        // Die Objektebene aus der TiledMap wird der Variable Player Layer zugewiesen
        _playerLayer = _tiledMap.getLayers().get("PlayerLayer");

        // stellt die Startposition des Players ein und fügt ihn der Objektebene hinzu
        for (Enemy enemy : _enemyList) {
            enemy.setName("Enemy");
            enemy.setX(enemy.getX());
            enemy.setY(enemy.getY());
            _playerLayer.getObjects().add(enemy);
        }
    }

    public void addEnemy(ArrayList<Enemy> enemyList){
        // Die Objektebene aus der TiledMap wird der Variable Player Layer zugewiesen
        _playerLayer = _tiledMap.getLayers().get("PlayerLayer");

        // stellt die Startposition des Players ein und fügt ihn der Objektebene hinzu
        for (Enemy enemy : enemyList) {
            enemy.setName("Enemy");
            enemy.setX(enemy.getX());
            enemy.setY(enemy.getY());
            _playerLayer.getObjects().add(enemy);
        }
    }

    public Rectangle getMapRectangle() {
        return _mapRectangle;
    }


}

