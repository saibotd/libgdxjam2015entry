package com.gdxjam.magellan.drones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.Sector;

/**
 * Created by saibotd on 27.12.15.
 */
public class DroneRoutineScouting extends DroneRoutine{

    private int wait = 5;

    public DroneRoutineScouting(Drone drone) {
        super(drone);
        routine = ROUTINES.SCOUTING;
        sprite = new Sprite(MagellanGame.assets.get("drone_thruster.png", Texture.class));
        windowSprite = new Sprite(MagellanGame.assets.get("sectorview_drone_thruster.png", Texture.class));
    }

    public void tick(){
        wait -= powerLevel;
        if(wait <= 0) {
            Sector sector = drone.sector.connectedSectors.random();
            sector.discovered = true;
            sector.visited = true;
            for(Sector _sector : sector.connectedSectors){
                for (Sector __sector : _sector.connectedSectors) {
                    __sector.discovered = true;
                }
                _sector.discovered = true;
                _sector.visited = true;
            }
            drone.moveTo(sector);
            wait = 5;
        }
    }
}
