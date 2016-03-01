package com.kyte.svs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class START extends Game {
    // used by all screens
    public SpriteBatch batcher;
    @Override
    public void create () {
        batcher = new SpriteBatch();
        setScreen(new MainMenu(this));
    }
    @Override
    public void render() {
        super.render();
    }
}
