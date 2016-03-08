package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by X303 on 08.03.2016.
 */
public class Options extends ScreenAdapter {

    START game;
    OrthographicCamera guiCam;
    Rectangle soundBounds;
    Vector3 touchPoint;
    long time;


    public Options (START game)
    {
        this.game = game;
        guiCam = new OrthographicCamera(1080, 1920);
        guiCam.position.set(1080 / 2,  1920 / 2, 0);
        soundBounds = new Rectangle(250, 1400, 579, 300);
        touchPoint = new Vector3();
        time = System.currentTimeMillis();
    }

    public void update () {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            time = System.currentTimeMillis();
            if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
                if(game.music.isPlaying()) {

                    game.music.stop();
                }else
                {
                    game.music.play();

                }
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
        game.batcher.draw(new Texture(Gdx.files.internal("data/musicbutton.png")), 250, 1400, 579, 300);
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
