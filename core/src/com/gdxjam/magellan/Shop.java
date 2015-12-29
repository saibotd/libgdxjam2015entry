package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by Felix on 29.12.2015.
 */
public class Shop extends GameObj implements IDrawableWindow, IDrawableMap, IInteractable {

    private Sprite mapSprite;
    private Sprite windowSprite;

    public Shop(Sector sector) {
        super(sector);
    }

    @Override
    public ObjectMap<String, Interaction> getInteractions(GameObj with) {
        return null;
    }


    @Override
    public void prepareRenderingOnMap() {
        mapSprite = new Sprite(MagellanGame.assets.get("map_shop.png", Texture.class));
        mapSprite.setSize(14, 14);
        mapSprite.setPosition(sector.position.x - 20, sector.position.y + 20);
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        super.render(delta);
        mapSprite.draw(batch);
    }

    @Override
    public void prepareRendering() {
        windowSprite = new Sprite(MagellanGame.assets.get("map_shop.png", Texture.class));
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        super.render(delta);
        windowSprite.draw(batch);
    }

    @Override
    public String getTitle() {
        return "Trading Post";
    }

    @Override
    public String getInfo() {
        return null;
    }
}
