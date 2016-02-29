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
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;

/**
 * Enthält die Karte, Spieler, Mobs usw.
 */
public class World {
    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    Texture texture;
    MapLayer objectLayer;
    TextureRegion textureRegion;
    Player _player;


    public World() {
        //createMap();
    }




    public void createMap() {
        float w = Gdx.graphics.getWidth() / 3;
        float h = Gdx.graphics.getHeight() / 4;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();
        tiledMap = new TmxMapLoader().load("ersteMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);

        texture = new Texture(Gdx.files.internal("data/Player.png"));

        objectLayer = tiledMap.getLayers().get("objects");
        textureRegion = new TextureRegion(texture, 64, 64);

        TextureMapObject tmo = new TextureMapObject(textureRegion);
        tmo.setX(0);
        tmo.setY(0);
        objectLayer.getObjects().add(tmo);
    }

    public void renderMap() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }


    public Player getPlayer() {
        return _player;
    }
}

