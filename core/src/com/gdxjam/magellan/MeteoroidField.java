package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by lolcorner on 22.12.2015.
 */
public class MeteoroidField extends GameObj implements IDrawableMap, IDrawableWindow {

    public int resource; // 1 - 3
    public int resourcePerTick;
    private Sprite mapSprite;
    private Sprite sprite;

    public MeteoroidField(Sector sector) {
        super(sector);
        resource = MathUtils.random(1, 3);
        resourcePerTick = MathUtils.random(10, 100);
    }

    @Override
    public void prepareRenderingOnMap() {
        if (sector.hasPlanet) {
            mapSprite = new Sprite(MagellanGame.assets.get("map_meteoroids_planetsector.png", Texture.class));
            mapSprite.setSize(45,45);
        } else {
            mapSprite = new Sprite(MagellanGame.assets.get("map_meteoroids_emptysector.png", Texture.class));
            mapSprite.setSize(23,23);
        }

        switch (resource){
            case 1:
                mapSprite.setColor(MagellanColors.RESOURCE_1);
                break;
            case 2:
                mapSprite.setColor(MagellanColors.RESOURCE_2);
                break;
            case 3:
                mapSprite.setColor(MagellanColors.RESOURCE_3);
                break;
        }
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        mapSprite.setPosition(sector.position.x - mapSprite.getWidth()/2, sector.position.y - mapSprite.getHeight()/2);
        mapSprite.draw(batch);
    }

    @Override
    public void prepareRendering() {
        sprite = new Sprite(MagellanGame.assets.get("map_meteoroids_planetsector.png", Texture.class));
        sprite.setPosition(300,300);
        sprite.setSize(200,200);
        switch (resource){
            case 1:
                sprite.setColor(MagellanColors.RESOURCE_1);
                break;
            case 2:
                sprite.setColor(MagellanColors.RESOURCE_2);
                break;
            case 3:
                sprite.setColor(MagellanColors.RESOURCE_3);
                break;
        }
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        sprite.draw(batch);
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }
}
