package com.kyte.svs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Yanneck on 29.02.2016.
 */
public class CameraManager extends OrthographicCamera{

    float ASPECT_RATIO;
    public CameraManager(float VIRTUAL_WIDTH, float VIRTUAL_HEIGHT)
    {
        super(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        ASPECT_RATIO = VIRTUAL_WIDTH / VIRTUAL_HEIGHT;
    }

}
