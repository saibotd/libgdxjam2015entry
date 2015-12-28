package com.gdxjam.magellan.ships;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.gdxjam.magellan.MagellanColors;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.Sector;
import com.gdxjam.magellan.SpriteAccessor;
import com.gdxjam.magellan.drones.Drone;
import com.gdxjam.magellan.drones.DroneRoutineScouting;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class PlayerShip extends Ship {

    public int HUMANS = 10000;
    public Array<Integer> drones = new Array();

    public PlayerShip(Sector sector) {
        super(sector);
        faction = Factions.PLAYER;
        setSectorsDiscovered();
        drones.add(1);
        drones.add(2);
        drones.add(2);
        drones.add(2);
        drones.add(3);
        drones.add(4);
        drones.add(5);
    }

    public void moveTo(Sector sector) {
        super.moveTo(sector);
        setSectorsDiscovered();
        tweenManager.killAll();
        Timeline.createSequence()
                .push(Tween.to(this.spriteShip, SpriteAccessor.ROTATION, 0.3f).target((float)Math.atan2(sector.position.y - lastSector.position.y, sector.position.x - lastSector.position.x)*180f/(float)Math.PI-90f))
                .push(Tween.to(this.spriteShip, SpriteAccessor.POSITION_XY, 0.5f).target(sector.position.x + 20, sector.position.y - 30).ease(TweenEquations.easeInOutQuint))
                .push(Tween.to(this.spriteShip, SpriteAccessor.ROTATION, 1f).target(50).ease(TweenEquations.easeInOutCubic))
                .push(Tween.to(this.spriteShip, SpriteAccessor.POSITION_XY, 0.5f).target(sector.position.x + 12, sector.position.y - 22).ease(TweenEquations.easeInOutCubic)).delay(-0.2f)
        .start(tweenManager);
    }

    public void releaseDrone(int level){
        drones.removeValue(level, false);
        Drone drone = new Drone(this.sector, level);
        drone.faction = faction;
        drone.addRoutine(new DroneRoutineScouting(drone));
        MagellanGame.gameState.updateNumberOfDrones();
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
        //spriteDot = new Sprite(MagellanGame.assets.get("circle.png", Texture.class));
        //spriteDot.setSize(24,24);
        //spriteDot.setColor(Color.YELLOW);

        spriteShip = new Sprite(MagellanGame.assets.get("map_playership.png", Texture.class));
        spriteShip.setSize(10, 20);
        spriteShip.setOriginCenter();
        spriteShip.setPosition(sector.position.x + 12, sector.position.y - 22);
        spriteShip.setRotation(50);
        spriteShip.setColor(MagellanColors.FACTION_PLAYER);
    }

    @Override
    public void renderOnMap(SpriteBatch batch, float delta) {
        super.render(delta);
        //spriteDot.setPosition(sector.position.x - spriteDot.getWidth()/2, sector.position.y - spriteDot.getHeight()/2);
        //spriteDot.draw(batch);

        spriteShip.draw(batch);
    }

}
