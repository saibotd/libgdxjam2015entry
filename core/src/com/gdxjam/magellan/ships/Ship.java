package com.gdxjam.magellan.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.gdxjam.magellan.*;
import com.gdxjam.magellan.gameobj.*;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class Ship extends MovingGameObj implements IDrawableMap, IDrawableWindow, IDestroyable, IArmed {

    public float shield = 0;
    public int health = 3;
    public int attack = 1;
    public Sprite spriteDot;

    public Ship(Sector sector) {
        super(sector);
    }

    @Override
    public int shootAt(IDestroyable target) {
        if (target.receiveDamage(attack)) {
            return attack;
        } else {
            return -1;
        }
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public boolean receiveDamage(int damage) {
        if(Math.random() < shield) return false;
        health -= damage;
        if(health <= 0) destroy();
        return true;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void destroy() {
        dispose();
    }

    @Override
    public void dispose() {
        this.sector.gameObjs.removeValue(this, true);
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public float getShield() {
        return shield;
    }

    @Override
    public String getTitle() {
        return "SHIP";
    }

    @Override
    public String getInfo() {
        String s = "Faction: " + faction.toString();
        s += "\nHealth: " + health;
        s += "\nAttack: " + attack;
        s += "\nShield: " + Math.round(shield * 100) + "%";
        return s;
    }

    @Override
    public Actor getActor() {
        Image image = new Image(MagellanGame.assets.get("map_playership.png", Texture.class));
        image.setUserObject(this);
        return image;
    }

    @Override
    public void prepareRenderingOnMap() {

    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {

    }

    @Override
    public void moveTo(Sector sector) {
        super.moveTo(sector);
    }
}
