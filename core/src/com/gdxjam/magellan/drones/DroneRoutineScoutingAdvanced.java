package com.gdxjam.magellan.drones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Predicate;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.Sector;

/**
 * Created by saibotd on 27.12.15.
 */
public class DroneRoutineScoutingAdvanced extends DroneRoutine{

    private int wait = 5;

    public DroneRoutineScoutingAdvanced(Drone drone) {
        super(drone);
        routine = ROUTINES.SCOUTING;
        sprite = new Sprite(MagellanGame.assets.get("drone_thruster.png", Texture.class));
    }

    public void tick(){
        wait -= powerLevel;
        if(wait <= 0) {
            Sector sector;
            Array<Sector> notVisitedSectors = new Array<Sector>();
            for (Sector _sector:drone.sector.connectedSectors) {
                if (_sector.visited == false)
                    notVisitedSectors.add(_sector);
            }
            if (notVisitedSectors.size == 0) {
                sector = drone.sector.connectedSectors.random();
            } else {
                sector = notVisitedSectors.random();
            }
            sector.discovered = true;
            sector.visited = true;
            for(Sector _sector: sector.connectedSectors){
                _sector.discovered = true;
            }
            drone.moveTo(sector);
            wait = 5;
        }
    }
}
