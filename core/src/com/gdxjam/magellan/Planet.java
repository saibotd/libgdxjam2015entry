package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by lolcorner on 19.12.2015.
 */
public class Planet extends GameObj implements IDrawableMap, IDrawableWindow, IDestroyable {
    private Sprite sprite;
    private Sprite mapSprite;
    public int population = 0;

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
        mapSprite = new Sprite(MagellanGame.assets.get("dot.png", Texture.class));
        mapSprite.setSize(24,24);
        mapSprite.setColor(Color.MAGENTA);
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        mapSprite.setPosition(sector.position.x - mapSprite.getWidth()/2, sector.position.y - mapSprite.getHeight()/2);
        mapSprite.draw(batch);
    }

    @Override
    public void receiveDamage(int damage) {
        population -= damage;
        if(population <= 0){
            population = 0;
            faction = Factions.NEUTRAL;
        }
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void dispose() {

    }
}
