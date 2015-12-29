package com.gdxjam.magellan.drones;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdxjam.magellan.GameObj;
import com.gdxjam.magellan.IDestroyable;
import com.gdxjam.magellan.IInteractable;
import com.gdxjam.magellan.MagellanGame;

/**
 * Created by saibotd on 27.12.15.
 */
public class DroneRoutine {
    public Drone drone;
    public float powerLevel;
    public Sprite sprite;

    enum ROUTINES{ MINING, DEFENDING, SCOUTING, ATTACKING }
    public ROUTINES routine;

    public DroneRoutine(Drone drone){
        this.drone = drone;
        drone.addRoutine(this);
    }
    

    public void setPowerLevel(float powerLevel) {
        this.powerLevel = powerLevel;
    }

    public void tick(){

    }

    public void receiveDamage(int damage) {

    }

    public ObjectMap<String, IInteractable.Interaction> getInteractions(GameObj with) {
        return null;
    }

    public void shootAt(IDestroyable target) {

    }

    public void render(SpriteBatch batch, float delta) {

    }
}
