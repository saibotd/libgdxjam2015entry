package com.gdxjam.magellan.drones;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.gdxjam.magellan.GameObj;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.MetroidField;

/**
 * Created by saibotd on 27.12.15.
 */
public class DroneRoutineMining extends DroneRoutine{

    private int resourcesPerTick = 2;

    public DroneRoutineMining(Drone drone) {
        super(drone);
        routine = ROUTINES.MINING;
    }

    public void tick(){
        Array<MetroidField> metroidFields = new Array();
        for(GameObj gameObj : drone.sector.gameObjs){
            if(gameObj instanceof MetroidField){
                metroidFields.add((MetroidField) gameObj);
            }
        }
        for(MetroidField metroidField:metroidFields){
            switch (metroidField.resource){
                case 1:
                    MagellanGame.gameState.RESOURCE1 += MathUtils.clamp(metroidField.resourcePerTick, 0, resourcesPerTick * powerLevel);
                    break;
                case 2:
                    MagellanGame.gameState.RESOURCE2 += MathUtils.clamp(metroidField.resourcePerTick, 0, resourcesPerTick * powerLevel);
                    break;
                case 3:
                    MagellanGame.gameState.RESOURCE3 += MathUtils.clamp(metroidField.resourcePerTick, 0, resourcesPerTick * powerLevel);
                    break;

            }
        }
    }
}
