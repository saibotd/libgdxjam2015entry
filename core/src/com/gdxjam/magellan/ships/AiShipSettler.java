package com.gdxjam.magellan.ships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.gdxjam.magellan.*;
import com.gdxjam.magellan.drones.Drone;
import com.gdxjam.magellan.gameobj.GameObj;
import com.gdxjam.magellan.gameobj.IDestroyable;
import com.gdxjam.magellan.gameobj.Planet;

/**
 * Created by lolcorner on 20.12.2015.
 */
public class AiShipSettler extends AiShip {

    public AiShipSettler(Sector sector) {
        super(sector);
        health = 10;
        attack = 2;
        shield = 0.3f;
        faction = Factions.SAATOO;
    }

    @Override
    public void prepareRenderingOnMap() {
        super.prepareRenderingOnMap();
        spriteVessel = new Sprite(MagellanGame.assets.get("enemy_transport.png", Texture.class));
        spriteVessel.setSize(20, 20);
        spriteVessel.setOriginCenter();

        getFreeSectorSlot();
        getParkingPosition();

        spriteVessel.setPosition(parkingPosition.x, parkingPosition.y);
        spriteVessel.setRotation(parkingAngle);
    }

    @Override
    public Actor getActor() {
        Image image = new Image(MagellanGame.assets.get("sectorview_enemy_transporter.png", Texture.class));
        Image imageShield = new Image(MagellanGame.assets.get("sectorview_enemy_transporter_shield.png", Texture.class));
        imageShield.setColor(1,1,1,0);

        Stack stack = new Stack();
        stack.setSize(500,370);
        stack.setUserObject(this);
        stack.addActor(image);
        stack.addActor(imageShield);

        return stack;
    }

    private void decideState(){
        target = null;
        for (int i = 0; i < sector.gameObjs.size; i++){
            GameObj gameObj = sector.gameObjs.get(i);
            if(gameObj instanceof IDestroyable && gameObj.faction == Factions.PLAYER){
                if(gameObj instanceof  PlayerShip){
                    if(Math.random() < .3){
                        target = (IDestroyable) gameObj;
                    }
                    break;
                } else {
                    if(Math.random() < .3){
                        target = (IDestroyable) gameObj;
                    }
                }
            }
            if(gameObj instanceof Planet && gameObj.faction == Factions.NEUTRAL){
                Planet planet = (Planet) gameObj;
                planet.claim(this);
                planet.resource1 += MathUtils.random(100,200);
                planet.resource2 += MathUtils.random(100,200);
                planet.resource3 += MathUtils.random(100,200);
                planet.populate(this,  MathUtils.random(500,1500));
            }
        }
        if(target != null && target.isAlive() && MagellanGame.gameState.AI_HOSTILITY >= 5){
            state = States.FLEEING;
            if(Math.random() < .2){
                state = States.HOSTILE;
            }
            return;
        }
        state = States.IDLE;
    }

    public void passiveTick(){
        decideState();
        switch (state){
            case IDLE:
                if(Math.random() < .5) super.passiveTick();
                break;
            case FLEEING:
                super.passiveTick();
                break;
        }
    }

    public void activeTick(){
        super.activeTick();
        decideState();
        if(state == States.HOSTILE){
            if (target instanceof Drone && ((Drone) target).faction == Factions.PLAYER) {
                MagellanGame.instance.mapScreen.log.addEntry("Your drone is under attack!", sector);
            }
            if (target instanceof Planet && ((Planet) target).faction == Factions.PLAYER) {
                MagellanGame.instance.mapScreen.log.addEntry("Your planet is under attack!", sector);
            }
            new Battle(this, target);
        }
    }

}
