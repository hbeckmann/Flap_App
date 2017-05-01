package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;

/**
 * Created by Hunter on 3/22/2017.
 */

public class CreditsScreen implements Screen {

    private Background background;
    private FlapApp game;
    private Stage stage;
    private Table table;
    private BitmapFont font;
    private Label label;
    private Label sLabel;
    private Label.LabelStyle lStyle;
    private Label.LabelStyle sStyle;
    private Label soundLabel;
    private Label scLable;
    private Skin skin;

    public CreditsScreen(FlapApp game) {
        this.game = game;
        skin = new Skin();
        background = new Background();
        background.setScrollSpeed(50);
        font = new BitmapFont(Gdx.files.internal("score.fnt"));
        //font.getData().setScale(2f, 2f);
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        skin.add("font", font);
        lStyle = new Label.LabelStyle();
        lStyle.font = font;
        sStyle = new Label.LabelStyle();
        sStyle.font = font;
        label = new Label("Credits", lStyle);
        label.setFontScale((float)Gdx.graphics.getWidth() / 200);
        sLabel = new Label(
                "Programming - Hunter Beckmann \n" +
                        "Sprites - Bevoulin (Imaginary Perception) \n" +
                        "Background Art - Rubberduck \n",
                sStyle);
        sLabel.setFontScale((float)Gdx.graphics.getWidth() / 800);
        soundLabel = new Label("Sound", lStyle);
        soundLabel.setFontScale((float) Gdx.graphics.getWidth() / 300);
        scLable = new Label("Roccow - Welcome! \n" +
                "Broke For Free - Something Elated \n" +
                "ProjectU012 - Coin Collect \n" +
                "Jobro - Explosion", sStyle);
        scLable.setFontScale((float) Gdx.graphics.getWidth() / 800);
        table.top();
        table.add(label).pad(Gdx.graphics.getWidth() / 10, 0, 0, 0);
        table.row();
        table.add(sLabel).pad(Gdx.graphics.getWidth() / 20, 0, 0, 0);
        table.row();
        table.add(soundLabel);
        table.row();
        table.add(scLable).pad(Gdx.graphics.getWidth() / 20, 0, 0, 0);

        stage.addActor(table);


    }

    public void hide(){}
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override public boolean keyUp(final int keycode) {
                if (keycode == Input.Keys.BACK) {
                    game.setScreen(game.mainMenu);
                }
                return false;
            }
        });
    }
    public void resume(){}
    public void pause(){}
    public void dispose(){
        background.getBackground().dispose();
        background = null;
    }
    public void render(float delta){

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        background.scrollBackground();
        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();
        game.batch.draw(background.getBackground(), background.getBackgroundX(), 0, background.getWidth(), 1920);
        game.batch.draw(background.getReversebackground(), background.getReversebackgroundX(), 0 , background.getWidth(), 1920);
        game.batch.end();

        stage.draw();
        table.debug();
    }
    public void resize(int width, int height){}



}
