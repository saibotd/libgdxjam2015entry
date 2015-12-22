package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class PlayerShip extends Ship {

    public ObjectMap<String, Integer> stats = new ObjectMap();

    public PlayerShip(Sector sector) {
        super(sector);
        stats.put("coins", 0);
        stats.put("humansOnBoard", 0);
        stats.put("drones", 0);
        stats.put("population", 10000);
        stats.put("ressource1", 0);
        stats.put("ressource2", 0);
        stats.put("ressource3", 0);
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
