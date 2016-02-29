package com.kyte.svs;

import com.badlogic.gdx.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
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


/**
 * Created by Yanneck dem Chef 24.02.2016 17:17 Uhr
 */
public class KYTE_SVS implements ApplicationListener {

    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Touchpad touchpad;
    private Touchpad touchpad2;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Texture blockTexture;
    private Sprite blockSprite;
    private float blockSpeed;
    private Rectangle viewport;
    private static final int VIRTUAL_WIDTH = 480;
    private static final int VIRTUAL_HEIGHT = 320;
    private static final float ASPECT_RATIO =
            (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;

    @Override
    public void create() {

        batch = new SpriteBatch();
        //Create camera
        /*float aspectRatio = (float) Gdx.graphics.getWidth() / (float) ;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10f * aspectRatio, 10f); */
        camera = new OrthographicCamera();
        //viewport = new FitViewport(800, 480, camera);
        //viewport = new ScalingViewport(Scaling.fill, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("data/touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("data/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 100, 100);

        touchpad2 = new Touchpad(0, touchpadStyle);
        touchpad2.setBounds(480, 15, 100, 100);
        //Create a Stage and add TouchPad
        stage = new Stage();
        //stage.setViewport(new ExtendViewport(800, 800));
        stage.addActor(touchpad);
        stage.addActor(touchpad2);

        Gdx.input.setInputProcessor(stage);

        //Create block sprite
        blockTexture = new Texture(Gdx.files.internal("data/Player.png"));
        blockSprite = new Sprite(blockTexture);
        //Set position to centre of the screen
        blockSprite.setPosition(Gdx.graphics.getWidth() / 2 - blockSprite.getWidth() / 2, Gdx.graphics.getHeight() / 2 - blockSprite.getHeight() / 2);

        blockSpeed = 10;
    }


    @Override
    public void dispose() {


    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        // set viewport
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
                (int) viewport.width, (int) viewport.height);

        //Move blockSprite with TouchPad
        double d = Math.atan2(touchpad.getKnobY(), touchpad.getKnobX());
        float degree = (float) d;


        blockSprite.setX(blockSprite.getX() + touchpad.getKnobPercentX() * blockSpeed);
        blockSprite.setY(blockSprite.getY() + touchpad.getKnobPercentY() * blockSpeed);

        float rotation = blockSprite.getRotation();
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
        blockSprite.setRotation(rotation);

        //Draw
        batch.begin();
        blockSprite.draw(batch);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        // calculate new viewport
        float aspectRatio = (float) width / (float) height;
        float scale = 1f;
        Vector2 crop = new Vector2(0f, 0f);

        if(aspectRatio > ASPECT_RATIO)
        {
            scale = (float)height/(float)VIRTUAL_HEIGHT;
            crop.x = (width - VIRTUAL_WIDTH*scale)/2f;
        }
        else if(aspectRatio < ASPECT_RATIO)
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
            crop.y = (height - VIRTUAL_HEIGHT*scale)/2f;
        }
        else
        {
            scale = (float)width/(float)VIRTUAL_WIDTH;
        }

        float w = (float)VIRTUAL_WIDTH*scale;
        float h = (float)VIRTUAL_HEIGHT*scale;
        viewport = new Rectangle(crop.x, crop.y, w, h);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}