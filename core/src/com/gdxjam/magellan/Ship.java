package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class Ship extends MovingGameObj implements IDrawableMap, IDrawableWindow, IDestroyable, IArmed {

    public float shield = .1f;
    public int health = 100;
    public int attack;
    public Sprite sprite;

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

    @Override
    public void prepareRenderingOnMap() {
        sprite = new Sprite(MagellanGame.assets.get("dot.png", Texture.class));
        sprite.setSize(10,10);
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        sprite.setPosition(sector.position.x - sprite.getWidth()/2, sector.position.y - sprite.getHeight()/2);
        sprite.draw(batch);
    }
}
