package com.gdxjam.magellan.drones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gdxjam.magellan.MagellanGame;

/**
 * Created by saibotd on 27.12.15.
 */
public class DroneRoutineReparing extends DroneRoutine{

    public DroneRoutineReparing(Drone drone) {
        super(drone);
        routine = ROUTINES.REPAIRING;
        sprite = new Sprite(MagellanGame.assets.get("drone_mine.png", Texture.class));
        windowSprite = new Sprite(MagellanGame.assets.get("sectorview_drone_mine.png", Texture.class));
    }

    public void tick(){
        if(MagellanGame.instance.universe.playerShip.sector == drone.sector){
            MagellanGame.instance.universe.playerShip.heal(powerLevel);
        }
    }
}
