package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by X303 on 07.03.2016.
 */
public class HpBar {
    Stage _stage;
    Skin _skin;
    TextureRegionDrawable _textureBar;
    ProgressBar _bar;

    public HpBar(Stage stage, float width, float height) {
        _stage = stage;
        _skin = new Skin();
        Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        _skin.add("white", new Texture(pixmap));

        //_textureBar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/redbar.png"))));
        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle(_skin.newDrawable("white", Color.DARK_GRAY), _skin.newDrawable("white", Color.CYAN));
        barStyle.knobBefore = barStyle.knob;
        _bar = new ProgressBar(0, 100, 0.5f, false, barStyle);
        _bar.setPosition(width / 3, height - 50);
        _bar.setSize(width / 3, _bar.getPrefHeight());
        _bar.setAnimateDuration(2);
        _bar.setValue(100);
        _stage.addActor(_bar);
    }

    public ProgressBar getBar(){return _bar;}

    public void getDmg(int dmg){_bar.setValue(_bar.getValue()-dmg);}

}
