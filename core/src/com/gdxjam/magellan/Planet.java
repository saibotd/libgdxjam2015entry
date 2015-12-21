package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class Planet extends GameObj implements IDrawableMap, IDrawableWindow {
    private Sprite sprite;

    public Planet(Sector sector) {
        super(sector);
    }

    @Override
    public void prepareRendering() {
        sprite = new Sprite(MagellanGame.assets.get("dot.png", Texture.class));
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        sprite.setPosition(1000, 400);
        sprite.setSize(800,800);
        sprite.draw(batch);
    }

    @Override
    public void prepareRenderingOnMap() {
        sprite = new Sprite(MagellanGame.assets.get("dot.png", Texture.class));
        sprite.setSize(24,24);
        sprite.setColor(Color.MAGENTA);
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        sprite.setPosition(sector.position.x - sprite.getWidth()/2, sector.position.y - sprite.getHeight()/2);
        sprite.draw(batch);
    }
}
