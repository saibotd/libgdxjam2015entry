package com.gdxjam.magellan.drones;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.OrderedMap;
import com.gdxjam.magellan.gameobj.GameObj;
import com.gdxjam.magellan.gameobj.IDestroyable;
import com.gdxjam.magellan.gameobj.IInteractable;

/**
 * Created by saibotd on 27.12.15.
 */
public class DroneRoutine {
    public Drone drone;
    public float powerLevel;
    public Sprite sprite;
    public Sprite windowSprite;

    public enum ROUTINES{ MINING, SCOUTING, ATTACKING, ADVSCOUTING, FOLLOWING, REPAIRING }
    public ROUTINES routine;

    public DroneRoutine(Drone drone){
        this.drone = drone;
        drone.addRoutine(this);

        sprite = new Sprite();
        windowSprite = new Sprite();
    }
    

    public void setPowerLevel(float powerLevel) {
        this.powerLevel = powerLevel;
    }

    public void tick(){

    }

    public void receiveDamage(int damage) {

    }

    public OrderedMap<String, IInteractable.Interaction> getInteractions(GameObj with) {
        OrderedMap<String, IInteractable.Interaction> interactions = new OrderedMap();
        return interactions;
    }

    public void shootAt(IDestroyable target) {

    }

    public void render(SpriteBatch batch, float delta) {
        sprite.setOriginCenter();
        sprite.setPosition(drone.spriteVessel.getX(), drone.spriteVessel.getY());
        sprite.setRotation(drone.spriteVessel.getRotation());
        sprite.setSize(drone.spriteVessel.getWidth(), drone.spriteVessel.getHeight());
        sprite.draw(batch);
    }
}
