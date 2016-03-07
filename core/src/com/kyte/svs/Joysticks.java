package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by Yanneck dem Chef 24.02.2016 17:17 Uhr
 */
public class Joysticks {


    private Stage _stage;
    private Touchpad touchpad;
    private Touchpad touchpad2;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Button _backButton;



    public Joysticks(Stage stage, float width, float height)
    {
        float touchpadSize = width / 5;
        touchpadSkin = new Skin();
        touchpadSkin.add("Erste", new Texture(Gdx.files.internal("data/touchBackground.png")));

        touchpadSkin.add("Zweite", new Texture(Gdx.files.internal("data/touchKnob.png")));

        touchpadSkin.add("Dritte", new Texture(Gdx.files.internal("data/backbutton.png")));

        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchpadSkin.getDrawable("Erste");
        touchKnob = touchpadSkin.getDrawable("Zweite");

        touchpadStyle.background = touchBackground;
        touchKnob.setMinHeight(touchpadSize / 4);
        touchKnob.setMinWidth(touchpadSize / 4);
        touchpadStyle.knob = touchKnob;

        touchpad = new Touchpad(0, touchpadStyle);
        touchpad.setBounds(0.2f * touchpadSize, touchpadSize/5, touchpadSize, touchpadSize);

        touchpad2 = new Touchpad(0, touchpadStyle);
        touchpad2.setBounds(width - 1.2f * touchpadSize, touchpadSize/5, touchpadSize, touchpadSize);

        _backButton = new Button(touchpadSkin.getDrawable("Dritte"));
        _backButton.setBounds(0, height - 100, 200, 100);

        _stage = stage;
        _stage.addActor(touchpad);
        _stage.addActor(touchpad2);

        Gdx.input.setInputProcessor(_stage);

    }

    /**
     * Zeichnet die beiden Joysticks
     */
    public void renderJoysticks()
    {
        _stage.act(Gdx.graphics.getDeltaTime());
        _stage.draw();
    }


    /**
     * Gibt die neue Rotation zurück
     * @param rot   aktuelle Rotation
     * @return  neue Rotation
     */
    public float getRotation(float rot)
    {
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
        return rotation;
    }


    /**
     * Gibt einen Vector mit X und Y zurück
     * @return Vector mit X und Y Wert
     */
    public Vector2 getPositionVector()
    {
        return new Vector2(touchpad.getKnobPercentX(),touchpad.getKnobPercentY());
    }

    public Vector2 getRotationVector()
    {
        return new Vector2(touchpad2.getKnobPercentX(),touchpad2.getKnobPercentY());
    }

    public boolean touched()
    {
        return touchpad2.isTouched();
    }



}