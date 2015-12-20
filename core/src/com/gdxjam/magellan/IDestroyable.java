package com.gdxjam.magellan;

import com.badlogic.gdx.utils.Disposable;

/**
 * Created by lolcorner on 20.12.2015.
 */
public interface IDestroyable extends Disposable {
    void receiveDamage(int damage);
    boolean isAlive();
    void destroy();
    void dispose();
}
