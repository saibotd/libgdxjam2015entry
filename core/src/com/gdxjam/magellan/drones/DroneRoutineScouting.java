package com.gdxjam.magellan.drones;

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
