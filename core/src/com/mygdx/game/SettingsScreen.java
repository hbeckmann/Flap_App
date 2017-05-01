package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by Hunter on 3/25/2017.
 */

public class SettingsScreen implements Screen {

    private Background background;
    private FlapApp games;
    private ShapeRenderer shape;
    private BitmapFont font;
    private BitmapFont font2;
    private Slider slider;
    private Slider slider2;
    private Skin skin;
    private Stage stage;
    private Table table;
    private InputMultiplexer multi;
    private Preferences prefs;

    public SettingsScreen(FlapApp games) {
        background = new Background();
        this.games = games;
        final FlapApp game = games;
        background.setScrollSpeed(50);
        shape = new ShapeRenderer();
        shape.setProjectionMatrix(games.camera.combined);
        prefs = Gdx.app.getPreferences("user_preferences");
        font = new BitmapFont(Gdx.files.internal("score.fnt"));
        font2 = new BitmapFont(Gdx.files.internal("default.fnt"));
        font.getData().setScale(3f, 3f);
        font2.getData().setScale(3f, 3f);
        font.setColor(Color.BLACK);
        font2.setColor(Color.BLACK);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        slider = new Slider(0f, 1f, .1f, false, skin);
        slider.setValue(prefs.getFloat("music_volume", 1f));
        slider.getStyle().knob.setMinHeight(50);
        slider.getStyle().knob.setMinWidth(20);
        slider.setHeight(200);
        slider2 = new Slider(0f, 1f, .1f, false, skin);
        slider2.setValue(prefs.getFloat("sfx_volume", 1f));
        slider2.getStyle().knob.setMinHeight(50);
        slider2.getStyle().knob.setMinWidth(20);
        slider2.setHeight(200);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("Slider changed:", String.valueOf(slider.getValue()));
                prefs.putFloat("music_volume", slider.getValue());
                prefs.flush();
                game.sound.setGameMusicVolume();
            }
        });
        slider2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("Slider2 changed:", String.valueOf(slider2.getValue()));
                prefs.putFloat("sfx_volume", slider2.getValue());
                prefs.flush();
                game.sound.setSfxVolume();
                game.sound.getCollectSound().play();
            }
        });

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        table.top();
        table.add().height(Value.percentHeight(.40f, table));
        table.row();
        table.add(slider).width(Value.percentWidth(.50f, table)).height(Value.percentHeight(.10f, table));
        table.row();
        table.add().height(Value.percentHeight(.05f, table));
        table.row();
        table.add(slider2).width(Value.percentWidth(.50f, table)).height(Value.percentHeight(.10f, table));
        stage.addActor(table);

        multi = new InputMultiplexer();
        multi.addProcessor(stage);
        multi.addProcessor(new InputAdapter(){
            @Override public boolean keyUp(final int keycode) {

                if (keycode == Input.Keys.BACK) {
                    game.setScreen(game.mainMenu);
                }
                return false;
            }
        });
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        background.scrollBackground();
        games.camera.update();
        games.batch.setProjectionMatrix(games.camera.combined);
        games.batch.begin();
        games.batch.draw(background.getBackground(), background.getBackgroundX(), 0, background.getWidth(), 1920);
        games.batch.draw(background.getReversebackground(), background.getReversebackgroundX(), 0 , background.getWidth(), 1920);
        games.batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(1,1,1,.75f));
        shape.rect(190, 460, 700, 1000);
        slider.drawDebug(shape);
        shape.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        games.batch.begin();
        font.draw(games.batch, "Settings", 300, 1420);
        font2.draw(games.batch, "Music Volume", 300, 1200);
        font2.draw(games.batch, "SFX Volume", 300, 900);
        games.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        table.debug();

    }

    public void show(){
        Gdx.input.setInputProcessor(multi);
    }
    public void hide(){}
    public void pause(){}
    public void resume(){}
    public void dispose(){
        background.getBackground().dispose();
        background = null;
    }
    public void resize(int width, int height){}



}
