package com.gdxjam.magellan.gameobj;

import com.badlogic.gdx.utils.Disposable;

/**
 * Created by lolcorner on 20.12.2015.
 */
public interface IDestroyable extends Disposable {
    boolean receiveDamage(int damage);
    boolean isAlive();
    void destroy();
    void dispose();
    int getHealth();
    float getShield();
}
