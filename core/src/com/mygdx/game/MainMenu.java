package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Created by Hunter on 3/20/2017.
 */

public class MainMenu implements Screen {

    final FlapApp game;
    private Background background;
    private UIManager ui;
    private Stage stage;
    private InputMultiplexer multi;
    private Table table;


    public MainMenu(FlapApp games) {
        this.game = games;
        this.background = new Background();
        this.ui = new UIManager();
        background.setScrollSpeed(50);
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        table.top();
        table.add().height(Value.percentHeight(.45f, table));
        table.row();
        table.add(ui.getPlayButtonActor())
                .width(Value.percentWidth(.5f, table))
                .height(Value.percentHeight(.12f, table));

        ui.getPlayButtonActor().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
        table.row();
        table.add(ui.getSettingsButtonActor())
                .width(Value.percentWidth(.4f, table))
                .height(Value.percentHeight(.08f, table))
                .pad(10f, 0, 0, 0);
        ui.getSettingsButtonActor().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.settings);
            }
        });
        table.row();
        table.add(ui.getCreditsButtonActor())
                .width(Value.percentWidth(.4f, table))
                .height(Value.percentHeight(.08f, table))
                .pad(10f, 0, 0, 0);
        ui.getCreditsButtonActor().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.credits);
            }
        });

        stage.addActor(table);

        multi = new InputMultiplexer();
        multi.addProcessor(stage);
        multi.addProcessor(new InputAdapter(){
            @Override public boolean keyUp(final int keycode) {
                if (keycode == Input.Keys.BACK) {
                    Gdx.app.exit();
                }
                return false;
            }
        });

        Gdx.input.setCatchBackKey(true);

    }

    public void resume() {}
    public void pause(){}
    public void show() {
        Gdx.input.setInputProcessor(multi);
    }
    public void hide() {
    }
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        background.scrollBackground();
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.batch.draw(background.getBackground(), background.getBackgroundX(), 0, background.getWidth(), 1920);
        game.batch.draw(background.getReversebackground(), background.getReversebackgroundX(), 0 , background.getWidth(), 1920);
        game.batch.draw(ui.getBirdHeader(), 240, 1300, 600, 500);
        game.batch.draw(ui.getAppTitle(), 60, 850, 1000, 800);
        game.batch.end();

        stage.draw();
        //table.debug();




    }
    public void resize(int width, int height) {}
    public void dispose() {
        background.dispose();
        stage.dispose();
        ui.dispose();
    }


}
