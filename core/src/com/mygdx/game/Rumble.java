package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.Random;


/**
 * Created by Hunter on 3/17/2017.
 */

public class Rumble {

    private float x;
    private float y;
    private Random rand;
    private OrthographicCamera cam;
    private float currentTime;
    private float time;
    private boolean rumbled;


    public Rumble(OrthographicCamera cam) {
        x = 0;
        y = 0;
        currentTime = 0;
        rand = new Random();
        this.cam = cam;
        rumbled = false;

    }


    public void tick(float time) {

        if(currentTime <= time) {
            this.time = time;
            x = (rand.nextFloat() - .5f) * (2500) * Gdx.graphics.getDeltaTime();
            y = (rand.nextFloat() - .5f) * (2500) * Gdx.graphics.getDeltaTime();
            cam.translate(x, y, 0);
            cam.update();
            currentTime += Gdx.graphics.getDeltaTime();
        } else {
            cam.position.x = 1080 / 2;
            cam.position.y = 1920 / 2;
            cam.update();
            rumbled = true;

        }


    }

    public void reset() {
        rumbled = false;
        currentTime = 0;
    }

    public float getCurrentTime() {
        return currentTime;
    }

    public float getTime() {
        return time;
    }

    public boolean isRumbled() {
        return rumbled;
    }

    public void setRumbled(boolean rumbling) {
        this.rumbled = rumbling;
    }
}
