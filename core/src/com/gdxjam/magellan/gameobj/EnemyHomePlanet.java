package com.gdxjam.magellan.gameobj;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.OrderedMap;
import com.gdxjam.magellan.*;
import com.gdxjam.magellan.ships.AiShipSettler;
import com.gdxjam.magellan.ships.PlayerShip;
import com.gdxjam.magellan.ships.Ship;


/**
 * Created by lolcorner on 19.12.2015.
 */
public class EnemyHomePlanet extends Planet {

    public EnemyHomePlanet(Sector sector) {
        super(sector);
        color = MagellanColors.FACTION_ENEMY;
    }

    @Override
    public void prepareRenderingOnMap() {
        mapSprite = new Sprite(MagellanGame.assets.get("map_planet_"+visualType+".png", Texture.class));
        mapSprite.setColor(color);
        mapSprite.setSize(50,50);

        mapClaimedSprite = new Sprite(MagellanGame.assets.get("map_planet_claimed.png", Texture.class));
        mapClaimedSprite.setSize(15, 20);
    }

    @Override
    public Actor getActor() {
        Image image = new Image(MagellanGame.assets.get("sectorview_planet_"+visualType+".png", Texture.class));
        image.setColor(color);
        image.setFillParent(true);
        return image;
    }

}
