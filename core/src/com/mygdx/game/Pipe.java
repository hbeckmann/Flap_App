package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by Hunter on 3/16/2017.
 */

public class Pipe {


    private Texture pipe;
    private Sprite flippedPipe;
    private int minOpening;
    private int maxOpening;
    private int aboveOpening;
    private int belowOpening;
    private int aboveY;
    private int belowY;
    private int viewHeight;
    private int viewWidth;
    private int width;
    private double speed;
    private int aboveHitboxLeniency;
    private int belowHitboxLeniency;
    private int x;
    private Random rand;
    private Rectangle topBody;
    private Rectangle bottomBody;
    private Rectangle opening;



    public Pipe() {



        viewHeight = 1920;
        viewWidth = 1080;
        pipe = new Texture(Gdx.files.internal("spike_2.png"));
        flippedPipe = new Sprite(pipe);
        flippedPipe.flip(false, true);
        minOpening =  viewHeight / 6;
        maxOpening =  viewHeight / 5;
        width = viewWidth / 6;
        x = viewWidth;
        speed = 500;
        rand = new Random();
        aboveOpening = randomizeAboveOpening(viewHeight);
        belowOpening = randomizeBelowOpening(viewHeight);
        aboveY = 0;
        belowY = (aboveOpening + (viewHeight - aboveOpening - belowOpening));
        aboveHitboxLeniency = (aboveOpening / 10) * 2;
        belowHitboxLeniency = (belowOpening / 10) * 2;

        topBody = new Rectangle();
        topBody.x = x;
        topBody.y = 1920 - aboveOpening;
        topBody.width = width;
        topBody.height = aboveOpening;

        opening = new Rectangle();
        opening.x = x;
        opening.y = belowOpening - belowHitboxLeniency;
        opening.width = width;
        opening.height = 1920 - belowOpening - aboveOpening + aboveHitboxLeniency + belowHitboxLeniency;

        bottomBody = new Rectangle();
        bottomBody.x = x;
        bottomBody.y = 0;
        bottomBody.width = width;
        bottomBody.height = aboveOpening - aboveHitboxLeniency;

    }

    public void update() {
        if (x <= 0 - width) {
            x = viewWidth;
            aboveOpening = randomizeAboveOpening(viewHeight);
            aboveHitboxLeniency = (aboveOpening / 10) * 2;
            belowOpening = randomizeBelowOpening(viewHeight);
            belowHitboxLeniency = (belowOpening / 10) * 2;
            belowY = (aboveOpening + (viewHeight - aboveOpening - belowOpening));
            speed += 20;

        }

        x -= speed * Gdx.graphics.getDeltaTime();
        topBody.x = x;
        topBody.y = 1920 - aboveOpening + aboveHitboxLeniency;
        topBody.height = aboveOpening;

        opening.x = x;
        opening.y = belowOpening - belowHitboxLeniency;
        opening.height = 1920 - belowOpening - aboveOpening + aboveHitboxLeniency + belowHitboxLeniency;

        bottomBody.x = x;
        bottomBody.height = belowOpening - belowHitboxLeniency;
    }




    public void reset() {

        this.x = viewWidth;
        this.randomizeAboveOpening(viewHeight);
        this.randomizeBelowOpening(viewWidth);
        this.speed = 500;

        opening.x = x;
        opening.y = belowOpening - belowHitboxLeniency;
        opening.width = width;
        opening.height = 1920 - belowOpening - aboveOpening + aboveHitboxLeniency + belowHitboxLeniency;

        topBody.x = x;
        topBody.y = 1920 - aboveOpening + aboveHitboxLeniency;
        topBody.width = width;
        topBody.height = aboveOpening;

        bottomBody.x = x;
        bottomBody.y = 0;
        bottomBody.width = width;
        bottomBody.height = aboveOpening - aboveHitboxLeniency;

    }

    public int randomizeAboveOpening ( int viewHeight) {

        return rand.nextInt((viewHeight - minOpening - 200) - minOpening) + minOpening;

    }

    public int randomizeBelowOpening ( int viewHeight ) {

        // Plus 5 at end guarantees that it doesn't have a width of 0 and creates a bitmap exception
        return rand.nextInt((viewHeight - aboveOpening - minOpening) - (viewHeight - aboveOpening - maxOpening)) + (viewHeight - aboveOpening - maxOpening) + 5;

    }


    public int getAboveOpening() {
        return aboveOpening;
    }

    public int getBelowOpening() {
        return belowOpening;
    }

    public double getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getAboveY() {
        return aboveY;
    }

    public int getBelowY() {
        return belowY;
    }

    public int getAboveHitboxLeniency() {
        return aboveHitboxLeniency;
    }

    public int getBelowHitboxLeniency() {
        return belowHitboxLeniency;
    }

    public Texture getPipe() {
        return pipe;
    }

    public Sprite getFlippedPipe() {
        return flippedPipe;
    }

    public Rectangle getTopBody() {
        return topBody;
    }

    public Rectangle getBottomBody() {
        return bottomBody;
    }

    public Rectangle getOpening() {
        return opening;
    }

    public void dispose() {

        pipe.dispose();

    }




}
