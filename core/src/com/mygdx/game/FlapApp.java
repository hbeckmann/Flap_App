package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

/**
 * Created by Hunter on 3/20/2017.
 */

public class FlapApp extends Game {

    public SpriteBatch batch;
    public OrthographicCamera camera;
    public SoundManager sound;

    public MyGdxGame gameScreen;
    public MainMenu mainMenu;
    public CreditsScreen credits;
    public SettingsScreen settings;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        camera.setToOrtho(false, 1080, 1920);
        sound = new SoundManager();
        gameScreen = new MyGdxGame(this);
        mainMenu = new MainMenu(this);
        credits = new CreditsScreen(this);
        settings = new SettingsScreen(this);
        setScreen(mainMenu);
    }


}
