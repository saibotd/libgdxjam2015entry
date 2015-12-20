package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class Ship extends MovingGameObj implements IDrawable, IDestroyable, IArmed {

    public float shield = .1f;
    public int health = 100;
    public int attack;

    public Ship(Sector sector) {
        super(sector);
    }

    @Override
    public void prepareRendering() {

    }

    @Override
    public void render(SpriteBatch batch, float delta) {

    }

    @Override
    public void shootAt(IDestroyable target) {
        target.receiveDamage(attack);
    }

    @Override
    public void receiveDamage(int damage) {
        if(Math.random() < shield) return;
        health -= damage;
        if(health <= 0) destroy();
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void destroy() {
        dispose();
    }

    @Override
    public void dispose() {

    }

}
