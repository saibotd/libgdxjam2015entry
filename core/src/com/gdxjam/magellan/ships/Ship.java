package com.gdxjam.magellan.ships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxjam.magellan.*;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class Ship extends MovingGameObj implements IDrawableMap, IDrawableWindow, IDestroyable, IArmed {

    public float shield = .1f;
    public int health = 100;
    public int attack;
    public Sprite spriteDot;
    public Sprite spriteShip;

    public Ship(Sector sector, MagellanGame game) {
        super(sector, game);
    }

    @Override
    public void prepareRendering() {

    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        super.render(delta);
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
        return health <= 0;
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
        spriteDot = new Sprite(MagellanGame.assets.get("dot.png", Texture.class));
        spriteDot.setSize(10,10);
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        spriteDot.setPosition(sector.position.x - spriteDot.getWidth()/2, sector.position.y - spriteDot.getHeight()/2);
        spriteDot.draw(batch);
    }
}
