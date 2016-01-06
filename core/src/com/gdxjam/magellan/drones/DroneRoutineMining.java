package com.gdxjam.magellan.drones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.gdxjam.magellan.gameobj.GameObj;
import com.gdxjam.magellan.MagellanGame;
import com.gdxjam.magellan.gameobj.MeteoroidField;

/**
 * Created by saibotd on 27.12.15.
 */
public class DroneRoutineMining extends DroneRoutine{

    private int resourcesPerTick = 2;

    public DroneRoutineMining(Drone drone) {
        super(drone);
        routine = ROUTINES.MINING;
        sprite = new Sprite(MagellanGame.assets.get("drone_mine.png", Texture.class));
        windowSprite = new Sprite(MagellanGame.assets.get("sectorview_drone_mine.png", Texture.class));
    }

    public void tick(){
        Array<MeteoroidField> metroidFields = new Array();
        for(GameObj gameObj : drone.sector.gameObjs){
            if(gameObj instanceof MeteoroidField){
                metroidFields.add((MeteoroidField) gameObj);
            }
        }
        for(MeteoroidField meteoroidField :metroidFields){
            switch (meteoroidField.resource){
                case 1:
                    MagellanGame.gameState.RESOURCE1 += meteoroidField.mine(Math.round(resourcesPerTick * powerLevel));
                    break;
                case 2:
                    MagellanGame.gameState.RESOURCE2 += meteoroidField.mine(Math.round(resourcesPerTick * powerLevel));
                    break;
                case 3:
                    MagellanGame.gameState.RESOURCE3 += meteoroidField.mine(Math.round(resourcesPerTick * powerLevel));
                    break;

            }
        }
    }
}
