package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class PlayerShip extends Ship {

    public int COINS = 0;
    public int HUMANS = 10000;
    public int DRONES = 0;
    public int RESSOURCE1 = 0;
    public int RESSOURCE2 = 0;
    public int RESSOURCE3 = 0;

    public PlayerShip(Sector sector) {
        super(sector);
        sector.discovered = true;
    }

    public void moveTo(Sector sector) {
        super.moveTo(sector);
        sector.discovered = true;
    }

    @Override
    public void prepareRenderingOnMap() {
        mapSprite = new Sprite(MagellanGame.assets.get("circle.png", Texture.class));
        mapSprite.setSize(24,24);
        mapSprite.setColor(Color.YELLOW);
    }

}
