package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

import java.sql.*;

public class START extends Game {
    // used by all screens
    public SpriteBatch batcher;
    public Music music;
    @Override
    public void create () {


        music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/NeverRun.mp3"));
        music.play();
        music.setLooping(true);
        batcher = new SpriteBatch();
        setScreen(new MainMenu(this));
    }
    @Override
    public void render() {
        super.render();
    }
}
