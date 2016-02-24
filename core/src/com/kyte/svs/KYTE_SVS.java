package com.kyte_svs.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;


// import static com.badlogic.gdx.Gdx.*;

/**
 * Created by Yanneck dem Chef 24.02.2016 17:17 Uhr
 */
public class KYTE_SVS implements ApplicationListener {

    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 1;

    private Animation _walkDown;
    private TextureRegion[] _walkDownFrames;
    private SpriteBatch _spriteBatch;
    private Sprite _sprite;
    private Texture _texture;
    private TextureRegion _currentFrame;
    private float _stateTime;
    private Touchpad _touchpad;
    private Stage _stage;


    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Texture blockTexture;
    private Sprite blockSprite;
    private float blockSpeed;

    @Override
    public void create() {

        batch = new SpriteBatch();
        //Create camera
        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10f * aspectRatio, 10f);

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
        touchpad.setBounds(15, 15, 200, 200);

        //Create a Stage and add TouchPad
        stage = new Stage();
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);

        //Create block sprite
        blockTexture = new Texture(Gdx.files.internal("data/block.png"));
        blockSprite = new Sprite(blockTexture);
        //Set position to centre of the screen
        blockSprite.setPosition(Gdx.graphics.getWidth() / 2 - blockSprite.getWidth() / 2, Gdx.graphics.getHeight() / 2 - blockSprite.getHeight() / 2);

        blockSpeed = 5;
        spritesheedanimation();


    }

    /**
     * Vorratsdatenspeicherung
     **/
    private void spritesheedanimation() {
        // Später für Animationen
        /*
        _texture = new Texture(Gdx.files.internal("spritexb-2180.png"));
		_sprite = new Sprite(_texture);
        TextureRegion[][] tmp = TextureRegion.split(_texture,_texture.getWidth()/FRAME_COLS, _texture.getHeight()/FRAME_ROWS);
        _walkDownFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS;i++)
        {
            for(int j = 0; j < FRAME_COLS; j++)
            {
                _walkDownFrames[index++] = tmp[i][j];
            }
        }
        _walkDown = new Animation(0.09f, _walkDownFrames);


        _spriteBatch = new SpriteBatch();

        _stateTime = 0f;
	*/
    }


    @Override
    public void dispose() {
        /*
        _spriteBatch.dispose();
		_texture.dispose();
        */
        _stage.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        //Move blockSprite with TouchPad
        blockSprite.setX(blockSprite.getX() + touchpad.getKnobPercentX() * blockSpeed);
        blockSprite.setY(blockSprite.getY() + touchpad.getKnobPercentY() * blockSpeed);

        //Draw
        batch.begin();
        blockSprite.draw(batch);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        /*
        _stateTime += Gdx.graphics.getDeltaTime();
        _currentFrame = _walkDown.getKeyFrame(_stateTime, true);
        _spriteBatch.begin();
        _spriteBatch.draw(_currentFrame, 400, 400);
        _spriteBatch.end();
        */


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}