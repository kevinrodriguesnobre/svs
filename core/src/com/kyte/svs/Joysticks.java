package com.kyte.svs;

import com.badlogic.gdx.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import sun.rmi.runtime.Log;

/**
 * Created by Yanneck dem Chef 24.02.2016 17:17 Uhr
 */
public class Joysticks {

    private Stage stage;
    private Touchpad touchpad;
    private Touchpad touchpad2;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;



    public Joysticks()
    {
        touchpadSkin = new Skin();
        touchpadSkin.add("Erste", new Texture(Gdx.files.internal("badlogic.jpg")));

        touchpadSkin.add("Zweite", new Texture(Gdx.files.internal("data/touchKnob.png")));

        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchpadSkin.getDrawable("Erste");
        touchKnob = touchpadSkin.getDrawable("Zweite");
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        touchpad = new Touchpad(10, touchpadStyle);
        touchpad.setBounds(15, 15, 100, 100);

        touchpad2 = new Touchpad(0, touchpadStyle);
        touchpad2.setBounds(480, 15, 100, 100);
        stage = new Stage();
        stage.addActor(touchpad);
        stage.addActor(touchpad2);

        Gdx.input.setInputProcessor(stage);

    }

    /**
     * Zeichnet die beiden Joysticks
     */
    public void renderJoysticks()
    {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }


    /**
     * Gibt die neue Rotation zurück
     * @param rot   aktuelle Rotation
     * @return  neue Rotation
     */
    public float getRotation(float rot)
    {return 0;
    /*
        float rotation = rot;
        if (touchpad2.getKnobPercentX() > 0) {
            if (touchpad2.getKnobPercentY() > 0) {
                rotation = (float) (-Math.atan(touchpad2.getKnobPercentX() / touchpad2.getKnobPercentY()) * 180 / Math.PI);
            } else if (touchpad2.getKnobPercentY() < 0) {
                rotation = (float) -(-180 + Math.atan(touchpad2.getKnobPercentX() / touchpad2.getKnobPercentY()) * 180 / Math.PI);
            }
        } else if (touchpad2.getKnobPercentX() < 0) {
            if (touchpad2.getKnobPercentY() > 0) {
                rotation = (float) -(Math.atan(touchpad2.getKnobPercentX() / touchpad2.getKnobPercentY()) * 180 / Math.PI);
            } else if (touchpad2.getKnobPercentY() < 0) {
                rotation = (float) -(-180 + Math.atan(touchpad2.getKnobPercentX() / touchpad2.getKnobPercentY()) * 180 / Math.PI);

            }
        }
        return rotation;*/
    }


    /**
     * Gibt einen Vector mit X und Y zurück
     * @return
     */
    public Vector2 getPositionVector()
    {
        return new Vector2(0,0);//return new Vector2(touchpad.getKnobPercentX(),touchpad.getKnobPercentY());
    }

}