package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Hunter on 3/16/2017.
 */

public class Player {
    private Rectangle bird;
    private Rectangle body;
    private Texture birdSprite;
    private Animation<TextureRegion> birdAnim;
    private Animation<TextureRegion> explosionAnim;
    private Texture explosionSheet;
    private double velocity;
    private double acceleration;
    private boolean jumping, exploding, exploded;


    public Player() {
        bird = new Rectangle();
        bird.x = 200;
        bird.y = 1920 / 2;
        bird.width = 160;
        bird.height = 140;

        body = new Rectangle();
        body.width = 120;
        body.height = 105;
        body.x = bird.x + (bird.width - body.width) / 2;
        body.y = bird.y + (bird.height - body.height) / 2;

        jumping = false;
        exploding = false;
        exploded = false;
        velocity = -1100;
        acceleration = -4200;
        birdSprite = new Texture(Gdx.files.internal("frame_1.png"));
        birdAnim =
                new Animation<TextureRegion>(.07f,
                        new TextureRegion(new Texture(Gdx.files.internal("frame_1.png"))),
                        new TextureRegion(new Texture(Gdx.files.internal("frame_2.png"))),
                        new TextureRegion(new Texture(Gdx.files.internal("frame_3.png"))),
                        new TextureRegion(new Texture(Gdx.files.internal("frame_4.png")))
                        );

        birdAnim.setPlayMode(Animation.PlayMode.NORMAL);

        explosionSheet = new Texture(Gdx.files.internal("xpl.png"));
        TextureRegion[][] tmp = TextureRegion.split(explosionSheet,
                explosionSheet.getWidth() / 10,
                explosionSheet.getHeight() / 4
        );

        TextureRegion[] explosionFrames = new TextureRegion[40];
        int index = 0;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 10; j++) {
                explosionFrames[index++] = tmp[i][j];
            }
        }

        explosionAnim = new Animation<TextureRegion>(0.008f, explosionFrames);
        explosionAnim.setPlayMode(Animation.PlayMode.NORMAL);


    }

    public void update() {
        velocity += acceleration * Gdx.graphics.getDeltaTime();
        bird.y += velocity * Gdx.graphics.getDeltaTime();
        body.y = bird.y + (bird.height - body.height) / 2;
    }

    public boolean collidedWithBounds() {
        if(bird.y <= 0) {
            return true;
        }
        if(bird.y >= 1920 - bird.height) {
            return true;
        }

        return false;
    }



    public void reset() {
        bird.y = 1920 / 2;
        exploded = false;
    }

    public void dispose() {
       birdSprite.dispose();
    }

    public boolean isCollided(Rectangle rect) {
        return rect.overlaps(body);
    }

    public Animation<TextureRegion> getExplosionAnim() {
        return explosionAnim;
    }

    public Rectangle getBird() {
        return bird;
    }

    public Texture getBirdSprite() {
        return birdSprite;
    }

    public Animation<TextureRegion> getBirdAnim() {
        return birdAnim;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public Rectangle getBody() {
        return body;
    }

    public boolean isExploding() {
        return exploding;
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }
}
