package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class PlayerShip extends Ship {

    public int HUMANS = 10000;

    public PlayerShip(Sector sector, MagellanGame game) {
        super(sector, game);
        faction = Factions.PLAYER;
        sector.discovered = true;
    }

    public void moveTo(Sector sector) {
        super.moveTo(sector);
        sector.discovered = true;
        game.gameState.YEAR++;
    }

    @Override
    public void prepareRenderingOnMap() {
        mapSprite = new Sprite(MagellanGame.assets.get("circle.png", Texture.class));
        mapSprite.setSize(24,24);
        mapSprite.setColor(Color.YELLOW);
    }

}
