package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import javax.xml.soap.Text;

/**
 * Created by Hunter on 3/20/2017.
 */

public class UIManager {

    private Texture birdHeader;
    private Texture playButtonTexture;
    private Texture appTitle;
    private Button playButtonActor;
    private Button settingsButtonActor;
    private Button creditsButtonActor;

    public UIManager() {
        birdHeader = new Texture(Gdx.files.internal("frame_1.png"));
        playButtonTexture = new Texture(Gdx.files.internal("play_btn_out.png"));
        appTitle = new Texture(Gdx.files.internal("title.png"));
        playButtonActor = new Button(
                new SpriteDrawable(new Sprite(playButtonTexture)),
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("play_btn_in.png"))))
        );
        settingsButtonActor = new Button(
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("settings_btn_out.png")))),
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("settings_btn_in.png"))))
        );
        creditsButtonActor = new Button(
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("credits_btn_out.png")))),
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("credits_btn_in.png"))))
        );
    }

    public Texture getBirdHeader() {
        return birdHeader;
    }

    public Texture getPlayButtonTexture() {
        return playButtonTexture;
    }

    public Button getPlayButtonActor() {
        return playButtonActor;
    }

    public Button getSettingsButtonActor() {
        return settingsButtonActor;
    }

    public Button getCreditsButtonActor() {
        return creditsButtonActor;
    }

    public Texture getAppTitle() {
        return appTitle;
    }

    public void dispose() {
        birdHeader.dispose();
        playButtonTexture.dispose();
        playButtonActor = null;
    }
}
