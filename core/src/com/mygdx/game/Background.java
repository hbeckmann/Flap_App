package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Hunter on 3/16/2017.
 */

public class Background {

    private Texture background;
    private float backgroundX;
    private Sprite reversebackground;
    private float reversebackgroundX;
    private float scrollSpeed;
    private int width;
    private int height;


    public Background() {

        backgroundX = 0;
        background = new Texture(Gdx.files.internal("background.png"));
        reversebackground = new Sprite(new Texture(Gdx.files.internal("background.png")));
        reversebackground.flip(true, false);
        //3.7 scale
        width = 3756;
        height = 1920;
        scrollSpeed = 400;
        reversebackgroundX = width;

    }


    public void reset() {

        this.backgroundX = 0;
        this.reversebackgroundX = width;
        this.scrollSpeed = 400;

    }

    public void scrollBackground() {

        try {
            if(backgroundX + width * 2 - 1080 <= 0) {

                backgroundX = backgroundX + width * 2;

            } else if(reversebackgroundX + width * 2 - 1080 <= 0) {

                reversebackgroundX = reversebackgroundX + width * 2;

            }
        } catch (Exception e) {
            System.out.println(e.getCause());
        }

        float scrollRate = scrollSpeed * Gdx.graphics.getDeltaTime();
        backgroundX -= scrollRate;
        reversebackgroundX -= scrollRate;

    }

    public void dispose() {
        background.dispose();
    }

    public float getBackgroundX() {
        return backgroundX;
    }

    public float getReversebackgroundX() {
        return reversebackgroundX;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getScrollSpeed() {
        return scrollSpeed;
    }

    public void setScrollSpeed(int scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
    }

    public Texture getBackground() {
        return background;
    }

    public Sprite getReversebackground() {
        return reversebackground;
    }
}
