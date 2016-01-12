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
public class DroneRoutineFollowing extends DroneRoutine{

    public DroneRoutineFollowing(Drone drone) {
        super(drone);
        routine = ROUTINES.FOLLOWING;
        sprite = new Sprite(MagellanGame.assets.get("drone_thruster.png", Texture.class));
        windowSprite = new Sprite(MagellanGame.assets.get("sectorview_drone_thruster.png", Texture.class));
    }

    public void tick(){
        if(MagellanGame.instance.universe.playerShip.sector != drone.sector){
            drone.moveTo(MagellanGame.instance.universe.playerShip.sector);
        }
    }
}
