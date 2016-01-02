package com.gdxjam.magellan.gameobj;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lolcorner on 19.12.2015.
 */
public interface IDrawableMap {
    void prepareRenderingOnMap();
    void renderOnMap(SpriteBatch batch, float delta);
}
