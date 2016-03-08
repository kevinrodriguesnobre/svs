package com.kyte.svs.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class EffectSounds
{
    Sound _pistolSound, _alienSound, _hitmarkerSound, _deathSound;

    public EffectSounds()
    {
        _pistolSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/M4A1_Single.mp3"));
        _alienSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Alienwaffe_Single.mp3"));
        _hitmarkerSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/Hitmarker_Sound.mp3"));
        _deathSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/death_sound.mp3"));
    }


    public Sound getPistolSound() {
        return _pistolSound;
    }
    public Sound getAlienSound ()
    {
        return _alienSound;
    }
    public Sound get_hitmarkerSound()
    {
        return _hitmarkerSound;
    }
    public Sound getDeathSound()
    {
        return _deathSound;
    }
}
