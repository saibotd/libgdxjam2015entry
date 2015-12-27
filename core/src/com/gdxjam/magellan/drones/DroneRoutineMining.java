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

    private int ressourcesPerTick = 2;

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
            switch (metroidField.ressource){
                case 1:
                    MagellanGame.gameState.RESSOURCE1 += MathUtils.clamp(metroidField.ressourcePerTick, 0, ressourcesPerTick * powerLevel);
                    break;
                case 2:
                    MagellanGame.gameState.RESSOURCE2 += MathUtils.clamp(metroidField.ressourcePerTick, 0, ressourcesPerTick * powerLevel);
                    break;
                case 3:
                    MagellanGame.gameState.RESSOURCE3 += MathUtils.clamp(metroidField.ressourcePerTick, 0, ressourcesPerTick * powerLevel);
                    break;

            }
        }
    }
}
