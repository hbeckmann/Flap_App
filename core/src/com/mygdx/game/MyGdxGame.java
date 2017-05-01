package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;


public class MyGdxGame implements Screen {
	private FlapApp game;
	private SpriteBatch batch;
	private Vector3 touchPos;
	private Player player;
	private Background background;
	private Pipe pipe;
	private Powerups powerups;
	private Rumble rumble;
	private float animationTime, xplAnimationTime;
	private TextureRegion currentFrame, currentXplFrame;
	private ShapeRenderer shape;
	private Boolean scored, newScore, powerupGet;
	private int currentScore;
	private String highScore;
	private Boolean topCollision, bottomCollision, dead, resetting;
	private BitmapFont font;
	private BitmapFont font2;
	private GlyphLayout layout;
	private Preferences prefs;

	private Boolean debugMode;

	public MyGdxGame(FlapApp game) {
		this.game = game;
		this.create();
	}


	public void create () {

		batch = new SpriteBatch();
		player = new Player();
		pipe = new Pipe();
		background = new Background();
		rumble = new Rumble(game.camera);
		powerups = new Powerups(player, pipe);
		animationTime = 0f;
		xplAnimationTime = 0f;
		shape = new ShapeRenderer();
		scored = false;
		newScore = true;
		resetting = true;
		touchPos = new Vector3();
		font = new BitmapFont(Gdx.files.internal("score.fnt"));
		font.getData().setScale(8f, 8f);
		font2 = new BitmapFont(Gdx.files.internal("score.fnt"));
		font2.getData().setScale(2f, 2f);
		layout = new GlyphLayout();
		currentScore = 0;
		prefs = Gdx.app.getPreferences("user_preferences");
		//prefs.putString("highScore", "20000").flush();
		highScore = prefs.getString("highScore", "0");

		debugMode = false;
	}

	public void show() {
		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override public boolean keyUp(final int keycode) {
				if (keycode == Input.Keys.BACK) {
					game.setScreen(game.mainMenu);
					Gdx.app.log("Game screen", "holy shit what is happening");
				}
				return false;
			}
		});
	}
	public void hide() {}
	public void pause() {}
	public void resume() {}
	public void resize(int width, int height) {

	}



	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Go back if back button is pressed
//		if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
//			game.setScreen(game.mainMenu);
//		}

		topCollision = player.isCollided(pipe.getTopBody());
		bottomCollision = player.isCollided(pipe.getBottomBody());

		dead = topCollision || bottomCollision;

		dead = dead || player.collidedWithBounds();

		//Updates State
		if(!dead && !resetting) {
			game.camera.update();
			player.update();
			pipe.update();
			powerups.updatePosition();
			background.scrollBackground();
		}

		//Tap to jump or restart the game state
		if(Gdx.input.justTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			player.setJumping(true);
			game.camera.unproject(touchPos);
			player.setVelocity(1500);
			game.sound.getJumpSound().play(game.sound.getSfxVolume());
			if(dead) {
				currentScore = 0;
				background.reset();
				pipe.reset();
				player.reset();
				rumble.reset();
				dead = false;
				resetting = true;
			} else if(resetting) {
				resetting = false;
			}
		}

		batch.setProjectionMatrix(game.camera.combined);
		batch.begin();

		//draw background
		batch.draw(background.getBackground(), background.getBackgroundX(), 0, background.getWidth(), background.getHeight());
		batch.draw(background.getReversebackground(), background.getReversebackgroundX(), 0, background.getWidth(), background.getHeight());

		//Animates jump or keeps sprite still
		if(player.isJumping() && !dead && !resetting) {

			animationTime += Gdx.graphics.getDeltaTime();
			currentFrame =(TextureRegion) player.getBirdAnim().getKeyFrame(animationTime);
			batch.draw(currentFrame, player.getBird().x, player.getBird().y, player.getBird().width, player.getBird().height);

			if(player.getBirdAnim().isAnimationFinished(animationTime)) {
				animationTime = 0;
				player.setJumping(false);

			}
		} else {
			batch.draw(player.getBirdSprite(), player.getBird().x, player.getBird().y, player.getBird().width, player.getBird().height);
		}

		//Player has died and is exploding
		if(dead && !player.isExploded()) {
			xplAnimationTime += Gdx.graphics.getDeltaTime();
			currentXplFrame = player.getExplosionAnim().getKeyFrame(xplAnimationTime);
			batch.draw(currentXplFrame, player.getBird().x - 100, player.getBird().y - 100, player.getBird().width + 200, player.getBird().height + 200);

			if(player.isExploding() == false) {
				game.sound.getExplosionSound().play(game.sound.getSfxVolume());
				player.setExploding(true);
				if(currentScore > Integer.parseInt(highScore)) {

					prefs.putString("highScore", Integer.toString(currentScore)).flush();
				}
				highScore = prefs.getString("highScore", "0");
			}

			if(player.getExplosionAnim().isAnimationFinished(xplAnimationTime)) {
				player.setExploded(true);
				player.setExploding(false);
				xplAnimationTime = 0f;
			}
		}

		//Rumble on death
		if (dead && !rumble.isRumbled()) {
			rumble.tick(.07f);
		}

		//draw pipes
		batch.draw(pipe.getFlippedPipe(), pipe.getX(), 1920 - pipe.getAboveOpening(), pipe.getWidth(), pipe.getAboveOpening());
		batch.draw(pipe.getPipe(), pipe.getX(), 0, pipe.getWidth(), pipe.getBelowOpening());

		//draw powerups
		if(!powerups.getPoweringUp()) {
			batch.draw(powerups.getPowerupSprite(), powerups.getDimens().x, powerups.getDimens().y, powerups.getDimens().width, powerups.getDimens().height);
		}

		//Draw Scores
		layout.setText(font, Integer.toString(currentScore));
		font.draw(batch, Integer.toString(currentScore), 540 - layout.width / 2, 1900);
		font2.draw(batch, "High Score: " + highScore, 550, 40);

		batch.end();

		if(debugMode) {
			shape.setProjectionMatrix(game.camera.combined);
			shape.begin(ShapeRenderer.ShapeType.Line);
			shape.setColor(0, 1, 0, 1);
			shape.rect(pipe.getOpening().x, pipe.getOpening().y, pipe.getOpening().width, pipe.getOpening().height);
			shape.setColor(1, 0, 0, 1);
			shape.rect(pipe.getTopBody().x, pipe.getTopBody().y, pipe.getTopBody().width, pipe.getTopBody().height);
			shape.rect(pipe.getBottomBody().x, pipe.getBottomBody().y, pipe.getBottomBody().width, pipe.getBottomBody().height);
			shape.setColor(1, 1, 1, 1);
			shape.rect(player.getBody().x, player.getBody().y, player.getBody().width, player.getBody().height);

			shape.end();
		}

		//Collision Detection
		newScore = scored;
		scored = player.isCollided(pipe.getOpening());
		if (scored != newScore && scored) {
			game.sound.getScoreSound().play(game.sound.getSfxVolume());
			currentScore += 1;
			newScore = false;
		}

		powerupGet = player.isCollided(powerups.getDimens());
		if(powerupGet && !powerups.getPoweringUp()) {
			powerups.setPoweringUp(true);
			game.sound.getCollectSound().play();
		}


	}

	
	@Override
	public void dispose () {
		batch.dispose();
		player.dispose();
		game.sound.dispose();
		pipe.dispose();
	}

}
