package com.gdxjam.magellan.drones;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdxjam.magellan.*;

/**
 * Created by saibotd on 26.12.15.
 */
public class Drone extends MovingGameObj implements IDestroyable, IDrawableMap, IDrawableWindow, IInteractable {
    private int health = 25;
    public static int price = 1000;


    public Drone(Sector sector, MagellanGame game) {
        super(sector, game);
    }

    @Override
    public void receiveDamage(int damage) {
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

    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {

    }

    @Override
    public void prepareRendering() {

    }

    @Override
    public void render(SpriteBatch batch, float delta) {

    }

    @Override
    public ObjectMap<String, Interaction> getInteractions(GameObj with) {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }
}
