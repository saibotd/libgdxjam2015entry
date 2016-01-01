package com.gdxjam.magellan.drones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gdxjam.magellan.MagellanGame;

/**
 * Created by saibotd on 27.12.15.
 */
public class DroneRoutineFighting extends DroneRoutine{

    private int attack = 2;

    public DroneRoutineFighting(Drone drone) {
        super(drone);
        routine = ROUTINES.ATTACKING;
        sprite = new Sprite(MagellanGame.assets.get("drone_gun.png", Texture.class));
    }

    public void tick(){

    }
}
