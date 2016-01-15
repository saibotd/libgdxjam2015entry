package com.gdxjam.magellan.drones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.gdxjam.magellan.Battle;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.gameobj.GameObj;
import com.gdxjam.magellan.gameobj.IDestroyable;
import com.gdxjam.magellan.ships.AiShip;

/**
 * Created by saibotd on 27.12.15.
 */
public class DroneRoutineFighting extends DroneRoutine{

    private int attack = 1;

    public DroneRoutineFighting(Drone drone) {
        super(drone);
        routine = ROUTINES.ATTACKING;
        sprite = new Sprite(MagellanGame.assets.get("drone_gun.png", Texture.class));
        windowSprite = new Sprite(MagellanGame.assets.get("sectorview_drone_gun.png", Texture.class));
    }

    public void tick(){
        for(GameObj gameObj : drone.sector.gameObjs){
            if(gameObj instanceof AiShip){
                new Battle(drone, (IDestroyable) gameObj);
                if (drone.faction == GameObj.Factions.PLAYER) {
                    MagellanGame.instance.mapScreen.log.addEntry("Attack drone is engaging an enemy!", drone.sector);
                }
            }
        }
    }

    public int getAttack() {
        return (int) MathUtils.clamp(attack * powerLevel, 1, 5);
    }
}
