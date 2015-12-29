package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by lolcorner on 19.12.2015.
 */
public interface IDrawableWindow {
    String getTitle();
    String getInfo();
    Actor getActor();
}
