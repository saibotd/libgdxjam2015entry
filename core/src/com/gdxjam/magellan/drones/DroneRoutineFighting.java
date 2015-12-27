package com.gdxjam.magellan.drones;

/**
 * Created by saibotd on 27.12.15.
 */
public class DroneRoutineFighting extends DroneRoutine{

    private int attack = 2;

    public DroneRoutineFighting(Drone drone) {
        super(drone);
        routine = ROUTINES.ATTACKING;
    }

    public void tick(){

    }
}
