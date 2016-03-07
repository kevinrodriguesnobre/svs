package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class START extends Game {
    // used by all screens
    public SpriteBatch batcher;
    @Override
    public void create () {
        //Music music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/NeverRun.mp3"));
        //music.play();
        //music.setLooping(true);
        batcher = new SpriteBatch();
        setScreen(new MainMenu(this));
    }
    @Override
    public void render() {
        super.render();
    }
}
