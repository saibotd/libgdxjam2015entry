package com.gdxjam.magellan;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class PlayerShip extends Ship {

    public int HUMANS = 10000;

    public PlayerShip(Sector sector, MagellanGame game) {
        super(sector, game);
        faction = Factions.PLAYER;
        setSectorsDiscovered();
    }

    public void moveTo(Sector sector) {
        super.moveTo(sector);
        setSectorsDiscovered();


        Timeline.createSequence()
                .push(Tween.to(this.spriteShip, SpriteAccessor.ROTATION, 0.3f).target((float)Math.atan2(sector.position.y - lastSector.position.y, sector.position.x - lastSector.position.x)*180f/(float)Math.PI-90f))
                .push(Tween.to(this.spriteShip, SpriteAccessor.POSITION_XY, 0.5f).target(sector.position.x + 20, sector.position.y - 30).ease(TweenEquations.easeInOutQuint))
                //.push(Tween.to(this.spriteShip, SpriteAccessor.ROTATION, 0.5f).target(0).delay(-0.1f).ease(TweenEquations.easeInOutCubic))
        .start(tweenManager);


    }

    private void setSectorsDiscovered() {
        sector.visited = true;
        sector.discovered = true;
        for(Sector _sector : sector.connectedSectors){
            _sector.discovered = true;
        }
    }

    @Override
    public void prepareRenderingOnMap() {
        spriteDot = new Sprite(MagellanGame.assets.get("circle.png", Texture.class));
        spriteDot.setSize(24,24);
        spriteDot.setColor(Color.YELLOW);

        spriteShip = new Sprite(MagellanGame.assets.get("map_playership.png", Texture.class));
        spriteShip.setSize(20, 40);
        spriteShip.setOriginCenter();
        spriteShip.setPosition(sector.position.x + 20, sector.position.y - 30);
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        super.render(delta);
        spriteDot.setPosition(sector.position.x - spriteDot.getWidth()/2, sector.position.y - spriteDot.getHeight()/2);
        spriteDot.draw(batch);

        spriteShip.draw(batch);
    }

}
