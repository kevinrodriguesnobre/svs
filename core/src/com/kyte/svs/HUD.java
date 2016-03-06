package com.kyte.svs;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class HUD {

    private Stage _stage;
    private Joysticks _joysticks;
    float _width;
    float _height;

    public HUD()
    {
        _stage = new Stage();
        _width = Gdx.graphics.getWidth();
        _height = Gdx.graphics.getHeight();
        _joysticks = new Joysticks(_stage,_width,_height);



        Gdx.input.setInputProcessor(_stage);
    }

    public void renderHUD()
    {
        _stage.act(Gdx.graphics.getDeltaTime());
        _stage.draw();
    }

    public Joysticks getJoysticks()
    {
        return _joysticks;
    }

    public Stage getStage()
    {
        return _stage;
    }


}
