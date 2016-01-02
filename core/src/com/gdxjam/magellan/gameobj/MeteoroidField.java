package com.gdxjam.magellan.gameobj;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gdxjam.magellan.MagellanColors;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.Sector;
import com.gdxjam.magellan.Statics;

/**
 * Created by lolcorner on 22.12.2015.
 */
public class MeteoroidField extends GameObj implements IDrawableMap, IDrawableWindow {

    public int resource; // 1 - 3
    public int resourcePerTick;
    private Sprite mapSprite;

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
    public Actor getActor() {
        Image image = new Image(MagellanGame.assets.get("map_meteoroids_planetsector.png", Texture.class));
        image.setSize(200,200);
        switch (resource){
            case 1:
                image.setColor(MagellanColors.RESOURCE_1);
                break;
            case 2:
                image.setColor(MagellanColors.RESOURCE_2);
                break;
            case 3:
                image.setColor(MagellanColors.RESOURCE_3);
                break;
        }
        return image;
    }

    @Override
    public String getTitle() {
        return "Meteoroid field";
    }

    @Override
    public String getInfo() {
        String resourceName = "";
        switch (resource){
            case 1:
                resourceName = Statics.resource1;
                break;
            case 2:
                resourceName = Statics.resource2;
                break;
            case 3:
                resourceName = Statics.resource3;
                break;
        }
        String s = "Faction: " + faction.toString();
        s += "\nResource: " + resourceName;
        s += "\nResources per year: " + resourcePerTick;
        return s;
    }
}
