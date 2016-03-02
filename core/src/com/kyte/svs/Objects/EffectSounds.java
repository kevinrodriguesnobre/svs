package com.kyte.svs.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Yanneck on 02.03.2016.
 */
public class EffectSounds
{
    Sound _pistolSound;

    public EffectSounds()
    {
        _pistolSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/M4A1_Single.mp3"));
    }


    public Sound getPistolSound() {
        return _pistolSound;
    }
}
