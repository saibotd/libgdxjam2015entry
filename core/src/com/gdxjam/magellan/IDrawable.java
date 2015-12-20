package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lolcorner on 19.12.2015.
 */
public interface IDrawable {
    void prepareRendering();
    void render(SpriteBatch batch, float delta);
}
