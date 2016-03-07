package com.kyte.svs.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class EffectSounds
{
    Sound _pistolSound;
    Sound _alienSound;

    public EffectSounds()
    {
        _pistolSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/M4A1_Single.mp3"));
        _alienSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Alienwaffe_Single.mp3"));
    }


    public Sound getPistolSound() {
        return _pistolSound;
    }
    public Sound getAlienSound ()
    {
        return _alienSound;
    }
}
