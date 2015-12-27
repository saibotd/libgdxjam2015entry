package com.gdxjam.magellan.drones;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.gdxjam.magellan.GameObj;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.MetroidField;

/**
 * Created by saibotd on 27.12.15.
 */
public class DroneRoutineScouting extends DroneRoutine{

    private int wait = 5;

    public DroneRoutineScouting(Drone drone) {
        super(drone);
        routine = ROUTINES.SCOUTING;
    }

    public void tick(){
        wait -= powerLevel;
        if(wait <= 0) {
            drone.moveTo(drone.sector.connectedSectors.random());
            wait = 5;
        }
    }
}
