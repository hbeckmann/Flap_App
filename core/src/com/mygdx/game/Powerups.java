package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Hunter on 3/20/2017.
 */

public class Powerups {

    private Texture powerupSprite;
    private Rectangle dimens;
    private List<Powerup> powerupList;
    private Powerup randomPowerup;
    private Player player;
    private Pipe pipe;
    private Random rand;
    private Boolean powerupAppeared, poweringUp;


    public Powerups(Player player, Pipe pipe) {
        this.powerupSprite = new Texture(Gdx.files.internal("box.png"));
        dimens = new Rectangle();
        dimens.width = 1080 / 7;
        dimens.height = 1920 / 12;
        this.player = player;
        this.pipe = pipe;
        this.powerupAppeared = true;
        this.poweringUp = false;
        rand = new Random();
        powerupList = new ArrayList<Powerup>();
        powerupList.add(new Powerup("Anti-Gravity", new ReverseGravity()));
        randomizePosition();
        dimens.x = pipe.getX() + (1080 / 2) + pipe.getWidth();
    }

    interface Callback {
        void func();
    }

    class ReverseGravity implements Callback {
        public void func() {
            player.setAcceleration(-player.getAcceleration());
        }
    }

    private class Powerup {

        private String name;
        private Callback callback;


        public Powerup(String name, Callback func) {
            this.name = name;
            this.callback = func;
        }

        public String getName() {
            return name;
        }

        public void activatePowerup() {
            callback.func();
        }
    }

    public void randomizePosition() {
        dimens.y = rand.nextFloat() * (1920 - dimens.height);

    }

    public void updatePosition() {
        if (powerupAppeared && dimens.x + dimens.width > 0) {
            dimens.x -= pipe.getSpeed() * Gdx.graphics.getDeltaTime();
        } else if (powerupAppeared && dimens.x + dimens.width < 0) {
            randomizePowerupTiming();
            dimens.x = pipe.getX() + (1080 / 2) + pipe.getWidth();
        } else if (!powerupAppeared && dimens.x + dimens.width > 0) {
            dimens.x -= pipe.getSpeed() * Gdx.graphics.getDeltaTime();
        } else {
            dimens.x = pipe.getX() + (1080 / 2) + pipe.getWidth();
            randomizePowerupTiming();
        }
    }

    public void randomizePowerupTiming() {
        if(rand.nextInt(4) < 1) {
            powerupAppeared = true;
            randomizePosition();
            poweringUp = false;
        } else  {
            powerupAppeared = false;
            dimens.y = 0 - dimens.height - 100;
        }
    }


    public Texture getPowerupSprite() {
        return powerupSprite;
    }

    public Boolean getPoweringUp() {
        return poweringUp;
    }

    public void setPoweringUp(Boolean poweringUp) {
        this.poweringUp = poweringUp;
    }

    public Rectangle getDimens() {
        return dimens;
    }
}
