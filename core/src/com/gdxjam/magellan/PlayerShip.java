package com.gdxjam.magellan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class PlayerShip extends Ship {

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
        sprite = new Sprite(MagellanGame.assets.get("circle.png", Texture.class));
        sprite.setSize(24,24);
        sprite.setColor(Color.YELLOW);
    }

}
