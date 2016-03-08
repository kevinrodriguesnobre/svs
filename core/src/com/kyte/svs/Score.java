package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by X303 on 08.03.2016.
 */
public class Score extends ScreenAdapter {

    START game;
    OrthographicCamera guiCam;
    Rectangle backBounds;
    Vector3 touchPoint;
    long time;
    Label _label;
    Stage _stage;


    public Score (START game)
    {
        this.game = game;
        guiCam = new OrthographicCamera(1080, 1920);
        guiCam.position.set(1080 / 2,  1920 / 2, 0);
        backBounds = new Rectangle(0,0, 290, 150);
        touchPoint = new Vector3();
        time = System.currentTimeMillis();

        _stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        _label = new Label("NAME1                 SCORE", skin);
        _label.setPosition(Gdx.graphics.getWidth() / 6.5f, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10);
        _label.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        _label.setFontScale(Gdx.graphics.getWidth() / 400);


        _stage.addActor(_label);
        Gdx.input.setInputProcessor(_stage);
    }

    public void update () {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            time = System.currentTimeMillis();
            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new MainMenu(game));
                return;
            }
        }
    }
    public void draw () {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        game.batcher.setProjectionMatrix(guiCam.combined);
        game.batcher.disableBlending();
        game.batcher.enableBlending();
        game.batcher.begin();
        game.batcher.draw(new Texture(Gdx.files.internal("data/background1.png")), 0, 0, 1080, 1920);
        game.batcher.draw(new Texture(Gdx.files.internal("data/backbutton.png")), 0, 0, 290, 150);
        game.batcher.draw(new Texture(Gdx.files.internal("data/backbutton.png")), 0, 0, 290, 150);
        _stage.act(Gdx.graphics.getDeltaTime());
        _stage.draw();
        explosion();
        //game.batcher.draw(Settings.soundEnabled ? Assets.soundOn : Assets.soundOff, 0, 0, 64, 64);
        game.batcher.end();
    }

    public void explosion(){
        if((System.currentTimeMillis() - time) < 500){
            game.batcher.draw(new Texture(Gdx.files.internal("data/hand.png")),touchPoint.x - 100 ,touchPoint.y - 100,200,200);
        }
    }
    @Override
    public void render (float delta) {
        update();
        draw();
    }
    @Override
    public void pause () {
        //Settings.save();
    }
}

