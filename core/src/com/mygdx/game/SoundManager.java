package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Hunter on 3/16/2017.
 */

public class SoundManager {

    private Music gameMusic, collectSound;
    private Sound jumpSound, scoreSound, explosionSound;
    private Float musicVol, sfxVol;
    private Preferences prefs;

    public SoundManager() {

        gameMusic =  Gdx.audio.newMusic(Gdx.files.internal("something_elated.mp3"));
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump3.wav"));
        scoreSound = Gdx.audio.newSound(Gdx.files.internal("coin2.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
        collectSound = Gdx.audio.newMusic(Gdx.files.internal("collect.wav"));
        prefs = Gdx.app.getPreferences("user_preferences");
        musicVol = prefs.getFloat("music_volume", 1f);
        sfxVol = prefs.getFloat("sfx_volume", 1f);
        gameMusic.setVolume(prefs.getFloat("music_volume", 1f));
        gameMusic.setLooping(true);
        gameMusic.play();
    }

    public Music getGameMusic() {
        return gameMusic;
    }

    public Sound getJumpSound() {
        return jumpSound;
    }

    public Sound getScoreSound() {
        return scoreSound;
    }

    public Sound getExplosionSound() {
        return explosionSound;
    }

    public Music getCollectSound() {
        return collectSound;
    }

    public void setGameMusicVolume() {
        musicVol = prefs.getFloat("music_volume", 1f);
        gameMusic.setVolume(musicVol);
    }

    public void setSfxVolume() {
        sfxVol = prefs.getFloat("sfx_volume", 1f);
        collectSound.setVolume(sfxVol);
    }

    public float getSfxVolume() {
        return sfxVol;
    }

    public void dispose() {
        gameMusic.dispose();
        jumpSound.dispose();
        scoreSound.dispose();
        explosionSound.dispose();
        collectSound.dispose();
    }
}
